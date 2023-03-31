package game.arenas.air;

import game.arenas.Arena;
import utilities.EnumContainer;

public class AerialArena extends Arena  {
   private final static double DEFAULT_FRICTION =0.4;
   private  final static int DEFAULT_MAX_RACERS =6;
   private  final static int DEFAULT_LENGTH=1500;
   private EnumContainer.Vision vision = EnumContainer.Vision.SUNNY;
   private EnumContainer.Weather weather = EnumContainer.Weather.DRY;
   private EnumContainer.Height height = EnumContainer.Height.HIGH;
   private EnumContainer.Wind wind = EnumContainer.Wind.HIGH;

   public AerialArena() {
      this(DEFAULT_LENGTH, DEFAULT_MAX_RACERS);
   }
   public AerialArena(double length, int maxRacers) {
      super(length, maxRacers, DEFAULT_FRICTION);
      setArenaType(EnumContainer.ArenaType.AERIAL_ARENA);
      vision = EnumContainer.Vision.SUNNY;
      weather = EnumContainer.Weather.DRY;
      height = EnumContainer.Height.HIGH;
      wind = EnumContainer.Wind.HIGH;
   }

   public void setVision(EnumContainer.Vision vision) {
      this.vision = vision;
   }

   public void setWeather(EnumContainer.Weather weather) {
      this.weather = weather;
   }

   public void setHeight(EnumContainer.Height height) {
      this.height = height;
   }

   public void setWind(EnumContainer.Wind wind) {
      this.wind = wind;
   }
}
