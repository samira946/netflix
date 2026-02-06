package repositories.interfaces;

import models.Movies;

import java.util.List;

public interface IMovieRepository {
    // Здесь можно добавить поиск по категории
    List<Movies> findByCategory(String category);
}