<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>安防综合监控系统</title>
		<link href="<c:url value='/resources/images/favicon.ico'></c:url>" rel="icon" type="image/x-icon" />
	    <link href="<c:url value='/resources/images/favicon.ico'></c:url>" rel="shortcut icon" type="image/x-icon" />
		<link href="${pageContext.request.contextPath}/resources/css/common.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/videomonitor.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
            setWindowMode(4);
		    if ('${videoAlarm.vidioUrl}' !='') {
		        doipcplay('${videoAlarm.vidioUrl}',0);
		    }
            if ('${videoAlarm.leftVidioUrl}' !='') {
		        doipcplay('${videoAlarm.leftVidioUrl}',2);
		    }		   
		    if ('${videoAlarm.rightVidioUrl}' !='') {
		        doipcplay('${videoAlarm.rightVidioUrl}',3);
		    }
			//doipcplay('${videoAlarm.leftVidioUrl}',2);
			//doipcplay('${videoAlarm.rightVidioUrl}',3); 
			});
			
		</script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/alarmmgt/queryDeviceAlarm"></c:url>">告警管理</a></li>
					<li>摄像头视频&nbsp;&nbsp;<span style="font-weight:bold;">[告警编号:${videoAlarm.alarmId }]</span></li>
				</ul>
			</div>
			<div id="content_container">
				<object id="plugin0" type="application/x-ptplayerplugin"
					style="width:1455px;height:510px">
					<param name="onload" value="pluginLoaded" />
				</object>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>