import controllers.interfaces.IUserController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {

    private final Scanner scanner = new Scanner(System.in);
    private final IUserController controller;

    public MyApplication(IUserController controller) {
        this.controller = controller;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to My Application");
        System.out.println("Select option:");
        System.out.println("1. Get all users");
        System.out.println("2. Get user by id");
        System.out.println("3. Create user");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter option (1-3): ");
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> getAllUsersMenu();
                    case 2 -> getUserByIdMenu();
                    case 3 -> createUserMenu();
                    default -> {
                        return;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be integer!");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("*************************");
        }
    }

    // ===== VALIDATION (код со скрина) =====
    private boolean validateUserInput(String name, String login, String password) {
        if (name.length() < 2) {
            System.out.println("[QA Error]: Name is too short!");
            return false;
        }

        if (login.contains(" ") || password.length() < 4) {
            System.out.println("[QA Error]: Invalid login or weak password!");
            return false;
        }

        return true;
    }

    public void getAllUsersMenu() {
        String response = controller.getAllUsers();
        System.out.println(response);
    }

    public void getUserByIdMenu() {
        System.out.println("Please enter id");
        int id = scanner.nextInt();

        String response = controller.getUser(id);
        System.out.println(response);
    }

    // ===== REGISTRATION FLOW =====
    public void createUserMenu() {
        System.out.println("Please enter name");
        String name = scanner.next();

        System.out.println("Please enter surname");
        String surname = scanner.next();

        System.out.println("Please enter gender (male/female)");
        String gender = scanner.next();

        System.out.println("Please enter login");
        String login = scanner.next();

        System.out.println("Please enter password");
        String password = scanner.next();

        if (validateUserInput(name, login, password)) {
            String result = controller.createUser(name, surname, gender, login, password);
            System.out.println(result);
        } else {
            System.out.println("Registration aborted due to validation errors.");
        }
    }
}
