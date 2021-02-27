import java.util.*;
/**
 * Abstract class HabitatFood - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
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
    
    //diseased/poisonous food lmao
    private boolean hasDisease;
    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public HabitatFood(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        hasDisease = generateDisease();
    }
    
    /**
     * randomly generate if the food is diseased
     */
    public boolean generateDisease() {
        if(Math.random() > 0.5) {
            hasDisease  = true;}
        else {hasDisease = false;}
        return hasDisease;
    }
    
    /**
     * returns if the food has disease
     */
    protected boolean hasDisease() {
        return hasDisease;
    }
    
    
    
    protected Location getLocation()
    {
        return location;
    }
    
    abstract public void act(List<HabitatFood> food);
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    protected boolean isAlive()
    {
        return alive;
    }
    
    protected Field getField()
    {
        return field;
    }
    
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
