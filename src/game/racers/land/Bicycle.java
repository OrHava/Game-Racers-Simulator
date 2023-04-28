package game.racers.land;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer;

/** @author Or Hava 208418483
 */
public class Bicycle extends  Racer implements LandRacer {
    // Fields
    private static final String CLASS_NAME = "Bicycle";
    private static final int DEFAULT_WHEELS = 2;
    private static final double DEFAULT_MAX_SPEED = 270;
    private static final double DEFAULT_ACCELERATION = 10;
    private static final  EnumContainer.Color DEFAULT_COLOR =  EnumContainer.Color.GREEN;

    private Wheeled wheeled;
    private EnumContainer.BicycleType type= EnumContainer.BicycleType.MOUNTAIN ;

    // Constructors
    public Bicycle() {
        this(CLASS_NAME , DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR, DEFAULT_WHEELS);
    }

    /**
     * @param name
     * @param maxSpeed
     * @param acceleration
     * @param color
     * @param numOfWheels
     */
    public Bicycle(String name, double maxSpeed, double acceleration,  EnumContainer.Color color, int numOfWheels) {
        super( name, maxSpeed, acceleration, color);
        this.wheeled = new Wheeled(numOfWheels);

    }


    /**
     * @return
     */
    public EnumContainer.BicycleType getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(EnumContainer.BicycleType type) {
        this.type = type;
    }

    /**
     * @return
     */
    public String describeSpecific(){
        return wheeled.describeSpecific()+", Bicycle Type: "+type.toString();

    }

    @Override
    public String className() {
        return CLASS_NAME;
    }

}
