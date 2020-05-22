package org.rainbowlabs.discordbot.olaf.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * The interface to determine how a command has to look like
 * @author Christoph Pelzer
 */
public interface Command {
    boolean called(ArrayList<String> args, MessageReceivedEvent event);
    void action(ArrayList<String> args, MessageReceivedEvent event);
    void executed(boolean success, MessageReceivedEvent event);
    String help();
}
