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
		<script type="text/javascript">
			$(function(){
				
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
					<li>修改密码</li>
				</ul>
			</div>
			<div id="content_container">
				<div class="roleadd_container">
					<form action="<c:url value="/authmgt/createAccount"></c:url>" method="post" id="accountform">
						<c:choose>
							<c:when test="${commonBean.id eq '1' || commonBean.id eq '-1' }">
								<div style="margin-top:10px;margin-bottom:10px;font-weight:bold;color:red;margin-left:100px;">
									${commonBean.name }
								</div>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">备注:</label>
								<input type="text" name="account.remark" value="${acount.remark }" id="remark" class="normaltext" style="width:710px;">
								<div class="clear"></div>
							</div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">备注:</label>
								<input type="text" name="account.remark" value="${acount.remark }" id="remark" class="normaltext" style="width:710px;">
								<div class="clear"></div>
							</div>
						</div>
						<div class="top5 left40">
							<input type="submit" value="创建" class="btn left20"/>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>