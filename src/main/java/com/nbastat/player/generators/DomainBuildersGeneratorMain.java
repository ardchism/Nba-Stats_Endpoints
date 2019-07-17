package com.nbastat.player.generators;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class DomainBuildersGeneratorMain {

    public static void main(String[] args) {

//        if(args.length == 0) {
//            throw new RuntimeException("Minimum of 1 path parameter required....");
//        }
//
//        Arrays.stream(args)
//              .forEach(DomainBuildersGeneratorMain::generateBuilders);

        int i = 010;
        int j = 07;
        System.out.print(i);
        System.out.print(j);

    }

    @SneakyThrows
    private static void generateBuilders(String targetPackagePath){

        Stream<Path> pathStream = Files.walk(Paths.get(targetPackagePath), 1).filter(Files::isRegularFile);
        GeneratedClasses generatedClasses = DomainBuildersGenerator.generateBuilders(pathStream);
        pathStream.close();

        generatedClasses.writeClasses();

    }

}
