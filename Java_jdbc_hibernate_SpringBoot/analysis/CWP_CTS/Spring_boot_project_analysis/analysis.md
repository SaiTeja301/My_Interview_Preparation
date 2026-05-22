================================================================================
SPRING BOOT ENTERPRISE BACKEND ARCHITECTURE
COMPLETE ANALYSIS FROM BEGINNER TO EXPERT
Interview Preparation Guide

# Based on: ess-odadmin-service Enterprise Project

Spring Boot Version: 3.5.7 | Java Version: 21

## TABLE OF CONTENTS

## PART 1:  Spring Boot Backend Architecture Overview

## PART 2:  Enterprise Spring Boot Project Structure

## PART 3:  Spring Boot Application Startup Internal Working

## PART 4:  Controller Layer Deep Explanation

## PART 5:  Service Layer Deep Explanation

## PART 6:  Repository Layer Deep Explanation

## PART 7:  Entity Layer Deep Explanation

## PART 8:  DTO Layer Deep Explanation

## PART 9:  JWT Authentication Deep Explanation

## PART 10: Spring Security Deep Explanation

## PART 11: Security Filter Internal Working

## PART 12: Authorization Deep Explanation

## PART 13: Exception Handling Deep Explanation

## PART 14: Configuration Layer Deep Explanation

## PART 15: Database Integration Deep Explanation

## PART 16: Complete End-to-End Enterprise Request Flow

## PART 17: Dependency Injection Internal Working

## PART 18: Spring Boot Internal Working

## PART 19: Performance Optimization

## PART 20: Enterprise Best Practices

## PART 21: Interview Questions and Answers

## PART 1: SPRING BOOT BACKEND ARCHITECTURE OVERVIEW

1.1 WHAT IS SPRING FRAMEWORK

## DEFINITION:

Spring Framework is a comprehensive, open-source application framework for Java
that provides infrastructure support for developing enterprise Java applications.

KEY CONCEPTS:
- Inversion of Control (IoC): Framework controls object creation and lifecycle
- Dependency Injection (DI): Objects receive dependencies from external sources
- Aspect-Oriented Programming (AOP): Separation of cross-cutting concerns
- Modular Architecture: Use only what you need

INTERNAL WORKING:
1. Spring IoC Container reads configuration (XML/Annotations/Java Config)
2. Creates ApplicationContext (bean container)
3. Instantiates beans based on configuration
4. Manages bean lifecycle and dependencies
5. Provides beans when requested

INTERVIEW EXPLANATION:
"Spring Framework is a lightweight container that manages the lifecycle of Java
objects (beans) and their dependencies. It uses IoC/DI to achieve loose coupling
between components, making applications easier to test, maintain, and scale."

1.2 WHAT IS SPRING BOOT

## DEFINITION:

Spring Boot is an opinionated framework built on top of Spring Framework that
simplifies configuration and deployment of Spring applications.

KEY FEATURES:
- Auto-configuration: Automatically configures beans based on classpath
- Starter Dependencies: Pre-configured dependency sets
- Embedded Servers: Tomcat, Jetty, Undertow built-in
- Production-ready: Actuator, metrics, health checks
- No XML configuration required

YOUR PROJECT EXAMPLE:
@SpringBootApplication
public class EssOdadminServiceApplication {
```java
    public static void main(String[] args) {
        SpringApplication.run(EssOdadminServiceApplication.class, args);
    }
}
```

INTERNAL WORKING:
1. @SpringBootApplication triggers auto-configuration
2. Scans classpath for libraries (JPA, Security, Web, etc.)
3. Creates default beans for detected libraries
4. Starts embedded Tomcat server
5. Application ready to receive requests

INTERVIEW EXPLANATION:
"Spring Boot eliminates boilerplate configuration through convention over
configuration. It auto-configures beans based on classpath dependencies,
provides embedded servers, and offers production-ready features out of the box."

1.3 WHY SPRING BOOT IN ENTERPRISE APPLICATIONS

## ENTERPRISE ADVANTAGES:

1. Rapid Development: Less configuration, faster time-to-market
2. Microservices Ready: Easy to create standalone services
3. Cloud Native: Works seamlessly with Kubernetes, Docker
4. Security: Built-in Spring Security integration
5. Scalability: Stateless design supports horizontal scaling
6. Monitoring: Actuator provides health checks, metrics
7. Testing: Comprehensive testing support

YOUR PROJECT USES:
- spring-boot-starter-web: REST API development
- spring-boot-starter-data-jpa: Database operations
- spring-boot-starter-security: Authentication/Authorization
- spring-boot-starter-actuator: Production monitoring
- nimbus-jose-jwt: JWT token handling

1.4 SPRING BOOT ARCHITECTURE OVERVIEW

## LAYERED ARCHITECTURE:

```text
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT (Browser/Mobile/API)              │
└───────────────────────────────┬─────────────────────────────────┘
                                │ HTTP Request
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                     SECURITY FILTER CHAIN                        │
│                   (UserFilter, CorsFilter)                       │
└───────────────────────────────┬─────────────────────────────────┘
                                │ Authenticated Request
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    CONTROLLER LAYER                              │
│    (AgencyAcctRoutingController, R2BContractAgentController)    │
│         @RestController - Handles HTTP requests                  │
└───────────────────────────────┬─────────────────────────────────┘
                                │ DTO Objects
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                     SERVICE LAYER                                │
│   (AgencyAcctRoutingServiceImpl, R2BContractAgentServiceImpl)   │
│        @Service - Business Logic & Transaction Management        │
└───────────────────────────────┬─────────────────────────────────┘
                                │ Entity Objects
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    REPOSITORY LAYER                              │
│     (AgencyAcctRoutingRepo, ApplicationUsersRepo)               │
│       @Repository - Database Operations via JPA                  │
└───────────────────────────────┬─────────────────────────────────┘
                                │ SQL Queries
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                       DATABASE                                   │
│                    (Oracle Database)                             │
└─────────────────────────────────────────────────────────────────┘
```

1.5 MONOLITHIC VS MICROSERVICES ARCHITECTURE

## MONOLITHIC ARCHITECTURE:

- Single deployable unit
- All modules in one codebase
- Shared database
- Simpler deployment
- Scaling entire application

MICROSERVICES ARCHITECTURE:
- Multiple independent services
- Each service has own database
- Services communicate via REST/messaging
- Independent deployment and scaling
- Your project (ess-odadmin-service) is a microservice

YOUR PROJECT AS MICROSERVICE:
- Independent deployable service
- Has own database connection (Oracle)
- Exposes REST APIs
- Can be scaled independently
- Part of larger ESS system

1.6 COMPLETE RUNTIME FLOW

## DETAILED REQUEST FLOW:

1. CLIENT REQUEST
```text
   └─► HTTP POST /agency-acct-routings
       Headers: X-User-Id: "john123"
       Body: {"agntNbr": "12345", "entpsId": "E001", "userId": "john123"}
```

2. CORS FILTER (CorsFilter.java)
```text
   └─► Sets CORS headers
   └─► Allows cross-origin requests
```

3. SECURITY FILTER (UserFilter.java)
```text
   └─► Extracts X-User-Id header
   └─► Validates user exists in database
   └─► Creates SecurityContext
   └─► Sets authentication token
```

4. DISPATCHER SERVLET
```text
   └─► Routes request to appropriate controller
   └─► Calls AgencyAcctRoutingController
```

5. CONTROLLER (AgencyAcctRoutingController.java)
```text
   └─► @PostMapping receives request
   └─► Deserializes JSON to AgencyAcctRoutingDTO
   └─► Calls service layer
```

6. SERVICE (AgencyAcctRoutingServiceImpl.java)
```text
   └─► Validates business rules
   └─► Checks if user exists
   └─► Checks for duplicates
   └─► Converts DTO to Entity
   └─► Calls repository
```

7. REPOSITORY (AgencyAcctRoutingRepo.java)
```text
   └─► JpaRepository handles database operations
   └─► Hibernate generates SQL
   └─► Executes INSERT statement
```

8. DATABASE (Oracle)
```text
   └─► Stores data
   └─► Returns generated ID
```

9. RESPONSE FLOW (Reverse)
```text
   └─► Repository returns Entity
   └─► Service converts Entity to DTO
   └─► Controller returns ResponseEntity
   └─► Jackson serializes to JSON
   └─► HTTP 201 Created sent to client
```

## PART 2: ENTERPRISE SPRING BOOT PROJECT STRUCTURE

YOUR PROJECT STRUCTURE:

## src/main/java/com/nw/odAdmin_service/

```text
├── Controller/              # REST API endpoints
│   ├── AgencyAcctRoutingController.java
│   ├── GlobalExceptionHandler.java
│   ├── PremiumAuditRoutingController.java
│   ├── R2BContractAgentController.java
│   └── WinsValidationController.java
│
├── service/                 # Service interfaces
│   ├── AgencyAcctRoutingService.java
│   ├── PremiumAuditRoutingService.java
│   ├── R2BContractAgentService.java
│   ├── UserService.java
│   └── WinsValidationService.java
│
├── ServiceImpl/             # Service implementations
│   ├── AgencyAcctRoutingServiceImpl.java
│   ├── PremiumAuditRoutingServiceImpl.java
│   ├── R2BContractAgentServiceImpl.java
│   └── WinsValidationServiceImpl.java
│
├── Repositories/            # Data access layer
│   ├── AgencyAcctRoutingRepo.java
│   ├── ApplicationUsersRepo.java
│   ├── PremiumAuditRoutingRepo.java
│   ├── R2BContractAgentRepo.java
│   ├── UsersRepo.java
│   └── WinsValidationRepo.java
│
├── Entities/                # JPA entities
│   ├── AgencyAcctRouting.java
│   ├── ApplicationUsers.java
│   ├── PremiumAuditRouting.java
│   ├── R2BContractAgent.java
│   └── WinsValidation.java
│
├── model/                   # DTOs
│   ├── AgencyAcctRoutingDTO.java
│   ├── ApplicationUsersDTO.java
│   ├── ErrorResponseDTO.java
│   └── R2BContractAgentDTO.java
│
├── Configuration/           # Bean configurations
│   ├── CorsFilter.java
│   ├── ModelMapperConfig.java
│   └── SecurityConfig.java
│
├── filter/                  # Security filters
│   └── UserFilter.java
│
├── exception/               # Custom exceptions
│   ├── AgencyAcctRoutingException.java
│   ├── AlreadyExistException.java
│   ├── NotFoundException.java
│   └── UnauthorizedException.java
│
├── constants/               # Application constants
│   └── ExceptionConstants.java
│
└── EssOdadminServiceApplication.java  # Main class
```

src/main/resources/
```text
├── application.properties       # Common configuration
├── application-dev.properties   # Development environment
├── application-prod.properties  # Production environment
└── application-pt.properties    # Performance testing
```

FOLDER PURPOSE EXPLANATION:

## 1. Controller/ - Presentation Layer

PURPOSE: Handles HTTP requests, validates input, returns responses
REAL-TIME USAGE: Exposes REST endpoints for frontend/other services

2. service/ - Business Logic Interface
PURPOSE: Defines contracts for business operations
REAL-TIME USAGE: Allows multiple implementations, enables testing

3. ServiceImpl/ - Business Logic Implementation
PURPOSE: Contains actual business logic
REAL-TIME USAGE: Transaction management, data transformation, validation

4. Repositories/ - Data Access Layer
PURPOSE: Database operations using Spring Data JPA
REAL-TIME USAGE: CRUD operations, custom queries

5. Entities/ - Domain Model
PURPOSE: Maps Java objects to database tables
REAL-TIME USAGE: ORM mapping with Hibernate

6. model/ - Data Transfer Objects
PURPOSE: Transfer data between layers
REAL-TIME USAGE: API request/response, hide entity details

7. Configuration/ - Bean Configurations
PURPOSE: Define beans, security, CORS settings
REAL-TIME USAGE: Application configuration

8. filter/ - Security Filters
PURPOSE: Request/response interception
REAL-TIME USAGE: Authentication, logging, validation

9. exception/ - Exception Classes
PURPOSE: Custom exception handling
REAL-TIME USAGE: Business-specific error handling

10. constants/ - Application Constants
PURPOSE: Centralized constant values
REAL-TIME USAGE: Error messages, config values

## PART 3: SPRING BOOT APPLICATION STARTUP INTERNAL WORKING

3.1 MAIN METHOD ANALYSIS

## YOUR CODE:

@SpringBootApplication
public class EssOdadminServiceApplication {
```java
    public static void main(String[] args) {
        SpringApplication.run(EssOdadminServiceApplication.class, args);
    }
}
```

INTERNAL WORKING STEP-BY-STEP:

STEP 1: JVM Starts
```text
└─► main() method is entry point
└─► Java Virtual Machine loads class
```

STEP 2: SpringApplication.run() executes
```text
└─► Creates SpringApplication instance
└─► Detects application type (SERVLET, REACTIVE, NONE)
└─► Your app: SERVLET (web application)
```

STEP 3: Environment Preparation
```text
└─► Loads application.properties
└─► Loads environment-specific properties (dev, prod)
└─► Processes environment variables
```

STEP 4: ApplicationContext Creation
```text
└─► Creates AnnotationConfigServletWebServerApplicationContext
└─► This is the IoC container
```

STEP 5: Bean Definition Loading
```text
└─► Scans packages for @Component, @Service, @Repository, @Controller
└─► Registers bean definitions (not instances yet)
```

STEP 6: Auto-configuration
```text
└─► Detects classpath dependencies
└─► Creates default beans for:
    - DataSource (Oracle connection)
    - EntityManagerFactory (Hibernate)
    - SecurityFilterChain
    - DispatcherServlet
```

STEP 7: Bean Instantiation
```text
└─► Creates singleton beans
└─► Injects dependencies
└─► Calls @PostConstruct methods
```

STEP 8: Embedded Server Start
```text
└─► Creates embedded Tomcat
└─► Deploys DispatcherServlet
└─► Opens port 8080
```

STEP 9: Application Ready
```text
└─► Publishes ApplicationReadyEvent
└─► Application ready to receive requests
```

3.2 @SPRINGBOOTAPPLICATION INTERNAL WORKING

## @SpringBootApplication is a combination of THREE annotations:

@SpringBootConfiguration
```text
├── Equivalent to @Configuration
└── Marks class as source of bean definitions
```

@EnableAutoConfiguration
```text
├── Enables Spring Boot auto-configuration
├── Reads META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
└── Creates beans for detected libraries
```

@ComponentScan
```text
├── Scans current package and sub-packages
├── Detects @Component, @Service, @Repository, @Controller
└── In your case: scans com.nw.odAdmin_service and below
```

3.3 COMPONENT SCANNING PROCESS

## SCANNING ORDER:

1. Base package: com.nw.odAdmin_service
2. Sub-packages scanned:
- com.nw.odAdmin_service.Controller
- com.nw.odAdmin_service.service
- com.nw.odAdmin_service.ServiceImpl
- com.nw.odAdmin_service.Repositories
- com.nw.odAdmin_service.Configuration
- com.nw.odAdmin_service.filter

COMPONENTS FOUND IN YOUR PROJECT:
- @RestController: AgencyAcctRoutingController, R2BContractAgentController
- @Service: AgencyAcctRoutingServiceImpl, UserService
- @Repository: AgencyAcctRoutingRepo, ApplicationUsersRepo
- @Configuration: SecurityConfig, ModelMapperConfig
- @Component: UserFilter, CorsFilter

3.4 BEAN CREATION LIFECYCLE

## BEAN LIFECYCLE PHASES:

1. INSTANTIATION
```text
   └─► Constructor called
   └─► Object created in memory
```

2. POPULATE PROPERTIES
```text
   └─► @Autowired dependencies injected
   └─► Field injection, setter injection, constructor injection
```

3. BEAN NAME AWARE
```text
   └─► setBeanName() called if implements BeanNameAware
```

4. BEAN FACTORY AWARE
```text
   └─► setBeanFactory() called if implements BeanFactoryAware
```

5. APPLICATION CONTEXT AWARE
```text
   └─► setApplicationContext() called if implements ApplicationContextAware
```

6. PRE-INITIALIZATION
```text
   └─► @PostConstruct methods called
   └─► BeanPostProcessor.postProcessBeforeInitialization()
```

7. INITIALIZATION
```text
   └─► afterPropertiesSet() if implements InitializingBean
   └─► Custom init method if specified
```

8. POST-INITIALIZATION
```text
   └─► BeanPostProcessor.postProcessAfterInitialization()
```

9. BEAN READY
```text
   └─► Bean available for use
```

10. DESTRUCTION (on shutdown)
```text
    └─► @PreDestroy called
    └─► destroy() if implements DisposableBean
```

## PART 4: CONTROLLER LAYER DEEP EXPLANATION

4.1 @RESTCONTROLLER VS @CONTROLLER

## @RestController = @Controller + @ResponseBody

@Controller:
- Returns view names (for MVC/Thymeleaf)
- Needs @ResponseBody for REST

@RestController:
- Returns data directly as JSON/XML
- @ResponseBody applied automatically
- Used for REST APIs

YOUR PROJECT EXAMPLE:
@RestController
@RequestMapping("/agency-acct-routings")
public class AgencyAcctRoutingController {
```text
    // All methods return JSON directly
}
```

4.2 REQUEST MAPPING ANNOTATIONS

## @RequestMapping - Base mapping for class/method

@GetMapping    - HTTP GET (read data)
@PostMapping   - HTTP POST (create data)
@PutMapping    - HTTP PUT (update data)
@DeleteMapping - HTTP DELETE (remove data)

YOUR PROJECT MAPPINGS:

AgencyAcctRoutingController:
```text
├── POST   /agency-acct-routings          → saveAgencyAcctRouting()
├── PUT    /agency-acct-routings          → updateAgencyAcctRouting()
├── GET    /agency-acct-routings/search   → retrieveAgencyAcctRouting()
├── GET    /agency-acct-routings          → retrieveAllAgencyAcctRouting()
└── DELETE /agency-acct-routings/{id}     → deleteAllAgencyAcctRoutingDetails()
```

4.3 CONTROLLER METHOD ANALYSIS

## EXAMPLE - YOUR saveAgencyAcctRouting METHOD:

@PostMapping
public ResponseEntity<AgencyAcctRoutingDTO> saveAgencyAcctRouting(
```text
    @RequestBody AgencyAcctRoutingDTO agencyAcctRoutingDTO)
    throws AgencyAcctRoutingException {

    AgencyAcctRoutingDTO result = agencyAcctRoutingService
        .saveAgentAcctRouting(agencyAcctRoutingDTO);

    if(result.getStatus().equals("NOT_FOUND"))
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    else if(result.getStatus().equals("CREATED"))
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    else
        return new ResponseEntity<>(result, HttpStatus.FOUND);
}
```

ANNOTATION BREAKDOWN:

@PostMapping
```text
├── Maps to HTTP POST method
├── URL: /agency-acct-routings (from class-level @RequestMapping)
└── Typically used for creating new resources
```

@RequestBody
```text
├── Deserializes JSON request body to Java object
├── Uses Jackson ObjectMapper internally
└── Content-Type: application/json expected
```

ResponseEntity<T>
```text
├── Wraps response body and HTTP status
├── Provides fine-grained control over response
└── Can set headers, status codes
```

4.4 PARAMETER ANNOTATIONS

## @PathVariable - Extract value from URL path

Example: @DeleteMapping("/{id}")
```sql
public void delete(@PathVariable Long id)
URL: /agency-acct-routings/123 → id = 123
```

@RequestParam - Extract query parameters
Example: @GetMapping("/search")
```java
public List<DTO> search(@RequestParam String agntNbr)
URL: /search?agntNbr=12345 → agntNbr = "12345"
```

YOUR EXAMPLE:
@GetMapping("/search")
public ResponseEntity<List<AgencyAcctRoutingDTO>> retrieveAgencyAcctRouting(
```text
@RequestParam(value="agntNbr", required=false) String agntNbr,
@RequestParam(value="entpsId", required=false) String entpsId,
@RequestParam(value="userId", required=false) String userId)
```

4.5 REQUEST LIFECYCLE INTERNALLY

## HTTP Request → Controller Flow:

1. HTTP Request Received
```text
   └─► Tomcat receives HTTP request
```

2. Filter Chain Execution
```text
   └─► CorsFilter → UserFilter → DispatcherServlet
```

3. DispatcherServlet
```text
   └─► Central controller in Spring MVC
   └─► Delegates to HandlerMapping
```

4. HandlerMapping
```text
   └─► Finds matching @RequestMapping
   └─► Returns HandlerExecutionChain
```

5. HandlerAdapter
```text
   └─► Invokes controller method
   └─► Handles parameter resolution
```

6. Argument Resolution
```text
   └─► @RequestBody: RequestResponseBodyMethodProcessor
   └─► @PathVariable: PathVariableMethodArgumentResolver
   └─► @RequestParam: RequestParamMethodArgumentResolver
```

7. Method Execution
```text
   └─► Controller method invoked
   └─► Business logic executed
```

8. Return Value Handling
```text
   └─► ResponseEntity processed
   └─► JSON serialization via Jackson
```

9. Response Sent
```text
   └─► HTTP response returned to client
```

## PART 5: SERVICE LAYER DEEP EXPLANATION

5.1 SERVICE LAYER ROLE

## PURPOSE:

- Contains business logic
- Transaction management
- Data transformation (DTO ↔ Entity)
- Orchestrates multiple repositories
- Validates business rules

WHY INTERFACE + IMPLEMENTATION?
- Loose coupling
- Multiple implementations possible
- Easier unit testing with mocks
- Follows SOLID principles (DIP)

5.2 @SERVICE ANNOTATION INTERNAL WORKING

## @Service:

```text
├── Specialization of @Component
├── Semantically indicates business service
├── No difference from @Component at runtime
└── Helps with readability and organization
```

INTERNAL WORKING:
1. Component scan finds @Service class
2. Creates bean definition with name (lowercase first letter)
3. Instantiates as singleton by default
4. Injects dependencies via @Autowired

5.3 YOUR SERVICE INTERFACE

## public interface AgencyAcctRoutingService {

AgencyAcctRoutingDTO saveAgentAcctRouting(AgencyAcctRoutingDTO dto)
```text
        throws AgencyAcctRoutingException;
    List<AgencyAcctRoutingDTO> retrieveAgencyAcctRouting(
        String agntNbr, String entpsId, String userId)
        throws AgencyAcctRoutingException;
    List<AgencyAcctRoutingDTO> retrieveAllAgencyAcctRouting();
    void deleteAllAgencyAcctRoutingDetails(List<Long> ids);
}
```

PURPOSE:
- Defines contract for service operations
- Controller depends on interface, not implementation
- Enables dependency injection of implementation

5.4 YOUR SERVICE IMPLEMENTATION ANALYSIS

## @Service

public class AgencyAcctRoutingServiceImpl implements AgencyAcctRoutingService {

```java
    @Autowired
    private AgencyAcctRoutingRepo agencyAcctRoutingRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private ModelMapper mapper;

    @Override
    public AgencyAcctRoutingDTO saveAgentAcctRouting(
        AgencyAcctRoutingDTO agencyAcctRoutingDTO) throws AgencyAcctRoutingException {

        // BUSINESS RULE 1: Validate user exists
        boolean existsUserId = usersRepo.existsByUserId(
            agencyAcctRoutingDTO.getUserId());

        if (existsUserId) {
            // BUSINESS RULE 2: Check for duplicates
            List<AgencyAcctRouting> list = agencyAcctRoutingRepo
                .findByAgntNbrAndEntpsId(
                    agencyAcctRoutingDTO.getAgntNbr(),
                    agencyAcctRoutingDTO.getEntpsId());

            if (list != null && !list.isEmpty()) {
                agencyAcctRoutingDTO.setStatus("EXISTS");
                agencyAcctRoutingDTO.setMessage("Already Exists");
                return agencyAcctRoutingDTO;
            }

            // CONVERSION: DTO → Entity
            AgencyAcctRouting entity = mapper.map(
                agencyAcctRoutingDTO, AgencyAcctRouting.class);

            // DATABASE OPERATION
            AgencyAcctRouting saved = agencyAcctRoutingRepo.save(entity);

            // CONVERSION: Entity → DTO
            AgencyAcctRoutingDTO response = mapper.map(
                saved, AgencyAcctRoutingDTO.class);
            response.setStatus("CREATED");
            return response;
        } else {
            agencyAcctRoutingDTO.setStatus("NOT_FOUND");
            agencyAcctRoutingDTO.setMessage("User not found");
            return agencyAcctRoutingDTO;
        }
    }
}
```

5.5 TRANSACTION MANAGEMENT

## @Transactional annotation manages database transactions.

YOUR EXAMPLE:
@Override
@Transactional
public void deleteAllAgencyAcctRoutingDetails(List<Long> ids) {
```text
    for(Long id : ids) {
        agencyAcctRoutingRepo.deleteById(id);
    }
}
```

INTERNAL WORKING:
1. Spring creates proxy around method
2. Before method: BEGIN TRANSACTION
3. Method executes
4. If success: COMMIT
5. If exception: ROLLBACK

TRANSACTION ATTRIBUTES:
- propagation: How to handle existing transactions
- isolation: Read isolation level
- rollbackFor: Exceptions that trigger rollback
- readOnly: Optimization hint for read-only operations

## PART 6: REPOSITORY LAYER DEEP EXPLANATION

6.1 WHAT IS REPOSITORY LAYER

## PURPOSE:

- Abstracts database operations
- Provides CRUD operations
- Custom query methods
- Integrates with ORM (Hibernate)

SPRING DATA JPA:
- Generates implementation at runtime
- Method names become queries
- Reduces boilerplate code significantly

6.2 YOUR REPOSITORY EXAMPLE

## public interface AgencyAcctRoutingRepo

extends JpaRepository<AgencyAcctRouting, Long> {

```sql
    // Spring Data JPA generates queries from method names
    List<AgencyAcctRouting> findByAgntNbrAndEntpsIdAndUserId(
        String agntNbr, String entpsId, String userId);
    List<AgencyAcctRouting> findByAgntNbrAndEntpsId(
        String agntNbr, String entpsId);
    List<AgencyAcctRouting> findByAgntNbr(String agntNbr);
    boolean existsByUserId(String userId);

    // Custom JPQL queries
    @Query("SELECT a FROM AgencyAcctRouting a WHERE a.agntNbr LIKE :agntNbr")
    List<AgencyAcctRouting> findByAgntNbrLike(@Param("agntNbr") String agntNbr);
}
```

6.3 JPAREPOSITORY INTERNAL WORKING

## JpaRepository<Entity, ID> provides:

CRUD METHODS:
```text
├── save(entity)           → INSERT/UPDATE
├── findById(id)           → SELECT by ID
├── findAll()              → SELECT all
├── deleteById(id)         → DELETE by ID
├── count()                → COUNT(*)
└── existsById(id)         → EXISTS check
```

INTERNAL WORKING:
1. Spring creates proxy implementation at startup
2. Method invocation intercepted by proxy
3. Query generated from method name
4. EntityManager executes query
5. Results mapped to entities

6.4 QUERY METHOD NAMING CONVENTION

## Method Name → Generated Query:

findByAgntNbr(String agntNbr)
```text
└─► SELECT a FROM AgencyAcctRouting a WHERE a.agntNbr = ?1
```

findByAgntNbrAndEntpsId(String agntNbr, String entpsId)
```text
└─► SELECT a FROM AgencyAcctRouting a
    WHERE a.agntNbr = ?1 AND a.entpsId = ?2
```

existsByUserId(String userId)
```text
└─► SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
    FROM AgencyAcctRouting a WHERE a.userId = ?1
```

KEYWORDS:
- findBy: Query starter
- And/Or: Logical operators
- Like: Pattern matching
- OrderBy: Sorting
- Between: Range queries
- IsNull/IsNotNull: Null checks

6.5 @QUERY CUSTOM QUERIES

## When method naming isn't enough, use @Query:

@Query("SELECT a FROM AgencyAcctRouting a WHERE a.agntNbr LIKE :agntNbr")
List<AgencyAcctRouting> findByAgntNbrLike(@Param("agntNbr") String agntNbr);

TYPES:
- JPQL: Object-oriented query language
- Native SQL: Database-specific SQL with nativeQuery=true

## PART 7: ENTITY LAYER DEEP EXPLANATION

7.1 WHAT IS @ENTITY

## PURPOSE:

- Maps Java class to database table
- Each instance represents a table row
- Fields map to columns

YOUR ENTITY EXAMPLE:
@Entity
@Table(name = "AGENCY_ACCT_ROUTING")
public class AgencyAcctRouting implements Serializable {

```java
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "agency_acct_routing_seq")
    @SequenceGenerator(
        name = "agency_acct_routing_seq",
        sequenceName = "SEQ_AGENCY_ACCT_ROUTING",
        allocationSize = 1)
    private Long id;

    private String agntNbr;
    private String entpsId;
    private String userId;

    // Getters and Setters
}
```

7.2 JPA ANNOTATIONS EXPLAINED

## @Entity

```text
├── Marks class as JPA entity
├── Required for ORM mapping
└── Must have no-arg constructor
```

@Table(name = "AGENCY_ACCT_ROUTING")
```text
├── Specifies table name
├── Optional if class name matches table
└── Can specify schema, catalog
```

@Id
```text
├── Marks primary key field
├── Required for every entity
└── Must be unique
```

@GeneratedValue
```text
├── Auto-generates primary key
├── Strategies: AUTO, IDENTITY, SEQUENCE, TABLE
└── SEQUENCE used with Oracle (your case)
```

@SequenceGenerator
```text
├── Defines database sequence
├── allocationSize: IDs to pre-allocate
└── Used with Oracle for performance
```

@Column
```text
├── Maps field to column
├── Optional if names match
└── Can specify name, length, nullable
```

7.3 HIBERNATE ORM MAPPING FLOW

## SAVE OPERATION FLOW:

1. Entity created in Java
AgencyAcctRouting entity = new AgencyAcctRouting();

2. EntityManager.persist(entity) called
```text
   └─► Hibernate manages entity
```

3. Transaction commit triggers flush
```text
   └─► Dirty checking performed
```

4. SQL INSERT generated
INSERT INTO AGENCY_ACCT_ROUTING (AGNT_NBR, ENTPS_ID, USER_ID)
VALUES (?, ?, ?)

5. Primary key assigned from sequence
SELECT SEQ_AGENCY_ACCT_ROUTING.NEXTVAL FROM DUAL

6. Entity synchronized with database

FIND OPERATION FLOW:

1. findById(1L) called

2. Hibernate checks first-level cache
```text
   └─► Return if found in session
```

3. If not cached, generate SELECT
SELECT * FROM AGENCY_ACCT_ROUTING WHERE ID = ?

4. ResultSet mapped to entity

5. Entity added to session cache

6. Entity returned

## PART 8: DTO LAYER DEEP EXPLANATION

8.1 WHAT IS DTO

## DTO = Data Transfer Object

PURPOSE:
- Transfer data between layers
- Decouple API from internal model
- Control data exposure
- Add transient properties (status, message)

8.2 ENTITY VS DTO COMPARISON

## ENTITY (AgencyAcctRouting):

```text
├── Maps to database table
├── Contains JPA annotations
├── Managed by Hibernate
├── Should not expose to API directly
└── Contains database-specific logic
```

DTO (AgencyAcctRoutingDTO):
```text
├── Plain Java object (POJO)
├── No JPA annotations
├── Used for API request/response
├── Contains UI-specific fields
└── Can aggregate multiple entities
```

YOUR DTO EXAMPLE:
public class AgencyAcctRoutingDTO {
```java
    private Long id;
    private String agntNbr;
    private String entpsId;
    private String userId;

    // Additional fields NOT in Entity
    private String status;   // "CREATED", "EXISTS", "NOT_FOUND"
    private String message;  // Human-readable message
}
```

8.3 WHY DTO IS USED

## 1. SECURITY: Hide internal entity structure

2. FLEXIBILITY: Different DTOs for different use cases
3. VERSIONING: Change DTO without changing entity
4. PERFORMANCE: Return only needed fields
5. VALIDATION: Add validation annotations
6. AGGREGATION: Combine data from multiple entities

8.4 DATA TRANSFER FLOW

## REQUEST FLOW:

Client → JSON → Controller → DTO → Service → Entity → Repository → Database

RESPONSE FLOW:
Database → Repository → Entity → Service → DTO → Controller → JSON → Client

8.5 MODELMAPPER FOR CONVERSION

## YOUR CONFIGURATION:

@Configuration
public class ModelMapperConfig {
```java
    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
}
```

USAGE IN SERVICE:
// DTO to Entity
AgencyAcctRouting entity = mapper.map(dto, AgencyAcctRouting.class);

// Entity to DTO
AgencyAcctRoutingDTO dto = mapper.map(entity, AgencyAcctRoutingDTO.class);

// List conversion
List<AgencyAcctRoutingDTO> dtoList = mapper.map(
entityList,
```text
    new TypeToken<List<AgencyAcctRoutingDTO>>(){}.getType()
);
```

## PART 9: JWT AUTHENTICATION DEEP EXPLANATION

9.1 WHAT IS JWT

## JWT = JSON Web Token

PURPOSE:
- Stateless authentication
- Self-contained token with user info
- Digitally signed for integrity
- Used in microservices architecture

9.2 JWT STRUCTURE

## JWT consists of three parts separated by dots:

xxxxx.yyyyy.zzzzz
HEADER.PAYLOAD.SIGNATURE

HEADER:
{
"alg": "RS256",    // Signing algorithm
"typ": "JWT"       // Token type
}

PAYLOAD (Claims):
{
"sub": "john123",           // Subject (user ID)
"name": "John Doe",         // Custom claims
"roles": ["USER", "ADMIN"],
"iat": 1609459200,          // Issued at
"exp": 1609545600           // Expiration
}

SIGNATURE:
HMACSHA256(
base64UrlEncode(header) + "." + base64UrlEncode(payload),
secret
)

9.3 JWT AUTHENTICATION FLOW

## YOUR PROJECT USES HEADER-BASED AUTHENTICATION:

1. CLIENT LOGIN REQUEST
```text
   └─► POST /login
   └─► Credentials: username, password
```

2. BACKEND VALIDATES CREDENTIALS
```text
   └─► Check against database
   └─► Generate JWT token
```

3. TOKEN RETURNED TO CLIENT
```text
   └─► Response: { "token": "eyJhbG..." }
```

4. CLIENT STORES TOKEN
```text
   └─► Local storage / Session storage
```

5. CLIENT SENDS TOKEN IN HEADER
```text
   └─► Header: X-User-Id: john123
   └─► (Your project uses simplified header-based auth)
```

6. BACKEND VALIDATES TOKEN
```text
   └─► UserFilter extracts X-User-Id
   └─► Validates user exists in database
   └─► Creates SecurityContext
```

9.4 YOUR PROJECT'S AUTHENTICATION

## Your project uses simplified header-based authentication:

// UserFilter.java
String userId = request.getHeader("X-User-Id");

if(userId != null) {
ApplicationUsers user = userService.getUserById(userId);

```text
    if(user != null) {
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
        throw new UnauthorizedException("Unauthorized");
    }
}
```

This is a trust-based authentication where:
- Client sends user ID in header
- Backend trusts the header value
- Suitable for internal services behind API gateway

## PART 10: SPRING SECURITY DEEP EXPLANATION

10.1 WHAT IS SPRING SECURITY

## PURPOSE:

- Authentication: Verify identity
- Authorization: Control access
- Protection against attacks (CSRF, XSS, etc.)

CORE COMPONENTS:
- SecurityFilterChain: Chain of security filters
- AuthenticationManager: Coordinates authentication
- UserDetailsService: Loads user data
- PasswordEncoder: Encodes/verifies passwords

10.2 YOUR SECURITY CONFIGURATION

## @Configuration

@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            // STATELESS: No server-side session
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // CSRF DISABLED: Not needed for REST APIs
            .csrf(AbstractHttpConfigurer::disable)

            // ALL REQUESTS REQUIRE AUTHENTICATION
            .authorizeHttpRequests(auth ->
                auth.anyRequest().authenticated())

            // SECURITY CONTEXT REPOSITORY
            .securityContext(request ->
                request.securityContextRepository(securityContextRepository()))

            // CUSTOM FILTER BEFORE USERNAME/PASSWORD FILTER
            .addFilterBefore(usertFilter(),
                UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public UserFilter usertFilter() {
        return new UserFilter();
    }
}
```

10.3 SECURITY FILTER CHAIN

## Filter execution order in your project:

1. CorsFilter (Ordered.HIGHEST_PRECEDENCE)
```text
   └─► Sets CORS headers
```

2. UserFilter (before UsernamePasswordAuthenticationFilter)
```text
   └─► Extracts X-User-Id header
   └─► Validates user
   └─► Sets authentication
```

3. SecurityContextPersistenceFilter
```text
   └─► Manages security context
```

4. ExceptionTranslationFilter
```text
   └─► Handles security exceptions
```

5. FilterSecurityInterceptor
```text
   └─► Checks authorization
```

10.4 AUTHENTICATION VS AUTHORIZATION

## AUTHENTICATION (Who are you?):

- Verify user identity
- Check credentials
- Issue token/session

YOUR PROJECT AUTHENTICATION:
// UserFilter authenticates via X-User-Id header
String userId = request.getHeader("X-User-Id");
ApplicationUsers user = userService.getUserById(userId);

AUTHORIZATION (What can you do?):
- Check permissions
- Role-based access
- Resource-level access

YOUR PROJECT AUTHORIZATION:
// All requests require authentication
.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())

## PART 11: SECURITY FILTER INTERNAL WORKING

11.1 ONCEPERREQUESTFILTER

## PURPOSE:

- Guarantees filter runs once per request
- Handles forwarded/included requests
- Base class for security filters

YOUR USERFILTER:
@Component
public class UserFilter extends OncePerRequestFilter {

```java
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        // Filter logic here
    }
}
```

11.2 FILTER EXECUTION FLOW

## COMPLETE FLOW IN YOUR PROJECT:

REQUEST ARRIVES
```text
      │
      ▼
┌─────────────────────────────┐
│       CorsFilter            │
│  ─────────────────────────  │
│  1. Check if OPTIONS        │
│  2. Set CORS headers        │
│  3. Continue chain          │
└─────────────────────────────┘
      │
      ▼
┌─────────────────────────────┐
│       UserFilter            │
│  ─────────────────────────  │
│  1. Check URL               │
│  2. Extract X-User-Id       │
│  3. Validate user           │
│  4. Set authentication      │
│  5. Continue chain          │
└─────────────────────────────┘
      │
      ▼
┌─────────────────────────────┐
│    DispatcherServlet        │
│  ─────────────────────────  │
│  1. Route to controller     │
│  2. Execute business logic  │
│  3. Return response         │
└─────────────────────────────┘
```

11.3 YOUR USERFILTER ANALYSIS

## @Override

protected void doFilterInternal(HttpServletRequest request,
HttpServletResponse response, FilterChain filterChain)
```text
    throws ServletException, IOException {

    String url = request.getRequestURL().toString();

    // UNPROTECTED ENDPOINTS
    if (url.contains("/actuator/health") || url.contains("/favicon.ico")) {
        // Allow without authentication
        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken(null, null, null);
        setDetails(request, response, auth);
        filterChain.doFilter(request, response);
    } else {
        // PROTECTED ENDPOINTS
        String userId = request.getHeader("X-User-Id");

        try {
            if(userId != null) {
                ApplicationUsers user = userService.getUserById(userId);

                if(user != null) {
                    // CREATE AUTHENTICATION TOKEN
                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                            user, null, null);

                    // SET SECURITY CONTEXT
                    setDetails(request, response, auth);
                }
                else {
                    throw new UnauthorizedException("Unauthorized");
                }
            }
            else {
                throw new UnauthorizedException("User not found");
            }

            // CONTINUE TO NEXT FILTER/CONTROLLER
            filterChain.doFilter(request, response);

        } catch (UnauthorizedException e) {
            // HANDLE EXCEPTION
            resolver.resolveException(request, response, null, e);
        }
    }
}
```

11.4 SETTING SECURITY CONTEXT

## private void setDetails(HttpServletRequest request, HttpServletResponse response,

UsernamePasswordAuthenticationToken auth) {

```text
    // Add request details to authentication
    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    // Get or create SecurityContext
    SecurityContext sc = SecurityContextHolder.getContext();

    // Set authentication in context
    sc.setAuthentication(auth);

    // Create session and save context
    HttpSession session = request.getSession(true);
    new DelegatingSecurityContextRepository(
        new RequestAttributeSecurityContextRepository(),
        new HttpSessionSecurityContextRepository()
    ).saveContext(sc, request, response);
}
```

INTERNAL WORKING:
1. WebAuthenticationDetailsSource extracts IP, session ID
2. SecurityContextHolder is ThreadLocal storage
3. SecurityContext holds authentication
4. Context persisted in request attribute and session

## PART 12: AUTHORIZATION DEEP EXPLANATION

12.1 ROLE-BASED AUTHORIZATION

## SPRING SECURITY ROLES:

- Roles are authorities prefixed with "ROLE_"
- Stored in Authentication.getAuthorities()
- Checked via hasRole(), hasAuthority()

YOUR PROJECT:
Currently uses simple authenticated/unauthenticated model.
All authenticated users have same access.

12.2 @PREAUTHORIZE ANNOTATION

## Enables method-level security:

@EnableMethodSecurity  // In SecurityConfig

// In Controller
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public ResponseEntity<String> delete(@PathVariable Long id) {
```sql
    // Only ADMIN can delete
}
```

@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@GetMapping
public List<DTO> getAll() {
```text
    // USER and ADMIN can read
}
```

@PreAuthorize("#userId == authentication.principal.userId")
@GetMapping("/user/{userId}")
public DTO getByUser(@PathVariable String userId) {
```text
    // User can only access their own data
}
```

12.3 URL-BASED AUTHORIZATION

## In SecurityConfig:

.authorizeHttpRequests(auth -> auth
.requestMatchers("/actuator/health").permitAll()
.requestMatchers("/admin/**").hasRole("ADMIN")
.requestMatchers("/api/**").authenticated()
.anyRequest().denyAll()
)

## PART 13: EXCEPTION HANDLING DEEP EXPLANATION

13.1 YOUR EXCEPTION HANDLING ARCHITECTURE

## YOUR PROJECT STRUCTURE:

exception/
```text
├── AgencyAcctRoutingException.java   # Business exception
├── AlreadyExistException.java        # Duplicate resource
├── NotFoundException.java            # Resource not found
├── UnauthorizedException.java        # Authentication failed
└── ...
```

Controller/
```text
└── GlobalExceptionHandler.java       # Central handler
```

13.2 CUSTOM EXCEPTION CLASSES

## // Base business exception

public class AgencyAcctRoutingException extends Exception {
```java
    private String message;

    public AgencyAcctRoutingException(String message) {
        this.message = message;
    }
}
```

// Not found exception
public class NotFoundException extends Exception {
```java
    private String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
```

// Unauthorized exception
public class UnauthorizedException extends Exception {
```java
    private String message;

    public UnauthorizedException(String message) {
        this.message = message;
    }
}
```

13.3 @CONTROLLERADVICE AND @EXCEPTIONHANDLER

## @RestControllerAdvice  // Combines @ControllerAdvice + @ResponseBody

public class GlobalExceptionHandler {

```java
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlreadyExists(
        AlreadyExistException exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
            LocalDateTime.now(),
            exception.getMessage(),
            "value is Already Exist"
        );

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
        NotFoundException exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
            LocalDateTime.now(),
            exception.getMessage(),
            "value not found"
        );

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception exception) {

        ErrorResponseDTO error = new ErrorResponseDTO(
            LocalDateTime.now(),
            exception.getMessage(),
            "Internal server error"
        );

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(error);
    }
}
```

13.4 ERROR RESPONSE DTO

## public class ErrorResponseDTO {

```java
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorResponseDTO(LocalDateTime timestamp,
                           String message,
                           String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
    // Getters and setters
}
```

13.5 EXCEPTION HANDLING FLOW

## WHEN EXCEPTION OCCURS:

1. Exception thrown in service/controller
```text
throw new NotFoundException("User not found");
```

2. Spring looks for @ExceptionHandler
```text
   └─► First checks controller-level handlers
   └─► Then checks @ControllerAdvice classes
```

3. Matching handler invoked
```text
   └─► handleNotFound() method called
```

4. Handler creates response
```text
   └─► ErrorResponseDTO created
```

5. Response returned to client
```text
   └─► HTTP 404 with JSON error body
```

## PART 14: CONFIGURATION LAYER DEEP EXPLANATION

14.1 @CONFIGURATION ANNOTATION

## PURPOSE:

- Marks class as source of bean definitions
- Equivalent to XML configuration
- Processed during component scan

INTERNAL WORKING:
1. Spring creates CGLIB proxy of @Configuration class
2. @Bean methods intercepted
3. Singleton beans returned from cache
4. Ensures single instance per bean name

14.2 @BEAN ANNOTATION

## PURPOSE:

- Declares method return value as Spring bean
- Method name becomes bean name
- Can specify name, initMethod, destroyMethod

YOUR EXAMPLES:

@Configuration
public class ModelMapperConfig {

```java
    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
}
```

@Configuration
public class SecurityConfig {

```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
        throws Exception {
        // Configuration here
        return http.build();
    }

    @Bean
    public UserFilter usertFilter() {
        return new UserFilter();
    }
}
```

14.3 BEAN CREATION INTERNAL WORKING

## BEAN CREATION PROCESS:

1. CONFIGURATION CLASS LOADED
```text
   └─► ModelMapperConfig.class loaded
```

2. @BEAN METHODS IDENTIFIED
```text
   └─► mapper() method found
```

3. BEAN DEFINITION REGISTERED
```text
   └─► Name: "mapper"
   └─► Type: ModelMapper.class
   └─► Scope: singleton (default)
```

4. DEPENDENCIES RESOLVED
```text
   └─► Check if bean has dependencies
   └─► Create dependencies first
```

5. BEAN INSTANTIATED
```text
   └─► mapper() method called
   └─► new ModelMapper() returned
```

6. BEAN STORED IN CONTAINER
```text
   └─► Singleton stored in ApplicationContext
   └─► Available for injection
```

## PART 15: DATABASE INTEGRATION DEEP EXPLANATION

15.1 YOUR DATABASE CONFIGURATION

## # Oracle Database Configuration

spring.datasource.url=${OMNIVIEW_DB_URL}
spring.datasource.username=${OMNIVIEW_DB_USR}
spring.datasource.password=${SECRET_OMNIVIEW_DB_SEC}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.show-sql=false

15.2 HIBERNATE INTERNAL WORKING

## HIBERNATE ARCHITECTURE:

```text
┌────────────────────────────────────────────────────────────┐
│                  APPLICATION CODE                          │
│              (Service, Repository)                         │
└──────────────────────────┬─────────────────────────────────┘
                           │
┌──────────────────────────▼─────────────────────────────────┐
│                  ENTITY MANAGER                            │
│  - persist(), find(), merge(), remove()                    │
│  - Manages entity lifecycle                                │
└──────────────────────────┬─────────────────────────────────┘
                           │
┌──────────────────────────▼─────────────────────────────────┐
│                  SESSION (First-Level Cache)               │
│  - Persistence Context                                     │
│  - Entity state tracking                                   │
└──────────────────────────┬─────────────────────────────────┘
                           │
┌──────────────────────────▼─────────────────────────────────┐
│                  TRANSACTION                               │
│  - BEGIN, COMMIT, ROLLBACK                                 │
│  - @Transactional management                               │
└──────────────────────────┬─────────────────────────────────┘
                           │
┌──────────────────────────▼─────────────────────────────────┐
│                  JDBC / DataSource                         │
│  - Connection Pool (HikariCP)                              │
│  - SQL execution                                           │
└──────────────────────────┬─────────────────────────────────┘
                           │
┌──────────────────────────▼─────────────────────────────────┐
│                  DATABASE (Oracle)                         │
└────────────────────────────────────────────────────────────┘
```

15.3 JPA ENTITY LIFECYCLE

## ENTITY STATES:

1. TRANSIENT (New)
```text
   └─► Just created with 'new'
   └─► Not associated with persistence context
   └─► No database row
```

2. MANAGED (Persistent)
```text
   └─► Associated with persistence context
   └─► Changes tracked (dirty checking)
   └─► Synchronized with database on flush/commit
```

3. DETACHED
```text
   └─► Was managed, now disconnected
   └─► Changes not tracked
   └─► Can be re-attached with merge()
```

4. REMOVED
```text
   └─► Scheduled for deletion
   └─► Will be deleted on flush/commit
```

15.4 TRANSACTION LIFECYCLE

## @Transactional METHOD EXECUTION:

1. BEFORE METHOD
```text
   └─► TransactionInterceptor intercepts call
   └─► getTransaction() creates new transaction
   └─► Connection obtained from pool
```

2. METHOD EXECUTES
```text
   └─► Business logic runs
   └─► EntityManager operations
   └─► SQL queued
```

3. AFTER METHOD (SUCCESS)
```text
   └─► flush() - Write changes to database
   └─► commit() - Commit transaction
   └─► Connection returned to pool
```

4. AFTER METHOD (EXCEPTION)
```text
   └─► rollback() - Undo changes
   └─► Connection returned to pool
```

## PART 16: COMPLETE END-TO-END ENTERPRISE REQUEST FLOW

COMPLETE FLOW DIAGRAM:

```text
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLIENT                                          │
│     POST /agency-acct-routings                                              │
│     Headers: X-User-Id: john123                                             │
│     Body: {"agntNbr":"12345", "entpsId":"E001", "userId":"john123"}        │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 1. HTTP Request
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         EMBEDDED TOMCAT                                      │
│     - Accepts TCP connection on port 8080                                    │
│     - Parses HTTP request                                                    │
│     - Creates HttpServletRequest/Response                                    │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 2. Servlet Request
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         CORS FILTER                                          │
│     - Sets Access-Control-Allow-Origin: *                                    │
│     - Sets Access-Control-Allow-Methods                                      │
│     - Sets Access-Control-Allow-Headers                                      │
│     - If OPTIONS: respond 200 OK                                            │
│     - Else: continue chain                                                   │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 3. CORS Validated
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         USER FILTER                                          │
│     - Extract X-User-Id header: "john123"                                    │
│     - Call userService.getUserById("john123")                                │
│     - Query: SELECT * FROM OV_APPLICATION_USERS WHERE USER_ID = 'john123'   │
│     - If user found: Create UsernamePasswordAuthenticationToken              │
│     - Set SecurityContext with authentication                                │
│     - If not found: throw UnauthorizedException                              │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 4. Authenticated
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                      DISPATCHER SERVLET                                      │
│     - Receives authenticated request                                         │
│     - HandlerMapping finds: AgencyAcctRoutingController.saveAgencyAcctRouting│
│     - HandlerAdapter prepares to invoke                                      │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 5. Route to Controller
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                ARGUMENT RESOLVER                                             │
│     - @RequestBody: Jackson deserializes JSON to AgencyAcctRoutingDTO        │
│     - Creates: AgencyAcctRoutingDTO{agntNbr="12345", entpsId="E001"...}     │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 6. DTO Created
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│           AGENCY ACCT ROUTING CONTROLLER                                     │
│     @PostMapping                                                             │
│     saveAgencyAcctRouting(AgencyAcctRoutingDTO dto) {                       │
│         return agencyAcctRoutingService.saveAgentAcctRouting(dto);          │
│     }                                                                        │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 7. Call Service
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│        AGENCY ACCT ROUTING SERVICE IMPL                                      │
│                                                                              │
│     // Step 1: Validate user exists                                          │
│     boolean exists = usersRepo.existsByUserId("john123");                    │
│     └─► Query: SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END              │
│                FROM USERS WHERE USER_ID = 'john123'                          │
│                                                                              │
│     // Step 2: Check for duplicates                                          │
│     List<Entity> list = repo.findByAgntNbrAndEntpsId("12345", "E001");      │
│     └─► Query: SELECT * FROM AGENCY_ACCT_ROUTING                            │
│                WHERE AGNT_NBR = '12345' AND ENTPS_ID = 'E001'               │
│                                                                              │
│     // Step 3: Convert DTO to Entity                                         │
│     AgencyAcctRouting entity = mapper.map(dto, AgencyAcctRouting.class);    │
│                                                                              │
│     // Step 4: Save entity                                                   │
│     AgencyAcctRouting saved = repo.save(entity);                            │
│     └─► Query: SELECT SEQ_AGENCY_ACCT_ROUTING.NEXTVAL FROM DUAL             │
│     └─► Query: INSERT INTO AGENCY_ACCT_ROUTING (ID, AGNT_NBR, ENTPS_ID...)  │
│                VALUES (1, '12345', 'E001', 'john123')                        │
│                                                                              │
│     // Step 5: Convert Entity to DTO                                         │
│     AgencyAcctRoutingDTO response = mapper.map(saved, DTO.class);           │
│     response.setStatus("CREATED");                                           │
│     return response;                                                         │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 8. Return DTO
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│           CONTROLLER (continued)                                             │
│     AgencyAcctRoutingDTO result = service.saveAgentAcctRouting(dto);        │
│     if(result.getStatus().equals("CREATED"))                                 │
│         return new ResponseEntity<>(result, HttpStatus.CREATED);             │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 9. ResponseEntity
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│               RETURN VALUE HANDLER                                           │
│     - HttpEntityMethodProcessor handles ResponseEntity                       │
│     - Sets HTTP status: 201 Created                                          │
│     - Jackson serializes DTO to JSON                                         │
│     - Sets Content-Type: application/json                                    │
└───────────────────────────────────────┬─────────────────────────────────────┘
                                        │ 10. JSON Response
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLIENT                                          │
│     HTTP/1.1 201 Created                                                     │
│     Content-Type: application/json                                           │
│                                                                              │
│     {                                                                        │
│         "id": 1,                                                             │
│         "agntNbr": "12345",                                                  │
│         "entpsId": "E001",                                                   │
│         "userId": "john123",                                                 │
│         "status": "CREATED",                                                 │
│         "message": "AgencyAcctRouting number is Created"                     │
│     }                                                                        │
└─────────────────────────────────────────────────────────────────────────────┘
```

## PART 17: DEPENDENCY INJECTION INTERNAL WORKING

17.1 WHAT IS DEPENDENCY INJECTION

## DEFINITION:

Dependency Injection (DI) is a design pattern where objects receive their
dependencies from external sources rather than creating them internally.

WITHOUT DI:
public class AgencyAcctRoutingServiceImpl {
```java
    // Tight coupling - creates own dependency
    private AgencyAcctRoutingRepo repo = new AgencyAcctRoutingRepo();
}
```

WITH DI:
@Service
public class AgencyAcctRoutingServiceImpl {
```java
    // Loose coupling - receives dependency
    @Autowired
    private AgencyAcctRoutingRepo repo;
}
```

17.2 IOC CONTAINER

## INVERSION OF CONTROL:

- Traditional: Application controls object creation
- IoC: Container controls object creation

SPRING IOC CONTAINER:
- BeanFactory: Basic container
- ApplicationContext: Advanced container with more features

YOUR PROJECT'S APPLICATIONCONTEXT:
- Type: AnnotationConfigServletWebServerApplicationContext
- Created by: SpringApplication.run()
- Contains all beans

17.3 TYPES OF DEPENDENCY INJECTION

## 1. CONSTRUCTOR INJECTION (Recommended):

@Service
public class AgencyAcctRoutingServiceImpl {

```java
    private final AgencyAcctRoutingRepo repo;
    private final ModelMapper mapper;

    // Spring injects dependencies via constructor
    public AgencyAcctRoutingServiceImpl(
        AgencyAcctRoutingRepo repo,
        ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }
}
```

2. FIELD INJECTION:
@Service
public class AgencyAcctRoutingServiceImpl {

```java
    @Autowired
    private AgencyAcctRoutingRepo repo;

    @Autowired
    private ModelMapper mapper;
}
```

3. SETTER INJECTION:
@Service
public class AgencyAcctRoutingServiceImpl {

```java
    private AgencyAcctRoutingRepo repo;

    @Autowired
    public void setRepo(AgencyAcctRoutingRepo repo) {
        this.repo = repo;
    }
}
```

17.4 BEAN LIFECYCLE

## BEAN LIFECYCLE PHASES:

1. INSTANTIATION
```text
   └─► Spring creates bean instance
```

2. POPULATE PROPERTIES
```text
   └─► @Autowired fields injected
```

3. BEAN NAME AWARE
```text
   └─► setBeanName() if implements BeanNameAware
```

4. BEAN FACTORY AWARE
```text
   └─► setBeanFactory() if implements BeanFactoryAware
```

5. PRE-INITIALIZATION
```text
   └─► BeanPostProcessor.postProcessBeforeInitialization()
```

6. INITIALIZATION
```text
   └─► @PostConstruct method
   └─► afterPropertiesSet() if implements InitializingBean
```

7. POST-INITIALIZATION
```text
   └─► BeanPostProcessor.postProcessAfterInitialization()
```

8. BEAN READY FOR USE

9. DESTRUCTION (on shutdown)
```text
   └─► @PreDestroy method
   └─► destroy() if implements DisposableBean
```

17.5 BEAN SCOPES

## SINGLETON (Default):

- One instance per ApplicationContext
- Shared across all requests
- Used for stateless services

PROTOTYPE:
- New instance every time requested
- Used for stateful beans

REQUEST:
- New instance per HTTP request
- Used for request-scoped data

SESSION:
- New instance per HTTP session
- Used for user session data

APPLICATION:
- One instance per ServletContext
- Similar to singleton for web apps

## PART 18: SPRING BOOT INTERNAL WORKING

18.1 AUTO-CONFIGURATION INTERNAL WORKING

## HOW AUTO-CONFIGURATION WORKS:

1. @EnableAutoConfiguration triggers auto-config

2. Spring Boot reads:
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

3. For each auto-configuration class:
- Check @Conditional annotations
- If conditions met, create beans

YOUR PROJECT AUTO-CONFIGURATIONS:

DataSourceAutoConfiguration:
```text
├── @ConditionalOnClass(DataSource.class) ✓
├── Creates HikariDataSource bean
└── Configures connection pool
```

JpaRepositoriesAutoConfiguration:
```text
├── @ConditionalOnClass(JpaRepository.class) ✓
├── Creates EntityManagerFactory
└── Enables repository proxies
```

SecurityAutoConfiguration:
```text
├── @ConditionalOnClass(WebSecurityConfigurerAdapter.class) ✓
├── Creates default security
└── Overridden by your SecurityConfig
```

18.2 DISPATCHERSERVLET INTERNAL WORKING

## DISPATCHERSERVLET FLOW:

1. doDispatch() method receives request

2. getHandler() - Find handler
```text
   └─► RequestMappingHandlerMapping matches URL to controller method
   └─► Returns HandlerExecutionChain
```

3. getHandlerAdapter() - Get adapter
```text
   └─► RequestMappingHandlerAdapter handles @RequestMapping
```

4. applyPreHandle() - Run interceptors
```text
   └─► Before controller execution
```

5. handle() - Invoke controller
```text
   └─► Argument resolution
   └─► Method invocation
   └─► Return value handling
```

6. applyPostHandle() - Post processing
```text
   └─► After controller, before view
```

7. processDispatchResult() - Render response
```text
   └─► Handle ModelAndView or ResponseEntity
```

18.3 REQUEST MAPPING FLOW

## URL TO METHOD MAPPING:

1. HTTP Request: POST /agency-acct-routings

2. RequestMappingHandlerMapping:
```text
   └─► Scans all @Controller classes
   └─► Builds mapping: URL pattern → Handler method
```

3. Mapping lookup:
```text
   └─► Pattern: /agency-acct-routings
   └─► HTTP Method: POST
   └─► Match: AgencyAcctRoutingController.saveAgencyAcctRouting()
```

4. HandlerExecutionChain:
```text
   └─► Handler: controller method
   └─► Interceptors: security, logging, etc.
```

5. Method invocation:
```text
   └─► Resolve arguments
   └─► Call method
   └─► Process return value
```

## PART 19: PERFORMANCE OPTIMIZATION

19.1 CACHING

## SPRING CACHE ANNOTATIONS:

@Cacheable("users")
public ApplicationUsers getUserById(String userId) {
```text
    // Result cached with key = userId
    return applicationUsersRepo.findByUserId(userId).orElse(null);
}
```

@CacheEvict(value = "users", key = "#userId")
public void updateUser(String userId, UserDTO dto) {
```sql
    // Removes user from cache after update
}
```

@CachePut(value = "users", key = "#result.userId")
public ApplicationUsers saveUser(ApplicationUsers user) {
```text
    // Updates cache with new value
}
```

19.2 CONNECTION POOLING

## YOUR PROJECT USES HIKARICP (Default):

# HikariCP Configuration
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.idle-timeout=30000
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.max-lifetime=1800000

WHY CONNECTION POOLING?
- Creating connections is expensive
- Pool maintains ready connections
- Reuse connections across requests

19.3 LAZY LOADING

## JPA LAZY LOADING:

@Entity
public class ApplicationUsers {

```java
    @OneToMany(fetch = FetchType.LAZY)
    private List<UserApplication> applications;
    // Loaded only when accessed
}
```

BENEFITS:
- Reduces initial query load
- Loads related data only when needed
- Improves startup performance

19.4 QUERY OPTIMIZATION

## BEST PRACTICES:

1. Use @Query for complex queries instead of method naming
2. Use projections to fetch only needed columns
3. Use pagination for large result sets
4. Avoid N+1 queries with JOIN FETCH

@Query("SELECT a FROM AgencyAcctRouting a JOIN FETCH a.user")
List<AgencyAcctRouting> findAllWithUsers();

## PART 20: ENTERPRISE BEST PRACTICES

20.1 CODE ORGANIZATION

## ✓ Separate concerns into layers (Controller, Service, Repository)

✓ Use interfaces for services
✓ Keep controllers thin - delegate to services
✓ Use DTOs for API communication
✓ Centralize exception handling
✓ Use constants for error messages

20.2 SECURITY

## ✓ Never expose entity directly in API

✓ Validate all input
✓ Use HTTPS in production
✓ Implement proper authentication/authorization
✓ Store secrets in environment variables
✓ Use parameterized queries (JPA does this)

20.3 LOGGING

## ✓ Log at appropriate levels (ERROR, WARN, INFO, DEBUG)

✓ Include correlation IDs for tracing
✓ Don't log sensitive data (passwords, tokens)
✓ Use structured logging in production

20.4 TESTING

## ✓ Unit tests for service layer

✓ Integration tests for controller layer
✓ Use @MockBean for mocking dependencies
✓ Test edge cases and error scenarios
✓ Maintain good code coverage (your project has JaCoCo)

20.5 CONFIGURATION

## ✓ Use environment-specific properties (dev, prod)

✓ Externalize configuration
✓ Use environment variables for secrets
✓ Document configuration options

## PART 21: INTERVIEW QUESTIONS AND ANSWERS

BEGINNER LEVEL:

## Q1: What is Spring Boot?

A: Spring Boot is a framework built on top of Spring that simplifies configuration
and deployment. It provides auto-configuration, embedded servers, and
production-ready features out of the box.

Q2: What is the difference between @Controller and @RestController?
A: @RestController = @Controller + @ResponseBody. @Controller returns view names
```text
for MVC, while @RestController returns data directly as JSON/XML.
```

Q3: What is @Autowired?
A: @Autowired is used for dependency injection. Spring automatically injects the
required bean into the annotated field, constructor, or setter method.

Q4: What is the purpose of application.properties?
A: It contains configuration properties for the Spring Boot application like
database connection, server port, logging levels, etc.

Q5: What is JPA?
A: Java Persistence API is a specification for ORM (Object-Relational Mapping).
It maps Java objects to database tables and provides CRUD operations.

INTERMEDIATE LEVEL:

## Q6: Explain the Spring Boot request flow.

A: Request → Filter Chain → DispatcherServlet → HandlerMapping →
HandlerAdapter → Controller → Service → Repository → Database →
Response flows back through the same layers.

Q7: What is @Transactional and how does it work?
A: @Transactional manages database transactions. Spring creates a proxy around
the method, begins a transaction before execution, and commits on success
or rolls back on exception.

Q8: Explain Spring Data JPA method naming convention.
A: Spring Data JPA generates queries from method names:
- findByAgntNbr → SELECT ... WHERE agntNbr = ?
- findByAgntNbrAndEntpsId → SELECT ... WHERE agntNbr = ? AND entpsId = ?
- existsByUserId → SELECT CASE WHEN COUNT > 0 THEN true END

Q9: What is the difference between Entity and DTO?
A: Entity maps to database table with JPA annotations, managed by Hibernate.
DTO is a plain object for data transfer, used for API request/response,
can have additional fields not in database.

Q10: How does Spring Security work?
A: Spring Security uses a filter chain. Requests pass through filters like
CorsFilter, AuthenticationFilter. Filters validate credentials, set
SecurityContext, and authorize access to endpoints.

ADVANCED LEVEL:

## Q11: Explain Spring Boot auto-configuration mechanism.

A: @EnableAutoConfiguration reads META-INF/spring/...AutoConfiguration.imports,
loads auto-configuration classes. Each class has @Conditional annotations
that check classpath, beans, properties. If conditions met, beans created.

Q12: What is the bean lifecycle in Spring?
A: Instantiation → Property Population → BeanNameAware → BeanFactoryAware →
ApplicationContextAware → @PostConstruct → InitializingBean.afterPropertiesSet →
BeanPostProcessor.postProcessAfterInitialization → Ready →
```text
@PreDestroy → DisposableBean.destroy
```

Q13: Explain @ControllerAdvice exception handling.
A: @ControllerAdvice creates a global exception handler. @ExceptionHandler
methods handle specific exceptions. When exception thrown, Spring finds
matching handler, executes it, and returns error response.

Q14: How does connection pooling work with HikariCP?
A: HikariCP maintains a pool of database connections. When code needs a
connection, it borrows from pool. After use, connection returns to pool.
This avoids expensive connection creation for every request.

Q15: Explain JPA entity states.
A: Transient (new, not managed), Managed (in persistence context, tracked),
Detached (was managed, now disconnected), Removed (scheduled for deletion).

SENIOR ARCHITECT LEVEL:

## Q16: How would you design a microservices authentication system?

A: Use API Gateway as single entry point. Gateway validates JWT tokens.
Internal services trust gateway. Use OAuth 2.0 for token issuance.
Implement refresh token rotation. Store tokens in Redis for validation.

Q17: How do you handle distributed transactions in microservices?
A: Avoid distributed transactions when possible. Use Saga pattern:
- Choreography: Services emit events, others react
- Orchestration: Central coordinator manages steps
Use eventual consistency with compensating transactions.

Q18: Explain your approach to API versioning.
A: Options: URL versioning (/v1/api), Header versioning (Accept: v1),
Query parameter (?version=1). URL versioning is most common and clear.
Use API Gateway for routing. Deprecate old versions gradually.

Q19: How would you implement caching strategy?
A: Multi-level caching: L1 (application cache), L2 (distributed cache like Redis).
Cache-aside pattern: Check cache first, load from DB if miss, update cache.
Use cache eviction on write operations. Set appropriate TTL.

Q20: Describe your approach to logging and monitoring.
A: Structured logging with correlation IDs for request tracing.
ELK stack (Elasticsearch, Logstash, Kibana) for log aggregation.
Prometheus + Grafana for metrics. Spring Boot Actuator for health checks.
Distributed tracing with Zipkin or Jaeger.

SCENARIO-BASED QUESTIONS:

## Q21: Service is slow under high load. How do you diagnose and fix?

A: 1. Check application metrics (CPU, memory, threads)
2. Analyze database query performance (slow query log)
3. Check connection pool settings (max connections, timeout)
4. Enable SQL logging to identify N+1 queries
5. Add caching for frequently accessed data
6. Consider horizontal scaling with load balancer

Q22: Database deadlock occurring. How do you resolve?
A: 1. Enable deadlock detection logging
2. Identify the conflicting queries
3. Ensure consistent lock ordering across transactions
4. Reduce transaction scope and duration
5. Use optimistic locking with @Version
6. Consider read replicas for read-heavy operations

Q23: Memory leak suspected. How do you investigate?
A: 1. Monitor heap usage with Actuator/JMX
2. Take heap dump during high memory usage
3. Analyze with Eclipse MAT or VisualVM
4. Look for objects with high retention
5. Check for unclosed resources, static collections
6. Review entity relationships for circular references

Q24: API returning different results for same request. How to debug?
A: 1. Check if request has any caching (browser, CDN, server)
2. Verify database state (check for concurrent modifications)
3. Review logging for request processing
4. Check for race conditions in concurrent code
5. Verify all replicas have same data
6. Add request correlation ID for tracing

## END OF ANALYSIS DOCUMENT

This document was generated for interview preparation based on the
ess-odadmin-service enterprise Spring Boot project.

Key Technologies Used:
- Spring Boot 3.5.7
- Java 21
- Spring Data JPA
- Spring Security
- Oracle Database
- ModelMapper
- JUnit 5 / Mockito
