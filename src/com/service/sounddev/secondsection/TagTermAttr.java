package com.service.sounddev.secondsection;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public class TagTermAttr extends Structure {
	/** 终端ID */
	public NativeLong tid;
	/** 终端状态：-1-不连通，0-空闲, >0-使用中 */
	public int status;
	/** 活动会话ID */
	public NativeLong a_sid;
	/** 音量：0~56，0最小，56最大 */
	public int vol;
	/**
	 * 终端IP地址<br>
	 * C type : char[40]
	 */
	public byte[] ipaddr = new byte[40];
	/**
	 * 中继服务器IP地址<br>
	 * C type : char[40]
	 */
	public byte[] fwdaddr = new byte[40];
	/**
	 * 终端名称<br>
	 * C type : char[256]
	 */
	public byte[] name = new byte[256];
	public TagTermAttr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("tid", "status", "a_sid", "vol", "ipaddr", "fwdaddr", "name");
	}
	/**
	 * @param tid 终端ID<br>
	 * @param status 终端状态：-1-不连通，0-空闲, >0-使用中<br>
	 * @param a_sid 活动会话ID<br>
	 * @param vol 音量：0~56，0最小，56最大<br>
	 * @param ipaddr 终端IP地址<br>
	 * C type : char[40]<br>
	 * @param fwdaddr 中继服务器IP地址<br>
	 * C type : char[40]<br>
	 * @param name 终端名称<br>
	 * C type : char[256]
	 */
	public TagTermAttr(NativeLong tid, int status, NativeLong a_sid, int vol, byte ipaddr[], byte fwdaddr[], byte name[]) {
		super();
		this.tid = tid;
		this.status = status;
		this.a_sid = a_sid;
		this.vol = vol;
		if ((ipaddr.length != this.ipaddr.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.ipaddr = ipaddr;
		if ((fwdaddr.length != this.fwdaddr.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.fwdaddr = fwdaddr;
		if ((name.length != this.name.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.name = name;
	}
	public TagTermAttr(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagTermAttr implements Structure.ByReference {
		
	};
	public static class ByValue extends TagTermAttr implements Structure.ByValue {
		
	};
}
