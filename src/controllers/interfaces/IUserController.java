package controllers.interfaces;

import models.Movies;

import java.util.List;

public interface IUserController {
    String createUser(String name, String surname, String gender, String login, String password, String subscriptionType);
    String getUser(int id);
    String getAllUsers();
    String login(String login, String password);
    List<Movies> getAllMovies();
    String watchMovie(String login, String movieTitle);
    boolean updateUserRole(int id, String role);
    boolean deleteUser(int id);
    List<models.User> getAllUsersList();
    models.User getUserByLogin(String login);
    String addMovie(String title, String category, boolean isPremium);
    public String deleteMovie(int id);
    public String updateMovieStatus(int id, boolean isPremium);

}
