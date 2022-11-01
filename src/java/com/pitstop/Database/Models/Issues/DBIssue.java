package com.pitstop.Database.Models.Issues;

import java.util.Date;

import com.pitstop.Core.Models.Issues.IssueCategory;
import com.pitstop.Core.Models.Issues.IssueStatus;
import com.pitstop.Database.Models.Users.DBEmployee;

public final class DBIssue {
    public final String issueID;
    private final IssueCategory category;
    private final String description;
    private final DBEmployee assignedTo;
    private final DBEmployee createdBy;
    private final IssueStatus status;
    private final Date createdAt;
    private final Date resolvedAt;

    public DBIssue(String issueID, IssueCategory category, String description, DBEmployee assignedTo,
            DBEmployee createdBy, IssueStatus status, Date createdAt, Date resolvedAt) {
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

    public DBEmployee getAssignedTo() {
        return assignedTo;
    }

    public DBEmployee getCreatedBy() {
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
