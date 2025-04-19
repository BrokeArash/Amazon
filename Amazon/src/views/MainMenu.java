package views;

import models.App;
import models.enums.MainMenuCommands;
import models.enums.Menu;
import models.enums.UserType;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu{
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        MainMenuCommands matchedCommand = null;
        for (MainMenuCommands command : MainMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null) {
            System.out.println("invalid command");
        } else {
            switch (matchedCommand) {
                case GoToMenu:
                    Matcher goToMatcher = MainMenuCommands.GoToMenu.getMatcher(input);
                    String menu = goToMatcher.group("menu");
                    Menu selectedMenu = Menu.findMenu(menu);
                    if (selectedMenu.equals(Menu.UserMenu) && (App.getLoggedIn() == null ||
                            App.getLoggedInType().equals(UserType.Store))) {
                        System.out.println("You need to login as a user before accessing the user menu.");
                    } else if (selectedMenu.equals(Menu.StoreMenu) && (App.getLoggedIn() == null ||
                            App.getLoggedInType().equals(UserType.Costumer))) {
                        System.out.println("You should login as store before accessing the store menu.");
                    } else {
                        App.setCurrentMenu(selectedMenu);
                        System.out.println("Redirecting to the " + menu + " ...");
                    }
                    break;
                case Exit:
                    App.setCurrentMenu(Menu.ExitMenu);
                    break;
            }

        }
    }
}
