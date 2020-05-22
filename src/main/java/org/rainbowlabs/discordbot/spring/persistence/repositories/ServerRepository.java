package org.rainbowlabs.discordbot.spring.persistence.repositories;

import org.rainbowlabs.discordbot.spring.persistence.entities.Server;
import org.rainbowlabs.discordbot.spring.persistence.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServerRepository extends CrudRepository<Server, Long> {
    List<Server> findAllServerByUsers(User user);
}
