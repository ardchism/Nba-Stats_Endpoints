package com.nbastat.player.generators.factories;

import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatEndpoint;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatHeaders;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;
import com.nbastat.player.generators.domain.builders.NBAStatEndpointBuilder;
import com.nbastat.player.generators.domain.builders.NBAStatEndpointsDefinitionBuilder;
import com.nbastat.player.generators.domain.builders.NBAStatHeadersBuilder;
import com.nbastat.player.generators.domain.builders.NBAStatParameterBuilder;
import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NBAStatEndpointsDefinitionFactoryTests {

    @Test
    public void buildFromFileNameHappyPath() {

        String testFileName = "testNbaStat.json";

        NBAStatHeaders headers = NBAStatHeadersBuilder.Builder()
                                                      .withHost("stats.nba.com")
                                                      .withReferer(
                                                              "http://stats.nba.com/players/t…17&SeasonType=Regular%20Season")
                                                      .withUserAgent(
                                                              "Mozilla/5.0 (X11; Fedora; Linu…) Gecko/20100101 Firefox/59.0")
                                                      .withXnbastatsOrigin("stats")
                                                      .withXnbastatsToken("true")
                                                      .get();

        NBAStatEndpoint endpoints = NBAStatEndpointBuilder.Builder()
                                                          .withName("league Leaders")
                                                          .withUrl(
                                                                  "http://stats.nba.com/stats/leaguedashplayerstats")
                                                          .withRequiredParameters("MeasureType")
                                                          .withRequiredParameters("PerMode")
                                                          .withRequiredParameters("Season")
                                                          .withRequiredParameters("SeasonType")
                                                          .withParameters("College")
                                                          .withParameters("Conference")
                                                          .withParameters("Country")
                                                          .withParameters("MeasureType")
                                                          .withParameters("PerMode")
                                                          .withParameters("Season")
                                                          .withParameters("SeasonType")
                                                          .get();


        NBAStatParameter parameter = NBAStatParameterBuilder.Builder()
                                                            .withName("MeasureType")
                                                            .withDefaultValue("Base")
                                                            .withValues("Defense")
                                                            .withValues("Usage")
                                                            .withValues("Opponent")
                                                            .withValues("Scoring")
                                                            .withValues("Four Factors")
                                                            .withValues("Misc")
                                                            .withValues("Advanced")
                                                            .withValues("Base")
                                                            .get();

        NBAStatEndpointsDefinition dummyNbaStatsEndpointsDefinition
                = NBAStatEndpointsDefinitionBuilder.Builder()
                                                   .withHeaders(headers)
                                                   .withEndpoints(endpoints)
                                                   .withParameters(parameter)
                                                   .get();


        NBAStatEndpointsDefinitionFactory nbaStatEndpointsDefinitionFactory = new NBAStatEndpointsDefinitionFactory();

        NBAStatEndpointsDefinition nbaStatEndpointsDefinition
                = nbaStatEndpointsDefinitionFactory.buildFromJsonFile(testFileName);

        assertThat(nbaStatEndpointsDefinition.getHeaders()).isEqualTo(headers);

        assertThat(
                nbaStatEndpointsDefinition.getEndpoints()
                                          .size())
                .isEqualTo(dummyNbaStatsEndpointsDefinition.getEndpoints()
                                                           .size()
                );
        for(int x = 0; x < nbaStatEndpointsDefinition.getEndpoints()
                                                     .size(); x++) {
            assertThat(
                    nbaStatEndpointsDefinition.getEndpoints()
                                              .get(x))
                    .isEqualTo(dummyNbaStatsEndpointsDefinition.getEndpoints()
                                                               .get(x)
                    );
        }

    }

    @SuppressWarnings("unused")
    @Test
    public void buildFromFileNameGrumpyPathFileNotFound() {

        String testFileName = "testFileNotFound.json";

        NBAStatEndpointsDefinitionFactory nbaStatEndpointsDefinitionFactory = new NBAStatEndpointsDefinitionFactory();

        try {
            NBAStatEndpointsDefinition nbaStatEndpointsDefinition = nbaStatEndpointsDefinitionFactory.buildFromJsonFile(
                    testFileName);
            Assert.fail("Test should not make it this far");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("File Not Found At : " + testFileName);
        }

    }
}
