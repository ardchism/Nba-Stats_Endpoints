package com.nbastat.player.domain.repository.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Data
    public static class NbaStatResultSetResponse {

        private String Name;
        private List<String> Headers = new ArrayList<>();
        private List<List<String>> rowSet = new ArrayList<>();

    }

    @Data
    public static class ParametersResponse {

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
}
