package com.example.inditex;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.inditex.controller.dto.PricesDto;
import com.example.inditex.persistence.entity.PricesEntity;
import com.example.inditex.persistence.repository.PricesRepository;

/**
 * Integration Test class for {@link InditexDemoApplication}
 * 
 * @author Juan Francisco Gonzalez
 * 
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InditexDemoApplicationIntegrationTests {

	@LocalServerPort
	int port;

	@Autowired
	protected PricesRepository prices;

	@Autowired
	private RestTemplateBuilder builder;

	@Test
	void testPrices_at_10_of_Day14_Product_35455_Brand_1() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<PricesDto> result = template.exchange(UriComponentsBuilder.fromUriString("/inditex-demo/api/v1/prices")
					.queryParam("appTime", LocalDateTime.of(2020, 6, 14, 10, 0))
					.queryParam("brandId", "1")
					.queryParam("productId", "35455")
					.build()
					.toUriString(),
				HttpMethod.GET, 
				constructEmptyEntity(), 
				PricesDto.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getPrice()).isEqualTo(35.5);
		
	}

	@Test
	void testPrices_at_16_of_Day14_Product_35455_Brand_1() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<PricesDto> result = template.exchange(UriComponentsBuilder.fromUriString("/inditex-demo/api/v1/prices")
					.queryParam("appTime", LocalDateTime.of(2020, 6, 14, 16, 0))
					.queryParam("brandId", "1")
					.queryParam("productId", "35455")
					.build()
					.toUriString(),
				HttpMethod.GET, 
				constructEmptyEntity(), 
				PricesDto.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getPrice()).isEqualTo(25.5);
		
	}

	@Test
	void testPrices_at_21_of_Day14_Product_35455_Brand_1() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<PricesDto> result = template.exchange(UriComponentsBuilder.fromUriString("/inditex-demo/api/v1/prices")
					.queryParam("appTime", LocalDateTime.of(2020, 6, 14, 21, 0))
					.queryParam("brandId", "1")
					.queryParam("productId", "35455")
					.build()
					.toUriString(),
				HttpMethod.GET, 
				constructEmptyEntity(), 
				PricesDto.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getPrice()).isEqualTo(35.5);
		
	}

	@Test
	void testPrices_at_10_of_Day15_Product_35455_Brand_1() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<PricesDto> result = template.exchange(UriComponentsBuilder.fromUriString("/inditex-demo/api/v1/prices")
					.queryParam("appTime", LocalDateTime.of(2020, 6, 15, 10, 0))
					.queryParam("brandId", "1")
					.queryParam("productId", "35455")
					.build()
					.toUriString(),
				HttpMethod.GET, 
				constructEmptyEntity(), 
				PricesDto.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getPrice()).isEqualTo(30.5);
		
	}

	@Test
	void testPrices_at_21_of_Day16_Product_35455_Brand_1() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		ResponseEntity<PricesDto> result = template.exchange(UriComponentsBuilder.fromUriString("/inditex-demo/api/v1/prices")
					.queryParam("appTime", LocalDateTime.of(2020, 6, 16, 21, 0))
					.queryParam("brandId", "1")
					.queryParam("productId", "35455")
					.build()
					.toUriString(),
				HttpMethod.GET, 
				constructEmptyEntity(), 
				PricesDto.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getPrice()).isEqualTo(38.95);
		
	}

	@Test
	void testPrices_at_10_of_Day11_Product_35455_Brand_1_ShouldReturn_404() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		try {
			ResponseEntity<PricesDto> result = template.exchange(UriComponentsBuilder.fromUriString("/inditex-demo/api/v1/prices")
					.queryParam("appTime", LocalDateTime.of(2020, 6, 11, 10, 0))
					.queryParam("brandId", "1")
					.queryParam("productId", "35455")
					.build()
					.toUriString(),
				HttpMethod.GET, 
				constructEmptyEntity(), 
				PricesDto.class);			
		} catch (HttpStatusCodeException exception) {
			assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		}		
	}

	@Test
	void testPrices_at_10_of_Day14_Product_35455_Brand_0_ShouldReturn_400() {
		RestTemplate template = builder.rootUri("http://localhost:" + port).build();
		try {
			ResponseEntity<PricesDto> result = template.exchange(UriComponentsBuilder.fromUriString("/inditex-demo/api/v1/prices")
					.queryParam("appTime", LocalDateTime.of(2020, 6, 11, 10, 0))
					.queryParam("brandId", "0")
					.queryParam("productId", "35455")
					.build()
					.toUriString(),
				HttpMethod.GET, 
				constructEmptyEntity(), 
				PricesDto.class);			
		} catch (HttpStatusCodeException exception) {
			assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		}		
	}

	@Test
	void shouldFindAll() {
		List<PricesEntity> prices = this.prices.findAll();
		assertThat(prices).asList().hasSize(4);
	}

    private HttpEntity<Void> constructEmptyEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<Void>(null, headers);

        return entity;
    }

}
