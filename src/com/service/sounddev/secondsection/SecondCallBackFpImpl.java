package com.service.sounddev.secondsection;

import java.io.File;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.service.sounddev.SoundDevStatus;
import com.service.sounddev.thirdsection.SoundDevInnerService;

public class SecondCallBackFpImpl extends  SecondBaseCallBackFp {
	 
	 private Log log = LogFactory.getLog(this.getClass());	
	
	public SecondCallBackFpImpl(SoundDevInnerService service) {
		super(service);
	}
	
	public SecondCallBackFpImpl() {
	}
	/*-------------------------------------------------------------------------------------------------
	回调函数指针定义：
		事件回调函数，参数缓冲区不小于MAX_CB_BUF(1024)字节
		EventNo:
			1：服务器状态变化
				ParamStr: state=%d
						  state: 0-无法连接或未启动, 1-正常连接
			2：终端状态变化
				ParamStr: tid=%d, state=%d
						  tid: 终端id
						  state: -1-不连通，0-空闲, >0-使用中
			3：终端IO端口输入状态变化
				ParamStr：tid=%d, %iport%=%d
						  tid: 终端id
						  %iport%: 1~8, 值：0-断开, 1-连通
			4：文件播放会话状态变化
				ParamStr：sid=%d, state=%d, ifile=%d, tplay=%d, ttotal=%d
						  sid: 文件播放会话ID
						  state: 0-停止, 1-播放, 2-暂停, 3-文件末尾, 4-任务关闭
						  ifile: 当前播放文件序号
						  tplay: 当前文件当前播放时间
						  ttotal: 当前文件总时间

	-------------------------------------------------------------------------------------------------*/
	public int invoke(int EventNo, String ParamStr) {
		log.info("二标段系统时间："+System.currentTimeMillis()+"   "+"EventNo="+EventNo+"  ********  "+ParamStr);
		if (4 == EventNo) {
			if (ParamStr.indexOf("state=3") != -1) {
				for (String elem : ParamStr.split(",")) {
					if (elem.indexOf("sid=") != -1) {
						try {
							int sid = Integer.valueOf(elem.split("=")[1]);
							//没有被抢占
							if(!isByEnter(sid))
							{
								String termid = secondSidToTermId.get(sid);
								log.info("获取二标段controllerIp");
								String controllerIp = secondservice
										.getIOControllerAddress(termid);
								log.info("断开二标段controllerIp");
								secondservice.secondcontrollPower(controllerIp,
										(byte) 0);
								//标识状态空闲
								SoundDevStatus.getInstance().setDevStatus(termid, 0);
							}
							deleteTempSoundFile(secondSidToFileName.get(Integer
									.valueOf(elem.split("=")[1])));
							
						} catch (ServletException e) {
							e.printStackTrace();
						}
					}
				}

			}

		}
		return -88;
	}
	
	/**
	 * 是否被抢占
	 * @param sid
	 * @return
	 */
	private boolean isByEnter(int sid) {
		if(null != secondSidToTermId.get(sid)){
			String termId = secondSidToTermId.get(sid);
			if(null != secondTermIdToSid.get(termId)){
				int tempsid = secondTermIdToSid.get(termId);
				if(tempsid == sid)
					return false;
			}
		}
		log.info("二标段sid=="+sid+"对应终端被抢占！");
		return true;
	}

	/**
	 * 删除临时声音文件
	 * @param fileName
	 */
	public void deleteTempSoundFile(String fileName){
		File tempFile = new File(fileName);
		if(tempFile.exists()&& isNumeric(trimExtension(tempFile.getName()))){
			tempFile.delete();
		}
		
	}
	
    public String trimExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i >-1) && (i < (filename.length()))) {
                return filename.substring(0, i);
            }
        }
        return filename;
    }
    
    public boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[0-9]*"); 
        return pattern.matcher(str).matches();    
   } 
	
}
