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
				var allSelectChecks = $(".selectallpermissions");
				for(var i=0;i<allSelectChecks.length;i++)
				{
					var childChecks = $(allSelectChecks[i]).parent().parent().find("label input[type='checkbox']");
					var childChecksChecked = $(allSelectChecks[i]).parent().parent().find("label input[type='checkbox']:checked");
					/*var ifCheck = true;
					for(var j=0;j<childChecks.length;j++)
					{
						if(!$(childChecks[j]).is(':checked'))
						{
							ifCheck = false;
							break;
						}
					}*/
					if(childChecks.length==childChecksChecked.length)
					{
						$(allSelectChecks[i]).prop("checked", true);
					}
					else
					{
						$(allSelectChecks[i]).prop("checked", false);
					}
				}
			});
		</script>

	</head>
	<body>
		<c:import url="../../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/authmgt/queryAllRoles"></c:url>">角色管理</a></li>
					<li>添加角色</li>
				</ul>
			</div>
			<div id="content_container">
				<div class="roleadd_container">
					<form action="<c:url value='/authmgt/createRole'></c:url>" method="post" id="roleform">
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
								<label class="labelname" for="">角色名称:</label>
									<input type="text" class="normaltext validate[required,maxSize[100]]" name="role.name" value="${role.name }">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">角色描述:</label>
								<input type="text" class="normaltext validate[maxSize[256]]" name="role.desc" value="${role.desc }" style="width: 1113px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="text_form">
							<label class="labelname" for="">授权操作&nbsp;</label>
							<div class="clear"></div>
						</div>
						<div class="">
							<c:forEach items="${groupAndOperations }" var="opertion">
								<fieldset>
									<legend>${opertion.opGroupName }<input type="checkbox" class="selectallpermissions left10"></legend>
									<c:forEach items="${opertion.opName }" var="op">
									 	<label class="floating">
									 		<c:choose>
									 			<c:when test="${poteviofn:clContains(ops,op) }">
									 				<input type="checkbox" class="validate[required]" checked="checked" value="${op }" name="ops" id=""><span style="margin-left:10px;">${op }</span>
									 			</c:when>
									 			<c:otherwise>
									 				<input type="checkbox" class="validate[required]" value="${op }" name="ops" id=""><span style="margin-left:10px;">${op }</span>
									 			</c:otherwise>
									 		</c:choose>
										</label>
									</c:forEach>
								</fieldset>
							</c:forEach>
						</div>
						<div class="top5 left40">
							<input type="submit" value="保存" class="btn left20"/>
							<a href="<c:url value='/authmgt/queryAllRoles'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>