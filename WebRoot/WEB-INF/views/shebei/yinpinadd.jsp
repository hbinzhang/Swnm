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
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/yinpin.js" type="text/javascript"></script>
	</head>
	<body>
		<c:import url="../layout/header.jsp" charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/sounddevEnter"></c:url>">音频设备</a></li>
					<li>添加</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=yinpin" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<input type="hidden" id="redirecturl" value="/sounddevQuery?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${queryCondition.offset }">
					<form action="" method="post" class="left50 top30" id="yinpinadd_form">
						<div>
							<div class="text_form top10">
								<label class="labelname" for="">设备ID:</label>
								<input type="text" name="toAddSoundDev.id" id="shebeiId" class="normaltext validate[required]" maxlength="11" style="width: 500px;">
								<span class="" id="shebeiidtip" style="coLor:red;">设备ID已存在！</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">设备名称:</label>
								<input type="text" name="toAddSoundDev.name" id="" class="normaltext validate[required,maxSize[64]]" style="width: 500px;">
								<span class="input_tip">*设备名称为必填项，为当前设备的名称</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">IP地址:</label>
								<input type="text" name="toAddSoundDev.ipAddress" id="" class="normaltext validate[required,custom[ipv4]]" style="width: 500px;">
								<span class="input_tip">*IP地址为必填项，为设备本身的IP</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">厂商名称:</label>
								<select id="" class="normalselect"  style="width: 513px;" name="toAddSoundDev.vendorName">
									<c:forEach items="${manufacturer }" var="factory">
										<option value="${factory.vendorId}"><c:out value="${factory.vendorName }"></c:out></option>
									</c:forEach>
								</select>
								<span class="input_tip">*厂商名称为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">设备类型:</label>
								<select id="yinpin_type" class="normalselect validate[required]"  style="width: 513px;" name="toAddSoundDev.devType">
									<option value="音频服务器">音频服务器</option>
									<option value="音频终端">音频终端</option>
									<option value="IO控制器">IO控制器</option>
								</select>
								<span class="input_tip">*设备类型为必选项，其中音频服务器没有所属设备和所属管理处，IO控制器不需要选择所属管理处</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top10">
								<label class="labelname" for="">所属设备:</label>
								<select id="yinpin_shebei_list" class="normalselect validate[required]"  style="width: 513px;" disabled="disabled" name="toAddSoundDev.ownerdev">
								</select>
								<span class="input_tip">*音频服务器没有所属设备，不需要选择。IO控制器和音频终端为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">设备管理处:</label>
								<select id="yinpin_guanlichu_list" class="normalselect validate[required]"  style="width: 513px;" disabled="disabled" name="toAddSoundDev.mgtCode">
								</select>
								<span class="input_tip">*音频服务器没有所属管理处，IO控制器可以根据所属设备确定管理处，音频终端为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top10">
								<label class="labelname" for="">关联摄像头ID:</label>
								<select name="toAddSoundDev.ipcCode" id="yinpin_shexiangtou_id" class="normalselect validate[required]" style="width: 513px;" disabled="disabled">
								</select>
								<span class="input_tip">*音频终端设备该项为必填项</span>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
						</div>
						<div class="normalajaxloading" id="data_loading"></div>
						<div class="left40 top20">
							<input type="button" value="添加" class="btn left20 top10" id="addbtn"/>
							<a href="<c:url value='/sounddevQuery?${queryCondition.searchUri}&queryCondition.size=10&queryCondition.offset=${queryCondition.offset }'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>