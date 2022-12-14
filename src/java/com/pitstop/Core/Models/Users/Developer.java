package com.pitstop.Core.Models.Users;

import java.util.Collection;

import com.pitstop.Core.Middleware.Issues.DevIssueManager;
import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Issues.IssueCategory;

public final class Developer extends Employee {

    private final DevIssueManager manager;
    private static final EmployeeRole ROLE = EmployeeRole.DEVELOPER;

    public Developer(String username, String password, String employeeName, String employeeID,
            DevIssueManager manager) {
        super(username, password, employeeName, ROLE, employeeID);
        this.manager = manager;
    }

    public String createIssue(IssueCategory category, String description) {
        String issueID = this.manager.createIssue(category, description, this);
        return issueID;
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
