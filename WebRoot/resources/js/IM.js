
 var localEvent ;
 var remoteEvent ;
 var localWarning;
 var localOperation;  
 var pushletPing;
 var pushletClose;
 
 var remotId;
 var remotRefresh;
 var sessionLifeTime = 18000;	//ms
 var pushletLogin;
 
function linkageInit(userID, orgID, regType)
{
        	PL._init(); //无需初始化 否则chrome下会报错  
            /*根据在联动控制中的角色，分别侦听不同类型的消息：
        	  gispage（联动中的GIS界面）可以接收联动主控界面发送过来的消息
        	  mainctrl（联动主控界面）可以接收GIS界面发送过来的消息
        	*/
        	if ( regType == "mainctrl" )
        		{
        			remoteEvent = "/linkageCtrl/"+orgID+"/"+userID+"/GIS";
                	localEvent = "/linkageCtrl/"+orgID+"/"+userID+"/MC";
         			localWarning = "/warning/mc/"+orgID;
         			localOperation = "/operation/mc/"+orgID;
         		}
        	else if (regType == "gispage")
        	{
        		remoteEvent = "/linkageCtrl/"+orgID+"/"+userID+"/MC";
                //alert(eventMC);
                localEvent = "/linkageCtrl/"+orgID+"/"+userID+"/GIS";
                localWarning = "/warning/gis/"+orgID;
                localOperation = "/operation/gis/"+orgID;
        	}
        	pushletPing = "/pushlet/ping/"+userID;
        	pushletClose = "/pushlet/close/"+userID;
        	pushletLogin= "/pushlet/login/"+userID;
        	//监听该主题的事件，如果发生该主题的事件，那么onData()方法会被调用  
        	joinListen();
}

//取消debug模式
PL.setDebug(false);

//监听服务
function joinListen() {
	//监听该主题的事件，如果发生该主题的事件，那么onData()方法会被调用  
	PL.joinListen(remoteEvent+"," + localWarning + "," + localOperation
				+ "," + pushletPing + "," + pushletClose + ",helloevent");

}

// 服务器回调事件 登录之后回调
function onData(event) {
	//取得发送方的sessionID
	var remotIdTmp =  event.get("p_from");
	if (event.getSubject() == pushletPing || event.getSubject() == pushletLogin ) {
		if( (PL.sessionId != event.get("p_from")) && (event.get("name")== remoteEvent))
		//进行对端用户的添加
		var remotInfo = event.get("p_from") ;
		//alert(localEvent + "remot id is = "+event.get("p_from"));
		//alert("remot name is = "+event.get("name"));
		remotId = remotInfo;
		remotRefresh = event.get("p_time");
		
	}
	else if (event.getSubject() == pushletClose  ) {
		
		if( event.get("name") == remoteEvent )
		//进行对端用户的删除
		onCloselinkagectrl();
	}
	else
	{
		//这里将后台告警推送和操作推送给界面的top
		PL._doCallback(event, window.onTopData);
		//这里拦截的是联动的信息  、后台告警推送和操作推送给应用部分
		PL._doCallback(event, window.onAppData);
	}
	
}


//借助一定周期的RefreshAck事件  发送我的在线信息
//是为了提供给刚上线的user 并监控在线用户的时间戳是否较新  否则判断为掉线 踢掉
//Refresh是为了让服务器知道client没死
function onRefreshAck(event) {
	//当前时间戳-上次更新的时间戳 如果间隔较大 说明没更新  掉了
	if ((remotId!=null)&&((event.get("p_time") - remotRefresh ) > sessionLifeTime) ) {
		alert("remot gis has removed!\n");
		remotId = null;
		remotRefresh = null;  //删除
	} 
	
	publish();
}

//发布ping信息
function publish() {
	//与服务器建立连接 建立session  并监听事件
//  /pushlet/ping  是为了与服务器保持心跳  知道服务器没死
	
	PL.publish(pushletPing, "name=" + localEvent);
}
//发布上线信息
function publishLogin() {
	//与服务器建立连接 建立session  并监听事件
//  /pushlet/ping  是为了与服务器保持心跳  知道服务器没死
	
	PL.publish(pushletLogin, "name=" + localEvent);
}

//上线后马上发布我的上线信息
function onJoinListenAck(event) {
	publishLogin();
}

//关闭对方
function onCloselinkagectrl() {
	if ((remotId!=null)) {
		alert("Close remot gis!\n");
		remotId = null;
		remotRefresh = null;  //删除
	} 
}
//关闭联动窗口
function closelinkagectrl() {
	PL.publish(pushletClose, "name=" + localEvent);
}

function sendLinkageMsg(msgType, msgBody) {
	//得到要发送信息的内容
	//alert("send:"+msgType+"/"+msgBody);
	//alert("remotId:"+remotId);
	PL.publish(localEvent, "msgType=" + msgType + "&message=" + msgBody);
    
}
