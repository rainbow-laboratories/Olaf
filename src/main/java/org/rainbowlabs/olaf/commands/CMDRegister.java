package org.rainbowlabs.olaf.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command class needed to create the command.
 * @author Christoph Pelzer
 */
public class CMDRegister implements Command {
    private static Logger logger = Logger.getLogger(CMDRegister.class.getName());
    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }

    public void action(ArrayList<String> args, MessageReceivedEvent event) {
        event.getChannel().sendMessage(event.getAuthor().getName() + " tried to register but that wasn't possible... Olaf sorry").queue();
    }

    public void executed(boolean sucsess, MessageReceivedEvent event) {
        logger.log(Level.INFO, "Executed CMDRegister with success");
    }

    public String help() {
        return null;
    }
}
