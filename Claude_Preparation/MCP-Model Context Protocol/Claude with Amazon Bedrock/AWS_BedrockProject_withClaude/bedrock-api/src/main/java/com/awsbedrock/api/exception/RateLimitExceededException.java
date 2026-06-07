package com.awsbedrock.api.exception;

/**
 * ============================================================
 * RateLimitExceededException — HTTP 429 Exception
 * ============================================================
 *
 * Thrown when a client exceeds the configured rate limit.
 * The GlobalExceptionHandler maps this to HTTP 429 Too Many Requests.
 *
 * EXAMPLE:
 *   If rate limit is 10 requests/minute and client sends request #11:
 *   → throw new RateLimitExceededException("Rate limit exceeded. Try again in 6 seconds.")
 *   → Client receives HTTP 429 with error message
 */
public class RateLimitExceededException extends RuntimeException {

    public RateLimitExceededException(String message) {
        super(message);
    }
}
