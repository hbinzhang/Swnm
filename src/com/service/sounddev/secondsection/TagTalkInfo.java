package com.service.sounddev.secondsection;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;


public class TagTalkInfo extends Structure {
	/** 主叫终端ID */
	public NativeLong master_tid;
	/** 被叫终端ID */
	public NativeLong slave_tid;
	/** 主叫终端呼叫状态：0-空闲，1-已接通，2-振铃，3-等待，4-挂断，5-占线，6-无应答，7-空号，8-呼叫受限，9-连接错误，11-版本错误，12-提示已接通 */
	public NativeLong master_status;
	/** 主叫终端呼叫状态：0-空闲，1-已接通，2-振铃，3-等待，4-挂断，5-占线，6-无应答，7-空号，8-呼叫受限，9-连接错误，11-版本错误，12-提示已接通 */
	public NativeLong slave_status;
	public TagTalkInfo() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("master_tid", "slave_tid", "master_status", "slave_status");
	}
	/**
	 * @param master_tid 主叫终端ID<br>
	 * @param slave_tid 被叫终端ID<br>
	 * @param master_status 主叫终端呼叫状态：0-空闲，1-已接通，2-振铃，3-等待，4-挂断，5-占线，6-无应答，7-空号，8-呼叫受限，9-连接错误，11-版本错误，12-提示已接通<br>
	 * @param slave_status 主叫终端呼叫状态：0-空闲，1-已接通，2-振铃，3-等待，4-挂断，5-占线，6-无应答，7-空号，8-呼叫受限，9-连接错误，11-版本错误，12-提示已接通
	 */
	public TagTalkInfo(NativeLong master_tid, NativeLong slave_tid, NativeLong master_status, NativeLong slave_status) {
		super();
		this.master_tid = master_tid;
		this.slave_tid = slave_tid;
		this.master_status = master_status;
		this.slave_status = slave_status;
	}
	public TagTalkInfo(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends TagTalkInfo implements Structure.ByReference {
		
	};
	public static class ByValue extends TagTalkInfo implements Structure.ByValue {
		
	};
}
