package com.awsbedrock.api.util;

/**
 * ============================================================
 * ModelRegistry — AWS Bedrock Model ID Constants
 * ============================================================
 *
 * Each Bedrock model has a unique Model ID used in API calls.
 * This registry centralises them for easy reference and prevents
 * typos in model ID strings scattered across the codebase.
 *
 * MODEL ID FORMAT: provider.model-name-version
 *
 * IMPORTANT NOTES:
 * 1. You must ENABLE each model in the AWS Bedrock Console before use
 *    (AWS Console → Bedrock → Model access → Request access)
 * 2. Not all models are available in all AWS regions
 * 3. Pricing varies significantly between models — check AWS pricing page
 * 4. Model IDs may change when new versions are released
 *
 * CHOOSING THE RIGHT MODEL:
 * ┌─────────────────┬──────────────────────────┬─────────────────────┐
 * │ Use Case        │ Recommended Model        │ Why                 │
 * ├─────────────────┼──────────────────────────┼─────────────────────┤
 * │ Chat / General  │ Claude 3.5 Sonnet        │ Best quality/cost   │
 * │ Coding          │ Claude 3.5 Sonnet        │ Top coding ability  │
 * │ Fast responses  │ Claude 3 Haiku           │ Lowest latency      │
 * │ Complex tasks   │ Claude 3 Opus            │ Highest capability  │
 * │ Embeddings      │ Titan Embeddings V2      │ AWS-native, cheap   │
 * │ Cost-sensitive   │ Llama 3 8B / Haiku      │ Lowest price/token  │
 * │ RAG             │ Titan Embeddings + Sonnet│ Embed + Generate    │
 * └─────────────────┴──────────────────────────┴─────────────────────┘
 */
public final class ModelRegistry {

    // Private constructor prevents instantiation of this utility class
    private ModelRegistry() {
        throw new UnsupportedOperationException("Utility class — do not instantiate");
    }

    // =============================================
    // ANTHROPIC CLAUDE MODELS
    // Best for: chat, coding, analysis, reasoning
    // =============================================

    /** Claude 3.5 Sonnet v2 — Best balance of quality, speed, and cost */
    public static final String CLAUDE_3_5_SONNET_V2 = "apac.anthropic.claude-sonnet-4-20250514-v1:0";

    /** Claude 3.5 Haiku — Fast and cost-effective, good for simple tasks */
    public static final String CLAUDE_3_5_HAIKU = "anthropic.claude-3-5-haiku-20241022-v1:0";

    /** Claude 3 Opus — Most capable, best for complex reasoning */
    public static final String CLAUDE_3_OPUS = "anthropic.claude-3-opus-20240229-v1:0";

    /** Claude 3 Sonnet — Previous generation, still very capable */
    public static final String CLAUDE_3_SONNET = "anthropic.claude-3-sonnet-20240229-v1:0";

    /** Claude 3 Haiku — Fastest Claude model, lowest cost */
    public static final String CLAUDE_3_HAIKU = "anthropic.claude-3-haiku-20240307-v1:0";

    // =============================================
    // AMAZON TITAN MODELS
    // Best for: embeddings, basic text generation
    // =============================================

    /** Titan Text G1 Express — AWS-native text generation */
    public static final String TITAN_TEXT_EXPRESS = "amazon.titan-text-express-v1";

    /** Titan Text G1 Lite — Lighter, faster, cheaper text generation */
    public static final String TITAN_TEXT_LITE = "amazon.titan-text-lite-v1";

    /** Titan Text Premier — Higher quality text generation */
    public static final String TITAN_TEXT_PREMIER = "amazon.titan-text-premier-v1:0";

    /** Titan Embeddings V2 — For generating text embeddings (RAG, search) */
    public static final String TITAN_EMBEDDINGS_V2 = "amazon.titan-embed-text-v2:0";

    // =============================================
    // META LLAMA MODELS
    // Best for: cost-effective general-purpose tasks
    // =============================================

    /** Llama 3.1 70B Instruct — High quality, open-source model */
    public static final String LLAMA_3_1_70B = "meta.llama3-1-70b-instruct-v1:0";

    /** Llama 3.1 8B Instruct — Fast, lightweight open-source model */
    public static final String LLAMA_3_1_8B = "meta.llama3-1-8b-instruct-v1:0";

    /** Llama 3.2 90B Vision — Multimodal model (text + images) */
    public static final String LLAMA_3_2_90B = "meta.llama3-2-90b-instruct-v1:0";

    // =============================================
    // MISTRAL AI MODELS
    // Best for: European-language tasks, coding
    // =============================================

    /** Mistral Large — Flagship model, strong reasoning */
    public static final String MISTRAL_LARGE = "mistral.mistral-large-2407-v1:0";

    /** Mixtral 8x7B — Mixture-of-experts, great cost/performance */
    public static final String MIXTRAL_8X7B = "mistral.mixtral-8x7b-instruct-v0:1";

    // =============================================
    // COHERE MODELS
    // Best for: embeddings, search, RAG
    // =============================================

    /** Cohere Command R+ — Optimized for RAG and tool use */
    public static final String COHERE_COMMAND_R_PLUS = "cohere.command-r-plus-v1:0";

    /** Cohere Embed English — English text embeddings */
    public static final String COHERE_EMBED_ENGLISH = "cohere.embed-english-v3";

    /** Cohere Embed Multilingual — Multilingual text embeddings */
    public static final String COHERE_EMBED_MULTILINGUAL = "cohere.embed-multilingual-v3";

    // =============================================
    // DEFAULT MODEL
    // =============================================

    /** Default model used when no model is specified in the request */
    public static final String DEFAULT_MODEL = CLAUDE_3_5_SONNET_V2;
}
