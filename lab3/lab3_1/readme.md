# Lab 3.1 Questions

## a) Identify a couple of examples on the use of AssertJ expressive methods chaining


In the `EmployeeService_UnitTest` class:
```java
// in the `whenValidName_thenEmployeeShouldBeFound` test:
assertThat(found.getName()).isEqualTo(name);

// in the `given3Employees_whengetAll_thenReturn3Records` test:
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
```

In the `EmployeeRestControllerTemplateIT` class:
```java
// in the `givenEmployees_whenGetEmployees_thenStatus200` test:
assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```

## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database)

In the `EmployeeService_UnitTest` class:
```java
@Mock( lenient = true)
private EmployeeRepository employeeRepository;
// ...
@BeforeEach
public void setUp() {
    Employee john = new Employee("john", "john@deti.com");
    john.setId(111L);

    Employee bob = new Employee("bob", "bob@deti.com");
    Employee alex = new Employee("alex", "alex@deti.com");

    List<Employee> allEmployees = Arrays.asList(john, bob, alex);

    Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
    Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
    Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
    Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
    Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
    Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
}
```

## c) What is the difference between standard @Mock and @MockBean?

A standard `@Mock` won't be injected into a Spring Boot application that uses the mocked object through bean injection, like `Autowired` annotation, while a `@MockBean` is ready made for such cases, Spring knows to inject MockBeans in the application as needed.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

The `application-integrationtest.properties` file contains environment variables to be used in integrations tests in the case of testing with a real data source instead of an in-memory database.

## e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

Strategy C loads only the boundary components (the controller). Dependencies of this controller, the EmployeeService in this case, are mocked through MockBeans and actions are invoked on the API through the MockMvc, being a more direct method of interaction.

Both strategy D and E load the entire application into a web environment, with the difference between the two strategies being the way that the API is called. Strategy D uses the MockMvc like in C, while strategy E makes API calls through a REST client (TestRestTemplate), which is simulates real requests more closely.
