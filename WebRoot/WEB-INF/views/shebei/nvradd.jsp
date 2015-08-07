<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.util.*"%>
<%
	String jsonmt = JSONObject.fromObject((Map<String,List<Map<String, String>>>)request.getAttribute("managementagencies")).toString();
	String cmt = (String)request.getAttribute("nvrToAddOrUpdate.managementagency");
	boolean isadd = (Boolean)request.getAttribute("isadd");
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
		
		$('#smt').change(function(){
			hideNoAuthorityIpc();
		});
		
		$.ajax({
			url:'<%=basePath%>videomonitor/loadIpcsByOrgIdAjax.action',
			data:'branch=&managementagency=&nvridToLoadIpcs=' + $('#invrid').val(),
			type:'post',
			dataType:'json',
			success:function(d,e){
				var ipcs = eval(d)[0].ipcs;
				var length = ipcs.length;
				var isAdd = <%=isadd%>;
				if(isAdd){
					for(var i = 0; i< length;i++){
						if(!ipcs[i].nvr || !ipcs[i].nvr.nvrid || ipcs[i].nvr.nvrid == ''){
							$('#tbn').append("<tr class='b"+ipcs[i].branch + ipcs[i].managementagency+"'><td><input type='checkbox'/></td><td>"+ipcs[i].devname+"</td><td style='display:none;'><input name='ipcnochecked' value='"+ipcs[i].ipcid+"'></input></td></tr>");
						}
					}
				}else{
					for(var i = 0; i< length;i++){
						if(ipcs[i].nvr && ipcs[i].nvr.nvrid && ipcs[i].nvr.nvrid != ''){
							$("#tbs").append("<tr class='b"+ipcs[i].branch + ipcs[i].managementagency+"'><td><input type='checkbox'/></td><td>"+ipcs[i].devname+"</td><td style='display:none;'><input name='ipcchecked' value='"+ipcs[i].ipcid+"'></input></td></tr>");
						}else
						{
							$('#tbn').append("<tr class='b"+ipcs[i].branch + ipcs[i].managementagency+"'><td><input type='checkbox'/></td><td>"+ipcs[i].devname+"</td><td style='display:none;'><input name='ipcnochecked' value='"+ipcs[i].ipcid+"'></input></td></tr>");
						}
					}
				}
				hideNoAuthorityIpc();
			},
			error:function(d,e){
				alert(d);			
			}
		});
	});
	
	function hideNoAuthorityIpc(){
		$('#tbn tr').css('display','none');
		$('#tbn').find('.b'+$('#sib').val()+$('#smt').val()).css('display','block');
	}
	
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
		hideNoAuthorityIpc();
	}
	function rightMoveOne() {
		$("#tbn tr").each(function(d, i) {
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			if (td1 && td1[0].checked) {
				var td2 = $(this).find("td:eq(2) input:eq(0)");
				if (td2) {
					td2.attr("name", "ipcchecked");
				}
				td1[0].checked = false;
				$(this).remove();
				$("#tbs").append($(this));
			}
		});
	}
	function leftMoveOne() {
		$("#tbs tr").each(function(d, i) {
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			if (td1 && td1[0].checked) {
				var td2 = $(this).find("td:eq(2) input:eq(0)");
				if (td2) {
					td2.attr("name", "ipcnochecked");
				}
				td1[0].checked = false;
				$(this).remove();
				$("#tbn").append($(this));
			}
		});
	}
	function rightMoveAll() {
		$("#tbn tr").each(function(d, i) {
			var cssdisplay = $(this).css('display');
			if(cssdisplay=='none'){
				return true;
			}
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			var td2 = $(this).find("td:eq(2) input:eq(0)");
			if (td2) {
				td2.attr("name", "ipcchecked");
			}
			td1[0].checked = false;
			$(this).remove();
			$("#tbs").append($(this));
		});
	}
	function leftMoveAll() {
		$("#tbs tr").each(function(d, i) {
			var cssdisplay = $(this).css('display');
			if(cssdisplay=='none'){
				return true;
			}
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			var td2 = $(this).find("td:eq(2) input:eq(0)");
			if (td2) {
				td2.attr("name", "ipcnochecked");
			}
			td1[0].checked = false;
			$(this).remove();
			$("#tbn").append($(this));
		});
	}
</script>
</head>
<body>
	<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
	<div id="main_content_container">
		<div id="nav_place">
			<div id="title">位置：</div>
			<ul>
				<li><a href="<c:url value=""></c:url>">首页</a>
				</li>
				<li><a href="<c:url value="/videomonitor/loadIpc.action"></c:url>">设备管理</a>
				</li>
				<li><a href="<c:url value="/videomonitor/loadNvr.action"></c:url>">Nvr设备</a>
				</li>
				<li>添加</li>
			</ul>
		</div>
		<div id="content_container">
			<c:import url="../layout/shebeitab.jsp?sTabLink=nvr"
				charEncoding="UTF-8"></c:import>
			<div class="roleadd_container">
				<form id="nvrform" action="<%=basePath%>videomonitor/<c:choose><c:when test='${isadd == true }'>addNvr</c:when><c:otherwise>updateNvr</c:otherwise></c:choose>.action"
					method="POST">
					<div class='dialogModal_content'>
						<div class="companysubject_add_form_cantainer">
							<div id="companysubject_add_form">
								<!-- 					<form id="fnvr" action="<%=basePath%>addNvr.do" method="POST" enctype="multipart/form-data"> -->
								<%-- <input name="nvrToAddOrUpdate.nvrid" id="invrid"
									style="display:none;" value="${ nvrToAddOrUpdate.nvrid }" /> --%>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备ID:</label>
										<input type="text" id="invrid" class="normaltext left validate[required,maxSize[7]<c:if test='${ isadd == true }'>,funcCall[checkNvrIdExistAjax]</c:if>]"
											style="width: 70%;" name = "nvrToAddOrUpdate.nvrid" value="${ nvrToAddOrUpdate.nvrid }" <c:if test="${ isadd == false }">readonly="readonly"</c:if>/>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">系统版本号:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.version }"
											disabled="disabled" />
										<div class="clear"></div>
									</div>
									<%-- <div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">磁盘数量:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" name="nvrToAddOrUpdate.disknum" value="${ nvrToAddOrUpdate.disknum }" />
										<div class="clear"></div>
									</div> --%>
									<%-- <div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">NVR编号:</label>
										<select class="normalselect left" style="width: 70%;"
											name="nvrToAddOrUpdate.nvr.nvrid">
											<option></option>
											<option value="0" <c:if test="${nvrToAddOrUpdate.vendor.vendorid == 0}">selected="selected"</c:if>>海康nvr</option>
											<option value="1" <c:if test="${nvrToAddOrUpdate.vendor.vendorid == 1}">selected="selected"</c:if>>大华nvr</option>
											<c:if test="${nvrToAddOrUpdate.vendor.vendorid == 0}">
											<option value="0" selected="selected">海康nvr</option>
											<option value="1">大华nvr</option>
											</c:if>
											<c:if test="${nvrToAddOrUpdate.vendor.vendorid == 1}">
											<option value="0">海康nvr</option>
											<option value="1" selected="selected">大华nvr</option>
											</c:if>
										</select>
										<div class="clear"></div>
									</div> --%>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">IP:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="nvrToAddOrUpdate.ip"
											value="${ nvrToAddOrUpdate.ip }"/>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">端口:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[integer]]"
											style="width: 70%;" name="nvrToAddOrUpdate.port"
											value="${ nvrToAddOrUpdate.port }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">子网掩码:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="nvrToAddOrUpdate.netmask"
											value="${ nvrToAddOrUpdate.netmask }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">网关:</label>
										<input type="text" id="" class="normaltext left validate[required,custom[ipv4]]"
											style="width: 70%;" name="nvrToAddOrUpdate.gateway"
											value="${ nvrToAddOrUpdate.gateway }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">用户名:</label>
										<input type="text" id="" class="normaltext left validate[required,maxSize[16],custom[english]]"
											style="width: 70%;" name="nvrToAddOrUpdate.username2"
											value="${ nvrToAddOrUpdate.username2 }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">密码:</label>
										<input type="password" id="" class="normaltext left validate[required,maxSize[16],custom[english]]"
											style="width: 70%;" name="nvrToAddOrUpdate.password"
											value="${ nvrToAddOrUpdate.password }" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备名:</label>
										<input type="text" id="" class="normaltext left validate[required,maxSize[21]]"
											style="width: 70%;" name="nvrToAddOrUpdate.devname"
											value="${ nvrToAddOrUpdate.devname }" />
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备类型:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.devtype }"
											disabled="disabled" />
										<div class="clear"></div>
									</div>
									<%-- <div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备友好名:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" name="nvrToAddOrUpdate.devfriendlyname" value="${ nvrToAddOrUpdate.devfriendlyname }" />
										<div class="clear"></div>
									</div> --%>
									<div class="clear"></div>
								</div>
								<div class="top10">

									<%-- <div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备通道号:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" name="nvrToAddOrUpdate.channel" value="${ nvrToAddOrUpdate.channel }" />
										<div class="clear"></div>
									</div> --%>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">分公司:</label>
										<select class="normalselect left validate[required]" style="width: 72%;"
											name="nvrToAddOrUpdate.branch" id="sib">
											<option></option>
											<c:forEach items="${ branchs }" var="vbranch">
												<option value="${vbranch['id']}"
													<c:if test="${nvrToAddOrUpdate.branch == vbranch['id'] }">selected="selected"</c:if>>${
													vbranch['name'] }</option>
											</c:forEach>
										</select>
										<div class="clear"></div>
									</div>
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">管理处编号:</label>
										<select class="normalselect left validate[required]" style="width: 72%;"
											name="nvrToAddOrUpdate.managementagency" id="smt">
											<option></option>
										</select>
										<div class="clear"></div>
									</div>

									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">厂商编号:</label>
										<select class="normalselect left" style="width: 72%;"
											name="nvrToAddOrUpdate.vendor.vendorid">
											<option></option>
											<c:forEach items="${ vendors }" var="vendor">
												<option value="${ vendor.vendorid }"
													<c:if test="${nvrToAddOrUpdate.vendor.vendorid == vendor.vendorid}">selected='selected'</c:if>>${
													vendor.vendorname }</option>
											</c:forEach>
										</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<%-- <div class="left" style="width: 48%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">设备状态:</label>
										<select class="normalselect left" style="width: 73%;"
											name="nvrToAddOrUpdate.nvrfault">
											<option value="0" <c:if test="${ nvrToAddOrUpdate.nvrfault == 0 }">selected='selected'</c:if> >正常</option>
											<option value="1" <c:if test="${ nvrToAddOrUpdate.nvrfault == 1 }">selected='selected'</c:if>>故障</option>
										</select>
										<div class="clear"></div>
									</div> --%>
									<div id="ipcs" style="width:100%;height:300px;">
										<div style='width:85%;height:300px;margin:auto auto;'>
											<div
												style="width:44%;height:300px;float:left;border:1px solid lightblue;overflow:auto;">
												<a
													style="font-size:16px;background:lightblue;width:100%;display:block;">未分配摄像机：</a>
												<table id="tbn">
													<tr style='display:none;'><td><input name="ipcnochecked"/></td></tr>
												</table>
											</div>
											<div style="width:10%;height:100%;float:left;">
												<div style="width:90%;margin:30px auto;">
													<input type="button" value="&gt;"
														style="width:100%;height:40px;margin:10px auto;"
														onclick="rightMoveOne()" /> <input type="button"
														value="&lt;"
														style="width:100%;height:40px;margin:10px auto;"
														onclick="leftMoveOne()" /> <input type="button"
														value="&gt;&gt;"
														style="width:100%;height:40px;margin:10px auto;"
														onclick="rightMoveAll()" /> <input type="button"
														value="&lt;&lt;"
														style="width:100%;height:40px;margin:10px auto;"
														onclick="leftMoveAll()" />
												</div>
											</div>
											<div
												style="width:44%;height:300px;float:left;border:1px solid lightblue;overflow:auto;">
												<a
													style="font-size:16px;background:lightblue;width:100%;display:block;">已分配摄像机</a>
												<table id="tbs">
													<tr style='display:none;'><td><input name="ipcchecked"/></td></tr>
												</table>
											</div>
											<div class="clear"></div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="left40 top20">
						<input type="button" value="保存" class="btn left20 top10" onclick="savenvr(${isadd},'nvrform')"/> <a
							href="<%=basePath%>videomonitor/loadNvr.action" class="hrefbtn">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
</body>
</html>