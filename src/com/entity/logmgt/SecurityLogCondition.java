package com.entity.logmgt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecurityLogCondition {
	
	/**
	 * 开始时间，为null表示不限制开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间，为null表示不限制结束时间
	 */
	private Date endTime;

	/**
	 * 帐号列表，为null表示全部操作员
	 */
	private String accountId;

	/**
	 * 操作标识，-1表示全部
	 */
	private int commandId = -1;

	/**
	 * 操作结果，-1表示全部
	 */
	private int opResult = -1;

	/**
	 * 排序信息；支持三级排序，排序选项有产生时间、机构标识、操作员
	 */
//	private List<SortedOptionItem> sortedOptions;

	/**
	 * 机构id
	 */
	private String organizationId;
	
	/**排序条件：产生时间*/
    public static final String ORDER_BY_TIME ="OPTIME";
    /**排序条件：机构标识*/
    public static final String ORDER_BY_ORGANIZATIONID ="ORGANIZATIONID";
    /**排序条件：操作员*/
    public static final String ORDER_BY_ACCOUNTID ="ACCOUNTID";
    
    /**
	 * 分页起始条数
	 */
	private int start;
	
	/**
	 * 分页结束条数
	 */
	private int end;
	
	private String organs;
	
	/**
	 * 选中的机构级别
	 */
	private String orgLev;
	
	/**
	 * 1 按机构查
	 * 2 按帐号查
	 */
	private int flag;
	
	private String searchUri;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	/*public static enum SecurotyLogSort {
		OPTIME, ACCOUNTID, ORGANIZATIONID
	}*/
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public int getOpResult() {
		return opResult;
	}

	public void setOpResult(int opResult) {
		this.opResult = opResult;
	}

	/*public List<SortedOptionItem> getSortedOptions() {
		return sortedOptions;
	}

	public void setSortedOptions(List<SortedOptionItem> sortedOptions) {
		this.sortedOptions = sortedOptions;
	}*/

	public boolean beginTimeIsValid() {
		if (beginTime == null) {
			return false;
		}
		return true;
	}

	public boolean endTimeIsValid() {
		if (endTime == null) {
			return false;
		}
		return true;
	}

	public boolean accountIdIsValid() {
		if (this.accountId == null) {
			return false;
		}
		return true;
	}

	public boolean commandIdIsValid() {
		if (commandId == -1) {
			return false;
		}
		return true;
	}

	public boolean opResultIsValid() {
		if (opResult == -1) {
			return false;
		}
		return true;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrgans() {
		return organs;
	}

	public void setOrgans(String organs) {
		this.organs = organs;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public void setOrgLev(String orgLev) {
		this.orgLev = orgLev;
	}
	
	public String getOrgLev() {
		return orgLev;
	}
	
	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}
	
	public String getSearchUri() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("securityLogCondition.flag=").append(this.flag).append("&");
		if(this.flag==2){
			buffer.append("securityLogCondition.accountId=").append(this.accountId).append("&");
		}
		else {
			buffer.append("securityLogCondition.orgLev=").append(this.orgLev).append("&");
			buffer.append("securityLogCondition.organizationId=").append(this.organizationId).append("&");
		}
		try {
			buffer.append("securityLogCondition.beginTime=").append(this.beginTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.beginTime)).append("&");
			buffer.append("securityLogCondition.endTime=").append(this.endTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime)).append("&");
		} catch (Exception e) {
		}
		buffer.append("securityLogCondition.opResult=").append(this.opResult).append("&");
		buffer.append("securityLogCondition.commandId=").append(this.commandId);
		this.searchUri = buffer.toString();
		return this.searchUri;
	}

	@Override
	public String toString() {
		return "SecurityLogCondition [beginTime=" + beginTime + ", endTime="
				+ endTime + ", accountId=" + accountId + ", commandId="
				+ commandId + ", opResult=" + opResult + ", organizationId="
				+ organizationId + ", start=" + start + ", end=" + end
				+ ", organs=" + organs + ", orgLev=" + orgLev + ", flag="
				+ flag + ", searchUri=" + searchUri + "]";
	}
	
}
