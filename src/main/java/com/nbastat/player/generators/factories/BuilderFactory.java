package com.nbastat.player.generators.factories;

import com.failfast.sourcewriter.domain.*;
import com.failfast.sourcewriter.domain.SourceCode.SourceCodeBuilder;
import com.failfast.sourcewriter.domain.SourceCodeField.SourceCodeFieldBuilder;
import com.failfast.sourcewriter.domain.SourceCodeMethod.SourceCodeMethodBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Stream;

public class BuilderFactory {

    public Map<String, String> build(String fullClassName) throws ClassNotFoundException {
        Class<?> currentClass = Class.forName(fullClassName);
        return buildFromClass(currentClass, fullClassName);
    }

    private Map<String, String> buildFromClass(Class<?> currentClass, String fullClassName) {

        Map<String, String> builders = new HashMap<>();

        List<SourceCodeField> sourceCodeFields = new ArrayList<>();
        List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();

        String packageName = currentClass.getPackage()
                                         .getName() + ".builders";
        String className = currentClass.getSimpleName() + "Builder";

        // create builtObject
        SourceCodeField builtObjectField = SourceCodeFieldBuilder.Builder(currentClass,
                                                                          "builtObject")
                                                                 .withIsFinal(true)
                                                                 .withFieldAccessLevel(
                                                                         FieldAccessLevel.PRIVATE)
                                                                 .build();
        sourceCodeFields.add(builtObjectField);

        // create constructor
        SourceCodeMethod constructor = SourceCodeMethodBuilder.ConstructorBuilder(
                MethodAccessLevel.PRIVATE,
                "this.builtObject = new " + currentClass.getSimpleName() + "();")
                                                              .build();

        // create static builder method
        SourceCodeMethod staticBuilderMethod = SourceCodeMethodBuilder.Builder(
                MethodAccessLevel.PUBLIC, true, "Builder", "return new " + className + "();")
                                                                      .withMethodReturnSelf()
                                                                      .build();
        sourceCodeMethods.add(staticBuilderMethod);

        // create withMethods for each field in class
        Stream<Field> fields = Arrays.stream(currentClass.getDeclaredFields());
        fields.forEach(field -> {

            String fieldType = field.getType()
                                    .getSimpleName();
            String fieldName = field.getName()
                                    .substring(0, 1)
                                    .toUpperCase() + field.getName()
                                                          .substring(1);

            if(fieldType.contains("List")) {

                ParameterizedType listType = (ParameterizedType) field.getGenericType();

                Type listGenericType = listType.getActualTypeArguments()[0];

                Class<?> listTypeGenericClass = null;
                if(listGenericType.toString()
                                  .contains("<")) {

                    try {
                        listTypeGenericClass = Class.forName(listGenericType.getTypeName()
                                                                            .substring(0,
                                                                                       listGenericType.getTypeName()
                                                                                                      .indexOf(
                                                                                                              "<")));
                    } catch(ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                } else {

                    listTypeGenericClass = (Class<?>) listType.getActualTypeArguments()[0];

                }

                assert listTypeGenericClass != null;
                SourceCodeMethod withMethod = SourceCodeMethodBuilder.Builder(
                        MethodAccessLevel.PUBLIC, false, "with" + fieldName,
                        "builtObject.get" + fieldName + "().add(" + field.getName() + ");\n return this;")
                                                                     .withMethodReturnSelf()
                                                                     .withParameter(
                                                                             listTypeGenericClass,
                                                                             field.getName())
                                                                     .build();
                sourceCodeMethods.add(withMethod);

            } else {

                SourceCodeMethod withMethod = SourceCodeMethodBuilder.Builder(
                        MethodAccessLevel.PUBLIC, false, "with" + fieldName,
                        "builtObject.set" + fieldName + "(" + field.getName() + ");\n return this;")
                                                                     .withMethodReturnSelf()
                                                                     .withParameter(field.getType(),
                                                                                    field.getName())
                                                                     .build();
                sourceCodeMethods.add(withMethod);
            }

        });

        SourceCode sourceCode = SourceCodeBuilder.Builder(className, packageName)
                                                 .withSourceCodeConstructor(constructor)
                                                 .withSourceCodeFields(sourceCodeFields)
                                                 .withSourceCodeMethods(sourceCodeMethods)
                                                 .build();


        builders.put(packageName.replace(".", "/") + "/" + className,
                     sourceCode.getFormattedSourceCode());

        currentClass.getDeclaredClasses();
        Stream<Class<?>> innerClasses = Arrays.stream(currentClass.getDeclaredClasses());
        innerClasses.forEach(innerClass -> {
            System.out.println();
            builders.putAll(buildFromClass(innerClass, fullClassName));
        });

        return builders;
    }

}
