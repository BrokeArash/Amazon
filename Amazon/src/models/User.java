package models;

import models.enums.UserType;

abstract public class User {

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }
    public void setType(UserType type) {
        this.type = type;
    }

    String password;
    String email;
    UserType type;


}
