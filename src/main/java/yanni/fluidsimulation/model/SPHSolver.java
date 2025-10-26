package yanni.fluidsimulation.model;

import java.util.List;

public class SPHSolver {

    public double targetDensity = 0.4;
    public double pressureMultiplier = 5000;

    /**
     * OBLICZ SIŁY CIŚNIENIA I GĘSTOŚCI
     */
    public void calculatePressureForce(List<Particle> particles) {
        double viscosityStrength = 0.04;
        for (int i  = 0; i < particles.size(); i++) {

            Particle particle = particles.get(i);
            //LEPKOŚĆ
            double viscosityX = 0.0;
            double viscosityY = 0.0;

            double forceX = 0.0;
            double forceY = 0.0;

            for (int j = 0; j < particles.size(); j++) {
                if (i == j) {
                    continue;
                }
                Particle other = particles.get(j);

                double dx = particle.getPosition().getX() - other.getPosition().getX();
                double dy = particle.getPosition().getY() - other.getPosition().getY();
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < 0.3 * particle.getRadius()) {
                    dist = 0.3 * particle.getRadius();
                }

                if (dist >= particle.getSmoothingRadius()) {
                    continue;
                }

                double avgPressure = (particle.getPressure()+ other.getPressure()) / 2;
                double shared = other.getMass() * avgPressure / other.getDensity();

                //gradient kernel
                Vector2 gradient = spikyKernel(particle,dist,dx,dy);

                forceX += shared * gradient.getX();
                forceY += shared * gradient.getY();

                double velocityDiffX = other.getVelocity().getX() - particle.getVelocity().getX();
                double velocityDiffY = other.getVelocity().getY() - particle.getVelocity().getY();

                double h = particle.getSmoothingRadius();
                double influence = (h - dist) / h;

                viscosityX += velocityDiffX * influence;
                viscosityY += velocityDiffY * influence;
            }
            double finalForceX = -(particle.getMass()) * forceX;
            double finalForceY = -(particle.getMass()) * forceY;

            finalForceX += viscosityX * viscosityStrength;
            finalForceY += viscosityY * viscosityStrength;

            particle.setForce(new Vector2(finalForceX, finalForceY));
        }
    }

    /**
     * OBLICZ CIŚNIENIE I GĘSTOŚĆ
     */
    public void calculateDensityAndPressure(List<Particle> particles) {
        for (int i  = 0; i < particles.size(); i++) {
            Particle particle = particles.get(i);
            double density = 0.0;

            for (int j = 0; j < particles.size(); j++) {

                Particle other = particles.get(j);
                double dx = particle.getPosition().getX() - other.getPosition().getX();
                double dy = particle.getPosition().getY() - other.getPosition().getY();
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < 0.3 * particle.getRadius()) {
                    dist = 0.3 * particle.getRadius();
                }

                if (dist >= particle.getSmoothingRadius()) {
                    continue;
                }
                //polykernel
                density += other.getMass() * poly6Kernel(dist, particle.getSmoothingRadius());
            }
            particle.setDensity(density);
            particle.setPressure(convertDensityToPressure(density));
        }
    }


    /**
     * POMOC FUNKCJA JĄDRA KERNELA
     */
    public double poly6Kernel(double distance, double smoothingRadius) {
        if (distance >= smoothingRadius) {
            return  0.0;
        }
        double coefficient = 4.0 / (Math.PI * Math.pow(smoothingRadius, 8));
        double term = smoothingRadius * smoothingRadius -  distance *  distance;
        return coefficient * term * term * term;
    }

    /**
     * POMOCNICZA FUNKCJA GRADIENTU KERNELA
     */
    public Vector2 spikyKernel(Particle particle, double distance, double dx, double dy) {
    if (distance >= particle.getSmoothingRadius()) {
        return new Vector2(0,0);
    }

    double safeDist = Math.max(distance, 0.001);

    double h = particle.getSmoothingRadius();
    double diff = h - distance;
    double coefficient = -10.0 / (Math.PI * Math.pow(h, 5));
    double factor = coefficient * diff * diff / safeDist;

    double gradientX = factor * dx;
    double gradientY = factor * dy;

    return new Vector2(gradientX,gradientY);
}

    /**
     * KONWERTUJ GĘSTOŚĆ NA CIŚNIENIE
     */
    public double convertDensityToPressure(double density) {
        double densityError = density - targetDensity;
        double pressure = densityError * pressureMultiplier;
        return Math.max(0, Math.min(pressure, 50000));
    }


}


