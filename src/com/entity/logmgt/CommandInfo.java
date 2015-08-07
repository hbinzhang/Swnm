package com.entity.logmgt;

public class CommandInfo {
	
	private int commandId = 0;
	
	private String name = "";
	
	private String displayName = "";
	
	private String isAsynch = "false";
	
	private int moduleId = -1;
	
	private int logType = -1;

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIsAsynch() {
		return isAsynch;
	}

	public void setIsAsynch(String isAsynch) {
		this.isAsynch = isAsynch;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	@Override
	public String toString() {
		return "CommandInfo [commandId=" + commandId + ", name=" + name
				+ ", displayName=" + displayName + ", isAsynch=" + isAsynch
				+ ", moduleId=" + moduleId + ", logType=" + logType + "]";
	}

}
