package com.pitstop.StorageManager;

import java.io.IOException;

import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public class DBStorageManager {

    private static DBStorageManager instance = null;
    private final IssuesDatabase issuesDatabase;
    private final EmployeeDatabase employeeDatabase;

    public static DBStorageManager getInstance() {
        if (instance == null)
            instance = new DBStorageManager(IssuesDatabase.getInstance(), EmployeeDatabase.getInstance());
        return instance;
    }

    private DBStorageManager(IssuesDatabase issuesDatabase, EmployeeDatabase employeeDatabase) {
        this.issuesDatabase = issuesDatabase;
        this.employeeDatabase = employeeDatabase;
    }

    public void loadData(StorageLoadTypes type) {
        JSONDataParser parser = new JSONDataParser(type);
        if(!parser.validateData()) return;
        this.issuesDatabase.updateCurrentID(parser.getCurrentIssueID());
        this.employeeDatabase.updateCurrentID(parser.getCurrentEmployeeID());
        parser.getIssues().values().forEach(issue -> issuesDatabase.add(issue));
        parser.getEmployees().values().forEach(employee -> employeeDatabase.add(employee));
    }

    public void saveData(StorageLoadTypes type) throws IOException {
        JSONDataParser parser = new JSONDataParser(type);
        parser.saveIssues(issuesDatabase.getCurrentID(), issuesDatabase.getAll().values());
        parser.saveEmployees(employeeDatabase.getCurrentID(), employeeDatabase.getAll().values());
    }

}
