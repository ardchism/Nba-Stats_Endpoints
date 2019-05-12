package com.nbastat.player;

import com.nbastat.player.domain.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.devtools.remote.client.HttpHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PlayerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(
        @Autowired
            Headers headers) {

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HttpHeaderInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        interceptors.add(new HttpHeaderInterceptor("Accept-Encoding", "utf-8"));
        interceptors.add(new HttpHeaderInterceptor("Accept-Language", "en-US,en;q=0.5"));
        interceptors.add(new HttpHeaderInterceptor("Connection", "keep-alive"));
        interceptors.add(new HttpHeaderInterceptor("Host", "stats.nba.com"));
        interceptors.add(new HttpHeaderInterceptor("Referer",
                                                   "http://stats.nba.com/players/t…17&SeasonType=Regular%20Season"));
        interceptors.add(new HttpHeaderInterceptor("User-Agent",
                                                   "Mozilla/5.0 (X11; Fedora; Linu…) Gecko/20100101 Firefox/59.0"));
        interceptors.add(new HttpHeaderInterceptor("x-nba-stats-origin", "stats"));
        interceptors.add(new HttpHeaderInterceptor("x-nba-stats-token", "true"));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(headers.getInterceptors());

        return restTemplate;
    }
}
