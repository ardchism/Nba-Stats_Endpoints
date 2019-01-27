package com.nbastat.player.generators;

import com.nbastat.player.generators.factories.BuilderFactory;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class DomainBuildersGenerator {

    public static Map<String, String> generateBuilders(Stream<Path> pathStream) {

        Map<String, String> builders = new HashMap<>();

        pathStream.map(file -> file.toString()
                                   .replace("src/main/java/", "")
                                   .replace("/", ".")
                                   .replace(".java", ""))
                  .forEach(fullClassPackage -> builders.putAll(createBuilder(fullClassPackage)));

        return builders;

    }

    @SneakyThrows
    private  static Map<String, String> createBuilder(String fullClassPackage){
        BuilderFactory builderFactory = new BuilderFactory();
        return builderFactory.build(fullClassPackage);
    }

    @SneakyThrows
    public static void writeSourceCodeToFile(String className, String code){

        String filePath = "src/test/generated/java/" + className + ".java";
        String directoryPath = filePath.substring(0, filePath.lastIndexOf("/"));

        File directory = new File(directoryPath);
        if(!directory.exists()) {
            directory.mkdirs();
        } else {
            directory.delete();
            directory.mkdirs();
        }

        Files.write(Paths.get(filePath), code.getBytes());
        System.out.println("Builder written to " + filePath);

    }
}
