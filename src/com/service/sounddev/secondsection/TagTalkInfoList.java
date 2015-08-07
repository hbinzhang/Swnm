package com.service.sounddev.secondsection;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public class TagTalkInfoList extends Structure {
	/** 对讲数目 */
	public NativeLong talk_count;
	/**
	 * 对讲信息列表<br>
	 * C type : TalkInfo[1024]
	 */
	public TagTalkInfo[] talk_infos = new TagTalkInfo[1024];
	public TagTalkInfoList() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("talk_count", "talk_infos");
	}
	/**
	 * @param talk_count 对讲数目<br>
	 * @param talk_infos 对讲信息列表<br>
	 * C type : TalkInfo[1024]
	 */
	public TagTalkInfoList(NativeLong talk_count, TagTalkInfo talk_infos[]) {
		super();
		this.talk_count = talk_count;
		if ((talk_infos.length != this.talk_infos.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.talk_infos = talk_infos;
	}
	public TagTalkInfoList(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagTalkInfoList implements Structure.ByReference {
		
	};
	public static class ByValue extends TagTalkInfoList implements Structure.ByValue {
		
	};
}
