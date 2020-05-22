package org.rainbowlabs.discordbot.spring.persistence.entities;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Server {
    @Id
    private Long id;

    private String serverName;

    private String prefix;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;

    public Server(Long id, String serverName, String prefix) {
        this.id = id;
        this.serverName = serverName;
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Server() {}

    public void addUser(User user) {
        if (this.users.contains(user)) {
            throw new IllegalArgumentException("User already exists");
        }
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "ID: " + id + " server name: " + serverName + " current server prefix: " + prefix;
    }
}
