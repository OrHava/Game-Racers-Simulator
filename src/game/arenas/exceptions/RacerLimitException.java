package game.arenas.exceptions;

public class RacerLimitException extends Exception {
    public RacerLimitException(int maxRacers, int currentRacers) {
        super("Arena is full! (" + currentRacers + " active racers exist). racer #"+currentRacers+" was not added");
    }

}