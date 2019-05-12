package com.nbastat.player.domain.repository;

import com.nbastat.player.responses.LeagueDashPlayerStatsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Repository
public class NbaStatResultSetRepository {

    private RestTemplate restTemplate;

    public Map<String, String> getPlayerStatsById(Integer playerId) {

        Objects.requireNonNull(playerId, "PlayerId can not be null");

        //TODO: Replace test url with uri builder
        LeagueDashPlayerStatsResponse leagueDashPlayerStatsResponse =
            restTemplate.getForObject("TEST", LeagueDashPlayerStatsResponse.class);

        Objects.requireNonNull(leagueDashPlayerStatsResponse,
                               "League Dashboard Did Not Return A Response!");

        return leagueDashPlayerStatsResponse.getResultSets()
                                            .get(0)
                                            .findHeaderResultsMapByField(
                                                "PLAYER_ID", playerId);

    }
}
