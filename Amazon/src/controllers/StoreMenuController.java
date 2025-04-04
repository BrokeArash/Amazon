package controllers;

import models.*;

public class StoreMenuController {
    public Result addProduct(String name, double producerCost, double price, String aboutThisItem, int numberOfProductsToSell) {
        Store mainUser = (Store) App.getLoggedIn();
        if (producerCost > price) {
            return new Result(false, "Selling price must be greater than or equal to the producer cost.");
        } else if (numberOfProductsToSell <= 0) {
            return new Result(false, "Number of products must be a positive number.");
        } else {
            Product newProduct = new Product(mainUser.getBrandName(), 2.5F, numberOfProductsToSell, price, name, aboutThisItem);
            mainUser.products.add(newProduct);
            App.products.add(newProduct);
            return new Result(true, "Product " + name + " has been added successfully with ID " + newProduct.getID() +".");
        }
    }

    public Result applyDiscount(int productID, int discountPercentage, int quantity) {
        Store mainUser = (Store) App.getLoggedIn();
        Product product = Store.getProductByID(productID, mainUser);
        if (discountPercentage < 1 || discountPercentage > 100) {
            return new Result(false, "Discount percentage must be between 1 and 100.");
        } else if (product == null) {
            return new Result(false, "No product found.");
        } else if (quantity > product.getQuantity()) {
            return new Result(false, "Oops! Not enough stock to apply the discount to that many items.");
        } else {
            product.setDiscount(discountPercentage);
            product.addNumberOfDiscounted(quantity);
            return new Result(true, "A " + discountPercentage + "% discount has been applied to " + quantity + " units of product ID " + productID + ".");
        }
    }

    public void showProfit() {
        Store mainUser = (Store) App.getLoggedIn();
        float totalProfit = mainUser.getRevenue() - mainUser.getCosts();
        System.out.println("Total Profit: $" + totalProfit);
        System.out.printf("(Revenue: $%.1f - Costs: $%.1f)\n", mainUser.getRevenue(), mainUser.getCosts()); //TODO: double
    }

    public void showListOfProducts() {
        Store mainUser = (Store) App.getLoggedIn();
        System.out.println("Store Products (Sorted by date added)  ");
        System.out.println("------------------------------------------------");
        for (Product product : mainUser.products) {
            double newPrice = product.getDiscountPrice();
            if (product.getQuantity() == 0) {
                System.out.printf("ID: %d  (**Sold out!**)\n", product.getID());
            } else if (product.getDiscount() > 0) {
                System.out.printf("ID: %d  **(On Sale! %d units discounted)**\n",product.getID(), product.getNumberOfDiscounted());
            } else {
                System.out.println("ID: " + product.getID());
            }
            System.out.println("Name: " + product.getName().substring(1, product.getName().length()-1));
            if (product.getDiscount() > 0) {
                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n", product.getPrice(), newPrice, product.getDiscount());
            } else {
                System.out.printf("Price: $%.1f\n", product.getPrice());
            }
            System.out.println("Stock: " + product.getQuantity());
            System.out.println("Sold:" + product.getNumberOfSold());
            System.out.println("------------------------------------------------");
        }
    }

    public Result addStock(int productId, int amount) {
        Store mainUser = (Store) App.getLoggedIn();
        Product product = Store.getProductByID(productId, mainUser);
        if (product == null) {
            return new Result(false, "No product found.");
        } else if (amount <= 0) {
            return new Result(false, "Amount must be a positive number.");
        } else {
            product.addQuantity(amount);
            return new Result(true, amount + " units of " + product.getName() + " have been added to the stock.");
        }
    }

    public Result updatePrice(int productId, double newPrice) {
        Store mainUser = (Store) App.getLoggedIn();
        Product product = Store.getProductByID(productId, mainUser);
        if (product == null) {
            return new Result(false, "No product found.");
        } else if (newPrice <= 0) {
            return new Result(false, "Price must be a positive number.");
        } else {
            product.setPrice(newPrice);
            return new Result(true, "Price of " + product.getName() + " has been updated to $" + newPrice + ".");
        }
    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "Redirecting to the MainMenu ...");
    }
}



