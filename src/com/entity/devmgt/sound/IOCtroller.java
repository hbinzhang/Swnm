package com.entity.devmgt.sound;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * IO控制器实体类
 * 
 * @author maming 2015-3-25上午10:33:37
 * 
 */
public class IOCtroller implements Serializable {
	@NotNull(message = "ID IS NULL")
	@NotEmpty(message = "ID IS EMPTY")
	String ID = null;
	@NotNull(message = "AUDIOID IS NULL")
	@NotEmpty(message = "AUDIOID IS EMPTY")
	String AUDIOID = null;
	@NotNull(message = "VENDORID IS NULL")
	@NotEmpty(message = "VENDORID IS EMPTY")
	String VENDORID = null;
	@NotNull(message = "NAME IS NULL")
	@NotEmpty(message = "NAME IS EMPTY")
	String NAME = null;
	@MatchPattern(pattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$", message = "IP FORMAT ERROR")
	@NotNull(message = "IP IS NULL")
	@NotEmpty(message = "IP IS EMPTY")
	String IP = null;
	@NotNull(message = "MGTCODE IS NULL")
	@NotEmpty(message = "MGTCODE IS EMPTY")
	String MGTCODE = null;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAUDIOID() {
		return AUDIOID;
	}

	public void setAUDIOID(String aUDIOID) {
		AUDIOID = aUDIOID;
	}

	public String getVENDORID() {
		return VENDORID;
	}

	public void setVENDORID(String vENDORID) {
		VENDORID = vENDORID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getMGTCODE() {
		return MGTCODE;
	}

	public void setMGTCODE(String mGTCODE) {
		MGTCODE = mGTCODE;
	}
}
