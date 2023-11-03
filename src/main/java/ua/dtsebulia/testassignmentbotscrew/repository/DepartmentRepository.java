package ua.dtsebulia.testassignmentbotscrew.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.dtsebulia.testassignmentbotscrew.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByName(String departmentName);
}
