package ua.dtsebulia.testassignmentbotscrew.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dtsebulia.testassignmentbotscrew.entity.Degree;
import ua.dtsebulia.testassignmentbotscrew.entity.Department;
import ua.dtsebulia.testassignmentbotscrew.entity.Lector;
import ua.dtsebulia.testassignmentbotscrew.repository.DepartmentRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    private DepartmentService departmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        departmentService = new DepartmentService(departmentRepository);
    }

    @Test
    void testFindHeadOfDepartment() {

        Lector headOfDepartment = Lector
                .builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        Department department = Department
                .builder()
                .name("Test Department")
                .headOfDepartment(headOfDepartment)
                .build();

        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.findHeadOfDepartment("Test Department");
        assertEquals("Head of Test Department department is John Doe.", result);
    }

    @Test
    void testFindHeadOfDepartmentWhenNotAssigned() {

        Department department = Department
                .builder()
                .name("Test Department")
                .build();

        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.findHeadOfDepartment("Test Department");
        assertEquals("Head of Test Department department is not assigned.", result);
    }

    @Test
    void testFindHeadOfDepartmentWhenDepartmentNotFound() {

        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        String result = departmentService.findHeadOfDepartment("Test Department");
        assertEquals("Department with name Test Department not found.", result);
    }

    @Test
    void testGetDepartmentStatistic() {

        Degree assistantDegree = Degree
                .builder()
                .name("assistant")
                .build();

        Degree associateProfessorDegree = Degree
                .builder()
                .name("associate professor")
                .build();

        Degree professorDegree = Degree
                .builder()
                .name("professor")
                .build();

        Lector assistant = Lector
                .builder()
                .degree(assistantDegree)
                .build();

        Lector associateProfessor = Lector
                .builder()
                .degree(associateProfessorDegree)
                .build();

        Lector professor = Lector
                .builder()
                .degree(professorDegree)
                .build();

        Department department = Department
                .builder()
                .name("Test Department")
                .lectors(Set.of(assistant, associateProfessor, professor))
                .build();


        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.getDepartmentStatistic("Test Department");
        assertEquals("assistants - 1.\nassociate professors - 1.\nprofessors - 1.", result);
    }

    @Test
    void testGetDepartmentStatisticWhenDepartmentNotFound() {

        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        String result = departmentService.getDepartmentStatistic("Test Department");
        assertEquals("Department with name Test Department not found.", result);
    }

    @Test
    void testGetDepartmentStatisticWhenNoLectors() {

        Department department = Department
                .builder()
                .name("Test Department")
                .build();

        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.getDepartmentStatistic("Test Department");
        assertEquals("Department Test Department has no lectors.", result);
    }

    @Test
    void testGetAverageSalaryForDepartment() {

        Lector lector1 = Lector.builder().salary(50000).build();
        Lector lector2 = Lector.builder().salary(70000).build();
        Lector lector3 = Lector.builder().salary(60000).build();

        Department department = Department
                .builder()
                .name("Test Department")
                .lectors(Set.of(lector1, lector2, lector3))
                .build();


        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.getAverageSalaryForDepartment("Test Department");
        assertEquals("The average salary of Test Department is 60000.0.", result);
    }

    @Test
    void testGetAverageSalaryForDepartmentWhenDepartmentNotFound() {

        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        String result = departmentService.getAverageSalaryForDepartment("Test Department");
        assertEquals("Department with name Test Department not found.", result);
    }

    @Test
    void testGetAverageSalaryForDepartmentWhenNoLectors() {

        Department department = Department
                .builder()
                .name("Test Department")
                .build();

        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.getAverageSalaryForDepartment("Test Department");
        assertEquals("Department Test Department has no lectors.", result);
    }

    @Test
    void testGetAverageSalaryForDepartmentWhenLectorsHaveNoSalary() {

        Lector lector1 = Lector.builder().firstName("John").lastName("Doe").build();
        Lector lector2 = Lector.builder().firstName("Jane").lastName("Doe").build();
        Lector lector3 = Lector.builder().firstName("Jack").lastName("Doe").build();

        Department department = Department
                .builder()
                .name("Test Department")
                .lectors(Set.of(lector1, lector2, lector3))
                .build();

        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.getAverageSalaryForDepartment("Test Department");
        assertEquals("The average salary of Test Department is 0.0.", result);
    }

    @Test
    void testGetEmployeeCount() {

        Lector lector1 = Lector.builder().firstName("John").lastName("Doe").build();
        Lector lector2 = Lector.builder().firstName("Jane").lastName("Doe").build();
        Lector lector3 = Lector.builder().firstName("Jack").lastName("Doe").build();

        Department department = Department
                .builder()
                .name("Test Department")
                .lectors(Set.of(lector1, lector2, lector3))
                .build();

        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.getEmployeeCount("Test Department");
        assertEquals("Employee count of Test Department is 3.", result);
    }

    @Test
    void testGetEmployeeCountWhenDepartmentNotFound() {

        when(departmentRepository.findByName("Test Department")).thenReturn(null);

        String result = departmentService.getEmployeeCount("Test Department");
        assertEquals("Department with name Test Department not found.", result);
    }

    @Test
    void testGetEmployeeCountWhenNoLectors() {

        Department department = Department
                .builder()
                .name("Test Department")
                .build();

        when(departmentRepository.findByName("Test Department")).thenReturn(department);

        String result = departmentService.getEmployeeCount("Test Department");
        assertEquals("Department Test Department has no lectors.", result);
    }

}
