package factory;

import game.arenas.Arena;
import game.racers.Racer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class RacingClassesFinder {
	private static String GAME_PACKAGE = "game";
	private static String GAME_PACKAGE_DIR = "src/game";
	private static RacingClassesFinder instance;

	public static RacingClassesFinder getInstance() {
		if (instance == null) {
			instance = new RacingClassesFinder();
		}
		return instance;
	}

	private List<Class<?>> classList;
	private List<Class<?>> racersList;
	private List<Class<?>> arenasList;

	private RacingClassesFinder() {
		try {
			this.classList = this.loadClasses(new File(GAME_PACKAGE_DIR), GAME_PACKAGE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.arenasList = loadArenas();
		this.racersList = loadRacers();
	}

	public List<Class<?>> getArenasList() {
		return arenasList;
	}

	public List<String> getArenasNamesList() {
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.arenasList) {
			String s = c.getName();
			list.add(s.substring(s.lastIndexOf('.') + 1));
		}
		return list;
	}

	public List<Class<?>> getRacersList() {
		return racersList;
	}

	public List<String> getRacersNamesList() {
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.racersList) {
			String s = c.getName();
			list.add(s.substring(s.lastIndexOf('.') + 1));
		}
		return list;
	}

	private List<Class<?>> loadArenas() {
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> cls : classList) {
			if (Arena.class.isAssignableFrom(cls) && (Modifier.isAbstract(cls.getModifiers()) == false)) {

				list.add(cls);
			}
		}
		return list;
	}

	private List<Class<?>> loadClasses(File directory, String packageName)
			throws ClassNotFoundException, FileNotFoundException {
		List<Class<?>> list = new ArrayList<Class<?>>();

		if (!directory.exists()) {
			throw new FileNotFoundException();
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				list.addAll(loadClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".java")) {
				list.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 5)));
			}
		}
		return list;
	}

	private List<Class<?>> loadRacers() {
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> cls : classList) {
			if (Racer.class.isAssignableFrom(cls) && (Modifier.isAbstract(cls.getModifiers()) == false)) {
				list.add(cls);
			}
		}
		return list;
	}

}
