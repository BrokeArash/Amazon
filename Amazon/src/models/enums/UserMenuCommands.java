package models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserMenuCommands implements Command{
    OrderList("^\\s*list\\s+my\\s+orders\\s*$"),
    OrderDetails("^\\s*show\\s+order\\s+details\\s+-id\\s+(?<orderID>-?\\d+)\\s*$"),
    EditName("^\\s*edit\\s+name\\s+" +
            "-fn\\s+(?<firstName>\\S+)\\s+" +
            "-ln\\s+(?<lastName>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)\\s*$"),
    EditEmail("^\\s*edit\\s+email\\s+-e\\s+(?<email>\\S+)\\s+-p\\s+(?<password>\\S+)\\s*$"),
    EditPass("^\\s*edit\\s+password\\s+-np\\s+(?<newPass>\\S+)\\s+-op\\s+(?<oldPass>\\S+)\\s*$"),
    ShowInfo("^\\s*show\\s+my\\s+info\\s*$"),
    AddAddress("^\\s*add\\s+address\\s+" +
            "-country\\s+(?<country>.*)\\s+" +
            "-city\\s+(?<city>.*)\\s+" +
            "-street\\s+(?<street>.*)\\s+" +
            "-postal\\s+(?<postal>-?\\d+)\\s*$"),
    DeleteAddress("^\\s*delete\\s+address\\s+-id\\s+(?<id>-?\\d+)\\s*$"),
    ListAddress("^\\s*list\\s+my\\s+addresses\\s*$"),
    AddCard("^\\s*add\\s+a\\s+credit\\s+card\\s+" +
            "-number\\s+(?<cardNumber>-?\\d+)\\s+" +
            "-ed\\s+(?<expirationDate>\\S+)\\s+" +
            "-cvv\\s+(?<cvv>-?\\d+)\\s+" +
            "-initialValue\\s+(?<initialValue>-?\\d+\\.?\\d+|\\d+)\\s*$"),
    ChargeCard("^\\s*Charge\\s+credit\\s+card\\s+" +
            "-a\\s+(?<amount>-?\\d+\\.?\\d+|\\d+)\\s+" +
            "-id\\s+(?<id>-?\\d+)\\s*$"),
    CheckCardBalance("^\\s*Check\\s+credit\\s+card\\s+balance\\s+-d\\s+(?<id>-?\\d+)\\s*$"),
    ShowProductsInCart("^\\s*show\\s+products\\s+in\\s+cart\\s*$"),
    Checkout("^\\s*checkout\\s+" +
            "-card\\s+(?<cardID>-?\\d+)\\s+" +
            "-address\\s+(?<addressId>-?\\d+)\\s*$"),
    RemoveFromCart("^\\s*remove\\s+from\\s+cart\\s+" +
            "-product\\s+(?<productID>-?\\d+)\\s+" +
            "-quantity\\s+(?<amount>-?\\d+)\\s*$"),
    Back("^\\s*go\\s+back\\s*$"),
    GoToMenu("^\\s*go\\s+to\\s+-m\\s+(?<menu>\\S+)\\s*$"),
    CheckPostal("^\\d{10}$"),
    CheckCardNumber("^\\d{16}$"),
    CheckDate("^(0[1-9]|1[0-2])\\/\\d{2}$"),
    CheckCVV("^\\d{3,4}$");




    private final String pattern;

    UserMenuCommands(String pattern) {
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

