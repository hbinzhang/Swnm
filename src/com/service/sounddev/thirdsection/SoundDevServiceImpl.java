package com.service.sounddev.thirdsection;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import com.service.sounddev.SoundDevStatus;
import com.service.sounddev.secondsection.SecondBaseCallBackFp;
import com.service.sounddev.secondsection.SecondsectionAudioLibrary;
import com.service.sounddev.secondsection.SecondsectionRelayLibrary;
import com.service.sounddev.thirdsection.TagPlayFile.ByReference;

import com.service.sounddev.thirdsection.ThirdsectionAudioLibrary;
import com.service.sounddev.thirdsection.ThirdsectionRelayLibrary;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.CHAR;
import com.sun.jna.ptr.NativeLongByReference;
import com.dao.devmgt.sound.ISoundDevDao;
import com.entity.devmgt.sound.AudioServer;
import com.entity.devmgt.sound.IOCtroller;
import com.entity.devmgt.sound.AudioAdapter;

/**
 * 音频模块功能提供
 * 
 * @author maming 2015-3-19下午1:13:41 二〇一五年五月二十九日 14:17:51 针对告警频繁上报做第二版本实现逻辑
 * 
 */
final class SoundDevServiceImpl extends SoundDevInnerService {

	private static final String FIRSTSECTIONFL_STRING = "EB1";
	private static final String SECONDSECTIONFL_STRING = "EB2";
	private static final String THIRDSECTIONFL_STRING = "EB3";

	static byte[] cSendClose = new byte[] { (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x06, (byte) 0x01,
			(byte) 0x05, (byte) 0x00, (byte) 0x10, (byte) 0xff, (byte) 0x00 };
	static byte[] cSendOpen = new byte[] { (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x06, (byte) 0x01,
			(byte) 0x05, (byte) 0x00, (byte) 0x10, (byte) 0x00, (byte) 0x00 };
	static ByteBuffer buffercSendClose = ByteBuffer.wrap(cSendClose);
	static ByteBuffer bufferccSendOpen = ByteBuffer.wrap(cSendOpen);
	static int IOCOMMANDLENGTH = 12;

//	/**
//	 * null/0未连接 1连接
//	 */
//	private static ConcurrentHashMap<String, Integer> devStatusMap = new ConcurrentHashMap<String, Integer>();

	static int sid;

	private Log log = LogFactory.getLog(this.getClass());
	/**
	 * 文件播放完毕回调
	 */
	static BaseCallBackFp thirdcallBack;
	static SecondBaseCallBackFp secondcallBack;

	WebApplicationContext wac = null;

	/**
	 * @param callFp
	 */
	public SoundDevServiceImpl(BaseCallBackFp thirdcallFp,
			SecondBaseCallBackFp secondcallFp) {
		thirdcallBack = thirdcallFp;
		thirdcallBack.setService(this);

		secondcallBack = secondcallFp;
		secondcallBack.setService(this);

		wac = ContextLoader.getCurrentWebApplicationContext();

		// 塞入三标段回调函数
		ThirdsectionAudioLibrary.INSTANCE
				.IPCAST_SetCallBack((CallBackFp) thirdcallBack);
		// 塞入二标段回调函数
		SecondsectionAudioLibrary.INSTANCE
				.IPCAST_SetCallBack((CallBackFp) secondcallBack);
	}

	@Deprecated
	/**
	 * 废弃不用，需要调用public synchronized void devPlay(List<String> list, String fileName,int level)
	 */
	public synchronized void devPlay(List<String> list, String fileName)
			throws ServletException {
		devPlay(list, fileName, 1);
	}

	/**
	 * 音频设备播放文件 (non-Javadoc)
	 * 
	 * @see com.service.sounddev.thirdsection.SoundDevService#devPlay(java.util.List,
	 *      java.lang.String)
	 */
	public synchronized void devPlay(List<String> list, String fileName,
			int level) throws ServletException {
		boolean isOk = true;
		log.info("devPlay(List<String> list, String fileName)");
		for (String termId : list) {
			// 根据终端id查询此套设备状态，工作或者比当前优先级小则丢弃
			if (!canEnter(termId, level))
				continue;
			String controllerIp = "";
			String vendorId = "";
			String termIp = "";
			try {
				// 根据音频终端id查控制器ip
				log.info("根据音频终端id查控制器ip");
				controllerIp = getIOControllerAddress(termId);
				// 根据终端id获取终端IP
				log.info("根据终端id获取终端IP");
				termIp = getTermAddress(termId);
				// 根据终端id查终端厂商
				log.info("根据终端id查终端厂商");
				vendorId = getManufacturerbyTermId(termId);
				if (vendorId.startsWith(FIRSTSECTIONFL_STRING)) {

				} else if (vendorId.startsWith(SECONDSECTIONFL_STRING)) {
					// IO控制器打开功放喇叭电源
					log.info("二标段IO控制器打开功放喇叭电源");
					secondcontrollPower(controllerIp, (byte) 1);
					// 音频终端播放文件
					log.info("二标段音频终端播放文件");
					secondtermPlay(termIp, fileName.trim(), termId);
				} else if (vendorId.startsWith(THIRDSECTIONFL_STRING)) {
					// IO控制器打开功放喇叭电源
					log.info("三标段IO控制器打开功放喇叭电源");
					thirdcontrollPower(controllerIp, (byte) 1);
					// 音频终端播放文件
					log.info("三标段音频终端播放文件");
					thirdtermPlay(termIp, fileName.trim(), termId);
				}
			} catch (Exception e) {
				// 有异常回滚设备
				log.info("回滚：" + "termId = " + termId + "termIp=" + termIp
						+ "controllerIp=" + controllerIp + "---");
				rollbackSoundDev(termId, termIp, controllerIp, vendorId);
				log.info(e);
				isOk = false;
			}
		}
		if (!isOk)
			throw new ServletException("音频设备播放存在异常");

	}

	/**
	 * 根据优先级判断是否能抢占
	 * 
	 * @param termId
	 * @param level
	 * @return
	 */
	private boolean canEnter(String termId, int level) {
		int oldLevel = SoundDevStatus.getInstance().getDevStatus(termId);
		if (level > oldLevel) {
			log.info("termId=" + termId + "level=" + level + ">" + oldLevel
					+ "抢占！");
			SoundDevStatus.getInstance().setDevStatus(termId, level);
			return true;
		} else {
			String controllerIp = "";
			String vendorId = "";
			String termIp = "";
			// 设备突然故障情况下，有可能留下上次的工作状态
			// 根据终端id获取终端IP
			termIp = getTermAddress(termId);
			controllerIp = getIOControllerAddress(termId);
			// 根据终端id查终端厂商
			vendorId = getManufacturerbyTermId(termId);
			if (vendorId.startsWith(FIRSTSECTIONFL_STRING)) {

			} else if (vendorId.startsWith(SECONDSECTIONFL_STRING)) {
				// 测试是否可达
				boolean state = false;
				try {
					InetAddress ad = InetAddress.getByName(controllerIp);

					state = ad.isReachable(5000);
					// 测试是否可以达到该地址
					if (state)
						log.info("二标段控制器连接成功" + ad.getHostAddress());
					else
						log.info("二标段控制器连接失败" + ad.getHostAddress());
				} catch (Exception e) {
					log.info("二标段控制器连接失败" + e);
				}
				if (!state) {
					// ByteBuffer bufferIp =
					// ByteBuffer.wrap(controllerIp.getBytes());
					// int isConnect = SecondsectionRelayLibrary.INSTANCE
					// .INTEGTCP_Connect(bufferIp, (short) 8000, 500, 500, 500);
					// if (isConnect < 0)
					SoundDevStatus.getInstance().reSetDev(termId);
				}
			} else if (vendorId.startsWith(THIRDSECTIONFL_STRING)) {
				// // 连接IO控制器
				// Pointer termPointer = new Memory(
				// 20 * Native.getNativeSize(CHAR.class));
				// termPointer.clear(20 * Native.getNativeSize(CHAR.class));
				// termPointer.setString(0, controllerIp);
				// int result =
				// ThirdsectionRelayLibrary.INSTANCE.ADAMTCP_Connect(
				// termPointer, (short) 502, 2000, 2000, 2000);
				// if (result != 0)
				// SoundDevStatus.getInstance().reSetDev(termId);
				// 测试是否可达
				boolean state = false;
				try {
					InetAddress ad = InetAddress.getByName(controllerIp);

					state = ad.isReachable(5000);
					// 测试是否可以达到该地址
					if (state)
						log.info("三标段控制器连接成功" + ad.getHostAddress());
					else
						log.info("三标段控制器连接失败" + ad.getHostAddress());
				} catch (Exception e) {
					log.info("三标段控制器连接失败" + e);
				}
				if (!state) {
					// ByteBuffer bufferIp =
					// ByteBuffer.wrap(controllerIp.getBytes());
					// int isConnect = SecondsectionRelayLibrary.INSTANCE
					// .INTEGTCP_Connect(bufferIp, (short) 8000, 500, 500, 500);
					// if (isConnect < 0)
					SoundDevStatus.getInstance().reSetDev(termId);
				}
			}
		}
		return false;
	}

	/**
	 * 存在异常回滚设备，设备复位
	 * 
	 * @param termId
	 */
	private synchronized void rollbackSoundDev(String termId, String termIp,
			String controllerIp, String vendorId) {
		try {
			SoundDevStatus.getInstance().reSetDev(termId);
			if (vendorId.startsWith(FIRSTSECTIONFL_STRING)) {

			} else if (vendorId.startsWith(SECONDSECTIONFL_STRING)) {
				// IO控制器打开功放喇叭电源
				secondcontrollPower(controllerIp, (byte) 0);
			} else if (vendorId.startsWith(THIRDSECTIONFL_STRING)) {
				thirdcontrollPower(controllerIp, (byte) 0);
			}
		} catch (Exception e) {
			log.info("IO控制器复位异常" + e);
		}

	}

	/**
	 * 根据终端Id获取厂商
	 * 
	 * @param termId
	 * @return
	 */
	private synchronized String getManufacturerbyTermId(String termId) {
		String vendorId;
		if (wac == null)
			wac = ContextLoader.getCurrentWebApplicationContext();
		ISoundDevDao userService = ((ISoundDevDao) wac.getBean("SoundDevImpl"));
		vendorId = ((AudioAdapter) userService.queryAdapterByTermId(termId))
				.getVendorId().trim();
		System.out
				.println("String getTermAddress(String vendorId)=" + vendorId);
		return vendorId;
	}

	/**
	 * 根据终端ID获取终端IP
	 * 
	 * @param termId
	 * @return
	 */
	private synchronized String getTermAddress(String termId) {
		String iPAddress;
		if (wac == null)
			wac = ContextLoader.getCurrentWebApplicationContext();
		ISoundDevDao userService = ((ISoundDevDao) wac.getBean("SoundDevImpl"));
		iPAddress = ((AudioAdapter) userService.queryAdapterByTermId(termId))
				.getAudioIp().trim();
		System.out.println("String getTermAddress(String termId)=" + iPAddress);
		return iPAddress;
		// return "10.3.17.253";
	}

	/**
	 * 根据终端ID获取它的控制器IP
	 * 
	 * @param termIp
	 *            (non-Javadoc)
	 * @see com.service.sounddev.thirdsection.SoundDevInnerService#getIOControllerAddress(java.lang.String)
	 */
	public synchronized String getIOControllerAddress(String termID) {
		log.info("getIOControllerAddress---------" + termID);
		String iPAddress = "";
		if (wac == null)
			wac = ContextLoader.getCurrentWebApplicationContext();
		ISoundDevDao userService = ((ISoundDevDao) wac.getBean("SoundDevImpl"));
		if (userService == null) {
			log.info("nullllllllllllllllllllllllllllllllllllllllllllllllllllllllllll");
		}
		IOCtroller ioCtroller = (IOCtroller) userService
				.queryControllerByTermId(termID);
		if (null != ioCtroller)
			iPAddress = ioCtroller.getIP().trim();
		log.info("--------------------iPAddress----------------==========="
				+ iPAddress);
		return iPAddress;
		// return "10.3.17.250";
	}

	/**
	 * 根据终端IP获取音频服务器IP
	 * 
	 * @return
	 */
	private String getAudioServerIp(String termIP) {
		String iPAddress;
		if (wac == null)
			wac = ContextLoader.getCurrentWebApplicationContext();
		iPAddress = ((AudioServer) ((ISoundDevDao) wac.getBean("SoundDevImpl"))
				.queryServerIpByTermIp(termIP)).IPADDRESS;
		// TODO 校验IP合法性
		return iPAddress;
		// return "10.3.17.81";
	}

	/**
	 * 三标段控制功放电源
	 * 
	 * openOrClose:1为闭合，0为断开
	 * 
	 * @return
	 * @throws Exception
	 *             (non-Javadoc)
	 * @see com.service.sounddev.thirdsection.SoundDevInnerService#controllPower(java.lang.String,
	 *      byte)
	 */
	public synchronized boolean thirdcontrollPower(String ipAddress,
			byte openOrClose) throws ServletException {
		int result = -9;

		// 连接IO控制器
		Pointer termPointer = new Memory(20 * Native.getNativeSize(CHAR.class));
		termPointer.clear(20 * Native.getNativeSize(CHAR.class));
		termPointer.setString(0, ipAddress);
		result = ThirdsectionRelayLibrary.INSTANCE.ADAMTCP_Connect(termPointer,
				(short) 502, 2000, 2000, 2000);
		log.info("三标段继电器连接------//" + result);
		if (result != 0) {
			log.error("三标段ip地址为" + ipAddress + "的IO控制器未能连接");
			throw new ServletException("三标段ip地址为" + ipAddress + "的IO控制器未能连接");
		}

		// 写0通道线圈
		byte[] CoilValue = new byte[1];
		CoilValue[0] = openOrClose;
		Pointer termpCoil = new Memory(1 * Native.getNativeSize(byte.class));
		termpCoil.clear(1 * Native.getNativeSize(byte.class));
		termpCoil.setByte(0, CoilValue[0]);
		result = ThirdsectionRelayLibrary.INSTANCE.ADAMTCP_Write6KDO(
				termPointer, (short) 6052, (short) 1, (short) 0, (short) 1,
				termpCoil);
		log.info("三标段继电器写线圈--" + "CoilValue[0]==" + CoilValue[0] + "----//结果是"
				+ result);
		if (result != 0) {
			log.error("三标段ip地址为" + ipAddress + "的IO控制器控制失败");
			throw new ServletException("三标段ip地址为" + ipAddress + "的IO控制器控制失败");
		}

		// 断开连接
		ThirdsectionRelayLibrary.INSTANCE.ADAMTCP_Disconnect();
		return true;
	}

	/**
	 * 三标段终端播放文件
	 * 
	 * @return
	 */
	private synchronized boolean thirdtermPlay(String termIp, String fileName,
			String termId) throws ServletException {
		log.info("三标段马上播放文件：" + "termIp = " + termIp + " fileName=" + fileName);
		String serverIpString = getAudioServerIp(termIp).trim();
		// 连接服务器
		boolean isConnect = ThirdsectionAudioLibrary.INSTANCE.IPCAST_Connect(
				serverIpString, "admin", "admin");
		log.info("三标段连接是否成功：" + isConnect);
		if (!isConnect)
			throw new ServletException("三标段服务器连接失败" + serverIpString);
		log.info("三标段待播放文件名：" + fileName);
		// 获取终端id
		long audiotermid = ThirdsectionAudioLibrary.INSTANCE
				.IPCAST_GetTermByIPAddr(termIp);
		log.info("三标段真实终端id：" + audiotermid);

		isConnect = ThirdsectionAudioLibrary.INSTANCE.IPCAST_ServerStatus();
		log.info("三标段服务器状态：" + isConnect);
		// 播放文件
		byte[] fname = new byte[256];
		System.arraycopy(fileName.trim().getBytes(), 0, fname, 0, fileName
				.trim().getBytes().length);
		ByReference file = new ByReference(0, fname, 10);
		ByReference[] files = new ByReference[1];
		files[0] = file;
		file.write();
		NativeLongByReference rf = new NativeLongByReference(new NativeLong(
				audiotermid));
		sid = ThirdsectionAudioLibrary.INSTANCE.IPCAST_FilePlayStart(files, 1,
				rf, 1, 500, ThirdsectionAudioLibrary.PLAY_CYC_DAN, 4, 8000);
		if (sid == -1) {
			log.error("三标段终端" + termIp + "播放文件失败");
			throw new ServletException("终端" + termIp + "播放文件失败");
		}
		// 记录会话对应的Id地址
		thirdcallBack.setThirdSidToTermId(sid, termId);
		// 记录终端ID对应的会话ID
		thirdcallBack.setThirdTermidTosid(termId, sid);
		thirdcallBack.setsidToFileName(sid, fileName);
		log.info("三标段服务器播放：" + sid);
		return true;
	}

	/**
	 * 二标段终端播放文件
	 * 
	 * @return
	 */
	private synchronized boolean secondtermPlay(String termIp, String fileName,
			String termId) throws ServletException {
		log.info("二标段马上播放文件：" + "termIp = " + termIp + " fileName=" + fileName);
		// 初始化
		SecondsectionAudioLibrary.INSTANCE.IPCAST_SDKInit();

		String serverIpString = getAudioServerIp(termIp).trim();
		// 连接服务器
		boolean isConnect = SecondsectionAudioLibrary.INSTANCE.IPCAST_Connect(
				serverIpString, "admin", "admin");
		log.info("二标段服务器" + serverIpString + "连接是否成功：" + isConnect);
		if (!isConnect)
			throw new ServletException("二标段服务器连接失败");
		// 获取终端id
		long termid = SecondsectionAudioLibrary.INSTANCE
				.IPCAST_GetTermByIPAddr(termIp);
		log.info("二标段终端id===" +termid);
		// 播放文件
		byte[] fname = new byte[256];
		System.arraycopy(fileName.getBytes(), 0, fname, 0,
				fileName.getBytes().length);
		com.service.sounddev.secondsection.TagPlayFile.ByReference file = new com.service.sounddev.secondsection.TagPlayFile.ByReference(
				0, fname, 10);
		com.service.sounddev.secondsection.TagPlayFile.ByReference[] files = new com.service.sounddev.secondsection.TagPlayFile.ByReference[1];
		files[0] = file;
		file.write();
		NativeLongByReference rf = new NativeLongByReference(new NativeLong(
				termid));
		sid = SecondsectionAudioLibrary.INSTANCE.IPCAST_FilePlayStart(files, 1,
				rf, 1, 500, ThirdsectionAudioLibrary.PLAY_CYC_DAN, 4, 8000);
		if (sid == -1) {
			log.error("二标段终端" + termIp + "播放文件失败");
			throw new ServletException("二标段终端" + termIp + "播放文件失败");
		}

		// 记录会话ID对应的终端ID
		secondcallBack.setSecondSidToTermId(sid, termId);
		// 记录终端ID对应的会话ID
		secondcallBack.setSecondTermidTosid(termId, sid);
		secondcallBack.setSecondsidToFileName(sid, fileName);
		log.info("二标段服务器播放：" + sid);
		return true;
	}

	/**
	 * 二标段控制功放电源
	 * 
	 * openOrClose:1为闭合，0为断开
	 * 
	 */
	public synchronized boolean secondcontrollPower(String controllerIp, byte b)
			throws ServletException {
		ByteBuffer bufferIp = ByteBuffer.wrap(controllerIp.getBytes());
		int ret;
		SecondsectionRelayLibrary.INSTANCE.INTEGTCP_ModuleDisconnect(bufferIp);
		log.info("begin DisConnect------------------");
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int isConnect = SecondsectionRelayLibrary.INSTANCE.INTEGTCP_Connect(
				bufferIp, (short) 8000, 500, 500, 500);
		if (isConnect < 0) {
			log.error("二标段ip地址为" + controllerIp + "的IO控制器未能连接");
			// throw new ServletException("二标段ip地址为" + controllerIp +
			// "的IO控制器未能连接");
		} else {
//			devStatusMap.put(controllerIp, 1);
			log.info("二标段ip地址为" + controllerIp + "的IO控制器连接ok");
		}
		byte[] rbuf = new byte[IOCOMMANDLENGTH];
		for (int i = 0; i < rbuf.length; i++) {
			rbuf[i] = '\0';
		}
		ByteBuffer bufferR = ByteBuffer.wrap(rbuf);
		IntBuffer bint = IntBuffer.allocate(IOCOMMANDLENGTH);
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (b == 1)
			ret = SecondsectionRelayLibrary.INSTANCE.Send_Recv_Input(
					buffercSendClose, IOCOMMANDLENGTH, bufferR, bint);
		else
			ret = SecondsectionRelayLibrary.INSTANCE.Send_Recv_Input(
					bufferccSendOpen, IOCOMMANDLENGTH, bufferR, bint);
		try {
			Thread.currentThread().sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ret < 0) {
			log.error("二标段ip地址为" + controllerIp + "的IO控制器控制失败");
			int isDisConnect = SecondsectionRelayLibrary.INSTANCE
					.INTEGTCP_ModuleDisconnect(bufferIp);
			log.info("isDisConnect------------------" + isDisConnect);
			// throw new ServletException("二标段ip地址为" + controllerIp +
			// "的IO控制器控制失败");

		} else {
			log.info("二标段ip地址为" + controllerIp + "的IO控制器控制OK");
			int isDisConnect = SecondsectionRelayLibrary.INSTANCE
					.INTEGTCP_ModuleDisconnect(bufferIp);
			log.info("二标段isDisConnect------------------" + isDisConnect);
		}

		return true;

	}

	@Override
	public synchronized boolean firstcontrollPower(String ipAddress,
			byte openOrClose) throws ServletException {
		// TODO Auto-generated method stub
		return false;
	}

}
