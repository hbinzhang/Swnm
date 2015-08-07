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
		<script src="${pageContext.request.contextPath}/resources/js/assessment.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
				var kaohejigou = '${checkOrgId}';
				$("#select_assessment_kaohejigou").find("option[value='"+kaohejigou+"']").attr("selected",true);
		
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
					<li><a href="<c:url value="/securityinfo/queryAssessment"></c:url>">目标考核</a></li>
					<li>创建考核指标</li>	
				</ul>
			</div>
			<div id="content_container">
				<div class="normal_tab">

				  	<ul>
					    <li><a class="selected" href="<c:url value='/securityinfo/queryAssessment'></c:url>">考核指标</a></li>

					    <li><a href="<c:url value='/securityinfo/queryRewardPunishment'></c:url>">奖惩管理</a></li>

						<li><a href="<c:url value='/securityinfo/queryRectification'></c:url>">整改管理</a></li>

				  	</ul>

			    </div>
				<div class="roleadd_container" style="">
					<input type="hidden" id="redirectUrl" value="/securityinfo/queryAssessment">									    				
					<form  action="" method="post" id="addassessment_form">
						<div class="top10">
							<div class="text_form left" >
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">考核机构:</label>
								<select id="select_assessment_kaohejigou" name="assessment.orgId" class="normalselect" style="width:665px;">							
								    <c:forEach items="${orgList}" var="lv">
							    		<option value="${lv.id }">${lv.name}</option>
								    </c:forEach>	
					    		</select>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">指标名称:</label>
								<select id="select_assessment_zhibiaomingcheng" name="assessment.name" class="normalselect validate[required,funcCall[checkAssessmentExistAjax]]"  style="width:666px;">							
		    						<c:forEach items="${assessIndexList}" var="lv">
								    	<option value="${lv.name }">${lv.name }</option>
								    </c:forEach>	
					    		</select>
					    		<span class="input_tip">*指标名称为安防任务的名称</span>					    										
					    		<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label style="margin-left:21px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">考核机构责任人:</label>
				    			<select id="select_assessment_kaohejigouzerenren" name="assessment.orgPerson" class="normalselect validate[required]" style="width:665px;">							
		    						<c:forEach items="${orgPersonList}" var="lv">
								    	<option value="${lv.id }">${lv.name }</option>
								    </c:forEach>	
					    		</select>
					    		<span class="input_tip">*考核机构责任人为考核机构的账号</span>					    												    		
				    			<div class="clear"></div>
							</div>
							<div class="clear"></div>
							
							<div class="text_form left">
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">指标目标:</label>
								<input type="text" name="assessment.target" value="${assessment.target}" id="target" class="normaltext validate[maxSize[250]]" style="width:652px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">指标权重:</label>
								<input type="text" name="assessment.percent" value="${assessment.percent}" id="percent" class="normaltext" style="width:652px;">								
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">考核人员:</label>
				    			<select id="reviewer" name="assessment.reviewer" class="normalselect" style="width:665px;">							
		    						<c:forEach items="${assessPersonList}" var="lv">
								    	<option value="${lv.id }">${lv.name }</option>
								    </c:forEach>	
					    		</select>
				    			<div class="clear"></div>
							</div>
				    	<div class="clear"></div>
						<div class="top5 left50">
							<input type="button" value="保存" class="btn left10" id="addassessment_btn" />
							<a href="<c:url value='/securityinfo/queryAssessment'></c:url>" class="hrefbtn">取消</a>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>