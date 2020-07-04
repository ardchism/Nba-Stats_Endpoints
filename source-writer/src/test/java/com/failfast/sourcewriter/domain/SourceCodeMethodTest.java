package com.failfast.sourcewriter.domain;

import com.failfast.sourcewriter.domain.SourceCodeMethod.SourceCodeMethodBuilder;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceCodeMethodTest {

    @Test
    public void buildSourceCodeMethodAsConstructorHappyPath() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodBody = "return \"test\";";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .ConstructorBuilder(expectedMethodAccessLevel, expectedMethodBody)
                .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getIsStatic()).isEqualTo((Object) false);
        assertThat(sourceCodeMethod.getIsConstructor()).isEqualTo(true);
        assertThat(sourceCodeMethod.getDoesMethodReturnSelf()).isNull();
        assertThat(sourceCodeMethod.getMethodGenericReturnType()).isNull();
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo("");
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);

    }

    @Test
    public void buildSourceCodeMethodHappyPathWithMethodReturnSelf() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, true, expectedMethodName, expectedMethodBody)
                .withMethodReturnSelf()
                .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getIsStatic()).isEqualTo((Object) true);
        assertThat(sourceCodeMethod.getDoesMethodReturnSelf()).isTrue();
        assertThat(sourceCodeMethod.getMethodGenericReturnType()).isNull();
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo(expectedMethodName);
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);

    }

    @Test
    public void buildSourceCodeMethodHappyPathWithMethodReturnType() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = String.class;
        String expectedSimpleReturnType = expectedMethodReturnType.getSimpleName();
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, true, expectedMethodName, expectedMethodBody)
                .withMethodReturnType(expectedMethodReturnType)
                .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getIsStatic()).isEqualTo((Object) true);
        assertThat(sourceCodeMethod.getMethodReturnType()).isEqualTo(expectedMethodReturnType);
        assertThat(sourceCodeMethod.getMethodGenericReturnType()).isNull();
        assertThat(sourceCodeMethod.getSimpleMethodReturnType()).isEqualTo(
                expectedSimpleReturnType);
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo(expectedMethodName);
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);

    }

    @Test
    public void buildSourceCodeMethodWithMethodParameterHappyPath() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";
        Class<?> expectedParameterType = String.class;
        String expectedParameterName = "testParameter";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, true, expectedMethodName, expectedMethodBody)
                .withMethodReturnType(expectedMethodReturnType)
                .withParameter(expectedParameterType, expectedParameterName)
                .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getMethodReturnType()).isEqualTo(expectedMethodReturnType);
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo(expectedMethodName);
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);
        assertThat(sourceCodeMethod.getMethodParameters()
                                   .containsKey(expectedParameterName)).isTrue();
        assertThat(sourceCodeMethod.getMethodParameters()
                                   .get(expectedParameterName)).isEqualTo(expectedParameterType);

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathMethodNameNull() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodBody = "return \"test\";";

        try {
            SourceCodeMethodBuilder.Builder(expectedMethodAccessLevel, true, null,
                                            expectedMethodBody)
                                   .withMethodReturnType(expectedMethodReturnType)
                                   .build();
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("methodName");
        }

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathMethodAccessLevelNull() {

        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodName = "testName";
        String expectedMethodBody = "return \"test\";";

        try {
            SourceCodeMethodBuilder.Builder(null, true, expectedMethodName, expectedMethodBody)
                                   .withMethodReturnType(expectedMethodReturnType)
                                   .build();
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("methodAccessLevel");
        }

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathMethodBodyNull() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PRIVATE;
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodName = "testName";

        try {
            SourceCodeMethodBuilder.Builder(expectedMethodAccessLevel, true, expectedMethodName,
                                            null)
                                   .withMethodReturnType(expectedMethodReturnType)
                                   .build();
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("methodBody");
        }

    }

    @Test
    public void buildSourceCodeMethodHappyPath() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, true, expectedMethodName, expectedMethodBody)
                .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getMethodReturnType()).isNull();
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo(expectedMethodName);
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);

    }

    @Test
    public void buildSourceCodeMethodHappyPathWithGenericMethodReturnType() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = List.class;
        Class<?> expectedGenericType = String.class;
        String expectedSimpleReturnType = expectedMethodReturnType.getSimpleName() + "<" + expectedGenericType.getSimpleName() + ">";
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, true, expectedMethodName, expectedMethodBody)
                .withGenericMethodReturnType(expectedMethodReturnType, expectedGenericType)
                .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getMethodReturnType()).isEqualTo(expectedMethodReturnType);
        assertThat(sourceCodeMethod.getMethodGenericReturnType()).isEqualTo(expectedGenericType);
        assertThat(sourceCodeMethod.getSimpleMethodReturnType()).isEqualTo(
                expectedSimpleReturnType);
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo(expectedMethodName);
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathGenericTypeIsNull() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PRIVATE;
        Class<?> expectedMethodReturnType = List.class;
        String expectedMethodName = "testName";
        String expectedMethodBody = "testBody";

        try {
            SourceCodeMethodBuilder.Builder(expectedMethodAccessLevel, true, expectedMethodName,
                                            expectedMethodBody)
                                   .withGenericMethodReturnType(expectedMethodReturnType, null);
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("genericType can not be null");
        }

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathParameterTypeIsNull() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PRIVATE;
        String expectedMethodName = "testName";
        String expectedMethodBody = "testBody";
        String expectedParameterName = "testParameter";

        try {
            SourceCodeMethodBuilder.Builder(expectedMethodAccessLevel, true, expectedMethodName,
                                            expectedMethodBody)
                                   .withParameter(null, expectedParameterName);
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("parameterType can not be null");
        }

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathParameterNameIsNull() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PRIVATE;
        String expectedMethodName = "testName";
        String expectedMethodBody = "testBody";
        Class<?> expectedParameterType = String.class;

        try {
            SourceCodeMethodBuilder.Builder(expectedMethodAccessLevel, true, expectedMethodName,
                                            expectedMethodBody)
                                   .withParameter(expectedParameterType, null);
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("parameterName can not be null");
        }

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathParameterNameIsDuplicate() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PRIVATE;
        String expectedMethodName = "testName";
        String expectedMethodBody = "testBody";
        Class<?> expectedParameterType1 = String.class;
        String expectedParameterName1 = "paramter1";
        Class<?> expectedParameterType2 = Integer.class;
        String expectedParameterName2 = "paramter1";

        try {
            SourceCodeMethodBuilder.Builder(expectedMethodAccessLevel, true, expectedMethodName,
                                            expectedMethodBody)
                                   .withParameter(expectedParameterType1, expectedParameterName1)
                                   .withParameter(expectedParameterType2, expectedParameterName2);
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("parameterName : " + expectedParameterName1
                                                         + " was already used in method : " + expectedMethodName);
        }

    }

    @Test
    public void buildSourceCodeMethodHappyPathWithAnnotations() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";
        Class<?> expectedAnnotation1 = Override.class;
        Class<?> expectedAnnotation2 = SneakyThrows.class;

        List<Class<?>> annotations = new ArrayList<>();
        annotations.add(expectedAnnotation1);
        annotations.add(expectedAnnotation2);

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                expectedMethodAccessLevel, true, expectedMethodName, expectedMethodBody)
                                                                   .withAnnotations(annotations)
                                                                   .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getIsStatic()).isEqualTo((Object) true);
        assertThat(sourceCodeMethod.getDoesMethodReturnSelf()).isNull();
        assertThat(sourceCodeMethod.getMethodGenericReturnType()).isNull();
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo(expectedMethodName);
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);
        assertThat(sourceCodeMethod.getAnnotations()
                                   .size()).isEqualTo(2);
        assertThat(sourceCodeMethod.getAnnotations()
                                   .get(0)).isEqualTo(expectedAnnotation1);
        assertThat(sourceCodeMethod.getAnnotations()
                                   .get(1)).isEqualTo(expectedAnnotation2);

    }

    @Test
    public void buildSourceCodeMethodHappyPathWithAnnotation() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";
        Class<?> expectedAnnotation = Override.class;

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                expectedMethodAccessLevel, true, expectedMethodName, expectedMethodBody)
                                                                   .withAnnotation(
                                                                           expectedAnnotation)
                                                                   .build();

        assertThat(sourceCodeMethod.getMethodAccessLevel()).isEqualTo(expectedMethodAccessLevel);
        assertThat(sourceCodeMethod.getIsStatic()).isEqualTo((Object) true);
        assertThat(sourceCodeMethod.getDoesMethodReturnSelf()).isNull();
        assertThat(sourceCodeMethod.getMethodGenericReturnType()).isNull();
        assertThat(sourceCodeMethod.getMethodName()).isEqualTo(expectedMethodName);
        assertThat(sourceCodeMethod.getMethodBody()).isEqualTo(expectedMethodBody);
        assertThat(sourceCodeMethod.getAnnotations()
                                   .size()).isEqualTo(1);
        assertThat(sourceCodeMethod.getAnnotations()
                                   .get(0)).isEqualTo(expectedAnnotation);

    }

    @Test
    public void buildSourceCodeMethodGrumpyPathWithAnnotationThatIsNotAnnotation() {

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return \"test\";";
        Class<?> expectedAnnotation = String.class;

        try {
            SourceCodeMethodBuilder.Builder(expectedMethodAccessLevel, true, expectedMethodName,
                                            expectedMethodBody)
                                   .withAnnotation(expectedAnnotation);
            throw new RuntimeException("Test should not reach this point");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo(
                    "Class " + expectedAnnotation.getName() + " is not an annotation.");
        }


    }

}
