package com.service.sounddev.thirdsection;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public  class TagGroupAttr extends Structure {
	/** 分组ID */
	public NativeLong gid;
	/**
	 * 分组名称<br>
	 * C type : char[256]
	 */
	public byte[] name = new byte[256];
	/** 终端数目 */
	public int t_count;
	/**
	 * 终端ID表<br>
	 * C type : ULONG[1024]
	 */
	public NativeLong[] tids = new NativeLong[1024];
	public TagGroupAttr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("gid", "name", "t_count", "tids");
	}
	/**
	 * @param gid 分组ID<br>
	 * @param name 分组名称<br>
	 * C type : char[256]<br>
	 * @param t_count 终端数目<br>
	 * @param tids 终端ID表<br>
	 * C type : ULONG[1024]
	 */
	public TagGroupAttr(NativeLong gid, byte name[], int t_count, NativeLong tids[]) {
		super();
		this.gid = gid;
		if ((name.length != this.name.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.name = name;
		this.t_count = t_count;
		if ((tids.length != this.tids.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.tids = tids;
	}
	public TagGroupAttr(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagGroupAttr implements Structure.ByReference {
		
	};
	public static class ByValue extends TagGroupAttr implements Structure.ByValue {
		
	};
}
