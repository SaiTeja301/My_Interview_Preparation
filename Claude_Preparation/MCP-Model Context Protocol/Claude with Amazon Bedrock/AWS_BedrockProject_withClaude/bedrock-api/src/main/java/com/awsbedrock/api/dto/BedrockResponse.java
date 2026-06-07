package com.awsbedrock.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ============================================================
 * BedrockResponse — Outbound Response DTO
 * ============================================================
 *
 * This DTO wraps the Bedrock model's response and sends it back
 * to the client in a structured, consistent format.
 *
 * @JsonInclude(NON_NULL) → Omits null fields from the JSON output.
 * This keeps responses clean. For example, if 'stopReason' is null,
 * it won't appear in the JSON at all.
 *
 * EXAMPLE JSON RESPONSE:
 * {
 *   "response": "Microservices is an architectural style...",
 *   "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
 *   "inputTokens": 25,
 *   "outputTokens": 342,
 *   "totalTokens": 367,
 *   "latencyMs": 2845,
 *   "stopReason": "end_turn"
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BedrockResponse {

    /**
     * The model's generated text response.
     * This is the main output the user cares about.
     */
    private String response;

    /**
     * The model ID that was used to generate this response.
     * Useful when the client sends a request without specifying a model
     * and the default model is used.
     */
    private String modelId;

    /**
     * Number of tokens in the INPUT (user's prompt + system prompt).
     * You are CHARGED for input tokens.
     *
     * Pricing example (Claude 3.5 Sonnet):
     *   Input: $3.00 per 1 million tokens
     *   25 input tokens ≈ $0.000075
     */
    private Integer inputTokens;

    /**
     * Number of tokens in the OUTPUT (model's response).
     * Output tokens are typically MORE EXPENSIVE than input tokens.
     *
     * Pricing example (Claude 3.5 Sonnet):
     *   Output: $15.00 per 1 million tokens
     *   342 output tokens ≈ $0.00513
     */
    private Integer outputTokens;

    /**
     * Total tokens = inputTokens + outputTokens.
     * Use this to track your overall token consumption.
     */
    private Integer totalTokens;

    /**
     * Time taken for the API call in milliseconds.
     * Useful for monitoring performance and SLA compliance.
     *
     * Typical latencies:
     *   Claude Haiku:  500-2000ms
     *   Claude Sonnet: 1000-5000ms
     *   Claude Opus:   3000-15000ms
     */
    private Long latencyMs;

    /**
     * Why the model stopped generating text.
     *
     * Possible values:
     *   "end_turn"    → Model naturally finished its response
     *   "max_tokens"  → Hit the maxTokens limit (response may be truncated!)
     *   "stop_sequence" → Model encountered a stop sequence
     *   "content_filtered" → Response was filtered by content moderation
     *
     * If stopReason is "max_tokens", the response is INCOMPLETE.
     * Consider increasing maxTokens and retrying.
     */
    private String stopReason;
}
