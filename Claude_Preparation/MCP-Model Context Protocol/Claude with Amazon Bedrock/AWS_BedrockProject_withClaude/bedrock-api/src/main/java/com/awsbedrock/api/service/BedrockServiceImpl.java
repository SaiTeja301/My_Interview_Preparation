package com.awsbedrock.api.service;

import com.awsbedrock.api.dto.BedrockResponse;
import com.awsbedrock.api.dto.PromptRequest;
import com.awsbedrock.api.exception.BedrockApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * BedrockServiceImpl — Core Business Logic
 * ============================================================
 *
 * This class contains ALL the logic for communicating with
 * AWS Bedrock using the CONVERSE API.
 *
 * ============================================================
 * WHAT IS THE CONVERSE API?
 * ============================================================
 *
 * The Converse API is AWS Bedrock's UNIFIED interface for chatting
 * with ANY supported model. Before Converse, each model required
 * a different JSON payload format (InvokeModel API). With Converse:
 *
 *   ┌──────────────────────────────────────────────────────┐
 *   │  YOUR CODE                                           │
 *   │  bedrockClient.converse(request)                     │
 *   └──────────────┬───────────────────────────────────────┘
 *                  │  Same API call for ALL models
 *                  ▼
 *   ┌──────────────────────────────────────────────────────┐
 *   │  AWS BEDROCK (Converse API)                          │
 *   │  Automatically translates to each model's format     │
 *   ├──────────────────────────────────────────────────────┤
 *   │  → Claude:  Anthropic Messages format                │
 *   │  → Titan:   Amazon Titan format                      │
 *   │  → Llama:   Meta Llama format                        │
 *   │  → Mistral: Mistral format                           │
 *   └──────────────────────────────────────────────────────┘
 *
 * KEY CONVERSE API CONCEPTS:
 *
 * 1. Message — A single turn in the conversation
 *    - Has a ROLE: "user" or "assistant"
 *    - Has CONTENT: one or more ContentBlocks (text, image, etc.)
 *
 * 2. ContentBlock — A piece of content within a message
 *    - ContentBlock.fromText("Hello") → text content
 *    - ContentBlock.fromImage(...)    → image content (multimodal)
 *
 * 3. SystemContentBlock — System-level instructions
 *    - Sets the model's behavior/persona
 *    - Applied BEFORE the conversation messages
 *
 * 4. InferenceConfiguration — Model parameters
 *    - maxTokens:   Maximum response length
 *    - temperature: Creativity level (0.0 to 1.0)
 *    - topP:        Nucleus sampling threshold
 *
 * @Service → Marks this as a Spring service bean (business logic layer)
 * @Slf4j → Auto-generates SLF4J logger: log.info(), log.error(), etc.
 * @RequiredArgsConstructor → Generates constructor for final fields (DI)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BedrockServiceImpl implements BedrockService {

    /**
     * Injected by Spring from AwsBedrockConfig.
     * This is the AWS SDK client we use to call Bedrock APIs.
     */
    private final BedrockRuntimeClient bedrockRuntimeClient;

    /**
     * Default model ID loaded from application.yml.
     * Used when the client doesn't specify a model in their request.
     */
    @Value("${aws.bedrock.default-model:apac.anthropic.claude-3-sonnet-20240229-v1:0}")
    private String defaultModelId;

    /**
     * Default max tokens loaded from application.yml.
     * Used when the client doesn't specify maxTokens.
     */
    @Value("${aws.bedrock.max-tokens:4096}")
    private int defaultMaxTokens;

    /**
     * Default temperature loaded from application.yml.
     */
    @Value("${aws.bedrock.temperature:0.7}")
    private double defaultTemperature;

    /**
     * ============================================================
     * chat() — Main method that calls AWS Bedrock Converse API
     * ============================================================
     *
     * FLOW:
     * 1. Determine which model to use (from request or default)
     * 2. Build the Message with user's prompt as a ContentBlock
     * 3. Optionally add a system prompt
     * 4. Configure inference parameters (maxTokens, temperature, topP)
     * 5. Call bedrockRuntimeClient.converse()
     * 6. Parse the response (extract text, token counts, stop reason)
     * 7. Return structured BedrockResponse DTO
     */
    @Override
    public BedrockResponse chat(PromptRequest request) {

        // Record start time for latency measurement
        long startTime = System.currentTimeMillis();

        // =============================================
        // STEP 1: Determine model ID
        // =============================================
        // Use the model from the request, or fall back to the default
        String modelId = (request.getModelId() != null && !request.getModelId().isBlank())
                ? request.getModelId()
                : defaultModelId;

        log.info("📤 Sending prompt to model: {} | Prompt length: {} chars",
                modelId, request.getPrompt().length());

        try {
            // =============================================
            // STEP 2: Build the user message
            // =============================================
            // Create a ContentBlock containing the user's text prompt
            ContentBlock userContent = ContentBlock.fromText(request.getPrompt());

            // Create a Message with role "user" and the content block
            // The Converse API expects messages in a conversation format:
            //   [{ role: "user", content: [...] }, { role: "assistant", content: [...] }, ...]
            Message userMessage = Message.builder()
                    .role(ConversationRole.USER)
                    .content(userContent)
                    .build();

            // =============================================
            // STEP 3: Build the Converse request
            // =============================================
            ConverseRequest.Builder converseRequestBuilder = ConverseRequest.builder()
                    .modelId(modelId)
                    .messages(userMessage);

            // =============================================
            // STEP 4: Add system prompt (if provided)
            // =============================================
            // System prompts set the model's persona/behavior.
            // They're processed BEFORE user messages.
            if (request.getSystemPrompt() != null && !request.getSystemPrompt().isBlank()) {
                SystemContentBlock systemBlock = SystemContentBlock.fromText(request.getSystemPrompt());
                converseRequestBuilder.system(systemBlock);
                log.debug("System prompt added: {} chars", request.getSystemPrompt().length());
            }

            // =============================================
            // STEP 5: Configure inference parameters
            // =============================================
            // These control HOW the model generates text
            int maxTokens = request.getMaxTokens() != null ? request.getMaxTokens() : defaultMaxTokens;
            double temperature = request.getTemperature() != null ? request.getTemperature() : defaultTemperature;
            double topP = request.getTopP() != null ? request.getTopP() : 0.9;

            InferenceConfiguration inferenceConfig = InferenceConfiguration.builder()
                    .maxTokens(maxTokens)
                    .temperature((float) temperature)
                    .topP((float) topP)
                    .build();

            converseRequestBuilder.inferenceConfig(inferenceConfig);

            // =============================================
            // STEP 6: Call AWS Bedrock Converse API
            // =============================================
            // This is THE API call that sends your prompt to the model
            // and waits for the complete response (synchronous).
            ConverseResponse converseResponse = bedrockRuntimeClient.converse(
                    converseRequestBuilder.build()
            );

            // =============================================
            // STEP 7: Parse the response
            // =============================================
            long latencyMs = System.currentTimeMillis() - startTime;

            // Extract the assistant's response text from the response
            // The response contains a Message with role "assistant"
            // and one or more ContentBlocks
            String responseText = extractResponseText(converseResponse);

            // Extract token usage information
            // This tells us how many tokens were consumed (for cost tracking)
            TokenUsage tokenUsage = converseResponse.usage();
            int inputTokens = tokenUsage != null ? tokenUsage.inputTokens() : 0;
            int outputTokens = tokenUsage != null ? tokenUsage.outputTokens() : 0;

            // Extract stop reason
            // "end_turn" = model finished naturally
            // "max_tokens" = hit the limit (response may be truncated)
            String stopReason = converseResponse.stopReasonAsString();

            log.info("✅ Response received from {} | Tokens: in={}, out={} | Latency: {}ms | Stop: {}",
                    modelId, inputTokens, outputTokens, latencyMs, stopReason);

            // =============================================
            // STEP 8: Build and return the response DTO
            // =============================================
            return BedrockResponse.builder()
                    .response(responseText)
                    .modelId(modelId)
                    .inputTokens(inputTokens)
                    .outputTokens(outputTokens)
                    .totalTokens(inputTokens + outputTokens)
                    .latencyMs(latencyMs)
                    .stopReason(stopReason)
                    .build();

        } catch (BedrockRuntimeException e) {
            // AWS SDK exceptions (throttling, access denied, model errors)
            // These are re-thrown and caught by GlobalExceptionHandler
            log.error("❌ Bedrock API call failed for model {}: {}", modelId, e.getMessage());
            throw e;

        } catch (Exception e) {
            // Unexpected errors (network issues, parsing failures)
            log.error("❌ Unexpected error calling model {}: {}", modelId, e.getMessage(), e);
            throw new BedrockApiException(
                    "Failed to get response from Bedrock model: " + modelId, e);
        }
    }

    /**
     * Extracts the text response from a ConverseResponse.
     *
     * The response structure is:
     *   ConverseResponse
     *     └── output (ConverseOutput)
     *           └── message (Message)
     *                 └── content (List<ContentBlock>)
     *                       └── [0].text() → "The actual response text"
     *
     * We iterate through all content blocks and concatenate text blocks.
     * (Most responses have a single text block, but some models may return multiple.)
     */
    private String extractResponseText(ConverseResponse response) {
        // Get the output message from the response
        if (response.output() == null || response.output().message() == null) {
            throw new BedrockApiException("Bedrock returned an empty response");
        }

        Message assistantMessage = response.output().message();
        List<ContentBlock> contentBlocks = assistantMessage.content();

        if (contentBlocks == null || contentBlocks.isEmpty()) {
            throw new BedrockApiException("Bedrock response contains no content blocks");
        }

        // Concatenate all text content blocks
        StringBuilder responseText = new StringBuilder();
        for (ContentBlock block : contentBlocks) {
            // Check if this content block is a text block
            if (block.text() != null) {
                responseText.append(block.text());
            }
        }

        String result = responseText.toString();
        if (result.isBlank()) {
            throw new BedrockApiException("Bedrock response contains no text content");
        }

        return result;
    }
}
