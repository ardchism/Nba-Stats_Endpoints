package com.failsfast.nbastatendpoints.api.contracts.factories;

import com.failsfast.nbastatendpoints.domain.Player;
import com.failsfast.nbastatendpoints.api.contracts.PlayerResponse;
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
