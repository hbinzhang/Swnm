package com.entity.devmgt.sound;

import java.io.Serializable;

/**
 * 厂商实体类
 * @author maming
 * 2015-3-28下午4:18:58
 *
 */
public class SoundDevManufacturer implements Serializable {

	private static final long serialVersionUID = -62915696821695489L;
	public String vendorId = null;
	public String vendorName = null;

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
}
