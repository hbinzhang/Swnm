package com.service.videomonitor.impl;

import java.util.List;
import java.util.Map;

import com.dao.videomonitor.TVmDecoderMapper;
import com.entity.videomonitor.TVmDecoder;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmManufacturer;
import com.service.videomonitor.DeviceManageDecoderService;

public class DeviceManageDecoderServiceImpl extends DeviceManageServiceImpl implements
		DeviceManageDecoderService{
	
	private TVmDecoderMapper decoderDao;

	public TVmDecoderMapper getDecoderDao() {
		return decoderDao;
	}

	public void setDecoderDao(TVmDecoderMapper decoderDao) {
		this.decoderDao = decoderDao;
	}

	@Override
	public int addDecoder(TVmDecoder decoder) {
		int res = 0;
		try {
			//ipc.setIpcid(UUID.randomUUID().toString());
			res = decoderDao.insertDecoderSelective(decoder);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int deleteDecoders(List<String> decoderids) {
		// TODO Auto-generated method stub
		int res = 0;
		for(String id:decoderids){
			res += decoderDao.deleteDecoderByPrimaryKey(id);
		}
		return res;
	}

	@Override
	public int modifyDecoder(TVmDecoder decoder) {
		int res = decoderDao.updateDecoderByPrimaryKeySelective(decoder);
		return res;
	}

	@Override
	public TVmDecoder searchById(String id) {
		return decoderDao.selectDecoderByPrimaryKey(id);
	}

	@Override
	public List<TVmDecoder> searchByPage(Map<String, Object> cond) {
		return decoderDao.selectDecoderByPage(cond);
	}

	@Override
	public int searchByPageRowCount(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return decoderDao.selectDecoderByPageRowCount(cond);
	}

	@Override
	public int testDecoderExistById(String decoderid) {
		// TODO Auto-generated method stub
		return decoderDao.testDecoderExistById(decoderid);
	}
}
