<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
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
		<c:import url="../layout/header.jsp"  charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/views/zhanghao/list.jsp"></c:url>">帐号管理</a></li>
					<li>编辑账号信息</li>
				</ul>
			</div>
			<div id="content_container">
				<div class="roleadd_container">
					<form action="" method="post">
						<div>
							<div class="text_form left">
								<label class="labelname" for="">用户名:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">姓名:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">邮箱:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">性别:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">工号:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">办公电话:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">家庭电话:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">手机:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">职务:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">职能描述:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">出生日期:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">家庭住址:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">机构级别:</label>
								<select id="normal_jibie_select" class="normalselect">
				    				<option value="0">总公司</option>
				    				<option value="1">分公司</option>
				    				<option value="2">管理处</option>
				    			</select>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">机构名称:</label>
								<select id="normal_mingcheng_select" class="normalselect">
				    				<option value="0">总公司</option>
				    			</select>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">备注:</label>
								<input type="text" name="" id="" class="normaltext" style="width:710px;">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="text_form">
							<label class="labelname" for="">授予角色&nbsp;</label>
							<div class="clear"></div>
						</div>
						<div class="">
							<fieldset>
								<legend>可被授予的角色</legend>
						        <label class="floating">
						        	<input type="checkbox" value="add_project" name="permissionNames" id="permissionNames1"><input type="hidden" value="on" name="_permissionNames">
							        角色1
						        </label>
						        <label class="floating">
						        	<input type="checkbox" value="edit_project" name="permissionNames" id="permissionNames2"><input type="hidden" value="on" name="_permissionNames">
							        角色2
						        </label>
						        <label class="floating">
						        	<input type="checkbox" value="select_project_modules" name="permissionNames" id="permissionNames3"><input type="hidden" value="on" name="_permissionNames">
							        角色3
						        </label>
						    </fieldset>
						</div>
						<div class="top5 left40">
							<input type="submit" value="创建" class="btn left20"/>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp"  charEncoding="UTF-8"></c:import>
	</body>
</html>