package com.dao.securityinfo;

import java.util.List;

import com.entity.securityinfo.Rectification;
import com.entity.securityinfo.RectificationCondition;

public interface IRectificationDao {

	List queryRectification(RectificationCondition rectificationCondition);

	void createRectification(Rectification rectification);

	Object queryRectificationByName(Rectification rectification);

	void updateRectification(Rectification rectification);

	void deleteRectification(Rectification rectification);

}
