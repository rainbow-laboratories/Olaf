package de.cpelzer.listener;

import de.cpelzer.handler.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import de.cpelzer.utils.variables;

/**
 * Command Listener which reacts on every message recieved and checks if its a command
 * @author Christoph Pelzer
 */
public class CommandListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        if((event.getMessage().getContentRaw().startsWith(variables.prefix) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId())) {
            CommandHandler.handleCommand(CommandHandler.parser.parse(event.getMessage().getContentRaw(), event));
        } else if (event.getAuthor().getName().matches("Silas770")) {
            System.out.println(event.getAuthor().getName());
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage("Persilas der Seelachs sagt: " + event.getMessage().getContentRaw()).queue();
        } else if(event.getMessage().getMentionedMembers().size() > 0) {
            if(event.getMessage().getMentionedMembers().get(0).getUser().equals(event.getJDA().getSelfUser())) {
                String tempString = event.getMessage().getContentRaw();
                String newString = tempString.replace("<@" + event.getJDA().getSelfUser().getId() + "> ", variables.prefix);
                CommandHandler.handleCommand(CommandHandler.parser.parse(newString, event));
            }
        }
    }
}