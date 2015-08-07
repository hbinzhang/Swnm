package com.service.authmgt.impl;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.service.authmgt.impl.AuthorizationManager;

public class AuthenticationManager {
	private static Log log = LogFactory.getLog(AuthenticationManager.class);
	/** 这些操作是授权时授权树中的叶子节点 */
	private static Map<String, List<String>> accountAndAutohrizedOps = new ConcurrentHashMap<String, List<String>>();

	/**
	 * 授权的操作与命令的映射数据 key：为操作名称，即授权时授权树中的叶子节点对应的名称，即操作的名字（operation的name）
	 * value：为命令名称列表。当无命令时，则为操作名称
	 */
	private static Map<String, List<String>> OPERATION_COMMAND_MAPPING = new HashMap<String, List<String>>();

	private static final String COMMANDMAPPING_FILE_NAME = "operation_authentication";

	private static final String NEGATIVE_ONE = "-1";

	static {
		initCommandMappingData();
	}

	/**
	 * 初始化所有相关的数据
	 * 
	 * @param authorizedOperations
	 *            其中授权的操作是当前登录用户已经授权的操作。 void
	 */
	public static void initializeData(String account,
			List<String> authorizedOperations) {
		// 初始化当前帐号授权的操作
		
		// 无任何权限的普通用户
		if (authorizedOperations == null) {
			authorizedOperations=new ArrayList();
			authorizedOperations.add("null");
		}
		initAuthorizedOperations(account, authorizedOperations);
	}

	/**
	 * 释放资源 void
	 */
	public static void finalizeData(String account) {
		if (accountAndAutohrizedOps.keySet().contains(account)) {
			accountAndAutohrizedOps.remove(account);
		}
	}

	/**
	 * 初始化授权的操作列表
	 * 
	 * @param authorizedOperations
	 *            void
	 */
	private static void initAuthorizedOperations(String account,
			List<String> authorizedOperations) {
		if (accountAndAutohrizedOps.keySet().contains(account)) {
			accountAndAutohrizedOps.remove(account);
		}
		accountAndAutohrizedOps.put(account, authorizedOperations);
		log.info("initAuthorizedOperations--accountAndAutohrizedOps:"+accountAndAutohrizedOps);
	}

	/**
	 * 初始化操作-命令映射 void
	 */
	private static void initCommandMappingData() {

		if (OPERATION_COMMAND_MAPPING == null) {
			OPERATION_COMMAND_MAPPING = new HashMap<String, List<String>>();
		}
		OPERATION_COMMAND_MAPPING.clear();
		try {
			Document document = AuthorizationManager.parseDocument();
			if (document != null) {
				Element element = document.getRootElement();
				if (element != null) {
					parseElement(element);

				}
			}
		} catch (Throwable e) {
		}
	}

	/**
	 * 解析操作-命令映射文件。 由于有可能和授权配置文件使用同一个文件配置，因此需要解析操作组，解析操作组是为了找操作，真正映射部分配置在操作。
	 * 如果操作-命令映射文件使用独立文件，要求root的下级元素是操作即可。
	 * 
	 * @param element
	 *            void
	 */
	private static void parseElement(Element element) {
		if ("operation".equals(element.getName())) {
			parseOperation(element);
		} else {
			// 找操作
			parseOperationGroup(element);

		}
	}

	private static void parseOperationGroup(Element element) {
		List children = element.getChildren();
		for (int i = 0, n = children.size(); i < n; i++) {
			parseElement((Element) children.get(i));
		}
	}

	/**
	 * 解析操作-命令映射内容
	 * 
	 * @param element
	 *            void
	 */
	private static void parseOperation(Element element) {
		// 处理command_group
		List commandGroupChildren = element.getChildren("command_group");
		List commands = new ArrayList<String>();
		String opName = element.getAttributeValue("name");
		// 操作下面配置了命令组
		if (commandGroupChildren != null && commandGroupChildren.size() > 0) {
			// 只处理第一个命令组
			Element commandGroup = (Element) commandGroupChildren.get(0);
			List commandElements = commandGroup.getChildren();
			// 获得命令组下的命令列表
			// 如果命令列表为空，则认为当前操作名称对应的就是命令，直接将操作和相应的命令标识列表放入Map
			if (commandElements == null || commandElements.size() == 0) {
				commands.add(opName);
				OPERATION_COMMAND_MAPPING.put(opName, commands);
				// 否则处理命令列表，将命令标识的列表放入Map
			} else {
				for (Object obj : commandElements) {
					commands.add(((Element) obj).getAttributeValue("name"));
				}
				OPERATION_COMMAND_MAPPING.put(opName, commands);
			}
			// 如果命令组为空，则命令列表为空，则认为当前操作名称对应的就是命令，直接将操作和相应的命令标识列表放入Map
		} else {
			commands.add(opName);
			OPERATION_COMMAND_MAPPING.put(opName, commands);
		}
	}

	/**
	 * 判断给定的操作是否有权限
	 * 
	 * @param operation
	 * @return boolean
	 */
	private static boolean isAuthorizedOperation(String account,
			String operation) {
		boolean ret = false;
		List<String> authorizedOps = (List) accountAndAutohrizedOps
				.get(account);
		// 
		if (authorizedOps == null) {
			ret = false;
			// 管理员
		} else if (authorizedOps.size()==0) {
			ret = true;
		} else
			ret = authorizedOps.contains(operation);
		return ret;
	}

	/**
	 * 判断给定的Id是否有权限
	 * 
	 * @param id
	 * @return boolean
	 */
	public static boolean isAuthorizedId(String account, String ids) {
		boolean ret = false;
		if (ids != null) {
			String[] idStrs = ids.split(",");
			for (String id : idStrs) {
				ret = isAuthorizedCommand(account, id);
				if (ret)
					break;
			}
		}

		return ret;
	}

	// /**
	// * 增加对操作的处理，20101118
	// *
	// * @param operationName
	// * @return
	// */
	// private static boolean isOperation(String operationName) {
	// return (AuthorizationManager.getOperation(operationName) != null);
	// }

	/**
	 * 判断命令是否被授权，是通过查找该命令在操作-命令映射文件中对应的操作来鉴权的
	 * 
	 * @param command
	 * @return boolean
	 */
	private static boolean isAuthorizedCommand(String account, String command) {
		// 没有配置映射，认为所有命令不需要鉴权
		if (OPERATION_COMMAND_MAPPING == null) {
			return true;
		}
		// 否则在操作-映射的命令中找相应的命令并由此找到操作，用操作是否授权来鉴权
		Set<String> keys = OPERATION_COMMAND_MAPPING.keySet();
		for (Object key : keys) {
			List<String> commands = OPERATION_COMMAND_MAPPING.get(key);
			if (commands.contains(command)) {
				// 找到对应的操作，那操作鉴权
				boolean b = isAuthorizedOperation(account, (String) key);
				return b;
			}
		}
		return true;
	}
}
