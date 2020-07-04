package com.failfast.sourcewriter.domain;

import lombok.*;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.EnumConstantSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SourceCodeEnum {

    private final String fullyQualifiedClassName;
    private final String formattedSourceCode;

    @Data
    @AllArgsConstructor
    public static class SourceCodeEnumValue {

        private String name;
        private String constructorValue;

        public SourceCodeEnumValue(String name) {

            this.name = name;
        }

    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SourceCodeEnumBuilder {

        @Getter(AccessLevel.PRIVATE)
        @NonNull
        private final String enumName;
        @Getter(AccessLevel.PRIVATE)
        @NonNull
        private final String packageName;

        @Getter
        @Setter
        private Class<?> implementedInterface;

        @Getter(AccessLevel.PRIVATE)
        private List<SourceCodeEnumValue> enumValues = new ArrayList<>();

        @Getter(AccessLevel.PRIVATE)
        private List<Class<? extends Annotation>> annotations = new ArrayList<>();

        @Getter(AccessLevel.PRIVATE)
        private List<SourceCodeMethod> methods = new ArrayList<>();

        @Getter(AccessLevel.PRIVATE)
        private List<SourceCodeField> fields = new ArrayList<>();

        @Getter
        @Setter
        private SourceCodeMethod sourceCodeConstructor;

        public static SourceCodeEnumBuilder Builder(String enumName, String packageName) {
            return new SourceCodeEnumBuilder(enumName, packageName);
        }

        public SourceCodeEnumBuilder withValues(List<String> enumNames) {

            getEnumValues().addAll(buildSourceCodeEnumValues(enumNames));
            return this;
        }

        private List<SourceCodeEnumValue> buildSourceCodeEnumValues(List<String> names) {

            return names.stream()
                        .map(SourceCodeEnumValue::new)
                        .collect(Collectors.toList());
        }

        public SourceCodeEnumBuilder withValueConstructor(String valueName, String value) {

            getEnumValues().add(new SourceCodeEnumValue(valueName, value));
            return this;
        }

        public SourceCodeEnumBuilder withAnnotations(List<Class<?>> annotations) {

            annotations.forEach(this::validateAnnotation);

            return this;

        }

        @SuppressWarnings("unchecked")
        private void validateAnnotation(Class<?> annotation) {

            if(annotation.isAnnotation()) {
                getAnnotations().add((Class<? extends Annotation>) annotation);
            } else {
                throw new RuntimeException(annotation.getSimpleName() + " is not a valid " +
                                           "annotation!");
            }
        }

        public SourceCodeEnumBuilder withMethods(Collection<SourceCodeMethod> sourceCodeMethods) {

            sourceCodeMethods.forEach(this::withMethod);
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public SourceCodeEnumBuilder withMethod(SourceCodeMethod sourceCodeMethod) {

            if(isDuplicateMethodName(sourceCodeMethod)) {
                throw new RuntimeException(
                        sourceCodeMethod.getMethodName() + " is a duplicate " + "method name");
            }

            getMethods().add(sourceCodeMethod);
            return this;
        }

        private boolean isDuplicateMethodName(SourceCodeMethod sourceCodeMethod) {

            return getMethods().stream()
                               .anyMatch(scm -> scm.getMethodName()
                                                   .equals(sourceCodeMethod.getMethodName()));
        }

        public SourceCodeEnumBuilder withFields(Collection<SourceCodeField> sourceCodeFields) {

            sourceCodeFields.forEach(this::withField);
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        public SourceCodeEnumBuilder withField(SourceCodeField sourceCodeField) {

            if(isDuplicateFieldName(sourceCodeField)) {
                throw new RuntimeException("Duplicate field names are not allowed. The name" + " " +
                                           sourceCodeField.getFieldName() + " was already used");
            }

            getFields().add(sourceCodeField);
            return this;
        }

        private boolean isDuplicateFieldName(SourceCodeField sourceCodeField) {

            return getFields().stream()
                              .anyMatch(scf -> scf.getFieldName()
                                                  .equals(sourceCodeField.getFieldName()));
        }

        public SourceCodeEnumBuilder withInterface(Class<?> implementedInterface) {

            if(!implementedInterface.isInterface()) {
                throw new RuntimeException(
                        implementedInterface.getName() + " is not an " + "interface.");
            }

            setImplementedInterface(implementedInterface);

            return this;
        }

        public SourceCodeEnum build() {

            JavaEnumSource javaEnumSource = Roaster.create(JavaEnumSource.class);

            javaEnumSource.setName(this.getEnumName())
                          .setPackage(getPackageName());

            if(!getAnnotations().isEmpty()) {
                getAnnotations().forEach(javaEnumSource::addAnnotation);
            }

            if(getImplementedInterface() != null) {
                javaEnumSource.addInterface(getImplementedInterface());
            }

            if(!getEnumValues().isEmpty()) {
                getEnumValues().forEach(sourceCodeEnumValue -> addEnumConstant(javaEnumSource,
                                                                               sourceCodeEnumValue));
            }

            if(!getFields().isEmpty()) {

                getFields().forEach(sourceCodeField -> {

                    javaEnumSource.addImport(sourceCodeField.getFieldType());

                    FieldSource fieldSource = javaEnumSource.addField()
                                                            .setType(sourceCodeField.getFieldType())
                                                            .setName(sourceCodeField.getFieldName())
                                                            .setVisibility(
                                                                    translateFieldAccessLevel(
                                                                            sourceCodeField));

                    if(sourceCodeField.getIsFinal() != null) {
                        fieldSource.setFinal(sourceCodeField.getIsFinal());
                    }

                });

            }

            if(!getMethods().isEmpty()) {


                getMethods().forEach(sourceCodeMethod -> {

                    addMethodImports(sourceCodeMethod, javaEnumSource);

                    javaEnumSource.addMethod(buildMethodSource(sourceCodeMethod));

                });
            }

            String fileSystemPath = javaEnumSource.getPackage() + "." + javaEnumSource.getName();
            String formattedSourceCode = javaEnumSource.toString();

            return new SourceCodeEnum(fileSystemPath, formattedSourceCode);

        }

        private void addEnumConstant(JavaEnumSource javaEnumSource,
                                     SourceCodeEnumValue sourceCodeEnumValue) {

            EnumConstantSource enumConstantSource = javaEnumSource.addEnumConstant()
                                                                  .setName(
                                                                          sourceCodeEnumValue.getName());

            if(sourceCodeEnumValue.getConstructorValue() != null) {
                enumConstantSource.setConstructorArguments(
                        sourceCodeEnumValue.getConstructorValue());
            }

        }

        private Visibility translateFieldAccessLevel(SourceCodeField sourceCodeField) {

            if(sourceCodeField == null) {
                return Visibility.PROTECTED;
            }
            if(sourceCodeField.getFieldAccessLevel()
                              .equals(FieldAccessLevel.PRIVATE))
            {
                return Visibility.PRIVATE;
            } else if(sourceCodeField.getFieldAccessLevel()
                                     .equals(FieldAccessLevel.PROTECTED))
            {
                return Visibility.PROTECTED;
            } else if(sourceCodeField.getFieldAccessLevel()
                                     .equals(FieldAccessLevel.PUBLIC))
            {
                return Visibility.PUBLIC;
            }

            return Visibility.PROTECTED;

        }

        private void addMethodImports(SourceCodeMethod sourceCodeMethod,
                                      JavaEnumSource javaEnumSource) {

            getClasses(sourceCodeMethod).forEach(javaEnumSource::addImport);

        }

        private MethodSource buildMethodSource(SourceCodeMethod sourceCodeMethod) {

            MethodSource methodSource = Roaster.create(JavaEnumSource.class)
                                               .addMethod()
                                               .setName(sourceCodeMethod.getMethodName())
                                               .setReturnType(
                                                       sourceCodeMethod.getMethodReturnType())
                                               .setBody(sourceCodeMethod.getMethodBody());

            if(!sourceCodeMethod.getMethodParameters()
                                .isEmpty())
            {

                sourceCodeMethod.getMethodParameters()
                                .forEach((parmName, parmType) -> methodSource.addParameter(
                                        parmType.getSimpleName(), parmName));

            }

            switch(sourceCodeMethod.getMethodAccessLevel()) {
                default:
                    break;
                case PUBLIC:
                    methodSource.setPublic();
                    break;
                case PRIVATE:
                    methodSource.setPrivate();
                    break;
                case PROTECTED:
                    methodSource.setProtected();
                    break;

            }

            if(sourceCodeMethod.hasAnnotations()) {
                sourceCodeMethod.getAnnotations()
                                .forEach(annotation -> methodSource.addAnnotation(annotation));
            }

            return methodSource;

        }

        private Set<Class<?>> getClasses(SourceCodeMethod sourceCodeMethod) {

            Set<Class<?>> classes = new LinkedHashSet<>();

            if(sourceCodeMethod.getMethodReturnType() != null) {
                classes.add(sourceCodeMethod.getMethodReturnType());
            }

            if(sourceCodeMethod.getMethodParameters() != null) {
                classes.addAll(sourceCodeMethod.getMethodParameters()
                                               .values());
            }

            return classes;
        }

    }
}
