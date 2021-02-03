package com.srikanthmadhira.portfolio.pointsservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.srikanthmadhira.portfolio.pointsservice.model.Points;
import com.srikanthmadhira.portfolio.pointsservice.model.RedeemPointsRequest;
import com.srikanthmadhira.portfolio.pointsservice.model.response.RedeemResponse;
import com.srikanthmadhira.portfolio.pointsservice.service.IPointsAccountingService;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PointsServiceAccountingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IPointsAccountingService mockService;

	@BeforeAll
	public static void init() {

	}

	private HttpHeaders header;

	@BeforeEach
	public void setup() {

		header = new HttpHeaders();
	}

	@Test
	public void badEndpointFailTest() {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/invalid-endpoint")
				.accept(MediaType.APPLICATION_JSON).headers(header);
		try {
			mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
			verifyNoInteractions(mockService);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	// earnPoints - /points/earn
	// redeemPoints - /points/redeem
	@Test
	public void earnPointsNullRequestFailTest() throws JsonProcessingException {
		Points requestBean= null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(requestBean);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/points/earn").accept(MediaType.APPLICATION_JSON)
				.headers(header).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson);
		try {
			mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
			verifyNoInteractions(mockService);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void earnPointsNullUserNameTest() throws JsonProcessingException {
		Points requestBean = new Points();
		requestBean.setAccountId(111);
		requestBean.setPoints(100);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(requestBean);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/points/earn").accept(MediaType.APPLICATION_JSON)
				.headers(header).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError()).andReturn();
			assertThat(result.getResponse().getContentAsString()).contains("userName cannot be null");
			verifyNoInteractions(mockService);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void earnPointsSuceessTest() throws JsonProcessingException {
		Points requestBean = new Points();
		requestBean.setUserName("bobTheBuilder");
		requestBean.setAccountId(111);
		requestBean.setPoints(100);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(requestBean);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/points/earn").accept(MediaType.APPLICATION_JSON)
				.headers(header).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
			verify(mockService, times(1)).earnPoints(any(Points.class));
			verifyNoMoreInteractions(mockService);
			assertThat(result.getResponse().getContentAsString()).contains("Added ");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void redeemPointsNullRequestFailTest() throws JsonProcessingException {
		RedeemPointsRequest requestBean= null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(requestBean);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/points/redeem").accept(MediaType.APPLICATION_JSON)
				.headers(header).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson);
		try {
			mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andReturn();
			verifyNoInteractions(mockService);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void redeemPointsSuceessTest() throws JsonProcessingException {
		RedeemPointsRequest requestBean = new RedeemPointsRequest(111, 100);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(requestBean);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/points/redeem").accept(MediaType.APPLICATION_JSON)
				.headers(header).contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson);
		List<RedeemResponse> responseBean = new ArrayList<>();
		RedeemResponse response = new RedeemResponse("now", "bob", 100);
		responseBean.add(response);
		when(mockService.redeemPoints(requestBean)).thenReturn(responseBean);
		try {
			MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();
            List<RedeemResponse> jsonResult = mapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<List<RedeemResponse>>() {
                    });
			verify(mockService, times(1)).redeemPoints(any(RedeemPointsRequest.class));
			verifyNoMoreInteractions(mockService);
			assertEquals(jsonResult.size(),1);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
