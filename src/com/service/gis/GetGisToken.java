package com.service.gis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 向ArcgisServer申请链接令牌
 * 
 * @author wyj
 * 
 */
public class GetGisToken {

	private Log log = LogFactory.getLog(this.getClass());

	private static final String JSON_KEY_TOKEN = "token";// 返回的token令牌中的token
															// key-value 中的key
	private static final String JSON_KEY_EXPIRES = "expires";// 返回的token令牌中的expires
																// key-value
																// 中的key
	private static final String PARAM_KEY_USERNAME = "username";
	private static final String PARAM_KEY_PASSWORD = "password";
	private static final String PARAM_KEY_IP = "ip";
	private static final String PARAM_KEY_TPOV = "tpov";
	private static final String PARAM_KEY_SERVICE = "serviceNames";

	private static String gisTokenServerUrl = "http://10.40.14.70:9080/nsbd/s/nsbd/system/token/getCommonToken";// gis
																												// token服务地址
	private static String userName = "AFJKSERVER";// gis分配的用户名

	private static String password = "123qwe";// gis分配的密码
	private static String[] mapServiceName = { "nsbd_eagle", "nsbd_baseMap",
			"nsbd_project" };// 鹰眼地图服务，叠加所有地图要素
	private static String tokenValidTime = "1440";// 令牌有效期，单位：分钟，最大有效期1个月，40320是按照2月的28天计算
	private String clientIP = "";// 使用浏览器访问服务的计算机IP，非服务器IP

	public GetGisToken() {
		// TODO Auto-generated constructor stub
	}

	public String test() {
		return "alkf";
	}

	public String getGisToken(HttpServletRequest request) {
		String tokenStr = null;

		clientIP = getClientIP(request);

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost(gisTokenServerUrl);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		nvps.add(new BasicNameValuePair(PARAM_KEY_USERNAME, userName));
		nvps.add(new BasicNameValuePair(PARAM_KEY_PASSWORD, password));
		nvps.add(new BasicNameValuePair(PARAM_KEY_IP, clientIP));
		nvps.add(new BasicNameValuePair(PARAM_KEY_TPOV, tokenValidTime));
		nvps.add(new BasicNameValuePair(PARAM_KEY_SERVICE, StringUtils.join(
				mapServiceName, ",")));

		CloseableHttpResponse response = null;

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			//EntityUtils.consume(entity);

			/*
			 * 令牌格式 { "token":"123489asdfasdfafd", "expires": "1400911790996" }
			 */
			String resStr = entity != null ? EntityUtils.toString(entity)
					: null;
			if (resStr == null || resStr.length() == 0) {// 未取得令牌
				return null;
			}
			JSONObject jsonObj = JSON.parseObject(resStr);
			tokenStr = jsonObj.getString(JSON_KEY_TOKEN);
            EntityUtils.consume(entity);
			httpPost.releaseConnection();
			// jsonObj.getString(JSON_KEY_EXPIRES);//目前可以不处理，由token服务器管理有效期
		} catch (Exception e) {
			log.error("无法从Arcgis服务器获取令牌");
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (response != null) {
					response.close();
				}

				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return tokenStr;
	}

	private String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
