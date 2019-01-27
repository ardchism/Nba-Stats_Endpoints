package com.nbastat.player.generators;

import com.nbastat.player.generators.factories.BuilderFactory;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class DomainBuildersGeneratorMain {

    public static void main(String[] args) {

        if(args.length == 0) {
            throw new RuntimeException("Minimum of 1 path parameter required....");
        }

        Arrays.stream(args).forEach(domainPath -> generateBuilders(domainPath));

    }

    @SneakyThrows
    private static void generateBuilders(String targetPackagePath){

        Stream<Path> pathStream = Files.walk(Paths.get(targetPackagePath), 1).filter(Files::isRegularFile);
        Map<String, String> buildersMap = DomainBuildersGenerator.generateBuilders(pathStream);
        pathStream.close();

        buildersMap.forEach((key, value) -> DomainBuildersGenerator.writeSourceCodeToFile(key, value));

    }

}
