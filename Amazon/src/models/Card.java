package models;

public class Card {
    private final String cardNumber;
    private final String expirationDate;
    private final String cvv;
    private double value;
    private int id;

    public Card(int id, String cardNumber, String expirationDate, String cvv, double value) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.value = value;
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getValue() {
        return value;
    }

    public void addValue(double value) {
        this.value += value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
