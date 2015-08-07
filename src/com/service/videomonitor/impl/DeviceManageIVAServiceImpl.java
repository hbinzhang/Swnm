package com.service.videomonitor.impl;

import java.util.List;
import java.util.Map;

import com.dao.videomonitor.TvmIvaMapper;
import com.entity.videomonitor.TVmIva;
import com.service.videomonitor.DeviceManageIVAService;

public class DeviceManageIVAServiceImpl extends DeviceManageServiceImpl implements DeviceManageIVAService{
	private  TvmIvaMapper ivaMapper;
	
	@Override
	public int addIVA(TVmIva iva) throws Exception {
		
		int res = 0;
		try {
			//ipc.setIpcid(UUID.randomUUID().toString());
			res = ivaMapper.addIVA(iva);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return res;
	}
	/*************************getter/setter*******************************/
	public TvmIvaMapper getIvaMapper() {
		return ivaMapper;
	}
	public void setIvaMapper(TvmIvaMapper ivaMapper) {
		this.ivaMapper = ivaMapper;
	}
	
	@Override
	public int testIvaExistById(String ivaID) {
		return ivaMapper.testIvaExistById(ivaID);

	}

	@Override
	public int deleteIvas(List<String> ivas) {
		// TODO Auto-generated method stub
		int res = 0;
		for(String id:ivas){
			res += ivaMapper.deleteIvaById(id);
			//res += decoderDao.deleteDecoderByPrimaryKey(id);
		}
		return res;
	}

	@Override
	public TVmIva searchById(String ivaID) {
		// TODO Auto-generated method stub
		return ivaMapper.selectIvaById(ivaID);
	}

	@Override
	public int searchByPageRowCount(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return ivaMapper.selectIvaByPageRowCount(cond);
	}

	@Override
	public List<TVmIva> searchByPage(Map<String, Object> cond) {
		// TODO Auto-generated method stub
		return ivaMapper.selectIvaByPage(cond);
	}
	
	@Override
	public int modifyIva(TVmIva ivaToAddOrUpdate) {
		// TODO Auto-generated method stub
		int res = ivaMapper.updateIvaByPrimaryKeySelective(ivaToAddOrUpdate);
		return res;
	}

}
