package com.entity.authmgt;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Operation implements Serializable{
/**
     * 
     */
    private static final long serialVersionUID = -1888585262791843981L;
	private int opId;
	private String opName;
	private String opDisplayName;
//	private String opDesc;
	private String opGroupDisplayName;
	private int parentOpId;	
	private List<String> higherPriorityAuthenticatedOperations = new ArrayList<String>();
	private List<String> lowerPriorityAuthenticatedOperations = new ArrayList<String>();	
	private List<String> commands;

	public Operation(String opName, String opDisplayName,
			String opGroupDisplayName) {
		super();
		this.opName = opName;
		this.opDisplayName = opDisplayName;
		this.opGroupDisplayName = opGroupDisplayName;
	}

	public int getOpId() {
		return opId;
	}

	public void setOpId(int opId) {
		this.opId = opId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getOpDisplayName() {
		return opDisplayName;
	}

	public void setOpDisplayName(String opDisplayName) {
		this.opDisplayName = opDisplayName;
	}

//	public String getOpDesc() {
//		return opDesc;
//	}
//
//	public void setOpDesc(String opDesc) {
//		this.opDesc = opDesc;
//	}


	public int getParentOpId() {
		return parentOpId;
	}

	public String getOpGroupDisplayName() {
		return opGroupDisplayName;
	}

	public void setOpGroupDisplayName(String opGroupDisplayName) {
		this.opGroupDisplayName = opGroupDisplayName;
	}

	public void setParentOpId(int parentOpId) {
		this.parentOpId = parentOpId;
	}

	public List<String> getHigherPriorityAuthenticatedOperations() {
		return higherPriorityAuthenticatedOperations;
	}

	public void setHigherPriorityAuthenticatedOperations(
			List<String> higherPriorityAuthenticatedOperations) {
		this.higherPriorityAuthenticatedOperations = higherPriorityAuthenticatedOperations;
	}

	public List<String> getLowerPriorityAuthenticatedOperations() {
		return lowerPriorityAuthenticatedOperations;
	}

	public void setLowerPriorityAuthenticatedOperations(
			List<String> lowerPriorityAuthenticatedOperations) {
		this.lowerPriorityAuthenticatedOperations = lowerPriorityAuthenticatedOperations;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}

	@Override
	public String toString() {
		return "Operation [opId=" + opId + ", opName=" + opName
				+ ", opDisplayName=" + opDisplayName + ", opGroupDisplayName=" + opGroupDisplayName + ", parentOpId=" + parentOpId
				+ ", higherPriorityAuthenticatedOperations="
				+ higherPriorityAuthenticatedOperations
				+ ", lowerPriorityAuthenticatedOperations="
				+ lowerPriorityAuthenticatedOperations + ", commands="
				+ commands + "]";
	}
	
}

