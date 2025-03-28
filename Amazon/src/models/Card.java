package models;

public class Card {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private float value;
    private int id;
    private static int lastAssigned = 0;

    public Card(String cardNumber, String expirationDate, String cvv, float value) {
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.value = value;
        this.id = ++lastAssigned;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public float getValue() {
        return value;
    }

    public void addValue(float value) {
        this.value += value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
