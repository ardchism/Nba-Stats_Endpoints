package com.nbastat.player.generators;

import com.failfast.sourcewriter.domain.FieldAccessLevel;
import com.failfast.sourcewriter.domain.SourceCodeEnum;
import com.failfast.sourcewriter.domain.SourceCodeEnum.SourceCodeEnumBuilder;
import com.failfast.sourcewriter.domain.SourceCodeField;
import com.failfast.sourcewriter.domain.SourceCodeField.SourceCodeFieldBuilder;
import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ApiGenerator {

    private static final String API_PARAMETER_PACKAGE = "com.nbastat.player.domain";

    public static GeneratedClasses generateApiParameter(NBAStatParameter nbaStatParameter) {

        List<Class<?>> annotaions = new ArrayList<>();
        annotaions.add(AllArgsConstructor.class);
        annotaions.add(Getter.class);

        SourceCodeField sourceCodeField =
            SourceCodeFieldBuilder.Builder(String.class, "value")
                                  .withFieldAccessLevel(FieldAccessLevel.PRIVATE)
                                  .withIsFinal(Boolean.FALSE)
                                  .build();

        // TODO: Add implements to enum

        SourceCodeEnumBuilder sourceCodeEnumBuilder =
            SourceCodeEnumBuilder.Builder(nbaStatParameter.getName(), API_PARAMETER_PACKAGE)
                                 .withAnnotations(annotaions)
                                 .withField(sourceCodeField);

        nbaStatParameter.getValues()
                        .forEach(value -> sourceCodeEnumBuilder.withValueConstructor(value,
                                                                                     "\"" + formatValue(
                                                                                         value) + "\""));

        //TODO: Add override method

        SourceCodeEnum sourceCodeEnum = sourceCodeEnumBuilder.build();

        GeneratedClasses generatedClasses = new GeneratedClasses();

        generatedClasses.put(sourceCodeEnum.getFullyQualifiedClassName(),
                             sourceCodeEnum.getFormattedSourceCode());

        return generatedClasses;

    }

    private static String formatValue(String value) {
        return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(value), ' ');
    }

}
