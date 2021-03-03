import java.util.*;
/**
 * Abstract class HabitatFood.
 * This class contains all the food species within the simulation.
 *
 * @author Sumaiya Mohbubul
 * @version 27/02/2021
 */
public abstract class HabitatFood extends Actor
{
    Random rand = new Random();
    //counts the amount of each food within the field
    int counter = 0;
    private boolean hasDisease;
    /** 
     * Create a new food species at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public HabitatFood(Field field, Location location)
    {
        super(field, location);
        hasDisease = generateDisease();
    }
    
    /**
     * Randomly generate if the food is diseased.
     * @return If food will be diseased.
     */
    public boolean generateDisease() {
        hasDisease  = Math.random() > 0.65;
        return hasDisease;
    }
    
    /**
     * Returns if the food is diseased.
     * @return if the food has disease.
     */
    protected boolean hasDisease() {
        return hasDisease;
    }
    
    
    /**
     * Make this food species act - that is: make it do
     * whatever it wants/needs to do.
     * @param food A list to receive newly born animals.* Abstract act method for all species.
     */
    abstract public void act(List<HabitatFood> food);
   
    /**
     * find free locations within the field
     * @return all free locations found.
     */
    protected List<Location> findFreeLocations() {
        Field field = getField();
        List<Location> free = new LinkedList<>();
        for(int i = 0; i < field.getDepth(); i++) {
            for(int j = 0; j < field.getWidth(); j++) {
                if(field.getObjectAt(i, j) == null) {
                    free.add(new Location(i, j));
                    
                }
            }
        }
        Collections.shuffle(free);
        return free;
    }

}
