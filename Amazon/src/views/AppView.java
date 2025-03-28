package views;

import models.App;
import models.enums.Menu;

import java.util.Scanner;

public class AppView{
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do{
            //System.out.println(App.getCurrentMenu()); //TODO: delete later
            App.getCurrentMenu().checkCommand(scanner);
            /* Log In */
            if (App.getCurrentMenu() == Menu.LoginMenu && App.isLogInSuccessful()) {
                App.setLogInSuccessful(false);
                App.setCurrentMenu(Menu.MainMenu);
            }
            /* Log Out */
            else if (App.getCurrentMenu() == Menu.LoginMenu && App.isLogOutRequested()) {
                App.setLogOutRequested(false);
                App.setCurrentMenu(Menu.MainMenu);
            }
            /* Go Back */
            else if (App.isBackRequested()) {
                App.setBackRequested(false);
                App.setCurrentMenu(Menu.MainMenu);
            }

            /* Delete Account */
            else if (App.isDeleteRequested()) {
                App.setDeleteRequested(false);
                App.setCurrentMenu(Menu.MainMenu);
            }
        }



        while (App.getCurrentMenu() != Menu.ExitMenu);
    }

}
