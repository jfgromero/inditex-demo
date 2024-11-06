package com.example.inditex.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.inditex.persistence.entity.PricesEntity;
import com.example.inditex.persistence.repository.PricesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Francisco Gonzalez
 * 
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PricesServiceImpl implements PricesService { 
	
	private final PricesRepository pricesRepository;

    public Optional<PricesEntity> getPrices(LocalDateTime appTime, long brandId, long productId)  {
    	log.info("Starting Query... Parameters brandId {} productId {} appTime {}.", brandId, productId, appTime);
    	
		List<PricesEntity> pricesEntity = pricesRepository.getPrices(appTime, brandId, productId);
		
    	log.info("Ending Query...");
    	
    	return pricesEntity.stream().findFirst();
     }

}
