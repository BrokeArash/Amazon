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
                sortBy.replaceAll(" ", "");
                Result result = controller.showProducts(sortBy.trim());
                System.out.println(result);
                break;
            case ShowInformationProduct:
                Matcher showInformationProductMatcher = ProductMenuCommands.ShowInformationProduct.getMatcher(input);
                int productID = Integer.parseInt(showInformationProductMatcher.group("productId"));
                controller.showInformationProduct(productID);
                break;
            case RateProduct:
                Matcher rateProductMatcher = ProductMenuCommands.RateProduct.getMatcher(input);
                float number = Integer.parseInt(rateProductMatcher.group("number"));
                String message;
                if (rateProductMatcher.group("message") != null) {
                    message = rateProductMatcher.group("message");
                } else {
                    message = null;
                }
                productID = Integer.parseInt(rateProductMatcher.group("id"));
                result = controller.rateMessage(number, message, productID);
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
            case GoToMenu:
                Matcher menuMatcher = ProductMenuCommands.GoToMenu.getMatcher(input);
                String menu = menuMatcher.group("menu");
                Menu selectedMenu = Menu.findMenu(menu);
                App.setCurrentMenu(selectedMenu);
                System.out.println("Redirecting to the " + menu + " ...");
                break;
        }
    }
}
