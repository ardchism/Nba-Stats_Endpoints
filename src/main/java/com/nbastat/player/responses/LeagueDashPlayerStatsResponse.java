package com.nbastat.player.responses;

import com.nbastat.player.domain.NbaStatResultSet;
import com.nbastat.player.domain.Parameters;

import lombok.Data;

@Data
public class LeagueDashPlayerStatsResponse {

	private String Resource;
	private Parameters parameters;
	private NbaStatResultSet resultSet;
	
}
