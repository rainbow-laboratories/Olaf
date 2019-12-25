package org.rainbowlabs.discordbot.spring.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Server> servers;

    public User() {
        servers = new HashSet<>();
    }

    public Set<Server> getServers() {
        return this.servers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addServer(Server server) {
        this.servers.add(server);
    }

    @Override
    public String toString() {
        return "ID: " + id + " servers: " + servers;
    }
}
