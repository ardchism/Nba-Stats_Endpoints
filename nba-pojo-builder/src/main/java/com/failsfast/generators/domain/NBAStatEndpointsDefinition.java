package com.failsfast.generators.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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
        private Boolean isJavaDate;
        private Boolean isJavaMonth;

        public boolean shouldSkipGeneratingParameter() {
            return hasSuperObject() || hasJavaInteger() || hasJavaDate() || hasJavaMonth();
        }

        boolean hasSuperObject() {
            return StringUtils.isNotEmpty(getSuperObject());
        }

        boolean hasJavaInteger() {
            return getIsJavaInteger() != null && getIsJavaInteger();
        }

        boolean hasJavaDate() {
            return getIsJavaDate() != null && getIsJavaDate();
        }

        boolean hasJavaMonth() {
            return getIsJavaMonth() != null && getIsJavaMonth();
        }

    }

}
