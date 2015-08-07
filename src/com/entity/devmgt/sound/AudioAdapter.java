package com.entity.devmgt.sound;

import java.io.Serializable;

import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * 音频终端实体类
 * 
 * @author maming 2015-3-24下午1:33:29
 * 
 */
public class AudioAdapter implements Serializable {
	private static final long serialVersionUID = 6665345286724947798L;

	@NotNull(message = "AUDIOID IS NULL")
	@NotEmpty(message = "AUDIOID IS EMPTY")
	String audioId = null;// 音频ID
	@NotNull(message = "VENDORID IS NULL")
	@NotEmpty(message = "VENDORID IS EMPTY")
	String vendorId = null;// 厂商编号
	@NotNull(message = "AUDIONAME IS NULL")
	@NotEmpty(message = "AUDIONAME IS EMPTY")
	String audioName = null;// 终端名称
	@MatchPattern(pattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$", message = "IP FORMAT ERROR")
	@NotNull(message = "AUDIOIP IS NULL")
	@NotEmpty(message = "AUDIOIP IS EMPTY")
	String audioIp = null;// IP地址
	@NotNull(message = "MGTCODE IS NULL")
	@NotEmpty(message = "MGTCODE IS EMPTY")
	String mgtCode = null;// 管理处编号
	@NotNull(message = "OWNER IS NULL")
	@NotEmpty(message = "OWNER IS EMPTY")
	String owner = null;// 所属服务器
	@NotNull(message = "IPCID IS NULL")
	@NotEmpty(message = "IPCID IS EMPTY")
	String ipcId = null;//关联的摄像头ID

	public String getIpcCode() {
		return ipcId;
	}

	public void setIpcCode(String ipcCode) {
		this.ipcId = ipcCode;
	}

	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public String getAudioIp() {
		return audioIp;
	}

	public void setAudioIp(String audioIp) {
		this.audioIp = audioIp;
	}

	public String getMgtCode() {
		return mgtCode;
	}

	public void setMgtCode(String mgtCode) {
		this.mgtCode = mgtCode;
	}
}
