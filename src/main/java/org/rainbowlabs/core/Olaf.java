package org.rainbowlabs.core;

import org.rainbowlabs.commands.CMDMusic;
import org.rainbowlabs.commands.CMDPrefix;
import org.rainbowlabs.commands.CMDRegister;
import org.rainbowlabs.handler.CommandHandler;
import org.rainbowlabs.listener.CommandListener;
import org.rainbowlabs.listener.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.rainbowlabs.utils.VARIABLES;

import javax.security.auth.login.LoginException;

/**
 * Olaf Class
 * @author Christoph Pelzer
 */
public class Olaf {

    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(VARIABLES.TOKEN);
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
