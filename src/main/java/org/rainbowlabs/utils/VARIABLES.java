package org.rainbowlabs.utils;

/**
 * Variable class to store constants and other stuff
 * @author Christoph Pelzer
 */
public final class VARIABLES {
    public static final String TOKEN = "";
    private static String prefix = "!";

    public VARIABLES() {}

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String PREFIX) {
        VARIABLES.prefix = PREFIX;
    }
}
