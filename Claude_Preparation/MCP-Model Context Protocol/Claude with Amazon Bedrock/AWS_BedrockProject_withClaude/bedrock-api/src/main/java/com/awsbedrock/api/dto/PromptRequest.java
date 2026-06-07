package com.awsbedrock.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ============================================================
 * PromptRequest — Inbound Request DTO
 * ============================================================
 *
 * DTO (Data Transfer Object) that captures the user's request
 * to send a prompt to an AWS Bedrock model.
 *
 * WHAT IS A DTO?
 *   A DTO is a simple Java object used to transfer data between
 *   the client (Postman, frontend) and your API. It:
 *   - Defines what fields the client must/can send
 *   - Validates input using Jakarta Bean Validation annotations
 *   - Decouples the API contract from internal domain objects
 *
 * VALIDATION ANNOTATIONS (from Jakarta Bean Validation):
 *   @NotBlank → Field must not be null, empty, or whitespace-only
 *   @Size     → String length must be within min/max bounds
 *   @Min/@Max → Numeric value must be within bounds
 *
 * LOMBOK ANNOTATIONS:
 *   @Data           → Generates getters, setters, toString, equals, hashCode
 *   @Builder        → Enables PromptRequest.builder().prompt("...").build()
 *   @NoArgsConstructor → Generates no-arg constructor (needed by Jackson)
 *   @AllArgsConstructor → Generates constructor with all fields
 *
 * EXAMPLE JSON REQUEST BODY:
 * {
 *   "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
 *   "prompt": "Explain microservices architecture in simple terms",
 *   "systemPrompt": "You are a helpful software architect",
 *   "maxTokens": 1024,
 *   "temperature": 0.7,
 *   "topP": 0.9
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromptRequest {

    /**
     * The Bedrock model ID to use for this request.
     * If null/empty, the service will use the default model from application.yml.
     *
     * Example values:
     *   "anthropic.claude-3-5-sonnet-20241022-v2:0"
     *   "amazon.titan-text-express-v1"
     *   "meta.llama3-1-70b-instruct-v1:0"
     */
    private String modelId;

    /**
     * The user's prompt/question to send to the model.
     * This is the main input — REQUIRED.
     *
     * @NotBlank ensures the client cannot send null, "", or "   "
     * @Size limits prompt length to prevent abuse and control costs
     */
    @NotBlank(message = "Prompt is required and cannot be blank")
    @Size(min = 1, max = 100000, message = "Prompt must be between 1 and 100,000 characters")
    private String prompt;

    /**
     * Optional system prompt that sets the model's behavior/persona.
     *
     * System prompts are instructions that the model follows throughout
     * the conversation. They're different from the user prompt:
     *   - System: "You are a Java expert. Always provide code examples."
     *   - User: "How do I read a file in Java?"
     *
     * The model will respond as a Java expert with code examples.
     */
    @Size(max = 10000, message = "System prompt must not exceed 10,000 characters")
    private String systemPrompt;

    /**
     * Maximum number of tokens (words/subwords) the model can generate.
     *
     * WHAT ARE TOKENS?
     *   - 1 token ≈ 0.75 English words (or ~4 characters)
     *   - "Hello, World!" = 4 tokens
     *   - A 500-word essay ≈ 670 tokens
     *
     * More tokens = longer response = higher cost
     * Default: 4096 tokens (~3000 words)
     */
    @Min(value = 1, message = "maxTokens must be at least 1")
    @Max(value = 8192, message = "maxTokens must not exceed 8192")
    @Builder.Default
    private Integer maxTokens = 4096;

    /**
     * Temperature controls the randomness/creativity of the response.
     *
     * LOW temperature (0.0 - 0.3):  → Deterministic, factual, consistent
     * MID temperature (0.4 - 0.7):  → Balanced creativity and accuracy
     * HIGH temperature (0.8 - 1.0): → Creative, varied, sometimes unpredictable
     *
     * Use cases:
     *   0.0 → Code generation, factual Q&A
     *   0.5 → General chat, summaries
     *   0.9 → Creative writing, brainstorming
     */
    @Min(value = 0, message = "Temperature must be between 0.0 and 1.0")
    @Max(value = 1, message = "Temperature must be between 0.0 and 1.0")
    @Builder.Default
    private Double temperature = 0.7;

    /**
     * Top-P (nucleus sampling) — alternative to temperature for controlling randomness.
     *
     * The model considers only tokens whose cumulative probability
     * reaches topP. For example, topP=0.9 means the model picks from
     * the top 90% most likely tokens.
     *
     * topP=1.0 → Consider all tokens (most diverse)
     * topP=0.1 → Consider only the top 10% most likely tokens (most focused)
     *
     * TIP: Usually adjust EITHER temperature OR topP, not both.
     */
    @Min(value = 0, message = "topP must be between 0.0 and 1.0")
    @Max(value = 1, message = "topP must be between 0.0 and 1.0")
    @Builder.Default
    private Double topP = 0.9;
}
