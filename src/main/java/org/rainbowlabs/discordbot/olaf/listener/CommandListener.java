package org.rainbowlabs.discordbot.olaf.listener;

import lombok.extern.slf4j.Slf4j;
import org.rainbowlabs.discordbot.olaf.handler.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.rainbowlabs.discordbot.olaf.utils.VARIABLES;
import org.rainbowlabs.discordbot.spring.application.SpringContext;
import org.rainbowlabs.discordbot.spring.persistence.entities.Server;
import org.rainbowlabs.discordbot.spring.persistence.repositories.ServerRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command Listener which reacts on every message recieved and checks if its a command
 * @author Christoph Pelzer
 */
@Slf4j
public class CommandListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        ServerRepository serverRepository = SpringContext.getBean(ServerRepository.class);
        Server eventServer = serverRepository
                .findById(event
                        .getGuild()
                        .getIdLong())
                .get();
        log.debug("EventServer prefix: {}", eventServer.getPrefix());
        if((event.getMessage().getContentRaw().startsWith(eventServer.getPrefix()) &&
                !event.getMessage().getAuthor().getId().equals(
                        event.getJDA().getSelfUser().getId()))) {
            log.info("Command {} received", event.getMessage().getContentRaw());
            CommandHandler
                    .getCommandHandler()
                    .parse(event
                            .getMessage()
                            .getContentRaw(), event, eventServer
                            .getPrefix())
                    .handleCommand();
        }
    }


}
