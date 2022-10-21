package pitstop.UserInterface.Helpers;

public final class Scanner {

    private static final java.io.Console CONSOLE = System.console();
    private static final String PROMPT_END = StringFormat.formatWhite("");

    private Scanner() {
    }

    public static final String getString() {
        return getString("");
    }

    public static final String getString(String prompt) {
        return CONSOLE.readLine(formatPrompt(prompt));
    }

    public static final int getInt() throws Exception {
        return getInt("");
    }

    public static final int getInt(String prompt) throws Exception {
        String input = CONSOLE.readLine(formatPrompt(prompt));
        return Integer.parseInt(input);
    }

    public static final double getDouble() throws Exception {
        return getDouble("");
    }

    public static final double getDouble(String prompt) throws Exception {
        String input = CONSOLE.readLine(formatPrompt(prompt));
        return Double.parseDouble(input);
    }

    public static final float getFloat() throws Exception {
        return getFloat("");
    }

    public static final float getFloat(String prompt) throws Exception {
        String input = CONSOLE.readLine(formatPrompt(prompt));
        return Float.parseFloat(input);
    }

    public static final long getLong() throws Exception {
        return getLong("");
    }

    public static final long getLong(String prompt) throws Exception {
        String input = CONSOLE.readLine(formatPrompt(prompt));
        return Long.parseLong(input);
    }

    public static final char getChar() throws Exception {
        return getChar("");
    }

    public static final char getChar(String prompt) throws Exception {
        String input = CONSOLE.readLine(formatPrompt(prompt));
        if (input.length() > 1)
            throw new Exception("Character length is out of bounds");
        return input.toCharArray()[0];
    }

    private static final String formatPrompt(String rawPrompt) {
        if (!rawPrompt.endsWith("\n") && !rawPrompt.equals(""))
            rawPrompt += "\n";

        if (!rawPrompt.equals(""))
            rawPrompt = StringFormat.formatBlue(rawPrompt);

        return rawPrompt + PROMPT_END;
    }

}
