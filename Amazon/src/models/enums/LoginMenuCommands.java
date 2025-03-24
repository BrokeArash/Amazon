package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements Command{

    Logout("\\s*logout\\s*"),
    CreateUser("^\\s*create\\s+a\\s+user\\s+account\\s+" +
            "-fn\\s+(?<firstName>[A-Z]{1}[a-z]+)\\s+" +
            "-ln\\s+(?<lastName>[A-Z]{1}[a-z]{2,})\\s+" +
            "-p\\s+(?<password>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})\\s+" +
            "-rp\\s+(?<reEnteredPassword>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})\\s+" +
            "-e\\s+(?<emailAddress>[a-zA-Z0-9.]+@[a-z]+.com\\s*$"),
    CreateStore("^\\s*create\\s+a\\s+store\\s+account\\s+" +
            "-b\\s+(?<brand>.*\\S{1})\\s+" +
            "-p\\s+(?<password>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})\\s+" +
            "-rp\\s+(?<reEnterPassword>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})\\s+" +
            "-e\\s+(?<email>[a-zA-Z0-9.]+@[a-z]+.com)\\s*"),
    LoginUser("\\s*login\\s+as\\s+user\\s+" +
            "-e\\s+(?<email>[a-zA-Z0-9.]+@[a-z]+.com))\\s+" +
            "-p\\s+(?<password>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})"),
    LoginStore("\\s*login\\s+as\\s+store\\s+" +
            "-e\\s+(?<email>[a-zA-Z0-9.]+@[a-z]+.com))\\s+" +
            "-p\\s+(?<password>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})"),
    DeleteAccount("\\s*delete account\\s+" +
            "-p\\s+(?<password>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})\\s+" +
            "-rp\\s+(?<reEnterPassword>(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,})\\s*"),
    Back("\\s*go back\\s*"),
    GoTOMenu("\\s*go to\\s+-m");

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
