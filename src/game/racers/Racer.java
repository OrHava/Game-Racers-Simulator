package game.racers;

import game.arenas.Arena;
import utilities.EnumContainer;
import utilities.Fate;
import utilities.Mishap;
import utilities.Point;

import java.util.Observable;


public abstract class Racer extends Observable implements Runnable {
    private static int  count = 0;
    private int serialNumber;


    private String name;
    private Point currentLocation;
    private Point finish;
    private Arena arena;
    private double maxSpeed;
    private double acceleration;
    private double currentSpeed;
    private double failureProbability;
    private EnumContainer.Color color;
    private Mishap mishap;
    private double arenaFriction;

    /**
     @param name The name of the Racer.
     @param maxSpeed The maximum speed of the Racer.
     @param acceleration The acceleration of the Racer.
     @param color The color of the Racer.
     */
    public Racer(String name,double maxSpeed,double acceleration,EnumContainer.Color color){
        count++;
        serialNumber = count;

        this.name = name;
        this.currentLocation = new Point(0, 0); // default value
        this.finish = new Point(0, 0); // default value
        this.arena = null; // default value
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.currentSpeed = 0; // default value
        this.failureProbability = 0.1; // default value
        this.color = color;
        this.mishap = null; // default value

    }

    public Racer(){


        this.name = null;
        this.currentLocation = new Point(0, 0); // default value
        this.finish = new Point(0, 0); // default value
        this.arena = null; // default value
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.currentSpeed = 0; // default value
        this.failureProbability = 0; // default value
        this.color = null;
        this.mishap = null; // default value
    }

    /**
     * @param arena    The arena the Racer is racing in.
     * @param start    The starting point of the Racer.
     * @param finish   The finish point of the Racer.
     * @param FRICTION
     */
    public void initRace(Arena arena, Point start, Point finish, double FRICTION){
        this.arena=arena;
        this.setCurrentLocation (new Point(start));
        this.setFinish(new Point(finish));
        this.arenaFriction=FRICTION;


    }

    /**

     Moves the Racer a distance based on its current speed, acceleration, and friction.
     Also updates the Racer's mishap status and calculates its reduction factor.
     @param friction The friction of the surface the Racer is currently on.
     @return The Racer's new location.
     */
    public Point Move(double friction) {
        double reductionFactor = 1;
        if (this.mishap == null || (mishap.isFixable() && mishap.getTurnsToFix() == 0)) {
            mishap = null;
            if (Fate.breakDown(this.failureProbability)) {
                this.mishap = Fate.generateMishap();
                System.out.println(this.getName() + " Has a new mishap!: " + mishap);
                mishap.nextTurn();
                reductionFactor = mishap.getReductionFactor();
            }
        } else if (mishap.isFixable()) {
            mishap.nextTurn();
            reductionFactor = mishap.getReductionFactor();
        } else {
            reductionFactor = mishap.getReductionFactor();
        }

        if (this.currentSpeed < this.maxSpeed) {
            this.setCurrentSpeed(this.currentSpeed + this.acceleration * friction * reductionFactor);
        }

        if (this.currentSpeed > this.maxSpeed) {
            this.setCurrentSpeed(this.maxSpeed);
        }

        Point newPoint = new Point((this.currentLocation.getX() + (1 * this.currentSpeed)), this.currentLocation.getY());
        this.setCurrentLocation(newPoint);
        return newPoint;
    }

    public void move() {
        double reductionFactor = 1;
        if (!(this.hasMishap()) && Fate.breakDown(this.failureProbability)) {
            this.mishap = Fate.generateMishap();
            this.setChanged();
            System.out.println(this.name + " Has a new mishap! (" + this.mishap + ")");
            if (this.isDisabled()) {
                notifyObservers(EnumContainer.RacerEvent.DISABLED);
                return;
            } else {
                this.notifyObservers(EnumContainer.RacerEvent.BROKENDOWN);
            }
        }

        if (this.hasMishap()) {
            reductionFactor = mishap.getReductionFactor();
            this.mishap.nextTurn();
        }

        if (this.currentSpeed < this.maxSpeed) {
            this.setCurrentSpeed(this.currentSpeed + this.acceleration * this.arenaFriction);
        }

        double newX = (this.currentLocation.getX() + this.currentSpeed) * reductionFactor;
        Point newLocation = new Point(newX, this.currentLocation.getY());
        this.setCurrentLocation(newLocation);
    }

    private boolean isDisabled() {
        if (this.mishap != null) {
            return this.mishap.isFixable() == false;
        }
        return false;
    }

    private boolean hasMishap() {
        if (this.mishap != null && this.mishap.getTurnsToFix() == 0) {
            this.setMishap(null);
            this.setChanged();
            this.notifyObservers(EnumContainer.RacerEvent.REPAIRED);
        }
        return this.mishap != null;
    }
    private boolean raceInPrograss() {
        return this.currentLocation.getX() < this.finish.getX();
    }
    @Override
    public void run() {
        while (this.raceInPrograss() && !this.isDisabled()) {
            System.out.println("*************************************run of racer work: ****************************************************" );
            this.move();
            // DEV disable sleep
            // try {
            // Thread.sleep(100);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
        }
        if (this.isDisabled())
            return;
        setChanged();
        this.notifyObservers(EnumContainer.RacerEvent.FINISHED);
    }

    /**
     * @return describe of Specific Racer
     */
    public abstract String describeSpecific();

    /**
     * @return String of Desc of Racer
     */
    public String describeRacer() {

        if("Airplane Helicopter Bicycle Car Horse RowBoat SpeedBoat".contains(getName())){
            return  "["+this.getClass().getSimpleName()+"]" +" name: "+getName()+" #"+serialNumber+", SerialNumber: "+getSerialNumber()+
                    ", maxSpeed: "+getMaxSpeed()+", acceleration: "+
                    getAcceleration()+", color: "+
                    getColor().toString()+describeSpecific();
        }
        return  "["+this.getClass().getSimpleName()+"]" +" name: "+getName()+", SerialNumber: "+getSerialNumber()+
                ", maxSpeed: "+getMaxSpeed()+", acceleration: "+
                getAcceleration()+", color: "+
                getColor().toString()+describeSpecific();

    }

    /**
     * @return describe Racer To Results
     */
    public String describeRacerToResults() {

      return "name: "+getName()+", SerialNumber: "+getSerialNumber()+
                ", maxSpeed: "+getMaxSpeed()+", acceleration: "+
                getAcceleration()+", color: "+
                getColor().toString()+describeSpecific();

    }

    public void introduce() {
        System.out.println(describeRacer());
    }

    public void introduceResults() {
        System.out.println(describeRacerToResults());
    }

    /**
     * @return
     */
    public String className() {
        return this.getClass().getSimpleName();
    }

    /**
     * @return
     */
//    public boolean hasMishap() {
//        return mishap != null;
//    }


    /**
     * @return
     */
    public  int getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber
     */
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     */
    public Point getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @param currentLocation
     */
    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * @return
     */
    public Point getFinish() {
        return finish;
    }


    /**
     * @return
     */
    public static int getCount() {
        return count;
    }


    /**
     * @param finish
     */
    public void setFinish(Point finish) {
        this.finish = finish;
    }

    /**
     * @return
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * @param arena
     */
    public void setArena(Arena arena) {
        this.arena = arena;
    }


    /**
     * @return
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }


    /**
     * @param currentSpeed
     */
    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    /**
     * @return
     */
    public double getAcceleration() {
        return acceleration;
    }


    /**
     * @param acceleration
     */
    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    /**
     * @return
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }


    /**
     * @param maxSpeed
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    /**
     * @return
     */
    public double getFailureProbability() {
        return failureProbability;
    }


    /**
     * @param failureProbability
     */
    public void setFailureProbability(double failureProbability) {
        this.failureProbability = failureProbability;
    }

    /**
     * @return
     */
    public EnumContainer.Color getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(EnumContainer.Color color) {
        this.color = color;
    }

    /**
     * @return
     */
    public Mishap getMishap() {
        return mishap;
    }

    /**
     * @param mishap
     */
    public void setMishap(Mishap mishap) {
        this.mishap = mishap;
    }
}
