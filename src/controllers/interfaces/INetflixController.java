package controllers.interfaces;

import models.Movies;

import java.util.List;

public interface INetflixController {
    String getAllMovies();
    String getMovieById(int id);
    String searchMovies(String keyword);
    List<Movies> findByCategory(String category);
    String watchMovie(int id, models.User user);
}
