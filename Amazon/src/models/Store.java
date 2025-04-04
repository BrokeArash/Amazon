package models;

import models.enums.UserType;

import java.util.ArrayList;
import java.util.HashMap;

public class Store extends User{


    private String brandName;
    public ArrayList<Product> products = new ArrayList<>();
    private float revenue;
    private float costs;

    public Store(String brandName, String password, String email) {
        this.brandName = brandName;
        this.password = password;
        this.email = email;
    }

    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public float getRevenue() {
        return revenue;
    }

    public void addRevenue(float revenue) {
        this.revenue += revenue;
    }

    public float getCosts() {
        return costs;
    }

    public void addCosts(float costs) {
        this.costs += costs;
    }

    public static boolean isEmailUnique (String email) {
        return Store.getStoreByEmail(email) == null;
    }

    public static Store getStoreByEmail(String email) {
        for (Store store : App.stores) {
            if (store.getEmail().equals(email)) {
                return store;
            }
        }
        return null;
    }

    public static Product getProductByID(int id, Store mainUser) { //TODO: override
        for (Product product : mainUser.products) {
            if (product.getID() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public UserType getType() {
        return super.getType();
    }

    @Override
    public void setType(UserType type) {
        super.setType(type);
    }
}
