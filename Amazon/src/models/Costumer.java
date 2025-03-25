package models;

public class Costumer extends User{

    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public Costumer(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
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
}
