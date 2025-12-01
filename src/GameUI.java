import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class GameUI extends Application {

    private static Player player;
    private static List<Zombie> zombies;

    private Text playerHealthText;
    private Text zombiesCountText;

    public static void setGameObjects(Player p, List<Zombie> z) {
        player = p;
        zombies = z;
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        playerHealthText = new Text(10, 20, "Player Health: " + player.getHealth());
        zombiesCountText = new Text(10, 40, "Zombies: " + zombies.size());

        root.getChildren().addAll(playerHealthText, zombiesCountText);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Zombie Survivors Prototype");
        primaryStage.setScene(scene);
        primaryStage.show();

        // loop simplificat pentru UI (fără animation thread complex)
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100); // 10 updates/sec
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateUI();
            }
        }).start();
    }

    private void updateUI() {
        // actualizare UI pe thread-ul JavaFX
        javafx.application.Platform.runLater(() -> {
            playerHealthText.setText("Player Health: " + player.getHealth());
            zombiesCountText.setText("Zombies: " + zombies.size());
        });
    }
}