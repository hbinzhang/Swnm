package com.entity.videomonitor;

public class TVmRoundSequence {
	private int rsid;
	private int roundid;
	private TVmIpc ipc;
	private int seqorder;
	
	public int getRsid() {
		return rsid;
	}
	public void setRsid(int rsid) {
		this.rsid = rsid;
	}
	public int getRoundid() {
		return roundid;
	}
	public void setRoundid(int roundid) {
		this.roundid = roundid;
	}
	public TVmIpc getIpc() {
		return ipc;
	}
	public void setIpc(TVmIpc ipc) {
		this.ipc = ipc;
	}
	public int getSeqorder() {
		return seqorder;
	}
	public void setSeqorder(int seqorder) {
		this.seqorder = seqorder;
	}
}
