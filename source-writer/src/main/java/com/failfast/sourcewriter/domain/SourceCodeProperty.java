package com.failfast.sourcewriter.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceCodeProperty {

    private final Class<?> propertyType;
    private final Class<?> genericType;
    private final String propertyName;

    private Boolean isFinal;

    public String getSimplePropertyTypeName() {

        if(genericType == null) {
            return getPropertyType().getSimpleName();
        } else {
            return getPropertyType().getSimpleName() + "<" + getGenericType().getSimpleName() + ">";
        }

    }

    @Data
    public static class SourceCodePropertyBuilder {

        @NonNull
        private final Class<?> propertyType;
        private final Class<?> genericType;

        @NonNull
        private final String propertyName;

        private Boolean isFinal;

        private SourceCodePropertyBuilder(Class<?> properyType, String propertyName) {

            this.propertyType = properyType;
            this.propertyName = propertyName;
            this.genericType = null;
        }

        private SourceCodePropertyBuilder(Class<?> properyType, Class<?> genericType,
                                          String propertyName) {

            this.propertyType = properyType;
            this.propertyName = propertyName;
            this.genericType = genericType;
        }

        public static SourceCodePropertyBuilder Builder(Class<?> propertyType,
                                                        String propertyName) {
            return new SourceCodePropertyBuilder(propertyType, propertyName);
        }

        public static SourceCodePropertyBuilder Builder(Class<?> propertyType, Class<?> genericType,
                                                        String propertyName) {
            return new SourceCodePropertyBuilder(propertyType, genericType, propertyName);
        }

        public SourceCodePropertyBuilder withIsFinal(Boolean isFinal) {
            setIsFinal(isFinal);
            return this;
        }

        public SourceCodeProperty build() {

            validate();

            SourceCodeProperty sourceCodeProperty = new SourceCodeProperty(getPropertyType(),
                                                                           getGenericType(),
                                                                           getPropertyName());

            sourceCodeProperty.setIsFinal(getIsFinal());

            return sourceCodeProperty;
        }

        private void validate() {

            Objects.requireNonNull(getPropertyType(), "propertyType must not be null");
            Objects.requireNonNull(getPropertyName(), "propertyName must not be null");

            if(doesClassImplementInterface(getPropertyType(), Collection.class))
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
