package com.service.sounddev.thirdsection;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public  class TagFileAttr extends Structure {
	/** 文件ID */
	public NativeLong fid;
	/** 文件属性：0-目录，1-文件 */
	public int attr;
	/** 时间长度（秒，目录为0） */
	public int length;
	/**
	 * 文件名称<br>
	 * C type : char[256]
	 */
	public byte[] name = new byte[256];
	public TagFileAttr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("fid", "attr", "length", "name");
	}
	/**
	 * @param fid 文件ID<br>
	 * @param attr 文件属性：0-目录，1-文件<br>
	 * @param length 时间长度（秒，目录为0）<br>
	 * @param name 文件名称<br>
	 * C type : char[256]
	 */
	public TagFileAttr(NativeLong fid, int attr, int length, byte name[]) {
		super();
		this.fid = fid;
		this.attr = attr;
		this.length = length;
		if ((name.length != this.name.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.name = name;
	}
	public TagFileAttr(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagFileAttr implements Structure.ByReference {
		
	};
	public static class ByValue extends TagFileAttr implements Structure.ByValue {
		
	};
}
