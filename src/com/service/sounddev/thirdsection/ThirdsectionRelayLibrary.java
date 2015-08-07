package com.service.sounddev.thirdsection;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.ShortByReference;
import com.sun.jna.win32.StdCallLibrary;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;


/**
 * 三标段继电器控制器ADAM6052 DLL接口
 * @author maming
 * 2015-3-19上午10:14:31
 *
 */
public interface ThirdsectionRelayLibrary extends StdCallLibrary {
	public static final String JNA_LIBRARY_NAME = "ADAMTCP";
	public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(ThirdsectionRelayLibrary.JNA_LIBRARY_NAME);
	public static final ThirdsectionRelayLibrary INSTANCE = (ThirdsectionRelayLibrary)Native.loadLibrary(ThirdsectionRelayLibrary.JNA_LIBRARY_NAME, ThirdsectionRelayLibrary.class);
	public static final int ADAM5KTCP_CreateWsaEventFailure = (int)(-8);
	public static final int ADAMTCP_TYPE_LOALARM = (int)0x4000;
	public static final int ADAMTCP_UNI_0TO100C = (int)12;
	public static final int ADAM5KTCP_ExceedMaxFailure = (int)(-7);
	public static final int ADAMTCP_STATUS_LOST = (int)0xFFF;
	public static final int ADAM5KTCP_ReadStreamDataFailure = (int)(-9);
	public static final int ADAMTCP_CreateWsaEventFailure = (int)(-8);
	public static final int ADAMTCP_EventError = (int)(-100);
	public static final int ADAMTCP_SendFailure = (int)(-5);
	public static final int ADAMTCP_BI_10V = (int)0;
	public static final int ADAMTCP_BI_30TO120C = (int)15;
	public static final int ADAMTCP_BI_40TO160C = (int)16;
	public static final int ADAMTCP_ReadStreamDataFailure = (int)(-9);
	public static final int ADAMTCP_TypeE = (int)21;
	public static final int ADAM5KTCP_SocketFailure = (int)(-2);
	public static final int ADAM5KTCP_SetTimeoutFailure = (int)(-4);
	public static final int ADAMTCP_BI_200C = (int)19;
	public static final int ADAMTCP_TypeB = (int)20;
	public static final int ADAMTCP_UNI_0TO400C = (int)14;
	public static final int ADAMTCP_TYPE_AIO = (int)0x2000;
	public static final int ADAMTCP_BI_80TO120C = (int)18;
	public static final int ADAMTCP_InvalidIP = (int)(-10);
	public static final int ADAMTCP_NoError = (int)0;
	public static final int ADAM5KTCP_ReceiveFailure = (int)(-6);
	public static final int ADAMTCP_BI_50mV = (int)7;
	public static final int ADAM5KTCP_SendFailure = (int)(-5);
	public static final int ADAM5KTCP_AlarmInfoEmpty = (int)(-12);
	public static final int ADAMTCP_SocketFailure = (int)(-2);
	public static final int ADAMTCP_STATUS_OFF_TO_ON = (int)0x200;
	public static final int ADAMTCP_ExceedMaxFailure = (int)(-7);
	public static final int ADAM5KTCP_ThisIPNotConnected = (int)(-11);
	public static final int ADAMTCP_STATUS_NO_CHANGE = (int)0x000;
	public static final int ADAMTCP_BI_100mV = (int)6;
	public static final int ADAMTCP_ReceiveFailure = (int)(-6);
	public static final int ADAMTCP_AlarmInfoEmpty = (int)(-12);
	public static final int ADAM5KTCP_NoError = (int)0;
	public static final int ADAMTCP_UNI_0TO20mA = (int)10;
	public static final int ADAMTCP_BI_1V = (int)3;
	public static final int ADAMTCP_TYPE_DIO = (int)0x1000;
	public static final int ADAMTCP_BI_2Point5V = (int)2;
	public static final int ADAMTCP_BI_500mV = (int)4;
	public static final int ADAMTCP_NotSupportModule = (int)(-13);
	public static final int ADAM5KTCP_UdpSocketFailure = (int)(-3);
	public static final int ADAMTCP_StartupFailure = (int)(-1);
	public static final int ADAM5KTCP_StartupFailure = (int)(-1);
	public static final int ADAMTCP_BI_150mV = (int)5;
	public static final int ADAMTCP_BI_50TO150C = (int)17;
	public static final int ADAM5KTCP_ReceiveStreamIngoreAlarm = (int)0;
	public static final int ADAMTCP_InvalidRange = (int)(-15);
	public static final int ADAM5KTCP_ReceiveStreamWhenAlarm = (int)1;
	public static final int ADAMTCP_UNI_4TO20mA = (int)9;
	public static final int ADAMTCP_BI_5V = (int)1;
	public static final int ADAMTCP_UNI_0TO200C = (int)13;
	public static final int ADAMTCP_TypeK = (int)23;
	public static final int ADAMTCP_TypeJ = (int)22;
	public static final int ADAMTCP_ExceedDONo = (int)(-14);
	public static final int ADAMTCP_TYPE_HIALARM = (int)0x3000;
	public static final int ADAMTCP_TYPE_CONNECT = (int)0xF000;
	public static final int ADAMTCP_ReceiveStreamIngoreAlarm = (int)0;
	public static final int ADAMTCP_TypeT = (int)26;
	public static final int ADAMTCP_SetTimeoutFailure = (int)(-4);
	public static final int ADAMTCP_TypeS = (int)25;
	public static final int ADAMTCP_UdpSocketFailure = (int)(-3);
	public static final int ADAMTCP_TypeR = (int)24;
	public static final int ADAMTCP_STATUS_ON_TO_OFF = (int)0x100;
	public static final int ADAMTCP_ThisIPNotConnected = (int)(-11);
	public static final int ADAMTCP_ReceiveStreamWhenAlarm = (int)1;
	public static final int ADAM5KTCP_InvalidIP = (int)(-10);
	public static final int ADAMTCP_BI_20mA = (int)11;
	public static final int ADAMTCP_BI_15mV = (int)8;

	int ADAM5KTCP_Open();

	void ADAM5KTCP_Close();

	int ADAM5KTCP_Connect(Pointer szIP, short port, int iConnectionTimeout, int iSendTimeout, int iReceiveTimeout);

	int ADAM5KTCP_Connect(ByteBuffer szIP, short port, int iConnectionTimeout, int iSendTimeout, int iReceiveTimeout);

	void ADAM5KTCP_Disconnect();

	int ADAM5KTCP_ModuleDisconnect(Pointer szIP);

	int ADAM5KTCP_ModuleDisconnect(ByteBuffer szIP);

	int ADAM5KTCP_GetDLLVersion();

	int ADAM5KTCP_ReadReg(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, ShortByReference wData);

	int ADAM5KTCP_ReadReg(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ShortBuffer wData);

	int ADAM5KTCP_WriteReg(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, ShortByReference wData);

	int ADAM5KTCP_WriteReg(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ShortBuffer wData);

	int ADAM5KTCP_ReadCoil(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, Pointer byData);

	int ADAM5KTCP_ReadCoil(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ByteBuffer byData);

	int ADAM5KTCP_WriteCoil(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, Pointer byData);

	int ADAM5KTCP_WriteCoil(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ByteBuffer byData);

	int ADAM5KTCP_SendReceive5KTCPCmd(Pointer szIP, Pointer szSendToTCP, Pointer szReceiveFromTCP, Pointer szModbusSend, Pointer szModbusReceive);

	int ADAM5KTCP_SendReceive5KTCPCmd(ByteBuffer szIP, ByteBuffer szSendToTCP, ByteBuffer szReceiveFromTCP, ByteBuffer szModbusSend, ByteBuffer szModbusReceive);

	int ADAM5KTCP_Add5KTCPForStream(Pointer szIP);

	int ADAM5KTCP_Add5KTCPForStream(ByteBuffer szIP);

	int ADAM5KTCP_ReadStreamData(Pointer szIP, _StreamData pStreamData);

	int ADAM5KTCP_ReadStreamData(ByteBuffer szIP, _StreamData pStreamData);

	int ADAM5KTCP_ReadAlarmInfo(_AlarmInfo pAlarmInfo);

	int ADAM5KTCP_StartStream(ThirdsectionRelayLibrary.HANDLE EventFromApp);

	int ADAM5KTCP_StopStream();

	int ADAM5KTCP_SetStreamAlarmState(short wStreamAlarmState);

	int ADAM5KTCP_Debug(IntByReference iMatchIndex, IntByReference iReceiveCount, IntByReference iThreadRun, IntByReference iTotalStream, Pointer szFromIP);

	int ADAM5KTCP_Debug(IntBuffer iMatchIndex, IntBuffer iReceiveCount, IntBuffer iThreadRun, IntBuffer iTotalStream, ByteBuffer szFromIP);

	int ADAM5KTCP_UDPOpen(int iSendTimeout, int iReceiveTimeout);

	int ADAM5KTCP_UDPClose();

	int ADAM5KTCP_SendReceiveUDPCmd(Pointer szIP, Pointer szSend, Pointer szReceive);

	int ADAM5KTCP_SendReceiveUDPCmd(ByteBuffer szIP, ByteBuffer szSend, ByteBuffer szReceive);

	int ADAMTCP_Open();

	void ADAMTCP_Close();

	int ADAMTCP_Connect(Pointer szIP, short port, int iConnectionTimeout, int iSendTimeout, int iReceiveTimeout);

	int ADAMTCP_Connect(ByteBuffer szIP, short port, int iConnectionTimeout, int iSendTimeout, int iReceiveTimeout);

	void ADAMTCP_Disconnect();

	int ADAMTCP_ModuleDisconnect(Pointer szIP);

	int ADAMTCP_ModuleDisconnect(ByteBuffer szIP);

	int ADAMTCP_GetDLLVersion();

	int ADAMTCP_ReadReg(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, ShortByReference wData);

	int ADAMTCP_ReadReg(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ShortBuffer wData);

	int ADAMTCP_WriteReg(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, ShortByReference wData);

	int ADAMTCP_WriteReg(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ShortBuffer wData);

	int ADAMTCP_ReadCoil(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, Pointer byData);

	int ADAMTCP_ReadCoil(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ByteBuffer byData);

	int ADAMTCP_WriteCoil(Pointer szIP, short wIDAddr, short wStartAddress, short wCount, Pointer byData);

	int ADAMTCP_WriteCoil(ByteBuffer szIP, short wIDAddr, short wStartAddress, short wCount, ByteBuffer byData);

	int ADAMTCP_SendReceive5KTCPCmd(Pointer szIP, Pointer szSendToTCP, Pointer szReceiveFromTCP, Pointer szModbusSend, Pointer szModbusReceive);

	int ADAMTCP_SendReceive5KTCPCmd(ByteBuffer szIP, ByteBuffer szSendToTCP, ByteBuffer szReceiveFromTCP, ByteBuffer szModbusSend, ByteBuffer szModbusReceive);

	int ADAMTCP_SendReceive6KTCPCmd(Pointer szIP, Pointer szSendToTCP, Pointer szReceiveFromTCP, Pointer szModbusSend, Pointer szModbusReceive);

	int ADAMTCP_SendReceive6KTCPCmd(ByteBuffer szIP, ByteBuffer szSendToTCP, ByteBuffer szReceiveFromTCP, ByteBuffer szModbusSend, ByteBuffer szModbusReceive);

	int ADAMTCP_AddTCPForStream(Pointer szIP);

	int ADAMTCP_AddTCPForStream(ByteBuffer szIP);

	int ADAMTCP_ReadStreamData(Pointer szIP, _StreamData pStreamData);

	int ADAMTCP_ReadStreamData(ByteBuffer szIP, _StreamData pStreamData);

	int ADAMTCP_ReadAlarmInfo(_AlarmInfo pAlarmInfo);

	int ADAMTCP_StartStream(ThirdsectionRelayLibrary.HANDLE EventFromApp);

	int ADAMTCP_StopStream();

	int ADAMTCP_SetStreamAlarmState(short wStreamAlarmState);

	int ADAMTCP_Debug(IntByReference iMatchIndex, IntByReference iReceiveCount, IntByReference iThreadRun, IntByReference iTotalStream, Pointer szFromIP);

	int ADAMTCP_Debug(IntBuffer iMatchIndex, IntBuffer iReceiveCount, IntBuffer iThreadRun, IntBuffer iTotalStream, ByteBuffer szFromIP);

	int ADAMTCP_UDPOpen(int iSendTimeout, int iReceiveTimeout);

	int ADAMTCP_UDPClose();

	int ADAMTCP_SendReceiveUDPCmd(Pointer szIP, Pointer szSend, Pointer szReceive);

	int ADAMTCP_SendReceiveUDPCmd(ByteBuffer szIP, ByteBuffer szSend, ByteBuffer szReceive);

	int ADAMTCP_SendReceive6KUDPCmd(Pointer szIP, Pointer szSend, Pointer szReceive);

	int ADAMTCP_SendReceive6KUDPCmd(ByteBuffer szIP, ByteBuffer szSend, ByteBuffer szReceive);

	int ADAMTCP_Read6KDIO(Pointer szIP, short wModule, short wIDAddr, Pointer byDI, Pointer byDO);

	int ADAMTCP_Read6KDIO(ByteBuffer szIP, short wModule, short wIDAddr, ByteBuffer byDI, ByteBuffer byDO);

	int ADAMTCP_Write6KDO(Pointer szIP, short wModule, short wIDAddr, short wStartDO, short wCount, Pointer byDO);

	int ADAMTCP_Write6KDO(ByteBuffer szIP, short wModule, short wIDAddr, short wStartDO, short wCount, ByteBuffer byDO);

	int ADAMTCP_Read6KAI(Pointer szIP, short wModule, short wIDAddr, ShortByReference wGain, ShortByReference wHex, DoubleByReference dlValue);

	int ADAMTCP_Read6KAI(ByteBuffer szIP, short wModule, short wIDAddr, ShortBuffer wGain, ShortBuffer wHex, DoubleBuffer dlValue);

	int ADAMTCP_Read6KDIOMode(Pointer szIP, short wModule, short wIDAddr, Pointer byDIStatus, Pointer byDOStatus);

	int ADAMTCP_Read6KDIOMode(ByteBuffer szIP, short wModule, short wIDAddr, ByteBuffer byDIStatus, ByteBuffer byDOStatus);

	int ADAMTCP_Write6KDIOMode(Pointer szIP, short wModule, short wIDAddr, Pointer byDIStatus, Pointer byDOStatus);

	int ADAMTCP_Write6KDIOMode(ByteBuffer szIP, short wModule, short wIDAddr, ByteBuffer byDIStatus, ByteBuffer byDOStatus);

	int ADAMTCP_Read6KSignalWidth(Pointer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoWidth, NativeLongByReference ulHiWidth);

	int ADAMTCP_Read6KSignalWidth(ByteBuffer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoWidth, NativeLongByReference ulHiWidth);

	int ADAMTCP_Write6KSignalWidth(Pointer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoWidth, NativeLongByReference ulHiWidth);

	int ADAMTCP_Write6KSignalWidth(ByteBuffer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoWidth, NativeLongByReference ulHiWidth);

	int ADAMTCP_Read6KCounter(Pointer szIP, short wModule, short wIDAddr, NativeLongByReference ulCounterValue);

	int ADAMTCP_Read6KCounter(ByteBuffer szIP, short wModule, short wIDAddr, NativeLongByReference ulCounterValue);

	int ADAMTCP_Clear6KCounter(Pointer szIP, short wIDAddr, short wChIndex, short wData);

	int ADAMTCP_Clear6KCounter(ByteBuffer szIP, short wIDAddr, short wChIndex, short wData);

	int ADAMTCP_Start6KCounter(Pointer szIP, short wIDAddr, short wChIndex);

	int ADAMTCP_Start6KCounter(ByteBuffer szIP, short wIDAddr, short wChIndex);

	int ADAMTCP_Stop6KCounter(Pointer szIP, short wIDAddr, short wChIndex);

	int ADAMTCP_Stop6KCounter(ByteBuffer szIP, short wIDAddr, short wChIndex);

	int ADAMTCP_Clear6KDILatch(Pointer szIP, short wIDAddr, short wChIndex);

	int ADAMTCP_Clear6KDILatch(ByteBuffer szIP, short wIDAddr, short wChIndex);

	int ADAMTCP_Read6KPulseDelayWidth(Pointer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoPulseWidth, NativeLongByReference ulHiPulseWidth, NativeLongByReference ulLoDelayWidth, NativeLongByReference ulHiDelayWidth);

	int ADAMTCP_Read6KPulseDelayWidth(ByteBuffer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoPulseWidth, NativeLongByReference ulHiPulseWidth, NativeLongByReference ulLoDelayWidth, NativeLongByReference ulHiDelayWidth);

	int ADAMTCP_Read6KSinglePulseDelayWidth(Pointer szIP, short wModule, short wIDAddr, short wChIndex, NativeLongByReference ulLoPulseWidth, NativeLongByReference ulHiPulseWidth, NativeLongByReference ulLoDelayWidth, NativeLongByReference ulHiDelayWidth);

	int ADAMTCP_Read6KSinglePulseDelayWidth(ByteBuffer szIP, short wModule, short wIDAddr, short wChIndex, NativeLongByReference ulLoPulseWidth, NativeLongByReference ulHiPulseWidth, NativeLongByReference ulLoDelayWidth, NativeLongByReference ulHiDelayWidth);

	int ADAMTCP_Write6KPulseDelayWidth(Pointer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoPulseWidth, NativeLongByReference ulHiPulseWidth, NativeLongByReference ulLoDelayWidth, NativeLongByReference ulHiDelayWidth);

	int ADAMTCP_Write6KPulseDelayWidth(ByteBuffer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoPulseWidth, NativeLongByReference ulHiPulseWidth, NativeLongByReference ulLoDelayWidth, NativeLongByReference ulHiDelayWidth);

	int ADAMTCP_Write6KPulseDelayWidthRuntime(Pointer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoWidth, NativeLongByReference ulHiWidth);

	int ADAMTCP_Write6KPulseDelayWidthRuntime(ByteBuffer szIP, short wModule, short wIDAddr, NativeLongByReference ulLoWidth, NativeLongByReference ulHiWidth);

	int ADAMTCP_PulseOutputCount(Pointer szIP, short wModule, short wIDAddr, short wChannelIndex, NativeLong ulPulseCount);

	int ADAMTCP_PulseOutputCount(ByteBuffer szIP, short wModule, short wIDAddr, short wChannelIndex, NativeLong ulPulseCount);

	void ADAMTCP_InitializeEventTrigger();

	void ADAMTCP_TerminateEventTrigger();
 
	int ADAMTCP_AddModuleIP(Pointer szIP, int iAliveInterval);

	int ADAMTCP_AddModuleIP(ByteBuffer szIP, int iAliveInterval);

	int ADAMTCP_StartEventTrigger(ThirdsectionRelayLibrary.HANDLE EventFromApp);

	int ADAMTCP_StopEventTrigger();

	int ADAMTCP_ReadEventInfo(NativeLongByReference lIP, NativeLongByReference lSlot, NativeLongByReference lChannel, NativeLongByReference lType, NativeLongByReference lStatus, NativeLongByReference lValue, NativeLongByReference lDateTime);

	int ADAMTCP_ReadEventPattern(NativeLongByReference lIP, Pointer szPattern, IntByReference len);

	int ADAMTCP_ReadEventPattern(NativeLongByReference lIP, ByteBuffer szPattern, IntBuffer len);
	public static class HANDLE extends PointerType {
		public HANDLE(Pointer address) {
			super(address);
		}
		public HANDLE() {
			super();
		}
	};
}
