package com.service.videomonitor.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.videomonitor.TVmRoundMapper;
import com.dao.videomonitor.TVmRoundSequenceMapper;
import com.entity.authmgt.Session;
import com.entity.videomonitor.TVmCruise;
import com.entity.videomonitor.TVmCruiseSequence;
import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmPresetposition;
import com.entity.videomonitor.TVmRound;
import com.entity.videomonitor.TVmRoundSequence;
import com.service.videomonitor.RoundService;
import com.util.alarmmgt.AlarmUtil;

public class RoundServiceImpl implements RoundService {
	
	private TVmRoundMapper roundDao;
	private TVmRoundSequenceMapper roundSequenceDao;

	public TVmRoundMapper getRoundDao() {
		return roundDao;
	}

	public void setRoundDao(TVmRoundMapper roundDao) {
		this.roundDao = roundDao;
	}

	public TVmRoundSequenceMapper getRoundSequenceDao() {
		return roundSequenceDao;
	}

	public void setRoundSequenceDao(TVmRoundSequenceMapper roundSequenceDao) {
		this.roundSequenceDao = roundSequenceDao;
	}

	@Override
	public int saveRound(String roundSetData) throws Exception {
		//{'roundid':1,'roundname':'round1','screenmod':4,'pausemins':3,'sequences':[{'rsid':1,'roundid':1,'ipcid':'ipc001','seqorder':2},{...},...]}
		int res = 0;
		JSONObject jo = JSONObject.fromObject(roundSetData);
		String roundname = jo.getString("roundname");
		int pausemins = jo.getInt("pausemins");
		int screenmod = jo.getInt("screenmod");

		TVmRound tr = new TVmRound();
		if (jo.containsKey("roundid") && !jo.getString("roundid").equals("")) {
			int roundid = jo.getInt("roundid");
			tr.setRoundid(roundid);
			tr.setRoundname(roundname);
			tr.setPausemins(pausemins);
			tr.setScreenmod(screenmod);
			res = roundDao.updateRound(tr);
			roundSequenceDao.deleteRoundSequenceByRound(roundid);
			if (res != 1) {
				throw new Exception("修改 巡航名称时出现错误！");
			}
		} else {
			Session session = AlarmUtil.getLoginSession();
			String userId = session.getId();
			if (userId == null) {
				throw new Exception("用户id为空");
			}
			tr.setRoundname(roundname);
			tr.setPausemins(pausemins);
			tr.setScreenmod(screenmod);
			tr.setUserid(userId);
			res = roundDao.insertRound(tr);
		}
		JSONArray ja = jo.getJSONArray("sequences");

		for (Object sequence : ja.toArray()) {
			JSONObject joseq = (JSONObject) sequence;
			int seqorder = joseq.getInt("seqorder");
			JSONObject ipcjson = joseq.getJSONObject("ipc");
			String ipcid = ipcjson.getString("ipcid");
			TVmRoundSequence trs = new TVmRoundSequence();
			TVmIpc ipc = new TVmIpc();
			ipc.setIpcid(ipcid);
			trs.setIpc(ipc);
			trs.setSeqorder(seqorder);
			trs.setRoundid(tr.getRoundid());
			roundSequenceDao.insertRoundSequence(trs);
		}
		return res;
	}

	@Override
	public List<TVmRound> loadRounds() throws Exception {
		Session session = AlarmUtil.getLoginSession();
		String userid = session.getId();
		if (userid == null) {
			throw new Exception("用户id为空");
		}
		Map<String,Object> cond = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userid", userid);
		cond.put("cond", params);
		return roundDao.selectRound(cond);
	}

	@Override
	public int deleteRoundById(String roundSetData) {
		JSONObject jo = JSONObject.fromObject(roundSetData);
		int roundid = jo.getInt("roundid");
		roundSequenceDao.deleteRoundSequenceByRound(roundid);
		return roundDao.deleteRoundById(roundid);
	}

}
