package controllers;

import models.*;
import models.enums.CostumerMenuCommands;
import models.enums.LoginMenuCommands;

import java.util.Comparator;
import java.util.regex.Matcher;

public class CostumerMenuController {

    public Result orderList() {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        if (mainUser.orders.isEmpty()) {
            return new Result(false, "No orders found.");
        }

        System.out.println("order History  ");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━  ");
        for (Order order : mainUser.orders) {
            System.out.println();
            System.out.println("Order ID: " + order.getID());
            System.out.println("Shipping Address: " + order.getAddress());
            System.out.println("Total Items Ordered: " + order.getTIO());
            System.out.println();
            System.out.println("Products (Sorted by Name):");
            order.products.sort(Comparator.comparing(Product::getName));
            for (int i = 0; i < order.getTIO(); i++) { //TODO: sort by name //
                System.out.println(i+"- " + order.products.get(i));
            }
            System.out.println();
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━  ");
        }
        return new Result(true, "");



    }

    public Result orderDetails(int id) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        if (Costumer.getOrderByID(id, mainUser) == null) {
            return new Result(false, "Order not found.");
        }
        Order order = Costumer.getOrderByID(id, mainUser);
        float sum = 0;
        for (Product product : order.products) {
            sum += product.getPrice();
        }
        System.out.println("Products in this order:  ");
        System.out.println();
        for (int i = 0; i < order.products.size(); i++) {
            System.out.println(i + "- Product Name: " + order.products.get(i).getBrand());
            System.out.println("    ID: " + order.products.get(i).getID());
            System.out.println("    Brand: " + order.products.get(i).getBrand());
            System.out.printf("    Rating:%.1f/5\n", order.products.get(i).getRating());
            System.out.println("    Quantity: " + order.products.get(i).getQuantity());
            if (order.products.get(i).getQuantity() > 1) {
                System.out.println("    Price: " + order.products.get(i).getPrice() + " each");
            } else {
                System.out.println("    Price: " + order.products.get(i).getPrice());
            }
            System.out.println();
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━  ");
        System.out.printf("**Total Cost: $%.1f**  ", sum);
        return new Result(true, "");
    }

    public Result editName(String firstName, String lastName, String password) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Matcher firstNameMatcher = LoginMenuCommands.CheckName.getMatcher(firstName);
        Matcher lastNameMatcher = LoginMenuCommands.CheckName.getMatcher(lastName);
        if (!mainUser.getPassword().equals(password)) {
            return new Result(false, "Incorrect password. Please try again.");
        } else if (mainUser.getFirstName().equals(firstName) || mainUser.getLastName().equals(lastName)) {
            return new Result(false, "The new name must be different from the current name.");
        } else if (firstName.length() < 3 || lastName.length() < 3) {
            return new Result(false, "Name is too short.");
        } else if (firstNameMatcher == null || lastNameMatcher == null) {
            return new Result(false, "Incorrect name format.");
        } else {
            mainUser.setFirstName(firstName);
            mainUser.setLastName(lastName);
            return new Result(true, "Name updated successfully.");
        }


    }

    public Result editEmail(String email, String password) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Matcher emailMatcher = LoginMenuCommands.CheckEmail.getMatcher(email);
        if (!mainUser.getPassword().equals(password)) {
            return new Result(false, "Incorrect password. Please try again.");
        } else if (mainUser.getEmail().equals(email)){
            return new Result(false, "The new email must be different from the current email.");
        } else if (emailMatcher == null) {
            return new Result(false, "Incorrect email format.");
        } else if (!User.isEmailUnique(email)) {
            return new Result(false, "Email already exists.");
        } else {
            mainUser.setEmail(email);
            return new Result(true, "Email updated successfully.");
        }
    }

    public Result editPass(String newPass, String oldPass) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Matcher passwordMatcher = LoginMenuCommands.CheckPassword.getMatcher(newPass);
        if (!mainUser.getPassword().equals(oldPass)) {
            return new Result(false, "Incorrect password. Please try again.");
        } else if (oldPass.equals(newPass)) {
            return new Result(false, "The new password must be different from the old password.");
        } else if (passwordMatcher == null) {
            return new Result(false, "The new password is weak.");
        } else {
            mainUser.setPassword(newPass);
            return new Result(true, "Password updated successfully.");
        }
    }

    public void showInfo() {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        System.out.println("Name: " + mainUser.getFirstName() + " " + mainUser.getLastName());
        System.out.println("Email: " + mainUser.getEmail());
    }

    public Result addAddress(String country, String city, String street, String postal) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Matcher postalMatcher = CostumerMenuCommands.CheckPostal.getMatcher(postal);

        if (postalMatcher == null) {
            return new Result(false, "Invalid postal code. It should be a 10-digit number.");
        } else if (!Costumer.isPostalUnique(postal, mainUser)) {
            return new Result(false, "This postal code is already associated with an existing address.");
        } else {
            Address newAddress = new Address(street, city, country, postal);
            mainUser.addresses.add(newAddress);
            return new Result(true, "Address successfully added with id " + newAddress.getId() + ".");
        }
    }

    public Result deleteAddress(int id) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Address address = Costumer.getAddressByID(id, mainUser);
        if (address == null) {
            return new Result(false, "No address found.");
        } else {
            mainUser.addresses.remove(address);
            return new Result(true, "Address with id " + address.getId() + " deleted successfully.");
        }
    }

    public void listAddress() {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        if (mainUser.addresses.isEmpty()) {
            System.out.println("No addresses found. Please add an address first.");
        } else {
            System.out.println("Saved Addresses");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━  ");
            for (Address address : mainUser.addresses) {
                System.out.println();
                System.out.println("Address " + address.getId() + ":");
                System.out.println("Postal Code: " + address.getPostal());
                System.out.println("Country: " + address.getCountry());
                System.out.println("City: " + address.getCity());
                System.out.println("Street: " + address.getStreet()); //TODO: might be wrong with number
                System.out.println();
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━  ");
            }
        }
    }


    public Result addCard(String cardNumber, String expirationDate, String cvv, int value) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Matcher cardNumberMatcher = CostumerMenuCommands.CheckCardNumber.getMatcher(cardNumber);
        Matcher expirationDateMatcher = CostumerMenuCommands.CheckDate.getMatcher(expirationDate);
        Matcher cvvMatcher = CostumerMenuCommands.CheckCVV.getMatcher(cvv);
        if (cardNumberMatcher == null) {
            return new Result(false, "The card number must be exactly 16 digits.");
        } else if (expirationDateMatcher == null) {
            return new Result(false, "Invalid expiration date. Please enter a valid date in MM/YY format.");
        } else if (cvvMatcher == null) {
            return new Result(false, "The CVV code must be 3 or 4 digits.");
        } else if (value < 0) {
            return new Result(false, "The initial value cannot be negative.");
        } else if (Costumer.getCardByNumber(cardNumber, mainUser) != null) {
            return new Result(false, "This card is already saved in your account.");
        } else {
            Card newCard = new Card(cardNumber, expirationDate, cvv, value);
            mainUser.cards.add(newCard);
            return new Result(true, "Credit card with Id " + newCard.getId() + " has been added successfully.");
        }
    }

    public Result chargeCard(float amount, int cardID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Card card = Costumer.getCardByID(cardID, mainUser);
        if (amount <= 0) {
            return new Result(false, "The amount must be greater than zero.");
        } else if (card == null) {
            return new Result(false, "No credit card found.");
        } else {
            card.addValue(amount);
            return new Result(true, "$" + amount + " has been added to the credit card " + card.getId() + ". New balance: $" + card.getValue() + ".");
        }
    }


    public Result checkCardBalance(int cardID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Card card = Costumer.getCardByID(cardID, mainUser);
        if (card == null) {
            return new Result(false, "No credit card found.");
        } else {
            return new Result(true, "Current balance : $" + card.getValue());
        }
    }


    public void showProductsInCart() {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        if (mainUser.shoppingList.isEmpty()) {
            System.out.println("Your shopping cart is empty.");
        } else {
            System.out.println("Your Shopping Cart:");
            System.out.println("------------------------------------");
            for (Product product : mainUser.shoppingList) {
                System.out.println("Product ID  : " + product.getID());
                System.out.println("Name        : " + product.getName());
                System.out.println("Quantity    : " + product.getQuantity());
                System.out.println("Price       : $" + product.getPrice() + " (each)");
                System.out.println("Total Price : $" + product.getPrice() * product.getQuantity());
                System.out.println("Brand       : " + product.getBrand());
                System.out.printf("Rating      : %.1f/5\n", product.getRating());
                System.out.println("------------------------------------");
            }
        }
    }

    public Result checkout(int cardID, int addressID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Card card = Costumer.getCardByID(cardID, mainUser);
        Address address = Costumer.getAddressByID(addressID, mainUser);
        float sum = 0;
        for (Product product : mainUser.shoppingList) {
            sum += product.getQuantity() * product.getPrice();
        }
        if (mainUser.shoppingList.isEmpty()) {
            return new Result(false, "Your shopping cart is empty.");
        } else if (address == null) {
            return new Result(false, "The provided address ID is invalid.");
        } else if (card == null) {
            return new Result(false, "The provided card ID is invalid.");
        } else if (card.getValue() < sum) {
            return new Result(false, "Insufficient balance in the selected card.");
        }  else {
            Order newOrder = new Order(mainUser.orders.size() + 1, address, mainUser.shoppingList.size(), mainUser.shoppingList);
            mainUser.orders.add(newOrder);
            String message = "Order has been placed successfully!\nOrder ID: " + newOrder.getID() + "\nTotal Paid: $" + sum + "\nShipping to: " + newOrder.getAddress();
            return new Result(true, message);
        }
    }

    public Result removeFromCart(int productID, int quantity) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Product product = Costumer.getProductByID(productID, mainUser);
        if (mainUser.shoppingList.isEmpty()) {
            return new Result(false, "Your shopping cart is empty.");
        } else if (product == null) {
            return new Result(false,"The product with ID " + productID + " is not in your cart.");
        } else if (quantity <= 0) {
            return new Result(false, "Quantity must be a positive number.");
        } else if (quantity > product.getQuantity()) {
            return new Result(false, "You only have " + product.getQuantity() + " of \"" + product.getName() + "\" in your cart.");
        } else {
            product.decreaseQuantity(quantity);
            if (product.getQuantity() > 0) {
                return new Result(true,"Successfully removed " + quantity + " of \"" + product.getName() + "\" from your cart.");
            } else {
                return new Result(false, "\"" + product.getName() + "\" has been completely removed from your cart.");
            }
        }
    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "Redirecting to the MainMenu ...");

    }

}

