package views;

import controllers.ProductMenuController;
import models.App;
import models.Result;
import models.enums.LoginMenuCommands;
import models.enums.Menu;
import models.enums.ProductMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProductMenu implements AppMenu{
    final ProductMenuController controller = new ProductMenuController();
    String mainSortBy;
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        ProductMenuCommands matchedCommand = null;
        for (ProductMenuCommands command : ProductMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null) {
            System.out.println("invalid command");
        }

        switch (matchedCommand) {
            case ShowProducts:
                Matcher showProductsMatcher = ProductMenuCommands.ShowProducts.getMatcher(input);
                String sortBy = showProductsMatcher.group("sortBy");
                mainSortBy = sortBy;
                sortBy = sortBy.replaceAll(" ", "");
                controller.showProducts(sortBy.trim(), mainSortBy);
                break;
            case ShowNext:
                controller.showNext(mainSortBy);
                break;
            case ShowPast:
                controller.showPast(mainSortBy);
                break;
            case ShowInformationProduct:
                Matcher showInformationProductMatcher = ProductMenuCommands.ShowInformationProduct.getMatcher(input);
                int productID = Integer.parseInt(showInformationProductMatcher.group("productId"));
                controller.showInformationProduct(productID);
                break;
            case RateProduct:
                Matcher rateProductMatcher = ProductMenuCommands.RateProduct.getMatcher(input);
                float number = Float.parseFloat(rateProductMatcher.group("number"));
                String message;
                if (rateProductMatcher.group("message") != null) {
                    message = rateProductMatcher.group("message");
                } else {
                    message = null;
                }
                productID = Integer.parseInt(rateProductMatcher.group("id"));
                Result result = controller.rateMessage(number, message, productID);
                System.out.println(result);
                break;
            case AddToCart:
                Matcher addToCartMatcher = ProductMenuCommands.AddToCart.getMatcher(input);
                productID = Integer.parseInt(addToCartMatcher.group("productID"));
                int amount = Integer.parseInt(addToCartMatcher.group("amount"));
                result = controller.addToCart(productID, amount);
                System.out.println(result);
                break;
            case Back:
                System.out.println(controller.back());
                break;
        }
    }
}
