package com.dao.mgtserver;

import java.util.List;

import com.entity.mgtserver.ServerBean;

public interface IServerDao {
	
	String getMgtIPByMgtID(String mgtID)throws Exception;
	List<ServerBean> getAllServer()throws Exception;
	String getMgtIdByMgtIP(String mngIP)throws Exception;
	String hasServerByMgtID(String mngId)throws Exception;

}
