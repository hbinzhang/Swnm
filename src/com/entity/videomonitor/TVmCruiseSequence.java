package com.entity.videomonitor;

public class TVmCruiseSequence {
	private int seqid;
	private TVmCruise cruise;
	private TVmPresetposition point;
	private int pausemins;
	private int speed;
	private int cruiseorder;
	
	public int getSeqid() {
		return seqid;
	}
	public void setSeqid(int seqid) {
		this.seqid = seqid;
	}
	public TVmCruise getCruise() {
		return cruise;
	}
	public void setCruise(TVmCruise cruise) {
		this.cruise = cruise;
	}
	public TVmPresetposition getPoint() {
		return point;
	}
	public void setPoint(TVmPresetposition point) {
		this.point = point;
	}
	public int getPausemins() {
		return pausemins;
	}
	public void setPausemins(int pausemins) {
		this.pausemins = pausemins;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getCruiseorder() {
		return cruiseorder;
	}
	public void setCruiseorder(int cruiseorder) {
		this.cruiseorder = cruiseorder;
	}
	
}
