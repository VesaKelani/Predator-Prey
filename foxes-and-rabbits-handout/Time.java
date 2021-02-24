/**
 * Time during the simulation.
 *
 * @Vesa Kelani
 * @24/02/2020
 */
public class Time
{
    //Create the clock starting from 6am
    private static ClockDisplay clock = new ClockDisplay(6,0);

    /**
     * Get the display of the time.
     * @return The time as a string.
     */
    public static String getDisplay()
    {
        return clock.getDisplay();
    }
    
    /**
     * Tick the metaphorical clock.
     */
    public static void timeTick()
    {
        clock.timeTick();
    }
    
    /**
     * Return whether it is daytime or not.
     * @return Whether it is daytime or not.
     */
    public static boolean getIsDay()
    {
        return clock.isDay();
    }
}
