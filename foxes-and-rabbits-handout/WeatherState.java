
/**
 * Is the current weather state.
 *
 * @Vesa Kelani
 * @20/02/2021
 */
public class WeatherState
{
    private static Weather weatherState = new Weather();
    private static String currentWeather = "Sun";
    
    public static String getRandWeatherState()
    {
        currentWeather = weatherState.randomWeather();
        return currentWeather;
    }
    
    public static String getCurrentWeather()
    {
        return currentWeather;
    }
    
    public static void weatherChange()
    {
        if (Time.getHours() == 7 && Time.getMinutes() == 0) {
            currentWeather = getRandWeatherState();
        }
    }
}
