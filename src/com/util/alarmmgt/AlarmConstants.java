package com.util.alarmmgt;

public class AlarmConstants {

	/**
	 * 设备类型
	 * 1 高压脉冲主机
	 * 2一体化探测主机
	 * 3 防区型振动光纤
	 * 4 定位型振动光纤
	 * 5 NVR
	 * 6 IPC
	 * 7 智能视频服务器
	 */
	public static final int DEVICE_HIGH_VOLTAGE_PULSE = 1;	
	public static final int DEVICE_INTEGRATION = 2;	
	public static final int DEVICE_DEFENSE_VIBRATING_FIBER = 3;	
	public static final int DEVICE_POSITION_VIBRATING_FIBER = 4;	
	public static final int DEVICE_NVR = 5;	
	public static final int DEVICE_IPC = 6;	
	public static final int DEVICE_INTELLIGENT_VIDEO_SERVER = 7;	

	/**
	 * 是否影响业务,0 不影响,1 影响
	 */
	public static final int NOT_AFFECT = 0;
	public static final int AFFECT = 1;
	
	/**
	 * 告警级别
	 * 1 警告
	 * 2 次要
	 * 3 主要
	 * 4 严重
	 */
	public static final int LEVEL_WARNING = 1;	
	public static final int LEVEL_MINOR = 2;	
	public static final int LEVEL_MAJOR = 3;	
	public static final int LEVEL_SERIOUS = 4;
	
	/**
	 * 告警类型
	 * 1 安防告警
	 * 2 设备告警
	 */
	public static final int TYPE_SECURITY_ALARM = 1;
	public static final int TYPE_DEVICE_ALARM = 2;
	
	/**
	 * 复核严重程度
	 * 1 警告
	 * 2 次要
	 * 3 主要
	 * 4 严重
	 */
	public static final int CHECK_LEVEL_SLIGHT  = 1;	
	public static final int CHECK_LEVEL_GENERAL = 2;	
	public static final int CHECK_LEVEL_SERIOUS = 3;	
	public static final int CHECK_LEVEL_VERY_SERIOUS = 4;
	
	/**
	 * 处理状态
	 * 0 未处理
	 * 1 已处理
	 */
	public static final int STATUS_NOT_HANDLED = 0;	
	public static final int STATUS_HANDLED = 1;
	
	/**
	 * 复核依据
	 * 1 视频复核
	 * 2 现场复核
	 */
	public static final int CHECK_METHOD_VIDEO = 1;	
	public static final int CHECK_METHOD_SCENE = 1;
	
	/**
	 * 是否有事件视频
	 * 0 没有
	 * 1 有
	 */
	public static final int NOT_HAS_EVENT_VIDEO = 0;
	public static final int HAS_EVENT_VIDEO = 1;
	
	/**
	 * 统计粒度
	 * 1 分公司
	 * 2 管理处
	 * 3 防区
	 * 4 设备类型
	 * 5 告警级别
	 */
	public static final int STATIC_GRANU_BRANCH = 1;
	public static final int STATIC_GRANU_DEPARTMENT = 2;
	public static final int STATIC_GRANU_ZONE = 3;
	public static final int STATIC_GRANU_DEVICE_TYPE = 4;
	public static final int STATIC_GRANU_ALARM_LEVEL = 5;
	
	/**
	 * 时间粒度
	 * DD 天
	 * DY 周
	 * MM 月
	 * Q  季度
	 * YY 年
	 */
	public static final String TIME_GRANU_DATE = "DD";
	public static final String TIME_GRANU_WEEK = "DY";
	public static final String TIME_GRANU_MONTH = "MM";
	public static final String TIME_GRANU_QUARTER = "Q";
	public static final String TIME_GRANU_YEAR = "YY";
	public static final String TIME_GRANU_ALL = "CC";
	
	/**
	 * 虚实警
	 * 0 虚警
	 * 1 实警
	 */
	public static final int IS_NOT_REAL = 0;
	public static final int IS_REAL = 1;	
	
	/**
	 * 用户所在机构标识
	 * 0 总公司
	 * 1 分公司
	 * 2 管理处
	 */
	public static final String USER_HEADQUARTERS = "0";
	public static final String USER_BRANCH = "1";
	public static final String USER_DEPARTMENT = "2";
	
	/**
	 * ajax返回结果定义
	 */
	public static final int RESULT_FAIL = 0;
	public static final int RESULT_SUC = 1;
}
