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
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/alarmmgt/queryDeviceAlarm"></c:url>">告警管理</a></li>
					<li>告警知识库</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/gaojingtab.jsp?gTabLink=gaojingzhishiku" charEncoding="UTF-8"></c:import>
			    <div class="tableui_container">
					<table class="tableui">
				    	<thead>
					    	<tr>                       
					        <th><span>告警码</span></th>
					        <th><span>告警名称</span></th>
					        <th><span>告警类型</span></th>
					        <th><span>设备类型</span></th>
					        <th><span>告警级别</span></th>
					        <th><span>可能原因</span></th>
							<th><span>可能后果</span></th>
					        <th><span>建议操作</span></th>
					        <th><span>是否影响业务</span></th>
							<th><span>编辑</span></th>
					        </tr>
					   	</thead>
						<tbody>
							<c:forEach items="${alarmKnowledgeList }" var="know">
								<tr>
									<td><c:out value="${know.alarmCode }"></c:out></td>
									<td><c:out value="${know.alarmName }"></c:out></td>
										<c:choose>
											<c:when test="${know.alarmType == 1 }">
												<td><c:out value="安防告警"></c:out></td>
											</c:when>
											<c:when test="${know.alarmType == 2 }">
												<td><c:out value="设备告警"></c:out></td>
											</c:when>
										</c:choose>
									<c:choose>
										<c:when test="${know.deviceType == 1 }">
											<td><c:out value="高压脉冲主机"></c:out></td>
										</c:when>
										<c:when test="${know.deviceType == 2 }">
											<td><c:out value="一体化探测主机"></c:out></td>
										</c:when>
										<c:when test="${know.deviceType == 3 }">
											<td><c:out value="防区型振动光纤"></c:out></td>
										</c:when>
										<c:when test="${know.deviceType == 4 }">
											<td><c:out value="定位型振动光纤"></c:out></td>
										</c:when>
										<c:when test="${know.deviceType == 5 }">
											<td><c:out value="NVR"></c:out></td>
										</c:when>
										<c:when test="${know.deviceType == 6 }">
											<td><c:out value="IPC"></c:out></td>
										</c:when>
										<c:when test="${know.deviceType == 7 }">
											<td><c:out value=" 智能视频服务器"></c:out></td>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${know.alarmLevel == 1 }">
											<td><c:out value="警告"></c:out></td>
										</c:when>
										<c:when test="${know.alarmLevel == 2 }">
											<td><c:out value="次要"></c:out></td>
										</c:when>
										<c:when test="${know.alarmLevel == 3 }">
											<td><c:out value="主要"></c:out></td>
										</c:when>
										<c:when test="${know.alarmLevel == 4 }">
											<td><c:out value="严重"></c:out></td>
										</c:when>
										<c:otherwise>
											<td><c:out value=""></c:out></td>
										</c:otherwise>
									</c:choose>
									<td><c:out value="${know.cause }"></c:out></td>
									<td><c:out value="${know.result }"></c:out></td>
									<td><c:out value="${know.operation }"></c:out></td>
										<c:choose>
											<c:when test="${know.isAffect==1 }"><td>是</td></c:when>
											<c:when test="${know.isAffect==0 }"><td>否</td></c:when>
											<c:otherwise>
												<td><c:out value=""></c:out></td>
											</c:otherwise>
										</c:choose>
									<td align="center">
									<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'修改告警基本信息') }">
										<a href="<c:url value='/alarmmgt/goupdateAlarmKnowledge?alarmCode=${know.alarmCode }'></c:url>" title="编辑" class="edit_zhishiku editlink">
											编辑
										</a>
									</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
				    </table>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		<div id="zhishiku_edit" style="display: none;z-index:0;" wid="30">
			<form action="<c:out value='/alarmmgt/updateAlarmKnowledge'></c:out>" method="post">
			<div class='dialogModal_header'>编辑告警知识库</div>
			<div class='dialogModal_content'>
				<div class="companysubject_add_form_cantainer">
					<div id="companysubject_add_form">
						<div class="top10">
							可能原因:<input type="text" id="" class="normaltext" style="width: 80%;" name="cause" />
						</div>
						<div class="top10">
							可能后果:<input type="text" id="" class="normaltext" style="width: 80%;" name="result" />
						</div>
						<div class="top10">
							建议操作:<input type="text" id="" class="normaltext" style="width: 80%;" name="operation" />
						</div>
						<div class="top10">
							备注信息:<input type="text" id="" class="normaltext" style="width: 80%;" name="note"/>
						</div>
							<input type="hidden" id="" class="normaltext" style="width: 80%;" name="alarmCode"/>
					</div>
				</div>
			</div>
			<div class="left20 top10">
				<input class="left20 btn" id="" data-dialogModalBut="ok" type="submit" value="保存"/>
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="取消"/>
			</div>
			</form>
		</div>
	</body>
</html>