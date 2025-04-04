package models;

import java.util.HashMap;

public class Product{
    private String brand;
    private int ID;
    private String name;
    private float rating;
    private int quantity;
    private double price;
    private int discount = 0;
    private int numberOfDiscounted = 0;
    private String ATI;
    private static int lastAssigned = 100;
    private int numberOfSold = 0;


    public HashMap <String, HashMap<String, Float>> ratings = new HashMap<>();

    public Product(String brand, float rating, int quantity, double price, String name, String ati) {
        this.brand = brand;
        this.ID = ++lastAssigned;
        this.name = name;
        this.rating = rating;
        this.quantity = quantity;
        this.price = price;
        ATI = ati;
        this.discount = 0;
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
        return price;
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
        this.numberOfDiscounted += numberOfDiscounted;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getDiscountPrice() {
        return getPrice() - (getPrice() * (float) (getDiscount()) / 100);
    }
}
