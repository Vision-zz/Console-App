package com.pitstop;

import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.SystemAdmin;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.Database.Middleware.Issues.DBIssueManager;
import com.pitstop.Database.Middleware.Users.DBEmployeeManager;
import com.pitstop.Database.Middleware.Users.EmployeeUtil;
import com.pitstop.Database.Models.Users.EmployeeDatabase;
import com.pitstop.UserInterface.ConsoleFrontend.AdminUI;
import com.pitstop.UserInterface.ConsoleFrontend.DeveloperUI;
import com.pitstop.UserInterface.ConsoleFrontend.EngineerUI;
import com.pitstop.UserInterface.ConsoleFrontend.LoginUI;
import com.pitstop.UserInterface.ConsoleFrontend.SessionInitializeStatus;
import com.pitstop.UserInterface.Helpers.Logger;
import com.pitstop.UserInterface.Helpers.Scanner;
import com.pitstop.UserInterface.SessionManager.Session;

public class App {

    static {
        SystemAdmin admin = new SystemAdmin("admin", "pass", "Shivaneesh", "0_ADMIN", DBIssueManager.getInstance(),
                DBEmployeeManager.getInstance());
        SystemEngineer engineer = new SystemEngineer("engineer", "pass", "Ragav Suresh", "0_ENGINEER",
                DBIssueManager.getInstance());
        Developer developer = new Developer("developer", "pass", "Sathya Narayanan", "0_DEVELOPER",
                DBIssueManager.getInstance());

        EmployeeDatabase employeeDB = EmployeeDatabase.getInstance();
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(admin));
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(engineer));
        employeeDB.add(EmployeeUtil.cloneToDBEmployee(developer));

    }

    public static void main(String[] args) {

        MainLoop: while (true) {

            SessionInitializeStatus status = new LoginUI().initializeSession();
            if (status.equals(SessionInitializeStatus.FAILED)) {
                break MainLoop;
            }

            Employee loggedInEmployee = Session.getInstance().getLoggedInAs();

            System.out.println("\033[1;92m" + "___________________________________" + "\033[0m\n");
            Logger.logSuccess("Welcome " + loggedInEmployee.getEmployeeName());
            System.out.println("\033[1;92m" + "___________________________________" + "\033[0m\n");

            

            switch (loggedInEmployee.getEmployeeRole()) {
                case SYSTEM_ADMIN:
                    new AdminUI((SystemAdmin) loggedInEmployee).mainMenu();
                    break;

                case SYSTEM_ENGINEER:
                    new EngineerUI((SystemEngineer) loggedInEmployee).mainMenu();
                    break;

                case DEVELOPER:
                    new DeveloperUI((Developer) loggedInEmployee).mainMenu();
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
