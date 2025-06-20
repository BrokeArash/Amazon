package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Command {

    GoToMenu("^\\s*go\\s+to\\s+-m\\s+(?<menu>\\S+)\\s*$"),
    Exit("^\\s*exit\\s*$");

    private final String pattern;

    MainMenuCommands(String pattern) {
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
