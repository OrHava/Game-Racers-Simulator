package game.racers.land;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer;

/** @author Or Hava 208418483
 */
public class Car extends Racer implements LandRacer{
    // Fields
    private static final String CLASS_NAME = "Car";
    private static final int DEFAULT_WHEELS = 4;
    private static final double DEFAULT_MAX_SPEED = 400;
    private static final double DEFAULT_ACCELERATION = 20;
    private static final  EnumContainer.Color DEFAULT_COLOR =  EnumContainer.Color.RED;

    private Wheeled wheeled;
    private EnumContainer.Engine engine= EnumContainer.Engine.FOURSTROKE ;

    // Constructors
    public Car() {
        this(CLASS_NAME  , DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR, DEFAULT_WHEELS);
    }

    public Car(String name, double maxSpeed, double acceleration,  EnumContainer.Color color, int numOfWheels) {
        super(name, maxSpeed, acceleration, color);
        this.wheeled = new Wheeled(numOfWheels);

    }

    // Getters and Setters
    public EnumContainer.Engine getEngine() {
        return engine;
    }

    public void setEngine(EnumContainer.Engine engine) {
        this.engine = engine;
    }

    public String describeSpecific(){
        return wheeled.describeSpecific()+", Engine Type: "+engine.toString();

    }

    @Override
    public String className() {
        return CLASS_NAME;
    }

}
