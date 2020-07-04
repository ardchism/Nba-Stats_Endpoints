package com.nbastat.player.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadersIntegrationTests {

    @Autowired
    Headers headers;

    @Test
    public void getHeadersHappyPath() {

        assertThat(headers.getInterceptors()
                          .size()).isEqualTo(headers.getHeaders()
                                                    .size());

    }

}
