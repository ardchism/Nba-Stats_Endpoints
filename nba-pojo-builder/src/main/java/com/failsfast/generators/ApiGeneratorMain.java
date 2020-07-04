package com.failsfast.generators;

import com.failsfast.generators.domain.NBAStatEndpointsDefinition;
import com.failsfast.generators.factories.NBAStatEndpointsDefinitionFactory;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiGeneratorMain {

    @SneakyThrows
    public static void main(String[] args) {

        if(args.length == 0) {
            throw new RuntimeException("Minimum of 1 path parameter required....");
        }

        Arrays.stream(args)
              .forEach(ApiGeneratorMain::generateParameters);

    }

    @SneakyThrows
    private static void generateParameters(String path2NbaJson) {

        NBAStatEndpointsDefinitionFactory nbaStatEndpointsDefinitionFactory =
            new NBAStatEndpointsDefinitionFactory();

        NBAStatEndpointsDefinition nbaStatEndpointsDefinition =
            nbaStatEndpointsDefinitionFactory.buildFromJsonFile(path2NbaJson);

        List<GeneratedClasses> generatedClasses = nbaStatEndpointsDefinition.getParameters()
                                                                            .stream()
                                                                            .map(
                                                                                ApiGenerator::generateApiParameter)
                                                                            .collect(
                                                                                Collectors.toList());

        generatedClasses.forEach(GeneratedClasses::writeClasses);

    }
}
