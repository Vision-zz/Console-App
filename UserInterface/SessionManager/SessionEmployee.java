package UserInterface.SessionManager;

import Core.Models.Users.EmployeeRole;

public class SessionEmployee {
	private final String username;
    private final String password;
    private final String employeeName;
    private final EmployeeRole employeeRole;

    public SessionEmployee(String username, String password, String employeeName, EmployeeRole employeeRole) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
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

    public final EmployeeRole getEmployeeRole() {
        return this.employeeRole;
    }
}
