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
					<li>详细信息(防区名称)</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=fangqu" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<div>
						<div class="text_form left">
							<label class="labelname" for="">防区名称:</label>
							<input type="text" value="防区名称" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="text_form left">
							<label class="labelname" for="">朝向:</label>
							<input type="text" value="朝南" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="text_form left">
							<label class="labelname" for="">分公司:</label>
							<input type="text" value="北京分公司" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="text_form left">
							<label class="labelname" for="">管理处:</label>
							<input type="text" value="大兴管理处" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
					<div>
						<div class="text_form left">
							<label class="labelname" for="">起始经度:</label>
							<input type="text" value="55" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="text_form left">
							<label class="labelname" for="">起始纬度:</label>
							<input type="text" value="35" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="text_form left">
							<label class="labelname" for="">终止经度:</label>
							<input type="text" value="76" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="text_form left">
							<label class="labelname" for="">终止纬度:</label>
							<input type="text" value="36" id="" class="normaltext" readonly="readonly" disabled="disabled">
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="text_form">
						<label class="labelname" for="">附加信息:</label>
						<input type="text" value="附加信息" id="" class="normaltext" style="width:710px;" readonly="readonly" disabled="disabled">
						<div class="clear"></div>
					</div>
					<div class="top10 bottom20" style="width:96%;margin-left: 2%;">
				    	<div class="tab-hd">
							<li onclick="set('tab0',1,3)" id="tab01" style="margin-right:2px;cursor: pointer;" class="active">电子围栏</li>
							<li onclick="set('tab0',2,3)" id="tab02" style="margin-right:2px;cursor: pointer;" class="">摄像头</li>
							<li onclick="set('tab0',3,3)" id="tab03" style="margin-right:2px;cursor: pointer;" class="">音频</li>
						</div>
						<div class="tab-bd">
							<div id="contab01">
								<div>
									<div class="text_form left">
										<label class="labelname" for="">主机ID:</label>
										<input type="text" value="主机1" id="" class="normaltext" readonly="readonly" disabled="disabled">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">围栏类型:</label>
										<input type="text" value="一体化探测" id="" class="normaltext" readonly="readonly" disabled="disabled">
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div id="yitihua">
										<label class="labelname">节点列表:</label>
										<textarea rows="10" cols="10" class="normaltext" style="height: 275px;width: 715px;">子系统1，子系统2，子系统3</textarea>
										<div class="clear"></div>
									</div>
									<div id="maichong" style="display: none;">
										<label class="labelname">子系统表:</label>
										<div class="left" style="min-height: 275px;width: 720px;background-color: #eef8ff;border: 1px solid #d5dce2;">
											<p class="left10 top10">
												<input type="checkbox" value=""/>子系统1
											</p>
											<p class="left10 top10">
												<input type="checkbox" value=""/>子系统2
											</p>
										</div>
										<div class="clear"></div>
									</div>
									<div id="dingweixing" style="display: none;">
										<label class="labelname">子防区列表:</label>
										<textarea rows="10" cols="10" class="normaltext" style="height: 275px;width: 715px;"></textarea>
										<div class="clear"></div>
									</div>
									<div id="tancexing" style="display: none;">
										<div class="left">
											<div>
												<div class="text_form left">
													<label class="labelname" for="">通道ID:</label>
													<input type="text" name="" id="" class="normaltext">
													<div class="clear"></div>
												</div>
												<div class="text_form left">
													<label class="labelname" for="">内部防区ID:</label>
													<input type="text" name="" id="" class="normaltext">
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</div>
											<div>
												<div class="text_form left">
													<label class="labelname" for="">起始点:</label>
													<input type="text" name="" id="" class="normaltext">
													<div class="clear"></div>
												</div>
												<div class="text_form left">
													<label class="labelname" for="">结束点:</label>
													<input type="text" name="" id="" class="normaltext">														
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</div>
										</div>
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<div style="display:none;min-height:350px;" id="contab02">
								<div>
									<div class="text_form left">
										<label class="labelname" for="">设备ID:</label>
										<input type="text" value="234" id="" class="normaltext" readonly="readonly" disabled="disabled">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">预置位:</label>
										<input type="text" value="预置位" id="" class="normaltext" readonly="readonly" disabled="disabled">
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div>
									<div class="text_form left">
										<label class="labelname" for="">主/辅:</label>
										<input type="text" value="主" id="" class="normaltext" readonly="readonly" disabled="disabled">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">附加信息:</label>
										<input type="text" value="附加信息" id="" class="normaltext" readonly="readonly" disabled="disabled">											
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
							<div style="min-height:350px; display:none;" id="contab03">
								<div>
									<div class="text_form left">
										<label class="labelname" for="">设备ID:</label>
										<input type="text" value="234" id="" class="normaltext" readonly="readonly" disabled="disabled">													
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">主/辅:</label>
										<input type="text" value="主" id="" class="normaltext" readonly="readonly" disabled="disabled">
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
			    	</div>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>