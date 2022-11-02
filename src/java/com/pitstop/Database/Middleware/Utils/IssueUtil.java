package com.pitstop.Database.Middleware.Utils;

import com.pitstop.Core.Models.Issues.Issue;
import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Models.Issues.DBIssue;
import com.pitstop.Database.Models.Users.DBEmployee;

public class IssueUtil {

        public static final DBIssue cloneToDBIssue(Issue issue) {
                DBEmployee assignedTo = issue.getAssignedEngineer() != null
                                ? EmployeeUtil.cloneToDBEmployee(issue.getAssignedEngineer())
                                : null;
                DBEmployee createdBy = EmployeeUtil.cloneToDBEmployee(issue.getCreatedBy());
                return new DBIssue(issue.issueID, issue.getCategory(), issue.getDescription(), assignedTo,
                                createdBy, issue.getStatus(), issue.getCreatedAt(), issue.getResolvedAt());
        }

        public static final Issue cloneToIssue(DBIssue issue) {
                Employee assignedToEmployee = issue.getAssignedTo() != null
                                ? EmployeeUtil.cloneToEmployee(issue.getAssignedTo())
                                : null;
                Employee createdByEmployee = EmployeeUtil.cloneToEmployee(issue.getCreatedBy());
                if ((assignedToEmployee != null && !(assignedToEmployee instanceof SystemEngineer))
                                || !(createdByEmployee instanceof Developer)) {
                        throw new RuntimeException("Corrupted data. Cannot downcast from employee");
                }

                SystemEngineer assignedTo = (SystemEngineer) assignedToEmployee;
                Developer createdBy = (Developer) createdByEmployee;

                return new Issue(issue.issueID, issue.getCategory(), issue.getDescription(), assignedTo,
                                createdBy, issue.getStatus(), issue.getCreatedAt(), issue.getResolvedAt());
        }

}