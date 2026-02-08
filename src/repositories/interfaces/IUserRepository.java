package repositories.interfaces;

import models.User;

import java.util.List;

public interface IUserRepository {
    boolean createUser(User user);
    User getUser(int id);
    List<User> getAllUsers();
    User login(String login, String password);
    boolean checkCredentials(String login, String password);
    User getUserByLogin(String login);

    List<models.Movies> getAllMovies();

    boolean updateUserRole(int id, String role);
    boolean deleteUser(int id);
}
