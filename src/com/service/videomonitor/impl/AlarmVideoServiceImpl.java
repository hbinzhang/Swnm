package com.service.videomonitor.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dao.linkagectl.IIpcZoneMapDao;
import com.dao.videomonitor.TVmIpcMapper;
import com.dao.videomonitor.TVmNvrMapper;
import com.entity.linkagectl.IpcZoneMap;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmNvr;
import com.service.videomonitor.AlarmVideoService;
import com.service.videomonitor.DeviceAdaptor;

public class AlarmVideoServiceImpl implements AlarmVideoService {

	private static Log log = LogFactory.getLog(AlarmVideoServiceImpl.class);
	private IIpcZoneMapDao ipcZoneMapDao;
	private TVmIpcMapper ipcDao;
	private TVmNvrMapper nvrDao;

	public TVmNvrMapper getNvrDao() {
		return nvrDao;
	}

	public void setNvrDao(TVmNvrMapper nvrDao) {
		this.nvrDao = nvrDao;
	}

	public IIpcZoneMapDao getIpcZoneMapDao() {
		return ipcZoneMapDao;
	}

	public void setIpcZoneMapDao(IIpcZoneMapDao ipcZoneMapDao) {
		this.ipcZoneMapDao = ipcZoneMapDao;
	}

	public TVmIpcMapper getIpcDao() {
		return ipcDao;
	}

	public void setIpcDao(TVmIpcMapper ipcDao) {
		this.ipcDao = ipcDao;
	}

	@Override
	public String searchAlarmVideo(String defectId, Date beginDate)
			throws NumberFormatException, SQLException {

		List<Map<String, Object>> allChannels = new ArrayList<Map<String, Object>>();
		try {
			List<IpcZoneMap> ipcZoneMaps = (List<IpcZoneMap>) ipcZoneMapDao
					.findall(Integer.parseInt(defectId));
			String mainIpcId = "";
			String ipcids = "'";
			for (IpcZoneMap izm : ipcZoneMaps) {
				ipcids += izm.getIpcId() + "','";
				if (izm.getMainIpc() == 1) {
					mainIpcId = izm.getIpcId();
				}
			}
			Map<String, Object> channel0 = new HashMap<String, Object>();
			Map<String, Object> channel1 = new HashMap<String, Object>();
			List<Map<String, Object>> channel23 = new ArrayList<Map<String, Object>>();

			if (ipcids != null && ipcids.length() > 2) {
				ipcids = ipcids.substring(0, ipcids.length() - 2);
				List<TVmIpc> ipcs = ipcDao.selectIpcsByIds(ipcids);
				for (TVmIpc ipc : ipcs) {
					if (ipc.getNvr() == null) {
						continue;
					}
					if (ipc.getIpcid().equals(mainIpcId)) {
						channel0.put("playerType", 1);
						channel0.put("channelIp", ipc.getIp());
						channel0.put("cmdPort", ipc.getPort());
						channel0.put("dataPort", ipc.getPort().intValue() + 1);
						channel0.put("devIp", ipc.getNvr().getIp());
						channel0.put("psw", ipc.getPassword());
						channel0.put("usr", ipc.getUsername());

						Map<String, Object> record = new HashMap<String, Object>();
						record.put("beginTime",
								(beginDate.getTime() / 1000 - 30));
						record.put("byteRate", 10485760);
						record.put("channel", 1);
						record.put("idxType", 3);
						record.put("ip", ipc.getNvr().getIp());
						record.put("peroid", 30 * 60);
						record.put("protocol", 1);

						channel1.put("PlayType", 2);
						channel1.put("channelIp", ipc.getIp());
						channel1.put("cmdPort", ipc.getPort());
						channel1.put("dataPort", ipc.getPort().intValue() + 1);
						channel1.put("devIp", ipc.getNvr().getIp());
						channel1.put("playerType", 1);
						channel1.put("psw", ipc.getPassword());
						channel1.put("usr", ipc.getUsername());
						channel1.put("record", record);
					} else {
						Map<String, Object> channel = new HashMap<String, Object>();
						channel.put("playerType", 1);
						channel.put("channelIp", ipc.getIp());
						channel.put("cmdPort", ipc.getPort());
						channel.put("dataPort", ipc.getPort().intValue() + 1);
						channel.put("devIp", ipc.getNvr().getIp());
						channel.put("psw", ipc.getPassword());
						channel.put("usr", ipc.getUsername());
						channel23.add(channel);
					}
				}
			}

			allChannels.add(channel0);
			allChannels.add(channel1);
			allChannels.addAll(channel23);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return JSON.toJSONString(allChannels);
	}

	@Override
	public int configIpcToNvr(String configJson) {
		int res = 0;
		try {
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			res = da.devConfigNvrIPChnnl(configJson);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = -1;
		}
		return res;
	}

	@Override
	public int configIpcToNvr(String nvrid, List<String> ipcids) {

		int res = 0;
		try {
			TVmNvr nvr = nvrDao.selectByPrimaryKey(nvrid);
			StringBuilder vipcids = new StringBuilder();
			String sipcids = "";
			for (String ipcid : ipcids) {
				vipcids.append(ipcid + ",");
			}
			if (vipcids.length() > 0) {
				sipcids = vipcids.substring(0, vipcids.length() - 1);
			}
			List<TVmIpc> ipcs = ipcDao.selectIpcsByIds(sipcids);

			// "{"nvr":{"address":"192.168.88.114", "port":8000, "user":"admin", "password":"12345"}, "ipc":[{"address":"192.168.88.201", "port":8000, "user":"admin", "password":"12345", "protocol":"HIKVISION"},{"address":"192.168.88.206", "port":80, "user":"admin", "password":"12345", "protocol":"HIKVISION"}]}";
			JSONObject nvrjson = new JSONObject();
			nvrjson.put("address", nvr.getIp());
			nvrjson.put("port", nvr.getPort());
			nvrjson.put("user", nvr.getUsername2());
			nvrjson.put("password", nvr.getPassword());
			JSONArray ipcsjson = new JSONArray();
			for (TVmIpc ipc : ipcs) {
				JSONObject ipcjson = new JSONObject();
				ipcjson.put("address", ipc.getIp());
				ipcjson.put("port", ipc.getPort());
				ipcjson.put("user", ipc.getUserid());
				ipcjson.put("password", ipc.getPassword());
				ipcjson.put("protocol", "HIKVISION");
				ipcsjson.add(ipcjson);
			}
			JSONObject configjson = new JSONObject();
			configjson.put("nvr", nvrjson);
			configjson.put("ipc", ipcsjson);
			String sconfigjson = configjson.toJSONString();
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			res = da.devConfigNvrIPChnnl(sconfigjson);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = -1;
		}
		return res;
	}

	@Override
	public int configNTP(String devip, int devport, String devuser,
			String devpassword, String ntpip, int ntpport, int interval,
			int enable) {
		int res = -1;
		try {
			// "{\"dev\":{\"address\":\"192.168.50.114\", \"port\":8000, \"user\":\"admin\", \"password\":\"12345\"}, \"cmd\":\"setntp\", \"ntp\":{\"server\":\"192.168.50.2\", \"port\":300, \"interval\":10,\"enable\":0}}";
			JSONObject devjson = new JSONObject();
			devjson.put("address", devip);
			devjson.put("port", devport);
			devjson.put("user", devuser);
			devjson.put("password", devpassword);
			JSONObject ntpjson = new JSONObject();
			ntpjson.put("server", ntpip);
			ntpjson.put("port", ntpport);
			ntpjson.put("interval", interval);
			ntpjson.put("enable", enable);
			JSONObject configjson = new JSONObject();
			configjson.put("dev", devjson);
			configjson.put("ntp", ntpjson);
			configjson.put("cmd", "setntp");
			String sconfigjson = configjson.toJSONString();
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			res = da.devConfigSet(sconfigjson);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = -1;
		}
		return res;
	}

	@Override
	public int configTimezone(String devip, int port, String user,
			String password, int timedifh, int timedifm) {
		int res = -1;
		try {
			// "{\"dev\":{\"address\":\"192.168.50.114\", \"port\":8000, \"user\":\"admin\", \"password\":\"12345\"}, \"cmd\":\"settimezone\", \"timezone\":{\"timedifh\":1, \"timedifm\":30}}";
			JSONObject devjson = new JSONObject();
			devjson.put("address", devip);
			devjson.put("port", port);
			devjson.put("user", user);
			devjson.put("password", password);
			JSONObject timezonejson = new JSONObject();
			timezonejson.put("timedifh", timedifh);
			timezonejson.put("timedifm", timedifm);
			JSONObject configjson = new JSONObject();
			configjson.put("dev", devjson);
			configjson.put("timezone", timezonejson);
			configjson.put("cmd", "settimezone");
			String sconfigjson = configjson.toJSONString();
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			res = da.devConfigSet(sconfigjson);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = -1;
		}
		return res;
	}

	@Override
	public int configRestore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ptzControl(String nvrid, String ipcid, int cmdval, int stop,
			int speed) {
		// "{\"nvr\":{\"address\":\"192.168.50.114\", \"port\":8000, \"user\":\"admin\", \"password\":\"12345\"}, \"ipc\":{\"address\":\"192.168.50.85\"}, \"ptz\":{\"cmd\":\"control\", \"val\":23, \"stop\":0, \"speed\":1}}";
		
		int res  = -1;
		try {
			TVmNvr nvr = nvrDao.selectByPrimaryKey(nvrid);
			JSONObject nvrjson = new JSONObject();
			nvrjson.put("address", nvr.getIp());
			nvrjson.put("port", nvr.getPort());
			nvrjson.put("user", nvr.getUsername2());
			nvrjson.put("password", nvr.getPassword());
			TVmIpc ipc = ipcDao.selectByPrimaryKey(ipcid);
			JSONObject ipcjson = new JSONObject();
			ipcjson.put("address", ipc.getIp());
			JSONObject ptzjson = new JSONObject();
			ptzjson.put("cmd", "control");
			ptzjson.put("val", cmdval);
			ptzjson.put("stop", stop);
			ptzjson.put("speed", speed);
			JSONObject configjson = new JSONObject();
			configjson.put("nvr", nvrjson);
			configjson.put("ipc", ipcjson);
			configjson.put("ptz", ptzjson);
			DeviceAdaptor da = DeviceAdaptor.instanceDll;
			res = da.devPTZControl(configjson.toJSONString());
		} catch (Exception e) {
			log.error(e.getMessage());
			res = -1;
		}
		return res;
	}
}
