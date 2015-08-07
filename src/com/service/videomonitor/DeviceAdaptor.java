package com.service.videomonitor;

import com.sun.jna.*;
import com.sun.jna.win32.StdCallLibrary;

public interface DeviceAdaptor extends Library {
	DeviceAdaptor instanceDll = (DeviceAdaptor) Native.loadLibrary(
			"DeviceAdaptor", DeviceAdaptor.class);

	public String devAdaptorVersion();

	public int devConfigNvrIPChnnl(String strCfg);

	public int devPTZControl(String strPtzCmd);

	public int devPTZCruiseGet(String strCmd, byte[] pCruise, int bufLen);

	public int devConfigSet(String strCmd);

	public int devConfigGet(String strCmd, byte[] pData, int bufLen);

	public int devConfigRestore(String strCmd);
}
