package com.nbastat.player.domain;

import lombok.Data;

@Data
public class NbaStatResultSet {

	private String Name;
	private String[] Headers;
	private String[][] resultSet;
	
}
