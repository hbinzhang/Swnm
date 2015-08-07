package com.dao.securityinfo;

import java.util.List;

import com.entity.securityinfo.TaskBook;
import com.entity.securityinfo.TaskBookCondition;

public interface ITaskBookDao{

	void updateTaskBook(TaskBook taskBook);

	void createTaskBook(TaskBook taskBook);

	Object queryTaskBookByName(TaskBook taskBook);

	void publishTaskBook(TaskBook tb);

	void deleteTaskBookByName(TaskBook tb);

	void closeTaskBook(TaskBook tb);

	List queryTbByIssueTime(TaskBookCondition taskBookCondition);

	List queryTbByStartTime(TaskBookCondition taskBookCondition);

	List queryTbByEndTime(TaskBookCondition taskBookCondition);

}
