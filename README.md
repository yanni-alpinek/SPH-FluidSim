# Fluid Simulation (SPH)

Physics simulation of fluid behavior using Smoothed Particle Hydrodynamics. Watch 1500+ particles interact with gravity, pressure, and viscosity in real time.


![Fluid Simulation](FluidSimulationGIF.gif)
---

## What It Does

Simulates water or liquid using particles. Each particle affects others nearby - they push away when too close (pressure) and pull together when moving (viscosity).

---

## Quick Start

### Run It
- Open project in IntelliJ
- Run `Main.java`
- Click **"Start"** button
- Watch the particles fall and interact

### Controls
- Click **Start** to begin simulation
- Particles bounce off the green container
- No pause yet (coming soon!)

---

## How It Works

Particle System → SPH Solver → Physics → JavaFX Canvas


**Each frame:**
1. Calculate density for each particle (how crowded it is)
2. Convert density to pressure (push force)
3. Calculate pressure forces between nearby particles
4. Add gravity and viscosity
5. Update positions and velocities
6. Draw everything

---

## Tech Details

**Language:** Java  
**Graphics:** JavaFX  
**Physics:** Smoothed Particle Hydrodynamics (SPH)  
**Particles:** 1500 (configurable)

---

## Features

- 1500+ particles running smoothly
- Gravity simulation (9.81 m/s²)
- Pressure forces (particles push each other)
- Viscosity (sticky fluid behavior)
- Wall collisions with bounce
- Grid spawn pattern
- Real-time rendering

---

## Physics Explained

### SPH Method
Each particle looks at neighbors within **smoothing radius**. Closer particles have stronger effect.

### Kernels Used
- **Poly6 Kernel** - for density calculation
- **Spiky Kernel** - for pressure gradient

### Forces Applied
- **Pressure** - pushes particles apart when too close
- **Viscosity** - smooths velocity differences
- **Gravity** - pulls everything down

---

## Code Structure

model/ <br>
├── Particle.java - Single particle (position, velocity, mass)<br>
├── SPHSolver.java - Physics calculations (density, pressure, forces)<br>
└── Vector2.java - 2D vector math<br>
view/<br>
└── ParticleView.java - Graphics (draw particles)

Main.java - App entry, animation loop, setup


---

## Settings You Can Change

In `Main.java`:
- **Number of particles:** `createParticles(1500)`
- **Container size:** `new Vector2(950, 700)`
- **Bounce strength:** `checkCollision(..., 0.4)` (0-1)

In `SPHSolver.java`:
- **Target density:** `0.4` (higher = more compact)
- **Pressure strength:** `5000` (higher = stronger push)
- **Viscosity:** `0.04` (higher = thicker fluid)

---

## Known Issues

- No pause button yet
- Can't add particles during simulation
- Slows down with 2000+ particles
- Particles sometimes stick to walls
- No color gradient based on velocity

---

## What I Learned

- SPH algorithm for fluid physics
- Optimizing loops for thousands of objects
- JavaFX animation timers
- Kernel functions (Poly6, Spiky)
- Collision detection and response
- Delta time for smooth animation

---

## Future Ideas

- Add mouse interaction (drag particles)
- Color particles by velocity or pressure
- Different fluids (water, oil, honey)
- Obstacles you can place
- Save/load simulations

>Built to learn physics simulation and particle systems.


---


* **Multiplayer Combat Game** - Real-time multiplayer game built with Spring Boot and JavaFX
* **Mass-Spring Simulation** - A simple physics simulation of a spring-mass system with a bouncing ball
* **Real-Time Chat** - A real-time chat application using WebSockets for instant messaging
