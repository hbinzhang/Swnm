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
		<link href="${pageContext.request.contextPath}/resources/plugins/ztree/zTreeStyle.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/plugins/ztree/jquery.ztree.core-3.5.min.js" type="text/javascript"></script>
		<SCRIPT type="text/javascript">
			var setting = {
				data: {
					simpleData: {
						enable: true
					}
				}
			};
	
			var zNodes =[
				{ id:0, pId:-1, name:"总公司", open:true, iconOpen:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/1_open.png", 
					iconClose:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/1_close.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:1, pId:0, name:"北京分公司", open:true, iconOpen:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/1_open.png", 
					iconClose:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/1_close.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:11, pId:1, name:"海淀管理处", icon:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/2.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:12, pId:1, name:"朝阳管理处", icon:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/3.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:13, pId:1, name:"大兴管理处", icon:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/5.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:2, pId:0, name:"河北分公司", open:true, icon:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/4.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:21, pId:2, name:"石家庄管理处", icon:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/6.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:22, pId:2, name:"衡水管理处", icon:"${pageContext.request.contextPath}/resources/plugins/ztree/img/diy/7.png",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:3, pId:0, name:"河南分公司", open:true,url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:31, pId:3, name:"郑州管理处",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:32, pId:3, name:"商丘管理处",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"},
				{ id:33, pId:3, name:"开封管理处",url:"<c:url value='/views/jigou/info.jsp'></c:url>",target:"_self"}
	
			];
	
			$(document).ready(function(){
				$.fn.zTree.init($("#jigoutree"), setting, zNodes);
			});
	</SCRIPT>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="content_container" style="margin-top:0;">
			    <div id="lefttree">
			    	<ul id="jigoutree" class="ztree"></ul>
			    </div>
			    <div id="rightcontent">
			    	<div id="right_nav_place">
						<div id="title">位置：</div>
						<ul>
							<li><a href="<c:url value=""></c:url>">首页</a></li>
							<li><a href="<c:url value=""></c:url>">系统管理</a></li>
							<li>机构管理(XXXXXX)</li>
						</ul>
					</div>
			    	<div>
			    		<div class="roleadd_container">
							<div style="padding:10px;">
								<div>
									<h2 style="font-size:15px;">创建</h2>
									<div class="top10">
										<div class="left" style="width: 30%;">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">组织机构代码:</label>
											<input type="text" id="" class="normaltext left" style="width: 70%;" value=""/>
											<div class="clear"></div>
										</div>
										<div class="left" style="width: 30%;">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">组织机构名称:</label>
											<input type="text" id="" class="normaltext left" style="width: 70%;" value=""/>
											<div class="clear"></div>
										</div>
										<div class="left" style="width: 30%;">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">等级:</label>
											<select class="normalselect left" style="width: 70%;">
												<option>总公司</option>
												<option>分公司</option>
												<option>管理处</option>
											</select>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top10">
										<div class="left" style="width: 90%;">
											<label style="display: block;font-size:13px;width:6.6%;float: left;text-align: right;height:30px;line-height:30px;">备注:</label>
											<input type="text" id="" class="normaltext left" style="width: 90%;" value=""/>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top10 left40">
										<input type="button" value="添加" class="btn">
									</div>
								</div>
							</div>
			    		</div>
			    		<div class="roleadd_container">
							<div style="padding:10px;">
								<div>
									<h2 style="font-size:15px;">更改信息</h2>
									<div class="top10">
										<div class="left" style="width: 30%;">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">组织机构代码:</label>
											<input type="text" id="" class="normaltext left" style="width: 70%;" value="34543654646456456"/>
											<div class="clear"></div>
										</div>
										<div class="left" style="width: 30%;">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">组织机构名称:</label>
											<input type="text" id="" class="normaltext left" style="width: 70%;" value="总公司"/>
											<div class="clear"></div>
										</div>
										<div class="left" style="width: 30%;">
											<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">等级:</label>
											<select class="normalselect left" style="width: 70%;">
												<option>总公司</option>
												<option>分公司</option>
												<option>管理处</option>
											</select>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top10">
										<div class="left" style="width: 90%;">
											<label style="display: block;font-size:13px;width:6.6%;float: left;text-align: right;height:30px;line-height:30px;">备注:</label>
											<input type="text" id="" class="normaltext left" style="width: 90%;" value="备注"/>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top10 left40">
										<input type="button" value="保存" class="btn">
									</div>
								</div>
							</div>
			    		</div>
			    	</div>
			    </div>
			    <div class="clear"></div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>