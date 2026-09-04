package views;

import controllers.LoginMenuController;
import models.Result;
import models.enums.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu implements AppMenu{
    final LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        LoginMenuCommands matchedCommand = null;
        for (LoginMenuCommands command : LoginMenuCommands.values()) {
            Matcher matcher = command.getMatcher(input);
            if (matcher != null && matcher.matches()) {
                matchedCommand = command;
                break;
            }
        }

        if (matchedCommand == null ||
        matchedCommand == LoginMenuCommands.CheckName ||
        matchedCommand == LoginMenuCommands.CheckPassword ||
        matchedCommand == LoginMenuCommands.CheckEmail ||
        matchedCommand == LoginMenuCommands.CheckStoreName) {
            System.out.println("invalid command");
        }

        else {
            switch (matchedCommand) {
                case CreateUser:
                    Matcher createUser = LoginMenuCommands.CreateUser.getMatcher(input);
                    String firstName = createUser.group("firstName");
                    String lastName = createUser.group("lastName");
                    String password = createUser.group("password");
                    String reEnterPassword = createUser.group("reEnteredPassword");
                    String email = createUser.group("emailAddress");
                    email = email.trim();
                    Result result = controller.createUser(firstName, lastName, password, reEnterPassword, email);
                    System.out.println(result);
                    break;
                case CreateStore:
                    Matcher createStore = LoginMenuCommands.CreateStore.getMatcher(input);
                    String brand = createStore.group("brand");
                    password = createStore.group("password");
                    reEnterPassword = createStore.group("reEnterPassword");
                    email = createStore.group("email");
                    result = controller.createStore(brand, password, reEnterPassword, email.trim());
                    System.out.println(result);
                    break;
                case LoginUser:
                    Matcher loginUser = LoginMenuCommands.LoginUser.getMatcher(input);
                    email = loginUser.group("email");
                    password = loginUser.group("password");
                    result = controller.loginUser(email.trim(), password.trim());
                    System.out.println(result);
                    break;
                case LoginStore:
                    Matcher loginStore = LoginMenuCommands.LoginStore.getMatcher(input);
                    email = loginStore.group("email");
                    password = loginStore.group("password");
                    result = controller.loginStore(email.trim(), password.trim());
                    System.out.println(result);
                    break;
                case Logout:
                    System.out.println(controller.logout());
                    break;
                case Back:
                    System.out.println(controller.back());
                    break;
                case DeleteAccount:
                    Matcher deleteAccount = LoginMenuCommands.DeleteAccount.getMatcher(input);
                    password = deleteAccount.group("password");
                    reEnterPassword = deleteAccount.group("reEnterPassword");
                    result = controller.deleteAccount(password, reEnterPassword);
                    System.out.println(result);
                    break;
            }
        }

    }
}
