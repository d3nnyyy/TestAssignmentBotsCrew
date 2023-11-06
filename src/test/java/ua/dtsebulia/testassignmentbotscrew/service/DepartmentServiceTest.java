package ua.dtsebulia.testassignmentbotscrew.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.dtsebulia.testassignmentbotscrew.entity.Department;
import ua.dtsebulia.testassignmentbotscrew.entity.Lector;
import ua.dtsebulia.testassignmentbotscrew.repository.DepartmentRepository;

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

}
