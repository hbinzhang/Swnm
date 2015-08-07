package com.service.securityinfo.impl;

import java.util.List;

import com.dao.securityinfo.IRectificationDao;
import com.entity.securityinfo.Rectification;
import com.entity.securityinfo.RectificationCondition;
import com.service.securityinfo.IRectificationService;

public class RectificationServiceImp implements IRectificationService{

	private IRectificationDao rectificationDao;

	public IRectificationDao getRectificationDao() {
		return rectificationDao;
	}

	public void setRectificationDao(IRectificationDao rectificationDao) {
		this.rectificationDao = rectificationDao;
	}

	@Override
	public List queryRectification(RectificationCondition rectificationCondition) {
		return rectificationDao.queryRectification(rectificationCondition);
	}

	@Override
	public void createRectification(Rectification rectification) {
		rectificationDao.createRectification(rectification);
		
	}

	@Override
	public Object queryRectificationByName(Rectification rectification) {
		return rectificationDao.queryRectificationByName(rectification);
	}

	@Override
	public void updateRectification(Rectification rectification) {
		rectificationDao.updateRectification(rectification);
		
	}

	@Override
	public void deleteRectification(Rectification rectification) {
		rectificationDao.deleteRectification(rectification);
		
	}
	
	
}
