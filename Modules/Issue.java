package Modules;

import Database.Session;
import Helpers.Logger;
import Users.Developer;
import Users.Employee;
import Users.SystemAdmin;
import Users.SystemEngineer;

public final class Issue {
    public final String issueID;
    private final IssueCategory category;
    private String description;
    private Employee assignedTo;
    private final Employee createdBy;
    private IssueStatus status;

    private static int currentIssueID = 0;

    Issue(IssueCategory category, String description) {
        this.issueID = category.toString() + "_" + currentIssueID++;
        this.category = category;
        this.createdBy = Session.getLoggedInUser();
        this.description = description;
    }

    public String getCategory() {
        return this.category.toString();
    }

    public String getDescription() {
        return this.description;
    }

    void updateDescription(String newDescription) {
        if (!Session.getLoggedInUser().getEmployeeID().equals(this.createdBy.getEmployeeID())) {
            Logger.logWarning("You cannot perform this action");
            return;
        }
        this.description = newDescription;
    }

    public void assign(Employee engineer) {
        if (!(Session.getLoggedInUser() instanceof SystemAdmin)) {
            Logger.logWarning("You cannot perform this action");
            return;
        }
        this.assignedTo = engineer;
    }

    public SystemEngineer getAssignedEngineer() {
        return ((SystemEngineer) this.assignedTo);
    }

    public Developer getCreatedBy() {
        return ((Developer) this.createdBy);
    }

    public String getStatus() {
        return this.status.toString();
    }

    void updateStatus(IssueStatus status) {
        this.status = status;
    }

}
