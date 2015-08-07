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

		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">

		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">

		<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/assessment.js" type="text/javascript"></script>
		<script type="text/javascript">


		</script>

	</head>

	<body>

		<c:import url="../../layout/header.jsp" charEncoding="UTF-8"></c:import>

		<div id="main_content_container">

			<div id="nav_place">

				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/securityinfo/queryAssessment"></c:url>">目标考核</a></li>
					<li>检查情况</li>					
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
									    	
			    <div class="normalajaxloading" id="data_loading"></div>

			    <div class="tableui_container">				
				
					<table class="tableui" id="checkinfoTableContainer">
					
				    	<thead>

					    	<tr>       

						        <th><span>考核机构</span></th>						       
						        <th><span>指标名称</span></th>
						        
								<th><span>检查时间</span></th>

								<th><span>录入人员</span></th>

								<th><span>检查情况</span></th>
															
								<th><span>操作</span></th>

					        </tr>

				        </thead>

				        <tbody>
				        	<c:forEach items="${checkInfoList}" var="checkinfo" varStatus="index">
				        		
				        		<tr>        

							        <td ><c:out value="${checkinfo.orgName }"></c:out></td>							        								
									<td ><c:out value="${checkinfo.indexName}"></c:out></td>							
									<td><fmt:formatDate value="${checkinfo.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>																											
									<td><c:out value="${checkinfo.recordPersonName}"></c:out></td>																											
									<td><c:out value="${checkinfo.note}"></c:out></td>																																																																											        
									<td align="center">									
										<a href="javascript:void(0);" indexName="${checkinfo.indexName}" orgId="${checkinfo.orgId }"  time="<fmt:formatDate value="${checkinfo.time}" pattern="yyyy-MM-dd HH:mm:ss"/>"  title="删除" class="delete deletecheckinfo deletelink" >删除</a>						
 									</td>
								</tr>

				        	</c:forEach>

				        </tbody>

				    </table>
 				    <div class="clear"></div>
					<div class="roleadd_container">
					<input type="hidden" id="redirectUrl" value="/securityinfo/queryCheckInfoByName?checkInfo.orgId=${checkInfo.orgId }&checkInfo.indexName=${checkInfo.indexName}">									    				
					<form  action="" method="post" id="addcheckinfo_form">
						<div class="top10">
							<div class="text_form left" >
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">考核机构:</label>
					    		<input type="text" name="checkInfo.orgName" value="${checkInfo.orgName}" id="orgName" readonly="readonly" class="normaltext disabletext" style="width:652px;">				    		
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">指标名称:</label>	
					    		<input type="text" name="checkInfo.indexName" value="${checkInfo.indexName}" id="indexName" readonly="readonly" class="normaltext disabletext" style="width:652px;">				    						    								
					    	<div class="clear"></div>
							</div>
							
							<div class="clear"></div>
					
							<div class="text_form left" >
								<label style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">录入人员:</label>
								<input type="text" name="checkInfo.recordPersonName" value="${checkInfo.recordPersonName}" id="recordPersonName" class="normaltext disabletext" readonly="readonly" style="width:652px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<div class="text_form left">
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;">检查情况:</label>
								<input type="text" name="checkInfo.note" value="${checkInfo.note}" id="note" class="normaltext validate[maxSize[250]" style="width:652px;">								
								<div class="clear"></div>
							</div>
				    		<div class="clear"></div>
								<div class="text_form left">
								<label  style="margin-left:59px;display: block;font-size:13px;float: left;text-align: right;height:30px;line-height:30px;" >检查时间:</label>
								<input type="text" name="checkInfo.time" value="<fmt:formatDate value="${checkInfo.time }" pattern="yyyy-MM-dd HH:mm:ss"/>" class="normaltext validate[required,funcCall[checkCheckInfoExistAjax]] datepicker "  id="time"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'time'})"/>
								<a  href="javascript:void(0)" class="underlinenone">
									<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',el:'time'})" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a> 
								<div class="clear"></div>
							</div>
				    		<div>
				    			<input type="hidden" name="checkInfo.recordPerson" value="${checkInfo.recordPerson }"></input>			
				    			<input type="hidden" id="orgId" name="checkInfo.orgId" value="${checkInfo.orgId }"></input>
				    		</div>
				    		<div class="clear"></div>
							<div class="top5 left50">
								<input type="button" value="创建" class="btn left10" id="addcheckinfo_btn" />
								<a href="<c:url value='/securityinfo/queryAssessment'></c:url>" class="hrefbtn">返回</a>
							</div>
						</div>
					</form>
				</div>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>

</html>