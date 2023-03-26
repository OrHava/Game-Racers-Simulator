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

    public LandArena(double length, int maxRacers){
        super(length, maxRacers, DEFAULT_FRICTION);
        coverage=EnumContainer.Coverage.GRASS;
        surface=EnumContainer.LandSurface.FLAT;

    }


    public void setCoverage(EnumContainer.Coverage coverage) {
        this.coverage = coverage;
    }

    public void setSurface(EnumContainer.LandSurface surface) {
        this.surface = surface;
    }
}
