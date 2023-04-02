package game.racers.naval;

import game.racers.Racer;
import utilities.EnumContainer;

import static java.lang.constant.ConstantDescs.DEFAULT_NAME;

public class SpeedBoat extends Racer implements NavalRacer{

    // Fields
    public static final String CLASS_NAME = "SpeedBoat"
            ;
    public static final double DEFAULT_MAX_SPEED = 170;
    public static final double DEFAULT_ACCELERATION = 5;
    public static final  EnumContainer.Color DEFAULT_COLOR =  EnumContainer.Color.RED;

    private EnumContainer.BoatType type= EnumContainer.BoatType.SKULLING ;
    private EnumContainer.Team team= EnumContainer.Team.DOUBLE ;

    // Constructors
    public SpeedBoat() {
        this(CLASS_NAME  , DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_COLOR);
    }

    /**
     * @param name
     * @param maxSpeed
     * @param acceleration
     * @param color
     */
    public SpeedBoat(String name, double maxSpeed, double acceleration,  EnumContainer.Color color) {
        super( name, maxSpeed, acceleration, color);
        //+ Racer.getSerialNumber()

    }


    public EnumContainer.BoatType getType() {
        return type;
    }

    public void setType(EnumContainer.BoatType type) {
        this.type = type;
    }

    public EnumContainer.Team getTeam() {
        return team;
    }

    public void setTeam(EnumContainer.Team team) {
        this.team = team;
    }

    /**
     * @return
     */
    public String describeSpecific(){
        return ", Type: "+type.toString() +", Team: "+team.toString() ;

    }
}
