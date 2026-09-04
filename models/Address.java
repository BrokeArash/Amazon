package models;

public class Address {
    private int id;
    private final String street;
    private final String city;
    private final String country;
    private final String postal;

    public Address(int id, String street, String city, String country, String postal) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostal() {
        return postal;
    }
}
