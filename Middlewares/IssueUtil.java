package Middlewares;

import Database.DBIssue;
import Modules.Issue;

public class IssueUtil {

    public final DBIssue cloneToDBIssue(Issue issue) {
        return new DBIssue(issue.issueID, issue.getCategory(), issue.getDescription(), issue.getAssignedEngineer(),
                issue.getCreatedBy(), issue.getStatus(), issue.getCreatedAt(), issue.getResolvedAt());
    }

    public final Issue cloneToIssue(DBIssue issue) {
        return new Issue(issue.issueID, issue.getCategory(), issue.getDescription(), issue.getAssignedTo(),
                issue.getCreatedBy(), issue.getStatus(), issue.getCreatedAt(), issue.getResolvedAt());
    }

}
