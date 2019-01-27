package com.nbastat.player.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Parameters {

    @JsonProperty("MeasureType")
    private String measureType;
    @JsonProperty("PerMode")
    private String perMode;
    @JsonProperty("PlusMinus")
    private String plusMinus;
    @JsonProperty("PaceAdjust")
    private String paceAdjust;
    @JsonProperty("Rank")
    private String rank;
    @JsonProperty("LeagueID")
    private String leagueID;
    @JsonProperty("Season")
    private String season;
    @JsonProperty("SeasonType")
    private String seasonType;
    @JsonProperty("PORound")
    private Integer pORound;
    @JsonProperty("Outcome")
    private String outcome;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Month")
    private Integer month;
    @JsonProperty("SeasonSegment")
    private String seasonSegment;
    @JsonProperty("DateFrom")
    private String dateFrom;
    @JsonProperty("DateTo")
    private String dateTo;
    @JsonProperty("OpponentTeamID")
    private Integer opponentTeamID;
    @JsonProperty("VsConference")
    private String vsConference;
    @JsonProperty("VsDivision")
    private String vsDivision;
    @JsonProperty("TeamID")
    private Integer teamID;
    @JsonProperty("Conference")
    private String conference;
    @JsonProperty("Division")
    private String division;
    @JsonProperty("GameSegment")
    private String gameSegment;
    @JsonProperty("Period")
    private Integer period;
    @JsonProperty("ShotClockRange")
    private String shotClockRange;
    @JsonProperty("LastNGames")
    private Integer lastNGames;
    @JsonProperty("PlayerExperience")
    private String playerExperience;
    @JsonProperty("PlayerPosition")
    private String playerPosition;
    @JsonProperty("StarterBench")
    private String starterBench;
    @JsonProperty("DraftYear")
    private String draftYear;
    @JsonProperty("DraftPick")
    private String draftPick;
    @JsonProperty("College")
    private String college;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Height")
    private String height;
    @JsonProperty("Weight")
    private String weight;

}
