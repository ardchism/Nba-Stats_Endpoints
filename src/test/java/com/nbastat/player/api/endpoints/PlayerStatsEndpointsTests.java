package com.nbastat.player.api.endpoints;

import com.nbastat.player.api.contracts.PlayerResponse;
import com.nbastat.player.api.service.PlayerStatService;
import org.junit.Test;
import org.mockito.Mockito;

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

        PlayerStatEndpoints playerStatEndpoints = new PlayerStatEndpoints(playerStatService);

        assertThat(playerStatEndpoints.getPlayerStatsById(1)
                                      .toString()).isEqualTo("{PlayerId=1, PlayerName=Jack Frost}");

    }

    @Test
    public void getPlayerStatsHappyPath() {

        PlayerStatService playerStatService = Mockito.mock(PlayerStatService.class);

        PlayerStatEndpoints playerStatEndpoints = new PlayerStatEndpoints(playerStatService);

        List<PlayerResponse> players = playerStatEndpoints.getPlayers();

    }

    @Test
    public void getPlayerStatsByIdGrumpyPathPlayerIdIsNull() {

        PlayerStatService playerStatService = Mockito.mock(PlayerStatService.class);

        PlayerStatEndpoints playerStatEndpoints = new PlayerStatEndpoints(playerStatService);

        try {
            playerStatEndpoints.getPlayerStatsById(null);
            throw new RuntimeException("Test should not reach this point!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("PlayerId can not be null");
        }

    }

}
