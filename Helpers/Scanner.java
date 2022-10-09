package Helpers;

public final class Scanner {

    private static final java.io.Console CONSOLE = System.console();
    private static final String PROMPT_END = "\n" + StringFormat.formatWhite("");

    private Scanner() {
    }

    public static final String getString() {
        return getString(null);
    }

    public static final String getString(String prompt) {
        return prompt == null ? CONSOLE.readLine() : CONSOLE.readLine(prompt + PROMPT_END);
    }

    public static final int getInt() throws Exception {
        return getInt(null);
    }

    public static final int getInt(String prompt) throws Exception {
        String input = prompt == null ? CONSOLE.readLine() : CONSOLE.readLine(prompt + PROMPT_END);
        return Integer.parseInt(input);
    }

    public static final double getDouble() throws Exception {
        return getDouble(null);
    }

    public static final double getDouble(String prompt) throws Exception {
        String input = prompt == null ? CONSOLE.readLine() : CONSOLE.readLine(prompt + PROMPT_END);
        return Double.parseDouble(input);
    }

    public static final float getFloat() throws Exception {
        return getFloat(null);
    }

    public static final float getFloat(String prompt) throws Exception {
        String input = prompt == null ? CONSOLE.readLine() : CONSOLE.readLine(prompt + PROMPT_END);
        return Float.parseFloat(input);
    }

    public static final long getLong() throws Exception {
        return getLong(null);
    }

    public static final long getLong(String prompt) throws Exception {
        String input = prompt == null ? CONSOLE.readLine() : CONSOLE.readLine(prompt + PROMPT_END);
        return Long.parseLong(input);
    }

    public static final char getChar() throws Exception {
        return getChar(null);
    }

    public static final char getChar(String prompt) throws Exception {
        String input = prompt == null ? CONSOLE.readLine() : CONSOLE.readLine(prompt + PROMPT_END);
        if (input.length() > 1)
            throw new Exception("Character length is out of bounds");
        return input.toCharArray()[0];
    }

}
