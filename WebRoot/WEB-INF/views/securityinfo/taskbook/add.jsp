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
		<script src="${pageContext.request.contextPath}/resources/js/taskbook.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
				var zerenjigou = '${zerenOrgId}';
				$("#select_taskbook_inchargeorg").find("option[value='"+zerenjigou+"']").attr("selected",true);
		
			});
		</script>
	</head>
	<body>
		<c:import url="../../layout/header.jsp"  charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/securityinfo/queryTaskBook"></c:url>">安防任务</a></li>
					<li>创建安防任务</li>
				</ul>
			</div>
			<div id="content_container">
				<div class="roleadd_container">
					<input type="hidden" id="redirectUrl" value="/securityinfo/queryTaskBook?${taskBookCondition.searchUri}">									    				
					<form  action="" method="post" id="addtaskbook_form">
						<div class="top10">
							<div class="text_form left" >
								<label style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;" for="">任务名称:</label>
								<input type="text" name="taskBook.name" value="${taskBook.name }" id="name" class="normaltext validate[required,maxSize[50],funcCall[checkTaskNameExistAjax]]" style="width:759px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;" for="">任务目标:</label>
								<input type="text" name="taskBook.target" value="${taskBook.target }" id="target" class="normaltext validate[required,maxSize[250]]" style="width:759px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;" for="">任务内容:</label>
								<input type="text" name="taskBook.content" value="${taskBook.content }" id="content" class="normaltext validate[required,maxSize[500]]" style="width:759px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label  style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">任务类型:</label>
								<select id="type" name="taskBook.type" class="normalselect">							
		    						<option value="0">月度任务</option>
		    						<option value="1">季度任务</option>
		    						<option value="2">年度任务</option>
					    		</select>
								<div class="clear"></div>
							</div>
							<div class="text_form left" >
								<label style="margin-left:91px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">任务状态:</label>
				    			<c:choose>
									<c:when test="${taskBook.state == 0 }">
						    			<input type="text" name="taskBook.stateStr" value="未发布" id="state" readonly="readonly" style="width:300px;" class="normaltext disabletext">			    									
						    		</c:when>
									<c:when test="${taskBook.state == 1 }">
										<input type="text" name="taskBook.stateStr" value="已发布" id="state" readonly="readonly" style="width:300px;" class="normaltext disabletext">			    									
									</c:when>
									<c:otherwise>
										<input type="text" name="taskBook.stateStr" value="已关闭" id="state" readonly="readonly" style="width:300px;" class="normaltext disabletext">			    									
									</c:otherwise>
								</c:choose>
				    			<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left"  >
								<label  style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;" >制定时间:</label>
								<input type="text" name="taskBook.planTime" value="<fmt:formatDate value="${taskBook.planTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="normaltext disabletext" readonly="readonly" id="plantime"  style="width: 282px;"/>
								<a  href="javascript:void(0)" class="underlinenone">
									<img  src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a> 
								<div class="clear"></div>
							</div>
							<div class="text_form left"  >
								<label style="margin-left:91px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;" >发布时间:</label>
								<input type="text" name="taskBook.issueTime" value="<fmt:formatDate value="${taskBook.issueTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="normaltext disabletext" readonly="readonly"  id="issuetime" style="width: 282px;" />
								<a href="javascript:void(0)" class="underlinenone">
									<img src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">启动时间:</label>
								<input type="text" name="taskBook.startTime" value="<fmt:formatDate value="${taskBook.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="normaltext validate[required] datepicker" id="starttime" style="width: 282px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endtime\',{s:-1})}' ,minDate:'#F{$dp.$D(\'plantime\',{s:1})}'})" />
								<a href="javascript:void(0)" class="underlinenone">
									<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'starttime',maxDate:'#F{$dp.$D(\'endtime\',{s:-1})}' ,minDate:'#F{$dp.$D(\'plantime\',{s:1})}'})" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a>
								<div class="clear"></div>
							</div>
							<div class="text_form left"  >
								<label style="margin-left:91px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">结束时间:</label>
								<input type="text" name="taskBook.endTime" value="<fmt:formatDate value="${taskBook.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="normaltext validate[required] datepicker" id="endtime" style="width: 282px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'starttime\',{s:1})}'})" />
								<a href="javascript:void(0)" class="underlinenone">
									<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'endtime',minDate:'#F{$dp.$D(\'starttime\',{s:1})}'})" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label  style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">制定机构:</label>
				    			<input type="text" name="taskBook.planOrgName" value="${taskBook.planOrgName }" id="planOrgName" readonly="readonly" style="width:300px;" readonly="readonly" class="normaltext disabletext">			    												    			
								<div class="clear"></div>
							</div>
							 <div class="text_form left" >
								<label style="margin-left:52px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">制定机构负责人:</label>
								<input type="text" name="taskBook.planOrgPersonName" value="${taskBook.planOrgPersonName }" id="planOrgPersonName" readonly="readonly" style="width:300px;" readonly="readonly" class="normaltext disabletext">
								<div class="clear"></div>
							</div>					
							<div class="clear"></div>
						   	<div class="text_form left">
								<label style="margin-left:49px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">责任机构:</label>
								<select id="select_taskbook_inchargeorg" name="taskBook.inChargeOrgId" class="normalselect">
					    			<c:forEach items="${inChargeOrgList}" var="lv">
										<option value="${lv.id}"><c:out value="${lv.name }"></c:out></option>
									</c:forEach>
				    			</select>
								<div class="clear"></div>
							</div>
						    <div class="text_form left"  >
								<label style="margin-left:52px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">责任机构负责人:</label>
								<select id="select_taskbook_zerenjigouren" name="taskBook.inChargeOrgPerson" class="normalselect validate[required]">							
									<c:forEach items="${inChargeOrgPersonList}" var="lv">
										<option value="${lv.id}"><c:out value="${lv.name }"></c:out></option>
									</c:forEach>
								</select>
								<span class="input_tip">*责任机构负责人为责任机构的账号</span>					    												    						
							</div>
							<div class="clear"></div>				
							<div class="text_form left" >
								<label style="margin-left:75px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;" for="">备注:</label>
								<input type="text" name="taskBook.note" value="${taskBook.note}" id="note" class="normaltext validate[maxSize[250]]" style="width:759px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div> 
						</div>
						<input type="hidden" value="${taskBook.planOrgId }" name="taskBook.planOrgId" id="planOrgId"/>
						<input type="hidden" value="${taskBook.planOrgPerson }" name="taskBook.planOrgPerson" id="planOrgPerson"/>						
				    	<div class="clear"></div>
						<div class="top5 left40">
							<input type="button" value="保存" class="btn left20" id="addtaskbook_btn"/>
							<a href="<c:url value='/securityinfo/queryTaskBook?${taskBookCondition.searchUri }'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>