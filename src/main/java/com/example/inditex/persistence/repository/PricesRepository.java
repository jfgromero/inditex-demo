package com.example.inditex.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.inditex.persistence.entity.PricesEntity;
import com.example.inditex.persistence.entity.PricesEntityId;

/**
 * @author Juan Francisco Gonzalez
 * 
 */
public interface PricesRepository extends JpaRepository<PricesEntity, PricesEntityId>{

	@Query("SELECT p FROM PricesEntity p WHERE p.brandId = :brandId AND p.startDate <= :appTime AND p.endDate > :appTime AND p.productId = :productId order by p.priority desc")
	List<PricesEntity> getPrices(@Param("appTime") LocalDateTime appTime, @Param("brandId") long brandId, @Param("productId") long productId);

}
