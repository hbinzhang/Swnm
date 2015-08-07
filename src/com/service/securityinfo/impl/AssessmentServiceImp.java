package com.service.securityinfo.impl;

import java.util.List;

import com.dao.securityinfo.IAssessmentDao;
import com.entity.securityinfo.Assessment;
import com.entity.securityinfo.CheckInfo;
import com.entity.securityinfo.TaskBookCondition;
import com.service.securityinfo.IAssessmentService;

public class AssessmentServiceImp implements IAssessmentService{

	private IAssessmentDao assessmentDao;

	public IAssessmentDao getAssessmentDao() {
		return assessmentDao;
	}

	public void setAssessmentDao(IAssessmentDao assessmentDao) {
		this.assessmentDao = assessmentDao;
	}

	@Override
	public List queryAssessment(String orgId) {
		return assessmentDao.queryAssessment(orgId);
	}

	@Override
	public void createAssessment(Assessment assessment) {
		assessmentDao.createAssessment(assessment);		
	}

	@Override
	public Object queryAssessmentByName(Assessment assessment) {
		return assessmentDao.queryAssessmentByName(assessment);
	}

	@Override
	public void updateAssessment(Assessment assessment) {
		assessmentDao.updateAssessment(assessment);		
		
	}

	@Override
	public void evaluateAssessment(Assessment assessment) {
		assessmentDao.evaluateAssessment(assessment);		
		
	}

	@Override
	public void deleteAssessment(Assessment assessment) {
		assessmentDao.deleteAssessment(assessment);		
		
	}

	@Override
	public List queryAssessResult4Org(TaskBookCondition condition) {
		return assessmentDao.queryAssessResult4Org(condition);
	}

	@Override
	public List queryCheckInfoByName(CheckInfo checkInfo) {
		return assessmentDao.queryCheckInfoByName(checkInfo);
	}

	@Override
	public void createCheckInfo(CheckInfo checkInfo) {
		assessmentDao.createCheckInfo(checkInfo);
		
	}

	@Override
	public void deleteCheckInfoByName(CheckInfo checkInfo) {
		assessmentDao.deleteCheckInfoByName(checkInfo);		
		
	}

	@Override
	public Object queryCheckInfoByNmAndTime(CheckInfo checkInfo) {
		return 	assessmentDao.queryCheckInfoByNmAndTime(checkInfo);
	}

	
}
