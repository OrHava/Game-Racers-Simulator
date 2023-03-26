/**
 * 
 */
package utilities;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import utilities.Point;

import factory.RaceBuilder;
import game.arenas.Arena;
import game.arenas.air.AerialArena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.arenas.land.LandArena;
import game.arenas.naval.NavalArena;
import game.racers.Racer;
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.Bicycle;
import game.racers.land.Car;
import game.racers.land.Horse;
import game.racers.naval.RowBoat;
import game.racers.naval.SpeedBoat;
import utilities.EnumContainer.Color;

/**
 * @author
 *
 */
public class Program {

	private static Arena arena;
	private static RaceBuilder builder = RaceBuilder.getInstance();;
	private static ArrayList<Racer> racers;

	private static void addRacersToArena() {
		for (Racer racer : racers) {
			try {
				arena.addRacer(racer);
			} catch (RacerLimitException e) {
				System.out.println("[Error] " + e.getMessage());
			} catch (RacerTypeException e) {
				System.out.println("[Error] " + e.getMessage());
			}
		}
	}

	private static void initAirRace() {
		try {
			arena = builder.buildArena("game.arenas.air.AerialArena", 1450, 4);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			System.out.println("Unable to build arena!");
			arena = new AerialArena();
		}
		racers = new ArrayList<>();
		try {
			racers.add(builder.buildWheeledRacer("game.racers.air.Airplane", "Bob", 220, 10, Color.BLUE, 3));
			racers.add(builder.buildWheeledRacer("game.racers.air.Airplane", "John", 175, 20, Color.BLUE, 3));
			racers.add(builder.buildWheeledRacer("game.racers.air.Airplane", "Frank", 180, 15, Color.BLUE, 3));
			racers.add(builder.buildRacer("game.racers.air.Helicopter", "Matt", 230, 8, Color.RED));
			racers.add(builder.buildWheeledRacer("game.racers.land.Car", "car", 15, 1, Color.GREEN, 3));
			racers.add(builder.buildRacer("game.racers.air.Helicopter", "Alby", 200, 8, Color.BLUE));
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}

		addRacersToArena();
	}

	private static void initLandRace() {
		try {
			arena = builder.buildArena("game.arenas.land.LandArena", 1450, 8);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			System.out.println("Unable to build arena!");
			arena = new LandArena();
		}
		racers = new ArrayList<>();
		try {
			racers.add(builder.buildWheeledRacer("game.racers.land.Car", "Bob", 220, 10, Color.BLUE, 4));
			racers.add(builder.buildWheeledRacer("game.racers.land.Car", "John", 175, 20, Color.BLUE, 4));
			racers.add(builder.buildRacer("game.racers.land.Horse", "Frank", 180, 15, Color.BLUE));
			racers.add(builder.buildRacer("game.racers.land.Horse", "Matt", 230, 8, Color.RED));
			racers.add(builder.buildWheeledRacer("game.racers.land.Bicycle", "Timmy", 15, 1, Color.GREEN, 3));
			racers.add(builder.buildRacer("game.racers.air.Helicopter", "Alby", 200, 8, Color.BLUE));
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}

		addRacersToArena();
	}

	private static void initNavalRace() {
		try {
			arena = builder.buildArena("game.arenas.naval.NavalArena", 1225, 2);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			System.out.println("Unable to build arena!");
			arena = new NavalArena();
		}
		racers = new ArrayList<>();
		try {
			racers.add(builder.buildRacer("game.racers.naval.RowBoat", "Bob", 220, 10, Color.BLUE));
			racers.add(builder.buildRacer("game.racers.naval.SpeedBoat", "John", 175, 20, Color.BLUE));
			racers.add(builder.buildRacer("game.racers.naval.RowBoat", "Matt", 230, 8, Color.RED));
			racers.add(builder.buildWheeledRacer("game.racers.land.Car", "car", 15, 1, Color.GREEN, 3));
			racers.add(builder.buildRacer("game.racers.land.Car", "car", 15, 1, Color.GREEN)); // intentional exception!
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		addRacersToArena();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Fate.setSeed(477734503); // to get same "random" results every run;
		////////////////////////////////////////////
		testDefaults();
		System.out.println("----------");
		////////////////////////////////////////////
		System.out.println("New Air Race");
		initAirRace();
		arena.initRace();
		startRace();
		arena.showResults();
		////////////////////////////////////////////
		System.out.println("----------");
		System.out.println("New Land Race");
		initLandRace();
		arena.initRace();
		startRace();
		arena.showResults();
		////////////////////////////////////////////
		System.out.println("----------");
		System.out.println("New Naval Race");
		initNavalRace();
		arena.initRace();
		startRace();
		arena.showResults();

	}

	private static void startRace() {
		System.out.println("Introduction: ");
		for (Racer racer : arena.getActiveRacers())
			racer.introduce();
		System.out.println("Strat Race!");
		while (arena.hasActiveRacers()) {
			arena.playTurn();
		}
		System.out.println("Race Compleated!");
	}

	private static void testDefaults() {
		System.out.println("Testing default valus and introduction.");
		(new Car()).introduce();
		(new Horse()).introduce();
		(new Bicycle()).introduce();
		(new Helicopter()).introduce();
		(new Airplane()).introduce();
		(new SpeedBoat()).introduce();
		(new RowBoat()).introduce();
		System.out.println("End of test.");
	}

}

// output
/*
Testing default valus and introduction.
[Car] name: Car #1, SerialNumber: 1, maxSpeed: 400.0, acceleration: 20.0, color: RED, Number of Wheels: 4, Engine Type: MOUNTAIN
[Horse] name: Horse #2, SerialNumber: 2, maxSpeed: 50.0, acceleration: 3.0, color: BLACK, Breed: THOROUGHBRED
[Bicycle] name: Bicycle #3, SerialNumber: 3, maxSpeed: 270.0, acceleration: 10.0, color: GREEN, Number of Wheels: 2, Bicycle Type: MOUNTAIN
[Helicopter] name: Helicopter #4, SerialNumber: 4, maxSpeed: 400.0, acceleration: 50.0, color: BLUE
[Airplane] name: Airplane #5, SerialNumber: 5, maxSpeed: 885.0, acceleration: 100.0, color: BLACK, Number of Wheels: 3
[SpeedBoat] name: SpeedBoat #6, SerialNumber: 6, maxSpeed: 170.0, acceleration: 5.0, color: RED, Type: SKULLING, Team: SINGLE
[RowBoat] name: RowBoat #7, SerialNumber: 7, maxSpeed: 75.0, acceleration: 10.0, color: RED, Type: SKULLING, Team: SINGLE
End of test.
----------
New Air Race
[Error] Invalid Racer of type "Car" for Aerial arena.
[Error] Arena is full! (4 active racers exist). racer #13 was not added
Introduction: 
[Airplane] name: Bob, SerialNumber: 8, maxSpeed: 220.0, acceleration: 10.0, color: BLUE, Number of Wheels: 3
[Airplane] name: John, SerialNumber: 9, maxSpeed: 175.0, acceleration: 20.0, color: BLUE, Number of Wheels: 3
[Airplane] name: Frank, SerialNumber: 10, maxSpeed: 180.0, acceleration: 15.0, color: BLUE, Number of Wheels: 3
[Helicopter] name: Matt, SerialNumber: 11, maxSpeed: 230.0, acceleration: 8.0, color: RED
Strat Race!
Frank Has a new mishap! (false, 4, 0.90)
Matt Has a new mishap! (true, 4, 0.62)
John Has a new mishap! (false, 5, 0.38)
Bob Has a new mishap! (false, 3, 1.00)
Matt Has a new mishap! (false, 3, 0.62)
Race Compleated!
#0 -> name: Frank, SerialNumber: 10, maxSpeed: 180.0, acceleration: 15.0, color: BLUE, Number of Wheels: 3
#1 -> name: Bob, SerialNumber: 8, maxSpeed: 220.0, acceleration: 10.0, color: BLUE, Number of Wheels: 3
#2 -> name: John, SerialNumber: 9, maxSpeed: 175.0, acceleration: 20.0, color: BLUE, Number of Wheels: 3
#3 -> name: Matt, SerialNumber: 11, maxSpeed: 230.0, acceleration: 8.0, color: RED
----------
New Land Race
[Error] Invalid Racer of type "Helicopter" for Land arena.
Introduction: 
[Car] name: Bob, SerialNumber: 14, maxSpeed: 220.0, acceleration: 10.0, color: BLUE, Number of Wheels: 4, Engine Type: MOUNTAIN
[Car] name: John, SerialNumber: 15, maxSpeed: 175.0, acceleration: 20.0, color: BLUE, Number of Wheels: 4, Engine Type: MOUNTAIN
[Horse] name: Frank, SerialNumber: 16, maxSpeed: 180.0, acceleration: 15.0, color: BLUE, Breed: THOROUGHBRED
[Horse] name: Matt, SerialNumber: 17, maxSpeed: 230.0, acceleration: 8.0, color: RED, Breed: THOROUGHBRED
[Bicycle] name: Timmy, SerialNumber: 18, maxSpeed: 15.0, acceleration: 1.0, color: GREEN, Number of Wheels: 3, Bicycle Type: MOUNTAIN
Strat Race!
Bob Has a new mishap! (false, 4, 0.81)
John Has a new mishap! (false, 2, 0.16)
Matt Has a new mishap! (false, 2, 0.31)
Timmy Has a new mishap! (false, 1, 0.94)
Frank Has a new mishap! (true, 1, 0.64)
Frank Has a new mishap! (true, 5, 0.90)
Frank Has a new mishap! (false, 3, 0.47)
Race Compleated!
#0 -> name: Frank, SerialNumber: 16, maxSpeed: 180.0, acceleration: 15.0, color: BLUE, Breed: THOROUGHBRED
#1 -> name: Bob, SerialNumber: 14, maxSpeed: 220.0, acceleration: 10.0, color: BLUE, Number of Wheels: 4, Engine Type: MOUNTAIN
#2 -> name: John, SerialNumber: 15, maxSpeed: 175.0, acceleration: 20.0, color: BLUE, Number of Wheels: 4, Engine Type: MOUNTAIN
#3 -> name: Matt, SerialNumber: 17, maxSpeed: 230.0, acceleration: 8.0, color: RED, Breed: THOROUGHBRED
#4 -> name: Timmy, SerialNumber: 18, maxSpeed: 15.0, acceleration: 1.0, color: GREEN, Number of Wheels: 3, Bicycle Type: MOUNTAIN
----------
New Naval Race
[Error] Arena is full! (2 active racers exist). racer #22 was not added
[Error] Invalid Racer of type "Car" for Naval arena.
Introduction: 
java.lang.NoSuchMethodException: game.racers.land.Car.<init>(java.lang.String, double, double, utilities.EnumContainer$Color)
	at java.lang.Class.getConstructor0(Unknown Source)
	at java.lang.Class.getConstructor(Unknown Source)
	at factory.RaceBuilder.buildRacer(RaceBuilder.java:43)
	at utilities.Program.initNavalRace(Program.java:110)
	at utilities.Program.main(Program.java:142)
[RowBoat] name: Bob, SerialNumber: 20, maxSpeed: 220.0, acceleration: 10.0, color: BLUE, Type: SKULLING, Team: SINGLE
[SpeedBoat] name: John, SerialNumber: 21, maxSpeed: 175.0, acceleration: 20.0, color: BLUE, Type: SKULLING, Team: SINGLE
Strat Race!
Bob Has a new mishap! (false, 3, 0.44)
John Has a new mishap! (false, 2, 0.04)
Race Compleated!
#0 -> name: Bob, SerialNumber: 20, maxSpeed: 220.0, acceleration: 10.0, color: BLUE, Type: SKULLING, Team: SINGLE
#1 -> name: John, SerialNumber: 21, maxSpeed: 175.0, acceleration: 20.0, color: BLUE, Type: SKULLING, Team: SINGLE
*/