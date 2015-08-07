package com.entity.videomonitor;

import java.util.List;

public class TVmRound {
	private int roundid;
	private String userid;
	private String roundname;
	private int screenmod;
	private int pausemins;
	private List<TVmRoundSequence> sequences;
	
	public int getRoundid() {
		return roundid;
	}
	public void setRoundid(int roundid) {
		this.roundid = roundid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRoundname() {
		return roundname;
	}
	public void setRoundname(String roundname) {
		this.roundname = roundname;
	}
	public int getScreenmod() {
		return screenmod;
	}
	public void setScreenmod(int screenmod) {
		this.screenmod = screenmod;
	}
	public List<TVmRoundSequence> getSequences() {
		return sequences;
	}
	public void setSequences(List<TVmRoundSequence> sequences) {
		this.sequences = sequences;
	}
	public int getPausemins() {
		return pausemins;
	}
	public void setPausemins(int pausemins) {
		this.pausemins = pausemins;
	}
}
