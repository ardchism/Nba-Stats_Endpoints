package com.nbastat.player.generators;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class GeneratedClasses {

    private final Map<String, String> className2SourceCodeMap = new HashMap<>();

    public String get(String className) {
        return getClassName2SourceCodeMap().get(className);
    }

    public Integer size() {
        return getClassName2SourceCodeMap().size();
    }

    public void put(String className, String sourceCode) {
        className2SourceCodeMap.put(className, sourceCode);
    }

    public void putAll(Collection<GeneratedClasses> generatedClasses) {

        generatedClasses.forEach(this::putAll);

    }

    public void putAll(GeneratedClasses generatedClasses) {
        getClassName2SourceCodeMap().putAll(generatedClasses.getClassName2SourceCodeMap());
    }


    public void writeClasses() {
        getClassName2SourceCodeMap().forEach(this::writeClass);
    }

    @SneakyThrows
    private void writeClass(String className, String code) {

        String filePath = "src/test/generated/java/" + className + ".java";
        String directoryPath = filePath.substring(0, filePath.lastIndexOf("/"));

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        } else {
            directory.delete();
            directory.mkdirs();
        }

        Files.write(Paths.get(filePath), code.getBytes());
        System.out.println("GeneratedClasses written to " + filePath);

    }

}
