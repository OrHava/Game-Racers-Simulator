package game.racers.land;

import game.racers.Racer;
import utilities.EnumContainer;


public class Horse extends Racer implements LandRacer{
    // Fields
    public static final String CLASS_NAME = "Horse";
    public static final double DEFAULT_MAX_SPEED = 50;
    public static final double DEFAULT_ACCELERATION = 3;
    public static final  EnumContainer.Color DEFAULT_COLOR =  EnumContainer.Color.BLACK;


    private EnumContainer.Breed breed= EnumContainer.Breed.THOROUGHBRED ;

    // Constructors
    public Horse() {
        this(CLASS_NAME  , DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR);
    }

    /**
     * @param name
     * @param maxSpeed
     * @param acceleration
     * @param color
     */
    public Horse(String name, double maxSpeed, double acceleration,  EnumContainer.Color color) {
        super( name, maxSpeed, acceleration, color);


    }


    /**
     * @return
     */
    public EnumContainer.Breed getBreed() {
        return breed;
    }

    /**
     * @param breed
     */
    public void setBreed(EnumContainer.Breed breed) {
        this.breed = breed;
    }

    /**
     * @return
     */
    public String describeSpecific(){
        return ", Breed: "+breed.toString();

    }
}
