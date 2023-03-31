package game.racers;

import game.arenas.Arena;
import utilities.EnumContainer;
import utilities.Mishap;
import utilities.Point;


public abstract class Racer {
    private static int serialNumber = 0;

    private int uniqueSerialNumber;

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



    public Racer(String name,double maxSpeed,double acceleration,EnumContainer.Color color){

        this.uniqueSerialNumber = serialNumber;
        serialNumber++;
        this.name = name;
        this.currentLocation = new Point(0, 0); // default value
        this.finish = new Point(0, 0); // default value
        this.arena = null; // default value
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.currentSpeed = 0; // default value
        this.failureProbability = 0; // default value
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

    public void initRace(Arena arena, Point start,Point finish){
        this.arena=arena;
        this.currentLocation=start;
        this.finish=finish;


    }
    public Point Move(double friction){
        if(currentSpeed!=maxSpeed){
            currentSpeed+=acceleration*friction;
            currentLocation.setX(currentLocation.getX()+currentSpeed);
        }

        return currentLocation;
    }

    public abstract String describeSpecific();

    public String describeRacer() {
        return  "["+this.getClass().getSimpleName()+"]" +" name: "+getName()+", SerialNumber: "+getSerialNumber()+
                ", maxSpeed: "+getMaxSpeed()+", acceleration: "+
                getAcceleration()+", color: "+
                getColor().toString()+describeSpecific();



    }

    public void introduce() {
        System.out.println(describeRacer());
    }

    public String className() {
        return this.getClass().getSimpleName();
    }

    public boolean hasMishap() {
        return mishap != null;
    }


    public static int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Point getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Point currentLocation) {
        this.currentLocation = currentLocation;
    }
    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }






    public double getCurrentSpeed() {
        return currentSpeed;
    }



    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
    public double getAcceleration() {
        return acceleration;
    }



    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }
    public double getMaxSpeed() {
        return maxSpeed;
    }



    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public double getFailureProbability() {
        return failureProbability;
    }


    public void setFailureProbability(double failureProbability) {
        this.failureProbability = failureProbability;
    }

    public EnumContainer.Color getColor() {
        return color;
    }

    public void setColor(EnumContainer.Color color) {
        this.color = color;
    }

    public Mishap getMishap() {
        return mishap;
    }

    public void setMishap(Mishap mishap) {
        this.mishap = mishap;
    }
}
