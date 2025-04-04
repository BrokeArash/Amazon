package models;

import models.enums.Menu;
import models.enums.UserType;

import java.util.ArrayList;

public class App {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Costumer> costumers = new ArrayList<>();
    public static ArrayList<Store> stores = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
    private static User loggedIn = null;
    private static UserType loggedInType = null;
    private static Menu currentMenu = Menu.MainMenu;
    private static boolean logInSuccessful = false;
    private static boolean logOutRequested = false;
    private static boolean backRequested = false;
    private static boolean DeleteRequested = false;



    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static User getLoggedIn() {
        return loggedIn;
    }
    public static void setLoggedIn(User loggedIn) {
        App.loggedIn = loggedIn;
    }
    public static UserType getLoggedInType() {
        return loggedInType;
    }
    public static void setLoggedInType(UserType loggedInType) {
        App.loggedInType = loggedInType;
    }

    public static boolean isLogInSuccessful() {
        return logInSuccessful;
    }
    public static void setLogInSuccessful(boolean logInSuccessful) {
        App.logInSuccessful = logInSuccessful;
    }

    public static boolean isLogOutRequested() {
        return logOutRequested;
    }
    public static void setLogOutRequested(boolean logOutRequested) {
        App.logOutRequested = logOutRequested;
    }

    public static boolean isBackRequested() {
        return backRequested;
    }
    public static void setBackRequested(boolean backRequested) {
        App.backRequested = backRequested;
    }


    public static boolean isDeleteRequested() {
        return DeleteRequested;
    }
    public static void setDeleteRequested(boolean deleteRequested) {
        DeleteRequested = deleteRequested;
    }
}
