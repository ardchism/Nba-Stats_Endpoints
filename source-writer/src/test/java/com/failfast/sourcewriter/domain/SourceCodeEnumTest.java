package com.failfast.sourcewriter.domain;


import com.failfast.sourcewriter.domain.SourceCodeEnum.SourceCodeEnumBuilder;
import com.failfast.sourcewriter.domain.SourceCodeField.SourceCodeFieldBuilder;
import com.failfast.sourcewriter.domain.SourceCodeMethod.SourceCodeMethodBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceCodeEnumTest {

    @Test
    public void buildSourceCodeEnumHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "public enum TestEnumName {\n" + "}";

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithInterfaceHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        Class<?> expectedInterface = Serializable.class;

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "\n" + "import java.io.Serializable;\n" + "\n" +
                "public enum TestEnumName implements Serializable {\n" + "}";

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withInterface(expectedInterface)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithInterfaceGrumpyPathClassNotInterface() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        Class<?> expectedInterface = String.class;

        try {
            SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                 .withInterface(expectedInterface);
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo(
                    expectedInterface.getName() + " is not an " + "interface.");
        }

    }

    @Test
    public void buildSourceCodeEnumWithValuesHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";
        List<String> testEnumValues = new ArrayList<>();
        testEnumValues.add("Success");
        testEnumValues.add("Failed");

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "public enum TestEnumName {\n" +
                "\tSuccess, Failed\n" + "}";

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withValues(testEnumValues)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithAnnotationsHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        List<Class<?>> expectedAnnotations = new ArrayList<>();
        expectedAnnotations.add(AllArgsConstructor.class);
        expectedAnnotations.add(Getter.class);

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "\n" + "import lombok.AllArgsConstructor;\n" +
                "import lombok.Getter;\n" + "\n" + "@AllArgsConstructor\n" + "@Getter\n" +
                "public enum TestEnumName {\n" + "}";

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withAnnotations(expectedAnnotations)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithAnnotationsGrumpyPathClassNotAnAnnotation() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        List<Class<?>> expectedAnnotations = new ArrayList<>();
        expectedAnnotations.add(String.class);
        expectedAnnotations.add(Getter.class);

        String expectedExceptionMessage =
                String.class.getSimpleName() + " is not a valid " + "annotation!";

        try {

            SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                 .withAnnotations(expectedAnnotations);

            throw new RuntimeException("Test should not reach this point!");

        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeEnumWithMethodsHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedMethodName = "testMethod";
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodParmName = "test";
        Class<?> expectedMethodParmType = Integer.class;

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "\n" + "import java.lang.String;\n" +
                "import java.lang.Integer;\n" + "\n" + "public enum TestEnumName {\n" + "\t;" +
                "\n" + "\n" + "\tpublic String testMethod(Integer test) {\n" + "\t\treturn test;" +
                "\n" + "\t}\n" + "}";


        List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                MethodAccessLevel.PUBLIC, false, expectedMethodName, "return " + "test;")

                                                                   .withMethodReturnType(
                                                                           expectedMethodReturnType)
                                                                   .withParameter(
                                                                           expectedMethodParmType,
                                                                           expectedMethodParmName)
                                                                   .build();

        sourceCodeMethods.add(sourceCodeMethod);

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withMethods(sourceCodeMethods)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithAnnotatedMethodsHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedMethodName = "testMethod";
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodParmName = "test";
        Class<?> expectedMethodParmType = Integer.class;

        Class<?> expectedAnnotation = Override.class;

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "\n" + "import java.lang.String;\n" +
                "import java.lang.Integer;\n" + "\n" + "public enum TestEnumName {\n" + "\t;" +
                "\n" + "\n" + "\t@Override\n" + "\tpublic String testMethod(Integer test) {\n" +
                "\t\treturn test;\n" + "\t}\n" + "}";


        List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                MethodAccessLevel.PUBLIC, false, expectedMethodName, "return " + "test;")

                                                                   .withMethodReturnType(
                                                                           expectedMethodReturnType)
                                                                   .withParameter(
                                                                           expectedMethodParmType,
                                                                           expectedMethodParmName)
                                                                   .withAnnotation(
                                                                           expectedAnnotation)
                                                                   .build();

        sourceCodeMethods.add(sourceCodeMethod);

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withMethods(sourceCodeMethods)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithPrivateMethodsHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedMethodName = "testMethod";
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodParmName = "test";
        Class<?> expectedMethodParmType = Integer.class;

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "\n" + "import java.lang.String;\n" +
                "import java.lang.Integer;\n" + "\n" + "public enum TestEnumName {\n" + "\t;\n" +
                "\n" + "\tprivate String testMethod(Integer test) {\n" + "\t\treturn test;\n" +
                "\t}\n" + "}";


        List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                MethodAccessLevel.PRIVATE, false, expectedMethodName, "return " + "test;")

                                                                   .withMethodReturnType(
                                                                           expectedMethodReturnType)
                                                                   .withParameter(
                                                                           expectedMethodParmType,
                                                                           expectedMethodParmName)
                                                                   .build();

        sourceCodeMethods.add(sourceCodeMethod);

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withMethods(sourceCodeMethods)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithPrivateMethodsGrumpyPathDuplicateMethod() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedMethodName = "testMethod";
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodParmName = "test";
        Class<?> expectedMethodParmType = Integer.class;

        List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder.Builder(
                MethodAccessLevel.PRIVATE, false, expectedMethodName, "return test;")

                                                                   .withMethodReturnType(
                                                                           expectedMethodReturnType)
                                                                   .withParameter(
                                                                           expectedMethodParmType,
                                                                           expectedMethodParmName)
                                                                   .build();

        sourceCodeMethods.add(sourceCodeMethod);
        sourceCodeMethods.add(sourceCodeMethod);

        try {
            SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                 .withMethods(sourceCodeMethods);
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo(
                    expectedMethodName + " is a duplicate method " + "name");
        }

    }

    @Test
    public void buildSourceCodeEnumWithFieldHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedFieldName = "testField";
        Class<?> expectedFieldReturnType = String.class;

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "\n" + "import java.lang.String;\n" + "\n" +
                "public enum TestEnumName {\n" + "\t;\n" + "\n" +
                "\tprivate final String testField;\n" + "}";


        List<SourceCodeField> sourceCodeFields = new ArrayList<>();

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldReturnType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        FieldAccessLevel.PRIVATE)
                                                                .withIsFinal(true)
                                                                .build();

        sourceCodeFields.add(sourceCodeField);

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withFields(sourceCodeFields)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithFieldHappyPathNullIsFinal() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedFieldName = "testField";
        Class<?> expectedFieldReturnType = String.class;

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "\n" + "import java.lang.String;\n" + "\n" +
                "public enum TestEnumName {\n" + "\t;\n" + "\n" + "\tprivate String testField;\n" +
                "}";


        List<SourceCodeField> sourceCodeFields = new ArrayList<>();

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldReturnType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        FieldAccessLevel.PRIVATE)
                                                                .build();

        sourceCodeFields.add(sourceCodeField);

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withFields(sourceCodeFields)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeEnumWithFieldGrumpyPathDuplicateFieldName() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedFieldName = "testField";
        Class<?> expectedFieldReturnType = String.class;

        List<SourceCodeField> sourceCodeFields = new ArrayList<>();

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldReturnType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        FieldAccessLevel.PRIVATE)
                                                                .withIsFinal(true)
                                                                .build();

        sourceCodeFields.add(sourceCodeField);
        sourceCodeFields.add(sourceCodeField);

        try {
            SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                 .withFields(sourceCodeFields);
            throw new RuntimeException("Test should not reach this point!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo(
                    "Duplicate field names are not allowed. The name" + " " + expectedFieldName +
                    " was already used");
        }


    }

    @Test
    public void buildSourceCodeEnumWithConstructorHappyPath() {

        String testPackageName = "com.testpackagename";
        String testEnumName = "TestEnumName";

        String expectedValueName = "TEST";
        String expectedValue = "\"test\"";

        String expectedFullyQualifiedClassName = testPackageName + "." + testEnumName;
        String expectedSourceCode =
                "package com.testpackagename;\n" + "public enum TestEnumName {\n" +
                "\tTEST(\"test\")\n" + "}";

        SourceCodeEnum sourceEnumCode = SourceCodeEnumBuilder.Builder(testEnumName, testPackageName)
                                                             .withValueConstructor(
                                                                     expectedValueName,
                                                                     expectedValue)
                                                             .build();

        assertThat(sourceEnumCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceEnumCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

}
