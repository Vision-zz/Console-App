package pitstop.Database.Models.Issues;

import java.util.Date;

import pitstop.Core.Models.Issues.IssueCategory;
import pitstop.Core.Models.Issues.IssueStatus;
import pitstop.Core.Models.Users.Developer;
import pitstop.Core.Models.Users.SystemEngineer;

public final class DBIssue {
    public final String issueID;
    private final IssueCategory category;
    private final String description;
    private final SystemEngineer assignedTo;
    private final Developer createdBy;
    private final IssueStatus status;
    private final Date createdAt;
    private final Date resolvedAt;

    public DBIssue(String issueID, IssueCategory category, String description, SystemEngineer assignedTo,
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

    public String getIssueID() {
        return issueID;
    }

    public IssueCategory getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public SystemEngineer getAssignedTo() {
        return assignedTo;
    }

    public Developer getCreatedBy() {
        return createdBy;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getResolvedAt() {
        return resolvedAt;
    }

}
