package com.nbastat.player.generators;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.nbastat.player.generators.factories.BuilderFactory;

public class DomainBuildersGenerator {

	public static void main(String[] args) {

		Map<String, String> builders = new HashMap<>();

		if (args.length == 0) {
			throw new RuntimeException("Minimum of 1 path parameter required....");
		}

		for (String targetPackagePath : args) {

			Stream<Path> paths;
			try {
				paths = Files.walk(Paths.get(targetPackagePath), 1);
				paths.filter(Files::isRegularFile)
						.forEach(file -> {
							String fullClassPackage = file.toString().replace("src/main/java/", "").replace("/", ".")
									.replace(".java", "");
							BuilderFactory builderFactory = new BuilderFactory();
							try {
								builders.putAll(builderFactory.build(fullClassPackage));
							}
							catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						});

				paths.close();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}

			builders.forEach((key, value) -> {
				try {
					String filePath = "src/test/generated/java/" + key + ".java";
					String directoryPath = filePath.substring(0, filePath.lastIndexOf("/"));

					File directory = new File(directoryPath);
					if (!directory.exists()) {
						directory.mkdirs();
					}
					else {
						directory.delete();
						directory.mkdirs();
					}

					Files.write(Paths.get(filePath), value.getBytes());
					System.out.println("Builder written to " + filePath);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

	}

}
