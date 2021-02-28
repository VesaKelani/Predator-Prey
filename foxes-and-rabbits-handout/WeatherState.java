/**
 * Is the current weather state.
 *
 * @author Vesa Kelani
 * @version 20/02/2021
 */
public class WeatherState
{
    //Create the weather.
    private static Weather weatherState = new Weather();
    
    /**
     * Change the weather.
     */
    public static void changeWeather()
    {
        weatherState.weatherChange();
    }
    
    /**
     * Return the current weather.
     * @return The current weather as a string.
     */
    public static String getCurrentWeather()
    {
        return weatherState.getCurrentWeather();
    }
}
