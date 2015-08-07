package com.entity.authmgt;

import java.io.Serializable;
import java.util.List;

public class GroupAndOperation implements Serializable {

	private String opGroupName;

	private List<String> opName;
	

	public GroupAndOperation() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getOpGroupName() {
		return opGroupName;
	}


	public void setOpGroupName(String opGroupName) {
		this.opGroupName = opGroupName;
	}


	public List<String> getOpName() {
		return opName;
	}


	public void setOpName(List<String> opName) {
		this.opName = opName;
	}


	public GroupAndOperation(String opGroupName, List<String> opName) {
		super();
		this.opGroupName = opGroupName;
		this.opName = opName;
	}


	@Override
	public String toString() {
		return "GroupAndOperation [opGroupName=" + opGroupName + ", opName="
				+ opName + "]";
	}

	
}
