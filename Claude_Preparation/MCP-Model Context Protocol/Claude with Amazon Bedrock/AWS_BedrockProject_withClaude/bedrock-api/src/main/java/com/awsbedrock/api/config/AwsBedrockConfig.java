package com.awsbedrock.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.core.retry.RetryPolicy;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

import java.time.Duration;

/**
 * ============================================================
 * AwsBedrockConfig — AWS Bedrock Client Configuration
 * ============================================================
 *
 * This class creates a singleton BedrockRuntimeClient bean that
 * the entire application uses to communicate with AWS Bedrock.
 *
 * KEY CONCEPTS FOR BEGINNERS:
 *
 * 1. @Configuration → Tells Spring this class contains @Bean methods.
 *    Spring calls these methods at startup to create objects (beans)
 *    that can be injected elsewhere with @Autowired.
 *
 * 2. @Value("${...}") → Injects values from application.yml.
 *    Example: @Value("${aws.bedrock.region}") reads the value under
 *    aws → bedrock → region in your YAML file.
 *
 * 3. DefaultCredentialsProvider → The AWS SDK's credential chain.
 *    It automatically checks (in order):
 *      a) Java System Properties (aws.accessKeyId, aws.secretAccessKey)
 *      b) Environment Variables (AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY)
 *      c) AWS credentials file (~/.aws/credentials)
 *      d) AWS config file (~/.aws/config) with SSO/profiles
 *      e) ECS Container Credentials (for ECS/Fargate tasks)
 *      f) EC2 Instance Profile (IAM Role attached to EC2 instance)
 *
 *    This means you NEVER hard-code credentials in code!
 *
 * 4. RetryPolicy → Automatically retries failed API calls
 *    (e.g., network timeouts, throttling) up to 3 times with
 *    exponential backoff.
 */
@Configuration
public class AwsBedrockConfig {

    /**
     * AWS region where Bedrock models are available.
     * Common choices: us-east-1, us-west-2, eu-west-1, ap-northeast-1
     * Not all models are available in all regions — check AWS docs.
     */
    @Value("${aws.bedrock.region:us-east-1}")
    private String awsRegion;

    /**
     * AWS Access Key ID, optional for local development overrides.
     */
    @Value("${aws.bedrock.access-key-id:}")
    private String accessKeyId;

    /**
     * AWS Secret Access Key, optional for local development overrides.
     */
    @Value("${aws.bedrock.secret-access-key:}")
    private String secretAccessKey;

    /**
     * Creates the BedrockRuntimeClient bean.
     *
     * BedrockRuntimeClient is used for:
     *   - converse()       → Unified API to chat with any model
     *   - converseStream() → Streaming version of converse
     *   - invokeModel()    → Low-level API (model-specific JSON payloads)
     *
     * We use converse() because it works the SAME way across ALL models
     * (Claude, Titan, Llama, Mistral, etc.) — no model-specific JSON needed.
     */
    @Bean
    public BedrockRuntimeClient bedrockRuntimeClient() {
        // Resolve credentials provider: use static basic credentials if explicitly set,
        // otherwise fall back to the default SDK credential loading chain (for environments like K8s, EC2).
        AwsCredentialsProvider credentialsProvider;
        if (accessKeyId != null && !accessKeyId.trim().isEmpty() &&
            secretAccessKey != null && !secretAccessKey.trim().isEmpty()) {
            credentialsProvider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKeyId, secretAccessKey)
            );
        } else {
            credentialsProvider = DefaultCredentialsProvider.create();
        }

        return BedrockRuntimeClient.builder()

                // REGION: Where your Bedrock models are hosted
                .region(Region.of(awsRegion))

                // CREDENTIALS: Dynamic provider chain determined above
                .credentialsProvider(credentialsProvider)

                // CLIENT CONFIGURATION: Timeouts and retry behavior
                .overrideConfiguration(ClientOverrideConfiguration.builder()

                        // API call timeout: max time for the ENTIRE call including retries
                        // Bedrock models can take 30-60s for long responses
                        .apiCallTimeout(Duration.ofSeconds(120))

                        // Individual attempt timeout: max time for a SINGLE attempt
                        .apiCallAttemptTimeout(Duration.ofSeconds(60))

                        // Retry policy: retries up to 3 times on transient errors
                        // Uses exponential backoff (1s, 2s, 4s between retries)
                        .retryPolicy(RetryPolicy.builder()
                                .numRetries(3)
                                .build())

                        .build())

                .build();
    }
}
