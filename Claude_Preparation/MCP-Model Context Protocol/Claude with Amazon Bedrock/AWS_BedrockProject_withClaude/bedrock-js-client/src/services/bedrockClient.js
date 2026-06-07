/**
 * ============================================================
 * bedrockClient.js — HTTP Client to Spring Boot Bedrock API
 * ============================================================
 *
 * This module encapsulates all HTTP communication with the
 * Spring Boot Bedrock API microservice.
 *
 * MICROSERVICE COMMUNICATION PATTERN:
 * ═══════════════════════════════════
 *
 *   ┌──────────────────┐  HTTP/JSON   ┌──────────────────┐  AWS SDK   ┌──────────────┐
 *   │  JS Client       │─────────────▶│  Spring Boot API │───────────▶│  AWS Bedrock  │
 *   │  (Express:3000)  │◀─────────────│  (Tomcat:8080)   │◀───────────│  (Claude AI)  │
 *   └──────────────────┘  Response     └──────────────────┘  Response  └──────────────┘
 *
 *   The JS client NEVER calls AWS Bedrock directly.
 *   It only knows about the Spring Boot API's REST endpoints.
 *   This is the "Backend for Frontend" (BFF) pattern.
 *
 * RETRY LOGIC:
 *   If a request fails (network error, 5xx), we retry up to
 *   MAX_RETRIES times with exponential backoff:
 *     Attempt 1: immediate
 *     Attempt 2: wait 1s
 *     Attempt 3: wait 2s
 */

const axios = require('axios');
const config = require('../config');

// Create a reusable Axios instance with base configuration
const apiClient = axios.create({
    baseURL: config.BEDROCK_API_URL,
    timeout: config.REQUEST_TIMEOUT,
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    },
});

/**
 * Send a chat prompt to the Bedrock API.
 *
 * @param {Object} params - Request parameters
 * @param {string} params.prompt - The user's prompt (required)
 * @param {string} [params.modelId] - Bedrock model ID (optional)
 * @param {string} [params.systemPrompt] - System prompt (optional)
 * @param {number} [params.maxTokens=4096] - Max response tokens
 * @param {number} [params.temperature=0.7] - Creativity level (0-1)
 * @returns {Promise<Object>} Bedrock response with text, tokens, latency
 *
 * @example
 *   const response = await chat({
 *     prompt: "Explain microservices",
 *     modelId: "apac.anthropic.claude-sonnet-4-20250514-v1:0",
 *     maxTokens: 512
 *   });
 *   console.log(response.data.response); // "Microservices is..."
 */
async function chat(params) {
    return retryRequest(() =>
        apiClient.post('/api/v1/chat', {
            prompt: params.prompt,
            modelId: params.modelId || null,
            systemPrompt: params.systemPrompt || null,
            maxTokens: params.maxTokens || 4096,
            temperature: params.temperature || 0.7,
            topP: params.topP || 0.9,
        })
    );
}

/**
 * Analyze jobs from the database using Bedrock AI.
 *
 * @param {Object} params - Analysis parameters
 * @param {string} params.question - What to analyze (required)
 * @param {string} [params.keyword] - Filter by keyword
 * @param {string} [params.company] - Filter by company
 * @param {string} [params.location] - Filter by location
 * @param {boolean} [params.includeFullDescription] - Include full descriptions
 * @returns {Promise<Object>} AI analysis of job data
 *
 * @example
 *   const analysis = await analyzeJobs({
 *     question: "Which Java jobs have the least competition?",
 *     keyword: "Java"
 *   });
 */
async function analyzeJobs(params) {
    return retryRequest(() =>
        apiClient.post('/api/v1/jobs/analyze', {
            question: params.question,
            keyword: params.keyword || null,
            company: params.company || null,
            location: params.location || null,
            platform: params.platform || null,
            includeFullDescription: params.includeFullDescription || false,
            analysisType: params.analysisType || 'JOBS',
            modelId: params.modelId || null,
        })
    );
}

/**
 * List all available Bedrock models.
 * @returns {Promise<Object>} Map of model IDs to descriptions
 */
async function listModels() {
    return retryRequest(() => apiClient.get('/api/v1/models'));
}

/**
 * Get all jobs from the database.
 * @returns {Promise<Object>} List of Job objects
 */
async function getJobs() {
    return retryRequest(() => apiClient.get('/api/v1/jobs'));
}

/**
 * Search jobs by keyword.
 * @param {string} keyword - Search keyword
 * @returns {Promise<Object>} List of matching Job objects
 */
async function searchJobs(keyword) {
    return retryRequest(() =>
        apiClient.get('/api/v1/jobs/search', { params: { keyword } })
    );
}

/**
 * Get job statistics.
 * @returns {Promise<Object>} Aggregated job statistics
 */
async function getJobStats() {
    return retryRequest(() => apiClient.get('/api/v1/jobs/stats'));
}

/**
 * Health check — verify the Spring Boot API is running.
 * @returns {Promise<Object>} Health status
 */
async function healthCheck() {
    return retryRequest(() => apiClient.get('/api/v1/health'));
}

/**
 * Retry wrapper with exponential backoff.
 *
 * HOW EXPONENTIAL BACKOFF WORKS:
 *   Attempt 1: immediate call
 *   Attempt 2: wait 1s, then retry
 *   Attempt 3: wait 2s, then retry
 *   Attempt 4: wait 4s, then retry
 *   ...each wait doubles
 *
 * @param {Function} fn - The function to retry
 * @returns {Promise<any>} The successful response
 */
async function retryRequest(fn) {
    let lastError;

    for (let attempt = 1; attempt <= config.MAX_RETRIES; attempt++) {
        try {
            return await fn();
        } catch (error) {
            lastError = error;

            // Don't retry client errors (4xx) — they won't succeed on retry
            if (error.response && error.response.status >= 400 && error.response.status < 500) {
                throw error;
            }

            // Log and retry server errors (5xx) and network errors
            if (attempt < config.MAX_RETRIES) {
                const delay = config.RETRY_DELAY * Math.pow(2, attempt - 1);
                console.warn(
                    `⚠️ Request failed (attempt ${attempt}/${config.MAX_RETRIES}). Retrying in ${delay}ms...`,
                    error.message
                );
                await new Promise(resolve => setTimeout(resolve, delay));
            }
        }
    }

    throw lastError;
}

module.exports = {
    chat,
    analyzeJobs,
    listModels,
    getJobs,
    searchJobs,
    getJobStats,
    healthCheck,
};
