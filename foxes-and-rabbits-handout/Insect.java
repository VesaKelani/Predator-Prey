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
    private static final int MAX_AGE = 50;
    private static final Random rand = Randomizer.getRandom();
    
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
}
