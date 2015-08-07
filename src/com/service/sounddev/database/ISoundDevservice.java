package com.service.sounddev.database;

import java.util.List;

import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.IOCtroller;
import com.entity.devmgt.sound.SoundDevManufacturer;

/**
 * 音频设备数据库操作服务接口
 * 
 * @author maming 2015-3-24上午9:57:01
 * 
 */
public interface ISoundDevservice {

	/**
	 * 增加一个音频设备
	 * 
	 * @return
	 */
	public boolean addDev(Object o);

	/**
	 * 批量导入音频设备
	 * 
	 * @return
	 */
	public boolean batchImport();

	/**
	 * 删除一个音频设备
	 * 
	 * @param id
	 * @return
	 */
	public boolean delete(String id);

	/**
	 * 批量删除音频设备
	 * 
	 * @return
	 */
	public boolean batchDelete(List<String> idList);

	/**
	 * 修改一个音频设备
	 * 
	 * @return
	 */
	public boolean modify(Object o);

	/**
	 * 查询所有音频设备
	 * 
	 * @return
	 */
	public List queryAll();

	/**
	 * 按条件查询音频设备
	 * 
	 * @return
	 */
	public Object query(Object o);
	
	/**
	 * 查询所有音频厂商
	 * @return
	 */
	public List queryAllManufacturer();
	
	/**
	 * 查询所有音频服务器
	 * @return
	 */
	public List queryAllAudioServer();
	
	/**
	 * 所有未关联IO控制器的音频终端查询
	 * @return
	 */
	public List queryAllAdapterNotAttachedController();
	
	
	/**
	 * 所有未关联音频终端的IPC的ID
	 * @return
	 */
	public List queryAllIPCIDNotAttached();
	
	/**
	 * 根据IPCID查询音频终端
	 * @param id
	 * @return
	 */
	public Object queryAdapterByIPCId(String id);
	
	
	/**
	 * 获取所有音频终端ID
	 * @return
	 */
	public List queryAllAudioAdapterID();
	
	
	/**
	 * 添加一个音频终端
	 * @return
	 */
	public void addAdapter(Object o);

	public List<String> queryAllIOControllerID();

	public void addIOController(IOCtroller ioCtroller);

	public List<String> queryAllServerID();

	public void addAudioServer(AudioServer serverBean);

	public List<String> queryAllmanufacturerID();

	public void addManufacturer(SoundDevManufacturer manufacturerBean);
	
	public Object queryById(String id);
	
	public Object queryAdapterByIp(String ip);
	
	public Object queryAllIPCIDByMgtId(String id);
	
	public Object batchDeleteCheck(List<String> ids);

}
