package repositories.interfaces;

import models.Movies;

import java.util.List;

public interface IMovieRepository {
    List<Movies> getAllMovies();
    Movies getMovieById(int id);
    List<Movies> findByCategory(String category);
}