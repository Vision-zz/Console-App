package Modules;

import Database.Session;
import Helpers.Logger;
import Users.Developer;
import Users.SystemAdmin;
import Users.SystemEngineer;

public final class Issue {
    public final String issueID;
    private final IssueCategory category;
    private String description;
    private SystemEngineer assignedTo;
    private final Developer createdBy;
    private IssueStatus status;

    private static int currentIssueID = 0;

    Issue(IssueCategory category, String description) {
        this.issueID = category.toString() + "_" + currentIssueID++;
        this.category = category;
        this.createdBy = (Developer) Session.getLoggedInUser();
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

    public void assign(SystemEngineer engineer) {
        if (!(Session.getLoggedInUser() instanceof SystemAdmin)) {
            Logger.logWarning("You cannot perform this action");
            return;
        }
        this.assignedTo = engineer;
    }

    public SystemEngineer getAssignedEngineer() {
        return this.assignedTo;
    }

    public Developer getCreatedBy() {
        return this.createdBy;
    }

    public String getStatus() {
        return this.status.toString();
    }

    void updateStatus(IssueStatus status) {
        this.status = status;
    }

}
