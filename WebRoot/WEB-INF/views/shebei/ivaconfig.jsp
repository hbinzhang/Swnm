<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="net.sf.json.*"%>
<%@ page import="java.util.*"%>
<%
	Object pages = request.getAttribute("page.pages");	
	pages = pages == null ? 1 : pages;
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
<script src="${pageContext.request.contextPath}/resources/js/videomonitor.js" type="text/javascript"></script>
<script type="text/javascript">
	var ivaconf = {};
	/* ivaconf.ivaID='iva001';
		ivaconf.ipcs = [];
		ivaconf.ipcs[0] = {"ipcID":"ipc001","ip":"10.3.29.122"};
		ivaconf.ipcs[1] = {"ipcID":"ipc002","ip":"10.3.29.123"};
		ivaconf.ipcs[0].alarms = [];
		ivaconf.ipcs[0].alarms[0] = {"highTossAct":1,"flotage":1}; */
	$(function(){
		//ajax请求不使用缓存
		$.ajaxSetup({ cache: false }); 
		setWindowMode(1);
		$('#iflotage').change(function(d){
			var flotage = $(this)[0].checked;
			var ipcdata = $('#tbs').find('.selectedipc').attr('ipcdata');
			var ipcjson = JSON.parse(ipcdata);
			ipcjson.alarms.flotage = flotage ? 1 : 0;
			$('#tbs').find('.selectedipc').attr('ipcdata',JSON.stringify(ipcjson));
		});
		$('#ihighttossact').change(function(d){
			var hightossact = $(this)[0].checked;
			var ipcdata = $('#tbs').find('.selectedipc').attr('ipcdata');
			var ipcjson = JSON.parse(ipcdata);
			ipcjson.alarms.highTossAct = hightossact ? 1 : 0;
			$('#tbs').find('.selectedipc').attr('ipcdata',JSON.stringify(ipcjson));
		});
		$('#spoint').change(function(d){
			var point = $(this).find('option:selected').val();
			var ipcdata = $('#tbs').find('.selectedipc').attr('ipcdata');
			var ipcjson = JSON.parse(ipcdata);
			ipcjson.point = point;
			$('#tbs').find('.selectedipc').attr('ipcdata',JSON.stringify(ipcjson));
		});
	});
	
	function createIVAConf(){
		var ivaselected = $('#tivasc tr.ivaselected');
		if(ivaselected.length==0){
			alert('请选择一个IVA配置！');
			return;
		}
		ivaconf.ivaID=$('#tivasc tr.ivaselected').find('.tdivaid').html();
		ivaconf.ip=$('#tivasc tr.ivaselected').find('.tdip').html();
		ivaconf.port=$('#tivasc tr.ivaselected').find('.tdport').html();
		ivaconf.ipcs = [];
		$('#tbs tr').each(function(i,d){
			var ipcdata = $(d).attr('ipcdata');
			ivaconf.ipcs[i] = JSON.parse(ipcdata);
		});
		
		/* ivaconf.ipcs[0].alarms = [];
		ivaconf.ipcs[0].alarms[0] = {"hightTossAct":1,"flotage":1}; */
		//alert(JSON.stringify(ivaconf));
		
		$.ajax({
			url:'<%=basePath%>videomonitor/addConfig.action',
			data:'ivaConfig=' + JSON.stringify(ivaconf),
			success:function(d,e){
				alert('保存配置成功！');
			},
			error:function(d,e){
				alert('保存配置失败！');
			}});
	}
	
	function onipcselect(trelement){
		var lastselected = trelement.parent().find('.selectedipc');
		lastselected.find('.devname').css('background-color','white');
		lastselected.css('color','black');
		lastselected.attr('class','');
		trelement.attr('class','selectedipc');
		trelement.find('.devname').css('background-color','gray');
		trelement.css('color','white');
		
		loadAlarmByIpc(trelement.attr('ipcdata'));
	}
	
	function loadAlarmByIpc(sipcdata){
		var ipcdata = JSON.parse(sipcdata);
		var point = ipcdata.point;
		var highTossAct = ipcdata.alarms.highTossAct;
		var flotage = ipcdata.alarms.flotage;
		$('#ihighttossact')[0].checked = highTossAct == '1' ? true : false;
		$('#iflotage')[0].checked = flotage == '1' ? true : false;
		$.ajax({
			url:'<%=basePath%>videomonitor/queryPointByIpcId.action',
			data:'ipcID=' + ipcdata.ipcID,
			dataType:'json',
			success:function(d,e){
				var data = d.object;
				$('#spoint option').remove();
				if(data){
					$('#spoint').append('<option></option>');
					for(var index in data){
						var p = parseInt(data[index]);
						if(p){
							var selectedvar = p == point ? 'selected="selected"' : '';
							$('#spoint').append('<option value="'+p+'"'+selectedvar+'>'+p+'</option>');
						}
					}
				}
				
				//$('#spoint').val(point);
			},
			error:function(d,e){
				alert('获取预置点失败！');
			}
		});
	}
	
	function rightMoveOne() {
		$("#tbn tr").each(function(d, i) {
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			if (td1 && td1[0].checked) {
				td1[0].checked = false;
				$(this).remove();
				var ipcjson = $(this).attr('ipcdata');
				var playdata = $(this).attr('playdata');
				var toelement = "<tr onclick='onipcselect($(this))' ipcdata='"+ipcjson+"' playdata='"+playdata+"'><td><input type='checkbox'/></td><td class='devname'>"+$(this).find('.devname').html()+"</td><td style='display:none;'><input class='ipcchecked' value='"+$(this).find('.ipcchecked').val()+"'></input></td><td><input type='button' style='background:lightblue;width:50px;height:20px;color:white; border-radius:4px;' value='播放' onclick='doplay($(this))'/></td></tr>";
				$("#tbs").append(toelement);
			}
		});
	}
	function leftMoveOne() {
		$("#tbs tr").each(function(d, i) {
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			if (td1 && td1[0].checked) {
				td1[0].checked = false;
				$(this).remove();
				var ipcjson = $(this).attr('ipcdata');
				var playdata = $(this).attr('playdata');
				var toelement = "<tr ipcdata='"+ipcjson+"' playdata='"+playdata+"'><td><input type='checkbox'/></td><td class='devname'>"+$(this).find('.devname').html()+"</td><td style='display:none;'><input class='ipcnochecked' value='"+$(this).find('.ipcchecked').val()+"'></input></td></tr>";
				$("#tbn").append(toelement);
			}
		});
	}
	function rightMoveAll() {
		$("#tbn tr").each(function(d, i) {
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			td1[0].checked = false;
			$(this).remove();
			var ipcjson = $(this).attr('ipcdata');
			var playdata = $(this).attr('playdata');
			var toelement = "<tr onclick='onipcselect($(this))' ipcdata='"+ipcjson+"' playdata='"+playdata+"'><td><input type='checkbox'/></td><td class='devname'>"+$(this).find('.devname').html()+"</td><td style='display:none;'><input class='ipcchecked' value='"+$(this).find('.ipcchecked').val()+"'></input></td><td><input type='button' style='background:lightblue;width:50px;height:20px;color:white; border-radius:4px;' value='播放' onclick='doplay($(this))'/></td></tr>";
			$("#tbs").append(toelement);
		});
	}
	function leftMoveAll() {
		$("#tbs tr").each(function(d, i) {
			var td1 = $(this).find("td:eq(0) input:eq(0)");
			td1[0].checked = false;
			$(this).remove();
			var ipcjson = $(this).attr('ipcdata');
			var playdata = $(this).attr('playdata');
			var toelement = "<tr ipcdata='"+ipcjson+"' playdata='"+playdata+"'><td><input type='checkbox'/></td><td class='devname'>"+$(this).find('.devname').html()+"</td><td style='display:none;'><input class='ipcnochecked' value='"+$(this).find('.ipcchecked').val()+"'></input></td></tr>";
			$("#tbn").append(toelement);
		});
	}
	function onivaselected(tr){
		var selectediva = tr.parent().find('.ivaselected');
		if(selectediva.length != 0 && !confirm('请确认已经保存当前IVA配置再选择配置其它IVA！')){
			return;
		}
		selectediva.css('background','white');
		selectediva.attr('class','');
		selectediva.css('color','black');
		
		tr.css('background','gray');
		tr.attr('class','ivaselected');
		tr.css('color','white');
		
		loadIpcsByIva(tr.find('.tdivaid').html(),tr.find('.tdbranch').html(),tr.find('.tdmanagementagency').html());
	}
	
	function loadIpcsByIva(ivaid,branch,managementagency){
		if(ivaid && ivaid != '' && branch && branch != '' && managementagency && managementagency != ''){
			$.ajax({
			url:'<%=basePath%>videomonitor/loadIpcConfig.action?',
			data:'branch='+branch+'&managementagency='+managementagency+'&ivaID=' + ivaid,
			type:'post',
			dataType:'json',
			success:function(d,e){
				$('#tbn tr').remove();
				$('#tbs tr').remove();
				var unconfig = d.object.unConfIpc;
				var confIpc = d.object.confIpc;
				for(var index in unconfig){
					var ipc = unconfig[index];
					var ipcjson = {'ipcID':ipc.ipcid,'point':'','alarms':{'highTossAct':0,'flotage':0}};
					var playjson = '';
					if(ipc.nvr){
						playjson = '{"playerType" : 1, "cmdPort" : '+ipc.port
									+ ',"dataPort" : '
									+ (parseInt(ipc.port) + 1)
									+ ', "devIp" : "'
									+ ipc.nvr.ip
									+ '","channelIp" : "'
									+ ipc.ip
									+ '", "usr" : "'
									+ ipc.username
									+ '","psw" : "'
									+ ipc.password
									+ '"}';
					}
					$('#tbn').append("<tr ipcdata='"+JSON.stringify(ipcjson)+"' playdata='"+playjson+"'><td><input type='checkbox'/></td><td class='devname'>"+ipc.devname+"</td><td style='display:none;'><input class='ipcnochecked' value='"+ipc.ipcid+"'></input></td></tr>");
				}
				for(var index in confIpc){
					var ipc = confIpc[index];
					var ipcjson = {'ipcID':ipc.ipcid,'point':ipc.point,'alarms':{'highTossAct':ipc.highTossAct,'flotage':ipc.flotage}};
					var playjson = '{"playerType" : 1, "cmdPort" : '+ipc.port
									+ ',"dataPort" : '
									+ (parseInt(ipc.port) + 1)
									+ ', "devIp" : "'
									+ ipc.nvr.ip
									+ '","channelIp" : "'
									+ ipc.ip
									+ '", "usr" : "'
									+ ipc.username
									+ '","psw" : "'
									+ ipc.password
									+ '"}';
					$("#tbs").append("<tr onclick='onipcselect($(this))' ipcdata='"+JSON.stringify(ipcjson)+"' playdata='"+playjson+"'><td><input type='checkbox'/></td><td class='devname'>"+ipc.devname+"</td><td style='display:none;'><input class='ipcchecked' value='"+ipc.ipcid+"'></input></td><td><input type='button' style='background:lightblue;width:50px;height:20px;color:white; border-radius:4px;' value='播放' onclick='doplay($(this))'/></td></tr>");
				}
			},
			error:function(d,e){
				alert('加载IVA所属摄像机失败！');
			}});
		}
	}
	
	function onsave(){
		createIVAConf();
	}
	
	function dopageup(){		
		var pageOffset = parseInt($('#ipageoffset').html()) - 1;
		if(pageOffset < 1){
			return;
		}
		window.location = "<%=basePath%>videomonitor/queryIvaByPage.action?page.offset="+pageOffset+"&page.size=5";
	}
	
	function dopagedown(){
		var pageOffset = parseInt($('#ipageoffset').html()) + 1;
		if(pageOffset > <%=pages%>){
			return;
		}
		window.location = "<%=basePath%>videomonitor/queryIvaByPage.action?page.offset="+pageOffset+"&page.size=5";
	}
	
	function doskip(){
		var pageOffset = $('#itopage').val();
		if(pageOffset >= 1 && pageOffset <= <%=pages%>){
			window.location = "<%=basePath%>videomonitor/queryIvaByPage.action?page.offset="+pageOffset+"&page.size=5";
		}
	}
	
	function doplay(ipcelement){
		var ipcjson = ipcelement.parent().parent().attr('ipcdata');
		url = ipcelement.parent().parent().attr('playdata');
		if(!url){
			alert('播放参数不全，请重新配置此设备！');
			return;
		}
		var index = GetCurSelectWndIndex();
		doipcplayasync(url,index,2000);
	}
	
	function doGotoPreset(presetno){
		var ipcdata = $('#tbs').find('.selectedipc').attr('ipcdata');
		var ipcjson = JSON.parse(ipcdata);
		
		gotoPreset(ipcjson.ipcid,presetno);
	}
</script>
</head>
<body>
	<c:import url="../layout/header.jsp?navLink=zhinengshipin" charEncoding="UTF-8"></c:import>
	<div id="main_content_container">
		<div id="content_container">
			<div class="roleadd_container">
				<form action="<%=basePath%>videomonitor/addOrUpdateNvr.action"
					method="post">
					<div class='dialogModal_content'>
						<div class="companysubject_add_form_cantainer">
							<div id="companysubject_add_form" style="width:50%;float:left;">
								<!-- 					<form id="fnvr" action="<%=basePath%>addNvr.do" method="POST" enctype="multipart/form-data"> -->
								<%-- <input name="nvrToAddOrUpdate.nvrid" id="invrid"
									style="display:none;" value="${ nvrToAddOrUpdate.nvrid }" /> --%>
								
								<div style="float:left;width:98%;height:282px; padding:10px 0px 0px 10px;">
									<table class="tableui" id="tivasc"" style="height:222px;">
										<thead>
											<tr>
												<th>服务器名称</th>
												<th>IP</th>
												<th>PORT</th>
												<th>分公司</th>
												<th>管理处</th>
											</tr>
										</thead>
										<tbody>
											<s:iterator var="data" value="page.datas" status="st">
												<tr onclick="onivaselected($(this))">
													<td style="display:none;" class="tdivaid"><s:property value="ivaID" /></td>
													<td><s:property value="name" />
													</td>
													<td class="tdip"><s:property value="ip" /></td>
													<td class="tdport"><s:property value="port" /></td>
													<td><s:property value="branchName"/></td>
													<td><s:property value="mgtName"/></td>
													<td style="display:none;" class="tdbranch"><s:property value="branch"/></td>
													<td style="display:none;" class="tdmanagementagency"><s:property value="managementagency"/></td>
												</tr>
											</s:iterator>
										</tbody>
									</table>
									<div class="pagin">
										<div class="message left">
											共<a class="blue" href="javascript:void(0);"><s:property value="page.total"/></a>条记录，当前显示第&nbsp;<a
												class="blue" href="javascript:void(0);" id="ipageoffset"><s:property value="page.offset"/></a>&nbsp;页
										</div>
										<ul class="paginList">
											<li class="paginItem"><a href="javascript:dopageup();">上页</a>
											</li>
											<li class="paginItem"><a href="javascript:dopagedown();">下页</a>
											</li>
											<li class="paginItem2"><span>第<input type="text"
													value="" name="" id="itopage">页</span>
											</li>
											<li class="paginItem"><a href="javascript:doskip();">跳转</a>
											</li>
										</ul>
									</div>
								</div>
								<%-- <div style="float:left;width:50%;">
									<div class="top10">
									<div class="left" style="width: 100%;float:left;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">智能服务器ID:</label>
										<input type="text" id="invrid" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.ivaid }" disabled="disabled"/>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 100%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">服务器名称:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.name }"
											disabled="disabled" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 100%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">服务器IP:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.ip }"
											disabled="disabled" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 100%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">服务器PORT:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.port }"
											disabled="disabled" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 100%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">分公司:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.branch }"
											disabled="disabled" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="top10">
									<div class="left" style="width: 100%;">
										<label
											style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">管理处:</label>
										<input type="text" id="" class="normaltext left"
											style="width: 70%;" value="${ nvrToAddOrUpdate.managementagency }"
											disabled="disabled" />
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
								
								</div>
								</div> --%>
								
								
								<div style="width:100%;margin:8px 0px 0px 0px;float:left;">
									<div id="ipcs" style="width:100%;height:500px;">
										<div style='width:98%;height:500px;margin:auto auto;'>
											<div
												style="width:44%;height:500px;float:left;border:1px solid lightblue;overflow:auto;">
												<a
													style="font-size:16px;background:lightblue;width:100%;display:block;">未分配摄像机：</a>
												<table id="tbn">
<!-- 													<tr style='display:none;'><td><input name="ipcnochecked"/></td></tr> -->
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
												style="width:44%;height:500px;float:left;border:1px solid lightblue;overflow:auto;">
												<a
													style="font-size:16px;background:lightblue;width:100%;display:block;">已分配摄像机</a>
												<table id="tbs">
<!-- 													<tr style='display:none;'><td><input name="ipcchecked"/></td></tr> -->
												</table>
											</div>
											<div class="clear"></div>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<div style="width:50%;float:left;">
								<div style="width:100%;height:300px;">
									<div style="width:50%;height:30px;margin:10px 0px;float:left;">									
										<input type="checkbox" id="iflotage"/> <label>漂浮物</label>
									</div>
									<div style="width:50%;height:50px;margin:10px 0px;float:left;">
										<input type="checkbox" id="ihighttossact"/> <label>高空抛物</label>
									</div>
									<div style="width:50%;height:50px;margin:10px 0px;float:left;">
										<label>预置位：</label>
										<select id="spoint">
											
										</select>
										<input type="button" value="调用" style="width:50px;height:20px;background:lightblue;color:white;" onclick="doGotoPreset($('#spoint').val())"/>
									</div>
								</div>
								<div style="width:100%;">
									<object id="plugin0" type="application/x-ptplayerplugin" classid="clsid:4e29a691-8bf0-547a-9d91-a11e23b5a090" 
									codebase="<%=basePath %>resources/plugins/videomonitor/NSSPluginSetup.exe#version=${ pluginVersion }"
										style="width:100%;height:500px">
										<param name="onload" value="pluginLoaded" />
									</object>
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<div class="left40 top20">
						<input type="button" onclick="onsave()" value="保存" class="btn left20 top10" /> <a
							href="<%=basePath%>videomonitor/queryIvaByPage.action?page.offset=1&page.size=5" class="hrefbtn">取消</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
</body>
</html>