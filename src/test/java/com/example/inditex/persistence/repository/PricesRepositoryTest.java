package com.example.inditex.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.inditex.persistence.entity.PricesEntity;
import com.example.inditex.persistence.repository.PricesRepository;

/**
 * @author Juan Francisco Gonzalez
 * 
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PricesRepositoryTest {

	@Autowired
	protected PricesRepository pricesRepository;

	@Test
	void shouldFindAll() {
		List<PricesEntity> prices = this.pricesRepository.findAll();
		assertThat(prices).asList().hasSize(4);
	}

	@Test
	void givenParameters_whenGetPrices_thenShouldReturn_PricesList() {
		List<PricesEntity> prices = pricesRepository.getPrices(LocalDateTime.of(2020, 6, 14, 15, 10), 1, 35455);
		
		assertThat(prices).asList().hasSize(2);
        assertEquals(25.5, prices.get(0).getPrice());
        assertEquals(35.5, prices.get(1).getPrice());
	}

}
