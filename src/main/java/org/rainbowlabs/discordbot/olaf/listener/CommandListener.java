package org.rainbowlabs.discordbot.olaf.listener;

import org.rainbowlabs.discordbot.olaf.handler.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.rainbowlabs.discordbot.olaf.utils.VARIABLES;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command Listener which reacts on every message recieved and checks if its a command
 * @author Christoph Pelzer
 */
public class CommandListener extends ListenerAdapter {
    private Logger logger = Logger.getLogger(CommandListener.class.getName());
    public void onMessageReceived(MessageReceivedEvent event) {
        if((event.getMessage().getContentRaw().startsWith(VARIABLES.getPrefix()) &&
                event.getMessage().getAuthor().getId() !=
                        event.getJDA().getSelfUser().getId())) {
            logger.log(Level.INFO, "Command {0} received", event.getMessage().getContentRaw());
            CommandHandler.getCommandHandler().parse(event.getMessage().getContentRaw(), event).handleCommand();
        } else if (event.getAuthor().getName().matches("Silas770") && VARIABLES.getReplaceSilasComment()) {
            logger.log(Level.INFO, "{0} said something", event.getAuthor().getName());
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage("> Mutti sagt: " + event.getMessage().getContentRaw()).queue();
        } else if(event.getMessage().getMentionedMembers().size() > 0) {
            if(event.getMessage().getMentionedMembers().get(0).getUser().equals(event.getJDA().getSelfUser())) {
                logger.log(Level.INFO, "Message received with the bot in mention");
                String tempString = event.getMessage().getContentRaw();
                String newString = tempString.replace("<@" + event.getJDA().getSelfUser().getId() + "> ", VARIABLES.getPrefix());
                logger.log(Level.INFO, "Updated string to: {0}", newString);
                CommandHandler.getCommandHandler().parse(newString, event).handleCommand();
            }
        }
    }
}
