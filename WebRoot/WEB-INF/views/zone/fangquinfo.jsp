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
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value="/fence/queryFence"></c:url>">设备管理</a></li>
					<li><a href="<c:url value="/zone/queryZoneByPage"></c:url>">防区管理</a></li>
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
								<div class="right">									<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'修改防区') }">										<a href="javascript:void(0);" class="editlink editfanquinfo">编辑</a>									</c:if>
								</div>
								<div class="clear"></div>
								<table class="detail_table" id="fangqu_info_table">
									<tbody>
										<tr>
											<td class="title_name">防区ID:</td>
											<td class="content content_zhujiid"><c:out value="${zoneBean.zoneID }"></c:out></td>
											<td class="title_name">防区名称:</td>
											<td class="content content_gaojingma"><c:out value="${zoneBean.zoneName }"></c:out></td>
											<td class="title_name">左/右岸:</td>
											<td class="content content_mingcheng"><c:out value="${zoneBean.orientation }"></c:out></td>
										</tr>
										<tr>
											<td class="title_name">分公司:</td>
											<td class="content content_shijian"><c:out value="${zoneBean.branchName }"></c:out></td>
											<td class="title_name">管理处:</td>
											<td class="content content_jibie"><c:out value="${zoneBean.mgtName }"></c:out></td>
											<td class="title_name">起始经度:</td>
											<td class="content content_leixing"><c:out value="${zoneBean.startLon }"></c:out></td>
										</tr>
										<tr>
											<td class="title_name">终止经度:</td>
											<td class="content content_shebeileixing"><c:out value="${zoneBean.endLon }"></c:out></td>
											<td class="title_name">起始纬度:</td>
											<td class="content content_shebeiid"><c:out value="${zoneBean.startLat }"></c:out></td>
											<td class="title_name">终止纬度:</td>
											<td class="content content_zhuangtai"><c:out value="${zoneBean.endLat }"></c:out></td>
										</tr>
										<tr>
											<td class="title_name">附加信息:</td>
											<td colspan="6" class="content content_fujia"><c:out value="${zoneBean.info }"></c:out></td>
										</tr>
									<tbody>
								</table>
							</div>
							<form action="" id="editfangqucontainer">
							<div id="edit_fangqu_container" style="display:none;">
								<div>
									<div class="text_form left">
										<label class="labelname" for="">防区ID:</label>
										<input type="text" name="zoneBean.zoneID" id="fangquid" class="normaltext disabletext" value="${zoneBean.zoneID }" readonly="readonly"/>
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">防区名称:</label>
										<input type="text" name="zoneBean.zoneName" id="" class="normaltext validate[required,maxSize[32]]" value="${zoneBean.zoneName }" />
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">分公司:</label>
										<select id="normal_gongsi_select" class="disableselect" name="zoneBean.branchID">
						    				<option value="${zoneBean.branchID }">${zoneBean.branchName }</option>
						    			</select>
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">管理处:</label>
										<select id="normal_guanlichu_select" class="disableselect" name="zoneBean.mgtID">
						    				<option value="${zoneBean.mgtID }">${zoneBean.mgtName }</option>
						    			</select>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
									<div>
										<div class="text_form left">
											<label class="labelname" for="">起始经度:</label>
											<input type="text" name="zoneBean.startLon" id="startjingdu" class="normaltext validate[required,custom[number]]" value="${zoneBean.startLon }" />
											<div class="clear"></div>
										</div>
										<div class="text_form left">
											<label class="labelname" for="">起始纬度:</label>
											<input type="text" name="zoneBean.startLat" id="startweidu" class="normaltext validate[required,custom[number]]" value="${zoneBean.startLat }" />
											<div class="clear"></div>
										</div>
										<div class="text_form left">
											<label class="labelname" for="">终止经度:</label>
											<input type="text" name="zoneBean.endLon" id="endjingdu" class="normaltext validate[required,custom[number]]" value="${zoneBean.endLon }" />
											<div class="clear"></div>
										</div>
										<div class="text_form left">
											<label class="labelname" for="">终止纬度:</label>
											<input type="text" name="zoneBean.endLat" id="endweidu" class="normaltext validate[required,custom[number]]" value="${zoneBean.endLat }">
											<div class="clear"></div>
										</div>
										<div class="clear"></div>
									</div>
								<div>
									<div class="text_form left">
										<label class="labelname" for="">左/右岸:</label>
										<select class="normalselect" name="zoneBean.orientation">
											<c:choose>
												<c:when test="${zoneBean.orientation eq '左岸' }">
													<option value="左岸" selected="selected">左岸</option>
													<option value="右岸">右岸</option>
												</c:when>
												<c:otherwise>
													<option value="左岸" >左岸</option>
													<option value="右岸" selected="selected">右岸</option>
												</c:otherwise>
											</c:choose>
										</select>
										<div class="clear"></div>
									</div>
									<div class="text_form left">
										<label class="labelname" for="">附加信息:</label>
										<input type="text" name="zoneBean.info" id="" class="normaltext" style="width:710px;" value="${zoneBean.info }">
									</div>
									<div class="clear"></div>
								</div>
								<div class="bottom10 left10" style="">
									<input type="button" value="确定" class="btn" id="fangqueditbtn" style="margin-left:50px;margin-top:20px;">
									<input type="button" value="取消" class="btn" id="cancel_fangqueditbtn" style="margin-left:50px;margin-top:20px;">
								</div>
							</div>
							</form>
						</div>
						<div id="contab02" style="display:none;min-height:350px;">
							<div class="top10 bottom20" style="width:96%;margin-left: 2%;">
						    	<div class="tab-hd">
									<li onclick="set('tab00',1,2)" id="tab001" style="margin-right:2px;cursor: pointer;" class="active">电子围栏</li>
									<li onclick="set('tab00',2,2)" id="tab002" style="margin-right:2px;cursor: pointer;" class="">摄像头</li>
								</div>
								<div class="tab-bd">
									<div id="contab001">										<form action="" id="dianziweilanshexiangtouedit">										<input type="hidden" value="${zoneBean.zoneID}" name="zoneBean.zoneID" />
										<div id="fangqu_dianziweilan_info" style="margin-top:40px;">
											<div class="right">												<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'修改电子围栏映射') }">
													<a href="javascript:void(0);" class="editlink editfangqudianziweilaninfo">编辑</a>												</c:if>
											</div>
											<div class="clear"></div>
											<c:choose>
												<c:when test="${zoneBean.fenceType == 1 }">
													<table class="detail_table">
													<tr>
														<td class="title_name">围栏类型:</td>
														<td class="content content_zhujiid">
															<c:out value="高压脉冲主机"></c:out>
														</td>
														<td class="title_name">主机ID:</td>
														<td class="content content_gaojingma"><c:out value="${pulseZoneMapBean.hostID }"></c:out></td>
														<td class="title_name">子系统列表:</td>
														<td class="content content_mingcheng">
															<c:out value="${pulseZoneMapBean.subZone }"></c:out>
														</td>
													</tr>
												</table>
												</c:when>
												<c:when test="${zoneBean.fenceType == 2 }">
													<table class="detail_table">
													<tr>
														<td class="title_name">围栏类型:</td>
														<td class="content content_zhujiid">
															<c:out value="一体化主机"></c:out>
														</td>
														<td class="title_name">主机ID:</td>
														<td class="content content_gaojingma"><c:out value="${integrationZoneMapBean.hostID }"></c:out></td>
														<td class="title_name">节点列表:</td>
														<td class="content content_mingcheng">
															<c:out value="${integrationZoneMapBean.point }"></c:out>
														</td>
													</tr>
												</table>
												</c:when>
												<c:when test="${zoneBean.fenceType == 3 }">
													<table class="detail_table">
														<tr>
															<td class="title_name">围栏类型:</td>
															<td class="content content_zhujiid">
																<c:out value="防区型光纤主机"></c:out>
															</td>
															<td class="title_name">主机ID:</td>
															<td class="content content_gaojingma"><c:out value="${defenceZoneMapBean.hostID }"></c:out></td>
															<td class="title_name">子防区列表:</td>
															<td class="content content_mingcheng">
																<c:out value="${defenceZoneMapBean.innerZoneID }"></c:out>
															</td>
														</tr>
													</table>
												</c:when>
												<c:when test="${zoneBean.fenceType == 4 }">
													<table class="detail_table">
														<tr>
															<td class="title_name">围栏类型:</td>
															<td class="content content_zhujiid">
																<c:out value="定位型光纤主机"></c:out>
															</td>
															<td class="title_name">主机ID:</td>
															<td class="content content_gaojingma"><c:out value="${positionZoneMapBean.hostID }"></c:out></td>
															<td class="title_name">通道:</td>
															<td class="content content_mingcheng"><c:out value="${positionZoneMapBean.chanID }"></c:out></td>
														</tr>
														<tr>
															<td class="title_name">内部防区ID:</td>
															<td class="content content_shijian"><c:out value="${positionZoneMapBean.innerZoneID }"></c:out></td>
															<td class="title_name">起始点:</td>
															<td class="content content_jibie"><c:out value="${positionZoneMapBean.startPoint }"></c:out></td>
															<td class="title_name">结束点:</td>
															<td class="content content_leixing"><c:out value="${positionZoneMapBean.endPoint }"></c:out></td>
														</tr>
													</table>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</div>
										<div id="fangqu_dianziweilan_edit_container" style="display:none;">
											<c:choose>
												<c:when test="${zoneBean.fenceType == 1 }">
													<div>
														<div class="text_form left">
															<label class="labelname" for="">围栏类型:</label>
															<select class="disableselect" id="" name="zoneBean.fenceType">
																<option value="1" selected="selected" >高压脉冲主机</option>
															</select>
															<div class="clear"></div>
														</div>
														<div class="text_form left">
															<label class="labelname" for="">主机ID:</label>
															<select id="" class="disableselect" name="pulseZoneMapBean.hostID">
											    				<option value="${pulseZoneMapBean.hostID }"><c:out value="${pulseZoneMapBean.hostID }"></c:out></option>
											    			</select>
															<div class="clear"></div>
														</div>
														<div class="clear"></div>
													</div>
													<div class="top10">
														<div id="">
															<label class="labelname">子系统表:</label>
															<div class="left" style="min-height: 275px;width: 720px;background-color: #eef8ff;border: 1px solid #d5dce2;">
																	<c:choose>
																		<c:when test="${pulseZoneMapBean.subZone eq '1' }">
																			<p class="left10 top10">
																				<input type="checkbox" value="1" name="pulseZoneMapBean.subZones" checked="checked"/>子系统1
																			</p>
																			<p class="left10 top10">
																				<input type="checkbox" value="2" name="pulseZoneMapBean.subZones"/>子系统2
																			</p>
																		</c:when>
																		<c:when test="${pulseZoneMapBean.subZone eq '2' }">
																			<p class="left10 top10">
																				<input type="checkbox" value="1" name="pulseZoneMapBean.subZones" />子系统1
																			</p>
																			<p class="left10 top10">
																				<input type="checkbox" value="2" name="pulseZoneMapBean.subZones" checked="checked"/>子系统2
																			</p>
																		</c:when>
																		<c:when test="${pulseZoneMapBean.subZone eq '1,2' }">
																			<p class="left10 top10">
																				<input type="checkbox" value="1" name="pulseZoneMapBean.subZones" checked="checked"/>子系统1
																			</p>
																			<p class="left10 top10">
																				<input type="checkbox" value="2" name="pulseZoneMapBean.subZones" checked="checked"/>子系统2
																			</p>
																		</c:when>
																		<c:otherwise>
																			<p class="left10 top10">
																				<input type="checkbox" value="1" name="pulseZoneMapBean.subZones" />子系统1
																			</p>
																			<p class="left10 top10">
																				<input type="checkbox" value="2" name="pulseZoneMapBean.subZones" />子系统2
																			</p>
																		</c:otherwise>
																	</c:choose>
															</div>
															<div class="clear"></div>
														</div>
														<div class="top10 bottom10 left10" style="">
															<input type="button" value="确定" class="btn" id="fangqudianziweilan_save_btn1" style="margin-left:50px;margin-top:20px;">
															<input type="button" value="取消" class="btn" id="fangqudianziweilan_edit_btn1" style="margin-left:50px;margin-top:20px;">
														</div>
													</div>
												</c:when>
												<c:when test="${zoneBean.fenceType == 2 }">
													<div>
														<div class="text_form left">
															<label class="labelname" for="">围栏类型:</label>
															<select class="disableselect" id="" name="zoneBean.fenceType">
																<option value="2" selected="selected">一体化探测</option>
															</select>
															<div class="clear"></div>
														</div>
														<div class="text_form left">
															<label class="labelname" for="">主机ID:</label>
															<select id="" class="disableselect" name="integrationZoneMapBean.hostID">
											    				<option value="${integrationZoneMapBean.hostID }">${integrationZoneMapBean.hostID }</option>
											    			</select>
															<div class="clear"></div>
														</div>
														<div class="clear"></div>
													</div>
													<div class="top10">
														<div id="">
															<label class="labelname">节点列表:</label>
															<textarea rows="10" cols="10" class="normaltext" style="height: 275px;width: 715px;" name="integrationZoneMapBean.point"><c:out value="${integrationZoneMapBean.point }"></c:out></textarea>
															<div class="clear"></div>
														</div>
														<div class="top10 bottom10 left10" style="">
															<input type="button" value="确定" class="btn" id="fangqudianziweilan_save_btn2" style="margin-left:50px;margin-top:20px;">
															<input type="button" value="取消" class="btn" id="fangqudianziweilan_edit_btn2" style="margin-left:50px;margin-top:20px;">
														</div>
													</div>
												</c:when>
												<c:when test="${zoneBean.fenceType == 3 }">
													<div>
														<div class="text_form left">
															<label class="labelname" for="">围栏类型:</label>
															<select class="disableselect" id="" name="zoneBean.fenceType">
																<option value="3">防区型振动光纤</option>
															</select>
															<div class="clear"></div>
														</div>
														<div class="text_form left">
															<label class="labelname" for="">主机ID:</label>
															<select id="" class="disableselect" name="defenceZoneMapBean.hostID">
											    				<option value="${defenceZoneMapBean.hostID }">${defenceZoneMapBean.hostID }</option>
											    			</select>
															<div class="clear"></div>
														</div>
														<div class="clear"></div>
													</div>
													<div class="top10">
														<div id="" style="">
															<label class="labelname">子防区列表:</label>
															<textarea rows="10" cols="10" class="normaltext" style="height: 275px;width: 715px;" name="defenceZoneMapBean.innerZoneID"><c:out value="${defenceZoneMapBean.innerZoneID }"></c:out></textarea>
															<div class="clear"></div>
														</div>
														<div class="top10 bottom10 left10" style="">
															<input type="button" value="确定" class="btn" id="fangqudianziweilan_save_btn3" style="margin-left:50px;margin-top:20px;">
															<input type="button" value="取消" class="btn" id="fangqudianziweilan_edit_btn3" style="margin-left:50px;margin-top:20px;">
														</div>
													</div>
												</c:when>
												<c:when test="${zoneBean.fenceType == 4 }">
													<div>
														<div class="text_form left">
															<label class="labelname" for="">围栏类型:</label>
															<select class="disableselect" id="" name="zoneBean.fenceType">
																<option value="4">定位型振动光纤</option>
															</select>
															<div class="clear"></div>
														</div>
														<div class="text_form left">
															<label class="labelname" for="">主机ID:</label>
															<select id="" class="disableselect" name="positionZoneMapBean.hostID">
											    				<option value="${positionZoneMapBean.hostID }">${positionZoneMapBean.hostID }</option>
											    			</select>
															<div class="clear"></div>
														</div>
														<div class="clear"></div>
													</div>
													<div class="top10">
														<div id="" style="">
															<div class="left">
																<div>
																	<div class="text_form left">
																		<label class="labelname" for="">通道ID:</label>
																		<select id="" class="normalselect" name="positionZoneMapBean.chanID">
																			<c:choose>
																				<c:when test="${positionZoneMapBean.chanID ==1 }">
																				<option value="1" selected="selected">1</option>
														    					<option value="2">2</option>
														    					</c:when>
														    					<c:when test="${positionZoneMapBean.chanID ==2 }">
																				<option value="1">1</option>
														    					<option value="2" selected="selected">2</option>
														    					</c:when>
														    					<c:otherwise></c:otherwise>
																			</c:choose>
														    			</select>
																		<div class="clear"></div>
																	</div>
																	<div class="text_form left">
																		<label class="labelname" for="">内部防区ID:</label>
																		<input type="text" id="" class="normaltext" name="positionZoneMapBean.innerZoneID" value="${positionZoneMapBean.innerZoneID }" />
																		<div class="clear"></div>
																	</div>
																	<div class="clear"></div>
																</div>
																<div>
																	<div class="text_form left">
																		<label class="labelname" for="">起始点:</label>
																		<input type="text"  id="" class="normaltext" name="positionZoneMapBean.startPoint" value="${positionZoneMapBean.startPoint }" />
																		<div class="clear"></div>
																	</div>
																	<div class="text_form left">
																		<label class="labelname" for="">结束点:</label>
																		<input type="text"  id="" class="normaltext" name="positionZoneMapBean.endPoint" value="${positionZoneMapBean.endPoint }" />													
																		<div class="clear"></div>
																	</div>
																	<div class="clear"></div>
																</div>
															</div>
															<div class="clear"></div>
														</div>
														<div class="top10 bottom10 left10" style="">
															<input type="button" value="确定" class="btn" id="fangqudianziweilan_save_btn4" style="margin-left:50px;margin-top:20px;">
															<input type="button" value="取消" class="btn" id="fangqudianziweilan_edit_btn4" style="margin-left:50px;margin-top:20px;">
														</div>
													</div>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</div>										</form>
									</div>
									<div style="display:none;min-height:350px;" id="contab002">								
										<div>											<form action="" id="addIPCForm">											<input type="hidden" value="${zoneBean.zoneID}" name="zoneBean.zoneID" />
											<div class="text_form left">
												<label class="labelname" for="">设备ID:</label>
												<select id="yinpinshebei_shebeiid" class="normalselect" style="width:250px;" name="ipcZoneMap.ipcId">
													<c:forEach items="${ipcIDs}" var="ipc">
														<option value="${ipc}">${ipc}</option>
													</c:forEach>
								    			</select>
												<div class="clear"></div>
											</div>
											<div class="text_form left">
												<label class="labelname" for="">预置位:</label>
												<input type="text" name="ipcZoneMap.point" id="yinpinshebei_syuzhiwei" class="normaltext validate[required,custom[integer],maxSize[5],ajax[checkPreSetUnique]]" style="width:250px;">
												<div class="clear"></div>
											</div>
											<div class="text_form left">
												<label class="labelname" for="">主/辅:</label>
												<select class="normalselect validate[funcCall[checkMainIpcUnique]]" id="yinpinshebei_zhufu" style="width:250px;" name="ipcZoneMap.mainIpc">
													<option value="1">主设备</option>
													<option value="0">辅设备</option>
												</select>
												<div class="clear"></div>
											</div>
											<div class="text_form left">
												<label class="labelname" for="">附加信息:</label>
												<input type="text" name="ipcZoneMap.info" id="yinpinshebei_fujia" class="normaltext" style="width:250px;">													
												<div class="clear"></div>
											</div>
											<div class="bottom10 left10" style="">											<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'增加摄像头映射') }">												<input type="button" value="添加摄像头" class="btn" id="shexiangtouupdateadd" style="margin-left:50px;margin-top:20px;">											</c:if>
											</div>
											<div class="clear"></div>											</form>
										</div>
										<input type="hidden" value="${zoneBean.zoneID }" id="fangqushexiangtoubianjiid">
										<table class="tableui" id="shexiangtou_table" style="width:87%;margin-left:50px;">
											<thead>
												<tr>
													<%--<th width="2%">
										    			<input type="checkbox" id="shexiangtou_select_all"/>
										    		</th>  
													--%><th>设备ID</th>
													<th>预置位</th>
													<th>主/辅</th>
													<th>附加信息</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${ipcZoneMaps }" var="icp">
													<tr>
														<%--<td align="center">
															<input type="checkbox" value="${icp.id}" name="deleteids" id="deleteids"/>
														</td>
														--%><td>${icp.ipcId }</td>
														<td>${icp.point }</td>
														<td>														<c:choose>														<c:when test="${icp.mainIpc eq '0' }">															辅设备														</c:when>														<c:otherwise>															主设备														</c:otherwise>														</c:choose>														</td>														<td>${icp.info }</td>														<td>															<c:if test="${poteviofn:clContains(sessionScope.session.authorizatedOps,'删除摄像头映射') }">																<a href="javascript:void(0);" durl="<c:url value='/zone/delIpcMapInfo?ipcZoneMap.ipcId=${icp.ipcId }&ipcZoneMap.zoneID=${zoneBean.zoneID }'></c:url>" onclick="deleteIPC(this);">删除</a>															</c:if>														</td>													</tr>												</c:forEach>
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