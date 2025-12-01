import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color; // dacă folosești culori în render
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class GameLoop {
    private Player player;
    private List<Zombie> zombies;
    private List<Projectile> projectiles;
    private int lastDirX = 1; // 1 = dreapta, -1 = stanga, 0 = niciuna
    private int lastDirY = 0; // 1 = jos, -1 = sus, 0 = niciuna
    private int score = 0;

    private boolean up, down, left, right, shooting;

    private int wave = 1000;             // valul curent
    private int zombiesPerWave = 1000;   // câți zombii să apară în val


    private int canvasWidth = 1200;
    private int canvasHeight = 800;

    List<Zombie> zombiesToRemove = new ArrayList<>();
    List<Projectile> projectilesToRemove = new ArrayList<>();

    public GameLoop() {
        player = new Player(100, 100);  // pozitie initiala
        zombies = new ArrayList<>();
        projectiles = new ArrayList<>();
        spawnZombies(zombiesPerWave);
        GameUI.setGameObjects(player, zombies);
    }

//    public void start() {
//        spawnZombie(); // generam primul zombie
//        while(true) {
//            update();
//            render(gc);
//            try {
//                Thread.sleep(16); // ~60 FPS
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void update() {
        player.update();                   // mutare player, atac
        for (Zombie z : zombies) {
            z.update(player);              // zombi se misca catre player
        }
        for (Projectile p : projectiles) {
            p.update();
        }
        checkCollisions();

        double speed = 5; // viteza player

        if (up) {
            player.setY(player.getY() - speed);
            lastDirX = 0; lastDirY = -1;
        }
        if (down) {
            player.setY(player.getY() + speed);
            lastDirX = 0; lastDirY = 1;
        }
        if (left) {
            player.setX(player.getX() - speed);
            lastDirX = -1; lastDirY = 0;
        }
        if (right) {
            player.setX(player.getX() + speed);
            lastDirX = 1; lastDirY = 0;
        }

        if (shooting) {
            double projSpeed = 10;
            double vx = lastDirX * projSpeed;
            double vy = lastDirY * projSpeed;

            // dacă nu s-a mișcat încă, default spre dreapta
            if (vx == 0 && vy == 0) vx = projSpeed;

            projectiles.add(new Projectile(player.getX(), player.getY(), vx, vy));
            shooting = false;
        }
        for (Projectile p : projectiles) {
            p.update();
        }
        for (Projectile p : projectiles) {
            for (Zombie z : zombies) {
                if (p.collidesWith(z)) {
                    z.takeDamage(p.getDamage());
                    projectilesToRemove.add(p);

                    if (z.getHealth() <= 0) {
                        zombiesToRemove.add(z);
                        score++; // crește punctajul
                    }
                }
            }
        }
        zombies.removeAll(zombiesToRemove);
        projectiles.removeAll(projectilesToRemove);

        if (zombies.isEmpty()) {
            wave =wave+1000;                 // treci la următorul val
            zombiesPerWave = wave;  // crește numărul de zombii
            spawnZombies(zombiesPerWave);
        }
    }

    private void spawnZombies(int count) {
        for (int i = 0; i < count; i++) {
            double x = Math.random() * 600; // ajustați după canvas width
            double y = Math.random() * 400; // ajustați după canvas height
            zombies.add(new Zombie(x, y));
        }
    }
    public void render(GraphicsContext gc, double fps, double fpsMin, double fpsMax, double updateMs, double renderMs, double renderPerObject,double renderPerObjectMin,double renderPerObjectMax) {

        // fundal
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, 1200, 800);

        // player
        gc.setFill(Color.BLUE);
        gc.fillOval(player.getX()-10, player.getY()-10, 20, 20);

        // zombies
        gc.setFill(Color.RED);
        for(Zombie z : zombies) {
            gc.fillOval(z.getX()-10, z.getY()-10, 20, 20);
        }

        // proiectile
        gc.setFill(Color.BLACK);
        for(Projectile p : projectiles) {
            gc.fillOval(p.getX()-5, p.getY()-5, 10, 10);
        }

        gc.setFill(Color.BLACK);
        int lineHeight = 20;         // înălțimea fiecărui rând
        int uiY = canvasHeight - 10; // start de jos

        String[] stats = {
                "Health: " + player.getHealth(),
                "Zombies: " + zombies.size(),
                "Wave: " + wave,
                "Score: " + score,
                "Projectiles: " + projectiles.size(),
                String.format("FPS: %.1f (min: %.1f, max: %.1f)", fps, fpsMin, fpsMax),
                String.format("Update: %.2f ms", updateMs),
                String.format("Render: %.2f ms", renderMs),
                "Objects: " + (1 + zombies.size() + projectiles.size()),
                String.format("Render/Object: %.2f ms (min: %.2f, max: %.2f)",
                        renderPerObject, renderPerObjectMin, renderPerObjectMax)
        };

// afișăm de jos în sus
        for (int i = 0; i < stats.length; i++) {
            gc.fillText(stats[i], 10, uiY - i * lineHeight);
        }

    }
    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    private void spawnZombie() {
        zombies.add(new Zombie(200, 200));  // Pozitie zombie
    }

    private void checkCollisions() {
        // coliziuni player-zombie
        for (Zombie z : zombies) {
            if (player.collidesWith(z)) {
                player.takeDamage(10);
            }
        }
        // coliziuni proiectile-zombie
        for (Projectile p : projectiles) {
            for (Zombie z : zombies) {
                if (p.collidesWith(z)) {
                    z.takeDamage(p.getDamage());
                }
            }
        }
    }

    public void onKeyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.W) up = true;
        if (code == KeyCode.S) down = true;
        if (code == KeyCode.A) left = true;
        if (code == KeyCode.D) right = true;
        if (code == KeyCode.SPACE) shooting = true;
    }

    public void onKeyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        if (code == KeyCode.W) up = false;
        if (code == KeyCode.S) down = false;
        if (code == KeyCode.A) left = false;
        if (code == KeyCode.D) right = false;
        if (code == KeyCode.SPACE) shooting = false;
    }

    public void init() {
        player = new Player(300, 200);
        zombies.add(new Zombie(50, 50));
        projectiles.clear();
    }
    public Player getPlayer() {
        return player;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }
}