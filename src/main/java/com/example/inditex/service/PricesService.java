package com.example.inditex.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.inditex.persistence.entity.PricesEntity;

public interface PricesService {
	
    Optional<PricesEntity> getPrices(LocalDateTime appTime, long brandId, long productId);

}
