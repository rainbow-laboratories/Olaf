package org.rainbowlabs.discordbot.olaf.listener;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.rainbowlabs.discordbot.spring.application.SpringContext;
import org.rainbowlabs.discordbot.spring.persistence.entities.Server;
import org.rainbowlabs.discordbot.spring.persistence.repositories.ServerRepository;

import java.util.Optional;

/**
 * Ready Listener, On productive manner this class prints out when the bot is ready to use.
 * @author Christoph Pelzer
 */
@Slf4j
public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        ServerRepository serverRepository = SpringContext.getBean(ServerRepository.class);
        for(Guild g : event.getJDA().getGuilds()) {
            log.debug("Servers:\n\t{}\n\t{}", g.getName(), g.getId());

            Optional<Server> serverOptional = serverRepository.findById(g.getIdLong());
            if (serverOptional.isEmpty()) {
                Server server = new Server(g.getIdLong(), g.getName(), "o!");
                serverRepository.save(server);
                log.info("Server has been initialized in the database. Server name: {}", g.getName());
            }
            //g.getTextChannels().get(0).sendMessage("I'm Olaf.").queue();
        }
    }
}
