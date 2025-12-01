import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        GameLoop game = new GameLoop();

        // setăm referințele pentru UI
        GameUI.setGameObjects(game.getPlayer(), game.getZombies());

        // pornește UI într-un thread separat
        new Thread(() -> Application.launch(GameUI.class)).start();

        // start game loop
//        game.start();
    }
}