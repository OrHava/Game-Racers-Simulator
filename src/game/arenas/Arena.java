
package game.arenas;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

import utilities.EnumContainer.RacerEvent;
import utilities.Point;

import javax.swing.*;

/** @author Or Hava 208418483
 */

public abstract class Arena implements Observer {

    private final static int MIN_Y_GAP = 100;
    private final double FRICTION;
    private final int MAX_RACERS;
    private double length;
    private ArrayList<Racer> activeRacers;
    private ArrayList<Racer> brokenRacers;
    private ArrayList<Racer> compleatedRacers;
    private ArrayList<Racer> disabledRacers;
    private EnumContainer.ArenaType arenaType;
    public Arena(double length, int maxRacers, double friction) {
        this.setLength(length);
        this.MAX_RACERS = maxRacers;
        this.FRICTION = friction;
        this.setActiveRacers(new ArrayList<Racer>());
        this.setCompleatedRacers(new ArrayList<Racer>());
        this.setBrokenRacers(new ArrayList<Racer>());
        this.setDisabledRacers(new ArrayList<Racer>());
    }

    public void addRacer(Racer racer) throws RacerLimitException, RacerTypeException {
        racer.addObserver(this);
        synchronized (activeRacers) {
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
            this.activeRacers.add(racer);
        }
    }

    @Deprecated
    public void crossFinishLine(Racer racer) {
        this.compleatedRacers.add(racer);
        this.activeRacers.remove(racer);
        if (this.activeRacers.size() == 0) {

        }
    }
    public EnumContainer.ArenaType getArenaType() {
        return this.arenaType;
    }

    protected void setArenaType(EnumContainer.ArenaType arenaType) {
        this.arenaType = arenaType;
    }
    public ArrayList<Racer> getActiveRacers() {
        synchronized (activeRacers) {
            return activeRacers;
        }
    }

    public ArrayList<Racer> getBrokenRacers() {
        synchronized (activeRacers) {
            return brokenRacers;
        }
    }

    public ArrayList<Racer> getCompleatedRacers() {
        synchronized (activeRacers) {
            return compleatedRacers;
        }
    }

    public ArrayList<Racer> getDisabledRacers() {
        synchronized (activeRacers) {
            return disabledRacers;
        }
    }

    public double getLength() {
        return length;
    }

    public boolean hasActiveRacers() {
        synchronized (activeRacers) {
            return this.activeRacers.size() > 0;
        }
    }

    public void initRace() {
        int y = 0;
        synchronized (activeRacers) {
            for (Racer racer : this.activeRacers) {
                Point s = new Point(0, y);
                Point f = new Point(this.length, y);
                racer.initRace(this, s, f, this.FRICTION);
                y += Arena.MIN_Y_GAP;
            }
        }
    }

    @Deprecated
    public void playTurn() {
        for (Racer racer : this.activeRacers) {
            racer.move();
        }
        for (Racer r : this.compleatedRacers)
            this.activeRacers.remove(r);
    }

    public void setActiveRacers(ArrayList<Racer> activeRacers) {
        this.activeRacers = activeRacers;
    }

    public void setBrokenRacers(ArrayList<Racer> brokenRacers) {
        this.brokenRacers = brokenRacers;
    }

    public void setCompleatedRacers(ArrayList<Racer> compleatedRacers) {
        this.compleatedRacers = compleatedRacers;
    }

    public void setDisabledRacers(ArrayList<Racer> disabledRacers) {
        this.disabledRacers = disabledRacers;
    }

    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Prints the results of the race to the console, including completed,
     * disabled, broken, and active racers.
     */
    public void showResults() {
        synchronized (activeRacers) {
            int i = 1;

            System.out.println("Completed:");
            for (Racer racer : this.compleatedRacers) {
                String s = String.format("#%d -> %s", i++, racer.describeRacer());
                System.out.println(s);
            }

            System.out.println("Disabled:");
            for (Racer racer : this.disabledRacers) {
                String s = String.format("#%d -> %s", i++, racer.describeRacer());
                System.out.println(s);
            }


            System.out.println("Broken:");
            for (Racer racer : this.brokenRacers) {
                String s = String.format("#%d -> %s", i++, racer.describeRacer());
                System.out.println(s);
            }

            System.out.println("Active:");
            for (Racer racer : this.activeRacers) {
                String s = String.format("#%d -> %s", i++, racer.describeRacer());
                System.out.println(s);
            }
        }
    }


    public void startRace() throws InterruptedException {
        initRace();
        ExecutorService executorService;
        synchronized (activeRacers) {
            executorService = Executors.newFixedThreadPool(this.activeRacers.size());
            synchronized (this) {
                for (Racer racer : activeRacers) {
                    executorService.execute(racer);
                }
            }
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
    }
    @Override
    public void update(Observable o, Object arg) {

        Racer racer = (Racer) o;

        switch ((RacerEvent) arg) {
            case BROKENDOWN:
                synchronized (this.activeRacers) {
                    this.activeRacers.remove(racer);
                    this.brokenRacers.add(racer);
                }
                break;
            case FINISHED:
                synchronized (this.activeRacers) {
                    this.activeRacers.remove(racer);
                    this.brokenRacers.remove(racer);
                    this.compleatedRacers.add(racer);
                }
                break;
            case REPAIRED:
                synchronized (this.activeRacers) {
                    this.brokenRacers.remove(racer);
                    this.activeRacers.add(racer);
                }
                break;
            case DISABLED:
                synchronized (this.activeRacers) {
                    this.activeRacers.remove(racer);
                    this.disabledRacers.add(racer);
                }
                break;
        }
    }
}