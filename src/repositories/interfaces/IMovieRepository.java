package repositories.interfaces;

import models.Movies;

import java.util.List;

public interface IMovieRepository {
    boolean addMovie(Movies movie);
    List<Movies> getAllMovies();
    public boolean deleteMovie(int id);
    public boolean updateMovieStatus(int id, boolean isPremium);
}