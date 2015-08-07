package com.actions.videomonitor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.entity.authmgt.Session;
import com.util.alarmmgt.AlarmUtil;

public class VideoWallAction extends LoadVideoViewAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String playCookieContent;
	private String setCookieRes;
	
	public VideoWallAction(){
		log = LogFactory.getLog(VideoWallAction.class);
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

	public String loadVideoWall(){
		String res = SUCCESS;
		return res;
	}
	
	public String setVideoWallCookie(){
		String res = SUCCESS;
		try {
			setCookieRes = JSONObject.fromObject("{}").toString();
			JSONObject playContent = JSONObject.fromObject(playCookieContent);
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
			String playurlName =  "videowall-" + userId;
			boolean isFind = false;
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(playurlName)){
					try {
						String playValue = cookie.getValue();
						JSONObject joPlayValue = JSONObject.fromObject(playValue);
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					response.addCookie(cookie);
					isFind = true;
					break;
				}
			}
			if(!isFind){
				JSONObject joPlayValue = new JSONObject();
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

}
