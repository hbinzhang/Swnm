package com.entity.zone;

import java.io.Serializable;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.MemberOf;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Range;
import net.sf.oval.constraint.ValidateWithMethod;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dao.common.IManufacturerDao;
import com.dao.common.impl.ManufacturerDaoImpl;
import com.dao.mgtserver.IServerDao;
import com.dao.mgtserver.impl.ServerDaoImpl;
import com.dao.zone.IZoneDao;
import com.dao.zone.impl.ZoneDaoImpl;

public class ZoneBean implements Serializable,DeviceMapInterface {

	private static final long serialVersionUID = 1728570318408986941L;
	@NotNull(message = "防区ID不能为空")
	@NotEmpty(message = "防区ID不能为空")
	@Length(max=9,message="防区ID长度限制为9位")
	@MatchPattern(pattern = "^\\d+$",message ="防区ID只能为整数")
	@ValidateWithMethod(methodName = "isRepeat", parameterType = Integer.class,message ="该防区ID已存在")
	private Integer zoneID;
	private String zoneName;
	@NotNull(message = "管理处ID不能为空")
	@NotEmpty(message = "管理处ID不能为空")
	@ValidateWithMethod(methodName = "isMngExsit", parameterType = String.class,message ="管理处不存在或者管理处未配置服务器")
	private String mgtID;//管理处ID
	private String mgtName;
	@NotNull(message = "分公司ID不能为空")
	@NotEmpty(message = "分公司ID不能为空")
	@ValidateWithMethod(methodName = "isMatchMng", parameterType = String.class,message ="分公司与管理处不一致")
	private String branchID;
	private String branchName;
	private Integer status=0;
	@NotNull(message = "朝向不能为空")
	@NotEmpty(message = "朝向不能为空")
	@MemberOf(value = {"左岸","右岸"},message ="朝向只能是 左岸 或 右岸")
	private String orientation;
	@MatchPattern(pattern = "^[0-9]+\\.{0,1}[0-9]{0,4}$",message ="开始经度只能为数字")
	private String startLon;
	@MatchPattern(pattern = "^[0-9]+\\.{0,1}[0-9]{0,4}$",message ="开始纬度只能为数字")
	private String startLat;
	@MatchPattern(pattern = "^[0-9]+\\.{0,1}[0-9]{0,4}$",message ="结束经度只能为数字")
	private String endLon;
	@MatchPattern(pattern = "^[0-9]+\\.{0,1}[0-9]{0,4}$",message ="结束纬度只能为数字")
	private String endLat;
	@Range(min=1,max=4,message = "围栏类型只能是1到4之间的数字")
	private Integer fenceType;
	private String info;
	private String hostID;
	private Integer filter=1;
	
	private String searchUri;
	
	public void init(){
		if(this.filter==null||this.filter==1)
		{
			this.fenceType = null;
			this.zoneName = null;
			
		}else if(this.filter==2)
		{
			this.fenceType = null;
			this.zoneID = null;
		}
		else
		{
			this.zoneName = null;
			this.zoneID = null;
		}
	}
	
	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}
	
	public String getSearchUri() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("zoneBean.branchID=").append(this.branchID==null?"":this.branchID).append("&");
		buffer.append("zoneBean.mgtID=").append(this.mgtID==null?"":this.mgtID).append("&");
		buffer.append("fenceBean.filter=").append(this.filter==null?1:this.filter).append("&");
		buffer.append("zoneBean.zoneName=").append(this.zoneName==null?"":this.zoneName).append("&");
		buffer.append("zoneBean.zoneID=").append(this.zoneID==null?"":this.zoneID).append("&");
		buffer.append("zoneBean.fenceType=").append(this.fenceType==null?"":this.fenceType);
		this.searchUri = buffer.toString();
		return searchUri;
	}
	
	public Integer getFilter() {
		return filter;
	}

	public void setFilter(Integer filter) {
		this.filter = filter;
	}

	public Integer getZoneID() {
		return zoneID;
	}

	public void setZoneID(Integer zoneID) {
		this.zoneID = zoneID;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getMgtID() {
		return mgtID;
	}

	public void setMgtID(String mgtID) {
		this.mgtID = mgtID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		if(status==null){
			
			this.status=0;
			
		}else{
			
			this.status = status;
		}
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getStartLon() {
		return startLon;
	}

	public void setStartLon(String startLon) {
		this.startLon = startLon;
	}

	public String getStartLat() {
		return startLat;
	}

	public void setStartLat(String startLat) {
		this.startLat = startLat;
	}

	public String getEndLon() {
		return endLon;
	}

	public void setEndLon(String endLon) {
		this.endLon = endLon;
	}

	public String getEndLat() {
		return endLat;
	}

	public void setEndLat(String endLat) {
		this.endLat = endLat;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public Integer getFenceType() {
		return fenceType;
	}

	public void setFenceType(Integer fenceType) {
		this.fenceType = fenceType;
	}

	public String getMgtName() {
		return mgtName;
	}

	public void setMgtName(String mgtName) {
		this.mgtName = mgtName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getHostID() {
		return hostID;
	}

	public void setHostID(String hostID) {
		this.hostID = hostID;
	}
/**********************************自定义校验方法开始***************************************************/
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
			String branchID = m.getSubComIDByMngID(this.mgtID);
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
	private boolean isRepeat(Integer id){
		IZoneDao zd = (ZoneDaoImpl)getDaoObject("zoneDaoImpl");
		try {
			ZoneBean z = zd.getZoneByZoneID(id);
			if(z==null){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
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
}
