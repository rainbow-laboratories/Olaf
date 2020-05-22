package org.rainbowlabs.discordbot.olaf;

import lombok.extern.slf4j.Slf4j;
import org.rainbowlabs.discordbot.olaf.commands.CMDPrefix;
import org.rainbowlabs.discordbot.olaf.commands.CMDRegister;
import org.rainbowlabs.discordbot.olaf.commands.CMDWerewolf;
import org.rainbowlabs.discordbot.olaf.handler.CommandHandler;
import org.rainbowlabs.discordbot.olaf.listener.KickerListener;
import org.rainbowlabs.discordbot.olaf.listener.CommandListener;
import org.rainbowlabs.discordbot.olaf.listener.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.rainbowlabs.discordbot.olaf.utils.VARIABLES;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

/**
 * Olaf Class
 * @author Christoph Pelzer
 */
@Component
@Slf4j
public class Olaf implements CommandLineRunner {
    @Override
    public void run(String... args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        builder.setToken(VARIABLES.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(Game.playing("Werewolf Online"));

        builder.addEventListener(new CommandListener());
        builder.addEventListener(new ReadyListener());
        //builder.addEventListener(new KickerListener());

        addCommands();

        try {
            builder.build();
        } catch (LoginException e) {
            log.error("Failed to login to the discord application", e);
        }
    }

    public static void addCommands() {
        //CommandHandler.getCommandHandler().addCommand("prefix", new CMDPrefix());
        //CommandHandler.getCommandHandler().addCommand("register", new CMDRegister());
        CommandHandler.getCommandHandler().addCommand("ww", new CMDWerewolf());
    }
}
