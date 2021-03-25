package com.douineau.demokeycloak.controller;

import com.douineau.demokeycloak.model.Player;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PlayerController {

    @GetMapping("/players")
//    @RolesAllowed("user")
    public String getPlayers(Model model) {

        List<Player> players = new ArrayList<>();
        players.add(
                new Player(
                "Ronaldinho",
                29,
                54,
                "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.eurosport.fr%2Ffootball%2Fligue-1%2F2020-2021%2Frai-meilleur-joueur-du-psg-devant-susic-et-ronaldinho-de-la-difficulte-de-classer-les-legendes_sto7913666%2Fstory.shtml&psig=AOvVaw1-e-4l1P5QNi2grh2MLb_i&ust=1616258565753000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCNDw3sDmvO8CFQAAAAAdAAAAABAJ"
            )
        );

        model.addAttribute("players", players);

        return "players";
    }

    @GetMapping("/logout")
//    @RolesAllowed("user")
    public String logout(HttpServletRequest req) throws ServletException {

        req.logout();

        return "/";
    }

    @GetMapping("/jwt")
//    @RolesAllowed("user")
    public Map<String, String> jwt(HttpServletRequest req) throws ServletException {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) req.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext ctx = principal.getKeycloakSecurityContext();

        Map<String, String> map = new HashMap<>();
        map.put("access_token", ctx.getTokenString());
        return map;
    }
}
