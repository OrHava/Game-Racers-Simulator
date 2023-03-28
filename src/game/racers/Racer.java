package game.racers;

import game.arenas.Arena;
import utilities.EnumContainer;
import utilities.Mishap;
import utilities.Point;


public class Racer {
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



    public Racer(String name,double maxSpeed,double acceleration,EnumContainer.Color color){

        this.serialNumber = -1; // default value
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

    public void initRace(Arena arena, Point start,Point finish){
        this.arena=arena;
        this.currentLocation=start;
        this.finish=finish;


    }


    public int getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public Point getFinish() {
        return finish;
    }

    public Arena getArena() {
        return arena;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public double getFailureProbability() {
        return failureProbability;
    }

    public EnumContainer.Color getColor() {
        return color;
    }

    public Mishap getMishap() {
        return mishap;
    }
}
