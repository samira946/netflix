package models;

public class Movies {
    private int id;
    private String title;
    private String category;
    private boolean isPremium;


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
}
