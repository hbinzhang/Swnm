package com.actions.videomonitor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.entity.authmgt.Session;
import com.entity.videomonitor.TVmCruise;
import com.entity.videomonitor.TVmPresetposition;
import com.entity.videomonitor.TVmRound;
import com.service.videomonitor.CruiseService;
import com.service.videomonitor.PresetService;
import com.service.videomonitor.RoundService;
import com.util.alarmmgt.AlarmUtil;

public class VideoPreviewAction extends LoadVideoViewAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playCookieContent;
	private String setCookieRes;
	private PresetService presetService;
	private CruiseService cruiseService;
	private RoundService roundService;
	private String ajaxRes;
	private String presetSetData;//设置预置位ajax参数
	private String cruiseSetData;//设置巡航ajax参数
	private String roundSetData;//设置轮巡ajax参数
	
	public String getRoundSetData() {
		return roundSetData;
	}

	public void setRoundSetData(String roundSetData) {
		this.roundSetData = roundSetData;
	}

	public String getCruiseSetData() {
		return cruiseSetData;
	}

	public void setCruiseSetData(String cruiseSetData) {
		this.cruiseSetData = cruiseSetData;
	}

	public String getPresetSetData() {
		return presetSetData;
	}

	public void setPresetSetData(String presetSetData) {
		this.presetSetData = presetSetData;
	}
	
	public String getAjaxRes() {
		return ajaxRes;
	}

	public void setAjaxRes(String ajaxRes) {
		this.ajaxRes = ajaxRes;
	}

	public PresetService getPresetService() {
		return presetService;
	}

	public void setPresetService(PresetService presetService) {
		this.presetService = presetService;
	}

	public CruiseService getCruiseService() {
		return cruiseService;
	}

	public void setCruiseService(CruiseService cruiseService) {
		this.cruiseService = cruiseService;
	}

	public RoundService getRoundService() {
		return roundService;
	}

	public void setRoundService(RoundService roundService) {
		this.roundService = roundService;
	}

	public VideoPreviewAction(){
		log = LogFactory.getLog(VideoPreviewAction.class);
	} 

	public String getSetCookieRes() {
		return setCookieRes;
	}

	public void setSetCookieRes(String setCookieRes) {
		this.setCookieRes = setCookieRes;
	}

	public String getPlayCookieContent() {
		return playCookieContent;
	}

	public void setPlayCookieContent(String playCookieContent) {
		this.playCookieContent = playCookieContent;
	}

	public String loadVideoPreview(){
		String res = SUCCESS;
		return res;
	}

	public String setPlayCookie(){
		String res = SUCCESS;
		try {
			setCookieRes = JSONObject.fromObject("{}").toString();
			JSONObject playContent = JSONObject.fromObject(playCookieContent);
			Object windowmode = playContent.get("windowmode");
			Object index = playContent.get("index");
			Object url = playContent.get("url");
			Session session = AlarmUtil.getLoginSession();
			String userId = session.getId();
			if(userId == null){
				throw new Exception("用户id为空");
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			Cookie[] cookies = request.getCookies();
			String playurlName =  "playurl-" + userId;
			boolean isFind = false;
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(playurlName)){
					try {
						String playValue = cookie.getValue();
						JSONObject joPlayValue = JSONObject.fromObject(playValue);
						if(windowmode != null && !windowmode.equals("")){
							joPlayValue.put("windowmode", windowmode);
						}

						JSONArray jaUrls = joPlayValue.getJSONArray("urls");
						boolean isFindUrl = false;
						JSONObject joUrl = null;
						if(url != null && !url.equals("")){
							for(Object jo : jaUrls.toArray()){
								joUrl = (JSONObject)jo;
								if(joUrl == null){
									continue;
								}
								if(joUrl.get("index").equals(index)){
									joUrl.put("url", url);
									isFindUrl = true;
									break;
								}
							}
							if(!isFindUrl){
								joUrl = new JSONObject();
								joUrl.put("index", index);
								joUrl.put("url", url);
								jaUrls.add(joUrl);
							}
						}
						else{
							for(Object jo : jaUrls.toArray()){
								joUrl = (JSONObject)jo;
								if(joUrl == null){
									continue;
								}
								if(joUrl.get("index").equals(index)){
									isFindUrl = true;
									break;
								}
							}
							if(isFindUrl){
								jaUrls.remove(joUrl);
							}
						}

						cookie.setValue(joPlayValue.toString());
						cookie.setMaxAge(Integer.MAX_VALUE);
					} catch (Exception e) {
						e.printStackTrace();
					}
					response.addCookie(cookie);
					isFind = true;
					break;
				}
			}
			if(!isFind){
				JSONObject joPlayValue = new JSONObject();
				joPlayValue.put("windowmode", windowmode);
				JSONArray jaUrls = new JSONArray();
				JSONObject joUrl = new JSONObject();
				joUrl.put("index", index);
				joUrl.put("url", url);
				jaUrls.add(joUrl);
				joPlayValue.put("urls", jaUrls);
				Cookie playCookie = new Cookie(playurlName,joPlayValue.toString());
				playCookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(playCookie);
				
			}
		} catch (Exception e) {
			log.error("保存cookie失败：" + e.getMessage());
			res = ERROR;
		}
		return res;
	}

	public String gotoPreset(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		int sres = 0;
		try {
			JSONObject jop = JSONObject.fromObject(presetSetData);
			String ipcid = jop.getString("ipcid");
			int presetno = jop.getInt("presetno");
			sres = presetService.gotoPtzPreset(ipcid, presetno);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
			jsonres.put("sres", e.getMessage());
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String savePreset(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		int sres = 0;
		try {
			sres = presetService.savePreset(presetSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			res = ERROR;
			jsonres.put("sres", e.getMessage());
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String deletePreset(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			int sres = presetService.deletePreset(presetSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String loadPresetByPage(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			List<TVmPresetposition> presets = presetService.loadPresetByPage(presetSetData);
			JSONArray ja = JSONArray.fromObject(presets);
			jsonres.put("sres", ja);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String saveCruise(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		int sres = 0;
		try {
			sres = cruiseService.saveCruise(cruiseSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());			
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();		
		return res;
	}
	
	public String loadCruiseByIpcid(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			List<TVmCruise> cruises = cruiseService.loadCruisesByIpc(cruiseSetData);
			JSONArray ja = JSONArray.fromObject(cruises);
			jsonres.put("sres", ja);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String deleteCruiseById(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			int sres = cruiseService.deleteCruiseById(cruiseSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String startCruise(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			int sres = cruiseService.startCruise(cruiseSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String stopCruise(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			int sres = cruiseService.stopCruise(cruiseSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String saveRound(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		int sres = 0;
		try {
			sres = roundService.saveRound(roundSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());			
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();		
		return res;
	}
	
	public String loadRounds(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			List<TVmRound> rounds = roundService.loadRounds();
			JSONArray ja = JSONArray.fromObject(rounds);
			jsonres.put("sres", ja);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
	
	public String deleteRoundById(){
		String res = SUCCESS;
		JSONObject jsonres = new JSONObject();
		try {
			int sres = roundService.deleteRoundById(roundSetData);
			jsonres.put("sres", sres);
		} catch (Exception e) {
			log.error(e.getMessage());
			jsonres.put("sres", e.getMessage());
			res = ERROR;
		}
		jsonres.put("res", res);
		ajaxRes = jsonres.toString();
		return res;
	}
}
