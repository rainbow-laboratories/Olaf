package org.rainbowlabs.olaf;

import org.rainbowlabs.olaf.commands.CMDPrefix;
import org.rainbowlabs.olaf.commands.CMDRegister;
import org.rainbowlabs.olaf.commands.CMDSilas;
import org.rainbowlabs.olaf.handler.CommandHandler;
import org.rainbowlabs.olaf.listener.CommandListener;
import org.rainbowlabs.olaf.listener.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.rainbowlabs.olaf.utils.VARIABLES;

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
            JDA jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public static void addCommands() {
        CommandHandler.getCommandHandler().addCommand("prefix", new CMDPrefix());
        CommandHandler.getCommandHandler().addCommand("register", new CMDRegister());
        CommandHandler.getCommandHandler().addCommand("silas", new CMDSilas());
    }
}
