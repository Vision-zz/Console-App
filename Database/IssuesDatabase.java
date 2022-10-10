package Database;

import java.util.HashMap;

import Helpers.Logger;
import Modules.Issue;

public class IssuesDatabase implements DatabaseFunctions<Issue> {
     private final HashMap<String, Issue> issues;

     private static IssuesDatabase instance = null;

     public static IssuesDatabase getInstance() {
          if (instance == null)
               instance = new IssuesDatabase();
          return instance;
     }

     private IssuesDatabase() {
          this.issues = new HashMap<String, Issue>();
     }

     @Override
     public Issue get(String ID) {
          return this.issues.get(ID);
     }

     @Override
     public void add(Issue issue) throws Exception {
          if (this.issues.containsKey(issue.issueID)) {
               throw new Exception("Issue with ID: " + issue.issueID
                         + " already exists. If you are trying to update a issue, try using IssueDatabase.update() method");
          }
          this.issues.put(issue.issueID, issue);
     }

     @Override
     public void remove(Issue issue) {
          if (!this.issues.containsKey(issue.issueID)) {
               Logger.logInfo("Issue with ID: " + issue.issueID + " does not exist. Operation cancelled");
               return;
          }
          this.issues.remove(issue.issueID);

     };

     @Override
     public void udpate(Issue issue) throws Exception {
          if (this.issues.containsKey(issue.issueID)) {
               throw new Exception("Issue with ID: " + issue.issueID
                         + " does not exist. If you are trying to add a issue, try using IssueDatabase.add() method");
          }
          this.issues.put(issue.issueID, issue);
     }

}
