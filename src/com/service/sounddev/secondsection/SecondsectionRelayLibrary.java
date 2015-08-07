package com.service.sounddev.secondsection;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.win32.StdCallLibrary;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * 二标段IO控制器DLL库接口
 * @author maming
 * 2015-3-31下午4:24:36
 *
 */
public interface SecondsectionRelayLibrary extends StdCallLibrary {
	public static final String JNA_LIBRARY_NAME = "c:/secondsection/Win32Dynic";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(SecondsectionRelayLibrary.JNA_LIBRARY_NAME);
	public static final SecondsectionRelayLibrary INSTANCE = (SecondsectionRelayLibrary)Native.loadLibrary(SecondsectionRelayLibrary.JNA_LIBRARY_NAME, SecondsectionRelayLibrary.class);
	
	int open();
	
	int readport(int addr);
	
	int writeport(int addr, int value);
	
	int InitWatchDog(int TimeType, int Time);
	
	int CloseWatchDog();
	
	@Deprecated 
	int Send_Recv_Input(Pointer pSend, int slen, Pointer pRecv, IntByReference rlen);
	
	int Send_Recv_Input(ByteBuffer pSend, int slen, ByteBuffer pRecv, IntBuffer rlen);
	
	@Deprecated 
	int INTEGTCP_Connect(Pointer szIP, short port, int iConnectionTimeout, int iSendTimeout, int iReceiveTimeout);
	
	int INTEGTCP_Connect(ByteBuffer szIP, short port, int iConnectionTimeout, int iSendTimeout, int iReceiveTimeout);
	
	@Deprecated 
	int INTEGTCP_ModuleDisconnect(Pointer strIP);
	
	int INTEGTCP_ModuleDisconnect(ByteBuffer strIP);
	
	@Deprecated 
	int INTEGTCP_Read6KAI(Pointer szIP, short wModule, short wIDAddr, ShortByReference wGain, ShortByReference wHex, DoubleByReference dlValue);
	
	int INTEGTCP_Read6KAI(ByteBuffer szIP, short wModule, short wIDAddr, ShortBuffer wGain, ShortBuffer wHex, DoubleBuffer dlValue);
	
	@Deprecated 
	int INTEGTCP_Read6KDIO(Pointer szIP, short wModule, short wIDAddr, Pointer byDI, Pointer byDO);
	
	int INTEGTCP_Read6KDIO(ByteBuffer szIP, short wModule, short wIDAddr, ByteBuffer byDI, ByteBuffer byDO);
}
