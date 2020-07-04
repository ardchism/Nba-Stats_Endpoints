package com.failfast.sourcewriter.domain;

import lombok.*;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Visibility;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.PropertySource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class SourceCode {

    private final String fullyQualifiedClassName;
    private final String formattedSourceCode;

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SourceCodeBuilder {

        @Getter(AccessLevel.PRIVATE)
        @NonNull
        private final String className;
        @Getter(AccessLevel.PRIVATE)
        @NonNull
        private final String packageName;

        @Getter(AccessLevel.PRIVATE)
        private List<SourceCodeField> sourceCodeFields = new ArrayList<>();
        @Getter(AccessLevel.PRIVATE)
        private List<SourceCodeProperty> sourceCodeProperties = new ArrayList<>();
        @Getter(AccessLevel.PRIVATE)
        private List<SourceCodeMethod> sourceCodeMethods = new ArrayList<>();

        @Getter
        @Setter
        private SourceCodeMethod sourceCodeConstructor;

        public static SourceCodeBuilder Builder(String className, String packageName) {
            return new SourceCodeBuilder(className, packageName);
        }

        public SourceCodeBuilder withSourceCodeFields(List<SourceCodeField> sourceCodeFields) {

            sourceCodeFields.forEach(this::withSourceCodeField);

            return this;

        }

        public SourceCodeBuilder withSourceCodeField(SourceCodeField sourceCodeField) {

            if(validateFieldOrPropertyNameIsNotDuplicated(sourceCodeField.getFieldName())) {
                throw new RuntimeException(
                        "Cannot create two properties/fields with the same name : " + sourceCodeField.getFieldName());
            }

            getSourceCodeFields().add(sourceCodeField);

            return this;

        }

        public boolean validateFieldOrPropertyNameIsNotDuplicated(String name) {

            List<String> currentNames = new ArrayList<>();
            currentNames.addAll(getSourceCodeProperties().stream()
                                                         .map(SourceCodeProperty::getPropertyName)
                                                         .collect(Collectors.toList()));
            currentNames.addAll(getSourceCodeFields().stream()
                                                     .map(SourceCodeField::getFieldName)
                                                     .collect(Collectors.toList()));
            return currentNames.contains(name);

        }

        public SourceCodeBuilder withSourceCodeProperties(
                Collection<SourceCodeProperty> sourceCodeProperties) {

            sourceCodeProperties.forEach(this::withSourceCodeProperty);

            return this;

        }

        public SourceCodeBuilder withSourceCodeProperty(SourceCodeProperty sourceCodeProperty) {

            if(validateFieldOrPropertyNameIsNotDuplicated(sourceCodeProperty.getPropertyName())) {
                throw new RuntimeException(
                        "Cannot create two properties/fields with the same name : "
                                + sourceCodeProperty.getPropertyName());
            }

            getSourceCodeProperties().add(sourceCodeProperty);

            return this;

        }

        public SourceCodeBuilder withSourceCodeMethods(List<SourceCodeMethod> sourceCodeMethods) {

            for(SourceCodeMethod sourceCodeMethod : sourceCodeMethods) {

                withSourceCodeMethod(sourceCodeMethod);

            }

            return this;
        }

        public SourceCodeBuilder withSourceCodeMethod(SourceCodeMethod sourceCodeMethod) {

            if(isSourceCodeMethodNameDuplicate(sourceCodeMethod.getMethodName())) {
                throw new RuntimeException(
                        "Duplicate sourceCodeMethods named " + sourceCodeMethod.getMethodName());
            }

            getSourceCodeMethods().add(sourceCodeMethod);

            return this;
        }

        public boolean isSourceCodeMethodNameDuplicate(String methodName) {

            return getSourceCodeMethods().stream()
                                         .anyMatch(
                                                 sourceCodeMetohd -> sourceCodeMetohd.getMethodName()
                                                                                     .equalsIgnoreCase(
                                                                                             methodName));

        }

        public SourceCodeBuilder withSourceCodeConstructor(SourceCodeMethod sourceCodeConstructor) {
            setSourceCodeConstructor(sourceCodeConstructor);
            return this;
        }

        public SourceCode build() {

            JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class);

            javaClassSource.setName(getClassName())
                           .setPackage(getPackageName());

            if(getSourceCodeConstructor() != null) {

                MethodSource methodSource = javaClassSource.addMethod()
                                                           .setConstructor(true)
                                                           .setBody(
                                                                   getSourceCodeConstructor().getMethodBody());
                if(getSourceCodeConstructor().getMethodAccessLevel() == MethodAccessLevel.PRIVATE) {
                    methodSource.setPrivate();
                } else if(getSourceCodeConstructor().getMethodAccessLevel() == MethodAccessLevel.PUBLIC) {
                    methodSource.setPublic();
                } else if(getSourceCodeConstructor().getMethodAccessLevel() == MethodAccessLevel.PROTECTED) {
                    methodSource.setProtected();
                }

                getSourceCodeConstructor().getMethodParameters()
                                          .forEach((name, type) -> {

                                              javaClassSource.addImport(type);
                                              methodSource.addParameter(type.getSimpleName(), name);

                                          });

            }

            getSourceCodeFields().forEach(
                    sourceCodeField -> addField2JavaClassSource(javaClassSource, sourceCodeField));

            getSourceCodeProperties().forEach(
                    sourceCodeProperty -> addProperty2JavaClassSource(javaClassSource,
                                                                      sourceCodeProperty));

            getSourceCodeMethods().forEach(
                    sourceCodeMethod -> this.addMethod2JavaClassSource(javaClassSource,
                                                                       sourceCodeMethod));

            String fileSystemPath = javaClassSource.getPackage() + "." + javaClassSource.getName();
            String formattedSourceCode = javaClassSource.toString();

            return new SourceCode(fileSystemPath, formattedSourceCode);

        }

        private void addField2JavaClassSource(JavaClassSource javaClassSource,
                                              SourceCodeField sourceCodeField) {

            javaClassSource.addImport(sourceCodeField.getFieldType());

            if(sourceCodeField.getGenericType() != null) {
                javaClassSource.addImport(sourceCodeField.getGenericType());
            }

            FieldSource<JavaClassSource> field = javaClassSource.addField()
                                                                .setName(
                                                                        sourceCodeField.getFieldName())
                                                                .setType(
                                                                        sourceCodeField.getSimpleFieldTypeName());

            if(sourceCodeField.getFieldAccessLevel() != null) {
                field.setVisibility(findFieldVisibilty(sourceCodeField));
            }

            if(sourceCodeField.getIsFinal() != null) {
                field.setFinal(sourceCodeField.getIsFinal());
            }

        }

        private void addProperty2JavaClassSource(JavaClassSource javaClassSource,
                                                 SourceCodeProperty sourceCodeProperty) {

            javaClassSource.addImport(sourceCodeProperty.getPropertyType());

            if(sourceCodeProperty.getGenericType() != null) {
                javaClassSource.addImport(sourceCodeProperty.getGenericType());
            }

            PropertySource<JavaClassSource> property = javaClassSource
                    .addProperty(sourceCodeProperty.getSimplePropertyTypeName(),
                                 sourceCodeProperty.getPropertyName());

            if(sourceCodeProperty.getIsFinal() != null) {
                property.setMutable(!sourceCodeProperty.getIsFinal());
            }

        }

        private void addMethod2JavaClassSource(JavaClassSource javaClassSource,
                                               SourceCodeMethod sourceCodeMethod) {

            MethodSource<JavaClassSource> methodSource = javaClassSource.addMethod();

            if(sourceCodeMethod.getMethodAccessLevel() == MethodAccessLevel.PRIVATE) {
                methodSource.setPrivate();
            } else if(sourceCodeMethod.getMethodAccessLevel() == MethodAccessLevel.PUBLIC) {
                methodSource.setPublic();
            } else if(sourceCodeMethod.getMethodAccessLevel() == MethodAccessLevel.PROTECTED) {
                methodSource.setProtected();
            }

            methodSource.setStatic(sourceCodeMethod.getIsStatic());

            if(sourceCodeMethod.getMethodReturnType() != null) {

                javaClassSource.addImport(sourceCodeMethod.getMethodReturnType());

                if(sourceCodeMethod.getMethodGenericReturnType() != null) {
                    javaClassSource.addImport(sourceCodeMethod.getMethodGenericReturnType());
                }

                methodSource.setReturnType(sourceCodeMethod.getSimpleMethodReturnType());

            } else if(sourceCodeMethod.getDoesMethodReturnSelf() != null && sourceCodeMethod.getDoesMethodReturnSelf()) {

                methodSource.setReturnType(javaClassSource.getName());

            }

            methodSource.setName(sourceCodeMethod.getMethodName());

            sourceCodeMethod.getMethodParameters()
                            .forEach((name, type) -> {

                                javaClassSource.addImport(type);
                                methodSource.addParameter(type.getSimpleName(), name);

                            });

            methodSource.setBody(sourceCodeMethod.getMethodBody());

        }

        private Visibility findFieldVisibilty(SourceCodeField sourceCodeField) {

            if(sourceCodeField.getFieldAccessLevel() == null) {
                return null;
            }

            switch(sourceCodeField.getFieldAccessLevel()) {

                case PUBLIC:
                    return Visibility.PUBLIC;

                case PROTECTED:
                    return Visibility.PROTECTED;

                case PRIVATE:
                    return Visibility.PRIVATE;

                default:
                    return null;

            }

        }

    }

}
