package com.service.videomonitor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.dao.videomonitor.TVmIpcMapper;
import com.dao.videomonitor.TVmPresetpositionMapper;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmPresetposition;
import com.service.videomonitor.DeviceAdaptor;
import com.service.videomonitor.PresetService;

public class PresetServiceImpl implements PresetService {
	
	private TVmPresetpositionMapper presetDao;

	public TVmPresetpositionMapper getPresetDao() {
		return presetDao;
	}

	public void setPresetDao(TVmPresetpositionMapper presetDao) {
		this.presetDao = presetDao;
	}
	
	private TVmIpcMapper ipcDao;

	public TVmIpcMapper getIpcDao() {
		return ipcDao;
	}

	public void setIpcDao(TVmIpcMapper ipcDao) {
		this.ipcDao = ipcDao;
	}

	@Override
	public int savePreset(String presetSetData) throws Exception {
		//{'id':2,'presetno':1,'presetname':'大桥入口','ipcid':'ipc001'}
		JSONObject jo = JSONObject.fromObject(presetSetData);
		int presetno = jo.getInt("presetno");
		String presetname = jo.getString("presetname");
		String ipcid = jo.getString("ipcid");
		TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);
		int res = 0;
		
		if(ipc != null){
			Map<String, Object> channelIpc = new HashMap<String, Object>();
			Map<String, Object> channelNvr = new HashMap<String, Object>();
			Map<String, Object> channelPtz = new HashMap<String, Object>();
			
			channelIpc.put("address", ipc.getIp());

			channelNvr.put("address", ipc.getNvr().getIp());
			channelNvr.put("port", ipc.getNvr().getPort());
			channelNvr.put("user", ipc.getNvr().getUsername2());
			channelNvr.put("password", ipc.getPassword());
			
			channelPtz.put("cmd", "setpreset");
			channelPtz.put("val", presetno);

			Map<String, Object> preset = new HashMap<String, Object>();
			preset.put("nvr", channelNvr);
			preset.put("ipc", channelIpc);
			preset.put("ptz", channelPtz);	
			JSONObject presetCmdJO = JSONObject.fromObject(preset);
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			int result = da.devPTZControl(presetCmdJO.toString());
			if(result == 0){
				if(jo.containsKey("id")){
					int pointid = jo.getInt("id");
					TVmPresetposition tvp = new TVmPresetposition();
					tvp.setPointid(pointid);
					tvp.setIpcid(ipcid);
					tvp.setPoint(presetno);
					tvp.setPresetname(presetname);
					res = presetDao.updatePreset(tvp);		
				}else{
					TVmPresetposition tvp = new TVmPresetposition();
					tvp.setIpcid(ipcid);
					tvp.setPoint(presetno);
					tvp.setPresetname(presetname);
					res = presetDao.insertPreset(tvp);
				}
			}
		}
		return res;
	}

	@Override
	public int deletePreset(String presetSetData) {
		JSONObject jo = JSONObject.fromObject(presetSetData);
		int res = 0;
		if(jo.containsKey("id")){
			int id = jo.getInt("id");
			TVmPresetposition presete = presetDao.selectPresetByPrimaryKey(id);
			TVmIpc ipc = ipcDao.selectByPrimaryKey(presete.getIpcid());
			Map<String, Object> channelIpc = new HashMap<String, Object>();
			Map<String, Object> channelNvr = new HashMap<String, Object>();
			Map<String, Object> channelPtz = new HashMap<String, Object>();
			
			channelIpc.put("address", ipc.getIp());

			channelNvr.put("address", ipc.getNvr().getIp());
			channelNvr.put("port", ipc.getNvr().getPort());
			channelNvr.put("user", ipc.getNvr().getUsername2());
			channelNvr.put("password", ipc.getPassword());
			
			channelPtz.put("cmd", "clepreset");
			channelPtz.put("val", presete.getPoint());

			Map<String, Object> preset = new HashMap<String, Object>();
			preset.put("nvr", channelNvr);
			preset.put("ipc", channelIpc);
			preset.put("ptz", channelPtz);	
			JSONObject presetCmdJO = JSONObject.fromObject(preset);
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			int result = da.devPTZControl(presetCmdJO.toString());
			if(result == 0){
				res = presetDao.deletePresetByPrimaryKey(id);
			}
		}
		return res;
	}

	@Override
	public List<TVmPresetposition> loadPresetByPage(String presetSetData) {
		List<TVmPresetposition> res = null;
		JSONObject jo = JSONObject.fromObject(presetSetData);
		String ipcid = jo.getString("ipcid");
		int from = jo.getInt("from");
		int to = jo.getInt("to");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ipcid", ipcid);
		params.put("from", from);
		params.put("to", to);
		Map<String,Object> cond = new HashMap<String,Object>();
		cond.put("cond",params);
		res = presetDao.selectPresetByPage(cond);
		return res;
	}
	
	@Override
	public int gotoPtzPreset(String ipcid,int presetno) {
		int result = 0;
		try {
			Map<String, Object> channelIpc = new HashMap<String, Object>();
			Map<String, Object> channelNvr = new HashMap<String, Object>();
			Map<String, Object> channelPtz = new HashMap<String, Object>();
			
			TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);
			
			channelIpc.put("address", ipc.getIp());

			channelNvr.put("address", ipc.getNvr().getIp());
			channelNvr.put("port", ipc.getNvr().getPort());
			channelNvr.put("user", ipc.getNvr().getUsername2());
			channelNvr.put("password", ipc.getPassword());
			
			channelPtz.put("cmd", "gotopreset");
			channelPtz.put("val", presetno);

			Map<String, Object> res = new HashMap<String, Object>();
			res.put("nvr", channelNvr);
			res.put("ipc", channelIpc);
			res.put("ptz", channelPtz);			
			
			//String preset = "{\"nvr\":{\"address\":\"10.3.10.190\", \"port\":8000, \"user\":\"admin\", \"password\":\"12345\"}, \"ipc\":{\"address\":\"10.3.10.193\"}, \"ptz\":{\"cmd\":\"gotopreset\", \"val\":"+presetno+"}}";
			String preset = JSONObject.fromObject(res).toString();
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			result = da.devPTZControl(preset);
		} catch (Exception e) {
			//log.error(e.getMessage());
			result = 0;
		}
		return result;
	}
}
