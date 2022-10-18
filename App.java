
// import Database.DBEmployee;
import Core.Models.Users.Developer;
import Core.Models.Users.SystemAdmin;
import Core.Models.Users.SystemEngineer;
import Database.Middleware.Users.EmployeeUtil;
import Database.Models.Users.EmployeeDatabase;
import UserInterface.ConsoleFrontend.LoginManager;
import UserInterface.ConsoleFrontend.SessionInitializeStatus;

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

        MainLoop: while (true) {

            SessionInitializeStatus status = new LoginManager().initializeSession();
            if (status.equals(SessionInitializeStatus.FAILED)) {
                break MainLoop;
            }

        }
    }

}
