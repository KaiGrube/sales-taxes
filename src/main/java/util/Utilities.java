package util;

import sales.Product;

public class Utilities {
    public static double roundTax(double value) {
        return Math.ceil(value * 20) / 20;
    }
    public static double roundPrice(double value) {
        return (double) Math.round(value * 100) / 100;
    }
}
