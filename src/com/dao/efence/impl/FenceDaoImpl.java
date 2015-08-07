package com.dao.efence.impl;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.common.page.Page;
import com.dao.efence.IFenceDao;
import com.entity.alarmmgt.AlarmDTO;
import com.entity.efence.FenceBean;
import com.nsbd.fence.FenceInfo;
@SuppressWarnings("unchecked")
public class FenceDaoImpl implements IFenceDao {
	
	private SqlMapClientTemplate sqlmapclienttemplate=null;

	public void setSqlmapclienttemplate(SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public int addFence(FenceBean fi) throws Exception{
			
		sqlmapclienttemplate.insert("insertFence", fi);
		
		return 0;
	}

	public int delFence(String hostID) throws Exception{
		
		sqlmapclienttemplate.delete("deleteFence", hostID);
		
		return 0;
	}

	public int modFence(FenceBean fi) throws Exception{
		sqlmapclienttemplate.update("modFence", fi);
		return 0;
	}

	public List<Integer> getAllFenceHostID() throws Exception {
		
		return (List<Integer>)sqlmapclienttemplate.queryForList("getAllHostID");
	}

	public void updateFenceStatus(FenceInfo fenceInfo)
			throws Exception {
		sqlmapclienttemplate.update("updateFenceStatus", fenceInfo);
	}

	public FenceBean getFenceByHostID(String hostID)throws Exception{
		
		return (FenceBean)sqlmapclienttemplate.queryForObject("getFenceByHostID", hostID);
	}

	public Page<FenceBean> queryFenceByPage(Page<FenceBean> page) throws Exception {
		
		List<FenceBean> fences = (List<FenceBean>)sqlmapclienttemplate.queryForList("queryFenceByPage",page);
		int totalCount = (Integer)sqlmapclienttemplate.queryForObject("findFenceByCount",page);
		
		Page<FenceBean> p = new Page<FenceBean>(page.getOffset(), page.getSize(), (long)totalCount);
		p.setDatas(fences);
		
		return p;
	}
public List<AlarmDTO> findAlarmsByDeviceID(String devID) throws Exception {
		
		return (List<AlarmDTO>)sqlmapclienttemplate.queryForList("findAlarmsByDeviceID", devID);
		
	}
	
	/**
	 * 查询围栏通过分公司ID和管理处ID
	 */
	@Override
	public List<FenceBean> getFencesBySubComIdOrMngId(FenceBean fenceBean)
			throws Exception {
		
		return (List<FenceBean>)sqlmapclienttemplate.queryForList("getFencesBySubComIdOrMngId", fenceBean);
		
	}
	/**
	 * 通过管理处ID查询分公司ID
	 */
	@Override
	public String findSubComByMngID(FenceBean fi) throws Exception {
		return (String)sqlmapclienttemplate.queryForObject("findSubComByMngID",fi);
	}
 
	@Override
	public void updateFenceStatusByMngID(Map paramMap) throws Exception {
		
		sqlmapclienttemplate.update("updateFenceStatusByMngID", paramMap);
		
	}

}
