package com.entity.zone;

public enum EnumDeviceType {
	
	PULSE(1),INTEGRATION(2),DEFENCE(3),POSITION(4);
	
	private int value;

	EnumDeviceType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

}
