package Users;

import java.util.Collection;
import java.util.function.Predicate;

import Middlewares.Issues.AdminIssueManager;
import Modules.Issue;

public final class SystemAdmin extends Employee {

    public SystemAdmin(String username, String password, String employeeName) {
        super(username, password, employeeName);
    }

    public Collection<Issue> getAllIssues(AdminIssueManager manager) {
        return manager.requestAllIssues();
    }

    public Collection<Issue> getUnassignedIssues(AdminIssueManager manager) {
        Collection<Issue> allIssues = manager.requestAllIssues();

        Predicate<Issue> predicate = issue -> issue.getAssignedEngineer() != null;
        allIssues.removeIf(predicate);

        return allIssues;
    }

    public void assignIssueToEngineer(Issue issue, SystemEngineer engineer, AdminIssueManager manager) {
        manager.requestAssignIssue(issue, engineer);
    }

    public Collection<SystemEngineer> viewAllEngineers() {
        // TODO Finish this after completing DB Employee
        return null;
    }

}
