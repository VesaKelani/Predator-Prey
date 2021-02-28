import java.util.*;
/**
 * A simple model of Grass.
 * Grass does not move, it simply exists.
 * New Grass can grow.
 *
 * @author Sumaiya Mohbubul
 * @version 27.02.2021
 */
public class Grass extends HabitatFood
{
    // variables shared by all grass.
    private static final int MAX_AGE = 20;
    private static final double GROW_PROBABILITY = 0.04;
    private static final int MAX_GRASS_GROWN = 1;
    //each specific flower age.
    private int age;
    private static final Random rand = Randomizer.getRandom();
    private int totalGrass = 0;

    /**
     * Create a new grass. Grass may be created with age so that it can wither
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the grass will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    /**
     * Increase the age.
     * This could result in the grass' death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    /**
     * Inherited from the abstract habitatFood class.
     */
    public void act(List<HabitatFood> newGrass){}
    
    /** 
     * Generate number of grass to grow.
     * @return The number of grass to grow.
     */
    private int grow() {
        int babyGrass = 0;
        
        if(rand.nextDouble() <= GROW_PROBABILITY) {
            babyGrass = rand.nextInt(MAX_GRASS_GROWN) + 1;
            totalGrass += babyGrass;
        }
        return babyGrass;
    }
    
    /**
     * Grow new grass in random free locations.
     * @param List of new grass to grow.
     */
    private void growNewGrass(List<HabitatFood> newGrass) {
        Field field = getField();
        List<Location> free = new LinkedList<>();
        int babyGrass = grow();
        
        for(int i = 0; i < field.getDepth(); i++) {
            for(int j = 0; j < field.getWidth(); j++) {
                if(field.getObjectAt(i, j) == null) {
                    free.add(new Location(i, j));
                }
            }
        }
        Collections.shuffle(free);

        for (int i = 0; i < babyGrass && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Grass baby= new Grass(false, field, loc);
            newGrass.add(baby);
        }

    }
    
}
