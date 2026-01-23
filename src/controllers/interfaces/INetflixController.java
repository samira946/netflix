package controllers.interfaces;

public interface INetflixController {
    String getAllMovies();
    String getMovieById(int id);
    String searchMovies(String keyword);
    String watchMovie(int id);
}
