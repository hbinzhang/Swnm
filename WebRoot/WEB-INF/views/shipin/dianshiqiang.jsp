<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../include.inc.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息管理系统界面</title>

<link href="<%=basePath%>resources/css/style.css"
	rel="stylesheet"></link>
<link href="<%=basePath%>resources/css/style_shipin.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/tabs.js"></script>
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="icon" type="image/x-icon" />
<link href="<c:url value='/resources/images/favicon.ico'></c:url>"
	rel="shortcut icon" type="image/x-icon" />
<link href="<%=basePath%>resources/css/common.css"
	rel="stylesheet"></link>
<link href="<%=basePath%>resources/css/style.css"
	rel="stylesheet"></link>
<link
	href="<%=basePath%>resources/css/popModal.min.css"
	rel="stylesheet"></link>
<script
	src="<%=basePath%>resources/js/jquery-1.9.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>resources/js/Calendar3.js" type="text/javascript"></script>
<script
	src="<%=basePath%>resources/js/jquery.form.js"
	type="text/javascript"></script>
<script src="<%=basePath%>resources/js/videomonitor.js" type="text/javascript"></script>
<script language="javascript">
$(function(){	
	//ajax请求不使用缓存
	$.ajaxSetup({ cache: false }); 
	//setWindowMode(4);
	//导航切换
	$(".imglist li").click(function(){
		$(".imglist li.selected").removeClass("selected");
		$(this).addClass("selected");
	});
	$('.leftmenu dd').empty();
	$('.leftmenu dd').append('<span>&nbsp;&nbsp;设备列表：</span>');
	
	$.ajax({
		url:"<%=basePath%>videomonitor/loadDecoderAjax.action",
			data : '',
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
			data : 'ajaxIpcSearchCond=test',
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
						$('#fgc91_'+ipcs[i].managementagency).append(
								"<div class='b' onmouseover='$(this)[0].focus();'><a ondblclick=\"doplay('"
										+ ipcs[i].ip + "','" + ipcs[i].port
										+ "','" + ipcs[i].username + "','"
										+ ipcs[i].password + "','"
										+ (ipcs[i].nvr ? ipcs[i].nvr.ip : '') +"')\">"
										+ ipcs[i].devname + "</a><div class='clear'></div></div>");
					}
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
<script type="text/javascript">

$(function(){

function tabs(tabTit,on,tabCon){

$(tabCon).each(function(){

$(this).children().eq(0).show();

});

$(tabTit).each(function(){

$(this).children().eq(0).addClass(on);

});

$(tabTit).children().hover(function(){

$(this).addClass(on).siblings().removeClass(on);

var index = $(tabTit).children().index(this);

$(tabCon).children().eq(index).show().siblings().hide();

});

}

tabs(".tab-hd","active",".tab-bd");

});

 

</script>

<style type="text/css">
ul,li{ list-style:none;}

.tab{margin:0 auto; height:160px;}

.tab-hd { height:29px; line-height:29px;font-size:14px; color:#010101; border-bottom:#a1bccb 1px solid; margin-bottom:5px; font-weight:bold;}

.tab-hd li{font-weight:bold;font-size:14px; border-bottom:solid 3px #66c9f3;float:left;padding:0 5px; height:27px; line-height:27px;}

.tab-hd li.active{ background:url(<%=basePath%>resources/images/videomonitor/itabbg1.png) repeat-x;}

.tab-bd div{ }

</style>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})	
</script>
<style>
*{ margin:0; padding:0; list-style:none;}
.lanrenzhijia{ width:355px; height:20px; line-height:20px; margin-top:27px; z-index:999; float:right;}
.lanrenzhijia li a{ color:#000; text-decoration:none; display:block; float:left; height:20px; line-height:20px; padding:0px 10px; font-size:12px;background:#d7dadf;border-radius: 8px 8px 0px 0px; margin-right:1px;}
.lanrenzhijia li a:hover{ background:#f2eeee;}
.lanrenzhijia li{float:left;position:relative; height:20px; line-height:20px;}
.lanrenzhijia li .second{position:absolute;left:0;display:none;}
.lanrenzhijia li .second a{border-radius:0px;}
</style>

</head>
<body >
<c:import url="../layout/header.jsp?navLink=shishijiankong" charEncoding="UTF-8"></c:import>
<div style="width:100%;height:890px;position:absolute;">
	<!--—————left—————-->  
<div style="float:left; height:100%;width:14%;"> 
<style>
.p{margin-left:10px;}
.ps{margin-left:10px;display:none;}
.pss{margin-left:10px;display:block;}
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
.fblod{font-weight:bold;}
.padtb8{padding:8px 0;}
.fontred{color:#f00;} 

.scbtn{width:85px;height:35px; background:url(<%=basePath %>resources/images/videomonitor/btnbg.png) no-repeat center; font-size:14px;font-weight:bold;color:#fff; cursor:pointer;border-radius:3px; behavior:url(js/pie.htc);}
/*jiangeshijian*/
.jiangeshijian{ height:42px; margin-top:15px;}
.jiangeshijian li{float:left; margin-right:15px;}
.jiangeshijian li label{padding-right:10px; float:left; line-height:32px; font-size:15px;}
.jiangeshijian li label b{ margin-right:5px;}
.jiangeshijian .scinput{width:150px; height:32px; line-height:32px; border-top:solid 1px #a7b5bc; border-left:solid 1px #a7b5bc; border-right:solid 1px #ced9df; border-bottom:solid 1px #ced9df; background:url(<%=basePath %>resources/images/videomonitor/inputbg.gif) repeat-x; text-indent:10px;}
</style>
<div style="height:100%;overflow:auto;">
<dl class="leftmenu">
		<dd>
		<span>&nbsp;&nbsp;设备列表：</span>
		<div id="sgc" class="t fblod title" onclick="w('gc')">
		<a href="#">河北分公司</a>
		</div>
		<ul id="gc" class="ps menuson" style="display: block;">
		<li id="sfgc91" class="f" onclick="k('fgc91')" style="display: block;">
		<a href="" >易县管理处</a>
		</li>
		<li id="fgc91" class="ps" style="display: block;">
		<div class="b">
		<input type="radio"/><a href="">01摄像头</a>
		</div>
		<div class="b">
		<a href="">01摄像头</a>
		</div>
		<div class="b">
		<a href="">01摄像头</a>
		</div>
		<div class="b">
		<a href="">01摄像头</a>
		</div>
		<div class="b">
		<a href="">01摄像头</a>
		</div>
		</li>
		</ul>
		
		</dd>
	  
    
    </dl>
</div>

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
	<!--—————right—————-->  
<div style="float:left; height:100%;width:86%;">
    <div class="rightinfo">
    
   		<div class="right_ssjk">
        	<div class="right_ssjk_l">
            	
           	  <div class="ss_video">
			  	<ul>
			  	<div style="height:45px;">
			  	</div>
            	<object id="plugin-videowall" type="application/x-ptrender"
							codebase="<%=basePath %>resources/plugins/videomonitor/NSSPluginSetup.exe#version=1"
								style="width:1600px;height:832px">
<!-- 								<param name="onload" value="pluginLoaded" /> -->
							</object>
				</ul>
				<div class="clear"></div>
			
           	  </div>
			
            <div class="clear"></div>
        	<!--ss_video end-->
            </div>
        	
            
        </div>
       
    
   </div> 
    
    
    </div>
    
		<div style="display:none;">
			<c:import url="../layout/footer.jsp" charEncoding="UTF-8"></c:import>
		</div>
</div>
</body>

</html>

