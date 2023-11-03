package ua.dtsebulia.testassignmentbotscrew.console;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.dtsebulia.testassignmentbotscrew.service.DepartmentService;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleInterface implements CommandLineRunner {

    private final DepartmentService departmentService;

    private static final String EXIT_COMMAND = "exit";

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        printAvailableCommands();

        while (!exit) {
            System.out.println("Enter a command (or 'exit' to quit):");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase(EXIT_COMMAND)) {
                exit = true;
            } else {
                processCommand(command);
            }
        }
    }

    private void printAvailableCommands() {
        System.out.println("Available Commands:");
        System.out.println("1. Who is head of department {department_name}");
        System.out.println("2. Show statistics for {department_name}");
        System.out.println("3. Show the average salary for department {department_name}");
        System.out.println("4. Show count of employee for {department_name}");
        System.out.println("5. Global search by {template}");
        System.out.println();
    }

    private void processCommand(String command) {
        if (command.contains("Who is head of department")) {
            String departmentName = extractDepartmentName(command);
            String result = departmentService.findHeadOfDepartment(departmentName);
            System.out.println(result);
        } else if (command.contains("Show statistics for")) {
            String departmentName = extractDepartmentName(command);
            String result = departmentService.getDepartmentStatistic(departmentName);
            System.out.println(result);
        } else if (command.contains("Show the average salary for department")) {
            String departmentName = extractDepartmentName(command);
            String result = departmentService.getAverageSalaryForDepartment(departmentName);
            System.out.println(result);
        } else if (command.contains("Show count of employee for")) {
            String departmentName = extractDepartmentName(command);
            String result = departmentService.getEmployeeCount(departmentName);
            System.out.println(result);
        } else if (command.contains("Global search by")) {
            String template = extractTemplate(command);
            String result = departmentService.globalSearch(template);
            System.out.println(result);
        } else {
            System.out.println("Invalid command. Please try again.");
        }
    }

    private String extractDepartmentName(String command) {
        int startIndex = command.indexOf("{");
        int endIndex = command.indexOf("}");

        if (startIndex != -1 && endIndex != -1) {
            return command.substring(startIndex + 1, endIndex);
        } else {
            return "";
        }
    }

    private String extractTemplate(String command) {
        int startIndex = command.indexOf("by") + 3;
        return command.substring(startIndex).trim();
    }
}
