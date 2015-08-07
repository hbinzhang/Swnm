package com.entity.securityinfo;

public class Rectification {

	/**
	 * 所属机构
	 */
	private String belongToOrgId;
	
	/**
	 * 所属机构
	 */
	private String belongToOrgName;
	
	/**
	 * 整改名称
	 */
	private String name;	
	
	/**
	 * 整改发起部门
	 */
	private String initiatingOrg;
	
	/**
	 * 整改措施
	 */
	private String actions;
	
	/**
	 * 实际整改时间
	 */
	private String time;
	
	/**
	 * 整改结果
	 */
	private String result;
	
	/**
	 * 整改实施部门
	 */
	private String implementOrg;
	
	/**
	 * 整改实施人员
	 */
	private String implementPerson;

	/**
	 * 整改备注
	 */
	private String note;

	public String getBelongToOrgId() {
		if (belongToOrgId == null ){
			return "";
		}
		return belongToOrgId;
	}

	public void setBelongToOrgId(String belongToOrgId) {
		this.belongToOrgId = belongToOrgId;
	}

	public String getBelongToOrgName() {
		if (belongToOrgName == null ){
			return "";
		}
		return belongToOrgName;
	}

	public void setBelongToOrgName(String belongToOrgName) {
		this.belongToOrgName = belongToOrgName;
	}

	public String getName() {
		if (name == null ){
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitiatingOrg() {
		if (initiatingOrg == null ){
			return "";
		}
		return initiatingOrg;
	}

	public void setInitiatingOrg(String initiatingOrg) {
		this.initiatingOrg = initiatingOrg;
	}

	public String getActions() {
		if (actions == null ){
			return "";
		}
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getImplementOrg() {
		if (implementOrg == null ){
			return "";
		}
		return implementOrg;
	}

	public void setImplementOrg(String implementOrg) {
		this.implementOrg = implementOrg;
	}

	public String getImplementPerson() {
		if (implementPerson == null ){
			return "";
		}
		return implementPerson;
	}

	public void setImplementPerson(String implementPerson) {
		this.implementPerson = implementPerson;
	}

	public String getNote() {
		if (note == null ){
			return "";
		}
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Rectification [belongToOrgId=" + belongToOrgId
				+ ", belongToOrgName=" + belongToOrgName + ", name=" + name
				+ ", initiatingOrg=" + initiatingOrg + ", actions=" + actions
				+ ", time=" + time + ", result=" + result + ", implementOrg="
				+ implementOrg + ", implementPerson=" + implementPerson
				+ ", note=" + note + "]";
	}
	
}
