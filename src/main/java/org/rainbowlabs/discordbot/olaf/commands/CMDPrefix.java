package org.rainbowlabs.discordbot.olaf.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.rainbowlabs.discordbot.olaf.utils.VARIABLES;
import org.rainbowlabs.discordbot.spring.application.SpringContext;
import org.rainbowlabs.discordbot.spring.persistence.entities.Server;
import org.rainbowlabs.discordbot.spring.persistence.repositories.ServerRepository;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command class needed to create the command.
 * @author Christoph Pelzer
 */
public class CMDPrefix implements Command {
    private static Logger logger = Logger.getLogger(CMDPrefix.class.getName());
    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }

    public void action(ArrayList<String> args, MessageReceivedEvent event) {
        ServerRepository serverRepository = SpringContext.getBean(ServerRepository.class);
        Server eventServer = serverRepository.findById(event.getGuild().getIdLong()).get();
        if (args.isEmpty()) {
            event.getTextChannel().sendMessage("Your current prefix is: " + eventServer.getPrefix()).queue();
        } else {
            event.getTextChannel().sendMessage("Old Prefix was: " + eventServer.getPrefix()).queue();
            eventServer.setPrefix(args.get(0));
            serverRepository.save(eventServer);
            Server updatedServer = serverRepository.findById(event.getGuild().getIdLong()).get();
            event.getTextChannel().sendMessage("New Prefix is: " + updatedServer.getPrefix()).queue();
        }
    }

    public void executed(boolean sucess, MessageReceivedEvent event) {
        String[] strings = new String[2];
        strings[0] = event.getMember().getNickname();
        strings[1] = event.getAuthor().getId();
        logger.log(Level.INFO, "Command '!prefix' wurde ausgeführt von {0} mit der ID: {1}", strings);

    }

    public String help() {
        return "Try o!prefix ? (NOTE: NOT COMPLETELY IMPLEMATED YET)";
    }
}
