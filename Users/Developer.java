package Users;

import Middlewares.Issues.DevIssueManager;
import Modules.Issue;
import Modules.IssueCategory;

public final class Developer extends Employee {

    public Developer(String username, String password, String employeeName) {
        super(username, password, employeeName);
    }

    public void createIssue(IssueCategory category, String description, DevIssueManager manager) {
        Issue issue = new Issue(category, description);
        manager.newIssueRequest(issue);
    }

    public Issue getIssueByID(String ID, DevIssueManager manager) {
        Issue[] issues = manager.requestCreatedIssues();
        Issue issue = null;

        for (Issue i : issues) {
            if (i.issueID.equals(ID)) {
                issue = i;
                break;
            }
        }

        return issue;
    }

    public Issue[] getAllCreatedIssues(DevIssueManager manager) {
        return manager.requestCreatedIssues();
    }

}
