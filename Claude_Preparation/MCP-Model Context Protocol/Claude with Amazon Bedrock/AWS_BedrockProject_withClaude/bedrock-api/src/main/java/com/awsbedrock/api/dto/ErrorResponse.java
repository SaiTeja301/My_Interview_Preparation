package com.awsbedrock.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ============================================================
 * ErrorResponse — Standardised Error Envelope
 * ============================================================
 *
 * All error responses from the API follow this consistent format.
 * This makes it easy for clients to parse errors programmatically.
 *
 * WHY STANDARDISED ERRORS MATTER:
 *   Without this, Spring Boot returns different error formats for
 *   different exception types. The client never knows what shape
 *   the error JSON will be. With this DTO, every error looks like:
 *
 * {
 *   "timestamp": "2025-01-15T10:30:45",
 *   "status": 400,
 *   "error": "Bad Request",
 *   "message": "Prompt is required and cannot be blank",
 *   "path": "/api/v1/chat"
 * }
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /** When the error occurred — formatted as ISO date-time */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /** HTTP status code (400, 404, 429, 500, etc.) */
    private int status;

    /** Human-readable error category ("Bad Request", "Internal Server Error") */
    private String error;

    /** Detailed error message explaining what went wrong */
    private String message;

    /** The API endpoint path that was called */
    private String path;
}
