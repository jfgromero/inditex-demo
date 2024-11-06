package com.example.inditex.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.inditex.controller.mapper.PricesDtoMapperImpl;
import com.example.inditex.persistence.entity.PricesEntity;
import com.example.inditex.service.PricesService;

/**
 * Unit Test class for {@link PricesController} but using {@link MockMvc}
 * All functional cases has been covered by Integration Test so purpose for this test is only check one case
 * 
 * @author Juan Francisco Gonzalez
 * 
 */
@WebMvcTest(PricesController.class)
@Import(PricesDtoMapperImpl.class)
public class PricesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PricesService pricesService;

	@BeforeEach
	void setup() {		
		PricesEntity pricesEntity = PricesEntity.builder()
				.brandId(1)
				.startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
				.endDate(LocalDateTime.of(2020, 6, 22, 10, 0))
				.productId(35455)
				.priceList(1)
				.priority((short)1)
				.price(35.5)
				.curr("EUR").build();

		given(this.pricesService.getPrices(LocalDateTime.of(2020, 6, 14, 10, 0), 1, 35455)).willReturn(Optional.of(pricesEntity));
	}

	@Test
	void givenParameters_whenGetPrices_AvailableDate_thenStatusOk_AndCheckPrice() throws Exception {
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
	void givenParameters_whenGetPrices_AvailableDate_thenStatus404_AndPricesNotFoundException() throws Exception {
		ResultActions actions = mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-11T10:00:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		
		actions.andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
			.andExpect(jsonPath("$.detail", containsString("Price Not Found")));
	}

}
