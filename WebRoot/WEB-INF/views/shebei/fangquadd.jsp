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
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/views/shebei/fangqu.jsp"></c:url>">防区管理</a></li>
					<li>添加</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=fangqu" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<div style="margin-left:30%;margin-top:20px;">
						<div>
							<div class="left step stepcurrent">
								添加基本信息
							</div>
							<div class="next left">
							</div>
							<div class="left step stepafter">
								添加设备
							</div>
							<div class="clear"></div>
						</div>
					</div>	
					<form action="" method="post">
						<div>
							<div class="text_form left">
								<label class="labelname" for="">防区ID:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">防区名称:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">分公司:</label>
								<select id="normal_gongsi_select" class="normalselect">
				    				<option value="1">北京分公司</option>
				    				<option value="2">河北分公司</option>
				    			</select>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">管理处:</label>
								<select id="normal_guanlichu_select" class="normalselect">
				    				<option value="1">海淀管理处</option>
				    				<option value="1">大兴管理处</option>
				    			</select>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">起始经度:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">起始纬度:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">终止经度:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">终止纬度:</label>
								<input type="text" name="" id="" class="normaltext">
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div>
							<div class="text_form left">
								<label class="labelname" for="">左/右岸:</label>
								<select class="normalselect">
									<option>左岸</option>
									<option>右岸</option>
								</select>
								<div class="clear"></div>
							</div>
							<div class="text_form left">
								<label class="labelname" for="">附加信息:</label>
								<input type="text" name="" id="" class="normaltext" style="width:710px;">
							</div>
							<div class="clear"></div>
						</div>
				    	<div class="top10 bottom10 left10" style="">
							<%--<input type="submit" value="保存" class="btn" style="margin-left:50px;">
							--%><a href="<c:url value='/views/shebei/fangqushebeiadd.jsp'></c:url>" class="hrefbtn">下一步</a>
							<a href="<c:url value='/views/shebei/fangqu.jsp'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>