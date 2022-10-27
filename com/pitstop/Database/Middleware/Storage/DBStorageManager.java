package com.pitstop.Database.Middleware.Storage;

import java.io.IOException;

import com.pitstop.Database.Models.DBStorageLoadable;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public class DBStorageManager implements DBStorageLoadable {

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

    @Override
    public void loadDataToDB(StorageParseable parser) {
        if(!parser.validateData()) return;
        this.issuesDatabase.updateCurrentID(parser.getCurrentIssueID());
        this.employeeDatabase.updateCurrentID(parser.getCurrentEmployeeID());
        parser.getIssues().values().forEach(issue -> issuesDatabase.add(issue));
        parser.getEmployees().values().forEach(employee -> employeeDatabase.add(employee));
    }

    @Override
    public void saveDataFromDB(StorageParseable parser) throws IOException {
        parser.saveIssues(issuesDatabase.getCurrentID(), issuesDatabase.getAll().values());
        parser.saveEmployees(employeeDatabase.getCurrentID(), employeeDatabase.getAll().values());
    }

}
