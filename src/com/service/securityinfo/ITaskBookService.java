package com.service.securityinfo;

import java.util.List;

import com.entity.securityinfo.Assessment;
import com.entity.securityinfo.TaskBook;
import com.entity.securityinfo.TaskBookCondition;

public interface ITaskBookService {

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
