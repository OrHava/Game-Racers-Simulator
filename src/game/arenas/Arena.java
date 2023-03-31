package game.arenas;

import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.air.AerialRacer;
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.Bicycle;
import game.racers.land.Car;
import game.racers.land.Horse;
import game.racers.land.LandRacer;
import game.racers.naval.NavalRacer;
import game.racers.naval.RowBoat;
import game.racers.naval.SpeedBoat;
import utilities.EnumContainer;
import utilities.Point;

import java.util.ArrayList;

public class Arena {


    private ArrayList<Racer> activeRacers;
    private ArrayList<Racer> completedRacers;
    private final double FRICTION;
    private final int MAX_RACERS;
    private final static int MIN_Y_GAP = 10;
    private double length;
    private EnumContainer.ArenaType arenaType;
    public Arena(double length, int maxRacers, double friction) {
        this.activeRacers = new ArrayList<Racer>();
        this.completedRacers = new ArrayList<Racer>();
        this.length = length;
        this.MAX_RACERS = maxRacers;
        this.FRICTION = friction;
        this.arenaType = EnumContainer.ArenaType.BASE_ARENA;
    }



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
            throw new RacerLimitException(MAX_RACERS, activeRacers.size());
        }
        activeRacers.add(racer);
    }
    public EnumContainer.ArenaType getArenaType() {
        return this.arenaType;
    }

    protected void setArenaType(EnumContainer.ArenaType arenaType) {
        this.arenaType = arenaType;
    }
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
        updateRacersStatus();
    }

    private void crossFinishLine(Racer racer) {

        if (!(racer.getCurrentLocation().getX()==racer.getFinish().getX())){
            completedRacers.add(racer);
            activeRacers.remove(racer);
        }

    }

    public void showResults() {
        System.out.println("Race Results:");
        for (int i = 0; i < completedRacers.size(); i++) {
            System.out.println((i + 1) + ". " + completedRacers.get(i).describeRacer());
        }
    }

    private void updateRacersStatus() {
        for (int i = 0; i < activeRacers.size(); i++) {
            if (!(activeRacers.get(i).getCurrentLocation().getX()==activeRacers.get(i).getFinish().getX())) {
                completedRacers.add(activeRacers.get(i));
                activeRacers.remove(i);
            }
        }
    }

    public ArrayList<Racer> getActiveRacers() {
        return activeRacers;
    }

    public void setActiveRacers(ArrayList<Racer> activeRacers) {
        this.activeRacers = activeRacers;
    }

    public ArrayList<Racer> getCompletedRacers() {
        return completedRacers;
    }

    public void setCompletedRacers(ArrayList<Racer> completedRacers) {
        this.completedRacers = completedRacers;
    }

    public double getFRICTION() {
        return FRICTION;
    }

    public int getMAX_RACERS() {
        return MAX_RACERS;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

}
