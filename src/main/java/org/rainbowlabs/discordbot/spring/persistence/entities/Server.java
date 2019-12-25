package org.rainbowlabs.discordbot.spring.persistence.entities;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Server {
    @Id
    private Long id;

    private String serverName;

    private String prefix;

    public Server(Long id, String serverName, String prefix) {
        this.id = id;
        this.serverName = serverName;
        this.prefix = prefix;
    }

    public Server() {}

    @Override
    public String toString() {
        return "ID: " + id + " server name: " + serverName + " current server prefix: " + prefix;
    }
}
