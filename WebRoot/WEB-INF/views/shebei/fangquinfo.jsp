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
					<div class="tab-hd">
						<li onclick="set('tab0',1,2)" id="tab01" style="margin-right:2px;cursor: pointer;" class="active">基本信息</li>
						<li onclick="set('tab0',2,2)" id="tab02" style="margin-right:2px;cursor: pointer;" class="">设备列表</li>
					</div>
					<div class="tab-bd">
						<div id="contab01">
							<div style="width:90%;margin-left:5%;margin-top:30px;margin-bottom:20px;" id="fangqu_info_table">
								<div class="right">
									<a href="javascript:void(0);" class="editlink editfanquinfo">编辑</a>
								</div>
								<div class="clear"></div>
								<table class="detail_table">
									<tbody>
										<tr>
											<td class="title_name">防区ID:</td>
											<td class="content content_zhujiid">1110</td>
											<td class="title_name">防区名称:</td>
											<td class="content content_gaojingma">防区1</td>
											<td class="title_name">左/右岸:</td>
											<td class="content content_mingcheng">左</td>
										</tr>
										<tr>
											<td class="title_name">分公司:</td>
											<td class="content content_shijian">北京分公司</td>
											<td class="title_name">管理处:</td>
											<td class="content content_jibie">海淀管理处</td>
											<td class="title_name">起始经度:</td>
											<td class="content content_leixing">43</td>
										</tr>
										<tr>
											<td class="title_name">终止经度:</td>
											<td class="content content_shebeileixing">55</td>
											<td class="title_name">起始纬度:</td>
											<td class="content content_shebeiid">34</td>
											<td class="title_name">终止纬度:</td>
											<td class="content content_zhuangtai">60</td>
										</tr>
										<tr>
											<td class="title_name">附加信息:</td>
											<td colspan="6" class="content content_fujia">备注信息</td>
										</tr>
									<tbody>
								</table>
							</div>
							<div id="edit_fangqu_container" style="display:none;">
								<div>
									<div class="text_form left">
										<label class="labelname" for="">防区ID:</label>
										<input type="text" name="" id="" class="normaltext">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">防区名称:</label>
										<input type="text" name="" id="" class="normaltext">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">分公司:</label>
										<select id="normal_gongsi_select" class="normalselect">
						    				<option value="1">北京分公司</option>
						    				<option value="2">河北分公司</option>
						    			</select>
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">管理处:</label>
										<select id="normal_guanlichu_select" class="normalselect">
						    				<option value="1">海淀管理处</option>
						    				<option value="1">大兴管理处</option>
						    			</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div>
									<div class="text_form left">
										<label class="labelname" for="">起始经度:</label>
										<input type="text" name="" id="" class="normaltext">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">起始纬度:</label>
										<input type="text" name="" id="" class="normaltext">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">终止经度:</label>
										<input type="text" name="" id="" class="normaltext">
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">终止纬度:</label>
										<input type="text" name="" id="" class="normaltext">
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div>
									<div class="text_form left">
										<label class="labelname" for="">朝向:</label>
										<select class="normalselect">
											<option>左岸</option>
											<option>右岸</option>
										</select>
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">附加信息:</label>
										<input type="text" name="" id="" class="normaltext" style="width:710px;">
									</div>
									<div class="clear"></div>
								</div>
								<div class="bottom10 left10" style="">
									<input type="button" value="保存" class="btn" id="fangqueditbtn" style="margin-left:50px;margin-top:20px;">
									<input type="button" value="取消" class="btn" id="cancel_fangqueditbtn" style="margin-left:50px;margin-top:20px;">
								</div>
							</div>
						</div>
						<div id="contab02" style="display:none;min-height:350px;">
							<div class="top10 bottom20" style="width:96%;margin-left: 2%;">
						    	<div class="tab-hd">
									<li onclick="set('tab00',1,2)" id="tab001" style="margin-right:2px;cursor: pointer;" class="active">电子围栏</li>
									<li onclick="set('tab00',2,2)" id="tab002" style="margin-right:2px;cursor: pointer;" class="">摄像头</li>
								</div>
								<div class="tab-bd">
									<div id="contab001">
										<div id="fangqu_dianziweilan_info" style="margin-top:40px;">
											<div class="right">
												<a href="javascript:void(0);" class="editlink editfangqudianziweilaninfo">编辑</a>
											</div>
											<div class="clear"></div>
											<table class="detail_table">
												<tbody>
													<tr>
														<td class="title_name">围栏类型:</td>
														<td class="content content_zhujiid">定位型震动光纤</td>
														<td class="title_name">主机ID:</td>
														<td class="content content_gaojingma">001</td>
														<td class="title_name">通道:</td>
														<td class="content content_mingcheng">2</td>
													</tr>
													<tr>
														<td class="title_name">内部防区ID:</td>
														<td class="content content_shijian">003</td>
														<td class="title_name">起始点:</td>
														<td class="content content_jibie">1</td>
														<td class="title_name">结束点:</td>
														<td class="content content_leixing">5</td>
													</tr>
												<tbody>
											</table>
										</div>
										<div id="fangqu_dianziweilan_edit_container" style="display:none;">
											<div>
												<div class="text_form left">
													<label class="labelname" for="">围栏类型:</label>
													<select class="normalselect" id="fangqu_weilanleixing">
														<option value="1">高压脉冲主机</option>
														<option value="2" selected="selected">一体化探测</option>
														<option value="3">防区型振动光纤</option>
														<option value="4">定位型振动光纤</option>
													</select>
													<div class="clear"></div>
												</div>
												<div class="text_form left">
													<label class="labelname" for="">主机ID:</label>
													<select id="" class="normalselect">
									    				<option value="1">主机1</option>
									    				<option value="1">主机2</option>
									    			</select>
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</div>
											<div class="top10">
												<div id="yitihua">
													<label class="labelname">节点列表:</label>
													<textarea rows="10" cols="10" class="normaltext" style="height: 275px;width: 715px;"></textarea>
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
																<select id="" class="normalselect">
												    				<option value="1">1</option>
												    				<option value="1">2</option>
												    			</select>
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
												<div class="top10 bottom10 left10" style="">
													<input type="button" value="保存" class="btn" id="fangqudianziweilan_save_btn" style="margin-left:50px;margin-top:20px;">
													<input type="button" value="取消" class="btn" id="fangqudianziweilan_edit_btn" style="margin-left:50px;margin-top:20px;">
												</div>
											</div>
										</div>
									</div>
									<div style="display:none;min-height:350px;" id="contab002">
										<div>
											<div class="text_form left">
												<label class="labelname" for="">设备ID:</label>
												<select id="yinpinshebei_shebeiid" class="normalselect" style="width:250px;">
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
												<tr>
													<td>001</td>
													<td>3</td>
													<td>主</td>
													<td>附加信息</td>
												</tr>
											</tbody>
										</table>
									</div>
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