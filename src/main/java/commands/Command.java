package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public interface Command {
    public boolean called(ArrayList<String> args, MessageReceivedEvent event);
    public void action(ArrayList<String> args, MessageReceivedEvent event);
    public void executed(boolean sucess, MessageReceivedEvent event);
    public String help();
}
