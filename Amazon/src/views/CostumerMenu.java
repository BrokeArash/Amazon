package views;

import controllers.CostumerMenuController;
import models.App;
import models.Result;
import models.enums.CostumerMenuCommands;
import models.enums.LoginMenuCommands;
import models.enums.Menu;
import models.enums.UserType;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CostumerMenu implements AppMenu {
    final CostumerMenuController controller = new CostumerMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        CostumerMenuCommands matchedCommand = null;
        for (CostumerMenuCommands command : CostumerMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null ||
                matchedCommand == CostumerMenuCommands.CheckCardNumber ||
                matchedCommand == CostumerMenuCommands.CheckCVV ||
                matchedCommand == CostumerMenuCommands.CheckDate ||
                matchedCommand == CostumerMenuCommands.CheckPostal) {
            System.out.println("invalid command");
        }
        else if (App.getLoggedIn() == null || App.getLoggedInType() == UserType.Store) {
            System.out.println("You need to login as a user before accessing the user menu.");
        }else {
            switch (matchedCommand) {
                case OrderList:
                    System.out.println(controller.orderList());
                    break;
                case OrderDetails:
                    Matcher orderMatcher = CostumerMenuCommands.OrderDetails.getMatcher(input);
                    int id = Integer.parseInt(orderMatcher.group("orderID")); //TODO: might be string
                    Result result = controller.orderDetails(id);
                    System.out.println(result);
                    break;
                case EditName:
                    Matcher editNameMatcher = CostumerMenuCommands.EditName.getMatcher(input);
                    String firstName = editNameMatcher.group("firstName");
                    String lastName = editNameMatcher.group("lastName");
                    String password = editNameMatcher.group("password");
                    result = controller.editName(firstName, lastName, password);
                    System.out.println(result);
                    break;
                case EditEmail:
                    Matcher editEmailMatcher = CostumerMenuCommands.EditEmail.getMatcher(input);
                    String email = editEmailMatcher.group("email");
                    password = editEmailMatcher.group("password");
                    result = controller.editEmail(email, password);
                    System.out.println(result);
                    break;
                case EditPass:
                    Matcher editPassMatcher = CostumerMenuCommands.EditPass.getMatcher(input);
                    String newPass = editPassMatcher.group("newPass");
                    String oldPass = editPassMatcher.group("oldPass");
                    result = controller.editPass(newPass, oldPass);
                    System.out.println(result);
                    break;
                case ShowInfo:
                    controller.showInfo();
                    break;
                case AddAddress:
                    Matcher addAddressMatcher = CostumerMenuCommands.AddAddress.getMatcher(input);
                    String country = addAddressMatcher.group("country").trim();
                    String city = addAddressMatcher.group("city").trim();
                    String street = addAddressMatcher.group("street").trim();
                    String postal = addAddressMatcher.group("postal").trim();
                    result = controller.addAddress(country, city, street, postal);
                    System.out.println(result);
                    break;
                case DeleteAddress:
                    Matcher deleteAddressMatcher = CostumerMenuCommands.DeleteAddress.getMatcher(input);
                    id = Integer.parseInt(deleteAddressMatcher.group("id"));
                    result = controller.deleteAddress(id);
                    System.out.println(result);
                    break;
                case ListAddress:
                    controller.listAddress();
                    break;
                case AddCard:
                    Matcher addCardMatcher = CostumerMenuCommands.AddCard.getMatcher(input);
                    String cardNumber = addCardMatcher.group("cardNumber");
                    String expirationDate = addCardMatcher.group("expirationDate");
                    String cvv = addCardMatcher.group("cvv");
                    float value = Float.parseFloat(addCardMatcher.group("initialValue"));
                    result = controller.addCard(cardNumber, expirationDate, cvv, value);
                    System.out.println(result);
                    break;
                case ChargeCard:
                    Matcher chargeCardMatcher = CostumerMenuCommands.ChargeCard.getMatcher(input);
                    float amount = Float.parseFloat(chargeCardMatcher.group("amount"));
                    int cardID = Integer.parseInt(chargeCardMatcher.group("id"));
                    result = controller.chargeCard(amount, cardID);
                    System.out.println(result);
                    break;
                case CheckCardBalance:
                    Matcher checkCardBalanceMatcher = CostumerMenuCommands.CheckCardBalance.getMatcher(input);
                    cardID = Integer.parseInt(checkCardBalanceMatcher.group("id"));
                    result = controller.checkCardBalance(cardID);
                    System.out.println(result);
                    break;
                case ShowProductsInCart:
                    controller.showProductsInCart();
                    break;
                case Checkout:
                    Matcher checkoutMatcher = CostumerMenuCommands.Checkout.getMatcher(input);
                    cardID = Integer.parseInt(checkoutMatcher.group("cardID"));
                    int addressID = Integer.parseInt(checkoutMatcher.group("addressId"));
                    result = controller.checkout(cardID, addressID);
                    System.out.println(result);
                    break;
                case RemoveFromCart:
                    Matcher removeFromCartMatcher = CostumerMenuCommands.RemoveFromCart.getMatcher(input);
                    int productID = Integer.parseInt(removeFromCartMatcher.group("productID"));
                    int quantity = Integer.parseInt(removeFromCartMatcher.group("amount"));
                    result = controller.removeFromCart(productID, quantity);
                    System.out.println(result);
                    break;
                case Back:
                    System.out.println(controller.back());
                    break;
            }
        }
    }
}
