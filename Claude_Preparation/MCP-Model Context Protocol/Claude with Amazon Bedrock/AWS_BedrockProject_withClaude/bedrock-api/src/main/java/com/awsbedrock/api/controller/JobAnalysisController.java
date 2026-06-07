package com.awsbedrock.api.controller;

import com.awsbedrock.api.dto.BedrockResponse;
import com.awsbedrock.api.dto.JobAnalysisRequest;
import com.awsbedrock.api.dto.PromptRequest;
import com.awsbedrock.api.entity.Job;
import com.awsbedrock.api.exception.RateLimitExceededException;
import com.awsbedrock.api.service.BedrockService;
import com.awsbedrock.api.service.JobService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ============================================================
 * JobAnalysisController — AI-Powered Job Data Analysis
 * ============================================================
 *
 * This controller demonstrates the MCP (Model Context Protocol)
 * concept by connecting a DATABASE to an AI MODEL:
 *
 *   ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
 *   │  MySQL        │────▶│  Spring Boot  │────▶│  AWS Bedrock  │
 *   │  (Jobs Table) │     │  (MCP Bridge) │     │  (Claude AI)  │
 *   └──────────────┘     └──────────────┘     └──────────────┘
 *         DB Data          Context Builder        AI Analysis
 *
 * WHAT IS MCP (Model Context Protocol)?
 * ═════════════════════════════════════
 * MCP standardises how AI models access external tools and data:
 *
 *   1. MCP SERVER (this Spring Boot app):
 *      → Connects to data sources (MySQL, APIs, files)
 *      → Exposes structured context to the AI model
 *      → Handles tool calls and data retrieval
 *
 *   2. MCP CLIENT (the AI model / Claude):
 *      → Receives context about available data
 *      → Decides what data to request
 *      → Analyzes the data to answer user questions
 *
 *   3. MCP PROTOCOL:
 *      → Standardises the format for context exchange
 *      → Supports "tools" (functions the AI can call)
 *      → Supports "resources" (data the AI can read)
 *
 * In this implementation, we simulate MCP by:
 *   - Fetching data from MySQL (MCP resource)
 *   - Converting it to structured context (MCP context)
 *   - Injecting it into the Bedrock prompt (MCP protocol)
 *   - Getting AI analysis back (MCP response)
 *
 * ENDPOINTS:
 *   POST /api/v1/jobs/analyze         → AI analyzes job data
 *   GET  /api/v1/jobs                 → List all jobs
 *   GET  /api/v1/jobs/{id}            → Get job by ID
 *   GET  /api/v1/jobs/search?keyword= → Search jobs
 *   GET  /api/v1/jobs/stats           → Job statistics
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobAnalysisController {

    private final JobService jobService;
    private final BedrockService bedrockService;
    private final Bucket rateLimitBucket;

    /**
     * ============================================================
     * POST /api/v1/jobs/analyze — AI-Powered Job Analysis
     * ============================================================
     *
     * This is the STAR endpoint that combines database + AI:
     *
     * FLOW (MCP-style):
     *   1. Receive user's question about jobs
     *   2. Fetch relevant jobs from MySQL (based on filters)
     *   3. Convert jobs to structured context text
     *   4. Build a Bedrock prompt: system context + job data + question
     *   5. Call AWS Bedrock (Claude) with the combined prompt
     *   6. Return AI analysis to the user
     *
     * CURL EXAMPLES:
     *
     *   # Analyze all jobs
     *   curl -X POST http://localhost:8080/api/v1/jobs/analyze \
     *     -H "Content-Type: application/json" \
     *     -d '{
     *       "question": "Which jobs have the least competition and I should apply to?"
     *     }'
     *
     *   # Analyze Java jobs with full descriptions
     *   curl -X POST http://localhost:8080/api/v1/jobs/analyze \
     *     -H "Content-Type: application/json" \
     *     -d '{
     *       "question": "What are the top skills required for Java developer roles?",
     *       "keyword": "Java",
     *       "includeFullDescription": true
     *     }'
     *
     *   # Analyze jobs by company
     *   curl -X POST http://localhost:8080/api/v1/jobs/analyze \
     *     -H "Content-Type: application/json" \
     *     -d '{
     *       "question": "Compare job offerings from this company",
     *       "company": "TCS"
     *     }'
     *
     *   # Get analytics summary
     *   curl -X POST http://localhost:8080/api/v1/jobs/analyze \
     *     -H "Content-Type: application/json" \
     *     -d '{
     *       "question": "Give me a hiring trends report and recommendations",
     *       "analysisType": "ANALYTICS"
     *     }'
     */
    @PostMapping("/analyze")
    public ResponseEntity<BedrockResponse> analyzeJobs(
            @Valid @RequestBody JobAnalysisRequest request) {

        // Rate limiting
        if (!rateLimitBucket.tryConsume(1)) {
            throw new RateLimitExceededException(
                    "Rate limit exceeded. Please wait before making another request.");
        }

        log.info("🤖 Job analysis requested | Question: {} | Filters: keyword={}, company={}, location={}",
                request.getQuestion().substring(0, Math.min(50, request.getQuestion().length())),
                request.getKeyword(), request.getCompany(), request.getLocation());

        // =============================================
        // STEP 1: Fetch relevant jobs from database
        // =============================================
        List<Job> jobs = fetchFilteredJobs(request);
        log.info("📊 Fetched {} jobs from database for analysis", jobs.size());

        // =============================================
        // STEP 2: Build context string (MCP Context)
        // =============================================
        String context;
        if ("ANALYTICS".equalsIgnoreCase(request.getAnalysisType())) {
            // Aggregated analytics summary
            context = jobService.buildAnalyticsSummary();
        } else if (Boolean.TRUE.equals(request.getIncludeFullDescription())) {
            // Full descriptions (more tokens, more cost, deeper analysis)
            context = jobService.buildDetailedContextForBedrock(jobs);
        } else {
            // Concise summaries (fewer tokens, lower cost)
            context = jobService.buildContextForBedrock(jobs);
        }

        // =============================================
        // STEP 3: Build the combined prompt
        // =============================================
        // System prompt: tells the AI WHO it is and HOW to respond
        String systemPrompt = """
                You are an expert Job Market Analyst and Career Advisor AI.
                You have access to a database of job listings from LinkedIn and Naukri platforms.
                
                Your responsibilities:
                1. Analyze job data accurately based on the provided database context
                2. Provide actionable career advice and recommendations
                3. Identify trends, patterns, and opportunities in the job market
                4. Compare roles, companies, and locations when asked
                5. Highlight which jobs have less competition (fewer applicants)
                6. Suggest which jobs match specific skills or experience levels
                
                Always base your analysis on the ACTUAL data provided.
                If the data is insufficient, say so clearly.
                Format your response with clear headers and bullet points.
                """;

        // Combine context + question into a single user prompt
        String combinedPrompt = String.format(
                "%s\n\n--- USER QUESTION ---\n%s",
                context, request.getQuestion()
        );

        // =============================================
        // STEP 4: Call AWS Bedrock via BedrockService
        // =============================================
        PromptRequest bedrockRequest = PromptRequest.builder()
                .modelId(request.getModelId())  // null → uses default model
                .prompt(combinedPrompt)
                .systemPrompt(systemPrompt)
                .maxTokens(4096)
                .temperature(0.5)  // Lower temperature for analytical accuracy
                .topP(0.9)
                .build();

        BedrockResponse response = bedrockService.chat(bedrockRequest);

        log.info("✅ Job analysis complete | Jobs analyzed: {} | Tokens: in={}, out={}",
                jobs.size(), response.getInputTokens(), response.getOutputTokens());

        return ResponseEntity.ok(response);
    }

    /**
     * ============================================================
     * GET /api/v1/jobs — List All Jobs
     * ============================================================
     *
     * Returns all jobs from the database as JSON.
     *
     * CURL: curl http://localhost:8080/api/v1/jobs
     */
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    /**
     * ============================================================
     * GET /api/v1/jobs/{id} — Get Job by ID
     * ============================================================
     *
     * CURL: curl http://localhost:8080/api/v1/jobs/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Optional<Job> job = jobService.getJobById(id);
        return job.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ============================================================
     * GET /api/v1/jobs/search?keyword=Java — Search Jobs
     * ============================================================
     *
     * CURL: curl "http://localhost:8080/api/v1/jobs/search?keyword=Java"
     */
    @GetMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam String keyword) {
        List<Job> jobs = jobService.searchJobs(keyword);
        return ResponseEntity.ok(jobs);
    }

    /**
     * ============================================================
     * GET /api/v1/jobs/stats — Job Statistics
     * ============================================================
     *
     * Returns aggregated statistics about the jobs in the database.
     *
     * CURL: curl http://localhost:8080/api/v1/jobs/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getJobStats() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalJobs", jobService.getJobCount());
        stats.put("unappliedJobs", jobService.getUnappliedJobs().size());
        stats.put("analyticsSummary", jobService.buildAnalyticsSummary());
        return ResponseEntity.ok(stats);
    }

    // =============================================
    // PRIVATE HELPER METHODS
    // =============================================

    /**
     * Fetches jobs from the database based on the request filters.
     * Applies filters in priority order: keyword > company > location > platform > all
     */
    private List<Job> fetchFilteredJobs(JobAnalysisRequest request) {
        if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
            return jobService.searchJobs(request.getKeyword());
        }
        if (request.getCompany() != null && !request.getCompany().isBlank()) {
            return jobService.getJobsByCompany(request.getCompany());
        }
        if (request.getLocation() != null && !request.getLocation().isBlank()) {
            return jobService.getJobsByLocation(request.getLocation());
        }
        if (request.getPlatform() != null && !request.getPlatform().isBlank()) {
            return jobService.getJobsByPlatform(request.getPlatform());
        }
        // No filters → fetch all jobs
        return jobService.getAllJobs();
    }
}
