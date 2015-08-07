package com.service.sounddev.secondsection;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public class TagPlayFile extends Structure {
	/** 文件序号（序号小于等于0则取全路径） */
	public int fid;
	/**
	 * 文件名或全路径名<br>
	 * C type : char[256]
	 */
	public byte[] fname = new byte[256];
	/** 文件音量放大倍数(除以10倍) */
	public int fvol;
	public TagPlayFile() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("fid", "fname", "fvol");
	}
	/**
	 * @param fid 文件序号（序号小于等于0则取全路径）<br>
	 * @param fname 文件名或全路径名<br>
	 * C type : char[256]<br>
	 * @param fvol 文件音量放大倍数(除以10倍)
	 */
	public TagPlayFile(int fid, byte fname[], int fvol) {
		super();
		this.fid = fid;
		if ((fname.length != this.fname.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.fname = fname;
		this.fvol = fvol;
	}
	public TagPlayFile(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagPlayFile implements Structure.ByReference {
		public ByReference(int fid, byte fname[], int fvol) {
			super(fid,fname,fvol);
		}
	};
	public static class ByValue extends TagPlayFile implements Structure.ByValue {
		public ByValue(int fid, byte fname[], int fvol) {
			super(fid,fname,fvol);
		}
	};
}
