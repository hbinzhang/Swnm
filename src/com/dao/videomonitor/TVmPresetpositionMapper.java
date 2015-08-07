package com.dao.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmPresetposition;

public interface TVmPresetpositionMapper {
	TVmPresetposition selectPresetByPrimaryKey(int id);
	int insertPreset(TVmPresetposition preset);
	int updatePreset(TVmPresetposition preset);
	int deletePresetByPrimaryKey(int pointid);
	List<TVmPresetposition> selectPresetByPage(Map<String,Object> cond);
}
