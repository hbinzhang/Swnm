package com.service.sounddev.thirdsection;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class _AlarmInfo extends Structure {
	/** the Slot of 5000/TCP which cause the alarm change */
	public byte bySlot;
	/** the Channel of 5000/TCP which cause the alarm change */
	public byte byChannel;
	/** 0: Low Alarm, 1: High Alarm */
	public byte byAlarmType;
	/** 0: Alarm Off, 1: Alarm On */
	public byte byAlarmStatus;
	/** indicate the index 5000/TCP which cause the alarm change, zero-based */
	public byte byIndexOf5KTCP;
	/**
	 * the IP address which cause the alarm change<br>
	 * C type : char[20]
	 */
	public byte[] szIP = new byte[20];
	/**
	 * e.x 2001/09/23 10:12:34:567 (Year/Month/Day Hour:Minute:Second:mSecond)<br>
	 * C type : char[48]
	 */
	public byte[] szDateTime = new byte[48];
	public _AlarmInfo() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bySlot", "byChannel", "byAlarmType", "byAlarmStatus", "byIndexOf5KTCP", "szIP", "szDateTime");
	}
	/**
	 * @param bySlot the Slot of 5000/TCP which cause the alarm change<br>
	 * @param byChannel the Channel of 5000/TCP which cause the alarm change<br>
	 * @param byAlarmType 0: Low Alarm, 1: High Alarm<br>
	 * @param byAlarmStatus 0: Alarm Off, 1: Alarm On<br>
	 * @param byIndexOf5KTCP indicate the index 5000/TCP which cause the alarm change, zero-based<br>
	 * @param szIP the IP address which cause the alarm change<br>
	 * C type : char[20]<br>
	 * @param szDateTime e.x 2001/09/23 10:12:34:567 (Year/Month/Day Hour:Minute:Second:mSecond)<br>
	 * C type : char[48]
	 */
	public _AlarmInfo(byte bySlot, byte byChannel, byte byAlarmType, byte byAlarmStatus, byte byIndexOf5KTCP, byte szIP[], byte szDateTime[]) {
		super();
		this.bySlot = bySlot;
		this.byChannel = byChannel;
		this.byAlarmType = byAlarmType;
		this.byAlarmStatus = byAlarmStatus;
		this.byIndexOf5KTCP = byIndexOf5KTCP;
		if ((szIP.length != this.szIP.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.szIP = szIP;
		if ((szDateTime.length != this.szDateTime.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.szDateTime = szDateTime;
	}
	public _AlarmInfo(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends _AlarmInfo implements Structure.ByReference {
		
	};
	public static class ByValue extends _AlarmInfo implements Structure.ByValue {
		
	};
}
