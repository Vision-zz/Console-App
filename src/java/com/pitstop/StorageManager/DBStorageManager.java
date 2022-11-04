package com.pitstop.StorageManager;

import java.io.IOException;

import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;

public class DBStorageManager implements StorageLoadable {

    private static DBStorageManager instance = null;
    private final IssuesDatabase issuesDatabase;
    private final EmployeeDatabase employeeDatabase;
    private final StorageDataManager manager;

    public static DBStorageManager getInstance(StorageDataManager manager) {
        if (instance == null)
            instance = new DBStorageManager(IssuesDatabase.getInstance(), EmployeeDatabase.getInstance(), manager);
        return instance;
    }

    private DBStorageManager(IssuesDatabase issuesDatabase, EmployeeDatabase employeeDatabase,
            StorageDataManager manager) {
        this.issuesDatabase = issuesDatabase;
        this.employeeDatabase = employeeDatabase;
        this.manager = manager;
    }

    @Override
    public void loadData(StorageLoadTypes type) {
        if (!manager.validateData(type))
            return;
        this.issuesDatabase.updateCurrentID(manager.getCurrentIssueID(type));
        this.employeeDatabase.updateCurrentID(manager.getCurrentEmployeeID(type));
        manager.getIssues(type).values().forEach(issue -> issuesDatabase.add(issue));
        manager.getEmployees(type).values().forEach(employee -> employeeDatabase.add(employee));
    }

    @Override
    public void saveData() throws IOException {
        manager.saveIssues(issuesDatabase.getCurrentID(), issuesDatabase.getAll().values());
        manager.saveEmployees(employeeDatabase.getCurrentID(), employeeDatabase.getAll().values());
    }

}
