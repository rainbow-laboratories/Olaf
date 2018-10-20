package de.cpelzer.core;

import de.cpelzer.commands.CMDMusic;
import de.cpelzer.commands.CMDPrefix;
import de.cpelzer.commands.CMDRegister;
import de.cpelzer.handler.CommandHandler;
import de.cpelzer.listener.CommandListener;
import de.cpelzer.listener.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import de.cpelzer.utils.variables;

import javax.security.auth.login.LoginException;

/**
 * Main Class
 * @author Christoph Pelzer
 */
public class Main {

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(variables.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(Game.playing("CARDS AGAINST DOWNTIME"));

        builder.addEventListener(new CommandListener());
        builder.addEventListener(new ReadyListener());

        addCommands();

        try {
            JDA jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void addCommands() {
        CommandHandler.commands.put("prefix", new CMDPrefix());
        CommandHandler.commands.put("register", new CMDRegister());
        CommandHandler.commands.put("music", new CMDMusic());
    }
}
