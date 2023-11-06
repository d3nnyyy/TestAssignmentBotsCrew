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

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private static final String NOT_FOUND_MESSAGE = "Department with name %s not found.";

    public String findHeadOfDepartment(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department != null) {
            Lector headOfDepartment = department.getHeadOfDepartment();

            if (headOfDepartment != null) {
                return "Head of " + departmentName + " department is " +
                        headOfDepartment.getFullName() + ".";
            } else {
                return "Head of " + departmentName + " department is not assigned.";
            }
        }
        return String.format(NOT_FOUND_MESSAGE, departmentName);
    }

    public String getDepartmentStatistic(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department != null) {
            if (department.getLectors() != null && !department.getLectors().isEmpty()) {
                long assistantCount = countLectorsByDegree(department, "assistant");
                long associateProfessorCount = countLectorsByDegree(department, "associate professor");
                long professorCount = countLectorsByDegree(department, "professor");
                return "assistants - " + assistantCount + ".\n" +
                        "associate professors - " + associateProfessorCount + ".\n" +
                        "professors - " + professorCount + ".";
            }
            return "Department " + departmentName + " has no lectors.";
        }
        return String.format(NOT_FOUND_MESSAGE, departmentName);
    }

    public String getAverageSalaryForDepartment(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department != null) {
            if (department.getLectors() != null && !department.getLectors().isEmpty()) {
                double averageSalary = calculateAverageSalary(department);
                return "The average salary of " + departmentName + " is " + averageSalary + ".";
            }
            return "Department " + departmentName + " has no lectors.";
        }
        return String.format(NOT_FOUND_MESSAGE, departmentName);
    }

    public String getEmployeeCount(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department != null) {
            if (department.getLectors() != null && !department.getLectors().isEmpty()) {
                int employeeCount = department.getLectors().size();
                return "Employee count of " + departmentName + " is " + employeeCount + ".";
            }
            return "Department " + departmentName + " has no lectors.";
        }
        return String.format(NOT_FOUND_MESSAGE, departmentName);
    }

    public String globalSearch(String template) {

        List<String> results = new ArrayList<>();

        List<Department> departments = departmentRepository.findAll();

        departments.forEach(department -> {
            if (department.getName().contains(template)) {
                results.add(department.getName());
            }
        });

        Set<Lector> lectors = departments.stream()
                .flatMap(department -> department.getLectors() == null ? Stream.empty() : department.getLectors().stream()).collect(Collectors.toSet());

        lectors.forEach(lector -> {
            if (lector.getFullName().contains(template)) {
                results.add(lector.getFullName());
            }
        });

        return String.join(",", results);
    }

    private Department getDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

    private long countLectorsByDegree(Department department, String degree) {
        return department.getLectors().stream()
                .filter(lector -> lector.getDegree().getName().equals(degree))
                .count();
    }

    private double calculateAverageSalary(Department department) {
        Set<Lector> lecturers = department.getLectors();
        if (lecturers != null && !lecturers.isEmpty()) {
            return lecturers.stream()
                    .mapToInt(lector -> lector.getSalary() != null ? lector.getSalary() : 0)
                    .average()
                    .orElse(0);
        } else {
            return 0.0;
        }
    }

}
