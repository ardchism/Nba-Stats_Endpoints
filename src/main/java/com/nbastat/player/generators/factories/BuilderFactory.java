package com.nbastat.player.generators.factories;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import lombok.SneakyThrows;

public class BuilderFactory {

	public void build(String fullClassName) {
		try {
			Class<?> currentClass = Class.forName(fullClassName);
			buildFromClass(currentClass, fullClassName);			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildFromClass(Class<?> currentClass, String fullClassName){
		
		String packageName = currentClass.getPackage().getName() + ".builders" ;
		String className = currentClass.getSimpleName() + "Builder";
		final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
		javaClass.setPackage(packageName).setName(className);
		javaClass.addImport(currentClass);
		javaClass.addImport(String.class);
		javaClass.addImport(List.class);
		javaClass.addProperty(currentClass, "builtObject").setMutable(false).setAccessible(true);
		
		//TODO: Create a with method for each field.
		Stream<Field> fields = Arrays.stream(currentClass.getDeclaredFields());
		fields.forEach(field -> {
				String fieldType = field.getType().getSimpleName();
				String fieldName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
				if(fieldType.contains("List")) {
	
					String listType = field.getGenericType().toString().replace("java.lang.", "").replace("java.util.", "");
					
					javaClass.addMethod()
						.setName("with" + fieldName)
						.setPublic()
						.setReturnType(javaClass.getName())
						.setBody("builtObject.get" + fieldName + ".add(" + field.getName()  +");\n return this;")
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
		
				System.out.println(javaClass.toString());
		
		//TODO: Stream inner classes and call recursively. 
		currentClass.getDeclaredClasses();
		Stream<Class<?>> innerClasses = Arrays.stream(currentClass.getDeclaredClasses());
		innerClasses.forEach(innerClass -> {
			System.out.println();
			buildFromClass(innerClass, fullClassName);
		});
	}
	
}
