/**
 * ============================================================
 * chatRoutes.js — Express Routes for the JS Client
 * ============================================================
 *
 * These routes provide a frontend-friendly API that proxies
 * requests to the Spring Boot Bedrock API.
 *
 * WHY A JS PROXY?
 *   1. Adds a layer between frontend and backend (security)
 *   2. Can aggregate multiple backend calls into one response
 *   3. Can transform data formats for the frontend
 *   4. Demonstrates microservice-to-microservice communication
 */

const express = require('express');
const router = express.Router();
const bedrockClient = require('../services/bedrockClient');

/**
 * POST /chat — Send a prompt via the JS client
 *
 * CURL:
 *   curl -X POST http://localhost:3000/chat \
 *     -H "Content-Type: application/json" \
 *     -d '{"prompt": "What is AWS Bedrock?"}'
 */
router.post('/chat', async (req, res) => {
    try {
        const { prompt, modelId, systemPrompt, maxTokens, temperature } = req.body;

        if (!prompt) {
            return res.status(400).json({ error: 'prompt is required' });
        }

        console.log(`📤 Forwarding chat request to Spring Boot API | Prompt: ${prompt.substring(0, 50)}...`);

        const response = await bedrockClient.chat({
            prompt,
            modelId,
            systemPrompt,
            maxTokens,
            temperature,
        });

        console.log(`✅ Response received | Tokens: ${response.data.totalTokens} | Latency: ${response.data.latencyMs}ms`);

        res.json(response.data);
    } catch (error) {
        console.error('❌ Chat error:', error.response?.data || error.message);
        res.status(error.response?.status || 500).json({
            error: error.response?.data?.message || 'Failed to get response from Bedrock API',
        });
    }
});

/**
 * POST /jobs/analyze — Analyze job data with AI
 *
 * CURL:
 *   curl -X POST http://localhost:3000/jobs/analyze \
 *     -H "Content-Type: application/json" \
 *     -d '{"question": "Which Java jobs should I apply to?"}'
 */
router.post('/jobs/analyze', async (req, res) => {
    try {
        const { question, keyword, company, location, includeFullDescription, analysisType } = req.body;

        if (!question) {
            return res.status(400).json({ error: 'question is required' });
        }

        console.log(`🤖 Forwarding job analysis request | Question: ${question.substring(0, 50)}...`);

        const response = await bedrockClient.analyzeJobs({
            question,
            keyword,
            company,
            location,
            includeFullDescription,
            analysisType,
        });

        res.json(response.data);
    } catch (error) {
        console.error('❌ Job analysis error:', error.response?.data || error.message);
        res.status(error.response?.status || 500).json({
            error: error.response?.data?.message || 'Failed to analyze jobs',
        });
    }
});

/**
 * GET /models — List available Bedrock models
 *
 * CURL: curl http://localhost:3000/models
 */
router.get('/models', async (req, res) => {
    try {
        const response = await bedrockClient.listModels();
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: 'Failed to fetch models' });
    }
});

/**
 * GET /jobs — List all jobs from the database
 *
 * CURL: curl http://localhost:3000/jobs
 */
router.get('/jobs', async (req, res) => {
    try {
        const response = await bedrockClient.getJobs();
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: 'Failed to fetch jobs' });
    }
});

/**
 * GET /jobs/search?keyword=Java — Search jobs
 *
 * CURL: curl "http://localhost:3000/jobs/search?keyword=Java"
 */
router.get('/jobs/search', async (req, res) => {
    try {
        const { keyword } = req.query;
        if (!keyword) {
            return res.status(400).json({ error: 'keyword query parameter is required' });
        }
        const response = await bedrockClient.searchJobs(keyword);
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: 'Failed to search jobs' });
    }
});

/**
 * GET /jobs/stats — Get job statistics
 *
 * CURL: curl http://localhost:3000/jobs/stats
 */
router.get('/jobs/stats', async (req, res) => {
    try {
        const response = await bedrockClient.getJobStats();
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: 'Failed to fetch job stats' });
    }
});

/**
 * GET /health — Health check (checks both JS client AND Spring Boot API)
 *
 * CURL: curl http://localhost:3000/health
 */
router.get('/health', async (req, res) => {
    try {
        const backendHealth = await bedrockClient.healthCheck();
        res.json({
            jsClient: { status: 'UP', service: 'bedrock-js-client', port: 3000 },
            springBootApi: backendHealth.data,
        });
    } catch (error) {
        res.json({
            jsClient: { status: 'UP', service: 'bedrock-js-client', port: 3000 },
            springBootApi: { status: 'DOWN', error: error.message },
        });
    }
});

module.exports = router;
