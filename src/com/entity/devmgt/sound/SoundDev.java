package com.entity.devmgt.sound;

import java.io.Serializable;

/**
 * 音频模块统一实体类
 * 
 * @author maming 2015-3-24上午11:05:01
 * 
 */
public class SoundDev implements Serializable {

	private static final long serialVersionUID = -3359968605671481508L;
	public String id = null;// 设备ID
	public String name = null;;// 设备名称
	public String vendorName = null;// 厂商名称/ID，插入时插入所属设备ID
	public String ipAddress = null;;// IP地址
	public String devType = null;;// 设备类型
	public String ownerdev = null;;// 所属设备名称/ID，插入时插入所属设备ID
	public String ownerIp = null;;// 所属设备IP
	public String mgtCode = null;;// 管理处，插入时插入管理处ID
	public String ipcCode = null;
	public String status = null;
	public String mgtName = null;
	public String ownerdevid = null;

	public String getOwnerdevid() {
		return ownerdevid;
	}

	public void setOwnerdevid(String ownerdevid) {
		this.ownerdevid = ownerdevid;
	}

	public String getMgtName() {
		return mgtName;
	}

	public void setMgtName(String mgtName) {
		this.mgtName = mgtName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getOwnerdev() {
		return ownerdev;
	}

	public void setOwnerdev(String ownerdev) {
		this.ownerdev = ownerdev;
	}

	public String getOwnerIp() {
		return ownerIp;
	}

	public void setOwnerIp(String ownerIp) {
		this.ownerIp = ownerIp;
	}

	public String getMgtCode() {
		return mgtCode;
	}

	public void setMgtCode(String mgtCode) {
		this.mgtCode = mgtCode;
	}
	public String getIpcCode() {
		return ipcCode;
	}

	public void setIpcCode(String ipcCode) {
		this.ipcCode = ipcCode;
	}
}
