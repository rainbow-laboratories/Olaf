package listener;

import handler.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.variables;

/**
 * Command Listener which reacts on every message recieved and checks if its a command
 * @author Christoph Pelzer
 */
public class CommandListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith(variables.prefix) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            CommandHandler.handleCommand(CommandHandler.parser.parse(event.getMessage().getContentRaw(), event));
        } else if (event.getAuthor().getName().matches("Silas770")) {
            System.out.println(event.getAuthor().getName());
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage("Persilas der Seelachs sagt: " + event.getMessage().getContentRaw()).queue();
        }
    }
}
