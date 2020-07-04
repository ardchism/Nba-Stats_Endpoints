package com.nbastat.player.api.endpoints;

import com.nbastat.player.api.contracts.PlayerResponse;
import com.nbastat.player.api.contracts.factories.PlayerResponseFactory;
import com.nbastat.player.api.service.PlayerStatService;
import com.nbastat.player.domain.Player;
import com.nbastat.player.domain.builders.PlayerBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerStatsEndpointsTests {

    @Test
    public void getPlayerStatsByIdHappyPath() {

        Map<String, String> playerStats = new HashMap<>();
        playerStats.put("PlayerName", "Jack Frost");
        playerStats.put("PlayerId", "1");

        PlayerStatService playerStatService = Mockito.mock(PlayerStatService.class);
        Mockito.when(playerStatService.getPlayerStatsById(Mockito.eq(1)))
               .thenReturn(playerStats);

        PlayerStatEndpoints playerStatEndpoints = new PlayerStatEndpoints(playerStatService, null);

        assertThat(playerStatEndpoints.getPlayerStatsById(1)
                                      .toString()).isEqualTo("{PlayerId=1, PlayerName=Jack Frost}");

    }

    @Test
    public void getPlayerStatsHappyPath() {

        Player testPlayer1 = PlayerBuilder.Builder()
                                          .withPlayerId(1L)
                                          .withPlayerName("Adam C")
                                          .get();

        Player testPlayer2 = PlayerBuilder.Builder()
                                          .withPlayerId(2L)
                                          .withPlayerName("Tina C")
                                          .get();

        List<Player> expectedPlayers = new ArrayList<>();
        expectedPlayers.add(testPlayer1);
        expectedPlayers.add(testPlayer2);

        PlayerStatService playerStatService = Mockito.mock(PlayerStatService.class);
        Mockito.when(playerStatService.getPlayers())
               .thenReturn(expectedPlayers);

        PlayerStatEndpoints playerStatEndpoints = new PlayerStatEndpoints(playerStatService,
                                                                          new PlayerResponseFactory());

        List<PlayerResponse> playerResponses = playerStatEndpoints.getPlayers();

        assertThat(playerResponses.size()).isEqualTo(2);

        PlayerResponse playerResponse1 = playerResponses.stream()
                                                        .filter(pr -> pr.getPlayerId()
                                                                        .equals(1L))
                                                        .findFirst()
                                                        .orElseThrow(() -> new RuntimeException(
                                                            "PlayerResponse not found with id 1."));

        assertThat(playerResponse1.getPlayerName()).isEqualTo(testPlayer1.getPlayerName());

        PlayerResponse playerResponse2 = playerResponses.stream()
                                                        .filter(pr -> pr.getPlayerId()
                                                                        .equals(2L))
                                                        .findFirst()
                                                        .orElseThrow(() -> new RuntimeException(
                                                            "PlayerResponse not found with id 2."));

        assertThat(playerResponse2.getPlayerName()).isEqualTo(testPlayer2.getPlayerName());

    }

    @Test
    public void getPlayerStatsByIdGrumpyPathPlayerIdIsNull() {

        PlayerStatService playerStatService = Mockito.mock(PlayerStatService.class);

        PlayerStatEndpoints playerStatEndpoints = new PlayerStatEndpoints(playerStatService, null);

        try {
            playerStatEndpoints.getPlayerStatsById(null);
            throw new RuntimeException("Test should not reach this point!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("PlayerId can not be null");
        }

    }

}
