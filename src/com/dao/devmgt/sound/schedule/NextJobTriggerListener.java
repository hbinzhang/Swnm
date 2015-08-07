//package com.dao.devmgt.sound.schedule;
//
//import org.quartz.JobDetail;
//import org.quartz.JobExecutionContext;
//import org.quartz.Scheduler;
//import org.quartz.Trigger;
//import org.quartz.listeners.TriggerListenerSupport;
//import org.springframework.scheduling.quartz.SimpleTriggerBean;
//
//public class NextJobTriggerListener extends TriggerListenerSupport {
////	private Log logger = LogFactory.getLog(this.getClass());
//	private String name;
//
//	public String getName() {
//		return this.name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	private SimpleTriggerBean nextTrigger;
//
//	public void setNextTrigger(SimpleTriggerBean nextTrigger) {
//		this.nextTrigger = nextTrigger;
//	}
//
//	@Override
//	public void triggerComplete(Trigger trigger, JobExecutionContext context,
//			int code) {
//		try {
//			Scheduler schduler = context.getScheduler();
//			JobDetail nextJob = nextTrigger.getJobDetail();
//			// 查找名称和即将加入的任务一样的任务
//			JobDetail oldJob = schduler.getJobDetail(nextJob.getName(),
//					nextJob.getGroup());
//			// 查找名称和即将加入的触发器一样的触发器
//			Trigger oldTrigger = schduler.getTrigger(nextTrigger.getName(),
//					nextTrigger.getGroup());
//
//			if (oldJob == null && oldTrigger == null)// 同名的任务和触发器都不存在
//			{
////				logger.debug("inside scheduleJob." + code);
//				schduler.scheduleJob(nextJob, nextTrigger);
//			} else// 同名的任务或触发器
//			{
//
////				logger.debug("oldJob==null:" + (oldJob == null));
////				logger.debug("oldTrigger==null:" + (oldTrigger == null));
//			}
//			super.triggerComplete(trigger, context, code);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//}
