<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<script type="text/javascript">
	$(function(){
		var currentGTabLink = '${param.gTabLink}';
		$("#gtab_"+currentGTabLink).addClass("selected");
	});
</script>
		<div class="normal_tab">
			<div>
				<ul>
			  		<li><a id="gtab_gaojingjianshi" href="<c:url value='/alarmmgt/queryDeviceAlarm'></c:url>">设备告警监视</a></li>
				    <li><a id="gtab_gaojingchaxun" href="<c:url value='/alarmmgt/initAlarm'></c:url>">告警查询</a></li>
					<li><a id="gtab_gaojingtongji" href="<c:url value='/alarmmgt/initStatisticAlarm'></c:url>">告警统计</a></li>
					<li><a id="gtab_gaojingzhishiku" href="<c:url value='/alarmmgt/queryAllAlarmKnowledge'></c:url>">告警知识库</a></li>
			  	</ul>
			</div>
		  	
	   </div>
	   