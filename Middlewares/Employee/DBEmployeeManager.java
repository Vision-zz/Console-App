package Middlewares.Employee;

import java.util.Collection;
import java.util.HashSet;

import Database.DBEmployee;
import Database.EmployeeDatabase;
import Users.Employee;
import Users.EmployeeRole;

public class DBEmployeeManager implements AdminEmployeeManager, EmployeeSignupManager {

	private static DBEmployeeManager instance = null;

	public static DBEmployeeManager getInstance() {
		if (instance == null)
			instance = new DBEmployeeManager();
		return instance;
	}

	private DBEmployeeManager() {
	}

	@Override
	public Collection<Employee> getAllEmployees() {
		Collection<DBEmployee> dbEmployees = EmployeeDatabase.getInstance().getAll().values();
		Collection<Employee> allEmployees = new HashSet<>();
		dbEmployees.forEach(employee -> {
			allEmployees.add(EmployeeUtil.cloneToEmployee(employee));
		});
		return allEmployees;
	}

	@Override
	public void signUp(String username, String password, String employeeName, EmployeeRole employeeRole) {
		DBEmployee dbEmployee = new DBEmployee(username, password, employeeName, employeeRole);
		EmployeeDatabase.getInstance().add(dbEmployee);

	}

}
