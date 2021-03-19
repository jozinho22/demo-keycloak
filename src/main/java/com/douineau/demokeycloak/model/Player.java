package com.douineau.demokeycloak.model;

import lombok.Data;

@Data
public class Player {

    private String name;
    private int age;
    private int nbScores;
    private String avatar;

    public Player(String name, int age, int nbScores, String avatar) {
        this.name = name;
        this.age = age;
        this.nbScores = nbScores;
        this.avatar = avatar;
    }
}
