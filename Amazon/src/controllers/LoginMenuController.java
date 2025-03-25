package controllers;

import models.App;
import models.Result;
import models.Store;
import models.User;
import models.enums.LoginMenuCommands;
import models.enums.Menu;

import java.util.regex.Matcher;

public class LoginMenuController {

    public Result createUser(String firstName, String lastName, String password, String reEnterPassword, String email) {
        Matcher checkFirst = LoginMenuCommands.CheckName.getMatcher(firstName);
        Matcher checkLast = LoginMenuCommands.CheckName.getMatcher(lastName);
        Matcher checkPassword = LoginMenuCommands.CheckPassword.getMatcher(password);
        Matcher checkRePass = LoginMenuCommands.CheckPassword.getMatcher(reEnterPassword);
        Matcher checkEmail = LoginMenuCommands.CheckEmail.getMatcher(email);

        if (firstName.length() < 3 || lastName.length() < 3) {
            return new Result(false, "Name is too short.");
        } else if (checkFirst == null || checkLast == null) {
            return new Result(false, "Incorrect name format.");
        } else if (checkPassword == null) {
            return new Result(false, "Incorrect password format.");
        } else if (checkRePass == null || !password.equals(reEnterPassword)) {
            return new Result(false, "Re-entered password is incorrect.");
        } else if (checkEmail == null) {
            return new Result(false, "Incorrect email format.");
        } else if (!User.isEmailUnique(email)){
            return new Result(false, "Email already exists.");
        } else {
            App.users.add(new User(firstName, lastName, password, email));
            return new Result(true, "User account for "+firstName + " " + lastName + " created successfully.");
        }
    }

    public Result createStore(String brand, String password, String reEnterPassword, String email) {
        Matcher checkPassword = LoginMenuCommands.CheckPassword.getMatcher(password);
        Matcher checkRePass = LoginMenuCommands.CheckPassword.getMatcher(reEnterPassword);
        Matcher checkEmail = LoginMenuCommands.CheckEmail.getMatcher(email);

        if (brand.length() < 3) {
            return new Result(false, "Brand name is too short.");
        } else if (checkPassword == null) {
            return new Result(false, "Incorrect password format.");
        } else if (checkRePass == null || !password.equals(reEnterPassword)) {
            return new Result(false, "Re-entered password is incorrect.");
        } else if (checkEmail == null) {
            return new Result(false, "Incorrect email format.");
        } else if (!User.isEmailUnique(email)) {
            return new Result(false, "Email already exists.");
        } else {
            App.stores.add(new Store(brand, password, email));
            return new Result(true, "Store account for " + brand + " created successfully.");
        }
    }


    public Result loginUser(String email, String password) {

        if (User.getUserByEmail(email) == null) {
            return new Result(false, "No user account found with the provided email.");
        } else if (!User.getUserByEmail(email).getPassword().equals(password)) {
            return new Result(false, "Password is incorrect.");
        } else {
            App.setLoggedInUser(User.getUserByEmail(email));
            App.setLogInSuccessful(true);
            return new Result(true, "User logged in successfully. Redirecting to the MainMenu ...");
        }
    }

    public Result loginStore(String email, String password) {
        if (Store.getStoreByEmail(email) == null) {
            return new Result(false, "No store account found with the provided email.");
        } else if (!Store.getStoreByEmail(email).getPassword().equals(password)) {
            return new Result(false, "Password is incorrect.");
        } else {
            App.setLoggedInUser(User.getUserByEmail(email));
            App.setLogInSuccessful(true);
            return new Result(true, "Store logged in successfully. Redirecting to the MainMenu ...");
        }
    }

    public Result logout() {
        if (App.getLoggedInStore() == null || App.getLoggedInUser() == null) {
            return new Result(false, "You should login first.");
        } else {
            App.setLoggedInStore(null);
            App.setLoggedInUser(null);
            App.setLogOutRequested(true);
            return new Result(true, "Logged out successfully. Redirecting to the MainMenu ...");
        }
    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "Redirecting to the MainMenu ...");

    }

    public Result deleteAccount(String password, String reEnterPassword) {
        if (App.getLoggedInStore() == null && App.getLoggedInUser() == null) {
            return new Result(false, "You should login first.");
        }
        // As User
        if (App.getLoggedInUser() != null && App.getLoggedInStore() == null) {
            if (!password.equals(reEnterPassword)) {
                return new Result(false, "Re-entered password is incorrect.");
            } else if ()
        }

        } else if ()

    }
}
