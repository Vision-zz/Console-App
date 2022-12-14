package com.pitstop.Database.Models.Issues;

import java.util.HashMap;

public final class IssuesDatabase implements IssueDatabaseFunctions {
     private final HashMap<String, DBIssue> issues;
     private int currentIssueID = 0;

     private static IssuesDatabase instance = null;

     public static IssuesDatabase getInstance() {
          if (instance == null)
               instance = new IssuesDatabase();
          return instance;
     }

     private IssuesDatabase() {
          this.issues = new HashMap<String, DBIssue>();
     }

     @Override
     public int getCurrentID() {
          return this.currentIssueID;
     }

     @Override
     public void updateCurrentID(int ID) {
          this.currentIssueID = ID;
     }

     @Override
     public DBIssue get(String ID) {
          return this.issues.get(ID);
     }

     @Override
     public HashMap<String, DBIssue> getAll() {
          return new HashMap<String, DBIssue>(issues);
     }

     @Override
     public void add(DBIssue issue) {
          if (this.issues.containsKey(issue.issueID)) {
               throw new RuntimeException("Issue with ID: " + issue.issueID
                         + " already exists. If you are trying to update a issue, try using IssueDatabase.update() method");
          }
          this.issues.put(issue.issueID, issue);
     }

     @Override
     public void remove(DBIssue issue) {
          if (!this.issues.containsKey(issue.issueID)) {
               throw new RuntimeException("Issue with ID: " + issue.issueID + " does not exist. Operation cancelled");
          }
          this.issues.remove(issue.issueID);

     };

     @Override
     public void udpate(DBIssue issue) {
          if (!this.issues.containsKey(issue.issueID)) {
               throw new RuntimeException("Issue with ID: " + issue.issueID
                         + " does not exist. If you are trying to add a issue, try using IssueDatabase.add() method");
          }
          this.issues.put(issue.issueID, issue);
     }

}
