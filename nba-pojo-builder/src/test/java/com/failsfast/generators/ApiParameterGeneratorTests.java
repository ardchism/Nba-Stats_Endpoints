package com.failsfast.generators;

import com.failsfast.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;
import com.failsfast.generators.domain.builders.NBAStatParameterBuilder;
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
            "package com.nbastat.player.domain;\n" +
                "\n" +
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.Getter;\n" +
                "import com.failsfast.generators.domain.ApiParameter;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "@AllArgsConstructor\n" +
                "@Getter\n" +
                "public enum MeasureType implements ApiParameter {\n" +
                "\tBase(\"Base\"), Advanced(\"Advanced\"), Defense(\"D'efense\"), FourFactors(\n" +
                "\t\t\t\"Four Factors\"), M_sc(\"M.sc\"), O_ponent(\"O&ponent\"), _123_456(\n" +
                "\t\t\t\"123-456\"), Usa_ge(\"Usa(ge)\");\n" +
                "\n" +
                "\tprivate String value;\n" +
                "\n" +
                "\t@Override\n" +
                "\tpublic ApiParameter getDefaultValue() {\n" +
                "\t\treturn Base;\n" +
                "\t}\n" +
                "}";

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
                                                                   .withValues("123456")
                                                                   .withValues("Usa(ge)")
                                                                   .get();

        String expectedSourceCode =
            "package com.nbastat.player.domain;\n" +
                "\n" +
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.Getter;\n" +
                "import com.failsfast.generators.domain.ApiParameter;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "@AllArgsConstructor\n" +
                "@Getter\n" +
                "public enum MeasureType implements ApiParameter {\n" +
                "\tDefault(\"\"), Advanced(\"Advanced\"), Defense(\"D'efense\"), FourFactors(\n" +
                "\t\t\t\"Four Factors\"), M_sc(\"M.sc\"), O_ponent(\"O&ponent\"), _123456(\n" +
                "\t\t\t\"123456\"), Usa_ge(\"Usa(ge)\");\n" +
                "\n" +
                "\tprivate String value;\n" +
                "\n" +
                "\t@Override\n" +
                "\tpublic ApiParameter getDefaultValue() {\n" +
                "\t\treturn Default;\n" +
                "\t}\n" +
                "}";

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
    public void generateApiParameterHappyPathJavaInteger() {

        NBAStatParameter nbaStatParameter = NBAStatParameterBuilder.Builder()
                                                                   .withName("LastNGames")
                                                                   .withIsJavaInteger(true)
                                                                   .get();

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(0);

    }

    @Test
    public void generateApiParameterHappyPathNumericDefaultValue() {

        NBAStatParameter nbaStatParameter =
            NBAStatParameterBuilder.Builder()
                                   .withName("MeasureType")
                                   .withDefaultValue("0")
                                   .withValues("0")
                                   .withValues("D'efense")
                                   .withValues("Four Factors")
                                   .withValues("M.sc")
                                   .withValues("O&ponent")
                                   .withValues("123456")
                                   .withValues("Usa(ge)")
                                   .get();

        String expectedSourceCode =
            "package com.nbastat.player.domain;\n" +
                "\n" +
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.Getter;\n" +
                "import com.failsfast.generators.domain.ApiParameter;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "@AllArgsConstructor\n" +
                "@Getter\n" +
                "public enum MeasureType implements ApiParameter {\n" +
                "\t_0(\"0\"), Defense(\"D'efense\"), FourFactors(\"Four Factors\"), M_sc(\"M.sc\"), O_ponent(\n" +
                "\t\t\t\"O&ponent\"), _123456(\"123456\"), Usa_ge(\"Usa(ge)\");\n" +
                "\n" +
                "\tprivate String value;\n" +
                "\n" +
                "\t@Override\n" +
                "\tpublic ApiParameter getDefaultValue() {\n" +
                "\t\treturn _0;\n" +
                "\t}\n" +
                "}";

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
    public void generateApiParameterHappyPathSuperObject() {

        NBAStatParameter nbaStatParameter = NBAStatParameterBuilder.Builder()
                                                                   .withName("LastNGames")
                                                                   .withSuperObject("Test")
                                                                   .get();

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(0);

    }

    @Test
    public void generateApiParameterHappyPathJavaDate() {

        NBAStatParameter nbaStatParameter = NBAStatParameterBuilder.Builder()
                                                                   .withName("LastNGames")
                                                                   .withIsJavaDate(true)
                                                                   .get();

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(0);

    }

    @Test
    public void generateApiParameterHappyPathJavaMonth() {

        NBAStatParameter nbaStatParameter = NBAStatParameterBuilder.Builder()
                                                                   .withName("LastNGames")
                                                                   .withIsJavaMonth(true)
                                                                   .get();

        // call gen
        GeneratedClasses generatedClasses = ApiGenerator.generateApiParameter(nbaStatParameter);

        // assert
        assertThat(generatedClasses.size()).isEqualTo(0);

    }

    @Test
    public void generateApiParameterHappyPathSplitByMinus() {

        NBAStatParameter nbaStatParameter =
            NBAStatParameterBuilder.Builder()
                                   .withName("MeasureType")
                                   .withDefaultValue("0")
                                   .withValues("0")
                                   .withValues("D'efense")
                                   .withValues("Four Factors")
                                   .withValues("M.sc")
                                   .withValues("O&ponent")
                                   .withValues("123|456")
                                   .withValues("Usa(ge)")
                                   .get();

        String expectedSourceCode =
            "package com.nbastat.player.domain;\n" +
                "\n" +
                "import lombok.AllArgsConstructor;\n" +
                "import lombok.Getter;\n" +
                "import com.failsfast.generators.domain.ApiParameter;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "@AllArgsConstructor\n" +
                "@Getter\n" +
                "public enum MeasureType implements ApiParameter {\n" +
                "\t_0(\"0\"), Defense(\"D'efense\"), FourFactors(\"Four Factors\"), M_sc(\"M.sc\"), O_ponent(\n" +
                "\t\t\t\"O&ponent\"), _123(\"456\"), Usa_ge(\"Usa(ge)\");\n" +
                "\n" +
                "\tprivate String value;\n" +
                "\n" +
                "\t@Override\n" +
                "\tpublic ApiParameter getDefaultValue() {\n" +
                "\t\treturn _0;\n" +
                "\t}\n" +
                "}";

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
    public void generateApiParameterGrumpyMultipleMinusInValueName() {

        NBAStatParameter nbaStatParameter =
            NBAStatParameterBuilder.Builder()
                                   .withName("MeasureType")
                                   .withDefaultValue("0")
                                   .withValues("0")
                                   .withValues("D'efense")
                                   .withValues("Four Factors")
                                   .withValues("M.sc")
                                   .withValues("O&ponent")
                                   .withValues("123|4|56")
                                   .withValues("Usa(ge)")
                                   .get();

        try {
            ApiGenerator.generateApiParameter(nbaStatParameter);
            throw new RuntimeException("Test should not reach this point");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("123|4|56 contains more than one |. Not allowed " +
                                                     "because | is used for key pair values");
        }

    }


}
