package utilities;

import java.text.DecimalFormat;

public class Mishap {
    private boolean fixable;
    private int turnsToFix;
    private double reductionFactor;

    /**
     * @param fixable
     * @param turnsToFix
     * @param reductionFactor
     */
    public Mishap(boolean fixable, int turnsToFix, double reductionFactor) {
        this.fixable = fixable;
        this.turnsToFix = turnsToFix;
        this.reductionFactor = reductionFactor;
    }


    public Mishap() {

    }

    /**
     * @return
     */
    public boolean isFixable() {

        return fixable;
    }

    /**
     * @param fixable
     */
    public void setFixable(boolean fixable) {
        this.fixable = fixable;
    }

    /**
     * @return
     */
    public int getTurnsToFix() {
        return turnsToFix;
    }

    /**
     * @param turnsToFix
     */
    public void setTurnsToFix(int turnsToFix) {
        this.turnsToFix = turnsToFix;
    }

    /**
     * @return
     */
    public double getReductionFactor() {
        return reductionFactor;
    }

    /**
     * @param reductionFactor
     */
    public void setReductionFactor(double reductionFactor) {
        this.reductionFactor = reductionFactor;
    }

    /**
     *
     */
    public void nextTurn() {
        if (fixable && turnsToFix > 0) {
            turnsToFix--;
        }
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "(" + fixable + ", " + turnsToFix + ", " + df.format(reductionFactor) + ")";
    }
}
