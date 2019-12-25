package org.rainbowlabs.discordbot.olaf.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.rainbowlabs.discordbot.olaf.utils.VARIABLES;

import java.util.ArrayList;

public class CMDSilas implements Command {
    @Override
    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(ArrayList<String> args, MessageReceivedEvent event) {
        if (args.isEmpty()) {
            VARIABLES.setReplaceSilasComment(!VARIABLES.getReplaceSilasComment());
            this.sendHelp(event);
        } else if ("status".equals(args.get(0))) {
            this.sendHelp(event);
        } else {
            event.getTextChannel().sendMessage("Sry I dunno what to do with that").queue();
            this.sendHelp(event);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        if (VARIABLES.getReplaceSilasComment()) {
            return "Silas' comments will be replaced ";
        } else {
            return "Silas' comments will not be replaced ";
        }
    }

    private void sendHelp(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("> " + help()).queue();
    }
}
