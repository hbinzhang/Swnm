package com.entity.videomonitor;

import java.util.List;

public class TVmCruise {
	private int cruiseid;
	private String ipcid;
	private int route;
	private String cruisename;
	private List<TVmCruiseSequence> sequences;
	
	public List<TVmCruiseSequence> getSequences() {
		return sequences;
	}
	public void setSequences(List<TVmCruiseSequence> sequences) {
		this.sequences = sequences;
	}
	public int getCruiseid() {
		return cruiseid;
	}
	public void setCruiseid(int cruiseid) {
		this.cruiseid = cruiseid;
	}
	public String getIpcid() {
		return ipcid;
	}
	public void setIpcid(String ipcid) {
		this.ipcid = ipcid;
	}
	public int getRoute() {
		return route;
	}
	public void setRoute(int route) {
		this.route = route;
	}
	public String getCruisename() {
		return cruisename;
	}
	public void setCruisename(String cruisename) {
		this.cruisename = cruisename;
	}
}
