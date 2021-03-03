import java.util.*;
/**
 * * A simple model of a flower.
 * * Flowers age, grow, and multiply.
 *
 * @author Sumaiya Mohbubul
 * @version 27.02.2021
 */
public class Flower extends HabitatFood
{
    // variables shared by all flowers
    private static final int MAX_AGE = 50;
    private static final Random rand = Randomizer.getRandom();
    private static final double GROW_PROBABILITY = 0.02;
    private static final double DEATH_PROBABILITY = 0.018;
    private static final int MAX_FLOWER_GROWN = 1;
    //each specific flower age
    private int age;
    /**
     * Create a new rabbit. An insect may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
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
     * Increase the age.
     * This could result in the flower's death.
     */
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     *The flower's behaviour.
     ** @param newFlowers A list to return newly born flowers.
     */
    public void act(List<HabitatFood> newFlowers) {
        incrementAge(); 
        if(isAlive() && currentWeather() != "Rain") {
            growNewFlowers(newFlowers);
        } 
        else {
            if (rand.nextDouble() <= DEATH_PROBABILITY) {
                setDead();
            }
        }
    }

    /** 
     * Generate number of flowers to grow.
     * @return The number of flowers to grow.
     */
    private int grow() {
        int baby = 0;
        int flowerTotal = getFlowerTotal();
        if(flowerTotal <500 && rand.nextDouble() <= GROW_PROBABILITY) {
            baby = rand.nextInt(MAX_FLOWER_GROWN) + 1;
        }
        return baby;
    }

    /**
     * Grow new flowers in random free locations.
     * @param List of new flowers to grow.
     */
    private void growNewFlowers(List<HabitatFood> newFlowers) {
        int babyFlower = grow();
        Field field = getField();
        List<Location> free = findFreelocations();
        for (int i = 0; i < babyFlower && free.size() > 0; i++) {
            Location loc = free.remove(0);
            Flower baby= new Flower(false, field, loc);
            newFlowers.add(baby);
            counter += 1;
        }

    }

    /**
     * The total amount of flowers in the field.
     * @return Total amound of flowers.
     */ 
    public int getFlowerTotal() {
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
