
package game.arenas;

import java.beans.PropertyChangeEvent;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


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

public abstract class Arena  {

    private final static int MIN_Y_GAP = 100;
    private final double FRICTION;
    private final int MAX_RACERS;
    private double length;
    private ArrayList<Racer> activeRacers;
    private ArrayList<Racer> brokenRacers;
    private ArrayList<Racer> compleatedRacers;
    private ArrayList<Racer> disabledRacers;
    private EnumContainer.ArenaType arenaType;

    private PropertyChangeSupport propertyChangeSupport;
    /**
     Constructs an instance of the Arena class with the specified parameters.
     @param length The length of the arena.
     @param maxRacers The maximum number of racers allowed in the arena.
     @param friction The friction value of the arena.
     */
    public Arena(double length, int maxRacers, double friction) {
        this.setLength(length);
        this.MAX_RACERS = maxRacers;
        this.FRICTION = friction;
        this.setActiveRacers(new ArrayList<Racer>());
        this.setCompleatedRacers(new ArrayList<Racer>());
        this.setBrokenRacers(new ArrayList<Racer>());
        this.setDisabledRacers(new ArrayList<Racer>());

        propertyChangeSupport = new PropertyChangeSupport(this);

    }
    /**
     Adds a property change listener to the arena.
     @param listener The property change listener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    /**
     Removes a property change listener from the arena.
     @param listener The property change listener to be removed.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    /**
     Fires a property change event with the specified property name, old value, and new value.
     @param propertyName The name of the property that has changed.
     @param oldValue The old value of the property.
     @param newValue The new value of the property.
     */
    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    /**

     Adds a racer to the race.
     Registers a property change listener for the racer to handle racer events.
     The method is synchronized to ensure thread safety when accessing and modifying the active racers collection.
     @param racer The racer to be added.
     @throws RacerLimitException if the maximum number of racers has been reached.
     @throws RacerTypeException if the racer type is invalid for the current arena type.
     */
    public void addRacer(Racer racer) throws RacerLimitException, RacerTypeException {
        PropertyChangeListener listener = (evt) -> {
            if (evt.getPropertyName().equals("event")) {
                RacerEvent event = (RacerEvent) evt.getNewValue();
                update(racer, event);
            }
        };

        propertyChangeSupport.addPropertyChangeListener(listener);

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
            firePropertyChange("activeRacers", null, activeRacers);

        }
    }
    /**
     Adds the racer to the completed racers list and removes it from the active racers list.
     Fires property change events for the updated activeRacers and compleatedRacers.
     @param racer The racer that crossed the finish line.
     */
    @Deprecated
    public void crossFinishLine(Racer racer) {
        this.compleatedRacers.add(racer);
        this.activeRacers.remove(racer);
        if (this.activeRacers.size() == 0) {

        }
        firePropertyChange("activeRacers", null, activeRacers);
        firePropertyChange("compleatedRacers", null, compleatedRacers);
    }


    public EnumContainer.ArenaType getArenaType() {
        return this.arenaType;
    }

    protected void setArenaType(EnumContainer.ArenaType arenaType) {
        this.arenaType = arenaType;
    }
    /**

     Retrieves the list of Active racers.
     The method is synchronized to ensure thread safety when accessing the ActiveRacers collection.
     @return The list of Active racers.
     */
    public ArrayList<Racer> getActiveRacers() {
        synchronized (activeRacers) {
            return activeRacers;
        }
    }
    /**

     Retrieves the list of broken racers.
     The method is synchronized to ensure thread safety when accessing the brokenRacers collection.
     @return The list of broken racers.
     */
    public ArrayList<Racer> getBrokenRacers() {
        synchronized (activeRacers) {
            return brokenRacers;
        }
    }
    /**

     Retrieves the list of completed racers.
     The method is synchronized to ensure thread safety when accessing the completedRacers collection.
     @return The list of completed racers.
     */
    public ArrayList<Racer> getCompleatedRacers() {
        synchronized (activeRacers) {
            return compleatedRacers;
        }
    }
    /**

     Retrieves the list of disabled racers.
     The method is synchronized to ensure thread safety when accessing the disabledRacers collection.
     @return The list of disabled racers.
     */
    public ArrayList<Racer> getDisabledRacers() {
        synchronized (activeRacers) {
            return disabledRacers;
        }
    }

    /**
     Retrieves the length of the race.
     @return The length of the race.
     */
    public double getLength() {
        return length;
    }
    /**
     Checks if there are active racers in the race.
     The method is synchronized to ensure thread safety when accessing the activeRacers collection.
     @return {@code true} if there are active racers, {@code false} otherwise.
     */
    public boolean hasActiveRacers() {
        synchronized (activeRacers) {
            return this.activeRacers.size() > 0;
        }
    }
    /**
     Initializes the race by setting the starting and finishing points for each active racer.
     The method is synchronized to ensure thread safety when accessing and modifying the active racers collection.
     The y-coordinate for each racer is incremented by the minimum y-gap defined in the Arena class.
     */
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
    /**
     Plays a turn in the race by invoking the move method for each active racer.
     Removes completed racers from the active racer list.

     */
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

    /**

     Starts the race by initializing the race, creating an executor service, and executing the racers.
     The method is synchronized to ensure thread safety when accessing and modifying racer collections.
     Waits for the executor service to complete all racer threads before continuing.
     Removes property change listeners from racers once the race is finished.
     @throws InterruptedException if the thread is interrupted while waiting for the executor service to terminate.
     */
    public void startRace() throws InterruptedException {
        initRace();
        ExecutorService executorService;
        synchronized (activeRacers) {
            executorService = Executors.newFixedThreadPool(this.activeRacers.size());
            synchronized (this) {
                for (Racer racer : activeRacers) {
                    racer.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            RacerEvent event = (RacerEvent) evt.getNewValue();
                            update(racer, event);
                        }
                    });
                    executorService.execute(racer);
                }




            }
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);


        for (Racer racer : activeRacers) {
            racer.removeAllPropertyChangeListeners();
        }
        for (Racer racer : brokenRacers) {
            racer.removeAllPropertyChangeListeners();
        }

    }
    /**

     Updates the racer's state based on the provided racer event.

     The method is synchronized to ensure thread safety when modifying the racer collections.

     @param racer The racer to be updated.

     @param event The racer event indicating the state change.
     */
    public void update(Racer racer, RacerEvent event) {
        synchronized (activeRacers) {
            if (event == null) {
                return;
            }

            switch (event) {
                case BROKENDOWN:
                    activeRacers.remove(racer);
                    brokenRacers.add(racer);

                    break;
                case FINISHED:
                    activeRacers.remove(racer);
                     brokenRacers.remove(racer);
                    compleatedRacers.add(racer);

                    break;
                case REPAIRED:
                    brokenRacers.remove(racer);
                    activeRacers.add(racer);


                    break;
                case DISABLED:
                    activeRacers.remove(racer);
                    disabledRacers.add(racer);

                    break;
            }
        }
    }





}