package org.rainbowlabs.discordbot.spring.persistence.entities;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Slf4j
public class WWGuild {
    @Id
    private long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<WWUser> users;

    private boolean preparingForQuest;

    private String currentMessageId;

    public void addUser(WWUser user) {
        this.users.add(user);
    }

    public void removeById(WWUser user) {
        users.remove(user);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (WWUser user : users) {
            if (user.getFee() >= 200) {
                stringBuilder.append("+ " + user.toString());
            } else {
                stringBuilder.append("- " + user.toString());
            }
        }
        log.info("DAMN: " + stringBuilder);
        return stringBuilder.toString();
    }
}
