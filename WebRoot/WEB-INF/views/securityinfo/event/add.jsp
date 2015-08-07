<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include.inc.jsp"%>
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
		<script src="${pageContext.request.contextPath}/resources/js/event.js" type="text/javascript"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<c:import url="../../layout/header.jsp"  charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/securityinfo/queryEvent"></c:url>">安全事件</a></li>
				</ul>
			</div>
			<div id="content_container">
				<div class="roleadd_container" style="min-height:610px;">
					<input type="hidden" id="redirectUrl" value="/securityinfo/queryEvent?${eventCondition.searchUri }">									    				
					<form  action="" method="post" id="addevent_form">
						<div class="top10">
							<div class="text_form left" >
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">所属机构:</label>
					    		<input type="text" name="event.belongToOrgName" value="${event.belongToOrgName}" id="belongToOrgName" class="normaltext disabletext" readonly="readonly" style="width:652px;">				    		
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">事件名称:</label>
					    		<input type="text" name="event.name" value="${event.name}" id="name" class="normaltext validate[required,maxSize[50]]" style="width:652px;">				    						    		
					    		<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">发生时间:</label>
								<input type="text" name="event.time" value="<fmt:formatDate value="${event.time }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="normaltext validate[required] datepicker" id="time" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' })" />
								<a href="javascript:void(0)" class="underlinenone">
									<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'time'})" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a>				    			
								<div class="clear"></div>
							</div>
							<div class="clear"></div>														
							<div class="text_form left" >
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">事件地点:</label>
								<input type="text" name="event.process" value="${event.process }" class="normaltext validate[required,maxSize[50]]"  style="width:652px;" id="time"  />
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:26px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">所属区域和管理部门:</label>
					   			<input type="text" name="event.eventBelongToOrg" value="${event.eventBelongToOrg}" id="eventBelongToOrg" class="normaltext validate[required,maxSize[100]]" style="width:652px;">
				    			<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">事件定级:</label>
								<input type="text" name="event.eventLevel value="${event.eventLevel}" id="eventLevel" class="normaltext validate[required,maxSize[100]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
							<div class="clear"></div>				
				    		<div class="text_form left" >
								<label style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">事件原因:</label>
								<input type="text" name="event.reason" value="${event.reason}" id="reason" class="normaltext validate[required,maxSize[250]]" style="width:652px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">事件过程:</label>
								<input type="text" name="event.process" value="${event.process}" id="process" class="normaltext validate[required,maxSize[250]]" style="width:652px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">事件结果:</label>
								<input type="text" name="event.result" value="${event.result}" id="result" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
				    		<div class="text_form left" >
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">影响范围:</label>
								<input type="text" name="event.influenceRange" value="${event.influenceRange}" id="influenceRange" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
				    		<div class="text_form left" >
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">责任部门:</label>
								<input type="text" name="event.inChargeOrg" value="${event.inChargeOrg}" id="inChargeOrg" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
				    		<div class="text_form left">
								<label  style="margin-left:52px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">责任部门责任人:</label>
								<input type="text" name="event.inChargePerson" value="${event.inChargePerson}" id="inChargePerson" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
				    		<div class="text_form left">
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">补救措施:</label>
								<input type="text" name="event.remedialMeasures" value="${event.remedialMeasures}" id="remedialMeasures" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
				    		<div class="text_form left" >
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">补救结果:</label>
								<input type="text" name="event.remedialResult" value="${event.remedialResult}" id="remedialResult" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
				    		<div class="text_form left">
								<label  style="margin-left:90px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">事后评估:</label>
								<input type="text" name="event.postEvaluation" value="${event.postEvaluation}" id="postEvaluation" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
						</div>
						<input type="hidden" value="${event.belongToOrgId }" name="event.belongToOrgId" id="belongToOrgId"/>
						<div class="top5" style="margin-left:80px;">
							<input type="button" value="保存" class="btn left10" id="addevent_btn" />
							<a href="<c:url value='/securityinfo/queryEvent?${eventCondition.searchUri }'></c:url>" class="hrefbtn">取消</a>
						</div>					
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>