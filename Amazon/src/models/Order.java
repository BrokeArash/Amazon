package models;

import java.util.ArrayList;

public class Order {
    private int ID;
    private Address Address;
    private int TIO;
    public ArrayList<Product> products = new ArrayList<>();

    public Order(int ID, Address address, int TIO, ArrayList<Product> products) {
        this.ID = ID;
        Address = address;
        this.TIO = TIO;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public Address getAddress() {
        return Address;
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
