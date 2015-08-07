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
public class JMSQueueReceiveOld implements MessageListener
{
  // Defines the JNDI context factory.
  public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

  // Defines the JMS connection factory for the queue.
  public final static String JMS_FACTORY="jms/ConnectionFactory-nsbd";

  // Defines the queue.
  public final static String QUEUE="jms/Queue-sealarm-nsbd";
  public final static String providerUrl = "t3://localhost:7001"; // define weblogic JMS url

  private QueueConnectionFactory qconFactory;
  private QueueConnection qcon;
  private QueueSession qsession;
  private QueueReceiver qreceiver;
  private QueueBrowser qbrowser;
  private Queue queue;
  private boolean quit = false;
  
 

private List<IAlarmManager> listManager = new ArrayList<IAlarmManager>();

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
	 	String msgText = "";
		double d = 0;
		 try {
			if (msg instanceof TextMessage) {   
			        msgText = ((TextMessage) msg).getText();   
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
		}   
		
		System.out.println(msgText);
	
  }

  private void processMsg(ObjectMessage message ) throws JMSException
  {
	  Alarm alarm = (SecurityAlarm)message.getObject();   
      System.out.println(alarm.getAlarmID()+ " _ "+alarm.getDeviceID() ); 
     /*
      if(listManager!=null){    	  
	      Iterator<IAlarmManager> iter = listManager.iterator();
	      			
			while(iter.hasNext())
			{
				IAlarmManager alarmManager=iter.next();
				alarmManager.Execute(alarm);			
			}
      }
      */
  }
  
  /**
   * Creates all the necessary objects for receiving
   * messages from a JMS queue.
   *
   * @param   ctx	JNDI initial context
   * @param	queueName	name of queue
   * @exception NamingException if operation cannot be performed
   * @exception JMSException if JMS fails to initialize due to internal error
   */
  public void init(Context ctx, String queueName)
    throws NamingException, JMSException
  {
	// 得到一个JNDI初始化上下文(Context)
	// 根据上下文来查找一个连接工厂TopicConnectFactory/ QueueConnectionFactory (有两种连接工厂，根据是topic/queue来使用相应的类型)。
    qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
    // 从连接工厂得到一个连接(Connect 有两种[TopicConnection/ QueueConnection])。
    qcon = qconFactory.createQueueConnection();
    // 通过连接来建立一个会话(Session) 
    qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    //查找目的地(Topic/ Queue) 
    queue = (Queue) ctx.lookup(queueName);
    //根据会话以及目的地来建立消息制造者(TopicPublisher/QueueSender)和消费者(TopicSubscriber/ QueueReceiver)。
    
    qreceiver = qsession.createReceiver(queue);   
    qreceiver.setMessageListener(this);
    //创建消息浏览
    qbrowser = qsession.createBrowser(queue);
    qcon.start();
  }

  /**
   * Closes JMS objects.
   * @exception JMSException if JMS fails to close objects due to internal error
   */
  public void close()throws JMSException
  {
    qreceiver.close();    
    qsession.close();
    qcon.close();
  }
  
  
  
/**
  * main() method.
  *
  * @param args  WebLogic Server URL
  * @exception  Exception if execution fails
  */

  public static void main(String[] args) throws Exception {
    
	 
	  //ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Context-service.xml");
    InitialContext ic = getInitialContext(providerUrl);
    JMSQueueReceiveOld qr = new JMSQueueReceiveOld();
    qr.init(ic, QUEUE);

    System.out.println("JMS Ready To Receive Messages (To quit, send a \"quit\" message).");
    
    IAlarmManager alarm1=new SaveSeAlarmImp();
    IAlarmManager alarm2=new VidioLinkManagerImp();
    
    IAlarmManager alarm3=new SoundLinkManagerImp();
    qr.getListManager().add(alarm1);
    qr.getListManager().add(alarm2);
    qr.getListManager().add(alarm3);
    
    // Wait until a "quit" message has been received.
    synchronized(qr) {
    	
      while (! qr.quit) {
        try {
          qr.wait();
        } catch (InterruptedException ie) {}
      }
    }
    qr.close();
  }

  private static InitialContext getInitialContext(String url)
    throws NamingException
  {
	//首先关于初始化上下文（Context）
    Hashtable<String,String> env = new Hashtable<String,String>();
    env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
    env.put(Context.PROVIDER_URL, url);
    return new InitialContext(env);
  }
/*
public void contextDestroyed(ServletContextEvent sce) {
	// TODO Auto-generated method stub
	
}

public void contextInitialized(ServletContextEvent sce) {
	// TODO Auto-generated method stub
	//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:Context-service.xml");
    InitialContext ic = null;
	try {
		ic = getInitialContext(providerUrl);
	} catch (NamingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    JMSQueueReceive qr = new JMSQueueReceive();
    try {
		qr.init(ic, QUEUE);
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    System.out.println("JMS Ready To Receive Messages (To quit, send a \"quit\" message).");
    
    IAlarmManager alarm1=new SaveSeAlarmImp();
    IAlarmManager alarm2=new VidioLinkManagerImp();
    
    IAlarmManager alarm3=new SoundLinkManagerImp();
    qr.getListManager().add(alarm1);
    qr.getListManager().add(alarm2);
    qr.getListManager().add(alarm3);
    
    // Wait until a "quit" message has been received.
    synchronized(qr) {
    	
      while (! qr.quit) {
        try {
          qr.wait();
        } catch (InterruptedException ie) {}
      }
    }
    try {
		qr.close();
	} catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
*/
}




