package models;

public class User {
    private int id;
    private String name;
    private String surname;
    private boolean gender;

    private String login;
    private String password;
    private String subscriptionType;

    public User() {

    }

    public User(String name, String surname, boolean gender, String login, String password) {
        setName(name);
        setSurname(surname);
        setGender(gender);
        setLogin(login);
        setPassword(password);
        this.subscriptionType = "None";
    }

    public User(int id, String name, String surname, boolean gender, String login, String password, String subscriptionType) {
        this(name, surname, gender, login, password);
        setId(id);
        setSubscriptionType(subscriptionType);
    }

    public User(int id, String name, String surname, boolean gender) {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getSubscriptionType() { return subscriptionType; }

    public void setSubscriptionType(String subscriptionType) { this.subscriptionType = subscriptionType; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", subscription='" + subscriptionType + '\'' +
                ", gender=" + (gender ? "Male" : "Female") +
                '}';
    }
}
