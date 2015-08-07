package com.service.linkagectl.jms;
import com.entity.linkagectl.*;

import java.util.Calendar;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JMSSeAlarmSender {

	/**
	 * @param args
	 * @throws NamingException 
	 * @throws JMSException 
	 */
	public static void main(String[] args) throws NamingException, JMSException {
		//init JNDI context
		String JNDIFactory = "weblogic.jndi.WLInitialContextFactory";//define JNDI context factory
		String providerUrl = "t3://127.0.0.1:7001"; //define weblogic JMS url
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDIFactory);
		env.put(Context.PROVIDER_URL, providerUrl);
		Context ctx = new InitialContext(env);
		
		//find connection factory
		String connFactoryJNDI = "jms/ConnectionFactory-nsbd"; //jms connectionFactory JNDI name
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.lookup(connFactoryJNDI);
		//create queue connection
		QueueConnection qConn = (QueueConnection) connFactory.createConnection();
		//create session
		QueueSession qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		//find queue by JNDI lookup////////////////////////////////////////////////////////////////////////////////////////////////////
		Queue queue = (Queue) ctx.lookup("jms/Queue-sealarm-nsbd");
		//create sender
		QueueSender qSender = qSession.createSender(queue);
		//create message
		//对象消息   
		
		//Alarm alarm = new SecurityAlarm(1, 2,cal1,"123",1,2,3,(short)1); //alarm对象必须实现Serializable接口   
	    Alarm alarm = new SecurityAlarm();
	    alarm.setAlarmID(8);
	    alarm.setZoneID(10053);
	    alarm.setBranchID("010300");
	    alarm.setDepartmentID("010309");
	    alarm.setDeviceID("1");
	    alarm.setAlarmCode(103);
	    alarm.setAlarmTime(new java.util.Date());
	    alarm.setManagerTime(new java.util.Date());
	    ObjectMessage objectMessage = qSession.createObjectMessage(); 
		objectMessage.setObject(alarm);   
		/*
		Message msg = qSession.createTextMessage("Message is from JMS Sender!");
		
		qSender.send(msg);*/
		
		qSender.send(objectMessage);
		qSender.close();
		
		qSession.close();
		
		qConn.close();
		
	}

}

/*
 package org.springframework.samples.jpetstore.jms;  
  
import org.springframework.jms.core.JmsTemplate;  
  
public class JmsProducer {  
  
    private JmsTemplate jmsTemplate;  
  
    public JmsProducer(JmsTemplate jmsTemplate) {  
            this.jmsTemplate = jmsTemplate;  
    }  
  
    public void sendMessage() {  
            jmsTemplate.convertAndSend("Hello world!("+System.currentTimeMillis()+")");  
    }  
}  
 */
