package com.douineau.demokeycloak.controller;

import com.douineau.demokeycloak.model.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerController {

    @GetMapping("/players")
    public List<Player> getPlayers() {

        List<Player> players = new ArrayList<>();
        players.add(new Player("Ronaldinho", 29, 54));
        players.add(new Player("Ronalo", 24, 45));
        players.add(new Player("Bebeto", 35, 75));
        players.add(new Player("Pelé", 45, 44));
        players.add(new Player("Sokrates", 46, 45));

        return players;
    }
}
