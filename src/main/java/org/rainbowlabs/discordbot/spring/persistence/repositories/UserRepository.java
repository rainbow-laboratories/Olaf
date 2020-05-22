package org.rainbowlabs.discordbot.spring.persistence.repositories;

import org.rainbowlabs.discordbot.spring.persistence.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);

    User findByIdOrName(long id, String name);
}
