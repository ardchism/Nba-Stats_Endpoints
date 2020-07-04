package com.failsfast.generators.domain.builders;

import com.failsfast.generators.domain.NBAStatEndpointsDefinition;
import com.failsfast.generators.domain.NBAStatEndpointsDefinition.NBAStatEndpoint;
import com.failsfast.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;

public class NBAStatEndpointsDefinitionBuilder {

	private final NBAStatEndpointsDefinition builtObject;

	private NBAStatEndpointsDefinitionBuilder() {
		this.builtObject = new NBAStatEndpointsDefinition();
	}

	public static NBAStatEndpointsDefinitionBuilder Builder() {
		return new NBAStatEndpointsDefinitionBuilder();
	}

	public NBAStatEndpointsDefinitionBuilder withEndpoints(
			NBAStatEndpoint endpoints) {
		builtObject.getEndpoints().add(endpoints);
		return this;
	}

	public NBAStatEndpointsDefinitionBuilder withParameters(
			NBAStatParameter parameters) {
		builtObject.getParameters().add(parameters);
		return this;
	}

	public NBAStatEndpointsDefinition get() {
		return builtObject;
	}
}