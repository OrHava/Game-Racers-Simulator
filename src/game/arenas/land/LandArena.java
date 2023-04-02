package game.arenas.land;

import game.arenas.Arena;
import utilities.EnumContainer;


public class LandArena extends Arena {
    private final static double DEFAULT_FRICTION=0.5;
    private final static int DEFAULT_MAX_RACER=8;
    private  final static int DEFAULT_LENGTH=800;

    private EnumContainer.Coverage coverage = EnumContainer.Coverage.GRASS;
    private EnumContainer.LandSurface surface = EnumContainer.LandSurface.FLAT;

    public LandArena(){
        this(DEFAULT_LENGTH,DEFAULT_MAX_RACER);
    }

    /**
     * @param length The Length of Race.
     * @param maxRacers Amount of Max players who can race.
     */
    public LandArena(double length, int maxRacers){
        super(length, maxRacers, DEFAULT_FRICTION);
        setArenaType(EnumContainer.ArenaType.LAND_ARENA);
        coverage=EnumContainer.Coverage.GRASS;
        surface=EnumContainer.LandSurface.FLAT;

    }


    /**
     * @param coverage Characteristic of the Arena.
     */
    public void setCoverage(EnumContainer.Coverage coverage) {
        this.coverage = coverage;
    }

    /**
     * @param surface Characteristic of the Arena.
     */
    public void setSurface(EnumContainer.LandSurface surface) {
        this.surface = surface;
    }
}
