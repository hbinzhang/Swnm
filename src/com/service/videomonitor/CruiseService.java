package com.service.videomonitor;

import java.util.List;

import com.entity.videomonitor.TVmCruise;

public interface CruiseService {
	int saveCruise(String cruiseSetData) throws Exception;
	List<TVmCruise> loadCruisesByIpc(String cruiseSetData) throws Exception;
	int deleteCruiseById(String cruiseSetData) throws Exception;
	int startCruise(String cruiseSetData) throws Exception;
	int stopCruise(String cruiseSetData) throws Exception;
}
