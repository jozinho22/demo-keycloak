package com.douineau.demokeycloak.model;

import lombok.Data;

@Data
public class Player {

    private String name;
    private int age;
    private int nbScores;

    public Player(String name, int age, int nbScores) {
        this.name = name;
        this.age = age;
        this.nbScores = nbScores;
    }
}
