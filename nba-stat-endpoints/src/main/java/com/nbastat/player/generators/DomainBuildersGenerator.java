package com.nbastat.player.generators;

import com.nbastat.player.generators.factories.BuilderFactory;
import lombok.SneakyThrows;

import java.nio.file.Path;
import java.util.stream.Stream;


class DomainBuildersGenerator {

    static GeneratedClasses generateBuilders(Stream<Path> pathStream) {

        GeneratedClasses generatedClasses = new TestGeneratedClasses();

        pathStream.map(file -> file.toString()
                                   .replace("src/main/java/", "")
                                   .replace("src/test/java/", "")
                                   .replace("/", ".")
                                   .replace(".java", ""))
                  .filter(DomainBuildersGenerator::isEnumOrInterface)
                  .forEach(fullClassPackage -> generatedClasses.putAll(createBuilder(fullClassPackage)));

        return generatedClasses;

    }

    @SneakyThrows
    private static boolean isEnumOrInterface(String fullClassPackage) {
        return !Class.forName(fullClassPackage)
                     .isEnum() && !Class.forName(fullClassPackage)
                                        .isInterface();
    }

    @SneakyThrows
    static GeneratedClasses createBuilder(String fullClassPackage) {

        BuilderFactory builderFactory = new BuilderFactory();
        return builderFactory.build(fullClassPackage);
    }

}
