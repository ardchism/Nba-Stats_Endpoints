package com.nbastat.player.generators.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NBAStatEndpointsDefinition {

    private List<NBAStatEndpoint> endpoints = new ArrayList<>();
    private List<NBAStatParameter> parameters = new ArrayList<>();

    @Data
    public static class NBAStatEndpoint {

        private String name;
        private String url;
        private List<String> requiredParameters = new ArrayList<>();
        private List<String> parameters = new ArrayList<>();

    }

    @Data
    public static class NBAStatParameter {

        private String       name;
        private String       defaultValue;
        private List<String> values = new ArrayList<>();
        private String       superObject;
        private Boolean      isJavaInteger;

    }

}
