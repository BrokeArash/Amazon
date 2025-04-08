package models;

import controllers.StoreMenuController;
import models.enums.UserType;

import java.util.ArrayList;

public class Costumer extends User{

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    public ArrayList<Order> orders = new ArrayList<>();
    public ArrayList<Address> addresses = new ArrayList<>();
    public ArrayList<Product> shoppingList = new ArrayList<>();
    public ArrayList<Card> cards = new ArrayList<>();
    private int lastAddressId = 0;
    private int lastCardId = 0;

    public Costumer(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    public int getNextAddressId() {
        return ++lastAddressId;
    }

    public int getNextCardId() {
        return ++lastCardId;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static boolean isEmailUnique (String email) {
        return Costumer.getUserByEmail(email) == null;
    }

    public static Costumer getUserByEmail(String email) {
        for (Costumer costumer : App.costumers) {
            if (costumer.getEmail().equals(email)) {
                return costumer;
            }
        }
        return null;
    }
    public static Order getOrderByID(int id, Costumer costumer) {
        for (Order order : costumer.orders) {
            if (order.getID() == id) {
                return order;
            }
        }
        return null;
    }

    public static boolean isPostalUnique (String postal, Costumer user) {
        return Costumer.getAddressByPostal(postal, user) == null;
    }

    public static Address getAddressByPostal(String postal, Costumer user) {
        for (Address address : user.addresses) {
            if (address.getPostal().equals(postal)) {
                return address;
            }
        }
        return null;
    }

    public static Address getAddressByID(int id, Costumer user) {
        for (Address address : user.addresses) {
            if (address.getId() == id) {
                return address;
            }
        }
        return null;
    }

    public static Card getCardByNumber(String number, Costumer mainUser) {
        for (Card card : mainUser.cards) {
            if (card.getCardNumber().equals(number)) {
                return card;
            }
        }
        return null;
    }

    public static Card getCardByID(int id, Costumer mainUser) {
        for (Card card : mainUser.cards) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }

    public static Product getProductByID(int id) {
        for (Product product : App.products) {
            if (product.getID() == id) {
                return product;
            }
        }
        return null;
    }

    public Product getProductInCartByID(int id, Costumer mainUser) {
        for (Product product : this.shoppingList) {
            if (product.getID() == id) {
                return product;
            }
        }
        return null;
    }

    public void Cancel () {
        StoreMenuController controller = new StoreMenuController();
        for (Product product : this.shoppingList) {
            Store thisStore = Store.getStoreByBrand(product.getBrand());
            controller.addStock(product.getID(), product.getNumberOfSold());
        }
    }




    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
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
