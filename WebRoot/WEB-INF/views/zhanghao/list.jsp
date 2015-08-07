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
		<script src="${pageContext.request.contextPath}/resources/js/page.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				TablePage("#userlist_table",15,"#pagin0",0,"pagin0");
			});
		</script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">系统管理</a></li>
					<li>帐号管理</li>
				</ul>
			</div>
			<div id="content_container">
			    <div class="search_container">
			    	<div>
				    	<div class="left" style="margin-top:5px;">
					    	<div class="single_filter_container left">
					    		<label class="search_name">工号:</label>
					    		<div class="left single_filter">
					    			<input type="text" name="" id="search_user_value" class="normaltext">
					    		</div>
					    	</div>
					    	<div class="clear"></div>
				    	</div>
				    	<div class="single_filter_container left" style="margin-left: 20px;">
			    			<input type="button" value="查询" class="btn" id="search_users">
			    			<a href="<c:url value='/views/zhanghao/add.jsp'></c:url>" class="hrefbtn">创建帐号</a>
			    		</div>
			    	</div>
			    	<div>
			    		<div class="clear"></div>
			    	</div>
			    </div>
			    <div class="tableui_container">
			    	<table class="tableui" id="userlist_table_search" style="display:none;">
			    		<thead>
					    	<tr>       
						        <th><span>用户名</span></th>
						        <th><span>机构名称</span></th>
						        <th><span>工号</span></th>
						        <th><span>姓名</span></th>
								<th><span>办公电话</span></th>
								<th><span>手机</span></th>
								<th><span>家庭电话</span></th>
								<th><span>邮箱</span></th>
								<th><span>角色</span></th>
								<th><span>操作</span></th>
					        </tr>
				        </thead>
				        <tbody>
				        </tbody>
			    	</table>
					<table class="tableui" id="userlist_table">
				    	<thead>
					    	<tr>       
						        <th><span>用户名</span></th>
						        <th><span>机构名称</span></th>
						        <th><span>工号</span></th>
						        <th><span>姓名</span></th>
								<th><span>办公电话</span></th>
								<th><span>手机</span></th>
								<th><span>家庭电话</span></th>
								<th><span>邮箱</span></th>
								<th><span>角色</span></th>
								<th><span>操作</span></th>
					        </tr>
				        </thead>
				        <tbody>
				        	<c:forEach begin="1" end="50" var="index">
				        		<tr id="00${index }">        
							        <td>
							        	<a href="javascript:void(0);" class="detail zhanghao_detail">zhangsan</a>
							        </td>
							        <td>北京分公司</td>
							        <td>00${index }</td>
							        <td>张三</td>
							        <td>85679865</td>
							        <td>15010435676</td>
									<td>34567890</td>
							        <td>zhangsan@126.com</td>
									<td>角色1，角色2</td>
									<td align="center">
										<a href="<c:url value='/views/zhanghao/edit.jsp'></c:url>" title="编辑" class="editlink">
											编辑
										</a>
										<a href="javascript:void(0);" title="修改密码" class="changepasswdlink left20">
											重置密码
										</a>
										<a href="javascript:void(0);" title="删除" class="delete deletelink left20">
											删除
										</a>
									</td>
						        </tr> 
				        	</c:forEach>
				        </tbody>
				    </table>
				</div>
				<div class="pagin" id="pagin0">
				  	<div class="message left">共<a class="blue" href="javascript:void(0);">50</a>条记录</div>
				</div>
				<div class="clear">
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		<div id="zhanghao_detail_container" style="display: none;z-index:0;" wid="60">
			<div class='dialogModal_header'>账号详细信息</div>
			<div class='dialogModal_content'>
				<table class="detail_table">
					<tbody>
						<tr>
							<td class="title_name">用户名:</td>
							<td class="content content_zhujiid">zhangsan</td>
							<td class="title_name">姓名:</td>
							<td class="content content_gaojingma">张三</td>
							<td class="title_name">性别:</td>
							<td class="content content_mingcheng">男</td>
						</tr>
						<tr>
							<td class="title_name">工号:</td>
							<td class="content content_zhujiid">1110</td>
							<td class="title_name">年龄:</td>
							<td class="content content_gaojingma">25</td>
							<td class="title_name">出生日期:</td>
							<td class="content content_mingcheng">1990-01-01</td>
						</tr>
						<tr>
							<td class="title_name">电子邮箱:</td>
							<td class="content content_shijian">zhangsan@126.com</td>
							<td class="title_name">移动电话:</td>
							<td class="content content_jibie">1234457464</td>
							<td class="title_name">办公电话:</td>
							<td class="content content_leixing">5678935</td>
						</tr>
						<tr>
							<td class="title_name">机构级别:</td>
							<td class="content content_zhujiid">分公司</td>
							<td class="title_name">机构名称:</td>
							<td class="content content_gaojingma">北京分公司</td>
							<td class="title_name">职务:</td>
							<td class="content content_mingcheng">巡逻员</td>
						</tr>
						<tr>
							<td class="title_name">家庭住址:</td>
							<td class="content content_shebeileixing" colspan="3">北京市昌平区某某小区</td>
							<td class="title_name">职务描述:</td>
							<td class="content content_shebeiid">巡逻员</td>
						</tr>
						<tr>
							<td class="title_name">备注:</td>
							<td colspan="6" class="content content_fujia">备注信息</td>
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