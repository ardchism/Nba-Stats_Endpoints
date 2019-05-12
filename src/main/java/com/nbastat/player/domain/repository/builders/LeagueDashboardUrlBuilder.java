package com.nbastat.player.domain.repository.builders;

import com.nbastat.player.domain.*;
import lombok.Data;
import org.apache.http.client.utils.URIBuilder;

import java.time.Month;
import java.util.Date;

@Data
public class LeagueDashboardUrlBuilder {

    private College college = College.Default;
    private Conference conference = Conference.Default;
    private Country country = Country.Default;
    private Date dateFrom = null;
    private Date dateTo = null;
    private Division division = Division.Default;
    private DraftPick draftPick = DraftPick.Default;
    private DraftYear draftYear = DraftYear.Default;
    private GameScope gameScope = GameScope.Default;
    private GameSegment gameSegment = GameSegment.Default;
    private Height height = Height.Default;
    private int lastNGames = 0;
    private LeagueID leagueID = LeagueID._00;
    private Location location = Location.Default;
    private MeasureType measureType = MeasureType.Base;
    private Month month = null;
    private TeamID opponentTeamId = TeamID._0;
    private Outcome outcome = Outcome.Default;
    private PORound poRound = PORound._0;
    private PaceAdjust paceAdjust = PaceAdjust.N;
    private PerMode perMode = PerMode.PerGame;
    private Period period = Period._0;
    private PlayerExperience playerExperience = PlayerExperience.Default;
    private PlayerPosition playerPosition = PlayerPosition.Default;
    private PlusMinus plusMinus = PlusMinus.N;
    private Rank rank = Rank.N;
    private Season season = Season._2018_19;
    private SeasonSegment seasonSegment = SeasonSegment.Default;
    private SeasonType seasonType = SeasonType.RegularSeason;
    private ShotClockRange shotClockRange = ShotClockRange.Default;
    private StarterBench starterBench = StarterBench.Default;
    private TeamID teamID = TeamID._0;
    private Conference vsConference = Conference.Default;
    private Division vsDivision = Division.Default;
    private Weight weight = Weight.Default;

    public static LeagueDashboardUrlBuilder Builder() {
        return new LeagueDashboardUrlBuilder();
    }

    public String build() {

        URIBuilder uriBuilder =
            new URIBuilder().setScheme("https")
                            .setHost("stats.nba.com")
                            .setPath("/stats/leaguedashplayerstats")
                            .addParameter("College", getCollege().getValue())
                            .addParameter("Conference", getConference().getValue())
                            .addParameter("DateFrom", "")
                            .addParameter("DateTo", "")
                            .addParameter("Division", getDivision().getValue())
                            .addParameter("DraftPick", getDraftYear().getValue())
                            .addParameter("DraftYear", getDraftPick().getValue())
                            .addParameter("GameScope", getGameScope().getValue())
                            .addParameter("GameSegment", getGameSegment().getValue())
                            .addParameter("Height", getHeight().getValue())
                            .addParameter("LastNGames", getLastNGames() + "")
                            .addParameter("LeagueID", getLeagueID().getValue())
                            .addParameter("Location", getLocation().getValue())
                            .addParameter("MeasureType", getMeasureType().getValue())
                            .addParameter("Month", "0")
                            .addParameter("OpponentTeamID", getOpponentTeamId().getValue())
                            .addParameter("Outcome", getOutcome().getValue())
                            .addParameter("PORound", getPoRound().getValue())
                            .addParameter("PaceAdjust", getPaceAdjust().getValue())
                            .addParameter("PerMode", getPerMode().getValue())
                            .addParameter("Period", getPeriod().getValue())
                            .addParameter("PlayerExperience", getPlayerExperience().getValue())
                            .addParameter("PlayerPosition", getPlayerPosition().getValue())
                            .addParameter("PlusMinus", getPlusMinus().getValue())
                            .addParameter("Rank", getRank().getValue())
                            .addParameter("Season", getSeason().getValue())
                            .addParameter("SeasonSegment", getSeasonSegment().getValue())
                            .addParameter("SeasonType", getSeasonType().getValue())
                            .addParameter("ShotClockRange", getShotClockRange().getValue())
                            .addParameter("StarterBench", getStarterBench().getValue())
                            .addParameter("TeamID", getTeamID().getValue())
                            .addParameter("VsConference", getVsConference().getValue())
                            .addParameter("VsDivision", getVsDivision().getValue())
                            .addParameter("Weight", getWeight().getValue());

        return uriBuilder.toString();
    }

}
