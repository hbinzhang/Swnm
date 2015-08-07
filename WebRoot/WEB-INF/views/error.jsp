<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include.inc.jsp"%>
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
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li>系统出现异常</li>
				</ul>
			</div>
			<div id="content_container">
				<div class="errorpagecontainer">
					<div class="clear"></div>
					<div class="error_content">
						<div class="content_container">
							<p>对不起，您访问的页面出现错误或者不存在</p>
							<p>请检查你输入的地址是否正确</p>
							<p>如果您认为是系统存在问题</p>
							<p>请联系系统管理人员，谢谢!</p>
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<c:import url="layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>