import java.util.*;
/**
 * Write a description of class Flower here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Flower extends HabitatFood
{
    // variables shared by all flowers
    private static final int MAX_AGE = 500;
    private static final Random rand = Randomizer.getRandom();
    
    //each specific flower age
    private int age;
    

    /**
     * Constructor for objects of class Flower
     */
    public Flower(boolean randomAge, Field field, Location location)
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
    
    public void act(List<HabitatFood> newFlowers)
    {
        incrementAge();
        // if(!isAlive()) {
                      
            // // Try to move into a free location.
            // Location newLocation = getField().freeAdjacentLocation(getLocation());
            // if(newLocation != null) {
                // setLocation(newLocation);
            // }
            // else {
                // // Overcrowding.
                // setDead();
            // }
        // }
    }
}
