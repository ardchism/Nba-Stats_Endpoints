package com.nbastat.player.api.contracts.factories;

import com.nbastat.player.api.contracts.PlayerResponse;
import com.nbastat.player.domain.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerResponseFactory {

    public List<PlayerResponse> build(List<Player> players) {
        return players.stream()
                      .map(this::build)
                      .collect(Collectors.toList());
    }

    public PlayerResponse build(Player player) {

        PlayerResponse playerResponse = new PlayerResponse();

        playerResponse.setPlayerId(player.getPlayerId());
        playerResponse.setPlayerName(player.getPlayerName());

        return playerResponse;
    }

}
