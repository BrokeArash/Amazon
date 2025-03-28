package views;

import models.App;
import models.enums.MainMenuCommands;
import models.enums.Menu;

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
                    String menu = goToMatcher.group("menu"); //TODO: might not match
                    Menu selectedMenu = Menu.findMenu(menu);
                    if (selectedMenu.equals(Menu.UserMenu) && App.getLoggedIn() == null) {
                        System.out.println("You need to login as a user before accessing the user menu.");
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
