package com.service.alarmmgt.impl;

import java.util.List;

import com.dao.alarmmgt.IAlarmKnowledgeDao;
import com.entity.alarmmgt.AlarmKnowledge;
import com.service.alarmmgt.IAlarmKnowledgeService;

public class AlarmKnowledgeServiceImp implements IAlarmKnowledgeService{
	
	private IAlarmKnowledgeDao alarmKnowledgeInfoDao= null;

	public void setAlarmKnowledgeInfoDao(IAlarmKnowledgeDao alarmKnowledgeInfoDao) {
		this.alarmKnowledgeInfoDao = alarmKnowledgeInfoDao;
	}

	public List queryAllAlarmKnowledge() {
		return alarmKnowledgeInfoDao.queryAllAlarmKnowledge();
	}

	public void updateAlarmKnowledge(AlarmKnowledge alarmKnowledge) {
		alarmKnowledgeInfoDao.updateAlarmKnowledge(alarmKnowledge);		
	}

	@Override
	public List queryAlaKnowByKnowledge(AlarmKnowledge alarmKnowledge) {
		return alarmKnowledgeInfoDao.queryAlaKnowByKnowledge(alarmKnowledge);
	}

	@Override
	public Object queryAlarmKnowledgeByCode(int alarmCode) {
		// TODO Auto-generated method stub
		return alarmKnowledgeInfoDao.queryAlarmKnowledgeByCode(alarmCode);
	}

}
