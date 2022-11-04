package com.pitstop;

import java.io.IOException;

import com.pitstop.Core.Models.Users.Developer;
import com.pitstop.Core.Models.Users.Employee;
import com.pitstop.Core.Models.Users.SystemAdmin;
import com.pitstop.Core.Models.Users.SystemEngineer;
import com.pitstop.StorageManager.DBStorageManager;
import com.pitstop.StorageManager.JSONDataParser;
import com.pitstop.StorageManager.StorageLoadTypes;
import com.pitstop.UserInterface.ConsoleFrontend.AdminUI;
import com.pitstop.UserInterface.ConsoleFrontend.DeveloperUI;
import com.pitstop.UserInterface.ConsoleFrontend.EngineerUI;
import com.pitstop.UserInterface.ConsoleFrontend.SessionInitializeStatus;
import com.pitstop.UserInterface.ConsoleFrontend.StartupUI;
import com.pitstop.UserInterface.Helpers.Logger;
import com.pitstop.UserInterface.Helpers.Scanner;
import com.pitstop.UserInterface.SessionManager.Session;

public class App {

    public static void main(String[] args) {

        do {

            String loadConfirmation = Scanner.getString("Do you want to load previously saved data? [Y]es / [N]o");
            if (!loadConfirmation.matches("[YyNn]")) {
                Logger.logWarning("Please select a valid option");
                continue;
            }

            if (loadConfirmation.toUpperCase().equals("Y")) {

                StorageLoadTypes loadType;

                do {
                    Logger.logInfo("Select the type of data to load into DB",
                            "1. Default data | 2. Previous session data");

                    String type = Scanner.getString();
                    if (!type.matches("[12]")) {
                        Logger.logWarning("Please select a valid option");
                        continue;
                    }

                    loadType = type.equals("1") ? StorageLoadTypes.DEFAULT
                            : StorageLoadTypes.PREVIOUS_SESSION;
                    break;
                } while (true);

                DBStorageManager manager = DBStorageManager.getInstance(JSONDataParser.getInstance());

                manager.loadData(loadType);

            }

            break;
        } while (true);

        MainLoop: while (true) {

            SessionInitializeStatus status = new StartupUI().showUserInterface();
            if (status.equals(SessionInitializeStatus.FAILED)) {
                break MainLoop;
            }

            Employee loggedInEmployee = Session.getInstance().getLoggedInAs();

            System.out.println("\033[1;92m" + "___________________________________" + "\033[0m\n");
            Logger.logSuccess("Welcome " + loggedInEmployee.getEmployeeName());
            System.out.println("\033[1;92m" + "___________________________________" + "\033[0m\n");

            switch (loggedInEmployee.getEmployeeRole()) {
                case SYSTEM_ADMIN:
                    new AdminUI((SystemAdmin) loggedInEmployee).showUserInterface();
                    break;

                case SYSTEM_ENGINEER:
                    new EngineerUI((SystemEngineer) loggedInEmployee).showUserInterface();
                    break;

                case DEVELOPER:
                    new DeveloperUI((Developer) loggedInEmployee).showUserInterface();
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

        do {
            String input = Scanner.getString("Do you want to save your current session data? [Y]es / [N]o");
            if (!input.matches("[YyNn]")) {
                Logger.logWarning("Please select a valid option");
                continue;
            }

            if (input.toUpperCase().equals("Y")) {
                DBStorageManager manager = DBStorageManager.getInstance(JSONDataParser.getInstance());
                try {
                    manager.saveData();
                } catch (IOException e) {
                    Logger.logWarning("Error while writing to JSON file");
                }
            }

            break;
        } while (true);

        System.out.println("\033[1;92m" + "_______________ ADIOS _______________" + "\033[0m\n");

    }

}
