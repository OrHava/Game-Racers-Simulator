package game.arenas.naval;

import game.arenas.Arena;
import utilities.EnumContainer;

public class NavalArena extends Arena {
    private final static double DEFAULT_FRICTION=0.7;
    private final static int DEFAULT_MAX_RACER=5;
    private  final static int DEFAULT_LENGTH=1000;

    private EnumContainer.Water water = EnumContainer.Water.SWEET;
    private EnumContainer.WaterSurface surface = EnumContainer.WaterSurface.FLAT;
    private EnumContainer.Body Body = EnumContainer.Body.LAKE;

    public NavalArena(){
        this(DEFAULT_LENGTH,DEFAULT_MAX_RACER);
    }

    public NavalArena(double length, int maxRacers){
        super(length, maxRacers, DEFAULT_FRICTION);
        setArenaType(EnumContainer.ArenaType.NAVAL_ARENA);

        Body=EnumContainer.Body.LAKE;
        surface=EnumContainer.WaterSurface.FLAT;
        water=EnumContainer.Water.SWEET;
    }


    public void setWater(EnumContainer.Water water) {
        this.water = water;
    }

    public void setSurface(EnumContainer.WaterSurface surface) {
        this.surface = surface;
    }

    public void setBody(EnumContainer.Body body) {
        Body = body;
    }
}
