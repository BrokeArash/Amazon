package controllers;

import models.*;
import models.enums.LoginMenuCommands;
import models.enums.UserType;
import java.util.regex.Matcher;

public class LoginMenuController {

    public Result createUser(String firstName, String lastName, String password,
                             String reEnterPassword, String email) {
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
        } else if (!Costumer.isEmailUnique(email)){
            return new Result(false, "Email already exists.");
        } else {
            Costumer newCostumer = new Costumer(firstName, lastName, password, email);
            newCostumer.setType(UserType.Costumer);
            App.costumers.add(newCostumer);
            App.users.add(newCostumer);
            return new Result(true, "User account for " +
                    firstName + " " + lastName + " created successfully.");
        }
    }

    public Result createStore(String brand, String password, String reEnterPassword, String email) {
        Matcher checkPassword = LoginMenuCommands.CheckPassword.getMatcher(password);
        Matcher checkRePass = LoginMenuCommands.CheckPassword.getMatcher(reEnterPassword);
        Matcher checkEmail = LoginMenuCommands.CheckEmail.getMatcher(email);

        if (brand.length() < 5) {
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
            brand = brand.substring(1, brand.length() - 1);
            User newStore = new Store(brand, password, email);
            App.users.add(newStore);
            App.stores.add((Store) newStore);
            return new Result(true, "Store account for \"" + brand + "\" created successfully.");
        }
    }

    public Result loginUser(String email, String password) {
        Costumer user = Costumer.getUserByEmail(email.trim());
        if (user == null) {
            return new Result(false, "No user account found with the provided email.");
        } else if (!user.getPassword().trim().equals(password)) {
            return new Result(false, "Password is incorrect.");
        } else {
            App.setLoggedIn(user);
            App.setLoggedInType(UserType.Costumer);
            App.setLogInSuccessful(true);
            return new Result(true, "User logged in successfully. Redirecting to the MainMenu ...");
        }
    }

    public Result loginStore(String email, String password) {
        Store user = Store.getStoreByEmail(email);
        if (user == null) {
            return new Result(false, "No store account found with the provided email.");
        } else if (!Store.getStoreByEmail(email).getPassword().trim().equals(password)) {
            return new Result(false, "Password is incorrect.");
        } else {
            App.setLoggedIn(user);
            App.setLoggedInType(UserType.Store);
            App.setLogInSuccessful(true);
            return new Result(true, "Store logged in successfully. Redirecting to the MainMenu ...");
        }
    }

    public Result logout() {
        if (App.getLoggedIn() == null) {
            return new Result(false, "You should login first.");
        } else {
            App.setLoggedIn(null);
            App.setLoggedInType(null);
            App.setLogOutRequested(true);
            return new Result(true, "Logged out successfully. Redirecting to the MainMenu ...");
        }
    }

    public Result back() {
        App.setBackRequested(true);
        return new Result(true, "Redirecting to the MainMenu ...");

    }

    public Result deleteAccount(String password, String reEnterPassword) {
        if (App.getLoggedIn() == null) {
            return new Result(false, "You should login first.");
        } else if (!password.equals(reEnterPassword)) {
            return new Result(false, "Re-entered password is incorrect.");
        } else if (!App.getLoggedIn().getPassword().equals(password)) {
            return new Result(false, "Password is incorrect.");
        } else {
            App.users.remove(App.getLoggedIn());
            if (App.getLoggedInType().equals(UserType.Costumer)) {
                Costumer thisCostumer = (Costumer) App.getLoggedIn();
                App.costumers.remove(thisCostumer);
                thisCostumer.Cancel();
                thisCostumer.shoppingList.clear();

            } else {
                Store thisStore = (Store) App.getLoggedIn();
                App.stores.remove(thisStore);
                thisStore.products.clear();
                thisStore.deleteStore();
                App.products.removeIf(product -> product.getBrand().equals(thisStore.getBrandName()));
                for (Costumer costumer : App.costumers) {
                    costumer.shoppingList.removeIf(product -> product.getBrand().equals(thisStore.getBrandName()));
                }
            }
            App.setDeleteRequested(true);
            App.setLoggedIn(null);
            App.setLoggedInType(null);
            return new Result(true, "Account deleted successfully. Redirecting to the MainMenu ...");

        }


    }
}
