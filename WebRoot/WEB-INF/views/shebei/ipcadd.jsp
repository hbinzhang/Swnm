<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String jsonmt = JSONObject.fromObject((Map<String,List<Map<String, String>>>)request.getAttribute("managementagencies")).toString();
	String cmt = (String)request.getAttribute("ipcToAddOrUpdate.managementagency");
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
		//ajax请求不使用缓存
		$.ajaxSetup({ cache: false }); 
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
				<li><a href="<c:url value="/videomonitor/loadIpc.action"></c:url>">IPC设备</a>
				</li>
				<li>添加</li>
			</ul>
		</div>
		<div id="content_container">
			<c:import url="../layout/shebeitab.jsp?sTabLink=ipc"
				charEncoding="UTF-8"></c:import>
			<div class="roleadd_container">
				<form action="<%=basePath%>videomonitor/<c:choose><c:when test='${isadd == true }'>addIpc</c:when><c:otherwise>updateIpc</c:otherwise></c:choose>.action" method="POST"
					enctype="multipart/form-data" id="ipcform">
					<div class='dialogModal_content'>
						<div class="companysubject_add_form_cantainer">
							<div id="companysubject_add_form">
								<!-- 					<form id="fipc" action="<%=basePath%>addIpc.do" method="POST" enctype="multipart/form-data"> -->
								<%-- <input name="ipcToAddOrUpdate.ipcid" id="iipcid"
									style="display:none;" value="${ ipcToAddOrUpdate.ipcid }"/> --%>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备ID:</label>
										<input type="text" id="fipcid" class="normaltext left validate[required,maxSize[7]<c:if test='${ isadd == true }'>,funcCall[checkIpcIdExistAjax]</c:if>]"
											style="width: 70%;" name="ipcToAddOrUpdate.ipcid" value="${ ipcToAddOrUpdate.ipcid }" <c:if test="${ isadd == false }">readonly="readonly"</c:if>></input>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">NVR编号:</label>
										<select class="normalselect left" style="width: 72%;"
											name="ipcToAddOrUpdate.nvr.nvrid">
											<option></option>
											<c:forEach items="${ nvrs }" var="vnvr">
												<option value="${vnvr.nvrid}"
													<c:if test="${ipcToAddOrUpdate.nvr.nvrid == vnvr.nvrid }">selected="selected"</c:if>>${
													vnvr.devname }</option>
											</c:forEach>
											<%-- <option value="0" <c:if test="${ipcToAddOrUpdate.nvr.nvrid == 0}">selected="selected"</c:if>>海康nvr</option>
											<option value="1" <c:if test="${ipcToAddOrUpdate.nvr.nvrid == 1}">selected="selected"</c:if>>大华nvr</option> --%>
											<%-- <c:if test="${ipcToAddOrUpdate.vendor.vendorid == 0}">
											<option value="0" selected="selected">海康nvr</option>
											<option value="1">大华nvr</option>
											</c:if>
											<c:if test="${ipcToAddOrUpdate.vendor.vendorid == 1}">
											<option value="0">海康nvr</option>
											<option value="1" selected="selected">大华nvr</option>
											</c:if> --%>
										</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">IP:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="ipcToAddOrUpdate.ip"
											value="${ ipcToAddOrUpdate.ip }"/>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">端口:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[integer]]"
											style="width: 70%;" name="ipcToAddOrUpdate.port"
											value="${ ipcToAddOrUpdate.port }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">子网掩码:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="ipcToAddOrUpdate.netmask"
											value="${ ipcToAddOrUpdate.netmask }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">网关:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="ipcToAddOrUpdate.gateway"
											value="${ ipcToAddOrUpdate.gateway }"/>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">用户名:</label>
										<input type="text" id="" class="normaltext left validate[required,maxSize[16],custom[english]]"
											style="width: 70%;" name="ipcToAddOrUpdate.username"
											value="${ ipcToAddOrUpdate.username }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">密码:</label>
										<input type="password" id="" class="normaltext left validate[required,maxSize[16],custom[english]]"
											style="width: 70%;" name="ipcToAddOrUpdate.password"
											value="${ ipcToAddOrUpdate.password }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>

								<%-- <div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备类型:</label>
										<select class="normalselect left" style="width: 73%;"
											name="ipcToAddOrUpdate.devictype">
											<option value="NVR" <c:if test="${ ipcToAddOrUpdate.devictype == 'NVR' }">selected='selected'</c:if>><option>
											<option value="IPC" <c:if test="${ ipcToAddOrUpdate.devictype == 'IPC' }"></c:if> >IPC</option>
										</select>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" name="ipcToAddOrUpdate.devictype" value="IPC" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备通道号:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" name="ipcToAddOrUpdate.channel" value="${ ipcToAddOrUpdate.channel }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div> --%>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">分公司:</label>
										<select class="normalselect left validate[required]" style="width: 72%;"
											name="ipcToAddOrUpdate.branch" id="sib">
											<option></option>
											<c:forEach items="${ branchs }" var="vbranch">
												<option value="${vbranch['id']}"
													<c:if test="${ipcToAddOrUpdate.branch == vbranch['id'] }">selected="selected"</c:if>>${
													vbranch['name'] }</option>
											</c:forEach>
										</select>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">管理处:</label>
										<select class="normalselect left validate[required]" style="width: 72%;" id="smt"
											name="ipcToAddOrUpdate.managementagency">
											<option></option>
											<%-- <c:forEach items="${ managementagencies }" var="mt">
												<option value="${ mt['id'] }" <c:if test="${ ipcToAddOrUpdate.managementagency == mt['id'] }">selected='selected'</c:if>>${ mt['name'] }</option>
											</c:forEach> --%>
											<%-- <option value="GLC1" <c:if test="${ ipcToAddOrUpdate.managementagency == 'GLC1' }">selected='selected'</c:if>>管理处1</option>
											<option value="GLC2" <c:if test="${ ipcToAddOrUpdate.managementagency == 'GLC2' }">selected='selected'</c:if>>管理处2</option> --%>
										</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">经度:</label>
										<input type="text" id="" class="normaltext left validate[required]"
											style="width: 70%;" name="ipcToAddOrUpdate.ipclongitude"
											value="${ ipcToAddOrUpdate.ipclongitude }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">纬度:</label>
										<input type="text" id="" class="normaltext left validate[required]"
											style="width: 70%;" name="ipcToAddOrUpdate.ipclatitude"
											value="${ ipcToAddOrUpdate.ipclatitude }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备名称:</label>
										<input type="text" id="" class="normaltext left validate[required,maxSize[21]]"
											style="width: 70%;" name="ipcToAddOrUpdate.devname"
											value="${ ipcToAddOrUpdate.devname }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备类型:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ ipcToAddOrUpdate.devtype }"
											disabled='disabled' />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
									<%-- <div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备友好名:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" name="ipcToAddOrUpdate.devfriendlyname" value="${ ipcToAddOrUpdate.devfriendlyname }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div> --%>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">系统版本号:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ ipcToAddOrUpdate.version }"
											disabled='disabled' />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">厂商编号:</label>
										<select class="normalselect left" style="width: 72%;"
											name="ipcToAddOrUpdate.vendor.vendorid">
											<option></option>
											<c:forEach items="${ vendors }" var="vendor">
												<option value="${ vendor.vendorid }"
													<c:if test="${ipcToAddOrUpdate.vendor.vendorid == vendor.vendorid}">selected='selected'</c:if>>${
													vendor.vendorname }</option>
											</c:forEach>
										</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="left40 top20">
						<input type="button" value="保存" class="btn left20 top10" onclick="saveipc(${isadd},'ipcform')"/> <a
							href="<%=basePath%>videomonitor/loadIpc.action" class="hrefbtn">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
</body>
</html>