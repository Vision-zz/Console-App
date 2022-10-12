package Core.Models.Users;

import java.util.Collection;

import Core.Middleware.Issues.EngineerIssueManager;
import Core.Models.Issues.Issue;

public final class SystemEngineer extends Employee {

    private int reportsResolved;

    public SystemEngineer(String username, String password, String employeeName) {
        super(username, password, employeeName, EmployeeRole.SYSTEM_ENGINEER);
        this.reportsResolved = 0;
    }

    public int getTotalResolvedReports() {
        return reportsResolved;
    }

    public void markIssueAsResolved(Issue issue, EngineerIssueManager manager) {
        manager.resolveIssue(issue);
    }

    public Collection<Issue> getAllAssignedIssues(EngineerIssueManager manager) {
        return manager.getAssignedIssues(this);
    }

}