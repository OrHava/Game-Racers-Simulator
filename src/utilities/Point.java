package utilities;
/** @author Or Hava 208418483
 */
public class Point {
    private static final int MAX_X = 1000000;
    private static final int MIN_X = 0;
    private static final int MAX_Y = 800;
    private static final int MIN_Y = 0;

    private double x;
    private double y;

    /**
     * @param x
     * @param y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0, 0);
    }

    /**
     * @param other
     */
    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    /**
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
