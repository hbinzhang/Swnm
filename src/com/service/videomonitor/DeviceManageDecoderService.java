package com.service.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmDecoder;

public interface DeviceManageDecoderService extends DeviceManageService {
	public int addDecoder(TVmDecoder decoder);
	public int deleteDecoders(List<String> decoderids);
	public int modifyDecoder(TVmDecoder decoder);
	public TVmDecoder searchById(String id);
	public List<TVmDecoder> searchByPage(Map<String,Object> cond);
	public int searchByPageRowCount(Map<String,Object> cond);
	public int testDecoderExistById(String decoderid);
}
