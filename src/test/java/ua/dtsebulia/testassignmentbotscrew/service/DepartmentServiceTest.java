package ua.dtsebulia.testassignmentbotscrew.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dtsebulia.testassignmentbotscrew.entity.Degree;
import ua.dtsebulia.testassignmentbotscrew.entity.Department;
import ua.dtsebulia.testassignmentbotscrew.entity.Lector;
import ua.dtsebulia.testassignmentbotscrew.repository.DepartmentRepository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link DepartmentService}.
 */
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    private DepartmentService departmentService;

    /**
     * Set up the test environment.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentService = new DepartmentService(departmentRepository);
    }

    /**
     * Test case for finding the head of a department when the head is assigned.
     * It verifies that the correct message is returned when the head is found.
     */
    @Test
    void testFindHeadOfDepartment() {
        // Create a head of department.
        Lector headOfDepartment = Lector
                .builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        // Create a department with the head assigned.
        Department department = Department
                .builder()
                .name("Test Department")
                .headOfDepartment(headOfDepartment)
                .build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.findHeadOfDepartment("Test Department");

        // Verify that the expected message is returned.
        assertEquals("Head of Test Department department is John Doe.", result);
    }

    /**
     * Test case for finding the head of a department when the head is not assigned.
     * It verifies that the correct message is returned when the head is not found.
     */
    @Test
    void testFindHeadOfDepartmentWhenNotAssigned() {
        // Create a department without the head assigned.
        Department department = Department
                .builder()
                .name("Test Department")
                .build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.findHeadOfDepartment("Test Department");

        // Verify that the expected message is returned.
        assertEquals("Head of Test Department department is not assigned.", result);
    }

    /**
     * Test case for finding the head of a department when the department is not found.
     * It verifies that the correct error message is returned when the department is not found.
     */
    @Test
    void testFindHeadOfDepartmentWhenDepartmentNotFound() {
        // Mock the department repository to return null, indicating that the department is not found.
        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        // Execute the method under test.
        String result = departmentService.findHeadOfDepartment("Test Department");

        // Verify that the expected error message is returned.
        assertEquals("Department with name Test Department not found.", result);
    }

    /**
     * Test case for getting the department statistics with lectors having different degrees.
     * It verifies that the correct statistics message is returned when the department and lectors are found.
     */
    @Test
    void testGetDepartmentStatistic() {
        // Create degrees for lectors.
        Degree assistantDegree = Degree.builder().name("assistant").build();
        Degree associateProfessorDegree = Degree.builder().name("associate professor").build();
        Degree professorDegree = Degree.builder().name("professor").build();

        // Create lectors with different degrees.
        Lector assistant = Lector.builder().degree(assistantDegree).build();
        Lector associateProfessor = Lector.builder().degree(associateProfessorDegree).build();
        Lector professor = Lector.builder().degree(professorDegree).build();

        // Create a department with lectors having different degrees.
        Department department = Department.builder()
                .name("Test Department")
                .lectors(Set.of(assistant, associateProfessor, professor))
                .build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.getDepartmentStatistic("Test Department");

        // Verify that the expected statistics message is returned.
        assertEquals("assistants - 1.\nassociate professors - 1.\nprofessors - 1.", result);
    }

    /**
     * Test case for getting the department statistics when the department is not found.
     * It verifies that the correct error message is returned when the department is not found.
     */
    @Test
    void testGetDepartmentStatisticWhenDepartmentNotFound() {
        // Mock the department repository to return null, indicating that the department is not found.
        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        // Execute the method under test.
        String result = departmentService.getDepartmentStatistic("Test Department");

        // Verify that the expected error message is returned.
        assertEquals("Department with name Test Department not found.", result);
    }

    /**
     * Test case for getting the department statistics when the department has no lectors.
     * It verifies that the correct message is returned when the department has no lectors.
     */
    @Test
    void testGetDepartmentStatisticWhenNoLectors() {
        // Create a department with no lectors.
        Department department = Department.builder().name("Test Department").build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.getDepartmentStatistic("Test Department");

        // Verify that the expected message indicating no lectors is returned.
        assertEquals("Department Test Department has no lectors.", result);
    }

    /**
     * Test case for getting the average salary of a department with lectors having salaries.
     * It verifies that the correct average salary message is returned when the department and lectors are found.
     */
    @Test
    void testGetAverageSalaryForDepartment() {
        // Create lectors with salaries.
        Lector lector1 = Lector.builder().salary(50000).build();
        Lector lector2 = Lector.builder().salary(70000).build();
        Lector lector3 = Lector.builder().salary(60000).build();

        // Create a department with lectors having salaries.
        Department department = Department.builder()
                .name("Test Department")
                .lectors(Set.of(lector1, lector2, lector3))
                .build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.getAverageSalaryForDepartment("Test Department");

        // Verify that the expected average salary message is returned.
        assertEquals("The average salary of Test Department is 60000.0.", result);
    }

    /**
     * Test case for getting the average salary of a department when the department is not found.
     * It verifies that the correct error message is returned when the department is not found.
     */
    @Test
    void testGetAverageSalaryForDepartmentWhenDepartmentNotFound() {
        // Mock the department repository to return null, indicating that the department is not found.
        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        // Execute the method under test.
        String result = departmentService.getAverageSalaryForDepartment("Test Department");

        // Verify that the expected error message is returned.
        assertEquals("Department with name Test Department not found.", result);
    }

    /**
     * Test case for getting the average salary of a department when the department has no lectors.
     * It verifies that the correct message is returned when the department has no lectors.
     */
    @Test
    void testGetAverageSalaryForDepartmentWhenNoLectors() {
        // Create a department with no lectors.
        Department department = Department.builder().name("Test Department").build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.getAverageSalaryForDepartment("Test Department");

        // Verify that the expected message indicating no lectors is returned.
        assertEquals("Department Test Department has no lectors.", result);
    }

    /**
     * Test case for getting the count of employees for a department with lectors.
     * It verifies that the correct count of employees message is returned when the department and lectors are found.
     */
    @Test
    void testGetAverageSalaryForDepartmentWhenLectorsHaveNoSalary() {
        // Create lectors with no salaries.
        Lector lector1 = Lector.builder().firstName("John").lastName("Doe").build();
        Lector lector2 = Lector.builder().firstName("Jane").lastName("Doe").build();
        Lector lector3 = Lector.builder().firstName("Jack").lastName("Doe").build();

        // Create a department with lectors having no salaries.
        Department department = Department
                .builder()
                .name("Test Department")
                .lectors(Set.of(lector1, lector2, lector3))
                .build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.getAverageSalaryForDepartment("Test Department");

        // Verify that the expected average salary message is returned.
        assertEquals("The average salary of Test Department is 0.0.", result);
    }

    /**
     * Test case for getting the count of employees in a department.
     * It verifies that the correct count of employees message is returned when the department and lectors are found.
     */
    @Test
    void testGetEmployeeCount() {
        // Create lectors to represent employees.
        Lector lector1 = Lector.builder().firstName("John").lastName("Doe").build();
        Lector lector2 = Lector.builder().firstName("Jane").lastName("Doe").build();
        Lector lector3 = Lector.builder().firstName("Jack").lastName("Doe").build();

        // Create a department with lectors representing employees.
        Department department = Department.builder()
                .name("Test Department")
                .lectors(Set.of(lector1, lector2, lector3))
                .build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.getEmployeeCount("Test Department");

        // Verify that the expected count of employees message is returned.
        assertEquals("Employee count of Test Department is 3.", result);
    }

    /**
     * Test case for getting the count of employees in a department when the department is not found.
     * It verifies that the correct error message is returned when the department is not found.
     */
    @Test
    void testGetEmployeeCountWhenDepartmentNotFound() {
        // Mock the department repository to return null, indicating that the department is not found.
        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        // Execute the method under test.
        String result = departmentService.getEmployeeCount("Test Department");

        // Verify that the expected error message is returned.
        assertEquals("Department with name Test Department not found.", result);
    }

    /**
     * Test case for getting the count of employees in a department when the department has no lectors.
     * It verifies that the correct message is returned when the department has no lectors.
     */
    @Test
    void testGetEmployeeCountWhenNoLectors() {
        // Create a department with no lectors.
        Department department = Department.builder().name("Test Department").build();

        // Mock the department repository to return the department.
        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        // Execute the method under test.
        String result = departmentService.getEmployeeCount("Test Department");

        // Verify that the expected message indicating no lectors is returned.
        assertEquals("Department Test Department has no lectors.", result);
    }

    /**
     * Test case for performing a global search where department names contain the given template.
     * It verifies that the search results include department names that match the template.
     */
    @Test
    void testGlobalSearchWhereDepartmentsContainTemplate() {
        // Create three departments with names containing the template.
        Department department1 = Department.builder().name("Test Department 1").build();
        Department department2 = Department.builder().name("Test Department 2").build();
        Department department3 = Department.builder().name("Test Department 3").build();

        // Mock the department repository to return the list of departments.
        when(departmentRepository.findAll()).thenReturn(List.of(department1, department2, department3));

        // Execute the method under test.
        String result = departmentService.globalSearch("Department");

        // Verify that the search results include department names that match the template.
        assertEquals("Test Department 1,Test Department 2,Test Department 3", result);
    }

    /**
     * Test case for performing a global search where lector names contain the given template.
     * It verifies that the search results include lector names that match the template.
     */
    @Test
    void testGlobalSearchWhereLectorsContainTemplate() {
        // Create a set of lectors with names containing the template.
        // Use a LinkedHashSet to preserve the order of the lectors.
        LinkedHashSet<Lector> lectors = new LinkedHashSet<>();
        lectors.add(Lector.builder().firstName("John").lastName("Doe").build());
        lectors.add(Lector.builder().firstName("Jane").lastName("Doe").build());
        lectors.add(Lector.builder().firstName("Doe").lastName("Jack").build());

        // Create a department with lectors having names that match the template.
        Department department = Department.builder()
                .name("Test Department")
                .lectors(lectors)
                .build();

        // Mock the department repository to return the list containing the department.
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        // Execute the method under test.
        String result = departmentService.globalSearch("Doe");

        // Verify that the search results include lector names that match the template.
        assertEquals("John Doe,Jane Doe,Doe Jack", result);
    }

    /**
     * Test case for performing a global search where both department names and lector names contain the given template.
     * It verifies that the search results include both matching department names and lector names.
     */
    @Test
    void testGlobalSearchWhereDepartmentsAndLectorsContainTemplate() {
        // Create a department with a name containing the template.
        Department department = Department.builder().name("Test Department").build();

        // Create lectors with names that contain the template.
        Lector lector1 = Lector.builder().firstName("FirstName").lastName("Test").build();
        Lector lector2 = Lector.builder().firstName("Test").lastName("LastName").build();

        // Set the lectors in the department.
        department.setLectors(Set.of(lector1, lector2));

        // Mock the department repository to return the list containing the department.
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        // Execute the method under test.
        String result = departmentService.globalSearch("Test");

        // Verify that the search results include both the department name and lector names that match the template.
        assertEquals("Test Department,FirstName Test,Test LastName", result);
    }

    /**
     * Test case for performing a global search that returns no results.
     * It verifies that an empty string is returned when no matching names are found.
     */
    @Test
    void testGlobalSearchWhenNoResults() {
        // Create a department with lectors having names that do not match the template.
        Department department = Department.builder().name("Test Department").build();

        Lector lector1 = Lector.builder().firstName("John").lastName("Doe").build();
        Lector lector2 = Lector.builder().firstName("Jane").lastName("Doe").build();

        department.setLectors(Set.of(lector1, lector2));

        // Mock the department repository to return the list containing the department.
        when(departmentRepository.findAll()).thenReturn(List.of(department));

        // Execute the method under test.
        String result = departmentService.globalSearch("No Results");

        // Verify that an empty string is returned when no matching names are found.
        assertEquals("", result);
    }


}
