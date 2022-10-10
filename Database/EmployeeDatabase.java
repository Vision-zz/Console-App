package Database;

import java.util.HashMap;

import Helpers.Logger;
import Users.Employee;

public final class EmployeeDatabase implements DatabaseFunctions<Employee> {

    private final HashMap<String, Employee> employees;

    private static EmployeeDatabase instance = null;

    public static EmployeeDatabase getInstance() {
        if (instance == null)
            instance = new EmployeeDatabase();
        return instance;
    }

    private EmployeeDatabase() {
        employees = new HashMap<String, Employee>();
    }

    @Override
    public Employee get(String username) {
        return this.employees.get(username);
    }

    @Override
    public void add(Employee employee) {
        if (this.employees.containsKey(employee.getUsername())) {
            Logger.logWarning("Employee with ID: " + employee.getUsername()
                    + " already exist. If you are trying to update a Employee, try using EmployeeDatabase.update() method");
            return;
        }
        this.employees.put(employee.getUsername(), employee);
    }

    @Override
    public void remove(Employee employee) {
        if (!this.employees.containsKey(employee.getUsername())) {
            Logger.logWarning("Employee with ID: " + employee.getUsername() + " do not exist. Operation cancelled");
            return;
        }
        this.employees.remove(employee.getUsername());
    }

    @Override
    public void udpate(Employee employee) {
        if (!this.employees.containsKey(employee.getUsername())) {
            Logger.logWarning("Employee with ID: " + employee.getUsername()
                    + " do not exist. If you are trying to add a Employee, try using EmployeeDatabase.add() method");
            return;
        }
        this.employees.put(employee.getUsername(), employee);

    }

}
