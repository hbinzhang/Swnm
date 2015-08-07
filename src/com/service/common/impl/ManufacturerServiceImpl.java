package com.service.common.impl;

import java.util.List;

import com.dao.common.IManufacturerDao;
import com.entity.Manufacturer;
import com.service.common.IManufacturerService;

public class ManufacturerServiceImpl implements IManufacturerService {
	
	private IManufacturerDao manufacturerDao;

	@Override
	public List<Manufacturer> findAllManufacturer() throws Exception {
		return manufacturerDao.findAllManufacturer();
	}

	public IManufacturerDao getManufacturerDao() {
		return manufacturerDao;
	}

	public void setManufacturerDao(IManufacturerDao manufacturerDao) {
		this.manufacturerDao = manufacturerDao;
	}
	
	

}
