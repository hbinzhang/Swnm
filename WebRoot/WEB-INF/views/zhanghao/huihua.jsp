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
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">系统管理</a></li>
					<li>会话管理</li>
				</ul>
			</div>
			<div id="content_container">
			    <div class="search_container">
			    	<form action="" method="get">
			    		<div>
				    		<div class="left" style="margin-top:5px;">
				    			<div class="single_filter_container left">
						    		<label class="search_name">查询条件:</label>
						    		<div class="left single_filter" style="margin-top:8px;margin-left:0px;" id="jigou_gonghao_search_control">
						    			<input type="radio" value=1 name="search_filter" id="search_by_jigou" checked="checked">按机构查询
						    			<input type="radio" value=2 name="search_filter" id="search_by_gonghao" class="left20">按工号查询
						    		</div>
						    	</div>
						    	<div class="left" id="searchjigou_container">
						    		<div class="single_filter_container left">
							    		<label class="search_name">机构级别:</label>
							    		<div class="left single_filter">
							    			<select id="normal_jibie_select" class="normalselect">
							    				<option value="0">总公司</option>
							    				<option value="1">分公司</option>
							    				<option value="2">管理处</option>
							    			</select>
							    		</div>
							    	</div>
							    	<div class="single_filter_container left">
							    		<label class="search_name">机构名称:</label>
							    		<div class="left single_filter">
							    			<select id="normal_mingcheng_select" class="normalselect">
							    				<option value="0">总公司</option>
							    			</select>
							    		</div>
							    	</div>
						    	</div>
						    	<div class="single_filter_container left" style="display:none;" id="searchgonghao_container">
						    		<label class="search_name">工号:</label>
						    		<div class="left single_filter">
						    			<input type="text" name="" id="" class="normaltext">
						    		</div>
						    	</div>
						    	<div class="clear"></div>
					    	</div>
					    	<div class="single_filter_container left" style="margin-left: 20px;">
				    			<input type="submit" value="查询" class="btn">
				    		</div>
				    	</div>
				    	<div>
				    		<div class="clear"></div>
				    	</div>
			    	</form>
			    </div>
			    <div class="tableui_container">
					<table class="tableui">
				    	<thead>
					    	<tr>    
					    		<th><span>会话标识</span></th>   
						        <th><span>工号</span></th>
						        <th><span>用户名</span></th>
						        <th><span>账号类型</span></th>
						        <th><span>登录时间</span></th>
						        <th><span>主机名称</span></th>
								<th><span>主机地址</span></th>
								<th><span>机构名称</span></th>
								<th><span>操作</span></th>
					        </tr>
				        </thead>
				        <tbody>
				        	<c:forEach begin="1" end="15" var="index">
					        	<tr>        
					        		<td>0000${index }</td>
							        <td>00${index }</td>
							        <td>帐号${index }</td>
							        <td>类型${index }</td>
							        <td>2014-1-${index } 22:59:59</td>
									<td>主机${index }</td>
							        <td>192.168.1.${index }</td>
									<td>标志${index }</td>
									<td align="center">
										<a href="javascript:void(0);" title="清除" class="delete clearuserlink">
											清除用户
										</a>
									</td>
						        </tr> 
				        	</c:forEach>
				        </tbody>
				    </table>
				</div>
				<c:import url="../layout/pager.jsp" charEncoding="UTF-8"></c:import>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>