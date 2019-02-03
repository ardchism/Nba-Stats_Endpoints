package com.nbastat.player.generators.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition;
import com.nbastat.player.utils.FileReader;
import lombok.SneakyThrows;

public class NBAStatEndpointsDefinitionFactory {

    @SneakyThrows
    public NBAStatEndpointsDefinition buildFromJsonFile(String fileName) {

        FileReader fileReader = new FileReader();
        String nbaStatJson = fileReader.getStringFromFile(fileName);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(nbaStatJson,
                                NBAStatEndpointsDefinition.class);
    }
}
