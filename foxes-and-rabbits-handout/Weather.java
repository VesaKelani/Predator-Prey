import java.util.*;

/**
 * Simulates the Weather. Has 3 basic states, snow, rain, and 
 * sun which are randomly chosen. Array List weatherList 
 * can be appended to add any extra number of states.
 *
 * @author Vesa Kelani
 * @version 26/02/2021
 */
public class Weather
{
    //The list of possible weather states.
    private ArrayList<String> weatherList;
    private Random rand = new Random();
    //Set the weather wehn the simulator is first created.
    private String currentWeather = "Rain";
    
    /**
     * Constructs the list of weather states and the basic
     * states added to it.
     */
    public Weather()
    {
        weatherList = new ArrayList<>();
        basicWeatherStates();
    }
    
    /**
     * Randomly generate a weather state.
     * @return The weather state generated as a string.
     */
    public String randomWeather()
    {
        int randInt = rand.nextInt(weatherList.size());
        currentWeather = weatherList.get(randInt);
        return currentWeather;
    }
    
    /**
     * Change the current weather if the time is 6am.
     */
    public void weatherChange()
    {
        if (Time.getHours() == 6 && Time.getMinutes() == 0) {
            currentWeather = randomWeather();
        }
    }
    
    /**
     * Return the current weather.
     * @return The current weather.
     */
    public String getCurrentWeather() {
        return currentWeather;
    }
    
    /**
     * Add a weather state to the list of states.
     */
    public void addWeatherState(String weatherState)
    {
        weatherList.add(weatherState);
    }
    
    /**
     * The original 3 basic weather states added
     * to the list of states.
     */
    public void basicWeatherStates()
    {
        addWeatherState("Sun");
        addWeatherState("Snow");
        addWeatherState("Rain");
    }
}
