import java.util.*;
/**
 * Abstract class HabitatFood.
 * This class contains all the food species within the simulation.
 *
 * @author Sumaiya Mohbubul
 * @version 27/02/2021
 */
public abstract class HabitatFood
{
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    
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
        alive = true;
        this.field = field;
        setLocation(location);
        hasDisease = generateDisease();
    }
    
    /**
     * Randomly generate if the food is diseased.
     * @return If food will be diseased.
     */
    public boolean generateDisease() {
        if(Math.random() > 0.5) {
            hasDisease  = true;}
        else {hasDisease = false;}
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
     * Returns the location.
     * @returns the location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Make this food species act - that is: make it do
     * whatever it wants/needs to do.
     * @param food A list to receive newly born animals.* Abstract act method for all species.
     */
    abstract public void act(List<HabitatFood> food);
    
    /**
     * Place the food species at the new location in the given field.
     * @param newLocation The food's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Check whether the food species is alive or not.
     * @return true if the species is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Return the foods's field.
     * @return The food's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Indicate that the food is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * find free locations within the field
     * @return all free locations found.
     */
    protected List<Location> findFreelocations() {
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
