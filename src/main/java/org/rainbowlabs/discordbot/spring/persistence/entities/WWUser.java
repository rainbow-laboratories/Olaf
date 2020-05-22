package org.rainbowlabs.discordbot.spring.persistence.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class WWUser {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private int fee;

    public void payFee(int fee) {
        this.fee += fee;
    }

    @Override
    public String toString() {
        return name + "(" + fee + ")\n";
    }
}
