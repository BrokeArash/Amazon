package models.enums;

import views.*;

import java.util.Scanner;

public enum Menu {
    MainMenu (new MainMenu()),
    LoginMenu (new LoginMenu()),
    UserMenu (new UserMenu()),
    ProductMenu (new ProductMenu()),
    StoreMenu (new StoreMenu()),
    ExitMenu (new ExitMenu());

    private final AppMenu menu;

    Menu(AppMenu menu) {
        this.menu = menu;
    }
    public void checkCommand(Scanner scanner) {
        this.menu.check(scanner);
    }
}
