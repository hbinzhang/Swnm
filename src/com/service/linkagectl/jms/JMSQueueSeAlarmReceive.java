package com.service.linkagectl.jms;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Date;
import java.util.Iterator;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Enumeration;
import javax.naming.NamingException;


import org.springframework.context.ApplicationContext;


import java.util.List;
import com.entity.linkagectl.*;
import com.service.linkagectl.*;
import com.service.linkagectl.impl.SaveSeAlarmImp;
import com.service.linkagectl.impl.SoundLinkManagerImp;
import com.service.linkagectl.impl.VidioLinkManagerImp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
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
public class JMSQueueSeAlarmReceive implements MessageListener
{
 

private List<IAlarmManager> listManager =new ArrayList<IAlarmManager>();
private Log log = LogFactory.getLog(this.getClass());
public List<IAlarmManager> getListManager() {
	return listManager;
}

public void setListManager(List<IAlarmManager> listManager) {
	this.listManager = listManager;
}
 /**
  * Message listener interface.
  * @param msg  message
  */
  public void onMessage(Message msg)
  {
	  
		//打印JMS消息
		try {
			log.info("接收一条新JMS-SeAlarm消息");
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
			        log.info(msgText);	
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
		
		 	
	
  }

  private void processMsg(ObjectMessage message ) throws JMSException
  {
	  try{
		  Alarm alarm = (SecurityAlarm)message.getObject();   
		   
		  if(null==alarm.getDeviceID() || null==alarm.getZoneID() 
				  || null==alarm.getDepartmentID() || null==alarm.getBranchID()|| null==alarm.getAlarmCode()||null==alarm.getAlarmTime() ){
			  log.warn(" DeviceID or ZoneID or DepartmentID or BranchID or alarmCode or alarmTime is null ,alarm discard!");
			  return;
		  }
		  if(null==alarm.getAlarmStatus() ){
				 alarm.setAlarmStatus(0);
			 }  
		  if(listManager!=null){    	  
			  Iterator<IAlarmManager> iter = listManager.iterator();
	      			
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
}




