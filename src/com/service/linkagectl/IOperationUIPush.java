package com.service.linkagectl;

public interface IOperationUIPush {
	// 将操作放入推送框架的推送队列
	// 推送到主控界面
	public int pullOperationEvent2MC(String orgID,String devOperation, String devType, String messageBody);
	// 将操作放入推送框架的推送队列
		// 推送到GIS界面
	public int pullOperationEvent2GIS(String orgID, String devOperation, String devType, String messageBody);

	public boolean devHasActiveAlarms(String devID);
}
