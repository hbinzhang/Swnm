package com.util.logmgt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ModuleAndObjectTypeConf {
	
	private Map<Integer, String> moduleMap = new HashMap<Integer, String>();
	private static ModuleAndObjectTypeConf _instance = null;
	private Log log = LogFactory.getLog(this.getClass());


    private ModuleAndObjectTypeConf(){
    	loadFromFile();
    }

    public static ModuleAndObjectTypeConf getInstance() {
		synchronized (CommandParser.class) {
		if (_instance == null) {
			_instance = new ModuleAndObjectTypeConf();
		}
		return _instance;
		}
	}
	

	/**
     * 从文件中加载数据
     */
    private void loadFromFile() {
	//	URL url = ModuleAndObjectTypeConf.class.getClassLoader().getResource(""); 
        Document document = parseDocument();
        if (document != null) {
        	int moduleId;
        	String moduleStr;
        //  String objTypeKey;
        //  int objTypeId;
        //	String displayName;
        	List<Element> moduleList = document.getRootElement().elements();
        	for(Element module: moduleList){
        		moduleId = Integer.parseInt(module.attributeValue("id"));
        		moduleStr = module.attributeValue("display_name");
        		moduleMap.put(moduleId, moduleStr);
        	}
       /* 	List<Element> objTypeList = module.getChildren();
            for(Element objType: objTypeList){
             	objTypeKey = objType.getAttributeValue("key");
             	objTypeId = Integer.parseInt(objType.getAttributeValue("id"));
             	displayName = I18n.getMsg(objType.getAttributeValue("display_name"));
             	if(displayName.indexOf(I18n.KEY_NOT_EXIST) != -1){
             		displayName = objType.getAttributeValue("display_name");
         		}
             	List objTypeLst = new ArrayList();
             	objTypeLst.add(objTypeId);
             	objTypeLst.add(displayName);
             	objTypeMap.put(objTypeKey, objTypeLst);
            }*/
        }
    }
    
    /**
     * 解析XML文件
     * @param uri String
     * @return Document
     */
    private Document parseDocument() {
    	SAXReader sax = new SAXReader();
		Document document = null;
		try {
			document = sax.read(ModuleAndObjectTypeConf.class.getResourceAsStream(
					"/module_object_type_conf.xml"));
		} catch (Exception e) {
			log.error("parseDocument error!",e);
		} 
        return document;
    }
    
	/**
	 * 获取软件标识map
	 * @return Map
	 */
	public Map<Integer,String> getModuleMap(){
		return moduleMap;
	}
  
}
