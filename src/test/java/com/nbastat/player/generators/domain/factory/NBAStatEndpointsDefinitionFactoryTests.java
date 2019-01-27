package com.nbastat.player.generators.domain.factory;

import com.nbastat.player.domain.builders.NBAStatHeadersBuilder;
import com.nbastat.player.domain.builders.NBAStatParameterBuilder;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatEndpoint;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatHeaders;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NBAStatEndpointsDefinitionFactoryTests {

    @Test
    public void buildFromFileNameHappyPath() {

        String testFileName = "testNbaStat.json";

        NBAStatHeaders headers = NBAStatHeadersBuilder.builder()
                                                      .build()
                                                      .withHost("stats.nba.com")
                                                      .withReferer(
                                                              "http://stats.nba.com/players/t…17&SeasonType=Regular%20Season")
                                                      .withUserAgent(
                                                              "Mozilla/5.0 (X11; Fedora; Linu…) Gecko/20100101 Firefox/59.0")
                                                      .withXnbastatsOrigin("stats")
                                                      .withXnbastatsToken("true")
                                                      .get();

        NBAStatEndpoint endpoints = new NBAStatEndpoint();
        endpoints.setName("league Leaders");
        endpoints.setUrl("http://stats.nba.com/stats/leaguedashplayerstats");
        endpoints.getRequiredParameters()
                 .add("MeasureType");
        endpoints.getRequiredParameters()
                 .add("PerMode");
        endpoints.getRequiredParameters()
                 .add("Season");
        endpoints.getRequiredParameters()
                 .add("SeasonType");
        endpoints.getParameters()
                 .add("College");
        endpoints.getParameters()
                 .add("Conference");
        endpoints.getParameters()
                 .add("Country");
        endpoints.getParameters()
                 .add("MeasureType");
        endpoints.getParameters()
                 .add("PerMode");
        endpoints.getParameters()
                 .add("Season");
        endpoints.getParameters()
                 .add("SeasonType");

        List<String> values = new ArrayList<>();
        values.add("Defense");
        values.add("Usage");
        values.add("Opponent");
        values.add("Scoring");
        values.add("Four Factors");
        values.add("Misc");
        values.add("Advanced");
        values.add("Base");

        NBAStatParameter parameter = NBAStatParameterBuilder.builder()
                                                            .build()
                                                            .withName("MeasureType")
                                                            .withDefaultValue("Base")
                                                            .withValues(values)
                                                            .get();

        NBAStatEndpointsDefinition dummyNbaStatsEndointsDefinition = new NBAStatEndpointsDefinition();
        dummyNbaStatsEndointsDefinition.setHeaders(headers);
        dummyNbaStatsEndointsDefinition.getEndpoints()
                                       .add(endpoints);
        dummyNbaStatsEndointsDefinition.getParameters()
                                       .add(parameter);

        NBAStatEndpointsDefinitionFactory nbaStatEndpointsDefinitionFactory = new NBAStatEndpointsDefinitionFactory();

        NBAStatEndpointsDefinition nbaStatEndpointsDefinition = nbaStatEndpointsDefinitionFactory.buildFromJsonFile(
                testFileName);

        assertThat(nbaStatEndpointsDefinition.getHeaders()).isEqualTo(headers);

        assertThat(nbaStatEndpointsDefinition.getEndpoints()
                                             .size()).isEqualTo(
                dummyNbaStatsEndointsDefinition.getEndpoints()
                                               .size());
        for(int x = 0; x < nbaStatEndpointsDefinition.getEndpoints()
                                                     .size(); x++) {
            assertThat(nbaStatEndpointsDefinition.getEndpoints()
                                                 .get(x)).isEqualTo(
                    dummyNbaStatsEndointsDefinition.getEndpoints()
                                                   .get(x));
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
