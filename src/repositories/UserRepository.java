package repositories;

import data.interfaces.IDB;
import models.Movies;
import models.User;
import repositories.interfaces.IUserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private final IDB db;
    private Connection connection;


    public UserRepository(IDB db) {
        this.db = db;
        try {
            this.connection = db.getConnection();
        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    @Override
    public boolean createUser(User user) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO users(name,surname,gender,login,password,subscription_type) VALUES (?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setString(1, user.getName());
            st.setString(2, user.getSurname());
            st.setBoolean(3, user.getGender());
            st.setString(4, user.getLogin());
            st.setString(5, user.getPassword());
            st.setString(6, user.getSubscriptionType());

            st.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(int id) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id, name, surname, gender, login, password, subscription_type FROM users WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("subscription_type"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return null;
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id, name, surname, gender, login, password, subscription_type, role FROM users";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBoolean("gender"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("subscription_type"),
                        rs.getString("role")
                ));
            }

            return users;
        } catch (SQLException e) {
            System.out.println("sql error: " + e.getMessage());
        }
        return users;
    }

    @Override
    public User login(String login, String password) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        String sql = "SELECT id, name, surname, gender, login, password, subscription_type, role FROM users WHERE login = ? LIMIT 1";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, login);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getBoolean("gender"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("subscription_type"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getUserByLogin: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean checkCredentials(String login, String password) {
        String sql = "SELECT 1 FROM users WHERE login = ? AND password = ? LIMIT 1";

        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, login);
            st.setString(2, password);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }
    @Override
    public List<Movies> getAllMovies() {
        List<Movies> movies = new ArrayList<>();
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, title, category, is_premium FROM movies")) {

            while (rs.next()) {
                movies.add(new Movies(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getBoolean("is_premium")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error in getAllMovies: " + e.getMessage());
        }
        return movies;
    }
    public boolean updateUserRole(int id, String role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, role);
            st.setInt(2, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}
