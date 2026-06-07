package com.awsbedrock.api.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * ============================================================
 * RateLimitConfig — API Rate Limiting Configuration
 * ============================================================
 *
 * Rate limiting protects your API (and your AWS bill!) from:
 *   - Abuse and denial-of-service attacks
 *   - Runaway scripts hammering your endpoint
 *   - Exceeding AWS Bedrock's own rate limits
 *
 * HOW IT WORKS (Token Bucket Algorithm):
 *   1. A "bucket" starts with N tokens (e.g., 10)
 *   2. Each API request consumes 1 token
 *   3. Tokens refill at a fixed rate (e.g., 10 per minute)
 *   4. If the bucket is empty → HTTP 429 Too Many Requests
 *
 * EXAMPLE with 10 tokens/minute:
 *   - User sends 10 requests quickly → all succeed, bucket is now empty
 *   - User sends request #11 → gets 429 error
 *   - After 6 seconds, 1 token refills → next request succeeds
 *
 * NOTE: This is a simple in-memory rate limiter (per JVM instance).
 * For production with multiple instances, use Redis-backed Bucket4j
 * or AWS API Gateway rate limiting.
 */
@Configuration
public class RateLimitConfig {

    /**
     * Number of requests allowed per minute.
     * Configurable via application.yml: rate-limit.requests-per-minute
     * Default: 10 requests per minute
     */
    @Value("${rate-limit.requests-per-minute:10}")
    private int requestsPerMinute;

    /**
     * Creates a Bucket bean for rate limiting.
     *
     * Bandwidth.classic() creates a token bucket with:
     *   - capacity: maximum burst size (requestsPerMinute)
     *   - refill: how fast tokens are replenished
     *
     * Refill.greedy() → tokens are added continuously
     * Refill.intervally() → all tokens added at once at interval end
     *
     * We use greedy for smoother traffic distribution.
     */
    @Bean
    public Bucket rateLimitBucket() {
        // Define the rate limit bandwidth
        Bandwidth limit = Bandwidth.classic(
                requestsPerMinute,                              // Bucket capacity
                Refill.greedy(requestsPerMinute, Duration.ofMinutes(1))  // Refill rate
        );

        // Build and return the bucket
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
