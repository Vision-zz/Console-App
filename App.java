// import Database.DBEmployee;
import Database.EmployeeDatabase;
import Helpers.Logger;
import Helpers.Scanner;
import Middlewares.Employee.EmployeeUtil;
// import SessionManager.Session;
// import SessionManager.SignInStatus;
import Users.Developer;
import Users.SystemAdmin;
import Users.SystemEngineer;

public class App {

    static {
        SystemAdmin admin = new SystemAdmin("shiva", "verysecuredpass", "Shivaneesh");
        SystemEngineer engineer = new SystemEngineer("sankar", "anothersecuredpass", "Ragav Suresh");
        Developer developer = new Developer("sathya", "yetasecuredpass", "Sathya Narayanan");

        EmployeeDatabase employeeDB = EmployeeDatabase.getInstance();
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(admin));
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(engineer));
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(developer));

    }

    public static void main(String[] args) {

        // TODO Create a Middleware between UI Login flow and the Database

        MainLoop: while (true) {

            Logger.logSuccess("---- Jogo Pitstop ----", "Select an option below");

            LoginInputLoop: do {

                String input = Scanner.getString("1. Sign In | 2. Sign Up | 3. Exit");
                if (!input.matches("[12]")) {
                    Logger.logWarning("Please select a valid option");
                    continue;
                }

                if (input.equals("3"))
                    break MainLoop;

                break LoginInputLoop;

            } while (true);

            CredentialsLoop: do {


                // TODO Finish this shit
                // String username = Scanner.getString("Enter your username");
                // String password = Scanner.getString("Enter your password");


                // SignInStatus signInStatus =  Session.getInstance().signIn(username, password);

                // switch (signInStatus) {
                //     case :
                        
                //         break;
                
                //     default:
                //         break;
                // }
                // Logger.logSuccess("Logged in as " + username);

                break CredentialsLoop;

            } while (true);

            Scanner.getString("Press any key to continue . . .");
            System.out.println("Done");

            // TODO Start the actual work flow

        }

    }

}
