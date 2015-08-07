package com.service.sounddev.thirdsection;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public  class TagCallAddr extends Structure {
	/** 终端ID */
	public NativeLong tid;
	/** 终端面板号： 0 -- 终端主机；1 ~ 8 - 终端分控面板 */
	public int box_id;
	public TagCallAddr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("tid", "box_id");
	}
	/**
	 * @param tid 终端ID<br>
	 * @param box_id 终端面板号： 0 -- 终端主机；1 ~ 8 - 终端分控面板
	 */
	public TagCallAddr(NativeLong tid, int box_id) {
		super();
		this.tid = tid;
		this.box_id = box_id;
	}
	public TagCallAddr(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagCallAddr implements Structure.ByReference {
		
	};
	public static class ByValue extends TagCallAddr implements Structure.ByValue {
		
	};
}
