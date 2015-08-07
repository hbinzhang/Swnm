<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="com.entity.authmgt.Session" %>
<%@ page import="com.util.alarmmgt.AlarmUtil" %>
<%@ page import="net.sf.json.*" %>
<%
	/* Cookie[] cookies = request.getCookies();  
 	int windowmode=0;  
 	String[] urls = null; 
	if (cookies != null) {  
	   	for (Cookie c : cookies) {  
	 
	       if ("windowmode".equals(c.getName())) { 
			   String value = c.getValue();
			   if(value == null || value.equals("")){
			   		break;
			   }
	           windowmode = Integer.parseInt(value);
	           urls = new String[windowmode];
	           break;  
	       }  
	    }
	   	for (Cookie c : cookies) {
	   		String name = c.getName();
	   		 if(name.substring(0,4).equals("url-")){
	   		 	String index = name.substring(4,5);
	   		 	if(index != null && !index.equals("")){
	   		 		int iindex = Integer.parseInt(index);
	   		 		if(iindex < urls.length){
	   		 			urls[iindex] = c.getValue();
	   		 		}
	   		 	}
	   		 }
	   	}
    } */ 
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
<link href="${pageContext.request.contextPath}/resources/css/jquery-ui.min.css" rel="stylesheet"/>
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
	
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/videomonitor.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	//ajax请求不使用缓存
	$.ajaxSetup({ cache: false }); 
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
		<% 
		if(urls != null){
			int i = 0;
			for(String url : urls){
				if(url == null || url.equals("")){
					i++;
					continue;
				}
			%>
				var surl = <%=url%>;
				if(surl){
					doipcplay(JSON.stringify(surl),<%=i%>);
				}
			<%
				i++;
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
						}
					}
				}
				if (ipcs) {
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
										+ (ipcs[i].nvr ? ipcs[i].nvr.ip : '') +"')\" title="+ipcs[i].devname+">"
										+ displayName + "</a><div class='clear'></div></div>");
					}
				}
				if(branchs && branchs.length == 1){
					$('.leftmenusjjk dd div:eq(0)').each(function(i,d){
						d.click();
					});
				}
			},
			error : function(d, e) {
				alert(e);
			}
		});
		
				$("#slider-bright").slider({
			      min: 0,
			      max: 100,
			      value: 50,
			      slide: function( event, ui ) {
			        $("#amount-bright").html(ui.value);
			      }
			    });
			    
			    $("#slider-contrast").slider({
			      min: 0,
			      max: 100,
			      value: 50,
			      slide: function( event, ui ) {
			        $("#amount-contrast").html(ui.value);
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
		doipcplay(url,index);
		
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
	<c:import url="../layout/header.jsp?navLink=shishijiankong" charEncoding="UTF-8"></c:import>

	<!--—————right—————-->
	<div style="float:left; width:100%;">
		<div class="rightinfo">

			<div class="right_ssjk">
				<div class="right_ssjk_l">

					<div class="ss_video">
						<div class="ssjk_window">
							
							<div style="height:42px;float:left;">
								<ul class="window_control">
									<li>
										<img src="<%=basePath %>resources/images/videomonitor/win-1.png" usemap="#win-1.png"/>
										<map name="win-1.png" id="win-1.png">
											<area shape="rect" coords="0,0,65,31" href="javascript:setWindowMode(1)"/>
										</map>
									</li>
									<li>
										<img src="<%=basePath %>resources/images/videomonitor/win-4.png" usemap="#win-4.png"/>
										<map name="win-4.png" id="win-4.png">
											<area shape="rect" coords="0,0,65,31" href="javascript:setWindowMode(4)"/>
										</map>
									</li>
									<li>
										<img src="<%=basePath %>resources/images/videomonitor/win-9.png" usemap="#win-9.png"/>
										<map name="win-9.png" id="win-9.png">
											<area shape="rect" coords="0,0,65,31" href="javascript:setWindowMode(9)"/>
										</map>
									</li>
									<li>
										<img src="<%=basePath %>resources/images/videomonitor/win-16.png" usemap="#win-16.png"/>
										<map name="win-16.png" id="win-16.png">
											<area shape="rect" coords="0,0,65,31" href="javascript:setWindowMode(16)"/>
										</map>
									</li>
									<li>
										<img src="<%=basePath %>resources/images/videomonitor/win-capture.png" usemap="#win-capture.png"/>
										<map name="win-capture.png" id="win-capture.png">
											<area shape="rect" coords="0,0,65,31" href="javascript:playCaptureLocal()"/>
										</map>
									</li>
								</ul>
							</div>
							<div style="float:right;margin:3px auto;">								
								<img src="<%=basePath %>resources/images/videomonitor/win-full.png" usemap="#full.png" border="0" alt="全屏"/>
								<map name="full.png" id="full.png">
									<area shape="rect" coords="0,0,65,31" href="javascript:fullScreen()"/>
								</map>
								<img src="<%=basePath %>resources/images/videomonitor/shangqiang.png" usemap="#shangqiang.png" border="0" alt="打开电视墙"/>
								<map name="shangqiang.png" id="shangqiang.png">
									<area shape="rect" coords="0,0,67,33" href="<%=basePath%>videomonitor/loadVideoWall.action"/>
								</map>
							</div>
						</div>
						<ul>
							<!-- <li><img src="<%=basePath %>resources/images/video_img01.jpg" /></li>
                <li><img src="<%=basePath %>resources/images/video_img02.jpg" /></li>
                <li><img src="<%=basePath %>resources/images/video_img03.jpg" /></li>
                <li><img src="<%=basePath %>resources/images/video_img04.jpg" /></li> -->
							<object id="plugin0" type="application/x-ptplayerplugin" classid="clsid:4e29a691-8bf0-547a-9d91-a11e23b5a090" 
							codebase="<%=basePath %>resources/plugins/videomonitor/NSSPluginSetup.exe"
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

					<%-- <img src="<%=basePath %>resources/images/videomonitor/control.jpg" usemap="#Map2"
						border="0" style="width:250px;"/>
					<map name="Map2" id="Map2">
						<area shape="circle" coords="175,32,14" onmousedown="javascript:a1d()" onmouseup="javascript:a1u()"/>
						<area shape="circle" coords="222,34,13" onmousedown="javascript:a2d()" onmouseup="javascript:a2u()"/>
						<area shape="circle" coords="186,76,14" onmousedown="javascript:a3d()" onmouseup="javascript:a3u()"/>
						<area shape="circle" coords="235,76,14" onmousedown="javascript:a4d()" onmouseup="javascript:a4u()"/>
						<area shape="circle" coords="174,118,14" onmousedown="javascript:a5d()" onmouseup="javascript:a5u()"/>
						<area shape="circle" coords="224,119,14" onmousedown="javascript:a6d()" onmouseup="javascript:a6u()"/>
						<area shape="rect" coords="79,22,96,37" onmousedown="javascript:a7d()" onmouseup="javascript:a7u()"/>
						<area shape="rect" coords="114,34,130,54" onmousedown="javascript:a8d()" onmouseup="javascript:a8u()"/>
						<area shape="rect" coords="128,66,143,85" onmousedown="javascript:a9d()" onmouseup="javascript:a9u()"/>
						<area shape="rect" coords="112,97,128,114" onmousedown="javascript:a10d()" onmouseup="javascript:a10u()"/>
						<area shape="rect" coords="85,109,104,128" onmousedown="javascript:a11d()" onmouseup="javascript:a11u()"/>
						<area shape="rect" coords="51,94,74,114" onmousedown="javascript:a12d()" onmouseup="javascript:a12u()"/>
						<area shape="rect" coords="46,68,57,81" onmousedown="javascript:a13d()" onmouseup="javascript:a13u()"/>
						<area shape="rect" coords="57,33,74,51" onmousedown="javascript:a14d()" onmouseup="javascript:a14u()"/>
						<area shape="circle" coords="90,73,25" onmousedown="javascript:a15d()" onmouseup="javascript:a15u()"/>
					</map> --%>
					<div style="width:100%;height:100%;float:left;">
						<div style="width:50%;height:100%;float:left;margin:5px 0px;">
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
						<div style="width:50%;height:100%;float:left;margin:5px 0px;">
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
				<!--ptzcontrol end-->
				<!--ptzcontrol2 end-->
				<!--tab title-->
				
				<style >
ul,li {
	list-style: none;
}

.tab {
	margin: 0 auto;
}


</style>
				<div class="tab-hd">
					<li class="active" id="tab02" onmouseup="set('tab0',2,2)" >设备列表</li>
					<li id="tab01" onmouseup="set('tab0',1,2)">参数设置</li>
				</div>
				<!--tab1-->
				<div id="contab01" style="display:none;">
					<div class="lunxun">
						<div class="title">
							<span>轮巡</span>
						</div>
						<div class="sub_tit">画面切换</div>
						<form action="" method="get">
							<div class="qiehuan_sj">
								选择方案&nbsp;&nbsp;&nbsp;<select name="">
									<option value="1111111111">方案1</option>
									<option value="2222222222">方案2</option>
								</select>
							</div>
						</form>
						<div class="stop_start">
							<img src="<%=basePath %>resources/images/videomonitor/003_lightmenu.png" border="0"
								usemap="#Map2" />
							<map name="Map2" id="Map2">
								<area shape="circle" coords="19,25,19" href="#" />
								<area shape="circle" coords="70,23,19" href="#" />
							</map>
						</div>
						<div class="sub_tit">预置位切换</div>
						<!--div class="stop_start"><img src="<%=basePath %>resources/images/003_lightmenu.png" border="0" usemap="#Map2" />
											<map name="Map2" id="Map2">
											  <area shape="circle" coords="19,25,19" href="#" />
											  <area shape="circle" coords="70,23,19" href="#" />
											</map>
					</div>
				-->
						<label style="width:100px;">&nbsp;</label> <label
							style="width:50px;">&nbsp;</label> 
							<select class="select1" id="presetlist" style="width:80px;">
							<% for(int i=1;i <= 16;i++) {%>
								<option><%=i %></option>
							<% } %>
						</select>
						<input name="" type="button"
							style="background:#046dae;color:white;width:50px; height:25px;" value="调用" onclick="doPreset($('#presetlist').val())"/> 						

					</div>
					<div class="clear"></div>
					<!--lunxun end-->

					<div class="fanganxinxi">
						<div class="title">
							<span>方案信息</span>
						</div>
						<div class="sub_tit">轮巡轨迹：1-3-4-5</div>
						<div class="sub_tit">运行状态：正常</div>
					</div>
					<div class="clear"></div>
					<!--fanganxinxi end-->

					<div class="huamianshezhi">
						<div class="title">
							<span>画面设置</span>
						</div>
						<div class="gundongtao">
							<p>亮度:<span style="display:inline;" id="amount-bright">50</span></p>
							<%-- <img src="<%=basePath %>resources/images/videomonitor/tiao.png" border="0"
								usemap="#Map3" />
							<map name="Map3" id="Map3">
								<area shape="rect" coords="23,3,34,25" href="#" />
							</map> --%>
							<div id="slider-bright" style="color:black;background:lightblue;margin-top:10px;"></div>
						</div>
						<div class="gundongtao">
							<p>对比度:<span style="display:inline;" id="amount-contrast">50</span></p>
							<%-- <img src="<%=basePath %>resources/images/videomonitor/tiao.png" border="0"
								usemap="#Map4" />
							<map name="Map4" id="Map4">
								<area shape="rect" coords="21,6,31,26" href="#" />
							</map> --%>
							<div id="slider-contrast" style="color:black;background:lightblue;margin-top:10px;"></div>
						</div>
					</div>
					<div class="clear"></div>
					<!--huamianshezhi end-->
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
	background: url(<%=basePath %>resources/images/ico080426_open.gif) 8px
		no-repeat;
	line-height: 40px;
	padding-left: 40px;
	height: 40px;
}

.f {
	cursor: pointer;
	background: url(<%=basePath %>resources/images/ico080426_close.gif) 8px no-repeat;
	line-height: 20px;
	padding-left: 40px;
	height: 20px;
}

.b {
	cursor: pointer;
	background: url(<%=basePath %>resources/images/ico080426_close.gif) 8px
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
						<dd>
							<!--		<div id="sgc" class="t fblod title" onclick="w('gc')">
		<a href="#">北京分公司</a>
		</div>-->
							
						</dd>


					</dl>
					<script language="javascript" type="text/javascript">
						function w(vd,p)
{
  var ob=document.getElementById(vd);
  if(ob.style.display=="block" || ob.style.display=="")
  {
     ob.style.display="none";
     var ob2=document.getElementById(p);
     ob2.style.backgroundImage="url(<%=basePath %>resources/images/ico080426_open.gif)";
  }
  else
  {
    ob.style.display="block";
    var ob2=document.getElementById(p);
    ob2.style.backgroundImage="url(<%=basePath %>resources/images/ico080426_close.gif)";
  }
}
function k(vd,p)
{
  var ob=document.getElementById(vd);  
  if(ob.style.display=="block")
  {
     ob.style.display="none";
     var ob2=document.getElementById(p);
     ob2.style.backgroundImage="url(<%=basePath %>resources/images/ico080426_open.gif)";
  }
  else
  {
    ob.style.display="block";
    var ob2=document.getElementById(p);
    ob2.style.backgroundImage="url(<%=basePath %>resources/images/ico080426_close.gif)";
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
