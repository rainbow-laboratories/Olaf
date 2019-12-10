package org.rainbowlabs.olaf.listener;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Ready Listener, On productive manner this class prints out when the bot is ready to use.
 * @author Christoph Pelzer
 */
public class ReadyListener extends ListenerAdapter {
    public void onReady(ReadyEvent event) {
        for(Guild g : event.getJDA().getGuilds()) {
            System.out.println("Servers: " + g.getName() + "\n\tid:" + g.getId());
            //g.getTextChannels().get(0).sendMessage("I'm Olaf.").queue();
        }
    }
}
