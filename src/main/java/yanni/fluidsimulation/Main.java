package yanni.fluidsimulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import yanni.fluidsimulation.model.Particle;
import yanni.fluidsimulation.model.SPHSolver;
import yanni.fluidsimulation.model.Vector2;
import yanni.fluidsimulation.view.ParticleView;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("FluidSimulation");
        stage.setMaximized(true);

        Pane root = new Pane();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        //ADD CSS STYLESHEET
        URL cssURL = getClass().getResource("/yanni/fluidsimulation/style.css");
        if (cssURL != null) {
            scene.getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.out.println("style.css not found");
        }

        //ADD BOUNDS
        Vector2 boundsSize = new Vector2(950, 700);
        root.getChildren().add(createBounds(boundsSize));

        //ADD PARTICLE
        ArrayList<ParticleView> pvs = new ArrayList<>();
        //List<Particle> particles = createParticlesWithoutGrid(1500,new Vector2(950,700));
        List<Particle> particles = createParticles(1500);
        for (Particle p1 : particles) {
            ParticleView particleView = new ParticleView(p1);
            pvs.add(particleView);
            root.getChildren().add(particleView.getCircle());
            particleView.getCircle().getStyleClass().add("particle");
        }

        //StartSimulationButton
        Button startButton = new Button("Start");
        startButton.setId("startButton");
        root.getChildren().add(startButton);

        SPHSolver sphSolver = new SPHSolver();

        //TIMER AFTER START -> deltaTime
        startButton.setOnAction(e -> {
            AnimationTimer timer = new AnimationTimer() {
                long lastTime = 0;

                @Override
                public void handle(long now) {
                    if  (lastTime == 0) {
                        lastTime = now;
                        return;
                    }
                    double deltaTime = (now - lastTime) / 1_000_000_000.0;
                    lastTime = now;
                    sphSolver.calculateDensityAndPressure(particles);
                    sphSolver.calculatePressureForce(particles);

                    for (int i = 0; i < particles.size(); i++) {
                        Particle particle = particles.get(i);
                        ParticleView particleView = pvs.get(i);
                        particle.update(deltaTime);
                        particle.checkCollision(new Vector2(421, 200), boundsSize, 0.4);
                        particleView.updateView();
                    }
                }
            };
            timer.start();
        });

        startButton.layoutXProperty().bind(
                scene.widthProperty().divide(2).subtract(startButton.widthProperty().divide(2))
        );
        startButton.setLayoutY(40);


    }

    /**
     *Tworzy granice
     */
    private Rectangle createBounds(Vector2 boundsSize) {
        Rectangle bounds = new Rectangle();
        bounds.setHeight(boundsSize.getY());
        bounds.setWidth(boundsSize.getX());
        bounds.setId("bounds");
        bounds.setX(421);
        bounds.setY(200);
        return bounds;
    }

    /**
     * STWORZ I USTAW RANDOMOWO CZĄSTECZKI
     */
    private ArrayList<Particle> createParticlesWithoutGrid(int numberOfParticles, Vector2 boundsSize) {

        ArrayList<Particle> particleList = new ArrayList<>();

        for (int i = 0; i < numberOfParticles; i++) {
            double randomX = boundsSize.getX() * Math.random();
            double randomY = (boundsSize.getY() - 4) * Math.random();
            Particle particle = new Particle(new Vector2(421 + randomX,200 + randomY),new Vector2(0,0),12);
            particleList.add(particle);
        }
        return particleList;
    }

    /**
     *Stworz czasteczki w grid
     */
    private ArrayList<Particle> createParticles(int numberOfParticles) {
        int cols =  (int) Math.ceil(Math.sqrt(numberOfParticles));
        int rows =  (int) Math.ceil(Math.sqrt(numberOfParticles));

        double spacingX = 15;
        double spacingY = 15;

        ArrayList<Particle> particleList = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Particle particle = new Particle(new Vector2(500 + i * spacingY,270 + j * spacingX),new Vector2(0,0),7);
                particleList.add(particle);
            }
        }
        return particleList;
    }
}
