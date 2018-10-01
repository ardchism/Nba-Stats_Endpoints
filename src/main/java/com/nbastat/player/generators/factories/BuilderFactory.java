package com.nbastat.player.generators.factories;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

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
	
	private void buildFromClass(Class<?> currentClass, String fullClassName) {
		System.out.println(currentClass.getName());
		Stream<Field> fields = Arrays.stream(currentClass.getDeclaredFields());
		fields.forEach(field -> System.out.println(field.getName() + " : " + field.getGenericType().toString().replace(fullClassName + "$", "")));
		currentClass.getDeclaredClasses();
		Stream<Class<?>> innerClasses = Arrays.stream(currentClass.getDeclaredClasses());
		innerClasses.forEach(innerClass -> {
			System.out.println();
			buildFromClass(innerClass, fullClassName);
		});
	}
	
}
