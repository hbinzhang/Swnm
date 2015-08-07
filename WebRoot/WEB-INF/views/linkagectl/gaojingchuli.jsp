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
		<link href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/popModal.min.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/resources/css/validationEngine.jquery.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/videomonitor.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/hanhua.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/gaojingchuli.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				//页面加载时报警列表获取焦点
				$("#gaojingchuliTable")[0].focus();
				
				$("#slider-range").slider({
			      range: true,
			      min: -60,
			      max: 60,
			      values: [ -10, 10 ],
			      slide: function( event, ui ) {
			        $("#amount").val("开始(分)：" + ui.values[ 0 ] + " - 结束(分)：" + ui.values[ 1 ]);
			      }
			    });
			    $("#slider-range-left").slider({
			      range: true,
			      min: -60,
			      max: 60,
			      values: [ -10, 10 ],
			      slide: function( event, ui ) {
			        $("#amount").val("开始(分)：" + ui.values[ 0 ] + " - 结束(分)：" + ui.values[ 1 ]);
			      }
			    });
			    $("#slider-range-right").slider({
			      range: true,
			      min: -60,
			      max: 60,
			      values: [ -10, 10 ],
			      slide: function( event, ui ) {
			        $("#amount").val("开始(分)：" + ui.values[ 0 ] + " - 结束(分)：" + ui.values[ 1 ]);
			      }
			    });
			    $("#amount").val("开始(分)：" + $("#slider-range").slider("values", 0) + " - 结束(分)：" + $( "#slider-range" ).slider("values", 1));
			    //$('#vidioURL').val();
			    $("#slider-range").hover(function(){
			    	$("#amount").val("开始(分)：" + $("#slider-range").slider("values", 0) + " - 结束(分)：" + $( "#slider-range" ).slider("values", 1));
			    });
			    $("#slider-range-left").hover(function(){
			    	$("#amount").val("开始(分)：" + $("#slider-range-left").slider("values", 0) + " - 结束(分)：" + $( "#slider-range-left" ).slider("values", 1));
			    });
			    $("#slider-range-right").hover(function(){
			    	$("#amount").val("开始(分)：" + $("#slider-range-right").slider("values", 0) + " - 结束(分)：" + $( "#slider-range-right" ).slider("values", 1));
			    });
			    
			    $("#slider-range").mouseup(function(){
			    	updateV1($('#vidioURL').val());
			    });
			    
			    $("#slider-range-left").mouseup(function(){
			    	updateV2($('#leftVidioURL').val());
			    });
			    
			    $("#slider-range-right").mouseup(function(){
			    	updateV3($('#rightVidioURL').val());
			    });
			});
			
			function updateV1(playdata){
				var data = eval('('+playdata+')');
				var begin = parseInt($("#slider-range").slider("values", 0));
				var end = parseInt($("#slider-range").slider("values", 1));
				var sdt =  $('#iplaybackjson').val().replace(/-/g,"/");
				var dt =  Date.parse(sdt) / 1000;
				//var bt = new Date(beginTime);
				var url = '{' +
                '"PlayType": 2,' +
                '"channelIp": "'+data.channelIp+'",' +
                '"cmdPort": '+data.cmdPort+',' +
                '"dataPort": '+data.dataPort+',' +
                '"devIp": "'+data.devIp+'",' +
                '"playerType": '+data.playerType+',' +
                '"psw": "'+data.psw+'",' +
                '"record": {' +
                    '"beginTime": '+(dt + begin * 60)+',' +
                    '"byteRate": 10485760,' +
                    '"channel": 1,' +
                    '"idxType": 3,' +
                    '"ip": 0,' +
                    '"peroid": '+((end - begin) * 60)+',' +
                    '"protocol": 1' +
                '},' +
                '"usr": "'+data.usr+'"' +
            	'}';
            	
            	$('#vidioURL').val(url);
			}
			
			function updateV2(playdata){
				var data = eval('('+playdata+')');
				var begin = parseInt($("#slider-range-left").slider("values", 0));
				var end = parseInt($("#slider-range-left").slider("values", 1));
				var sdt =  $('#iplaybackjson').val().replace(/-/g,"/");
				var dt =  Date.parse(sdt)/1000;
				//var bt = new Date(beginTime);
				var url = '{' +
                '"PlayType": 2,' +
                '"channelIp": "'+data.channelIp+'",' +
                '"cmdPort": '+data.cmdPort+',' +
                '"dataPort": '+data.dataPort+',' +
                '"devIp": "'+data.devIp+'",' +
                '"playerType": '+data.playerType+',' +
                '"psw": "'+data.psw+'",' +
                '"record": {' +
                    '"beginTime": '+(dt + begin * 60)+',' +
                    '"byteRate": 10485760,' +
                    '"channel": 1,' +
                    '"idxType": 3,' +
                    '"ip": 0,' +
                    '"peroid": '+((end - begin) * 60)+',' +
                    '"protocol": 1' +
                '},' +
                '"usr": "'+data.usr+'"' +
            	'}';
            	
            	$('#leftVidioURL').val(url);
			}
			
			function updateV3(playdata){
				var data = eval('('+playdata+')');
				var begin = parseInt($("#slider-range-right").slider("values", 0));
				var end = parseInt($("#slider-range-right").slider("values", 1));
				var sdt =  $('#iplaybackjson').val().replace(/-/g,"/");
				var dt =  Date.parse(sdt)/1000;
				//var bt = new Date(beginTime);
				var url = '{' +
                '"PlayType": 2,' +
                '"channelIp": "'+data.channelIp+'",' +
                '"cmdPort": '+data.cmdPort+',' +
                '"dataPort": '+data.dataPort+',' +
                '"devIp": "'+data.devIp+'",' +
                '"playerType": '+data.playerType+',' +
                '"psw": "'+data.psw+'",' +
                '"record": {' +
                    '"beginTime": '+(dt + begin * 60)+',' +
                    '"byteRate": 10485760,' +
                    '"channel": 1,' +
                    '"idxType": 3,' +
                    '"ip": 0,' +
                    '"peroid": '+((end - begin) * 60)+',' +
                    '"protocol": 1' +
                '},' +
                '"usr": "'+data.usr+'"' +
            	'}';
            	
            	$('#rightVidioURL').val(url);
			}
			
			function playCapture2(){
				capture();
			}
			
			function capture(){
				var img = playCapture();
				if(!img || img == ''){
					return;
				}
				var  img = 'data:image/jpg;base64,' + img; 
				$('#contab03').append("<div><input name='pictures' value='"+img+"' style='display:none;'/><img src='"+img+"' style='width:90%;height:240px;margin:5px'/><img src='<%=basePath%>resources/images/delete1.png' style='width:5%;' onclick='deleteimg($(this).parent())'/></div>");
			}
			
			function doplay(){
				url = '{"playerType" : 1, "cmdPort" : 8000,'+
   '"dataPort" : 8001, "devIp" : "10.3.10.190", "channelIp" : "10.3.10.193",'+
   '"usr" : "admin", "psw" : "12345"}';
   				doipcplay(url,GetCurSelectWndIndex());
			}
			
			function deleteimg(img){
				if(img){
					img.remove();
				}
			}
			
			function onAppData(event)
			{
				  if (event.getSubject() == localWarning) 
				  {//告警推送发来的消息
						var alarmOperation = event.get("alarmOperation");//消息处理类型 add 或者 del
						var alarmType = event.get("alarmType");//消息类型 securityAlarm或者devAlarm
						 if(alarmOperation=="add")
						 {
						 	if(alarmType=="securityAlarm")
				        	{
								$("#data_loading").show();
				        		var meesage = event.get("message");
				        		var jsonString = $.parseJSON(meesage);
				        		var currentUserLev = '${sessionScope.session.lev}';
				        		var alarmId = jsonString['alarmID'];
				        		var chengDu = jsonString['alarmLvl'];
				        		var hiddenSpan = "";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_gaojingbianhao'>"+jsonString['alarmID']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_fangquid'>"+jsonString['alarmZoneID']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_fujiaxinxi'>"+jsonString['info']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_idevType'>"+jsonString['idevType']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_alarmLvl'>"+jsonString['alarmLvl']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_chixushijian'>"+jsonString['alarmDuringTime']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_fuheyiju'>"+jsonString['checkMethodStr']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_baojingleixing'>"+jsonString['isRealStr']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_yanzhongjibie'>"+jsonString['checkLevelStr']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_shijianyuanyin'>"+jsonString['reason']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_shijianguocheng'>"+jsonString['description']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_fuzeren'>"+jsonString['peopleName']+"</span>";
				        		hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_yanzhongchengduLv'>"+jsonString['alarmLvl']+"</span>";
								hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_cishu'>"+jsonString['alarmCnt']+"</span>";
								hiddenSpan+="<span style='display:none;' id='gaojingchuli"+alarmId+"_pics'>"+jsonString['pictureURL']+"</span>";
				        		var trNode = "";
				        		if(chengDu==4)
				        		{
				        			//trNode+="<tr chuliIndex='"+alarmId+"' id='gaojingchuli_tr_"+alarmId+"' ondblclick='trDoubleClick(this,"+currentUserLev+")' class='bgcolor'>";
									trNode+="<tr chuliIndex='"+alarmId+"' id='gaojingchuli_tr_"+alarmId+"' ondblclick='trDoubleClick(this,"+currentUserLev+")'>";
				        		}
				        		else
				        		{
				        			trNode+="<tr chuliIndex='"+alarmId+"' id='gaojingchuli_tr_"+alarmId+"' ondblclick='trDoubleClick(this,"+currentUserLev+")'>";
				        		}
								trNode+="<td id='gaojingchuli"+alarmId+"_gaojingbianhaotr'>"+jsonString['alarmID']+"</td>";
				        		trNode+="<td id='gaojingchuli"+alarmId+"_gaojingshijian'>"+jsonString['alarmTime']+"</td>";
				        		trNode+="<td id='gaojingchuli"+alarmId+"_fengongsi'>"+jsonString['branchName']+"</td>";
				        		trNode+="<td id='gaojingchuli"+alarmId+"_guanlichu'>"+jsonString['mgtName']+"</td>";
				        		trNode+="<td id='gaojingchuli"+alarmId+"_fangqu'>"+jsonString['alarmZoneName']+"</td>";
				        		trNode+="<td id='gaojingchuli"+alarmId+"_gaojingmingcheng'>"+jsonString['alarmName']+"</td>";
				        		trNode+="<td id='gaojingchuli"+alarmId+"_shebeileixing'>"+jsonString['devType']+"</td>";
				        		trNode+="<td id='gaojingchuli"+alarmId+"_shebeiid'>"+jsonString['devID']+"</td>";
				        		//trNode+="<td id='gaojingchuli"+alarmId+"_yanzhongchengdu'>"+jsonString['severityLvl']+"</td>";
								if(chengDu==4)
				        		{
				        			trNode+="<td class='bgcolor' id='gaojingchuli"+alarmId+"_yanzhongchengdu'>"+jsonString['severityLvl']+"</td>";
				        		}
				        		else
				        		{
				        			trNode+="<td id='gaojingchuli"+alarmId+"_yanzhongchengdu'>"+jsonString['severityLvl']+"</td>";
				        		}
				        		trNode+="</tr>";
				        		$("#gaojingchuliTable tbody").prepend(hiddenSpan);
				        		$("#gaojingchuliTable tbody").prepend(trNode);
								var totalCount = parseInt($("#totalrecords").val());
								if(!isNaN(totalCount)&&totalCount>=500)
								{
									$("#gaojingchuliTable tbody tr:last").remove();
								}
								else
								{
									$("#totalrecords").val(totalCount+1);
								}
								$("#data_loading").hide();
				        	}
						 }
				  }
			}
		</script>
	</head>
	<body>
		<c:import url="../layout/header.jsp?navLink=gaojingchuli"  charEncoding="UTF-8"></c:import>
		<div id="main_content_container">
			<div id="nav_place">
				<div id="title">位置：</div>
				<ul>
					<li><a href="<c:url value="/"></c:url>">首页</a></li>
					<li><a href="<c:url value=""></c:url>">安防监控</a></li>
					<li>告警处理</li>
				</ul>
			</div>
			<div id="content_container">
			    <div id="left_gaojingchuli">
			    	<div class="top10 bottom10" style="width: 98%;margin-left: 1%;">
			    		<%-- <div class="ssjk_window">
							<img src="<%=basePath %>resources/images/videomonitor/Window.png" usemap="#Map"
								border="0" />
							<map name="Map" id="Map">
								<area shape="rect" coords="29,14,49,31"
									href="javascript:setWindowMode(4)" />
								<area shape="rect" coords="97,12,114,29"
									href="javascript:setWindowMode(4)" />
								<area shape="rect" coords="167,12,186,31"
									href="javascript:setWindowMode(4)" />
								<area shape="rect" coords="230,12,251,30"
									href="javascript:setWindowMode(4)" />
								<area shape="rect" coords="280,13,350,31"
									href="javascript:playCapture2()" />
								<area shape="rect" coords="670,13,730,33"
									href="javascript:fullScreen()" />
							</map>
						</div> --%>
			    		<object id="plugin0" type="application/x-ptplayerplugin" classid="clsid:4e29a691-8bf0-547a-9d91-a11e23b5a090" 							codebase="<%=basePath %>resources/plugins/videomonitor/NSSPluginSetup.exe"								style="width:1455px;height:555px">
								<param name="onload" value="pluginLoaded" />
							</object>
			    	</div>
			    	<div class="top10 bottom20" style="width:98%;margin-left: 1%;height:200px;overflow: auto;border-collapse: collapse;height: expression(document.body.clientHeight*0.3); ">
			    		<input type="hidden" id="totalrecords" value="${fn:length(uiAlarmList)}">
						<table border="1" width="730" class="imgtable" id="gaojingchuliTable">
							<thead>
								<tr>
									<th><span>告警编号</span></th>
									<th><span>时间</span></th>
									<th><span>分公司</span></th>
									<th><span>管理处</span></th>
									<th><span>防区</span></th>
									<th><span>告警名称</span></th>
									<th><span>设备类型</span></th>
									<th><span>设备ID</span></th>
									<th><span>严重程度</span></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${uiAlarmList }" var="uialarm" varStatus="index">
									<span style="display: none;" id="gaojingchuli${index.count }_gaojingbianhao"><c:out value="${uialarm.alarmID }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_fangquid"><c:out value="${uialarm.alarmZoneID }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_fujiaxinxi"><c:out value="${uialarm.info }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_idevType"><c:out value="${uialarm.idevType }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_alarmLvl"><c:out value="${uialarm.alarmLvl }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_chixushijian"><c:out value="${uialarm.alarmDuringTime }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_fuheyiju"><c:out value="${uialarm.checkMethodStr }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_baojingleixing"><c:out value="${uialarm.isRealStr }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_yanzhongjibie"><c:out value="${uialarm.severityLvl }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_shijianyuanyin"><c:out value="${uialarm.reason }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_shijianguocheng"><c:out value="${uialarm.description }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_fuzeren"><c:out value="${uialarm.peopleName }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_yanzhongchengduLv"><c:out value="${uialarm.alarmLvl }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_cishu"><c:out value="${uialarm.alarmCnt }"></c:out></span>
									<span style="display: none;" id="gaojingchuli${index.count }_pics"><c:out value="${uialarm.pictureURL }"></c:out></span>
									<c:choose>
										<c:when test="${uialarm.alarmLvl==4 }">
											<tr chuliIndex="${index.count }" id="gaojingchuli_tr_${uialarm.alarmID }" ondblclick="trDoubleClick(this,'${sessionScope.session.lev}');">
												<td id="gaojingchuli${index.count }_gaojingbianhaotr"><c:out value="${uialarm.alarmID }"></c:out></td>
												<td id="gaojingchuli${index.count }_gaojingshijian">
													<fmt:formatDate value="${uialarm.alarmTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td id="gaojingchuli${index.count }_fengongsi"><c:out value="${uialarm.branchName }"></c:out></td>
												<td id="gaojingchuli${index.count }_guanlichu"><c:out value="${uialarm.mgtName }"></c:out></td>
												<td id="gaojingchuli${index.count }_fangqu"><c:out value="${uialarm.alarmZoneName }"></c:out></td>
												<td id="gaojingchuli${index.count }_gaojingmingcheng"><c:out value="${uialarm.alarmName }"></c:out></td>
												<td id="gaojingchuli${index.count }_shebeileixing"><c:out value="${uialarm.devType }"></c:out></td>
												<td id="gaojingchuli${index.count }_shebeiid"><c:out value="${uialarm.devID }"></c:out></td>
												<td id="gaojingchuli${index.count }_yanzhongchengdu" class="bgcolor"><c:out value="${uialarm.severityLvl }"></c:out></td>
										  	</tr>
										</c:when>
										<c:otherwise>
											<tr chuliIndex="${index.count }" id="gaojingchuli_tr_${uialarm.alarmID }" ondblclick="trDoubleClick(this,'${sessionScope.session.lev}');">
												<td id="gaojingchuli${index.count }_gaojingbianhaotr"><c:out value="${uialarm.alarmID }"></c:out></td>
												<td id="gaojingchuli${index.count }_gaojingshijian">
													<fmt:formatDate value="${uialarm.alarmTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td id="gaojingchuli${index.count }_fengongsi"><c:out value="${uialarm.branchName }"></c:out></td>
												<td id="gaojingchuli${index.count }_guanlichu"><c:out value="${uialarm.mgtName }"></c:out></td>
												<td id="gaojingchuli${index.count }_fangqu"><c:out value="${uialarm.alarmZoneName }"></c:out></td>
												<td id="gaojingchuli${index.count }_gaojingmingcheng"><c:out value="${uialarm.alarmName }"></c:out></td>
												<td id="gaojingchuli${index.count }_shebeileixing"><c:out value="${uialarm.devType }"></c:out></td>
												<td id="gaojingchuli${index.count }_shebeiid"><c:out value="${uialarm.devID }"></c:out></td>
												<td id="gaojingchuli${index.count }_yanzhongchengdu" ><c:out value="${uialarm.severityLvl }"></c:out></td>
										  	</tr>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tbody>
			            </table>
			    	</div>
			    </div>
			    <div id="right_gaojingchuli" style="height:793px;">
			    	<div class="top10 bottom10" style="width: 96%;margin-left: 2%;">
			    		<div class="ss_ptzcontrol" style="height:150px; overflow:hidden;">

							<div class="ptzcontrol" style="">
								<div style="width:100%;height:100%;float:left;margin:10px;">
									<div style="width:60%;height:100%;float:left;margin:5px 0px;">
										<img src="<%=basePath %>resources/images/videomonitor/control1.png" alt="control" usemap="#Map2"/>
										<map id="Map2" >
											<area shape="rect" coords="50,10,65,25" onmousedown="javascript:a7d()" onmouseup="javascript:a7u()" alt="top" title="上 "/>
											<area shape="rect" coords="50,90,65,105" onmousedown="javascript:a11d()" onmouseup="javascript:a11u()"  alt="bottom" title="下 "/>
											<area shape="rect" coords="10,50,25,65" onmousedown="javascript:a13d()" onmouseup="javascript:a13u()" alt="left" title="左 "/>
											<area shape="rect" coords="90,50,105,65" onmousedown="javascript:a9d()" onmouseup="javascript:a9u()"  alt="right" title="右  "/>
											<area shape="rect" coords="25,20,40,35" onmousedown="javascript:a14d()" onmouseup="javascript:a14u()"  alt="leftup" title="左上 "/>
											<area shape="rect" coords="78,25,93,40" onmousedown="javascript:a8d()" onmouseup="javascript:a8u()"  alt="rightup" title="右 上 "/>
											<area shape="rect" coords="78,78,93,93" onmousedown="javascript:a10d()" onmouseup="javascript:a10u()"  alt="rightbottom" title="右 下 "/>
											<area shape="rect" coords="25,78,40,93" onmousedown="javascript:a12d()" onmouseup="javascript:a12u()"  alt="leftbottom" title="左  下 "/>
											<area shape="circle" coords="58,58,25" onmousedown="javascript:a15d()" onmouseup="javascript:a15u()"  alt="stop" title="停止 "/>
										</map>
									</div>
									<div style="width:40%;height:100%;float:left;margin:5px 0px;">
										<div style="width:48%;height:38px;float:left;">
											<img src="<%=basePath %>resources/images/videomonitor/control-r0c0.png" alt="" title="光圈缩小" style="margin:auto auto;" onmousedown="javascript:a1d()" onmouseup="javascript:a1u()"/>
										</div>
										<div style="width:48%;height:38px;float:left;">
											<img src="<%=basePath %>resources/images/videomonitor/control-r0c1.png" alt="" title="光圈扩大" style="margin:auto auto;" onmousedown="javascript:a2d()" onmouseup="javascript:a2u()"/>
										</div>
										<div style="width:48%;height:38px;float:left;">
											<img src="<%=basePath %>resources/images/videomonitor/control-r1c0.png" alt="" title="焦距变大" style="margin:auto 10px;" onmousedown="javascript:a3d()" onmouseup="javascript:a3u()"/>
										</div>
										<div style="width:48%;height:38px;float:left;">
											<img src="<%=basePath %>resources/images/videomonitor/control-r1c1.png" alt="" title="焦距变小" style="margin:auto 10px;" onmousedown="javascript:a4d()" onmouseup="javascript:a4u()"/>
										</div>
										<div style="width:50%;height:38px;float:left;">
											<img src="<%=basePath %>resources/images/videomonitor/control-r2c0.png" alt="" title="焦点前调" style="margin:auto auto;" onmousedown="javascript:a5d()" onmouseup="javascript:a5u()"/>
										</div>
										<div style="width:50%;height:38px;float:left;">
											<img src="<%=basePath %>resources/images/videomonitor/control-r2c1.png" alt="" title="焦点后调" style="margin:auto auto;" onmousedown="javascript:a6d()" onmouseup="javascript:a6u()"/>
										</div>
									</div>
								</div>
							</div>
							<div class="clear"></div>
			    		</div>
			    		<div class="gaojingchuli_operation">
							<div class="title"><span>现场操作</span></div>
							<ul>
								<li><a href="javascript:void(0);" class="button_zidongyuyan" id="button_zidongyuyan"></a><p>自动语音</p></li>
								<li><a href="javascript:void(0);" class="button_hanhua" id="button_hanhua"></a><p>喊话</p></li>
								<object id="plugin3" type="application/x-ptvoicecastplugin" width="0" height="0">
								    <param name="onload" value="pluginLoaded" />
								</object>
								<c:choose>
									<c:when test="${sessionScope.session.lev eq '2' }">
										<li><a href="javascript:playCapture2();" class="button_zhuatu" id="button_chujing"></a><p>抓图</p></li>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>								
							<!-- <li><a href="javascript:void(0);" class="button_jingjiqingbao"></a><p>紧急情报</p></li> -->
							</ul>
						</div>
						<div class="clear"></div>
			    	</div>
			    	<div class="normalajaxloading" id="data_loading"></div>
			    	<div class="normalajaxloading" id="confirm_loading"></div>
			    	<div class="normalajaxloading" id="report_loading"></div>
			    	<div class="top10 bottom20" style="width:96%;margin-left: 2%;overflow: hidden;">
				    	<div class="tab-hd">
							<li onclick="set('tab0',1,3)" id="tab01" style="margin-right:2px;cursor: pointer;width:80px;" class="active">基本信息</li>
							<c:choose>
								<c:when test="${sessionScope.session.lev eq '2' }">
									<li onclick="set('tab0',2,3)" id="tab02" style="margin-right:2px;cursor: pointer;width:80px;" class="">录像</li>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							<li onclick="set('tab0',3,3)" id="tab03" style="margin-right:2px;cursor: pointer;width:80px;" class="">图像</li>
						</div>
						<input type="hidden" name="" value="" id="yanzhongchengduLv"/>
						<input type="hidden" name="" value="${sessionScope.session.serverHostIp }" id="zonggongsiip"/>
						<form action="" method="post" id="gaojingchuliajaxform">
							<input type="hidden" name="alarm.deviceID" value="1" id="shebeiid"/>
							<input type="hidden" name="alarm.alarmID" value="148" id="gaojingbianhao"/>
							<input type="hidden" name="alarm.zoneID" value="0" id="fangquid"/>
							<div class="tab-bd">
								<div id="contab01" style="height:401px;overflow: auto;">
									<c:choose>
										<c:when test="${sessionScope.session.lev eq '2' }">
											<div class="top10">
												<div class="">
													<label style="display: block;font-size:13px;width:25%;float: left;text-align: right;height:30px;line-height:30px;">复核依据:</label>
													<input type="radio" id="fuheyiju" value="1" name="alarm.checkMothod" style="margin-top:5px;margin-left:10px;" checked="checked"/>视频复核
													<input type="radio" id="fuheyiju" value="2" name="alarm.checkMothod" style="margin-top:5px;margin-left:20px;"/>现场复核
													<div class="clear"></div>
												</div>
											</div>
											<div class="top5">
												<div class="">
													<label style="display: block;font-size:13px;width:25%;float: left;text-align: right;height:30px;line-height:30px;">警报类型:</label>
													<input type="radio" id="baojingleixing" value="0" name="alarm.isReal" style="margin-top:5px;margin-left:10px;"/>虚警
													<input type="radio" id="baojingleixing" value="1" name="alarm.isReal" style="margin-top:5px;margin-left:20px;" checked="checked"/>实警
													<div class="clear"></div>
												</div>
											</div>
											<div class="top5">
												<div class="">
													<label style="display: block;font-size:13px;width:25%;float: left;text-align: right;height:30px;line-height:30px;">复核严重级别:</label>
													<select class="normalselect left" style="width: 55%;" name="alarm.checkLevel">
														<option value="1" selected="selected">警告</option>
														<option value="2">次要</option>
														<option value="3">主要</option>
														<option value="4">严重</option>
													</select>
													<div class="clear"></div>
												</div>
											</div>
											<div class="top5">
												<div class="">
													<label style="display: block;font-size:13px;width:25%;float: left;text-align: right;height:30px;line-height:30px;">事件原因:</label>
													<input type="text" id="" name="alarm.reason" class="normaltext left validate[maxSize[80]]" style="width: 53%;" value=""/>
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</div>
											<div class="top5">
												<div class="">
													<label style="display: block;font-size:13px;width:25%;float: left;text-align: right;height:30px;line-height:30px;">事件过程:</label>
													<textarea rows="10" cols="10" name="alarm.description"  class="normaltext left validate[maxSize[160]]" style="width: 53%;height:100px;"></textarea>
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</div>
											<div class="top5">
												<div class="">
													<label style="display: block;font-size:13px;width:25%;float: left;text-align: right;height:30px;line-height:30px;">负责人:</label>
													<select class="normalselect left" style="width: 55%;" name="alarm.peopleID">
														<c:forEach items="${peopleList }" var="personBean">
															<option value="${personBean.id }">${personBean.name }</option>
														</c:forEach>
													</select>
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</div>
											<div class="top5">
												<div class="">
													<label style="display: block;font-size:13px;width:25%;float: left;text-align: right;height:30px;line-height:30px;">记录备注:</label>
													<textarea rows="10" cols="10" name="alarm.info" class="normaltext left validate[maxSize[80]]" style="width: 53%;height:100px;"></textarea>
													<div class="clear"></div>
												</div>
												<div class="clear"></div>
											</div>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="alarm.alarmID" value="" id="gaojingbianhao"/>
											<table class="detail_table" id="gaojingchuli_detail" style="font-size:12px;width:99%;table-layout: fixed;">
												<tbody>
													<tr>
														<td class="title_name" style="width:32%;">告警编号:</td>
														<td class="content content_gaojingbianhao" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
													<tr>
														<td class="title_name" style="width:32%;">复核依据:</td>
														<td class="content content_fuheyiju" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
													<tr>
														<td class="title_name" style="width:32%;">警报类型:</td>
														<td class="content content_baojingleixing" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
													<tr>
														<td class="title_name" style="width:32%;">复核严重级别:</td>
														<td class="content content_yanzhongjibie" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
													<tr>
														<td class="title_name" style="width:32%;">事件原因:</td>
														<td class="content content_shijianyuanyin" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
													<tr>
														<td class="title_name" style="width:32%;">事件过程:</td>
														<td class="content content_shijianguocheng" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
													<tr>
														<td class="title_name" style="width:32%;">负责人:</td>
														<td class="content content_fuzeren" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
													<tr>
														<td class="title_name" style="width:32%;">记录备注:</td>
														<td class="content content_beizhu" style="font-size:12px;word-wrap: break-word;"></td>
													</tr>
												<tbody>
											</table>
										</c:otherwise>
									</c:choose>
								</div>
								<div style="display:none;" id="contab02">
<!-- 									<img alt="" src="${pageContext.request.contextPath}/resources/images/t2.png"> -->
										<p>
										  <label for="amount">录像时间范围：</label>
										  <input type="text" id="amount" style="border:0;background:lightblue; font-weight:bold;width:100%;">
										</p>
										<div id="slider-range" style="color:black;background:lightblue;margin-top:10px;"></div>
										<div id="slider-range-left" style="color:black;background:lightblue;margin-top:10px;"></div>
										<div id="slider-range-right" style="color:black;background:lightblue;margin-top:10px;"></div>
									<input type="hidden" id="iplaybackjson"/>
									<input type="hidden" id="vidioURL" value="" name="alarm.vidioURL">
									<input type="hidden" id="leftVidioURL" value="" name="alarm.leftVidioURL">
									<input type="hidden" id="rightVidioURL" value="" name="alarm.rightVidioURL">
								</div>
								<div style="display:none;overflow:auto;" id="contab03">
									<%-- <img alt="" class="pictureURL" src="${pageContext.request.contextPath}/resources/images/t3.png">
									<input type="hidden" id="pictureURLHidden" value="" name="pictures"> --%>
								</div>
							</div>
							<div class="top10 bottom10" style="margin-left:20%;">
								<c:choose>
									<c:when test="${sessionScope.session.lev eq '2' }">
										<input type="button" value="不上报" class="btn" id="gaojingchuli_queren">
										<input type="button" value="上报" class="btn" id="gaojingchuli_shangbao">
									</c:when>
									<c:when test="${sessionScope.session.lev eq '1' }">
										<input type="button" value="不上报" class="btn" id="gaojingchuli_queren_2">
										<input type="button" value="上报" class="btn" id="gaojingchuli_shangbao_2">
									</c:when>
									<c:otherwise>
										<input type="button" value="确认" class="btn" id="gaojingchuli_queren_2">
									</c:otherwise>
								</c:choose>
								
							</div>
							
			    			<input id="iautopicture" name="autopicture" value='' style='display:none;'/>
						</form>
			    	</div>
			    </div>
			    <div class="clear"></div>
			</div>
		</div>
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</body>
</html>