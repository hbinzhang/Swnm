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
		<script src="${pageContext.request.contextPath}/resources/js/rectification.js" type="text/javascript"></script>
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
					<li><a href="<c:url value="/securityinfo/queryAssessment"></c:url>">目标考核</a></li>
					<li>创建整改记录</li>	
				</ul>
			</div>
			<div id="content_container">
				<div class="normal_tab">

				  	<ul>
					    <li><a  href="<c:url value='/securityinfo/queryAssessment'></c:url>">考核指标</a></li>

					    <li><a  href="<c:url value='/securityinfo/queryRewardPunishment'></c:url>">奖惩管理</a></li>

						<li><a  class="selected" href="<c:url value='/securityinfo/queryRectification'></c:url>">整改管理</a></li>

				  	</ul>

			    </div>
				<div class="roleadd_container" style="min-height:610px;">
					<input type="hidden" id="redirectUrl" value="/securityinfo/queryRectification?${rectificationCondition.searchUri }">									    				
					<form  action="" method="post" id="addrectification_form">
						<div class="top10">
							<div class="text_form left" >
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">所属机构:</label>
					    		<input type="text" name="rectification.belongToOrgName" value="${rectification.belongToOrgName}" id="belongToOrgName" class="normaltext disabletext" readonly="readonly" style="width:652px;">				    		
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">整改名称:</label>
					    		<input type="text" name="rectification.name" value="${rectification.name}" id="name" class="normaltext validate[required,maxSize[50],funcCall[checkRectificationNameExistAjax]]" style="width:652px;">				    						    		
					    		<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">发起部门:</label>
					    		<input type="text" name="rectification.initiatingOrg" value="${rectification.initiatingOrg}" id="initiatingOrg" class="normaltext validate[required,maxSize[100]]" style="width:652px;">				    						    		
				    			<div class="clear"></div>
							</div>
							<div class="clear"></div>
														
							<div class="text_form left" >
								<label  style="margin-left:33px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">实际整改时间:</label>
								<input type="text" name="rectification.time" value="${rectification.time }" class="normaltext validate[required,maxSize[50]]"  style="width:652px;" id="time"  />
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">实施部门:</label>
					   			<input type="text" name="rectification.implementOrg" value="${rectification.implementOrg}" id="implementOrg" class="normaltext validate[required,maxSize[100]]" style="width:652px;">
				    			<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">实施人员:</label>
								<input type="text" name="rectification.implementPerson" value="${rectification.implementPerson}" id="implementPerson" class="normaltext validate[required,maxSize[100]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
							<div class="clear"></div>				
				    		<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">整改措施:</label>
								<input type="text" name="rectification.actions" value="${rectification.actions}" id="actions" class="normaltext validate[required,maxSize[250]]" style="width:652px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">整改结果:</label>
								<input type="text" name="rectification.result" value="${rectification.result}" id="result" class="normaltext validate[required,maxSize[250]]" style="width:652px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">整改备注:</label>
								<input type="text" name="rectification.note" value="${rectification.note}" id="note" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
						</div>
						<input type="hidden" value="${rectification.belongToOrgId }" name="rectification.belongToOrgId" id="belongToOrgId"/>
						<div class="top5 left50">
							<input type="button" value="保存" class="btn left10" id="addrectification_btn" />
							<a href="<c:url value='/securityinfo/queryRectification?${rectificationCondition.searchUri }'></c:url>" class="hrefbtn">取消</a>
						</div>					
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>