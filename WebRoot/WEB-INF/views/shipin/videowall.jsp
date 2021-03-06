<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="../include.inc.jsp"%>
<%@ page import="com.entity.authmgt.Session" %>
<%@ page import="com.util.alarmmgt.AlarmUtil" %>
<%@ page import="net.sf.json.*" %>
<%
	Session mySession = AlarmUtil.getLoginSession();
	String userId = mySession.getId();
    Cookie[] cookies = request.getCookies();  
 	String[] urls = new String[6];
	if (cookies != null) {  
	   	try{
	   		for (Cookie c : cookies) {
		   		String name = c.getName();
		   		 if(name.equals("videowall-" + userId)){
		   		 	String cookieValue = c.getValue();
		   		 	JSONObject joPlayValue = JSONObject.fromObject(cookieValue);
		   		 	JSONArray jaPlayUrls = joPlayValue.getJSONArray("urls");
		   		 	if(jaPlayUrls == null){
		   		 		continue;
		   		 	}
		   		 	for(Object jo : jaPlayUrls.toArray()){
		   		 		JSONObject joUrl = (JSONObject)jo;
		   		 		int index = joUrl.getInt("index");
		   		 		if(index < 6){
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
	/* var sv = '${ pluginVersion }';
	if(hasNewVersion(sv)){
		showUpdateAlert(sv);
	} */
	setWindowMode(4);
	//$('.right_ssjk_l').css('width','1040px');
	//$('.ssjk_window').css('width','1040px')
	//$('.ss_video').css('width','1030px');
	//$('#plugin0').css('width','1530px');
	
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
					setVideoWallRenderInfo(JSON.stringify(surl),<%=i%>);
				}
			<%
				i++;
			}
		}%>
		
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected");
		$(this).addClass("selected");
	});
	
	$('.leftmenu dd').empty();
	//$('.leftmenu dd').append('<span>&nbsp;&nbsp;设备列表：</span>');
	
	$.ajax({
		url:"<%=basePath%>videomonitor/loadDecoderAjax.action",
			data : 'pageSize=100000',
			type : 'get',
			dataType : 'json',
			success : function(d, e) {
				var data = eval(d);
				for(var i in data){
					decoders.push(data[i]);
				}
			},
			error:function(d,e){
				alert(e);
			}
	});
	
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
						$('.leftmenu dd').append(divbranch);
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
										+ (ipcs[i].nvr ? ipcs[i].nvr.ip : '') +"')\" title='"+ipcs[i].devname+"'>"
										+ displayName + "</a><div class='clear'></div></div>");
					}
				}
				if(branchs && branchs.length == 1){
					$('.leftmenu dd div:eq(0)').each(function(i,d){
						d.click();
					});
				}
			},
			error : function(d, e) {
				alert(e);
			}
		});
});

function doplay(ipcip, ipcport, ipcusername, ipcpassword, nvrip) {
		var index = getVideoWallSelectedWindow();
		var decoder = getDecoder(index);
		if(!decoder){
			alert('窗口'+(index + 1)+'解码器没有配置！');
			return;
		}
		var url = '{' +
                '"decoderIp": "'+decoder.ip+'",' +
                '"decoderCmdPort": '+decoder.port+',' +
                '"decoderDataPort": '+(parseInt(decoder.port) + 1)+',' +
                '"decoderUsr": "'+decoder.username+'",' +
                '"decoderPsw": "'+decoder.password+'",' +
                '"ipCamera": {' +
                    '"devIp": "'+ipcip+'",' +
                    '"cmdPort": '+ipcport+',' +
                    '"dataPort": '+(ipcport + 1)+',' +
                    '"usr": "'+ipcusername+'",' +
                    '"psw": "'+ipcpassword +'"'+
                '}' +
            	'}';
        //alert(url);
        var urljson = eval('('+url+')');
		dovideowallplay(url,index);
		
		$.ajax({
			url:'<%=basePath%>videomonitor/setVideoWallCookie.action',
			data:'playCookieContent=' + '{\"index\":'+index+',\"url\":'+url+'}',
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
	
	var decoders = new Array();
	function getDecoder(index){
		var res = null;
		for(var i in decoders){
			var decoder = decoders[i];
			if(decoder && decoder.ordernumber && decoder.ordernumber != ''){
				var ordernumber = parseInt(decoder.ordernumber);
				if((ordernumber - 1)==index){
					res = decoder;
					break;
				}
			}
		}
		return res;
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
							
							
						</div>
						<ul>
							<!-- <li><img src="<%=basePath %>resources/images/video_img01.jpg" /></li>
                <li><img src="<%=basePath %>resources/images/video_img02.jpg" /></li>
                <li><img src="<%=basePath %>resources/images/video_img03.jpg" /></li>
                <li><img src="<%=basePath %>resources/images/video_img04.jpg" /></li> -->
							<object id="plugin-videowall" type="application/x-ptrender"
							codebase="<%=basePath %>resources/plugins/videomonitor/NSSPluginSetup.exe"
								style="width:1600px;height:875px">
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
				
				
				
				<div class="clear"></div>
				<!--ptzcontrol end-->
				<!--ptzcontrol2 end-->
				<!--tab title-->
				
				<style >
/*jiangeshijian*/
.jiangeshijian li{float:left; margin-right:10px;}
.jiangeshijian li label{padding-right:10px; float:left; line-height:30px; font-size:15px;}
.jiangeshijian li label b{ margin-right:5px;}
.scbtn{width:80px;height:35px;float:left; margin:auto auto; background:url(<%=basePath %>resources/images/videomonitor/btnbg.png) no-repeat center; font-size:14px;font-weight:bold;color:#fff; cursor:pointer;border-radius:3px; behavior:url(js/pie.htc);}
.jiangeshijian .scinput{width:150px; height:32px; line-height:32px; border-top:solid 1px #a7b5bc; border-left:solid 1px #a7b5bc; border-right:solid 1px #ced9df; border-bottom:solid 1px #ced9df; background:url(<%=basePath %>resources/images/videomonitor/inputbg.gif) repeat-x; text-indent:10px;}		

ul,li {
	list-style: none;
}

.tab {
	margin: 0 auto;
}


</style>

	
				<!--tab2-->
				
					<span>&nbsp;&nbsp;设备列表：</span>
				<div id="contab02" style="display:block;height:850px;overflow:auto;">
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
					<dl class="leftmenu">
						<dd>
							
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
				<!--tab2 end-->
			</div>






		</div>
		
	</div>
		<div style="display:none;">
			<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		</div>
</body>

</html>
