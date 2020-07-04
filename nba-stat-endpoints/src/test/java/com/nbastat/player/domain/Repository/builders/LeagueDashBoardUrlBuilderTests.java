package com.nbastat.player.domain.Repository.builders;

import com.nbastat.player.domain.repository.builders.LeagueDashboardUrlBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LeagueDashBoardUrlBuilderTests {

    @Test
    public void buildDefaultUrlHappyPath() {

        String expectedURL = "https://stats.nba.com/stats/leaguedashplayerstats?College=&Conference=&DateFrom=&DateTo=&Division=&DraftPick=&DraftYear=&GameScope=&GameSegment=&Height=&LastNGames=0&LeagueID=00&Location=&MeasureType=Base&Month=0&OpponentTeamID=0&Outcome=&PORound=0&PaceAdjust=N&PerMode=PerGame&Period=0&PlayerExperience=&PlayerPosition=&PlusMinus=N&Rank=N&Season=2018-19&SeasonSegment=&SeasonType=Regular+Season&ShotClockRange=&StarterBench=&TeamID=0&VsConference=&VsDivision=&Weight=";

        assertThat(LeagueDashboardUrlBuilder.Builder()
                                            .build()).isEqualTo(expectedURL);

    }

}
