package com.pitstop.Session;

import com.pitstop.Core.Models.Users.EmployeeRole;

public class SessionEmployee {
	private final String username;
    private final String password;
    private final String employeeName;
    private final String employeeID;
    private final EmployeeRole employeeRole;

    public SessionEmployee(String username, String password, String employeeName,String employeeID, EmployeeRole employeeRole) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
        this.employeeID = employeeID;
        this.employeeRole = employeeRole;
    }

    // Getters
    public final String getUsername() {
        return this.username;
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getEmployeeName() {
        return this.employeeName;
    }

    public final String getEmployeeID() {
        return this.employeeID;
    }

    public final EmployeeRole getEmployeeRole() {
        return this.employeeRole;
    }
}
