package ua.dtsebulia.testassignmentbotscrew.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dtsebulia.testassignmentbotscrew.entity.Department;
import ua.dtsebulia.testassignmentbotscrew.entity.Lector;
import ua.dtsebulia.testassignmentbotscrew.repository.DepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service class for managing departments and lectors.
 */
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private static final String DEPARTMENT_NOT_FOUND_MSG = "Department with name %s not found.";
    private static final String DEPARTMENT_HAS_NO_LECTORS_MSG = "Department %s has no lectors.";

    /**
     * Retrieve the head of the department by its name.
     *
     * @param departmentName The name of the department.
     * @return A message containing the head of the department's name.
     */
    public String findHeadOfDepartment(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department != null) {

            // Get the head of the department.
            Lector headOfDepartment = department.getHeadOfDepartment();

            // If head of the department is not null,
            // return a message containing the head of the department's name.
            if (headOfDepartment != null) {
                return "Head of " + departmentName + " department is " + headOfDepartment.getFullName() + ".";
            }

            // If head of the department is null,
            // return a message saying that the head of the department is not assigned.
            return "Head of " + departmentName + " department is not assigned.";
        }

        // If the department is not found,
        // return a message saying that the department is not found.
        return String.format(DEPARTMENT_NOT_FOUND_MSG, departmentName);
    }

    /**
     * Get department statistics, including the count of lectors by degree.
     *
     * @param departmentName The name of the department.
     * @return Statistics about lectors in the department.
     */
    public String getDepartmentStatistic(String departmentName) {

        // Get the department by its name.
        Department department = getDepartmentByName(departmentName);

        // If the department and its lectors are not null,
        // calculate the count of lectors by degree.
        if (department != null) {
            if (department.getLectors() != null && !department.getLectors().isEmpty()) {

                // Use countLectorsByDegree() method to calculate the count of lectors by degree.
                long assistantCount = countLectorsByDegree(department, "assistant");
                long associateProfessorCount = countLectorsByDegree(department, "associate professor");
                long professorCount = countLectorsByDegree(department, "professor");

                // Return the statistics.
                return "assistants - " + assistantCount + ".\n" +
                        "associate professors - " + associateProfessorCount + ".\n" +
                        "professors - " + professorCount + ".";
            }

            // If the department has no lectors,
            // return a message saying that the department has no lectors.
            return String.format(DEPARTMENT_HAS_NO_LECTORS_MSG, departmentName);
        }

        // If the department is not found,
        // return a message saying that the department is not found.
        return String.format(DEPARTMENT_NOT_FOUND_MSG, departmentName);
    }

    /**
     * Calculate and retrieve the average salary for a department.
     *
     * @param departmentName The name of the department.
     * @return The average salary of the department.
     */
    public String getAverageSalaryForDepartment(String departmentName) {

        // Get the department by its name.
        Department department = getDepartmentByName(departmentName);

        // If the department and its lectors are not null,
        // calculate the average salary of the department.
        if (department != null) {
            if (department.getLectors() != null && !department.getLectors().isEmpty()) {

                // Use calculateAverageSalary() method
                // to calculate the average salary of the department.
                double averageSalary = calculateAverageSalary(department);

                // Return the average salary of the department.
                return "The average salary of " + departmentName + " is " + averageSalary + ".";
            }

            // If the department has no lectors,
            // return a message saying that the department has no lectors.
            return String.format(DEPARTMENT_HAS_NO_LECTORS_MSG, departmentName);
        }

        // If the department is not found,
        // return a message saying that the department is not found.
        return String.format(DEPARTMENT_NOT_FOUND_MSG, departmentName);
    }

    /**
     * Calculate and retrieve the count of employees for a department.
     *
     * @param departmentName The name of the department.
     * @return The count of employees for the department.
     */
    public String getEmployeeCount(String departmentName) {

        // Get the department by its name.
        Department department = getDepartmentByName(departmentName);

        // If the department and its lectors are not null,
        // calculate the count of employees for the department.
        if (department != null) {
            if (department.getLectors() != null && !department.getLectors().isEmpty()) {

                // Get the count of employees for the department.
                int employeeCount = department.getLectors().size();

                // Return the count of employees for the department.
                return "Employee count of " + departmentName + " is " + employeeCount + ".";
            }

            // If the department has no lectors,
            // return a message saying that the department has no lectors.
            return String.format(DEPARTMENT_HAS_NO_LECTORS_MSG, departmentName);
        }

        // If the department is not found,
        // return a message saying that the department is not found.
        return String.format(DEPARTMENT_NOT_FOUND_MSG, departmentName);
    }


    /**
     * Search for departments and lectors by a template.
     *
     * @param template The template.
     * @return The results of the search.
     */
    public String globalSearch(String template) {

        // Create a list to store the results of the search.
        List<String> results = new ArrayList<>();

        // Get all departments.
        List<Department> departments = departmentRepository.findAll();

        // Iterate through each department,
        // and add the department name to the results if it contains the template.
        departments.forEach(department -> {
            if (department.getName().contains(template)) {
                results.add(department.getName());
            }
        });

        // Create a set to store the lectors.
        // Use Java 8 Stream API to get all lectors from all departments.
        // If the department has no lectors, use an empty stream.
        Set<Lector> lectors = departments.stream()
                .flatMap(department ->
                        department.getLectors() == null ?
                                Stream.empty() :
                                department.getLectors().stream())
                .collect(Collectors.toSet());

        // Iterate through each lector,
        // and add the lector name to the results if it contains the template.
        lectors.forEach(lector -> {
            if (lector.getFullName().contains(template)) {
                results.add(lector.getFullName());
            }
        });

        // Return the results of the search separated by commas.
        return String.join(",", results);
    }

    /**
     * Get the department by its name.
     *
     * @param departmentName The name of the department.
     * @return The department.
     */
    private Department getDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

    /**
     * Calculate the count of lectors by degree.
     *
     * @param department The department.
     * @param degree     The degree.
     * @return The count of lectors by degree.
     */
    private long countLectorsByDegree(Department department, String degree) {

        // Use Java 8 Stream API to calculate the count of lectors by degree.
        return department.getLectors().stream()
                .filter(lector -> lector.getDegree().getName().equals(degree))
                .count();
    }

    /**
     * Calculate the average salary of the department.
     *
     * @param department The department.
     * @return The average salary of the department.
     */
    private double calculateAverageSalary(Department department) {

        // Use Java 8 Stream API to calculate the average salary of the department.
        // If the salary is null, use 0 instead.
        return department.getLectors().stream()
                .mapToInt(lector -> lector.getSalary() != null ? lector.getSalary() : 0)
                .average()
                .orElse(0);
    }

}
