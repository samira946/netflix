package controllers.interfaces;

import models.Movies;

import java.util.List;

public interface INetflixController {
    String getAllMovies();
    String getMovieById(int id);
    String searchMovies(String keyword);
    String watchMovie(int id);
    List<Movies> findByCategory(String category);
}
