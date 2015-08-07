package com.service.videomonitor;

import java.util.List;

import com.entity.videomonitor.TVmPresetposition;

public interface PresetService {
	/** 调用预置位
	 * @param ipcid
	 * @param presetno
	 * @return
	 */
	public int gotoPtzPreset(String ipcid,int presetno);
	int savePreset(String presetSetData) throws Exception;
	int deletePreset(String presetSetData);
	List<TVmPresetposition> loadPresetByPage(String presetSetData);
}
