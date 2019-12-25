package org.rainbowlabs.discordbot.olaf.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.rainbowlabs.discordbot.spring.application.SpringContext;
import org.rainbowlabs.discordbot.spring.persistence.entities.Server;
import org.rainbowlabs.discordbot.spring.persistence.entities.User;
import org.rainbowlabs.discordbot.spring.persistence.repositories.ServerRepository;
import org.rainbowlabs.discordbot.spring.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command class needed to create the command.
 * @author Christoph Pelzer
 */
public class CMDRegister implements Command {
    private static Logger logger = Logger.getLogger(CMDRegister.class.getName());

    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }


    public void action(ArrayList<String> args, MessageReceivedEvent event) {
        UserRepository userRepository = SpringContext.getBean(UserRepository.class);
        ServerRepository serverRepository = SpringContext.getBean(ServerRepository.class);
        Long userId = event.getAuthor().getIdLong();
        Optional<User> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            User user = userEntity.get();
            if (user.getServers().contains(event.getGuild().getIdLong())) {
                event
                        .getChannel()
                        .sendMessage(event.getAuthor().getName()
                                + " tried to register but was already registered.")
                        .queue();
            }

        }

        User user = new User();
        user.setId(event.getAuthor().getIdLong());
        Optional<Server> serverOptional = serverRepository.findById(event.getGuild().getIdLong());
        if (serverOptional.isEmpty()) {
            event
                    .getChannel()
                    .sendMessage("We could not add the server to your user account...")
                    .queue();
            return;
        }
        user.addServer(serverOptional.get());
        userRepository.save(user);

        User savedUser = userRepository.findById(event.getAuthor().getIdLong()).get();
        event
                .getChannel()
                .sendMessage(event.getAuthor().getName()
                        + " the user object was saved, welcome:  " + savedUser )
                .queue();
    }

    public void executed(boolean sucsess, MessageReceivedEvent event) {
        logger.log(Level.INFO, "Executed CMDRegister with success");
    }

    public String help() {
        return null;
    }
}
