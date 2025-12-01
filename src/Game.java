import javafx.application.Application;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Game extends Application {
    private GameLoop gameLoop;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(1200, 800);
        GraphicsContext gc;
        gc = canvas.getGraphicsContext2D();
        Pane root = new Pane(canvas);

        // Axe X și Y Chart
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Objects");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Render Time (ms)");

        // Grafic
        LineChart<Number, Number> renderChart = new LineChart<>(xAxis, yAxis);
        renderChart.setTitle("Render Time vs Objects");
        renderChart.setLegendVisible(false);
        renderChart.setCreateSymbols(false); // fără puncte pe linie
        renderChart.setPrefSize(400, 200);
        renderChart.setLayoutX(780); // poziționare în fereastră
        renderChart.setLayoutY(580);

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        renderChart.getData().add(series);

        root.getChildren().add(renderChart);
        Scene scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Zombie Survivors Minimal");
        stage.show();

        // inițializează logica jocului
        gameLoop = new GameLoop();
        gameLoop.init(); // eventual o metodă init pentru player/zombie

        // input
        scene.setOnKeyPressed(e -> gameLoop.onKeyPressed(e));
        scene.setOnKeyReleased(e -> gameLoop.onKeyReleased(e));

        // loop JavaFX
        new AnimationTimer() {
            private int frameIndex = 0; // definește în Game.java deasupra AnimationTimer
            private long lastTime = 0;
            private double fps = 0;
            private double fpsMin = Double.MAX_VALUE;
            private double fpsMax = 0;
            private double alpha = 0.1;     // coeficient medie mobilă

            private double renderPerObjectMin = Double.MAX_VALUE;
            private double renderPerObjectMax = Double.MIN_VALUE;
            private double renderPerObject;  // valoarea curentă

            @Override
            public void handle(long now) {

                // FPS
                if (lastTime > 0) {
                    double delta = (now - lastTime) / 1e9;
                    double instantFps = 1 / delta;
                    fps = fps * (1 - alpha) + instantFps * alpha;

                    fpsMin = Math.min(fpsMin, instantFps);
                    fpsMax = Math.max(fpsMax, instantFps);
                }
                lastTime = now;


                // UPDATE
                long startUpdate = System.nanoTime();
                gameLoop.update();
                double updateMs = (System.nanoTime() - startUpdate) / 1e6;


                // CALCUL OBIECTE
                int totalObjects = 1 + gameLoop.getZombies().size() + gameLoop.getProjectiles().size();


                // RENDER TIMING
                long startRender = System.nanoTime();
                gameLoop.render(gc, fps, fpsMin, fpsMax, updateMs, 0, 0, renderPerObjectMin, renderPerObjectMax);
                double renderMs = (System.nanoTime() - startRender) / 1e6;


                // PER OBJECT
                double renderPerObject = totalObjects > 0 ? renderMs / totalObjects : 0;

                if (renderPerObject < renderPerObjectMin) renderPerObjectMin = renderPerObject;
                if (renderPerObject > renderPerObjectMax) renderPerObjectMax = renderPerObject;


                // GRAFIC
                series.getData().add(new XYChart.Data<>(frameIndex++, renderMs));
                if (series.getData().size() > 50)
                    series.getData().remove(0);


                // 🔥 RENDER FINAL CU VALOARE CORECTĂ 🔥
                gameLoop.render(gc, fps, fpsMin, fpsMax, updateMs,
                        renderMs, renderPerObject, renderPerObjectMin, renderPerObjectMax);
            }
        }.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}