package models;

import java.util.HashMap;

public class Product{
    private String brand;
    private int ID;
    private String name;
    private float rating;
    private int quantity;
    private double basePrice;
    private double price;
    private final double producerCost;
    private int discount = 0;
    private int numberOfDiscounted = 0;
    private String ATI;
    private static int lastAssigned = 100;
    private int numberOfSold = 0;


    public HashMap <String, HashMap<String, Float>> ratings = new HashMap<>();

    public Product(String brand, float rating, int quantity,double producerCost, double basePrice, String name, String ati) {
        this.brand = brand;
        this.ID = ++lastAssigned;
        this.name = name;
        this.rating = rating;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.price = basePrice;
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
        this.price = other.price;
        this.producerCost = other.producerCost;
        this.discount = other.discount;
        this.numberOfDiscounted = other.numberOfDiscounted;
        this.ATI = other.ATI;
        this.numberOfSold = other.numberOfSold;
        this.ratings = new HashMap<>(other.ratings);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return (discount > 0) ? price * (1 - discount/100.0) : price;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void decreaseQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
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

    public void setATI(String ATI) {
        this.ATI = ATI;
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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getDiscountPrice() {
        return getPrice() - (getPrice() * ( (float)(getDiscount())) / 100);
    }

    public float calculateAverageRating() {
        if (ratings.isEmpty()) {
            return this.rating;
        }

        float totalSum = 0f;
        int ratingCount = 0;

        for (HashMap<String, Float> userRatings : ratings.values()) {
            for (Float score : userRatings.values()) {
                totalSum += score;
                ratingCount++;
            }
        }

        return ratingCount > 0 ? totalSum / ratingCount : 0f;
    }

}
