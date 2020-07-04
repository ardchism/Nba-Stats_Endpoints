package com.failsfast.generators.domain.builders;

import com.failsfast.generators.domain.NBAStatEndpointsDefinition;

import java.lang.String;
import java.lang.Boolean;

public class NBAStatParameterBuilder {

	private final NBAStatEndpointsDefinition.NBAStatParameter builtObject;

	private NBAStatParameterBuilder() {
		this.builtObject = new NBAStatEndpointsDefinition.NBAStatParameter();
	}

	public static NBAStatParameterBuilder Builder() {
		return new NBAStatParameterBuilder();
	}

	public NBAStatParameterBuilder withName(String name) {
		builtObject.setName(name);
		return this;
	}

	public NBAStatParameterBuilder withDefaultValue(String defaultValue) {
		builtObject.setDefaultValue(defaultValue);
		return this;
	}

	public NBAStatParameterBuilder withValues(String values) {
		builtObject.getValues().add(values);
		return this;
	}

	public NBAStatParameterBuilder withSuperObject(String superObject) {
		builtObject.setSuperObject(superObject);
		return this;
	}

	public NBAStatParameterBuilder withIsJavaInteger(Boolean isJavaInteger) {
		builtObject.setIsJavaInteger(isJavaInteger);
		return this;
	}

	public NBAStatParameterBuilder withIsJavaDate(Boolean isJavaDate) {
		builtObject.setIsJavaDate(isJavaDate);
		return this;
	}

	public NBAStatParameterBuilder withIsJavaMonth(Boolean isJavaMonth) {
		builtObject.setIsJavaMonth(isJavaMonth);
		return this;
	}

	public NBAStatEndpointsDefinition.NBAStatParameter get() {
		return builtObject;
	}
}