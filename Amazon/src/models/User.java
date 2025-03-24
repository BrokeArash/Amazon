package models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public User(String firstName, String lastName, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }
}
