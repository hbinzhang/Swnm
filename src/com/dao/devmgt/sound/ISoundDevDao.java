package com.dao.devmgt.sound;

import java.util.List;

import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.IOCtroller;
import com.entity.devmgt.sound.AudioAdapter;
import com.entity.devmgt.sound.SoundDevManufacturer;

/**
 * 音频设备DAO接口
 * @author maming
 * 2015-3-24下午1:19:34
 *
 */
/**
 * @author maming
 * 2015-3-28下午6:33:38
 *
 */
public interface ISoundDevDao {
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
	public boolean batchImport(String fileName);

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
	public boolean update(Object o);

	/**
	 * 查询所有音频设备
	 * 
	 * @return
	 */
	public List queryAll();

	/**
	 * 根据条件查询音频设备
	 * 
	 * @return
	 */
	public Object query(Object o);
	

	/**
	 * 查询当前用户能看到的所有音频终端
	 * @param o 管理处ID列表
	 * @return
	 */
	public List queryAllAudioAdapter(Object o);
	
	/**
	 * 查询所有音频服务器
	 * @return
	 */
	public List queryAllAudioServer();
	
	
	/**
	 * 查询音频模块所有厂商信息
	 * @return
	 */
	public List queryAllManufacturer();
	
	/**
	 * 根据终端ID查询IO控制器
	 * @param o
	 * @return
	 */
	public IOCtroller queryControllerByTermId(String id);
	
	/**
	 * 根据终端ID查询终端
	 * @param id
	 * @return
	 */
	public AudioAdapter queryAdapterByTermId(String id);
	
	
	/**
	 * 根据终端IP查询服务器
	 * @param termIp
	 * @return
	 */
	public AudioServer queryServerIpByTermIp(String termIp);
	
	
	
	/**
	 * 所有未关联IO控制器的音频终端查询
	 */
	public List queryAllAdapterNotAttachedController();
	
	
	/**
	 * 所有未被关联的摄像头ID查询
	 */
	public List queryAllIPCIDNotAttached();
	
	
	/**
	 * 根据摄像头ID查询终端
	 * @param id
	 * @return
	 */
	public Object queryAdapterByIPCId(String id);
	
	
	/**
	 * 获取所有音频终端ID
	 * @return
	 */
	public List queryAllAudioAdapterID();

	public void addManufacturer(SoundDevManufacturer manufacturerBean);

	public List<String> queryAllmanufacturerID();

	public void addAudioServer(AudioServer serverBean);

	public List<String> queryAllServerID();

	public void addIOController(IOCtroller ioCtroller);

	public List<String> queryAllIOControllerID();

	public void addAdapter(Object o);
	
	public Object queryById(String id);
	
	public Object queryAdapterByIp(String ip);
	
	public Object  queryAllIPCIDByMgtId(String id);

	public Object batchDeleteCheck(List<String> ids);
}
