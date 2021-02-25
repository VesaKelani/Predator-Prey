import java.util.*;
/**
 * Write a description of class Insect here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Insect extends HabitatFood
{
    // instance variables - replace the example below with your own
    private static final int MAX_AGE = 70;
    private static final Random rand = Randomizer.getRandom();
    private static final double GROW_PROBABILITY = 0.02;
    private static final int MAX_INSECT_GROWN = 1;
    
    
    private int age;
    /**
     * Constructor for objects of class Insect
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
     *increments the age of the flower
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     *how the flower will act during the simulation
     */
    public void act(List<HabitatFood> newInsects)
    {
        incrementAge();
        if(isAlive()) {
            growNewInsects(newInsects);
        } 
    }
    
    /** 
     * generate number of grass to grow
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
     * grow new flowers in random free locations
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
     * get total amount of flowers in the field
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
