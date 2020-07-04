package com.failfast.sourcewriter.domain;

import com.failfast.sourcewriter.domain.SourceCode.SourceCodeBuilder;
import com.failfast.sourcewriter.domain.SourceCodeField.SourceCodeFieldBuilder;
import com.failfast.sourcewriter.domain.SourceCodeMethod.SourceCodeMethodBuilder;
import com.failfast.sourcewriter.domain.SourceCodeProperty.SourceCodePropertyBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceCodeTest {

    @Test
    public void buildSourceCodeHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        String expectedSourceCode = "package com.testpackagename;\n" +
                "public class TestClassName {\n" +
                "}";

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodVoidReturnTypeHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "System.out.println(\"test\");";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public void testMethodName() {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethod(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodConstructorHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodBody = "System.out.println(\"test\");";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public TestClassName() {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .ConstructorBuilder(expectedMethodAccessLevel, expectedMethodBody)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeConstructor(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodPrivateConstructorWithParameterHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PRIVATE;
        String expectedMethodBody = "System.out.println(\"test\");";
        Class<?> expectedParameterType = String.class;
        String expectedParameterName = "testing123";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	private TestClassName(String testing123) {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .ConstructorBuilder(expectedMethodAccessLevel, expectedMethodBody)
                .withParameter(expectedParameterType, expectedParameterName)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeConstructor(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodReturnSelfHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "return this;";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public TestClassName testMethodName() {\n" +
                "		return this;\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withMethodReturnSelf()
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethod(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodStaticHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Boolean expectedIsStatic = true;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "System.out.println(\"test\");";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public static void testMethodName() {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, expectedIsStatic, expectedMethodName,
                         expectedMethodBody)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethod(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodObjectReturnTypeHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = String.class;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "System.out.println(\"test\");";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public String testMethodName() {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withMethodReturnType(expectedMethodReturnType)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethod(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodCollectionReturnTypeHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = List.class;
        Class<?> expectedMethodCollectionType = String.class;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "System.out.println(\"test\");";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public List<String> testMethodName() {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withGenericMethodReturnType(expectedMethodReturnType, expectedMethodCollectionType)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethod(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodsHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = List.class;
        Class<?> expectedMethodCollectionType = String.class;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "System.out.println(\"test\");";
        MethodAccessLevel expectedMethodAccessLevel2 = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType2 = List.class;
        Class<?> expectedMethodCollectionType2 = String.class;
        String expectedMethodName2 = "testMethodName2";
        String expectedMethodBody2 = "System.out.println(\"test\");";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public List<String> testMethodName() {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "\n" +
                "	public List<String> testMethodName2() {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withGenericMethodReturnType(expectedMethodReturnType, expectedMethodCollectionType)
                .build();

        SourceCodeMethod sourceCodeMethod2 = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel2, false, expectedMethodName2,
                         expectedMethodBody2)
                .withGenericMethodReturnType(expectedMethodReturnType2,
                                             expectedMethodCollectionType2)
                .build();

        List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();
        sourceCodeMethods.add(sourceCodeMethod);
        sourceCodeMethods.add(sourceCodeMethod2);

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethods(sourceCodeMethods)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodWithParameterHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        Class<?> expectedParameterType = String.class;
        String expectedParameterName = "testParameter";
        String expectedMethodBody = "System.out.println(\"test\");";
        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public void testMethodName(String testParameter) {\n" +
                "		System.out.println(\"test\");\n" +
                "	}\n" +
                "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withParameter(expectedParameterType, expectedParameterName)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethod(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodWithParametersHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedFullyQualifiedClassName = testPackageName + "." + testClassName;
        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        String expectedMethodName = "testMethodName";
        Class<?> expectedParameterType = String.class;
        String expectedParameterName = "testParameter";
        Class<?> expectedParameterType2 = String.class;
        String expectedParameterName2 = "testParameter2";
        String expectedMethodBody = "System.out.println(\"test\");";
        String expectedSourceCode =
                "package com.testpackagename;\n" +
                        "\n" +
                        "import java.lang.String;\n" +
                        "\n" +
                        "public class TestClassName {\n" +
                        "\n" +
                        "	public void testMethodName(String testParameter, String testParameter2) {\n" +
                        "		System.out.println(\"test\");\n" +
                        "	}\n" +
                        "}";

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withParameter(expectedParameterType, expectedParameterName)
                .withParameter(expectedParameterType2, expectedParameterName2)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeMethod(sourceCodeMethod)
                                                 .build();

        assertThat(sourceCode.getFullyQualifiedClassName()).isEqualTo(
                expectedFullyQualifiedClassName);
        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodsGrumpyPathDuplicateMethodName() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = List.class;
        Class<?> expectedMethodCollectionType = String.class;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "System.out.println(\"test\");";

        String expectedExceptionMessage = "Duplicate sourceCodeMethods named " + expectedMethodName;

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withGenericMethodReturnType(expectedMethodReturnType, expectedMethodCollectionType)
                .build();

        SourceCodeMethod sourceCodeMethodDuplicate = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withGenericMethodReturnType(expectedMethodReturnType, expectedMethodCollectionType)
                .build();

        List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();
        sourceCodeMethods.add(sourceCodeMethod);
        sourceCodeMethods.add(sourceCodeMethodDuplicate);

        try {
            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .withSourceCodeMethods(sourceCodeMethods)
                             .build();
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeWithSourceCodeMethodGrumpyPathDuplicateMethodName() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        MethodAccessLevel expectedMethodAccessLevel = MethodAccessLevel.PUBLIC;
        Class<?> expectedMethodReturnType = List.class;
        Class<?> expectedMethodCollectionType = String.class;
        String expectedMethodName = "testMethodName";
        String expectedMethodBody = "System.out.println(\"test\");";

        String expectedExceptionMessage = "Duplicate sourceCodeMethods named " + expectedMethodName;

        SourceCodeMethod sourceCodeMethod = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withGenericMethodReturnType(expectedMethodReturnType, expectedMethodCollectionType)
                .build();

        SourceCodeMethod sourceCodeMethodDuplicate = SourceCodeMethodBuilder
                .Builder(expectedMethodAccessLevel, false, expectedMethodName, expectedMethodBody)
                .withGenericMethodReturnType(expectedMethodReturnType, expectedMethodCollectionType)
                .build();

        try {
            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .withSourceCodeMethod(sourceCodeMethod)
                             .withSourceCodeMethod(sourceCodeMethodDuplicate)
                             .build();
            throw new RuntimeException("Test should not reach this point!!");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeGrumpyPathClassNameNotFound() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedExceptionMessage = "className";

        try {

            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .build();

            SourceCodeBuilder.Builder(null, testPackageName)
                             .build();

            throw new RuntimeException("Test should not reach this point!!");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeGrumpyPathPackageNameNotFound() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedExceptionMessage = "packageName";

        try {

            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .build();

            SourceCodeBuilder.Builder(testClassName, null)
                             .build();

            throw new RuntimeException("Test should not reach this point!!");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeWithSourceCodePropertiesDefaultPropertiesHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	private String testPropertyName1;\n" +
                "	private String testPropertyName2;\n" +
                "\n" +
                "	public String getTestPropertyName1() {\n" +
                "		return testPropertyName1;\n" +
                "	}\n" +
                "\n" +
                "	public void setTestPropertyName1(String testPropertyName1) {\n" +
                "		this.testPropertyName1 = testPropertyName1;\n" +
                "	}\n" +
                "\n" +
                "	public String getTestPropertyName2() {\n" +
                "		return testPropertyName2;\n" +
                "	}\n" +
                "\n" +
                "	public void setTestPropertyName2(String testPropertyName2) {\n" +
                "		this.testPropertyName2 = testPropertyName2;\n" +
                "	}\n" +
                "}";

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName1 = "testPropertyName1";
        String expectedPropertyName2 = "testPropertyName2";

        List<SourceCodeProperty> sourceCodeProperties = new ArrayList<>();

        SourceCodeProperty sourceCodeProperty1 = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName1)
                .build();
        sourceCodeProperties.add(sourceCodeProperty1);

        SourceCodeProperty sourceCodeProperty2 = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName2)
                .build();
        sourceCodeProperties.add(sourceCodeProperty2);

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeProperties(sourceCodeProperties)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodePropertiesGrumpyPathTwoPropertiesHavingTheSameName() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName1 = "testPropertyName1";
        String expectedExceptionMessage = "Cannot create two properties/fields with the same name : "
                + expectedPropertyName1;

        List<SourceCodeProperty> sourceCodeProperties = new ArrayList<>();

        SourceCodeProperty sourceCodeProperty1 = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName1)
                .build();
        sourceCodeProperties.add(sourceCodeProperty1);

        SourceCodeProperty sourceCodeProperty2 = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName1)
                .build();
        sourceCodeProperties.add(sourceCodeProperty2);

        try {

            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .withSourceCodeProperties(sourceCodeProperties)
                             .build();

            throw new RuntimeException("Test should not reach this point!!");

        } catch(RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }
    }

    @Test
    public void buildSourceCodeWithSourceCodePropertyDefaultPropertyHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	private String testPropertyName;\n" +
                "\n" +
                "	public String getTestPropertyName() {\n" +
                "		return testPropertyName;\n" +
                "	}\n" +
                "\n" +
                "	public void setTestPropertyName(String testPropertyName) {\n" +
                "		this.testPropertyName = testPropertyName;\n" +
                "	}\n" +
                "}";

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName = "testPropertyName";

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeProperty(sourceCodeProperty)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodePropertyWithCollectionHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	private List<String> testPropertyName;\n" +
                "\n" +
                "	public List<String> getTestPropertyName() {\n" +
                "		return testPropertyName;\n" +
                "	}\n" +
                "\n" +
                "	public void setTestPropertyName(List<String> testPropertyName) {\n" +
                "		this.testPropertyName = testPropertyName;\n" +
                "	}\n" +
                "}";

        Class<?> expectedPropertyType = List.class;
        Class<?> expectedGenericType = String.class;
        String expectedPropertyName = "testPropertyName";

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedGenericType, expectedPropertyName)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeProperty(sourceCodeProperty)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodePropertyFinalPropertyHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	private final String testFieldName;\n" +
                "\n" +
                "	public String getTestFieldName() {\n" +
                "		return testFieldName;\n" +
                "	}\n" +
                "}";

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName = "testFieldName";
        Boolean expectedIsFinal = true;

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName)
                .withIsFinal(expectedIsFinal)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeProperty(sourceCodeProperty)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodePropertyGrumpyPathTwoPropertiesHavingTheSameName() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName1 = "testPropertyName1";

        String expectedExceptionMessage = "Cannot create two properties/fields with the same name : "
                + expectedPropertyName1;

        SourceCodeProperty sourceCodeProperty1 = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName1)
                .build();
        SourceCodeProperty sourceCodeProperty2 = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName1)
                .build();
        try {

            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .withSourceCodeProperty(sourceCodeProperty1)
                             .withSourceCodeProperty(sourceCodeProperty2)
                             .build();

            throw new RuntimeException("Test should not reach this point!!");

        } catch(RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }
    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldDefaultFieldHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	String testFieldName;\n" +
                "}";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeField(sourceCodeField)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldWithCollectionHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.util.List;\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	List<String> testFieldName;\n" +
                "}";

        Class<?> expectedFieldType = List.class;
        Class<?> expectedGenericType = String.class;
        String expectedFieldName = "testFieldName";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder
                .Builder(expectedFieldType, expectedGenericType, expectedFieldName)
                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeField(sourceCodeField)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldFinalFieldHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	final String testFieldName;\n" +
                "}";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .withIsFinal(true)
                                                                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeField(sourceCodeField)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldPrivateFieldHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	private String testFieldName;\n" +
                "}";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        FieldAccessLevel.PRIVATE)
                                                                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeField(sourceCodeField)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldPublicFieldHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	public String testFieldName;\n" +
                "}";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        FieldAccessLevel.PUBLIC)
                                                                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeField(sourceCodeField)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldProtectedFieldHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	protected String testFieldName;\n" +
                "}";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        FieldAccessLevel.PROTECTED)
                                                                .build();

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeField(sourceCodeField)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldGrumpyPathTwoFieldsHavingTheSameName() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        String expectedExceptionMessage = "Cannot create two properties/fields with the same name : "
                + expectedFieldName;

        SourceCodeField sourceCodeField1 = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                          expectedFieldName)
                                                                 .build();

        SourceCodeField sourceCodeField2 = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                          expectedFieldName)
                                                                 .build();
        try {

            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .withSourceCodeField(sourceCodeField1)
                             .withSourceCodeField(sourceCodeField2)
                             .build();

            throw new RuntimeException("Test should not reach this point!!");

        } catch(RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldsHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        String expectedSourceCode = "package com.testpackagename;\n" +
                "\n" +
                "import java.lang.String;\n" +
                "\n" +
                "public class TestClassName {\n" +
                "\n" +
                "	String testFieldName1;\n" +
                "	String testFieldName2;\n" +
                "}";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName1 = "testFieldName1";
        String expectedFieldName2 = "testFieldName2";

        SourceCodeField sourceCodeField1 = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                          expectedFieldName1)
                                                                 .build();

        SourceCodeField sourceCodeField2 = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                          expectedFieldName2)
                                                                 .build();

        List<SourceCodeField> sourceCodeFields = new ArrayList<>();
        sourceCodeFields.add(sourceCodeField1);
        sourceCodeFields.add(sourceCodeField2);

        SourceCode sourceCode = SourceCodeBuilder.Builder(testClassName, testPackageName)
                                                 .withSourceCodeFields(sourceCodeFields)
                                                 .build();

        assertThat(sourceCode.getFormattedSourceCode()).isEqualTo(expectedSourceCode);

    }

    @Test
    public void buildSourceCodeWithSourceCodeFieldsGrumpyPathTwoFieldsHavingTheSameName() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        String expectedExceptionMessage = "Cannot create two properties/fields with the same name : "
                + expectedFieldName;

        SourceCodeField sourceCodeField1 = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                          expectedFieldName)
                                                                 .build();

        SourceCodeField sourceCodeField2 = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                          expectedFieldName)
                                                                 .build();

        List<SourceCodeField> sourceCodeFields = new ArrayList<>();
        sourceCodeFields.add(sourceCodeField1);
        sourceCodeFields.add(sourceCodeField2);

        try {

            SourceCodeBuilder.Builder(testClassName, testPackageName)
                             .withSourceCodeFields(sourceCodeFields)
                             .build();

            throw new RuntimeException("Test should not reach this point!!");

        } catch(RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void validateFieldOrPropertyNameIsNotDuplicatedWithPropertyCreatedHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName = "testPropertyName1";

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder
                .Builder(expectedPropertyType, expectedPropertyName)
                .build();

        SourceCodeBuilder sourceCodeBuilder = SourceCodeBuilder.Builder(testClassName,
                                                                        testPackageName)
                                                               .withSourceCodeProperty(
                                                                       sourceCodeProperty);

        assertThat(sourceCodeBuilder.validateFieldOrPropertyNameIsNotDuplicated(
                expectedPropertyName)).isTrue();

    }

    @Test
    public void validateFieldOrPropertyNameIsNotDuplicatedWithFieldCreatedHappyPath() {

        String testPackageName = "com.testpackagename";
        String testClassName = "TestClassName";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .build();

        SourceCodeBuilder sourceCodeBuilder = SourceCodeBuilder.Builder(testClassName,
                                                                        testPackageName)
                                                               .withSourceCodeField(
                                                                       sourceCodeField);

        assertThat(sourceCodeBuilder.validateFieldOrPropertyNameIsNotDuplicated(
                expectedFieldName)).isTrue();

    }

}
