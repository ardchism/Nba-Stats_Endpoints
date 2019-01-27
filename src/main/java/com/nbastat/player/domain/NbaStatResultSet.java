package com.nbastat.player.domain;

import lombok.Data;

import java.util.List;

@Data
public class NbaStatResultSet {

    private String Name;
    private List<String> Headers;
    private List<List<String>> rowSet;

    //business methods
    public List<String> findResultsByPlayerId(Integer playerId) {
        int playerIdIndex = Headers.indexOf("PLAYER_ID");
        if(playerIdIndex < 0) {
            throw new RuntimeException("PLAYER ID is not present in this result set");
        }
        List<String> playerRow = null;
        for(List<String> strings : rowSet) {
            if(Integer.parseInt(strings
                                        .get(playerIdIndex)) == playerId) {
                playerRow = strings;
            }
        }
        if(playerRow == null) {
            throw new RuntimeException("PLAYER ID not found for " + playerId);
        }
        return playerRow;
    }

}
