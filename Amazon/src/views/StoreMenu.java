package views;

import controllers.StoreMenuController;
import models.App;
import models.Result;
import models.Store;
import models.enums.Menu;
import models.enums.ProductMenuCommands;
import models.enums.StoreMenuCommands;
import models.enums.UserType;

import java.util.Scanner;
import java.util.regex.Matcher;

public class StoreMenu implements AppMenu{
    StoreMenuController controller = new StoreMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        StoreMenuCommands matchedCommand = null;
        for (StoreMenuCommands command : StoreMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null) {
            System.out.println("invalid command");
        } else if (App.getLoggedIn() == null || App.getLoggedInType() == UserType.Costumer) {
            System.out.println("You should login as store before accessing the store menu.");
        } else {
            switch (matchedCommand) {
                case AddProduct:
                    Matcher addProductMatcher = StoreMenuCommands.AddProduct.getMatcher(input);
                    String name = addProductMatcher.group("name").trim();
                    name = name.substring(1, name.length()-1);
                    double producerCost = Double.parseDouble(addProductMatcher.group("producerCost"));
                    double price = Double.parseDouble((addProductMatcher.group("price")));
                    String aboutThisItem = addProductMatcher.group("aboutThisItem");
                    aboutThisItem = aboutThisItem.substring(1, aboutThisItem.length()-1);
                    int numberOfProductsToSell = Integer.parseInt(addProductMatcher.group("numberOfProductsToSell"));
                    Result result = controller.addProduct(name, producerCost, price, aboutThisItem, numberOfProductsToSell);
                    System.out.println(result);
                    break;
                case ApplyDiscount:
                    Matcher applyDiscountMatcher = StoreMenuCommands.ApplyDiscount.getMatcher(input);
                    int productID = Integer.parseInt(applyDiscountMatcher.group("productID"));
                    int discountPercentage = Integer.parseInt(applyDiscountMatcher.group("discountPercentage"));
                    int quantity = Integer.parseInt(applyDiscountMatcher.group("quantity"));
                    result = controller.applyDiscount(productID, discountPercentage, quantity);
                    System.out.println(result);
                    break;
                case ShowProfit:
                    controller.showProfit();
                    break;
                case ShowListOfProducts:
                    controller.showListOfProducts();
                    break;
                case AddStock:
                    Matcher addStockMatcher = StoreMenuCommands.AddStock.getMatcher(input);
                    int productId = Integer.parseInt(addStockMatcher.group("productId"));
                    int amount = Integer.parseInt(addStockMatcher.group("amount"));
                    Store mainUser = (Store) App.getLoggedIn();
                    result = controller.addStock(productId, amount, mainUser);
                    System.out.println(result);
                    break;
                case UpdatePrice:
                    Matcher updatePriceMatcher = StoreMenuCommands.UpdatePrice.getMatcher(input);
                    productId = Integer.parseInt(updatePriceMatcher.group("productId"));
                    double newPrice = Double.parseDouble(updatePriceMatcher.group("newPrice"));
                    result = controller.updatePrice(productId, newPrice);
                    System.out.println(result);
                    break;
                case Back:
                    System.out.println(controller.back());
                    break;
            }
        }
    }
}
