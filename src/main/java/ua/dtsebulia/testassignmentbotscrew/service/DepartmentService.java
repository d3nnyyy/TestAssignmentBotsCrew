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
            long assistantCount = countLectorsByDegree(department, "assistant");
            long associateProfessorCount = countLectorsByDegree(department, "associate professor");
            long professorCount = countLectorsByDegree(department, "professor");

            return "assistants - " + assistantCount + ".\n" +
                    "associate professors - " + associateProfessorCount + ".\n" +
                    "professors - " + professorCount + ".";
        }
        return String.format(NOT_FOUND_MESSAGE, departmentName);
    }

    public String getAverageSalaryForDepartment(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department != null) {
            double averageSalary = calculateAverageSalary(department);
            return "The average salary of " + departmentName + " is " + averageSalary + ".";
        }
        return String.format(NOT_FOUND_MESSAGE, departmentName);
    }

    public String getEmployeeCount(String departmentName) {
        Department department = getDepartmentByName(departmentName);

        if (department != null) {
            int employeeCount = department.getLectors().size();
            return "Employee count of " + departmentName + " is " + employeeCount + ".";
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
                .flatMap(department -> department.getLectors().stream())
                .collect(Collectors.toSet());

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
        return department.getLectors().stream()
                .mapToInt(Lector::getSalary)
                .average()
                .orElse(0.0);
    }
}
