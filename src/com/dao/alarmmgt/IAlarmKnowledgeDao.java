package com.dao.alarmmgt;

import java.util.List;

import com.entity.alarmmgt.AlarmKnowledge;

public interface IAlarmKnowledgeDao {
	
	public List queryAllAlarmKnowledge();

	public void updateAlarmKnowledge(AlarmKnowledge alarmKnowledge);

	public List queryAlaKnowByKnowledge(AlarmKnowledge alarmKnowledge);

	public Object queryAlarmKnowledgeByCode(int alarmCode);
	
}
