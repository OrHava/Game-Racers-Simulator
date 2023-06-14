package game.racers;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;



import game.arenas.Arena;
import utilities.EnumContainer;
import utilities.EnumContainer.RacerEvent;
import utilities.Fate;
import utilities.Mishap;
import utilities.Point;
/** @author Or Hava 208418483
 */
public abstract class Racer implements Runnable,Cloneable {
    protected static int lastSerialNumber = 1;

    public static void setLastSerialNumber(int lastSerialNumber) {
        Racer.lastSerialNumber = lastSerialNumber;
    }

    public static int getLastSerialNumber() {
        return lastSerialNumber++;
    }

    private int serialNumber;
    private String name;
    private Point currentLocation;
    private Point finish;
    private double maxSpeed;
    private double acceleration;
    private double currentSpeed;
    private double failureProbability;
    private EnumContainer.Color color;
    private Mishap mishap;
    private double arenaFriction;
    private Arena arena;
    private boolean isBroken;
    private List<PropertyChangeListener> propertyChangeListeners;

    private PropertyChangeSupport propertyChangeSupport;
    private int position;




    /**

     Constructs a racer object with the specified name, maximum speed, acceleration, and color.
     @param name the name of the racer
     @param maxSpeed the maximum speed of the racer
     @param acceleration the acceleration of the racer
     @param color the color of the racer
     */
    public Racer(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
        this.setSerialNumber(Racer.getLastSerialNumber());
        this.setName(name);
        this.setMaxSpeed(maxSpeed);
        this.setAcceleration(acceleration);
        this.setColor(color);
        this.setFailureProbability(0.2);
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.propertyChangeListeners = new ArrayList<>();
        isBroken = false;
    }
    /**

     Constructs a copy of the given racer object.
     @param racer the racer to be copied
     */
    public Racer(Racer racer) {
        this.serialNumber = racer.serialNumber;
        this.name = racer.name;
        this.currentLocation = new Point(racer.currentLocation);
        this.finish = new Point(racer.finish);
        this.maxSpeed = racer.maxSpeed;
        this.acceleration = racer.acceleration;
        this.currentSpeed = racer.currentSpeed;
        this.failureProbability = racer.failureProbability;
        this.color = racer.color;
        this.mishap = null;
        this.arenaFriction = racer.arenaFriction;
        this.arena = racer.arena;
        this.isBroken = racer.isBroken;
        this.propertyChangeListeners = new ArrayList<>(racer.propertyChangeListeners);
        this.propertyChangeSupport = new PropertyChangeSupport(this);
        this.position = racer.position;
    }
    /**

     Returns the class name of the racer.
     @return the class name
     */
    public abstract String className();
    /**

     Returns a string describing the specific details of the racer.
     @return the specific description of the racer
     */
    public abstract String describeSpecific();
    /**

     Returns a string describing the racer's details.
     @return the description of the racer
     */
    public String describeRacer() {
        if ("Airplane Helicopter Bicycle Car Horse RowBoat SpeedBoat".contains(getName())) {
            return "[" + this.getClass().getSimpleName() + "]" + " name: " + getName() + " #" + serialNumber
                    + ", SerialNumber: " + getSerialNumber() + ", maxSpeed: " + getMaxSpeed() + ", acceleration: "
                    + getAcceleration() + ", color: " + getColor().toString() + describeSpecific();
        }
        return "[" + this.getClass().getSimpleName() + "]" + " name: " + getName() + ", SerialNumber: "
                + getSerialNumber() + ", maxSpeed: " + getMaxSpeed() + ", acceleration: " + getAcceleration()
                + ", color: " + getColor().toString() + describeSpecific();
    }
    /**

     Returns a string describing the racer's details for race results.
     @return the description of the racer for race results
     */
    public String describeRacerToResults() {
        return "name: " + getName() + ", SerialNumber: " + getSerialNumber() + ", maxSpeed: " + getMaxSpeed()
                + ", acceleration: " + getAcceleration() + ", color: " + getColor().toString() + describeSpecific();
    }
    /**

     Prints the racer's description to the console.
     */
    public void introduce() {
        System.out.println(describeRacer());
    }
    /**

     Prints the racer's description for race results to the console.
     */
    public void introduceResults() {
        System.out.println(describeRacerToResults());
    }

    public double getAcceleration() {
        return acceleration;
    }

    public Arena getArena() {
        return arena;
    }

    public double getArenaFriction() {
        return arenaFriction;
    }

    public EnumContainer.Color getColor() {
        return color;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public double getFailureProbability() {
        return failureProbability;
    }

    public Point getFinish() {
        return finish;
    }

    public Point getLocation() {
        return this.currentLocation;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public Mishap getMishap() {
        return mishap;
    }

    public String getName() {
        return name;
    }

    public int getSerialNumber() {
        return serialNumber;
    }



    public void initRace(Arena arena, Point start, Point finish, double friction) {
        this.setArena(arena);
        this.setCurrentLocation(new Point(start));
        this.setFinish(new Point(finish));
        this.setArenaFriction(friction);
    }

    public boolean isDisabled() {
        if (this.mishap != null) {
            return !this.mishap.isFixable();
        }
        return false;
    }



    private boolean hasMishap() {
        if (this.mishap != null && this.mishap.getTurnsToFix() == 0) {
            this.setMishap(null);

            this.propertyChangeSupport.firePropertyChange("RacerEvent", null, RacerEvent.REPAIRED);

        }
        return this.mishap != null;
    }

    /**

     Moves the racer by updating its current location and speed.
     If the racer has a mishap, it applies a reduction factor to the distance traveled.
     Fires property change events based on the racer's state changes.
     */
    public void move() {
        double reductionFactor = 1;
        if (!(this.hasMishap()) && Fate.breakDown(this.failureProbability)) {
            this.mishap = Fate.generateMishap();


            if (this.isDisabled()) {
                this.propertyChangeSupport.firePropertyChange("RacerEvent", null, RacerEvent.DISABLED);


                return;
            } else {
                this.propertyChangeSupport.firePropertyChange("RacerEvent", null, RacerEvent.BROKENDOWN);

            }
        }

        if (this.hasMishap()) {
            reductionFactor = mishap.getReductionFactor();
            this.mishap.nextTurn();
        }

        if (currentSpeed < maxSpeed) {
            setCurrentSpeed(currentSpeed + acceleration * arenaFriction);
        }

        double distance = 1 * currentSpeed * reductionFactor;
        Point newPoint = new Point(currentLocation.getX() + distance, currentLocation.getY());


        setCurrentLocation(newPoint);

        if (this.hasMishap() && mishap.getTurnsToFix() == 0) {
            this.setMishap(null);
            this.propertyChangeSupport.firePropertyChange("RacerEvent", null, RacerEvent.REPAIRED);
        }
    }



    private boolean raceInProgress() {
        return this.currentLocation.getX() < this.finish.getX();
    }


    /**
     Overrides the run method of the Thread class.
     Executes a loop that continues until the race is finished or the racer is disabled.
     In each iteration, it calls the move method to update the racer's position and speed.
     Pauses the thread for 100 milliseconds after each move.
     Fires a property change event when the racer finishes the race.
     */
    @Override
    public void run() {
        while (raceInProgress() && !isDisabled()) {
            move();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (isDisabled())
            return;

        propertyChangeSupport.firePropertyChange("RacerEvent", null, RacerEvent.FINISHED);
    }


    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    private void setArenaFriction(double friction) {
        this.arenaFriction = friction;
    }

    public void setColor(EnumContainer.Color color) {
        this.color = color;
    }

    public void setCurrentLocation(Point currentLocation) {
        if (this.finish != null && currentLocation.getX() > finish.getX()) {
            currentLocation.setX(finish.getX());
        }
        this.currentLocation = currentLocation;
    }

    private void setCurrentSpeed(double currentSpeed) {
        if (currentSpeed > this.maxSpeed)
            currentSpeed = this.maxSpeed;
        this.currentSpeed = currentSpeed;
    }

    public void setFailureProbability(double failureProbability) {
        if (failureProbability >= 0 && failureProbability <= 1)
            this.failureProbability = failureProbability;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setMishap(Mishap mishap) {
        this.mishap = mishap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }


    public void setPosition(int position) {
        this.position = position;
    }



    /**
     Adds a property change listener to the object.
     The listener will be notified when a property value changes.
     @param listener the property change listener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
        propertyChangeListeners.add(listener);
    }


    /**

     Removes all registered property change listeners from the object.
     This method clears the internal list of property change listeners and
     unsubscribes them from receiving property change events.
     */
    public void removeAllPropertyChangeListeners() {
        for (PropertyChangeListener listener : propertyChangeListeners) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }
        propertyChangeListeners.clear();
    }


    /**

     Creates a clone of the current Racer object.
     @return a cloned instance of the Racer object
     */
    @Override
    public Racer clone() {
        try {

            Racer racer = (Racer) super.clone();
            racer.propertyChangeListeners = new ArrayList<>(this.propertyChangeListeners); // Deep copy of List
            racer.propertyChangeSupport = new PropertyChangeSupport(racer);
            racer.setSerialNumber(Racer.getLastSerialNumber());
            return racer;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }



}