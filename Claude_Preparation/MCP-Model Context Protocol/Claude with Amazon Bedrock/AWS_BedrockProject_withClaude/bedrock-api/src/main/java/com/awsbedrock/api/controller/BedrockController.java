package com.awsbedrock.api.controller;

import com.awsbedrock.api.dto.BedrockResponse;
import com.awsbedrock.api.dto.PromptRequest;
import com.awsbedrock.api.exception.RateLimitExceededException;
import com.awsbedrock.api.service.BedrockService;
import com.awsbedrock.api.util.ModelRegistry;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ============================================================
 * BedrockController — REST API Endpoints
 * ============================================================
 *
 * This is the entry point for all HTTP requests to the Bedrock API.
 * It follows the REST architectural style:
 *
 *   POST /api/v1/chat     → Send a prompt, get a response
 *   GET  /api/v1/models   → List available Bedrock models
 *   GET  /api/v1/health   → Health check endpoint
 *
 * LAYER RESPONSIBILITIES (Clean Architecture):
 *   ┌─────────────┐
 *   │ Controller   │ ← Handles HTTP (request/response, validation, status codes)
 *   ├─────────────┤
 *   │ Service      │ ← Contains business logic (calls Bedrock, parses response)
 *   ├─────────────┤
 *   │ Config       │ ← Creates and configures AWS clients
 *   └─────────────┘
 *
 *   The controller NEVER contains business logic.
 *   It only: validates input → calls service → returns response.
 *
 * ANNOTATIONS EXPLAINED:
 *   @RestController = @Controller + @ResponseBody
 *     → Every method's return value is serialised to JSON (not a view name)
 *
 *   @RequestMapping("/api/v1")
 *     → Base path for all endpoints in this controller
 *     → Versioned API (v1) for backward compatibility
 *
 *   @RequiredArgsConstructor
 *     → Lombok generates constructor for final fields
 *     → Spring uses this constructor for dependency injection
 *
 *   @Slf4j
 *     → Lombok generates: private static final Logger log = ...
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BedrockController {

    // Injected by Spring (constructor injection via @RequiredArgsConstructor)
    private final BedrockService bedrockService;
    private final Bucket rateLimitBucket;

    /**
     * ============================================================
     * POST /api/v1/chat — Send a prompt to AWS Bedrock
     * ============================================================
     *
     * This is the MAIN endpoint. It accepts a prompt and returns
     * the model's response.
     *
     * REQUEST FLOW:
     *   1. Client sends POST with JSON body (PromptRequest)
     *   2. @Valid triggers Jakarta Bean Validation on the request
     *   3. Rate limit check (Bucket4j)
     *   4. BedrockService.chat() calls AWS Bedrock Converse API
     *   5. Response is wrapped in BedrockResponse DTO
     *   6. Returned as HTTP 200 with JSON body
     *
     * @param request The prompt request body (validated by @Valid)
     * @return BedrockResponse with model's response, token usage, latency
     *
     * CURL EXAMPLE:
     *   curl -X POST http://localhost:8080/api/v1/chat \
     *     -H "Content-Type: application/json" \
     *     -d '{
     *       "prompt": "Explain microservices in 3 sentences",
     *       "modelId": "anthropic.claude-3-5-sonnet-20241022-v2:0",
     *       "maxTokens": 512,
     *       "temperature": 0.7
     *     }'
     */
    @PostMapping("/chat")
    public ResponseEntity<BedrockResponse> chat(@Valid @RequestBody PromptRequest request) {

        // =============================================
        // RATE LIMITING CHECK
        // =============================================
        // tryConsume(1) attempts to consume 1 token from the bucket.
        // If the bucket is empty (rate limit exceeded), it returns false.
        if (!rateLimitBucket.tryConsume(1)) {
            log.warn("⚠️ Rate limit exceeded for chat request");
            throw new RateLimitExceededException(
                    "Rate limit exceeded. Please wait before making another request.");
        }

        // Log the incoming request (don't log the full prompt for privacy!)
        log.info("📨 Chat request received | Model: {} | Prompt: {} chars",
                request.getModelId() != null ? request.getModelId() : "default",
                request.getPrompt().length());

        // Delegate to service layer — all business logic is there
        BedrockResponse response = bedrockService.chat(request);

        // Return HTTP 200 OK with the response body
        return ResponseEntity.ok(response);
    }

    /**
     * ============================================================
     * GET /api/v1/models — List supported Bedrock models
     * ============================================================
     *
     * Returns a map of available model IDs with descriptions.
     * Useful for frontends to populate a model selection dropdown.
     *
     * CURL EXAMPLE:
     *   curl http://localhost:8080/api/v1/models
     */
    @GetMapping("/models")
    public ResponseEntity<Map<String, String>> listModels() {
        log.info("📋 Models list requested");

        // LinkedHashMap preserves insertion order
        Map<String, String> models = new LinkedHashMap<>();

        // Anthropic Claude models
        models.put(ModelRegistry.CLAUDE_3_5_SONNET_V2, "Claude 3.5 Sonnet v2 — Best balance of quality and cost");
        models.put(ModelRegistry.CLAUDE_3_5_HAIKU, "Claude 3.5 Haiku — Fast and cost-effective");
        models.put(ModelRegistry.CLAUDE_3_OPUS, "Claude 3 Opus — Most capable for complex tasks");
        models.put(ModelRegistry.CLAUDE_3_SONNET, "Claude 3 Sonnet — Previous gen, still capable");
        models.put(ModelRegistry.CLAUDE_3_HAIKU, "Claude 3 Haiku — Fastest and cheapest Claude");

        // Amazon Titan models
        models.put(ModelRegistry.TITAN_TEXT_EXPRESS, "Titan Text Express — AWS-native text generation");
        models.put(ModelRegistry.TITAN_TEXT_LITE, "Titan Text Lite — Lightweight text generation");
        models.put(ModelRegistry.TITAN_TEXT_PREMIER, "Titan Text Premier — Higher quality text");

        // Meta Llama models
        models.put(ModelRegistry.LLAMA_3_1_70B, "Llama 3.1 70B — High quality open-source");
        models.put(ModelRegistry.LLAMA_3_1_8B, "Llama 3.1 8B — Fast, lightweight");

        // Mistral models
        models.put(ModelRegistry.MISTRAL_LARGE, "Mistral Large — Strong reasoning");
        models.put(ModelRegistry.MIXTRAL_8X7B, "Mixtral 8x7B — Great cost/performance");

        // Cohere models
        models.put(ModelRegistry.COHERE_COMMAND_R_PLUS, "Cohere Command R+ — Optimized for RAG");

        return ResponseEntity.ok(models);
    }

    /**
     * ============================================================
     * GET /api/v1/health — Health Check Endpoint
     * ============================================================
     *
     * Used by load balancers, Kubernetes probes, and monitoring
     * systems to check if the service is alive and ready.
     *
     * CURL EXAMPLE:
     *   curl http://localhost:8080/api/v1/health
     *
     * RESPONSE:
     *   { "status": "UP", "service": "bedrock-api", "version": "1.0.0" }
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> health = new LinkedHashMap<>();
        health.put("status", "UP");
        health.put("service", "bedrock-api");
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }
}
