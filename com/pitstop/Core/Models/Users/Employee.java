package pitstop.Core.Models.Users;

public abstract class Employee {

    private final String username;
    private final String password;
    private final String employeeName;
    private final String employeeID;
    private final EmployeeRole employeeRole;

    private static int currentID = 0;

    public Employee(String username, String password, String employeeName, EmployeeRole employeeRole,
            String employeeID) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
        this.employeeID = employeeID;
        this.employeeRole = employeeRole;
    }

    public Employee(String username, String password, String employeeName, EmployeeRole employeeRole) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
        this.employeeID = employeeRole + "_" + currentID++;
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
