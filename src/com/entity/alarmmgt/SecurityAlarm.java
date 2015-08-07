package com.entity.alarmmgt;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.alibaba.fastjson.annotation.JSONField;

public class SecurityAlarm extends Alarm{
	
	/**
	 * 复核严重程度
	 * 1 轻微
	 * 2 一般
	 * 3 严重
	 * 4非常严重
	 */
	private Integer checkLevel = -1;
	
	/**
	 * 处理状态
	 * 0 未处理
	 * 1 已处理
	 */
	private Integer alarmStatus = 0;

	/**
	 * 责任人Id
	 */
	private String peopleId;
//	private String peopleName = "";
	
	/**
	 * 处理人
	 */
//	private String userName = "";
	
	/**
	 * 处理时间
	 */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date managerTime;
	
	/**
	 * 复核依据
	 * 1 视频复核
	 * 2 现场复核
	 */
	private Integer checkMothod = -1;
	
	/**
	 * 防区摄像头录像URL
	 */
	private String vidioUrl;
	
	/**
	 * 左防区摄像头录像URL
	 */
	private String leftVidioUrl;
	
	/**
	 * 右防区摄像头录像URL
	 */
	private String rightVidioUrl;
	
	/**
	 * 图片地址，以“，”间隔
	 */
	private String pictureUrl;

	/**
	 * 告警码
	 */
	private Integer alarmCode;
	
	/**
	 * 告警名称（存在知识库里）
	 * 入侵告警
	 * 设备损坏告警
	 * 光纤振动告警
	 * 光纤断裂告警
	 * 跨越告警
	 * 触碰
	 * 墙面破坏告警
	 * 节点破坏（人为对节点进行破坏）
	 * 人为剪短告警
	 * 传感器节点异常
	 * 设备异常
	 */
	private String alarmName;

	/**
	 * 告警级别
	 * 1 轻微
	 * 2 一般
	 * 3 严重
	 * 4非常严重
	 */
//	private Integer level;
	
	/**
	 * 设备类型
	 * 1 高压脉冲
	 * 2 振动光纤
	 * 3 一体化
	 * 4 摄像头
	 */
	private Integer deviceType = -1;
	
	/**
	 * 设备Id
	 */
	private String deviceId;
	
	/**
	 * 防区
	 */	
	private String zoneName;
	
	/**
	 * 防区Id
	 */	
	private Integer zoneId;
	
	/**
	 * 管理处
	 */	
	private String departmentName;
	
	/**
	 * 管理处Id
	 */	
	private String departmentId;
	
	/**
	 * 分公司
	 */	
	private String branchName;
	
	/**
	 * 分公司Id
	 */	
	private String branchId;
	
	/**
	 * 附加信息
	 */
	private String info;
	
	/**
	 * 上报机构
	 * 1 管理处
	 * 2 分公司
	 * 3 总公司
	 */
	private Integer report = 1;
	
	/**
	 * 告警类型
	 * 1 安防告警
	 * 2 设备告警
	 */
	private Integer type = -1;
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 持续时间
	 */
	private Integer lastTime;
	
	/**
	 * 次数
	 */
	private Integer alarmCount;
		
	/**
	 * 虚实警
	 * 0 虚警
	 * 1 实警
	 */
	private Integer isReal = -1;
	
	/**
	 * 事件原因
	 */
	private String reason;
	
	/**
	 * 事件过程
	 */
	private String description;
	
	private Integer alarmLevel;
	
	
	public String getBranchId() {
		if (branchId == null ){
			return "";
		}
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Integer getCheckLevel() {
		return checkLevel;
	}

	public void setCheckLevel(Integer checkLevel) {
		this.checkLevel = checkLevel;
	}

	public Integer getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getManagerTime() {
		return managerTime;
	}

	public void setManagerTime(Date managerTime) {
		this.managerTime = managerTime;
	}

	public Integer getCheckMothod() {
		return checkMothod;
	}

	public void setCheckMothod(Integer checkMothod) {
		this.checkMothod = checkMothod;
	}

	public void setVidioUrl(String vidioUrl) {
		this.vidioUrl = vidioUrl;
	}

	public String getPictureUrl() {
		if (pictureUrl == null ){
			return "";
		}
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(Integer alarmCode) {
		this.alarmCode = alarmCode;
	}

	public String getAlarmName() {
		if (alarmName == null ){
			return "";
		}
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	/*public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}*/

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceId() {
		if (deviceId == null ){
			return "";
		}
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getZoneName() {
		if (zoneName == null ){
			return "";
		}
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getDepartmentName() {
		if (departmentName == null ){
			return "";
		}
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getBranchName() {
		if (branchName == null ){
			return "";
		}
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getInfo() {
		if (info == null ){
			return "";
		}
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getLeftVidioUrl() {
		if (leftVidioUrl == null ){
			return "";
		}
		return leftVidioUrl;
	}

	public void setLeftVidioUrl(String leftVidioUrl) {
		this.leftVidioUrl = leftVidioUrl;
	}

	public String getRightVidioUrl() {
		if (rightVidioUrl == null ){
			return "";
		}
		return rightVidioUrl;
	}

	public void setRightVidioUrl(String rightVidioUrl) {
		this.rightVidioUrl = rightVidioUrl;
	}

	public Integer getReport() {
		return report;
	}

	public void setReport(Integer report) {
		this.report = report;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPeopleId() {
		if (peopleId == null ){
			return "";
		}
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public String getDepartmentId() {
		if (departmentId == null ){
			return "";
		}
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getUserId() {
		if (userId == null ){
			return "";
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getLastTime() {
		return lastTime;
	}

	public void setLastTime(Integer lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getIsReal() {
		return isReal;
	}

	public void setIsReal(Integer isReal) {
		this.isReal = isReal;
	}

	public String getReason() {
		if (reason == null ){
			return "";
		}
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		if (description == null ){
			return "";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	
	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	
	public Integer getAlarmCount() {
		return alarmCount;
	}

	public void setAlarmCount(Integer alarmCount) {
		this.alarmCount = alarmCount;
	}

	public String getVidioUrl() {
		if (vidioUrl == null ){
			return "";
		}
		return vidioUrl;
	}


	@Override
	public String toString() {
		return super.toString() + "SecurityAlarm [checkLevel=" + checkLevel + ", alarmStatus="
				+ alarmStatus + ", peopleId=" + peopleId + ", managerTime="
				+ managerTime + ", checkMothod=" + checkMothod + ", vidioUrl="
				+ vidioUrl + ", leftVidioUrl=" + leftVidioUrl
				+ ", rightVidioUrl=" + rightVidioUrl + ", pictureUrl="
				+ pictureUrl + ", alarmCode=" + alarmCode + ", alarmName="
				+ alarmName + ", deviceType=" + deviceType + ", deviceId="
				+ deviceId + ", zoneName=" + zoneName + ", zoneId=" + zoneId
				+ ", departmentName=" + departmentName + ", departmentId="
				+ departmentId + ", branchName=" + branchName + ", branchId="
				+ branchId + ", info=" + info + ", report=" + report
				+ ", type=" + type + ", userId=" + userId + ", lastTime="
				+ lastTime + ", alarmCount=" + alarmCount + ", isReal="
				+ isReal + ", reason=" + reason + ", description="
				+ description + ", alarmLevel=" + alarmLevel + "]";
	}
	
}
