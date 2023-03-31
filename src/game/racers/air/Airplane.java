package game.racers.air;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer;

public class Airplane extends Racer implements AerialRacer  {
    private static final String CLASS_NAME = "Airplane";
    private static final int DEFAULT_WHEELS=3;
    static final double  DEFAULT_MAX_SPEED=885;
    static final double DEFAULT_ACCELERATION=100;
    static final EnumContainer.Color DEFAULT_color= EnumContainer.Color.BLACK;
    private Wheeled wheeled;
    public Airplane(String name, double maxSpeed, double acceleration, EnumContainer.Color color, int numOfWheels) {
        super(name == null ? CLASS_NAME + " #" + Racer.getSerialNumber() : name, maxSpeed, acceleration, color);
        this.wheeled = new Wheeled(numOfWheels);
    }

    public Airplane() {
        this(CLASS_NAME + " #" + Racer.getSerialNumber() , DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION,
                DEFAULT_color, DEFAULT_WHEELS);
    }

    public EnumContainer.Color getDefaultColor() {
        return DEFAULT_color;
    }


    public Wheeled getWheeled() {
        return wheeled;
    }
    public String describeSpecific(){
        return wheeled.describeSpecific();

    }
}
