package com.awsbedrock.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ============================================================
 * WebConfig — CORS and Web MVC Configuration
 * ============================================================
 *
 * CORS (Cross-Origin Resource Sharing) controls which domains
 * can call your API from a web browser.
 *
 * Without CORS configuration:
 *   - A React app on http://localhost:3000 CANNOT call your
 *     Spring Boot API on http://localhost:8080
 *   - The browser blocks the request with a CORS error
 *
 * With this configuration:
 *   - The JavaScript client microservice (and other frontends)
 *     can call the API freely
 *
 * SECURITY NOTE:
 *   In production, replace "*" with your actual frontend domain(s):
 *   .allowedOrigins("https://your-app.com", "https://admin.your-app.com")
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // Apply CORS to all API endpoints
                .addMapping("/api/**")

                // Allow requests from any origin (restrict in production!)
                .allowedOrigins("*")

                // Allow these HTTP methods
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                // Allow these headers in requests
                .allowedHeaders("*")

                // Cache preflight response for 1 hour (3600 seconds)
                // This reduces the number of OPTIONS preflight requests
                .maxAge(3600);
    }
}
