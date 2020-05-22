package org.rainbowlabs.discordbot.spring.persistence.repositories;

import org.rainbowlabs.discordbot.spring.persistence.entities.WWGuild;
import org.springframework.data.repository.CrudRepository;

public interface WWGuildRepository extends CrudRepository<WWGuild, Long> {
}
