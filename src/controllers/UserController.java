package controllers;

import models.User;
import controllers.interfaces.IUserController;
import repositories.interfaces.IUserRepository;

import java.util.List;

public class UserController implements IUserController {
    private final IUserRepository repo;

    public UserController(IUserRepository repo) {
        this.repo = repo;
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
}
