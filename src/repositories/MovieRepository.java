package repositories;

import data.interfaces.IDB;
import models.Movies;
import repositories.interfaces.IMovieRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieRepository implements IMovieRepository {
    private final IDB db;

    public MovieRepository(IDB db) { this.db = db; }

    @Override
    public List<Movies> getAllMovies() {
        List<Movies> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try (Connection con = db.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(new Movies(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getBoolean("is_premium")
                ));
            }
        } catch (SQLException e) { System.out.println(e.getMessage()); }
        return movies;
    }

    public List<Movies> findByCategory(String category) {
        return getAllMovies().stream()
                .filter(m -> m.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Movies getMovieById(int id) {
        return getAllMovies().stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
