package com.nbastat.player.generators.factories;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import lombok.SneakyThrows;

public class BuilderFactory {

	public Map<String, String> build(String fullClassName) throws ClassNotFoundException{
			Class<?> currentClass = Class.forName(fullClassName);
			return buildFromClass(currentClass, fullClassName);			
	}
	
	private Map<String, String> buildFromClass(Class<?> currentClass, String fullClassName){
		
		Map<String, String> builders = new HashMap<>();
		
		String packageName = currentClass.getPackage().getName() + ".builders" ;
		String className = currentClass.getSimpleName() + "Builder";
	
		final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
		
		javaClass.setPackage(packageName).setName(className);
		javaClass.addImport(currentClass);
		javaClass.addProperty(currentClass, "builtObject").setMutable(false).setAccessible(true);
		
		javaClass.addMethod()
		         .setConstructor(true)
		         .setPublic()
		         .setBody("this.builtObject = new " + currentClass.getSimpleName() + "();");
		
		javaClass.addMethod()
				 .setPublic()
				 .setStatic(true)
				 .setName("Builder")
				 .setReturnType(javaClass.getName())
				 .setBody("return new " + javaClass.getName() + "();");
		
		//TODO: Create a with method for each field.
		Stream<Field> fields = Arrays.stream(currentClass.getDeclaredFields());
		fields.forEach(field -> {
			
				addFieldImports(javaClass, field);
				
				String fieldType = field.getType().getSimpleName();
				String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				if(fieldType.contains("List") || fieldType.contains("Set")) {
	
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
					
				} else {

					javaClass.addMethod()
						.setName("with" + fieldName)
						.setPublic()
						.setReturnType(javaClass.getName())
						.setBody("builtObject.set" + fieldName + "(" + field.getName()  +");\n return this;")
						.addParameter(fieldType, field.getName());
				
				}
		});
		
			builders.put(javaClass.getPackage().replace(".", "/") + "/" + javaClass.getName(), javaClass.toString());
		
		//TODO: Stream inner classes and call recursively. 
		currentClass.getDeclaredClasses();
		Stream<Class<?>> innerClasses = Arrays.stream(currentClass.getDeclaredClasses());
		innerClasses.forEach(innerClass -> {
			System.out.println();
			builders.putAll(buildFromClass(innerClass, fullClassName));
		});
		
		return builders;
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
		
		return new HashSet<>();
		
	}
	
}
