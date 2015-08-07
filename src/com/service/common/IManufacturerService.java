package com.service.common;

import java.util.List;

import com.entity.Manufacturer;

public interface IManufacturerService {
	
	public List<Manufacturer> findAllManufacturer() throws Exception;

}
