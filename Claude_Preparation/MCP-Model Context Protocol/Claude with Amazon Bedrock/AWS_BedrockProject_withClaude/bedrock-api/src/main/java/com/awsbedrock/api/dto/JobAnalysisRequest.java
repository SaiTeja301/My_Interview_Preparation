package com.awsbedrock.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ============================================================
 * JobAnalysisRequest — Request DTO for AI Job Analysis
 * ============================================================
 *
 * This DTO is used when the user wants Bedrock to analyze
 * job data from the database. It specifies:
 *   - What question to ask about the jobs
 *   - How to filter/scope the data
 *   - Whether to include full descriptions
 *
 * EXAMPLE REQUESTS:
 *
 * 1. Analyze all jobs:
 *   {
 *     "question": "Which jobs have the least competition?",
 *     "includeFullDescription": false
 *   }
 *
 * 2. Analyze jobs by keyword:
 *   {
 *     "question": "What skills are most in demand for Java roles?",
 *     "keyword": "Java",
 *     "includeFullDescription": true
 *   }
 *
 * 3. Analyze by location:
 *   {
 *     "question": "Compare job opportunities in Bengaluru vs Hyderabad",
 *     "location": "Bengaluru"
 *   }
 *
 * 4. Analytics summary:
 *   {
 *     "question": "Give me a hiring trends report",
 *     "analysisType": "ANALYTICS"
 *   }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobAnalysisRequest {

    /**
     * The question to ask Bedrock about the job data.
     * This is REQUIRED — it's what the AI will answer.
     */
    @NotBlank(message = "Question is required")
    @Size(max = 5000, message = "Question must not exceed 5,000 characters")
    private String question;

    /**
     * Optional keyword to filter jobs by title/description.
     * If set, only matching jobs are sent as context to Bedrock.
     *
     * Example: "Java" → only jobs with "Java" in title/description
     */
    private String keyword;

    /**
     * Optional company filter.
     * Example: "TCS" → only TCS jobs sent as context
     */
    private String company;

    /**
     * Optional location filter.
     * Example: "Bengaluru" → only Bengaluru jobs
     */
    private String location;

    /**
     * Optional platform filter.
     * Example: "Naukri" → only Naukri jobs
     */
    private String platform;

    /**
     * Whether to include full job descriptions in the context.
     * - false (default): Send concise summaries (saves tokens/cost)
     * - true: Send full descriptions (needed for skills analysis)
     */
    @Builder.Default
    private Boolean includeFullDescription = false;

    /**
     * Type of analysis to perform.
     * - "JOBS"      → Analyze individual job listings (default)
     * - "ANALYTICS" → Analyze aggregated statistics
     */
    @Builder.Default
    private String analysisType = "JOBS";

    /**
     * Which Bedrock model to use (optional, uses default if not set).
     */
    private String modelId;
}
