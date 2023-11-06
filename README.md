# University Console Interface

This is a simple Spring Boot Java project with a console interface for managing university departments and lectors. The project allows you to perform various operations and retrieve information about departments and lectors, such as finding the head of a department, showing department statistics, calculating the average salary, and more.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
  - [Available Commands](#available-commands)
  - [Examples](#examples)
  - [Tests](#tests)

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) installed.
- Apache Maven installed (for building the project).
- A relational database (e.g., MySQL) installed and configured with the necessary tables.

### Installation

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/d3nnyyy/TestAssignmentBotsCrew
   ```

2. Build the project using Maven:

   ```bash
   mvn clean install
   ```
3. Configure the application properties:

   Open the `src/main/resources/application.properties` file and provide the necessary database connection details.

4. Run the Spring Boot application:
   
   ```
   mvn spring-boot:run
   ```

## Usage

### Available Commands

The application supports the following commands:

1. **Who is head of department {department_name}**
   - Example: `Who is head of department Computer Science`
   - Retrieves the head of the specified department.

2. **Show {department_name} statistics**
   - Example: `Show Computer Science statistics`
   - Shows statistics for the specified department, including the counts of assistant, associate professor, and professor lectors.

3. **Show the average salary for department {department_name}**
   - Example: `Show the average salary for department Mathematics`
   - Calculates and displays the average salary of the specified department.

4. **Show count of employee for {department_name}**
   - Example: `Show count of employee for Chemistry`
   - Shows the count of employees (lectors) for the specified department.

5. **Global search by {template}**
   - Example: `Global search by van`
   - Performs a global search for lectors and department names containing the specified template.

### Examples

Here are some example commands and their expected output:

- Command: `Who is head of department Computer Science`
  - Output: "Head of Computer Science department is John Smith."

- Command: `Show Computer Science statistics`
  - Output: "assistants - 3. associate professors - 5. professors - 2."

- Command: `Show the average salary for department Mathematics`
  - Output: "The average salary of Mathematics is 55000.0."

- Command: `Show count of employee for Chemistry`
  - Output: "Employee count of Chemistry is 4."

- Command: `Global search by van`
  - Output: "Ivan Petrov, Petro Ivanov, Computer Science."
 
## Tests

The application includes a comprehensive set of unit tests to ensure the correctness of its functionality. These tests are implemented using JUnit and Mockito and are located in the `ua.dtsebulia.testassignmentbotscrew.service` package.

### Test Cases

1. **`testFindHeadOfDepartment`**: Verifies the ability to find the head of a department, whether the head is assigned or not.

2. **`testFindHeadOfDepartmentWhenDepartmentNotFound`**: Tests the case when the department is not found.

3. **`testFindHeadOfDepartmentWhenNotAssigned`**: Tests the case for finding the head of a department when the head is not assigned.

4. **`testGetDepartmentStatistic`**: Tests the calculation of department statistics with lectors having different degrees.

5. **`testGetDepartmentStatisticWhenDepartmentNotFound`**: Tests the case when the department is not found while calculating statistics.

6. **`testGetDepartmentStatisticWhenNoLectors`**: Checks the behavior when the department has no lectors.

7. **`testGetAverageSalaryForDepartment`**: Tests the calculation of the average salary for a department with lectors having salaries.

8. **`testGetAverageSalaryForDepartmentWhenDepartmentNotFound`**: Tests the case when the department is not found while calculating the average salary.

9. **`testGetAverageSalaryForDepartmentWhenNoLectors`**: Checks the behavior when the department has no lectors while calculating the average salary.

10. **`testGetAverageSalaryForDepartmentWhenLectorsHaveNoSalary`**: Verifies the case when lectors have no salaries.

11. **`testGetEmployeeCount`**: Tests the calculation of the count of employees in a department.

12. **`testGetEmployeeCountWhenDepartmentNotFound`**: Tests the case when the department is not found while calculating the employee count.

13. **`testGetEmployeeCountWhenNoLectors`**: Checks the behavior when the department has no lectors while calculating the employee count.

14. **`testGlobalSearchWhereDepartmentsContainTemplate`**: Verifies the global search functionality when department names contain the given template.

15. **`testGlobalSearchWhereLectorsContainTemplate`**: Tests the global search functionality when lector names contain the given template.

16. **`testGlobalSearchWhereDepartmentsAndLectorsContainTemplate`**: Verifies the global search when both department names and lector names contain the template.

17. **`testGlobalSearchWhenNoResults`**: Checks the behavior of global search when no matching names are found.

Each test case is designed to validate a specific aspect of the application's functionality and provide confidence in the correctness of the implemented features.

To run the tests, you can use your preferred Java testing framework, such as JUnit, and execute the test class `DepartmentServiceTest`.

