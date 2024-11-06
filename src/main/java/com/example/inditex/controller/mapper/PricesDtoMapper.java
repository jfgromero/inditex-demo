package com.example.inditex.controller.mapper;

import org.mapstruct.Mapper;

import com.example.inditex.controller.dto.PricesDto;
import com.example.inditex.persistence.entity.PricesEntity;

/**
 * @author Juan Francisco Gonzalez
 * 
 */
@Mapper
public interface PricesDtoMapper {
	PricesDto from(PricesEntity owner);
}
