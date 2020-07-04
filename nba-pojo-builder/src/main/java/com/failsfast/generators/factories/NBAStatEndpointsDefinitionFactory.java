package com.failsfast.generators.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.failsfast.generators.domain.NBAStatEndpointsDefinition;
import com.failsfast.generators.utils.FileReader;
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
