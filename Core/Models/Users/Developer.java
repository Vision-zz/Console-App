package Core.Models.Users;

import java.util.Collection;

import Core.Middleware.Issues.DevIssueManager;
import Core.Models.Issues.Issue;
import Core.Models.Issues.IssueCategory;

public final class Developer extends Employee {


    public Developer(String username, String password, String employeeName) {
        super(username, password, employeeName, EmployeeRole.DEVELOPER);
    }

    public void createIssue(IssueCategory category, String description, DevIssueManager manager) {
        Issue issue = new Issue(category, description);
        manager.newIssueRequest(issue);
    }

    public Issue getIssueByID(String ID, DevIssueManager manager) {
        Collection<Issue> issues = manager.getDevCreatedIssues(this);
        Issue issue = null;

        for (Issue i : issues) {
            if (i.issueID.equals(ID)) {
                issue = i;
                break;
            }
        }

        return issue;
    }

    public Collection<Issue> getAllCreatedIssues(DevIssueManager manager) {
        return manager.getDevCreatedIssues(this);
    }

}
