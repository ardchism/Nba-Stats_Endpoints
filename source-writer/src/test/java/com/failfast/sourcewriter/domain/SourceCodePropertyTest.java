package com.failfast.sourcewriter.domain;

import com.failfast.sourcewriter.domain.SourceCodeProperty.SourceCodePropertyBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceCodePropertyTest {

    @Test
    public void buildSourceCodePropertyHappyPath() {

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName = "testPropertyName";

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder.Builder(
                expectedPropertyType, expectedPropertyName)
                                                                         .build();

        assertThat(sourceCodeProperty.getPropertyType()).isEqualTo(expectedPropertyType);
        assertThat(sourceCodeProperty.getPropertyName()).isEqualTo(expectedPropertyName);
        assertThat(sourceCodeProperty.getIsFinal()).isNull();
        assertThat(sourceCodeProperty.getGenericType()).isNull();

    }

    @Test
    public void buildSourceCodePropertyWithCollectionHappyPath() {

        Class<?> expectedPropertyType = List.class;
        Class<?> expectedGenericType = String.class;
        String expectedPropertyName = "testPropertyName";
        String expectedSimplePropertyTypeName = "List<String>";

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder.Builder(
                expectedPropertyType, expectedGenericType, expectedPropertyName)
                                                                         .build();

        assertThat(sourceCodeProperty.getPropertyType()).isEqualTo(expectedPropertyType);
        assertThat(sourceCodeProperty.getPropertyName()).isEqualTo(expectedPropertyName);
        assertThat(sourceCodeProperty.getGenericType()).isEqualTo(expectedGenericType);
        assertThat(sourceCodeProperty.getSimplePropertyTypeName()).isEqualTo(
                expectedSimplePropertyTypeName);
        assertThat(sourceCodeProperty.getIsFinal()).isNull();

    }

    @Test
    public void buildSourceCodePropertyWithAllHappyPath() {

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName = "testPropertyName";
        Boolean expectedIsFinal = true;

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder.Builder(
                expectedPropertyType, expectedPropertyName)
                                                                         .withIsFinal(
                                                                                 expectedIsFinal)
                                                                         .build();

        assertThat(sourceCodeProperty.getPropertyType()).isEqualTo(expectedPropertyType);
        assertThat(sourceCodeProperty.getPropertyName()).isEqualTo(expectedPropertyName);
        assertThat(sourceCodeProperty.getIsFinal()).isEqualTo(expectedIsFinal);
        assertThat(sourceCodeProperty.getSimplePropertyTypeName()).isEqualTo("String");
        assertThat(sourceCodeProperty.getGenericType()).isNull();

    }

    @Test
    public void buildSourceCodePropertyWithIsFinalHappyPath() {

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName = "testPropertyName";
        Boolean expectedIsFinal = true;

        SourceCodeProperty sourceCodeProperty = SourceCodePropertyBuilder.Builder(
                expectedPropertyType, expectedPropertyName)
                                                                         .withIsFinal(
                                                                                 expectedIsFinal)
                                                                         .build();

        assertThat(sourceCodeProperty.getPropertyType()).isEqualTo(expectedPropertyType);
        assertThat(sourceCodeProperty.getPropertyName()).isEqualTo(expectedPropertyName);
        assertThat(sourceCodeProperty.getIsFinal()).isEqualTo(expectedIsFinal);
        assertThat(sourceCodeProperty.getGenericType()).isNull();

    }

    @Test
    public void buildSourceCodePropertyPropertyTypeNotFoundGrumpyPath() {

        String expectedExceptionMessage = "propertyType must not be null";

        Class<?> expectedPropertyType = null;
        String expectedPropertyName = "testPropertyName";

        try {

            SourceCodePropertyBuilder.Builder(expectedPropertyType, expectedPropertyName)
                                     .build();

            throw new RuntimeException("Test should not reach this point");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodePropertyPropertyNameNotFoundGrumpyPath() {

        String expectedExceptionMessage = "propertyName must not be null";

        Class<?> expectedPropertyType = String.class;
        String expectedPropertyName = null;

        try {

            SourceCodePropertyBuilder.Builder(expectedPropertyType, expectedPropertyName)
                                     .build();

            throw new RuntimeException("Test should not reach this point");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }

    @Test
    public void buildSourceCodePropertyWithCollectionGenericTypeNotFoundGrumpyPath() {

        String expectedExceptionMessage = "genericType must not be null";

        Class<?> expectedPropertyType = ArrayList.class;
        String expectedPropertyName = "test";
        Class<?> expectedGenericName = null;

        try {

            SourceCodePropertyBuilder.Builder(expectedPropertyType, expectedGenericName,
                                              expectedPropertyName)
                                     .build();

            throw new RuntimeException("Test should not reach this point");

        } catch(NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo(expectedExceptionMessage);
        }

    }
}
