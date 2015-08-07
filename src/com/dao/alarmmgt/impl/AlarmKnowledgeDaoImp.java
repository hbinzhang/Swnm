package com.dao.alarmmgt.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.alarmmgt.IAlarmKnowledgeDao;
import com.entity.alarmmgt.AlarmKnowledge;

public class AlarmKnowledgeDaoImp implements IAlarmKnowledgeDao{
	
	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}
	
	public List queryAllAlarmKnowledge() {
		return sqlmapclienttemplate.queryForList("queryAllAlarmKnowledge");
	}

	public void updateAlarmKnowledge(AlarmKnowledge alarmKnowledge) {
		sqlmapclienttemplate.update("updateAlarmKnowledgeInfo", alarmKnowledge);		
	}

	@Override
	public List queryAlaKnowByKnowledge(AlarmKnowledge alarmKnowledge) {
		return sqlmapclienttemplate.queryForList("queryAlaKnowByKnowledge",
				alarmKnowledge);
	}

	@Override
	public Object queryAlarmKnowledgeByCode(int alarmCode) {
		// TODO Auto-generated method stub
		return sqlmapclienttemplate.queryForObject("queryAlarmKnowledgeByCode",alarmCode);
	}

}
