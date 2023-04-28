package game.arenas.exceptions;
/** @author Or Hava 208418483
 */
public class RacerLimitException extends Exception {
    /**
     * @param ActiveRacers Amount of Racers in the Arena.
     * @param currentRacer the index of Player who couldn't enter.
     */
    public RacerLimitException(int ActiveRacers, int currentRacer) {
        super("Arena is full! (" + ActiveRacers + " active racers exist). racer #"+(currentRacer)+" was not added");
    }

}