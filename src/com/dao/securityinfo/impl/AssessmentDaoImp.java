package com.dao.securityinfo.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.securityinfo.IAssessmentDao;
import com.entity.securityinfo.Assessment;
import com.entity.securityinfo.CheckInfo;
import com.entity.securityinfo.TaskBookCondition;

public class AssessmentDaoImp implements IAssessmentDao{

	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public List queryAssessment(String orgId) {
		return sqlmapclienttemplate.queryForList("queryAssessment",orgId);
	}

	@Override
	public void createAssessment(Assessment assessment) {
		sqlmapclienttemplate.insert("createAssessment",assessment);		
	}

	@Override
	public Object queryAssessmentByName(Assessment assessment) {
		return sqlmapclienttemplate.queryForObject("queryAssessmentByName",assessment);
	}

	@Override
	public void updateAssessment(Assessment assessment) {
		sqlmapclienttemplate.update("updateAssessment",assessment);		
	}

	@Override
	public void evaluateAssessment(Assessment assessment) {
		sqlmapclienttemplate.update("evaluateAssessment",assessment);		
		
	}

	@Override
	public void deleteAssessment(Assessment assessment) {
		sqlmapclienttemplate.delete("deleteAssessment",assessment);	
	}

	@Override
	public List queryAssessResult4Org(TaskBookCondition condition) {
		return sqlmapclienttemplate.queryForList("queryAssessResult4Org",condition);
	}

	@Override
	public List queryCheckInfoByName(CheckInfo checkInfo) {
		return sqlmapclienttemplate.queryForList("queryCheckInfoByName",checkInfo);
	}

	@Override
	public void createCheckInfo(CheckInfo checkInfo) {
		sqlmapclienttemplate.insert("createCheckInfo",checkInfo);		
		
	}

	@Override
	public void deleteCheckInfoByName(CheckInfo checkInfo) {
		sqlmapclienttemplate.delete("deleteCheckInfoByName",checkInfo);	
		
	}

	@Override
	public Object queryCheckInfoByNmAndTime(CheckInfo checkInfo) {
		return sqlmapclienttemplate.queryForObject("queryCheckInfoByNmAndTime",checkInfo);
	}

}
