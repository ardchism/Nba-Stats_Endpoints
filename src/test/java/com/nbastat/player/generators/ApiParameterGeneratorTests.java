package com.nbastat.player.generators;

import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;
import com.nbastat.player.generators.domain.builders.NBAStatParameterBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiParameterGeneratorTests {

    @Test
    public void generateApiParameterHappyPath() {

        NBAStatParameter nbaStatParameter =
            NBAStatParameterBuilder.Builder()
                                   .withName("MeasureType")
                                   .withDefaultValue("Base")
                                   .withValues("Advanced")
                                   .withValues("Defense")
                                   .withValues("FourFactors")
                                   .withValues("Misc")
                                   .withValues("Opponent")
                                   .withValues("Scoring")
                                   .withValues("Usage")
                                   .get();

        String expectedSourceCode =
            "package com.nbastat.player.domain;\n" + "\n" + "import lombok.AllArgsConstructor;\n" +
            "import lombok.Getter;\n" + "import com.nbastat.player.domain.ApiParameter;\n" +
            "import java.lang.String;\n" + "\n" + "@AllArgsConstructor\n" + "@Getter\n" +
            "public enum MeasureType implements ApiParameter {\n" +
            "\tAdvanced(\"Advanced\"), Defense(\"Defense\"), FourFactors" +
            "(\"Four Factors\"), Misc(\n" + "\t\t\t\"Misc\"), Opponent(\"Opponent\"), Scoring" +
            "(\"Scoring\"), Usage(\"Usage\");\n" + "\n" + "\tprivate String value;\n" + "\n" +
            "\t@Override\n" + "\tpublic ApiParameter getDefaultValue() {\n" + "\t\treturn Base;\n" +
            "\t}\n" + "}";

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(1);

        String code = generatedClasses.getClassName2SourceCodeMap()
                                      .get(
                                          "com.nbastat.player.domain.MeasureType");

        assertThat(code).isNotNull();

        assertThat(code).isEqualTo(expectedSourceCode);

    }

}
