import Database.EmployeeDatabase;
import Database.Session;
import Helpers.Logger;
import Helpers.Scanner;
import Users.Developer;
import Users.Employee;
import Users.SystemAdmin;
import Users.SystemEngineer;

public class App {

    static {
        SystemAdmin admin = new SystemAdmin("shiva", "verysecuredpass", "Shivaneesh");
        SystemEngineer engineer = new SystemEngineer("sankar", "anothersecuredpass", "Ragav Suresh");
        Developer developer = new Developer("sathya", "yetasecuredpass", "Sathya Narayanan");

        EmployeeDatabase employeeDB = EmployeeDatabase.getInstance();
        employeeDB.add(admin);
        employeeDB.add(engineer);
        employeeDB.add(developer);
    }

    public static void main(String[] args) {

        MainLoop: while (true) {

            Logger.logSuccess("---- Jogo Pitstop ----", "Select an option below");

            LoginInputLoop: do {

                String input = Scanner.getString("1. Sign In | 2. Exit");
                if (!input.matches("[12]")) {
                    Logger.logWarning("Please select a valid option");
                    continue;
                }

                if (input.equals("2"))
                    break MainLoop;

                break LoginInputLoop;

            } while (true);

            CredentialsLoop: do {
                
                String username = Scanner.getString("Enter your username");
                String password = Scanner.getString("Enter your password");

                EmployeeDatabase employeeDB = EmployeeDatabase.getInstance();
                Employee employee = employeeDB.get(username);

                if(employee == null || !employee.getPassword().equals(password)) {
                    Logger.logWarning("Incorrect login credentials");
                    String input = Scanner.getString("Press any key to retry or EXIT to exit");
                    if(input.equals("EXIT")) 
                        break MainLoop;
                    continue;
                }

                Session.login(employee);
                Logger.logSuccess("Logged in as " + employee.getEmployeeName());

                break  CredentialsLoop;

            } while (true);

            Scanner.getString("Press any key to continue . . .");
            System.out.println("Done");

            // TODO Start the actual work flow

        }

    }

}
