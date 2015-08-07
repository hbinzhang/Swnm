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
		<script src="${pageContext.request.contextPath}/resources/js/fangqu.js" type="text/javascript"></script>
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
					<li>添加</li>
				</ul>
			</div>
			<div id="content_container">
				<c:import url="../layout/shebeitab.jsp?sTabLink=fangqu" charEncoding="UTF-8"></c:import>
				<div class="roleadd_container">
					<div style="margin-left:30%;margin-top:20px;">
						<div>
							<div class="left step stepbefore">
								添加基本信息
							</div>
							<div class="next left">
							</div>
							<div class="left step stepcurrent">
								添加设备
							</div>
							<div class="clear"></div>
						</div>
					</div>	
					<form action="" method="post">
						<div class="top10 bottom20" style="width:96%;margin-left: 2%;">
					    	<div class="tab-hd">
								<li onclick="set('tab0',1,2)" id="tab01" style="margin-right:2px;cursor: pointer;" class="active">电子围栏</li>
								<li onclick="set('tab0',2,2)" id="tab02" style="margin-right:2px;cursor: pointer;" class="">摄像头</li>
							</div>
							<div class="tab-bd">
								<div id="contab01">
									<div>
										<div class="text_form left">
											<label class="labelname" for="">围栏类型:</label>
											<select class="normalselect" id="fangqu_weilanleixing" name="zoneBean.fenceType">
												<option value="1">高压脉冲主机</option>
												<option value="2" selected="selected">一体化探测</option>
												<option value="3">防区型振动光纤</option>
												<option value="4">定位型振动光纤</option>
											</select>
											<div class="clear"></div>
										</div>
											<label class="labelname" for="" id="hostid">主机ID:</label>
											<select id="hostidselect" class="normalselect" name="zoneBean.hostID">
							    			</select>
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
									<div class="top10">
										<div id="yitihua">
											<label class="labelname">节点列表:</label>
											<textarea rows="10" cols="10" class="normaltext" style="height: 275px;width: 715px;" name="integrationZoneMapBean.point"></textarea>
											<div class="clear"></div>
										</div>
										<div id="maichong" style="display: none;">
											<label class="labelname">子系统表:</label>
											<div class="left" style="min-height: 275px;width: 720px;background-color: #eef8ff;border: 1px solid #d5dce2;">
												<p class="left10 top10">
													<input type="checkbox" value="" name="pulseZoneMapBean.subZone"/>子系统1
												</p>
												<p class="left10 top10">
													<input type="checkbox" value="" name="pulseZoneMapBean.subZone"/>子系统2
												</p>
											</div>
											<div class="clear"></div>
										</div>
										<div id="dingweixing" style="display: none;">
											<label class="labelname">子防区列表:</label>
											<textarea rows="10" cols="10" class="normaltext" style="height: 275px;width: 715px;" name="defenceZoneMapBean.innerZoneID"></textarea>
											<div class="clear"></div>
										</div>
										<div id="tancexing" style="display: none;">
											<div class="left">
												<div>
													<div class="text_form left">
														<label class="labelname" for="">通道ID:</label>
														<select id="" class="normalselect" name="positionZoneMapBean.chanID">
										    				<option value="1">1</option>
										    				<option value="1">2</option>
										    			</select>
														<div class="clear"></div>
													</div>
													<div class="text_form left">
														<label class="labelname" for="">内部防区ID:</label>
														<input type="text" name="" id="" class="normaltext" name="positionZoneMapBean.innerZoneID">
														<div class="clear"></div>
													</div>
													<div class="clear"></div>
												</div>
												<div>
													<div class="text_form left">
														<label class="labelname" for="">起始点:</label>
														<input type="text" name="" id="" class="normaltext" name="positionZoneMapBean.startPoint">
														<div class="clear"></div>
													</div>
													<div class="text_form left">
														<label class="labelname" for="">结束点:</label>
														<input type="text" name="" id="" class="normaltext" name="positionZoneMapBean.endPoint">														
														<div class="clear"></div>
													</div>
													<div class="clear"></div>
												</div>
											</div>
											<div class="clear"></div>
										</div>
										<div class="top10 bottom10 left10" style="">
											<input type="submit" value="添加电子围栏" class="btn" style="margin-left:50px;">
										</div>
									</div>
								</div>
								<div style="display:none;min-height:350px;" id="contab02">
									<div>
										<div class="text_form left">
											<label class="labelname" for="">设备ID:</label>
											<select id="yinpinshebei_shebeiid" class="normalselect" style="width:250px;" name="">
							    				<option value="1">设备1</option>
							    				<option value="1">设备2</option>
							    			</select>
											<div class="clear"></div>
										</div>
										<div class="text_form left">
											<label class="labelname" for="">预置位:</label>
											<select id="yinpinshebei_syuzhiwei" class="normalselect" style="width:250px;">
							    				<option value="1">预置位1</option>
							    				<option value="1">预置位2</option>
							    			</select>
											<div class="clear"></div>
										</div>
										<div class="text_form left">
											<label class="labelname" for="">主/辅:</label>
											<select class="normalselect" id="yinpinshebei_zhufu" style="width:250px;">
												<option value="1">主设备</option>
												<option value="2" selected="selected">辅设备</option>
											</select>
											<div class="clear"></div>
										</div>
										<div class="text_form left">
											<label class="labelname" for="">附加信息:</label>
											<input type="text" name="" id="yinpinshebei_fujia" class="normaltext" style="width:250px;">													
											<div class="clear"></div>
										</div>
										<div class="bottom10 left10" style="">
											<input type="button" value="添加摄像头" class="btn" id="shexiangtouadd" style="margin-left:50px;margin-top:20px;">
										</div>
										<div class="clear"></div>
									</div>
									<table class="tableui" id="shexiangtou_table" style="width:87%;margin-left:50px;">
										<thead>
											<tr>
												<th>设备ID</th>
												<th>预置位</th>
												<th>主/辅</th>
												<th>附加信息</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
							</div>
				    	</div>
					</form>
				</div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>