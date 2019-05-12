package com.nbastat.player.responses;

import com.nbastat.player.domain.NbaStatResultSet;
import com.nbastat.player.domain.Parameters;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LeagueDashPlayerStatsResponse {

    private String Resource;
    private Parameters parameters;
    private List<NbaStatResultSet> resultSets = new ArrayList<>();

}
