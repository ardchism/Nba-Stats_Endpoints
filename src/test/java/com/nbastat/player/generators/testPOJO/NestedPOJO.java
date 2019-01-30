package com.nbastat.player.generators.testPOJO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class NestedPOJO {

    private Player player;
    private List<Stat> stats;


    public static class Player {

        private String playerName;
        private Integer playerId;
        private List<Integer> payerStats;


    }

    public static class Stat {

        private Integer number;
        private BigDecimal pointsPerGame;

    }

}
