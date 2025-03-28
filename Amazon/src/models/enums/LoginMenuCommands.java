package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements Command{

    Logout("\\s*logout\\s*"),
    CreateUser("^\\s*create\\s+a\\s+user\\s+account\\s+" +
            "-fn\\s+(?<firstName>.*)\\s+" +
            "-ln\\s+(?<lastName>.*)\\s+" +
            "-p\\s+(?<password>.*)\\s+" +
            "-rp\\s+(?<reEnteredPassword>.*)\\s+" +
            "-e\\s+(?<emailAddress>.*)\\s*$"),
    CreateStore("^\\s*create\\s+a\\s+store\\s+account\\s+" +
            "-b\\s+(?<brand>\".*\")\\s+" +
            "-p\\s+(?<password>.*)\\s+" +
            "-rp\\s+(?<reEnterPassword>.*)\\s+" +
            "-e\\s+(?<email>.*)\\s*$"),
    LoginUser("^\\s*login\\s+as\\s+user\\s+" +
            "-e\\s+(?<email>.*)\\s+" +
            "-p\\s+(?<password>.*)\\s*$"),
    LoginStore("^\\s*login\\s+as\\s+store\\s+" +
            "-e\\s+(?<email>.*)\\s+" +
            "-p\\s+(?<password>.*)\\s*$"),
    DeleteAccount("^\\s*delete\\s+account\\s+" +
            "-p\\s+(?<password>.*)\\s+" +
            "-rp\\s+(?<reEnterPassword>.*)\\s*$"),
    Back("^\\s*go\\s+back\\s*$"),
    GoToMenu("^\\s*go\\s+to\\s+-m\\s+(?<menu>\\S+)\\s*$"),
    CheckName("^[A-Z]{1}[a-z]+$"),
    CheckPassword("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,}$"),
    CheckEmail("^[a-zA-Z0-9]*\\.?[a-zA-Z0-9]+@[a-z]+\\.com$"),
    CheckStoreName(".{5,}");


    private final String pattern;

    LoginMenuCommands(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
