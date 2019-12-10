package org.rainbowlabs.olaf.handler;

import org.junit.Assert;
import org.junit.Test;

public class CommandHandlerTest {
    @Test
    public void testGetCommandHandlerInstance() {
        Assert.assertNotNull(CommandHandler.getCommandHandler());
    }
}
