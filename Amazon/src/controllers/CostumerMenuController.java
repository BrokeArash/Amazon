package controllers;

import models.*;
import models.enums.CostumerMenuCommands;
import models.enums.LoginMenuCommands;
import java.util.*;
import java.util.regex.Matcher;

public class CostumerMenuController {

    public Result orderList() {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        if (mainUser.orders.isEmpty()) {
            return new Result(false, "No orders found.");
        }

        System.out.println("order History");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Order order : mainUser.orders) {
            System.out.println();
            System.out.println("Order ID: " + order.getID());
            System.out.println("Shipping Address: " + order.getAddress());
            System.out.println("Total Items Ordered: " + order.getTIO());
            System.out.println();
            System.out.println("Products (Sorted by Name):");
            ArrayList<Product> sortedNames = new ArrayList<>(order.products);
            sortedNames.sort(Comparator.comparing(Product::getName));
            for (int i = 0; i < sortedNames.size(); i++) {
                System.out.println("  " + (i+1) + "- " + sortedNames.get(i).getName());
            }
            System.out.println();
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return new Result(true, "");



    }

    public Result orderDetails(int id) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        if (Costumer.getOrderByID(id, mainUser) == null) {
            return new Result(false, "Order not found.");
        }
        Order order = Costumer.getOrderByID(id, mainUser);
        double sum = 0;
        ArrayList<Product> sortedOrder = new ArrayList<>(order.products);
        sortedOrder.sort(Comparator.comparing(Product::getID));
        for (Product product : sortedOrder) {
            if (product.getDiscount() > 0 && product.getNumberOfDiscounted() > 0) {
                sum += product.getPrice() * product.getQuantity();
            } else {
                sum += product.getBasePrice() * product.getQuantity();
            }
        }
        System.out.println("Products in this order:");
        System.out.println();
        ArrayList<Product> sortedNames = new ArrayList<>(order.products);
        sortedNames.sort(Comparator.comparing(Product::getID));
        for (int i = 0; i < sortedNames.size(); i++) {
            System.out.println( (i+1) + "- Product Name: " + sortedNames.get(i).getName());
            System.out.println("    ID: " + sortedNames.get(i).getID());
            System.out.println("    Brand: " + sortedNames.get(i).getBrand());
            System.out.printf("    Rating: %.1f/5\n", sortedNames.get(i).getRating());
            System.out.println("    Quantity: " + sortedNames.get(i).getQuantity());
            if (sortedNames.get(i).getDiscount() > 0 && sortedNames.get(i).getNumberOfDiscounted() > 0) {
                if (sortedNames.get(i).getQuantity() > 1) {
                    System.out.printf("    Price: $%.1f each", sortedNames.get(i).getPrice());
                } else {
                    System.out.printf("    Price: $%.1f", sortedNames.get(i).getPrice());
                }
            } else {
                if (sortedNames.get(i).getQuantity() > 1) {
                    System.out.printf("    Price: $%.1f each", sortedNames.get(i).getBasePrice());
                } else {
                    System.out.printf("    Price: $%.1f", sortedNames.get(i).getBasePrice());
                }
            }
            System.out.println();
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("**Total Cost: $%.1f**", sum);
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
            int newId = mainUser.getNextAddressId();
            Address newAddress = new Address(newId, street, city, country, postal);
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
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
            for (Address address : mainUser.addresses) {
                System.out.println();
                System.out.println("Address " + address.getId() + ":");
                System.out.println("Postal Code: " + address.getPostal());
                System.out.println("Country: " + address.getCountry());
                System.out.println("City: " + address.getCity());
                System.out.println("Street: " + address.getStreet());
                System.out.println();
                System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        }
    }


    public Result addCard(String cardNumber, String expirationDate, String cvv, double value) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Matcher cardNumberMatcher = CostumerMenuCommands.CheckCardNumber.getMatcher(cardNumber);
        Matcher expirationDateMatcher = CostumerMenuCommands.CheckDate.getMatcher(expirationDate);
        Matcher cvvMatcher = CostumerMenuCommands.CheckCVV.getMatcher(cvv);
        if (cardNumberMatcher == null) {
            return new Result(false, "The card number must be exactly 16 digits.");
        } else if (expirationDateMatcher == null) {
            return new Result(false,
                    "Invalid expiration date. Please enter a valid date in MM/YY format.");
        } else if (cvvMatcher == null) {
            return new Result(false, "The CVV code must be 3 or 4 digits.");
        } else if (value < 0) {
            return new Result(false, "The initial value cannot be negative.");
        } else if (Costumer.getCardByNumber(cardNumber, mainUser) != null) {
            return new Result(false, "This card is already saved in your account.");
        } else {
            int newId = mainUser.getNextCardId();
            Card newCard = new Card(newId, cardNumber, expirationDate, cvv, value);
            mainUser.cards.add(newCard);
            return new Result(true, "Credit card with Id " +
                    newCard.getId() + " has been added successfully.");
        }
    }

    public Result chargeCard(double amount, int cardID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Card card = Costumer.getCardByID(cardID, mainUser);
        if (amount <= 0) {
            return new Result(false, "The amount must be greater than zero.");
        } else if (card == null) {
            return new Result(false, "No credit card found.");
        } else {
            card.addValue(amount);
            System.out.printf("$%.1f has been added to the credit card %d. New balance: $%.1f.",
                    amount, card.getId(), card.getValue());
            return new Result(true, "");
        }
    }


    public Result checkCardBalance(int cardID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Card card = Costumer.getCardByID(cardID, mainUser);
        if (card == null) {
            return new Result(false, "No credit card found.");
        } else {
            System.out.printf("Current balance : $%.1f", card.getValue());
            return new Result(true, "");
        }
    }


    public void showProductsInCart() {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        if (mainUser.shoppingList.isEmpty()) {
            System.out.println("Your shopping cart is empty.");
        } else {
            System.out.println("Your Shopping Cart:");
            System.out.println("------------------------------------");
            ArrayList<Product> sortedProducts = new ArrayList<>(mainUser.shoppingList);
            sortedProducts.sort(Comparator.comparing(Product::getName));
            for (Product product : sortedProducts) {
                System.out.println("Product ID  : " + product.getID());
                System.out.println("Name        : " + product.getName());
                System.out.println("Quantity    : " + product.getQuantity());
                if (product.getDiscount() > 0 && product.getNumberOfDiscounted() > 0) {
                    System.out.printf("Price       : $%.1f (each)\n", product.getPrice());
                    System.out.printf("Total Price : $%.1f\n", product.getPrice() * product.getQuantity());
                }else {
                    System.out.printf("Price       : $%.1f (each)\n", product.getBasePrice());
                    System.out.printf("Total Price : $%.1f\n", product.getBasePrice() * product.getQuantity());
                }
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
        HashMap<Store, Double> storeTemp = new HashMap<>();
        double sum = 0;
        for (Product product : mainUser.shoppingList) {
            Store thisStore = Store.getStoreByBrand(product.getBrand());
            if (product.getNumberOfDiscounted() > 0 && product.getDiscount() > 0) {
                double total = product.getQuantity() * product.getPrice();
                sum += total;
                storeTemp.merge(thisStore, total, Double::sum);
            }  else {
                double total = product.getQuantity() * product.getBasePrice();
                sum += total;
                storeTemp.merge(thisStore, total, Double::sum);
            }

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

            card.addValue(-sum);
            int TIO = 0;
            for (Product product : mainUser.shoppingList) {
                TIO += product.getQuantity();
            }
            Order newOrder = new Order(mainUser.orders.size() + 101, address, TIO, mainUser.shoppingList);
            mainUser.orders.add(newOrder);
            storeTemp.forEach((store, revenue) -> store.addRevenue(revenue));
            for (Product product : mainUser.shoppingList) {
                Product mainProduct = Costumer.getProductByID(product.getID());
                mainProduct.addNumberOfSold(product.getQuantity());
            }
            mainUser.shoppingList.clear();
            System.out.printf("Order has been placed successfully!\nOrder ID:" +
                    " %d\nTotal Paid: $%.1f\nShipping to: %s\n", newOrder.getID(), sum, newOrder.getAddress());
            return new Result(true, "");
        }
    }

    public Result removeFromCart(int productID, int quantity) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Product cartProduct = mainUser.getProductInCartByID(productID, mainUser);
        Product product = Costumer.getProductByID(productID);

        if (mainUser.shoppingList.isEmpty()) {
            return new Result(false, "Your shopping cart is empty.");
        } else if (cartProduct == null) {
            return new Result(false,"The product with ID " + productID + " is not in your cart.");
        } else if (quantity <= 0) {
            return new Result(false, "Quantity must be a positive number.");
        } else if (quantity > cartProduct.getQuantity()) {
            return new Result(false, "You only have " + cartProduct.getQuantity() +
                    " of \"" + cartProduct.getName() + "\" in your cart.");
        } else {
            cartProduct.decreaseQuantity(quantity);
            product.addQuantity(quantity);

            if (product.getDiscount() > 0) {
                product.addNumberOfDiscounted(quantity);
            }
            if (cartProduct.getQuantity() > 0) {
                return new Result(true,"Successfully removed " + quantity +
                        " of \"" + product.getName() + "\" from your cart.");
            } else {
                mainUser.shoppingList.remove(cartProduct);
                return new Result(false, "\"" + product.getName() + "\" " +
                        "has been completely removed from your cart.");
            }
        }
    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "Redirecting to the MainMenu ...");

    }

}

