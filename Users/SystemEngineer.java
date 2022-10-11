package Users;

import java.util.Collection;

import Middlewares.Issues.EngineerIssueManager;
import Modules.Issue;

public final class SystemEngineer extends Employee {

    private int reportsResolved;

    public SystemEngineer(String username, String password, String employeeName) {
        super(username, password, employeeName);
        this.reportsResolved = 0;
    }

    public int getTotalResolvedReports() {
        return reportsResolved;
    }

    public void markIssueAsResolved(Issue issue, EngineerIssueManager manager) {
        manager.requestResolveIssue(issue);
    }

    public Collection<Issue> getAllAssignedIssues(EngineerIssueManager manager) {
        return manager.requestAssignedIssues(this);
    }



}
