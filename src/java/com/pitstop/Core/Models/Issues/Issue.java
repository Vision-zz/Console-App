package com.pitstop.Core.Models.Issues;

import java.util.Date;

import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.SystemEngineer;

public class Issue {
    public final String issueID;
    private final IssueCategory category;
    private String description;
    private SystemEngineer assignedTo;
    private final Developer createdBy;
    private IssueStatus status;
    private final Date createdAt;
    private Date resolvedAt;


    public Issue(String issueID, IssueCategory category, String description, Developer createdBy) {
        this.issueID = issueID;
        this.category = category;
        this.createdBy = createdBy;
        this.description = description;
        this.status = IssueStatus.OPEN;
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
        this.description = newDescription;
    }

    public void assignEngineer(SystemEngineer engineer) {
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

    @Override
    public String toString() {
        final String newLine = "\n";
        String string = "|- Issue ID: " + this.issueID + newLine;

        string += "|_ Category: " + this.getCategory().toString() + newLine;
        string += "|_ Created By: " + this.getCreatedBy().getEmployeeName() + " EID: " + this.getCreatedBy().getEmployeeID() + newLine;
        string += "|_ Current Status: " + this.getStatus().toString() + newLine;
        if (!this.getStatus().toString().equals("OPEN")) {
            string += "|_ Engineer: " + this.getAssignedEngineer().getEmployeeName() + " EID: " + this.getAssignedEngineer().getEmployeeID() +  newLine;
        }

        string += String.format("%1$s %2$tB %2$td, %2$tY | %2$tr ", "|_ Created At: ", this.getCreatedAt()) + newLine;

        if (this.getStatus().toString().equals("RESOLVED")) {
            string += String.format("%1$s %2$tB %2$td, %2$tY | %2$tr ", "|_ Resolved At: ", this.getResolvedAt())
                    + newLine;
        }

        string += "|_ Description: " + this.getDescription() + newLine;
        string += "___________________________________";

        return string;
    }

}
