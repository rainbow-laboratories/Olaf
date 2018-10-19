package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * The interface to determine how a command has to look like
 * @author Christoph Pelzer
 */
public interface Command {
    public boolean called(ArrayList<String> args, MessageReceivedEvent event);
    public void action(ArrayList<String> args, MessageReceivedEvent event);
    public void executed(boolean sucess, MessageReceivedEvent event);
    public String help();
}
