package models;

import models.enums.UserType;
import java.util.ArrayList;

public class Store extends User{


    private String brandName;
    public ArrayList<Product> products = new ArrayList<>();
    private double revenue;
    private double costs;

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

    public double getRevenue() {
        return revenue;
    }

    public void addRevenue(double revenue) {
        this.revenue += revenue;
    }

    public double getCosts() {
        return costs;
    }

    public void addCosts(double costs) {
        this.costs += costs;
    }

    public static Store getStoreByEmail(String email) {
        for (Store store : App.stores) {
            if (store.getEmail().equals(email)) {
                return store;
            }
        }
        return null;
    }

    public static Store getStoreByBrand (String brandName) {
        for (Store store : App.stores) {
            if (store.getBrandName().equals(brandName)) {
                return store;
            }
        }
        return null;
    }

    public void deleteStore () {
        App.products.removeIf(product -> product.getBrand().equals(this.brandName));
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
