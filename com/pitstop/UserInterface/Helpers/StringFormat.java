package com.pitstop.UserInterface.Helpers;

public final class StringFormat {

    private StringFormat() {
    }

    private static final String RESET_COLOR = "\033[0m";
    private static final String GREEN = "\033[1;92m[ + ] " + RESET_COLOR;
    private static final String BLUE = "\033[1;94m[ i ] " + RESET_COLOR;
    private static final String YELLOW = "\033[1;93m[ - ] " + RESET_COLOR;
    private static final String RED = "\033[1;91m[ ! ] " + RESET_COLOR;
    private static final String WHITE = "\033[1;97m[ > ] " + RESET_COLOR;

    public static final String formatGreen(String message) {
        return GREEN + message;
    }

    public static final String formatBlue(String message) {
        return BLUE + message;
    }

    public static final String formatYellow(String message) {
        return YELLOW + message;
    }

    public static final String formatRed(String message) {
        return RED + message;
    }

    public static final String formatWhite(String message) {
        return WHITE + message;
    }
}
