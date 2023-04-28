package game.racers.air;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer;
/** @author Or Hava 208418483
 */
public class Airplane extends Racer implements AerialRacer  {
    private static final String CLASS_NAME = "Airplane";
    private static final int DEFAULT_WHEELS=3;
    private  static final double  DEFAULT_MAX_SPEED=885;
    private  static final double DEFAULT_ACCELERATION=100;
    private static final EnumContainer.Color DEFAULT_color= EnumContainer.Color.BLACK;
    private Wheeled wheeled;

    /**
     * @param name
     * @param maxSpeed
     * @param acceleration
     * @param color
     * @param numOfWheels
     */
    public Airplane(String name, double maxSpeed, double acceleration, EnumContainer.Color color, int numOfWheels) {
        super( name, maxSpeed, acceleration, color);
        this.wheeled = new Wheeled(numOfWheels);
    }

    public Airplane() {
        this(CLASS_NAME, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION,
                DEFAULT_color, DEFAULT_WHEELS);
    }

    /**
     * @return
     */
    public EnumContainer.Color getDefaultColor() {
        return DEFAULT_color;
    }


    /**
     * @return
     */
    public Wheeled getWheeled() {
        return wheeled;
    }

    /**
     * @return
     */
    public String describeSpecific(){
        return wheeled.describeSpecific();

    }

    @Override
    public String className() {
        return CLASS_NAME;
    }

}
