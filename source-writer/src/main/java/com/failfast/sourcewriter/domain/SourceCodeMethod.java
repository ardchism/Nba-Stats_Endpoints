package com.failfast.sourcewriter.domain;

import lombok.*;

import java.util.*;

@SuppressWarnings("WeakerAccess")
@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SourceCodeMethod {

    private final MethodAccessLevel           methodAccessLevel;
    private final Boolean                     isStatic;
    private final Boolean                     isConstructor;
    private final String                      methodName;
    private final String                      methodBody;
    private final SortedMap<String, Class<?>> methodParameters;
    private final List<Class<?>>              annotations;

    @Setter(AccessLevel.PRIVATE)
    private Class<?> methodReturnType;
    @Setter(AccessLevel.PRIVATE)
    private Class<?> methodGenericReturnType;
    @Setter(AccessLevel.PRIVATE)
    private Boolean doesMethodReturnSelf;

    public String getSimpleMethodReturnType() {

        if(getMethodGenericReturnType() != null)
            return getMethodReturnType().getSimpleName() + "<" + getMethodGenericReturnType().getSimpleName() + ">";
        else
            return getMethodReturnType().getSimpleName();

    }

    public boolean hasAnnotations() {

        return !getAnnotations().isEmpty();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SourceCodeMethodBuilder {

        @NonNull
        @Getter(AccessLevel.PRIVATE)
        private final MethodAccessLevel methodAccessLevel;
        @NonNull
        @Getter(AccessLevel.PRIVATE)
        private final Boolean isStatic;
        @NonNull
        @Getter(AccessLevel.PRIVATE)
        private final Boolean isConstructor;
        @NonNull
        @Getter(AccessLevel.PRIVATE)
        private final String methodName;
        @NonNull
        @Getter(AccessLevel.PRIVATE)
        private final String methodBody;

        @Getter(AccessLevel.PRIVATE)
        private final SortedMap<String, Class<?>> methodParameters = new TreeMap<>();

        @Getter(AccessLevel.PRIVATE)
        @Setter(AccessLevel.PRIVATE)
        private Class<?> methodReturnType;
        @Getter(AccessLevel.PRIVATE)
        @Setter(AccessLevel.PRIVATE)
        private Class<?> methodGenericReturnType;

        @Getter(AccessLevel.PRIVATE)
        @Setter(AccessLevel.PRIVATE)
        private Boolean doesMethodReturnSelf;

        @Getter(AccessLevel.PRIVATE)
        private final List<Class<?>> annotations = new ArrayList<>();

        public static SourceCodeMethodBuilder Builder(MethodAccessLevel methodAccessLevel,
                                                      Boolean isStatic, String methodName,
                                                      String methodBody) {
            return new SourceCodeMethodBuilder(methodAccessLevel, isStatic, false, methodName,
                                               methodBody);
        }

        public static SourceCodeMethodBuilder ConstructorBuilder(
                MethodAccessLevel methodAccessLevel, String methodBody) {
            return new SourceCodeMethodBuilder(methodAccessLevel, false, true, "", methodBody);
        }

        public SourceCodeMethodBuilder withParameter(Class<?> parameterType, String parameterName) {

            Objects.requireNonNull(parameterType, "parameterType can not be null");
            Objects.requireNonNull(parameterName, "parameterName can not be null");

            if(methodParameters.containsKey(parameterName)) {
                throw new RuntimeException("parameterName : " + parameterName
                                                   + " was already used in method : " + getMethodName());
            }

            methodParameters.put(parameterName, parameterType);

            return this;
        }

        public SourceCodeMethodBuilder withMethodReturnType(Class<?> methodReturnType) {

            Objects.requireNonNull(methodReturnType, "methodReturnType can not be null");

            setMethodReturnType(methodReturnType);

            return this;
        }

        public SourceCodeMethodBuilder withGenericMethodReturnType(Class<?> methodReturnType,
                                                                   Class<?> genericType) {

            Objects.requireNonNull(methodReturnType, "methodReturnType can not be null");
            Objects.requireNonNull(genericType, "genericType can not be null");

            setMethodReturnType(methodReturnType);
            setMethodGenericReturnType(genericType);

            return this;
        }

        public SourceCodeMethodBuilder withMethodReturnSelf() {

            setDoesMethodReturnSelf(true);

            return this;
        }

        public SourceCodeMethodBuilder withAnnotations(Collection<Class<?>> annotations) {

            annotations.forEach(this::withAnnotation);
            return this;
        }

        public SourceCodeMethodBuilder withAnnotation(Class<?> annotation) {

            if(annotation.isAnnotation()) {
                getAnnotations().add(annotation);
            } else {
                throw new RuntimeException(
                        "Class " + annotation.getName() + " is not an " + "annotation.");
            }
            return this;
        }

        public SourceCodeMethod build() {

            SourceCodeMethod sourceCodeMethod = new SourceCodeMethod(getMethodAccessLevel(),
                                                                     getIsStatic(),
                                                                     getIsConstructor(),
                                                                     getMethodName(),
                                                                     getMethodBody(),
                                                                     getMethodParameters(),
                                                                     getAnnotations());

            sourceCodeMethod.setMethodReturnType(getMethodReturnType());
            sourceCodeMethod.setMethodGenericReturnType(getMethodGenericReturnType());
            sourceCodeMethod.setDoesMethodReturnSelf(getDoesMethodReturnSelf());

            return sourceCodeMethod;

        }

    }

}
