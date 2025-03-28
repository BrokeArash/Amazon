package models;

public class Address {
    private int id;
    private String street;
    private String city;
    private String country;
    private String postal;
    private static int lastAssigned = 0;

    public Address(String street, String city, String country, String postal) {
        this.id = ++lastAssigned;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postal = postal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }
}
