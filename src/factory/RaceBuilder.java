
 package factory;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.arenas.Arena;
import game.racers.Racer;
import utilities.EnumContainer.Color;


/** @author Or Hava 208418483
   */
public class RaceBuilder
{
    private static RaceBuilder instance;


    /**
     * @return instance
     */
    public static RaceBuilder getInstance()
    {
        if(instance==null)
            instance=new RaceBuilder();
        return instance;
    }


    /**
     * Creates a new instance of a class specified by the string {@code arenaType},
     * using a constructor that takes a {@code double} and an {@code int} as parameters.
     *
     * @param arenaType the name of the class to load
     * @param length the {@code double} parameter to pass to the constructor
     * @param maxRacers the {@code int} parameter to pass to the constructor
     * @return a new instance of the specified class, cast to the {@code Arena} class
     * @throws ClassNotFoundException if the class specified by {@code arenaType} cannot be found
     * @throws NoSuchMethodException if the class specified by {@code arenaType} does not have a constructor
     *         that takes a {@code double} and an {@code int} as parameters
     * @throws IllegalAccessException if the constructor is not accessible
     * @throws InstantiationException if an instance of the class cannot be created
     * @throws InvocationTargetException if the constructor throws an exception
     */
    public Arena buildArena(String arenaType,double length,int maxRacers) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        ClassLoader classLoader=ClassLoader.getSystemClassLoader();
        Class<?> Class;
        Constructor<?> constructor;
        Class=classLoader.loadClass(arenaType);
        constructor=Class.getConstructor(double.class,int.class);
        return (Arena) constructor.newInstance(length,maxRacers);

    }

    /**

     * Constructs and returns a Racer object based on the given parameters.
     * @param racerType a String representing the type of Racer to be created.
     * @param name a String representing the name of the Racer.
     * @param maxSpeed a double representing the maximum speed of the Racer.
     * @param acceleration a double representing the acceleration of the Racer.
     * @param color a Color representing the color of the Racer.
     * @return a Racer object constructed with the given parameters.
     * @throws ClassNotFoundException if the specified class cannot be found.
     * @throws NoSuchMethodException if the specified constructor cannot be found.
     * @throws SecurityException if a security violation occurs.
     * @throws InstantiationException if the class cannot be instantiated.
     * @throws IllegalAccessException if the class or its constructor is not accessible.
     * @throws IllegalArgumentException if the given arguments are incompatible with the constructor.
     * @throws InvocationTargetException if the constructor throws an exception.
     */


    public Racer buildRacer(String racerType,String name,double maxSpeed,double acceleration,utilities.EnumContainer.Color color) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        ClassLoader classLoader=ClassLoader.getSystemClassLoader();
        Class<?> Class;
        Constructor<?> constructor;
        Class=classLoader.loadClass(racerType);
        constructor=Class.getConstructor(String.class,double.class,double.class,Color.class);
        return (Racer) constructor.newInstance(name,maxSpeed,acceleration,color);
    }

    /**

     * Builds a wheeled racer based on the provided parameters.
     * @param racerType The type of wheeled racer to build.
     * @param name The name of the racer.
     * @param maxSpeed The maximum speed of the racer.
     * @param acceleration The acceleration of the racer.
     * @param color The color of the racer.
     * @param wheels The number of wheels on the racer.
     * @return A Racer object representing the newly built wheeled racer.
     * @throws ClassNotFoundException If the specified racer type cannot be found.
     * @throws NoSuchMethodException If the specified constructor cannot be found.
     * @throws SecurityException If there is a security violation while accessing the constructor.
     * @throws InstantiationException If there is an error while instantiating the racer object.
     * @throws IllegalAccessException If there is an error accessing the racer object.
     * @throws IllegalArgumentException If the specified arguments are invalid.
     * @throws InvocationTargetException If there is an error while invoking the constructor.
     */
    public Racer buildWheeledRacer(String racerType,String name,double maxSpeed,double acceleration,utilities.EnumContainer.Color color,int wheels) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        ClassLoader classLoader=ClassLoader.getSystemClassLoader();
        Class<?> Class;
        Constructor<?> constructor;
        Class=classLoader.loadClass(racerType);
        constructor=Class.getConstructor(String.class,double.class,double.class,Color.class,int.class);
        return (Racer) constructor.newInstance(name,maxSpeed,acceleration,color,wheels);
    }
}
