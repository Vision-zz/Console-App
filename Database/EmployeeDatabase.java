package Database;

import java.util.HashMap;

import Helpers.Logger;
import Users.Employee;

public class EmployeeDatabase implements DatabaseFunctions<Employee> {

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
    public Employee get(String ID) {
        return this.employees.get(ID);
    }

    @Override
    public void add(Employee employee) throws Exception {
        if (this.employees.containsKey(employee.getEmployeeID())) {
            throw new Exception("Employee with ID: " + employee.getEmployeeID()
                    + " already exist. If you are trying to update a Employee, try using EmployeeDatabase.update() method");
        }
        this.employees.put(employee.getEmployeeID(), employee);
    }

    @Override
    public void remove(Employee employee) {
        if (!this.employees.containsKey(employee.getEmployeeID())) {
            Logger.logInfo("Employee with ID: " + employee.getEmployeeID() + " do not exist. Operation cancelled");
            return;
        }
        this.employees.remove(employee.getEmployeeID());
    }

    @Override
    public void udpate(Employee employee) throws Exception {
        if (!this.employees.containsKey(employee.getEmployeeID())) {
            throw new Exception("Employee with ID: " + employee.getEmployeeID()
                    + " do not exist. If you are trying to add a Employee, try using EmployeeDatabase.add() method");
        }
        this.employees.put(employee.getEmployeeID(), employee);

    }

}
