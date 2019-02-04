package com.nbastat.player.generators;

import com.nbastat.player.generators.factories.BuilderFactory;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.stream.Stream;


public class DomainBuildersGenerator {

    public static GeneratedClasses generateBuilders(Stream<Path> pathStream) {

        GeneratedClasses generatedClasses = new GeneratedClasses();

        pathStream.map(file -> file.toString()
                                   .replace("src/main/java/", "")
                                   .replace("src/test/java/", "")
                                   .replace("/", ".")
                                   .replace(".java", ""))
                .forEach(fullClassPackage -> generatedClasses.putAll(createBuilder(fullClassPackage)));

        return generatedClasses;

    }

    @SneakyThrows
    public static GeneratedClasses createBuilder(String fullClassPackage) {
        BuilderFactory builderFactory = new BuilderFactory();
        return builderFactory.build(fullClassPackage);
    }

}
