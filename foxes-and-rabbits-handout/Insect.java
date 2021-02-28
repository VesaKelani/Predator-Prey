import java.util.*;
/**
 * * A simple model of an Insect.
 * * Insects age, grow, and multiply.
 *
 * @author Sumaiya Mohbubul
 * @version 27.02.2021
 */
public class Insect extends HabitatFood
{
    // variables shared by all insects.
    private static final int MAX_AGE = 70;
    private static final Random rand = Randomizer.getRandom();
    private static final double GROW_PROBABILITY = 0.02;
    private static final int MAX_INSECT_GROWN = 1;
    //each specific insect age.
    private int age;
    /**
     * Create a new Insect. An insect may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbiinsectt will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Insect(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    /**
     * Increase the age.
     * This could result in the insect's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     *The insects's behaviour.
     ** @param newInsects A list to return newly born flowers.
     */
    public void act(List<HabitatFood> newInsects)
    {
        incrementAge();
        if(isAlive()) {
            growNewInsects(newInsects);
        } 
    }
    
    /** 
     * Generate number of insects to grow.
     * @return The number of insects to grow.
     */
    private int grow() {
        int babyFood = 0;
        int InsectTotal = getInsectTotal();
        if(InsectTotal <500 && rand.nextDouble() <= GROW_PROBABILITY) {
            babyFood = rand.nextInt(MAX_INSECT_GROWN) + 1;
        }
        return babyFood;
    }
    
    /**
     * Grow new insects in random free locations.
     * @param List of new insects to grow.
     */
    private void growNewInsects(List<HabitatFood> newInsects) {
        int babies = grow();
        Field field = getField();
        List<Location> free = findFreelocations();
        for (int i = 0; i < babies && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Insect baby= new Insect(false, field, loc);
            newInsects.add(baby);
            counter += 1;
        }

    }
    
    /**
     * The total amount of insects in the field.
     * @return Total amound of insects.
     */ 
    public int getInsectTotal() {
        Field field = getField();
        int total = 0;
        for(int i = 0; i < field.getDepth(); i++) {
            for(int j = 0; j < field.getWidth(); j++) {
                if(field.getObjectAt(i, j) instanceof Insect) {
                    total += 1;
                }
            }
        }
        return total;
    }

}
