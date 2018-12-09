package com.nbastat.player.generators.factories;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class BuilderFactory {

	public Map<String, String> build(String fullClassName) throws ClassNotFoundException{
			
		Class<?> currentClass = Class.forName(fullClassName);
		
		return buildFromClass(currentClass, fullClassName);			
	
	}
	
	private Map<String, String> buildFromClass(Class<?> currentClass, String fullClassName){
		
		Map<String, String> builders = new HashMap<>();
		
		final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
		
		addClassInformation(currentClass, javaClass);
		
		addBuiltObjectProperty(currentClass, javaClass);
		
		addConstructor(currentClass, javaClass);
		
		addStaticBuilderMethod(currentClass, javaClass);
		
		addWithMethods(currentClass, javaClass);
		
		builders.put(javaClass.getPackage().replace(".", "/") + "/" + javaClass.getName(), javaClass.toString());
		
		builders.putAll(buildInnerClassesFromClass(currentClass, fullClassName));
		
		return builders;
	}
	
	private void addClassInformation(Class<?> currentClass, JavaClassSource javaClass) {
		
		javaClass.setPackage(findPackageName(currentClass))
		 		 .setName(findClassName(currentClass));
	
	}

	private String findClassName(Class<?> currentClass) {
		return currentClass.getSimpleName() + "Builder";
	}
	
	private String findPackageName(Class<?> currentClass) {
		return currentClass.getPackage().getName() + ".builders" ;
	}
	
	private void addBuiltObjectProperty(Class<?> builtObjectType, JavaClassSource javaClass) {
		
		javaClass.addImport(builtObjectType);
		javaClass.addProperty(builtObjectType, "builtObject").setMutable(false).setAccessible(true);

	}
	
	private void addConstructor(Class<?> builtObjectType, JavaClassSource javaClass) {
		
		javaClass.addMethod()
		         .setConstructor(true)
		         .setPublic()
		         .setBody("this.builtObject = new " + builtObjectType.getSimpleName() + "();");

	}
	
	private void addStaticBuilderMethod(Class<?> builtObjectType, JavaClassSource javaClass) {
		
		javaClass.addMethod()
				 .setPublic()
				 .setStatic(true)
				 .setName("Builder")
				 .setReturnType(javaClass.getName())
				 .setBody("return new " + javaClass.getName() + "();");

	}
	
	private void addWithMethods(Class<?> currentClass, JavaClassSource javaClass) {
		
		Stream<Field> fields = Arrays.stream(currentClass.getDeclaredFields());
		
		fields.forEach(field -> {
			this.addWithMethod(field, javaClass);
		});
		
	}
	
	private void addWithMethod(Field field, JavaClassSource javaClass) {
			
		addFieldImports(javaClass, field);
		
		if(isFieldCollection(field)) {

			addCollectionWithMethod(field, javaClass);
			
		} else {

			addNonCollectionWithMethod(field, javaClass);
			
		}
	
	}
	
	private void addFieldImports(JavaClassSource javaClass, Field field) {
		
		String fieldType = field.getGenericType().toString().replace("$", ".");
		
		if(fieldType.contains("<")) {
			
			Set<String> imports = getListImports(fieldType);
			
			imports.forEach(importString -> {
				javaClass.addImport(importString);	
			});
			
			
		} else {
			
			String fieldTypeImport = field.getType().getName().replace("$", ".");
			
			javaClass.addImport(fieldTypeImport);

		
		}
		
	}
	
	private boolean isFieldCollection(Field field) {
		
		String fieldType = field.getType().getSimpleName();
		
		return fieldType.contains("List") || fieldType.contains("Set");
				
	}
	
	private void addCollectionWithMethod(Field field, JavaClassSource javaClass) {
		
		String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

		String listWithType = field.getGenericType().toString().replace("$", ".");
		String listTypeFullyQualified = listWithType.substring(listWithType.indexOf("<") + 1, listWithType.lastIndexOf(">"));
		String listType;
		
		if(listTypeFullyQualified.contains("<")) {
			listType = listTypeFullyQualified.substring(0, listTypeFullyQualified.indexOf("<") + 1) + listTypeFullyQualified.substring(listTypeFullyQualified.indexOf("<") + 1, listTypeFullyQualified.lastIndexOf(">")).substring(listTypeFullyQualified.substring(listTypeFullyQualified.indexOf("<") + 1, listTypeFullyQualified.lastIndexOf(">")).lastIndexOf(".") + 1) + ">";
		}else {
			listType = listTypeFullyQualified.substring(listTypeFullyQualified.lastIndexOf(".") + 1);
		}
		
		javaClass.addMethod()
			.setName("with" + fieldName)
			.setPublic()
			.setReturnType(javaClass.getName())
			.setBody("builtObject.get" + fieldName + "().add(" + field.getName()  +");\n return this;")
			.addParameter(listType, field.getName());
		
	}
	
	private void addNonCollectionWithMethod(Field field, JavaClassSource javaClass) {
		
		String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
		String fieldType = field.getType().getSimpleName();
		
		javaClass.addMethod()
			     .setName("with" + fieldName)
			     .setPublic()
			     .setReturnType(javaClass.getName())
			     .setBody("builtObject.set" + fieldName + "(" + field.getName()  +");\n return this;")
			     .addParameter(fieldType, field.getName());
		
	}
	
	private Set<String> getListImports(String fieldType){
		
		Set<String> imports = new HashSet<>();
		
		String listTypeFullyQualified = fieldType.substring(fieldType.indexOf("<") + 1, fieldType.lastIndexOf(">"));
			
		if(listTypeFullyQualified.contains("<")) {
				
			String collectionImplementation = listTypeFullyQualified.substring(0, listTypeFullyQualified.indexOf("<"));
				
			imports.add(collectionImplementation);
				
			String collectionType = listTypeFullyQualified.substring(listTypeFullyQualified.indexOf("<") + 1, listTypeFullyQualified.indexOf(">"));
			
			imports.add(collectionType);
				
		} else {
			
			imports.add(listTypeFullyQualified);
			
		}
		
		return imports;
		
	}
	
	private Map<String, String> buildInnerClassesFromClass(Class<?> currentClass, String fullClassName){
		
		Map<String, String> builders = new HashMap<>();
		
		currentClass.getDeclaredClasses();
		Stream<Class<?>> innerClasses = Arrays.stream(currentClass.getDeclaredClasses());
		innerClasses.forEach(innerClass -> {
			builders.putAll(buildFromClass(innerClass, fullClassName));
		});
		
		return builders;
	}
	
}
