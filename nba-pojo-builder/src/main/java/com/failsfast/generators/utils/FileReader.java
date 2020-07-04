package com.failsfast.generators.utils;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {

    @SneakyThrows
    public String getStringFromFile(String fileName) {

        URL url = getClass().getClassLoader()
                            .getResource(fileName);

        if(url == null) {
            throw new RuntimeException("File Not Found At : " + fileName);
        }

        Path path = Paths.get(url.toURI());

        StringBuilder data = new StringBuilder();
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> data.append(line)
                                  .append("\n"));
        lines.close();

        return data.toString();
    }

}
