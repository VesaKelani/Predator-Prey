import java.util.List;

/**
 * A class representing shared characteristics of animals.
 *
 * @author David J. Barnes, Michael Kölling, Sumaiya Mohbubul and Vesa Kelani.
 * @version 2016.02.29 (2)
 */
public abstract class Animal extends Actor
{
    //animal's sex
    private String sex;
    //if animal has caught a disease
    private boolean hasDisease;
    //health points
    private int HP;

    /**
     * Create a new animal at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location)
    {
        super(field, location);
        hasDisease = false;
        HP = 100;
        sex = generateSex();
    }
    
    /**
     * Returns that an animal has become diseased.
     * @returns Animal has become diseased.
     */
    protected boolean becomesDiseased() {
        return hasDisease = true;
    }
    
    /**
     * Decrease HP by a given number
     * @param Amount of HP to lose.
     * @returns Decrease HP by a given number.
     */
    protected int HPLoss(int loss) {
        HP -= loss;
        if (HP ==0) {
            setDead();
        }
        return HP;
    }
    
    /**
     * Returns if the animal is diseased.
     * @returns If the animal is diseased.
     */
    protected boolean hasDisease() {
        return hasDisease;
    }

    /**
     * Assigns random sex.
     * @returns Random sex.
     */
    public String generateSex() {
        if(Math.random() > 0.5) {
            sex  = "MALE";
        }
        else {
            sex = "FEMALE";
        }
        return sex;
    }

    /**
     * Returns the animals sex.
     * @returns the animals sex.
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Return whether it is daytime or not.
     * @return Whether it is daytime or not.
     */
    protected boolean isDay()
    {
      return Time.getIsDay();
    }
    
    /**
     * Reduces the food level by half if it is Sunny.
     * @param current food level of animal.
     * @returns The animals food level.
     */   
    protected int halfFoodLevel(int foodLevel)
    {
        if (WeatherState.getCurrentWeather() == "Sun"){
            return foodLevel/2;
        }
        else {
            return foodLevel;
        }
    }
}
