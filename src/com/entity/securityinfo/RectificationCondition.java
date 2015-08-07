package com.entity.securityinfo;

public class RectificationCondition {

/*	*//**
	 * 开始时间，为null表示不限制开始时间
	 *//*
	private Date beginTime;

	*//**
	 * 结束时间，为null表示不限制结束时间
	 *//*
	private Date endTime;*/
	
	private String time;
	
	/**
	 * 所属机构
	 */
	private String belongToOrgId;
	
	/**
	 * 整改名称
	 */
	private String name;
	
	/**
	 * 实施部门
	 */
	private String implementOrg;
	
	/**
	 * 发起部门
	 */
	private String initiatingOrg;
	
	private String searchUri;

	public void setSearchUri(String searchUri) {
		this.searchUri = searchUri;
	}

/*	public Date getBeginTime() {
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
	}*/

	public String getBelongToOrgId() {
		return belongToOrgId;
	}

	public void setBelongToOrgId(String belongToOrgId) {
		this.belongToOrgId = belongToOrgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImplementOrg() {
		return implementOrg;
	}

	public void setImplementOrg(String implementOrg) {
		this.implementOrg = implementOrg;
	}

	public String getInitiatingOrg() {
		return initiatingOrg;
	}

	public void setInitiatingOrg(String initiatingOrg) {
		this.initiatingOrg = initiatingOrg;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSearchUri() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("rectificationCondition.belongToOrgId=").append(this.belongToOrgId).append("&");	
		/*try {
			buffer.append("rectificationCondition.beginTime=").append(this.beginTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.beginTime)).append("&");
			buffer.append("rectificationCondition.endTime=").append(this.endTime==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.endTime)).append("&");
		} catch (Exception e) {
		}*/
		buffer.append("rectificationCondition.time=").append(this.time==null ||this.time =="null"?"":this.time).append("&");
		buffer.append("rectificationCondition.name=").append(this.name==null ||this.name =="null"?"":this.name).append("&");
		buffer.append("rectificationCondition.initiatingOrg=").append(this.initiatingOrg==null ||this.initiatingOrg =="null"?"":this.initiatingOrg).append("&");
		buffer.append("rectificationCondition.implementOrg=").append(this.implementOrg==null ||this.implementOrg =="null"?"":this.implementOrg);
		this.searchUri = buffer.toString();
		return searchUri;
	}

	@Override
	public String toString() {
		return "RectificationCondition [time=" + time + ", belongToOrgId="
				+ belongToOrgId + ", name=" + name + ", implementOrg="
				+ implementOrg + ", initiatingOrg=" + initiatingOrg
				+ ", searchUri=" + searchUri + "]";
	}
	
}
