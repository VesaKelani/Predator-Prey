import java.util.*;
/**
 * A simple model of a snake.
 * Snakes age, grow, eat mice and multiply.
 *
 * @author Vesa Kelani and Sumaiya Mohbubul
 * @version 27.02.2021
 */
public class Snake extends Animal
{
    // Characteristics shared by all snakes (class variables).
    // The age at which a snake can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a snake can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a snake breeding.
    private static final double BREEDING_PROBABILITY = 0.2;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 10;
    // The food value of a single mouse. In effect, this is the
    // number of steps a snake can go before it has to eat again.
    private static final int MOUSE_FOOD_VALUE = 15;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The snake's age.
    private int age;
    // The snake's food level, which is increased by eating Mouses.
    private int foodLevel;
    /**
     * Create a new snake. A snake may be created as a new born (age zero
     * and not hungy) or with a random age and food level.
     *
     * @param randomAge If true, the snake will have a random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Snake(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(MOUSE_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = MOUSE_FOOD_VALUE;
        }
    }

    /**
     * The snake's behaviour, which changes whether
     * it is day time or not. During the night it sleeps
     * and during the day it might breed, die of hunger,
     * or die of old age.
     * If a Mouse has been infected with a disease, it will lose health.
     * @param newSnakes A list to return newly born snakes.
     */
    public void act(List<Animal> newSnakes)
    {
        if (isDay()) {
            incrementAge();
            incrementHunger();
            if(isAlive()) {
                giveBirth(newSnakes);
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
     * Increase the age. This could result in the snake's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this snake more hungry. This could result in the snake's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for mice adjacent to the current location.
     * Only the first live mouse is eaten.
     * If the mouse has a disease, this is passed to the snake.
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
        }
        return null;
    }

    /**
     * Check whether or not this snake is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSnakes A list to return newly born snakes.
     */
    private void giveBirth(List<Animal> newSnakes)
    {
        // New snakes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Snake young = new Snake(false, field, loc);
            newSnakes.add(young);
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
        if(canBreed() && foundMate() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A snake can breed if it has reached the breeding age.
     * * @return true if the snake can breed, false otherwise.
     */
    public boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * Returns if a snake has found a mate to breed with.
     * @returns true if the sex of two snakes are different, false otherwise.
     */
    private boolean foundMate() {

        String sex = getSex();
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object adjacentOjbect = field.getObjectAt(where);
            if(adjacentOjbect instanceof Snake) {
                Snake mate = (Snake) adjacentOjbect;
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
