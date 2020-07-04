package com.failsfast.nbastatendpoints.domain.repository;

import com.failsfast.nbastatendpoints.domain.repository.builders.LeagueDashboardUrlBuilder;
import com.failsfast.nbastatendpoints.domain.repository.responses.LeagueDashPlayerStatsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Repository
public class NbaStatResultSetRepository {

    public RestTemplate restTemplate;

    public Map<String, String> getPlayerStatsById(Integer playerId) {

        Objects.requireNonNull(playerId, "PlayerId can not be null");

        String url = LeagueDashboardUrlBuilder.Builder()
                                              .build();

        LeagueDashPlayerStatsResponse leagueDashPlayerStatsResponse =
            restTemplate.getForObject(url, LeagueDashPlayerStatsResponse.class);

        Objects.requireNonNull(leagueDashPlayerStatsResponse,
                               "League Dashboard Did Not Return A Response!");

        return leagueDashPlayerStatsResponse.getResultSets()
                                            .get(0)
                                            .findHeaderResultsMapByField(
                                                "PLAYER_ID", playerId);

    }
}


