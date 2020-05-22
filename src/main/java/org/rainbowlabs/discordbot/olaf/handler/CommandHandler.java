package org.rainbowlabs.discordbot.olaf.handler;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.rainbowlabs.discordbot.olaf.commands.Command;
import org.rainbowlabs.discordbot.olaf.utils.VARIABLES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the parsed command and acts if the command is good *
 * @author Christoph Pelzer
 */
public class CommandHandler {
    private HashMap<String, Command> commands = new HashMap<>();

    private static Logger logger = Logger.getLogger(CommandHandler.class.getName());

    private String commandString = "";
    private MessageReceivedEvent triggerEvent;
    private ArrayList<String> arguments = new ArrayList<>();

    private static CommandHandler commandHandler;

    private CommandHandler() {}

    public static CommandHandler getCommandHandler() {
        if (commandHandler == null) {
            commandHandler = new CommandHandler();
        }

        return commandHandler;
    }

    public void handleCommand() {
        logger.log(Level.INFO, "handleCommand was called, the current commandString is {0}", this.commandString);

        if(this.commands.containsKey(commandString)) {
            logger.log(Level.INFO, "Command with key {0} is known", commandString);
            Command calledCommand = commands.get(commandString);
            boolean safe = calledCommand.called(arguments, triggerEvent);
            if(!safe) {
                logger.log(Level.INFO, "Executing command with arguments {0}", arguments);
                calledCommand.action(arguments, triggerEvent);
                calledCommand.executed(safe, triggerEvent);
            } else {
                calledCommand.executed(safe, triggerEvent);
            }
        }
    }

    public CommandHandler parse(String raw, MessageReceivedEvent event, String eventServerPrefix) {
        this.triggerEvent = event;
        String headlessCommand = raw.replaceFirst(eventServerPrefix, "");
        String[] fullCommand = headlessCommand.split(" ");

        ArrayList<String> tempArguments = new ArrayList<>();

        for (String s : fullCommand) {
            tempArguments.add(s);
        }
        this.commandString = tempArguments.get(0);

        tempArguments.remove(0);

        this.arguments.clear();
        this.arguments.addAll(tempArguments);

        return this;
    }

    public void addCommand(String prefix, Command command) {
        this.commands.put(prefix, command);
    }
}
