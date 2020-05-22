package org.rainbowlabs.discordbot.spring.persistence.repositories;

import org.rainbowlabs.discordbot.spring.persistence.entities.WWUser;
import org.springframework.data.repository.CrudRepository;

public interface WWUserRepository extends CrudRepository<WWUser, Long> {
    WWUser findByName(String name);
}
