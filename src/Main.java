
import controllers.UserController;
import controllers.interfaces.IUserController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.UserRepository;
import repositories.MovieRepository;
import repositories.interfaces.IMovieRepository;
import repositories.interfaces.IUserRepository;

public class Main {

    public static void main(String[] args) {
        IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "somedb");
        IUserRepository repo = new UserRepository(db);
        IUserController controller = new UserController(repo);
        IMovieRepository movieRepo = new MovieRepository(db);
        INetflixController movieController = new MovieController(movieRepo);

        MyApplication app = new MyApplication(controller, movieController);

        app.start();

        db.close();
    }
}
