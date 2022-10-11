package Users;

public abstract class Employee {

    private final String username;
    private final String password;
    private final String employeeName;

    protected Employee(String username, String password, String employeeName) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
    }

    // Getters
    public final String getUsername() {
        return username;
    }

    public final String getPassword() {
        return password;
    }

    public final String getEmployeeName() {
        return employeeName;
    }

    public final String getEmployeeRole() {
        return this instanceof SystemAdmin ? "System Admin"
                : this instanceof SystemEngineer ? "System Engineer" : "Developer";
    }

}
