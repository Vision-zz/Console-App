package Users;

import Helpers.Logger;
import Helpers.Scanner;
import Helpers.StringFormat;

public abstract class Employee {

    private String username;
    private String password;
    private String employeeName;

    protected Employee(String username, String password, String employeeName) {
        this.username = username;
        this.password = password;
        this.employeeName = employeeName;
    }

    // Getters
    public final String getUsername() {
        return username;
    }

    public final String getPassword() {
        return password;
    }

    public final String getEmployeeName() {
        return employeeName;
    }

    public final String getEmployeeRole() {
        return this instanceof SystemAdmin ? "System Admin"
                : this instanceof SystemEngineer ? "System Engineer" : "Developer";
    }

    // Setters
    public final void changeUsername() {
        String username = Scanner.getString(StringFormat.formatBlue("Enter the new username"));
        String confirmationPass;
        do {
            confirmationPass = Scanner
                    .getString(StringFormat.formatBlue("Enter your password to confirm your action"));
            if (!confirmationPass.equals(this.password)) {
                Logger.logError("Incorrect password. Please retry");
                continue;
            }
            break;
        } while (true);
        Logger.logInfo("Old username: " + this.username, "New username: " + username);
        this.username = username;
        Logger.logSuccess("Successfully changed username.");
    }

    public final void changePassword() {
        do {
            String confirmationPass = Scanner
                    .getString(StringFormat.formatBlue("Enter your old password to confirm your action"));
            if (!confirmationPass.equals(this.password)) {
                Logger.logError("Incorrect password. Please retry");
                continue;
            }
            break;
        } while (true);

        String newPassword = Scanner.getString(StringFormat.formatBlue("Enter new password"));

        do {
            String confirmNewPass = Scanner.getString(StringFormat.formatBlue("Confirm your new password"));
            if (!confirmNewPass.equals(newPassword)) {
                Logger.logError("Passwords do not match. Please retry");
                continue;
            }
            break;
        } while (true);
        this.password = newPassword;
        Logger.logSuccess("Password change successfull");
    }

    public final void changeName(String employeeName) {
        String name = Scanner.getString(StringFormat.formatBlue("Enter your full name"));
        this.employeeName = name;
        Logger.logSuccess("Successfully changed your full name");
    }

    public abstract void start();

}
