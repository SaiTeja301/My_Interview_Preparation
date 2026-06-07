package com.awsbedrock.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================
 * BedrockApiApplication — Main Entry Point
 * ============================================================
 *
 * This is the starting class for the Spring Boot application.
 *
 * @SpringBootApplication combines three annotations:
 *   1. @Configuration   → Marks this class as a source of bean definitions
 *   2. @EnableAutoConfiguration → Tells Spring Boot to auto-configure beans
 *      based on the dependencies in pom.xml (e.g., Tomcat, Jackson)
 *   3. @ComponentScan   → Scans com.awsbedrock.api and sub-packages for
 *      @Component, @Service, @Controller, @Repository beans
 *
 * HOW TO RUN:
 *   Option 1: mvn spring-boot:run
 *   Option 2: java -jar target/bedrock-api-1.0.0.jar
 *   Option 3: Run this class directly from your IDE
 */
@SpringBootApplication
public class BedrockApiApplication {

    public static void main(String[] args) {
        // SpringApplication.run() does the following:
        // 1. Creates the Spring ApplicationContext (IoC container)
        // 2. Starts the embedded Tomcat server (default port 8080)
        // 3. Registers all beans found via component scanning
        // 4. Applies auto-configuration (e.g., Jackson, Actuator)
        SpringApplication.run(BedrockApiApplication.class, args);
    }
}
