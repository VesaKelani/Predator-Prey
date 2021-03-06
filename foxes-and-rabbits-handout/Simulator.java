import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * test*******************
 * A simple predator-prey simulator, based on a rectangular field
 * containing lots of animals and plants such as hyenas, bats, 
 * and flowers.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 100;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 100;
    // The probability that a hyena will be created in any given grid position.
    private static final double HYENA_CREATION_PROBABILITY = 0.06;
    // The probability that a mouse will be created in any given grid position.
    private static final double MOUSE_CREATION_PROBABILITY = 0.05;
    // The probability that a snake will be created in any given grid position.
    private static final double SNAKE_CREATION_PROBABILITY = 0.06;
    // The probability that a flower will be created in any given grid position.
    private static final double FLOWER_CREATION_PROBABILITY = 0.02;
    // The probability that a grass will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.05;
    // The probability that a bat will be created in any given grid position.
    private static final double BAT_CREATION_PROBABILITY = 0.03;
    // The probability that a falcon will be created in any given grid position.
    private static final double FALCON_CREATION_PROBABILITY = 0.06;
    // The probability that an insect will be created in any given grid position.
    private static final double INSECT_CREATION_PROBABILITY = 0.02;


    // List of animals in the field.
    private List<Animal> animals;
    //list of habitat food
    private List<HabitatFood> habitatfood;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;


    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        animals = new ArrayList<>();
        habitatfood = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Mouse.class, Color.ORANGE);
        view.setColor(Hyena.class, Color.BLUE);
        view.setColor(Snake.class, Color.RED);
        view.setColor(Flower.class, Color.MAGENTA);
        view.setColor(Grass.class, Color.GREEN);
        view.setColor(Bat.class, Color.BLACK);
        view.setColor(Falcon.class, Color.YELLOW);
        view.setColor(Insect.class, Color.CYAN);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * animal and habitat food.
     */
    public void simulateOneStep()
    {
        step++;
        //Increment time.
        Time.timeTick();
        //Change weather if necessary.
        WeatherState.changeWeather();
        
    
        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();
        List<HabitatFood> newPlants = new ArrayList<>();
        // Let all animals act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            animal.act(newAnimals);
            if(! animal.isAlive()) {
                it.remove();
            }
        }
        //Let all the habitat food act.
        for(Iterator<HabitatFood> it = habitatfood.iterator(); it.hasNext(); ) {
            HabitatFood food = it.next();
            food.act(newPlants);
            if(! food.isAlive()) {
                it.remove();
            }
        }

        // Add the newly born animals and habitat foods to the field.
        animals.addAll(newAnimals);
        habitatfood.addAll(newPlants);

        view.showStatus(step, field);
    }

    /**
     * Return the total steps so far.
     * @return The total steps.
     */
    public int getSteps()
    {
        return step;
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        habitatfood.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Randomly populate the field with living organisms.
     */
    private void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= HYENA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Hyena hyena = new Hyena(true, field, location);
                    animals.add(hyena);
                }
                else if(rand.nextDouble() <= SNAKE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Snake snake = new Snake(true, field, location);
                    animals.add(snake);
                }
                else if(rand.nextDouble() <= FALCON_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Falcon falcon = new Falcon(true, field, location);
                    animals.add(falcon);
                }
                else if(rand.nextDouble() <= FLOWER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Flower flower = new Flower(true, field, location);
                    habitatfood.add(flower);
                }
                else if(rand.nextDouble() <= INSECT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Insect insect = new Insect(true, field, location);
                    habitatfood.add(insect);
                }
                else if(rand.nextDouble() <= BAT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Bat bat = new Bat(true, field, location);
                    animals.add(bat);
                }
                else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Grass grass = new Grass(true, field, location);
                    habitatfood.add(grass);
                }
                else if(rand.nextDouble() <= MOUSE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Mouse mouse = new Mouse(true, field, location);
                    animals.add(mouse);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
