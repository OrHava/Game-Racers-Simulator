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

    /**
     * @param length The Length of Race.
     * @param maxRacers Amount of Max players who can race.
     */
    public NavalArena(double length, int maxRacers){
        super(length, maxRacers, DEFAULT_FRICTION);
        setArenaType(EnumContainer.ArenaType.NAVAL_ARENA);

        Body=EnumContainer.Body.LAKE;
        surface=EnumContainer.WaterSurface.FLAT;
        water=EnumContainer.Water.SWEET;
    }


    /**
     * @param water Characteristic of the Arena.
     */
    public void setWater(EnumContainer.Water water) {
        this.water = water;
    }

    /**
     * @param surface Characteristic of the Arena.
     */
    public void setSurface(EnumContainer.WaterSurface surface) {
        this.surface = surface;
    }

    /**
     * @param body Characteristic of the Arena.
     */
    public void setBody(EnumContainer.Body body) {
        Body = body;
    }
}
