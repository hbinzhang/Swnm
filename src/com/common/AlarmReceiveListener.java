package com.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.service.videomonitor.IVAConfigSocketService;
import com.service.videomonitor.impl.AlarmIvaServiceImpl;

public class AlarmReceiveListener extends Thread implements ServletContextListener {

	private final static Log log = LogFactory.getLog(AlarmReceiveListener.class);
	private DatagramSocket ds = null;
	private DatagramPacket dp = null;
	private IVAConfigSocketService icss = null;
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
		destroySocket();
		log.info("报警接收socket正常关闭！");
		this.interrupt();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		if(this.isAlive()){
			this.interrupt();
		}
		this.start();
		String info = "报警接收线程启动！";
		log.info(info);
	}
	
	private AlarmIvaServiceImpl getAlarmIvaServiceObj(){
 
    	String path = this.getClass().getClassLoader().getResource("/").getPath();        
    	path = path.substring(0,path.indexOf("WEB-INF"));
		ApplicationContext ac = new FileSystemXmlApplicationContext(path
				+ "WEB-INF/Context-*.xml");
		AlarmIvaServiceImpl alarmIvaServiceImpl = (AlarmIvaServiceImpl) ac.getBean("AlarmIvaServiceImpl");
		
		return alarmIvaServiceImpl;
	}
	
	@Override
	public void run() {

		super.run();
		AlarmIvaServiceImpl alarmIvaServiceImpl = getAlarmIvaServiceObj();
		boolean isReceiving = false;
		boolean isNeedReconnect = false;
		int connectTimes = 0;
		SocketAddress sa = new InetSocketAddress(20000);
		do{
			try {
				if(isNeedReconnect){
					log.info("第" + connectTimes +"次重连");
				}
				if(ds == null){
					ds = new DatagramSocket(sa);
				}
				log.info("报警接收socket开始监听20000");
				isNeedReconnect = false;
				while(!this.isInterrupted()){
					isReceiving = true;
					byte[] data = new byte[1024 * 4];
					dp = new DatagramPacket(data, data.length);
					ds.receive(dp);
					String info = new String(dp.getData());
					log.info(info);
					try {
						JSONObject alarmObj = JSONObject.fromObject(info);
						//"{\"alarmType\":1,\"host\":\"10.3.29.60\",\"time\":\"2015-06-01 10:00:00\",\"preset\":1,\"pics\":[\"pic1.jpg\",\"pic2.jpg\",\"pic3.jpg\"]}"
						Integer alarmType = Integer.parseInt(alarmObj.getString("alarmType"));
						String host = alarmObj.getString("host");
						String time = alarmObj.getString("time");
						Integer preset = Integer.parseInt(alarmObj.getString("preset")==null?"0":alarmObj.getString("preset"));
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = df.parse(time);
						System.out.println(date);
						StringBuilder picssb = new StringBuilder();
						if(alarmObj.containsKey("pics")){
							JSONArray pics = alarmObj.getJSONArray("pics");
							if(pics != null){
								for(Object pic:pics.toArray()){
									if(pic == null){
										continue;
									}
									picssb.append(pic.toString() + ",");
								}
							}
						}
						//TODO:调用JMS上报接口
						alarmIvaServiceImpl.report(host, alarmType, date, preset,picssb.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error(e.getMessage());
					}
					
					/*if(icss == null){
						icss = new IVAConfigSocketServiceImpl();
					}
					icss.notifyIVAServer("10.3.29.23", 9002, "hello!");*/
				}
				if(ds != null && !ds.isClosed()){
					ds.close();
				}
			} catch(SocketException se){
				destroySocket();
				se.printStackTrace();
				log.error(se.getMessage());
				if(!this.isInterrupted()){
					isNeedReconnect = true;
					connectTimes++;
					try {
						Thread.sleep(connectTimes * 30 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}
			} catch(IOException ioe){
				ioe.printStackTrace();
				log.error(ioe.getMessage());
			}
		}while(isNeedReconnect && connectTimes < 4);
		
		String info = "报警接收线程正常退出！";
		if(connectTimes > 0){
			info = "报警接收线程连接" + connectTimes + "次后，退出！";
		}
		log.info(info);
	}
	
	private void destroySocket(){
		if(ds != null && !ds.isClosed()){
			ds.close();
			ds = null;
		}
	}

}
