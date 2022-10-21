package pitstop;

// import Database.DBEmployee;
import pitstop.Core.Models.Users.Developer;
import pitstop.Core.Models.Users.Employee;
import pitstop.Core.Models.Users.SystemAdmin;
import pitstop.Core.Models.Users.SystemEngineer;
import pitstop.Database.Middleware.Issues.DBIssueManager;
import pitstop.Database.Middleware.Users.DBEmployeeManager;
import pitstop.Database.Middleware.Users.EmployeeUtil;
import pitstop.Database.Models.Users.EmployeeDatabase;
import pitstop.UserInterface.ConsoleFrontend.AdminUIManager;
import pitstop.UserInterface.ConsoleFrontend.DeveloperUIManager;
import pitstop.UserInterface.ConsoleFrontend.EngineerUIManager;
import pitstop.UserInterface.ConsoleFrontend.LoginManager;
import pitstop.UserInterface.ConsoleFrontend.SessionInitializeStatus;
import pitstop.UserInterface.Helpers.Logger;
import pitstop.UserInterface.Helpers.Scanner;
import pitstop.UserInterface.SessionManager.Session;

public class App {

    static {
        SystemAdmin admin = new SystemAdmin("shiva", "pass", "Shivaneesh", DBIssueManager.getInstance(),
                DBEmployeeManager.getInstance());
        SystemEngineer engineer = new SystemEngineer("sankar", "pass", "Ragav Suresh",
                DBIssueManager.getInstance());
        Developer developer = new Developer("sathya", "pass", "Sathya Narayanan",
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

            System.out.println("\033[1;92m" + "___________________________________" + "\033[0m\n");
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

        System.out.println("\033[1;92m" + "_______________ ADIOS _______________" + "\033[0m\n");


    }

}