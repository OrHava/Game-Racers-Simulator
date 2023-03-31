package utilities;

import java.text.DecimalFormat;

public class Mishap {
    private boolean fixable;
    private int turnsToFix;
    private double reductionFactor;

    public Mishap(boolean fixable, int turnsToFix, double reductionFactor) {
        this.fixable = fixable;
        this.turnsToFix = turnsToFix;
        this.reductionFactor = reductionFactor;
    }

    public boolean isFixable() {
        return fixable;
    }

    public void setFixable(boolean fixable) {
        this.fixable = fixable;
    }

    public int getTurnsToFix() {
        return turnsToFix;
    }

    public void setTurnsToFix(int turnsToFix) {
        this.turnsToFix = turnsToFix;
    }

    public double getReductionFactor() {
        return reductionFactor;
    }

    public void setReductionFactor(double reductionFactor) {
        this.reductionFactor = reductionFactor;
    }

    public void nextTurn() {
        if (fixable && turnsToFix > 0) {
            turnsToFix--;
        }
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "(" + fixable + ", " + turnsToFix + ", " + df.format(reductionFactor) + ")";
    }
}
