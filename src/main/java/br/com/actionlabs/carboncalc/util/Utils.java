package br.com.actionlabs.carboncalc.util;

public class Utils {
    /**
     * Round a double value to a specific number of decimal places
     *
     * @param value  the value to be rounded
     * @param places the number of decimal places. Accepts 0 or higher (0 for integer rounding)
     * @return the rounded value
     */
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
