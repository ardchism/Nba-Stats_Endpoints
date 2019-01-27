package com.nbastat.player.domain.builders;

import com.nbastat.player.generators.domain.NBAStatEndpointsDefinition.NBAStatParameter;

import java.util.List;

public class NBAStatParameterBuilder {

    private NBAStatParameter builtObject;

    public static NBAStatParameterBuilder builder() {
        return new NBAStatParameterBuilder();
    }

    public NBAStatParameterBuilder build() {
        builtObject = new NBAStatParameter();
        return this;
    }

    public NBAStatParameterBuilder withName(String name) {
        builtObject.setName(name);
        return this;
    }

    public NBAStatParameterBuilder withDefaultValue(String defaultValue) {
        builtObject.setDefaultValue(defaultValue);
        return this;
    }

    public NBAStatParameterBuilder withSuperObject(String superObject) {
        builtObject.setSuperObject(superObject);
        return this;
    }

    public NBAStatParameterBuilder withValues(List<String> values) {
        values.forEach(value -> builtObject.getValues()
                                           .add(value));
        return this;
    }

    public NBAStatParameter get() {
        return builtObject;
    }

}
