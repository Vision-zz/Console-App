package Core.Models.Users;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import Core.Middleware.Issues.AdminIssueManager;
import Core.Middleware.Users.EmployeeDetailsManager;
import Core.Models.Issues.Issue;

public final class SystemAdmin extends Employee {

    private final AdminIssueManager issueManager;
    private final EmployeeDetailsManager employeeManager;

    public SystemAdmin(String username, String password, String employeeName, AdminIssueManager issueManager,
            EmployeeDetailsManager employeeManager) {
        super(username, password, employeeName, EmployeeRole.SYSTEM_ADMIN);
        this.issueManager = issueManager;
        this.employeeManager = employeeManager;
    }

    public Collection<Issue> getAllIssues() {
        return issueManager.getAllIssues();
    }

    public Collection<Issue> getUnassignedIssues() {
        Collection<Issue> allIssues = issueManager.getAllIssues();

        Predicate<Issue> predicate = issue -> issue.getAssignedEngineer() != null;
        allIssues.removeIf(predicate);

        return allIssues;
    }

    public void assignIssueToEngineer(Issue issue, SystemEngineer engineer) {
        issueManager.assignIssue(issue, engineer);
    }

    public Collection<SystemEngineer> viewAllEngineers() {
        Collection<Employee> allEmployees = employeeManager.getAllEmployees();
        Collection<SystemEngineer> engineers = new HashSet<>();

        allEmployees.forEach(employee -> {
            if (employee.getEmployeeRole().equals(EmployeeRole.SYSTEM_ENGINEER))
                engineers.add((SystemEngineer) employee);
        });

        return engineers;
    }

}
