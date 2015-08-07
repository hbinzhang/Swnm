<%@page import="com.entity.videomonitor.TVmIva"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String jsonmt = JSONObject.fromObject((Map<String,List<Map<String, String>>>)request.getAttribute("managementagencies")).toString();
	String cmt = (String)request.getAttribute("ivaToAddOrUpdate.managementagency");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>安防综合监控系统</title>
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="icon" type="image/x-icon" />
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resources/css/popModal.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/shebei.js"
		type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$('#sib').change(function(){
			reloadMT($(this));
		});
		
		reloadMT($('#sib'));
	});
	function reloadMT(branch){
		$('#smt').children('option').remove();
			var branch = branch.children('option:selected').val();
			var jmt = eval("("+'<%=jsonmt%>'+")");
			var mts = jmt[branch];
			if(mts){
			var length = mts.length;
			for(var i = 0 ; i < length;i++){
				var selected = "";
				if('<%=cmt%>' == mts[i].id) {
					selected = "selected='selected'";
				}
				$('#smt').append(
						"<option value='"+mts[i].id+"' "+selected+">"
								+ mts[i].name + "</option>");
			}
		}
	}
</script>
</head>
<body>
	<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
	<div id="main_content_container">
		<div id="nav_place">
			<div id="title">位置：</div>
			<ul>
				<li><a href="<c:url value=""></c:url>">首页</a></li>
				<li><a href="<c:url value="/videomonitor/loadIpc.action"></c:url>">设备管理</a></li>
				<li><a href="<c:url value="/videomonitor/loadIva.action"></c:url>">智能视频设备</a>
				</li>
				<li>添加</li>
			</ul>
		</div>
		<div id="content_container">
			<c:import url="../layout/shebeitab.jsp?sTabLink=iva"
				charEncoding="UTF-8"></c:import>
			<div class="roleadd_container">
				<form id="ivaform" action="<%=basePath%>videomonitor/<c:choose><c:when test='${isadd == true }'>addIva</c:when><c:otherwise>updateIva</c:otherwise></c:choose>.action" method="POST">
					<div class='dialogModal_content'>
						<div class="companysubject_add_form_cantainer">
							<div id="companysubject_add_form">
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备ID:</label>
										<input type="text" id="fivaid" class="normaltext left validate[required,maxSize[7]<c:if test='${ isadd == true }'>,funcCall[checkIvaIdExistAjax]</c:if>]"
											style="width: 70%;" name="ivaToAddOrUpdate.ivaID" value="${ ivaToAddOrUpdate.ivaID }" <c:if test="${ isadd == false }">readonly="readonly"</c:if>></input>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备名:</label>
										<input type="text" id="" class="normaltext left validate[required]"
											style="width: 70%;" name="ivaToAddOrUpdate.name"
											value="${ivaToAddOrUpdate.name }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								
								<!--  -->
								
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">IP:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="ivaToAddOrUpdate.ip"
											value="${ ivaToAddOrUpdate.ip }"/>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">端口:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[integer]]"
											style="width: 70%;" name="ivaToAddOrUpdate.port"
											value="${ ivaToAddOrUpdate.port }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">分公司:</label>
										<select class="normalselect left validate[required]" style="width: 73%;"
											name="ivaToAddOrUpdate.branch" id="sib">
											<option></option>
											<c:forEach items="${ branchs }" var="vbranch">
												<option value="${vbranch['id']}"
													<c:if test="${ivaToAddOrUpdate.branch == vbranch['id'] }">selected="selected"</c:if>>${
													vbranch['name'] }</option>
											</c:forEach>
										</select>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">管理处:</label>
										<select class="normalselect left validate[required]" style="width: 73%;" id="smt"
											name="ivaToAddOrUpdate.managementagency">
											<option></option>
										</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="left40 top20">
						<input type="button" value="保存" class="btn left20 top10" onclick="saveiva(${isadd},'ivaform')"/> <a
							href="<%=basePath%>videomonitor/loadIva.action" class="hrefbtn">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
</body>
</html>