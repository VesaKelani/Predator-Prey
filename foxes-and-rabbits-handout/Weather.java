import java.util.*;

/**
 * Simulates the Weather. Has 3 states, snow, rain, and 
 * sun which are randomly chosen. Array List weatherList 
 * can be appended to add any extra number of states.
 *
 * @Vesa Kelani
 * @26/02/2021
 */
public class Weather
{
    // instance variables - replace the example below with your own
    private ArrayList<String> weatherList;
    private Random rand = new Random();
    private String currentWeather = "Rain";
    
    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        weatherList = new ArrayList<>();
        basicWeatherStates();
    }
    
    public String randomWeather()
    {
        int randInt = rand.nextInt(weatherList.size());
        currentWeather = weatherList.get(randInt);
        return currentWeather;
    }
    
    public void weatherChange()
    {
        if (Time.getHours() == 7 && Time.getMinutes() == 0) {
            currentWeather = randomWeather();
        }
    }
    
    public String getCurrentWeather() {
        return currentWeather;
    }
    
    public void addWeatherState(String weatherState)
    {
        weatherList.add(weatherState);
    }
    
    public void basicWeatherStates()
    {
        addWeatherState("Sun");
        addWeatherState("Snow");
        addWeatherState("Rain");
    }
    
}
