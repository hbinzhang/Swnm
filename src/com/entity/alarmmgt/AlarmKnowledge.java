package com.entity.alarmmgt;

public class AlarmKnowledge {
	
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
	 * 告警类型
	 * 1安防告警
	 * 2设备告警
	 */
	private Integer alarmType = -1;
	
	/**
	 * 设备类型
	 * 1 高压脉冲
	 * 2 振动光纤
	 * 3 一体化 
	 * 4 摄像头
	 */
	private Integer deviceType = -1;
	
	/**
	 * 告警级别
	 * 1 轻微
	 * 2 一般
	 * 3 严重 
	 * 4非常严重
	 */
	private Integer alarmLevel = -1;
	
	/**
	 * 可能原因
	 */
	private String cause;
	
	/**
	 * 可能后果
	 */
	private String result;
	
	/**
	 * 建议操作
	 */
	private String operation;
		
	/**
	 * 是否影响业务,0 不影响,1 影响
	 */
	private Integer isAffect = 0;
		
	/**
	 * 备注
	 */
	private String info;

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

	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getAlarmLevel() {
		return alarmLevel;
	}

	public void setAlarmLevel(Integer alarmLevel) {
		this.alarmLevel = alarmLevel;
	}

	public String getCause() {
		if (cause == null ){
			return "";
		}
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getResult() {
		if (result == null ){
			return "";
		}
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOperation() {
		if (operation == null ){
			return "";
		}
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Integer getIsAffect() {
		return isAffect;
	}

	public void setIsAffect(Integer isAffect) {
		this.isAffect = isAffect;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "AlarmKnowledge [alarmCode=" + alarmCode + ", alarmName="
				+ alarmName + ", alarmType=" + alarmType + ", deviceType="
				+ deviceType + ", alarmLevel=" + alarmLevel + ", cause="
				+ cause + ", result=" + result + ", operation=" + operation
				+ ", isAffect=" + isAffect + ", info=" + info + "]";
	}
	
}
