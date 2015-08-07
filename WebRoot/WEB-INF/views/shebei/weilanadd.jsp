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
					<li><a href="<c:url value=""></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/views/shebei/weilan.jsp"></c:url>">电子围栏</a></li>
					<li>添加</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=weilan" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<form action="" method="post" class="left50 top30" id="weilan_form">
						<div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">主机ID:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*主机ID要符合一定的规范</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">围栏类型:</label>
								<select id="" class="normalselect"  style="width: 513px;">
									<option value="1">高压脉冲主机</option>
									<option value="2">一体化探测</option>
									<option value="3">定位型振动光纤</option>
									<option value="4">防区型振动光纤</option>
								</select>
								<span class="input_tip">*围栏类型为必选项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">围栏名称:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*围栏名称为必填项</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">网络地址:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*网络地址为必填项,格式例如192.168.1.1</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">端口号:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*端口号为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">安装时间:</label>
								<input type="text" name="fromdate" class="normaltext" id="fromdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm' })"  style="width: 480px;"/>
								<a href="javascript:void(0)" class="underlinenone">
									<img onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',el:'fromdate' })" src="<c:url value="/resources/images/datePicker.gif" />" width="16" height="22" align="absmiddle" />
								</a>
								<span class="input_tip">*安装时间不能为空，格式为年-月-日</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">分公司:</label>
								<select id="yinpin_shebei_list" class="normalselect"  style="width: 513px;">
									<option value="1">北京分公司</option>
									<option value="2">河北分公司</option>
								</select>
								<span class="input_tip">*分公司为必填项，选择分公司之后，对应的管理处会发生变化</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">管理处:</label>
								<select id="yinpin_shebei_list" class="normalselect"  style="width: 513px;">
									<option value="1">大兴管理处</option>
									<option value="2">海淀管理处</option>
								</select>
								<span class="input_tip">*管理处为必填项，此处的管理处是上面分公司所属的管理处</span>
								<div class="clear"></div>
							</div>
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">经度:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*经度为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">纬度:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*纬度为必填项，必须为数字</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">产品型号:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*产品型号必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">软件版本:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*软件版本为必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">生产厂商:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*生产厂商为必填项</span>
								<div class="clear"></div>
							</div> 
							<div class="text_form top5" style="margin-top:10px;">
								<label class="labelname" for="">备注:</label>
								<input type="text" name="" id="" class="normaltext" style="width: 500px;">
								<span class="input_tip">*备注信息为可选项，记录对此围栏的特别说明</span>
								<div class="clear"></div>
							</div> 
							<div class="clear"></div>
						</div>
						<div class="left40 top20">
							<input type="submit" value="添加" class="btn left20 top5"/>
							<a href="<c:url value='/views/shebei/weilan.jsp'></c:url>" class="hrefbtn">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>