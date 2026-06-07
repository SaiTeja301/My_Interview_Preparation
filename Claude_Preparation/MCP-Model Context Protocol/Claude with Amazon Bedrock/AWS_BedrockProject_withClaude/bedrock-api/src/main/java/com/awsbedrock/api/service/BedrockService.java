package com.awsbedrock.api.service;

import com.awsbedrock.api.dto.BedrockResponse;
import com.awsbedrock.api.dto.PromptRequest;

/**
 * ============================================================
 * BedrockService — Service Interface
 * ============================================================
 *
 * WHY AN INTERFACE?
 *   In clean architecture, the controller depends on an INTERFACE,
 *   not a concrete class. This gives us:
 *
 *   1. LOOSE COUPLING → Controller doesn't know about AWS SDK details
 *   2. TESTABILITY    → We can create a mock implementation for unit tests
 *   3. FLEXIBILITY    → We can swap implementations (e.g., switch from
 *                        Converse API to InvokeModel) without changing the controller
 *   4. SOLID PRINCIPLES → This follows the Dependency Inversion Principle
 *
 * USAGE IN CONTROLLER:
 *   @Autowired
 *   private BedrockService bedrockService;  // Spring injects BedrockServiceImpl
 *
 *   public ResponseEntity<BedrockResponse> chat(@RequestBody PromptRequest request) {
 *       return ResponseEntity.ok(bedrockService.chat(request));
 *   }
 */
public interface BedrockService {

    /**
     * Sends a prompt to a Bedrock model and returns the response.
     *
     * @param request The prompt request containing model ID, prompt text,
     *                system prompt, and inference parameters
     * @return BedrockResponse containing the model's response, token usage,
     *         latency, and stop reason
     * @throws com.awsbedrock.api.exception.BedrockApiException if the API call fails
     */
    BedrockResponse chat(PromptRequest request);
}
