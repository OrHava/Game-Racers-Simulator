package game.racers;

import game.racers.land.LandRacer;

public class Wheeled implements LandRacer {
 private int numOfWheels;


    public Wheeled() {
        this.numOfWheels = 0;
    }
    public Wheeled(int numOfWheels) {
        this.numOfWheels = numOfWheels;
    }
    public String describeSpecific(){
        return ", Number of Wheels: "+ numOfWheels;
    }

    public int getNumOfWheels() {
        return numOfWheels;
    }

    public void setNumOfWheels(int numOfWheels) {
        this.numOfWheels = numOfWheels;
    }
}
