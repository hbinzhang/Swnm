<%@page import="com.entity.videomonitor.TVmDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.math.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String jsonmt = JSONObject.fromObject((Map<String,List<Map<String, String>>>)request.getAttribute("managementagencies")).toString();
	String cmt = (String)request.getAttribute("decoderToAddOrUpdate.managementagency");
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
				<li><a href="<c:url value="/videomonitor/loadDecoder.action"></c:url>">解码器设备</a>
				</li>
				<li>添加</li>
			</ul>
		</div>
		<div id="content_container">
			<c:import url="../layout/shebeitab.jsp?sTabLink=decoder"
				charEncoding="UTF-8"></c:import>
			<div class="roleadd_container">
				<form id="decoderform" action="<%=basePath%>videomonitor/<c:choose><c:when test='${isadd == true }'>addDecoder</c:when><c:otherwise>updateDecoder</c:otherwise></c:choose>.action" method="POST">
					<div class='dialogModal_content'>
						<div class="companysubject_add_form_cantainer">
							<div id="companysubject_add_form">
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备ID:</label>
										<input type="text" id="fdecoderid" class="normaltext left validate[required,maxSize[7]<c:if test='${ isadd == true }'>,funcCall[checkDecoderIdExistAjax]</c:if>]"
											style="width: 70%;" name="decoderToAddOrUpdate.decoderid" value="${ decoderToAddOrUpdate.decoderid }" <c:if test="${ isadd == false }">readonly="readonly"</c:if>></input>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备类型:</label>
										<input type="text" id="" class="normaltext left" name="decoderToAddOrUpdate.devictype"
											style="width: 70%;" value="${ decoderToAddOrUpdate.devictype }"/>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">IP:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="decoderToAddOrUpdate.ip"
											value="${ decoderToAddOrUpdate.ip }"/>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">端口:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[integer]]"
											style="width: 70%;" name="decoderToAddOrUpdate.port"
											value="${ decoderToAddOrUpdate.port }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">用户名:</label>
										<input type="text" id="" class="normaltext left validate[required,maxSize[16],custom[english]]"
											style="width: 70%;" name="decoderToAddOrUpdate.username"
											value="${ decoderToAddOrUpdate.username }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">密码:</label>
										<input type="password" id="" class="normaltext left validate[required,maxSize[16],custom[english]]"
											style="width: 70%;" name="decoderToAddOrUpdate.password"
											value="${ decoderToAddOrUpdate.password }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">分公司:</label>
										<select class="normalselect left validate[required]" style="width: 73%;"
											name="decoderToAddOrUpdate.branch" id="sib">
											<option></option>
											<c:forEach items="${ branchs }" var="vbranch">
												<option value="${vbranch['id']}"
													<c:if test="${decoderToAddOrUpdate.branch == vbranch['id'] }">selected="selected"</c:if>>${
													vbranch['name'] }</option>
											</c:forEach>
										</select>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">管理处:</label>
										<select class="normalselect left validate[required]" style="width: 73%;" id="smt"
											name="decoderToAddOrUpdate.managementagency">
											<option></option>
										</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备名称:</label>
										<input type="text" id="" class="normaltext left validate[required,maxSize[21]]"
											style="width: 70%;" name="decoderToAddOrUpdate.devname"
											value="${ decoderToAddOrUpdate.devname }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">窗口序号:</label>
										<select class="normalselect left" style="width: 73%;" id="smt"
											name="decoderToAddOrUpdate.ordernumber">
											<%
												BigDecimal onobj = ((TVmDecoder)request.getAttribute("decoderToAddOrUpdate")).getOrdernumber();
												int ordernumber = onobj == null ? -1 : onobj.intValue();
												for(int i=1;i<=6;i++){
											 %>
											<option value="<%=i %>" <c:if test="<%=i == ordernumber %>" >selected='selected'</c:if>><%=i %></option>
											<%
												}
											 %>
										</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								
							</div>
						</div>
					</div>
					<div class="left40 top20">
						<input type="button" value="保存" class="btn left20 top10" onclick="savedecoder(${isadd},'decoderform')"/> <a
							href="<%=basePath%>videomonitor/loadDecoder.action" class="hrefbtn">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
</body>
</html>