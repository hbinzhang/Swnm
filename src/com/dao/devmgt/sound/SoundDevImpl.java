package com.dao.devmgt.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.entity.common.ExcelConfigInfo;
import com.entity.devmgt.sound.AudioAdapter;
import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.IOCtroller;
import com.entity.devmgt.sound.SoundDev;
import com.entity.devmgt.sound.SoundDevManufacturer;
import com.entity.devmgt.sound.SoundDevQueryCondition;
import com.entity.efence.FenceBean;
import com.util.fence.ExcelUtil;
import com.util.fence.FenceConvertUtil;

import common.page.Pager;

/**
 * 音频设备DAO
 * 
 * @author maming 2015-3-26下午7:43:10
 * 
 */
public class SoundDevImpl implements ISoundDevDao {
	private SqlMapClientTemplate sqlmapclienttemplate = null;
	
	private static final int SIZEOFONEPAGE = 10;

	private static final String IOCONTROLLER = "IO控制器";
	private static final String AUDIOADAPTER = "音频终端";
	private static final String AUDIOSERVER = "音频服务器";
	private static final String insertIoController = "insertIoController";
	private static final String insertAudioAdapter = "insertAudioAdapter";
	private static final String insertAudioServer = "insertAudioServer";
	private static final String updateIoController = "updateIoController";
	private static final String updateAudioAdapter = "updateAudioAdapter";
	private static final String updateAudioServer = "updateAudioServer";
	private static final Map<String, String> insertmethodMap = new HashMap<String, String>() {

		private static final long serialVersionUID = -7239350556024366531L;

		{
			put(IOCONTROLLER, insertIoController);
			put(AUDIOADAPTER, insertAudioAdapter);
			put(AUDIOSERVER, insertAudioServer);
		}
	};
	private static final Map<String, String> updatemethodMap = new HashMap<String, String>() {

		private static final long serialVersionUID = -7784853671141348854L;

		{
			put(IOCONTROLLER, updateIoController);
			put(AUDIOADAPTER, updateAudioAdapter);
			put(AUDIOSERVER, updateAudioServer);
		}
	};
	
	private Pager<SoundDev> page = new Pager<SoundDev>();

	private static final String deleteDev1 = "delete4";
	private static final String deleteDev2 = "delete5";
	private static final String deleteDev3 = "delete6";
	
	public SoundDevImpl(){
		page.setSize(SIZEOFONEPAGE);
	}

	public SqlMapClientTemplate getSqlmapclienttemplate() {
		return sqlmapclienttemplate;
	}

	public void setSqlmapclienttemplate(
			SqlMapClientTemplate sqlmapclienttemplate) {
		this.sqlmapclienttemplate = sqlmapclienttemplate;
	}

	@Override
	public boolean addDev(Object o) {
		sqlmapclienttemplate.insert(
				insertmethodMap.get(((SoundDev) o).devType), ((SoundDev) o));
		return true;
	}

	@Override
	public boolean batchImport(String fileName) {
		importData(fileName);
		return true;
	}

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public boolean batchDelete(List<String> idList) {
		try {
			for (String string : idList) {
				sqlmapclienttemplate.delete(deleteDev1, string);
				sqlmapclienttemplate.delete(deleteDev2, string);
				sqlmapclienttemplate.delete(deleteDev3, string);		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean update(Object o) {
		String keyString = updatemethodMap.get(((SoundDev) o).devType);
		sqlmapclienttemplate.update(keyString, ((SoundDev) o));
		return true;
	}

	@Override
	public List queryAll() {
		return null;
	}

	@Override
	public Pager query(Object o) {
		List<SoundDev> retValue = null;
		//总条数
		int count = (Integer)sqlmapclienttemplate.queryForObject("queryCount",o);
		page.setTotal(count);
		//查询当前页
		retValue = sqlmapclienttemplate.queryForList("query",
				((SoundDevQueryCondition) o));
		page.setDatas(retValue);
		return page;
	}

	@Override
	public List<AudioAdapter> queryAllAudioAdapter(Object o) {
		List<AudioAdapter> retValue = null;
		retValue = sqlmapclienttemplate.queryForList("queryAllAudioAdapter",
				((ArrayList) o));
		return retValue;
	}

	@Override
	public List queryAllAudioServer() {
		List<AudioServer> retValue = null;
		retValue = sqlmapclienttemplate.queryForList("queryAllAudioServer");
		return retValue;
	}

	@Override
	public List queryAllManufacturer() {
		List<SoundDevManufacturer> retValue = null;
		retValue = sqlmapclienttemplate.queryForList("queryAllManufacturer");
		return retValue;
	}

	public IOCtroller queryControllerByTermId(String termId) {
		IOCtroller retValue = (IOCtroller) sqlmapclienttemplate.queryForObject(
				"queryControllerByTermId", termId);
		return retValue;

	}

	@Override
	public AudioAdapter queryAdapterByTermId(String id) {
		AudioAdapter retValue = (AudioAdapter) sqlmapclienttemplate
				.queryForObject("queryAdapterByTermId", id);
		return retValue;
	}

	@Override
	public AudioServer queryServerIpByTermIp(String termIp) {
		AudioServer retValue = (AudioServer) sqlmapclienttemplate
				.queryForObject("queryServerByTermIp", termIp);
		return retValue;
	}

	private void importData(String fName) {


	}

	/**
	 * 验证导入内容
	 * 
	 * @param workbook
	 * @param moType
	 * @param logVer
	 * @param isImport
	 * @return
	 */
	private List<String> validateImportContent(Workbook workbook) {
//		if (workbook == null || workbook.getSheets() == null
//				|| workbook.getSheets().length == 0
//				|| workbook.getSheets()[0].getRows() <= 1) {
//			 String ret = "导入文件为空!";
//			return null;
//		}
//
//		Sheet[] sheets = workbook.getSheets();
//		StringBuffer errMsg = new StringBuffer();
//		try {
//			for (Sheet sheet : sheets) {
//				for (int i = 0; i < sheet.getRows(); i++) {
//					Cell[] aRowtemp = sheet.getRow(i);
//					ArrayList<Cell> rowList = new ArrayList<Cell>(
//							aRowtemp.length);
//
//					for (int t = 0; t < aRowtemp.length; t++) {
//						rowList.add(aRowtemp[t]);
//					}
//					rowList.remove(0);
//
//					Cell[] aRow = new Cell[rowList.size() - 1];
//					for (int t = 0; t < rowList.size() - 1; t++) {
//						aRow[t] = rowList.get(t);
//					}
//				}
//			}
//		} catch (Exception ex) {
//			
//			return null;
//		}
//
//		if (errMsg.toString().length() == 0) {
//			return mos;
//		} else {
//			
//			return null;
//		}
		return null;

	}

	@Override
	public List queryAllAdapterNotAttachedController() {
		List<AudioAdapter> retValue = null;
		retValue = sqlmapclienttemplate.queryForList("queryAllAdapterNotAttachedController");
		return retValue;
	}

	@Override
	public List queryAllIPCIDNotAttached() {
		List<String> retValue = null;
		retValue =  (List<String>)sqlmapclienttemplate.queryForList("SounddevQueryAllIPCIDNotAttached");
		return retValue;
	}

	@Override
	public Object queryAdapterByIPCId(String id) {
		AudioAdapter retValue = null;
		retValue =  (AudioAdapter)sqlmapclienttemplate.queryForObject("queryAdapterByIPCId",id);
		return retValue;
	}

	@Override
	public List queryAllAudioAdapterID() {
		List<String> retValue = null;
		retValue =  (List<String>)sqlmapclienttemplate.queryForList("queryAllAudioAdapterId");
		return retValue;
	}

	@Override
	public void addManufacturer(SoundDevManufacturer manufacturerBean) {
		sqlmapclienttemplate.insert("addManufacturerByImport",manufacturerBean);	
	}

	@Override
	public List<String> queryAllmanufacturerID() {
		List<String> retValue = null;
		retValue =  (List<String>)sqlmapclienttemplate.queryForList("queryAllmanufacturerId");
		return retValue;
	}

	@Override
	public void addAudioServer(AudioServer serverBean) {
		sqlmapclienttemplate.insert("addAudioServerByImport",serverBean);			
	}

	@Override
	public List<String> queryAllServerID() {
		List<String> retValue = null;
		retValue =  (List<String>)sqlmapclienttemplate.queryForList("queryAllServerId");
		return retValue;
	}

	@Override
	public void addIOController(IOCtroller ioCtroller) {
		sqlmapclienttemplate.insert("addIOControllerByImport",ioCtroller);		
	}

	@Override
	public List<String> queryAllIOControllerID() {
		List<String> retValue = null;
		retValue =  (List<String>)sqlmapclienttemplate.queryForList("queryAllIOControllerId");
		return retValue;
	}

	@Override
	public void addAdapter(Object o) {
		sqlmapclienttemplate.insert("addAdapterByImport",(AudioAdapter)o);		
	}

	@Override
	public Object queryById(String id) {
		SoundDev retValue = null;
		retValue =  (SoundDev)sqlmapclienttemplate.queryForObject("queryById",id);
		return retValue;
	}

	@Override
	public Object queryAdapterByIp(String ip) {
		List<AudioAdapter> retValue = null;
		retValue =  (List<AudioAdapter>)sqlmapclienttemplate.queryForList("queryAdapterByIP",ip);
		return retValue;
	}

	@Override
	public Object queryAllIPCIDByMgtId(String id) {
		List<String> retValue = null;
		retValue =  (List<String>)sqlmapclienttemplate.queryForList("queryAllIPCIDByMgtId",id);
		return retValue;
	}

	@Override
	public Object batchDeleteCheck(List<String> ids) {
		int retValue = -1;
		try {
			retValue =  (Integer)sqlmapclienttemplate.queryForObject("batchdelectCheck",ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retValue;
	}
}
