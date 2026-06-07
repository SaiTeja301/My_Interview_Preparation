# ============================================================
# DOCKERFILE — Node.js Client Microservice
# ============================================================

FROM node:18-alpine

# Security: Run as non-root user
RUN addgroup -S nodejs && adduser -S nodejs -G nodejs

WORKDIR /app

# Copy package files first (dependency caching)
COPY package*.json ./

# Install production dependencies only
RUN npm ci --omit=dev

# Copy source code
COPY src ./src

# Change ownership
RUN chown -R nodejs:nodejs /app

USER nodejs

EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:3000/health || exit 1

CMD ["node", "src/index.js"]
