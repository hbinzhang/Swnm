<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include.inc.jsp"%>
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
<link
	href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/yinpin.js"
	type="text/javascript"></script>
</head>
<body>
	<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
	<div id="main_content_container">
		<div id="nav_place">
			<div id="title">位置：</div>
			<ul>
				<li><a href="<c:url value="/"></c:url>">首页</a></li>
				<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a>
				</li>
				<li><a href="<c:url value="/sounddevEnter"></c:url>">音频设备</a></li>
				<li>编辑${sounddev.name }</li>
			</ul>
		</div>
		<div id="content_container">
			<div class="roleadd_container">
				<%-- <input type="hidden" id="redirecturl" value="/sounddevQuery?${queryCondition.searchUri }&queryCondition.size=10&queryCondition.offset=${queryCondition.offset }"> --%>
				<input type="hidden" id="redirectUrl"
					value="/sounddevQuery?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${queryCondition.offset }">
				<form action="" method="post" class="left50 top30" id="yinpin_form">
					<div>
						<div class="text_form top10">
							<label class="labelname" for="">设备ID:</label> <input type="text"
								name="toUpdateSoundDev.id" value="${sounddev.id }" id=""
								class="normaltext validate[required]" style="width: 500px;"
								readonly="readonly"> <span class="input_tip">*设备ID要符合一定的规范</span>
							<div class="clear"></div>
						</div>
						<div class="text_form top10">
							<label class="labelname" for="">设备名称:</label> <input type="text"
								name="toUpdateSoundDev.name" id=""
								class="normaltext validate[required,maxSize[64]]"
								style="width: 500px;" value="${sounddev.name }"> <span
								class="input_tip">*设备名称为必填项，为当前设备的名称</span>
							<div class="clear"></div>
						</div>
						<div class="text_form top10">
							<label class="labelname" for="">IP地址:</label> <input type="text"
								name="toUpdateSoundDev.ipAddress" id=""
								class="normaltext validate[required,custom[ipv4]]"
								style="width: 500px;" value="${sounddev.ipAddress }"> <span
								class="input_tip">*IP地址为必填项，为设备本身的IP</span>
							<div class="clear"></div>
						</div>
						<div class="text_form top10">
							<label class="labelname" for="">厂商名称:</label> <select id=""
								class="normalselect" style="width: 513px;"
								name="toUpdateSoundDev.vendorName">
								<c:forEach items="${manufacturer }" var="manuFacturer">
									<c:choose>
										<c:when
											test="${sounddev.vendorName eq manuFacturer.vendorName }">
											<option value="${manuFacturer.vendorId }" selected="selected">
												<c:out value="${manuFacturer.vendorName }"></c:out>
											</option>
										</c:when>
										<c:otherwise>
											<option value="${manuFacturer.vendorId }">
												<c:out value="${manuFacturer.vendorName }"></c:out>
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select> <span class="input_tip">*厂商名称为必选项</span>
							<div class="clear"></div>
						</div>
						<c:choose>
							<c:when test="${sounddev.devType eq '音频服务器'}">
								<div class="text_form top10">
									<label class="labelname" for="">设备类型:</label> <select
										id="yinpin_type" class="normalselect" style="width: 513px;"
										name="toUpdateSoundDev.devType">
										<option value="音频服务器" selected="selected">音频服务器</option>
									</select> <span class="input_tip">*设备类型为必选项，其中音频服务器没有所属设备和所属管理处，IO控制器不需要选择所属管理处</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">所属设备:</label> <select
										id="yinpin_shebei_list" class="normalselect"
										style="width: 513px;" disabled="disabled"
										name="toUpdateSoundDev.ownerdev">
									</select> <span class="input_tip">*音频服务器没有所属设备，不需要选择。IO控制器和音频终端为必选项</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">设备管理处:</label> <select
										id="yinpin_guanlichu_list" class="normalselect"
										style="width: 513px;" disabled="disabled"
										name="toUpdateSoundDev.mgtCode">
									</select> <span class="input_tip">*音频服务器没有所属管理处，IO控制器可以根据所属设备确定管理处，音频终端为必选项</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">关联摄像头ID:</label> <select
										name="toUpdateSoundDev.ipcCode" id="yinpin_shexiangtou_id"
										class="normalselect" style="width: 513px;" disabled="disabled">
									</select> <span class="input_tip">*音频终端设备该项为必填项</span>
									<div class="clear"></div>
								</div>
							</c:when>
							<c:when test="${sounddev.devType eq '音频终端' }">
								<div class="text_form top10">
									<label class="labelname" for="">设备类型:</label> <select
										id="yinpin_type" class="normalselect" style="width: 513px;"
										name="toUpdateSoundDev.devType">
										<option value="音频终端" selected="selected">音频终端</option>
									</select> <span class="input_tip">*设备类型为必选项，其中音频服务器没有所属设备和所属管理处，IO控制器不需要选择所属管理处</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">所属设备:</label> <select
										id="yinpin_shebei_list"
										class="normalselect validate[required]" style="width: 513px;"
										name="toUpdateSoundDev.ownerdev">
										<c:forEach items="${soundServer }" var="soundserver">
											<c:choose>
												<c:when
													test="${sounddev.ownerdevid eq soundserver.SERVERID }">
													<option value="${soundserver.SERVERID }"
														selected="selected">
														<c:out value="${soundserver.SERVERNAME}"></c:out>
													</option>
												</c:when>
												<c:otherwise>
													<option value="${soundserver.SERVERID }">
														<c:out value="${soundserver.SERVERNAME}"></c:out>
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select> <span class="input_tip">*音频服务器没有所属设备，不需要选择。IO控制器和音频终端为必选项</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">设备管理处:</label> <select
										id="yinpin_guanlichu_list"
										class="normalselect validate[required]" style="width: 513px;"
										name="toUpdateSoundDev.mgtCode">
										<c:forEach items="${mgts }" var="mgt">
											<c:choose>
												<c:when test="${sounddev.mgtCode eq mgt.id }">
													<option value="${mgt.id }" selected="selected">
														<c:out value="${mgt.name }"></c:out>
													</option>
												</c:when>
												<c:otherwise>
													<option value="${mgt.id }">
														<c:out value="${mgt.name }"></c:out>
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select> <span class="input_tip">*音频服务器没有所属管理处，IO控制器可以根据所属设备确定管理处，音频终端为必选项</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">关联摄像头ID:</label> <select
										name="toUpdateSoundDev.ipcCode" id="yinpin_shexiangtou_id"
										class="normalselect validate[required]" style="width: 513px;">
										<c:forEach items="${ipcids }" var="ipcids">
											<c:choose>
												<c:when test="${sounddev.ipcCode eq ipcids }">
													<option value="${ipcids }" selected="selected">
														<c:out value="${ipcids}"></c:out>
													</option>
												</c:when>
												<c:otherwise>
													<option value="${ipcids }">
														<c:out value="${ipcids}"></c:out>
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select> <span class="input_tip">*音频终端设备该项为必填项</span>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</c:when>
							<c:when test="${sounddev.devType eq 'IO控制器' }">
								<div class="text_form top10">
									<label class="labelname" for="">设备类型:</label> <select
										id="yinpin_type" class="normalselect" style="width: 513px;"
										name="toUpdateSoundDev.devType">
										<option value="IO控制器" selected="selected">IO控制器</option>
									</select> <span class="input_tip">*设备类型为必选项，其中音频服务器没有所属设备和所属管理处，IO控制器不需要选择所属管理处</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">所属设备:</label> <select
										id="yinpin_shebei_list"
										class="normalselect validate[required]" style="width: 513px;"
										name="toUpdateSoundDev.ownerdev">
										<c:forEach items="${audioAdapter }" var="audioadapter">
											<c:choose>
												<c:when
													test="${sounddev.ownerdevid eq audioadapter.audioId }">
													<option value="${audioadapter.audioId }"
														selected="selected">
														<c:out value="${audioadapter.audioName }"></c:out>
													</option>
												</c:when>
												<c:otherwise>
													<option value="${audioadapter.audioId }">
														<c:out value="${audioadapter.audioName }"></c:out>
													</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select> <span class="input_tip">*音频服务器没有所属设备，不需要选择。IO控制器和音频终端为必选项</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">设备管理处:</label> <select
										id="yinpin_guanlichu_list" class="normalselect"
										style="width: 513px;" disabled="disabled"
										name="toUpdateSoundDev.mgtCode">

									</select> <span class="input_tip">*音频服务器没有所属管理处，IO控制器可以根据所属设备确定管理处，音频终端为必选项</span>
									<div class="clear"></div>
								</div>
								<div class="text_form top10">
									<label class="labelname" for="">关联摄像头ID:</label> <select
										name="toUpdateSoundDev.ipcCode" id="yinpin_shexiangtou_id"
										class="normalselect" style="width: 513px;" disabled="disabled">

									</select> <span class="input_tip">*音频终端设备该项为必填项</span>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
						<!-- <div class="text_form top10">
								<label class="labelname" for="">设备类型:</label>
								<select id="yinpin_type" class="normalselect"  style="width: 513px;" name="queryCondition.devType">
									<option value="1">音频服务器</option>
									<option value="2">音频终端</option>
									<option value="3">IO控制器</option>
								</select>
								<span class="input_tip">*设备类型为必选项，其中音频服务器没有所属设备和所属管理处，IO控制器不需要选择所属管理处</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top10">
								<label class="labelname" for="">所属设备:</label>
								<select id="yinpin_shebei_list" class="normalselect"  style="width: 513px;" disabled="disabled" name="soundDev.ownerdev">
								</select>
								<span class="input_tip">*音频服务器没有所属设备，不需要选择。IO控制器和音频终端为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">设备管理处:</label>
								<select id="yinpin_guanlichu_list" class="normalselect"  style="width: 513px;" disabled="disabled" name="soundDev.mgtCode">
								</select>
								<span class="input_tip">*音频服务器没有所属管理处，IO控制器可以根据所属设备确定管理处，音频终端为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">关联摄像头ID:</label>
								<select name="toAddSoundDev.ipcCode" id="yinpin_shexiangtou_id" class="normalselect" style="width: 513px;" disabled="disabled">
								</select>
								<span class="input_tip">*音频终端设备该项为必填项</span>
								<div class="clear"></div>
							</div>
							<div class="clear"></div> -->
					</div>
					<div class="normalajaxloading" id="data_loading"></div>
					<div class="left40 top20">
						<input type="button" value="确定" class="btn left20 top10"
							id="editbtn" /> <a
							href="<c:url value='/sounddevQuery?${queryCondition.searchUri }&queryCondition.size=10&queryCondition.offset=${queryCondition.offset }'></c:url>"
							class="hrefbtn">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
</body>
</html>