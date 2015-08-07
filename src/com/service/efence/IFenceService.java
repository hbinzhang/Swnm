package com.service.efence;

import java.util.List;

import com.common.page.Page;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.efence.FenceBean;
import com.nsbd.fence.FenceInfo;

public interface IFenceService {
	
	int addFence(FenceBean fi)throws Exception;
	int delFence(String hostID)throws Exception;
	int modFence(FenceBean fi)throws Exception;
	List<Integer> getAllFenceHostID()throws Exception;
	int updateFenceStatus(FenceInfo fenceInfo)throws Exception;
	FenceBean getFenceByID(FenceBean fenceInfo)throws Exception;
	Page<FenceBean> queryFenceByPage(Page<FenceBean> page)throws Exception;
	List<AlarmDTO> findAlarmsByDeviceID(String devID)throws Exception;
	void handleRemoteJmsMsg(String mngIP) throws Exception;
	/**
	 * 
	 * @param fenceBean
	 * @return
	 * @throws Exception
	 * �ܹ�˾�û���
	 * fenceBeanΪnull
	 * �ֹ�˾�û���
	 * fenceBean=new FenceBean();
	 * fenceBean.setSubComID(�ֹ�˾ID)
	 * ���?�û���
	 * fenceBean=new FenceBean();
	 * fenceBean.setMntMentID(���?ID)
	 * 
	 */
	List<FenceBean> getFencesBySubComIdOrMngId(FenceBean fenceBean)throws Exception;
	String findSubComByMngID(FenceBean fi) throws Exception;
	
}
