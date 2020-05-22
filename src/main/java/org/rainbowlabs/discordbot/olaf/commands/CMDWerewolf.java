package org.rainbowlabs.discordbot.olaf.commands;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.rainbowlabs.discordbot.spring.application.SpringContext;
import org.rainbowlabs.discordbot.spring.persistence.entities.WWGuild;
import org.rainbowlabs.discordbot.spring.persistence.entities.WWUser;
import org.rainbowlabs.discordbot.spring.persistence.repositories.WWGuildRepository;
import org.rainbowlabs.discordbot.spring.persistence.repositories.WWUserRepository;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class CMDWerewolf implements Command{
    @Override
    public boolean called(ArrayList<String> args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(ArrayList<String> args, MessageReceivedEvent event) {
        log.info("yay: {}", args);
        WWGuildRepository wwGuildRepository = SpringContext.getBean(WWGuildRepository.class);
        WWUserRepository userRepository = SpringContext.getBean(WWUserRepository.class);
        if (args.size() > 0 ) {
            if("init".equals(args.get(0))) {
                log.info("HOT DAMN: {}", args);
                WWGuild guild = new WWGuild();
                guild.setId(event.getGuild().getIdLong());
                guild.setUsers(new ArrayList<>());
                wwGuildRepository.save(guild);
            } else if("user".equals(args.get(0)) && args.size() > 1) {
                log.info("USER");
                if ("add".equals(args.get(1)) && args.size() > 2) {
                    WWUser user = userRepository.findByName(args.get(2));
                    if (user == null) {
                        log.info("user was null");
                        user = new WWUser();
                        user.setName(args.get(2));
                        user.setFee(0);
                    }
                    WWUser repoUser = userRepository.save(user);
                    Optional<WWGuild> guild = wwGuildRepository.findById(event.getGuild().getIdLong());
                    if (guild.isPresent()) {
                        log.info("GUILD WAS PRESENT");
                        WWGuild wwGuild = guild.get();
                        if (!wwGuild.getUsers().contains(repoUser)) {
                            wwGuild.addUser(user);
                        }
                        WWGuild persistedGuild = wwGuildRepository.save(wwGuild);
                        if (wwGuild.isPreparingForQuest()) {
                            sendEmbedMessage(event, persistedGuild);
                        }
                    }
                } else if ("remove".equals(args.get(1)) && args.size() > 2) {
                    WWUser user = userRepository.findByName(args.get(2));
                    Optional<WWGuild> guild = wwGuildRepository.findById(event.getGuild().getIdLong());
                    if(guild.isPresent()) {
                        WWGuild wwGuild = guild.get();
                        wwGuild.removeById(user);
                        WWGuild persistedGuild = wwGuildRepository.save(wwGuild);
                        userRepository.delete(user);
                        if (persistedGuild.isPreparingForQuest()) {
                            sendEmbedMessage(event, persistedGuild);
                        }
                    }
                } else if ("pay".equals(args.get(2)) && args.size() > 3) {
                    WWUser user = userRepository.findByName(args.get(1));
                    Optional<WWGuild> guild = wwGuildRepository.findById(event.getGuild().getIdLong());
                    if (guild.isPresent()) {
                        WWUser guildUser = guild.get().getUsers().get(guild.get().getUsers().indexOf(user));
                        guildUser.payFee(Integer.valueOf(args.get(3)));
                        userRepository.save(guildUser);
                        WWGuild wwGuild = wwGuildRepository.save(guild.get());
                        if (wwGuild.isPreparingForQuest()) {
                            sendEmbedMessage(event, wwGuild);
                        }
                    }
                }
            } else if("preparing".equals(args.get(0))) {
                log.info("TADA");
                Optional<WWGuild> guild = wwGuildRepository.findById(event.getGuild().getIdLong());
                if (guild.isPresent()) {
                    guild.get().setPreparingForQuest(true);
                    sendEmbedMessage(event, guild.get());
                    log.info(event.getChannel().getLatestMessageId());
                    guild.get().setCurrentMessageId(event.getChannel().getLatestMessageId());
                    wwGuildRepository.save(guild.get());
                }
            } else if ("started".equals(args.get(0))) {
                Optional<WWGuild> guild = wwGuildRepository.findById(event.getGuild().getIdLong());
                if (guild.isPresent()) {
                    guild.get().setPreparingForQuest(false);
                    WWGuild wwGuild = guild.get();
                    for (WWUser user : wwGuild.getUsers()) {
                        user.setFee(0);
                        userRepository.save(user);
                    }

                    wwGuildRepository.save(wwGuild);
                }

            }
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }

    private void sendEmbedMessage(MessageReceivedEvent event, WWGuild guild) {
        event.getChannel().sendMessage("```diff\n" + guild.toString() + "```").queue();
    }
}
