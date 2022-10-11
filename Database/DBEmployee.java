package Database;

public final class DBEmployee {
	private final String username;
    private final String password;
    private final String employeeName;
	private final String employeeRole;

    public DBEmployee(String username, String password, String employeeName, String employeeRole) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
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

	public final String getEmployeeRole() {
		return this.employeeRole;
	}


}
