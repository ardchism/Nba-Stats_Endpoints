package com.nbastat.player.generators.testPOJO;

import java.util.List;

public class BasicPOJO {

    private String playerName;
    private Integer playerId;
    private List<Integer> payerStats;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public List<Integer> getPayerStats() {
        return payerStats;
    }

    public void setPayerStats(List<Integer> payerStats) {
        this.payerStats = payerStats;
    }
}
