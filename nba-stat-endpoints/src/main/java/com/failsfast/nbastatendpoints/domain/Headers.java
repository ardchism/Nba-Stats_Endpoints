package com.failsfast.nbastatendpoints.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties("requestheaders")
@Data
public class Headers {

    private Map<String, String> headers;


    public List<ClientHttpRequestInterceptor> getInterceptors() {

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        getHeaders().forEach(
                (name, value) -> interceptors.add(new HttpHeaderInterceptor(name, value)));

        return interceptors;
    }

}
