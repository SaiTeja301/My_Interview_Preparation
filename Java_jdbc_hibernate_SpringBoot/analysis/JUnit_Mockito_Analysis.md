================================================================================
JUNIT 5 & MOCKITO - COMPREHENSIVE INTERVIEW PREPARATION GUIDE
For: 7+ Years Experience Level | Java Developer
================================================================================

SECTION 1: ANALYSIS
Resume: JUnit mentioned in resume, used for testing in Nationwide and IKEA projects.
Coverage in Notes: Basic JUnit concepts
Missing: JUnit 5 architecture, Mockito deep dive, BDD testing, Integration testing,
         @SpringBootTest, Test Containers, TDD approach, parameterized tests

SECTION 2: JUNIT 5 ARCHITECTURE

JUnit 5 = JUnit Platform + JUnit Jupiter + JUnit Vintage
- Platform: Foundation for launching testing frameworks on JVM
- Jupiter: New programming model + extension model (JUnit 5 tests)
- Vintage: Backward compatibility with JUnit 3/4

Key Annotations:
@Test: Marks test method
@BeforeEach / @AfterEach: Run before/after each test
@BeforeAll / @AfterAll: Run before/after all tests (static)
@DisplayName: Custom test name
@Disabled: Skip test
@Nested: Group related tests
@ParameterizedTest: Data-driven tests
@ExtendWith: Register extensions

SECTION 3: INTERVIEW ROUNDS

ROUND 1 - BASIC

*** Q1. JUnit 4 vs JUnit 5.
Feature          | JUnit 4            | JUnit 5
Annotation       | @Before/@After     | @BeforeEach/@AfterEach
Rule             | @Rule              | @ExtendWith
Parameterized    | @RunWith(Parameterized) | @ParameterizedTest
Assertions       | Assert.assertEquals | Assertions.assertEquals
Assumptions      | Assume             | Assumptions
Min Java         | Java 5             | Java 8+
Architecture     | Single JAR         | 3 modules

*** Q2. Writing effective unit tests.
Structure: Arrange → Act → Assert (AAA)

@Test
@DisplayName("Should calculate premium for active policy")
void shouldCalculatePremium() {
    // Arrange
    Policy policy = new Policy("POL-001", PolicyType.LIFE, 100000.0);
    PremiumCalculator calculator = new PremiumCalculator();

    // Act
    double premium = calculator.calculate(policy);

    // Assert
    assertEquals(5000.0, premium, 0.01);
    assertTrue(premium > 0, "Premium should be positive");
}

ROUND 2 - CORE TECHNICAL (MOCKITO)

*** Q3. Mockito - mock vs spy vs stub.
Mock: Fake object with no real implementation, returns defaults (null, 0, false)
Spy: Partial mock, real object with selected method overrides
Stub: Pre-programmed response for specific method calls

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;  // Full mock

    @Spy
    private EmailService emailService = new EmailService(); // Partial mock

    @InjectMocks
    private PolicyService policyService;  // Inject mocks into this

    @Test
    void shouldFindPolicyById() {
        // Stub (when-then)
        Policy mockPolicy = new Policy("POL-001", "ACTIVE");
        when(policyRepository.findById("POL-001"))
            .thenReturn(Optional.of(mockPolicy));

        // Act
        PolicyDTO result = policyService.findById("POL-001");

        // Assert
        assertNotNull(result);
        assertEquals("POL-001", result.getPolicyId());

        // Verify interactions
        verify(policyRepository, times(1)).findById("POL-001");
        verify(emailService, never()).sendEmail(any()); // Not called
    }
}

*** Q4. Mockito argument matchers and verification.
Matchers:
any(), anyString(), anyInt(), anyLong()
eq("value"), argThat(arg -> arg.length() > 5)

Verification:
verify(mock, times(1)).method();      // Called exactly once
verify(mock, never()).method();        // Never called
verify(mock, atLeast(2)).method();     // At least 2 times
verify(mock, atMost(5)).method();      // At most 5 times
verifyNoMoreInteractions(mock);        // No other methods called

Argument Captor:
ArgumentCaptor<Policy> captor = ArgumentCaptor.forClass(Policy.class);
verify(policyRepository).save(captor.capture());
Policy savedPolicy = captor.getValue();
assertEquals("ACTIVE", savedPolicy.getStatus());

*** Q5. Testing exceptions.
@Test
void shouldThrowWhenPolicyNotFound() {
    when(policyRepository.findById("XXX"))
        .thenReturn(Optional.empty());

    PolicyNotFoundException ex = assertThrows(
        PolicyNotFoundException.class,
        () -> policyService.findById("XXX"));

    assertEquals("Policy not found: XXX", ex.getMessage());
}

ROUND 3 - ADVANCED

*** Q6. @SpringBootTest vs @WebMvcTest vs @DataJpaTest.
@SpringBootTest: Full application context, integration test (slow)
@WebMvcTest: Only web layer (Controller), mock Service/Repository (fast)
@DataJpaTest: Only JPA layer, H2 in-memory DB, test Repository (fast)

// Controller test with @WebMvcTest
@WebMvcTest(PolicyController.class)
class PolicyControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private PolicyService policyService;

    @Test
    void shouldReturnPolicy() throws Exception {
        PolicyDTO dto = new PolicyDTO("POL-001", "ACTIVE");
        when(policyService.findById("POL-001")).thenReturn(dto);

        mockMvc.perform(get("/api/v1/policies/POL-001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.policyId").value("POL-001"))
            .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void shouldReturn404WhenNotFound() throws Exception {
        when(policyService.findById("XXX"))
            .thenThrow(new PolicyNotFoundException("Not found"));

        mockMvc.perform(get("/api/v1/policies/XXX"))
            .andExpect(status().isNotFound());
    }
}

// Repository test with @DataJpaTest
@DataJpaTest
class PolicyRepositoryTest {
    @Autowired private PolicyRepository repo;
    @Autowired private TestEntityManager entityManager;

    @Test
    void shouldFindByStatus() {
        entityManager.persist(new Policy("POL-001", "ACTIVE"));
        entityManager.persist(new Policy("POL-002", "EXPIRED"));
        entityManager.flush();

        List<Policy> active = repo.findByStatus("ACTIVE");
        assertEquals(1, active.size());
    }
}

Q7. Parameterized Tests.
@ParameterizedTest
@CsvSource({"100000, 5000", "200000, 10000", "50000, 2500"})
void shouldCalculatePremium(double sumAssured, double expectedPremium) {
    assertEquals(expectedPremium, calculator.calculate(sumAssured), 0.01);
}

@ParameterizedTest
@MethodSource("invalidPolicies")
void shouldRejectInvalidPolicy(Policy policy) {
    assertThrows(ValidationException.class, () -> service.validate(policy));
}

ROUND 4 - SCENARIO-BASED

*** Q8. Test coverage strategy for a Spring Boot service.
Layer              | Test Type          | Tools
Controller         | @WebMvcTest        | MockMvc, @MockBean
Service            | Unit test          | @Mock, @InjectMocks
Repository         | @DataJpaTest       | TestEntityManager
Integration        | @SpringBootTest    | Full context
API Contract       | Spring Cloud Contract | Contract tests

Target: 80%+ line coverage, 100% critical path coverage

Q9. Testing async methods.
@Test
void shouldProcessAsync() throws Exception {
    CompletableFuture<String> future = asyncService.processAsync("data");
    String result = future.get(5, TimeUnit.SECONDS); // Wait with timeout
    assertEquals("PROCESSED", result);
}

ROUND 5 - BEST PRACTICES

Q10. Unit testing best practices.
1. Test behavior, not implementation
2. One assertion concept per test
3. Meaningful test names (@DisplayName)
4. No logic in tests (no if/else, loops)
5. Use @Nested for grouping related tests
6. Mock external dependencies, not the class under test
7. Test edge cases: null, empty, boundary values
8. Don't test private methods directly
9. Use assertAll() for related assertions
10. Keep tests fast (<100ms per test)

KEY QUESTIONS:
*** 1. JUnit 4 vs JUnit 5
*** 2. Mockito mock vs spy vs stub
*** 3. Argument matchers and verification
*** 4. @SpringBootTest vs @WebMvcTest vs @DataJpaTest
*** 5. Exception testing
*** 6. Parameterized tests
*** 7. Test coverage strategy

================================================================================
END OF JUNIT_MOCKITO ANALYSIS
================================================================================

================================================================================
JUNIT + MOCKITO DEEP ANALYSIS UPDATE - 10-Mar-2026
Source reviewed: Junit and Mockito Notes.txt
================================================================================

1) WHAT IS ACTUALLY COVERED IN THE SOURCE FILE
- Unit testing fundamentals, manual vs automation testing.
- JUnit basics and JUnit 4 style setup (JDK, JUNIT_HOME, CLASSPATH).
- JUnit framework concepts: fixtures, test suites, test runners, assert APIs.
- Basic JUnit usage with TestJunit + TestRunner examples.
- Mockito basics: what mocking is, why Mockito is used, core benefits.
- Mockito first application idea: mock(), when().thenReturn(), verify().
- Mockito environment setup and chapter index (verify, spy, ordered verify, BDD, timeout).

2) IMPORTANT SOURCE-QUALITY OBSERVATION
- Source material is mostly old JUnit 4 era content.
- Environment examples use outdated versions (for example JDK 1.6, junit4.10/junit4.11, mockito-all-2.0.2-beta).
- Source gives strong fundamentals but not enough depth for 7+ year Spring Boot interview rounds.

3) GAP ANALYSIS FOR SENIOR INTERVIEW READINESS
- Missing practical JUnit 5 migration details (Jupiter, extension model, modern annotations in real projects).
- Missing Mockito strict stubbing, argument captors in business scenarios, doThrow/doAnswer depth.
- Missing layered Spring Boot test strategy with clear trade-offs (unit vs slice vs full integration).
- Missing CI test reliability topics: flaky tests, deterministic data setup, test isolation.
- Missing modern toolchain topics: Maven Surefire/Failsafe split, JaCoCo thresholds, Testcontainers.

4) WHAT TO SAY IN INTERVIEW (BASED ON SOURCE + MODERN EXPECTATION)
- Start with: "Unit test checks one class/method in isolation with controlled dependencies."
- Mention JUnit role: test lifecycle, assertions, suites/runners (source-backed basics).
- Mention Mockito role: isolate dependencies and control collaborator behavior.
- Then add modern expectation: "In production projects we use JUnit 5 + Mockito extension + slice tests for speed."

5) INTERVIEW CHEAT SHEET (QUICK DECISION TABLE)
- Pure business logic class -> JUnit unit test (no Spring context).
- Class with collaborators -> JUnit + Mockito (@Mock, @InjectMocks).
- Controller contract -> @WebMvcTest.
- Repository query behavior -> @DataJpaTest.
- Full wiring, configuration, startup behavior -> @SpringBootTest.
- External dependency behavior -> mock server / Testcontainers based integration tests.

6) SOURCE-ALIGNED CODE SNIPPETS (HIGH VALUE)

6.1 Minimal JUnit style test (core idea from source)
```java
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MessageUtilTest {
    @Test
    public void shouldPrintMessage() {
        MessageUtil util = new MessageUtil("Hello World");
        assertEquals("Hello World", util.printMessage());
    }
}
```

6.2 Mockito behavior + verification (core idea from source)
```java
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class PortfolioTest {
    @Test
    public void shouldCalculateMarketValue() {
        StockService stockService = mock(StockService.class);
        Stock google = new Stock("1", "Google", 10);
        when(stockService.getPrice(google)).thenReturn(50.0);

        Portfolio portfolio = new Portfolio();
        portfolio.setStockService(stockService);
        portfolio.setStocks(java.util.Arrays.asList(google));

        assertEquals(500.0, portfolio.getMarketValue(), 0.001);
        verify(stockService, times(1)).getPrice(google);
    }
}
```

6.3 Exception assertion pattern (must know)
```java
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PolicyServiceTest {
    @Test
    void shouldThrowWhenPolicyMissing() {
        assertThrows(PolicyNotFoundException.class, () -> service.findById("X-404"));
    }
}
```

7) TOP QUESTIONS YOU SHOULD PREPARE NEXT
- Difference: mock vs spy vs stub with one real use case each.
- verify() variants: times, never, atLeast, verifyNoMoreInteractions.
- JUnit 4 vs JUnit 5 differences and why teams moved to Jupiter.
- When to choose @WebMvcTest vs @SpringBootTest.
- How to keep tests fast, deterministic, and independent.

8) ACTION PLAN TO UPGRADE YOUR CURRENT NOTES
- Keep source fundamentals as base (definitions and flow are good).
- Add JUnit 5 examples beside each JUnit 4 example.
- Add one real project scenario per topic (controller, service, repository tests).
- Add one "anti-pattern and fix" per topic (flaky tests, over-mocking, shared state).
- Add build pipeline section: test phases, coverage gates, and reporting.

================================================================================
APPEND CHECKPOINT - 10-Mar-2026
This section was appended after analyzing Junit and Mockito Notes.txt.
================================================================================

================================================================================
INTERVIEW LEVEL CODING QUESTIONS (20) WITH CODE ANSWERS - JUNIT + MOCKITO
================================================================================

Q1) Write a unit test for service method that fetches entity by id.
Answer:
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository repo;
    @InjectMocks UserService service;

    @Test
    void shouldGetUserById() {
        when(repo.findById(1L)).thenReturn(Optional.of(new User(1L, "Teja")));
        User result = service.getById(1L);
        assertEquals("Teja", result.getName());
        verify(repo).findById(1L);
    }
}
```

Q2) Test exception path when entity is not found.
Answer:
```java
@Test
void shouldThrowWhenUserNotFound() {
    when(repo.findById(99L)).thenReturn(Optional.empty());
    UserNotFoundException ex = assertThrows(UserNotFoundException.class,
            () -> service.getById(99L));
    assertTrue(ex.getMessage().contains("99"));
}
```

Q3) Write parameterized test for discount calculator.
Answer:
```java
@ParameterizedTest
@CsvSource({
    "1000,10,900",
    "500,20,400",
    "200,0,200"
})
void shouldCalculateDiscount(double price, int percent, double expected) {
    assertEquals(expected, DiscountCalculator.finalPrice(price, percent), 0.001);
}
```

Q4) Group related validation tests with @Nested.
Answer:
```java
class SignupValidatorTest {
    SignupValidator validator = new SignupValidator();

    @Nested
    class EmailValidation {
        @Test void validEmail() { assertTrue(validator.isValidEmail("a@b.com")); }
        @Test void invalidEmail() { assertFalse(validator.isValidEmail("ab.com")); }
    }
}
```

Q5) Verify dependency method called exact number of times.
Answer:
```java
@Test
void shouldCallRepositoryTwice() {
    service.refreshCache();
    service.refreshCache();
    verify(repo, times(2)).loadAll();
}
```

Q6) Capture saved argument and assert values.
Answer:
```java
@Test
void shouldSaveUserWithActiveStatus() {
    service.create("Teja");
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(repo).save(captor.capture());
    assertEquals("ACTIVE", captor.getValue().getStatus());
}
```

Q7) Verify call order across multiple mocks.
Answer:
```java
@Test
void shouldCallAuditAfterSave() {
    service.create("Teja");
    InOrder inOrder = inOrder(repo, auditService);
    inOrder.verify(repo).save(any(User.class));
    inOrder.verify(auditService).log("USER_CREATED");
}
```

Q8) Ensure no interaction with external client in validation failure.
Answer:
```java
@Test
void shouldNotCallGatewayWhenInvalid() {
    assertThrows(IllegalArgumentException.class, () -> service.pay(null));
    verifyNoInteractions(paymentGateway);
}
```

Q9) Use spy to stub one method and execute others normally.
Answer:
```java
@Test
void shouldUseSpyForPartialMock() {
    ArrayList<String> list = spy(new ArrayList<>());
    doReturn(100).when(list).size();
    list.add("A");
    assertEquals("A", list.get(0)); // real method
    assertEquals(100, list.size()); // stubbed method
}
```

Q10) Mock void method to throw exception.
Answer:
```java
@Test
void shouldHandleEmailFailure() {
    doThrow(new RuntimeException("SMTP down")).when(emailService).send(anyString());
    assertThrows(NotificationException.class, () -> service.notifyUser("u1"));
}
```

Q11) Return dynamic value using doAnswer.
Answer:
```java
@Test
void shouldGenerateTokenDynamically() {
    when(tokenClient.issue(anyString())).thenAnswer(inv -> "TKN-" + inv.getArgument(0));
    assertEquals("TKN-user1", service.createToken("user1"));
}
```

Q12) Test with timeout.
Answer:
```java
@Test
void shouldCompleteWithinTwoSeconds() {
    assertTimeout(Duration.ofSeconds(2), () -> {
        service.heavyComputation();
    });
}
```

Q13) Test asynchronous CompletableFuture method.
Answer:
```java
@Test
void shouldProcessAsyncRequest() throws Exception {
    CompletableFuture<String> future = service.processAsync("input");
    assertEquals("OK-input", future.get(3, TimeUnit.SECONDS));
}
```

Q14) Controller slice test success response using @WebMvcTest.
Answer:
```java
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired MockMvc mvc;
    @MockBean UserService service;

    @Test
    void shouldReturnUser() throws Exception {
        when(service.getById(1L)).thenReturn(new User(1L, "Teja"));
        mvc.perform(get("/users/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value("Teja"));
    }
}
```

Q15) Controller slice test 404 scenario.
Answer:
```java
@Test
void shouldReturnNotFound() throws Exception {
    when(service.getById(99L)).thenThrow(new UserNotFoundException("not found"));
    mvc.perform(get("/users/99"))
       .andExpect(status().isNotFound());
}
```

Q16) Repository query test using @DataJpaTest.
Answer:
```java
@DataJpaTest
class UserRepositoryTest {
    @Autowired UserRepository repo;

    @Test
    void shouldFindByEmail() {
        repo.save(new User(null, "Teja", "teja@mail.com"));
        Optional<User> found = repo.findByEmail("teja@mail.com");
        assertTrue(found.isPresent());
    }
}
```

Q17) Full integration test using @SpringBootTest.
Answer:
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {
    @Autowired MockMvc mvc;

    @Test
    void healthEndpointShouldReturnUp() throws Exception {
        mvc.perform(get("/actuator/health"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value("UP"));
    }
}
```

Q18) Verify method argument with argThat custom matcher.
Answer:
```java
@Test
void shouldSendOnlyHighPriorityAlerts() {
    service.alert("PAYMENT_FAILED");
    verify(alertClient).send(argThat(msg -> msg.contains("HIGH")));
}
```

Q19) Write BDD-style Mockito test.
Answer:
```java
@Test
void shouldUseBddStyle() {
    given(repo.findById(1L)).willReturn(Optional.of(new User(1L, "Teja")));
    User user = service.getById(1L);
    then(repo).should(times(1)).findById(1L);
    assertEquals("Teja", user.getName());
}
```

Q20) Test retry logic: first call fails, second succeeds.
Answer:
```java
@Test
void shouldRetryOnceAndSucceed() {
    when(client.fetch())
        .thenThrow(new RuntimeException("temporary"))
        .thenReturn("SUCCESS");

    String result = service.fetchWithRetry();
    assertEquals("SUCCESS", result);
    verify(client, times(2)).fetch();
}
```

================================================================================
PRACTICE CHECKPOINT - 10-Mar-2026
Use these 20 questions for mock interview coding rounds.
================================================================================
