<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="com.entity.authmgt.Session"%>
<%@ page import="com.util.alarmmgt.AlarmUtil"%>
<%@ page import="net.sf.json.*"%>
<%
	int pagesize = 12;
	int rowcount = 255;
	Session mySession = AlarmUtil.getLoginSession();
	String userId = mySession.getId();
    Cookie[] cookies = request.getCookies();  
 	int windowmode=0;  
 	String[] urls = null; 
	if (cookies != null) {  
	   	try{
	   		for (Cookie c : cookies) {
		   		String name = c.getName();
		   		 if(name.equals("playurl-" + userId)){
		   		 	String cookieValue = c.getValue();
		   		 	JSONObject joPlayValue = JSONObject.fromObject(cookieValue);
		   		 	windowmode = joPlayValue.getInt("windowmode");
		   		 	urls = new String[windowmode];
		   		 	JSONArray jaPlayUrls = joPlayValue.getJSONArray("urls");
		   		 	if(jaPlayUrls == null){
		   		 		continue;
		   		 	}
		   		 	for(Object jo : jaPlayUrls.toArray()){
		   		 		JSONObject joUrl = (JSONObject)jo;
		   		 		int index = joUrl.getInt("index");
		   		 		if(index < windowmode){
		   		 			urls[index] = joUrl.getString("url");
		   		 		}
		   		 	}
		   		 }
		   	}
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<title>信息管理系统界面</title>
<link href="<%=basePath%>resources/css/style_shipin.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css"
	rel="stylesheet" />
<script type="text/javascript" src="<%=basePath %>resources/js/tabs.js"></script>
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="icon" type="image/x-icon" />
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="shortcut icon" type="image/x-icon" />
<link href="${pageContext.request.contextPath}/resources/css/common.css"
	rel="stylesheet"></link>
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet"></link>
<link
	href="${pageContext.request.contextPath}/resources/css/popModal.min.css"
	rel="stylesheet"></link>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/jquery.form.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/videomonitor.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery.validationEngine.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery.validationEngine-zh_CN.js"
	type="text/javascript"></script>

<script type="text/javascript">

var ipcscache = null;

$(function(){
	//ajax请求不使用缓存
	$.ajaxSetup({ cache: false }); 
	setPreviewCallback(previewcallback);
	
	var sv = '${ pluginVersion }';
	if(hasNewVersion(sv)){
		showUpdateAlert(sv);
	}
	setWindowMode(4);
	//$('.right_ssjk_l').css('width','1040px');
	//$('.ssjk_window').css('width','1040px')
	//$('.ss_video').css('width','1030px');
	//$('#plugin0').css('width','1530px');
		
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected");
		$(this).addClass("selected");
	});
	
	var lastwindowmode = <%=windowmode == 0 ? 4 : windowmode%>;
	
	if(lastwindowmode){
		setWindowMode(lastwindowmode);
	}
	
	var win0_url = null;
	
		<%if(urls != null){
			int i = 0;
			for(String url : urls){
				if(url == null || url.equals("")){
					i++;
					continue;
				}%>
				var surl<%=i%> = <%=url%>;
				if(<%=i%> == 0){
					win0_url = surl<%=i%>;
				}
				if(surl<%=i%>){
					doipcplayasync(JSON.stringify(surl<%=i%>),<%=i%>, 2000 + 1000 * <%=i%>);
					//doipcplay(JSON.stringify(surl<%=i%>),<%=i%>);
				}
			<%i++;
			}
		}%>
		
		$('.leftmenusjjk dd').empty();
	//$('.leftmenusjjk dd').append('<span>&nbsp;&nbsp;设备列表：</span>');
	$.ajax({
		url:"<%=basePath%>videomonitor/loadIpcAjax.action",
			data : 'ajaxIpcSearchCond=test&pageSize=100000',
			type : 'get',
			dataType : 'json',
			success : function(d, e) {
				var data = eval(d);
				var ipcs = data[0].ipcs;
				var managements = data[0].managements;
				var branchs = data[0].branchs;
				if(branchs){
					var branchlen = branchs.length;
					for(var i = 0;i < branchlen;i++){
						var branch = branchs[i];
						if(!branch){
							continue;
						}
						var divbranch = '<div id="sgc_'+branch.id+'" class="t fblod title" onclick="w(\'gc_'+branch.id+'\',\'sgc_'+branch.id+'\')"><a href="#">'+branch.name+'</a></div><ul id="gc_'+branch.id+'" class="ps menuson" style="display: none;"></ul>';
						$('.leftmenusjjk dd').append(divbranch);
						
						var divbranch_round = '<div id="sgc_'+branch.id+'_wg" class="t fblod title" onclick="w(\'gc_'+branch.id+'_wg\',\'sgc_'+branch.id+'_wg\')"><a href="#">'+branch.name+'</a></div><ul id="gc_'+branch.id+'_wg" class="ps menuson" style="display: none;"></ul>';
						$('.dl-round-tree dd').append(divbranch_round);
					}
				}
				if(managements){
					for(var bid in managements){
						var managementlist = managements[bid];
						if(!managementlist){
							continue;
						}
						var mlength = managementlist.length;
						for(var i=0;i<mlength;i++){
							var management = managementlist[i];
							if(!management){
								continue;
							}
							var limanagement = '<li id="sfgc91_'+management.id+'" class="f" onclick="k(\'fgc91_'+management.id+'\',\'sfgc91_'+management.id+'\')" style="display: block;"><a href="#" >'+management.name+'</a></li><li id="fgc91_'+management.id+'" class="ps" style="display: block;"></li>';
							$('#gc_'+bid).append(limanagement);
							
							var limanagement_round = '<li id="sfgc91_'+management.id+'_wg" class="f" onclick="k(\'fgc91_'+management.id+'_wg\',\'sfgc91_'+management.id+'_wg\')" style="display: block;"><a href="#" >'+management.name+'</a></li><li id="fgc91_'+management.id+'_wg" class="ps" style="display: block;"></li>';
							$('#gc_'+bid+'_wg').append(limanagement_round);
						}
					}
				}
				if (ipcs) {
					ipcscache = ipcs;
					var length = ipcs.length;
					for ( var i = 0; i < length; i++) {
						
						if(!ipcs[i] || !ipcs[i].nvr || !ipcs[i].nvr.ip){
							continue;
						}
						var displayName = ipcs[i].devname;
						if(displayName && displayName.length > 10){
							displayName = displayName.substring(0,10) + '...';
						}
						$('#fgc91_'+ipcs[i].managementagency).append(
								"<div class='b' onmouseover='$(this)[0].focus();'><a ondblclick=\"doplay('"
										+ ipcs[i].ip + "','" + ipcs[i].port
										+ "','" + ipcs[i].username + "','"
										+ ipcs[i].password + "','"
										+ (ipcs[i].nvr ? ipcs[i].nvr.ip : '') +"')\" title='"+ipcs[i].devname+"'>"
										+ displayName + "</a><div class='clear'></div></div>");
										
						$('#fgc91_'+ipcs[i].managementagency+'_wg').append(
								"<div class='b' style='height:20px;' onmouseover='$(this)[0].focus();'><input class='i-round-ipc-check' type='checkbox' style='margin:5px 10px 0px 0px;float:left;line-height:20px;'/><a ondblclick=\"doplay('"
										+ ipcs[i].ip + "','" + ipcs[i].port
										+ "','" + ipcs[i].username + "','"
										+ ipcs[i].password + "','"
										+ (ipcs[i].nvr ? ipcs[i].nvr.ip : '') +"')\" title='"+ipcs[i].devname+"' ipcdata='"+JSON.stringify(ipcs[i])+"'>"
										+ displayName + "</a><div class='clear'></div></div>");
					}
				}
				if(branchs && branchs.length == 1){
					$('.leftmenusjjk dd div:eq(0)').each(function(i,d){
						d.click();
					});
					
					$('.dl-round-tree dd div:eq(0)').each(function(i,d){
						d.click();
					});
				}
				processVideoSelected(win0_url);
			},
			error : function(d, e) {
				alert(e);
			}
		});
		
				$("#slider-bright").slider({
			      min: 1,
			      max: 10,
			      value: 5,
			      slide: function( event, ui ) {
			        $("#amount-bright").html(ui.value);
			        setVideoParams(1,ui.value);
			      }
			    });
			    
			    $("#slider-contrast").slider({
			      min: 1,
			      max: 10,
			      value: 5,
			      slide: function( event, ui ) {
			        $("#amount-contrast").html(ui.value);
			        setVideoParams(2,ui.value);
			      }
			    });
			    
			    $("#slider-saturation").slider({
			      min: 1,
			      max: 10,
			      value: 5,
			      slide: function( event, ui ) {
			        $("#amount-saturation").html(ui.value);
			        setVideoParams(3,ui.value);
			      }
			    });
			    
			    $("#slider-sharpness").slider({
			      min: 1,
			      max: 10,
			      value: 5,
			      slide: function( event, ui ) {
			        $("#amount-sharpness").html(ui.value);
			        setVideoParams(5,ui.value);
			      }
			    });
		
	});
	
	function doplay(ipcip, port, username, password, nvrip) {
		url = '{"playerType" : 1, "cmdPort" : '+port
				+ ',"dataPort" : '
				+ (parseInt(port) + 1)
				+ ', "devIp" : "'
				+ nvrip
				+ '","channelIp" : "'
				+ ipcip
				+ '", "usr" : "'
				+ username
				+ '","psw" : "'
				+ password
				+ '"}';
		var index = GetCurSelectWndIndex();
		doipcplayasync(url,index,2000);
		//doipcplay(url,index);
		
		$.ajax({
			url:'<%=basePath%>videomonitor/setPlayCookie.action',
			data:'playCookieContent=' + '{\"windowmode\":'+windowmode+',\"index\":'+index+',\"url\":'+url+'}',
			dataType:'json',
			type:'post',
			success:function(d,e){
				//alert('COOKIE已保存！');
			},
			error:function(d,e){
				alert('保存COOKIE失败！');
			}
		});
	}
	
	function previewcallback(data){
		if(data == ""){
			processVideoNone();
		}
		else if(isNaN(data)){
			var videoinfo = eval('('+data+')');
			if(videoinfo){
				processVideoSelected(videoinfo);
			}
		}else{
			var location = (window.location+'').split('/'); 
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			$.ajax({
				url:basePath + '/videomonitor/setPlayCookie.action',
				data:'playCookieContent=' + '{\"windowmode\":'+windowmode+',\"index\":'+data+',\"url\":""}',
				dataType:'json',
				type:'post',
				success:function(d,e){
					//alert('COOKIE已保存！');
				},
				error:function(d,e){
					alert('保存COOKIE失败！');
				}
			});
		}
	}
	
	function docloseall(){
		for(var i = 0;i < windowmode;i++){
			doipcstop(i);
		}
	}
	
	function processVideoNone(){
		$('#vi_ipcid').html('');
		$('#vi_ipcid').attr('title','');
		$('#vi_ipcip').html('');
		$('#vi_ipcip').attr('title','');
		$('#vi_port').html('');
		$('#vi_port').attr('title','');
		$('#vi_devname').html('');
		$('#vi_devname').attr('title','');
		$('#vi_nvrip').html('');
		$('#vi_nvrip').attr('title','');
		setTimeout(function(){loadPreset();},1000);
		setTimeout(function(){loadcruises();},1000);
	}
	
	function processVideoSelected(videoinfo){
		var ipc = null;
		if(videoinfo){
			$(ipcscache).each(function(i,d){
				if(d.ip == videoinfo.channelIp){
					ipc = d;
				}
			});
		}
		
		if(ipc){
			if($('#vi_ipcid').html() == ipc.ipcid){
				return;
			}
		
			$('#vi_ipcid').html(ipc.ipcid);
			$('#vi_ipcid').attr('title',ipc.ipcid);
			$('#vi_ipcip').html(ipc.ip);
			$('#vi_ipcip').attr('title',ipc.ip);
			$('#vi_port').html(ipc.port);
			$('#vi_port').attr('title',ipc.port);
			
			var displayname = ipc.devname;
			if(displayname.length > 10){
				displayname = displayname.substring(0,10) + '...';
			}
			
			$('#vi_devname').html(displayname);
			$('#vi_devname').attr('title',ipc.devname);
			$('#vi_nvrip').html(ipc.nvr.ip);
			$('#vi_nvrip').attr('title',ipc.nvr.ip);
		}
		setTimeout(function(){loadPreset();},1000);
		setTimeout(function(){loadcruises();},1000);
	}
	
	function loadPreset(){
		dopagefirst(true);
	}
</script>
<style>
* {
	margin: 0;
	padding: 0;
	list-style: none;
}

.lanrenzhijia {
	width: 355px;
	height: 20px;
	line-height: 20px;
	margin-top: 27px;
	z-index: 999;
	float: right;
}

.lanrenzhijia li a {
	color: #000;
	text-decoration: none;
	display: block;
	float: left;
	height: 20px;
	line-height: 20px;
	padding: 0px 10px;
	font-size: 12px;
	background: #d7dadf;
	border-radius: 8px 8px 0px 0px;
	margin-right: 1px;
}

.lanrenzhijia li a:hover {
	background: #f2eeee;
}

.lanrenzhijia li {
	float: left;
	position: relative;
	height: 20px;
	line-height: 20px;
}

.lanrenzhijia li .second {
	position: absolute;
	left: 0;
	display: none;
}

.lanrenzhijia li .second a {
	border-radius: 0px;
}
</style>
</head>
<body>
	<c:import url="../layout/header.jsp?navLink=shishijiankong"
		charEncoding="UTF-8"></c:import>

	<!--—————right—————-->
	<div style="float:left; width:100%;">
		<div class="rightinfo">

			<div class="right_ssjk">
				<div class="right_ssjk_l">

					<div class="ss_video">
						<div class="ssjk_window">

							<div style="height:42px;float:left;">
								<ul class="window_control">
									<li><img
										src="<%=basePath%>resources/images/videomonitor/win-1.png"
										usemap="#win-1.png" /> <map name="win-1.png" id="win-1.png">
											<area shape="rect" coords="0,0,65,31"
												href="javascript:setWindowMode(1)" />
										</map>
									</li>
									<li><img
										src="<%=basePath%>resources/images/videomonitor/win-4.png"
										usemap="#win-4.png" /> <map name="win-4.png" id="win-4.png">
											<area shape="rect" coords="0,0,65,31"
												href="javascript:setWindowMode(4)" />
										</map>
									</li>
									<li><img
										src="<%=basePath%>resources/images/videomonitor/win-9.png"
										usemap="#win-9.png" /> <map name="win-9.png" id="win-9.png">
											<area shape="rect" coords="0,0,65,31"
												href="javascript:setWindowMode(9)" />
										</map>
									</li>
									<li><img
										src="<%=basePath%>resources/images/videomonitor/win-16.png"
										usemap="#win-16.png" /> <map name="win-16.png"
											id="win-16.png">
											<area shape="rect" coords="0,0,65,31"
												href="javascript:setWindowMode(16)" />
										</map>
									</li>
									<li><img
										src="<%=basePath%>resources/images/videomonitor/win-capture.png"
										usemap="#win-capture.png" /> <map name="win-capture.png"
											id="win-capture.png">
											<area shape="rect" coords="0,0,65,31"
												href="javascript:playCaptureLocal()" />
										</map>
									</li>
									<li><img
										src="<%=basePath%>resources/images/videomonitor/win-close.png"
										usemap="#win-close.png" /> <map name="win-close.png" id="win-close.png">
											<area shape="rect" coords="0,0,65,31"
												href="javascript:docloseall()" />
										</map>
									</li>
								</ul>
							</div>
							<div style="float:right;margin:3px auto;">
								<img
									src="<%=basePath%>resources/images/videomonitor/win-full.png"
									usemap="#full.png" border="0" alt="全屏" />
								<map name="full.png" id="full.png">
									<area shape="rect" coords="0,0,65,31"
										href="javascript:fullScreen()" />
								</map>
								<img
									src="<%=basePath%>resources/images/videomonitor/shangqiang.png"
									usemap="#shangqiang.png" border="0" alt="打开电视墙" />
								<map name="shangqiang.png" id="shangqiang.png">
									<area shape="rect" coords="0,0,67,33"
										href="<%=basePath%>videomonitor/loadVideoWall.action" />
								</map>
							</div>
						</div>
						<ul>
							<!-- <li><img src="<%=basePath%>resources/images/video_img01.jpg" /></li>
                <li><img src="<%=basePath%>resources/images/video_img02.jpg" /></li>
                <li><img src="<%=basePath%>resources/images/video_img03.jpg" /></li>
                <li><img src="<%=basePath%>resources/images/video_img04.jpg" /></li> -->
							<object id="plugin0" type="application/x-ptplayerplugin"
								classid="clsid:4e29a691-8bf0-547a-9d91-a11e23b5a090"
								codebase="<%=basePath%>resources/plugins/videomonitor/NSSPluginSetup.exe"
								style="width:1600px;height:832px">
								<param name="onload" value="pluginLoaded" />
							</object>
						</ul>
						<div class="clear"></div>
						<!--		<div class="progress_bar"><img src="images/progress_bar.png" /></div>-->
					</div>

					<div class="clear"></div>
					<!--ss_video end-->
				</div>


			</div>
			<div class="ss_ptzcontrol" style="height:876px; overflow:hidden;">

				<div class="ptzcontrol">
					<div style="width:100%;height:100%;float:left;">
						<div style="width:50%;height:100%;float:left;margin:5px 0px;">
							<img
								src="<%=basePath%>resources/images/videomonitor/control1.png"
								alt="control" usemap="#Map2" />
							<map id="Map2">
								<area shape="rect" coords="50,10,65,25"
									onmousedown="javascript:a7d()" onmouseup="javascript:a7u()"
									alt="top" title="上 " />
								<area shape="rect" coords="50,90,65,105"
									onmousedown="javascript:a11d()" onmouseup="javascript:a11u()"
									alt="bottom" title="下 " />
								<area shape="rect" coords="10,50,25,65"
									onmousedown="javascript:a13d()" onmouseup="javascript:a13u()"
									alt="left" title="左 " />
								<area shape="rect" coords="90,50,105,65"
									onmousedown="javascript:a9d()" onmouseup="javascript:a9u()"
									alt="right" title="右  " />
								<area shape="rect" coords="25,20,40,35"
									onmousedown="javascript:a14d()" onmouseup="javascript:a14u()"
									alt="leftup" title="左上 " />
								<area shape="rect" coords="78,25,93,40"
									onmousedown="javascript:a8d()" onmouseup="javascript:a8u()"
									alt="rightup" title="右 上 " />
								<area shape="rect" coords="78,78,93,93"
									onmousedown="javascript:a10d()" onmouseup="javascript:a10u()"
									alt="rightbottom" title="右 下 " />
								<area shape="rect" coords="25,78,40,93"
									onmousedown="javascript:a12d()" onmouseup="javascript:a12u()"
									alt="leftbottom" title="左  下 " />
								<area shape="circle" coords="58,58,25"
									onmousedown="javascript:a15d()" onmouseup="javascript:a15u()"
									alt="stop" title="停止 " />
							</map>
						</div>
						<div style="width:50%;height:100%;float:left;margin:5px 0px;">
							<div style="width:48%;height:38px;float:left;">
								<img
									src="<%=basePath%>resources/images/videomonitor/control-r0c0.png"
									alt="" title="光圈缩小" style="margin:auto auto;"
									onmousedown="javascript:a1d()" onmouseup="javascript:a1u()" />
							</div>
							<div style="width:48%;height:38px;float:left;">
								<img
									src="<%=basePath%>resources/images/videomonitor/control-r0c1.png"
									alt="" title="光圈扩大" style="margin:auto auto;"
									onmousedown="javascript:a2d()" onmouseup="javascript:a2u()" />
							</div>
							<div style="width:48%;height:38px;float:left;">
								<img
									src="<%=basePath%>resources/images/videomonitor/control-r1c0.png"
									alt="" title="焦距变大" style="margin:auto 10px;"
									onmousedown="javascript:a3d()" onmouseup="javascript:a3u()" />
							</div>
							<div style="width:48%;height:38px;float:left;">
								<img
									src="<%=basePath%>resources/images/videomonitor/control-r1c1.png"
									alt="" title="焦距变小" style="margin:auto 10px;"
									onmousedown="javascript:a4d()" onmouseup="javascript:a4u()" />
							</div>
							<div style="width:50%;height:38px;float:left;">
								<img
									src="<%=basePath%>resources/images/videomonitor/control-r2c0.png"
									alt="" title="焦点前调" style="margin:auto auto;"
									onmousedown="javascript:a5d()" onmouseup="javascript:a5u()" />
							</div>
							<div style="width:50%;height:38px;float:left;">
								<img
									src="<%=basePath%>resources/images/videomonitor/control-r2c1.png"
									alt="" title="焦点后调" style="margin:auto auto;"
									onmousedown="javascript:a6d()" onmouseup="javascript:a6u()" />
							</div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<!--ptzcontrol end-->
				<!--ptzcontrol2 end-->
				<!--tab title-->

				<style>
ul,li {
	list-style: none;
}

.tab {
	margin: 0 auto;
}
</style>
				<div class="tab-hd">
					<li class="active" id="tab02" onmouseup="set('tab0',2,2)">设备列表</li>
					<li id="tab01" onmouseup="set('tab0',1,2)">参数设置</li>
				</div>
				<!--tab1-->
				<div id="contab01" style="display:none;">
					<div id="coninner_tab01" style="display:block;height:680px;">
						<div class="huamianshezhi">
							<div class="title">
								<span>画面设置</span>
							</div>
							<div class="gundongtao">
								<p>亮度:</p>
								<div id="slider-bright"></div>
								<span style="display:inline;margin-left:10px;"
									id="amount-bright">5</span>
							</div>
							<div class="gundongtao">
								<p>对比度:</p>
								<div id="slider-contrast"></div>
								<span style="display:inline;margin-left:10px;"
									id="amount-contrast">5</span>
							</div>
							<div class="gundongtao">
								<p>饱和度:</p>
								<div id="slider-saturation"></div>
								<span style="display:inline;margin-left:10px;"
									id="amount-saturation">5</span>
							</div>
							<div class="gundongtao">
								<p>锐度:</p>
								<div id="slider-sharpness"></div>
								<span style="display:inline;margin-left:10px;"
									id="amount-sharpness">5</span>
							</div>
						</div>
						<div class="c_videoinfo">
							<div class="title">
								<li id="tab01" onmouseup="set('tab0',1,2)">设备基本信息</li>
							</div>
							<div class="clear"></div>
							<div>
								<span>设备ID：</span><span id="vi_ipcid"></span>
							</div>
							<div class="clear"></div>
							<div>
								<span>设备名称：</span><span id="vi_devname"></span>
							</div>
							<div class="clear"></div>
							<div>
								<span>设备IP：</span><span id="vi_ipcip"></span>
							</div>
							<div class="clear"></div>
							<div>
								<span>设备端口：</span><span id="vi_port"></span>
							</div>
							<div class="clear"></div>
							<div>
								<span>NVR IP：</span><span id="vi_nvrip"></span>
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<div id="coninner_tab02" style="display:none;height:680px;">
						<div class="dpresetlist dcruiseconfig" style="">
							<div class="title">
								<span>预置位列表</span>
							</div>
							<div style="height:500px;overflow:auto;">
								<script type="text/javascript">
									var pageno = 1;
									var pagesize = <%=pagesize%>;
									var rowcount = <%=rowcount%>;
									var pagecount = Math.ceil(rowcount / pagesize);
									$(function(){
										$('#i-pageno').val(pageno);
										$('#s-pagecount').html(pagecount);
										//setTimeout(function(){dopagefirst(true);},5000);
									});
									function liselect(index){					
										var selectedli = $('#ul-preset-presetlist li.liselected');
										var currentli = $('#ul-preset-presetlist li:eq('+index+')');
										if(currentli.index() == selectedli.index()){
											return;
										}
										currentli.css({
													background:'gray',
										});
										currentli.addClass('liselected');
										selectedli.css({
													background:'blue',
										});
										selectedli.removeClass('liselected');
										
										var currentlispan = currentli.find('.s-presetname');
										$('#i-newpresetname').val(currentlispan.html());	
									}
									function dodeleteli(index){
										var currentli = $('#ul-preset-presetlist li:eq('+index+')');
										var presetdata = currentli.attr('presetdata');
										if(!presetdata){
											alert('此预置位未设置过！');
											return;
										}
										
										var persetdatajson = eval('('+presetdata+')');
										
										if(!confirm('确认要删除此预置位的配置吗？')){
											return;
										}
										$.ajax({
											url:'<%=basePath%>videomonitor/deletePreset.action',
											type:'post',
											dataType:'json',
											data:"presetSetData={'id':"+persetdatajson.pointid+"}",
											success:function(d,e){
												var data = eval('('+d+')');
												if(data && data.res == e && data.sres > 0){
													alert('删除预置位成功！');
														
													var origname = '预置位' + (currentli.index() + 1);
													currentli.find('.s-presetname').html(origname);
													$('#i-newpresetname').val(origname);
												}else{
													alert('删除预置位失败！');
												}
											},
											error:function(d,e){
												alert('删除预置位失败：' + d.sres);
											}
										});
									}
									function dosavepresetname(){										
										var newname = $('#i-newpresetname').val();
										var selectedli = $('#ul-preset-presetlist li.liselected');
										if(selectedli.length == 0){
											alert('请选中要配置的预置位！');
											return;
										}
										
										var res = $("#f-preset").validationEngine('validate');
										if(!res){
											return;
										}
										
										var ipcid = $('#vi_ipcid').html();
										var presetno = selectedli.attr('id').split('\-')[2];
										var id = '';
										var presetdata = selectedli.attr('presetdata');						
										if(presetdata){
											var presetdatajson = eval('('+presetdata+')');
											id = '"id":'+presetdatajson.pointid+',';
										}
										var data = 'presetSetData='+'{'+id+'"ipcid":"'+ipcid+'","presetname":"'+newname+'","presetno":'+presetno+'}';
										$.ajax({
											url:'<%=basePath%>videomonitor/savePreset.action',
											type:'post',
											dataType:'json',
											data:data,
											success:function(d,e){
												var data = eval('('+d+')');
												if(!data || !data.sres || data.res != e){
													alert('保存预置位失败！');
													return;
												}
												var presetdata = '{"ipcid":"'+ipcid+'","point":'+presetno+',"presetname":"'+newname+'","pointid":'+data.sres+'}';
												selectedli.attr('presetdata',presetdata);
												alert('保存预置位成功！');
												
												var displayname = newname;
												if(newname.length > 10){
													displayname = newname.substr(0,10)+'...';
												}
												selectedli.find('.s-presetname').html(displayname);
												selectedli.find('.s-presetname').attr('title',newname);
											},
											error:function(d,e){
												alert('保存预置位失败：' + d.sres);
											}
										});
									}
									function docancelpresetname(){
										var selectedli = $('#ul-preset-presetlist li.liselected');
										$('#i-newpresetname').val(selectedli.find('.s-presetname').html());
									}
									function doplaypreset(presetno){
										//doPreset(index);
										gotoPreset($('#vi_ipcid').html(),presetno);
									}
									function appendli(rowbase){
										for(var i=1;i <= 12;i++){
											if(rowbase + i > rowcount){
												continue;
											}
											var vli = '<li style="width:260px;text-align:left;" id="li-preset-'+(rowbase + i)+'" presetdata="" onclick="liselect('+(i - 1)+')"><span style="margin-left:5px;float:left;width:20px;">'+(rowbase + i)+'</span><span style="width:180px;margin-left:10px;float:left;" class="s-presetname">预置位'+(rowbase + i)+'</span><img src="<%=basePath%>resources/images/videomonitor/delete.png" style="margin:8px 5px 0px 0px;width:15px;height:15px;float:right;" onclick="dodeleteli('+(i-1)+')"/><img src="<%=basePath%>resources/images/videomonitor/play.jpg" style="margin-left:10px;margin:8px 5px 0px 0px;width:15px;height:15px;float:right;" onclick="doplaypreset('+i+')"/></li>';
											$('#ul-preset-presetlist').append(vli);
										}
										var ipcid = $('#vi_ipcid').html();
										if(!ipcid){
											return;
										}
										var data = "presetSetData={'from':"+(rowbase + 1)+",'to':"+(rowbase + 12)+",'ipcid':'"+ipcid+"'}";
										$.ajax({
											url:'<%=basePath%>videomonitor/loadPresetByPage.action',
											type:'post',
											dataType:'json',
											data:data,
											success:function(d,e){
												var presets = eval('('+d+')');
												if(presets && presets.sres && presets.res == e){
													var length = presets.sres.length;
													for(var i=0;i<length;i++){
														var presetno = presets.sres[i].point;
														var presetname = presets.sres[i].presetname;
														var vli = $('#ul-preset-presetlist li#li-preset-'+presetno);
														if(vli){
															vli.attr('presetdata',JSON.stringify(presets.sres[i]));
															if(presetname){
																var displayname = presetname;
																if(presetname.length > 10){
																	displayname = displayname.substr(0,10) + '...';
																}
																vli.find('.s-presetname').html(displayname);
																vli.find('.s-presetname').attr('title',presetname);
															}
														}
													}
												}else{
													alert('获取预置位配置失败：' + presets.sres);
												}
											},
											error:function(d,e){
												alert('获取预置位配置失败：' + d.sres);
											}
										});										
									}
									function checkpage(){
										var res = true;
										res = $("#f-pageno").validationEngine('validate');
										return res;
									}
									function dopageup(){
										var tpageno = $('#i-pageno').val();
										$('#i-pageno').val(isNaN(tpageno) ? tpageno : parseInt(tpageno) - 1);
										if(!checkpage()){
											$('#i-pageno').val(pageno);
											return;
										}
										$('#ul-preset-presetlist li').remove();
										pageno = parseInt(tpageno) - 1;
										var rowbase = (pageno - 1) * pagesize;
										appendli(rowbase);
										$('#i-pageno').val(pageno);
										$('#i-newpresetname').val('');
									}
									function dopagedown(){
										var tpageno = $('#i-pageno').val();
										$('#i-pageno').val(isNaN(tpageno) ? tpageno : parseInt(tpageno) + 1);
										if(!checkpage()){
											$('#i-pageno').val(pageno);
											return;
										}
										$('#ul-preset-presetlist li').remove();
										pageno = parseInt(tpageno) + 1;
										var rowbase = (pageno - 1) * pagesize;
										appendli(rowbase);
										$('#i-pageno').val(pageno);
										$('#i-newpresetname').val('');
									}
									function doskip(){
										if(!checkpage($('#i-pageno').val())){
											$('#i-pageno').val(pageno);
											return;
										}
										if(pageno == $('#i-pageno').val()){
											return;
										}
										pageno = parseInt($('#i-pageno').val());
										$('#ul-preset-presetlist li').remove();
										var rowbase = (pageno - 1) * pagesize;
										appendli(rowbase);
										$('#i-pageno').val(pageno);
										$('#i-newpresetname').val('');
									}
									function dopagefirst(isinit){
										pageno = parseInt($('#i-pageno').val());
										if(!isinit && pageno == 1){
											alert('已经是第一页！');
											return;
										}
										$('#ul-preset-presetlist li').remove();
										var rowbase = 0;
										appendli(rowbase);
										pageno = 1;
										$('#i-pageno').val(pageno);
										$('#i-newpresetname').val('');
										$("#f-pageno").validationEngine('validate');
									}
									function dopagelast(){
										pageno = $('#i-pageno').val();
										if(pageno == pagecount){
											alert('已经是最后一页！');
											return;
										}
										$('#ul-preset-presetlist li').remove();
										var rowbase = (pagecount - 1) * pagesize;
										appendli(rowbase);
										pageno = pagecount;
										$('#i-pageno').val(pageno);
										$('#i-newpresetname').val('');
										$("#f-pageno").validationEngine('validate');
									}
									function getpagecount(){
										return pagecount;
									}
								</script>
								<ul id="ul-preset-presetlist">
								</ul>
							</div>
							<div style="float:right;">
								<div style="display:block;text-align:center;">
									<span style="float:left;width:80px;height:25px;font-size:12px;">当前页号：</span>
									<form id="f-pageno">
										<input id="i-pageno" type="text"
											class="validate[required,custom[integer,max[<%=Math.ceil((double)rowcount / pagesize)%>],min[1]]]"
											style="width:40px;height:20px;float:left;font-size:12px;" />
									</form>
									<span style="float:left;width:60px;height:25px;font-size:12px;">&nbsp&nbsp总页数：</span><span
										id="s-pagecount"
										style="float:left;font-size:12px;width:30px;height:25px;" />
								</div>
								<div class="clear"></div>
								<div style="margin-top:10px;">
									<input type="button" class="video-btn"
										style="width:47px;height:30px;font-size:12px;" value="跳转"
										onclick="doskip()" /> <input type="button" class="video-btn"
										style="float:left;width:47px;height:30px;font-size:12px;"
										value="首页" onclick="dopagefirst()" /> <input type="button"
										class="video-btn"
										style="float:left;width:47px;height:30px;font-size:12px;"
										value="上一页" onclick="dopageup()" /> <input type="button"
										class="video-btn"
										style="float:left;width:47px;height:30px;font-size:12px;"
										value="下一页" onclick="dopagedown()" /> <input type="button"
										class="video-btn"
										style="float:left;width:47px;height:30px;font-size:12px;"
										value="尾页" onclick="dopagelast()" />
								</div>

							</div>
						</div>
						<div class="clear"></div>
						<div class="dpresetconfig" style="margin-top:10px">
							<div class="title">
								<span>设置预置位</span>
							</div>
							<div>
								<form id="f-preset">
									<input id="i-newpresetname" type="text"
										style="float:left;width:150px;height:32px;"
										class="validate[required,maxSize[21]]" />
								</form>
								<input type="button" class="video-btn"
									style="float:left;width:50px;height:30px;" value="保存"
									onclick="dosavepresetname()" /> <input type="button"
									class="video-btn" style="float:left;width:50px;height:30px;"
									value="取消" onclick="docancelpresetname()" />
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<div id="coninner_tab03" style="display:none;height:680px;">
						<script type="text/javascript">
							$(function(){
								//loadcruises();
								$('#s-cruise-config-list').change(function(){
									docruiselistchange();
								});								
							});
							function docruiselistchange(){
								var selectedcruise =  $('#s-cruise-config-list option:selected');
								if(selectedcruise.length == 0){
									return;
								}
								$('#i-cruise-route').val(selectedcruise.val());
								$('#i-cruise-name').val(selectedcruise.html());
								loadcruisepresets(selectedcruise.val());
							}
							function loadcruises(){
								$('#s-cruise-config-list').empty();
								$('#u-cruise-preset-list li').remove();
								$('#i-cruise-route').val('');
								$('#i-cruise-name').val('');
								
								var ipcid = $('#vi_ipcid').html();
								if(!ipcid){
									return;
								}
								
								for(var i = 1;i <= 32;i++){
									$('#s-cruise-config-list').append('<option value=\''+i+'\' cruisedata=\'\'>巡航方案'+i+'</option>');					
								}
								
								var data = "cruiseSetData={'ipcid':'"+ipcid+"'}";
								$.ajax({
									url:'<%=basePath%>videomonitor/loadCruiseByIpcid.action',
									type:'post',
									dataType:'json',
									data:data,
									success:function(d,e){
										var cruises = eval('('+d+')');
										if(cruises && cruises.sres && cruises.res == e){
											$(cruises.sres).each(function(i,cruise){
												//$('#s-cruise-config-list').append('<option value=\''+cruise.cruiseid+'\' cruisedata=\''+JSON.stringify(cruise)+'\'>'+cruise.cruisename+'</option>');
												var curcruise = $('#s-cruise-config-list option[value="'+cruise.route+'"]');
												if(curcruise){
													curcruise.attr('cruisedata',JSON.stringify(cruise));
													curcruise.html(cruise.cruisename);
												}
											});
										}else{
											alert('获取巡航方案失败：' + cruises.sres);
										}
										$('#s-cruise-config-list').change();
									},
									error:function(d,e){
										alert('获取巡航方案失败：' + d.sres);										
									}
								});	
							}
							function licruisepresetselect(lielement){
								var selectedli = $('#u-cruise-preset-list li.licruisepresetselected');
								var currentli = lielement;
								if(currentli.index() == selectedli.index()){
									return;
								}
								currentli.css({
										background:'gray',
								});
								currentli.addClass('licruisepresetselected');
								selectedli.css({
										background:'blue',
								});
								selectedli.removeClass('licruisepresetselected');
							}
							function createpreset(sequence){
								var presetdatajson = sequence.point;
								var interval = sequence.pausemins;
								var speed = sequence.speed;
								var displayname = presetdatajson.presetname;										
								if(displayname.length > 6){
									displayname = displayname.substr(0,6)+'...';
								}
								var vli = '<li style="text-align:left;" onclick="licruisepresetselect($(this))" sequencedata=\''+JSON.stringify(sequence)+'\'><span style="display:block;margin-left:5px;width:12px;font-size:10px;float:left;">'+presetdatajson.point+'</span><span style="display:block;width:100px;float:left;margin-left:10px;" class="s-presetname" title="'+presetdatajson.presetname+'">'+displayname+'</span><span style="display:inline;margin-left:5px;font-size:10px;" id="s-cruise-preset-interval">'+interval+'</span><span style="display:inline;font-size:10px;">s</span><span style="display:inline;margin-left:5px;font-size:10px;" id="s-cruise-preset-speed">'+speed+'</span><span style="display:inline;font-size:10px;">x</span><img src="<%=basePath%>resources/images/videomonitor/delete.png" style="margin:8px 5px 0px 0px;width:15px;height:15px;float:right;" onclick="dodeletecruisepreset($(this).parent())"/><img src="<%=basePath%>resources/images/videomonitor/edit.png" style="margin:8px 5px 0px 0px;width:15px;height:15px;float:right;" onclick="doeditcruisepreset($(this).parent())"/><img src="<%=basePath%>resources/images/videomonitor/play.jpg" style="margin:8px 5px 0px 0px;width:15px;height:15px;float:right;" onclick="doplaypreset('+presetdatajson.point+')"/></li>';
								return vli;	
							}						
							function loadcruisepresets(selectedcruise){
								if(!selectedcruise){
									return;
								}
								$('#u-cruise-preset-list li').remove();
								var cruisedata = $('#s-cruise-config-list option[value="'+selectedcruise+'"]').attr('cruisedata');
								if(cruisedata){
									var cruisedatajson = eval('('+cruisedata+')');
									$(cruisedatajson.sequences).each(function(i,sequence){
										var vli = createpreset(sequence);
										$('#u-cruise-preset-list').append(vli);	
									});
								}
								var addpresetlistr = '<li id="l-cruise-addpreset" style="background:green;" onclick="doaddcruisepreset()"><img src="<%=basePath%>resources/images/videomonitor/add.png" style="width:20px;height:20px;margin-right:30px;"/><span style="display:inline;line-height:25px;">添加预置位</span></li>';
								$('#u-cruise-preset-list').append(addpresetlistr);
							}
							function doaddcruise(){
// 								$('#i-cruise-name').val('');
// 								$('#i-cruise-route').val('');
// 								var addpreset = $('#u-cruise-preset-list li#l-cruise-addpreset');
// 								if(addpreset.length == 0){
// 									var addpresetlistr = '<li id="l-cruise-addpreset" style="background:green;" onclick="doaddcruisepreset()"><img src="<%=basePath%>resources/images/videomonitor/add.png" style="width:20px;height:20px;margin-right:30px;"/><span style="display:inline;line-height:25px;">添加预置位</span></li>';
// 									$('#u-cruise-preset-list').append(addpresetlistr);
// 								}
// 								$('#u-cruise-preset-list li:not("#l-cruise-addpreset")').remove();
							}
							function getallpresets(callback){
								var ipcid = $('#vi_ipcid').html();
								if(!ipcid){
									return;
								}
								var data = "presetSetData={'from':"+1+",'to':"+256+",'ipcid':'"+ipcid+"'}";
								$.ajax({
									url:'<%=basePath%>videomonitor/loadPresetByPage.action',
									type:'post',
									dataType:'json',
									data:data,
									success:function(d,e){
										var presets = eval('('+d+')');
										if(presets && presets.sres && presets.res){
											if(presets.res == e){
												callback(presets.sres);
											}else{
												alert('获取预置位配置失败：' + d.sres);
												callback(null);
											}
										}else{
											alert('获取预置位配置失败：' + d.sres);
											callback(null);
										}
									},
									error:function(d,e){
										alert('获取预置位配置失败：' + d.sres);
										callback(null);
									}
								});	
							}
							function doaddcruisepreset(){
								var allconfigedpreset = $('#u-cruise-preset-list li:not("#l-cruise-addpreset")');
								if(allconfigedpreset.length >= 32){
									alert('一个巡航方案最多配置32个巡航点');
									return;
								}
								
								getallpresets(function(presets){
									if(!presets){
										return;
									}
									var presetlist = $('#s-cruise-preset-list-config');
									presetlist.find('option').remove();
									var length = presets.length;
									for(var i=0;i<length;i++){
										var presetname = presets[i].presetname;
										var presetno = presets[i].point;
										var displayname = presetname;
										if(displayname.length > 6){
											displayname = displayname.substr(0,6)+'...';
										}
										var preset = JSON.stringify(presets[i]);
										var option = "<option presetdata='"+preset+"' value='"+presetno+"' title='"+presetname+"'>"+displayname+"</option>";
										presetlist.append(option);										
									}
								});
								$('#i-cruise-preset-config-id').val('');
								$('#d-cruise-preset-config').show();
							}
							function doeditcruisepreset(lielement){
								var sequencedata = eval('('+lielement.attr('sequencedata')+')');
								getallpresets(function(presets){
									if(!presets){
										return;
									}
									var presetlist = $('#s-cruise-preset-list-config');
									presetlist.find('option').remove();
									var length = presets.length;
									for(var i=0;i<length;i++){
										var presetname = presets[i].presetname;
										var presetno = presets[i].point;
										var displayname = presetname;
										if(displayname.length > 6){
											displayname = displayname.substr(0,6)+'...';
										}
										var preset = JSON.stringify(presets[i]);
										var option = "<option presetdata='"+preset+"' value='"+presetno+"' title='"+presetname+"'>"+displayname+"</option>";
										presetlist.append(option);										
									}
									$('#s-cruise-preset-list-config option[value=\''+sequencedata.point.point+'\']').attr('selected','true');
								});
								
								$('#i-cruise-preset-config-id').val(sequencedata.point.pointid);
								$('#i-cruise-preset-interval').val(lielement.find('#s-cruise-preset-interval').html());
								$('#i-cruise-preset-speed').val(lielement.find('#s-cruise-preset-speed').html());
								$('#d-cruise-preset-config').show();
							}
							function docruisepresetadded(){
								$.validationEngine.defaults.promptPosition = 'topLeft';
								var validateres = $('#f-cruise-preset-config').validationEngine('validate');								
								$.validationEngine.defaults.promptPosition = 'topRight';
								if(!validateres){
									return;
								}
								var presetdata = $('#s-cruise-preset-list-config option:selected').attr('presetdata');
								var interval =  $('#i-cruise-preset-interval').val();
								var speed = $('#i-cruise-preset-speed').val();
								if(!presetdata){
									alert('预置位数据解析失败！');
									return;
								}					
								var presetdatajson = eval('('+presetdata+')');
								var sequencedata = {'cruiseorder':'','pausemins':interval,'speed':speed,'point':presetdatajson};			
								var newpreset = createpreset(sequencedata);
								
								var presetid = $('#i-cruise-preset-config-id').val();
								var nextpreset = null;
								if(presetid != ''){
									var selectedpreset = $('#u-cruise-preset-list li.licruisepresetselected');
									var index = selectedpreset.index();
									selectedpreset.remove();
									nextpreset = $('#u-cruise-preset-list li:eq('+index+')');
								}else{
									nextpreset = $('#u-cruise-preset-list li:last');
								}
								var newpresetelement = $(newpreset);
								newpresetelement.insertBefore(nextpreset);
								licruisepresetselect(newpresetelement);
								
								$('#i-cruise-preset-interval').val('');
								$('#i-cruise-preset-speed').val('');								
								$('#d-cruise-preset-config').hide();
							}							
							function dodeletecruise(){
								if(!confirm('确认删除此巡航方案吗？')){
									return;
								}
								var cruiseselected = $('#s-cruise-config-list option:selected');
								var cruisedata = cruiseselected.attr('cruisedata');
								if(!cruisedata){
									alert('该方案没有配置，不能做删除操作。');
									return;
								}
								var cruisedatajson = eval('('+cruisedata+')');
								var jsondata = {'route':cruisedatajson.route,'ipcid':cruisedatajson.ipcid};
								$.ajax({
									url:'<%=basePath%>videomonitor/deleteCruiseById.action',
									type:'post',
									dataType:'json',
									data:'cruiseSetData='+JSON.stringify(jsondata),
									success:function(d,e){
										var data = eval('('+d+')');
										if(data && data.sres && data.res){
											if(data.res == e && data.sres == 1){
												alert('删除巡航方案成功！');
												
												cruiseselected.text('巡航方案' + cruisedata.route);
												cruiseselected.attr('cruisedata','');
												$('#u-cruise-preset-list li:not("#l-cruise-addpreset")').remove();
												$('#s-cruise-config-list').change();
											}else{
												alert('删除巡航方案失败！');
											}
										}else{
											alert('删除巡航方案失败！');
										}
									},
									error:function(d,e){
										alert('删除巡航方案失败！');
									}
								});
							}
							function docruisepresetup(){
								var selectedli = $('#u-cruise-preset-list li.licruisepresetselected');
								var index = selectedli.index();
								var lastpreset = $('#u-cruise-preset-list li:eq('+(index - 1)+')');
								if(index - 1 < 0){
									return;
								}
								selectedli.remove();
								selectedli.insertBefore(lastpreset);
							}
							function docruisepresetdown(){
								var selectedli = $('#u-cruise-preset-list li.licruisepresetselected');
								var index = selectedli.index();		
								var nextpreset = $('#u-cruise-preset-list li:eq('+(index + 1)+')');		
								if(index + 1 >= $('#u-cruise-preset-list li').length - 1){
									return;
								}				
								selectedli.remove();								
								selectedli.insertAfter(nextpreset);
							}
							function docruisesave(){
								$.validationEngine.defaults.promptPosition = 'topLeft';
								var validateres = $('#f-cruise-config').validationEngine('validate');								
								$.validationEngine.defaults.promptPosition = 'topRight';
								if(!validateres){
									return;
								}
								
								var ipcid = $('#vi_ipcid').html();
								if(!ipcid){
									alert('请选择一个正在播放的视频窗口！');
									return;
								}
								var allpresets = $('#u-cruise-preset-list li:not("#l-cruise-addpreset")');
								var route = $('#i-cruise-route').val();
								var cruisename = $('#i-cruise-name').val();
								var sequesces = new Array();
								allpresets.each(function(i,d){
									var sequencedata = eval('('+$(d).attr('sequencedata')+')');
									if(sequencedata){
										sequencedata.cruiseorder = i + 1;
										sequesces.push(sequencedata);
									}
								});
								//{'route':1,'ipcid':'1','userid':'22','cruisename':'cruise1','sequences':[{'seqid':1,'cruiseid':1,'preset':{'pointid':2,'point':1,'presetname':'大桥入口','ipcid':'ipc001'},'pausemins':3,'speed':2,'cruiseorder':2},{...},...]}
								var jsondata = {'route':route,'ipcid':ipcid,'cruisename':cruisename,'sequences':sequesces};
								$.ajax({
									url:'<%=basePath%>videomonitor/saveCruise.action',
									type:'post',
									dataType:'json',
									data:'cruiseSetData='+JSON.stringify(jsondata),
									success:function(d,e){
										var data = eval('('+d+')');
										if(data && data.sres && data.res){
											var scruisedata = $('#s-cruise-config-list option:selected').attr('cruisedata');
											if(scruisedata == ''){
												if(data.res == e){
													//$('#i-cruise-route').val(data.sres);
													alert('添加巡航方案成功！');
													jsondata.cruiseid = data.sres;
													$('#s-cruise-config-list').append('<option value=\''+jsondata.route+'\' cruisedata=\''+JSON.stringify(jsondata)+'\'>'+jsondata.cruisename+'</option>');
													$('#s-cruise-config-list option:last').attr('selected',true);
												}else{
													alert('添加巡航配置失败：' + data.sres);
												}
											}else{
												if(data.res == e){
													alert('修改巡航方案成功！');
													$('#s-cruise-config-list option:selected').html(jsondata.cruisename);
													$('#s-cruise-config-list option:selected').attr('cruisedata',JSON.stringify(jsondata));
												}else{
													alert('修改巡航配置失败：'+data.sres);
												}
											}
										}else{
											alert('修改巡航方案失败！');
										}
									},
									error:function(d,e){
										var data = eval('('+d+')');
										alert('保存巡航配置失败：' + data.sres);
										if(cruiseid != ''){
											$('#s-cruise-config-list').change();
										}
									}
								});
							}
							function docruisecancel(){
								$('#s-cruise-config-list').change();
							}
							function dodeletecruisepreset(presetelement){
								if(confirm('确认删除此预置位吗？')){
									presetelement.remove();
								}
							}
							function dostartcruise(){
								var selectedcruise =  $('#s-cruise-config-list option:selected');
								if(selectedcruise && selectedcruise.attr('cruisedata')){
									var cruisedata = selectedcruise.attr('cruisedata');
									if(cruisedata.length == 0){
										alert('此巡航方案没有配置！');
										return;
									}
									var cruisedatajson = eval('('+cruisedata+')');
									var ipcid = $('#vi_ipcid').html();
									var route = cruisedatajson.route;
									var ipcid = cruisedatajson.ipcid;
									var jsondata = {'route':route,'ipcid':ipcid};
									$.ajax({
										url:'<%=basePath%>videomonitor/startCruise.action',
										type:'post',
										dataType:'json',
										data:'cruiseSetData='+JSON.stringify(jsondata),
										success:function(d,e){
											var data = eval('('+d+')');
											if(data && data.res){
												if(data.res == e){
													alert('调用巡航方案成功！');
												}else{
													alert('调用巡航方案失败！');
												}
											}else{
												alert('调用巡航方案失败！');
											}
										},
										error:function(d,e){
											alert('调用巡航方案失败！');
										}
									});
								}
							}
							function dostopcruise(){
								var selectedcruise =  $('#s-cruise-config-list option:selected');
								if(selectedcruise && selectedcruise.attr('cruisedata')){
									var cruisedata = selectedcruise.attr('cruisedata');
									if(cruisedata.length == 0){
										alert('此巡航方案没有配置！');
										return;
									}
									var cruisedatajson = eval('('+cruisedata+')');
									var ipcid = $('#vi_ipcid').html();
									var route = cruisedatajson.route;
									var ipcid = cruisedatajson.ipcid;
									var jsondata = {'route':route,'ipcid':ipcid};
									$.ajax({
										url:'<%=basePath%>videomonitor/stopCruise.action',
										type:'post',
										dataType:'json',
										data:'cruiseSetData='+JSON.stringify(jsondata),
										success:function(d,e){
											var data = eval('('+d+')');
											if(data && data.res){
												if(data.res == e){
													alert('停止巡航方案成功！');
												}else{
													alert('停止巡航方案失败！');
												}
											}else{
												alert('停止巡航方案失败！');
											}
										},
										error:function(d,e){
											alert('调用巡航方案失败！');
										}
									});
								}
							}
						</script>
						<div class="dcruiselist" style="">
							<div class="title">
								<span>方案列表</span>
							</div>
							<div>
								<select class="normalselect"
									style="float:left;width:260px;height:32px;"
									id="s-cruise-config-list"></select> <input type="button"
									class="video-btn"
									style="float:left;width:80px;height:30px;margin-top:20px;"
									value="调用" onclick="dostartcruise()" /> <input type="button"
									class="video-btn"
									style="float:left;width:80px;height:30px;margin-top:20px;"
									value="停止" onclick="dostopcruise()" /> <input type="button"
									class="video-btn"
									style="float:left;width:80px;height:30px;margin-top:20px;"
									value="删除" onclick="dodeletecruise()" />
							</div>
						</div>
						<div class="clear"></div>
						<div class="dcruiseconfig" style="margin-top:30px">
							<form id="f-cruise-config">
								<div class="title">
									<span>方案详细信息</span>
								</div>
								<div style="height:40px;">
									<p style="display:block;width:80px;height:30px;margin-top:5px;float:left;">方案名:</p>
									<input id="i-cruise-name" type="text"
										style="float:left;width:180px;height:25px;"
										class="validate[required,maxSize[21]]" /> <input
										id="i-cruise-route" type="text" style="display:none;" />
								</div>
								<div style="height:420px;overflow:auto;text-align:center;">
									<ul id="u-cruise-preset-list">

									</ul>
								</div>
								<div style="float:left;">
									<input type="button" class="video-btn"
										style="float:left;width:50px;height:30px;margin-left:-1px;"
										value="向上" onclick="docruisepresetup()" /> <input
										type="button" class="video-btn"
										style="float:left;width:50px;height:30px;" value="向下"
										onclick="docruisepresetdown()" />
								</div>
								<div style="float:right;">
									<input type="button" class="video-btn"
										style="float:left;width:50px;height:30px;" value="保存"
										onclick="docruisesave()" /> <input type="button"
										class="video-btn" style="float:left;width:50px;height:30px;"
										value="取消" onclick="docruisecancel()" />
								</div>
							</form>
						</div>
						<div class="clear"></div>

						<div id="d-cruise-preset-config"
							style="position:absolute;margin-top:-400px;width:256px;height:240px;background:lightgray;border:2px solid yellow;border-radius:5px;display:none;">
							<form id="f-cruise-preset-config">
								<input id="i-cruise-preset-config-id" style="display:none;" />
								<div
									style="width:100%;height:20px;float:right;margin:5px 5px 0px 0px;">
									<img src="<%=basePath%>resources/images/delete1.png" alt=""
										onclick="$('#d-cruise-preset-config').hide()"
										style="float:right;" />
								</div>
								<div style="float:left;margin:10px 20px 0px 20px;">
									<span
										style="width:96px;height:30px;float:left;line-height:30px;">预置位列表：</span>
									<select id="s-cruise-preset-list-config" class="normalselect"
										style="height:30px;width:120px;"></select>
								</div>
								<div style="float:left;margin:20px 20px 0px 20px;">
									<span
										style="width:96px;height:30px;float:left;line-height:30px;">时间间隔(秒)：</span>
									<input id="i-cruise-preset-interval"
										class="validate[required,custom[integer],max[30],min[1]]"
										style="width:120px;height:30px;" />
								</div>
								<div style="float:left;margin:20px 20px 0px 20px;">
									<span
										style="width:96px;height:30px;float:left;line-height:30px;">速度：</span>
									<input id="i-cruise-preset-speed"
										style="width:120px;height:30px;"
										class="validate[required,custom[integer],max[40],min[1]]" />
								</div>
								<div class="clear"></div>
								<div style="float:right;margin:20px 20px 0px 0px;">
									<input type="button" class="video-btn"
										style="height:30px;width:50px;" value="确定"
										onclick="docruisepresetadded()" /> <input type="button"
										class="video-btn" style="height:30px;width:50px;" value="取消"
										onclick="$('#d-cruise-preset-config').hide()" />
								</div>
								<div class="clear"></div>
							</form>
						</div>
					</div>
					<!-- 轮巡 -->
					<div id="coninner_tab04" style="display:none;height:680px;">
						<script type="text/javascript">
							$(function(){
								loadrounds();
								$('#s-round-config-list').change(function(){
									doroundlistchange();
								});							
							});
							function loadrounds(){
								$('#s-round-config-list').empty();
								$.ajax({
									url:'<%=basePath%>videomonitor/loadRounds.action',
									type:'post',
									dataType:'json',									
									success:function(d,e){
										var rounds = eval('('+d+')');
										if(rounds && rounds.sres && rounds.res == e){
											$(rounds.sres).each(function(i,round){
												$('#s-round-config-list').append('<option value=\''+round.roundid+'\' rounddata=\''+JSON.stringify(round)+'\'>'+round.roundname+'</option>');
											});
										}else{
											alert('获取轮巡方案失败：' + rounds.sres);
										}
										$('#s-round-config-list').change();
									},
									error:function(d,e){
										alert('获取轮巡方案失败：' + d.sres);										
									}
								});	
							}
							function doroundlistchange(){
								var selectedround = $('#s-round-config-list option:selected').attr('rounddata');
								if(!selectedround){
									return;
								}
								var rounddata = eval('('+selectedround+')');
								$('#i-round-name').val(rounddata.roundname);
								$('#i-round-id').val(rounddata.roundid);
								$('#i-round-interval').val(rounddata.pausemins);
								loadroundipcs(rounddata.roundid);
							}
							function liroundpresetselect(lielement){
								var selectedli = $('#u-round-ipc-list li.liroundpresetselected');
								var currentli = lielement;
								if(currentli.index() == selectedli.index()){
									return;
								}
								currentli.css({
										background:'gray',
								});
								currentli.addClass('liroundpresetselected');
								selectedli.css({
										background:'blue',
								});
								selectedli.removeClass('liroundpresetselected');
							}
							function createipc(sequence){
								var ipcid = sequence.ipc.ipcid;										
								var interval = sequence.pausemins;
								var displayname = sequence.ipc.devname;										
								if(displayname.length > 13){
									displayname = displayname.substr(0,13)+'...';
								}
										
								var playurl = '';
								var ipcip = sequence.ipc.ip;
								var ipcport = sequence.ipc.port;
								var username = sequence.ipc.username;
								var password = sequence.ipc.password;
								var nvrip = sequence.ipc.nvr.ip;
								playurl = '\''+ipcip+'\',\''+ipcport+'\',\''+username+'\',\''+password+'\',\''+nvrip+'\'';										
								var vli = '<li style="text-align:left;" onclick="liroundpresetselect($(this))" sequencedata=\''+JSON.stringify(sequence)+'\'><span style="display:block;width:160px;float:left;margin-left:10px;" class="s-presetname" title="'+sequence.ipc.devname+'">'+displayname+'</span><img src="<%=basePath%>resources/images/videomonitor/delete.png" style="margin:8px 5px 0px 0px;width:15px;height:15px;float:right;" onclick="dodeleteroundipc($(this).parent())"/><img src="<%=basePath%>resources/images/videomonitor/play.jpg" style="margin:8px 5px 0px 0px;width:15px;height:15px;float:right;" onclick="doplay('+playurl+')"/></li>';
								return vli;
							}							
							function loadroundipcs(selectedround){
								if(!selectedround){
									return;
								}
								$('#u-round-ipc-list li').remove();
								var rounddata = $('#s-round-config-list option[value="'+selectedround+'"]').attr('rounddata');
								if(rounddata){
									var rounddatajson = eval('('+rounddata+')');
									$(rounddatajson.sequences).each(function(i,sequence){
										var vli = createipc(sequence);
										$('#u-round-ipc-list').append(vli);
									});
								}
								var addpresetlistr = '<li id="l-round-addipc" style="background:green;" onclick="doaddroundpreset()"><img src="<%=basePath%>resources/images/videomonitor/add.png" style="width:20px;height:20px;margin-right:30px;"/><span style="display:inline;line-height:25px;">添加摄像机</span></li>';
								$('#u-round-ipc-list').append(addpresetlistr);
							}
							function doroundpresetup(){
								var selectedli = $('#u-round-ipc-list li.liroundpresetselected');
								var index = selectedli.index();
								var lastpreset = $('#u-round-ipc-list li:eq('+(index - 1)+')');
								if(index - 1 < 0){
									return;
								}
								selectedli.remove();
								selectedli.insertBefore(lastpreset);
							}
							function doroundpresetdown(){
								var selectedli = $('#u-round-ipc-list li.liroundpresetselected');
								var index = selectedli.index();		
								var nextpreset = $('#u-round-ipc-list li:eq('+(index + 1)+')');		
								if(index + 1 >= $('#u-round-ipc-list li').length - 1){
									return;
								}				
								selectedli.remove();								
								selectedli.insertAfter(nextpreset);
							}
							function doaddround(){
								$('#i-round-id').val('');
								$('#i-round-name').val('');
								$('#i-round-interval').val('');
								var addipc = $('#l-round-addipc');
								if(addipc.length == 0){
									var addipclistr = '<li id="l-round-addipc" style="background:green;" onclick="doaddroundpreset()"><img src="<%=basePath%>resources/images/videomonitor/add.png" style="width:20px;height:20px;margin-right:30px;"/><span style="display:inline;line-height:25px;">添加摄像机</span></li>';
									$('#u-round-ipc-list').append(addipclistr);
								}
								
								$('#u-round-ipc-list li:not("#l-round-addipc")').remove();
							}
							function doaddroundpreset(){
								$('#d-round-ipc-config').show();
							}
							function doeditroundpreset(lielement){
								$('#d-round-ipc-config').show();
							}
							function dodeleteround(){
								if(!confirm('确认删除此轮巡方案吗？')){
									return;
								}
								var roundselected = $('#s-round-config-list option:selected');
								var jsondata = {'roundid':roundselected.val()};
								$.ajax({
									url:'<%=basePath%>videomonitor/deleteRoundById.action',
									type:'post',
									dataType:'json',
									data:'roundSetData='+JSON.stringify(jsondata),
									success:function(d,e){
										var data = eval('('+d+')');
										if(data && data.sres && data.res){
											if(data.res == e && data.sres == 1){
												alert('删除轮巡方案成功！');
												
												roundselected.remove();
												$('#i-round-name').val('');
												$('#i-round-id').val('');
												$('#i-round-interval').val('');
												$('#u-round-ipc-list li:not("#l-cruise-addpreset")').remove();
												$('#s-round-config-list').change();
											}else{
												alert('删除巡航方案失败！');
											}
										}else{
											alert('删除巡航方案失败！');
										}
									},
									error:function(d,e){
										alert('删除巡航方案失败！');
									}
								});
							}
							function doroundsave(){
								$.validationEngine.defaults.promptPosition = 'topLeft';
								var validateres = $('#f-round-config').validationEngine('validate');								
								$.validationEngine.defaults.promptPosition = 'topRight';
								if(!validateres){
									return;
								}
							
								var allipcs = $('#u-round-ipc-list li:not("#l-round-addipc")');
								var roundid = $('#i-round-id').val();
								var roundname = $('#i-round-name').val();
								var pausemins = $('#i-round-interval').val();
								var screenmod = 4;
								var sequesces = new Array();
								allipcs.each(function(i,d){
									var sequencedata = eval('('+$(d).attr('sequencedata')+')');
									if(sequencedata){
										sequencedata.seqorder = i;
										sequencedata.roundid = roundid;
										sequesces.push(sequencedata);
									}
								});
								//{'roundid':1,'roundname':'round1','screenmod':4,'pausemins':3,'sequences':[{'rsid':1,'roundid':1,'ipcid':'ipc001','seqorder':2},{...},...]}
								var jsondata = {'roundid':roundid,'roundname':roundname,'screenmod':screenmod,'pausemins':pausemins,'sequences':sequesces};
								$.ajax({
									url:'<%=basePath%>videomonitor/saveRound.action',
									type:'post',
									dataType:'json',
									data:'roundSetData='+JSON.stringify(jsondata),
									success:function(d,e){
										var data = eval('('+d+')');
										if(data && data.sres && data.res){
											if(roundid == ''){
												if(data.res == e){
													$('#i-round-id').val(data.sres);
													alert('添加轮巡方案成功！');
													jsondata.roundid = data.sres;
													$('#s-round-config-list').append('<option value=\''+jsondata.roundid+'\' rounddata=\''+JSON.stringify(jsondata)+'\'>'+jsondata.roundname+'</option>');
													$('#s-round-config-list option:last').attr('selected',true);
												}else{
													alert('添加轮巡配置失败：' + data.sres);
												}
											}else{
												if(data.res == e){
													alert('修改轮巡方案成功！');
													$('#s-round-config-list option:selected').html(jsondata.roundname);
													$('#s-round-config-list option:selected').attr('rounddata',JSON.stringify(jsondata));
												}else{
													alert('修改轮巡配置失败：'+data.sres);
												}
											}
										}else{
											alert('修改轮巡方案失败！');
										}
									},
									error:function(d,e){
										var data = eval('('+d+')');
										alert('保存轮巡配置失败：' + data.sres);
										if(cruiseid != ''){
											$('#s-round-config-list').change();
										}
									}
								});
							}
							function doroundcancel(){
								$('#s-round-config-list').change();
							}
							function dosavetreeselected(){
								var ipcselected = new Array();
								$('.dl-round-tree input.i-round-ipc-check:checked').each(function(i,d){
									var ipcdata = $(this).parent().find('a').attr('ipcdata');
									ipcselected.push(ipcdata);
									$(this).attr('checked',false);
								});
								for(var i=0;i<ipcselected.length;i++){
									var ipcdata = eval('('+ipcselected[i]+')');
									if(!ipcdata){
										continue;
									}
									var sequencedata = {'ipc':ipcdata};
									var vli = createipc(sequencedata);
									$(vli).insertBefore($('#u-round-ipc-list li:last'));
								}
								$('#d-round-ipc-config').hide();
							}
							function docanceltreeselected(){
								$('.dl-round-tree input.i-round-ipc-check:checked').each(function(i,d){
									$(this).attr('checked',false);
								});
								$('#d-round-ipc-config').hide();
							}
							function dodeleteroundipc(ipcelement){
								if(confirm('确认删除此ipc吗？')){
									ipcelement.remove();
								}								
							}
							function dostartround(){
								var selectedround = $('#s-round-config-list option:selected');
								if(selectedround.length > 0){
									var roundjson = {};
									var rounddata = selectedround.attr('rounddata');
									if(rounddata && rounddata.length > 0){
										try{
											rounddatajson = eval('('+rounddata+')');
											var pollInterval = rounddatajson.pausemins;
											roundjson.PlayType = 0;
											roundjson.pollInterval = parseInt(pollInterval) * 1000;
											var pollScheme = new Array();
											var sequences = rounddatajson.sequences;
											for(var i = 0;i<sequences.length;i++){
												var scheme = {};
												var ipc = rounddatajson.sequences[i].ipc;
												var channelIp = ipc.ip;
												var cmdPort = ipc.port;
												var dataPort = ipc.port + 1;
												var devIp = ipc.nvr.ip;
												var index = i;
												var psw = ipc.password;
												var usr = ipc.username;
												scheme.channelIp = channelIp;
												scheme.cmdPort = cmdPort;
												scheme.dataPort = dataPort;
												scheme.devIp = devIp;
												scheme.index =index;
												scheme.psw = psw;
												scheme.usr = usr;
												scheme.playerType = 1;
												pollScheme.push(scheme);
												roundjson.pollScheme = pollScheme;
											}
											
											var res = startCameraPoll(JSON.stringify(roundjson));
											if(res != 0){
												alert('轮巡执行失败！');
											}
										}catch(e){
											alert('轮巡数据解析失败！');
										}
									}else{
										alert('没有获取到轮巡数据！');
									}
								}else{
									alert('请选择轮巡方案');
								}
							}
							function dostopround(){
								stopCameraPoll();
							}
						</script>
						<div class="droundlist" style="">
							<div class="title">
								<span>方案列表</span>
							</div>
							<div>
								<select class="normalselect"
									style="float:left;width:260px;height:32px;"
									id="s-round-config-list"></select> <input type="button"
									class="video-btn"
									style="float:left;width:58px;height:30px;margin-top:20px;"
									value="调用" onclick="dostartround()"/> <input type="button" class="video-btn"
									style="float:left;width:58px;height:30px;margin-top:20px;"
									value="停止" onclick="dostopround()"/> <input type="button" class="video-btn"
									style="float:left;width:58px;height:30px;margin-top:20px;"
									value="添加" onclick="doaddround()" /> <input type="button"
									class="video-btn"
									style="float:left;width:58px;height:30px;margin-top:20px;"
									value="删除" onclick="dodeleteround()" />
							</div>
						</div>
						<div class="clear"></div>
						<div class="dcruiseconfig" style="margin-top:30px">
							<form id="f-round-config">
								<div class="title">
									<span>方案详细信息</span>
								</div>
								<div style="height:40px;">
									<p
										style="display:block;width:90px;height:30px;margin-top:5px;float:left;">方案名:</p>
									<input id="i-round-name" type="text"
										style="float:left;width:170px;height:25px;"
										class="validate[required,maxSize[21]]" /> <input
										id="i-round-id" type="text" style="display:none;" />
								</div>
								<div style="height:40px;">
									<p
										style="display:block;width:90px;height:30px;margin-top:5px;float:left;">轮巡间隔(秒):</p>
									<input id="i-round-interval" type="text"
										style="float:left;width:170px;height:25px;"
										class="validate[required,custom[integer],max[1200]],min[1]" />
								</div>
								<div style="height:380px;overflow:auto;text-align:center;">
									<ul id="u-round-ipc-list">

									</ul>
								</div>
								<div style="float:left;">
									<input type="button" class="video-btn"
										style="float:left;width:50px;height:30px;margin-left:-1px;"
										value="向上" onclick="doroundpresetup()" /> <input
										type="button" class="video-btn"
										style="float:left;width:50px;height:30px;" value="向下"
										onclick="doroundpresetdown()" />
								</div>
								<div style="float:right;">
									<input type="button" class="video-btn"
										style="float:left;width:50px;height:30px;" value="保存"
										onclick="doroundsave()" /> <input type="button"
										class="video-btn" style="float:left;width:50px;height:30px;"
										value="取消" onclick="doroundcancel()" />
								</div>
							</form>
						</div>
						<div class="clear"></div>

						<div id="d-round-ipc-config"
							style="position:absolute;margin-top:-480px;width:256px;height:450px;background:lightgray;border:2px solid yellow;border-radius:5px;display:none;">
							<div
								style="width:100%;height:15px;float:right;margin:5px 5px 0px 0px;">
								<img src="<%=basePath%>resources/images/delete1.png" alt=""
									onclick="docanceltreeselected()" style="float:right;" />
							</div>
							<div style="float:left;margin:5px 10px 0px 10px;">
								<span
									style="width:100px;height:25px;float:left;line-height:25px;">摄像机列表：</span>
							</div>
							<div
								style="float:left;margin:5px 10px 0px 10px;height:340px;width:236px;">
								<div style="height:340px;overflow:auto;">
									<dl class="dl-round-tree">
										<dd></dd>
									</dl>
								</div>
							</div>
							<div class="clear"></div>
							<div style="float:right;margin:10px 10px 0px 0px;">
								<input type="button" class="video-btn"
									style="height:30px;width:50px;" value="确定"
									onclick="dosavetreeselected()" /> <input type="button"
									class="video-btn" style="height:30px;width:50px;" value="取消"
									onclick="docanceltreeselected()" />
							</div>
							<div class="clear"></div>
						</div>
					</div>
					<div class="tab-hd-inner">
						<li class="active" id="inner_tab01"
							onmouseup="set('inner_tab0',1,4)">画面</li>
						<li id="inner_tab02" onmouseup="set('inner_tab0',2,4)">预置位</li>
						<li id="inner_tab03" onmouseup="set('inner_tab0',3,4)">巡航</li>
						<li id="inner_tab04" onmouseup="set('inner_tab0',4,4)">轮巡</li>
					</div>
					<div class="clear"></div>
				</div>
				<!--tab1 end-->

				<!--tab2-->
				<div id="contab02" style="display:block;height:700px;">
					<span>设备列表： </span>
					<div style="height:690px;overflow:auto;">
						<style type="text/css">
.p {
	margin-left: 10px;
}

.ps {
	margin-left: 10px;
	display: none;
}

.pss {
	margin-left: 10px;
	display: block;
}

.t {
	cursor: pointer;
	background: url(<%=basePath%>resources/images/ico080426_open.gif) 8px
		no-repeat;
	line-height: 40px;
	padding-left: 40px;
	height: 40px;
}

.f {
	cursor: pointer;
	background: url(<%=basePath%>resources/images/ico080426_close.gif) 8px
		no-repeat;
	line-height: 20px;
	padding-left: 40px;
	height: 20px;
}

.b {
	cursor: pointer;
	background: url(<%=basePath%>resources/images/ico080426_close.gif) 8px
		no-repeat;
	line-height: 20px;
	padding-left: 40px;
	height: 20px;
}

.fblod {
	font-weight: bold;
}

.padtb8 {
	padding: 8px 0;
}

.fontred {
	color: #f00;
}
</style>
						<dl class="leftmenusjjk">
							<dd></dd>


						</dl>
						<script language="javascript" type="text/javascript">
						function w(vd,p)
{
  var ob=document.getElementById(vd);
  if(ob.style.display=="block" || ob.style.display=="")
  {
     ob.style.display="none";
     var ob2=document.getElementById(p);
     ob2.style.backgroundImage="url(<%=basePath%>resources/images/ico080426_open.gif)";
  }
  else
  {
    ob.style.display="block";
    var ob2=document.getElementById(p);
    ob2.style.backgroundImage="url(<%=basePath%>resources/images/ico080426_close.gif)";
  }
}
function k(vd,p)
{
  var ob=document.getElementById(vd);  
  if(ob.style.display=="block")
  {
     ob.style.display="none";
     var ob2=document.getElementById(p);
     ob2.style.backgroundImage="url(<%=basePath%>resources/images/ico080426_open.gif)";
  }
  else
  {
    ob.style.display="block";
    var ob2=document.getElementById(p);
    ob2.style.backgroundImage="url(<%=basePath%>resources/images/ico080426_close.gif)";
  }
}
					</script>
					</div>
				</div>
				<!--tab2 end-->
			</div>






		</div>

	</div>
	<div style="display:none;">
		<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
	</div>
</body>

</html>
