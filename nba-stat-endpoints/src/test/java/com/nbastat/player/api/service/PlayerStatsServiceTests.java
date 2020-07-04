package com.nbastat.player.api.service;

import com.nbastat.player.domain.repository.NbaStatResultSetRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerStatsServiceTests {

    @Test
    public void getPlayerStatsByIdHappyPath() {

        Map<String, String> playerStats = new HashMap<>();
        playerStats.put("PlayerName", "Jack Frost");
        playerStats.put("PlayerId", "1");

        NbaStatResultSetRepository nbaStatResultSetRepository = Mockito.mock(
            NbaStatResultSetRepository.class);
        Mockito.when(nbaStatResultSetRepository.getPlayerStatsById(Mockito.eq(1)))
               .thenReturn(playerStats);

        PlayerStatService playerStatService = new PlayerStatService(nbaStatResultSetRepository);

        assertThat(playerStatService.getPlayerStatsById(1)
                                    .toString()).isEqualTo(playerStats.toString());

    }

    @Test
    public void getPlayerStatsByIdGrumpyPathPlayerIdIsNull() {

        NbaStatResultSetRepository nbaStatResultSetRepository = Mockito.mock(
            NbaStatResultSetRepository.class);

        PlayerStatService playerStatService = new PlayerStatService(nbaStatResultSetRepository);

        try {
            playerStatService.getPlayerStatsById(null);
            throw new RuntimeException("Test should not reach this point!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("PlayerId can not be null");
        }

    }

}
