package org.rainbowlabs.olaf;

import org.junit.Assert;
import org.junit.Test;
import org.rainbowlabs.olaf.handler.CommandHandler;

public class OlafTest {
    @Test
    public void testAddCommands() {
        Olaf.addCommands();
        Assert.assertNotNull(CommandHandler.getCommandHandler());
    }
}
