<%@ page language="java" import="java.util.* ,com.service.gis.* ,com.entity.gis.* ,com.entity.authmgt.Session ,taglib.SystemFunction" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
    <meta http-equiv="Pragma" content="no-cache" />
    
	<title>安防综合监控系统-地理信息</title>

    <!-- 外网引用arcgis javascript 环境 -->    
    <!--link rel="stylesheet" href="http://js.arcgis.com/3.13/esri/css/esri.css"-->
    <!--script src="http://js.arcgis.com/3.13/"></script-->
    
<%
    /** 记得修改 **/
    boolean envirmentZXJ = false;//是否是中线局环境
    
    String cfg = SystemFunction.systemConfig("productEnvirement");
    if(cfg.equalsIgnoreCase("true")){
        envirmentZXJ = true;//中线局环境
    }else{
        envirmentZXJ = false;//测试环境
    }
    
    if(envirmentZXJ){//中线局环境
        out.print("<!-- 中线局生产环境 -->\n");
        out.print("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"http://10.20.6.103:9090/arcgis_js_api/library/3.12/3.12/dijit/themes/tundra/tundra.css\">\n");
        out.print("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"http://10.20.6.103:9090/arcgis_js_api/library/3.12/3.12/esri/css/esri.css\">\n");
    
        out.print("\t<script type=\"text/javascript\" src=\"http://10.20.6.103:9090/arcgis_js_api/library/3.12/3.12/init.js\"></script>\n");
    }else{//研究院环境
        out.print("<!-- 研究院环境 -->\n");
        out.print("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"http://10.3.10.183:9090/arcgis_js_api/library/3.12/3.12/dijit/themes/tundra/tundra.css\">\n");
        out.print("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"http://10.3.10.183:9090/arcgis_js_api/library/3.12/3.12/esri/css/esri.css\">\n");
    
        out.print("\t<script type=\"text/javascript\" src=\"http://10.3.10.183:9090/arcgis_js_api/library/3.12/3.12/init.js\"></script>\n");
    }
%>

    <link href="resources/css/gis.css" rel="stylesheet">
        
    <script type="text/javascript" src="resources/js/ajax-pushlet-client-gis.js"></script>
    <script type="text/javascript" src="resources/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="resources/js/IM.js"></script>
    <script type="text/javascript" src="resources/js/gis.js"></script>
    <!-- script src="/SSI/resources/js/fangqu.js" type="text/javascript"></script-->
    
    <!-- script type="text/javascript" src="resources/js/jquery-latest.js"></script-->
    <script type="text/javascript" src="resources/js/jquery-ui-latest.js"></script>
    <script type="text/javascript" src="resources/js/jquery.layout-latest.js"></script>
   
<%
    
    boolean isDebug = true;//

	//初始化的时候检测session，如果session异常，退出
	HttpSession httpSession = request.getSession();
	com.entity.authmgt.Session userSession = (Session) httpSession.getAttribute("session");
	
	if(userSession == null){
	        out.print("\t<script type=\"text/javascript\">\n");
	        out.print("\talert(\"尊敬的用户，您还没有登录！\\n请您登录后，从安防综合监控系统主界面打开地理信息窗口，谢谢！\\n地理信息窗口将关闭！ \");\n");
	        out.print("\twindow.close();\n");
	        out.print("\t</script>\n");
	}

    if(envirmentZXJ){//中线局生产环境，需要获取token
        GetGisToken tokenSvrObj = new GetGisToken();
        String token = tokenSvrObj.getGisToken(request);
        if(token == null || token.length() == 0){
            out.print("\t<script type=\"text/javascript\">\n");
            out.print("\talert(\"无法从地图服务器获得授权，\\n请与系统管理人员联系，以获取帮助！\\n地理信息窗口将关闭！ \");\n");
            out.print("\twindow.close();\n");
            out.print("\t</script>\n");
        }
        else{
            out.print("\t<script type=\"text/javascript\">\n");
            out.print("\tvar arcgisMapUrl = \"http://10.40.14.70:6080/arcgis/rest/services/nsbd_eagle/MapServer?token=" + token + "\";\n");
            //out.print("var arcgisMapUrl = \"http://10.40.14.70:6080/arcgis/rest/services/nsbd_project/MapServer?token=" + token + "\";\n");
            //out.print("var arcgisMapUrl = \"http://10.40.14.70:6080/arcgis/rest/services/nsbd_baseMap/MapServer?token=" + token + "\";\n");
            //out.print("var arcgisMapUrlBaseMap = \"http://10.40.14.70:6080/arcgis/rest/services/nsbd_baseMap/MapServer?token=" + token + "\";\n");
            out.print("\t</script>\n");
        }
    }
    else{//研究院测试环境
	    GetGisToken tokenSvrObj = new GetGisToken();
	    String token =  tokenSvrObj.test();
	    if(token == null || token.length() == 0){
	        out.print("\t<script type=\"text/javascript\">\n");
	        out.print("\talert(\"无法从地图服务器获得授权，\\n请与系统管理人员联系，以获取帮助！\\n地理信息窗口将关闭！ \");\n");
	        out.print("\twindow.close();\n");
	        out.print("\t</script>\n");
	    }
	    else{
	        out.print("\t<script type=\"text/javascript\">\n");
            out.print("\tvar arcgisMapUrl = \"http://www.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer\";\n");
	        out.print("\t</script>\n");
	    }
    }
%>
    <!--定义地图窗口的大小-->
    <style type="text/css">
        .MapClass {
            width: 1000px;
            height: 700px;
            border: 1px solid #000;
        }
    </style>

    <script type="text/javascript">
        dojo.require("dojo/dom-construct");
        
        dojo.require("esri/map");
        dojo.require("esri/InfoTemplate");

        dojo.require("esri/SpatialReference");
        dojo.require("esri/Color");
                
        dojo.require("esri/geometry/Point");
        
        dojo.require("esri/dijit/OverviewMap");
        dojo.require("esri/dijit/InfoWindow");
        
        dojo.require("esri/layers/ImageParameters");
        dojo.require("esri/layers/GraphicsLayer");
        dojo.require("esri/layers/ArcGISDynamicMapServiceLayer");
        dojo.require("esri/layers/ImageParameters");

       /* 
          if(document.documentElement.requestFullscreen) {
    document.documentElement.requestFullscreen();
  } else if(document.documentElement.mozRequestFullScreen) {
    document.documentElement.mozRequestFullScreen();
  } else if(document.documentElement.webkitRequestFullscreen) {
    document.documentElement.webkitRequestFullscreen();
  } else if(document.documentElement.msRequestFullscreen) {
    document.documentElement.msRequestFullscreen();
  }
  */
        //document.documentElement.requestFullscreen();
    </script>

    <!-- 全局数据和变量定义-->
    <script type="text/javascript">
        var ICON_SIZE_PNG = 28;//PNG图标尺寸
        var ICON_SIZE_GIF = 28;//GIF图标尺寸（注：与GIF图片的尺寸差别不能太大，不然无法显示）
        
<%
    /** 处理不同的坐标系统 **/
    if(envirmentZXJ){//中线局生产环境
        out.print("\t\tvar wkid = 4214;\n");
    }else{//研究院测试环境
        out.print("\t\tvar wkid = 4326;\n");//坐标系统代码,arcgis静态动态地图
        //out.print("var wkid = 3857;");//坐标系统代码,arcgis动态地图
    }

    GetGisParam gisParamGeter = new GetGisParam();
    BranchInfo range = gisParamGeter.queryBranchRangeByUserID(request);
    if(range != null){
        out.print("\t\tvar startPoint = new mapInitParam(" + range.getXminStr() + "," + range.getYminStr() + "," + range.getXmaxStr()+ ", " + range.getYmaxStr() + ");//管理处地图");
    }
    else{
        //out.print("startPoint = new mapInitParam(70.374747, 0.903097, 138.053125, 56.038121);//全国地图");
        out.print("\t\tvar startPoint = new mapInitParam(105.374747, 30.903097, 125.053125, 41.038121);//中线地图");
    }
    
        //startPoint = new mapInitParam(70.374747, 0.903097, 138.053125, 56.038121);//全国地图
        //startPoint = new mapInitParam(105.374747, 30.903097, 125.053125, 41.038121);//中线地图
    
%>
        //var wkid = 4326;//坐标系统代码,arcgis静态地图
        //var wkid = 3857;//坐标系统代码,arcgis动态地图
        //var wkid = 4214;//坐标系统代码,中线局
        
        //var ORGINAL_ZOOM = 0;
        
    	var alarmGraphicLayer4Security = new esri.layers.GraphicsLayer();//告警图层
        var alarmGraphicLayer4Device = new esri.layers.GraphicsLayer();//告警图层
        var deviceGraphicLayer = new esri.layers.GraphicsLayer();//设备图层
        var zoneGraphicLayer = new esri.layers.GraphicsLayer();//防区图层
        
        var searchResultGraphicLayer = new esri.layers.GraphicsLayer();//搜索结果图层
        
        var cameraPoint = new Array();//摄像头
        var fencePoint = new Array();//电子围栏主机
        var zonePoint = new Array();//防区
        var securityAlarmPoint = new Array();//安全告警
        var deviceAlarmPoint = new Array();//设备告警
        
        var isInSearchData = false; //标识当前是否处于搜索状态
    </script>
    
    <!--数据类型定义-->
    <script type="text/javascript">
        var isDebug = false;
        var alertInfo = function(message){
            if(isDebug){
                alert(message);
            }
        }
        
        var DEVICE_TYPE={
            "1":"摄像头",
            "2":"电子围栏"
        }
        
        var DEVICE_TYPE_NAME={
                "1":"高压脉冲主机",
                "2":"一体化探测主机",
                "3":"防区型振动光纤",
                "4":"定位型振动光纤",
                "5":"NVR",
                "6":"IPC",
                "7":"智能视频服务器",
                "8":"音频服务器",
                "9":"音频终端",
                "10":"IO控制器",
                "default":"安防设备"//找不到对应类型
                }

        var DEVICE_STAT_NAME={
                "1":"正常",
                "2":"故障",
                "3":"离线"
        }

       
        var ZONE_STAT_NAME={
                "0":"撤防",
                "1":"布防"
        }
        
        var DEV_TYPE_GIS_CAMERA = "camera";
        var DEV_TYPE_GIS_FENCE = "fence";
        var DEV_TYPE_GIS_ZONE = "zone";
                        
        var DEV_STAT_READY = "ready";
        var DEV_STAT_FAULT = "fault";
        var DEV_STAT_OFFLINE = "offline";
        
        var ALARM_LEVEL_YELLOW = 1;
        var ALARM_LEVEL_ORANGE = 2;
        var ALARM_LEVEL_RED = 3;
        
        var ALARM_TYPE_SECURITY="securityAlarm";//双屏联动，告警类型定义，安防
        var ALARM_TYPE_DEVICE="deviceAlarm";//双屏联动，告警类型定义，设备，保留
                
        /*** 颜色\图标定义 ***/
        var C_DEV_GREEN = "#00FF00";//设备颜色
        var C_DEV_YELLOW = "#FF00FF";
        var C_DEV_GRAY = "#0F0F0F";
        
        var C_ZONE_GREEN = "#00FF00";//防区颜色
        var C_ZONE_YELLOW = "#FF00FF";
        var C_ZONE_GRAY = "#9E9E9E";
        
        var C_ALARM_YELLOW = "#0F0F0F";//轻微和一般
        var C_ALARM_ORANGE = "#0F0F0F";//主要
        var C_ALARM_RED = "#FF0000";//严重,红色
        
        //var ICON_ALARM_YELLOW = "./resources/images/gis/alarm_yellow.gif";//一般，gif图片闪烁
        //var ICON_ALARM_ORANGE = "./resources/images/gis/alarm_orange.gif";//主要，gif图片闪烁
        //var ICON_ALARM_RED = "./resources/images/gis/alarm_flash.gif";//严重，gif图片闪烁
        
        var ICON_ALARM_YELLOW = "resources/images/gis/alarm_yellow.png";//一般，gif图片闪烁
        var ICON_ALARM_ORANGE = "resources/images/gis/alarm_orange.png";//主要，gif图片闪烁
        var ICON_ALARM_RED = "resources/images/gis/alarm_red.gif";//严重，gif图片闪烁
        
        var ICON_CAMERA_GREEN = "resources/images/gis/camera_green.png";//摄像头图标
        var ICON_CAMERA_YELLOW = "resources/images/gis/camera_yellow.png";
        var ICON_CAMERA_GRAY = "resources/images/gis/camera_gray.png";
        
        var ICON_FENCE_GREEN = "resources/images/gis/fence_green.png";//围栏主机图标
        var ICON_FENCE_YELLOW = "resources/images/gis/fence_yellow.png";
        var ICON_FENCE_GRAY = "resources/images/gis/fence_gray.png";
        
        /* 初始化地图参数 */
        /* 查询初始地图参数，要依据用户id，找到管理处的坐标范围 */
        //地图装载范围，默认地图范围--全国
        //startPoint = new mapInitParam(70.374747, 0.903097, 138.053125, 56.038121);//全国地图
        
        //startPoint = new mapInitParam(105.374747, 30.903097, 125.053125, 41.038121);//中线地图

        /* 设备 *************************************************************/
        /* 数据类型定义：设备*/
        function deviceAttributes(id,typeName, type,name,mntID,mntName,orgID,orgName,ip,port,stat, lon,lat)
        {
            this.id = id;//编号
            this.typeName = typeName;
            this.type = type;//类型
            this.name = name;//名称
            this.mntID = mntID;//管理处id
            this.mntName = mntName;//管理处名称
            this.orgID = orgID;//所属机构ID
            this.orgName = orgName;//所属机构名称
            this.ip = ip;//ip地址
            this.port = port;//ip端口
            this.stat = stat;//状态,
            this.lat = lat;//纬度
            this.lon = lon;//经度
        }
        var queryDevice = function ()
        {
        	var timenow = new Date().getTime();
            //摄像头
            var targetUrl = "${pageContext.request.contextPath}/gis/queryCameraAction?d="+timenow; 
            alertInfo(targetUrl); 

            $.ajax({ 
                url : targetUrl, 
                type : 'POST', 
                dataType : 'json', 
                success : function(json) { 
                    alertInfo(json); 
                    if(json){
                        $.each(json,function(idx,item){ 
	                        //if(idx==0){ 
	                            //return true;//同countinue，返回false同break 
	                        //}else{
	                           if(item.lon ==0 || item.lat ==0)
	                           {}
	                           else{
	                            cameraPoint.push( new deviceAttributes(
	                                item.deviceID,
	                                item.typeName,
	                                6,//item.type,DEVICE_TYPE_NAME:"6"-IPC
	                                item.deviceName,
	                                item.mntID,
	                                item.mntName,
	                                item.orgID,
	                                item.orgName,
	                                item.ip,
	                                item.port,
	                                item.stat,
	                                item.lon,
	                                item.lat));
	                           }
	                        //}
	                    });
	                    addDeviceInfo(cameraPoint); 
	                    cameraPoint = null;
                    } 
                }, 
                error : function() { 
                    alert("摄像头数据加载失败,请重试.."); 
                } 
            });
            
            //围栏主机
            targetUrl =  "${pageContext.request.contextPath}/gis/queryFenceAction?d="+timenow;
            alertInfo(targetUrl); 

            $.ajax({ 
                url : targetUrl, 
                type : 'POST', 
                dataType : 'json', 
                success : function(json) {
                
                    alertInfo(json); 
                    if(json){
                       $.each(json,function(idx,item){
                        //if(idx==0){ 
                            //return true;//同countinue，返回false同break 
                        //}else{
                            if(item.lon ==0 || item.lat ==0)
                            {}
                            else{//坐标不为0的才处理
                                fencePoint.push( new deviceAttributes(
                                item.hostID,
                                item.typeName,
                                item.fenceType,//围栏类型是大类型还是小类型？
                                item.fenceName,
                                item.mntMentID,
                                item.mntMentName,
                                item.branchID,
                                item.branchName,
                                item.ip,
                                item.port,
                                item.fenceStatus,
                                item.hostLon,
                                item.hostLat
                                ));
                            }
                        //}
                    }); 
                    addDeviceInfo(fencePoint); 
                    fencePoint = null;
                   }
                }, 
                error : function() {
                    alert("电子围栏数据加载失败,请重试..");
                } 
            });

        }

        /**　依据主界面传递的设备信息，定位和缩放地图　**/
        var focusDevice = function(deviceID,deviceType)
        {
            var cgraphic;
            for(var i=0; i<deviceGraphicLayer.graphics.length;i++)
            {
              cgraphic = deviceGraphicLayer.graphics[i];
              if(cgraphic.attributes.id == deviceID && cgraphic.attributes.type == deviceType){
                var po = new esri.geometry.Point(cgraphic.attributes.lon, cgraphic.attributes.lat, new esri.SpatialReference({ "wkid": wkid }) );
                MyMap.centerAndZoom(po,1);
		//MyMap.centerAndZoom(po,6);//静态地图
		          MyMap.infoWindow.setTitle(cgraphic.getTitle());
            MyMap.infoWindow.setContent(cgraphic.getContent());
		        MyMap.infoWindow.show(po);
                break;
              }
            }
        }
        
        /** 接收后台推送的设备添加消息 **/
        var displayDevice = function (deviceType,message){
            var msgObj = eval('(' + message + ')');
            
            var devicePoint = new Array();
            var tmpDevice = new deviceAttributes();
            if(deviceType == DEV_TYPE_GIS_CAMERA){//TVmIpc
			        
			        if (msgObj.ipclongitude != null) {
			            tmpDevice.lon = parseFloat(msgObj.ipclongitude);
			        }else{
			             return;
			        }
			        if (obj.ipclatitude != null) {
			            tmpDevice.lat = parseFloat(msgObj.ipclatitude);
			        }else{
			             return;
			        }
			        tmpDevice.id = msgObj.ipcid;
                    tmpDevice.ip = msgObj.ip;
                    tmpDevice.port = msgObj.port;
			        
			        tmpDevice.type = 6;
			        tmpDevice.name = msgObj.devname;
			        tmpDevice.stat = msgObj.ipcFault;
			        tmpDevice.mntID = msgObj.managementagency;
			        tmpDevice.orgID = msgObj.branch;
			        tmpDevice.typeName = "摄像头";// 是否需要转成名称
            }else if(deviceType == DEV_TYPE_GIS_FENCE){//fencebean
                    if (msgObj.efLongitude != null) {
                        tmpDevice.hostLon = parseFloat(msgObj.efLongitude);
                    }else{
                        return;
                    }
                    if (msgObj.efLatitude != null) {
                        tmpDevice.hostLat = parseFloat(msgObj.efLatitude);
                    }else{
                        return;
                    }
            
                    tmpDevice.id = msgObj.hostID;
			        tmpDevice.type = msgObj.fenceType;
			        tmpDevice.typeName = "电子围栏";
			        tmpDevice.ip = msgObj.ip;
			        tmpDevice.port = msgObj.port;
			        tmpDevice.stat = msgObj.fenceStatus;
			        tmpDevice.mntID = msgObj.mntMentID;
			        tmpDevice.mntName = msgObj.mntMentName;
			        tmpDevice.orgID = msgObj.subComID;
			        tmpDevice.orgName = msgObj.subComName;
            
            }
            
            //if(msgObj.lat == null || msgObj.lon == null){}
            //else{
	            //tmpDevice.lat = parseFloat(msgObj.lat);//纬度
	            //tmpDevice.lon = parseFloat(msgObj.lon);//经度
	            devicePoint.push(tmpDevice);
	            if(deviceType == DEV_TYPE_GIS_CAMERA){
	                //cameraPoint.push(tmpDevice);
	            }else if(deviceType == DEV_TYPE_GIS_FENCE){
	                //fencePoint.push(tmpDevice);
	            }
	            addDeviceInfo(devicePoint);
	            devicePoint = null;
            //}
        }
        
        /** 接受后台推送的设备状态变更消息,先找到graphic，修改状态属性attr(name, value)，然后新建symbol和infoT，和赋值setSymbol(symbol)\setInfoTemplate(infoTemplate)，最后show */
        var operateDevice = function(deviceType,message){
            var cgraphic;
            var msgObj = eval('(' + message + ')');
            var deviceID = 0;
            
            if(deviceType == DEV_TYPE_GIS_CAMERA){
                deviceID = msgObj.cameraID;
                for(var i=0; i<deviceGraphicLayer.graphics.length;i++)
	            {
	                cgraphic = deviceGraphicLayer.graphics[i];
	                
	                if(cgraphic.attributes.id == deviceID && cgraphic.attributes.type == 6){
	                    var stat = message.stat;//当前状态
	                    if(stat == DEV_STAT_READY){
	                        cgraphic.symbol.setUrl(ICON_CAMERA_GREEN);
	                    }
	                    else if(stat == DEV_STAT_FAULT){
	                        cgraphic.symbol.setUrl(ICON_CAMERA_YELLOW);
	                    }
	                    else if(stat == DEV_STAT_OFFLINE){
	                        cgraphic.symbol.setUrl(ICON_CAMERA_GRAY);
	                    }
	                    cgraphic.draw();
	                    break;
	               }
	            }
            }else if(deviceType == DEV_TYPE_GIS_FENCE){
                deviceID = msgObj.fenceID;
                for(var i=0; i<deviceGraphicLayer.graphics.length;i++)
	            {
	                cgraphic = deviceGraphicLayer.graphics[i];
	                
	                if(cgraphic.attributes.id == deviceID && cgraphic.attributes.type != 6){
	                    var stat = message.stat;//当前状态
                        if(stat == DEV_STAT_READY){
                            cgraphic.symbol.setUrl(ICON_FENCE_GREEN);
                        }
                        else if(stat == DEV_STAT_FAULT){
                            cgraphic.symbol.setUrl(ICON_FENCE_YELLOW);
                        }
                        else if(stat == DEV_STAT_OFFLINE){
                            cgraphic.symbol.setUrl(ICON_FENCE_GRAY);
                        }
                        cgraphic.draw();
                        break;
                    }
	            }
            }
        }
        
        /** 接受后台推送的设备 删除 消息，先找到graphic，然后用layer的remove(graph) */
        var deleteDevice = function(deviceType,message){
            var cgraphic;
            var msgObj = eval('(' + message + ')');
            var deviceID = 0;
            
            if(deviceType == DEV_TYPE_GIS_CAMERA){
                deviceID = msgObj.cameraID;
                for(var i=0; i<deviceGraphicLayer.graphics.length;i++)
	            {
	               cgraphic = deviceGraphicLayer.graphics[i];
	               if(cgraphic.attributes.id == deviceID && cgraphic.attributes.type == 6){
	                   deviceGraphicLayer.remove(cgraphic);
	                   break;
	               }
	            }
            }else if(deviceType == DEV_TYPE_GIS_FENCE){
                deviceID = msgObj.fenceID;
                for(var i=0; i<deviceGraphicLayer.graphics.length;i++)
	            {
	               cgraphic = deviceGraphicLayer.graphics[i];
	               if(cgraphic.attributes.id == deviceID && cgraphic.attributes.type != 6){
	                   deviceGraphicLayer.remove(cgraphic);
	                   break;
	               }
	            }
            }
        }
        
        
        /* 告警 *************************************************************/
        /* 数据类型定义：告警*/
        var alarmAttributes4Device = function(alarmID,cause,result,operation,isAffect,
                                        alarmCode,alarmName,alarmLevel,deviceType,deviceId,zoneId,zoneName,
                                        departmentName,departmentId,branchName,type,branchId,lon,lat)
        {
            this.alarmID = alarmID;
            this.cause = cause;// 告警原因
		    this.result = result;// 后果
		    this.operation = operation;// 建议操作
		    this.isAffect =isAffect;// 是否影响业务,0 不影响,1 影响
		    this.alarmCode=alarmCode;// 告警码
		    this.alarmName=alarmName;// 告警名称
		    this.alarmLevel=alarmLevel;// 告警级别
		    this.deviceType=deviceType;// 设备类型
		    this.deviceId=deviceId;// 设备Id
		    this.zoneId=zoneId;// 防区Id
		    this.zoneName=zoneName;// 防区
		    this.departmentName=departmentName;
		    this.departmentId=departmentId;
		    this.branchName=branchName;
		    this.branchId=branchId;
            this.type = "设备告警";    
            this.lon = lon;//经度
            this.lat = lat;//纬度
        }
        var alarmAttributes4Security = function(alarmID,alarmTime,alarmZoneID,alarmZoneName,alarmName,devType,devID,
            severityLvl,mgtName,branchName,zoneStartLon,zoneStartLat,zoneEndLon,zoneEndLat)
        {
            this.alarmID = alarmID;
            this.alarmTime = alarmTime;
            this.alarmZoneID = alarmZoneID;//告警防区id
            this.alarmZoneName = alarmZoneName;//告警防区名称
            this.alarmName = alarmName;//告警名称
            this.devType = devType;// 设备类型
            this.devID = devID;//告警设备ID
            this.severityLvl = severityLvl;//告警等级
            this.mgtName = mgtName;//管理处名称
            this.branchName = branchName;//分公司名称
            this.type = "安防告警";
            this.zoneStartLon = zoneStartLon;//告警防区经度
            this.zoneStartLat = zoneStartLat;//告警防区纬度
            this.zoneEndLon = zoneEndLon;//告警防区经度
            this.zoneEndLat = zoneEndLat;//告警防区纬度
        }

        var queryAlarm = function()
        {
            var timenow = new Date().getTime();
            //摄像头
            var tergetUrl =  "${pageContext.request.contextPath}/gis/queryWarning4DeviceAction?d="+timenow; 
            alertInfo("查询设备告警"); 

            $.ajax({ 
                url : tergetUrl, 
                type : 'POST', 
                dataType : 'json', 
                success : function(json) { 
                    alertInfo(json);
                    if(json){
                       $.each(json,function(idx,item){
                        //if(idx==0){
                         //   return true;//同countinue，返回false同break 
                        //}else{
                            if(item.lon == 0 || item.lat == 0){}
                            else{
                            deviceAlarmPoint.push( new alarmAttributes4Device(
                                item.alarmID,
                                item.cause, 
                                item.result,
                                item.operation,
                                item.isAffect,
                                item.alarmCode,
                                item.alarmName,
                                item.alarmLevel,
                                item.deviceType,
                                item.deviceId,
                                item.zoneId,
                                item.zoneName,
                                item.departmentName,
                                item.departmentId,
                                item.branchName,
                                item.branchId,
                                item.type,
                                item.lon,
                                item.lat
                                ));
                                }
                        //}
                    });
                        addAlarm4Device(deviceAlarmPoint); 
                        deviceAlarmPoint = null;
                    }
                }, 
                error : function() { 
                    alert("设备告警数据加载失败,请重试.."); 
                } 
            });
            
            tergetUrl =  "${pageContext.request.contextPath}/gis/queryWarning4SecurityAction?d="+timenow; 
            //alertInfo(url); 

            $.ajax({ 
                url : tergetUrl, 
                type : 'POST', 
                dataType : 'json', 
                success : function(json) {
                    alertInfo(json); 
                    if(json){
                        $.each(json,function(idx,item){
	                        //if(idx==0){ 
	                        //    return true;//同countinue，返回false同break 
	                        //}else{
	                            //securityAlarmPoint[idx] = new alarmAttributes4Security(
                                if(item.zoneStartLon == 0 || item.zoneStartLat ==0 || item.zoneEndLon == 0 || item.zoneEndLat ==0)
                                {}
                                else{
	                            securityAlarmPoint.push(new alarmAttributes4Security(
	                                item.alarmID,
	                                item.alarmTime,
	                                item.alarmZoneID,
	                                item.alarmZoneName,
	                                item.alarmName,
	                                item.devType,
	                                item.devID,
	                                item.severityLvl,
	                                item.mgtName,
	                                item.branchName,
	                                item.zoneStartLon,
	                                item.zoneStartLat,
	                                item.zoneEndLon,
	                                item.zoneEndLat
	                                ));
	                            }
	                        //}
                        });
                        addAlarm4Security(securityAlarmPoint);
                        securityAlarmPoint = null;
                    }
                }, 
                error : function() { 
                    alert("安防告警数据加载失败,请重试.."); 
                } 
            });
        }
        /** 接受后台推送的告警消息，在地图上显示 **/
        var displayAlarm = function(alarmType, message)
        {
            /* 先判断告警类型：安防、设备，在不同的图层上添加   */
            var msgObj = eval('(' + message + ')');
            var alarmPointArray = new Array();
            if(alarmType == ALARM_TYPE_SECURITY){//安防告警,entity:UIGisSecurityAlarm
                var tmpAlarm = new alarmAttributes4Security();
                
                tmpAlarm.alarmID =  msgObj.alarmID;
                tmpAlarm.alarmTime =  msgObj.alarmTime;
                tmpAlarm.alarmZoneID =  msgObj.alarmZoneID;//告警防区id
                tmpAlarm.alarmZoneName =  msgObj.alarmZoneName;//告警防区名称
                tmpAlarm.alarmName =  msgObj.alarmName;//告警名称
                tmpAlarm.devType =  msgObj.devType;// 设备类型
                tmpAlarm.devID = msgObj.devID;//告警设备ID
                tmpAlarm.severityLvl = msgObj.severityLvl;//告警等级
                tmpAlarm.mgtName = msgObj.mgtName;//管理处名称
                tmpAlarm.branchName = msgObj.branchName;//分公司名称
                tmpAlarm.type = "安防告警";
                
                //if(msgObj.zoneStartLon == null || msgObj.zoneStartLat == null
                  //  || msgObj.zoneEndLon == null || msgObj.zoneEndLat == null){}
                if(msgObj.alarmLongitude == null || msgObj.alarmLatitude == null){}
                else{
                    tmpAlarm.zoneStartLon = parseFloat(msgObj.alarmLongitude);//鍛婅闃插尯缁忓害
                    tmpAlarm.zoneStartLat = parseFloat(msgObj.alarmLatitude);//鍛婅闃插尯绾害
                    //tmpAlarm.zoneEndLon = parseFloat(msgObj.zoneEndLon);//鍛婅闃插尯缁忓害
                    //tmpAlarm.zoneEndLat = parseFloat(msgObj.zoneEndLat);//鍛婅闃插尯绾害
                    
                    alarmPointArray.push(tmpAlarm);
                    //securityAlarmPoint.push(tmpAlarm);
                    addAlarm4Security(alarmPointArray);
                }
            }
            else if(alarmType == ALARM_TYPE_DEVICE){//设备告警,entity:com.entity.alarmmgt.DeviceAlarm
                var tmpAlarm = new alarmAttributes4Device();
                tmpAlarm.alarmID = msgObj.alarmId;
	            tmpAlarm.cause = msgObj.cause;// 告警原因
	            tmpAlarm.result = msgObj.result;// 后果
	            tmpAlarm.operation = msgObj.operation;// 建议操作
	            tmpAlarm.isAffect = msgObj.isAffect;// 是否影响业务,0 不影响,1 影响
	            tmpAlarm.alarmCode= msgObj.alarmCode;// 告警码
	            tmpAlarm.alarmName= msgObj.alarmName;// 告警名称
	            tmpAlarm.alarmLevel= msgObj.alarmLevel;// 告警级别
	            tmpAlarm.deviceType= msgObj.deviceType;// 设备类型
	            tmpAlarm.deviceId= msgObj.deviceId;// 设备Id
	            tmpAlarm.zoneId= msgObj.zoneId;// 防区Id
	            tmpAlarm.zoneName= msgObj.zoneName;// 防区
	            tmpAlarm.departmentName= msgObj.departmentName;
	            tmpAlarm.departmentId= msgObj.departmentId;
	            tmpAlarm.branchName= msgObj.branchName;
	            tmpAlarm.branchId= msgObj.branchId;
	            tmpAlarm.type = "设备告警";
	            
	            if(msgObj.lon == null || msgObj.lat == null){}
                else{
		            tmpAlarm.lon =  parseFloat(msgObj.lon);//经度
		            tmpAlarm.lat =  parseFloat(msgObj.lat);//纬度
			            
		            alarmPointArray.push(tmpAlarm);
		            
		            //deviceAlarmPoint.push(tmpAlarm);
		            
		            addAlarm4Device(alarmPointArray);
	            }
            }
        }

        /** 接受后台推送的告警处理消息，从地图上删除 **/
        var deleteAlarm = function (alarmType, message){
            /* 先判断告警类型，在不同的图层中查找 */
            var msgObj = eval('(' + message + ')');
            var alarmID = msgObj.alarmID;
            var cgraphic;
            if(alarmType == ALARM_TYPE_SECURITY){//安防告警
                for(var i=0; i<alarmGraphicLayer4Security.graphics.length;i++)
                {
                    cgraphic = alarmGraphicLayer4Security.graphics[i];
                    if(cgraphic.attributes.alarmID == alarmID){
                        alarmGraphicLayer4Security.remove(cgraphic);
                        break;
                    }
                }
            }else if(alarmType == ALARM_TYPE_DEVICE){//设备告警
	            for(var i=0; i<alarmGraphicLayer4Device.graphics.length;i++)
	            {
	               cgraphic = alarmGraphicLayer4Device.graphics[i];
	               if(cgraphic.attributes.alarmID == alarmID){
	                   alarmGraphicLayer4Device.remove(cgraphic);
    	               break;
	              }
	            }
            }
        }
        
        /** 接受主界面的消息，从地图上聚焦 **/
        function focusAlarm(alarmID,alarmType){
            var cgraphic;
            if(alarmType == ALARM_TYPE_SECURITY){//安防告警
                for(var i=0; i<alarmGraphicLayer4Security.graphics.length;i++)
                {
                    cgraphic = alarmGraphicLayer4Security.graphics[i];
                    if(cgraphic.attributes.alarmID == alarmID){
                        var po = new esri.geometry.Point(cgraphic.attributes.zoneStartLon, cgraphic.attributes.zoneStartLat, new esri.SpatialReference({ "wkid": wkid }));
                        MyMap.centerAndZoom(po,1);
			//MyMap.centerAndZoom(po,6);//静态地图
			MyMap.infoWindow.setTitle(cgraphic.getTitle());
			MyMap.infoWindow.setContent(cgraphic.getContent());
			 MyMap.infoWindow.show(po);
                        break;
                    }
                }
            }else if(alarmType == ALARM_TYPE_DEVICE){//设备告警，保留
                for(var i=0; i<alarmGraphicLayer4Device.graphics.length;i++)
                {
                    cgraphic = alarmGraphicLayer4Device.graphics[i];
                    if(cgraphic.attributes.alarmID == alarmID){
                        var po = new esri.geometry.Point(cgraphic.attributes.lon, cgraphic.attributes.lat, new esri.SpatialReference({ "wkid": wkid }));
                        //MyMap.centerAndZoom(po,6);
                        MyMap.centerAndZoom(po,1);
                        MyMap.infoWindow.setTitle(cgraphic.getTitle());
                        MyMap.infoWindow.setContent(cgraphic.getContent());
                         MyMap.infoWindow.show(po);
                        break;
                  }
                }
            }
                           
        }
        

        /* 防区 *************************************************************/
        /* 数据类型定义：防区*/
        function zoneAttributes(id, name,orientation,mntID,mntName,orgID,orgName,stat, startLon, startLat, endLon, endLat)
        {
            this.id = id;
            //this.type = type;
            this.name = name;
            this.orientation =orientation;
            this.mntID = mntID;
            this.mntName = mntName;
            this.orgID = orgID;
            this.orgName = orgName;
            this.stat = stat;
            this.startLat = startLat;//防区起始位置，纬度
            this.startLon = startLon;
            this.endLat = endLat;//防区终点位置，纬度
            this.endLon = endLon;
        }
        
        
        var queryZone = function()
        {
        	var timenow = new Date().getTime();
            var targetUrl =  "${pageContext.request.contextPath}/gis/querySecurityZoneAction?d="+timenow;
            alertInfo(targetUrl); 

            $.ajax({
                type : 'GET', 
                url : targetUrl, 
                dataType : 'json', 
                success : function(json) {
                    alertInfo(json); 
                    if(json){
                         $.each(json,function(idx,item){
                    
                        //if(idx==0){ 
                            //return true;//同countinue，返回false同break 
                        //}else{
                            //zonePoint[idx] = new zoneAttributes(
                            alertInfo(item.zoneID + ";" +item.startLon+ ";"+ item.startLat +";" + item.endLon + ";" + item.endLat);
                            if(item.startLon == 0 || item.startLat ==0 || item.endLon == 0 || item.endLat ==0)
                            {}
                            else{
                                zonePoint.push(new zoneAttributes(
                                item.zoneID,
                                item.zoneName,
                                item.orientation,
                                item.mgtID,
                                item.mgtName,
                                item.branchID,
                                item.branchName,
                                item.stat,
                                item.startLon,
                                item.startLat,
                                item.endLon,
                                item.endLat
                                ));
                            }
                        }
                      //}
                    ); 
                    }
                 
                    addZone(zonePoint);
                    zonePoint = null;//wyj 150518
                }, 
                error : function(json) {
                    alertInfo(json.message);
                    alert("防区数据加载失败,请重试.."); 
                } 
            });
        }
        /** 定位防区位置，改变颜色，是否缩放地图依据是否由数据界面发送的消息判断 */
        var focusZone = function(zoneID)
        {
            var cgraphic;
                for(var i=0; i<zoneGraphicLayer.graphics.length;i++)
                {
                    cgraphic = zoneGraphicLayer.graphics[i];
                    if(cgraphic.attributes.id == zoneID && cgraphic.attributes.name != null){
                        var po = new esri.geometry.Point(cgraphic.attributes.startLon, cgraphic.attributes.startLat, new esri.SpatialReference({ "wkid": wkid }) );
                        MyMap.centerAndZoom(po,1);
                        //MyMap.centerAndZoom(po,6);
                                    MyMap.infoWindow.setTitle(cgraphic.getTitle());
            MyMap.infoWindow.setContent(cgraphic.getContent());
                                        MyMap.infoWindow.show(po);
                        break;
                    }
                }
        }
        
        /** 显示单个推送的防区，添加 */
        var displayZone = function(zoneID, message){
                    /* 先判断告警类型：安防、设备，在不同的图层上添加   */
            var zonePointTmp = new Array();
            var tmpZone = new zoneAttributes();
            
            var msgObj = eval('(' + message + ')');
            
            tmpZone.id = msgObj.zoneID;
            //tmpZone.type = msgObj.fenceType;
            tmpZone.name = msgObj.zoneName;
            tmpZone.orientation = msgObj.orientation;
            tmpZone.mntID = msgObj.mgtID;
            tmpZone.mntName = msgObj.mgtName;
            tmpZone.orgID = msgObj.branchID;
            tmpZone.orgName = msgObj.branchName;
            tmpZone.stat = msgObj.status;
            if(msgObj.startLat == null || msgObj.startLon == null
                    || msgObj.endLat == null || msgObj.endLon == null){}
            else{
	            tmpZone.startLat = parseFloat(msgObj.startLat);//防区起始位置，纬度
	            tmpZone.startLon = parseFloat(msgObj.startLon);
	            tmpZone.endLat = parseFloat(msgObj.endLat);//防区终点位置，纬度
	            tmpZone.endLon = parseFloat(msgObj.endLon);

	            zonePointTmp.push(tmpZone);

	            addZone(zonePointTmp);
	            zonePointTmp = null;
                MyMap.infoWindow.hide();
            }
        }
        
        /** 防区操作，撤布防等，状态改变 */
        /**接受后台推送的设备状态变更消息,先找到graphic，修改状态属性attr(name, value)，然后新建symbol和infoT，和赋值setSymbol(symbol)\setInfoTemplate(infoTemplate)，最后show */
        var operateZone = function(zoneID, message){
        
            var cgraphic;
            var msgObj = eval('(' + message + ')');
            
            //for(var i=0; i<zoneGraphicLayer.graphics.length;i++)
            var att = null;
            for(var i=(zoneGraphicLayer.graphics.length-1); i>=0;i--)
            {
              cgraphic = zoneGraphicLayer.graphics[i];
              if(cgraphic.attributes == null){
                continue;
              }
              if(cgraphic.attributes.id == zoneID){
                  if(cgraphic.attributes.name == null){
                    zoneGraphicLayer.remove(cgraphic);
                    continue;
                  }
                  att = cgraphic.attributes;
                var stat = msgObj.stat;//当前状态 
                att.stat = stat;
                zoneGraphicLayer.remove(cgraphic);

                /*
                if(stat == DEV_STAT_READY){
                    cgraphic.symbol.setColor(new dojo.Color(C_ZONE_GREEN));
                    cgraphic.infoTemplate.setContent(cgraphic.infoTemplate.content.replace(/bufang.png/,"chefang.png"));
                    cgraphic.infoTemplate.setContent(cgraphic.infoTemplate.content.replace(/布防/,"撤防"));//先替换按钮字符
                    cgraphic.infoTemplate.setContent(cgraphic.infoTemplate.content.replace(/:撤防/,":布防"));//后替换状态字符
                }
                else if(stat == DEV_STAT_FAULT){
                    cgraphic.symbol.setColor(new dojo.Color(C_ZONE_YELLOW));
                }
                else if(stat == DEV_STAT_OFFLINE){
                    cgraphic.symbol.setColor(new dojo.Color(C_ZONE_GRAY));
                    cgraphic.infoTemplate.setContent(cgraphic.infoTemplate.content.replace(/chefang.png/,"bufang.png"));
                    cgraphic.infoTemplate.setContent(cgraphic.infoTemplate.content.replace(/撤防/,"布防"));
                    cgraphic.infoTemplate.setContent(cgraphic.infoTemplate.content.replace(/:布防/,":撤防"));
                }
                */
                //cgraphic.draw();
                //break;
              }
              if(att != null){
                var attAry = new Array();
                attAry.push(att); 
                addZone(attAry);
              }
            }
        
        }
        
        /** 防区操作，删除 */
        var deleteZone = function(zoneID){
            var cgraphic;
            //for(var i=0; i<zoneGraphicLayer.graphics.length;i++)
            for(var i=(zoneGraphicLayer.graphics.length-1); i>=0;i--)
            {
		      cgraphic = zoneGraphicLayer.graphics[i];
		      if(cgraphic.attributes.id == zoneID){
		          zoneGraphicLayer.remove(cgraphic);
		          //i--;
		          //break;
		      }
		      
            }
            MyMap.infoWindow.hide();
        }
        
        //双屏联动，地图聚焦
        var focusPoint = function(msgType,message){
            /*if(msgType == "zone"){
                var zoneID = message.zoneID;
                focusZone(zoneID);
            }
            else */ 
            if(msgType == ALARM_TYPE_SECURITY){
                var msgObj = eval('(' + message + ')');
                var alarmID = msgObj.alarmID;
                //var alarmType = message.alarmType;
                focusAlarm(alarmID,msgType);
                MyMap.infoWindow.hide();
            }/*
            else if(msgType == "device"){
                var devID=message.devID;
                var devType = message.devType;
                focusDevice(devID,devType);
            }*/
        }
        
        /* 机构列表 *************************************************************/
        /* 数据类型定义：机构*/
        function orgAttributes(orgId,orgName,lev)
        {
            this.orgId = orgId;
            this.orgName = orgName;
            this.lev =lev;
        }
        var orgAllList = new Array();
        var queryOrg = function()
        {
            var timenow = new Date().getTime();
            var targetUrl =  "${pageContext.request.contextPath}/gis/queryOrganizationAction?d="+timenow;
            alertInfo(targetUrl); 

            $.ajax({
                type : 'GET', 
                url : targetUrl, 
                dataType : 'json', 
                success : function(json) {
                    alertInfo(json); 
                    if(json){
                         $.each(json,function(idx,item){
                                orgAllList.push(new orgAttributes(
                                item.orgId,
                                item.orgNm,
                                item.lev
                                ));
                            }
                        );
                    }
                }, 
                error : function(json) {
                    alertInfo(json.message);
                    alert("管理机构数据加载失败,请重试.."); 
                } 
            });
        }
        
        var getOrgNameFromList = function(orgid){
            for (var i=0;i<orgAllList.length;i++)
            {
                if(orgAllList[i].orgId == orgid){
                    return orgAllList[i].orgName;
                }
            }
        }

    </script>

    <!--地图处理-->
    <script type = "text/javascript" >
        /* 添加地图上的 设备 POI */
        function addDeviceInfo(pDevicePoint)
        {
            //添加poi
            for (var i = 0; i < pDevicePoint.length; i++)
            {
                //var attributes = new deviceAttributes();
                var attributes = pDevicePoint[i];
                /*
                attributes.id = pDevicePoint[i].id;
                attributes.type = pDevicePoint[i].type;
                attributes.name = pDevicePoint[i].name;
                attributes.lat = pDevicePoint[i].lat;
                attributes.lon = pDevicePoint[i].lon;
                attributes.stat = pDevicePoint[i].stat;
                attributes.ip =  pDevicePoint[i].ip;
                attributes.mntName =  pDevicePoint[i].mntName;
                */
                var point = esri.geometry.geographicToWebMercator(new esri.geometry.Point({
                    "x": attributes.lon,
                    "y": attributes.lat,
                    "spatialReference": {
                        "wkid": wkid
                    }
                }));
                
                var symbol = null; 
                var infoTemplate = null; 
                //使用不同的图像显示
                if(attributes.type == 6){//摄像头
                    if(attributes.stat == 0 || attributes.stat == "正常" ){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_CAMERA_GREEN,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else if(attributes.stat == 1 ||  attributes.stat == "故障"){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_CAMERA_YELLOW,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else{
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_CAMERA_GRAY,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }
                    if(attributes.stat == 0){
                    	attributes.stat = "正常";
                    }else{
                    	attributes.stat = "故障 或 离线";
                    }
                    attributes.typeName = "网络摄像头";
                    infoTemplate = new esri.InfoTemplate("摄像头，编号：" + attributes.id,
                        "设备编号：" + attributes.id +
                        "</br>设备类型：摄像头" +
                        "</br>设备名称：" + attributes.name + 
                        "</br>所属管理处：" + attributes.mntName +
                        "</br>设备IP地址：" + attributes.ip +
                        "</br>设备状态：" + attributes.stat +
                        "</br>设备坐标：经度：" + attributes.lon + "；纬度：" + attributes.lat + "；</br>"
                    );
                    /*
                    infoTemplate = new esri.InfoTemplate("摄像头，编号：${id}",
	                    "设备编号：${id}</br>\
	                                                     设备类型：摄像头</br>\
	                                                     设备名称：${name}</br>\
	                                                     所属管理处：${mntName}</br>\
	                                                     设备IP地址：${ip}</br>\
	                                                     设备状态：${stat}</br>\
	                                                     设备坐标：经度：${lon}；纬度：${lat}；</br>"
                    );
                    */
                }else /*if(attributes.type == 2)*/{//围栏主机
                    if(attributes.stat == 1){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_FENCE_GREEN,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else if(attributes.stat == 2){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_FENCE_YELLOW,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else{
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_FENCE_GRAY,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }
                    if(attributes.stat == 1){
                        attributes.stat = "正常";
                    }else if(attributes.stat == 2){
                        attributes.stat = "异常";
                    }
                    else{
                        attributes.stat = "离线";
                    }
                    if(attributes.type == 1){
                        attributes.typeName = "高压脉冲主机";
                    }else if(attributes.type == 2){
                        attributes.typeName = "一体化探测主机";
                    }else if(attributes.type == 3){
                        attributes.typeName = "防区型振动光纤";
                    }else{
                        attributes.typeName = "定位型振动光纤";
                    }

                    infoTemplate = new esri.InfoTemplate("电子围栏，编号：" + attributes.id, 
	                    "设备编号：" + attributes.id +
	                    "</br>设备名称：" + attributes.name +
	                    "</br>设备类型：" + attributes.typeName +
	                    "</br>所属管理处：" + attributes.mntName + 
	                    "</br>设备IP地址：" + attributes.ip +
	                    "</br>设备状态：" + attributes.stat + 
	                    "</br>设备坐标：经度：" + attributes.lon + "；纬度：" + attributes.lat+ "；</br>"
                    );
                }
                
                var graphic = new esri.Graphic(point, symbol, attributes, infoTemplate);
                deviceGraphicLayer.add(graphic);
            }// EOF for
        }/* EOF 添加设备 */

        /*** 绘制防区 ***/
        var addZone = function(pZone)
        {
            //添加poi
            for (var i = 0; i < pZone.length; i++)
            {
                var attributes = pZone[i];

                var line = new esri.geometry.Polyline({
                    "paths": [[
                        [attributes.startLon, attributes.startLat], [attributes.endLon, attributes.endLat]
                    ]]
                     ,"spatialReference": { "wkid": wkid } 
                });
                var lineColor = null;
                var chebufangStr = null;
                var buttonStr = null;
                if(attributes.stat=="布防" || attributes.stat=="ready" || attributes.stat=="1"){
                    lineColor = new dojo.Color(C_ZONE_GREEN);
                    attributes.stat = "布防";
                    //chebufangStr = "<a href=\"javascript:void(0);\" title=\"撤防\" zoneid=\"11\" class=\"zonechefanglink\" >撤防</a>"
                    chebufangStr = "<img src='resources/images/chefang.png'>";
                    buttonStr = "<input id='chebufangButt' type='button' class='gisbtn' style='width:50px;' value='撤防' name='chebufang' onclick='zoneChebufang(" + attributes.id + ")'/>";
                }else if(attributes.stat=="撤防" || attributes.stat=="offline" || attributes.stat=="0"){
                    lineColor = new dojo.Color(C_ZONE_GRAY);
                    attributes.stat = "撤防";
                    //chebufangStr = "<a href=\"javascript:void(0);\" title=\"布防\" zoneid=\"11\" class=\"zonebufanglink\" >布防</a>"
                    chebufangStr = "<img src='resources/images/bufang.png'>";
                    buttonStr = "<input id='chebufangButt' type='button' class='gisbtn' style='width:50px;' value='布防' name='chebufang' onclick='zoneChebufang(" + attributes.id + ")'/>";
                }else{//
                    lineColor = new dojo.Color(C_ZONE_YELLOW);
                }
                var lineSymbol = new esri.symbol.CartographicLineSymbol(
                    esri.symbol.CartographicLineSymbol.STYLE_SOLID,
                    lineColor, 5,
                    esri.symbol.CartographicLineSymbol.CAP_ROUND,
                    esri.symbol.CartographicLineSymbol.JOIN_MITER, 5
                );

                var infoTemplate = new esri.InfoTemplate("防区编号：" + attributes.id, 
                    "防区编号：" + attributes.id +
                    "</br>防区名称：" + attributes.name +
                    "</br>所属管理处：" + attributes.mntName +
                    "</br>防区朝向：" + attributes.orientation +
                    "</br>布防状态：" + attributes.stat +
                    "</br>始点坐标：经度：" + attributes.startLon + "；纬度：" + attributes.startLat + "；" +
                    "</br>终点坐标：经度：" + attributes.endLon + "；纬度：" + attributes.endLat + "；</br>" +
                    chebufangStr + buttonStr);
                var polyline = new esri.Graphic(line, lineSymbol, attributes, infoTemplate);
                //var copypolyline = new esri.Graphic(line, lineSymbol, attributes, infoTemplate);
                zoneGraphicLayer.add(polyline);
                
                /** 画防区两端的点，红色空心圆 ，通过id与防区相关联 **/
                var splitPoint = new esri.geometry.Point({
                    "x": attributes.startLon,
                    "y": attributes.startLat,
                    "spatialReference": {
                        "wkid": wkid
                    }
                });
                var splitePointAttr = new zoneAttributes(
                                attributes.id,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);
                var splitSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE, 10, 
                    new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID, new esri.Color([255,0,0]), 1),
                    new esri.Color([0,255,0,0.25]))
                var splitGraph = new esri.Graphic(splitPoint, splitSymbol, splitePointAttr, null);
                zoneGraphicLayer.add(splitGraph);
                
                splitPoint = new esri.geometry.Point({
                    "x": attributes.endLon,
                    "y": attributes.endLat,
                    "spatialReference": {
                        "wkid": wkid
                    }
                });
                splitSymbol = new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_CIRCLE, 10,
                    new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,new esri.Color([255,0,0]), 1),
                    new esri.Color([0,255,0,0.25]))
                splitGraph = new esri.Graphic(splitPoint, splitSymbol, splitePointAttr, null);
                zoneGraphicLayer.add(splitGraph);
                
                /** EOF 画防区两端的点 **/
                
                //var cgraphic;
                //cgraphic = zoneGraphicLayer
                //searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
                //searchResultGraphicLayer.add(copypolyline);
                //EOF 添加poi
            }// EOF for
            
            /*
            var cgraphic;
                for(var i=0; i<zoneGraphicLayer.graphics.length;i++)
                {
                  cgraphic = zoneGraphicLayer.graphics[i];
                searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
                       
                }
            */
        }/* EOF 绘制防区 */

        /*** 绘制设备告警 ***/
        var addAlarm4Device = function(pAlarm)
        {
            //添加poi
            for (var i = 0; i < pAlarm.length; i++)
            {
                var attributes = pAlarm[i];
                var point = esri.geometry.geographicToWebMercator(new esri.geometry.Point({
                    "x": attributes.lon,
                    "y": attributes.lat,
                    "spatialReference": {
                        "wkid": wkid
                    }                
                }));
                    
                var symbol = null; 
                var infoTemplate = null; 
                //使用不同的图像显示
                    //if(attributes.alarmLevel == ALARM_LEVEL_YELLOW){
                    if(attributes.alarmLevel == "非常严重" || attributes.alarmLevel == ALARM_LEVEL_RED ){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_ALARM_YELLOW,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else if(attributes.alarmLevel == ALARM_LEVEL_ORANGE){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_ALARM_ORANGE,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else{
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_ALARM_RED,ICON_SIZE_GIF,ICON_SIZE_GIF);
                    }
                    infoTemplate = new esri.InfoTemplate("设备告警，编号：" + attributes.alarmID,
	                     "设备编号：" + attributes.deviceId + 
	                     "</br>设备类型：" + attributes.deviceType +
	                     "</br>所属管理处：" + attributes.departmentName+
	                     "</br>告警级别：" + attributes.alarmLevel +
                         "</br>告警防区：" + attributes.departmentName +
                         "</br>告警原因：" + attributes.cause +
                         "</br>设备坐标：经度：" + attributes.lon + "；纬度：" + attributes.lat+ "；</br>");
                var graphic = new esri.Graphic(point, symbol, attributes, infoTemplate);
                alarmGraphicLayer4Device.add(graphic);
                //EOF 添加poi
            }// EOF for
        }/* EOF 绘制设备告警 */


        /*** 绘制安防告警 ***/
        var addAlarm4Security = function(pAlarm)
        {
            //添加poi
            for (var i = 0; i < pAlarm.length; i++)
            {
                var attributes = pAlarm[i];
	            attributes.type = "安防告警";
                var point = esri.geometry.geographicToWebMercator(new esri.geometry.Point({
                    "x": attributes.zoneStartLon,
                    "y": attributes.zoneStartLat,
                    "spatialReference": {
                        "wkid": wkid
                    }
                }));
                    
                var symbol = null; 
                var infoTemplate = null; 
                //根据告警等级使用不同的图像显示
                    if(attributes.severityLvl == ALARM_LEVEL_YELLOW){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_ALARM_YELLOW,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else if(attributes.severityLvl == ALARM_LEVEL_ORANGE){
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_ALARM_ORANGE,ICON_SIZE_PNG,ICON_SIZE_PNG);
                    }else{
                        symbol = new esri.symbol.PictureMarkerSymbol(ICON_ALARM_RED,ICON_SIZE_GIF,ICON_SIZE_GIF);
                    }
                    infoTemplate = new esri.InfoTemplate("安防告警，编号：" +attributes.alarmID,
                       "防区编号：" + attributes.alarmZoneID +
                       "</br>防区名称：" + attributes.alarmZoneName +
                       "</br>所属管理处：" + attributes.mgtName +
                       "</br>告警时间：" + attributes.alarmTime +
                       "</br>告警坐标：经度：" + attributes.zoneStartLon + "；纬度：" + attributes.zoneStartLat + "；</br>");
                var graphic = new esri.Graphic(point, symbol, attributes, infoTemplate);
                alarmGraphicLayer4Security.add(graphic);
                //EOF 添加poi
            }// EOF for
        }/* EOF 绘制安防告警 */

        /* 初始化地图参数 */
        /* 查询初始地图参数，要依据用户id，找到管理处的坐标范围 */
       
        //依据id查询地图载入范围，赋值给变量：startPoint
        /*
        var getInitMapData = function()
        {
            var url = "${pageContext.request.contextPath}/gis/QueryBranchAction"; 
            alertInfo(url); 

            $.ajax({ 
                url : url, 
                type : 'POST', 
                dataType : 'json', 
                success : function(json) { 
                    alertInfo(json); 
                    startPoint = new mapInitParam(json.xmin, json.ymin, json.xmax, json.ymax);
                }, 
                error : function() { 
                    alertInfo("数据加载失败,请重试.."); 
                } 
            }); 
        }
        */

        
        /*** 处理 pushlet 消息 ***/
        //双屏联动,消息类型:取值:devCamera（设备：摄像头）, devFence（设备：电子围栏），securityAlarm（告警：安防），deviceAlarm（告警：设备（保留占位）），zone（防区）
        //目前只有 securityAlarm
        var PUSHLET_FIELD_NAME_CTL_MSGTYPE = "msgType";
        //格式：json，携带设备ID（deviceID），告警ID(alarmID)，防区ID（zoneID）
        var PUSHLET_FIELD_NAME_CTL_MSSSAGE = "message";
        
        var PUSHLET_FIELD_NAME_ALARM_OPERATION = "alarmOperation";
        var PUSHLET_FIELD_NAME_ALARM_ALARMTYPE = "alarmType";
        var PUSHLET_FIELD_NAME_ALARM_MESSAGE = "message";
        
        var PUSHLET_FIELD_NAME_DEVICE_OPERATION = "devOperation";
        var PUSHLET_FIELD_NAME_DEVICE_TYPE = "devType";
        var PUSHLET_FIELD_NAME_DEVICE_MESSAGE = "message";
        
        var OPERATION_ADD = "add";
        var OPERATION_DELETE = "del";
        var OPERATION_CHANGE = "change";
        

        /*** EOF 处理 pushlet 消息 ***/
    
        //按照参数名从浏览器URL中获取传递的参数数值
        var getQueryString = function(paraName) {
            var reg = new RegExp("(^|&)" + paraName + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
        
        var objCopy = function(obj){
            var x = JSON.stringify( obj );
            return JSON.parse( x);
        }

        //依据不同的数据类型和操作，隐藏相应的图层
        //在地图左下角设置复选框，分别是：设备、防区、安防告警、设备告警
        function hideData(type){
            MyMap.infoWindow.hide();
            if(isInSearchData){
                return;
            }
            if(type == "device"){
                if(!document.getElementById("hideDataDevice").checked){
                    deviceGraphicLayer.setVisibility(true);
                }else{
                    deviceGraphicLayer.setVisibility(false);
                    //document.getElementById("hideDataDevice").checked = true;
                }
            }else if(type == "zone"){
                if(!document.getElementById("hideDataZone").checked){
                    zoneGraphicLayer.setVisibility(true);
                    //document.getElementById("hideDataZone").checked = false;
                }else{
                    zoneGraphicLayer.setVisibility(false);
                }
            }else if(type == "alarmSecurity"){
                if(!document.getElementById("hideDataAlarmSecurity").checked){
                    alarmGraphicLayer4Security.setVisibility(true);
                }else{
                    alarmGraphicLayer4Security.setVisibility(false);
                }

            }else if(type == "alarmDevice"){
                if(!document.getElementById("hideDataAlarmDevice").checked){
                    alarmGraphicLayer4Device.setVisibility(true);
                }else{
                    alarmGraphicLayer4Device.setVisibility(false);
                }
            }else if(type == "searchResult"){
                if(!document.getElementById("hideDataSearchResult").checked){
                    searchResultGraphicLayer.setVisibility(true);
                }else{
                    searchResultGraphicLayer.setVisibility(false);
                }
            }
        }
        
        //图层搜索，同时把搜索结果元素显示在searchResultGraphicLayer图层上，把其他图层隐藏
        //参数：类型：type，摄像头-camera，围栏-fence，设备告警-alarmDe，安防告警-alarmSe，防区-zone
        //参数：关键字：key，
        function searchData(type,key){
            MyMap.infoWindow.hide();
            var type = $("#typeselect").val();
            var key = document.getElementById("keywordText").value;
            
            alertInfo("searchData type value is : "+type);
            alertInfo("searchData key value is : "+key);

            searchResultGraphicLayer.clear();//搜索前清空搜索结果
            var resultArray = new Array();
            if(key == null || type == null || key.length == 0){
                alert("搜索参数无效，请输入或选择正确的参数！");
                return resultArray;
            }
            var cgraphic;
            
            if(type == DEV_TYPE_GIS_CAMERA){
	            for(var i=0; i<deviceGraphicLayer.graphics.length;i++)
	            {
	              cgraphic = deviceGraphicLayer.graphics[i];
	              if(cgraphic.attributes.type == 6){
	                   if(String(cgraphic.attributes.id).indexOf(key) != -1){// || String(cgraphic.attributes.name).indexOf(key) != -1){
                            var tmpJsonObj = {
                                "id":cgraphic.attributes.id,
                                "type":cgraphic.attributes.type,
                                "typeName":cgraphic.attributes.typeName,
                                "name":cgraphic.attributes.name
                            };
                            resultArray.push(tmpJsonObj);
			                searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
	                   }
	              }
	            }
            }else if(type == DEV_TYPE_GIS_FENCE){
                for(var i=0; i<deviceGraphicLayer.graphics.length;i++)
                {
                  cgraphic = deviceGraphicLayer.graphics[i];
                  if(cgraphic.attributes.type != 6){
                       if(String(cgraphic.attributes.id).indexOf(key) != -1 || String(cgraphic.attributes.name).indexOf(key) != -1){
                            var tmpJsonObj = {
                                "id":cgraphic.attributes.id,
                                "type":cgraphic.attributes.type,
                                "typeName":cgraphic.attributes.mntName,
                                "name":cgraphic.attributes.stat
            
                            };
                            resultArray.push(tmpJsonObj);
                            searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
                       }
                    }
                }
            }else if(type == "alarmSe"){
                for(var i=0; i<alarmGraphicLayer4Security.graphics.length;i++)
                {
                  cgraphic = alarmGraphicLayer4Security.graphics[i];
                       if(String(cgraphic.attributes.alarmID).indexOf(key) != -1){
                            var tmpJsonObj = {
                                "id":cgraphic.attributes.alarmID,
                                "type":cgraphic.attributes.type,
                                "typeName":cgraphic.attributes.alarmZoneID,
                                "name":cgraphic.attributes.alarmName
                            };
                            resultArray.push(tmpJsonObj);
                            searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
                       }
                }
            }else if(type == "alarmDe"){
                for(var i=0; i<alarmGraphicLayer4Device.graphics.length;i++)
                {
                  cgraphic = alarmGraphicLayer4Device.graphics[i];
                       if(String(cgraphic.attributes.alarmID).indexOf(key) != -1){
                            var tmpJsonObj = {
                                "id":cgraphic.attributes.alarmID,
                                "type":cgraphic.attributes.type,
                                "typeName":cgraphic.attributes.alarmName,
                                "name":cgraphic.attributes.deviceId
                            };
                            resultArray.push(tmpJsonObj);
                            searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
                       }
                }
            }else if(type == "zone"){
                for(var i=0; i<zoneGraphicLayer.graphics.length;i++)
                {
                  cgraphic = zoneGraphicLayer.graphics[i];
                  if(cgraphic.attributes == null){//排除端点
                    continue;
                  }
                       if(String(cgraphic.attributes.id).indexOf(key) != -1 || (cgraphic.attributes.name != null && String(cgraphic.attributes.name).indexOf(key) != -1)){
                            if(cgraphic.attributes.name == null){
                                searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),null));
                                continue;
                            }
                            var tmpJsonObj = {
                                "id":cgraphic.attributes.id,
                                "type":cgraphic.attributes.mntName,
                                "typeName":cgraphic.attributes.orientation,
                                "name":cgraphic.attributes.name
                            };
                            resultArray.push(tmpJsonObj);
                            searchResultGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
                       }
                }
            }
            
            if(resultArray.length == 0){
                alert("没有找到符合条件的数据，请重新选择或输入查询条件后重试！");
            }else{
	            deviceGraphicLayer.setVisibility(false);
	            zoneGraphicLayer.setVisibility(false);
	            alarmGraphicLayer4Security.setVisibility(false);
	            alarmGraphicLayer4Device.setVisibility(false);
	            
	            searchResultGraphicLayer.setVisibility(true);
	
	            displaySearchResult(resultArray);
	            
	            isInSearchData = true;
            }
        }
        //在div中显示搜索结果，在地图上的单独图层显示所有搜索结果，是否需要编号，编号如何处理？
         function displaySearchResult(searchResult){
         	var type = $("#typeselect").val();//搜索时选择的项目类型
         	var node = "";
            for(var i = 0; i< searchResult.length; i++){
            	var innerHtml = "<div class='singleresult' onclick='singleResultDisplayOnMap(this);' resultid='"+searchResult[i].id+"' objtypeid ='"+searchResult[i].type +"'>";
            	innerHtml+= "<table class='gisdetail_table'>";
            	
            	
	            if(type == DEV_TYPE_GIS_CAMERA){
	            	innerHtml+= "<tr><th>摄像头ID:</th><td>"+searchResult[i].id+"</td></tr>";
					innerHtml+= "<tr><th>类型名称:</th><td title='"+searchResult[i].typeName+"' nowrap>"+searchResult[i].typeName+"</td></tr>";
            		innerHtml+= "<tr><th>名称:</th><td title='"+searchResult[i].name+"' nowrap>"+searchResult[i].name+"</td></tr>";
	            }else if(type == DEV_TYPE_GIS_FENCE){
	            	innerHtml+= "<tr><th>电子围栏ID:</th><td>"+searchResult[i].id+"</td></tr>";
					innerHtml+= "<tr><th>所属管理处:</th><td title='"+searchResult[i].typeName+"' nowrap>"+searchResult[i].typeName+"</td></tr>";
            		innerHtml+= "<tr><th>状态:</th><td title='"+searchResult[i].name+"' nowrap>"+searchResult[i].name+"</td></tr>";
	            }else if(type == "alarmSe"){
	            	innerHtml+= "<tr><th>安防告警ID:</th><td>"+searchResult[i].id+"</td></tr>";
					innerHtml+= "<tr><th>告警防区ID:</th><td title='"+searchResult[i].typeName+"' nowrap>"+searchResult[i].typeName+"</td></tr>";
            		innerHtml+= "<tr><th>告警类型:</th><td title='"+searchResult[i].name+"' nowrap>"+searchResult[i].name+"</td></tr>";
	            }else if(type == "alarmDe"){
	            	innerHtml+= "<tr><th>设备告警ID:</th><td>"+searchResult[i].id+"</td></tr>";
					innerHtml+= "<tr><th>告警类型:</th><td title='"+searchResult[i].typeName+"' nowrap>"+searchResult[i].typeName+"</td></tr>";
            		innerHtml+= "<tr><th>设备编号:</th><td title='"+searchResult[i].name+"' nowrap>"+searchResult[i].name+"</td></tr>";
	            }else if(type == "zone"){
	            	innerHtml+= "<tr><th>防区ID:</th><td>"+searchResult[i].id+"</td></tr>";
					innerHtml+= "<tr><th>所属管理处:</th><td title='"+searchResult[i].type+"' nowrap>"+searchResult[i].type+"</td></tr>";
            		innerHtml+= "<tr><th>朝向:</th><td title='"+searchResult[i].typeName+"' nowrap>"+searchResult[i].typeName+"</td></tr>";
	            }
	            
            	innerHtml+= "</table>";
            	innerHtml = innerHtml +"</div>";
            	node+=innerHtml;
            }
            $("#searchResult").empty();
            $("#searchResult").html(node);
        }
        
        //与地图同步       
        function singleResultDisplayOnMap(obj){
            var type = $("#typeselect").val();
            var objid = $(obj).attr("resultid");
            var typeid = $(obj).attr("objtypeid");
            if(type == DEV_TYPE_GIS_CAMERA){
                focusDevice(objid,6);
            }else if(type == DEV_TYPE_GIS_FENCE){
                focusDevice(objid,typeid);
            }else if(type == "alarmSe"){
                focusAlarm(objid,ALARM_TYPE_SECURITY);
            }else if(type == "alarmDe"){
                focusAlarm(objid,ALARM_TYPE_DEVICE);
            }else if(type == "zone"){
                focusZone(objid);
            }
            //alert(objid);
        }
        
    </script>



    <script type="text/javascript">

    var myLayout; // a var is required because this page utilizes: myLayout.allowOverflow() method

    $(document).ready(function () {

        myLayout = $('body').layout({
            west__size:                 350
        ,   west__spacing_closed:       20
        ,   west__resizable:           false
        ,   west__togglerLength_closed: 100
        ,   west__togglerAlign_closed:  "top"
        ,   west__togglerContent_closed:"搜<BR><BR><BR>索<BR>"
        ,   west__togglerTip_closed:    "Open & Pin Menu"
        ,   west__sliderTip:            "Slide Open Menu"
        ,   west__slideTrigger_open:    "mouseover"
        ,   center__maskContents:       true // IMPORTANT - enable iframe masking
        //,	center__height:	200
        //,   west__onhide_start:               function (){alert("sss");} // CALLBACK when pane STARTS to Close   - BEFORE onclose_start
        });
        $("#searchDiv").height($(document).height()+100);
        //$("#MyMapDiv").height($(document).height()+100);

    });

    </script>


    <style type="text/css">
    /**
     *  Basic Layout Theme
     */
     
    .ui-layout-center{
        background-color: #FFFFFF;
        height:100%;
    }
    
    .ui-layout-pane { /* all 'panes' */ 
        border: 1px solid #BBB; 
    } 
    .ui-layout-pane-center { /* IFRAME pane */ 
        padding: 0;
        margin:  0;
    } 
    .ui-layout-pane-west { /* west pane */ 
        padding: 0 10px; 
        background-color: #EDF6FA !important;
        overflow: auto;
    } 

    .ui-layout-resizer { /* all 'resizer-bars' */ 
        background: #DDD; 
        } 
        .ui-layout-resizer-open:hover { /* mouse-over */
            background: #9D9; 
        }

    .ui-layout-toggler { /* all 'toggler-buttons' */ 
        background: #AAA; 
        } 
        .ui-layout-toggler-closed { /* closed toggler-button */ 
            background: #CCC; 
            border-bottom: 1px solid #BBB; 
        } 
        .ui-layout-toggler .content { /* toggler-text */ 
            font: 14px bold Verdana, Verdana, Arial, Helvetica, sans-serif;
        }
        .ui-layout-toggler:hover { /* mouse-over */ 
            background: #DCA; 
            } 
            .ui-layout-toggler:hover .content { /* mouse-over */ 
                color: #009; 
                }

        /* masks are usually transparent - make them visible (must 'override' default) */
        .ui-layout-mask {
            background: #C00 !important;
            opacity:    .20 !important;
            filter:     alpha(opacity=20) !important;
        }


    body {
        background-color: black;
        font-family: Geneva, Arial, Helvetica, sans-serif;
    }

    ul { /* basic menu styling */
        margin:     1ex 0;
        padding:    0;
        list-style: none;
        position:   relative;
    }
    li {
        padding: 0.15em 1em 0.3em 5px;
    }
    
    html, body, #MyMap {
        height: 100%;
    }
    </style>
    
    <script type="text/javascript">
        var searchDataType = "alarmSe";
	    var selectSearchType = function(dataType){
            if(dataType == DEV_TYPE_GIS_CAMERA){
                searchDataType = DEV_TYPE_GIS_CAMERA;
            }else if(dataType== DEV_TYPE_GIS_FENCE){
                searchDataType = DEV_TYPE_GIS_FENCE;
            }else if(dataType== "zone"){
                searchDataType = "zone";
            }else if(dataType== "alarmSe"){
                searchDataType = "alarmSe";
            }else if(dataType== "alarmDe"){
                searchDataType = "alarmDe";
                //document.getElementById("dataTypeFence").checked=false;
            }
	       alertInfo(searchDataType);
        }
        
        //清除搜索结果:清除图层上的grahpic,以及div上的html
        var clearSearchResult = function(){
            //需要依据数据隐藏复选框的值确定图层的隐藏与否
            if(!document.getElementById("hideDataZone").checked){
                zoneGraphicLayer.setVisibility(true);
            }else{
                zoneGraphicLayer.setVisibility(false);
            }
            if(!document.getElementById("hideDataDevice").checked){
                deviceGraphicLayer.setVisibility(true);
            }else{
                deviceGraphicLayer.setVisibility(false);
            }
            if(!document.getElementById("hideDataAlarmSecurity").checked){
                alarmGraphicLayer4Security.setVisibility(true);
            }else{
                alarmGraphicLayer4Security.setVisibility(false);
            }
            
            if(!document.getElementById("hideDataAlarmDevice").checked){
                alarmGraphicLayer4Device.setVisibility(true);
            }else{
                alarmGraphicLayer4Device.setVisibility(false);
            }
            
            //隐藏图层、清除数据
            searchResultGraphicLayer.setVisibility(false);
            searchResultGraphicLayer.clear();
            
            var searchDiv = document.getElementById("searchResult");
            var innerHtml = "<table></table>";
            searchDiv.innerHTML = innerHtml;
            
            MyMap.infoWindow.hide();
            isInSearchData = false;
        }
        
        /* 防区撤布防 */
        var zoneChebufang = function(zoneID){
            //首先搜索防区图层,确认防区参数,撤布防后,还需要更新infotemplate内容和颜色,可以用新建来处理
            var cgraphic = null;
            
            var zoneGeometry = null;
            var zoneSymbol = null;
            var zoneAttr = null;
            var zoneInfoTemp = null;
            
            for(var i=0; i<zoneGraphicLayer.graphics.length;i++)
            {
                cgraphic = zoneGraphicLayer.graphics[i];
                if(cgraphic.attributes.id == zoneID){
                    zoneGeometry = cgraphic.geometry;
		            zoneSymbol = cgraphic.symbol;
		            zoneAttr = cgraphic.attributes;;
		            zoneInfoTemp = cgraphic.infoTemplate;
		            
                    break;
                    //zoneGraphicLayer.remove(cgraphic);
                    //zoneGraphicLayer.add(new esri.Graphic((cgraphic.geometry),(cgraphic.symbol),(cgraphic.attributes),(cgraphic.infoTemplate)));
                }
            }
            if(zoneAttr == null){
                alert("地理信息界面中的数据已经过期，请刷新界面后重试");
                return;
            }

            //判断是撤防还是布防
            if(zoneAttr.stat == 1 || zoneAttr.stat == "布防" || zoneAttr.stat == "ready"){//现在是布防,目标:撤防
                var targetUrl = "${pageContext.request.contextPath}/zone/zoneCtrl?zoneBean.status=0&zoneBean.zoneID="+zoneID;
                if(confirm("确定要撤防吗?"))
		        {
		            jQuery.ajax({
		                type : 'GET',
		                cache:false,
		                url : targetUrl,
		                contentType: "application/json; charset=utf-8",
		                beforeSend:function(XMLHttpRequest){//显示装载……图标
		                     $("#data_loading").show();
		                },
		                success : function(data) {
		                    if(data.result==1)
		                    {
		                      //修改地图
                                zoneGraphicLayer.remove(cgraphic);
                                zoneAttr.stat = "撤防";
                                var zonArray = new Array();
                                zonArray.push(zoneAttr);
                                addZone(zonArray);
                                MyMap.infoWindow.hide();
                                zoneGraphicLayer.redraw();
                                alert("编号为：" + zoneAttr.id + "的防区 撤防 成功！");
                                return true;
		                    }
		                    else
		                    {
		                        alert("撤防失败!\n您没有相关的操作权限 或 设备当前不允许此操作，请与系统管理员联系获取帮助！");
		                    }
		                },
		                error : function(data) {
		                    alert("撤防操作出现异常!\n网络连接错误 或 设备当前不允许此操作，请与系统管理员联系获取帮助！");
		                }
		            });
		        }
            }else{
                var targetUrl = "${pageContext.request.contextPath}/zone/zoneCtrl?zoneBean.status=1&zoneBean.zoneID="+zoneID;
		        if(confirm("确定要布防吗?"))
		        {
		            jQuery.ajax({
		                type : 'GET',
		                cache:false,
		                url : targetUrl,
		                contentType: "application/json; charset=utf-8",
		                beforeSend:function(XMLHttpRequest){//显示装载……图标
                             $("#data_loading").show();
                        },
		                success : function(data) {
		                    if(data.result==1)
		                    {
                              //修改地图
                                zoneGraphicLayer.remove(cgraphic);
                                zoneAttr.stat = "布防";
                                var zonArray = new Array();
                                zonArray.push(zoneAttr);
                                addZone(zonArray);
                                MyMap.infoWindow.hide();
                                zoneGraphicLayer.redraw();
                                alert("编号为：" + zoneAttr.id + "的防区 布防 成功！");
		                        return true;
		                    }
		                    else
		                    {
                                alert("布防失败!\n您没有相关的操作权限 或 设备当前不允许此操作，请与系统管理员联系获取帮助！");
		                    }
		                },
		                error : function(data) {
		                    alert("布防操作出现异常!\n网络连接错误 或 设备当前不允许此操作，请与系统管理员联系获取帮助！");
		                }
		            });
		        }
            }
        }
    </script>
    
    <!-- 全局数据和变量定义，以及初始化-->
    <script type="text/javascript">

        /*** 初始化地图界面 ***/
        var gisInitFun = function ()
        {
            //查询数据

            //alert(startPoint.xmax);
            queryDevice();
            queryAlarm();
            queryZone();
            queryOrg();
            
            //地图范围
            var startExtent = new esri.geometry.Extent(
                {
                    "xmin": startPoint.xmin,
                    "ymin": startPoint.ymin,
                    "xmax": startPoint.xmax,
                    "ymax": startPoint.ymax,
                    "spatialReference": { "wkid": wkid }
                }
                );

            //MyMap = new esri.Map("MyMapDiv", { extent: startExtent,"minZoom":11,"maxZoom":15});//动态地图
            MyMap = new esri.Map("MyMapDiv", { extent: startExtent});//静态地图

            //装载底图
             var imagePar = new esri.layers.ImageParameters();
             //   imageParameters.format = "jpeg";
             imagePar.format = "png32";
             
            //20150519，改成静态装载底图，动态装载管线
            
            //var MyMapServiceLayer = new esri.layers.ArcGISTiledMapServiceLayer  //静态地图
            var MyMapServiceLayer = new esri.layers.ArcGISDynamicMapServiceLayer  //动态地图
            (
                arcgisMapUrl
                //"http://www.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer"
                ,{"opacity" : 1,                  //动态地图
                "imageParameters" : imagePar}     //动态地图
            );
            
            /*
            var MyMapServiceLayer = new esri.layers.ArcGISTiledMapServiceLayer  //静态底图
            (
                arcgisMapUrlBaseMap
            );
            
            var MyMapServiceLayerPipe = new esri.layers.ArcGISDynamicMapServiceLayer  //动态地图
            (
                arcgisMapUrl
                //"http://www.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer"
                ,{"opacity" : 1,                  //动态地图
                "imageParameters" : imagePar}     //动态地图
            );
            */
            
            //alertInfo("max zoom is : " + MyMap.getMaxZoom());
            //alertInfo("min zoom is : " + MyMap.getMinZoom());
            //alertInfo("cur zoom is : " + MyMap.getZoom());
            
            MyMap.addLayer(MyMapServiceLayer);
            //MyMap.addLayer(MyMapServiceLayerPipe);
            
            MyMap.showZoomSlider();
                        
            var overviewMapDijit = new esri.dijit.OverviewMap({  
                    map: MyMap,  
                    width: 355,
                    height: 200,
                    attachTo: "bottom-right",
                    expandFactor: 1.5,//鹰眼图与大图的比例
                    color: "#AAAAAA",
                    opacity: 0.50,//鹰眼图蒙布的透明度
                    visible: true
                    //maximizeButton:true
                    ,baseLayer:MyMapServiceLayer
                });
                //},dojo.byId('overview'));  
                //});
            overviewMapDijit.startup();
            
            //添加初始查询的数据
        
            //装载数据图层
            MyMap.addLayer(searchResultGraphicLayer);
            searchResultGraphicLayer.setVisibility(false);//初始化时隐藏搜索结果图层
                                    
            MyMap.addLayer(zoneGraphicLayer);
            MyMap.addLayer(deviceGraphicLayer);
            MyMap.addLayer(alarmGraphicLayer4Device);
            MyMap.addLayer(alarmGraphicLayer4Security);

        }
        /*** EOF 初始化地图界面 ***/
        
        /*************** 常量定义 *****************/
        var parameterNameUserID = "userID";
        var parameterNameOrgID = "orgID";

        var localEventKey = "empty";//定义监听的时间关键字
        var remoteEventKey = "empty";
        var localWarningEventKey = "empty";
        
        var userIDValue = getQueryString(parameterNameUserID);
        var orgIDValue = getQueryString(parameterNameOrgID);
        
        //alertInfo("UserID value is: " + userIDValue);
        //alertInfo("OrgID value is: " + orgIDValue);
        
        //根据传递的参数初始化 Pushlet，如果调用页面的时候没有传递参数，直接提醒和关闭窗口
        if(userIDValue == null || orgIDValue == null){
            alert("警告：缺少参数！GIS窗口无法正常接收服务器消息！\n请关闭窗口，并从业务系统主窗口提供的连接打开GIS窗口！");
            window.close();
        }else{
            dojo.addOnLoad(gisInitFun);
        }
    </script>
</head>

<!--html 主体-->

<body class="tundra">

    <c:import url="/WEB-INF/views/gis/header.jsp"  charEncoding="UTF-8"></c:import>
    
<div id="MyMapDiv" class="ui-layout-center">
    <div class="gislogo">

	</div>
</div>
<div id="searchDiv" class="ui-layout-west" style="overflow:hidden;"> 
     <div id="searchInput">
         <input id="keywordText" class="gistext" style="width:280px;" type="text" value="" name="keyword"  title="隐藏防区数据"/>
         <div>
         	<select class="gisselect" style="width:220px;" id="typeselect">
         		<option value="alarmSe">安防告警</option>
         		<option value="alarmDe">设备告警</option>
         		<option value="zone">防区</option>
         		<option value="camera">摄像头</option>
         		<option value="fence">电子围栏</option>
         	</select>
         	<input id="searchButt" class="gisbtn" type="button" value="搜索" style="width:70px;" name="keyword"  title="隐藏防区数据" onclick="searchData()"/>
         </div>
         <!-- <div>
         	<input id="dataTypeAlarmSe" type="radio" value="alarmSe" checked="checked" onclick="selectSearchType('alarmSe')" name="searchDataType"><span class="radiospan">安防告警</span>
         	<input id="dataTypeAlarmDe" type="radio" value="alarmDe" onclick="selectSearchType('alarmDe')" name="searchDataType"><span class="radiospan">设备告警</span>
         </div>
         <div style="margin-top:3px;">
         	<input id="dataTypeZone" type="radio" value="zone" onclick="selectSearchType('zone')" name="searchDataType"><span class="radiospan">防区</span>
         	<input id="dataTypeCamera" type="radio" value="camera" onclick="selectSearchType('camera')" name="searchDataType"><span class="radiospan">摄像头</span>
         	<input id="dataTypeFence" type="radio" value="fence" onclick="selectSearchType('fence')" name="searchDataType"><span class="radiospan">电子围栏</span>
         </div> -->
     </div>
     <div id="searchResult"></div>
     <div id="bottomDivContainer">
     	<div id="clearSearchResult">
        	<input id="clearSearchResultButt" type="button" class="gisbtn" style="width:325px;" value="清除搜索结果" name="clearSearchResult" onclick="clearSearchResult()"/>
      	</div>
      	<div id="SelectData">
	          <div>
	          	<input id="hideDataZone" type="checkbox" value="0" name="zone" onclick="hideData('zone')" title="隐藏防区数据"/><span class="radiospan">隐藏防区数据</span>
	          </div>
	          <div>
	          	<input id="hideDataDevice" type="checkbox" value="1" name="device" onclick="hideData('device')" title="隐藏设备数据"/><span class="radiospan">隐藏设备数据</span>
	          </div>
	          <div>
	          	<input id="hideDataAlarmDevice" type="checkbox" value="2" name="deviceAlarm" onclick="hideData('alarmDevice')" title="隐藏设备告警数据"/><span class="radiospan">隐藏设备告警数据</span>
	          </div>
	          <div>
	          	<input id="hideDataAlarmSecurity" type="checkbox" value="3" name="securityAlarm" onclick="hideData('alarmSecurity')" title="隐藏安防告警数据"/><span class="radiospan">隐藏安防告警数据</span>
	          </div>
      	</div>
     </div>
</div>       
</body>
</html>  