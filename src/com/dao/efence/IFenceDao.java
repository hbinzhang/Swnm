package com.dao.efence;

import java.util.List;
import java.util.Map;

import com.common.page.Page;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.efence.FenceBean;
import com.nsbd.fence.FenceInfo;

public interface IFenceDao {
	
	int addFence(FenceBean fi)throws Exception;
	int delFence(String hostID)throws Exception;
	int modFence(FenceBean fi)throws Exception;
	List<Integer> getAllFenceHostID()throws Exception;
	void updateFenceStatus(FenceInfo fenceInfo) throws Exception;
	FenceBean getFenceByHostID(String hostID)throws Exception;
	Page<FenceBean> queryFenceByPage(Page<FenceBean> page)throws Exception;
	List<AlarmDTO> findAlarmsByDeviceID(String devID)throws Exception;
	List<FenceBean> getFencesBySubComIdOrMngId(FenceBean fenceBean)throws Exception;
	String findSubComByMngID(FenceBean fi)throws Exception;
	void updateFenceStatusByMngID(Map paramMap)throws Exception;
	
}
