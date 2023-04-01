package game.racers.air;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer;

public class Helicopter extends Racer implements  AerialRacer{
    public static final String CLASS_NAME = "Helicopter";
    public static final double DEFAULT_MAX_SPEED = 400;
    public static final double DEFAULT_ACCELERATION = 50;
    public static final EnumContainer.Color DEFAULT_COLOR = EnumContainer.Color.BLUE;


    public Helicopter() {
        this(CLASS_NAME , DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION,
                DEFAULT_COLOR);
    }

    public Helicopter(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super( name, maxSpeed, acceleration, color);

    }
    public String describeSpecific(){
        return "";

    }

}
