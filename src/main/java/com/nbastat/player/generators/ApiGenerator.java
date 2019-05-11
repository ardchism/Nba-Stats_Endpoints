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

class ApiGenerator {

    private static final String API_PARAMETER_PACKAGE = "com.nbastat.player.domain";

    static GeneratedClasses generateApiParameter(NBAStatParameter nbaStatParameter) {

        if(nbaStatParameter.shouldSkipGeneratingParameter()) {
            return new MainGeneratedClasses();
        }

        List<Class<?>> annotaions = new ArrayList<>();
        annotaions.add(AllArgsConstructor.class);
        annotaions.add(Getter.class);

        SourceCodeField sourceCodeField =
            SourceCodeFieldBuilder.Builder(String.class, "value")
                                  .withFieldAccessLevel(FieldAccessLevel.PRIVATE)
                                  .withIsFinal(Boolean.FALSE)
                                  .build();

        SourceCodeEnumBuilder sourceCodeEnumBuilder = SourceCodeEnumBuilder.Builder(
            nbaStatParameter.getName(), API_PARAMETER_PACKAGE)
                                                                           .withInterface(
                                                                               ApiParameter.class)
                                                                           .withAnnotations(
                                                                               annotaions)
                                                                           .withField(
                                                                               sourceCodeField);

        if(StringUtils.isEmpty(nbaStatParameter.getDefaultValue())) {

            SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                MethodAccessLevel.PUBLIC, Boolean.FALSE, "getDefaultValue", "return null;")
                                                                       .withMethodReturnType(
                                                                           ApiParameter.class)
                                                                       .withAnnotation(
                                                                           Override.class)
                                                                       .build();

            sourceCodeEnumBuilder.withMethod(sourceCodeMethod);

        } else {

            SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                MethodAccessLevel.PUBLIC, Boolean.FALSE, "getDefaultValue",
                "return " + formatEnumName(nbaStatParameter.getDefaultValue()) + ";")
                                                                       .withMethodReturnType(
                                                                           ApiParameter.class)
                                                                       .withAnnotation(
                                                                           Override.class)
                                                                       .build();

            sourceCodeEnumBuilder.withMethod(sourceCodeMethod);

            if(nbaStatParameter.getValues()
                               .stream()
                               .noneMatch(value -> value.equalsIgnoreCase(
                                   nbaStatParameter.getDefaultValue()))) {
                sourceCodeEnumBuilder.withValueConstructor(
                    formatEnumName(nbaStatParameter.getDefaultValue()),
                    formatValue(nbaStatParameter.getDefaultValue()));
            }

        }

        nbaStatParameter.getValues()
                        .forEach(value -> sourceCodeEnumBuilder.withValueConstructor(
                            formatEnumName(value), formatValue(value)));

        SourceCodeEnum sourceCodeEnum = sourceCodeEnumBuilder.build();

        GeneratedClasses generatedClasses = new MainGeneratedClasses();

        generatedClasses.put(sourceCodeEnum.getFullyQualifiedClassName()
                                           .replace(".", "/"),
                             sourceCodeEnum.getFormattedSourceCode());

        return generatedClasses;

    }

    private static String formatEnumName(String enumName) {

        if(enumName.split("\\|").length > 2) {
            throw new RuntimeException(enumName + " contains more than one |. Not allowed because" +
                                           " | is used for key pair values");
        }

        enumName = enumName.split("\\|")[0];

        String name = enumName;

        if(StringUtils.startsWithAny(name, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9")) {
            name = "_" + name;
        }

        return name.replace(" ", "")
                   .replace("(", "_")
                   .replace("-", "_")
                   .replace(")", "")
                   .replace("&", "_")
                   .replace(".", "_")
                   .replace("'", "");
    }

    private static String formatValue(String value) {

        if(value.split("\\|").length > 2) {
            throw new RuntimeException(value + " contains more than one |. Not allowed because" +
                                           " | is used for key pair values");
        }

        if(value.split("\\|").length > 1)
            value = value.split("\\|")[1];

        return "\"" + value + "\"";
    }

}
