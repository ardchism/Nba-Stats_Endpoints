package com.failfast.sourcewriter.domain;

import com.failfast.sourcewriter.domain.SourceCodeField.SourceCodeFieldBuilder;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceCodeFieldTest {

    @Test
    public void buildSourceCodeFieldHappyPath() {

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";
        String expectedSimpleFieldTypeName = "String";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .build();

        assertThat(sourceCodeField.getFieldType()).isEqualTo(expectedFieldType);
        assertThat(sourceCodeField.getFieldName()).isEqualTo(expectedFieldName);
        assertThat(sourceCodeField.getSimpleFieldTypeName()).isEqualTo(expectedSimpleFieldTypeName);
        assertThat(sourceCodeField.getFieldAccessLevel()).isNull();
        assertThat(sourceCodeField.getIsFinal()).isNull();

    }

    @Test
    public void buildSourceCodeFieldWithCollectionHappyPath() {

        Class<?> expectedFieldType = List.class;
        Class<?> expectedGenericType = String.class;
        String expectedFieldName = "testFieldName";
        String expectedSimpleFieldTypeName = "List<String>";

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedGenericType,
                                                                         expectedFieldName)
                                                                .build();

        assertThat(sourceCodeField.getFieldType()).isEqualTo(expectedFieldType);
        assertThat(sourceCodeField.getFieldName()).isEqualTo(expectedFieldName);
        assertThat(sourceCodeField.getGenericType()).isEqualTo(expectedGenericType);
        assertThat(sourceCodeField.getSimpleFieldTypeName()).isEqualTo(expectedSimpleFieldTypeName);
        assertThat(sourceCodeField.getFieldAccessLevel()).isNull();
        assertThat(sourceCodeField.getIsFinal()).isNull();

    }

    @Test
    public void buildSourceCodeFieldWithAllHappyPath() {

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";
        FieldAccessLevel expectedFieldAccessLevel = FieldAccessLevel.PRIVATE;
        Boolean expectedIsFinal = true;

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        expectedFieldAccessLevel)
                                                                .withIsFinal(expectedIsFinal)
                                                                .build();

        assertThat(sourceCodeField.getFieldType()).isEqualTo(expectedFieldType);
        assertThat(sourceCodeField.getFieldName()).isEqualTo(expectedFieldName);
        assertThat(sourceCodeField.getFieldAccessLevel()).isEqualTo(expectedFieldAccessLevel);
        assertThat(sourceCodeField.getIsFinal()).isEqualTo(expectedIsFinal);

    }

    @Test
    public void buildSourceCodeFieldWithAccessLevelHappyPath() {

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";
        FieldAccessLevel expectedFieldAccessLevel = FieldAccessLevel.PRIVATE;

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .withFieldAccessLevel(
                                                                        expectedFieldAccessLevel)
                                                                .build();

        assertThat(sourceCodeField.getFieldType()).isEqualTo(expectedFieldType);
        assertThat(sourceCodeField.getFieldName()).isEqualTo(expectedFieldName);
        assertThat(sourceCodeField.getFieldAccessLevel()).isEqualTo(expectedFieldAccessLevel);
        assertThat(sourceCodeField.getIsFinal()).isNull();

    }

    @Test
    public void buildSourceCodeFieldWithIsFinalHappyPath() {

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = "testFieldName";
        Boolean expectedIsFinal = true;

        SourceCodeField sourceCodeField = SourceCodeFieldBuilder.Builder(expectedFieldType,
                                                                         expectedFieldName)
                                                                .withIsFinal(expectedIsFinal)
                                                                .build();

        assertThat(sourceCodeField.getFieldType()).isEqualTo(expectedFieldType);
        assertThat(sourceCodeField.getFieldName()).isEqualTo(expectedFieldName);
        assertThat(sourceCodeField.getIsFinal()).isEqualTo(expectedIsFinal);
        assertThat(sourceCodeField.getFieldAccessLevel()).isNull();

    }

    @Test
    public void buildSourceCodeFieldFieldTypeNotFoundGrumpyPath() {

        String expectedExceptionMessage = "fieldType must not be null";

        Class<?> expectedFieldType = null;
        String expectedFieldName = "testFieldName";

        try {

            SourceCodeFieldBuilder.Builder(expectedFieldType, expectedFieldName)
                                  .build();

            throw new RuntimeException("Test should not reach this point");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeFieldFieldNameNotFoundGrumpyPath() {

        String expectedExceptionMessage = "fieldName must not be null";

        Class<?> expectedFieldType = String.class;
        String expectedFieldName = null;

        try {

            SourceCodeFieldBuilder.Builder(expectedFieldType, expectedFieldName)
                                  .build();

            throw new RuntimeException("Test should not reach this point");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodeFieldGenericTypeNotFoundGrumpyPath() {

        String expectedExceptionMessage = "genericType must not be null";

        Class<?> expectedFieldType = List.class;
        Class<?> expectedGenericType = null;
        String expectedFieldName = "test";

        try {

            SourceCodeFieldBuilder.Builder(expectedFieldType, expectedGenericType,
                                           expectedFieldName)
                                  .build();

            throw new RuntimeException("Test should not reach this point");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }
}
