package controllers;

import models.Movies;
import models.User;
import controllers.interfaces.IUserController;
import repositories.interfaces.IMovieRepository;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository userRepo;
    private final IMovieRepository movieRepo;

    public UserController(IUserRepository userRepo, IMovieRepository movieRepo) {
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;
    }

    @Override
    public String addMovie(String title, String category, boolean isPremium) {
        Movies movie = new Movies(title, category, isPremium);
        return movieRepo.addMovie(movie) ? "Movie added!" : "Error adding movie";
    }

    @Override
    public String createUser(String name, String surname, String gender, String login, String password, String subscriptionType) {
        boolean male = gender.equalsIgnoreCase("male");
        User user = new User(name, surname, male, login, password, subscriptionType);
        return userRepo.createUser(user) ? "User was created!" : "User creation failed!";
    }

    @Override
    public String getUser(int id) {
        User user = userRepo.getUser(id);
        return (user == null ? "User was not found!" : user.toString());
    }

    @Override
    public String getAllUsers() {
        List<User> users = userRepo.getAllUsers();
        StringBuilder response = new StringBuilder();
        for (User user : users) {
            response.append(user.toString()).append("\n");
        }
        return response.toString();
    }

    @Override
    public String login(String login, String password) {
        boolean success = userRepo.checkCredentials(login, password);
        return success ? "SUCCESS: Logged in" : "ERROR: Wrong login or password";
    }

    @Override
    public List<Movies> getAllMovies() {
        return movieRepo.getAllMovies();
    }

    @Override
    public String watchMovie(String login, String movieTitle) {
        User user = userRepo.getUserByLogin(login);
        List<Movies> allMovies = movieRepo.getAllMovies();

        List<Movies> foundMovies = allMovies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(movieTitle.toLowerCase()))
                .toList();

        if (foundMovies.isEmpty()) return "Movie not found";
        if (foundMovies.size() > 1) return "Movie not found (Too many matches, please be more specific)";

        Movies foundMovie = foundMovies.get(0);
        String sub = user.getSubscriptionType().toUpperCase();

        if (foundMovie.isPremium() && !sub.equals("PREMIUM")) {
            return "This movie requires Premium subscription";
        }

        if (sub.equals("BASIC")) {
            return "Enjoy your movie! '" + foundMovie.getTitle() + "' (contains ads)";
        }

        return "Enjoy your movie! '" + foundMovie.getTitle() + "'";
    }

    @Override
    public boolean updateUserRole(int id, String role) {
        return userRepo.updateUserRole(id, role);
    }

    @Override
    public boolean deleteUser(int id) {
        return userRepo.deleteUser(id);
    }

    @Override
    public List<User> getAllUsersList() {
        return userRepo.getAllUsers();
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepo.getUserByLogin(login);
    }

    @Override
    public String deleteMovie(int id) {
        return movieRepo.deleteMovie(id) ? "Movie deleted!" : "Failed to delete movie.";
    }

    @Override
    public String updateMovieStatus(int id, boolean isPremium) {
        return movieRepo.updateMovieStatus(id, isPremium) ? "Status updated!" : "Update failed.";
    }


}