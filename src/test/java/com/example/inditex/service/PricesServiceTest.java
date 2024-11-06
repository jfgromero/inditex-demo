package com.example.inditex.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.inditex.persistence.entity.PricesEntity;
import com.example.inditex.persistence.repository.PricesRepository;

/**
 * @author Juan Francisco Gonzalez
 * 
 */
@ExtendWith(MockitoExtension.class)
public class PricesServiceTest {

	@Mock
	PricesRepository prices;

	PricesService pricesService;

	@BeforeEach
	void setUp() {
		pricesService = new PricesServiceImpl(prices);
	}

	@Test
	void shouldFindPrices() throws Exception {
		PricesEntity pricesEntity1 = PricesEntity.builder()
				.brandId(1)
				.startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
				.endDate(LocalDateTime.of(2020, 6, 14, 23, 0))
				.productId(35455)
				.priceList(1)
				.priority((short)1)
				.price(35.5)
				.curr("EUR").build();
		PricesEntity pricesEntity2 = PricesEntity.builder()
				.brandId(1)
				.startDate(LocalDateTime.of(2020, 6, 14, 12, 0))
				.endDate(LocalDateTime.of(2020, 6, 22, 15, 0))
				.productId(35455)
				.priceList(2)
				.priority((short)2)
				.price(25.5)
				.curr("EUR").build();

		given(this.prices.getPrices(LocalDateTime.of(2020, 6, 14, 14, 0), 1, 35455)).willReturn(List.of(pricesEntity2, pricesEntity1));
		
		Optional<PricesEntity> price = pricesService.getPrices(LocalDateTime.of(2020, 6, 14, 14, 0), 1, 35455);
		
		assertTrue(price.isPresent());
		assertThat(price.get()).isEqualTo(pricesEntity2);
	}

}
