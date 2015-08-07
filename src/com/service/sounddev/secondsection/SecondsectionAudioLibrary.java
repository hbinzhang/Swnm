package com.service.sounddev.secondsection;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import com.service.sounddev.secondsection.TagPlayFile.ByReference;

/**
 * 二标段音频服务器DLL接口
 * 
 * @author maming 2015-4-1上午10:33:04
 * 
 */
public interface SecondsectionAudioLibrary extends Library {
	public static final String JNA_LIBRARY_NAME = "c:/secondsection/IPCast_I";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary
			.getInstance(SecondsectionAudioLibrary.JNA_LIBRARY_NAME);
	public static final SecondsectionAudioLibrary INSTANCE = (SecondsectionAudioLibrary) Native
			.loadLibrary(SecondsectionAudioLibrary.JNA_LIBRARY_NAME,
					SecondsectionAudioLibrary.class);
	public static final int CSTATE_LIMITED = (int) 8;
	public static final int MAX_TERM_IN_GROUP = (int) 1024;
	public static final int CB_TERM_STATE = (int) 2;
	public static final int TALK_HUNGUP = (int) 3;
	public static final int PLAY_CTRL_PREV = (int) 4;
	public static final int MAX_IPADDR = (int) 40;
	public static final int CSTATE_IDLE = (int) 0;
	public static final int SPEAK_HUNGUP = (int) 3;
	public static final int MAX_NAME = (int) 256;
	public static final int PLAY_CTRL_STOP = (int) 1;
	public static final int CSTATE_EMPTY = (int) 7;
	public static final int CSTATE_HUNGUP = (int) 4;
	public static final int PLAY_CTRL_JUMPTIME = (int) 7;
	public static final int CSTATE_SWITCH = (int) 13;
	public static final int CB_FILEPLAY_STATE = (int) 4;
	public static final int PLAY_CTRL_JUMPTO = (int) 2;
	public static final int CSTATE_RING = (int) 2;
	public static final int PLAY_CYC_DANORDE = (int) 3;
	public static final int CSTATE_SPKPROMPT = (int) 12;
	public static final int PLAY_CTRL_RESTORE = (int) 6;
	public static final int CB_IO_STATE = (int) 3;
	public static final int MAX_CB_BUF = (int) 1024;
	public static final int SPEAK_PROMPT = (int) 1;
	public static final int PLAY_CYC_DANCIRCLE = (int) 2;
	public static final int PLAY_CYC_ALLCIRCLE = (int) 4;
	public static final int PLAY_CYC_DAN = (int) 1;
	public static final int PLAY_CTRL_NEXT = (int) 3;
	public static final int SPEAK_CONNECT = (int) 2;
	public static final int CSTATE_FAILED = (int) 10;
	public static final int CSTATE_WAIT_EX = (int) 0x83;
	public static final int CSTATE_CONNECT = (int) 1;
	public static final int CSTATE_BUSY = (int) 5;
	public static final int CSTATE_ERROR = (int) 9;
	public static final int CB_SERVER_STATE = (int) 1;
	public static final int CB_TALK_STATE = (int) 5;
	public static final int CSTATE_VERERR = (int) 11;
	public static final int TALK_WAIT = (int) 1;
	public static final int TALK_CONNECT = (int) 2;
	public static final int PLAY_CTRL_PAUSE = (int) 5;
	public static final int CB_SPEAK_STATE = (int) 6;
	public static final int CSTATE_WAIT = (int) 3;
	public static final int CSTATE_NOANSWER = (int) 6;


	int IPCastCallBack(int EventNo, SecondsectionAudioLibrary.LPCTSTR ParamStr);


	int IPCastCallBack_Ex(int EventNo,
			SecondsectionAudioLibrary.LPCTSTR ParamStr, Pointer pUserContext);


	int IPCastTalkMonitorCallBack(int Length, Pointer pData,
			Pointer pUserContext);

	/**
	 * IPCAST_SDKInit 初始化API接口 函数
	 * 参数: 无 返回：
	 * 成功： TRUE 失败： FALSE
	 */
	boolean IPCAST_SDKInit();

	/**
	 * Original signature : <code>PASCAL IPCAST_SDKRelease()</code><br>
	 * <i>native declaration : line 212</i>
	 */
	boolean IPCAST_SDKRelease();

	/**
	 * Original signature : <code>PASCAL IPCAST_ExitStillPlay(BOOL)</code><br>
	 * <i>native declaration : line 220</i>
	 */
	boolean IPCAST_ExitStillPlay(boolean enable);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_Connect(LPCTSTR, LPCTSTR, LPCTSTR)</code><br>
	 * <i>native declaration : line 231</i>
	 */
	boolean IPCAST_Connect(String ipAddr,
			String user,
			String pass);

	/**
	 * Original signature : <code>PASCAL IPCAST_DisConnect()</code><br>
	 * <i>native declaration : line 240</i>
	 */
	boolean IPCAST_DisConnect();

	/**
	 * Original signature : <code>PASCAL IPCAST_ServerStatus()</code><br>
	 * <i>native declaration : line 250</i>
	 */
	boolean IPCAST_ServerStatus();

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_SetCallBack(IPCastCallBack*)</code><br>
	 * <i>native declaration : line 259</i>
	 */
	boolean IPCAST_SetCallBack(Callback pFunc);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_SetCallBack_Ex(IPCastCallBack_Ex*, void*)</code><br>
	 * <i>native declaration : line 268</i>
	 */
	boolean IPCAST_SetCallBack_Ex(
			SecondsectionAudioLibrary.IPCastCallBack_Ex pFunc,
			Pointer pUserContext);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_SetTalkMonitorCallBack(IPCastTalkMonitorCallBack*, void*)</code>
	 * <br>
	 * <i>native declaration : line 277</i>
	 */
	boolean IPCAST_SetTalkMonitorCallBack(
			SecondsectionAudioLibrary.IPCastTalkMonitorCallBack pFunc,
			Pointer pUserContext);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FilePlayStart(PlayFile*[], int, ULONG*, int, int, int, int, int)</code>
	 * <br>
	 * <i>native declaration : line 301</i><br>
	 * 
	 * @deprecated use the safer method
	 *             {@link #IPCAST_FilePlayStart(TagPlayFile.tagPlayFile.ByReference[], int, com.sun.jna.ptr.NativeLongByReference, int, int, int, int, int)}
	 *             instead
	 */
	@Deprecated
	boolean IPCAST_FilePlayStart(PointerByReference pFList, int fCount,
			NativeLongByReference pTList, int tCount, int Grade, int CycMode,
			int CycCount, int CycTime);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FilePlayStart(PlayFile*[], int, ULONG*, int, int, int, int, int)</code>
	 * <br>
	 * <i>native declaration : line 301</i>
	 */
	int IPCAST_FilePlayStart(ByReference pFList[], int fCount,
			NativeLongByReference pTList, int tCount, int Grade, int CycMode,
			int CycCount, int CycTime);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FilePlayStartLocal(const char*, const char*, int, int, int, int)</code>
	 * <br>
	 * <i>native declaration : line 322</i><br>
	 * 
	 * @deprecated use the safer methods
	 *             {@link #IPCAST_FilePlayStartLocal(java.lang.String, java.lang.String, int, int, int, int)}
	 *             and
	 *             {@link #IPCAST_FilePlayStartLocal(com.sun.jna.Pointer, com.sun.jna.Pointer, int, int, int, int)}
	 *             instead
	 */
	@Deprecated
	boolean IPCAST_FilePlayStartLocal(Pointer fileList, Pointer pTermList,
			int Grade, int CycMode, int CycCount, int CycTime);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FilePlayStartLocal(const char*, const char*, int, int, int, int)</code>
	 * <br>
	 * <i>native declaration : line 322</i>
	 */
	boolean IPCAST_FilePlayStartLocal(String fileList, String pTermList,
			int Grade, int CycMode, int CycCount, int CycTime);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FilePlayStartServer(const char*, const char*, int, int, int, int)</code>
	 * <br>
	 * <i>native declaration : line 342</i><br>
	 * 
	 * @deprecated use the safer methods
	 *             {@link #IPCAST_FilePlayStartServer(java.lang.String, java.lang.String, int, int, int, int)}
	 *             and
	 *             {@link #IPCAST_FilePlayStartServer(com.sun.jna.Pointer, com.sun.jna.Pointer, int, int, int, int)}
	 *             instead
	 */
	@Deprecated
	boolean IPCAST_FilePlayStartServer(Pointer pProgramList, Pointer pTermList,
			int Grade, int CycMode, int CycCount, int CycTime);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FilePlayStartServer(const char*, const char*, int, int, int, int)</code>
	 * <br>
	 * <i>native declaration : line 342</i>
	 */
	boolean IPCAST_FilePlayStartServer(String pProgramList, String pTermList,
			int Grade, int CycMode, int CycCount, int CycTime);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FilePlayCtrl(ULONG, int, int)</code><br>
	 * <i>native declaration : line 362</i>
	 */
	boolean IPCAST_FilePlayCtrl(NativeLong sid, int cmd, int pos);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FileGetList(ULONG, ULONG*, int)</code><br>
	 * <i>native declaration : line 375</i>
	 */
	boolean IPCAST_FileGetList(NativeLong fid, NativeLongByReference pFList,
			int nSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FileGetListAll(ULONG*, int)</code><br>
	 * <i>native declaration : line 385</i>
	 */
	boolean IPCAST_FileGetListAll(NativeLongByReference pFList, int nSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_FileGetInfo(ULONG, LPFileAttr)</code><br>
	 * <i>native declaration : line 397</i>
	 */
	boolean IPCAST_FileGetInfo(NativeLong fid, TagFileAttr pFAttr);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_RealPlayStart(UINT, UINT, ULONG*, int, int, LPCTSTR)</code>
	 * <br>
	 * <i>native declaration : line 413</i>
	 */
	boolean IPCAST_RealPlayStart(int uMxId, int iItem,
			NativeLongByReference pTList, int tCount, int Grade,
			SecondsectionAudioLibrary.LPCTSTR bakFile);

	/**
	 * Original signature : <code>PASCAL IPCAST_RealPlayStop(UINT)</code><br>
	 * <i>native declaration : line 424</i>
	 */
	boolean IPCAST_RealPlayStop(int uMxId);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_RealStreamStart(ULONG*, int, int)</code><br>
	 * <i>native declaration : line 435</i>
	 */
	boolean IPCAST_RealStreamStart(NativeLongByReference pTList, int tCount,
			int Grade);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_RealStreamTransmit(int, void*, int)</code><br>
	 * <i>native declaration : line 446</i>
	 */
	boolean IPCAST_RealStreamTransmit(int sid, Pointer pData, int Length);

	/**
	 * Original signature : <code>PASCAL IPCAST_RealStreamStop(int)</code><br>
	 * <i>native declaration : line 456</i>
	 */
	boolean IPCAST_RealStreamStop(int sid);

	/**
	 * Original signature : <code>PASCAL IPCAST_GetTermList(ULONG*, int)</code><br>
	 * <i>native declaration : line 466</i>
	 */
	boolean IPCAST_GetTermList(NativeLongByReference pTList, int nSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetTermStatus(ULONG, LPTermAttr)</code><br>
	 * <i>native declaration : line 477</i>
	 */
	boolean IPCAST_GetTermStatus(NativeLong tid, TagTermAttr pTerm);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetTermStatusEx(ULONG, LPTermAttrEx)</code><br>
	 * <i>native declaration : line 487</i>
	 */
	boolean IPCAST_GetTermStatusEx(NativeLong tid, TagTermAttrEx pTerm);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetSessionList(ULONG*, int)</code><br>
	 * <i>native declaration : line 496</i>
	 */
	boolean IPCAST_GetSessionList(NativeLongByReference pSList, int nSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetSessionStatus(ULONG, LPSessionAttr)</code><br>
	 * <i>native declaration : line 507</i>
	 */
	boolean IPCAST_GetSessionStatus(NativeLong sid, TagSessionAttr pSession);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetTermSessionList(ULONG, ULONG*, int)</code><br>
	 * <i>native declaration : line 518</i>
	 */
	boolean IPCAST_GetTermSessionList(NativeLong tid,
			NativeLongByReference pSList, int nSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetSessionTermList(ULONG, ULONG*, int)</code><br>
	 * <i>native declaration : line 529</i>
	 */
	boolean IPCAST_GetSessionTermList(NativeLong sid,
			NativeLongByReference pTList, int nSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_AddTermToSession(ULONG, ULONG)</code><br>
	 * <i>native declaration : line 540</i>
	 */
	boolean IPCAST_AddTermToSession(NativeLong tid, NativeLong sid);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_RemoveTermFromSession(ULONG, ULONG)</code><br>
	 * <i>native declaration : line 551</i>
	 */
	boolean IPCAST_RemoveTermFromSession(NativeLong tid, NativeLong sid);

	/**
	 * Original signature : <code>PASCAL IPCAST_GetTermByIPAddr(LPCTSTR)</code><br>
	 * <i>native declaration : line 561</i>
	 */
	int IPCAST_GetTermByIPAddr(String ipAddr);

	/**
	 * Original signature : <code>PASCAL IPCAST_RMSession(ULONG)</code><br>
	 * <i>native declaration : line 571</i>
	 */
	boolean IPCAST_RMSession(NativeLong sid);

	/**
	 * Original signature : <code>PASCAL IPCAST_GetGroupList(ULONG*, int)</code><br>
	 * <i>native declaration : line 581</i>
	 */
	boolean IPCAST_GetGroupList(NativeLongByReference pGList, int nSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetGroupAttr(ULONG, LPGroupAttr)</code><br>
	 * <i>native declaration : line 592</i>
	 */
	boolean IPCAST_GetGroupAttr(NativeLong groupId, TagGroupAttr pGroup);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_GetTalkTermList(LPTalkInfoList)</code><br>
	 * <i>native declaration : line 602</i>
	 */
	boolean IPCAST_GetTalkTermList(TagTalkInfoList pTalkInfo);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_SessionVol(ULONG, ULONG, ULONG, ULONG*)</code><br>
	 * <i>native declaration : line 615</i>
	 */
	boolean IPCAST_SessionVol(NativeLong sid, NativeLong vol,
			NativeLong t_count, NativeLongByReference tids);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_TermNumber2Id(const char*)</code><br>
	 * <i>native declaration : line 624</i><br>
	 * 
	 * @deprecated use the safer methods
	 *             {@link #IPCAST_TermNumber2Id(java.lang.String)} and
	 *             {@link #IPCAST_TermNumber2Id(com.sun.jna.Pointer)} instead
	 */
	@Deprecated
	boolean IPCAST_TermNumber2Id(Pointer number);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_TermNumber2Id(const char*)</code><br>
	 * <i>native declaration : line 624</i>
	 */
	boolean IPCAST_TermNumber2Id(String number);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_TermId2Number(ULONG, char*, int*)</code><br>
	 * <i>native declaration : line 635</i><br>
	 * 
	 * @deprecated use the safer methods
	 *             {@link #IPCAST_TermId2Number(com.sun.jna.NativeLong, java.nio.ByteBuffer, java.nio.IntBuffer)}
	 *             and
	 *             {@link #IPCAST_TermId2Number(com.sun.jna.NativeLong, com.sun.jna.Pointer, com.sun.jna.ptr.IntByReference)}
	 *             instead
	 */
	@Deprecated
	boolean IPCAST_TermId2Number(NativeLong tid, Pointer number,
			IntByReference buffSize);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_TermId2Number(ULONG, char*, int*)</code><br>
	 * <i>native declaration : line 635</i>
	 */
	boolean IPCAST_TermId2Number(NativeLong tid, ByteBuffer number,
			IntBuffer buffSize);

	/**
	 * Original signature : <code>PASCAL IPCAST_PCListenStart(ULONG)</code><br>
	 * <i>native declaration : line 645</i>
	 */
	boolean IPCAST_PCListenStart(NativeLong tid);

	/**
	 * Original signature : <code>PASCAL IPCAST_PCListenStop()</code><br>
	 * <i>native declaration : line 655</i>
	 */
	boolean IPCAST_PCListenStop();

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_MTListenStart(ULONG, ULONG)</code><br>
	 * <i>native declaration : line 666</i>
	 */
	boolean IPCAST_MTListenStart(NativeLong mtid, NativeLong tid);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_MTListenStartEx(ULONG, ULONG, int)</code><br>
	 * <i>native declaration : line 677</i>
	 */
	boolean IPCAST_MTListenStartEx(NativeLong mtid, NativeLong tid, int boxId);

	/**
	 * Original signature : <code>PASCAL IPCAST_MTListenStop(ULONG)</code><br>
	 * <i>native declaration : line 687</i>
	 */
	boolean IPCAST_MTListenStop(NativeLong mtid);

	/**
	 * Original signature : <code>PASCAL IPCAST_GetTermVolume(ULONG)</code><br>
	 * <i>native declaration : line 697</i>
	 */
	boolean IPCAST_GetTermVolume(NativeLong tid);

	/**
	 * Original signature : <code>PASCAL IPCAST_SetTermVolume(ULONG, int)</code><br>
	 * <i>native declaration : line 708</i>
	 */
	boolean IPCAST_SetTermVolume(NativeLong tid, int vol);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_StartSndRecord(ULONG, const char*, WORD)</code><br>
	 * <i>native declaration : line 730</i><br>
	 * 
	 * @deprecated use the safer methods
	 *             {@link #IPCAST_StartSndRecord(com.sun.jna.NativeLong, java.lang.String, short)}
	 *             and
	 *             {@link #IPCAST_StartSndRecord(com.sun.jna.NativeLong, com.sun.jna.Pointer, short)}
	 *             instead
	 */
	@Deprecated
	boolean IPCAST_StartSndRecord(NativeLong tid, Pointer ipAddr, short port);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_StartSndRecord(ULONG, const char*, WORD)</code><br>
	 * <i>native declaration : line 730</i>
	 */
	boolean IPCAST_StartSndRecord(NativeLong tid, String ipAddr, short port);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_StopSndRecord(ULONG, const char*, WORD)</code><br>
	 * <i>native declaration : line 742</i><br>
	 * 
	 * @deprecated use the safer methods
	 *             {@link #IPCAST_StopSndRecord(com.sun.jna.NativeLong, java.lang.String, short)}
	 *             and
	 *             {@link #IPCAST_StopSndRecord(com.sun.jna.NativeLong, com.sun.jna.Pointer, short)}
	 *             instead
	 */
	@Deprecated
	boolean IPCAST_StopSndRecord(NativeLong tid, Pointer ipAddr, short port);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_StopSndRecord(ULONG, const char*, WORD)</code><br>
	 * <i>native declaration : line 742</i>
	 */
	boolean IPCAST_StopSndRecord(NativeLong tid, String ipAddr, short port);

	/**
	 * Original signature : <code>PASCAL IPCAST_SetTermPower(ULONG, int)</code><br>
	 * <i>native declaration : line 753</i>
	 */
	boolean IPCAST_SetTermPower(NativeLong tid, int pow);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_SetTermPowerEx(ULONG*, int, int)</code><br>
	 * <i>native declaration : line 766</i>
	 */
	boolean IPCAST_SetTermPowerEx(NativeLongByReference pTList, int tCount,
			int pow);

	/**
	 * Original signature : <code>PASCAL IPCAST_IOWrite(ULONG, int, BOOL)</code><br>
	 * <i>native declaration : line 778</i>
	 */
	boolean IPCAST_IOWrite(NativeLong tid, int iPort, boolean bOn);

	/**
	 * Original signature : <code>PASCAL IPCAST_IORead(ULONG, int)</code><br>
	 * <i>native declaration : line 789</i>
	 */
	boolean IPCAST_IORead(NativeLong tid, int iPort);

	/**
	 * Original signature : <code>PASCAL IPCAST_IORead_Ex(ULONG, int)</code><br>
	 * <i>native declaration : line 800</i>
	 */
	boolean IPCAST_IORead_Ex(NativeLong tid, int iPort);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_IOWrite_Ex(ULONG, int, BOOL)</code><br>
	 * <i>native declaration : line 812</i>
	 */
	boolean IPCAST_IOWrite_Ex(NativeLong tid, int iPort, boolean bOn);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_Start_Talk(LPCallAddr, LPCallAddr)</code><br>
	 * <i>native declaration : line 825</i>
	 */
	boolean IPCAST_Start_Talk(TagCallAddr from, TagCallAddr target);

	/**
	 * Original signature : <code>PASCAL IPCAST_Stop_Talk(ULONG)</code><br>
	 * <i>native declaration : line 835</i>
	 */
	boolean IPCAST_Stop_Talk(NativeLong tid);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_Start_Speech(LPCallAddr, LPCallAddr, int)</code><br>
	 * <i>native declaration : line 850</i>
	 */
	boolean IPCAST_Start_Speech(TagCallAddr from, TagCallAddr targets,
			int target_number);

	/**
	 * Original signature : <code>PASCAL IPCAST_Stop_Speech(ULONG)</code><br>
	 * <i>native declaration : line 860</i>
	 */
	boolean IPCAST_Stop_Speech(NativeLong tid);

	/**
	 * Original signature : <code>PASCAL IPCAST_Accept_Call(ULONG)</code><br>
	 * <i>native declaration : line 870</i>
	 */
	boolean IPCAST_Accept_Call(NativeLong tid);

	/**
	 * Original signature : <code>PASCAL IPCAST_SetAlarmSignal(ULONG)</code><br>
	 * <i>native declaration : line 880</i>
	 */
	boolean IPCAST_SetAlarmSignal(NativeLong tid);

	/**
	 * Original signature :
	 * <code>PASCAL IPCAST_ClearAlarmSignal(ULONG, HANDLE)</code><br>
	 * <i>native declaration : line 890</i>
	 */
	boolean IPCAST_ClearAlarmSignal(NativeLong tid,
			SecondsectionAudioLibrary.HANDLE hSignal);

	public static class IPCastCallBack extends PointerType {
		public IPCastCallBack(Pointer address) {
			super(address);
		}

		public IPCastCallBack() {
			super();
		}
	};

	public static class LPCTSTR extends PointerType {
		public LPCTSTR(Pointer address) {
			super(address);
		}

		public LPCTSTR() {
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

	public static class IPCastCallBack_Ex extends PointerType {
		public IPCastCallBack_Ex(Pointer address) {
			super(address);
		}

		public IPCastCallBack_Ex() {
			super();
		}
	};

	public static class IPCastTalkMonitorCallBack extends PointerType {
		public IPCastTalkMonitorCallBack(Pointer address) {
			super(address);
		}

		public IPCastTalkMonitorCallBack() {
			super();
		}
	};
}
