package pitstop.Database.Models.Users;

import java.util.HashMap;

import pitstop.Database.Models.DatabaseFunctions;

public final class EmployeeDatabase implements DatabaseFunctions<DBEmployee> {

    private final HashMap<String, DBEmployee> employees;

    private static EmployeeDatabase instance = null;

    public static EmployeeDatabase getInstance() {
        if (instance == null)
            instance = new EmployeeDatabase();
        return instance;
    }

    private EmployeeDatabase() {
        employees = new HashMap<String, DBEmployee>();
    }

    @Override
    public DBEmployee get(String username) {
        return this.employees.get(username);
    }

    @Override
    public HashMap<String, DBEmployee> getAll() {
        return new HashMap<String, DBEmployee>(employees);
    }

    @Override
    public void add(DBEmployee employee) {
        if (this.employees.containsKey(employee.getUsername())) {
            throw new RuntimeException("Employee with ID: " + employee.getUsername()
                    + " already exist. If you are trying to update a Employee, try using EmployeeDatabase.update() method");
        }
        this.employees.put(employee.getUsername(), employee);
    }

    @Override
    public void remove(DBEmployee employee) {
        if (!this.employees.containsKey(employee.getUsername())) {
            throw new RuntimeException("Employee with ID: " + employee.getUsername() + " do not exist. Operation cancelled");
        }
        this.employees.remove(employee.getUsername());
    }

    @Override
    public void udpate(DBEmployee employee) {
        if (!this.employees.containsKey(employee.getUsername())) {
            throw new RuntimeException("Employee with ID: " + employee.getUsername()
                    + " do not exist. If you are trying to add a Employee, try using EmployeeDatabase.add() method");
        }
        this.employees.put(employee.getUsername(), employee);

    }

}
