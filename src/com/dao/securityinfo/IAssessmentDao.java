package com.dao.securityinfo;

import java.util.List;

import com.entity.securityinfo.Assessment;
import com.entity.securityinfo.CheckInfo;
import com.entity.securityinfo.TaskBookCondition;

public interface IAssessmentDao {

	List queryAssessment(String orgId);

	void createAssessment(Assessment assessment);

	Object queryAssessmentByName(Assessment assessment);

	void updateAssessment(Assessment assessment);

	void evaluateAssessment(Assessment assessment);

	void deleteAssessment(Assessment assessment);

	List queryAssessResult4Org(TaskBookCondition condition);

	List queryCheckInfoByName(CheckInfo checkInfo);

	void createCheckInfo(CheckInfo checkInfo);

	void deleteCheckInfoByName(CheckInfo checkInfo);

	Object queryCheckInfoByNmAndTime(CheckInfo checkInfo);

}
