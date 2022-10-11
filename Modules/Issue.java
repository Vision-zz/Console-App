package Modules;

import java.util.Date;

import Database.Session;
import Helpers.Logger;
import Users.Developer;
import Users.SystemAdmin;
import Users.SystemEngineer;

public class Issue {
    public final String issueID;
    private final IssueCategory category;
    private String description;
    private SystemEngineer assignedTo;
    private final Developer createdBy;
    private IssueStatus status;
    private final Date createdAt;
    private Date resolvedAt;

    private static int currentIssueID = 0;

    public Issue(IssueCategory category, String description) {
        this.issueID = category.toString() + "_" + currentIssueID++;
        this.category = category;
        this.createdBy = (Developer) Session.getLoggedInUser();
        this.description = description;
        this.status = IssueStatus.ACTIVE;
        this.createdAt = new Date();
    }

    public Issue(String issueID, IssueCategory category, String description, SystemEngineer assignedTo,
            Developer createdBy, IssueStatus status, Date createdAt, Date resolvedAt) {
        this.issueID = issueID;
        this.category = category;
        this.createdBy = createdBy;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.assignedTo = assignedTo;
        this.resolvedAt = resolvedAt;
    }

    public IssueCategory getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public void updateDescription(String newDescription) {
        if (!Session.getLoggedInUser().getUsername().equals(this.createdBy.getUsername())) {
            Logger.logWarning("You cannot perform this action");
            return;
        }
        this.description = newDescription;
    }

    public void assignEngineer(SystemEngineer engineer) {
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

    public IssueStatus getStatus() {
        return this.status;
    }

    public void updateStatus(IssueStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setResolvedAt(Date date) {
        this.resolvedAt = date;
    }

    public Date getResolvedAt() {
        return this.resolvedAt;
    }

}
