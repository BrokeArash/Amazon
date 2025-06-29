package models;

import java.util.ArrayList;

public class Product{
    private final String brand;
    private final int ID;
    private final String name;
    private double rating;
    private int quantity;
    private double basePrice;
    private final double producerCost;
    private double discount;
    private int numberOfDiscounted = 0;
    private final String ATI;
    private static int lastAssigned = 100;
    private int numberOfSold = 0;
    public ArrayList<Rating> ratings = new ArrayList<>();

    public Product(String brand, int quantity,double producerCost, double basePrice, String name, String ati) {
        this.brand = brand;
        this.ID = ++lastAssigned;
        this.name = name;
        this.rating = 2.5;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.producerCost = producerCost;
        ATI = ati;
        this.discount = 0;
    }

    public Product(Product other) {
        this.brand = other.brand;
        this.ID = other.ID;
        this.name = other.name;
        this.rating = other.rating;
        this.quantity = other.quantity;
        this.basePrice = other.basePrice;
        this.producerCost = other.producerCost;
        this.discount = other.discount;
        this.numberOfDiscounted = other.numberOfDiscounted;
        this.ATI = other.ATI;
        this.numberOfSold = other.numberOfSold;
        this.ratings.addAll(other.ratings);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return this.basePrice - (this.basePrice * this.discount);
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getProducerCost() {
        return producerCost;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getATI() {
        return ATI;
    }

    public int getNumberOfSold() {
        return numberOfSold;
    }

    public void addNumberOfSold(int numberOfSold) {
        this.numberOfSold += numberOfSold;
    }

    public int getNumberOfDiscounted() {
        return numberOfDiscounted;
    }

    public void addNumberOfDiscounted(int numberOfDiscounted) {
        if (this.numberOfDiscounted + numberOfDiscounted < 0) {
            this.numberOfDiscounted = 0;
            return;
        }
        this.numberOfDiscounted += numberOfDiscounted;
    }

    public String getBrand() {
        return brand;
    }

    public double calculateAverageRating() {

        double totalSum = 0;
        int ratingCount = 0;

        for(Rating rating : this.ratings) {
            totalSum += rating.getRate();
            ratingCount++;
        }

        return totalSum/ratingCount;
    }

}
