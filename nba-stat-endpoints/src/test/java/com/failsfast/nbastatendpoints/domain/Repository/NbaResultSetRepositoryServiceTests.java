package com.failsfast.nbastatendpoints.domain.Repository;

import com.failsfast.nbastatendpoints.domain.NbaStatResultSet;
import com.failsfast.nbastatendpoints.domain.builders.NbaStatResultSetBuilder;
import com.failsfast.nbastatendpoints.domain.repository.NbaStatResultSetRepository;
import com.failsfast.nbastatendpoints.domain.repository.responses.LeagueDashPlayerStatsResponse;
import com.failsfast.nbastatendpoints.domain.repository.responses.builders.LeagueDashPlayerStatsResponseBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class NbaResultSetRepositoryServiceTests {

    @Test
    public void getPlayerStatsByIdHappyPath() {

        Map<String, String> expectedPlayerStatsMap = new HashMap<>();
        expectedPlayerStatsMap.put("PLAYER_NAME", "Jack Frost");
        expectedPlayerStatsMap.put("PLAYER_ID", "201166");

        List<String> playerStats = new ArrayList<>();
        playerStats.add("201166");
        playerStats.add("Jack Frost");

        NbaStatResultSet nbaStatResultSet = NbaStatResultSetBuilder.Builder()
                                                                   .withHeaders("PLAYER_ID")
                                                                   .withHeaders("PLAYER_NAME")
                                                                   .withRowSet(playerStats)
                                                                   .get();

        LeagueDashPlayerStatsResponse leagueDashPlayerStatsResponse =
            LeagueDashPlayerStatsResponseBuilder.Builder()
                                                .withResultSets(nbaStatResultSet)
                                                .get();

        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        Mockito.when(restTemplate.getForObject(Mockito.anyString(),
                                               Mockito.any()))
               .thenReturn(leagueDashPlayerStatsResponse);

        NbaStatResultSetRepository nbaStatResultSetRepository = new NbaStatResultSetRepository(
            restTemplate);

        assertThat(nbaStatResultSetRepository.getPlayerStatsById(201166)
                                             .toString()).isEqualTo(
            expectedPlayerStatsMap.toString());

    }

    @Test
    public void getPlayerStatsByIdGrumpyPathPlayerIdIsNull() {

        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        NbaStatResultSetRepository nbaStatResultSetRepository = new NbaStatResultSetRepository(
            restTemplate);

        try {
            nbaStatResultSetRepository.getPlayerStatsById(null);
            throw new RuntimeException("Test should not reach this point!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("PlayerId can not be null");
        }

    }

    @Test
    public void getPlayerStatsByIdGrumpyPathLeagueDashResponseIsNull() {

        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        NbaStatResultSetRepository nbaStatResultSetRepository = new NbaStatResultSetRepository(
            restTemplate);

        try {
            nbaStatResultSetRepository.getPlayerStatsById(1);
            throw new RuntimeException("Test should not reach this point!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("League Dashboard Did Not Return A Response!");
        }

    }

}
