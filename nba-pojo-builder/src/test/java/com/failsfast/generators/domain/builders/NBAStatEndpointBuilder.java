package com.failsfast.generators.domain.builders;

import com.failsfast.generators.domain.NBAStatEndpointsDefinition;

import java.lang.String;

public class NBAStatEndpointBuilder {

	private final NBAStatEndpointsDefinition.NBAStatEndpoint builtObject;

	private NBAStatEndpointBuilder() {
		this.builtObject = new NBAStatEndpointsDefinition.NBAStatEndpoint();
	}

	public static NBAStatEndpointBuilder Builder() {
		return new NBAStatEndpointBuilder();
	}

	public NBAStatEndpointBuilder withName(String name) {
		builtObject.setName(name);
		return this;
	}

	public NBAStatEndpointBuilder withUrl(String url) {
		builtObject.setUrl(url);
		return this;
	}

	public NBAStatEndpointBuilder withRequiredParameters(
			String requiredParameters) {
		builtObject.getRequiredParameters().add(requiredParameters);
		return this;
	}

	public NBAStatEndpointBuilder withParameters(String parameters) {
		builtObject.getParameters().add(parameters);
		return this;
	}

	public NBAStatEndpointsDefinition.NBAStatEndpoint get() {
		return builtObject;
	}
}