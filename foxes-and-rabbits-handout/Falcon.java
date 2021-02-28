import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a falcon.
 * Falcons age, move, eat mouses or snakes, and die.
 *
 * @author David J. Barnes, Michael Kölling, Sumaiya Mohbubul and Vesa Kelani.
 * @version 27.02.2021
 */
public class Falcon extends Animal
{
    // Characteristics shared by all falcons (class variables).
    // The age at which a falcon can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a falcon can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a falcon breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single mouse. In effect, this is the
    // number of steps a falcon can go before it has to eat again.
    private static final int MOUSE_FOOD_VALUE = 20;
    private static final int SNAKE_FOOD_VALUE = 20;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The falcon's age.
    private int age;
    // The falcon's food level, which is increased by eating mouses and snakes.
    private int foodLevel;
    /**
     * Create a falcon. A falcon can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the falcon will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Falcon(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt((MOUSE_FOOD_VALUE) + rand.nextInt(SNAKE_FOOD_VALUE));
        }
        else {
            age = 0;
            foodLevel = MOUSE_FOOD_VALUE + SNAKE_FOOD_VALUE;
        }
    }

    /**
     * The falcon's behaviour, which changes whether
     * it is day time or not. During the night it sleeps
     * and during the day it might breed, die of hunger,
     * or die of old age.
     * If a falcon has been infected with a disease, it will lose health.
     * @param newFalcons A list to return newly born falcons.
     */
    public void act(List<Animal> newFalcons)
    {
        if (isDay()) {
            incrementAge();
            incrementHunger();
            if(isAlive() && currentWeather() != "Snow") {
                giveBirth(newFalcons);
                // Move towards a source of food if found.
                Location newLocation = findFood();
                if(newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getField().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
        else {
            //sleep
        }
        foodLevel = halfFoodLevel(foodLevel);
        if (hasDisease()) {
            HPLoss(20);
        }
    }

    /**
     * Increase the age. This could result in the falcon's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this falcon more hungry. This could result in the falcon's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for mouses and snakes adjacent to the current location.
     * Only the first live mouse or snake is eaten.
     * * if the mouse or snake has a disease, this is passed to the falcon.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Mouse) {
                Mouse mouse = (Mouse) animal;

                if(mouse.isAlive()) {
                    mouse.setDead();
                    foodLevel = MOUSE_FOOD_VALUE;
                    if (mouse.hasDisease()) {
                        becomesDiseased();
                    }
                    return where;
                }
            }
            else if (animal instanceof Snake) {
                Snake snake = (Snake) animal;
                if(snake.isAlive()) {
                    snake.setDead();
                    foodLevel = SNAKE_FOOD_VALUE;
                    if (snake.hasDisease()) {
                        becomesDiseased();
                    }
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this falcon is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFalcons A list to return newly born falcons.
     */
    private void giveBirth(List<Animal> newFalcons)
    {
        // New falcons are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Falcon young = new Falcon(false, field, loc);
            newFalcons.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && foundMate() &&  rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A falcon can breed if it has reached the breeding age.
     * @return true if the falcon can breed, false otherwise.
     */
    public boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * Returns if a falcon has found a mate to breed with.
     * @returns true if the sex of two falcons are different, false otherwise.
     */
    private boolean foundMate() {

        String sex = getSex();
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object adjacentOjbect = field.getObjectAt(where);
            if(adjacentOjbect instanceof Falcon) {
                Falcon mate = (Falcon) adjacentOjbect;
                if(mate.getSex().equals(sex)) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }
}
