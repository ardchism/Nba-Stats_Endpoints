package com.nbastat.player.api.endpoints;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerStatsEndpointsIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    public void getPlayerByIdHappyPath() {

        mvc.perform(get("/V1/PlayerStats/").param("playerId", "201143"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.PLAYER_ID").exists())
           .andExpect(jsonPath("$.PLAYER_ID").value("201143"))
           .andExpect(jsonPath("$.PLAYER_NAME").exists())
           .andExpect(jsonPath("$.PLAYER_NAME").value("Al Horford"));

    }

}
