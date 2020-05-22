package org.rainbowlabs.discordbot.spring.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;

    private long experience;

    private String name;

    public User(long id) {
        this.id = id;
        this.name = "";
        this.experience = 0;
    }

    public User() {}

    public User(long id, String name) {
        this.id = id;
        this.name = name;
        this.experience = 0;
    }

    public void addExperience(long experience) {
        this.experience += experience;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", experience=" + experience +
                ", name='" + name + '\'' +
                '}';
    }
}
