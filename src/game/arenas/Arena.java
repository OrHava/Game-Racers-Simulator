package game.arenas;

import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.Bicycle;
import game.racers.land.Car;
import game.racers.land.Horse;
import game.racers.naval.RowBoat;
import game.racers.naval.SpeedBoat;
import utilities.EnumContainer;
import utilities.Point;

import java.util.ArrayList;

public  abstract class Arena {


    private ArrayList<Racer> activeRacers;
    private ArrayList<Racer> completedRacers;
    private final double FRICTION;
    private final int MAX_RACERS;
    private final static int MIN_Y_GAP = 10;
    private double length;
    private EnumContainer.ArenaType arenaType;

    /**
     * @param length The Length of Race.
     * @param maxRacers Amount of Max players who can race.
     * @param friction of Arena.
     */
    public Arena(double length, int maxRacers, double friction) {
        this.activeRacers = new ArrayList<Racer>();
        this.completedRacers = new ArrayList<Racer>();
        this.length = length;
        this.MAX_RACERS = maxRacers;
        this.FRICTION = friction;
        this.arenaType = EnumContainer.ArenaType.BASE_ARENA;
    }


    /**
     * @param racer object of type Racer.
     * @throws RacerLimitException for Limit of size of arena.
     * @throws RacerTypeException for wrong type in arena.
     */
    public void addRacer(Racer racer) throws RacerLimitException, RacerTypeException {


        if ( getArenaType().toString().equals(EnumContainer.ArenaType.AERIAL_ARENA.toString()) ) {
            if (!(racer instanceof Airplane) && !(racer instanceof Helicopter)) {
                throw new RacerTypeException("Invalid Racer of type \"" + racer.getClass().getSimpleName() + "\" for Aerial arena.");
            }
        } else if ( getArenaType().toString().equals(EnumContainer.ArenaType.LAND_ARENA.toString())) {
            if (!(racer instanceof Car) && !(racer instanceof Bicycle) && !(racer instanceof Horse)) {
                throw new RacerTypeException("Invalid Racer of type \"" + racer.getClass().getSimpleName() + "\" for Land arena.");
            }
        } else if ( getArenaType().toString().equals(EnumContainer.ArenaType.NAVAL_ARENA.toString())) {
            if (!(racer instanceof RowBoat) && !(racer instanceof SpeedBoat)) {
                throw new RacerTypeException("Invalid Racer of type \"" + racer.getClass().getSimpleName() + "\" for Naval arena.");

            }
        }



        if (activeRacers.size() >= MAX_RACERS) {
            throw new RacerLimitException(MAX_RACERS ,racer.getSerialNumber());
        }
        activeRacers.add(racer);
    }

    /**
     * @return arenaType
     */
    public EnumContainer.ArenaType getArenaType() {
        return this.arenaType;
    }

    /**
     * @param arenaType SelfExplained.
     */
    protected void setArenaType(EnumContainer.ArenaType arenaType) {
        this.arenaType = arenaType;
    }

    /**
     * Function to init Places of each Racer.
     */
    public void initRace() {
        Point start = new Point(0, 0);
        Point finish = new Point(length, 0);
        int yStart = 0;
        for (int i = 0; i < activeRacers.size(); i++) {
            activeRacers.get(i).initRace(this, start, finish);
            yStart += activeRacers.get(i).getCurrentLocation().getY() + MIN_Y_GAP;
            start = new Point(0, yStart);
        }
    }


    /**
     * @return check if the Arena still have Active Racers.
     */
    public boolean hasActiveRacers() {
        return !activeRacers.isEmpty();
    }


    public void playTurn() {
        for (int i = 0; i < activeRacers.size(); i++) {
            activeRacers.get(i).Move(FRICTION);
            if (activeRacers.get(i).getCurrentLocation().getX() >= length) {
                crossFinishLine(activeRacers.get(i));

            }
        }

    }


    /** Check if racer finished the race.
     * @param racer object of type Racer.
     */
    private void crossFinishLine(Racer racer) {

        if (!(racer.getCurrentLocation().getX()==racer.getFinish().getX())){
            completedRacers.add(racer);
            activeRacers.remove(racer);
        }

    }

    public void showResults() {
        System.out.println("Race Results:");
        for (int i = 0; i < completedRacers.size(); i++) {
            System.out.print("#"+i+"-> ");
            completedRacers.get(i).introduceResults();
        }
    }


    /**
     * @return activeRacers
     */
    public ArrayList<Racer> getActiveRacers() {
        return activeRacers;
    }

    /**
     * @param activeRacers Amount Of Active Players.
     */
    public void setActiveRacers(ArrayList<Racer> activeRacers) {
        this.activeRacers = activeRacers;
    }

    /**
     * @return completedRacers Racers who completed the race.
     */
    public ArrayList<Racer> getCompletedRacers() {
        return completedRacers;
    }

    public void setCompletedRacers(ArrayList<Racer> completedRacers) {
        this.completedRacers = completedRacers;
    }

    /**
     * @return FRICTION of Arena.
     */
    public double getFRICTION() {
        return FRICTION;
    }

    /**
     * @return MAX_RACERS Possible AMount Racers in the Arena.
     */
    public int getMAX_RACERS() {
        return MAX_RACERS;
    }

    /**
     * @return length of Arena.
     */
    public double getLength() {
        return length;
    }

    /**
     * @param length of Arena.
     */
    public void setLength(double length) {
        this.length = length;
    }

}
