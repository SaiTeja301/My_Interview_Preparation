# ============================================================
# DOCKERFILE — Multi-Stage Build for Spring Boot
# ============================================================
#
# MULTI-STAGE BUILD EXPLANATION:
#   Stage 1 (builder): Uses Maven to compile and package the JAR
#   Stage 2 (runtime): Uses a slim JRE to run the JAR
#
# WHY MULTI-STAGE?
#   - Build stage needs Maven + JDK (large: ~800MB)
#   - Runtime only needs JRE (small: ~200MB)
#   - Final image is 3-4x smaller = faster deployments
#
# BUILD COMMAND:
#   docker build -t bedrock-api:1.0.0 .
#
# RUN COMMAND:
#   docker run -p 8080:8080 \
#     -e AWS_ACCESS_KEY_ID=your-key \
#     -e AWS_SECRET_ACCESS_KEY=your-secret \
#     -e AWS_REGION=us-east-1 \
#     bedrock-api:1.0.0
#
# ============================================================

# ==========================================
# STAGE 1: BUILD
# ==========================================
# Use Eclipse Temurin JDK 17 (free, production-ready JDK)
FROM eclipse-temurin:17-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy Maven wrapper and POM first (for dependency caching)
# Docker caches each layer — if pom.xml hasn't changed,
# Maven dependencies won't be re-downloaded on rebuilds
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies (cached separately from source code)
RUN ./mvnw dependency:go-offline -B

# Now copy source code (changes more frequently than dependencies)
COPY src ./src

# Build the JAR (skip tests for faster builds — run tests in CI separately)
RUN ./mvnw package -DskipTests -B

# ==========================================
# STAGE 2: RUNTIME
# ==========================================
# Use JRE-only image (no compiler needed at runtime)
FROM eclipse-temurin:17-jre-alpine

# Security: Run as non-root user
# Never run containers as root in production!
RUN addgroup -S spring && adduser -S spring -G spring

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Change ownership to the non-root user
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring

# Expose the application port
EXPOSE 8080

# Health check — Kubernetes/ECS will use this to check container health
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/v1/health || exit 1

# JVM OPTIMISATIONS for containers:
#   -XX:+UseContainerSupport     → JVM respects container memory limits
#   -XX:MaxRAMPercentage=75.0    → Use 75% of container memory for heap
#   -Djava.security.egd=...      → Faster startup (non-blocking random)
#
# SPRING PROFILE:
#   Default to 'prod' profile in containers
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=prod", \
    "-jar", "app.jar"]
