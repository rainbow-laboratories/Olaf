package org.rainbowlabs.discordbot.olaf.utils;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Variable class to store constants and other stuff
 * @author Christoph Pelzer
 */
public final class VARIABLES {
    public static final String TOKEN = "NTUxMDQxNTc2ODYwOTA5NTY4.XgEd-Q.ZgOkC9-UQpoE5H0Dtz0EizrxmOg";
    private static Logger LOGGER = Logger.getLogger(VARIABLES.class.getName());
    private static String prefix = "o!";
    private static boolean replaceSilasComment = true;

    private VARIABLES() {}

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String PREFIX) {
        LOGGER.log(Level.INFO, "Trying to set the prefix to: {0}", PREFIX);
        VARIABLES.prefix = PREFIX;
    }

    public static void setReplaceSilasComment(boolean replace) {
        replaceSilasComment = replace;
    }

    public static boolean getReplaceSilasComment() {
        return replaceSilasComment;
    }
}
