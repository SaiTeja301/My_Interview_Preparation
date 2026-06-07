package com.awsbedrock.api.exception;

/**
 * ============================================================
 * BedrockApiException — Custom Business Exception
 * ============================================================
 *
 * Thrown when something goes wrong during Bedrock API communication.
 *
 * WHY CUSTOM EXCEPTIONS?
 *   Instead of catching generic RuntimeException everywhere,
 *   custom exceptions let us:
 *   1. Add context (which model failed, what operation)
 *   2. Map to specific HTTP status codes in GlobalExceptionHandler
 *   3. Provide meaningful error messages to the client
 *
 * EXAMPLE USAGE:
 *   throw new BedrockApiException("Model not available: " + modelId);
 *   throw new BedrockApiException("Failed to parse response", cause);
 */
public class BedrockApiException extends RuntimeException {

    /**
     * Creates exception with a message.
     * @param message Human-readable error description
     */
    public BedrockApiException(String message) {
        super(message);
    }

    /**
     * Creates exception with a message and root cause.
     * The root cause is preserved for debugging (visible in stack trace).
     *
     * @param message Human-readable error description
     * @param cause   The original exception that triggered this error
     */
    public BedrockApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
