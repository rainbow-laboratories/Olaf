package org.rainbowlabs.discordbot.olaf.commands;

import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command class needed to create the command.
 * @author Christoph Pelzer
 */
@Slf4j
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
            log.info("User id is: {}, Username is: {}", userEntity.get().getId(), userEntity.get().getName());
            Optional<Server> optionalServer = serverRepository.findById(event.getGuild().getIdLong());
            if (optionalServer.isPresent()) {
                Server updatedServer = optionalServer.get();
                updatedServer.addUser(userEntity.get());
                serverRepository.save(updatedServer);
            } else {
                throw new IllegalArgumentException("Couldn't find the Server.. There's something srlsy wrong here");
            }
        } else {
            User user = new User(event.getAuthor().getIdLong(), event.getAuthor().getName());
            userRepository.save(user);
            action(args, event);
        }
    }

    public void executed(boolean sucsess, MessageReceivedEvent event) {
        logger.log(Level.INFO, "Executed CMDRegister with success");
    }

    public String help() {
        return null;
    }
}
