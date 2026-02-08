package models;

import controllers.interfaces.INetflixController;
import java.util.List;
import java.util.stream.Collectors;

public class Movies {

    private boolean isPremium;
    private int id;
    private String title;
    private String category;


    public Movies(int id, String title, String category, boolean isPremium) {
        setId(id);
        setTitle(title);
        setCategory(category);
        setPremium(isPremium);
    }

    public Movies() {}

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCategory() { return category; }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean isPremium() { return isPremium; }
    public void setPremium(boolean premium) {
        isPremium = premium;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", isPremium=" + isPremium +
                '}';
    }

    public Movies(String title, String category, boolean isPremium) {
        this.title = title;
        this.category = category;
        this.isPremium = isPremium;
    }
}