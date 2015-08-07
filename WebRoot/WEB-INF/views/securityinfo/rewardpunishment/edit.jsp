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
		<script src="${pageContext.request.contextPath}/resources/js/rewardpunishment.js" type="text/javascript"></script>
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
					<li>编辑奖惩记录</li>	
				</ul>
			</div>
			<div id="content_container">
				<div class="normal_tab">

				  	<ul>
					    <li><a  href="<c:url value='/securityinfo/queryAssessment'></c:url>">考核指标</a></li>

					    <li><a class="selected" href="<c:url value='/securityinfo/queryRewardPunishment'></c:url>">奖惩管理</a></li>

						<li><a href="<c:url value='/securityinfo/queryRectification'></c:url>">整改管理</a></li>

				  	</ul>

			    </div>
				<div class="roleadd_container" style="min-height:610px;">
					<input type="hidden" id="redirectUrl" value="/securityinfo/queryRewardPunishment?${rewardPunishmentCondition.searchUri }">									    				
					<form  action="" method="post" id="editrewardpunishment_form">
						<div class="top10">
							<div class="text_form left" >
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">所属机构:</label>
					    		<input type="text" name="rewardPunishment.belongToOrgName" value="${rewardPunishment.belongToOrgName}" id="belongToOrgName" class="normaltext disabletext" readonly="readonly" style="width:652px;">				    		
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">奖惩名称:</label>
					    		<input type="text" name="rewardPunishment.name" value="${rewardPunishment.name}" id="name" class="normaltext disabletext" readonly="readonly" style="width:652px;">				    						    		
					    		<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">奖惩部门:</label>
					    		<input type="text" name="rewardPunishment.rpOrg" value="${rewardPunishment.rpOrg}" id="rpOrg" class="normaltext validate[required,maxSize[100]]" style="width:652px;">				    						    		
				    			<div class="clear"></div>
							</div>
							<div class="clear"></div>
														
							<div class="text_form left">
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">奖惩时间:</label>
								<input type="text" name="rewardPunishment.time" value="<fmt:formatDate value="${rewardPunishment.time }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="normaltext validate[required] datepicker"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="time"  />
								<a  href="javascript:void(0)" class="underlinenone">
									<img  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'time'})" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a> 
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">奖惩级别:</label>
					   			<input type="text" name="rewardPunishment.rplevel" value="${rewardPunishment.rplevel}" id="rplevel" class="normaltext validate[required,maxSize[30]]" style="width:652px;">
				    			<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">实施部门:</label>
								<input type="text" name="rewardPunishment.implementOrg" value="${rewardPunishment.implementOrg}" id="implementOrg" class="normaltext validate[required,maxSize[100]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
							<div class="clear"></div>				
				    		<div class="text_form left">
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">奖惩描述:</label>
								<input type="text" name="rewardPunishment.description" value="${rewardPunishment.description}" id="description" class="normaltext validate[required,maxSize[500]]" style="width:652px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">奖惩备注:</label>
								<input type="text" name="rewardPunishment.note" value="${rewardPunishment.note}" id="note" class="normaltext validate[maxSize[250]]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
						</div>
						<input type="hidden" value="${rewardPunishment.belongToOrgId }" name="rewardPunishment.belongToOrgId" id="belongToOrgId"/>
						<div class="top5 left50">
							<input type="button" value="保存" class="btn left10" id="editrewardpunishment_btn" />
							<a href="<c:url value='/securityinfo/queryRewardPunishment?${rewardPunishmentCondition.searchUri }'></c:url>" class="hrefbtn">取消</a>
						</div>					
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>