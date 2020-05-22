package org.rainbowlabs.discordbot.olaf.utils;


import java.util.logging.Logger;

/**
 * Variable class to store constants and other stuff
 * @author Christoph Pelzer
 */
public final class VARIABLES {
    //public static final String TOKEN = "NTAyOTExMTI0OTMzNzA1NzU2.XkhPaw.tjjL42Mla43_dRHrjBYNGw7a-ZQ";
    //public static final String TOKEN = "NTUxMDQxNTc2ODYwOTA5NTY4.XjSLLw.fs7V0XwuGu-xvAxKR14kpW_44LU"; // Dev
    public static final String TOKEN = "NzA3Njk1NzIzNzY3OTIyNzg5.XrMjHA.PfGBXEPGug7UJLqZpK7Wu_J2OGM";
    private static Logger logger = Logger.getLogger(VARIABLES.class.getName());
    private static boolean replaceSilasComment = true;

    private VARIABLES() {}

    public static void setReplaceSilasComment(boolean replace) {
        replaceSilasComment = replace;
    }

    public static boolean getReplaceSilasComment() {
        return replaceSilasComment;
    }
}
