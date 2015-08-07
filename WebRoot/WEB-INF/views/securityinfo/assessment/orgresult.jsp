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
					<li>查看机构考核成绩(考核机构:${orgName })</li>					
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

			  <%--   <div class="search_container ">
					<label style="margin-left: 10px;font-size:13px;font-weight:bold;float: left;height:28px;line-height:34px; " for="">机构考核成绩：${orgResult }</label>
						<div class="clear"></div>				
				</div> --%>
				<div class="" style="width: 25%;">
					<label style="margin-left: 11px;font-size:14px;font-weight:bold;float: left;height:24px;line-height:34px; " for="">机构考核成绩：${orgResult }</label>
						<div class="clear"></div>				
				</div>
			    <div class="tableui_container">						
				<div class="clear"></div>
					<table class="tableui" id="assessmentTableContainer">
					
				    	<thead>
					    	<tr>       
						        <th><span>指标名称</span></th>

								<th><span>指标权重</span></th>

								<th><span>考评结果</span></th>								

					        </tr>
				        </thead>

				        <tbody>
				        	<c:forEach items="${orgAssessmentList}" var="assessment" varStatus="index">
				        		
				        		<tr>        						        								
									<td ><c:out value="${assessment.name}"></c:out></td>						
									<td><c:out value="${assessment.percent }"></c:out></td>																											
									<td><c:out value="${assessment.result }"></c:out></td>		
								</tr>
								
				        	</c:forEach>
								<%-- <tr>        						        								
									<td colspan="3">机构考核成绩：${orgResult }</td>						
								</tr> --%>
				        </tbody>

				    </table>

				</div>
				<div class="clear"></div>
				<div class="top5 ">
					<a href="<c:url value='/securityinfo/queryAssessment'></c:url>" class="hrefbtn">返回</a>
				</div>
				
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>

</html>