
/**
 * Write a description of class Time here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Time
{
    private ClockDisplay clock;
    private boolean isDay;
    
    /**
     * Constructor for objects of class Time
     */
    public Time()
    {
        clock = new ClockDisplay(7,0);
    }

    public String getDisplay()
    
    {
        return clock.getDisplay();
    }
    
    public void timeTick()
    
    {
        clock.timeTick();
        isDay = clock.isDay();
    }
    
    public boolean getIsDay()
    {
        return isDay;
    }
}
