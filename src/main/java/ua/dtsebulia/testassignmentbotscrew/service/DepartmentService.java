package ua.dtsebulia.testassignmentbotscrew.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dtsebulia.testassignmentbotscrew.entity.Department;
import ua.dtsebulia.testassignmentbotscrew.entity.Lector;
import ua.dtsebulia.testassignmentbotscrew.repository.DepartmentRepository;

import java.util.List;

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
        List<Department> departments = departmentRepository.findAll();
        List<Lector> lectors = departments.stream()
                .flatMap(department -> department.getLectors().stream())
                .toList();

        List<Department> departmentMatches = departments.stream()
                .filter(department -> department.getName().contains(template))
                .toList();

        List<Lector> lectorMatches = lectors.stream()
                .filter(lector -> lector.getFullName().contains(template))
                .toList();

        return "Departments found: " + departmentMatches.size() + ".\n" +
                "Lectors found: " + lectorMatches.size() + ".";
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
