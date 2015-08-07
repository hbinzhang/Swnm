package com.service.sounddev.thirdsection;
import com.service.sounddev.thirdsection.TagPlayFile.ByReference;
import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.win32.StdCallLibrary;


/**
 * 三标段音频服务器DLL接口
 * @author maming
 * 2015-3-19上午10:03:04
 *
 */
public interface ThirdsectionAudioLibrary extends StdCallLibrary {
	public static final String JNA_LIBRARY_NAME = "IPCast_I"; 
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(ThirdsectionAudioLibrary.JNA_LIBRARY_NAME);
	public static final ThirdsectionAudioLibrary INSTANCE = (ThirdsectionAudioLibrary)Native.loadLibrary(ThirdsectionAudioLibrary.JNA_LIBRARY_NAME, ThirdsectionAudioLibrary.class);
	public static final int CSTATE_LIMITED = (int)8;
	public static final int MAX_TERM_IN_GROUP = (int)1024;
	public static final int PLAY_CTRL_PREV = (int)4;
	public static final int MAX_IPADDR = (int)40;
	public static final int CSTATE_IDLE = (int)0;
	public static final int MAX_NAME = (int)256;
	public static final int PLAY_CTRL_STOP = (int)1;
	public static final int CSTATE_EMPTY = (int)7;
	public static final int CSTATE_HUNGUP = (int)4;
	public static final int PLAY_CTRL_JUMPTIME = (int)7;
	public static final int CSTATE_SWITCH = (int)13;
	public static final int PLAY_CTRL_JUMPTO = (int)2;
	public static final int CSTATE_RING = (int)2;
	public static final int PLAY_CYC_DANORDE = (int)3;
	public static final int CSTATE_SPKPROMPT = (int)12;
	public static final int PLAY_CTRL_RESTORE = (int)6;
	public static final int MAX_CB_BUF = (int)1024;
	public static final int PLAY_CYC_DANCIRCLE = (int)2;
	public static final int PLAY_CYC_ALLCIRCLE = (int)4;
	public static final int PLAY_CYC_DAN = (int)1;
	public static final int PLAY_CTRL_NEXT = (int)3;
	public static final int CSTATE_FAILED = (int)10;
	public static final int CSTATE_WAIT_EX = (int)0x83;
	public static final int CSTATE_CONNECT = (int)1;
	public static final int CSTATE_BUSY = (int)5;
	public static final int CSTATE_ERROR = (int)9;
	public static final int CSTATE_VERERR = (int)11;
	public static final int PLAY_CTRL_PAUSE = (int)5;
	public static final int CSTATE_WAIT = (int)3;
	public static final int CSTATE_NOANSWER = (int)6;

	public int IPCastCallBack(int EventNo, String ParamStr);

	public boolean IPCAST_Connect(String ipAddr, String user, String pass);

	public boolean IPCAST_DisConnect();

	public boolean IPCAST_ServerStatus();

	public boolean IPCAST_SetCallBack(Callback pFunc);

	public int IPCAST_FilePlayStart(PointerByReference pFList, int fCount, NativeLongByReference pTList, int tCount, int Grade, int CycMode, int CycCount, int CycTime);

	public int IPCAST_FilePlayStart(ByReference pFList[], int fCount, NativeLongByReference pTList, int tCount, int Grade, int CycMode, int CycCount, int CycTime);

	public boolean IPCAST_FilePlayCtrl(NativeLong sid, int cmd, int pos);

	public boolean IPCAST_FileGetList(NativeLong fid, NativeLongByReference pFList, int nSize);

	public boolean IPCAST_FileGetListAll(NativeLongByReference pFList, int nSize);

	public boolean IPCAST_FileGetInfo(NativeLong fid, TagFileAttr pFAttr);

	public boolean IPCAST_RealPlayStart(int uMxId, int iItem, NativeLongByReference pTList, int tCount, int Grade, String bakFile);

	public boolean IPCAST_RealPlayStop(int uMxId);

	public boolean IPCAST_GetTermList(Pointer pTList, int nSize);

	public boolean IPCAST_GetTermStatus(NativeLong tid, TagTermAttr pTerm);

	public boolean IPCAST_GetSessionList(Pointer pSList, int nSize);

	public boolean IPCAST_GetSessionStatus(NativeLong sid, TagSessionAttr pSession);

	public boolean IPCAST_GetTermSessionList(NativeLong tid, NativeLongByReference pSList, int nSize);

	public boolean IPCAST_GetSessionTermList(NativeLong sid, NativeLongByReference pTList, int nSize);

	public boolean IPCAST_AddTermToSession(NativeLong tid, NativeLong sid);

	public boolean IPCAST_RemoveTermFromSession(NativeLong tid, NativeLong sid);

	public int IPCAST_GetTermByIPAddr(String ipAddr);

	public boolean IPCAST_RMSession(NativeLong sid);

	public boolean IPCAST_GetGroupList(NativeLongByReference pGList, int nSize);

	public boolean IPCAST_GetGroupAttr(NativeLong groupId, TagGroupAttr pGroup);

	public boolean IPCAST_GetTalkTermList(TagTalkInfoList pTalkInfo);

	public boolean IPCAST_SessionVol(NativeLong sid, NativeLong vol, NativeLong t_count, NativeLongByReference tids);

	public boolean IPCAST_PCListenStart(NativeLong tid);

	public boolean IPCAST_PCListenStop();

	public boolean IPCAST_MTListenStart(NativeLong mtid, NativeLong tid);

	public boolean IPCAST_MTListenStop(NativeLong mtid);

	public boolean IPCAST_GetTermVolume(NativeLong tid);

	public boolean IPCAST_SetTermVolume(NativeLong tid, int vol);

	public boolean IPCAST_SetTermPower(NativeLong tid, int pow);

	public boolean IPCAST_IOWrite(NativeLong tid, int iPort, boolean bOn);

	public boolean IPCAST_IORead(NativeLong tid, int iPort);

	public boolean IPCAST_Start_Talk(TagCallAddr from, TagCallAddr target);

	public boolean IPCAST_Stop_Talk(NativeLong tid);

	public boolean IPCAST_Start_Speech(TagCallAddr from, TagCallAddr targets, int target_number);

	public boolean IPCAST_Stop_Speech(NativeLong tid);

	public boolean IPCAST_StartSndRecord(NativeLong tid, Pointer ipAddr, short port);

	public boolean IPCAST_StartSndRecord(NativeLong tid, String ipAddr, short port);

	public boolean IPCAST_StopSndRecord(NativeLong tid, Pointer ipAddr, short port);

	public boolean IPCAST_StopSndRecord(NativeLong tid, String ipAddr, short port);

	public boolean IPCAST_SetAlarmSignal(NativeLong tid);

	public boolean IPCAST_ClearAlarmSignal(NativeLong tid, ThirdsectionAudioLibrary.HANDLE hSignal);
	public static class IPCastCallBack extends PointerType {
		public IPCastCallBack(Pointer address) {
			super(address);
		}
		public IPCastCallBack() {
			super();
		}
	};
	public static class HANDLE extends PointerType {
		public HANDLE(Pointer address) {
			super(address);
		}
		public HANDLE() {
			super();
		}
	};
}
