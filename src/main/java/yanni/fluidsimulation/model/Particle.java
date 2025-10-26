package yanni.fluidsimulation.model;

/**
 * KLASA CZĄSTECZKI
 */
public class Particle {

    private Vector2 position;
    private Vector2 velocity;
    private double mass;
    private double smoothingRadius;
    private double radius;
    private double density;
    private double pressure;
    private Vector2 force;
    private static final double PIXELS_PER_METER = 50;
    private static final double GRAVITY_MS2 = 9.81;
    private static final double gravity = GRAVITY_MS2 * PIXELS_PER_METER;


    public Particle(Vector2 position, Vector2 velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.smoothingRadius = 12 * radius;
        this.mass = Math.PI * radius * radius;
        this.force = new Vector2(0, 0);
        this.density = 1.0;
    }

    public double getPressure() {
        return pressure;
    }

    public void setForce(Vector2 force) {
        this.force = force;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getMass() {
        return mass;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public Vector2 getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    public double getSmoothingRadius() {
        return smoothingRadius;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * UPDATE PRĘDKOŚCI I POŁOŻENIA CZĄSTECZKI
     */
    public void update(double deltaTime) {
        double accelerationX = force.getX() / getDensity();
        double accelerationY = force.getY() / getDensity();
        accelerationY += gravity;
        velocity.setX(velocity.getX() + accelerationX * deltaTime);
        velocity.setY(velocity.getY() + accelerationY * deltaTime);
        position.setY(position.getY() + velocity.getY() * deltaTime);
        position.setX(position.getX() + velocity.getX() * deltaTime);
        force.setX(0);
        force.setY(0);
    }

    /**
     * SPRAWDZ KOLIZJE Z PROSTOKĄTEM
     */
    public void checkCollision(Vector2 boundsPosition, Vector2 boundsSize, double collisionDamping) {

        //LEFT
        if (position.getX() - radius < boundsPosition.getX()) {
            position.setX(boundsPosition.getX() + radius);
            velocity.setX(velocity.getX() * -collisionDamping);
        }
        //RIGHT
        if (position.getX() + radius > boundsPosition.getX() + boundsSize.getX()) {
            position.setX(boundsPosition.getX() + boundsSize.getX() - radius);
            velocity.setX(velocity.getX() * -collisionDamping);
        }
        //TOP
        if (position.getY() - radius < boundsPosition.getY()) {
            position.setY(boundsPosition.getY() + radius);
            velocity.setY(velocity.getY() * -collisionDamping);
        }
        //BOTTOM
        if (position.getY() + radius > boundsPosition.getY() + boundsSize.getY()) {
            position.setY(boundsPosition.getY() + boundsSize.getY() - radius);
            velocity.setY(velocity.getY() * -collisionDamping);
        }
    }
}
