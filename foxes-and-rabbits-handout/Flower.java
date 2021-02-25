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
    private static final int MAX_AGE = 50;
    private static final Random rand = Randomizer.getRandom();
    private static final double GROW_PROBABILITY = 0.02;
    private static final int MAX_GRASS_GROWN = 1;
    //each specific flower age
    private int age;
    //private int totalFlowers = 0;
    int counter = 0;
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
    
    public void act(List<HabitatFood> newFlowers) {
        incrementAge(); 
        
        if(isAlive()) {
            growNewFlowers(newFlowers);
        } 
    }
    
    /** 
     * generate number of grass to grow
     */
    private int grow() {
        int babyFlower = 0;
        int flowerTotal = getFlowerTotal();
        if(flowerTotal <500 && rand.nextDouble() <= GROW_PROBABILITY) {
            babyFlower = rand.nextInt(MAX_GRASS_GROWN) + 1;
            //totalFlowers += babyFlower;
        }
        return babyFlower;
    }
    
    /**
     * grow new grass in random free locations
     */
    private void growNewFlowers(List<HabitatFood> newFlowers) {
        Field field = getField();
        List<Location> free = new LinkedList<>();
        int babyFlower = grow();
        
        for(int i = 0; i < field.getDepth(); i++) {
            for(int j = 0; j < field.getWidth(); j++) {
                if(field.getObjectAt(i, j) == null) {
                    free.add(new Location(i, j));
                    
                }
            }
        }
        Collections.shuffle(free);

        for (int i = 0; i < babyFlower && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Flower baby= new Flower(false, field, loc);
            newFlowers.add(baby);
            counter += 1;
        }

    }
    
    private int getFlowerTotal() {
        Field field = getField();
        int total = 0;
        for(int i = 0; i < field.getDepth(); i++) {
            for(int j = 0; j < field.getWidth(); j++) {
                if(field.getObjectAt(i, j) instanceof Flower) {
                    
                    total += 1;
                }
            }
        }
        return total;
    }
}
