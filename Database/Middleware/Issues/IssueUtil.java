package Database.Middleware.Issues;

import Core.Models.Issues.Issue;
import Database.Models.Issues.DBIssue;

public class IssueUtil {

    public static final DBIssue cloneToDBIssue(Issue issue) {
        return new DBIssue(issue.issueID, issue.getCategory(), issue.getDescription(), issue.getAssignedEngineer(),
                issue.getCreatedBy(), issue.getStatus(), issue.getCreatedAt(), issue.getResolvedAt());
    }

    public static final Issue cloneToIssue(DBIssue issue) {
        return new Issue(issue.issueID, issue.getCategory(), issue.getDescription(), issue.getAssignedTo(),
                issue.getCreatedBy(), issue.getStatus(), issue.getCreatedAt(), issue.getResolvedAt());
    }

}