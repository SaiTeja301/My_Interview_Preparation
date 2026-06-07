/**
 * ============================================================
 * config.js — Configuration for the JavaScript Client
 * ============================================================
 *
 * Loads configuration from environment variables (via .env file)
 * with sensible defaults for local development.
 *
 * USAGE:
 *   const config = require('./config');
 *   console.log(config.BEDROCK_API_URL); // "http://localhost:8080"
 */

// Load .env file into process.env
require('dotenv').config();

module.exports = {
    // Port this JS microservice listens on
    PORT: process.env.PORT || 3000,

    // Base URL of the Spring Boot Bedrock API
    // In Kubernetes: http://bedrock-api-service:8080
    // In Docker Compose: http://bedrock-api:8080
    // Local development: http://localhost:8080
    BEDROCK_API_URL: process.env.BEDROCK_API_URL || 'http://localhost:8080',

    // Request timeout (in milliseconds)
    // Bedrock models can take 30-60s for long responses
    REQUEST_TIMEOUT: parseInt(process.env.REQUEST_TIMEOUT) || 120000,

    // Maximum retries for failed requests
    MAX_RETRIES: parseInt(process.env.MAX_RETRIES) || 3,

    // Delay between retries (in milliseconds)
    RETRY_DELAY: parseInt(process.env.RETRY_DELAY) || 1000,
};
