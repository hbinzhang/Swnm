/**
 * 
 */
package com.entity.gis;

/**
 * @author wyj
 * 
 *         GIS
 */
public class BranchInfo {
	
	private double xmin = 0;
	private double ymin = 0;
	private double xmax = 0;
	private double ymax = 0;

	public BranchInfo() {

	}

	public BranchInfo(double xmin, double ymin, double xmax, double ymax) {
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}

	public double getXmin() {
		return xmin;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public double getYmin() {
		return ymin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public double getXmax() {
		return xmax;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public double getYmax() {
		return ymax;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

	public String getXminStr() {
		return String.valueOf(xmin);
	}

	public String getYminStr() {
		return String.valueOf(ymin);
	}

	public String getXmaxStr() {
		return String.valueOf(xmax);
	}

	public String getYmaxStr() {
		return String.valueOf(ymax);
	}
}
