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
        Integer playerIdIndex = Headers.indexOf("PLAYER_ID");
        if(playerIdIndex < 0) {
            throw new RuntimeException("PLAYER ID is not present in this result set");
        }
        List<String> playerRow = null;
        for(int x = 0; x < rowSet.size(); x++) {
            if(Integer.parseInt(rowSet.get(x)
                                      .get(playerIdIndex)) == playerId) {
                playerRow = rowSet.get(x);
            }
        }
        if(playerRow == null) {
            throw new RuntimeException("PLAYER ID not found for " + playerId);
        }
        return playerRow;
    }

}
