package com.example.inditex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.inditex.persistence.entity.PricesEntity;
import com.example.inditex.persistence.repository.PricesRepository;

/**
 * Integration Test class for {@link InditexDemoApplication} but using {@link MockMvc}
 * 
 * @author Juan Francisco Gonzalez
 * 
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class InditexDemoApplicationUsingMockMvcIntegrationTests {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	protected PricesRepository prices;


	@Test
	void givenPrices_whenGetPrices_AvailableDate_thenStatus200() throws Exception  {				
		mvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-14T10:30:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
			    .andExpect(status().isOk())
			    .andExpect(content()
			    		.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			      .andExpect(jsonPath("$.price", is(35.5)));
	}

	@Test
	void givenPrices_whenGetPrices_NotAvailableDate_thenStatus404() throws Exception  {				
		mvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-11T10:30:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
			    .andExpect(status().isNotFound())
			    .andExpect(content()
			    		.contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
			      .andExpect(jsonPath("$.detail", containsString("Price Not Found")));
;
	}

	@Test
	void givenPrices_whenGetPrices_NotValidDate_thenStatus400() throws Exception  {				
		mvc.perform(get("/api/v1/prices")
				.queryParam("appTime", "2020-06-11T30:30:00")
				.queryParam("brandId", "1")
				.queryParam("productId", "35455")
			    .contentType(MediaType.APPLICATION_JSON)
			    .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
			    .andExpect(status().isBadRequest())
			    .andExpect(content()
			    		.contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
			      .andExpect(jsonPath("$.detail", containsString("Failed to convert")));
;
	}

	@Test
	void shouldFindAll() {
		List<PricesEntity> prices = this.prices.findAll();
		assertThat(prices).asList().hasSize(4);
	}

}
