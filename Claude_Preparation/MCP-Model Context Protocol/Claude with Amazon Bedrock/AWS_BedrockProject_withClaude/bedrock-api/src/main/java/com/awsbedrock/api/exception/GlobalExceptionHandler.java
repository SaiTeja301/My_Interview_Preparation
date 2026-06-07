package com.awsbedrock.api.exception;

import com.awsbedrock.api.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.bedrockruntime.model.AccessDeniedException;
import software.amazon.awssdk.services.bedrockruntime.model.ModelTimeoutException;
import software.amazon.awssdk.services.bedrockruntime.model.ThrottlingException;
import software.amazon.awssdk.services.bedrockruntime.model.ValidationException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * ============================================================
 * GlobalExceptionHandler — Centralised Exception Handling
 * ============================================================
 *
 * @RestControllerAdvice is a powerful Spring annotation that
 * intercepts exceptions thrown by ANY controller in the application
 * and converts them into structured HTTP responses.
 *
 * WITHOUT this class:
 *   - Spring returns its default error format (ugly, inconsistent)
 *   - Stack traces might leak to the client (security risk!)
 *   - Each controller would need its own try/catch blocks
 *
 * WITH this class:
 *   - ALL errors follow the same ErrorResponse format
 *   - Proper HTTP status codes for each error type
 *   - Sensitive information is never exposed to clients
 *   - Errors are logged server-side for debugging
 *
 * HOW IT WORKS:
 *   1. A controller throws an exception
 *   2. Spring intercepts it BEFORE sending a response
 *   3. Spring finds the matching @ExceptionHandler method here
 *   4. That method creates an ErrorResponse and returns it
 *
 * @Slf4j (Lombok) → auto-generates: private static final Logger log = ...
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors from @Valid on request bodies.
     *
     * When: Client sends invalid JSON (e.g., blank prompt, maxTokens > 8192)
     * Returns: HTTP 400 Bad Request with field-level error details
     *
     * EXAMPLE:
     *   Request: { "prompt": "", "maxTokens": 99999 }
     *   Response: {
     *     "status": 400,
     *     "error": "Bad Request",
     *     "message": "prompt: Prompt is required and cannot be blank; maxTokens: must not exceed 8192"
     *   }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // Collect all field validation errors into a single message
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        log.warn("Validation failed for {}: {}", request.getRequestURI(), errors);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(errors)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles custom Bedrock API exceptions.
     *
     * When: Our service code detects an error (model not found, parse failure)
     * Returns: HTTP 502 Bad Gateway (upstream service error)
     */
    @ExceptionHandler(BedrockApiException.class)
    public ResponseEntity<ErrorResponse> handleBedrockApiException(
            BedrockApiException ex,
            HttpServletRequest request) {

        log.error("Bedrock API error: {}", ex.getMessage(), ex);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_GATEWAY.value())
                .error("Bedrock API Error")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    /**
     * Handles rate limit exceeded errors.
     *
     * When: Client exceeds the configured requests-per-minute limit
     * Returns: HTTP 429 Too Many Requests
     */
    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleRateLimitExceeded(
            RateLimitExceededException ex,
            HttpServletRequest request) {

        log.warn("Rate limit exceeded for {}", request.getRemoteAddr());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .error("Too Many Requests")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    /**
     * Handles AWS Bedrock Access Denied errors.
     *
     * When: IAM permissions are insufficient for the requested model
     * Returns: HTTP 403 Forbidden
     *
     * COMMON CAUSES:
     *   - Model not enabled in Bedrock Console
     *   - IAM policy missing bedrock:InvokeModel permission
     *   - IAM policy restricting to specific model ARNs
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex,
            HttpServletRequest request) {

        log.error("AWS Bedrock access denied: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("Access Denied")
                .message("Access denied to the requested Bedrock model. "
                        + "Ensure the model is enabled and IAM permissions are correct.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Handles AWS Bedrock throttling (too many requests to AWS).
     *
     * When: Your application exceeds Bedrock's per-account rate limits
     * Returns: HTTP 429 Too Many Requests
     */
    @ExceptionHandler(ThrottlingException.class)
    public ResponseEntity<ErrorResponse> handleThrottling(
            ThrottlingException ex,
            HttpServletRequest request) {

        log.warn("AWS Bedrock throttled: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .error("Bedrock Rate Limited")
                .message("AWS Bedrock is throttling requests. Please retry after a short delay.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }

    /**
     * Handles AWS Bedrock model timeout errors.
     *
     * When: The model takes too long to respond (>60s typically)
     * Returns: HTTP 504 Gateway Timeout
     */
    @ExceptionHandler(ModelTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleModelTimeout(
            ModelTimeoutException ex,
            HttpServletRequest request) {

        log.error("Bedrock model timeout: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.GATEWAY_TIMEOUT.value())
                .error("Model Timeout")
                .message("The Bedrock model timed out. Try reducing maxTokens or using a faster model.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(response);
    }

    /**
     * Handles AWS Bedrock validation errors.
     *
     * When: Invalid parameters sent to the Bedrock API
     * Returns: HTTP 400 Bad Request
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleBedrockValidation(
            ValidationException ex,
            HttpServletRequest request) {

        log.warn("Bedrock validation error: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bedrock Validation Error")
                .message("Invalid parameters for Bedrock model: " + ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Catch-all handler for any unexpected exceptions.
     *
     * SECURITY: Never expose internal error details to clients.
     * Log the full stack trace server-side, but return a generic message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        // Log full stack trace for debugging
        log.error("Unexpected error on {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
