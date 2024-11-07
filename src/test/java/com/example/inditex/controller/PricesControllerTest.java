package com.example.inditex.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	private PricesEntity createPriceEntity(long priceList, double price, String startDate, String endDate) {
        return PricesEntity.builder()
				.brandId(1)
				.startDate(LocalDateTime.parse(startDate, formatter))
				.endDate(LocalDateTime.parse(endDate, formatter))
				.priceList(priceList)
				.productId(35455)
				.priority((short) 1)
				.price(price)
				.curr("EUR")
				.build();
    }

	@Test
	void givenParameters_whenGetPricesAt10amOnJune14_AvailableDate_thenStatusOk_AndCheckPrice() throws Exception {
        PricesEntity mockEntity = createPriceEntity(1, 35.50, "2020-06-14T00:00:00", "2020-12-31T23:59:59");

        given(pricesService.getPrices(LocalDateTime.parse("2020-06-14T10:00:00", formatter), 1, 35455)).willReturn(Optional.of(mockEntity));

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
        PricesEntity mockEntity = createPriceEntity(2, 25.45, "2020-06-14T15:00:00", "2020-06-14T18:30:00");

        given(pricesService.getPrices(LocalDateTime.parse("2020-06-14T16:00:00", formatter), 1, 35455)).willReturn(Optional.of(mockEntity));

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
	void givenParameters_whenGetPricesAt21pmOnJune14_AvailableDate_thenStatusNotFoundException() throws Exception {
        given(pricesService.getPrices(LocalDateTime.parse("2020-06-14T21:00:00", formatter), 1, 35455)).willReturn(Optional.empty());

		ResultActions actions = mockMvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-14T21:00:00")
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
        PricesEntity mockEntity = createPriceEntity(3, 30.50, "2020-06-15T00:00:00", "2020-06-15T11:00:00");

        given(pricesService.getPrices(LocalDateTime.parse("2020-06-15T10:00:00", formatter), 1, 35455)).willReturn(Optional.of(mockEntity));

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
        PricesEntity mockEntity = createPriceEntity(4, 38.95, "2020-06-15T16:00:00", "2020-12-31T23:59:59");

        given(pricesService.getPrices(LocalDateTime.parse("2020-06-16T21:00:00", formatter), 1, 35455)).willReturn(Optional.of(mockEntity));

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
