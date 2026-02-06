package controllers;

import controllers.interfaces.INetflixController;
import models.Movies;
import models.User;
import models.Subscriptiondetails;
import models.SubscriptionFactory;
import repositories.interfaces.IMovieRepository;
import java.util.List;

public class MovieController implements INetflixController {
    private final IMovieRepository repo;

    public MovieController(IMovieRepository repo) { this.repo = repo; }

    @Override
    public String getAllMovies() {
        return repo.getAllMovies().toString();
    }

    @Override
    public String watchMovie(int id, User user) {
        Movies movie = repo.getMovieById(id);
        if (movie == null) return "Movie not found!";

        Subscriptiondetails sub = SubscriptionFactory.getDetails(user.getSubscriptionType());

        if (movie.isPremium() && !sub.isCanWatchPremium()) {
            return "ERROR: This movie requires PREMIUM subscription!";
        }

        String ads = sub.isHasAds() ? "(Watching with ADS)" : "(No Ads - Enjoy!)";
        return "Now watching: " + movie.getTitle() + " " + ads;
    }

    @Override
    public String getMovieById(int id) {
        return repo.getMovieById(id).toString();
    }
    @Override
    public String searchMovies(String keyword) {
        return "Search results for: " + keyword;
    }
    @Override
    public List<Movies> findByCategory(String category) {
        return repo.findByCategory(category);
    }
}
