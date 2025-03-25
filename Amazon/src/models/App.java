package models;

import models.enums.Menu;

import java.util.ArrayList;

public class App {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Store> stores = new ArrayList<>();
    private static User loggedInUser = null;
    private static Store loggedInStore = null;
    private static Menu currentMenu = Menu.MainMenu;
    private static boolean logInSuccessful = false;
    private static boolean logOutRequested = false;
    private static boolean backRequested = false;



    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }

    public static Store getLoggedInStore() {
        return loggedInStore;
    }
    public static void setLoggedInStore(Store loggedInStore) {
        App.loggedInStore = loggedInStore;
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
}
