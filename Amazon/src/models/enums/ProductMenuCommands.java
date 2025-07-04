package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProductMenuCommands implements Command{
    ShowProducts("^\\s*show\\s+products\\s+" +
            "-sortBy\\s+(?<sortBy>rate|\\s*higher\\s+price\\s+to\\s+lower\\s*" +
            "|\\s*lower\\s+price\\s+to\\s+higher\\s*|\\s*number\\s+of\\s+sold\\s*)$"),
    ShowNext("^\\s*show\\s+next\\s+10\\s+products\\s*$"),
    ShowPast("^\\s*show\\s+past\\s+10\\s+products\\s*$"),
    ShowInformationProduct("^\\s*show\\s+information\\s+of\\s+-id\\s+(?<productId>-?\\d+)\\s*$"),
    RateProduct("^\\s*Rate\\s+product\\s+" +
            "-r\\s+(?<number>-?\\d+|-?\\d+(\\.\\d+)?)" +
            "(?:\\s*-m\\s+(?<message>\".*\"))?\\s*" +
            "-id\\s+(?<id>-?\\d+)\\s*$"),
    AddToCart("^\\s*add\\s+to\\s+cart\\s+" +
            "-product\\s+(?<productID>-?\\d+)\\s+" +
            "-quantity\\s+(?<amount>-?\\d+|-?\\d+(\\.\\d+)?)\\s*$"),
    Back("^\\s*go\\s+back\\s*");


    private final String pattern;

    ProductMenuCommands(String pattern) {
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

