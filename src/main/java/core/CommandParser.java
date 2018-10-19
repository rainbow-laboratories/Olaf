package core;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import utils.variables;

import java.util.ArrayList;

/**
 * Parser class to translate a given message with prefix into every part
 * @author Christoph Pelzer
 */
public class CommandParser {

    public static CommandContainer parse(String raw, MessageReceivedEvent event) {
        String beheaded = raw.replaceFirst("\\" + variables.prefix, "");
        String[] splitbeheaded = beheaded.split(" ");
        String invoke = splitbeheaded[0];
        ArrayList<String> split = new ArrayList<String>();
        for (String s : splitbeheaded) {
            split.add(s);
        }
        split.remove(0);
        System.out.println(raw);
        return new CommandContainer(raw, beheaded, splitbeheaded, invoke, split, event);
    }

    public static class CommandContainer {
        public final String raw;
        public final String beheaded;
        public final String[] splitbeheaded;
        public final String invoke;
        public final ArrayList<String> args;
        public final MessageReceivedEvent event;

        public CommandContainer(String rw, String beheaded, String[] splitbeheaded, String invoke, ArrayList<String> args, MessageReceivedEvent event) {
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitbeheaded = splitbeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
        }
    }
}
