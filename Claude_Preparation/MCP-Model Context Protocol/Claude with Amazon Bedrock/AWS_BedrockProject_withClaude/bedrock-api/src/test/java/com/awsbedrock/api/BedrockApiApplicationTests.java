package com.awsbedrock.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * ============================================================
 * BedrockApiApplicationTests — Application Context Test
 * ============================================================
 *
 * This test verifies that the Spring Boot application context
 * loads successfully — meaning all beans are created, all
 * configurations are valid, and there are no circular dependencies.
 *
 * NOTE: For this test to pass without real AWS credentials,
 * we override the AWS region and use a mock-friendly setup.
 * In a full test suite, you would mock the BedrockRuntimeClient.
 */
@SpringBootTest
@TestPropertySource(properties = {
        "aws.bedrock.region=us-east-1",
        "aws.bedrock.default-model=apac.anthropic.claude-3-sonnet-20240229-v1:0"
})
class BedrockApiApplicationTests {

    @Test
    void contextLoads() {
        // If this test passes, it means:
        // 1. All @Configuration classes are valid
        // 2. All @Bean methods execute without errors
        // 3. All @Autowired injections are satisfied
        // 4. No circular dependencies exist
        //
        // Note: This test may fail if AWS credentials are not configured
        // because BedrockRuntimeClient requires valid credentials at creation time.
        // To fix: mock the BedrockRuntimeClient bean in a test configuration.
    }
}
