import java.util.List;
import java.util.*;

/**
 * A simple model of a Mouse.
 * Mice age, move, eat flowers, breed, and die.
 *
 * @author David J. Barnes, Michael Kölling, Sumaiya Mohbubul and Vesa Kelani
 * @version 27.02.2021
 */
public class Mouse extends Animal
{
    // Characteristics shared by all mice (class variables).

    // The age at which a souse can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a mouse can live.
    private static final int MAX_AGE = 30;
    // The likelihood of a mouse breeding.
    private static final double BREEDING_PROBABILITY = 0.4;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Individual characteristics (instance fields).

    // The mouse's age.
    private int age;


    /**
     * Create a new mouse. A mouse may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the mouse will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mouse(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }

    }

    /**
     * The mice behaviour, which changes whether
     * it is day time or not. During the night it sleeps
     * and during the day it might breed,
     * or die of old age.
     * If a mouse has been infected with a disease, it will lose health.
     * @param newMice A list to return newly born mice.
     */
    public void act(List<Animal> newMice)
    {
        if (isDay()) {
            incrementAge();
            if(isAlive()) {
                giveBirth(newMice);
                // Try to move into a free location.
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
        if (hasDisease()) {
            HPLoss(20);
        }
    }


    /**
     * Increase the age.
     * This could result in the Mouse's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Check whether or not this mouse is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newMice A list to return newly born mice.
     */
    private void giveBirth(List<Animal> newMice)
    {
        // New mice are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Mouse young = new Mouse(false, field, loc);
            newMice.add(young);
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
        if(canBreed() &&  foundMate() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    /**
     * Look for flowers adjacent to the current location.
     * Only the first live flower is eaten.
     * if the flower has a disease, this is passed to the mouse.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object plant = field.getObjectAt(where);
            if(plant instanceof Flower) {
                Flower flower = (Flower) plant;
                if(flower.isAlive()) {
                    flower.setDead();
                    if (flower.hasDisease()) {
                        becomesDiseased();
                    }
                    return where;
                }
            }
        }
        return null;
    }
    
    

    /**
     * A mouse can breed if it has reached the breeding age.
     * @return true if the mouse can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }

    /**
     * Returns if a mouse has found a mate to breed with.
     * @returns true if the sex of two mice are different, false otherwise.
     */
    private boolean foundMate() {

        String sex = getSex();
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object adjacentOjbect = field.getObjectAt(where);
            if(adjacentOjbect instanceof Mouse) {
                Mouse mate = (Mouse) adjacentOjbect;
                if(mate.getSex().equals(sex)) {
                    return false;
                }
                else {
                    return true;
                }
            }
        }
        return true;
    }
}
