package com.actions.devmgt.sound.util;import java.util.LinkedList;import java.util.List;import com.entity.devmgt.sound.AudioAdapter;import com.entity.devmgt.sound.IOCtroller;import com.entity.devmgt.sound.AudioServer;import com.entity.devmgt.sound.SoundDevManufacturer;import com.entity.zone.PositionZoneMapBean;/** * 表格实体到java bean转换 *  * @author maming 2015-4-5下午1:59:05 *  */public class ExcelObjectConvertUtil {	/**	 * 取得音频IO控制器beans	 * 	 * @param controllersList	 * @return	 * @throws Exception	 */	public static List<IOCtroller> getIOControllers(			List<List<Object>> controllersList) throws Exception {		List<IOCtroller> audioAdapterList = new LinkedList<IOCtroller>();		for (List<Object> obj : controllersList) {			List linked = (LinkedList) obj;			IOCtroller iocontrollerBean = new IOCtroller();			if (linked.get(0) != null) {				iocontrollerBean.setID(((String) linked.get(0)).trim());			}			if (linked.get(1) != null) {				iocontrollerBean.setAUDIOID(((String) linked.get(1)).trim());			}			if (linked.get(2) != null) {				iocontrollerBean.setVENDORID(((String) linked.get(2)).trim());			}			if (linked.get(3) != null) {				iocontrollerBean.setNAME(((String) linked.get(3)).trim());			}			if (linked.get(4) != null) {				iocontrollerBean.setIP(((String) linked.get(4)).trim());			}			if (linked.get(5) != null) {				iocontrollerBean.setMGTCODE(((String) linked.get(5)).trim());			}			audioAdapterList.add(iocontrollerBean);		}		return audioAdapterList;	}	/**	 * 取得音频终端beans	 * 	 * @param soundTerminalList	 * @return	 * @throws Exception	 */	public static List<AudioAdapter> getSoundTerminals(	List<List<Object>> soundTerminalList) throws Exception {		List<AudioAdapter> audioAdapterList = new LinkedList<AudioAdapter>();		for (List<Object> obj : soundTerminalList) {			List linked = (LinkedList) obj;			AudioAdapter audioAdapterBean = new AudioAdapter();			if (linked.get(0) != null) {				audioAdapterBean.setAudioId(((String) linked.get(0)).trim());			}			if (linked.get(1) != null) {				audioAdapterBean.setVendorId(((String) linked.get(1)).trim());			}			if (linked.get(2) != null) {				audioAdapterBean.setAudioName(((String) linked.get(2)).trim());			}			if (linked.get(3) != null) {				audioAdapterBean.setAudioIp(((String) linked.get(3)).trim());			}			if (linked.get(4) != null) {				audioAdapterBean.setMgtCode(((String) linked.get(4)).trim());			}			if (linked.get(5) != null) {				audioAdapterBean.setOwner(((String) linked.get(5)).trim());			}			if (linked.get(6) != null) {				audioAdapterBean.setIpcCode(((String) linked.get(6)).trim());			}			audioAdapterList.add(audioAdapterBean);		}		return audioAdapterList;	}	/**	 * 取得音频服务器beans	 * 	 * @param soundServerlList	 * @return	 * @throws Exception	 */	public static List<AudioServer> getAudioServers(			List<List<Object>> soundServerlList) throws Exception {		List<AudioServer> audioAdapterList = new LinkedList<AudioServer>();		for (List<Object> obj : soundServerlList) {			List linked = (LinkedList) obj;			AudioServer audioServerBean = new AudioServer();			if (linked.get(0) != null) {				audioServerBean.setSERVERID(((String) linked.get(0)).trim());			}			if (linked.get(1) != null) {				audioServerBean.setSERVERNAME(((String) linked.get(1)).trim());			}			if (linked.get(2) != null) {				audioServerBean.setIPADDRESS(((String) linked.get(2)).trim());			}			if (linked.get(3) != null) {				audioServerBean.setFACTORYNAME(((String) linked.get(3)).trim());			}			audioAdapterList.add(audioServerBean);		}		return audioAdapterList;	}	/**	 * 取得音频厂商beans	 * 	 * @param soundServerlList	 * @return	 * @throws Exception	 */	public static List<SoundDevManufacturer> getManufacturers(	List<List<Object>> soundServerlList) throws Exception {		List<SoundDevManufacturer> manufacturerList = new LinkedList<SoundDevManufacturer>();		for (List<Object> obj : soundServerlList) {			List linked = (LinkedList) obj;			SoundDevManufacturer manufacturerBean = new SoundDevManufacturer();			if (linked.get(0) != null) {				manufacturerBean.setVendorId(((String) linked.get(0)).trim());			}			if (linked.get(1) != null) {				manufacturerBean.setVendorName(((String) linked.get(1)).trim());			}			manufacturerList.add(manufacturerBean);		}		return manufacturerList;	}	public static AudioAdapter getAudioAdapter(List<Object> linked) {		AudioAdapter audioAdapterBean = new AudioAdapter();		if (linked.get(0) != null) {			audioAdapterBean.setAudioId(((String) linked.get(0)).trim());		}		if (linked.get(1) != null) {			audioAdapterBean.setVendorId(((String) linked.get(1)).trim());		}		if (linked.get(2) != null) {			audioAdapterBean.setAudioName(((String) linked.get(2)).trim());		}		if (linked.get(3) != null) {			audioAdapterBean.setAudioIp(((String) linked.get(3)).trim());		}		if (linked.get(4) != null) {			audioAdapterBean.setMgtCode(((String) linked.get(4)).trim());		}		if (linked.get(5) != null) {			audioAdapterBean.setOwner(((String) linked.get(5)).trim());		}		if (linked.get(6) != null) {			audioAdapterBean.setIpcCode(((String) linked.get(6)).trim());		}		return audioAdapterBean;	}	public static IOCtroller getIOCtroller(List<Object> linked) {		IOCtroller iocontrollerBean = new IOCtroller();		if (linked.get(0) != null) {			iocontrollerBean.setID(((String) linked.get(0)).trim());		}		if (linked.get(1) != null) {			iocontrollerBean.setAUDIOID(((String) linked.get(1)).trim());		}		if (linked.get(2) != null) {			iocontrollerBean.setVENDORID(((String) linked.get(2)).trim());		}		if (linked.get(3) != null) {			iocontrollerBean.setNAME(((String) linked.get(3)).trim());		}		if (linked.get(4) != null) {			iocontrollerBean.setIP(((String) linked.get(4)).trim());		}		if (linked.get(5) != null) {			iocontrollerBean.setMGTCODE(((String) linked.get(5)).trim());		}		return iocontrollerBean;	}	public static AudioServer getAudioServer(List<Object> linked) {		AudioServer audioServerBean = new AudioServer();		if (linked.get(0) != null) {			audioServerBean.setSERVERID(((String) linked.get(0)).trim());		}		if (linked.get(1) != null) {			audioServerBean.setSERVERNAME(((String) linked.get(1)).trim());		}		if (linked.get(2) != null) {			audioServerBean.setIPADDRESS(((String) linked.get(2)).trim());		}		if (linked.get(3) != null) {			audioServerBean.setFACTORYNAME(((String) linked.get(3)).trim());		}		return audioServerBean;	}	public static SoundDevManufacturer getManufacturer(List<Object> linked) {		SoundDevManufacturer manufacturerBean = new SoundDevManufacturer();		if (linked.get(0) != null) {			manufacturerBean.setVendorId(((String) linked.get(0)).trim());		}		if (linked.get(1) != null) {			manufacturerBean.setVendorName(((String) linked.get(1)).trim());		}				return manufacturerBean;	}}