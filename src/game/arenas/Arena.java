package game.arenas;

import game.racers.Racer;

import java.util.ArrayList;

public class Arena {

    private ArrayList<Racer> activeRacers;
    private ArrayList<Racer> completedRacers;
    private final double FRICTION;
    private final int MAX_RACERS;
    private final static int MIN_Y_GAP = 10;
    private double length;

    public Arena(double length, int maxRacers, double friction) {
        this.activeRacers = new ArrayList<Racer>();
        this.completedRacers = new ArrayList<Racer>();
        this.length = length;
        this.MAX_RACERS = maxRacers;
        this.FRICTION = friction;
    }

    public void addRacer(Racer newRacer) throws RacerTypeException, RacerLimitException {
        if (!newRacer.getClass().getSimpleName().equals("Airplane") &&
                !newRacer.getClass().getSimpleName().equals("Helicopter") &&
                !newRacer.getClass().getSimpleName().equals("Car")) {
            throw new RacerTypeException();
        }
        if (activeRacers.size() == MAX_RACERS) {
            throw new RacerLimitException();
        }
        activeRacers.add(newRacer);
    }

    public void initRace() {
        double yStart = 0;
        for (int i = 0; i < activeRacers.size(); i++) {
            activeRacers.get(i).initRace(0, yStart, length, FRICTION);
            yStart += activeRacers.get(i).getHeight() + MIN_Y_GAP;
        }
    }

    public boolean hasActiveRacers() {
        return !activeRacers.isEmpty();
    }

    public void playTurn() {
        for (int i = 0; i < activeRacers.size(); i++) {
            activeRacers.get(i).move();
            if (activeRacers.get(i).getX() >= length) {
                crossFinishLine(activeRacers.get(i));
            }
        }
        updateRacersStatus();
    }

    private void crossFinishLine(Racer racer) {
        racer.crossFinishLine();
        completedRacers.add(racer);
        activeRacers.remove(racer);
    }

    public void showResults() {
        System.out.println("Race Results:");
        for (int i = 0; i < completedRacers.size(); i++) {
            System.out.println((i + 1) + ". " + completedRacers.get(i).describeRacer());
        }
    }

    private void updateRacersStatus() {
        for (int i = 0; i < activeRacers.size(); i++) {
            if (!activeRacers.get(i).isRacing()) {
                completedRacers.add(activeRacers.get(i));
                activeRacers.remove(i);
            }
        }
    }
}
