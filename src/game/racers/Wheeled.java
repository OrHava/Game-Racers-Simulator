package game.racers;

import game.racers.land.LandRacer;

public class Wheeled implements LandRacer {
 private int numOfWheels;


    public Wheeled() {
        this.numOfWheels = 0;
    }

    /**
     * @param numOfWheels
     */
    public Wheeled(int numOfWheels) {
        this.numOfWheels = numOfWheels;
    }

    /**
     * @return
     */
    public String describeSpecific(){
        return ", Number of Wheels: "+ numOfWheels;
    }

    /**
     * @return
     */
    public int getNumOfWheels() {
        return numOfWheels;
    }

    /**
     * @param numOfWheels
     */
    public void setNumOfWheels(int numOfWheels) {
        this.numOfWheels = numOfWheels;
    }
}
