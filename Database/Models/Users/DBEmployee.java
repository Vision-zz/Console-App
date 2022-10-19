package Database.Models.Users;

import Core.Models.Users.EmployeeRole;

public final class DBEmployee {
	private final String username;
    private final String password;
    private final String employeeName;
    private final String employeeID;
	private final EmployeeRole employeeRole;

    public DBEmployee(String username, String password, String employeeName, String employeeID, EmployeeRole employeeRole) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
        this.employeeID = employeeID;
		this.employeeRole = employeeRole;
    }

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
