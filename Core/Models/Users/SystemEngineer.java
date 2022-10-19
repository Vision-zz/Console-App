package Core.Models.Users;

import java.util.Collection;

import Core.Middleware.Issues.EngineerIssueManager;
import Core.Models.Issues.Issue;

public final class SystemEngineer extends Employee {

    private final EngineerIssueManager manager;

    public SystemEngineer(String username, String password, String employeeName, EngineerIssueManager manager) {
        super(username, password, employeeName, EmployeeRole.SYSTEM_ENGINEER);
        this.manager = manager;
    }

    public SystemEngineer(String username, String password, String employeeName, String employeeID, EngineerIssueManager manager) {
        super(username, password, employeeName, EmployeeRole.SYSTEM_ENGINEER, employeeID);
        this.manager = manager;
    }

    public void markIssueAsResolved(Issue issue) {
        this.manager.resolveIssue(issue);
    }

    public Collection<Issue> getAllAssignedIssues() {
        return this.manager.getAssignedIssues(this);
    }

}
