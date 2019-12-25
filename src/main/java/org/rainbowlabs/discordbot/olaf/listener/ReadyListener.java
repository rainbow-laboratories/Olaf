package org.rainbowlabs.discordbot.olaf.listener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.rainbowlabs.discordbot.spring.application.SpringContext;
import org.rainbowlabs.discordbot.spring.persistence.entities.Server;
import org.rainbowlabs.discordbot.spring.persistence.repositories.ServerRepository;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ready Listener, On productive manner this class prints out when the bot is ready to use.
 * @author Christoph Pelzer
 */
public class ReadyListener extends ListenerAdapter {
    private static final Logger LOGGER = Logger.getLogger(ReadyListener.class.getName());
    public void onReady(ReadyEvent event) {
        ServerRepository serverRepository = SpringContext.getBean(ServerRepository.class);
        for(Guild g : event.getJDA().getGuilds()) {
            System.out.println("Servers: " + g.getName() + "\n\tid:" + g.getId());

            Optional<Server> serverOptional = serverRepository.findById(g.getIdLong());
            if (serverOptional.isEmpty()) {
                Server server = new Server(g.getIdLong(), g.getName(), "o!");
                Server serverPersisted = serverRepository.save(server);
                LOGGER.log(Level.INFO, "Server has been saved: {}", serverPersisted);
            }
            g.getTextChannels().get(0).sendMessage("I'm Olaf.").queue();
        }
    }
}
