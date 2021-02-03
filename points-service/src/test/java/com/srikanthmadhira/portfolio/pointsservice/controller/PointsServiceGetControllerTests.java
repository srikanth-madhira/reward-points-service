package com.srikanthmadhira.portfolio.pointsservice.controller;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.srikanthmadhira.portfolio.pointsservice.model.response.PointsResponse;
import com.srikanthmadhira.portfolio.pointsservice.service.IGetPointsService;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PointsServiceGetControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private IGetPointsService mockService;
    
    @BeforeAll
    public static void init() {
    	
    }

    private ObjectMapper mapper = new ObjectMapper();
    private HttpHeaders header;
    
    @BeforeEach
    public void setup() {

        header = new HttpHeaders();
    }
    
    @Test
    public void badEndpointFailTest() {
         RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/invalid-endpoint")
                .accept(MediaType.APPLICATION_JSON).headers(header);
    	try {
			mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
			verifyNoInteractions(mockService);
		} catch (Exception e) {
			fail(e.getMessage());
		}
    }

    @Test
    public void getAllEmptyResponseTest() {
         RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/points")
                .accept(MediaType.APPLICATION_JSON).headers(header);
         when(mockService.getAll(any(Pageable.class))).thenReturn(new ArrayList<>());
    	try {
			MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
            List<PointsResponse> jsonResult = mapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<List<PointsResponse>>() {
                    });

			verify(mockService, times(1)).getAll(any(Pageable.class));
			verifyNoMoreInteractions(mockService);
			assertEquals(jsonResult.size(), 0);
		} catch (Exception e) {
			fail(e.getMessage());
		}
    }
    
    @Test
    public void getAllSuccessTest() {
         RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/points")
                .accept(MediaType.APPLICATION_JSON).headers(header);
         List<PointsResponse> mockResponseBean = new ArrayList<>();
         PointsResponse points = new PointsResponse();
         points.setAccountId(111111);
         mockResponseBean.add(points);
         when(mockService.getAll(any(Pageable.class))).thenReturn(mockResponseBean);
    	try {
			MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
            List<PointsResponse> jsonResult = mapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<List<PointsResponse>>() {
                    });

			verify(mockService, times(1)).getAll(any(Pageable.class));
			verifyNoMoreInteractions(mockService);
			assertEquals(jsonResult.size(), 1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
    }


}
