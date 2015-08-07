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
	</head>
	<body>
		<c:import url="../../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li>全部角色</li>
				</ul>
			</div>
			<div id="content_container">
			<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'创建角色') }">
				<a href="<c:url value='/authmgt/queryAllGroupAndOperation'></c:url>" class="hrefbtn">添加</a>
				</c:if>
				<div class="normalajaxloading" id="data_loading"></div>
				<div class="tableui_container">
					<table class="tableui">
				    	<thead>
					    	<tr>                       
						        <th><span>角色名称</span></th>
						        <th><span>角色描述</span></th>
								<th><span>操作</span></th>
					        </tr>
					   	</thead>
						<tbody>
							<c:forEach items="${roles }" var="role" varStatus="s">
								<span style="display:none;" id="role${s.count }_ops">${role.authorizedOpsForUI }</span>
								<tr>
							        <td class="rolename" id="role${s.count }_rolename">
							        	<%-- <a href="javascript:void(0);" class="detail role_detail" roleindex="${s.count }"> --%><c:out value="${role.name }"></c:out>
							        </td>
							        <td class="roledescription" id="role${s.count }_roledescription"><c:out value="${role.desc }"></c:out></td>
									<td align="center">
									<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'修改角色') }">
										<a href="<c:url value='/authmgt/editRole?name=${role.name }'></c:url>" title="编辑" class="editlink">
											编辑
										</a>
										</c:if>
										<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除角色') }">
										<a href="javascript:void(0);" title="删除" rolename="${role.name }" class="delete deletelink left20 deleterole">
											删除
										</a>
										</c:if>
									</td>
						        </tr> 
							</c:forEach>
						</tbody>
				    </table>
				</div>
			</div>
		</div>
		<c:import url="../../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		<div id="role_detail_container" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>角色详细信息</div>
			<div class='dialogModal_content'>
				<table class="detail_table">
					<tbody>
						<tr>
							<td class="title_name">角色名称:</td>
							<td class="content content_rolename"></td>
							<td class="title_name">角色描述:</td>
							<td class="content content_roledesc" colspan="3"></td>
						</tr>
						<tr>
							<td class="title_name">操作:</td>
							<td colspan="6" class="content content_ops"></td>
						</tr>
					<tbody>
				</table>
			</div>
			<div class="left20 top10" style="padding-bottom:20px;">
				<input class="left20 btn" data-dialogModalBut="cancel" type="button" value="关闭"/>
			</div>
		</div>
	</body>
</html>