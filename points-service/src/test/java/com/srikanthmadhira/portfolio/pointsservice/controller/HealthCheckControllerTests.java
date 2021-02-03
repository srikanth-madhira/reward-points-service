package com.srikanthmadhira.portfolio.pointsservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.srikanthmadhira.portfolio.pointsservice.model.AppStatus;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HealthCheckControllerTests {

    private static final String HEALTH = "/health";

    @Autowired
    private MockMvc mockMvc;

    private HttpHeaders headers;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setBasicAuth("user", "password");
    }

    @Test
    public void testAvailabilityJson() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(HEALTH)
                .accept(MediaType.APPLICATION_JSON).headers(headers);

        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(content().string(containsString(AppStatus.UP)));

    }

}
