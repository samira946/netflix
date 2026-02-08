package repositories;

import data.interfaces.IDB;
import models.Movies;
import repositories.interfaces.IMovieRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository implements IMovieRepository {
    private final IDB db;

    public MovieRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean addMovie(Movies movie) {
        String sql = "INSERT INTO movies (title, category, is_premium) VALUES (?, ?, ?)";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, movie.getTitle());
            st.setString(2, movie.getCategory());
            st.setBoolean(3, movie.isPremium());

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Movies> getAllMovies() {
        List<Movies> movies = new ArrayList<>();
        String sql = "SELECT id, title, category, is_premium FROM movies";
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(new Movies(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getBoolean("is_premium")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return movies;
    }

    @Override
    public boolean deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    @Override
    public boolean updateMovieStatus(int id, boolean isPremium) {
        String sql = "UPDATE movies SET is_premium = ? WHERE id = ?";
        try (Connection con = db.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setBoolean(1, isPremium);
            st.setInt(2, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

}