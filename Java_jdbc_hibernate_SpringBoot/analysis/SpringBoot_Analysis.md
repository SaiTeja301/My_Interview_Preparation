# SPRING BOOT - COMPREHENSIVE INTERVIEW PREPARATION GUIDE
> *For: 7+ Years Experience Level | Java Developer*

## SECTION 1: SOURCE ANALYSIS

Source: Spring_and_Spring_boot.txt (Spring Boot section ~5000 lines)
Coverage: Auto Configuration, Starter dependencies, REST API, Profiles, Actuator,
Exception handling, Spring MVC, Packaging
Missing: Spring Boot 3.x changes, GraalVM native images, Observability (Micrometer),
Custom Auto-configuration, ConfigurationProperties validation

## SECTION 2: 5 INTERVIEW ROUNDS

## ROUND 1 - BASIC

#### Q1. What is Spring Boot? How is it different from Spring?

Spring: Full-featured framework, requires manual configuration
Spring Boot: Opinionated, convention-over-configuration, auto-configures

Spring Boot Advantages:
1. Auto Configuration: Configures beans based on classpath
2. Starter Dependencies: Curated dependency sets (spring-boot-starter-web)
3. Embedded Server: Tomcat/Jetty/Undertow built in
4. Production Ready: Actuator, health checks, metrics
5. No XML: Java-based or YAML/properties configuration

#### Q2. @SpringBootApplication annotation - what does it include?

@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan

@Configuration: Marks class as bean definition source
@EnableAutoConfiguration: Enables auto-configuration based on classpath
@ComponentScan: Scans current package and sub-packages for components

## ROUND 2 - CORE TECHNICAL

#### Q3. Spring Boot Auto-Configuration internal working.

Flow:
1. @EnableAutoConfiguration triggers AutoConfigurationImportSelector
2. Reads META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
3. Each auto-config class has @Conditional annotations:
@ConditionalOnClass: Only if class on classpath
@ConditionalOnMissingBean: Only if user hasn't defined bean
@ConditionalOnProperty: Only if property set

Example (DataSource auto-config):
@ConditionalOnClass(DataSource.class) // HikariCP on classpath?
@ConditionalOnMissingBean(DataSource.class) // User hasn't defined one?
@AutoConfiguration
public class DataSourceAutoConfiguration {
    @Bean
    public HikariDataSource dataSource(DataSourceProperties props) {
        return props.initializeDataSourceBuilder()
            .type(HikariDataSource.class).build();
    }
}

Debug auto-config: --debug flag or spring.autoconfigure.exclude

#### Q4. Profiles in Spring Boot.

application.properties (default)
application-dev.properties
application-prod.properties

Activation: spring.profiles.active=dev (or --spring.profiles.active=prod)

@Profile("prod")
@Configuration
public class ProdSecurityConfig { ... }

Multi-profile YAML:
```yaml
spring:
profiles:
active: dev
spring:
config:
activate:
on-profile: dev
server:
port: 8080
spring:
config:
activate:
on-profile: prod
server:
port: 443
```
#### Q5. Spring Boot Actuator.

Endpoint             | Purpose
/actuator/health     | Application health status
/actuator/info       | App information
/actuator/metrics    | JVM/app metrics
/actuator/env        | Environment properties
/actuator/beans      | All registered beans
/actuator/mappings   | URL mappings
/actuator/loggers    | View/change log levels at runtime
/actuator/heapdump   | Heap dump download
/actuator/threaddump | Thread dump

Config:
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always

Custom Health:
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        if (isDatabaseUp()) return Health.up().withDetail("db", "Running").build();
        return Health.down().withDetail("db", "Not reachable").build();
    }
}

## ROUND 3 - ADVANCED

#### Q6. Exception Handling in Spring Boot.

@RestControllerAdvice (Global Exception Handler):
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(404, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors().stream()
            .collect(Collectors.toMap(
                FieldError::getField, FieldError::getDefaultMessage));
        return new ErrorResponse(400, "Validation failed", errors);
    }
}

Flow:
Client Request -> Controller -> throws Exception
|
@RestControllerAdvice catches
|
Maps to HTTP status + Error body
|
JSON response to client

#### Q7. Spring Boot Logging.

Default: Logback (via spring-boot-starter-logging)
Can switch to: Log4j2

Levels: TRACE < DEBUG < INFO < WARN < ERROR
logging.level.root=INFO
logging.level.com.myapp=DEBUG
logging.file.name=/var/log/app.log
logging.pattern.console=%d{HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n

Structured logging for ELK/Splunk:
logging.pattern.console={"time":"%d","level":"%p","logger":"%logger","msg":"%m"}%n

## ROUND 4 - SCENARIO-BASED

#### Q8. REST API best practices in Spring Boot.

@RestController
@RequestMapping("/api/v1/policies")
public class PolicyController {
    @GetMapping
    public ResponseEntity<Page<PolicyDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(policyService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PolicyDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(policyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PolicyDTO> create(@Valid @RequestBody PolicyRequest req) {
        PolicyDTO created = policyService.create(req);
        URI location = URI.create("/api/v1/policies/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PolicyDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody PolicyRequest req) {
        return ResponseEntity.ok(policyService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        policyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

## Best Practices:

1. Use DTOs (never expose Entity directly)
2. Proper HTTP status codes (201 Created, 204 No Content)
3. API versioning (/api/v1/)
4. Pagination for list endpoints
5. Validation with @Valid + DTO annotations
6. Global exception handling

## ROUND 5 - ARCHITECTURE

#### Q9. Spring Boot application structure.

com.company.project/
```text
    ├── config/           (Configuration classes)
    ├── controller/       (REST controllers)
    ├── service/          (Business logic)
    ├── repository/       (Data access - JPA)
    ├── entity/           (JPA entities / DB models)
    ├── dto/              (Request/Response DTOs)
    ├── exception/        (Custom exceptions + handler)
    ├── security/         (Security config, JWT)
    ├── util/             (Utility classes)
    └── Application.java  (Main class)

```
KEY QUESTIONS:
1. Spring vs Spring Boot
2. @SpringBootApplication breakdown
3. Auto-Configuration internal working
4. Profiles usage
5. Actuator endpoints
6. Global exception handling
7. REST API best practices
8. Embedded server configuration

## END OF SPRING BOOT ANALYSIS

# SPRING BOOT - ADDITIONAL QUESTIONS (Q10-Q35) - ENHANCED EXPANSION

#### Q10. Spring Boot Auto-Configuration debugging.

Run with --debug flag to see auto-configuration report:
Positive matches: Auto-configurations that WERE applied
Negative matches: Auto-configurations that were NOT applied (and why)
Exclusions: Manually excluded auto-configurations

Exclude: @SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

#### Q11. @ConfigurationProperties - type-safe config.

@ConfigurationProperties(prefix = "app.policy")
public class PolicyConfig {
    private String defaultType;
    private int maxRetries;
    private Duration timeout; // Spring parses "5s", "2m", "1h"
    private List<String> allowedStatuses;
    // getters + setters
}
// app.policy.default-type=LIFE
// app.policy.max-retries=3
// app.policy.timeout=30s

#### Q12. Spring Boot Starter - how to create custom starter.

1. Create auto-configuration module
2. @Configuration + @ConditionalOnClass + @ConditionalOnProperty
3. Register in META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
4. Package as spring-boot-starter-yourlib

#### Q13. Embedded server comparison.

Tomcat (default): Servlet-based, blocking I/O, most mature
Jetty: Lightweight, HTTP/2 support, good for microservices
Undertow: Non-blocking, good performance, Wildfly team
Netty: Non-blocking, used with WebFlux (reactive)

Switch: spring-boot-starter-web (exclude tomcat) + spring-boot-starter-undertow

#### Q14. Spring Boot DevTools.

LiveReload, automatic restart, H2 console, property defaults for dev.
spring-boot-devtools dependency (auto-disabled in production JAR).

#### Q15. Scenario: How to handle 10K requests/second?

1. Connection pool tuning: HikariCP maximumPoolSize
2. Response caching: @Cacheable with Redis
3. Async processing: @Async + CompletableFuture
4. Database optimization: Indexes, query tuning, read replicas
5. Horizontal scaling: Multiple instances + load balancer
6. Rate limiting: Resilience4j RateLimiter
7. Response compression: server.compression.enabled=true

#### Q16. Spring WebFlux vs Spring MVC.

MVC: Servlet-based, blocking, thread-per-request (Tomcat)
WebFlux: Reactive, non-blocking, event-loop (Netty)

MVC: Good for CRUD apps, simple, most developers know it
WebFlux: Good for streaming, high-concurrency, reactive DBs

Use MVC unless you have specific non-blocking requirements.

Q17-Q20 Quick SpringBoot:

#### Q17. Graceful shutdown: server.shutdown=graceful (waits for active requests)

#### Q18. Spring Boot testing slices: @WebMvcTest, @DataJpaTest, @JsonTest

#### Q19. Health check groups: management.endpoint.health.group.readiness.include=db,diskSpace

#### Q20. Externalized config priority: CLI args > env vars > application.yml > defaults

## SPRING BOOT - DEEP DIVE ANALYSIS (FROM SOURCE FILE)

Extracted from Spring_and_Spring_boot.txt (Lines 5800-10694)
Topics: DataSource, REST APIs, SpEL, JSR-330, YAML Config

## SECTION A: DATASOURCE AUTO-CONFIGURATION - DECISION TREE

Spring Boot DataSource Auto-Configuration Priority:

Spring Boot starts
|
v
spring-boot-starter-jdbc on classpath?
| YES                    | NO
|                        --> No DataSource created
v
HikariCP jar present?
| YES              | NO
v                  v
CREATE HikariDS     Tomcat JDBC jar present?
(DEFAULT)                | YES          | NO
v              v
CREATE Tomcat    DBCP2 present?
DataSource       | YES    | NO
v        v
CREATE DBCP2   ERROR!
DataSource     No pool found

Manual DataSource Configuration (Overriding Auto-Config):
1. Exclude auto-config:
   @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

2. Create @Bean method in @Configuration class:
   @Configuration
   public class PersistConfig {
       @Autowired
       private Environment env;

       @Bean
       public DataSource createDataSource() throws Exception {
           ComboPooledDataSource ds = new ComboPooledDataSource();
           ds.setDriverClass(env.getProperty("jdbc.driver"));
           ds.setJdbcUrl(env.getProperty("jdbc.url"));
           ds.setUser(env.getProperty("jdbc.user"));
           ds.setPassword(env.getProperty("jdbc.password"));
           return ds;
       }
   }

Switching Connection Pools (HikariCP to Tomcat JDBC):
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
    <exclusions>
      <exclusion>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
      </exclusion>
    </exclusions>
  </dependency>
  <dependency>
    <groupId>org.apache.tomcat</groupId>
    <artifactId>tomcat-jdbc</artifactId>
  </dependency>

## SECTION B: CONFIGURATION - PROPERTIES vs YAML DETAILED

application.properties (Flat Key-Value):
```properties
server.port=8080
spring.application.name=MyApp
spring.datasource.url=jdbc:mysql:///testschema
spring.datasource.username=root
spring.datasource.password=root123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

application.yml (YAML - Hierarchical):
spring:
application:
name: MyApp
datasource:
url: jdbc:mysql:///testschema
username: root
password: root123
driver-class-name: com.mysql.cj.jdbc.Driver
server:
port: 8080

Comparison:
Feature                 .properties             .yml
----------------------  ----------------------  --------------------------
Format                  Flat key=value          Hierarchical YAML
Readability             Simple for few props    Better for nested config
Multi-document          Not supported           Supported (--- separator)
Profile-specific        application-dev.props   Can embed in same file
Collection support      Comma-separated         Native list/map syntax
Spring Boot Priority    Loaded AFTER yml        Loaded first
```
## SECTION C: SPRING EXPRESSION LANGUAGE (SpEL) - DEEP DIVE

SpEL enables runtime expression evaluation within Spring beans.

Syntax:
dollar{key}    = Property file value injection.
                  @Value("dollar{server.port}") -> reads from application.properties
  #{expression} = SpEL runtime expression.
```text
                  @Value("#{beanName.field}") -> reads from another bean.
                  @Value("#{10 + 20}") -> computed result = 30.

```
Cross-Bean Computation Example:
  // Bean 1: ItemsInfo (reads from properties)
  @Component("item")
  public class ItemsInfo {
      @Value("dollar{items.info.idlyPrice}")  private double idlyPrice;   // 10
      @Value("dollar{items.info.dosaPrice}")  private double dosaPrice;   // 20
      @Value("dollar{items.info.vadaPrice}")  private double vadaPrice;   // 30
  }

  // Bean 2: BillGenerator (reads from Bean 1 via SpEL)
  @Component("bill")
  public class BillGenerator {
      @Value("#{item.idlyPrice + item.dosaPrice + item.vadaPrice}")
      private double billAmount;  // Computed at DI time = 60.0

      @Value("Accord")
      private String hotelName;   // Literal string injection

      @Autowired
      private ItemsInfo info;     // Full bean injection
  }

SpEL Evaluation Flow:
  1. Spring creates "item" bean and injects property values (10, 20, 30).
  2. Spring creates "bill" bean.
  3. SpEL engine locates "item" bean in ApplicationContext.
  4. Reads idlyPrice, dosaPrice, vadaPrice fields.
  5. Evaluates expression: 10 + 20 + 30 = 60.0.
  6. Injects result into billAmount field.
  7. This happens during BeanPostProcessor phase.

## SECTION D: JSR-330 - NON-INVASIVE PROGRAMMING

JSR-330 annotations allow DI without Spring-specific imports.
Requires javax.inject dependency.

JSR-330 Annotation   Spring Equivalent         Notes
------------------   ----------------------    ----------------------------------
@Inject              @Autowired                Field/constructor/setter. No
                                               "required" attribute.
@Named("name")       @Component + @Qualifier   Names bean AND resolves ambiguity
@Resource            @Autowired+@Qualifier     JSR-250. Field/setter only (NOT
                                               constructor). byName first.

Annotation Priority Order (recommended):
  1. Java Config Annotations (JSR-330: @Inject, @Named) - First choice
  2. Spring Annotations (@Autowired, @Component)
  3. Third-party Annotations
  4. Custom Annotations

Example:
  @Named("std")
  public class Student {
      @Inject
      @Named("courseId")
      private ICourseMaterial material;
  }

  @Named("java")
  public class JavaCourseMaterial implements ICourseMaterial { ... }

Configuration:
  application.properties: course.choose=java
  applicationContext.xml:  <alias name="dollar{course.choose}" alias="courseId"/>
  -> "courseId" resolves to "java" bean dynamically.

## SECTION E: REST API DEVELOPMENT - CODE EXAMPLES

REST Controller with Full CRUD:
  @RestController
  @RequestMapping("/api/customers")
  public class CustomerRestController {

      @Autowired
      private CustomerService service;

      @GetMapping("/getById")
      public ResponseEntity<CustomerVo> getById(@RequestParam int id) {
          CustomerVo customer = service.findById(id);
          if (customer != null) return ResponseEntity.ok(customer);
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      @GetMapping("/all")
      public ResponseEntity<List<CustomerVo>> getAll() {
          return ResponseEntity.ok(service.getAllCustomers());
      }

      @PostMapping("/insert")
      public ResponseEntity<String> insert(@RequestBody CustomerVo vo) {
          try {
              String result = service.processResult(vo);
              return ResponseEntity.status(HttpStatus.CREATED).body(result);
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("Error: " + e.getMessage());
          }
      }

      @DeleteMapping("/deleteById")
      public ResponseEntity<String> delete(@RequestParam int id) {
          int status = service.deleteById(id);
          return ResponseEntity.ok("Deleted customer with ID: " + status);
      }

      @PutMapping("/updateById")
      public ResponseEntity<CustomerVo> update(@RequestParam int id) {
          CustomerVo updated = service.updateById(id);
          return ResponseEntity.ok(updated);
      }
  }

API Endpoints (from source file project):
  GET  http://localhost:8080/Spr/customers/getCustomerById?id=1
  GET  http://localhost:8080/Spr/customers/all
  POST http://localhost:8080/Spr/customers/insert
  PUT  http://localhost:8080/Spr/customers/updateCustomerById?id=4
  DELETE http://localhost:8080/Spr/customers/deleteCustomerById?id=6

## SECTION F: SPRING BOOT COLLECTION INJECTION (YAML)

Spring Boot supports injecting complex data from application.yml
into beans using @ConfigurationProperties.

application.yml Example:
```yaml
employee:
empName: Aravind
empId: 21
empSkills:
- Core Java
- Spring Boot
- J2EE
- SQL
empProjects:
- Banking Finance System
- Retail Merchandise System
idDetails:
aadhar: AD1234XYZ
pan: PAN1234XYZ
passport: PAS1234XYZ

Java Bean:
@Component
@ConfigurationProperties(prefix = "employee")
public class Employee {
    private String empName;
    private int empId;
    private List<String> empSkills;
    private List<String> empProjects;
    private Map<String, String> idDetails;
    // getters and setters
}
```
## SECTION G: SPRING BOOT JDBC PROJECT - COMPLETE LAYERED ARCHITECTURE

Project Architecture Flow:

REST CLIENT (Postman / Browser)
| HTTP GET/POST/PUT/DELETE
v
@RestController Layer (REST API)
- @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
- @RequestParam, @PathVariable, @RequestBody
- ResponseEntity returns
     | (DTO)
     v
@Service Layer (Business Logic)
- DTO <-> BO conversion
- Calculations (e.g., Simple Interest formula)
- Transaction boundaries
     | (BO)
     v
@Repository Layer (Data Access)
- JDBC queries
- DataSource.getConnection()
- PreparedStatement + ResultSet
- CRUD operations
     | (SQL)
     v
MySQL Database (via HikariCP DataSource auto-configured from yml/props)

application.yml (Spring Boot DataSource):
```yaml
  spring:
    datasource:
      url: jdbc:mysql:///testschema
      username: root
      password: 2580
      driver-class-name: com.mysql.cj.jdbc.Driver
```
## SECTION H: EXECUTION FLOW DIAGRAMS

Spring Boot DI Flow:
APP START -> @SpringBootApplication triggers ComponentScan
|
```text
|-> Finds @Named/@Component beans in packages
|-> @ImportResource loads applicationContext.xml (if present)
|      --> <alias name="dollar{course.choose}" alias="courseId"/>
|-> IoC Container: Instantiate all beans + inject dependencies
|-> Start Embedded Tomcat (if web starter present)
--> APPLICATION READY

```
REST API Request Flow:
Client HTTP Request
- DispatcherServlet (Front Controller)
- HandlerMapping: finds matching @RequestMapping
- @RestController method invoked
- @RequestParam/@PathVariable extracted
```text
- @RequestBody deserialized (Jackson JSON -> Java)
- Service Layer called -> DAO Layer called -> DB query

```
- ResponseEntity returned
- HTTP Response (JSON body + status code)

## SECTION I: ADDITIONAL BEST PRACTICES (FROM SOURCE FILE)

1. Use application.yml for nested configs, .properties for simple ones.
2. Always use ResponseEntity with proper HTTP status codes.
3. Use @ConfigurationProperties over multiple @Value annotations.
4. Use spring.profiles.active for environment-specific configs.
5. Enable Actuator: management.endpoints.web.exposure.include=health,info
6. Handle exceptions globally with @RestControllerAdvice.
7. Use HikariCP (default) unless a specific pool is needed.
8. Keep controller layer thin - move logic to service layer.
9. Use constructor injection in Spring Boot beans.
10. Use Lombok to reduce boilerplate in VO/DTO/BO classes.

## END OF SPRING BOOT DEEP DIVE ANALYSIS - Appended March 2026

## ASPECT-ORIENTED PROGRAMMING (AOP) - COMPREHENSIVE INTERVIEW GUIDE

For: 5+ Years Experience Level | Senior Java Developer | Spring Boot
Appended: March 2026

## TABLE OF CONTENTS:

## SECTION 1  : AOP Basics (What, Why, Key Concepts)

## SECTION 2  : Types of Advice (Before, After, Around, etc.)

## SECTION 3  : Pointcut Expressions (Deep Dive)

## SECTION 4  : Proxy Mechanism (JDK vs CGLIB)

## SECTION 5  : Spring AOP vs AspectJ

## SECTION 6  : Real-Time Use Cases with Code

## SECTION 7  : Complete Mini Project (Logging + Performance + Exception)

## SECTION 8  : Advanced Interview Questions

## SECTION 9  : Best Practices & Anti-Patterns

## SECTION 10 : Quick-Fire Interview Q&A Reference

## SECTION 1: AOP BASICS - WHAT, WHY, KEY CONCEPTS

#### Q1. What is AOP (Aspect-Oriented Programming)? Why is it used?

ANSWER:
AOP is a programming paradigm that allows you to separate CROSS-CUTTING
CONCERNS from business logic. Cross-cutting concerns are functionalities
that span across multiple layers of an application (logging, security,
transactions, performance monitoring) and cannot be cleanly modularized
using OOP alone.

PROBLEM WITHOUT AOP (code pollution):
@Service
public class OrderService {
    public void placeOrder(Order order) {
        // Logging code (cross-cutting concern)
        log.info("START placeOrder: " + order);

        // Security check (cross-cutting concern)
        if (!user.hasRole("ORDER_PLACER")) throw new AccessDeniedException();

        // Performance monitoring (cross-cutting concern)
        long start = System.currentTimeMillis();

        // === ACTUAL BUSINESS LOGIC ===
        validateOrder(order);
        chargePayment(order);
        dispatchOrder(order);
        // ==============================

        // More cross-cutting concerns mixed in
        long end = System.currentTimeMillis();
        log.info("EXECUTION TIME: " + (end - start) + "ms");
        log.info("END placeOrder: SUCCESS");
    }
}
PROBLEM: Business logic is buried under cross-cutting concerns.
         Same boilerplate repeated in PaymentService, UserService, etc.

WITH AOP:
@Service
public class OrderService {
    public void placeOrder(Order order) {
        // PURE BUSINESS LOGIC ONLY
        validateOrder(order);
        chargePayment(order);
        dispatchOrder(order);
    }
}
// Logging, Security, Performance -> handled separately in Aspect classes

HOW AOP SOLVES IT:
- Extracts cross-cutting concerns into Aspect classes
- Applies them automatically without modifying business classes
- Results in Clean, DRY, maintainable code

REAL-TIME USE CASES:
1. Logging (method entry/exit/params/return values)
2. Performance Monitoring (execution time measurement)
3. Security / Authorization checks
4. Transaction management (@Transactional internally uses AOP)
5. Exception handling / Alert triggering
6. Caching (@Cacheable internally uses AOP)
7. API request/response tracking
8. Audit logging (who changed what, when)
9. Rate limiting
10. Retry logic

INTERVIEW TIP: AOP is one of the 2 core features of the Spring Framework.
The other is IoC (Inversion of Control / Dependency Injection).

#### Q2. Explain Key AOP Concepts: Aspect, Advice, JoinPoint, Pointcut, Weaving

ANSWER:

| CONCEPT | DEFINITION |
| --- | --- |
| Aspect | A class that contains cross-cutting concern logic. |
| Annotated with @Aspect. Example: LoggingAspect.java |  |
| Advice | The ACTUAL ACTION to take at a join point. |
| Types: @Before, @After, @Around, @AfterReturning, |  |
| @AfterThrowing. It is the METHOD inside an Aspect. |  |
| JoinPoint | A specific point in program execution where advice can |
| be applied. In Spring AOP, always a METHOD EXECUTION. |  |
| The JoinPoint object gives method name, args, target. |  |
| Pointcut | An EXPRESSION that defines WHERE advice should be applied. |
| Which methods? Which classes? Which packages? |  |
| Example: execution(* com.app.service.*.*(..)) |  |
| Weaving | The process of linking Aspect code with the target object. |
| Types: Compile-time, Load-time, Runtime (Spring uses Runtime) |  |
| Target Object | The object being advised (proxied). Example: OrderService |
| Proxy | Spring creates a proxy wrapping the target object. |
| This proxy intercepts method calls and applies advice. |  |

ANALOGY (Real-World):
Think of a TOLL BOOTH on a highway:
- Highway = Your application
- Vehicles (method calls) = JoinPoints
- Toll Booth = Aspect
- Rules at toll booth (collect fee, check permit) = Advice
- "All vehicles passing between 8AM-8PM" = Pointcut
- Installing the toll booth = Weaving

CODE EXAMPLE:
@Aspect                         // <-- This class is an Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.app.service.*.*(..))")  // <-- Pointcut
    public void serviceLayer() {}                        // pointcut method

```text
    @Before("serviceLayer()")   // <-- Advice (Before type)
    public void logBefore(JoinPoint joinPoint) {          // <-- JoinPoint param

```
        System.out.println("Method called: " +
            joinPoint.getSignature().getName());
    }
}

INTERVIEW TRAP:
#### Q: "Can Spring AOP intercept field access or constructor calls?"
**A:** NO! Spring AOP only supports METHOD EXECUTION join points.
AspectJ supports more (field access, constructor call, etc.)

## SECTION 2: TYPES OF ADVICE

#### Q3. Explain all Types of Advice with examples.

ANSWER:

| TYPE | WHEN IT RUNS | USE CASE |
| --- | --- | --- |
| @Before | Before method executes | Auth checks, logging |
| @After | After method (always, like finally) | Cleanup, audit |
| @AfterReturning | After method returns successfully | Log return value |
| @AfterThrowing | After method throws exception | Alert, re-throw |
| @Around | Wraps method (most powerful) | Performance, retry |

@Before ADVICE:
Runs BEFORE the target method. Cannot prevent method execution
(unless exception thrown). Gets JoinPoint to inspect method details.

@Before("execution(* com.app.service.*.*(..))")
public void logMethodEntry(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    log.info(">>> ENTERING: {} with args: {}", methodName,
             Arrays.toString(args));
}

REAL-TIME SCENARIO: Authorization check before every service method.
@Before("execution(* com.app.service.*.*(..))")
public void checkAuthorization(JoinPoint joinPoint) {
    Authentication auth = SecurityContextHolder.getContext()
                                               .getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
        throw new AccessDeniedException("Unauthorized access!");
    }
}

@After ADVICE:
Runs AFTER method execution REGARDLESS of outcome (success or exception).
Like a finally block. Cannot access return value.

@After("execution(* com.app.service.*.*(..))")
public void logMethodExit(JoinPoint joinPoint) {
    log.info("<<< EXITING: {}", joinPoint.getSignature().getName());
    // Cleanup resources, release locks, audit trail, etc.
}

@AfterReturning ADVICE:
Runs only when method RETURNS SUCCESSFULLY. Can access the return value
using the 'returning' attribute.

@AfterReturning(
    pointcut = "execution(* com.app.service.OrderService.placeOrder(..))",
    returning = "result"  // binds return value to this param name
)
public void logReturnValue(JoinPoint joinPoint, Object result) {
    log.info("Method: {} returned: {}",
             joinPoint.getSignature().getName(), result);
}

REAL-TIME SCENARIO: Track successful payment completions.
@AfterReturning(
    pointcut = "execution(* com.app.service.PaymentService.processPayment(..))",
    returning = "paymentResult"
)
public void trackSuccessfulPayment(Object paymentResult) {
    metricsService.incrementSuccessfulPayments();
    log.info("Payment processed: {}", paymentResult);
}

@AfterThrowing ADVICE:
Runs only when method THROWS AN EXCEPTION. Can access the exception
using the 'throwing' attribute.

@AfterThrowing(
    pointcut = "execution(* com.app.service.*.*(..))",
    throwing = "ex"    // binds exception to this param name
)
public void handleException(JoinPoint joinPoint, Exception ex) {
    log.error("EXCEPTION in method: {} | Error: {}",
              joinPoint.getSignature().getName(), ex.getMessage());
    alertService.sendAlert("Service layer exception: " + ex.getMessage());
}

NOTE: @AfterThrowing does NOT suppress the exception. It propagates.

@Around ADVICE (Most Powerful):
Wraps the method. You control WHEN and IF the method runs.
Must call proceed() to execute the target method.
Can modify input args, modify return values, suppress exceptions.

@Around("execution(* com.app.service.*.*(..))")
public Object measurePerformance(ProceedingJoinPoint pjp) throws Throwable {
    long start = System.currentTimeMillis();
    String method = pjp.getSignature().getName();

    log.info(">>> START: {}", method);
    try {
        Object result = pjp.proceed();  // <-- Execute the actual method
        long elapsed = System.currentTimeMillis() - start;
        log.info("<<< END: {} | Time: {}ms", method, elapsed);
        return result;
    } catch (Exception ex) {
        log.error("EXCEPTION in {}: {}", method, ex.getMessage());
        throw ex;  // re-throw
    }
}

IMPORTANT: @Around uses ProceedingJoinPoint (not JoinPoint) because
           it needs the proceed() method to invoke the target.

EXECUTION ORDER (when multiple advice applied to same method):
@Around (before proceed)
```text
  -> @Before
  -> TARGET METHOD EXECUTES
  -> @AfterReturning OR @AfterThrowing

```
@After
@Around (after proceed returns)

INTERVIEW TRAP:
#### Q: "When to use @Around vs @Before + @AfterReturning?"
**A:** Use @Around when you need to measure time (need before AND after),
retry logic (call proceed multiple times), or modify args/result.
Use @Before/@AfterReturning when concerns are independent.

## SECTION 3: POINTCUT EXPRESSIONS (DEEP DIVE)

#### Q4. Explain Pointcut Expressions with examples.

ANSWER:
Pointcut expressions tell Spring AOP WHICH methods to intercept.
They use AspectJ pointcut expression language.

SYNTAX OF execution() POINTCUT:
execution([access-modifier] return-type [declaring-type].method-name(params) [throws])

Wildcards:
= matches any single element (any class, any method, any return type)
..  = matches any number of anything (any params, any sub-packages)

EXAMPLES:

1. ALL methods in a specific class:
execution(* com.app.service.OrderService.*(..))
^          ^ ^                           ^ ^
|modifier  |return|class                |method|params

2. ALL methods in ALL classes in a package:
execution(* com.app.service.*.*(..))

3. ALL methods in package AND sub-packages:
execution(* com.app..*.*(..))
^^--- matches any sub-package

4. Specific method name pattern (starts with "get"):
execution(* com.app.service.*.get*(..))

5. Methods with specific param type:
execution(* com.app.service.*.*(Long, ..))
   // first arg is Long, rest can be anything

6. Methods with NO params:
   execution(* com.app.service.*.find())
   // empty parentheses = no params

7. Methods that return String:
   execution(String com.app.service.*.*(..))

8. Methods annotated with custom annotation:
   @annotation(com.app.annotation.Loggable)
   // intercepts methods annotated with @Loggable

9. Within a specific type:
   within(com.app.service.*)
   // any method within any class in service package

10. Bean name pattern:
bean(orderService)     // specific bean
bean(*Service)         // any bean ending with "Service"

COMBINING POINTCUTS (Logical Operators):
// AND: method in service package AND annotated with @Transactional
@Pointcut("within(com.app.service..*) && @annotation(org.springframework.transaction.annotation.Transactional)")
public void transactionalServiceMethods() {}

// OR: either service OR repository layer
@Pointcut("within(com.app.service..*) || within(com.app.repository..*)")
public void serviceOrRepository() {}

// NOT: exclude specific method
@Pointcut("within(com.app.service..*) && !execution(* com.app.service.*.findById(..))")
public void serviceExceptFindById() {}

REUSABLE POINTCUT DEFINITION:
@Aspect
@Component
public class PointcutDefinitions {

    @Pointcut("execution(* com.app.controller.*.*(..))")
    public void controllerLayer() {}

    @Pointcut("execution(* com.app.service.*.*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(* com.app.repository.*.*(..))")
    public void repositoryLayer() {}

    @Pointcut("serviceLayer() || repositoryLayer()")
    public void serviceAndRepository() {}
}

// Used in another Aspect:
@Before("com.app.aspect.PointcutDefinitions.serviceLayer()")
public void logServiceMethods(JoinPoint jp) { ... }

INTERVIEW TRAP:
#### Q: "What's the difference between within() and execution()?"
**A:** execution() matches method signatures (return type, name, params).
within() matches based on the type (class/package) the method is in.
within(com.app.service.*) = any method in any class in service package.
execution(* com.app.service.*.*(..)) = same effect here, but
execution() gives more control over return type and params.

## SECTION 4: PROXY MECHANISM - JDK vs CGLIB

#### Q5. How does Spring AOP create proxies? JDK Dynamic Proxy vs CGLIB?

ANSWER:
Spring AOP works through PROXIES. When you apply advice to a bean,
Spring does not modify the original class. Instead, it creates a PROXY
object that wraps the original object and intercepts method calls.

TWO PROXY MECHANISMS:

JDK DYNAMIC PROXY:
- Used when target class IMPLEMENTS at least ONE INTERFACE
- Proxy implements the SAME INTERFACE as the target
- Uses java.lang.reflect.Proxy (JDK built-in)
- Proxy only intercepts methods defined in the INTERFACE

OrderService (interface)
^  implements
OrderServiceImpl (actual class)
^  proxied by
JDK Proxy (also implements OrderService)

CGLIB PROXY:
- Used when target class does NOT implement any interface
- Generates a SUBCLASS of the target class at runtime (bytecode generation)
- Uses CGLIB library (included in Spring Core)
- Cannot proxy FINAL classes or FINAL methods (subclassing restriction)

OrderServiceImpl (actual class)
^  subclassed by CGLIB at runtime
CGLIB Proxy (extends OrderServiceImpl, overrides all non-final methods)

WHICH ONE DOES SPRING CHOOSE?

```text
Target implements interface? -> YES -> JDK Dynamic Proxy (DEFAULT)
- NO  -> CGLIB Proxy

```
Force CGLIB always:
@EnableAspectJAutoProxy(proxyTargetClass = true)  // in config class
OR
spring.aop.proxy-target-class=true  // in application.properties

CODE (Proxy in action):
@Service
public class ProductService {  // No interface
    public String getProduct(Long id) { return "Product-" + id; }
}

// Spring creates CGLIB proxy for ProductService
// When you call productService.getProduct(1L) in controller,
// it actually calls CGLIB proxy -> advice applied -> then actual method

INJECTION TRICK:
@Autowired
private ProductService productService;  // CGLIB proxy injected here
// NOT the actual ProductService instance!

Check proxy type at runtime:
System.out.println(productService.getClass());
// Output: class com.app.service.ProductService$$EnhancerBySpringCGLIB$$...

SELF-INVOCATION PROBLEM (CRITICAL INTERVIEW QUESTION):
@Service
public class OrderService {

    public void placeOrder(Order order) {
        // Calls processPayment() directly (self-invocation)
        this.processPayment(order);  // <-- AOP will NOT intercept this!
    }

    @Transactional  // or @Cacheable, @Async
    public void processPayment(Order order) {
        // This advice is BYPASSED because self-call skips proxy!
    }
}

CAUSE: this.processPayment() bypasses the proxy. The proxy only
       intercepts calls coming FROM OUTSIDE the bean.

SOLUTIONS:
1. Inject self (ugly but works):
   @Autowired
   private OrderService self;  // inject proxy of itself
   self.processPayment(order); // goes through proxy

2. Extract to separate service (BEST PRACTICE):
   @Service public class PaymentService {
       @Transactional
       public void processPayment(Order order) { ... }
   }

3. Use AopContext (requires exposeProxy=true):
   @EnableAspectJAutoProxy(exposeProxy = true)
   // Then in method:
   ((OrderService) AopContext.currentProxy()).processPayment(order);

INTERVIEW TRAP:
#### Q: "Can Spring AOP intercept private methods?"
**A:** NO. Proxies override or implement interface methods. Private methods
are not visible to subclasses (CGLIB) or interfaces (JDK proxy).
If you annotate a private method with @Transactional, it's silently
IGNORED with no error. Use AspectJ for private method interception.

## SECTION 5: SPRING AOP vs ASPECTJ

#### Q6. What is the difference between Spring AOP and AspectJ?

ANSWER:

| FEATURE | SPRING AOP | ASPECTJ |
| --- | --- | --- |
| Weaving Type | Runtime (Proxy-based) | Compile-time or |
| Load-time |  |  |
| JoinPoint Types | Method execution ONLY | Method, field access, |
| constructor, static |  |  |
| initializer, etc. |  |  |
| Private methods | CANNOT intercept | CAN intercept |
| Final classes/methods | CANNOT proxy | CAN advise |
| Performance | Slight overhead (proxy) | Better (bytecode) |
| Setup complexity | Simple (just Spring Beans) | Requires AspectJ |
| compiler or agent |  |  |
| Dependency | spring-boot-starter-aop | aspectjweaver + |
| special build setup |  |  |
| Use case | 90% of enterprise needs | Complex requirements |
| (private, final) |  |  |

SPRING AOP WEAVING FLOW (Runtime):
1. Spring Context starts
2. BeanPostProcessor (AnnotationAwareAspectJAutoProxyCreator) kicks in
3. For each bean, checks if any Advice matches any methods
4. If YES -> creates Proxy (JDK or CGLIB) instead of raw bean
5. Proxy stored in ApplicationContext
6. When method called -> proxy intercepts -> runs advice -> calls real method

ASPECTJ WEAVING TYPES:
- COMPILE-TIME WEAVING: AspectJ compiler (ajc) modifies .class files
during compilation. Most performant.
- POST-COMPILE WEAVING: Weaves aspects into already-compiled .class files
- LOAD-TIME WEAVING (LTW): Java agent intercepts classloading.
Use -javaagent:aspectjweaver.jar

WHEN TO USE ASPECTJ OVER SPRING AOP?
1. Need to intercept private methods
2. Need to advise final classes/methods
3. Extremely high performance requirements
4. Need field-level or constructor-level interception
5. Advising code not managed by Spring (e.g., domain objects)

INTERVIEW TIP:
Spring AOP USES AspectJ ANNOTATIONS (@Aspect, @Before, etc.) but does NOT
use AspectJ runtime. This is a common confusion. Spring AOP borrowed the
annotation syntax from AspectJ but implements advice via proxies, not
bytecode modification.

## SECTION 6: REAL-TIME USE CASES WITH CODE

#### Q7. Implement a Logging Aspect (Real-Time Example)

SCENARIO: Log every service method call with method name, arguments,
return value, and execution time across the entire application.

DEPENDENCY (pom.xml):
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>

IMPLEMENTATION:
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Pointcut: all methods in service package
    @Pointcut("execution(* com.app.service.*.*(..))")
    public void serviceLayerPointcut() {}

    // Pointcut: all methods in controller package
    @Pointcut("execution(* com.app.controller.*.*(..))")
    public void controllerLayerPointcut() {}

    // Combined pointcut
    @Pointcut("serviceLayerPointcut() || controllerLayerPointcut()")
    public void applicationPointcut() {}

    @Around("serviceLayerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args     = joinPoint.getArgs();

        log.info(">>> ENTER [{}.{}] args: {}",
                 className, methodName, Arrays.toString(args));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed  = System.currentTimeMillis() - start;
            log.info("<<< EXIT  [{}.{}] result: {} | time: {}ms",
                     className, methodName, result, elapsed);
            return result;
        } catch (Exception ex) {
            log.error("!!! EXCEPTION [{}.{}] cause: {}",
                      className, methodName, ex.getMessage());
            throw ex;
        }
    }
}

> **OUTPUT (Console):**

>>> ENTER [OrderService.placeOrder] args: [Order{id=1, item='Book'}]
<<< EXIT  [OrderService.placeOrder] result: OrderResponse{status=SUCCESS} | time: 142ms

#### Q8. Performance Monitoring Aspect (Real-Time Example)

SCENARIO: Alert if any service method takes more than 2 seconds.

@Aspect
@Component
@Slf4j
public class PerformanceMonitoringAspect {

    private static final long THRESHOLD_MS = 2000L;

    @Around("execution(* com.app.service.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result  = pjp.proceed();
        long duration  = System.currentTimeMillis() - startTime;

        String method  = pjp.getSignature().toShortString();

        if (duration > THRESHOLD_MS) {
            log.warn("SLOW METHOD DETECTED: {} took {}ms (threshold: {}ms)",
                     method, duration, THRESHOLD_MS);
            alertingService.sendSlowMethodAlert(method, duration);
        } else {
            log.debug("Performance OK: {} = {}ms", method, duration);
        }

        // Optionally record to Micrometer/Prometheus metrics
        meterRegistry.timer("method.execution.time",
            "class",  pjp.getTarget().getClass().getSimpleName(),
            "method", pjp.getSignature().getName())
            .record(duration, TimeUnit.MILLISECONDS);

        return result;
    }
}

#### Q9. Exception Handling Aspect (Real-Time Example)

SCENARIO: Catch all service exceptions, log them with context, send alert
to monitoring system (Slack/PagerDuty), wrap in custom exception.

@Aspect
@Component
@Slf4j
public class ExceptionHandlingAspect {

    @Autowired
    private AlertService alertService;

    @AfterThrowing(
        pointcut = "execution(* com.app.service.*.*(..))",
        throwing  = "ex"
    )
    public void handleServiceException(JoinPoint joinPoint, Exception ex) {
        String method    = joinPoint.getSignature().toShortString();
        Object[] args    = joinPoint.getArgs();
        String className = joinPoint.getTarget().getClass().getName();

        log.error("=== SERVICE EXCEPTION ===");
        log.error("  Method : {}", method);
        log.error("  Args   : {}", Arrays.toString(args));
        log.error("  Type   : {}", ex.getClass().getName());
        log.error("  Message: {}", ex.getMessage());

        // Send to PagerDuty / Slack
        String alert = String.format(
            "Exception in %s.%s: %s", className, method, ex.getMessage());
        alertService.sendCriticalAlert(alert);
    }
}

> **NOTE: @AfterThrowing does NOT stop exception propagation.**

Use @Around if you want to catch + return fallback value instead.

// @Around with exception suppression (fallback pattern)
@Around("execution(* com.app.service.InventoryService.*(..))")
public Object withFallback(ProceedingJoinPoint pjp) throws Throwable {
    try {
        return pjp.proceed();
    } catch (InventoryUnavailableException ex) {
        log.warn("Inventory service unavailable, returning default");
        return InventoryResponse.defaultResponse();  // fallback value
    }
}

#### Q10. Security / Authorization Aspect (Real-Time Example)

SCENARIO: Create custom @Secured annotation and use AOP to enforce role checks.

CUSTOM ANNOTATION:
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {
    String value();  // e.g., "ADMIN", "MANAGER"
}

ASPECT:
@Aspect
@Component
public class SecurityAspect {

    @Autowired
    private UserContextService userContextService;

    @Before("@annotation(requiresRole)")
    public void checkRole(JoinPoint joinPoint, RequiresRole requiresRole) {
        String requiredRole  = requiresRole.value();
        String currentRole   = userContextService.getCurrentUserRole();

        if (!currentRole.equals(requiredRole)) {
            throw new AccessDeniedException(
                "Required role: " + requiredRole +
                ", Current role: " + currentRole);
        }
        log.info("Access granted to {} for role {}",
                 joinPoint.getSignature().getName(), requiredRole);
    }
}

USAGE IN SERVICE:
@Service
public class AdminService {

    @RequiresRole("ADMIN")  // AOP intercepts this method
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @RequiresRole("MANAGER")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

#### Q11. API Request/Response Tracking Aspect

SCENARIO: Track every incoming REST API request (URL, method, user, response time)
        for audit and analytics purposes.

@Aspect
@Component
@Slf4j
public class ApiTrackingAspect {

    @Around("execution(* com.app.controller.*.*(..))")
    public Object trackApiCall(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes)
            RequestContextHolder.currentRequestAttributes()).getRequest();

        String httpMethod  = request.getMethod();
        String requestURI  = request.getRequestURI();
        String clientIP    = request.getRemoteAddr();
        String user        = Optional.ofNullable(request.getHeader("X-User"))
                                     .orElse("anonymous");

        log.info("API REQUEST  -> [{} {}] | User: {} | IP: {}",
                 httpMethod, requestURI, user, clientIP);

        long start         = System.currentTimeMillis();
        Object response    = pjp.proceed();
        long duration      = System.currentTimeMillis() - start;

        log.info("API RESPONSE <- [{} {}] | Time: {}ms | Response: {}",
                 httpMethod, requestURI, duration, response);

        // Store in audit table
        auditService.saveApiAudit(httpMethod, requestURI, user, duration);

        return response;
    }
}

## SECTION 7: COMPLETE MINI REAL-TIME PROJECT EXAMPLE

#### Q12. Complete AOP Project: E-Commerce Order Management System

(Logging + Performance Monitoring + Exception Handling + Security)

PROJECT STRUCTURE:
com.ecommerce/
```text
  ├── annotation/
  │   └── RequiresRole.java
  ├── aspect/
  │   ├── LoggingAspect.java
  │   ├── PerformanceAspect.java
  │   └── SecurityAspect.java
  ├── controller/
  │   └── OrderController.java
  ├── service/
  │   └── OrderService.java
  ├── model/
  │   ├── Order.java
  │   └── OrderResponse.java
  └── EcommerceApplication.java

```
1. CUSTOM ANNOTATION:
package com.ecommerce.annotation;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {
    String value() default "USER";
}

2. ORDER MODEL:
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String product;
    private int quantity;
    private double price;
}

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String status;
    private String message;
}

3. ORDER SERVICE (PURE BUSINESS LOGIC - no cross-cutting concerns):
@Service
@Slf4j
public class OrderService {

    public OrderResponse placeOrder(Order order) {
        log.info("Processing business logic for order: {}", order.getId());
        // Simulate processing
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                "Quantity must be positive: " + order.getQuantity());
        }
        // Business logic only
        return new OrderResponse(order.getId(), "SUCCESS",
                                 "Order placed for " + order.getProduct());
    }

    public OrderResponse cancelOrder(Long orderId) {
        log.info("Cancelling order: {}", orderId);
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        return new OrderResponse(orderId, "CANCELLED", "Order cancelled");
    }

    public List<OrderResponse> getAllOrders() {
        // Simulate DB fetch
        return List.of(
            new OrderResponse(1L, "SUCCESS", "Book"),
            new OrderResponse(2L, "SUCCESS", "Laptop")
        );
    }
}

4. ORDER CONTROLLER:
@RestController
@RequestMapping("/api/v1/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @RequiresRole("USER")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody Order order) {
        OrderResponse response = orderService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{orderId}")
    @RequiresRole("ADMIN")
    public ResponseEntity<OrderResponse> cancelOrder(
                                         @PathVariable Long orderId) {
        OrderResponse response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}

5. LOGGING ASPECT:
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(com.ecommerce.service..*) || " +
              "within(com.ecommerce.controller..*)")
    public void applicationLayer() {}

    @Around("applicationLayer()")
    public Object logExecutionDetails(ProceedingJoinPoint pjp)
                                      throws Throwable {
        String cls    = pjp.getTarget().getClass().getSimpleName();
        String method = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        log.info("[LOG] >>> ENTERING {}.{} | ARGS: {}",
                 cls, method, Arrays.toString(args));

        long startTime = System.nanoTime();
        Object result;
        try {
            result = pjp.proceed();
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;

            log.info("[LOG] <<< EXITING  {}.{} | RESULT: {} | TIME: {}ms",
                     cls, method, result, durationMs);
        } catch (Throwable t) {
            log.error("[LOG] !!! EXCEPTION in {}.{} | CAUSE: {}",
                      cls, method, t.getMessage(), t);
            throw t;
        }
        return result;
    }
}

6. PERFORMANCE MONITORING ASPECT:
@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    private static final long SLOW_METHOD_THRESHOLD_MS = 500;

    @Around("execution(* com.ecommerce.service.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint pjp) throws Throwable {
        long start    = System.currentTimeMillis();
        Object result = pjp.proceed();
        long elapsed  = System.currentTimeMillis() - start;

        String method = pjp.getSignature().toShortString();

        if (elapsed > SLOW_METHOD_THRESHOLD_MS) {
            log.warn("[PERF] SLOW ALERT: {} took {}ms > threshold {}ms",
                     method, elapsed, SLOW_METHOD_THRESHOLD_MS);
        } else {
            log.info("[PERF] {} completed in {}ms", method, elapsed);
        }
        return result;
    }
}

7. SECURITY ASPECT:
@Aspect
@Component
@Slf4j
public class SecurityAspect {

    // In real app, get from SecurityContextHolder / JWT token
    private String getCurrentUserRole() {
        return Optional.ofNullable(RequestContextHolder
            .getRequestAttributes())
            .map(a -> ((ServletRequestAttributes) a).getRequest()
                .getHeader("X-Role"))
            .orElse("USER");
    }

    @Before("@annotation(requiresRole)")
    public void enforceRoleCheck(JoinPoint jp, RequiresRole requiresRole) {
        String required = requiresRole.value();
        String current  = getCurrentUserRole();

        if (!current.equalsIgnoreCase(required) &&
            !current.equalsIgnoreCase("ADMIN")) {
            log.warn("[SECURITY] Access DENIED to {} - Required: {}, Got: {}",
                     jp.getSignature().getName(), required, current);
            throw new AccessDeniedException(
                "Role required: " + required + ", current: " + current);
        }
        log.info("[SECURITY] Access GRANTED to {} for role {}",
                 jp.getSignature().getName(), current);
    }
}

8. MAIN APPLICATION:
@SpringBootApplication
@EnableAspectJAutoProxy   // Enables AOP proxy creation
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }
}

9. OUTPUT (when POST /api/v1/orders is called with Order body):
[SECURITY] Access GRANTED to placeOrder for role USER
[LOG]  >>> ENTERING OrderController.placeOrder | ARGS: [Order{id=1, product='Book', qty=2}]
[LOG]  >>> ENTERING OrderService.placeOrder | ARGS: [Order{id=1, product='Book', qty=2}]
[PERF] OrderService.placeOrder() completed in 12ms
| [LOG]  <<< EXITING  OrderService.placeOrder | RESULT: OrderResponse{status=SUCCESS} | TIME: 12ms |
| --- | --- | --- |
| [LOG]  <<< EXITING  OrderController.placeOrder | RESULT: <201 CREATED OrderResponse{...}> | TIME: 14ms |

HTTP RESPONSE: 201 Created
{
  "orderId": 1,
  "status": "SUCCESS",
  "message": "Order placed for Book"
}

FLOW EXPLANATION:
Request -> [SecurityAspect checks @RequiresRole]
- [LoggingAspect wraps controller method]
- [LoggingAspect wraps service method]
- [PerformanceAspect wraps service method]
- [Actual OrderService.placeOrder() runs]
<- PerformanceAspect logs time
<- LoggingAspect logs service result
<- LoggingAspect logs controller result
Response returned to client

## SECTION 8: ADVANCED INTERVIEW QUESTIONS

#### Q13. How does @Transactional internally use AOP?

ANSWER:
@Transactional is a perfect example of Spring's built-in AOP usage.
It is NOT magic - it is implemented using the exact same proxy mechanism.

INTERNAL FLOW:
1. Spring detects @Transactional on bean or method
2. Creates a proxy around the bean (JDK or CGLIB)
3. Proxy contains TransactionInterceptor (which implements MethodInterceptor)
4. When method called -> TransactionInterceptor.invoke() runs

BEFORE method: TransactionInterceptor calls:
- TransactionManager.getTransaction()  // BEGIN TRANSACTION
- Applies isolation level, propagation, readOnly settings

METHOD EXECUTES (actual business code)

ON SUCCESS:
- transactionManager.commit()  // COMMIT

ON EXCEPTION (matching rollbackFor):
- transactionManager.rollback()  // ROLLBACK

@Service
public class OrderService {
    @Transactional(
        propagation  = Propagation.REQUIRED,
        isolation    = Isolation.READ_COMMITTED,
        rollbackFor  = Exception.class,
        readOnly     = false,
        timeout      = 30
    )
    public void placeOrder(Order order) {
        // All DB operations in this method share ONE transaction
        orderRepository.save(order);
        paymentRepository.save(payment);
        inventoryRepository.deduct(order.getProductId(), order.getQuantity());
    }
}

// Internally equivalent to:
@Around("@annotation(Transactional)")
public Object handleTransaction(ProceedingJoinPoint pjp) throws Throwable {
    TransactionStatus tx = txManager.getTransaction(definition);
    try {
        Object result = pjp.proceed();
        txManager.commit(tx);
        return result;
    } catch (Exception ex) {
        txManager.rollback(tx);
        throw ex;
    }
}

INTERVIEW TRAP - @Transactional self-invocation:
@Service
public class OrderService {
    public void processOrders() {
        this.saveOrder();  // @Transactional on saveOrder() WILL NOT work!
    }

    @Transactional
    public void saveOrder() { ... }  // Proxy bypassed by this.saveOrder()
}

#### Q14. Explain the Order of Execution of Multiple Aspects.

ANSWER:
When multiple aspects apply to the same method, the ORDER matters.
Spring AOP uses the @Order annotation or Ordered interface to control it.

DEFAULT BEHAVIOR (no @Order):
Order is UNDEFINED when multiple aspects apply. Do not rely on it.

WITH @Order:
@Aspect
@Component
@Order(1)  // HIGHEST priority (runs first in Before, last in After)
public class SecurityAspect { ... }

@Aspect
@Component
@Order(2)  // Second
public class LoggingAspect { ... }

@Aspect
@Component
@Order(3)  // LOWEST priority (runs last in Before, first in After)
public class PerformanceAspect { ... }

EXECUTION ORDER with @Order(1) Security, @Order(2) Logging, @Order(3) Perf:
REQUEST:
```text
  @Before Security (Order 1) -> runs first
  @Before Logging  (Order 2) -> runs second
  @Before Perf     (Order 3) -> runs third

```
  [TARGET METHOD EXECUTES]
```text
  @After  Perf     (Order 3) -> runs first after
  @After  Logging  (Order 2) -> runs second after
  @After  Security (Order 1) -> runs last

```
Think of it like NESTED WRAPPERS:
Security {
  Logging {
    Performance {
      TARGET METHOD
    }
  }
}

Lower @Order number = Outermost layer = First Before, Last After

PRACTICAL EXAMPLE:
```text
@Order(1)  SecurityAspect   -> Auth check first (make sense to fail fast)
@Order(2)  LoggingAspect    -> Log after security passes
@Order(3)  AuditAspect      -> Audit after logging
@Order(100) PerformanceAspect -> Performance last (should not block others)

```
Use Ordered.HIGHEST_PRECEDENCE  (-2147483648) and
    Ordered.LOWEST_PRECEDENCE   (+2147483647) constants as anchors.

#### Q15. Proxy-Based AOP vs Compile-Time Weaving - When to choose?

ANSWER:

PROXY-BASED AOP (Spring AOP):
+ Simple setup, works with Spring beans out of the box
+ No special compiler needed
+ Hot-deploy friendly
- Only intercepts Spring-managed beans
- Only method execution join points
- Cannot intercept private/final/static methods
- Self-invocation problem
- Slight runtime performance overhead (proxy wrapping)

COMPILE-TIME WEAVING (AspectJ):
+ Intercepts anything: private, static, final, constructor, field
+ Zero runtime overhead (woven into bytecode)
+ Works on non-Spring objects (domain objects, utilities)
- Needs AspectJ compiler (ajc) in build pipeline
- More complex setup (Maven/Gradle AspectJ plugin)
- Harder to debug (bytecode level)

CHOOSE Spring AOP when:
- Standard enterprise application
- Cross-cutting on Spring beans (service, repository, controller)
- Team is comfortable with Spring
- 90% of real projects fall here

CHOOSE AspectJ when:
- Need private method interception
- Performance is critical (financial, HFT)
- Advising non-Spring managed objects
- Complex pointcut requirements not possible with method execution only

#### Q16. How does Spring create AOP proxies internally? (Spring Internals)

ANSWER:
The key internal class is: AnnotationAwareAspectJAutoProxyCreator
This is a BeanPostProcessor.

STEP-BY-STEP:
1. Application starts, Spring loads all bean definitions

2. For each bean being created, Spring calls
AnnotationAwareAspectJAutoProxyCreator.postProcessAfterInitialization()

3. This BeanPostProcessor checks:
a. Are there any @Aspect beans registered?
b. Do any of their @Pointcut expressions match this bean's methods?

4. If YES:
a. Collect all matching advisors (Advice + Pointcut pairs)
b. Create proxy:
```text
- If bean implements interface -> JDK Dynamic Proxy
- Otherwise -> CGLIB subclass

```
c. Return PROXY instead of original bean

5. The proxy is stored in ApplicationContext
(NOT the original bean)

6. All @Autowired injections get the PROXY

CONFIGURATION TO ENABLE:
@EnableAspectJAutoProxy  // registeres AnnotationAwareAspectJAutoProxyCreator

Spring Boot auto-enables this when spring-boot-starter-aop is on classpath
(via AopAutoConfiguration which uses @ConditionalOnClass(Advice.class))

DEBUGGING PROXY:
@Autowired OrderService orderService;
// orderService.getClass().getName()
// -> com.app.service.OrderService$$EnhancerBySpringCGLIB$$abc123
//    means CGLIB proxy
// -> com.sun.proxy.$Proxy42
//    means JDK Dynamic Proxy

## SECTION 9: BEST PRACTICES & ANTI-PATTERNS

#### Q17. Best Practices when using Spring AOP

ANSWER:

DO'S:
1. DEFINE REUSABLE POINTCUTS:
Create a dedicated class for pointcut definitions. Use them by reference.
Don't repeat pointcut expressions across aspect classes.

2. KEEP ASPECTS FOCUSED:
One aspect = one concern (SRP). LoggingAspect only logs.
Don't mix logging + security + performance in one aspect.

3. USE @Order TO CONTROL EXECUTION:
Always define @Order on aspects when multiple aspects apply
to the same methods. Security should run before Logging.

4. PREFER @Around FOR DUAL-PHASE OPERATIONS:
When you need both "before" and "after" logic (like timing),
use @Around instead of @Before + @After to keep context (start time).

5. USE CUSTOM ANNOTATIONS FOR TARGETED INTERCEPTION:
   @annotation(com.app.annotation.Auditable) is cleaner than broad
   execution() patterns. Makes it explicit what gets intercepted.

6. LOG ENOUGH CONTEXT:
   Always log class name, method name, and args (sanitized).
   Helps in production debugging.

7. HANDLE EXCEPTIONS IN @Around:
   Always re-throw or handle exceptions in @Around.
   Swallowing exceptions silently causes data consistency issues.

DON'T'S (Anti-Patterns):
1. DON'T use AOP for core business logic:
   If logic is part of "what the method does", put it in the method.
   AOP is for concerns that SURROUND business logic.

2. DON'T use overly broad pointcuts:
   execution(* *.*(..)) -> intercepts EVERYTHING including Spring internals.
   Always scope to your own packages.

3. DON'T use AOP for logic that needs GUARANTEED execution:
   If @Around advice throws before proceed(), the method never runs.
   Critical business flows should not depend on AOP for correctness.

4. DON'T create stateful aspects:
   Aspects are singletons by default. Instance variables are SHARED
   across all method calls. Use ThreadLocal if state is needed.

5. DON'T assume ordering without @Order:
   Without explicit ordering, aspect execution order is undefined.
   Always use @Order for predictability.

6. DON'T use AOP to replace proper design:
   If you find yourself advising 30 methods with complex conditional logic,
   reconsider your service layer design.

> **PERFORMANCE CONSIDERATIONS:**

1. Proxy creation overhead: One-time at startup, not at runtime
2. Method invocation overhead: ~microscecond level, negligible
3. Reflection in JoinPoint: Avoid calling joinPoint.getArgs()
unless actually needed (array creation cost)
4. Broad pointcuts: Every method call goes through advice check.
Be specific with pointcut expressions.
5. @Around is most expensive: Has proceed() invocation overhead.
Use @Before/@After when @Around not needed.

WHEN NOT TO USE AOP:
1. Simple helper methods that rarely change
2. Internal utility classes
3. Performance-critical hot paths (tight loops, HFT)
4. When the cross-cutting logic needs access to method-local variables
(AOP cannot access local variables inside methods)
5. When team is not familiar with AOP (maintenance problem)
6. When compile-time analysis is needed (AOP is runtime spring proxy)

CLEAN ARCHITECTURE WITH AOP:

| Layer | Aspect Applied |
| --- | --- |
| Controller | API request logging, response tracking, auth check |
| Service | Transaction, performance monitoring, audit logging |
| Repository | Query logging (via Spring Data), retry on failure |
| Cross-layer | Exception alerting, security enforcement |

## SECTION 10: QUICK-FIRE INTERVIEW Q&A REFERENCE

#### Q: What is a JoinPoint?
**A:** A point in execution where advice can be applied. In Spring AOP, always
a method execution. JoinPoint object provides method name, args, target.

#### Q: What is the difference between JoinPoint and ProceedingJoinPoint?
**A:** JoinPoint: read-only access to method info. Used in @Before, @After.
ProceedingJoinPoint: extends JoinPoint, adds proceed() method.
Used ONLY in @Around to actually invoke the target method.

#### Q: Can we have multiple @Before advices for same method?
**A:** YES. Multiple advices from same or different aspects can apply.
Order is controlled by @Order.

#### Q: What is Weaving?
**A:** Process of applying aspects to target objects. Runtime for Spring AOP
(proxy creation), Compile-time for AspectJ (bytecode modification).

#### Q: Does Spring AOP work without @EnableAspectJAutoProxy?
**A:** In Spring Boot, YES. AopAutoConfiguration enables it automatically
when spring-boot-starter-aop is on classpath.
In plain Spring, you need @EnableAspectJAutoProxy or XML config.

#### Q: Can AOP intercept a method annotated with @Transactional from SAME class?
**A:** NO. Self-invocation problem. The @Transactional advice is also proxy-based.
   this.method() bypasses proxy, so transaction is not applied.

#### Q: What is the "target" in JoinPoint?
**A:** The actual object being proxied (not the proxy).
   joinPoint.getTarget() returns original bean instance.
   joinPoint.getThis()   returns the proxy instance.

#### Q: What happens if @Around advice doesn't call proceed()?
**A:** The target method is NEVER executed. This is intentional design
   (used in rate limiting, circuit breaker, caching to skip method call).

#### Q: Can we apply multiple aspects to same method?
**A:** YES. Aspects are applied in order based on @Order.
   Lower number = higher priority (outermost in @Around chain).

#### Q: Why can't Spring AOP intercept private methods?
**A:** CGLIB creates a subclass - private methods are not accessible to subclass.
   JDK proxy implements interface - private methods not in interface.
   Use AspectJ compile-time weaving for private method interception.

#### Q: What is the difference between @After and @AfterReturning?
**A:** @After runs always (like finally block) - no access to return value.
   @AfterReturning runs only on successful return, CAN access return value
   via 'returning' attribute.

#### Q: Can @AfterThrowing suppress exceptions?
**A:** NO. It only observes exceptions but cannot suppress them.
   Use @Around with try-catch to suppress/replace exceptions.

#### Q: What is proxy-target-class=true in Spring AOP?
**A:** Forces CGLIB proxy even when target implements interface.
   Default: Spring uses JDK proxy if interface present.
   spring.aop.proxy-target-class=true overrides this.

#### Q: How does Spring @Cacheable work internally?
**A:** Same as @Transactional - uses AOP proxy. When @Cacheable method is called:
   CacheInterceptor checks cache for key. If hit: return cached value (skips
   method). If miss: proceed(), store result in cache, return result.

#### Q: What is @DeclareParents in AOP?
**A:** Introduction advice. Adds NEW methods/interfaces to existing beans
   without modifying them. Example: Add Auditable interface to all Services.

COMMON INTERVIEW SCENARIOS:
SCENARIO 1: "Add logging to all REST endpoints without modifying controllers"
ANSWER: Create @Aspect bean with @Around("within(com.app.controller..*)")
This wraps all controller methods automatically.

SCENARIO 2: "Measure DB query execution time at repository layer"
ANSWER: @Around("execution(* com.app.repository.*.*(..))")
Log before and after proceed() with time delta.

SCENARIO 3: "Send Slack alert when any service throws exception"
ANSWER: @AfterThrowing("within(com.app.service..*)", throwing="ex")
Call slackService.alert() in advice body.

SCENARIO 4: "Implement retry logic for payment service"
ANSWER: @Around("execution(* com.app.service.PaymentService.*(..))")
In advice: loop with pjp.proceed() + catch + retry up to N times.

SCENARIO 5: "@Transactional not working - what's the cause?"
ANSWER: Check for 3 things:
1. Self-invocation (this.method() - bypass proxy)
2. Private method (proxy cannot intercept)
3. Exception type not in rollbackFor (default: only RuntimeException)

## END OF AOP COMPREHENSIVE INTERVIEW GUIDE - Appended March 2026

## COMPLETE SPRING / SPRING BOOT ANNOTATIONS REFERENCE GUIDE

Added: 2026-04-22  |  Interview-Ready Cheat Sheet

## LEGEND:

[CORE]    = Spring Core / IoC / DI
[BOOT]    = Spring Boot specific
[MVC]     = Spring MVC / REST
[AOP]     = Aspect-Oriented Programming
[HIB]     = Hibernate ORM (javax.persistence / jakarta.persistence)
[JPA]     = Spring Data JPA
[SEC]     = Spring Security
[TX]      = Transaction Management
[VALID]   = Bean Validation (javax.validation)
[LOMBOK]  = Lombok (widely used alongside Spring Boot)

## SECTION A: SPRING CORE / IoC / DI ANNOTATIONS  [CORE]

1.  @Component
Package : org.springframework.stereotype
Definition : Generic stereotype to mark a class as a Spring-managed bean.
Auto-detected via component scanning and registered in the
ApplicationContext. Root annotation for @Service, @Repository,
             @Controller.
Use : Utility/helper classes not fitting any specific layer role.

2.  @Service
Package : org.springframework.stereotype
Definition : Specialization of @Component. Marks a class as a service-layer
bean holding business logic. Functionally identical to @Component
but adds semantic clarity.
Use : Business logic / use-case classes.

3.  @Repository
Package : org.springframework.stereotype
Definition : Specialization of @Component for the persistence layer.
Additionally activates Spring's PersistenceExceptionTranslation:
vendor-specific exceptions (HibernateException etc.) are
automatically translated into Spring's DataAccessException hierarchy.
Use : DAO classes; Spring Data JPA interfaces inherit this behavior.

4.  @Controller
Package : org.springframework.stereotype
Definition : Specialization of @Component for the presentation layer.
Marks a class as a Spring MVC controller returning view names
(for Thymeleaf / JSP rendering).
Use : Traditional MVC controllers returning HTML views.

5.  @RestController
Package : org.springframework.web.bind.annotation
Definition : Meta-annotation = @Controller + @ResponseBody.
All handler methods automatically serialize return values to
the HTTP response body (JSON/XML) — no per-method @ResponseBody needed.
Use : REST API controllers.

6.  @Autowired
Package : org.springframework.beans.factory.annotation
Definition : Instructs Spring to inject a dependency automatically by type.
Applicable to constructor, setter, or field.
Preferred style: constructor injection for immutability & testability.
Attribute  : required = false  (no NoSuchBeanDefinitionException if bean absent).

7.  @Qualifier
Package : org.springframework.beans.factory.annotation
Definition : Used alongside @Autowired when multiple beans of the same type
exist. Specifies which bean to inject by its name.
Example:
@Autowired
@Qualifier("mysqlDataSource")
private DataSource dataSource;

8.  @Primary
Package : org.springframework.context.annotation
Definition : Marks a bean as the default candidate when multiple beans of
the same type exist. Preferred when no @Qualifier is specified.

9.  @Bean
Package : org.springframework.context.annotation
Definition : Declares a method inside @Configuration as a bean factory.
The method return value is registered in the ApplicationContext.
Spring manages the bean's full lifecycle.

> **Note: In @Configuration classes, Spring intercepts @Bean calls via CGLIB**

to guarantee singleton semantics.

10. @Configuration
Package : org.springframework.context.annotation
Definition : Marks a class as a source of bean definitions (Java-based config).
Spring creates a CGLIB subclass to intercept @Bean methods
and enforce singleton contract.
Replaces : XML applicationContext.xml.

11. @Value
Package : org.springframework.beans.factory.annotation
Definition : Injects a scalar value from application.properties, environment
variables, or SpEL expression.
Examples:
@Value("${app.name}")               // from properties
@Value("${server.port:8080}")        // with default
@Value("#{systemProperties['os']}")  // SpEL

12. @PropertySource
Package : org.springframework.context.annotation
Definition : Adds a properties file to Spring's Environment.
Values accessible via @Value or Environment.getProperty().
Example : @PropertySource("classpath:db.properties")

13. @ComponentScan
Package : org.springframework.context.annotation
Definition : Configures component scanning. Spring scans specified base
packages (and sub-packages) for @Component-annotated classes.

> **Note: Included automatically by @SpringBootApplication.**

14. @Scope
Package : org.springframework.context.annotation
Definition : Defines the lifecycle scope of a Spring bean.
Values:
"singleton"   (default) : one instance per ApplicationContext
"prototype"             : new instance per request
"request"               : one per HTTP request  (web)
"session"               : one per HTTP session   (web)
"application"           : one per ServletContext  (web)

15. @Lazy
Package : org.springframework.context.annotation
Definition : Delays bean initialization until first usage (default is eager).
Use : Expensive beans, break circular dependency, optional deps.

16. @DependsOn
Package : org.springframework.context.annotation
Definition : Forces specified beans to be initialized BEFORE this bean.
Used when ordering matters without a direct dependency link.

17. @PostConstruct
Package : javax.annotation (jakarta.annotation in Jakarta EE 9+)
Definition : Method called AFTER all dependencies are injected.
Runs once at bean initialization. Replaces InitializingBean.
Use : Cache warming, connection pool init, data load.

18. @PreDestroy
Package : javax.annotation
Definition : Method called BEFORE the bean is destroyed (shutdown).
Use : Resource cleanup, connection closing, graceful shutdown logic.

19. @Profile
Package : org.springframework.context.annotation
Definition : Registers a bean only when the specified Spring profile is active.
Activation: spring.profiles.active=dev  in application.properties.
Example:
@Bean @Profile("prod")
public DataSource prodDataSource() { ... }

20. @Conditional
Package : org.springframework.context.annotation
Definition : Registers a bean only when the given Condition.matches() returns true.
Base for all @ConditionalOn* variants used in auto-configuration.

## SECTION B: SPRING BOOT ANNOTATIONS  [BOOT]

21. @SpringBootApplication
Package : org.springframework.boot.autoconfigure
Definition : Meta-annotation bundling three annotations:
```text
  @SpringBootConfiguration  -> marks class as config source
  @EnableAutoConfiguration  -> activates auto-configuration
  @ComponentScan            -> scans from root package

```
Entry point of every Spring Boot application.

22. @EnableAutoConfiguration
Package : org.springframework.boot.autoconfigure
Definition : Triggers Spring Boot's auto-configuration mechanism.
Reads spring.factories / AutoConfiguration.imports and applies
configuration classes when classpath conditions are met
(e.g., auto-configures DataSource if JDBC driver present).

23. @SpringBootConfiguration
Package : org.springframework.boot
Definition : Specialization of @Configuration for Spring Boot.
Signals Spring Boot application-level @Bean definitions.
Only ONE allowed per application.

24. @ConditionalOnClass
Package : org.springframework.boot.autoconfigure.condition
Definition : Loads a bean/configuration ONLY when the specified class is
present on the classpath. Core of auto-config conditional loading.
Example : @ConditionalOnClass(DataSource.class)

25. @ConditionalOnMissingBean
Package : org.springframework.boot.autoconfigure.condition
Definition : Loads a bean ONLY if no bean of the given type already exists.
Enables users to override Spring Boot's default auto-configured beans.

26. @ConditionalOnProperty
Package : org.springframework.boot.autoconfigure.condition
Definition : Loads a bean only when a property matches the required value.
Example : @ConditionalOnProperty(name="feature.enabled", havingValue="true")

27. @ConfigurationProperties
Package : org.springframework.boot.context.properties
Definition : Binds external configuration (application.properties / YAML)
to a Java POJO using a prefix. Provides type-safe config.
Example:
@ConfigurationProperties(prefix = "app.mail")
public class MailProperties {
    private String host;
    private int port;   // binds app.mail.host and app.mail.port
}

28. @EnableConfigurationProperties
Package : org.springframework.boot.context.properties
Definition : Enables @ConfigurationProperties bean registration.
Auto-handled in Spring Boot 2.2+; explicit in older versions.

29. @SpringBootTest
Package : org.springframework.boot.test.context
Definition : Loads the entire ApplicationContext for integration testing.
Can spin up a real HTTP server via webEnvironment attribute.
Values : RANDOM_PORT | DEFINED_PORT | MOCK | NONE

30. @TestPropertySource
Package : org.springframework.test.context
Definition : Overrides application properties for a specific test.
Example : @TestPropertySource(properties="spring.datasource.url=jdbc:h2:mem:test")

## SECTION C: SPRING MVC / REST ANNOTATIONS  [MVC]

31. @RequestMapping
Package : org.springframework.web.bind.annotation
Definition : Maps HTTP requests to handler methods/controllers.
Supports URL path, HTTP method, consumes, and produces.
Parent annotation; @GetMapping etc. are shortcuts.

32. @GetMapping
Definition : Shortcut for @RequestMapping(method=GET).
Use : Fetch / query operations. Safe, idempotent.

33. @PostMapping
Definition : Shortcut for @RequestMapping(method=POST).
Use : Create a new resource. Request body carries the new data.

34. @PutMapping
Definition : Shortcut for @RequestMapping(method=PUT).
Use : Full replacement update of an existing resource.

35. @PatchMapping
Definition : Shortcut for @RequestMapping(method=PATCH).
Use : Partial update of an existing resource.

36. @DeleteMapping
Definition : Shortcut for @RequestMapping(method=DELETE).
Use : Delete a resource.

37. @PathVariable
Package : org.springframework.web.bind.annotation
Definition : Extracts a value embedded in the URI path (e.g., /users/{id})
and binds it to a method parameter.
Example : @GetMapping("/users/{id}") ... getUser(@PathVariable Long id)

38. @RequestParam
Package : org.springframework.web.bind.annotation
Definition : Binds an HTTP query parameter (?key=value) to a method param.
Attributes : name, required (default true), defaultValue.
Example : @GetMapping("/search") ... search(@RequestParam String keyword)

39. @RequestBody
Package : org.springframework.web.bind.annotation
Definition : Deserializes the HTTP request body (JSON/XML) to a Java object
using HttpMessageConverter (Jackson by default).
Use : Typically on POST/PUT methods.

40. @ResponseBody
Package : org.springframework.web.bind.annotation
Definition : Serializes method return value directly to HTTP response body.
@RestController applies this globally; no per-method usage needed.

41. @ResponseStatus
Package : org.springframework.web.bind.annotation
Definition : Sets HTTP status code on a method or exception handler.
Example : @ResponseStatus(HttpStatus.CREATED) on a @PostMapping method.

42. @RequestHeader
Package : org.springframework.web.bind.annotation
Definition : Binds an HTTP request header value to a method parameter.
Example : @RequestHeader("Authorization") String token

43. @CrossOrigin
Package : org.springframework.web.bind.annotation
Definition : Enables CORS (Cross-Origin Resource Sharing) on a controller
or specific handler method. Configures allowed origins, methods, headers.

44. @ExceptionHandler
Package : org.springframework.web.bind.annotation
Definition : Handles specific exceptions thrown from handler methods
in the same controller (or globally in @ControllerAdvice).

45. @ControllerAdvice
Package : org.springframework.web.bind.annotation
Definition : Global handler for @ExceptionHandler, @InitBinder, @ModelAttribute
across ALL controllers. Enables centralized exception handling.

46. @RestControllerAdvice
Package : org.springframework.web.bind.annotation
Definition : Meta-annotation = @ControllerAdvice + @ResponseBody.
Returns exception responses as JSON automatically.
Use : Global REST API exception handlers.

47. @Valid
Package : javax.validation
Definition : Triggers JSR-303/380 bean validation on a method parameter.
Throws MethodArgumentNotValidException on constraint violations.
Cascades to nested objects (@Valid on nested @NotNull fields).

48. @Validated
Package : org.springframework.validation.annotation
Definition : Spring's variant of @Valid. Supports validation groups.
Also enables class-level method argument validation via AOP proxy.

## SECTION D: AOP (ASPECT-ORIENTED PROGRAMMING) ANNOTATIONS  [AOP]

49. @Aspect
Package : org.aspectj.lang.annotation
Definition : Marks a class as an Aspect (container for cross-cutting concerns).
Must also have @Component for Spring to detect it as a bean.
Enables the class to contain @Before, @After, @Around etc.

50. @Before
Package : org.aspectj.lang.annotation
Definition : Advice that executes BEFORE the matched method runs.
Cannot prevent method execution unless it throws an exception.
Example : @Before("execution(* com.app.service.*.*(..))")

51. @After
Package : org.aspectj.lang.annotation
Definition : Advice that executes AFTER the matched method finishes —
whether it returned normally or threw an exception.
Equivalent to a finally block. No access to return value.

52. @AfterReturning
Package : org.aspectj.lang.annotation
Definition : Advice that runs ONLY on successful method return (no exception).
Can access the return value via the 'returning' attribute.
Example : @AfterReturning(pointcut="...", returning="result")

53. @AfterThrowing
Package : org.aspectj.lang.annotation
Definition : Advice that runs ONLY when the matched method throws an exception.
Access the exception via 'throwing' attribute.
CANNOT suppress the exception (use @Around for suppression).
Example : @AfterThrowing(pointcut="...", throwing="ex")

54. @Around
Package : org.aspectj.lang.annotation
Definition : Most powerful advice. Completely wraps the method call.
Must call pjp.proceed() to run the actual method.
Can modify arguments, return value, or catch/suppress exceptions.
Use : Timing, caching, retry, rate-limiting, transaction management.

55. @Pointcut
Package : org.aspectj.lang.annotation
Definition : Declares a reusable, named pointcut expression.
Keeps advice annotations DRY (no repeated expressions).
Example:
@Pointcut("execution(* com.app.service.*.*(..))")
public void serviceLayer() {}

56. @EnableAspectJAutoProxy
Package : org.springframework.context.annotation
Definition : Registers AnnotationAwareAspectJAutoProxyCreator which creates
AOP proxies (JDK or CGLIB) for beans matched by @Aspect pointcuts.
Auto-enabled in Spring Boot when spring-boot-starter-aop is present.

57. @Order
Package : org.springframework.core.annotation
Definition : Controls execution order across multiple aspects for the same join point.
Lower number = higher priority (runs first in @Before, last in @After).
Example : @Order(1) SecurityAspect runs before @Order(2) LoggingAspect.

## SECTION E: HIBERNATE / JPA ANNOTATIONS  [HIB]

Package base: javax.persistence (Java EE)  /  jakarta.persistence (Jakarta EE 9+)
Hibernate-specific: org.hibernate.annotations

58. @Entity
Definition : Marks a Java class as a JPA-managed persistent entity.
Mapped to a database table. Requires a no-arg constructor and @Id.

59. @Table
Definition : Specifies database table name, schema, and unique constraints.
Optional — defaults to the class name if omitted.
Example : @Table(name="orders", schema="public")

60. @Id
Definition : Marks a field as the primary key (PK) of the entity.

61. @GeneratedValue
Definition : Configures automatic PK generation strategy.
Strategies:
GenerationType.IDENTITY  : DB auto-increment column (MySQL, PostgreSQL)  [most common]
GenerationType.SEQUENCE  : DB sequence object (PostgreSQL preferred)
GenerationType.AUTO      : JPA picks based on DB dialect
GenerationType.TABLE     : Uses key-generation table (avoid — poor performance)

62. @Column
Definition : Maps a field to a specific database column. Customizes DDL and DML.
Attributes : name, nullable, unique, length, insertable, updatable, columnDefinition.
Example : @Column(name="email", nullable=false, unique=true, length=100)

63. @Transient
Definition : Excludes a field from persistence. Field exists only in Java,
never stored in or loaded from the database.

64. @OneToOne
Definition : Maps a one-to-one relationship. Each side holds a reference
to exactly one instance of the other entity.
Attributes : cascade, fetch(EAGER default), orphanRemoval, mappedBy.

65. @OneToMany
Definition : Maps one entity owning a collection of another entity.
The FK is on the child (many) side.
Attributes : mappedBy (child field name), cascade, fetch (LAZY default), orphanRemoval.
Example:
@OneToMany(mappedBy="department", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
private List<Employee> employees;

66. @ManyToOne
Definition : Owning side of many-to-one. The FK column is in this entity's table.
Attributes : fetch (EAGER default — change to LAZY!), cascade, optional.
Example:
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="dept_id")
private Department department;

67. @ManyToMany
Definition : Maps a many-to-many relationship. Requires a join table in the DB.
Attributes : mappedBy (non-owning side), cascade, fetch (LAZY default).
Example:
@ManyToMany
@JoinTable(name="student_course",
    joinColumns=@JoinColumn(name="student_id"),
    inverseJoinColumns=@JoinColumn(name="course_id"))
private List<Course> courses;

68. @JoinColumn
Definition : Specifies the FK column for @ManyToOne / @OneToOne associations.
Attributes : name (FK column name), referencedColumnName, nullable.

69. @JoinTable
Definition : Specifies the join/bridge table for @ManyToMany relationships.
Attributes : name, joinColumns, inverseJoinColumns.

70. @Embeddable
Definition : Marks a class as an embeddable value object. Its fields are
mapped into the owning entity's table (no separate table created).
Example : Address class embedded into Customer table.

71. @Embedded
Definition : Used IN the owning entity to embed an @Embeddable class.
Example : @Embedded private Address address;  // Address cols -> Customer table

72. @AttributeOverride / @AttributeOverrides
Definition : Overrides the column name mappings of an @Embeddable class
when the same embeddable is used in multiple places.

73. @Lob
Definition : Maps a field to a Large Object (CLOB for String/char[],
BLOB for byte[]). Used for document content, images, etc.

74. @Enumerated
Definition : Maps a Java Enum to a DB column.
Values:
```text
EnumType.ORDINAL : stores enum ordinal int  → FRAGILE (avoid)
EnumType.STRING  : stores enum name String  → PREFERRED

```
75. @Temporal  (deprecated in JPA 2.2+ in favour of java.time)
Definition : Maps java.util.Date / Calendar to DATE, TIME, or TIMESTAMP.

> **Note: Use LocalDate, LocalDateTime, ZonedDateTime with JSR-310 support instead.**

76. @Version
Package : javax.persistence
Definition : Marks a field as the optimistic lock version counter.
Hibernate auto-increments it on every UPDATE. Concurrent update
conflict throws OptimisticLockException.
Example : @Version private int version;

77. @CreationTimestamp   [Hibernate-specific]
Package : org.hibernate.annotations
Definition : Auto-populates the field with the current timestamp when the
entity is first INSERT-ed. Never updated after that.

78. @UpdateTimestamp   [Hibernate-specific]
Package : org.hibernate.annotations
Definition : Auto-updates the field with the current timestamp on every
UPDATE operation. Combined with @CreationTimestamp for audit fields.

79. @NaturalId   [Hibernate-specific]
Package : org.hibernate.annotations
Definition : Marks a business/natural identifier (e.g., email, username).
Enables efficient lookup via session.byNaturalId() with
second-level cache support.

80. @Cache   [Hibernate-specific]
Package : org.hibernate.annotations
Definition : Enables second-level (L2) cache for the entity.
Example : @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)

81. @BatchSize   [Hibernate-specific]
Package : org.hibernate.annotations
Definition : Optimizes lazy collection loading. Instead of N+1 SELECT queries,
Hibernate fetches in batches of the specified size.
Example : @BatchSize(size=25) on a @OneToMany collection.

82. @NamedQuery
Package : javax.persistence
Definition : Defines a static JPQL query at the entity class level.
Parsed and validated at startup (fail-fast).

## SECTION F: SPRING DATA JPA ANNOTATIONS  [JPA]

83. @Query
Package : org.springframework.data.jpa.repository
Definition : Declares a custom JPQL or native SQL query on a repository method.
Attributes:
```text
value         -> JPQL or SQL string
nativeQuery=true -> treats value as native SQL

```
Example:
@Query("SELECT e FROM Employee e WHERE e.department = :dept")
List<Employee> findByDepartment(@Param("dept") String dept);

84. @Param
Package : org.springframework.data.repository.query
Definition : Binds a method parameter to a named parameter (:name) in @Query.
Required when using named parameters in JPQL/SQL.

85. @Modifying
Package : org.springframework.data.jpa.repository
Definition : Required for @Query methods performing UPDATE or DELETE (DML).
Without it, Spring Data throws InvalidDataAccessApiUsageException.
Must be paired with @Transactional.
Example:
@Modifying
@Transactional
@Query("UPDATE Employee e SET e.salary = :sal WHERE e.id = :id")
int updateSalary(@Param("id") Long id, @Param("sal") double sal);

86. @EnableJpaRepositories
Package : org.springframework.data.jpa.repository.config
Definition : Enables Spring Data JPA repository scanning for specified packages.
Auto-configured in Spring Boot; explicit in plain Spring.

87. @EntityGraph
Package : org.springframework.data.jpa.repository
Definition : Overrides the default fetch plan for a specific query.
Fetches LAZY associations eagerly to avoid N+1 SELECT problem.
Example:
@EntityGraph(attributePaths = {"orders", "orders.items"})
@Query("SELECT c FROM Customer c WHERE c.id = :id")
Optional<Customer> findWithOrdersById(@Param("id") Long id);

88. @Lock
Package : org.springframework.data.jpa.repository
Definition : Applies a JPA lock mode to a repository query method.
Modes:
LockModeType.PESSIMISTIC_WRITE  : SELECT ... FOR UPDATE (row-level DB lock)
LockModeType.OPTIMISTIC         : version-based optimistic locking

## SECTION G: TRANSACTION MANAGEMENT ANNOTATIONS  [TX]

89. @Transactional
Package : org.springframework.transaction.annotation
Definition : Wraps the annotated method in a DB transaction using AOP proxy.
On success -> COMMIT. On RuntimeException -> ROLLBACK.
Key attributes:
propagation  : REQUIRED(default), REQUIRES_NEW, NESTED, SUPPORTS,
NOT_SUPPORTED, MANDATORY, NEVER
isolation    : DEFAULT, READ_UNCOMMITTED, READ_COMMITTED,
REPEATABLE_READ, SERIALIZABLE
rollbackFor  : Exception classes that trigger rollback
(default: RuntimeException and its subclasses)
noRollbackFor: Exceptions that must NOT trigger rollback
readOnly     : true for read queries (optimization hint)
timeout      : seconds before auto-rollback

90. @EnableTransactionManagement
Package : org.springframework.transaction.annotation
Definition : Activates Spring's annotation-driven transaction management.
Auto-enabled by Spring Boot.

## SECTION H: SPRING SECURITY ANNOTATIONS  [SEC]

91. @EnableWebSecurity
Package : org.springframework.security.config.annotation.web.configuration
Definition : Activates Spring Security's web security support on a
@Configuration class. In Spring Boot 3, mostly auto-configured.

92. @PreAuthorize
Package : org.springframework.security.access.prepost
Definition : Method-level security check BEFORE method execution.
Evaluates a SpEL expression against the SecurityContext.
Examples:
  @PreAuthorize("hasRole('ADMIN')")
  @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
  @PreAuthorize("#userId == authentication.principal.id")  // owner check
Requires : @EnableMethodSecurity (Spring Boot 3+ / Spring Security 6+)

93. @PostAuthorize
Package : org.springframework.security.access.prepost
Definition : Method-level security check AFTER method execution.
Evaluates SpEL and can access the return value via returnObject.
Use : Verify returned resource belongs to the current user.
Example : @PostAuthorize("returnObject.ownerId == authentication.principal.id")

94. @Secured
Package : org.springframework.security.access.annotation
Definition : Simpler method-level security; restricts access by role name.
Less powerful than @PreAuthorize — no SpEL support.
Example : @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})

95. @RolesAllowed   (JSR-250 standard)
Package : javax.annotation.security
Definition : Standard Java EE annotation for role-based access.
Similar to @Secured. Requires jsr250Enabled=true in
             @EnableMethodSecurity.
Example : @RolesAllowed("ADMIN")

96. @EnableMethodSecurity  (Spring Security 6 / Spring Boot 3)
Package : org.springframework.security.config.annotation.method.configuration
Definition : Enables @PreAuthorize, @PostAuthorize, @Secured, @RolesAllowed
at class/method level. Replaces deprecated @EnableGlobalMethodSecurity.

97. @AuthenticationPrincipal
Package : org.springframework.security.web.bind.annotation
Definition : Injects the currently authenticated user (UserDetails or custom
principal object) directly into a controller method parameter.
Avoids manual SecurityContextHolder.getContext().getAuthentication().
Example:
@GetMapping("/profile")
public UserDTO getProfile(@AuthenticationPrincipal UserDetails user) {
    return userService.getProfile(user.getUsername());
}

98. @WithMockUser   (spring-security-test)
Package : org.springframework.security.test.context.support
Definition : Populates the SecurityContext with a mock authenticated user
             for unit and integration tests. Avoids real authentication setup.
Example : @WithMockUser(username="teja", roles={"ADMIN"})

## SECTION I: BEAN VALIDATION (JSR-380) ANNOTATIONS  [VALID]

99.  @NotNull
Definition : Field must not be null. Does NOT reject empty strings ("").

100. @NotEmpty
Definition : String / Collection must not be null AND not empty ("" or []).

101. @NotBlank
Definition : String must not be null, empty, or whitespace-only.
Best annotation for validating String input fields.

102. @Size
Definition : Constrains the size of a String (length), Collection, Array, or Map.
Example : @Size(min=2, max=50)

103. @Min / @Max
Definition : Numeric field must be >= min / <= max.

104. @Email
Definition : String must be a well-formed email address.

105. @Pattern
Definition : String must match the specified regular expression.
Example : @Pattern(regexp = "^[A-Z]{2,3}$")

106. @Positive / @PositiveOrZero
Definition : Numeric value must be > 0 / >= 0.

107. @Negative / @NegativeOrZero
Definition : Numeric value must be < 0 / <= 0.

108. @Future / @FutureOrPresent
Definition : Date/time value must be in the future / present or future.

109. @Past / @PastOrPresent
Definition : Date/time value must be in the past / present or past.

## SECTION J: LOMBOK ANNOTATIONS  [LOMBOK]

Package : lombok

> **Note: Lombok annotations generate boilerplate code at compile time via**

annotation processing. Source code stays clean; bytecode has the methods.

110. @Data
Definition : Bundles: @Getter + @Setter + @EqualsAndHashCode +
             @ToString + @RequiredArgsConstructor.
Warning : Avoid @EqualsAndHashCode on JPA entities with bidirectional
          relationships (infinite recursion / hashCode instability).

111. @Getter / @Setter
Definition : Generates getter / setter for all (class-level) or a specific
field (field-level).

112. @NoArgsConstructor
Definition : Generates a no-argument constructor. Required by JPA entities.

113. @AllArgsConstructor
Definition : Generates a constructor with ALL fields as parameters.

114. @RequiredArgsConstructor
Definition : Generates a constructor for all final and @NonNull fields.
Perfect for Spring constructor injection:
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo; // injected by Lombok ctor
}

115. @Builder
Definition : Implements the Builder pattern for clean object construction.
Example : User.builder().name("Teja").email("t@t.com").build();

116. @Slf4j
Definition : Injects a SLF4J Logger named 'log' into the class.
Equivalent to:
private static final Logger log = LoggerFactory.getLogger(Foo.class);

117. @ToString
Definition : Generates toString(). Specific fields can be included/excluded
with @ToString.Include / @ToString.Exclude.

118. @EqualsAndHashCode
Definition : Generates equals() and hashCode() based on specified fields.
callSuper=true : includes superclass fields.

## QUICK INTERVIEW ANNOTATION CHEAT SHEET — ALL CATEGORIES

| CATEGORY | KEY ANNOTATIONS |
| --- | --- |
| Spring Core / DI | @Component, @Service, @Repository, @Controller, @Autowired, |
| @Qualifier, @Primary, @Bean, @Configuration, @Value, @Scope, |  |
| @PostConstruct, @PreDestroy, @Profile, @Lazy, @DependsOn |  |
| Spring Boot | @SpringBootApplication, @ConfigurationProperties, |
| @ConditionalOnClass, @ConditionalOnMissingBean, |  |
| @ConditionalOnProperty, @SpringBootTest |  |
| Spring MVC / REST | @RestController, @RequestMapping, @GetMapping, @PostMapping, |
| @PutMapping, @PatchMapping, @DeleteMapping, @PathVariable, |  |
| @RequestParam, @RequestBody, @ResponseBody, @ResponseStatus, |  |
| @RequestHeader, @CrossOrigin, @ExceptionHandler, |  |
| @ControllerAdvice, @RestControllerAdvice, @Valid, @Validated |  |
| AOP | @Aspect, @Before, @After, @AfterReturning, @AfterThrowing, |
| @Around, @Pointcut, @EnableAspectJAutoProxy, @Order |  |
| Hibernate / JPA | @Entity, @Table, @Id, @GeneratedValue, @Column, @Transient, |
| @OneToOne, @OneToMany, @ManyToOne, @ManyToMany, |  |
| @JoinColumn, @JoinTable, @Embeddable, @Embedded, |  |
| @Lob, @Enumerated, @Version, @CreationTimestamp, |  |
| @UpdateTimestamp, @NaturalId, @Cache, @BatchSize, @NamedQuery |  |
| Spring Data JPA | @Query, @Param, @Modifying, @EntityGraph, @Lock, |
| @EnableJpaRepositories |  |
| Transactions | @Transactional, @EnableTransactionManagement |
| Spring Security | @EnableWebSecurity, @PreAuthorize, @PostAuthorize, |
| @Secured, @RolesAllowed, @EnableMethodSecurity, |  |
| @AuthenticationPrincipal, @WithMockUser |  |
| Bean Validation | @NotNull, @NotBlank, @NotEmpty, @Size, @Email, @Pattern, |
| @Min, @Max, @Positive, @PositiveOrZero, @Future, @Past |  |
| Lombok | @Data, @Builder, @Slf4j, @NoArgsConstructor, |
| @AllArgsConstructor, @RequiredArgsConstructor, |  |
| @Getter, @Setter, @ToString, @EqualsAndHashCode |  |

## END OF ANNOTATIONS REFERENCE GUIDE -- 2026-04-22

