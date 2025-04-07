package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Order {
    private int ID;
    private Address Address;
    private int TIO;
    public ArrayList<Product> products = new ArrayList<>();

    public Order(int ID, Address address, int TIO, ArrayList products) {
        this.ID = ID;
        Address = address;
        this.TIO = TIO;
        this.products.addAll(products);
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAddress() {
        String address = this.Address.getStreet() + ", " + this.Address.getCity() +", " + this.Address.getCountry();
        return address;
    }
    public void setAddress(Address address) {
        Address = address;
    }

    public int getTIO() {
        return TIO;
    }
    public void setTIO(int TIO) {
        this.TIO = TIO;
    }
}
