package com.pitstop.StorageManager.Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonParser;
import com.pitstop.Database.Models.Issues.IssuesDatabase;
import com.pitstop.Database.Models.Users.EmployeeDatabase;
import com.pitstop.StorageManager.Structure.ParsedStorageData;

public class DBStorageManager implements StorageDataManager {

    private static DBStorageManager instance = null;
    private final IssuesDatabase issuesDatabase;
    private final EmployeeDatabase employeeDatabase;
    private final DataConverter<ParsedStorageData, String> converter;

    private static final String DEFAULT_JSON = "json/defaultSession.json";
    private static final String PREVIOUS_SESSION_JSON = "./previousSession.json";

    public static DBStorageManager getInstance(DataConverter<ParsedStorageData, String> manager) {
        if (instance == null)
            instance = new DBStorageManager(IssuesDatabase.getInstance(), EmployeeDatabase.getInstance(), manager);
        return instance;
    }

    private DBStorageManager(IssuesDatabase issuesDatabase, EmployeeDatabase employeeDatabase,
            DataConverter<ParsedStorageData, String> manager) {
        this.issuesDatabase = issuesDatabase;
        this.employeeDatabase = employeeDatabase;
        this.converter = manager;
    }

    @Override
    public void loadData(StorageLoadTypes type) {
        InputStream inputStream;
        if (type.equals(StorageLoadTypes.DEFAULT))
            inputStream = DBStorageManager.class.getClassLoader().getResourceAsStream(DEFAULT_JSON);
        else {

            File previousSessionFile = new File(PREVIOUS_SESSION_JSON);
            if (!previousSessionFile.isFile())
                try {
                    previousSessionFile.createNewFile();
                } catch (IOException e1) {
                    throw new RuntimeException("Error while creating new previousSession.json");
                }

            try {
                inputStream = new FileInputStream(previousSessionFile);

            } catch (Exception e) {
                throw new RuntimeException("Error cannot find previousSession.json");
            }
        }

        String jsonString = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsString();
        ParsedStorageData parsedData = converter.convertToAppData(jsonString);

        if (!validateData(parsedData))
            return;

        issuesDatabase.updateCurrentID(parsedData.getIssueData().getCurrentID());
        parsedData.getIssueData().getIssues().forEach(issue -> issuesDatabase.add(issue));
        employeeDatabase.updateCurrentID(parsedData.getEmployeeData().getCurrentID());
        parsedData.getEmployeeData().getEmployees().forEach(employee -> employeeDatabase.add(employee));

    }

    @Override
    public void saveData() throws IOException {

        ParsedStorageData storingData = new ParsedStorageData();
        storingData.getEmployeeData().setCurrentID(employeeDatabase.getCurrentID());
        storingData.getIssueData().setCurrentID(issuesDatabase.getCurrentID());

        employeeDatabase.getAll().values().forEach(employee -> storingData.getEmployeeData().addEmployee(employee));
        issuesDatabase.getAll().values().forEach(issue -> storingData.getIssueData().addIssue(issue));

        String jsonString = converter.convertToStorageData(storingData);

        FileWriter writer = new FileWriter(PREVIOUS_SESSION_JSON);
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

    private boolean validateData(ParsedStorageData data) {

        if (data == null || data.getIssueData() == null || data.getEmployeeData() == null)
            return false;
        if (data.getIssueData().getCurrentID() == null || data.getEmployeeData().getCurrentID() == null)
            return false;
        if (data.getIssueData().getIssues() == null || data.getEmployeeData().getEmployees() == null)
            return false;

        return true;
    }

}
