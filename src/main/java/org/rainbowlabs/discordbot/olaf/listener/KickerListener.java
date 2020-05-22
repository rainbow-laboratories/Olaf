package org.rainbowlabs.discordbot.olaf.listener;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.rainbowlabs.discordbot.spring.application.SpringContext;
import org.rainbowlabs.discordbot.spring.persistence.entities.User;
import org.rainbowlabs.discordbot.spring.persistence.repositories.UserRepository;

import java.util.List;

@Slf4j
public class KickerListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if ("kicker-score".equals(event.getChannel().getName())) {
            String message;
            if (!event.getMessage().getMentionedMembers().isEmpty()) {
                message = this.normalizeMessage(event);
            }
            message = event.getMessage().getContentRaw();
            try {
                String[] splittedMessage = message.split("-");
                log.info("First team: {}, Secondteam: {}, FinalScore: {}", splittedMessage[0], splittedMessage[1], splittedMessage[2]);
                String[] scoreSplitted = splittedMessage[2].split(":");
                int[] score = new int[2];
                score[0] = Integer.parseInt(scoreSplitted[0].trim());
                score[1] = Integer.parseInt(scoreSplitted[1].trim());
                if (score[0] > score[1]) {
                    log.debug("First team won: {}", splittedMessage[0]);
                    String firstPlayer =  splittedMessage[0].split("&")[0].trim();
                    String secondPlayer =  splittedMessage[0].split("&")[1].trim();
                    UserRepository userRepository = SpringContext.getBean(UserRepository.class);
                    log.debug("Username: {}", firstPlayer);
                    User user = userRepository.findByName(firstPlayer);
                    log.info("User has id: {}, and Name: {}, and Experience: {}", user.getId(), user.getName(), user.getExperience());
                    user.addExperience(3);
                    userRepository.save(user);
                    user = userRepository.findByName(secondPlayer);
                    user.addExperience(3);
                    userRepository.save(user);
                }
            } finally {
                log.debug("LUL");
            }
        }
    }

    public String normalizeMessage(MessageReceivedEvent event) {
        UserRepository userRepository = SpringContext.getBean(UserRepository.class);

        String message = event.getMessage().getContentRaw();
        List<net.dv8tion.jda.core.entities.User> mentionedUsers = event.getMessage().getMentionedUsers();

        for (net.dv8tion.jda.core.entities.User mentionedUser : mentionedUsers) {
            User foundUser = userRepository.findByIdOrName(mentionedUser.getIdLong(), mentionedUser.getName());
            log.debug("FOUND USER: {}", foundUser);
        }


        return "";
    }
}
