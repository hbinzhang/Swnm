package com.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * Title: developerportal_guangdong<br>
 * Description: <br>
 * 
 * @date: Jul 4, 2012 1:40:43 PM <br>
 * @author lufei
 */

public class JsonUtil {
	public final static Log logger = LogFactory.getLog(JsonUtil.class);
	/**
	 * 从一个JSON 对象字符格式中得到一个java对象，形如： {"id" : idValue, "name" : nameValue,
	 * "aBean" : {"aBeanId" : aBeanIdValue, ...}}
	 * 
	 * @param object
	 * @param clazz
	 * @return
	 */
	@Deprecated
	public static Object convertJsonToObject(String jsonString, Class clazz) {
		JSONObject jsonObject = null;
		try {
			//setDataFormat2JAVA();
			jsonObject = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			logger.error("",e);;
		}
		return JSONObject.toBean(jsonObject, clazz);
	}

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象，其中beansList是一类的集合，形如： {"id" : idValue, "name" :
	 * nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}, beansList:[{}, {},
	 * ...]}
	 * 
	 * @param jsonString
	 * @param clazz
	 * @param map
	 *            集合属性的类型 (key : 集合属性名, value : 集合属性类型class) eg: ("beansList" :
	 *            Bean.class)
	 * @return
	 * @throws Exception 
	 */
	public static Object convertJsonToObject(String jsonString, Class clazz,
			Map map) throws Exception {
		JSONObject jsonObject = null;
		try {
			//setDataFormat2JAVA();
			jsonObject = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			throw new Exception("将一个JSON字符串转化为java异常！",e);
		}
		return JSONObject.toBean(jsonObject, clazz, map);
	}

	/**
	 * 把数据对象转换成json字符串 DTO对象形如：{"id" : idValue, "name" : nameValue, ...}
	 * 数组对象形如：[{}, {}, {}, ...] map对象形如：{key1 : {"id" : idValue, "name" :
	 * nameValue, ...}, key2 : {}, ...}
	 * 
	 * @param object
	 * @return
	 */
	public static String getJSONString(Object object) throws Exception {
		String jsonString = null;
		JsonConfig jsonConfig = new JsonConfig();
		if (object != null) {
			if (object instanceof Collection || object instanceof Object[]) {
				jsonString = JSONArray.fromObject(object, jsonConfig)
						.toString();
			} else {
				jsonString = JSONObject.fromObject(object, jsonConfig)
						.toString();
			}
		}
		return jsonString == null ? "{}" : jsonString;
	}

/*	private static void setDataFormat2JAVA() {

		// 设定日期转换格式
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd",
						"yyyy-MM-dd HH:mm:ss" }));
	}*/
	
	/**
	 * 拼接JSON字符串，有些特殊字符需要替换掉,
	 * 如果未替换这些特殊字符生成的JSON不会被正确解析 
	 * @param str
	 * @return
	 */
	 public static String ConvertStringToJson(String str) {
		 
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < str.length(); i++) {
             char c = str.toCharArray()[i];
             switch (c) {
                 case '\"': sb.append("\\\""); break;
                 case '\\': sb.append("\\\\"); break;
                 case '/': sb.append("\\/"); break;
                 case '\b': sb.append("\\b"); break;
                 case '\f': sb.append("\\f"); break;
                 case '\n': sb.append("\\n"); break;
                 case '\r': sb.append("\\r"); break;
                 case '\t': sb.append("\\t"); break;
                 default: sb.append(c); break;
             }
         }
         return sb.toString();
     }
	 
	 
	 public static <T>T jsonToBean(String json , Class<T> clazz){
		 JSONObject jsonObject = JSONObject.fromObject(json);
		 return (T)JSONObject.toBean(jsonObject,clazz);
	 }
	 
	 public static <T>List<T> jsonToList(String json , Class<T> clazz){
		 JSONArray arry = JSONArray.fromObject(json);
		 return JSONArray.toList(arry,clazz);
	 }
	 
	 public static <T>T[] jsonToArray(String json , Class<T> clazz){
		 JSONArray arry = JSONArray.fromObject(json);
		 return (T[])JSONArray.toArray(arry,clazz);
	 }
	 
	 public static String listToJson(List<?> list){
		 return JSONSerializer.toJSON(list).toString();
	 }

}
