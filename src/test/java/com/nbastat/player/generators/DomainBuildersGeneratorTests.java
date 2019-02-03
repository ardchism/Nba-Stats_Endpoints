package com.nbastat.player.generators;

import lombok.SneakyThrows;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class DomainBuildersGeneratorTests {

    @Test
    public void generateBuilderNoInnerClassesHappyPath() {

        String testClass = "com.nbastat.player.generators.testPOJO.BasicPOJO";

        String expectedBuilderSourceCode = "package com.nbastat.player.generators.testPOJO.builders;\n" +
                "\n" +
                "import com.nbastat.player.generators.testPOJO.BasicPOJO;\n" +
                "import java.lang.String;\n" +
                "import java.lang.Integer;\n" +
                "\n" +
                "public class BasicPOJOBuilder {\n" +
                "\n" +
                "\tprivate final BasicPOJO builtObject;\n" +
                "\n" +
                "\tprivate BasicPOJOBuilder() {\n" +
                "\t\tthis.builtObject = new BasicPOJO();\n" +
                "\t}\n" +
                "\n" +
                "\tpublic static BasicPOJOBuilder Builder() {\n" +
                "\t\treturn new BasicPOJOBuilder();\n" +
                "\t}\n" +
                "\n" +
                "\tpublic BasicPOJOBuilder withPlayerName(String playerName) {\n" +
                "\t\tbuiltObject.setPlayerName(playerName);\n" +
                "\t\treturn this;\n" +
                "\t}\n" +
                "\n" +
                "\tpublic BasicPOJOBuilder withPlayerId(Integer playerId) {\n" +
                "\t\tbuiltObject.setPlayerId(playerId);\n" +
                "\t\treturn this;\n" +
                "\t}\n" +
                "\n" +
                "\tpublic BasicPOJOBuilder withPayerStats(Integer payerStats) {\n" +
                "\t\tbuiltObject.getPayerStats().add(payerStats);\n" +
                "\t\treturn this;\n" +
                "\t}\n" +
                "\n" +
                " \tpublic BasicPOJO get() {\n" +
                " \t\treturn builtObject;\n" +
                " \t}\n" +
                "}";

        Map<String, String> sourceCode = DomainBuildersGenerator.createBuilder(testClass);

        assertThat(sourceCode.size()).isEqualTo(1);

        String code = sourceCode.get(
                "com/nbastat/player/generators/testPOJO/builders/BasicPOJOBuilder");

        assertThat(code).isNotNull();

        assertThat(code).isEqualToNormalizingWhitespace(expectedBuilderSourceCode);


    }

    @Test
    public void generateBuilderNestedClassesHappyPath() {

        String testClass = "com.nbastat.player.generators.testPOJO.NestedPOJO";

        String expectedBuilderSourceCode =
                "package com.nbastat.player.generators.testPOJO.builders;\n" +
                        "\n" +
                        "import com.nbastat.player.generators.testPOJO.NestedPOJO;\n" +
                        "import com.nbastat.player.generators.testPOJO.NestedPOJO.Player;\n" +
                        "import com.nbastat.player.generators.testPOJO.NestedPOJO.Stat;\n" +
                        "\n" +
                        "        public class NestedPOJOBuilder {\n" +
                        "\n" +
                        "            private final NestedPOJO builtObject;\n" +
                        "\n" +
                        "            private NestedPOJOBuilder() {\n" +
                        "                this.builtObject = new NestedPOJO();\n" +
                        "            }\n" +
                        "\n" +
                        "            public static NestedPOJOBuilder Builder() {\n" +
                        "                return new NestedPOJOBuilder();\n" +
                        "            }\n" +
                        "\n" +
                        "            public NestedPOJOBuilder withPlayer(Player player) {\n" +
                        "                builtObject.setPlayer(player);\n" +
                        "                return this;\n" +
                        "            }\n" +
                        "\n" +
                        "            public NestedPOJOBuilder withStats(Stat stats) {\n" +
                        "                builtObject.getStats().add(stats);\n" +
                        "                return this;\n" +
                        "            }\n" +
                        "\n" +
                        "           public NestedPOJO get() {\n" +
                        "               return builtObject;\n" +
                        "           }\n" +
                        "\n" +
                        "        }";

        Map<String, String> sourceCode = DomainBuildersGenerator.createBuilder(testClass);

        assertThat(sourceCode.size()).isEqualTo(3);

        String code = sourceCode.get(
                "com/nbastat/player/generators/testPOJO/builders/NestedPOJOBuilder");

        assertThat(code).isNotNull();

        assertThat(code).isEqualToNormalizingWhitespace(expectedBuilderSourceCode);


    }

    @Test
    @SneakyThrows
    public void generateBuildersHappyPath() {

        String testPackage = "src/test/java/com/nbastat/player/generators/testPOJO";
        Stream<Path> pathStream = Files.walk(Paths.get(testPackage), 1)
                                       .filter(Files::isRegularFile);

        Map<String, String> sourceCode = DomainBuildersGenerator.generateBuilders(pathStream);

        assertThat(sourceCode.size()).isEqualTo(4);

    }

}
