package game.racers.air;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer;
/** @author Or Hava 208418483
 */
public class Helicopter extends Racer implements  AerialRacer{
    private static final String CLASS_NAME = "Helicopter";
    private static final double DEFAULT_MAX_SPEED = 400;
    private static final double DEFAULT_ACCELERATION = 50;
    private static final EnumContainer.Color DEFAULT_COLOR = EnumContainer.Color.BLUE;


    public Helicopter() {
        this(CLASS_NAME , DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION,
                DEFAULT_COLOR);
    }

    /**
     * @param name
     * @param maxSpeed
     * @param acceleration
     * @param color
     */
    public Helicopter(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        super( name, maxSpeed, acceleration, color);

    }

    /**
     * @return
     */
    public String describeSpecific(){
        return "";

    }


    @Override
    public String className() {
        return CLASS_NAME;
    }


}
