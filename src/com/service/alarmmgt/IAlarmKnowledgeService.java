package com.service.alarmmgt;

import java.util.List;

import com.entity.alarmmgt.AlarmKnowledge;

public interface IAlarmKnowledgeService {
	
	public List queryAllAlarmKnowledge();
	
	public void updateAlarmKnowledge(AlarmKnowledge alarmKnowledge);

	public List queryAlaKnowByKnowledge(AlarmKnowledge alarmKnowledge);

	public Object queryAlarmKnowledgeByCode(int alarmCode);

}
