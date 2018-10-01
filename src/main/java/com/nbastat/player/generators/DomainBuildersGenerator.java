package com.nbastat.player.generators;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.nbastat.player.generators.factories.BuilderFactory;

import lombok.SneakyThrows;

public class DomainBuildersGenerator {

	@SneakyThrows
	public static void main(String[] args) {

		try (Stream<Path> paths = Files.walk(Paths.get("src/main/java/com/nbastat/player/generators/domain"), 1)) {
		    paths
		        .filter(Files::isRegularFile)
		        .forEach(file -> {
		        	String fullClassPackage = file.toString().replace("src/main/java/", "").replace("/", ".").replace(".java", "");
		        	BuilderFactory builderFactory = new BuilderFactory();
		        	builderFactory.build(fullClassPackage);
		        });
		} 		
	}

}
