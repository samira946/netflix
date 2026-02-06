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

}
