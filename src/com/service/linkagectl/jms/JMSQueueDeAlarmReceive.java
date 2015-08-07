package com.service.linkagectl.jms;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.entity.linkagectl.DeviceAlarm;
import com.nsbd.fence.FenceInfo;
import com.service.efence.IFenceService;
import com.service.linkagectl.IAlarmManager;
import com.service.videomonitor.AlarmManagerService;
import com.service.zone.IZoneService;

/**
 * This example shows how to establish a connection to
 * and receive messages from a JMS queue. The classes in this
 * package operate on the same JMS queue. Run the classes together to
 * witness messages being sent and received, and to browse the queue
 * for messages.  This class is used to receive and remove messages
 * from the queue.
 *
 * @author Copyright (c) 1999,2011, Oracle and/or its affiliates. All Rights Reserved.
 */
public class JMSQueueDeAlarmReceive implements MessageListener
{
 
	private IFenceService fenceManagerService;
	
	private IZoneService zoneService;
	
	private AlarmManagerService alarmManagerService;

	public AlarmManagerService getAlarmManagerService() {
		return alarmManagerService;
	}

	public void setAlarmManagerService(AlarmManagerService alarmManagerService) {
		this.alarmManagerService = alarmManagerService;
	}

	public IZoneService getZoneService() {
		return zoneService;
	}

	public void setZoneService(IZoneService zoneService) {
		this.zoneService = zoneService;
	}

	public IFenceService getFenceManagerService() {
		return fenceManagerService;
	}

	public void setFenceManagerService(IFenceService fenceManagerService) {
		this.fenceManagerService = fenceManagerService;
	}
private List<IAlarmManager> listDeAlarmManager =new ArrayList<IAlarmManager>();
private Log log = LogFactory.getLog(this.getClass());
public List<IAlarmManager> getListDeAlarmManager() {
	return listDeAlarmManager;
}

public void setListDeAlarmManager(List<IAlarmManager> listDeAlarmManager) {
	this.listDeAlarmManager = listDeAlarmManager;
}

 /**
  * Message listener interface.
  * @param msg  message
  */
  public void onMessage(Message msg)
  {
	//打印JMS消息
	  try {
		  log.info("接收一条新JMS-DeAlarm消息");
		  log.info("Message ID " + msg.getJMSMessageID() +
				  " delivered " + new Date(msg.getJMSTimestamp()) +
				  " to " + msg.getJMSDestination());
		  log.info("\tExpires        ");

		  if (msg.getJMSExpiration() > 0) {
			  log.info( new Date( msg.getJMSExpiration()));
			}
		  else {
				log.info("never");
				}

		  log.info("\tPriority       " + msg.getJMSPriority());
		  log.info("\tMode           " + (
						msg.getJMSDeliveryMode() == DeliveryMode.PERSISTENT ?
		                            "PERSISTENT" : "NON_PERSISTENT"));
		  log.info("\tCorrelation ID " + msg.getJMSCorrelationID());
		  log.info("\tReply to       " + msg.getJMSReplyTo());
		  log.info("\tMessage type   " + msg.getJMSType());
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				
			e1.printStackTrace();
			log.error(e1.getMessage(),e1);
			}	
	  	String msgText = "";
		double d = 0;
		 try {
			if (msg instanceof TextMessage) {   
			        msgText = ((TextMessage) msg).getText(); 
					processMsg(msgText);
			    } else if (msg instanceof StreamMessage) {   
			        msgText = ((StreamMessage) msg).readString();   
			        d = ((StreamMessage) msg).readDouble();   
			    } else if (msg instanceof BytesMessage) {   
			        byte[] block = new byte[1024];   
			        ((BytesMessage) msg).readBytes(block);   
			        msgText = String.valueOf(block);   
			    } else if (msg instanceof MapMessage) {   
			        msgText = ((MapMessage) msg).getString("name");   
			    }else if(msg instanceof ObjectMessage){ //接收对象消息   
			    	
			    	ObjectMessage message = (ObjectMessage)msg;   
	                processMsg( message );
	            }
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}   
		
		 log.info(msgText);	
	
  }

  private void processMsg(ObjectMessage message ) throws JMSException
  {
	  try{
		  Object msgBody = message.getObject();
		 /**
		 * 围栏状态上告信息   fenceManagerService属性通过Context-service.xml文件注入
		 * <property name="fenceManagerService" ref="fenceManagerServiceImpl" ></property>
		 */
		if (msgBody instanceof FenceInfo) {
			
			FenceInfo fence = (FenceInfo) message.getObject();
			try {
				fenceManagerService.updateFenceStatus(fence);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
			if(msgBody instanceof DeviceAlarm){
				processDeviceMsg( msgBody);
		  
			}
	  }
	  catch(RuntimeException e)
	  {
	 	  log.error(e.getMessage(),e);
	 	  throw e;
	  }
  }
  
 private void processDeviceMsg(Object msgBody){
	 try{		   
		 DeviceAlarm alarm = (DeviceAlarm)msgBody;   
		 if(null==alarm.getDeviceID() || null==alarm.getDepartmentID() || null==alarm.getBranchID() ||null==alarm.getAlarmTime()|| null==alarm.getAlarmCode()){			 
			 log.warn(" DeviceID  or DepartmentID or BranchID or alarmTime or alarmCode is null ,alarm discard!");
			 return;
		 } 
		 if(null==alarm.getAlarmStatus() ){
			 alarm.setAlarmStatus(0);
		 }
			 
		 if(listDeAlarmManager!=null){    	  
			 Iterator<IAlarmManager> iter = listDeAlarmManager.iterator();
     			
		  while(iter.hasNext())
		  {
			  IAlarmManager alarmManager=iter.next();
			  alarmManager.Execute(alarm);			
		  }
 		}
 	}

	 catch(RuntimeException e)
	 {
	  log.error(e.getMessage(),e);
	  throw e;
	 }
 	}
	/**
	 * 监听管理处重启消息
	 * @param message
	 * @throws JMSException
	 */
	private void processMsg(String message) throws JMSException {
		
		try {
			log.info("start execute fence handleRemoteJmsMsg,mng ip:"+message);
			//处理围栏信息
			fenceManagerService.handleRemoteJmsMsg(message);
			
			log.info("execute fence handleRemoteJmsMsg SUCCESS,mng ip:"+message);
			
			//处理防区信息
			log.info("start execute zone handleRemoteJmsMsg,mng ip:"+message);
			
			zoneService.handleRemoteJmsMsg(message);
			
			log.info("zone handleRemoteJmsMsg SUCCESS....");
			
			//处理告警信息
			log.info("start execute alarm handleRemoteJmsMsg,mng ip:"+message);
			
			alarmManagerService.handleRemoteJmsMsg(message);
			
			log.info("alarm handleRemoteJmsMsg SUCCESS....");
		} catch (Exception e) {
			log.info("handleRemoteJmsMsg FAILED...."+e);
		}
		
	}
		
	}



