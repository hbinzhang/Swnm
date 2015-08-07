package com.service.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmManufacturer;

public interface DeviceManageService {
	public List<TVmManufacturer> getManufactures();
	public List<Map<String,String>> getBranches();
	public Map<String, List<Map<String, String>>> getManagementagencies();
}
