package utilities;

/**
 * Contains all Enums for the game.
 * 
 * To set a field type: EnumContainer.Vision vision; To set a value: this.vision
 * = EnumContainer.Vision.Sunny
 * 
 * @author
 *
 */
public class EnumContainer {

	public static enum Vision {
		CLOUDS, SUNNY, FOG
	}
	public static enum Weather  {
		DRY, RAIN, SNOW
	}
	public static enum Height {
		LOW, MEDIUM, HIGH
	}
	public static enum Wind  {
		LOW, MEDIUM, HIGH
	}

	public static enum Water  {
	    SALTED,SWEET
	}
	public static enum WaterSurface {
		FLAT,WAVY
	}
	public static enum Body  {
		SEA,LAKE,RIVER,OCEAN
	}

	public static enum Coverage {
		SAND,GRASS,MUD
	}
	public static enum LandSurface  {
		 FLAT,MOUNTAIN
	}
	public static enum Color  {
		RED,GREEN,BLUE,BLACK,YELLOW
	}

}
