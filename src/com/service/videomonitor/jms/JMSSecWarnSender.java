package com.service.videomonitor.jms;

import java.util.Hashtable;
import javax.jms.JMSException;
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
import org.apache.log4j.Logger;
import com.entity.alarmmgt.WarnInfo;
import com.entity.linkagectl.SecurityAlarm;

public class JMSSecWarnSender extends JMSSenderConfig {
	private SecurityAlarm alarm = new SecurityAlarm();
	Logger logger = Logger.getLogger(JMSDevWarnSender.class);
	private String alarmQueue;

	public String getAlarmQueue() {
		return alarmQueue;
	}

	public void setAlarmQueue(String alarmQueue) {
		this.alarmQueue = alarmQueue;
	}

	private int getAlarm(WarnInfo wi) {
		if (wi == null) {
			logger.error("getAlarm(WarnInfo wi),wi is null!");
			return -1;
		}
		alarm.setPictureURL(wi.getPictureURL());
		alarm.setZoneID(wi.getZoneID());
		alarm.setAlarmCode(wi.getAlarmCode());
		alarm.setAlarmTime(wi.getBegintime());
		alarm.setDeviceID(wi.getIpcID());
		alarm.setDepartmentID(wi.getMngMentID());
		alarm.setBranchID(wi.getSubComID());
		alarm.setAlarmStatus(0);
		return 0;
	}

	public int warnSend(WarnInfo wi) throws NamingException, JMSException {
		if (getAlarm(wi) != 0) {
			logger.error("getAlarm(wi) return error!");
			return -1;
		}
		if (jndiFactory == null || providerUrl == null
				|| connFactoryJNDI == null || alarmQueue == null) {
			logger.error("JNDIFactory,providerUrl,connFactoryJNDI or alarmQueue is null!");
			return -1;
		}
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, jndiFactory);
		env.put(Context.PROVIDER_URL, providerUrl);
		Context ctx = new InitialContext(env);
		QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx
				.lookup(connFactoryJNDI);
		// create queue connection
		QueueConnection qConn = (QueueConnection) connFactory
				.createConnection();
		// create session
		QueueSession qSession = qConn.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);
		// find queue by JNDI lookup
		Queue queue = (Queue) ctx.lookup(alarmQueue);
		// create sender
		QueueSender qSender = qSession.createSender(queue);
		// create message
		ObjectMessage objectMessage = qSession.createObjectMessage();
		objectMessage.setObject(alarm);
		logger.debug("Send a warn:" + alarm.getDeviceID() + ","
				+ alarm.getAlarmTime());
		qSender.send(objectMessage);
		qSender.close();
		qSession.close();
		qConn.close();
		return 0;
	}

}
