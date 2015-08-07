package com.service.sounddev.database;

import java.util.List;

import com.dao.devmgt.sound.ISoundDevDao;
import com.entity.devmgt.sound.AudioAdapter;
import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.IOCtroller;
import com.entity.devmgt.sound.SoundDevManufacturer;

public class SoundDevserviceImpl implements ISoundDevservice {

	private ISoundDevDao soundDevDao = null;

	public ISoundDevDao getSoundDevDao() {
		return soundDevDao;
	}

	public void setSoundDevDao(ISoundDevDao soundDevDao) {
		this.soundDevDao = soundDevDao;
	}

	@Override
	public boolean addDev(Object o) {
		soundDevDao.addDev(o);
		return true;
	}

	@Override
	public boolean batchImport() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchDelete(List<String> idList) {
		soundDevDao.batchDelete(idList);
		return true;
	}

	@Override
	public boolean modify(Object o) {
		soundDevDao.update(o);
		return true;
	}

	@Override
	public List queryAll() {
//		soundDevDao.query(o);
		return null;
	}

	@Override
	public Object query(Object o) {
		return soundDevDao.query(o);
	}
	
	public List queryAllManufacturer(){
		return soundDevDao.queryAllManufacturer();
	}

	@Override
	public List queryAllAudioServer() {
		return soundDevDao.queryAllAudioServer();
	}

	@Override
	public List queryAllAdapterNotAttachedController() {
		return soundDevDao.queryAllAdapterNotAttachedController();
	}

	@Override
	public List queryAllIPCIDNotAttached() {
		return soundDevDao.queryAllIPCIDNotAttached();
	}
	
	@Override
	public Object queryAdapterByIPCId(String id) {
		return soundDevDao.queryAdapterByIPCId(id);
	}

	@Override
	public List queryAllAudioAdapterID() {
		return soundDevDao.queryAllAudioAdapterID();
	}

	@Override
	public void addAdapter(Object o) {
		soundDevDao.addAdapter(o);
	}

	@Override
	public List<String> queryAllIOControllerID() {
		return soundDevDao.queryAllIOControllerID();
	}

	@Override
	public void addIOController(IOCtroller ioCtroller) {
		soundDevDao.addIOController(ioCtroller);
		
	}

	@Override
	public List<String> queryAllServerID() {
		return soundDevDao.queryAllServerID();
	}

	@Override
	public void addAudioServer(AudioServer serverBean) {
		soundDevDao.addAudioServer(serverBean);
	}

	@Override
	public List<String> queryAllmanufacturerID() {
		return soundDevDao.queryAllmanufacturerID();
	}

	@Override
	public void addManufacturer(SoundDevManufacturer manufacturerBean) {
		soundDevDao.addManufacturer(manufacturerBean);
	}

	@Override
	public Object queryById(String id) {
		return soundDevDao.queryById(id);
	}

	@Override
	public Object queryAdapterByIp(String ip) {
		return soundDevDao.queryAdapterByIp(ip);
	}

	@Override
	public Object queryAllIPCIDByMgtId(String id) {
		return soundDevDao.queryAllIPCIDByMgtId(id);
	}

	@Override
	public Object batchDeleteCheck(List<String> ids) {
		return soundDevDao.batchDeleteCheck(ids);
	}

}
