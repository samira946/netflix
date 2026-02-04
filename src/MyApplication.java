import controllers.interfaces.IUserController;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IUserController controller;
    private String currentUserName = "";

    // ✅ добавили состояние логина (минимально)
    private boolean isLoggedIn = false;
    private String currentLogin = "";

    public MyApplication(IUserController controller) {
        this.controller = controller;
    }

    private void printLogo() {
        System.out.println("* NETFLIX CLONE PROJECT         *");
    }

    public void start() {
        printLogo();
        while (true) {
            showMainMenu();
            try {
                int option = scanner.nextInt();

                if (option == 1) {
                    getAllUsersMenu();
                } else if (option == 2) {
                    getUserByIdMenu();
                } else if (option == 3) {
                    registrationFlow();
                } else if (option == 4) {   // ✅ добавили login
                    loginFlow();
                } else if (option == 0) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option, try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number!");
                scanner.nextLine();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. List all users");
        System.out.println("2. Find user by ID");
        System.out.println("3. REGISTER NEW ACCOUNT");
        System.out.println("4. LOGIN"); // ✅ добавили пункт меню

        // (опционально) показать статус
        if (isLoggedIn) {
            System.out.println("Logged in as: " + currentLogin);
        }

        System.out.println("0. Exit");
        System.out.print("Select: ");
    }

    // ✅ новый метод логина (минимально)
    private void loginFlow() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Login: ");
        String login = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        String result = controller.login(login, password);

        if (result != null && result.toLowerCase().contains("success")) {
            isLoggedIn = true;
            currentLogin = login;
            currentUserName = login;
        }

        System.out.println(result);
    }

    private void registrationFlow() {
        System.out.println("\n--- Registration Form ---");
        System.out.print("Name: ");
        String name = scanner.next();
        System.out.print("Surname: ");
        String surname = scanner.next();
        System.out.print("Gender (male/female): ");
        String gender = scanner.next();
        System.out.print("Create Login: ");
        String login = scanner.next();
        System.out.print("Create Password: ");
        String password = scanner.next();
        this.currentUserName = name;
        String subscriptionType = subscriptionStep();

        String result = controller.createUser(name, surname, gender, login, password, subscriptionType);
        System.out.println(result);
    }

    public void getAllUsersMenu() {
        System.out.println(controller.getAllUsers());
    }

    public void getUserByIdMenu() {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        System.out.println(controller.getUser(id));
    }

    private String subscriptionStep() {
        System.out.println("\nCongratulations, " + currentUserName + "!");
        System.out.println("Choose your Netflix Plan to start watching:");
        System.out.println("1. [BASIC]    - 720p (1 device)   -> $9.99");
        System.out.println("2. [STANDARD] - 1080p (2 devices) -> $15.49");
        System.out.println("3. [PREMIUM]  - 4K+HDR (4 devices) -> $19.99");
        System.out.print("Select plan (1-3): ");

        int choice = scanner.nextInt();
        String planName;
        switch (choice) {
            case 1 -> planName = "BASIC";
            case 2 -> planName = "STANDARD";
            case 3 -> planName = "PREMIUM";
            default -> planName = "NONE";
        }

        if (!planName.equals("NONE")) {
            System.out.println("\n[SUCCESS] Your " + planName + " subscription is now active!");
            System.out.println("Enjoy your movies!");
        } else {
            System.out.println("No plan selected. You can choose it later in settings.");
        }

        return planName;
    }
}
