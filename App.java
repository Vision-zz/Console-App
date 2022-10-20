
// import Database.DBEmployee;
import Core.Models.Users.Developer;
import Core.Models.Users.Employee;
import Core.Models.Users.SystemAdmin;
import Core.Models.Users.SystemEngineer;
import Database.Middleware.Issues.DBIssueManager;
import Database.Middleware.Users.DBEmployeeManager;
import Database.Middleware.Users.EmployeeUtil;
import Database.Models.Users.EmployeeDatabase;
import UserInterface.ConsoleFrontend.AdminUIManager;
import UserInterface.ConsoleFrontend.DeveloperUIManager;
import UserInterface.ConsoleFrontend.EngineerUIManager;
import UserInterface.ConsoleFrontend.LoginManager;
import UserInterface.ConsoleFrontend.SessionInitializeStatus;
import UserInterface.Helpers.Logger;
import UserInterface.Helpers.Scanner;
import UserInterface.SessionManager.Session;

public class App {

    static {
        SystemAdmin admin = new SystemAdmin("shiva", "verysecuredpass", "Shivaneesh", DBIssueManager.getInstance(),
                DBEmployeeManager.getInstance());
        SystemEngineer engineer = new SystemEngineer("sankar", "anothersecuredpass", "Ragav Suresh",
                DBIssueManager.getInstance());
        Developer developer = new Developer("sathya", "yetasecuredpass", "Sathya Narayanan",
                DBIssueManager.getInstance());

        EmployeeDatabase employeeDB = EmployeeDatabase.getInstance();
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(admin));
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(engineer));
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(developer));

    }

    public static void main(String[] args) {

        MainLoop: while (true) {

            SessionInitializeStatus status = new LoginManager().initializeSession();
            if (status.equals(SessionInitializeStatus.FAILED)) {
                break MainLoop;
            }

            Employee loggedInEmployee = Session.getInstance().getLoggedInAs();

            Logger.logSuccess("Welcome " + loggedInEmployee.getEmployeeName());
            System.out.println("\033[1;92m" + "___________________________________" + "\033[0m\n");

            switch (loggedInEmployee.getEmployeeRole()) {
                case SYSTEM_ADMIN:
                    new AdminUIManager((SystemAdmin) loggedInEmployee).mainMenu();
                    break;

                case SYSTEM_ENGINEER:
                    new EngineerUIManager((SystemEngineer) loggedInEmployee).mainMenu();
                    break;

                case DEVELOPER:
                    new DeveloperUIManager((Developer) loggedInEmployee).mainMenu();
                    break;
                default:
                    Logger.logError("Unkonwn employee role");
                    break;
            }

            Logger.logInfo("Save your login info for ease of access during next time. ");

            do {

                Logger.logInfo("Do you want to save your login? [Y]es / [N]o");
                String input = Scanner.getString();

                if (!input.matches("[yYnN]")) {
                    Logger.logWarning("Please select a valid option");
                    continue;
                }

                Session.getInstance().logout(input.toUpperCase().equals("Y"));
                break;

            } while (true);

            Logger.logSuccess("--- Logged out ---");
            System.out.println("\033[1;91m" + "___________________________________" + "\033[0m\n");

        }
    }

}
