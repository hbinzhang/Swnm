package com.dao.videomonitor;

import java.util.List;
import java.util.Map;

import com.entity.videomonitor.TVmIpc;
import com.entity.videomonitor.TVmIpcDTO;
import com.entity.videomonitor.TVmIpcExample;

public interface TVmIpcMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int countByExample(TVmIpcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int deleteByExample(TVmIpcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int deleteByPrimaryKey(String ipcid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int insert(TVmIpc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int insertSelective(TVmIpc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    List<TVmIpc> selectByExample(TVmIpcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    TVmIpc selectByPrimaryKey(String ipcid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int updateByExampleSelective(TVmIpc record, TVmIpcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int updateByExample(TVmIpc record, TVmIpcExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int updateByPrimaryKeySelective(TVmIpc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table T_VM_IPC
     *
     * @mbggenerated Wed Apr 01 00:04:00 CST 2015
     */
    int updateByPrimaryKey(TVmIpc record);
    
    List<TVmIpc> selectByPage(Map<String,Object> cond);
    
    int selectByPageRowCount(Map<String,Object> cond);
    
    Map<String,?> selectByPrimaryKeyEx(TVmIpc ipc);
    
    int updateNvrByIpcIds(Map<String,Object> cond);
    
    int clearIpcByNvrIds(Map<String,Object> cond);
    
    int testIpcExistById(String ipcid);
    
    List<TVmIpc> selectIpcsByIds(String ipcids);
    
    /** 获取NVR可以添加的ipc列表（包括已添加的）
     * @param cond
     * @return
     */
    List<TVmIpc> getAllIpcByOrgIdNvrId(Map<String,Object> cond);
    //add by yangzhu for ArgGIS get IPCs
    List<TVmIpc> getIPCsByOrg(Map<String, String> org);

	List<TVmIpcDTO> getAllIpcByIvaID(String ivaID);
	
	int checkIpcUsedByAudio(String ipcid);
}