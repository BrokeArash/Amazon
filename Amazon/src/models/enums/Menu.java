package models.enums;

import views.*;

import java.util.Scanner;

public enum Menu {
    Login (new LoginMenu()),
    User (new UserMenu()),
    Product (new ProductMenu()),
    Store (new StoreMenu()),
    Exit(new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
