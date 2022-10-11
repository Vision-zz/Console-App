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
        return this.username;
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getEmployeeName() {
        return this.employeeName;
    }

    public final String getEmployeeRole() {
        return this instanceof SystemAdmin ? "System Admin"
                : this instanceof SystemEngineer ? "System Engineer" : "Developer";
    }

}
