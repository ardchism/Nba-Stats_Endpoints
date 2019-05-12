package com.nbastat.player.api.service;

import com.nbastat.player.domain.repository.NbaStatResultSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
