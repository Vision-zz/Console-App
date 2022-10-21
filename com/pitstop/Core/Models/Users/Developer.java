package pitstop.Core.Models.Users;

import java.util.Collection;

import pitstop.Core.Middleware.Issues.DevIssueManager;
import pitstop.Core.Models.Issues.Issue;
import pitstop.Core.Models.Issues.IssueCategory;

public final class Developer extends Employee {

    private final DevIssueManager manager;

    public Developer(String username, String password, String employeeName, DevIssueManager manager) {
        super(username, password, employeeName, EmployeeRole.DEVELOPER);
        this.manager = manager;
    }

    public Developer(String username, String password, String employeeName, String employeeID,
            DevIssueManager manager) {
        super(username, password, employeeName, EmployeeRole.DEVELOPER, employeeID);
        this.manager = manager;
    }

    public String createIssue(IssueCategory category, String description) {
        Issue issue = new Issue(category, description);
        this.manager.newIssueRequest(issue);
        return issue.issueID;
    }

    public Issue getIssueByID(String ID) {
        Collection<Issue> issues = this.manager.getDevCreatedIssues(this);
        Issue issue = null;

        for (Issue i : issues) {
            if (i.issueID.equals(ID)) {
                issue = i;
                break;
            }
        }

        return issue;
    }

    public Collection<Issue> getAllCreatedIssues() {
        return this.manager.getDevCreatedIssues(this);
    }

}