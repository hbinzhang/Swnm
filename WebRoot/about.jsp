<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/potevio/poteviofn" prefix="poteviofn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>关于</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		window.onload = function(){
			var versionelement = document.getElementById('sversion');
			var plugin = document.getElementById('plugin0');
			if(plugin && plugin.valid){
				versionelement.innerHTML = plugin.version;
			}
		};
	</script>
  </head>
  
  <body style="margin:0px;padding:0px;">
    <div style="width:100%;height:100%;background:#046DAE;">
  		<div style="position:absolute;left:50%;top:50%;margin:-80px -150px">
	  		<div style="display:none;">
	  			<object id="plugin0" type="application/x-ptplayerplugin" classid="clsid:4e29a691-8bf0-547a-9d91-a11e23b5a090" 
									style="width:400px;height:300px">
									<param name="onload" value="pluginLoaded" />
								</object>
	  		</div>
	  		<div >
	  			<img alt="" style="display:block;" src="<%=basePath %>resources/images/header/logo.png"><br/>
	  			<span style="color:yellow;margin:5px;display:block;">应用版本号：<span style="font-size:24px;color:red;">${poteviofn:systemConfig('current_version') }</span></span><br/>
	  			<span style="color:yellow;display:block;">视频插件版本号：<span style="font-size:24px;color:red;" id="sversion"></span></span>
	  		</div>
	  	</div>	
  	</div>
  </body>
</html>
