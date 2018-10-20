package de.cpelzer.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import de.cpelzer.utils.variables;

import java.util.ArrayList;

/**
 * Command class needed to create the command.
 * @author Christoph Pelzer
 */
public class CMDPrefix implements Command {
    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }

    public void action(ArrayList<String> args, MessageReceivedEvent event) {

        variables c = new variables();
        if (args.size() == 0) {
            event.getTextChannel().sendMessage("Your current prefix is: " + c.getPrefix()).queue();
        } else {
            event.getTextChannel().sendMessage("Old Prefix was: " + c.getPrefix()).queue();
            c.setPrefix(args.get(0));
            event.getTextChannel().sendMessage("New Prefix is: " + c.getPrefix()).queue();
        }
    }

    public void executed(boolean sucess, MessageReceivedEvent event) {
        System.out.println("[INFO] Command '!prefix' wurde ausgeführt von " + event.getMember().getNickname() + " mit der ID: " + event.getAuthor().getId());

    }

    public String help() {
        return "Try " + variables.prefix + "prefix ?";
    }
}
