package com.entity.efence;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Range;
import net.sf.oval.constraint.ValidateWithMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dao.common.IManufacturerDao;
import com.dao.common.impl.ManufacturerDaoImpl;
import com.dao.efence.IFenceDao;
import com.dao.efence.impl.FenceDaoImpl;
import com.dao.mgtserver.IServerDao;
import com.dao.mgtserver.impl.ServerDaoImpl;
import com.entity.Manufacturer;

public class FenceBean implements Serializable {
	
	private static final long serialVersionUID = 2629496579882646354L;
	@NotNull(message = "围栏ID不能为空")
	@NotEmpty(message = "围栏ID不能为空")
	@ValidateWithMethod(methodName = "isRepeat", parameterType = String.class,message ="该围栏主机ID已存在")
	private String hostID;
	@MatchPattern(pattern = "^(EC)[a-zA-Z0-9]+$",message ="该厂商不属于电子围栏厂商")
	@ValidateWithMethod(methodName = "isVendorIdExsit", parameterType = String.class,message ="该厂商不存在")
	private String vendorID;
	private String vendorName;
	@NotNull(message = "围栏类型不能为空")
	@NotEmpty(message = "围栏类型不能为空")
	@Range(min=1,max=4,message = "围栏类型只能是1到4之间的数字")
	private Integer fenceType;
	private String fenceName;
	@NotNull(message = "IP不能为空")
	@NotEmpty(message = "IP不能为空")
	@MatchPattern(pattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$", message = "IP格式错误")
	private String ip;
	@NotNull(message = "Port不能为空")
	@NotEmpty(message = "Port不能为空")
	@MatchPattern(pattern = "^\\d+$",message ="Port只能为整数")
	private Integer port;
	private Integer fenceStatus=3;
	private String installTime;
	@NotNull(message = "管理处ID不能为空")
	@NotEmpty(message = "管理处ID不能为空")
	@ValidateWithMethod(methodName = "isMngExsit", parameterType = String.class,message ="管理处不存在或者管理处未配置服务器")
	private String mntMentID;
	@NotNull(message = "分公司ID不能为空")
	@NotEmpty(message = "分公司ID不能为空")
	@ValidateWithMethod(methodName = "isMatchMng", parameterType = String.class,message ="分公司与管理处不一致")
	private String subComID;
	private String mntMentName;
	private String subComName;
	private String hardwareVer;
	private String sorfwareVer;
	private String remarks;
	@NotNull(message = "经度不能为空")
	@NotEmpty(message = "经度不能为空")
	@MatchPattern(pattern = "^[0-9]+\\.{0,1}[0-9]{0,4}$",message ="经度只能为数字")
	private String efLongitude;
	@NotNull(message = "纬度不能为空")
	@NotEmpty(message = "纬度不能为空")
	@MatchPattern(pattern = "^[0-9]+\\.{0,1}[0-9]{0,4}$",message ="纬度只能为数字")
	private String efLatitude;
	private Integer iconID;
	private String devID;
	
	private Integer filter=1;
	
	private String searchUri;
	
	public void init(){
		if(this.filter==null||this.filter==1)
		{
			this.fenceType = null;
		}
		else
		{
			this.hostID = "";
		}
	}
	
	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}
	
	public String getSearchUri() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("fenceCondition.subComID=").append(this.subComID==null?"":this.subComID).append("&");
		buffer.append("fenceCondition.mntMentID=").append(this.mntMentID==null?"":this.mntMentID).append("&");
		buffer.append("fenceCondition.filter=").append(this.filter==null?1:this.filter).append("&");
		if(this.filter==null||this.filter==1)
		{
			buffer.append("fenceCondition.hostID=").append(this.hostID==null?"":this.hostID).append("&");
		}
		else
		{
			buffer.append("fenceCondition.fenceType=").append(this.fenceType==null?"":this.fenceType);
		}
		this.searchUri = buffer.toString();
		return searchUri;
	}
	
	public FenceBean(){}

	public FenceBean(String hostID, String vendorID, Integer fenceType, String fenceName,
			String ip, Integer port, Integer fenceStatus, String installTime,
			String mntMentID, String subComID, String hardwareVer,
			String sorfwareVer, String remarks, Integer iconID, String devID) {
		this.hostID = hostID;
		this.vendorID = vendorID;
		this.fenceType = fenceType;
		this.fenceName = fenceName;
		this.ip = ip;
		this.port = port;
		this.fenceStatus = fenceStatus;
		this.installTime = installTime;
		this.mntMentID = mntMentID;
		this.subComID = subComID;
		this.hardwareVer = hardwareVer;
		this.sorfwareVer = sorfwareVer;
		this.remarks = remarks;
		this.iconID = iconID;
		this.devID = devID;
	}

	public String getHostID() {
		return hostID;
	}

	public void setHostID(String hostID) {
		this.hostID = hostID;
	}

	public String getVendorID() {
		return vendorID;
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	public Integer getFenceType() {
		return fenceType;
	}

	public void setFenceType(Integer fenceType) {
		this.fenceType = fenceType;
	}

	public String getFenceName() {
		return fenceName;
	}

	public void setFenceName(String fenceName) {
		this.fenceName = fenceName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getFenceStatus() {
		return fenceStatus;
	}

	public void setFenceStatus(Integer fenceStatus) {
		
		if(fenceStatus==null||fenceStatus==0){
			this.fenceStatus=3;
		}else{
			
			this.fenceStatus = fenceStatus;
		}
	}

	public String getInstallTime() {
		return installTime;
	}

	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}

	public String getMntMentID() {
		return mntMentID;
	}

	public void setMntMentID(String mntMentID) {
		this.mntMentID = mntMentID;
	}

	public String getSubComID() {
		return subComID;
	}

	public void setSubComID(String subComID) {
		this.subComID = subComID;
	}

	public String getHardwareVer() {
		return hardwareVer;
	}

	public void setHardwareVer(String hardwareVer) {
		this.hardwareVer = hardwareVer;
	}

	public String getSorfwareVer() {
		return sorfwareVer;
	}

	public void setSorfwareVer(String sorfwareVer) {
		this.sorfwareVer = sorfwareVer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getIconID() {
		return iconID;
	}

	public void setIconID(Integer iconID) {
		this.iconID = iconID;
	}

	public String getDevID() {
		return devID;
	}

	public void setDevID(String devID) {
		this.devID = devID;
	}

	public String getEfLongitude() {
		return efLongitude;
	}

	public void setEfLongitude(String efLongitude) {
		this.efLongitude = efLongitude;
	}

	public String getEfLatitude() {
		return efLatitude;
	}

	public void setEfLatitude(String efLatitude) {
		this.efLatitude = efLatitude;
	}
	public String getMntMentName() {
		return mntMentName;
	}

	public void setMntMentName(String mntMentName) {
		this.mntMentName = mntMentName;
	}
	public String getSubComName() {
		return subComName;
	}

	public void setSubComName(String subComName) {
		this.subComName = subComName;
	}
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public void setFilter(Integer filter) {
		this.filter = filter;
	}
	
	public Integer getFilter() {
		return filter;
	}
/**********************************自定义校验方法开始***************************************************/
	private boolean isRepeat(String id){
		IFenceDao fd = (FenceDaoImpl)getDaoObject("fenceDaoImpl");
		try {
			FenceBean fb = fd.getFenceByHostID(id);
			if(fb==null){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	private boolean isVendorIdExsit(String vendorID){
		IManufacturerDao m = (ManufacturerDaoImpl)getDaoObject("manufacturerDaoImpl");
		try {
			Manufacturer man = m.getManufacturerById(vendorID);
			if(man!=null){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	private boolean isMngExsit(String mngID){
		
		IServerDao serverDao = (ServerDaoImpl)getDaoObject("serverDaoImpl");
		try {
			String id = serverDao.hasServerByMgtID(mngID);
			if(id!=null){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	private boolean isMatchMng(String subComId){
		
		IManufacturerDao m = (ManufacturerDaoImpl)getDaoObject("manufacturerDaoImpl");
		try {
			String branchID = m.getSubComIDByMngID(this.mntMentID);
			if(branchID!=null && branchID.equals(subComId)){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
/**********************************自定义校验方法结束***************************************************/
	private Object getDaoObject(String targetObj) {
		
    	String path = this.getClass().getResource("/").getPath();        
    	path = path.substring(0,path.indexOf("WEB-INF"));
    	
		ApplicationContext ac = new FileSystemXmlApplicationContext(path
				+ "WEB-INF/Context-*.xml");
		  
		return ac.getBean(targetObj);

	}
	@Override
	public String toString() {
		return "FenceBean [hostID=" + hostID + ", vendorID=" + vendorID
				+ ", fenceType=" + fenceType + ", fenceName=" + fenceName
				+ ", ip=" + ip + ", port=" + port + ", fenceStatus="
				+ fenceStatus + ", installTime=" + installTime + ", mntMentID="
				+ mntMentID + ", subComID=" + subComID + ", hardwareVer="
				+ hardwareVer + ", sorfwareVer=" + sorfwareVer + ", remarks="
				+ remarks + ", efLongitude=" + efLongitude + ", efLatitude="
				+ efLatitude + ", iconID=" + iconID + ", devID=" + devID + "]";
	}
	 
}