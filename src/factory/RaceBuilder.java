package factory;

import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer;

import java.lang.reflect.Constructor;

public class RaceBuilder {
    private static RaceBuilder instance = null;
    private ClassLoader classLoader;
    private Class<?> classObject;
    private Constructor<?> constructor;

    private RaceBuilder() {}

    public static synchronized RaceBuilder getInstance() {
        if (instance == null) {
            instance = new RaceBuilder();
        }
        return instance;
    }

    public Arena buildArena(String arenaType, double length, int maxRacers) throws Exception {


        classLoader = ClassLoader.getSystemClassLoader();
        classObject = classLoader.loadClass(arenaType);
        constructor = classObject.getConstructor(double.class, int.class);
        return (Arena) constructor.newInstance(length, maxRacers);
    }

    public Racer buildRacer(String racerType, String name, double maxSpeed, double acceleration, EnumContainer.Color color) throws Exception {
        classLoader = ClassLoader.getSystemClassLoader();
        classObject = classLoader.loadClass(racerType);
        constructor = classObject.getConstructor(String.class, double.class, double.class, EnumContainer.Color.class);
        return (Racer) constructor.newInstance(name, maxSpeed, acceleration, color);
    }



    public Racer buildWheeledRacer(String racerType, String name, double maxSpeed, double acceleration, EnumContainer.Color color, int numOfWheels) throws Exception {
        classLoader = ClassLoader.getSystemClassLoader();
        classObject = classLoader.loadClass( racerType);
        constructor = classObject.getConstructor(String.class, double.class, double.class, EnumContainer.Color.class, int.class);
        return (Racer) constructor.newInstance(name, maxSpeed, acceleration, color, numOfWheels);
    }
}
