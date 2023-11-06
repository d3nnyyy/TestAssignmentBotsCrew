package ua.dtsebulia.testassignmentbotscrew.console;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.dtsebulia.testassignmentbotscrew.service.DepartmentService;

import java.util.Scanner;

/**
 * Console interface for interacting with department and lector information.
 */
@Component
@RequiredArgsConstructor
public class ConsoleInterface implements CommandLineRunner {

    private final DepartmentService departmentService;

    private static final String EXIT_COMMAND = "exit";

    /**
     * The main method for running the console interface.
     *
     * @param args Command-line arguments.
     */
    @Override
    public void run(String... args) {
        // Create a scanner to read user input
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Print the list of available commands to the console
        printAvailableCommands();

        while (!exit) {
            System.out.println("Enter a command (or 'exit' to quit):");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase(EXIT_COMMAND)) {
                // If the user enters 'exit', exit the loop and quit the program
                exit = true;
            } else {
                // Process the user-entered command
                processCommand(command);
            }
        }
    }

    /**
     * Print the available commands to the console.
     */
    private void printAvailableCommands() {
        System.out.println("Available Commands:");
        System.out.println("1. Who is head of department {department_name}");
        System.out.println("2. Show statistics for {department_name}");
        System.out.println("3. Show the average salary for department {department_name}");
        System.out.println("4. Show count of employee for {department_name}");
        System.out.println("5. Global search by {template}");
        System.out.println();
    }

    /**
     * Process the user-entered command and execute the corresponding action.
     *
     * @param command The user-entered command.
     */
    private void processCommand(String command) {
        if (command.contains("Who is head of department")) {
            // If the command is to find the head of a department
            String departmentName = extractDepartmentName(command);
            String result = departmentService.findHeadOfDepartment(departmentName);
            System.out.println(result);
        } else if (command.contains("Show statistics for")) {
            // If the command is to show statistics for a department
            String departmentName = extractDepartmentName(command);
            String result = departmentService.getDepartmentStatistic(departmentName);
            System.out.println(result);
        } else if (command.contains("Show the average salary for department")) {
            // If the command is to show the average salary for a department
            String departmentName = extractDepartmentName(command);
            String result = departmentService.getAverageSalaryForDepartment(departmentName);
            System.out.println(result);
        } else if (command.contains("Show count of employee for")) {
            // If the command is to show the count of employees for a department
            String departmentName = extractDepartmentName(command);
            String result = departmentService.getEmployeeCount(departmentName);
            System.out.println(result);
        } else if (command.contains("Global search by")) {
            // If the command is to perform a global search
            String template = extractTemplate(command);
            String result = departmentService.globalSearch(template);
            System.out.println(result);
        } else {
            // If the command is not recognized, display an error message
            System.out.println("Invalid command. Please try again.");
        }
    }

    /**
     * Extract the department name from the user-entered command.
     *
     * @param command The user-entered command.
     * @return The department name extracted from the command.
     */
    private String extractDepartmentName(String command) {
        int startIndex = command.indexOf("{");
        int endIndex = command.indexOf("}");

        if (startIndex != -1 && endIndex != -1) {
            // Extract the department name from the command using curly braces
            return command.substring(startIndex + 1, endIndex);
        } else {
            // Return an empty string if the department name is not found
            return "";
        }
    }

    /**
     * Extract the search template from the user-entered command.
     *
     * @param command The user-entered command.
     * @return The search template extracted from the command.
     */
    private String extractTemplate(String command) {
        int startIndex = command.indexOf("by") + 3;
        // Extract the search template from the command after 'by' keyword
        return command.substring(startIndex).trim();
    }
}
