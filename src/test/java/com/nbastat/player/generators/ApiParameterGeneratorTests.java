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
                                   .withValues("D'efense")
                                   .withValues("Four Factors")
                                   .withValues("M.sc")
                                   .withValues("O&ponent")
                                   .withValues("123-456")
                                   .withValues("Usa(ge)")
                                   .get();

        String expectedSourceCode =
            "package com.nbastat.player.domain;\n" + "\n" + "import lombok.AllArgsConstructor;\n" +
            "import lombok.Getter;\n" + "import com.nbastat.player.domain.ApiParameter;\n" +
            "import java.lang.String;\n" + "\n" + "@AllArgsConstructor\n" + "@Getter\n" +
            "public enum MeasureType implements ApiParameter {\n" +
            "\tAdvanced(\"Advanced\"), Defense(\"D'efense\"), FourFactors" +
            "(\"Four Factors\"), M_sc(\n" + "\t\t\t\"M.sc\"), O_ponent(\"O&ponent\"), _123_456" +
            "(\"123-456\"), Usa_ge(\n\t\t\t\"Usa(ge)\");\n" + "\n" + "\tprivate String value;\n" +
            "\n" +
            "\t@Override\n" + "\tpublic ApiParameter getDefaultValue() {\n" + "\t\treturn Base;\n" +
            "\t}\n" + "}";

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(1);

        String code = generatedClasses.getClassName2SourceCodeMap()
                                      .get("com/nbastat/player/domain/MeasureType");

        assertThat(code).isNotNull();

        assertThat(code).isEqualTo(expectedSourceCode);

    }

    @Test
    public void generateApiParameterHappyPathNullDefaultValue() {

        NBAStatParameter nbaStatParameter = NBAStatParameterBuilder.Builder()
                                                                   .withName("MeasureType")
                                                                   .withValues("Advanced")
                                                                   .withValues("D'efense")
                                                                   .withValues("Four Factors")
                                                                   .withValues("M.sc")
                                                                   .withValues("O&ponent")
                                                                   .withValues("123-456")
                                                                   .withValues("Usa(ge)")
                                                                   .get();

        String expectedSourceCode =
            "package com.nbastat.player.domain;\n" + "\n" + "import lombok.AllArgsConstructor;\n" +
            "import lombok.Getter;\n" + "import com.nbastat.player.domain.ApiParameter;\n" +
            "import java.lang.String;\n" + "\n" + "@AllArgsConstructor\n" + "@Getter\n" +
            "public enum MeasureType implements ApiParameter {\n" +
            "\tAdvanced(\"Advanced\"), Defense(\"D'efense\"), FourFactors" +
            "(\"Four Factors\"), M_sc(\n" + "\t\t\t\"M.sc\"), O_ponent(\"O&ponent\"), _123_456" +
            "(\"123-456\"), Usa_ge(\n\t\t\t\"Usa(ge)\");\n" + "\n" + "\tprivate String value;\n" +
            "\n" + "\t@Override\n" + "\tpublic ApiParameter getDefaultValue() {\n" +
            "\t\treturn null;\n" + "\t}\n" + "}";

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(1);

        String code = generatedClasses.getClassName2SourceCodeMap()
                                      .get("com/nbastat/player/domain/MeasureType");

        assertThat(code).isNotNull();

        assertThat(code).isEqualTo(expectedSourceCode);

    }

    //TODO: Add java integer test
    @Test
    public void generateApiParameterHappyPathJavaInteger() {

        NBAStatParameter nbaStatParameter = NBAStatParameterBuilder.Builder()
                                                                   .withName("LastNGames")
                                                                   .get();

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(0);

    }

    //TODO: Return integer when not java integer

    //TODO: SuperObject Tests

    //TODO: JavaMonth Tests


}
