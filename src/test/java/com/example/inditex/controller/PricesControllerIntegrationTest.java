package com.example.inditex.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

/**
 * Unit Test class for {@link PricesController} but using {@link MockMvc}
 * All functional cases has been covered by Integration Test so purpose for this test is only check one case
 * 
 * @author Juan Francisco Gonzalez
 * 
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:db/h2/schema.sql", "classpath:db/h2/data.sql"})
public class PricesControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void givenParameters_whenGetPricesAt10amOnJune14_AvailableDate_thenStatusOk_AndCheckPrice() throws Exception {
		ResultActions actions = mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-14T10:00:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.price", is(35.5)));
	}

	@Test
	void givenParameters_whenGetPricesAt16pmOnJune14_AvailableDate_thenStatusOk_AndCheckPrice() throws Exception {
		ResultActions actions = mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-14T16:00:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.price", is(25.45)));
	}

	@Test
	void givenParameters_whenGetPricesAt21pmOnJune14_AvailableDate_thenStatusOk_AndCheckPrice() throws Exception {
		mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-14T21:00:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.price").value(35.50));
		
	}

	@Test
	void givenParameters_whenGetPricesAt21pmOnJune11_AvailableDate_thenStatusNotFound() throws Exception {
		ResultActions actions = mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-11T21:00:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		
		actions.andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
			.andExpect(jsonPath("$.detail", containsString("Price Not Found")));
	}

	@Test
	void givenParameters_whenGetPricesAt10amOnJune15_AvailableDate_thenStatusOk_AndCheckPrice() throws Exception {
		ResultActions actions = mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-15T10:00:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.price", is(30.50)));
	}

	@Test
	void givenParameters_whenGetPricesAt21pmOnJune16_AvailableDate_thenStatusOk_AndCheckPrice() throws Exception {
		ResultActions actions = mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-16T21:00:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.price", is(38.95)));
	}

}
