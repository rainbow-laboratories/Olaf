package org.rainbowlabs.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * Command class needed to create the command.
 * @author Christoph Pelzer
 */
public class CMDRegister implements Command {

    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }

    public void action(ArrayList<String> args, MessageReceivedEvent event) {
        event.getChannel().sendMessage(event.getAuthor().getName() + " tried to register but that wasn't possible... Olaf sorry").queue();
    }

    public void executed(boolean sucess, MessageReceivedEvent event) {

    }

    public String help() {
        return null;
    }
}
