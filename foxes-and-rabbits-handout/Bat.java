import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a bat.
 * Bats age, move, eat insects, and die.
 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Bat extends Animal 
{
    // Characteristics shared by all bats (class variables).

    // The age at which a bat can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a bat can live.
    private static final int MAX_AGE = 200;
    // The likelihood of a bat breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The bat's age.
    private int age;

    /**
     * Create a bat. A bat can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the bat will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bat(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        
    }

    /**
     * The bat's behaviour, which changes whether
     * it is day time or not. During the day it sleeps
     * and during the night it might breed, die of hunger,
     * or die of old age.
     *
     * @param field The field currently occupied.
     * @param newBats A list to return newly born bats.
     */
    public void act(List<Animal> newBats)
    {
        if (isDay() == false) {
            incrementAge();
            if(isAlive()) {
                giveBirth(newBats);
                // Move towards a source of food if found.
                Location newLocation = getField().freeAdjacentLocation(getLocation());
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
    }

    /**
     * Increase the age. This could result in the bat's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Check whether or not this bat is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newBats A list to return newly born bats.
     */
    private void giveBirth(List<Animal> newBats)
    {
        // New bats are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Bat young = new Bat(false, field, loc);
            newBats.add(young);
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
     * A bat can breed if it has reached the breeding age.
     * @return If it can breen or not.
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
            if(adjacentOjbect instanceof Bat) {
                Bat mate = (Bat) adjacentOjbect;
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
