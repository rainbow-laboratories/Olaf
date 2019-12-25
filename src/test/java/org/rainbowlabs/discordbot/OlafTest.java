package org.rainbowlabs.discordbot;

import org.junit.Assert;
import org.junit.Test;
import org.rainbowlabs.discordbot.olaf.Olaf;
import org.rainbowlabs.discordbot.olaf.handler.CommandHandler;

public class OlafTest {
    @Test
    public void testAddCommands() {
        Olaf.addCommands();
        Assert.assertNotNull(CommandHandler.getCommandHandler());
    }
}
