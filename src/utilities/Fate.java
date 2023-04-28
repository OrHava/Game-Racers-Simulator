package utilities;

import java.util.Random;
/** @author Or Hava 208418483
 */
public class Fate {

	private static Random rand = new Random();

	/**
	 * @return
	 */
	public static boolean breakDown(double failureProbability) {
		return rand.nextFloat() < failureProbability;
	}

	/**
	 * @return
	 */
	public static boolean generateFixable() {

		return true;
	}


	/**
	 * @return
	 */
	public static Mishap generateMishap() {
		return new Mishap(generateFixable(), generateTurns(), generateReduction());
	}

	/**
	 * @return
	 */
	private static float generateReduction() {
		return rand.nextFloat();
	}

	/**
	 * @return
	 */
	private static int generateTurns() {
		return rand.nextInt(5) + 1;
	}

	/**
	 * @param seed
	 */
	public static void setSeed(int seed) {
		rand.setSeed(seed);
	}

}
