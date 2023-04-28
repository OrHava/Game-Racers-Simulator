package game.racers.naval;

import game.racers.Racer;
import utilities.EnumContainer;
/** @author Or Hava 208418483
 */

public class SpeedBoat extends Racer implements NavalRacer{

    // Fields
    private static final String CLASS_NAME = "SpeedBoat";
    private static final double DEFAULT_MAX_SPEED = 170;
    private static final double DEFAULT_ACCELERATION = 5;
    private static final  EnumContainer.Color DEFAULT_COLOR =  EnumContainer.Color.RED;

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


    @Override
    public String className() {
        return CLASS_NAME;
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
