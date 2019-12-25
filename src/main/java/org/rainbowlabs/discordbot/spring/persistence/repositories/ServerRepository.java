package org.rainbowlabs.discordbot.spring.persistence.repositories;

import org.rainbowlabs.discordbot.spring.persistence.entities.Server;
import org.springframework.data.repository.CrudRepository;

public interface ServerRepository extends CrudRepository<Server, Long> {
}
