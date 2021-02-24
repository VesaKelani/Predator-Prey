import java.util.*;
/**
 * Write a description of class Grass here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Grass extends HabitatFood
{
    private static final int MAX_AGE = 20;
    private static final double GROW_PROBABILITY = 0.04;
    private static final int MAX_GRASS_GROWN = 1;
    private int age;
    private static final Random rand = Randomizer.getRandom();
    private int totalGrass = 0;

    /**
     * Constructor for objects of class Grass
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
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    public void act(List<HabitatFood> newGrass)
    {
     //incrementAge(); 
     //String details = stats.generateCounts(field);
        // if(isAlive() && totalGrass < 50) {
            // growNewGrass(newGrass);
        // }   
    }
    
    /** 
     * generate number of grass to grow
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
     * grow new grass in random free locations
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
