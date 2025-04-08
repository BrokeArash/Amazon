package controllers;

import models.*;
import models.enums.UserType;

import java.util.*;

public class ProductMenuController {
    public static int pageNum;
    public static String sortby;
    public static ArrayList<Product> products = new ArrayList<>();

    public void showProducts(String sortBy, String mainSortBy) {
        products.clear();
        String sort = sortBy;
        products.addAll(App.products);
        if (sort.equals("rate")) {
            sortby = "Rate";
            Collections.sort(products, Comparator.comparing(Product::getRating).reversed());
        } else if (sort.equals("higherpricetolower")) {
            sortby = "Higher price to lower";
            Collections.sort(products, Comparator.comparing(Product::getPrice).reversed());
        } else if (sort.equals("lowerpricetohigher")) {
            sortby = "Lower price to higher";
            Collections.sort(products, Comparator.comparing(Product::getPrice));
        } else if (sort.equals("numberofsold")) {
            sortby = "Number of sold";
            Collections.sort(products, Comparator.comparing(Product::getNumberOfSold).reversed());
        }
        pageNum = 0;
        showPage(products, pageNum);
    }

    public static void showPage(ArrayList<Product> products, int pageNum) {
        System.out.printf("Product List (Sorted by: %s)\n------------------------------------------------\n", sortby);
        for (int i = pageNum; i < pageNum + 10; i++) {
            if (i == products.size()) {
                break;
            }
            Product currentProduct = products.get(i);
            if (currentProduct.getNumberOfDiscounted() > 0 && currentProduct.getNumberOfDiscounted() != 0) {
                System.out.printf("ID: %d  **(On Sale! %d units discounted)**\n", currentProduct.getID(), currentProduct.getNumberOfDiscounted());
            } else if (currentProduct.getQuantity() <= 0) {
                System.out.printf("ID: %d  **(Sold out!)**\n", currentProduct.getID());
            } else {
                System.out.printf("ID: %d\n", currentProduct.getID());
            }
            System.out.printf("Name: %s\n", currentProduct.getName());
            System.out.printf("Rate: %.1f/5\n", currentProduct.getRating());

            if (currentProduct.getNumberOfDiscounted() > 0 && currentProduct.getNumberOfDiscounted() != 0) {
                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", currentProduct.getBasePrice(), currentProduct.getPrice(),(int)(currentProduct.getDiscount()*100));
            } else {
                System.out.printf("Price: $%.1f\n", currentProduct.getBasePrice());
            }
            System.out.printf("Brand: %s\n", currentProduct.getBrand());
            System.out.printf("Stock: %d\n", currentProduct.getQuantity());
            System.out.println("------------------------------------------------");
        }
        System.out.printf("(Showing %d-%d out of %d)\n", pageNum + 1, pageNum + 10, products.size());
        if (products.size() > pageNum + 10) {
            System.out.println("Use `show next 10 products' to see more.");
        }
    }

    public static void showNext() {
        if (products.size() < pageNum + 11) {
            System.out.println("No more products available.");
        } else {
            pageNum += 10;
            showPage(products, pageNum);
        }
    }

    public static void showPast() {
        if (pageNum == 0) {
            System.out.println("No more products available.");
            return;
        }
        pageNum -= 10;
        showPage(products, pageNum);
    }

    public void showInformationProduct(int productID) {
        Product product = Costumer.getProductByID(productID);
        double newPrice = 0.0;
        if (product != null) {
            newPrice = product.getPrice();
        }
        if (product == null) {
            System.out.println("No product found.");
        } else {
            System.out.println("Product Details");
            System.out.println("------------------------------------------------");
            if (product.getQuantity() == 0) {
                System.out.println("Name: " + product.getName() + "  **(Sold out!)**");
            } else if (product.getDiscount() > 0 && product.getNumberOfDiscounted() > 0) {
                System.out.println("Name: " + product.getName() + "  **(On Sale! " + product.getNumberOfDiscounted() + " units discounted)**");
            } else {
                System.out.println("Name: " + product.getName());
            }
            System.out.println("ID: " + product.getID());

            System.out.printf("Rating: %.1f/5\n", product.getRating());

            if (product.getDiscount() > 0 && product.getNumberOfDiscounted() > 0) {
                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", product.getBasePrice(), product.getPrice(),(int)(product.getDiscount()*100)); //TODO
            } else {
                System.out.printf("Price: $%.1f\n", product.getBasePrice());
            }
            System.out.println("Brand: " + product.getBrand());
            System.out.println("Number of Products Remaining: " + product.getQuantity());
            System.out.println("About this item:");
            System.out.println(product.getATI()); //TODO
            System.out.println();
            System.out.println("Customer Reviews:");
            System.out.println("------------------------------------------------");
            for (Rating rating : product.ratings) {
                System.out.printf("%s (%d/5)\n", rating.getName(), rating.getRate());
                if (rating.getMessage() != null) {
                    System.out.println(rating.getMessage());
                }
                System.out.println("------------------------------------------------");
            }
        }
    }


    public Result rateMessage(float number, String message, int productID) {
        User mainUser = App.getLoggedIn();
        Product product = Costumer.getProductByID(productID);
        if (product == null) {
            return new Result(false, "No product found.");
        } else if (number < 1 || number > 5) {
            return new Result(false, "Rating must be between 1 and 5.");
        } else if (mainUser == null || App.getLoggedInType().equals(UserType.Store)) {
            return new Result(false, "You must be logged in to rate a product.");
        } else {
            Costumer main = (Costumer) mainUser;
            Rating newRate = new Rating(main, message, (int)number);
            product.ratings.add(newRate);
            product.setRating(product.calculateAverageRating());
            return new Result(true, "Thank you! Your rating and review have been submitted successfully.");
        }
    }

    public Result addToCart(int productID, int amount) {
        User mainUser = App.getLoggedIn();
        Product product = Costumer.getProductByID(productID);
        if (mainUser == null || App.getLoggedInType().equals(UserType.Store)) {
            return new Result(false, "You must be logged in to add items to your cart.");
        }
        else if (product == null) {
            return new Result(false, "No product found.");
        } else if (amount <= 0) {
            return new Result(false, "Quantity must be a positive number.");
        } else if (amount > product.getQuantity()) {
            System.out.printf("Only %d units of \"%s\" are available.\n", product.getQuantity(), product.getName());
            return new Result(false, "");
        }

        Costumer main =(Costumer) mainUser;
        for (Product cartProduct : main.shoppingList) {
            if (cartProduct.getID() == productID) {
                cartProduct.addQuantity(amount);
                product.addQuantity(-amount);
                product.addNumberOfDiscounted(-amount);
                return new Result(true, "\"" + product.getName() + "\" (x" + amount + ") has been added to your cart.");
            }
        }

        Product cartProduct = new Product(product);
        cartProduct.setQuantity(amount);
        product.addQuantity(-amount);
        product.addNumberOfDiscounted(-amount);
        main.shoppingList.add(cartProduct);
        return new Result(true, "\"" + product.getName() + "\" (x" + amount + ") has been added to your cart.");

    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "Redirecting to the MainMenu ...");
    }


}
