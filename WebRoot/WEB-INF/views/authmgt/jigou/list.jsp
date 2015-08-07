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
		<link href="${pageContext.request.contextPath}/resources/css/treetable.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/treetable.js"></script>
		<SCRIPT type="text/javascript">
			$(document).ready(function(){
				var heads = ["机构名称","组织机构代码","等级","备注","操作"];
				var widths = [30,20,10,20,20];
				var tNodes = new Array();
				<c:forEach items="${organizations }" var="org">
					<c:choose>
						<c:when test="${org.lev eq '0' }">
							tNodes.push({id: ${org.orgIdForUI }, pId: 0, name: '<c:out value="${org.orgNm }"></c:out>', 
							td: ['<c:out value="${org.orgId }"></c:out>', '总公司','<c:out value="${org.remark }"></c:out>',
							     '', 
							    ]});
						</c:when>
						<c:when test="${org.lev eq '1' }">
							tNodes.push({id: ${org.orgIdForUI }, pId: ${org.porgIdForUI }, name: '<c:out value="${org.orgNm }"></c:out>', 
							td: ['<c:out value="${org.orgId }"></c:out>', '分公司','<c:out value="${org.remark }"></c:out>',
							     "<c:if test='${poteviofn:clContains(sessionScope.session.authorizatedOps,"创建机构") }'><a class='addlink' href='<c:url value='/authmgt/addOrganization?organization.porgId=${org.orgId }&organization.lev=2'></c:url>'>添加管理处</a></c:if>", 
							    ]});
						</c:when>
						<c:otherwise>
							tNodes.push({id: ${org.orgIdForUI }, pId: ${org.porgIdForUI }, name: '<c:out value="${org.orgNm }"></c:out>', 
							td: ['<c:out value="${org.orgId }"></c:out>', '管理处','<c:out value="${org.remark }"></c:out>',
							     "<c:if test='${poteviofn:clContains(sessionScope.session.authorizatedOps,"修改机构") }'><a class='editlink' href='<c:url value='/authmgt/editOrganization?orgId=${org.orgId}'></c:url>'>编辑信息</a></c:if>", 
							    ]});
						</c:otherwise>			
					</c:choose>
				</c:forEach>
				$.TreeTable("tabletree", heads, tNodes,widths);
				$("#tabletree").resizableColumns({
					store: window.store
				});
				$("#tabletree").treetable("expandAll")
				$("#tabletree").find("tbody tr:odd").css("background-color","#f5f8fa");
				$("#tabletree").find("tbody tr:even").css("background-color","#ffffff");
			});
	</SCRIPT>
	</head>
	<body>
		<c:import url="../../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li>机构管理</li>
				</ul>
			</div>
			<div id="content_container">
			    <table class="treetable" id="tabletree"></table>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>