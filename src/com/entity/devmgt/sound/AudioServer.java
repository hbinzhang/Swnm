package com.entity.devmgt.sound;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 音频服务器实体类
 * @author maming
 * 2015-3-28下午5:10:43
 *
 */
public class AudioServer implements Serializable {

	private static final long serialVersionUID = -1930522223879246460L;

	@NotNull(message = "SERVERID IS NULL")
	@NotEmpty(message = "SERVERID IS EMPTY")
	public String SERVERID = null;
	@NotNull(message = "SERVERNAME IS NULL")
	@NotEmpty(message = "SERVERNAME IS EMPTY")
	public String SERVERNAME = null;
	@MatchPattern(pattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$", message = "IP FORMAT ERROR")
	@NotNull(message = "IPADDRESS IS NULL")
	@NotEmpty(message = "IPADDRESS IS EMPTY")
	public String IPADDRESS = null;
	
	@NotNull(message = "IPADDRESS IS NULL")
	@NotEmpty(message = "IPADDRESS IS EMPTY")
	public String FACTORYNAME = null;

	public String getFACTORYNAME() {
		return FACTORYNAME;
	}

	public void setFACTORYNAME(String fACTORYNAME) {
		FACTORYNAME = fACTORYNAME;
	}

	public String getSERVERID() {
		return SERVERID;
	}

	public void setSERVERID(String sERVERID) {
		SERVERID = sERVERID;
	}

	public String getSERVERNAME() {
		return SERVERNAME;
	}

	public void setSERVERNAME(String sERVERNAME) {
		SERVERNAME = sERVERNAME;
	}

	public String getIPADDRESS() {
		return IPADDRESS;
	}

	public void setIPADDRESS(String iPADDRESS) {
		IPADDRESS = iPADDRESS;
	}
}
