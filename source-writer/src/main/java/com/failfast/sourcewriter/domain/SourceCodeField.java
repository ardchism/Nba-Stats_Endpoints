package com.failfast.sourcewriter.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceCodeField {

    private final Class<?> fieldType;
    private final Class<?> genericType;
    private final String fieldName;

    private FieldAccessLevel fieldAccessLevel;
    private Boolean isFinal;

    public String getSimpleFieldTypeName() {

        if(genericType == null) {
            return getFieldType().getSimpleName();
        } else {
            return getFieldType().getSimpleName() + "<" + getGenericType().getSimpleName() + ">";
        }

    }

    @Data
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SourceCodeFieldBuilder {

        @NonNull
        private final Class<?> fieldType;
        private final Class<?> genericType;

        @NonNull
        private final String fieldName;

        private FieldAccessLevel fieldAccessLevel;
        private Boolean isFinal;

        private SourceCodeFieldBuilder(Class<?> fieldType, String fieldName) {
            this.fieldType = fieldType;
            this.fieldName = fieldName;
            this.genericType = null;
        }

        public static SourceCodeFieldBuilder Builder(Class<?> fieldType, String fieldName) {
            return new SourceCodeFieldBuilder(fieldType, fieldName);
        }

        public static SourceCodeFieldBuilder Builder(Class<?> fieldType, Class<?> genericType,
                                                     String fieldName) {
            return new SourceCodeFieldBuilder(fieldType, genericType, fieldName);
        }

        public SourceCodeFieldBuilder withFieldAccessLevel(FieldAccessLevel fieldAccessLevel) {
            setFieldAccessLevel(fieldAccessLevel);
            return this;
        }

        public SourceCodeFieldBuilder withIsFinal(Boolean isFinal) {
            setIsFinal(isFinal);
            return this;
        }

        public SourceCodeField build() {

            validate();

            SourceCodeField sourceCodeField = new SourceCodeField(getFieldType(), getGenericType(),
                                                                  getFieldName());

            sourceCodeField.setFieldAccessLevel(getFieldAccessLevel());

            sourceCodeField.setIsFinal(getIsFinal());

            return sourceCodeField;
        }

        private void validate() {

            Objects.requireNonNull(getFieldType(), "fieldType must not be null");
            Objects.requireNonNull(getFieldName(), "fieldName must not be null");

            if(doesClassImplementInterface(getFieldType(), Collection.class))
                Objects.requireNonNull(getGenericType(), "genericType must not be null");

        }

        private boolean doesClassImplementInterface(Class<?> clazz, Class<?> interFace) {

            if(clazz.isAssignableFrom(interFace))
                return true;

            for(Class<?> currentInterface : clazz.getInterfaces()) {

                if(doesClassImplementInterface(currentInterface, interFace))
                    return true;

            }

            return false;

        }

    }

}
