package com.pitstop.Core.Models.Users;

import java.util.Collection;
import java.util.HashSet;

import com.pitstop.Core.Middleware.Issues.AdminIssueManager;
import com.pitstop.Core.Middleware.Users.EmployeeDetailsManager;
import com.pitstop.Core.Models.Issues.Issue;

public final class SystemAdmin extends Employee {

    private final AdminIssueManager issueManager;
    private final EmployeeDetailsManager employeeManager;
    private static final EmployeeRole ROLE = EmployeeRole.SYSTEM_ADMIN;

    public SystemAdmin(String username, String password, String employeeName, String employeeID,
            AdminIssueManager issueManager,
            EmployeeDetailsManager employeeManager) {
        super(username, password, employeeName, ROLE, employeeID);
        this.issueManager = issueManager;
        this.employeeManager = employeeManager;
    }

    public Collection<Issue> getAllIssues() {
        return issueManager.getAllIssues();
    }

    public Collection<Issue> getUnassignedIssues() {
        Collection<Issue> allIssues = issueManager.getAllIssues();

        allIssues.removeIf(issue -> issue.getAssignedEngineer() != null);

        return allIssues;
    }

    public void assignIssueToEngineer(Issue issue, SystemEngineer engineer) {
        issueManager.assignIssue(issue, engineer);
    }

    public Collection<SystemEngineer> getAllEngineers() {
        Collection<Employee> allEmployees = employeeManager.getAllEmployees();
        Collection<SystemEngineer> engineers = new HashSet<>();

        allEmployees.forEach(employee -> {
            if (employee.getEmployeeRole().equals(EmployeeRole.SYSTEM_ENGINEER))
                engineers.add((SystemEngineer) employee);
        });

        return engineers;
    }

}
