package yanni.fluidsimulation.view;

import javafx.scene.shape.Circle;
import yanni.fluidsimulation.model.Particle;

public class ParticleView {

    private Particle particle;
    private Circle circle;
    private Circle smoothingCircle;

    public ParticleView(Particle particle) {
        this.particle = particle;
        this.circle = new Circle(particle.getRadius());
        this.smoothingCircle = new Circle(particle.getSmoothingRadius());
        updateView();
    }

    public Circle getCircle() {
        return circle;
    }


    /**
     * UPDATUJ WIDOK CZĄSTECZEK
     */
    public void updateView() {
        double x = particle.getPosition().getX();
        double y = particle.getPosition().getY();

        circle.setCenterX(x);
        circle.setCenterY(y);

        smoothingCircle.setCenterX(particle.getPosition().getX());
        smoothingCircle.setCenterY(particle.getPosition().getY());
    }
}
