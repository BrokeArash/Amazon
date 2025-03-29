package controllers;

import models.App;
import models.Costumer;
import models.Product;
import models.Result;
import models.enums.ProductMenuCommands;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductMenuController {

//    private int currentStartIndex = 0;
//    private static final int PAGE_SIZE = 10;
//    private List<Product> currentDisplayList;
//
//    public Result showProducts(String sortBy, Scanner scanner) {
//        Costumer mainUser = (Costumer) App.getLoggedIn();
//        if (sortBy.equals("rate")) {
//        mainUser.shoppingList.sort(Comparator.comparing(Product::getRating).reversed());
//        } else if (sortBy.equals("higherpricetolower")) {
//            mainUser.shoppingList.sort(Comparator.comparing(Product::getPrice).reversed());
//        } else if (sortBy.equals("lowerpricetohigher")) {
//            mainUser.shoppingList.sort(Comparator.comparing(Product::getPrice));
//        } else if (sortBy.equals("numberofsold")) {
//            mainUser.shoppingList.sort(Comparator.comparing(Product::getNumberOfSold).reversed());
//        }
//
//        currentDisplayList = mainUser.shoppingList;
//        currentStartIndex = 0;
//
//        displayCurrentPage(sortBy);
//        while (true) {
//            String input = scanner.nextLine();
//
//            if (ProductMenuCommands.ShowNext.getMatcher(input).matches()) {
//                showNextPage();
//            } else if (ProductMenuCommands.ShowPast.getMatcher(input).matches()) {
//                showPreviousPage();
//            } else {
//                break;
//            }
//        }
//
//        return new Result(true, "");
//    }
//    private void displayCurrentPage(String sortBy) {
//        int endIndex = Math.min(currentStartIndex + PAGE_SIZE, currentDisplayList.size());
//        List<Product> currentPage = currentDisplayList.subList(currentStartIndex, endIndex);
//
//        System.out.println("Product List (Sorted by: Rate)  \n");
//        System.out.println("------------------------------------------------");
//
//        for (int i = 0; i < currentPage.size(); i++) {
//            Product product = currentPage.get(i);
//            System.out.println((currentStartIndex + i + 1) + ". " + product.getName() +
//                    " | Price: " + product.getPrice() +
//                    " | Rating: " + product.getRating());
//        }
//
//        System.out.println("\nCommands: 'show next', 'show past', 'exit'");
//    }
//
//    private void showNextPage() {
//        if (currentStartIndex + PAGE_SIZE < currentDisplayList.size()) {
//            currentStartIndex += PAGE_SIZE;
//            displayCurrentPage();
//        } else {
//            System.out.println("You've reached the end of the list.");
//        }
//    }
//
//    private void showPreviousPage() {
//        if (currentStartIndex - PAGE_SIZE >= 0) {
//            currentStartIndex -= PAGE_SIZE;
//            displayCurrentPage();
//        } else {
//            System.out.println("You're already at the beginning of the list.");
//        }
//    }



    public void showInformationProduct(int productID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Product product = Costumer.getProductByID(productID, mainUser);
        float newPrice = product.getPrice() - (product.getPrice()*product.getDiscount()/100);
        if (product == null) {
            System.out.println("No product found.");
        } else {
            System.out.println("Product Details  ");
            System.out.println("------------------------------------------------");
            if (product.getQuantity() == 0) {
                System.out.println("Name: " + product.getName() + " **Sold out!");
            } else if (product.getDiscount() > 0) {
                System.out.println("Name: " + product.getName() + "  **(On Sale! " + product.getNumberOfDiscounted() + " units discounted)**");
            } else {
                System.out.println("Name: " + product.getName());
            }
            System.out.println("ID: " + product.getID());
            System.out.printf("Rating: %.1f/5\n", product.getRating());
            if (product.getDiscount() > 0) {
                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%)\n", product.getPrice(), newPrice, product.getDiscount()); //TODO
            } else {
                System.out.printf("Price: ~%%.1f~\n", product.getPrice());
            }
            System.out.println("Brand: " + product.getBrand());
            System.out.println("Number of Products Remaining: " + product.getQuantity());
            System.out.println("About this item:  ");
            System.out.println(product.getATI());
            System.out.println();
            System.out.println("Customer Reviews:  ");
            System.out.println("------------------------------------------------");
            product.ratings.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> System.out.printf("%s\n" +
                    "------------------------------------------------\n",
                    entry.getKey()));
        }
    }


    public Result rateMessage(float number, String message, int productID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Product product = Costumer.getProductByID(productID, mainUser);
        if (product == null) {
            return new Result(false, "No product found.");
        } else if (number < 1 || number > 5) {
            return new Result(false, "Rating must be between 1 and 5.");
        } else if (mainUser == null) {
            return new Result(false, "You must be logged in to rate a product.");
        } else {
            product.ratings.put(message, number);
            return new Result(true, "Thank you! Your rating and review have been submitted successfully.");
        }
    }

    public Result addToCart(int productID, int amount) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Product product = Costumer.getProductByID(productID, mainUser);
        if (mainUser == null) {
            return new Result(false, "You must be logged in to add items to your cart.");
        } else if (product == null) {
            return new Result(false, "No product found.");
        } else if (amount <= 0) {
            return new Result(false, "No product found.");
        } else if (amount > product.getDiscount()) {
            System.out.printf("Only %d units of \"%s\" are available.\n", product.getQuantity(), product.getName());
        }
        product.addQuantity(-amount);
        mainUser.shoppingList.add(product);
        return new Result(true, "\"<productName>\" (x<amount>) has been added to your cart.");
    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "Redirecting to the MainMenu ...");
    }


}
