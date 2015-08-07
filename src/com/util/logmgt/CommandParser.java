package com.util.logmgt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.entity.logmgt.CommandInfo;

public class CommandParser {
	
    private List<CommandInfo> allCommandInfo = new ArrayList<CommandInfo>();   
	private static CommandParser _instance = null;
	// key:moduleId, value:List<commandInfo>
	private Map<Integer, List<CommandInfo>> moduleWithCommandMap = new HashMap<Integer, List<CommandInfo>>();
	private Log log = LogFactory.getLog(this.getClass());

    private CommandParser(){
    	init();
    }

    public static CommandParser getInstance() {
		synchronized (CommandParser.class) {
		if (_instance == null) {
			_instance = new CommandParser();
		}
		return _instance;
		}
	}
    
	private void init(){	
		SAXReader sax = new SAXReader();
		Document document = null;
		try {
		//	URL url = CommandParser.class.getResource("/command-info.xml"); 
			document = sax.read(CommandParser.class.getResourceAsStream(
					"/command-info.xml"));
		} catch (Exception e) {
			log.error("read document error!",e);
		}
		List<Element> rootEle = document.getRootElement().elements();
		try{
			for(Element element : rootEle){
				CommandInfo commandInfo = new CommandInfo();
				int commandId = Integer.parseInt(element.attributeValue("id"));
				String name = element.attributeValue("name");
				String displayName = element.attributeValue("display_name");		
				commandInfo.setCommandId(commandId);
				commandInfo.setName(name);
				commandInfo.setDisplayName(displayName);
				String moduId = element.attributeValue("module_id");
				if(null != moduId && !moduId.equals("")){
					commandInfo.setModuleId(Integer.parseInt(moduId));
				}
				/*String logType = element.attributeValue("logtype");
				if(null != logType && !logType.equals("")){
					commandInfo.setLogType(Integer.parseInt(logType));
				}
				String isAsynch = element.attributeValue("is_asynch");
				if(null != isAsynch && !isAsynch.equals("")){
					commandInfo.setIsAsynch(isAsynch);
				}*/
				allCommandInfo.add(commandInfo);
			}
		}catch(Exception ex){
			log.error("init error!",ex);
		}
	}
	
	public CommandInfo getDetail(int commandId){
		for (CommandInfo c : allCommandInfo) {
			if (c.getCommandId() == commandId) {
				return c;
			}
		}
		return null;
	}
	
	public CommandInfo getDetailByName(String name){
		for (CommandInfo c : allCommandInfo) {
			if (c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}
	
	
	public List<CommandInfo> getAllCommand(){
		return allCommandInfo;
	}
	
	public List<CommandInfo> getCommandsByModule(int moduleId){
		List<CommandInfo> list = new ArrayList<CommandInfo>();
		for (CommandInfo c : allCommandInfo) {
			if (c.getModuleId() == moduleId) {
				list.add(c);
			}
		}
		return list;
	}
	
	public Map<Integer, List<CommandInfo>> getModuleWithCommandMap(){
		for (CommandInfo c : allCommandInfo) {
			int moduleId = c.getModuleId();	
			if (moduleWithCommandMap.containsKey(moduleId)){
				List<CommandInfo> valueList = moduleWithCommandMap.get(moduleId);
				valueList.add(c);
			} else {
				List<CommandInfo> valueList = new ArrayList<CommandInfo>();
				valueList.add(c);
				moduleWithCommandMap.put(moduleId, valueList);
			}
		}
		return moduleWithCommandMap;
	}
	
}
