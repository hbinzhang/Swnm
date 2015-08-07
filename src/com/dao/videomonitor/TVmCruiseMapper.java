package com.dao.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmCruise;

public interface TVmCruiseMapper {
	int insertCruise(TVmCruise cruise);	
	int updateCruise(TVmCruise cruise);
	int deleteCruiseById(int cruiseId);
	TVmCruise selectCruiseByRoute(Map<String,Object> cond);
	List<TVmCruise> selectCruiseByIpcid(Map<String,Object> cond);
}
