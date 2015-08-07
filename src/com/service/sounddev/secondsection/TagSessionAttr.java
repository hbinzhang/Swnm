package com.service.sounddev.secondsection;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public class TagSessionAttr extends Structure {
	/** 任务ID */
	public NativeLong sid;
	/** 任务状态：0-停止, 1-播放, 2-暂停, 4-关闭 */
	public int status;
	/** 会话类型：1-终端点播，2-定时任务，3-文件播放 */
	public int type;
	/**
	 * 4-声卡实时采播，5-双向对讲，6-报警触发任务<br>
	 * 任务优先级：0~999，999最大
	 */
	public int grade;
	/** 播放时间（秒） */
	public int t_play;
	/** 总时间（秒，采播任务为0） */
	public int t_total;
	/** 播放文件序号（采播任务为0） */
	public int iFile;
	/**
	 * 会话名称<br>
	 * C type : char[256]
	 */
	public byte[] name = new byte[256];
	public TagSessionAttr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("sid", "status", "type", "grade", "t_play", "t_total", "iFile", "name");
	}
	/**
	 * @param sid 任务ID<br>
	 * @param status 任务状态：0-停止, 1-播放, 2-暂停, 4-关闭<br>
	 * @param type 会话类型：1-终端点播，2-定时任务，3-文件播放<br>
	 * @param grade 4-声卡实时采播，5-双向对讲，6-报警触发任务<br>
	 * 任务优先级：0~999，999最大<br>
	 * @param t_play 播放时间（秒）<br>
	 * @param t_total 总时间（秒，采播任务为0）<br>
	 * @param iFile 播放文件序号（采播任务为0）<br>
	 * @param name 会话名称<br>
	 * C type : char[256]
	 */
	public TagSessionAttr(NativeLong sid, int status, int type, int grade, int t_play, int t_total, int iFile, byte name[]) {
		super();
		this.sid = sid;
		this.status = status;
		this.type = type;
		this.grade = grade;
		this.t_play = t_play;
		this.t_total = t_total;
		this.iFile = iFile;
		if ((name.length != this.name.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.name = name;
	}
	public TagSessionAttr(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagSessionAttr implements Structure.ByReference {
		
	};
	public static class ByValue extends TagSessionAttr implements Structure.ByValue {
		
	};
}
