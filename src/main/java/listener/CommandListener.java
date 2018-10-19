package listener;

import handler.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.variables;

public class CommandListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith(variables.prefix) && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {
            CommandHandler.handleCommand(CommandHandler.parser.parse(event.getMessage().getContentRaw(), event));
        }
    }
}
