package org.rainbowlabs.discordbot.spring.persistence.repositories;

import org.rainbowlabs.discordbot.spring.persistence.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
