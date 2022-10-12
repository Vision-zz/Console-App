package Middlewares.Employee;

import java.util.Collection;
import java.util.HashSet;

import Database.DBEmployee;
import Database.EmployeeDatabase;
import Users.Employee;

public class DBEmployeeManager implements AdminEmployeeManager {

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

}
