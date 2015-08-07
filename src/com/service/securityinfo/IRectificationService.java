package com.service.securityinfo;

import java.util.List;

import com.entity.securityinfo.Rectification;
import com.entity.securityinfo.RectificationCondition;

public interface IRectificationService {

	List queryRectification(
			RectificationCondition rectificationCondition);
	
	void createRectification(Rectification rectification);

	Object queryRectificationByName(Rectification rectification);

	void updateRectification(Rectification rectification);
	
	void deleteRectification(Rectification rectification);

}
