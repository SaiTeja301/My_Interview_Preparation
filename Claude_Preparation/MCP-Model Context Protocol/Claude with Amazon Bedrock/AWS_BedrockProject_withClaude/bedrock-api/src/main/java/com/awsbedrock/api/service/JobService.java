package com.awsbedrock.api.service;

import com.awsbedrock.api.entity.Job;
import com.awsbedrock.api.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ============================================================
 * JobService — Business Logic for Job Data
 * ============================================================
 *
 * This service handles:
 *   1. CRUD operations on the jobs table
 *   2. Converting job data into context strings for Bedrock
 *   3. Building AI prompts that include job data as context
 *
 * MCP (Model Context Protocol) CONCEPT:
 * ══════════════════════════════════════
 * MCP is a protocol that standardises how AI models access external
 * data sources (databases, APIs, file systems). In this application,
 * we implement the MCP concept by:
 *
 *   1. CONTEXT EXTRACTION → Fetch relevant job data from PostgreSQL
 *   2. CONTEXT FORMATTING → Convert raw DB rows into structured text
 *   3. CONTEXT INJECTION  → Prepend context to the user's prompt
 *   4. AI ANALYSIS        → Bedrock analyzes the data + prompt together
 *
 * This is similar to how MCP servers work in Claude Desktop:
 *   MCP Server ←→ Data Source (DB, API, Files)
 *   MCP Server → Provides context to the AI model
 *   AI Model   → Uses context to answer questions about the data
 *
 * FLOW:
 *   User asks: "Which Java jobs have fewer applicants?"
 *   ↓
 *   JobService.buildContextForBedrock()
 *     → Fetches jobs from DB
 *     → Converts to text context
 *     → Returns: "Here are the jobs from the database:\n Job #1: ..."
 *   ↓
 *   BedrockService.chat(context + user prompt)
 *     → Sends combined context + question to Claude
 *     → Returns AI analysis
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    // =============================================
    // CRUD OPERATIONS
    // =============================================

    /**
     * Get all jobs from the database.
     * SQL: SELECT * FROM linkedin_naukr_jobs.jobs
     */
    public List<Job> getAllJobs() {
        log.info("📊 Fetching all jobs from database");
        List<Job> jobs = jobRepository.findAll();
        log.info("📊 Found {} jobs", jobs.size());
        return jobs;
    }

    /**
     * Get a single job by ID.
     * SQL: SELECT * FROM linkedin_naukr_jobs.jobs WHERE id = ?
     */
    public Optional<Job> getJobById(Long id) {
        log.info("📊 Fetching job with ID: {}", id);
        return jobRepository.findById(id);
    }

    /**
     * Search jobs by keyword in title or description.
     * Uses custom JPQL query for case-insensitive search.
     */
    public List<Job> searchJobs(String keyword) {
        log.info("🔍 Searching jobs with keyword: '{}'", keyword);
        List<Job> results = jobRepository.searchByKeyword(keyword);
        log.info("🔍 Found {} jobs matching '{}'", results.size(), keyword);
        return results;
    }

    /**
     * Get jobs by company name.
     */
    public List<Job> getJobsByCompany(String company) {
        log.info("🏢 Fetching jobs for company: '{}'", company);
        return jobRepository.findByCompanyContainingIgnoreCase(company);
    }

    /**
     * Get jobs by location.
     */
    public List<Job> getJobsByLocation(String location) {
        log.info("📍 Fetching jobs for location: '{}'", location);
        return jobRepository.findByLocationContainingIgnoreCase(location);
    }

    /**
     * Get jobs by platform (Naukri, LinkedIn).
     */
    public List<Job> getJobsByPlatform(String platform) {
        log.info("🌐 Fetching jobs from platform: '{}'", platform);
        return jobRepository.findByPlatform(platform);
    }

    /**
     * Get unapplied jobs (applied = 0).
     */
    public List<Job> getUnappliedJobs() {
        log.info("📋 Fetching unapplied jobs");
        return jobRepository.findByApplied(0);
    }

    /**
     * Get total job count.
     */
    public long getJobCount() {
        return jobRepository.count();
    }

    // =============================================
    // MCP-STYLE CONTEXT BUILDING FOR BEDROCK
    // =============================================

    /**
     * ============================================================
     * buildContextForBedrock() — MCP Context Provider
     * ============================================================
     *
     * This is the KEY METHOD that implements MCP-style context injection.
     * It converts database records into a structured text context
     * that the Bedrock model can understand and analyze.
     *
     * HOW IT WORKS:
     *   1. Fetches all jobs from PostgreSQL
     *   2. Converts each Job entity into a one-line summary
     *   3. Combines them into a single context string
     *   4. This context is prepended to the user's prompt
     *
     * EXAMPLE OUTPUT:
     *   "DATABASE CONTEXT — Jobs from linkedin_naukr_jobs.jobs table (4 records):
     *    Job #1: Scala Developer at TCS | Location: Bengaluru | ...
     *    Job #2: Java Developer Apache Camel at Sciens | Location: Chennai | ...
     *    Job #3: Java Full Stack Developer at Quadrant | Location: Hyderabad | ...
     *    Job #4: React JS Developer at TCS | Location: Hyderabad | ..."
     *
     * TOKEN OPTIMIZATION:
     *   - We use concise summaries instead of full descriptions
     *   - Full descriptions could be 10,000+ characters each
     *   - Summaries are ~200 chars each → saves 95%+ tokens (and cost!)
     *
     * @param jobs List of jobs to include as context
     * @return Formatted context string for Bedrock
     */
    public String buildContextForBedrock(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return "DATABASE CONTEXT: No jobs found in the database.";
        }

        StringBuilder context = new StringBuilder();
        context.append(String.format(
                "DATABASE CONTEXT — Jobs from linkedin_naukr_jobs.jobs table (%d records):\n\n",
                jobs.size()
        ));

        for (Job job : jobs) {
            context.append(job.toBedrockContext()).append("\n");
        }

        log.debug("📝 Built Bedrock context with {} jobs, {} chars",
                jobs.size(), context.length());

        return context.toString();
    }

    /**
     * Build context with FULL job descriptions (for deep analysis).
     * WARNING: This uses significantly more tokens and costs more!
     * Use only when detailed analysis of job descriptions is needed.
     *
     * @param jobs List of jobs to include
     * @return Detailed context string including full descriptions
     */
    public String buildDetailedContextForBedrock(List<Job> jobs) {
        if (jobs == null || jobs.isEmpty()) {
            return "DATABASE CONTEXT: No jobs found in the database.";
        }

        StringBuilder context = new StringBuilder();
        context.append(String.format(
                "DETAILED DATABASE CONTEXT — Jobs table (%d records):\n\n",
                jobs.size()
        ));

        for (Job job : jobs) {
            context.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            context.append(String.format("JOB #%d\n", job.getId()));
            context.append(String.format("Title: %s\n", job.getTitle()));
            context.append(String.format("Company: %s\n", job.getCompany()));
            context.append(String.format("Location: %s\n", job.getLocation()));
            context.append(String.format("Posted: %s\n", job.getJobPosted()));
            context.append(String.format("Applicants: %s\n", job.getJobApplyedCountStatus()));
            context.append(String.format("Platform: %s\n", job.getPlatform()));
            context.append(String.format("Applied: %s\n", job.getApplied() == 1 ? "Yes" : "No"));
            context.append(String.format("URL: %s\n", job.getJobUrl()));
            context.append(String.format("Description:\n%s\n\n",
                    job.getDescription() != null ? job.getDescription() : "N/A"));
        }

        log.info("📝 Built DETAILED Bedrock context with {} jobs, {} chars",
                jobs.size(), context.length());

        return context.toString();
    }

    /**
     * Build analytics summary context for high-level questions.
     * Uses aggregated data instead of individual records.
     */
    public String buildAnalyticsSummary() {
        long totalJobs = jobRepository.count();
        List<Object[]> byCompany = jobRepository.countJobsByCompany();
        List<Object[]> byLocation = jobRepository.countJobsByLocation();
        List<Job> unapplied = jobRepository.findByApplied(0);

        StringBuilder summary = new StringBuilder();
        summary.append("DATABASE ANALYTICS SUMMARY:\n\n");
        summary.append(String.format("Total Jobs: %d\n", totalJobs));
        summary.append(String.format("Unapplied Jobs: %d\n\n", unapplied.size()));

        summary.append("Jobs by Company:\n");
        for (Object[] row : byCompany) {
            summary.append(String.format("  %s: %s jobs\n", row[0], row[1]));
        }

        summary.append("\nJobs by Location:\n");
        for (Object[] row : byLocation) {
            summary.append(String.format("  %s: %s jobs\n", row[0], row[1]));
        }

        return summary.toString();
    }
}
