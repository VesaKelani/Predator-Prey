import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a falcon.
 * Falcons age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Falcon extends Animal implements Predator
{
    // Characteristics shared by all falcons (class variables).

    // The age at which a falcon can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a falcon can live.
    private static final int MAX_AGE = 19;
    // The likelihood of a falcon breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a falcon can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 5;
    private static final int SNAKE_FOOD_VALUE = 5;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The falcon's age.
    private int age;
    // The falcon's food level, which is increased by eating rabbits and snakes.
    private int foodLevel;
    private String sex;

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
            foodLevel = rand.nextInt((RABBIT_FOOD_VALUE) + rand.nextInt(SNAKE_FOOD_VALUE));
        }
        else {
            age = 0;
            foodLevel = RABBIT_FOOD_VALUE + SNAKE_FOOD_VALUE;
        }
        sex = generateSex();
    }
    
    public String generateSex() {
        if(Math.random() > 0.5) {
            sex  = "MALE";}
        else {sex = "FEMALE";}
        return sex;
    }
    
    /**
     * Increase the age.
     * Returns the animals Sex
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * This is what the falcon does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFalcons A list to return newly born falcons.
     */
    public void act(List<Animal> newFalcons)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
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
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
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
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;

                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
            else if (animal instanceof Snake) {
                Snake snake = (Snake) animal;
                if(snake.isAlive()) { 
                    snake.setDead();
                    foodLevel = SNAKE_FOOD_VALUE;
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
        if(canBreed() &&  rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A falcon can breed if it has reached the breeding age.
     */
    public boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
    
    /**
     * returns if an animal has found a mate to breed with, y'know, since we have sex now
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
