package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreMenuCommands implements Command{
    AddProduct("^\\s*add\\s+product\\s+-n\\s+(?<name>\".*\")\\s+-pc\\s+(?<productCost>-?\\d+\\.\\d+|-?\\d+)\\s+-p\\s+(?<price>-?\\d+\\.\\d+|-?\\d+)\\s+-about\\s+(?<aboutThisItem>\".*\")\\s+-np\\s+(?<numberOfProductsToSell>-?\\d+)\\s*$"),
    ApplyDiscount("^\\s*apply\\s+discount\\s+-p\\s+(?<productID>-?\\d+)\\s+-d\\s+(?<discountPercentage>-?\\d+)\\s+-q\\s+(?<quantity>-?\\d+)\\s*$"),
    ShowProfit("^\\s*show\\s+profit\\s*$"),
    ShowListOfProducts("^\\s*show\\s+list\\s+of\\s+products\\s*$"),
    AddStock("^\\s*add\\s+stock\\s+-product\\s+(?<productId>-?\\d+)\\s+-amount\\s+(?<amount>-?\\d+)\\s*$"),
    UpdatePrice("^\\s*update\\s+price\\s+-product\\s+(?<productId>-?\\d+)\\s+-price\\s+(?<newPrice>\\d+.?\\d+)\\s*$"),
    Back("^\\s*go\\s*back\\s*$"),
    GoTOMenu("^\\s*go\\s+to\\s+-m\\s+(?<menu>\\S+)\\s*$");

    private final String pattern;

    StoreMenuCommands(String pattern) {
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
