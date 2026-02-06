package controllers;

import models.Movies;
import models.User;
import controllers.interfaces.IUserController;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
    }

    public IUserRepository getRepo() {
        return repo;
    }

    @Override
    public String createUser(String name, String surname, String gender, String login, String password, String subscriptionType) {
        boolean male = gender.equalsIgnoreCase("male");
        User user = new User(name, surname, male, login, password, subscriptionType);

        boolean created = repo.createUser(user);
        return (created ? "User was created!" : "User creation was failed!");
    }

    @Override
    public String getUser(int id) {
        User user = repo.getUser(id);
        return (user == null ? "User was not found!" : user.toString());
    }

    @Override
    public String getAllUsers() {
        List<User> users = repo.getAllUsers();

        StringBuilder response = new StringBuilder();
        for (User user : users) {
            response.append(user.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String login(String login, String password) {
        boolean success = repo.checkCredentials(login, password);
        return success ? "SUCCESS: Logged in" : "ERROR: Wrong login or password";
    }

    @Override
    public List<models.Movies> getAllMovies() {
        return repo.getAllMovies();
    }

    @Override
    public String watchMovie(String login, String movieTitle) {
        User user = repo.getUserByLogin(login);
        List<Movies> allMovies = repo.getAllMovies();

        Movies foundMovie = allMovies.stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(movieTitle))
                .findFirst()
                .orElse(null);

        if (foundMovie == null) return "Movie not found";

        String sub = user.getSubscriptionType().toUpperCase();

        if (foundMovie.isPremium() && !sub.equals("PREMIUM")) {
            return "This movie requires Premium subscription";
        }

        if (sub.equals("BASIC")) {
            return "Enjoy your movie! '" + foundMovie.getTitle() + "' (contains ads)";
        }

        return "Enjoy your movie! '" + foundMovie.getTitle() + "'";
    }
}
