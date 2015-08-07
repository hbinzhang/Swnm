package com.entity.authmgt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OperationGroup implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 2173010314890536441L;
    private int opGroupId=1;
	private String opGroupName;
	private String opGroupDisplayName;	
//	private String opGroupDesc;
	private String parentOpGroupName;
    private int parentOpGroupId=0;
	
	private List<String> children = new ArrayList<String>();
	/**
	 * @param opGroupName
	 * @param opGroupDisplayName
	 * @param opGroupDesc
	 * @param parentOpGroupName
	 */
//	public OperationGroup(String opGroupName, String opGroupDisplayName,
//			String opGroupDesc, String parentOpGroupName) {
//		this.opGroupName = opGroupName;
//		this.opGroupDisplayName = opGroupDisplayName;
//		this.opGroupDesc = opGroupDesc;
//		this.parentOpGroupName = parentOpGroupName;
//	}
	/**
	 * @param opGroupName
	 * @param opGroupDisplayName
	 */
	public OperationGroup(String opGroupName, String opGroupDisplayName) {
		super();
		this.opGroupName = opGroupName;
		this.opGroupDisplayName = opGroupDisplayName;
	}
	/**
	 * @param opGroupName
	 * @param opGroupDisplayName
	 * @param parentOpGroupName
	 */
//	public OperationGroup(String opGroupName, String opGroupDisplayName,
//			String parentOpGroupName) {
//		super();
//		this.opGroupName = opGroupName;
//		this.opGroupDisplayName = opGroupDisplayName;
//		this.parentOpGroupName = parentOpGroupName;
//	}
	
	public OperationGroup(String opGroupName,
			String opGroupDisplayName,
			String parentOpGroupName) {
		super();
		this.opGroupName = opGroupName;
		this.opGroupDisplayName = opGroupDisplayName;
		this.parentOpGroupName = parentOpGroupName;
	}
	public String getOpGroupName() {
		return opGroupName;
	}
	public void setOpGroupName(String opGroupName) {
		this.opGroupName = opGroupName;
	}
	public String getOpGroupDisplayName() {
		return opGroupDisplayName;
	}
	public void setOpGroupDisplayName(String opGroupDisplayName) {
		this.opGroupDisplayName = opGroupDisplayName;
	}
//	public String getOpGroupDesc() {
//		return opGroupDesc;
//	}
//	public void setOpGroupDesc(String opGroupDesc) {
//		this.opGroupDesc = opGroupDesc;
//	}
	public String getParentOpGroupName() {
		return parentOpGroupName;
	}
	public void setParentOpGroupName(String parentOpGroupName) {
		this.parentOpGroupName = parentOpGroupName;
	}
	
	public int getOpGroupId() {
		return opGroupId;
	}
	public void setOpGroupId(int opGroupId) {
		this.opGroupId = opGroupId;
	}
	public int getParentOpGroupId() {
		return parentOpGroupId;
	}
	public void setParentOpGroupId(int parentOpGroupId) {
		this.parentOpGroupId = parentOpGroupId;
	}
	public List<String> getChildren() {
		return children;
	}
	public void setChildren(List<String> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "OperationGroup [opGroupId=" + opGroupId + ", opGroupName="
				+ opGroupName + ", opGroupDisplayName=" + opGroupDisplayName
				+ ", parentOpGroupName=" + parentOpGroupName
				+ ", parentOpGroupId=" + parentOpGroupId + ", children="
				+ children + "]";
	}
	
}
