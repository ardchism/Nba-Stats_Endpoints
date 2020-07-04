package com.failsfast.nbastatendpoints.api.service;

import com.failsfast.nbastatendpoints.domain.Player;
import com.failsfast.nbastatendpoints.domain.repository.NbaStatResultSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Service
public class PlayerStatService {

    private NbaStatResultSetRepository nbaStatResultSetRepository;

    public Map<String, String> getPlayerStatsById(Integer playerId) {

        Objects.requireNonNull(playerId, "PlayerId can not be null");

        return nbaStatResultSetRepository.getPlayerStatsById(playerId);

    }

    public List<Player> getPlayers() {
        return null;
    }
}
