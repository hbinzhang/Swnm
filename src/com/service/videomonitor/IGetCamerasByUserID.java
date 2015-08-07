package com.service.videomonitor;

import java.util.List;

import com.entity.videomonitor.TVmIpc;

public interface IGetCamerasByUserID {
	public List<TVmIpc> getCamerasByUserID(String userID);

}
