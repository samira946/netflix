import controllers.interfaces.IUserController;
import models.Movies;
import models.User;
import repositories.interfaces.IMovieRepository;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final IUserController controller;
    private String currentUserName = "";
    private String currentUserRole = "NOT_LOGGED";


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

                if (option == 1 && currentUserRole.equals("NOT_LOGGED")) {
                    registrationFlow();
                } else if (option == 2 && currentUserRole.equals("NOT_LOGGED")) {
                    loginFlow();
                } else if (option == 3 && currentUserRole != null) {
                    showMoviesMenu();
                    movieSearchMenu();
                } else if (option == 4 && (currentUserRole.equals("MANAGER") || currentUserRole.equals("ADMIN"))) {
                    managerPanel();
                } else if (option == 5 && currentUserRole.equals("ADMIN")) {
                    adminPanel();
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
        if (currentUserRole.equals("NOT_LOGGED")) {
            System.out.println("1. REGISTER NEW ACCOUNT");
            System.out.println("2. LOGIN");
        } else if (isLoggedIn) {
            System.out.println("--- Logged in as: " + currentLogin + " [" + currentUserRole + "] ---");
            System.out.println("3. WATCH MOVIES");

            if (currentUserRole.equals("MANAGER") || currentUserRole.equals("ADMIN")) {
                System.out.println("4. MANAGE MOVIES (Add/Delete/Status)");
            }

            if (currentUserRole.equals("ADMIN")) {
                System.out.println("5. MANAGE USERS & ROLES (Ban/Promote/Show Passwords)");
            }
        }
        System.out.println("0. Exit");
        System.out.print("Select: ");
    }


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
            User user = controller.getUserByLogin(login);

            if (user != null) {
                this.currentUserRole = user.getRole();
            }
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

        if (!validateUserInput(name, login, password)) {
            System.out.println("Registration aborted due to validation errors.");

            return;
        }

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
        System.out.println("1. [BASIC]    - 720p, contains ads                  -> $9.99");
        System.out.println("2. [STANDARD] - 1080p, no ads                       -> $15.49");
        System.out.println("3. [PREMIUM]  - 4K+HDR, no ads, all Movies unlocked -> $19.99");
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

    private void showMoviesMenu() {
        System.out.println("\n--- MOVIES LIST ---");

        List<Movies> movies = controller.getAllMovies();

        if (movies == null || movies.isEmpty()) {
            System.out.println("No movies available right now.");
        } else {
            movies.forEach(movie -> System.out.println(
                    "🎬 " + movie.getTitle() + " | Category: " + movie.getCategory() +
                            (movie.isPremium() ? " [PREMIUM 💎]" : "")
            ));
        }
    }

    private void movieSearchMenu() {
        while (true) {
            System.out.println("\n--- MOVIE SEARCH ---");
            System.out.println("1. Search by Category");
            System.out.println("2. Search by Title");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter category (Fantasy, Sci-Fi, Horror, Comedy): ");
                String cat = scanner.nextLine();

                controller.getAllMovies().stream()
                        .filter(m -> m.getCategory().equalsIgnoreCase(cat))
                        .forEach(m -> System.out.println("- " + m.getTitle() + (m.isPremium() ? " [PREMIUM]" : "")));
            }
            else if (choice == 2) {
                System.out.print("Enter movie title to watch: ");
                String title = scanner.nextLine();

                System.out.println("\n>>> " + controller.watchMovie(currentLogin, title));
            }
            else if (choice == 0) break;
        }
    }

    private void adminPanel() {
        System.out.println("\n--- ADMIN PANEL ---");

        List<User> users = controller.getAllUsersList();

        users.forEach(u -> System.out.println(
                "ID: " + u.getId() + " | Login: " + u.getLogin() +
                        " | Pass: " + u.getPassword() + " | Role: " + u.getRole()
        ));

        System.out.println("\nActions:");
        System.out.println("1. Promote to Manager");
        System.out.println("2. Demote to User");
        System.out.println("3. Ban User");
        System.out.println("0. Back");
        int action = scanner.nextInt();

        if (action == 1) {
            System.out.print("Enter User ID to promote: ");
            int id = scanner.nextInt();
            if(controller.updateUserRole(id, "MANAGER")) {
                System.out.println("User promoted!");
            }
        } else if (action == 3) {
            System.out.print("Enter User ID to BAN: ");
            int id = scanner.nextInt();
            if(controller.deleteUser(id)) {
                System.out.println("User deleted.");
            }
        } else if (action == 2) {
            System.out.print("Enter Manager ID to demote to User: ");
            int id = scanner.nextInt();
            if (controller.updateUserRole(id, "USER")) System.out.println("Manager demoted to regular User.");
        }
    }

    private void managerPanel() {
        while (true) {
            System.out.println("\n--- MOVIE MANAGEMENT ---");
            System.out.println("1. Add new movie");
            System.out.println("2. Delete movie");
            System.out.println("3. Toggle Premium status");
            System.out.println("4. List all movies");
            System.out.println("0. Back to main menu");
            System.out.print("Select action: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                addMovieProcess();
            } else if (choice == 2) {
                deleteMovieProcess();
            } else if (choice == 3) {
                togglePremiumProcess();
            } else if (choice == 4) {
                listMoviesForManager();
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private void addMovieProcess() {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Is it premium? (true/false): ");
        boolean isPremium = scanner.nextBoolean();

        System.out.println(controller.addMovie(title, category, isPremium));
    }

    private void deleteMovieProcess() {
        System.out.print("Enter Movie ID to delete: ");
        int id = scanner.nextInt();
        System.out.println(controller.deleteMovie(id));
    }

    private void togglePremiumProcess() {
        System.out.print("Enter Movie ID to change status: ");
        int id = scanner.nextInt();
        System.out.print("Set premium status (true/false): ");
        boolean status = scanner.nextBoolean();
        System.out.println(controller.updateMovieStatus(id, status));
    }

    private void listMoviesForManager() {
        List<Movies> movies = controller.getAllMovies();
        System.out.println("\n--- DETAILED MOVIE LIST ---");

        if (movies.isEmpty()) {
            System.out.println("No movies found in database.");
            return;
        }

        for (Movies m : movies) {
            String premiumTag = m.isPremium() ? " [PREMIUM 💎]" : "";
            System.out.println("ID: " + m.getId() + " 🎬 " + m.getTitle() +
                    " | Category: " + m.getCategory() + premiumTag);
        }
    }

}