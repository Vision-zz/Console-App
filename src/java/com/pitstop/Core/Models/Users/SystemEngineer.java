package com.pitstop.Core.Models.Users;

import java.util.Collection;

import com.pitstop.Core.Middleware.Issues.EngineerIssueManager;
import com.pitstop.Core.Models.Issues.Issue;

public final class SystemEngineer extends Employee {

    private final EngineerIssueManager manager;
    private static final EmployeeRole ROLE = EmployeeRole.SYSTEM_ENGINEER;


    public SystemEngineer(String username, String password, String employeeName, String employeeID, EngineerIssueManager manager) {
        super(username, password, employeeName, ROLE, employeeID);
        this.manager = manager;
    }

    public void markIssueAsResolved(Issue issue) {
        this.manager.resolveIssue(issue);
    }

    public Collection<Issue> getAllAssignedIssues() {
        return this.manager.getAssignedIssues(this);
    }

}
