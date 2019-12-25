package org.rainbowlabs.discordbot.handler;

import org.junit.Assert;
import org.junit.Test;
import org.rainbowlabs.discordbot.olaf.handler.CommandHandler;

public class CommandHandlerTest {
    @Test
    public void testGetCommandHandlerInstance() {
        Assert.assertNotNull(CommandHandler.getCommandHandler());
    }
}
