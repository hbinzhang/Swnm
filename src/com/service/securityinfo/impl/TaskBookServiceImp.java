package com.service.securityinfo.impl;

import java.util.List;

import com.dao.securityinfo.ITaskBookDao;
import com.entity.securityinfo.TaskBook;
import com.entity.securityinfo.TaskBookCondition;
import com.service.securityinfo.ITaskBookService;

public class TaskBookServiceImp implements ITaskBookService{
	
	private ITaskBookDao taskBookDao;

	public ITaskBookDao getTaskBookDao() {
		return taskBookDao;
	}

	public void setTaskBookDao(ITaskBookDao taskBookDao) {
		this.taskBookDao = taskBookDao;
	}

	@Override
	public void updateTaskBook(TaskBook taskBook) {
		taskBookDao.updateTaskBook(taskBook);		
	}

	@Override
	public void createTaskBook(TaskBook taskBook) {
		taskBookDao.createTaskBook(taskBook);				
	}

	@Override
	public Object queryTaskBookByName(TaskBook taskBook) {
		return taskBookDao.queryTaskBookByName(taskBook);
	}

	@Override
	public void publishTaskBook(TaskBook tb) {
		taskBookDao.publishTaskBook(tb);		
	}

	@Override
	public void deleteTaskBookByName(TaskBook tb) {
		taskBookDao.deleteTaskBookByName(tb);		
		
	}

	@Override
	public void closeTaskBook(TaskBook tb) {
		taskBookDao.closeTaskBook(tb);		
		
	}

	@Override
	public List queryTbByIssueTime(TaskBookCondition taskBookCondition) {
		return taskBookDao.queryTbByIssueTime(taskBookCondition);
	}

	@Override
	public List queryTbByStartTime(TaskBookCondition taskBookCondition) {
		return taskBookDao.queryTbByStartTime(taskBookCondition);
	}

	@Override
	public List queryTbByEndTime(TaskBookCondition taskBookCondition) {
		return taskBookDao.queryTbByEndTime(taskBookCondition);
	}
	


}
