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

					<li><a href="<c:url value="/alarmmgt/queryAllAlarmKnowledge"></c:url>">告警知识库</a></li>

					<li>告警知识库编辑</li>

				</ul>

			</div>

			<div id="content_container">

				<c:import url="../layout/gaojingtab.jsp?gTabLink=gaojingzhishiku" charEncoding="UTF-8"></c:import>

			    <div class="roleadd_container">

					<form action="<c:url value='/alarmmgt/updateAlarmKnowledge'></c:url>" method="post" class="left50 top30" id="gaojingzhishiku_edit_form">

						<div>

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">告警码:</label>

								<input type="text" name="alarmKnowledge.alarmCode" id="" class="normaltext disabletext" style="width: 500px;" readonly="readonly" value="${alarmKnowledge.alarmCode }"/>

								

								<div class="clear"></div>

							</div>

							<div class="text_form top5" style="margin-top:10px;" readonly="readonly">

								<label class="labelname" for="">告警名称:</label>

								<input type="text" name="alarmKnowledge.alarmName" id="" class="normaltext disabletext" style="width: 500px;" readonly="readonly" value="${alarmKnowledge.alarmName }" />

								<div class="clear"></div>

							</div>

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">告警类型:</label>

								<c:choose>

									<c:when test="${alarmKnowledge.alarmType == 1 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;" readonly="readonly" value="安防告警" />

									</c:when>

									<c:when test="${alarmKnowledge.alarmType == 2 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;" readonly="readonly" value="设备告警" /> 

									</c:when>

								</c:choose>

								<div class="clear"></div>

							</div>

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">设备类型:</label>

								<c:choose>

										<c:when test="${alarmKnowledge.deviceType == 1 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="高压脉冲主机" />

										</c:when>

										<c:when test="${alarmKnowledge.deviceType == 2 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="一体化探测主机" />

										</c:when>

										<c:when test="${alarmKnowledge.deviceType == 3 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="防区型振动光纤" />

										</c:when>

										<c:when test="${alarmKnowledge.deviceType == 4 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="定位型振动光纤" />

										</c:when>

										<c:when test="${alarmKnowledge.deviceType == 5 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="NVR" />

										</c:when>

										<c:when test="${alarmKnowledge.deviceType == 6 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="IPC" />

										</c:when>

										<c:when test="${alarmKnowledge.deviceType == 7 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="智能视频服务器" />

										</c:when>

										<c:otherwise>

										</c:otherwise>

									</c:choose>

								<div class="clear"></div>

							</div> 

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">告警级别:</label>

								<c:choose>

									<c:when test="${alarmKnowledge.alarmLevel == 1 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="警告" />

									</c:when>

									<c:when test="${alarmKnowledge.alarmLevel == 2 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="次要" />

									</c:when>

									<c:when test="${alarmKnowledge.alarmLevel == 3 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="主要" />

									</c:when>

									<c:when test="${alarmKnowledge.alarmLevel == 4 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="严重" />

									</c:when>

									<c:otherwise>

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="" />									

									</c:otherwise>

								</c:choose>

								<div class="clear"></div>

							</div> 

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">是否影响业务:</label>

								<c:choose>

									<c:when test="${alarmKnowledge.isAffect ==1 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;" value="是" readonly="readonly"/>

									</c:when>

									<c:when test="${alarmKnowledge.isAffect ==0 }">

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;" value="否" readonly="readonly"/>

									</c:when>

									<c:otherwise>

										<input type="text" name="" id="" class="normaltext disabletext" style="width: 500px;"  readonly="readonly" value="" />									

									</c:otherwise>

								</c:choose>

								<div class="clear"></div>

							</div>

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">可能原因:</label>

								<input type="text" name="alarmKnowledge.cause" id="alarmKnowledgecause" class="normaltext validate[maxSize[100]]" style="width: 500px;" value="${alarmKnowledge.cause }" />

								<div class="clear"></div>

							</div>

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">可能后果:</label>

								<input type="text" name="alarmKnowledge.result" id="alarmKnowledgeresult" class="normaltext validate[maxSize[100]" style="width: 500px;" value="${alarmKnowledge.result }" />

								<div class="clear"></div>

							</div>

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">建议操作:</label>

								<input type="text" name="alarmKnowledge.operation" id="alarmKnowledgeoperation" class="normaltext validate[maxSize[100]" style="width: 500px;" value="${alarmKnowledge.operation }" />

								<div class="clear"></div>

							</div>

							<div class="text_form top5" style="margin-top:10px;">

								<label class="labelname" for="">备注信息:</label>

								<input type="text" name="alarmKnowledge.info" id="alarmKnowledgeinfo" class="normaltext validate[maxSize[100]" style="width: 500px;" value="${alarmKnowledge.info }" />

								<div class="clear"></div>

							</div>

							<div class="clear"></div>

						</div>

						<div class="left40 top20">

							<input type="submit" value="修改" class="btn left20 top5"/>

							<a href="<c:url value='/alarmmgt/queryAllAlarmKnowledge'></c:url>" class="hrefbtn">取消</a>

						</div>

					</form>

				</div>

			</div>

		</div>

		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>

	</body>

</html>