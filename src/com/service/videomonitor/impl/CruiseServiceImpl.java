package com.service.videomonitor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.videomonitor.TVmCruiseMapper;
import com.dao.videomonitor.TVmCruiseSequenceMapper;
import com.dao.videomonitor.TVmIpcMapper;
import com.entity.videomonitor.TVmCruise;
import com.entity.videomonitor.TVmCruiseSequence;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmPresetposition;
import com.service.videomonitor.CruiseService;
import com.service.videomonitor.DeviceAdaptor;

public class CruiseServiceImpl implements CruiseService {

	private TVmCruiseMapper cruiseDao;
	private TVmCruiseSequenceMapper cruiseSequenceDao;
	private TVmIpcMapper ipcDao;

	public TVmIpcMapper getIpcDao() {
		return ipcDao;
	}

	public void setIpcDao(TVmIpcMapper ipcDao) {
		this.ipcDao = ipcDao;
	}

	public TVmCruiseMapper getCruiseDao() {
		return cruiseDao;
	}

	public void setCruiseDao(TVmCruiseMapper cruiseDao) {
		this.cruiseDao = cruiseDao;
	}

	public TVmCruiseSequenceMapper getCruiseSequenceDao() {
		return cruiseSequenceDao;
	}

	public void setCruiseSequenceDao(TVmCruiseSequenceMapper cruiseSequenceDao) {
		this.cruiseSequenceDao = cruiseSequenceDao;
	}

	@Override
	public int saveCruise(String cruiseSetData) throws Exception {
		// {'route':1,'ipcid':'1','cruisename':'cruise1','sequences':[{'seqid':1,'cruiseid':1,'preset':{'pointid':2,'point':1,'presetname':'大桥入口','ipcid':'ipc001'},'pausemins':3,'speed':2,'cruiseorder':2},{...},...]}
		int res = 0;
		JSONObject jo = JSONObject.fromObject(cruiseSetData);
		int route = jo.getInt("route");
		String cruisename = jo.getString("cruisename");
		String ipcid = jo.getString("ipcid");
		TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);

		TVmCruise tc = null;

		Map<String,Object> cond = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ipcid", ipcid);
		param.put("route", route);
		cond.put("cond", param);
		tc = cruiseDao.selectCruiseByRoute(cond);
		if(tc == null){
			tc = new TVmCruise();
			tc.setCruisename(cruisename);
			tc.setRoute(route);
			tc.setIpcid(ipcid);
			res = cruiseDao.insertCruise(tc);
			
			if(res < 1){
				throw new Exception("添加巡航方案时出现错误！");
			}
		}else{
			tc.setCruisename(cruisename);
			res = cruiseDao.updateCruise(tc);
			
			List<TVmCruiseSequence> sequences = tc.getSequences();
			for(TVmCruiseSequence sequence : sequences){
				JSONObject cruiseCmdJO = createCruiseCmd(route, ipc,
						sequence.getCruiseorder(), sequence.getPoint().getPoint(),33);
				DeviceAdaptor da = DeviceAdaptor.instanceDll;
				int result = da.devPTZControl(cruiseCmdJO.toString());
				if(result != 0){
					throw new Exception("JNA删除巡航方案时出错。");
				}
			}
			
			cruiseSequenceDao.deleteCruiseSequenceByCruise(tc.getCruiseid());			

			if (res != 1) {
				throw new Exception("修改 巡航方案时出现错误！");
			}
		}
		
		JSONArray ja = jo.getJSONArray("sequences");

		for (Object sequence : ja.toArray()) {
			JSONObject joseq = (JSONObject) sequence;
			int cruiseorder = joseq.getInt("cruiseorder");
			int pausemins = joseq.getInt("pausemins");
			int speed = joseq.getInt("speed");
			JSONObject jopreset = joseq.getJSONObject("point");
			TVmCruiseSequence tcs = new TVmCruiseSequence();
			tcs.setCruise(tc);
			tcs.setCruiseorder(cruiseorder);
			tcs.setPausemins(pausemins);
			TVmPresetposition point = new TVmPresetposition();
			point.setPointid(jopreset.getInt("pointid"));
			point.setPoint(jopreset.getInt("point"));
			tcs.setPoint(point);
			tcs.setSpeed(speed);
			int cruisepointid = cruiseSequenceDao.insertCruiseSequence(tcs);
			
			if(cruisepointid < 1){
				throw new Exception("添加 巡航方案巡航点时出现错误！");
			}
			
			if (ipc != null) {
				JSONObject cruiseCmdJO = createCruiseCmd(route, ipc,
						cruiseorder, point.getPoint(),30);
				DeviceAdaptor da = DeviceAdaptor.instanceDll;
				int result = da.devPTZControl(cruiseCmdJO.toString());
				if(result != 0){
					throw new Exception("JNA配置巡航点时出错。");
				}
				cruiseCmdJO = createCruiseCmd(route, ipc,
						cruiseorder, pausemins,31);
				result = da.devPTZControl(cruiseCmdJO.toString());
				if(result != 0){
					throw new Exception("JNA配置巡航间隔时出错。");
				}
				cruiseCmdJO = createCruiseCmd(route, ipc,
						cruiseorder, speed,32);
				result = da.devPTZControl(cruiseCmdJO.toString());
				if(result != 0){
					throw new Exception("JNA配置巡航速度时出错。");
				}
			}
		}

		return res;
	}

	private JSONObject createCruiseCmd(int route, TVmIpc ipc, int cruiseorder,
			int input,int val) {
		Map<String, Object> channelIpc = new HashMap<String, Object>();
		Map<String, Object> channelNvr = new HashMap<String, Object>();
		Map<String, Object> channelPtz = new HashMap<String, Object>();

		channelIpc.put("address", ipc.getIp());

		channelNvr.put("address", ipc.getNvr().getIp());
		channelNvr.put("port", ipc.getNvr().getPort());
		channelNvr.put("user", ipc.getNvr().getUsername2());
		channelNvr.put("password", ipc.getPassword());

		channelPtz.put("cmd", "cruise");
		channelPtz.put("val", val);
		channelPtz.put("route", route);
		channelPtz.put("point", cruiseorder);
		channelPtz.put("input", input);

		Map<String, Object> preset = new HashMap<String, Object>();
		preset.put("nvr", channelNvr);
		preset.put("ipc", channelIpc);
		preset.put("ptz", channelPtz);
		JSONObject cruiseCmdJO = JSONObject.fromObject(preset);
		return cruiseCmdJO;
	}

	@Override
	public List<TVmCruise> loadCruisesByIpc(String cruiseSetData)
			throws Exception {
		JSONObject jo = JSONObject.fromObject(cruiseSetData);
		String ipcid = jo.getString("ipcid");
		Map<String, Object> cond = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ipcid", ipcid);
		cond.put("cond", params);
		return cruiseDao.selectCruiseByIpcid(cond);
	}

	@Override
	public int deleteCruiseById(String cruiseSetData) throws Exception {
		JSONObject jo = JSONObject.fromObject(cruiseSetData);
		int route = jo.getInt("route");
		String ipcid = jo.getString("ipcid");
		Map<String,Object> cond = new HashMap<String,Object>();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ipcid", ipcid);
		param.put("route", route);
		cond.put("cond", param);
		
		TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);
		TVmCruise tc = cruiseDao.selectCruiseByRoute(cond);
		List<TVmCruiseSequence> sequences = tc.getSequences();
		for(TVmCruiseSequence sequence : sequences){
			JSONObject cruiseCmdJO = createCruiseCmd(route, ipc,
					sequence.getCruiseorder(), sequence.getPoint().getPoint(),33);
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			int result = da.devPTZControl(cruiseCmdJO.toString());
			if(result != 0){
				throw new Exception("JNA删除巡航方案时出错。");
			}
		}
		
		cruiseSequenceDao.deleteCruiseSequenceByCruise(tc.getCruiseid());
		return cruiseDao.deleteCruiseById(tc.getCruiseid());
	}

	@Override
	public int startCruise(String cruiseSetData) throws Exception {
		int res = 0;
		JSONObject jo = JSONObject.fromObject(cruiseSetData);
		int route = jo.getInt("route");
		String ipcid = jo.getString("ipcid");
		TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);
		JSONObject cruiseCmdJO = createCruiseCmd(route,ipc,1,1,37);
		DeviceAdaptor da = DeviceAdaptor.instanceDll;
		int result = da.devPTZControl(cruiseCmdJO.toString());
		if(result != 0){
			throw new Exception("JNA配置巡航点时出错。");
		}
		return res;
	}

	@Override
	public int stopCruise(String cruiseSetData) throws Exception {
		int res = 0;
		JSONObject jo = JSONObject.fromObject(cruiseSetData);
		int route = jo.getInt("route");
		String ipcid = jo.getString("ipcid");
		TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);
		JSONObject cruiseCmdJO = createCruiseCmd(route,ipc,1,1,38);
		DeviceAdaptor da = DeviceAdaptor.instanceDll;
		int result = da.devPTZControl(cruiseCmdJO.toString());
		if(result != 0){
			throw new Exception("JNA配置巡航点时出错。");
		}
		return res;
	}

}
