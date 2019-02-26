package com.nbastat.player.generators;

import com.failfast.sourcewriter.domain.*;
import com.failfast.sourcewriter.domain.SourceCodeEnum.SourceCodeEnumBuilder;
import com.failfast.sourcewriter.domain.SourceCodeField.SourceCodeFieldBuilder;
import com.failfast.sourcewriter.domain.SourceCodeMethod.SourceCodeMethodBuilder;
import com.nbastat.player.domain.ApiParameter;
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

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
            MethodAccessLevel.PUBLIC, Boolean.FALSE, "getDefaultValue",
            "return " + nbaStatParameter.getDefaultValue() + ";")
                                                                   .withMethodReturnType(
                                                                       ApiParameter.class)
                                                                   .withAnnotation(Override.class)
                                                                   .build();

        SourceCodeEnumBuilder sourceCodeEnumBuilder =
            SourceCodeEnumBuilder.Builder(nbaStatParameter.getName(), API_PARAMETER_PACKAGE)
                                 .withInterface(ApiParameter.class)
                                 .withAnnotations(annotaions)
                                 .withField(sourceCodeField)
                                 .withMethod(sourceCodeMethod);

        nbaStatParameter.getValues()
                        .forEach(value -> sourceCodeEnumBuilder.withValueConstructor(value,
                                                                                     "\"" + formatValue(
                                                                                         value) + "\""));

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
