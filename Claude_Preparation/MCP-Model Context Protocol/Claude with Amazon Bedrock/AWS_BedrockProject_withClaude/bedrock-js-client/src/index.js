/**
 * ============================================================
 * index.js — Express Server Entry Point
 * ============================================================
 *
 * This is the JavaScript microservice that calls the Spring Boot
 * Bedrock API. It acts as a lightweight proxy / BFF (Backend for Frontend).
 *
 * MICROSERVICE COMMUNICATION FLOW:
 * ═════════════════════════════════
 *
 *   Browser/Postman → JS Client (Express:3000) → Spring Boot (Tomcat:8080) → AWS Bedrock
 *                     ↕                          ↕                            ↕
 *                   This file              BedrockController            Claude/Titan/Llama
 *                                          JobAnalysisController        (Foundation Models)
 *                                                ↕
 *                                          MySQL DB
 *                                          (linkedin_naukr_jobs.jobs)
 *
 * HOW TO RUN:
 *   1. Install dependencies: npm install
 *   2. Ensure Spring Boot API is running on port 8080
 *   3. Start this server: npm start (or npm run dev for hot-reload)
 *   4. Test: curl http://localhost:3000/health
 */

const express = require('express');
const cors = require('cors');
const morgan = require('morgan');
const config = require('./config');
const chatRoutes = require('./routes/chatRoutes');

// Create Express application
const app = express();

// =============================================
// MIDDLEWARE
// =============================================

// CORS — Allow cross-origin requests from any domain
app.use(cors());

// Morgan — HTTP request logger (dev format shows method, URL, status, response time)
app.use(morgan('dev'));

// JSON body parser — Parse incoming JSON request bodies
app.use(express.json({ limit: '10mb' }));

// =============================================
// ROUTES
// =============================================

// Mount all routes (no prefix — routes are defined in chatRoutes.js)
app.use('/', chatRoutes);

// Root endpoint — welcome message
app.get('/', (req, res) => {
    res.json({
        service: 'bedrock-js-client',
        version: '1.0.0',
        description: 'JavaScript microservice that calls the Spring Boot Bedrock API',
        endpoints: {
            'POST /chat': 'Send a prompt to Bedrock via Spring Boot API',
            'POST /jobs/analyze': 'Analyze job data with Bedrock AI',
            'GET /models': 'List available Bedrock models',
            'GET /jobs': 'List all jobs from database',
            'GET /jobs/search?keyword=Java': 'Search jobs by keyword',
            'GET /jobs/stats': 'Get job statistics',
            'GET /health': 'Health check (JS client + Spring Boot API)',
        },
        springBootApiUrl: config.BEDROCK_API_URL,
    });
});

// =============================================
// ERROR HANDLING
// =============================================

// 404 handler — for unmatched routes
app.use((req, res) => {
    res.status(404).json({
        error: 'Not Found',
        message: `Route ${req.method} ${req.path} not found`,
        availableEndpoints: 'GET / for a list of endpoints',
    });
});

// Global error handler
app.use((err, req, res, next) => {
    console.error('❌ Unhandled error:', err);
    res.status(500).json({
        error: 'Internal Server Error',
        message: 'An unexpected error occurred',
    });
});

// =============================================
// START SERVER
// =============================================
app.listen(config.PORT, () => {
    console.log('╔══════════════════════════════════════════════╗');
    console.log('║   🚀 Bedrock JS Client Microservice         ║');
    console.log('╠══════════════════════════════════════════════╣');
    console.log(`║   Port:     ${config.PORT}                            ║`);
    console.log(`║   Backend:  ${config.BEDROCK_API_URL.padEnd(32)}║`);
    console.log('║   Status:   Running ✅                       ║');
    console.log('╚══════════════════════════════════════════════╝');
    console.log('');
    console.log('Available endpoints:');
    console.log('  POST /chat              → Send prompt to Bedrock');
    console.log('  POST /jobs/analyze      → AI job analysis');
    console.log('  GET  /models            → List Bedrock models');
    console.log('  GET  /jobs              → List all jobs');
    console.log('  GET  /jobs/search       → Search jobs');
    console.log('  GET  /jobs/stats        → Job statistics');
    console.log('  GET  /health            → Health check');
});
