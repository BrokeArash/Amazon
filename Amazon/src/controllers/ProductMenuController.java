package controllers;

import models.*;

import java.util.*;

public class ProductMenuController {
    public static int pageNum;
    public static ArrayList<Product> products = new ArrayList<>();

    public void showProducts(String sortBy, String mainSortBy) {
        products.clear();
        products.addAll(App.products);
        if (sortBy.equals("rate")) {
            Collections.sort(products, Comparator.comparing(Product::getRating).reversed());
        } else if (sortBy.equals("higherpricetolower")) {
            Collections.sort(products, Comparator.comparing(Product::getDiscountPrice).reversed());
        } else if (sortBy.equals("lowerpricetohigher")) {
            Collections.sort(products, Comparator.comparing(Product::getDiscountPrice));
        } else if (sortBy.equals("numberofsold")) {
            Collections.sort(products, Comparator.comparing(Product::getNumberOfSold));
        }
        pageNum = 0;
        showPage(products, pageNum, mainSortBy);
    }

    public static void showPage(ArrayList<Product> products, int pageNum, String mainSortBy) {
        System.out.printf("Product List (Sorted by: %s)\n------------------------------------------------\n", mainSortBy);
        for (int i = pageNum; i < pageNum + 10; i++) {
            if (i == products.size()) {
                break;
            }
            Product currentProduct = products.get(i);
            if (currentProduct.getNumberOfDiscounted() > 0) {
                System.out.printf("ID: %d  **(On Sale! %d units discounted)**\n", currentProduct.getID(), currentProduct.getNumberOfDiscounted());
            } else if (currentProduct.getQuantity() <= 0) {
                System.out.printf("ID: %d  **(Sold out!)**\n", currentProduct.getID());
            } else {
                System.out.printf("ID: %d\n", currentProduct.getID());
            }
            System.out.printf("Name: %s\n", currentProduct.getName());
            System.out.printf("Rate: %.1f/5\n", currentProduct.getRating());
            if (currentProduct.getNumberOfDiscounted() > 0) {
                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", currentProduct.getPrice(), currentProduct.getDiscountPrice(), currentProduct.getDiscount());
            } else {
                System.out.printf("Price: $%.1f\n", currentProduct.getPrice());
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

    public static void showNext(String mainSortBy) {
        if (products.size() < pageNum + 11) {
            System.out.println("No more products available.");
        } else {
            pageNum += 10;
            showPage(products, pageNum, mainSortBy);
        }
    }

    public static void showPast(String mainSortBy) {
        if (pageNum == 0) {
            System.out.println("No more products available.");
            return;
        }
        pageNum -= 10;
        showPage(products, pageNum, mainSortBy);
    }

    public void showInformationProduct(int productID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Product product = Costumer.getProductByID(productID, mainUser);
        double newPrice = product.getDiscountPrice();
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
                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", product.getPrice(), newPrice, product.getDiscount()); //TODO
            } else {
                System.out.printf("Price: ~%%.1f~\n", product.getPrice());
            }
            System.out.println("Brand: " + product.getBrand());
            System.out.println("Number of Products Remaining: " + product.getQuantity());
            System.out.println("About this item:  ");
            System.out.println(product.getATI()); //TODO
            System.out.println();
            System.out.println("Customer Reviews:  ");
            System.out.println("------------------------------------------------");
            product.ratings.values().stream().flatMap(innerMap -> innerMap.entrySet().stream()).sorted(Map.Entry.<String, Float>comparingByValue().reversed()).forEach(entry -> System.out.printf("%s (%.1f/5)\n%s\n",
                    findName(product.ratings, entry.getKey()),
                    entry.getValue(),
                    entry.getKey()));

        }
    }



    public static String findName (
            HashMap<String, HashMap<String, Float>> rating,
            String name) {
        for (Map.Entry<String, HashMap<String, Float>> entry : rating.entrySet()) {
            if (entry.getValue().containsKey(name)) {
                return entry.getKey();
            }
        }
        return null;
    }





    public Result rateMessage(float number, String message, int productID) {
        Costumer mainUser = (Costumer) App.getLoggedIn();
        Product product = Costumer.getProductByID(productID, mainUser);
        HashMap<String, Float> currentUserRating = new HashMap<>();
        if (product == null) {
            return new Result(false, "No product found.");
        } else if (number < 1 || number > 5) {
            return new Result(false, "Rating must be between 1 and 5.");
        } else if (mainUser == null) {
            return new Result(false, "You must be logged in to rate a product.");
        } else {
            currentUserRating.put(message, number);
            product.ratings.put(mainUser.getPassword() + " " + mainUser.getLastName(), currentUserRating);
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
