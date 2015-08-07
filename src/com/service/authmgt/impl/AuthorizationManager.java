package com.service.authmgt.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
//import org.dom4j.io.SAXReader;
//import org.dom4j.Document;
//import org.dom4j.Element;
import org.jdom.Document;
//import org.dom4j.io.SAXReader;
import org.jdom.Element;
//import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.entity.authmgt.OperationGroup;
import com.entity.authmgt.Operation;
import com.util.logmgt.CommandParser;



public class AuthorizationManager {
	private static Log log = LogFactory.getLog(AuthorizationManager.class);
	private static Map<String, List<String>> OPERATION_TO_COMMAND_MAP = new HashMap<String, List<String>>();
	private static List<OperationGroup> OPERATION_GROUPS = new ArrayList<OperationGroup>();
	private static List<Operation> OPERATIONS = new ArrayList<Operation>();
	private static Properties displayAndNames = new Properties();
	private static Properties nameAndDisplays = new Properties();
	public static String ROOT_PARENT = "ROOT_PARENT";
	private static Map<String, List<String>> LOWER_OPERATION_CACHE_MAP = new HashMap<String, List<String>>();
	private static Map<String, List<String>> HIGHER_OPERATION_CACHE_MAP = new HashMap<String, List<String>>();
//	private static final String FILE_NAME = "security/operation_authentication_tree.xml";
	private static final String FILE_NAME = "operation_authentication";
	private static OperationGroup rootParent = new OperationGroup(ROOT_PARENT,"");
	
	static{
//		parseOperationAuthenticationTree();
		parseOperationAuthenticationTreeFile();
	}
	
    public static Document parseDocument() {
//        SAXReader sax = new SAXReader();
        Document document = null;
        try {
//                uri = ConfigFilePath.getPath(uri);
                SAXBuilder builder = new SAXBuilder();
                document = builder.build(AuthorizationManager.class.getResourceAsStream("/authmgt_operation_authentication_tree.xml"));
//        	document =  sax.read(AuthorizationManager.class.getResourceAsStream("/authmgt_operation_authentication_tree.xml"));
		} catch (Exception e) {
			log.error("read document error!",e);
		}
        return document;
    }
	
	/**
	 * 
	 * 
	 * @param fileName
	 *            String
	 */
	/*private static void parseOperationAuthenticationTree() {
//		File file1 = new File(LOGGER_NAME);
//		String authenticationTreeFileName = FILE_NAME;		
////        String fileName = ConfigFilePath.getPath(authenticationTreeFileName);
//		File file=new File("");
//		try {
//			URI uri = AuthorizationManager.class.getResource("/struts.xml").toURI();
//			log.info("parseOperationAuthenticationTree-uri: "+uri);
//	
//			String uriStr=uri.toString();
//			String filePath=uriStr.substring(6,uriStr.length()-10)+authenticationTreeFileName;
//			file=new File(filePath);
//			log.info("parseOperationAuthenticationTree-file: "+file);
//		} catch (Exception e) {
//			log.error("read document error!",e);
//		}
//        File file = new File(authenticationTreeFileName);

//		System.out.println(file.getAbsolutePath());    
		
//        if(file.isDirectory()){
//        	File[] files = file.listFiles();
//        	if(files == null || files.length == 0){
//        		log.debug("no authentication tree file in "+authenticationTreeFileName);
//    			return;
//        	}
//        	List<Element> elementList = new ArrayList<Element>(files.length);
//        	
//        	for(File tmpFile:files){
//    			log.info("parseOperationAuthenticationTree-tmpFile: "+tmpFile);
//        		if(tmpFile.isFile() && tmpFile.length() > 0 && tmpFile.getName().endsWith(".xml")){
//        			parseOperationAuthenticationTreeFileIndex(elementList);
//        		}
//        	}
//        	//
//        	if(elementList.size() == 0){
//        		log.debug("no authentication tree file whose size is more than 0 in  "+authenticationTreeFileName);
//    			return;
//        	}
        	//
//        	Collections.sort(elementList,new Comparator<Element>(){
// 			   public int compare(Element o1, Element o2){
// 				   String s1 = o1.getAttributeValue("index");
// 				   String s2 = o2.getAttributeValue("index");
// 				   //
//				   if(s1 == null || s2 == null){
//					   return 0;
//				   }
// 				   try{
// 					   Integer i1 = Integer.valueOf(s1);
// 					   Integer i2 = Integer.valueOf(s2);
// 					   return i1.compareTo(i2);
// 				   }catch(Throwable e){
// 					  return s1.compareTo(s2);
// 				   }
// 			   }
// 			});
        	//
//        	for(Element element:elementList){
//        		parseElement(rootParent, element);
//        	}
        //
//        }else if(file.isFile()){
        	parseOperationAuthenticationTreeFile();
//        }
//        processDependence();
	}*/
/*	
	private static void parseOperationAuthenticationTreeFileIndex(List<Element> elementList) {
//		if(LogOperator.isDebugEnabled(LOGGER_NAME)){
//			LogOperator.debug(LOGGER_NAME, "Begin parse file element,file is "+file.getAbsolutePath());	        				
//		}	
//		log.debug("Begin parse file element,file is "+file.getAbsolutePath());
		
		Document document = parseDocument();
		if (document != null) {
			Element element = document.getRootElement();
			if (element != null) {
				elementList.add(element);
			}else{
				log.debug("[parseOperationAuthenticationTreeFileIndex]" +",root element is null");
			}
		}else{
//			LogOperator.err(LOGGER_NAME,"[parseOperationAuthenticationTreeFileIndex]" +file.getAbsolutePath()+" document is null");	
			log.debug("[parseOperationAuthenticationTreeFileIndex]" +" document is null");	
		}		
	}	*/
	
	private static void parseOperationAuthenticationTreeFile() {
		Document document = parseDocument();
		if (document != null) {
			// operation_tree
			Element element = document.getRootElement();
			if (element != null) {
				parseElement(rootParent, element);
				//processDependence();
				//writeLog();
			}else{
//				LogOperator.err(LOGGER_NAME, file.getAbsolutePath()+",root element is null");
				log.error( ",root element is null");	
			}
		}else{
//			LogOperator.err(LOGGER_NAME, file.getAbsolutePath()+" document is null");	
			log.error( " document is null");	
		}		
	}
	
	
	

	private static void parseElement(OperationGroup parentGroup, Element element) {
		try{
		    if( "operation_tree".equals(element.getName())){
		    	//
		    	if(parentGroup.getChildren().size() == 0){
		    		//
		    		parentGroup.getChildren().add(parseOperationGroup(parentGroup, element).getOpGroupName());
		    	}else{
		    		//
		    		OperationGroup tmpParentGroup = getOperationGroup(parentGroup.getChildren().get(0));
		    		List children = element.getChildren();
		    		for(Object c:children){
		    			tmpParentGroup.getChildren().add(parseOperationGroup(tmpParentGroup, (Element)c).getOpGroupName());
		    		}
		    	}
		    }else if ("operation_group".equals(element.getName())) {
				parentGroup.getChildren().add(parseOperationGroup(parentGroup, element).getOpGroupName());
			} else if ("operation".equals(element.getName())) {
				parentGroup.getChildren().add(parseOperation(parentGroup, element).getOpName());
			}
	    }catch(Throwable e){
//			LogOperator.err(LOGGER_NAME, "[parseElement]",e);
	    	log.error("[parseElement]",e);		
		}
	}

	private static OperationGroup parseOperationGroup(OperationGroup parentGroup, Element element) {
		OperationGroup group = new OperationGroup(element
				.getAttributeValue("name"), element
				.getAttributeValue("display_name"), parentGroup.getOpGroupName());
		OPERATION_GROUPS.add(group);
		List children = element.getChildren();
		for (int i = 0, n = children.size(); i < n; i++) {
			parseElement(group, (Element) children.get(i));
		}
		return group;
	}

	private static Operation parseOperation(OperationGroup parentGroup, Element element) {
		Operation operation = new Operation(element.getAttributeValue("name"),
				element.getAttributeValue("display_name"),
				parentGroup.getOpGroupDisplayName());
		// 
		List commandGroupChildren = element.getChildren("command_group");
		List commands = new ArrayList<String>();
		if (commandGroupChildren != null && commandGroupChildren.size() > 0) {
			Element commandGroup = (Element) commandGroupChildren.get(0);
			List commandElements = commandGroup.getChildren();
			if (commandElements == null || commandElements.size() == 0) {
//wqj				commands.add(getCurrentCommandId(operation
//						.getOpName()));
				commands.add(operation.getOpName());
				OPERATION_TO_COMMAND_MAP.put(operation.getOpName(), commands);
			} else {
				for (Object obj : commandElements) {
//wqj					commands.add(getCurrentCommandId(((Element) obj)
//							.getAttributeValue("name")));
					commands.add(((Element) obj).getAttributeValue("name"));
				}
				OPERATION_TO_COMMAND_MAP.put(operation.getOpName(), commands);
			}
		} else {
//wqj			commands.add(getCurrentCommandId(operation.getOpName()));
			commands.add(operation.getOpName());
			OPERATION_TO_COMMAND_MAP.put(operation.getOpName(), commands);
		}
		operation.setCommands(OPERATION_TO_COMMAND_MAP.get(operation
				.getOpName()));
		// 
		List lowerPriorityOperationchildren = element
				.getChildren("lower_priority_operation");
		if (lowerPriorityOperationchildren != null
				&& lowerPriorityOperationchildren.size() > 0) {
			Element lowerElement = (Element) lowerPriorityOperationchildren
					.get(0);
			List lowerOpList = lowerElement.getChildren();
			for (int i = 0, n = lowerOpList.size(); i < n; i++) {
				String name = ((Element) lowerOpList.get(i))
						.getAttributeValue("name");
				operation.getLowerPriorityAuthenticatedOperations().add(name);
				Operation o = getOperation(name);
				if (o != null) {
					o.getHigherPriorityAuthenticatedOperations().add(
							operation.getOpName());
				} else {
					if (HIGHER_OPERATION_CACHE_MAP.containsKey(name)) {
						HIGHER_OPERATION_CACHE_MAP.get(name).add(
								operation.getOpName());
					} else {
						List tmp = new ArrayList<String>();
						tmp.add(operation.getOpName());
						HIGHER_OPERATION_CACHE_MAP.put(name, tmp);
					}
				}
			}
		}

		List higherPriorityOperationchildren = element
				.getChildren("higher_priority_operation");
		if (higherPriorityOperationchildren != null
				&& higherPriorityOperationchildren.size() > 0) {
			Element higherElement = (Element) higherPriorityOperationchildren
					.get(0);
			List higherOpList = higherElement.getChildren();
			for (int i = 0, n = higherOpList.size(); i < n; i++) {
				String name = ((Element) higherOpList.get(i))
						.getAttributeValue("name");
				operation.getHigherPriorityAuthenticatedOperations().add(name);
				Operation o = getOperation(name);
				if (o != null) {
					o.getLowerPriorityAuthenticatedOperations().add(
							operation.getOpName());
				} else {
					if (LOWER_OPERATION_CACHE_MAP.containsKey(name)) {
						LOWER_OPERATION_CACHE_MAP.get(name).add(
								operation.getOpName());
					} else {
						List tmp = new ArrayList<String>();
						tmp.add(operation.getOpName());
						LOWER_OPERATION_CACHE_MAP.put(name, tmp);
					}
				}
			}
		}
		
		OPERATIONS.add(operation);
		displayAndNames.setProperty(operation.getOpDisplayName(), operation.getOpName());
		nameAndDisplays.setProperty(operation.getOpName(), operation.getOpDisplayName());
		return operation;
	}

	private static Operation getOperation(String name) {
		return getOperation(OPERATIONS,name);
	}
	
	private static Operation getOperation(List<Operation> operations	,String name) {
		for (Operation o : operations) {
			if (name.equals(o.getOpName())) {
				return o;
			}
		}
		return null;
	}
	
//	public static String getOperationName(List<Operation> operations	,String displayName) {
//		for (Operation o : operations) {
//			if (displayName.equals(o.getOpDisplayName())) {
//				return o.getOpName();
//			}
//		}
//		return "";
//	}
	
	private static OperationGroup getOperationGroup(List<OperationGroup> operationGroups,String name) {
		for (OperationGroup og : operationGroups) {
			if (name.equals(og.getOpGroupName())) {
				return og;
			}
		}
		return null;
	}

	private static OperationGroup getOperationGroup(String name) {
		return getOperationGroup(OPERATION_GROUPS,name);
	}
	
//    public static void main(String[] s) {
//    	parseOperationAuthenticationTreeFile();
//
//    }	
    
//    public static Map<String, List<String>> getOperationToCommandMap() {
//		return OPERATION_TO_COMMAND_MAP;
//	}

	public static List<OperationGroup> getOperationGroups() {
		return OPERATION_GROUPS;
	}

	public static List<Operation> getOperations() {
		return OPERATIONS;
	}
//	public static Map<String, List<String>> getHigherOperationMap() {
//		return HIGHER_OPERATION_CACHE_MAP;
//	}
//	public static Map<String, List<String>> getLowerOperationMap() {
//		return LOWER_OPERATION_CACHE_MAP;
//	}
	
	public static Properties getOperationDisplayAndName() {
		return displayAndNames;
	}



	public static Properties getOperationNameAndDisplay() {
		return nameAndDisplays;
	}
//	public static List<String> getOperationNames() {
//		if(OPERATIONS == null){
//			return Collections.EMPTY_LIST;
//		}
//		List<String> names = new ArrayList<String>(OPERATIONS.size());
//		for(Operation o:OPERATIONS){
//			names.add(o.getOpName());
//		}
//		return names;
//	}	
//	public static String getCurrentCommandId(String commandId){
//    	try{
//    		Long.valueOf(commandId);
//    		return commandId;
//    	}catch(Throwable e){
////    		return String.valueOf(CommandInfoManager.getCommandId(commandId));
//    		return "";
//    	}
//    }    
}
