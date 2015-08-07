package com.dao.securityinfo.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.dao.securityinfo.ITaskBookDao;
import com.entity.securityinfo.TaskBook;
import com.entity.securityinfo.TaskBookCondition;

public class TaskBookDaoImp implements ITaskBookDao{

	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public void updateTaskBook(TaskBook taskBook) {
		sqlmapclienttemplate.update("updateTaskBook",taskBook);		
	}

	@Override
	public void createTaskBook(TaskBook taskBook) {
		sqlmapclienttemplate.insert("createTaskBook",taskBook);
	}

	@Override
	public Object queryTaskBookByName(TaskBook taskBook) {
		return sqlmapclienttemplate.queryForObject("queryTaskBookByName",taskBook);
	}

	@Override
	public void publishTaskBook(TaskBook tb) {
		sqlmapclienttemplate.update("publishTaskBook",tb);		
	}

	@Override
	public void deleteTaskBookByName(TaskBook tb) {
		sqlmapclienttemplate.delete("deleteTaskBookByName",tb);		
		
	}

	@Override
	public void closeTaskBook(TaskBook tb) {
		sqlmapclienttemplate.update("closeTaskBook",tb);		
		
	}

	@Override
	public List queryTbByIssueTime(TaskBookCondition taskBookCondition) {
		return sqlmapclienttemplate.queryForList("queryTbByIssueTime",taskBookCondition);
	}

	@Override
	public List queryTbByStartTime(TaskBookCondition taskBookCondition) {
		return sqlmapclienttemplate.queryForList("queryTbByStartTime",taskBookCondition);
	}

	@Override
	public List queryTbByEndTime(TaskBookCondition taskBookCondition) {
		return sqlmapclienttemplate.queryForList("queryTbByEndTime",taskBookCondition);
	}
}
