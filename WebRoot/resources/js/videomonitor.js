	var TYPE_HKPLAYER = 1;
  	var TYPE_DHPLAYER = 2; 
  	 
  	var windowmode = 4;
  	
  	var plugin = function(){
  		return $('#plugin0')[0];
  	};
  	
  	var plugin_videowall = function(){
  		return $('#plugin-videowall')[0];
  	};
  	
  	function setPreviewCallback(previewCallback){
  		if(previewCallback){
  	  		addVideoEvent(plugin(), 'optionsCallback', previewCallback);
  		}
  	}
  	
  	function dovideowallplay(url,panelindex){
  		if(plugin_videowall().valid){
  			plugin_videowall().PlayOpen(url,panelindex);
  		}else {
			alert("Plugin is not working :(");
		}
  	}
  	
  	function getVideoWallSelectedWindow(){
  		if(plugin_videowall().valid){
  			return plugin_videowall().GetSelectedIndex();
  		}else {
			alert("Plugin is not working :(");
		}
  	}
  	
  	function setVideoWallRenderInfo(url,panelindex){
  		if(plugin_videowall().valid){
  			plugin_videowall().SetRenderInfo(url,panelindex);
  		}else {
			alert("Plugin is not working :(");
		}
  	}
  	
  	function getPluginVersion(){
  		var version = '';
  		if(plugin() && plugin().valid){
  			version = plugin().version;
  		}else if(plugin_videowall()){
  			version = plugin_videowall().version;
  		}			
  		return version;
  	}
  	
  	function hasNewVersion(serverVersion){
  		return serverVersion != getPluginVersion();
  	}
  	
  	function showUpdateAlert(serverVersion){
  		var location = (window.location+'').split('/'); 
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
  		var alertDiv = "<div class='updateDiv' style='width:100%;height:100%;background:transparent;z-index:100000;position:absolute;'><iframe frameborder=0 scrolling=no style='background-color:transparent; position: absolute; z-index: -1; width: 100%; height: 100%; top: 0;left:0;'></iframe><div style='left:50%;top:50%;margin:-150px -200px;position:absolute;width:400px;height:300px;background:gray;'><img src='"+basePath+"/resources/images/videomonitor/close.png' style='Cursor:pointer;width:30px;height:30px;float:right;' onclick='$(this).parent().parent().hide()'/><span style='margin:50px;color:yellow;'>当前服务器端版本："+serverVersion+"<br>您现在安装的版本："+getPluginVersion()+"<br>点击下边链接下载服务器端版本！</span><a href='"+basePath+"/resources/plugins/videomonitor/NSSPluginSetup.exe' style='color:red;margin:50px;'>点击这里下载</a></div></div>";
  		var updateDiv = $('.updateDiv');
  		if(updateDiv.length==0){
  			$('body').prepend(alertDiv);
  		}
  		$('.updateDiv').show();
  	}
  	
	function doipcplay(url,panelindex) {
		//addVideoOptionsEvent();
		if (plugin().valid && url != '' && url) {
			plugin().PlayOpen(url, panelindex);
		} else {
			alert("Plugin is not working :(");
		}
	}
	
	function doipcplayasync(url,panelindex,timeout){
		setTimeout(function(){doipcplay(url,panelindex);},timeout);
	}
	
	function doipcstop(pannelindex){
		if (plugin().valid) {
			plugin().PlayClose(pannelindex);
		} else {
			alert("Plugin is not working :(");
		}
	}
	
	function pluginLoaded(){
		//addVideoOptionsEvent();
		GetCurSelectWndIndex();
	}

	function fullScreen() {

		if (plugin().valid) {
			plugin().SetFullScreen(6);
		} else {
			alert("Plugin is not working :(");
		}
	}

	function setWindowMode(wno) {
		if (plugin() && plugin().valid) {
			plugin().SetWndMode(wno);
			windowmode = wno;
		}
	}

	function playCapture() {
		if (plugin().valid) {
			return plugin().PlayCapturePic2(GetCurSelectWndIndex());
		} else {
			alert("Plugin is not working :(");
		}
	}
	
	function playCaptureLocal(){
		if (plugin().valid) {
			var date = new Date();
			var filePath =  "C:\\Users\\Public\\Pictures\\" + 
			date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + '-' + date.getHours() + '-' + date.getMinutes() + '-' + date.getSeconds() + '-' + date.getMilliseconds() + '.bmp';
			var res = plugin().PlayCapturePic(GetCurSelectWndIndex(),filePath, 0);
			if(res == 0){
				alert('抓图成功，本地保存路径：'+filePath);
			}else{
				alert('抓图失败！');
			}
		} else {
			alert("Plugin is not working :(");
		}
	}
	
	function doPreset(preset){
		if (plugin().valid) {
			if(preset){
				return plugin().PTZPresetAndCruise(GetCurSelectWndIndex(), 17, 0, preset, 0, 0); 
			}
		} else {
			alert("Plugin is not working :(");
		}
	}
	
	function gotoPreset(ipcid,presetno){
		var location = (window.location+'').split('/'); 
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		var data = "presetSetData={'ipcid':'"+ipcid+"','presetno':"+presetno+"}";
		$.ajax({
			url:basePath+'/videomonitor/gotoPreset.action',
			type:'post',
			dataType:'json',
			data:data,
			success:function(d,e){
				var res = eval('('+d+')');
				if(res && res.sres == 0 && res.res == e){
					
				}else{
					alert('调用预置位失败：' + res.sres);
				}
			},
			error:function(d,e){
				alert('调用预置位失败：' + d.sres);
			}
		});					
	}

	function GetCurSelectWndIndex() {
		//addVideoOptionsEvent();

		if (plugin().valid) {
			return plugin().GetCurSelectWndIndex();
		} else {
			alert("Plugin is not working :(");
		}
	}

	function optionsVideoCallback(txt,i) {
		if(txt == ""){
			return;
		}
		var location = (window.location+'').split('/'); 
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		$.ajax({
			url:basePath + '/videomonitor/setPlayCookie.action',
			data:'playCookieContent=' + '{\"windowmode\":'+windowmode+',\"index\":'+txt+',\"url\":""}',
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

	function addVideoOptionsEvent() {
		addVideoEvent(plugin(), 'optionsCallback', optionsVideoCallback);
	}
	
	 function addVideoEvent(obj, name, func){
         if (typeof obj.attachEvent == 'function') {
             obj.attachEvent("on"+name, func);
         } else {
             obj.addEventListener(name, func, false); 
         }
     }
	 
	//1 焦距变大/变小
	  var PTZ_ZOOMIN = 9; /* 焦距变大(倍率变大) */
	  var PTZ_ZOOMOUT = 10; /* 焦距变小(倍率变小) */

	  	//2 焦点前调/后调
	  	var PTZ_FOCUSNEAR = 11; /* 焦点前调 */
	  	var PTZ_FOCUSFAR = 12; /* 焦点后调 */

	  	//3 光圈扩大/缩小
	  	 var PTZ_OPENIRIS = 13;  /* 光圈扩大 */
	  	 var PTZ_CLOSEIRIS = 14;  /* 光圈缩小 */
	
	//云台
	//a1 down
	function a1d(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), PTZ_CLOSEIRIS, 0, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	//a1 up
	function a1u(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a2d(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), PTZ_OPENIRIS, 0, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a2u(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a3d(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), PTZ_ZOOMIN, 0, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a3u(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a4d(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), PTZ_ZOOMOUT, 0, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a4u(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a5d(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), PTZ_FOCUSNEAR, 0, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a5u(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a6d(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), PTZ_FOCUSFAR, 0, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a6u(){
		if (plugin().valid) {
			plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
		} else {
			alert("Plugin is not working :(");
		}
	}
	function a7d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 0, 0, 0);
	}
	function a7u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a8d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 6, 0, 0);
	}
	function a8u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a9d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 3, 0, 0);
	}
	function a9u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a10d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 8, 0, 0);
	}
	function a10u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a11d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 1, 0, 0);
	}
	function a11u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a12d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 7, 0, 0);
	}
	function a12u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a13d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 2, 0, 0);
	}
	function a13u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a14d(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 5, 0, 0);
	}
	function a14u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	function a15d(){
		
	}
	function a15u(){
		plugin().RealPTZControl(GetCurSelectWndIndex(), 4, 1, 0);
	}
	//云台
	
	//画面参数
	/*
	VIDEO_DEFAULT = 0,   //恢复默认
	VIDEO_BRIGHTNESS = 1, //亮度
	VIDEO_CONTRAST, //对比度
	VIDEO_SATURATION, //饱和度
	VIDEO_HUE,//色度
	VIDEO_SHARPNESS_EFFECT, //锐度
	VIDEO_DENOISING_EFFECT//去噪
	*/
	//一般取值范围：1-10
	function setVideoParams(type, value){
		if (plugin().valid) {
			plugin().RemoteControl(GetCurSelectWndIndex(), type, 0, value, 0); 
		} else {
			alert("Plugin is not working :(");
		}
	} 
	//画面参数
	
	/*轮巡
	 * 
	 * 函数接口：int StartCameraPoll(const char* cameraScheme)

	入参：const char* cameraScheme：镜头轮询组配置方案-json串
	实例：
	{
	   "PlayType" : 0,
	   //镜头组轮询时间间隔
	   "pollInterval" : 10000,
	   "pollScheme" : [
	      {
	         //轮询镜头1
	         //摄像头通道ip
	         "channelIp" : "192.168.88.206",
	         //通道控制端口
	         "cmdPort" : 8000,
	         //通道数据端口
	         "dataPort" : 8001,
	         //设备ip
	         "devIp" : "192.168.88.114",
	         //轮询编号
	         "index" : 0,
	         "playerType" : 1,
	         //设备用户名/密码
	         "psw" : "12345",
	         "usr" : "admin"
	      },
	      {
	         //轮询镜头2
	         "channelIp" : "192.168.88.206",
	         "cmdPort" : 8000,
	         "dataPort" : 8001,
	         "devIp" : "192.168.88.114",
	         "index" : 1,
	         "playerType" : 1,
	         "psw" : "12345",
	         "usr" : "admin"
	      },
	      {
	         //轮询镜头3
	         "channelIp" : "192.168.88.206",
	         "cmdPort" : 8000,
	         "dataPort" : 8001,
	         "devIp" : "192.168.88.114",
	         "index" : 2,
	         "playerType" : 1,
	         "psw" : "12345",
	         "usr" : "admin"
	      }
	   ]
	}

	 * 
	 * */
	function startCameraPoll(cameraScheme){
		var res = -1;
		if (plugin().valid) {
			res = plugin().StartCameraPoll(cameraScheme); 
		} else {
			alert("Plugin is not working :(");
		}
		return res;
	}
	
	function stopCameraPoll(){
		if (plugin().valid) {
			plugin().StopCameraPoll(); 
		} else {
			alert("Plugin is not working :(");
		}
	}