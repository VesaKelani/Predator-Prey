
/**
 * Is the current weather state.
 *
 * @Vesa Kelani
 * @20/02/2021
 */
public class WeatherState
{
    private static Weather weatherState = new Weather();
    
    public static void changeWeather()
    {
        weatherState.weatherChange();
    }
    
    public static String getCurrentWeather()
    {
        return weatherState.getCurrentWeather();

    }
    

}
