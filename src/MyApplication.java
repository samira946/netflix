
import controllers.interfaces.IUserController;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.interfaces.INetflixController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final INetflixController controller;

    public MyApplication(INetflixController controller) {
        this.controller = controller;
    }

    private void mainMenu() {
        System.out.println();
        System.out.println("Welcome to Netflix Console App");
        System.out.println("Select option:");
        System.out.println("1. View all movies");
        System.out.println("2. View movie by ID");
        System.out.println("3. Search movies");
        System.out.println("4. Watch movie");
        System.out.println("0. Exit");
        System.out.println();
        System.out.print("Enter option (0-4): ");
    }

    public void start() {
        while (true) {
            mainMenu();
            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        getAllMoviesMenu();
                        break;
                    case 2:
                        getMovieByIdMenu();
                        break;
                    case 3:
                        searchMoviesMenu();
                        break;
                    case 4:
                        watchMovieMenu();
                        break;
                    default:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer!");
                scanner.nextLine(); // clear invalid input
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("*************************");
        }
    }

    private void getAllMoviesMenu() {
        String response = controller.getAllMovies();
        System.out.println(response);
    }

    private void getMovieByIdMenu() {
        System.out.println("Please enter movie ID:");
        int id = scanner.nextInt();

        String response = controller.getMovieById(id);
        System.out.println(response);
    }

    private void searchMoviesMenu() {
        System.out.println("Enter keyword to search:");
        String keyword = scanner.next();

        String response = controller.searchMovies(keyword);
        System.out.println(response);
    }

    private void watchMovieMenu() {
        System.out.println("Enter movie ID to watch:");
        int id = scanner.nextInt();

        String response = controller.watchMovie(id);
        System.out.println(response);
    }
}