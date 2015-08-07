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
		<script src="${pageContext.request.contextPath}/resources/js/weilan.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">围栏主机</a></li>
					<li>编辑(围栏名称)</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=weilan" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<input type="hidden" id="redirectUrl" value="/fence/queryFence?${fenceCondition.searchUri}&page.size=10&page.offset=${page.offset }">
					<form action="" method="post" class="left50 top30" id="weilanedit_form">
						<div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">主机ID:</label>
								<input type="text" name="fenceBean.hostID" id="" class="normaltext disabletext" style="width: 500px;" readonly="readonly" value=${fenceBean.hostID }>
								<span class="input_tip">*主机ID要符合一定的规范</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">围栏类型:</label>
								<select id="" class="disableselect" style="width: 513px;" readonly="readonly" name="fenceBean.fenceType">
									<c:choose>
										
										<c:when test="${fenceBean.fenceType ==1 }">
											<option value="1" checked="checked">高压脉冲主机</option>
										</c:when>
										<c:when test="${fenceBean.fenceType ==2 }">
											<option value="2" checked="checked">一体化探测</option>
										</c:when>
										<c:when test="${fenceBean.fenceType ==3 }">
											<option value="3" checked="checked">防区型振动光纤</option>
										</c:when>
										<c:when test="${fenceBean.fenceType ==4 }">
											<option value="4" checked="checked">定位型振动光纤</option>
										</c:when>
									</c:choose>
								</select>
								<span class="input_tip">*围栏类型为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">围栏名称:</label>
								<input type="text" name="fenceBean.fenceName" id=""  class="normaltext validate[required,maxSize[32]]" style="width: 500px;" value="${fenceBean.fenceName }">
								<span class="input_tip">*围栏名称为必填项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">网络地址:</label>
								<input type="text" name="fenceBean.ip" id="" class="normaltext disabletext" style="width: 500px;" readonly="readonly" value="${fenceBean.ip }">
								<span class="input_tip">*网络地址为必填项,格式例如192.168.1.1</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">端口号:</label>
								<input type="text" name="fenceBean.port" id="" class="normaltext disabletext" readonly="readonly" style="width: 500px;" value="${fenceBean.port }">
								<span class="input_tip">*端口号为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">分公司:</label>
								<select id="yinpin_shebei_list" class="disableselect" style="width: 513px;" name="fenceBean.subComID">
									<c:forEach items="${session.session.orgIdAndNames.subCompanys}" var="company">
										<c:choose>
											<c:when test="${company.id==fenceBean.subComID}">
												<option value="${company.id }" selected="selected"><c:out value="${company.name }"></c:out></option>
											</c:when>
											<c:otherwise>
												<%--<option value="${company.id }"><c:out value="${company.name }"></c:out></option>--%>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<span class="input_tip">*分公司为必填项，选择分公司之后，对应的管理处会发生变化</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">管理处:</label>
								<select id="yinpin_shebei_list" class="disableselect" style="width: 513px;" name="fenceBean.mntMentID">
									<c:forEach items="${session.session.orgIdAndNames.managements }" var="mangement">
										<c:choose>
											<c:when test="${mangement.id==fenceBean.mntMentID}">
												<option value="${mangement.id }" selected="selected"><c:out value="${mangement.name }"></c:out></option>
											</c:when>
											<c:otherwise>
												<%--<option value="${mangement.id }"><c:out value="${mangement.name }"></c:out></option>--%>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								<span class="input_tip">*管理处为必填项，此处的管理处是上面分公司所属的管理处</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">经度:</label>
								<input type="text" name="fenceBean.efLongitude" id="" class="normaltext validate[required,custom[number]]" style="width: 500px;" value="${fenceBean.efLongitude }">
								<span class="input_tip">*经度为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">纬度:</label>
								<input type="text" name="fenceBean.efLatitude" id="" class="normaltext validate[required,custom[number]]" style="width: 500px;" value="${fenceBean.efLatitude }">
								<span class="input_tip">*纬度为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">产品型号:</label>
								<input type="text" name="fenceBean.hardwareVer" id="" class="normaltext" style="width: 500px;" value="${fenceBean.hardwareVer }">
								<span class="input_tip">*产品型号必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">软件版本:</label>
								<input type="text" name="fenceBean.sorfwareVer" id="" class="normaltext" style="width: 500px;" value="${fenceBean.sorfwareVer }">
								<span class="input_tip">*软件版本为必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">生产厂商:</label>
								<%-- <input type="text" name="fenceBean.vendorID" id="" class="normaltext" style="width: 500px;" value="${ }"> --%>
								<select class="normalselect"  style="width: 513px;"  name="fenceBean.vendorID" id="shengchanchangshang">
									<c:forEach items="${manufacturers }" var="mf">
										<option value="${mf.vendorID }"><c:out value="${mf.vendorName }"></c:out></option>
									</c:forEach>
								</select>
								<span class="input_tip">*生产厂商为必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">备注:</label>
								<input type="text" name="fenceBean.remarks" id="" class="normaltext" style="width: 500px;" value="${fenceBean.remarks }">
								<span class="input_tip">*备注信息为可选项，记录对此围栏的特别说明</span>
								<div class="clear"></div>
							</div> 
							<div class="clear"></div>
						</div>
						<div class="normalajaxloading" id="data_loading"></div>
						<div class="left40 top20">
							<input type="button" value="保存" class="btn left20 top5" id="weilaneditbtn"/>
							<a href="<c:url value='/fence/queryFence?${fenceCondition.searchUri}&page.size=10&page.offset=${page.offset }'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>