package UserInterface.Helpers;

public final class Logger {

    public static final void logSuccess(String... message) {
        for (String m : message) {
            System.out.println(StringFormat.formatGreen(m));
        }
    }

    public static final void logInfo(String... message) {
        for (String m : message) {
            System.out.println(StringFormat.formatBlue(m));
        }
    }

    public static final void logWarning(String... message) {
        for (String m : message) {
            System.out.println(StringFormat.formatYellow(m));
        }
    }

    public static final void logError(String... message) {
        for (String m : message) {
            System.out.println(StringFormat.formatRed(m));
        }
    }

    public static final void logMessage(String... message) {
        for (String m : message) {
            System.out.println(StringFormat.formatWhite(m));
        }
    }
}
