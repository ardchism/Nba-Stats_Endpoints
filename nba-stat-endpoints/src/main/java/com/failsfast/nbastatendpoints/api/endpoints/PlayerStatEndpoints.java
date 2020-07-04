package com.failsfast.nbastatendpoints.api.endpoints;

import com.failsfast.nbastatendpoints.api.contracts.factories.PlayerResponseFactory;
import com.failsfast.nbastatendpoints.api.contracts.PlayerResponse;
import com.failsfast.nbastatendpoints.api.service.PlayerStatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@RestController
public class PlayerStatEndpoints {

    private PlayerStatService playerStatService;
    private PlayerResponseFactory playerResponseFactory;

    @RequestMapping(path = "/V1/PlayerStats/", method = RequestMethod.GET)
    public Map<String, String> getPlayerStatsById(
        @RequestParam(value = "playerId")
            Integer playerId) {

        Objects.requireNonNull(playerId, "PlayerId can not be null");

        return playerStatService.getPlayerStatsById(playerId);

    }

    @RequestMapping(path = "/V1/PlayerStats/all", method = RequestMethod.GET)
    public List<PlayerResponse> getPlayers() {

        return playerResponseFactory.build(playerStatService.getPlayers());
    }
}
