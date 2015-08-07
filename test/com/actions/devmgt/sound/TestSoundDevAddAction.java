package com.actions.devmgt.sound;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Test; 
import org.junit.runner.RunWith; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.test.context.ContextConfiguration; 
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional; 

import com.entity.devmgt.sound.SoundDev;
import com.entity.devmgt.sound.SoundDevManufacturer;
import com.entity.devmgt.sound.SoundDevQueryCondition;

import common.page.AjaxObject;

/**
 * @author maming
 * 2015-5-14下午2:23:43
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={
		   "file:test/sound-config/Context-db.xml",
		   "file:test/sound-config/Context-action.xml",
		   "file:test/sound-config/Context-service.xml",
		   "file:test/sound-config/Context-dao.xml"})
public class TestSoundDevAddAction {
	
	@Autowired
	private SoundDevAddAction sounddevaddaction;

	@Test
	public void testSetQueryCondition() {
		SoundDevQueryCondition condition  = new SoundDevQueryCondition();
		condition.setId("12222");
		condition.setBrench("23232");
		sounddevaddaction.setQueryCondition(condition);
		assertEquals(condition, sounddevaddaction.getQueryCondition());
	}

	@Test
	public void testGetQueryCondition() {
		SoundDevQueryCondition condition  = new SoundDevQueryCondition();
		condition.setId("12222");
		condition.setBrench("23232");
		sounddevaddaction.setQueryCondition(condition);
		assertEquals(condition, sounddevaddaction.getQueryCondition());
	}

	@Test
	public void testGetConditionString() {
		String testString = "adsfsf-sdfsd,sdfsdf,sfsd";
		sounddevaddaction.setConditionString(testString);
		assertEquals(sounddevaddaction.getConditionString(), testString);
	}

	@Test
	public void testSetConditionString() {
		String testString = "adsfsf-sdfsd,sdfsdf,sfsd";
		sounddevaddaction.setConditionString(testString);
		assertEquals(sounddevaddaction.getConditionString(), testString);
	}

	@Test
	public void testGetAjaxObject() {
		AjaxObject object = new AjaxObject();
		sounddevaddaction.setAjaxObject(object);
		assertEquals(sounddevaddaction.getAjaxObject(),object);
	}

	@Test
	public void testSetAjaxObject() {
		AjaxObject object = new AjaxObject();
		sounddevaddaction.setAjaxObject(object);
		assertEquals(sounddevaddaction.getAjaxObject(),object);
	}

	@Test
	public void testGetManufacturer() {
		java.util.ArrayList<SoundDevManufacturer> manList = new java.util.ArrayList<SoundDevManufacturer>();
		SoundDevManufacturer facturers = new SoundDevManufacturer();
		manList.add(facturers);
		sounddevaddaction.setManufacturer(manList);
		assertEquals(sounddevaddaction.getManufacturer(), manList);
	}

	@Test
	public void testSetManufacturer() {
		java.util.ArrayList<SoundDevManufacturer> manList = new java.util.ArrayList<SoundDevManufacturer>();
		SoundDevManufacturer facturers = new SoundDevManufacturer();
		manList.add(facturers);
		sounddevaddaction.setManufacturer(manList);
		assertEquals(sounddevaddaction.getManufacturer(), manList);
	}

	@Test
	public void testGetOperationLogService() {
		assertNotNull(sounddevaddaction.getOperationLogService());
	}

	@Test
	public void testSetOperationLogService() {
		assertNotNull(sounddevaddaction.getOperationLogService());
	}

	@Test
	public void testGetSoundDevService() {
		assertNotNull(sounddevaddaction.getSoundDevService());
	}

	@Test
	public void testSetSoundDevService() {
		assertNotNull(sounddevaddaction.getSoundDevService());
	}

	@Test
	public void testExecute() {
		SoundDev dev = new SoundDev();
		dev.devType = "音频终端";
		dev.ipAddress = "10.34.23.44";
		dev.mgtCode = "12445";
		dev.name = "河北音频终端";
		dev.ownerdev = "s007";
		dev.ownerIp = "10.23.44.55";
		dev.id = "12wers";
		sounddevaddaction.setToAddSoundDev(dev);
		assertEquals("success",sounddevaddaction.execute());

	}
	@Test
	public void testExecute2() {
		SoundDev dev = new SoundDev();
		dev.devType = "音频终端";
		dev.ipAddress = "10.34.23.44";
		dev.mgtCode = "12445";
		dev.name = "河北音频终端";
		dev.ownerdev = "s007";
		dev.ownerIp = "10.23.44.55";
		dev.id = "a33251";
		sounddevaddaction.setToAddSoundDev(dev);
		assertEquals("success",sounddevaddaction.execute());
	}
	
	@Test
	public void testExecute3() {
		sounddevaddaction.setToAddSoundDev(null);
		assertEquals("error",sounddevaddaction.execute());
	}
	
	@Test
	public void testExecuteRepeat(){
		List adapterIds = sounddevaddaction.getSoundDevService().queryAllAudioAdapterID();
		SoundDev dev = new SoundDev();
		dev.devType = "音频终端";
		dev.ipAddress = "10.34.23.44";
		dev.mgtCode = "12445";
		dev.name = "河北音频终端";
		dev.ownerdev = "s007";
		dev.ownerIp = "10.23.44.55";
		dev.id = (String)adapterIds.get(0);
		sounddevaddaction.setToAddSoundDev(dev);
		assertEquals("success",sounddevaddaction.execute());
	}

	

	@Test
	public void testGetToAddSoundDev() {
		SoundDev dev = new SoundDev();
		dev.id="qwerui";
		dev.devType = "音频终端";
		sounddevaddaction.setToAddSoundDev(dev);
		assertEquals(sounddevaddaction.getToAddSoundDev(),dev);
		
	}

	@Test
	public void testSetToAddSoundDev() {
		SoundDev dev = new SoundDev();
		dev.id="qwerui";
		dev.devType = "音频终端";
		sounddevaddaction.setToAddSoundDev(dev);
		assertEquals(sounddevaddaction.getToAddSoundDev(),dev);
	}

	@Test
	public void testEnter() {
		assertEquals("success",sounddevaddaction.enter());
	}

}
