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

    public static boolean isEmailUnique (String email) {
        return User.getUserByEmail(email) == null;
    }

    public static User getUserByEmail(String email) {
        for (User user : App.users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }



    String password;
    String email;
    UserType type;


}
