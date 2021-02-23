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
    
    protected void newFood() {
        
    }
}
