package core;

import commands.CMDMusic;
import commands.CMDPrefix;
import commands.CMDRegister;
import handler.CommandHandler;
import listener.CommandListener;
import listener.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import utils.variables;

import javax.security.auth.login.LoginException;

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
