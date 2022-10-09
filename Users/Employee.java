package Users;

public abstract class Employee {
    private static int currentID = 0;

    private String username;
    private String password;
    private String employeeName;
    private final String employeeID;

    Employee(String username, String password, String employeeName, String employeeRole) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
        this.employeeID = employeeRole + "_" + currentID;
    }

    // Getters
    public final String getUsername() {
        return username;
    }

    public final String getPassword() {
        return password;
    }

    public final String getEmployeeID() {
        return employeeID;
    }

    public final String getEmployeeName() {
        return employeeName;
    }


    // Setters
    public final void setUsername() {

    }

    public final void setPassword() {

    }

    public final void setEmployeeName(String employeeName) {

    }

    abstract void start();

}
