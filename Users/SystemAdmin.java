package Users;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

import Middlewares.Employee.AdminEmployeeManager;
import Middlewares.Issues.AdminIssueManager;
import Models.Issue;

public final class SystemAdmin extends Employee {

    public SystemAdmin(String username, String password, String employeeName) {
        super(username, password, employeeName);
    }

    public Collection<Issue> getAllIssues(AdminIssueManager manager) {
        return manager.getAllIssues();
    }

    public Collection<Issue> getUnassignedIssues(AdminIssueManager manager) {
        Collection<Issue> allIssues = manager.getAllIssues();

        Predicate<Issue> predicate = issue -> issue.getAssignedEngineer() != null;
        allIssues.removeIf(predicate);

        return allIssues;
    }

    public void assignIssueToEngineer(Issue issue, SystemEngineer engineer, AdminIssueManager manager) {
        manager.assignIssue(issue, engineer);
    }

    public Collection<SystemEngineer> viewAllEngineers(AdminEmployeeManager manager) {
        Collection<Employee> allEmployees = manager.getAllEmployees();
        Collection<SystemEngineer> engineers = new HashSet<>();

        allEmployees.forEach(employee -> {
            if (employee instanceof SystemEngineer)
                engineers.add(
                        new SystemEngineer(employee.getUsername(), employee.getPassword(), employee.getEmployeeName()));
        });

        return engineers;
    }

}
