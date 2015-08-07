function trDoubleClick(obj,userLev)
{
	$("#data_loading").show();
	
	closeAllPlaying();
	
	$("#gaojingchuliajaxform")[0].reset();//重置form表单
	var indexGojing = $(obj).attr("chuliIndex");
	var fangquID = $("#gaojingchuli"+indexGojing+"_fangquid").text();
	if(userLev == "2")
	{
		$("#shebeiid").val($("#gaojingchuli"+indexGojing+"_shebeiid").text());
		$("#gaojingbianhao").val($("#gaojingchuli"+indexGojing+"_gaojingbianhao").text());
		$("#fangquid").val($("#gaojingchuli"+indexGojing+"_fangquid").text());
		$("#yanzhongchengduLv").val($("#gaojingchuli"+indexGojing+"_yanzhongchengduLv").text());
	}
	else
	{
		$("#gaojingbianhao").val($("#gaojingchuli"+indexGojing+"_gaojingbianhao").text());
		$("#fangquid").val($("#gaojingchuli"+indexGojing+"_fangquid").text());
		$("#yanzhongchengduLv").val($("#gaojingchuli"+indexGojing+"_yanzhongchengduLv").text());
		$(".detail_table tbody tr .content_gaojingbianhao").html($("#gaojingchuli"+indexGojing+"_gaojingbianhao").text());
		$(".detail_table tbody tr .content_fuheyiju").html($("#gaojingchuli"+indexGojing+"_fuheyiju").text());
		$(".detail_table tbody tr .content_baojingleixing").html($("#gaojingchuli"+indexGojing+"_baojingleixing").text());
		$(".detail_table tbody tr .content_yanzhongjibie").html($("#gaojingchuli"+indexGojing+"_yanzhongjibie").text());
		$(".detail_table tbody tr .content_shijianyuanyin").html($("#gaojingchuli"+indexGojing+"_shijianyuanyin").text());
		$(".detail_table tbody tr .content_shijianguocheng").html($("#gaojingchuli"+indexGojing+"_shijianguocheng").text());
		$(".detail_table tbody tr .content_fuzeren").html($("#gaojingchuli"+indexGojing+"_fuzeren").text());
		$(".detail_table tbody tr .content_beizhu").html($("#gaojingchuli"+indexGojing+"_fujiaxinxi").text());
	}
	
	$("#gaojingchuliTable .clickbgcolor").removeClass("clickbgcolor");
	$(obj).addClass("clickbgcolor");
	
	//sendLinkageMsg("securityAlarm",$("#gaojingchuli"+indexGojing+"_gaojingbianhao").text());
	sendLinkageMsg("securityAlarm","{alarmID:"+$("#gaojingchuli"+indexGojing+"_gaojingbianhao").text()+"}");
	
	var beginDate = $.trim($("#gaojingchuli"+indexGojing+"_gaojingshijian").text());
	
	jQuery.ajax({
		type : 'GET',
		dataType:'json',
		url : $("#ctx").val()+"/videomonitor/searchAlarmVideo?defectId="+fangquID+"&beginDate="+beginDate,
		contentType: "application/json; charset=utf-8",
		beforeSend:function(XMLHttpRequest){
			$("#data_loading").show();
	    },
		success : function(data) {
			$('#iplaybackjson').val(beginDate);
			var jsonString = eval($.parseJSON(data));
			var errormsg = '';
			var jsonData1 = JSON.stringify(jsonString[0]);
			if(jsonString[0] && jsonString[0].channelIp){
				updateV1(jsonData1);
				doipcplay(jsonData1,0);
			}else{
				errormsg+="1,";
			}				
			var jsonData2 = JSON.stringify(jsonString[1]);
			if(jsonString[1] && jsonString[1].channelIp){
				doipcplay(jsonData2,1);
			}else{
				errormsg+="2,";
			}
			var jsonData3 = JSON.stringify(jsonString[2]);
			if(jsonString[2] && jsonString[2].channelIp){
				updateV2(jsonData3);
				doipcplay(jsonData3,2);
			}else{
				errormsg+="3,";
			}
			var jsonData4 = JSON.stringify(jsonString[3]);
			if(jsonString[3] && jsonString[3].channelIp){
				updateV3(jsonData4);
				doipcplay(jsonData4,3);
			}else{
				errormsg+="4,";
			}
			if(errormsg.length!=0){
				errormsg = "第" + errormsg.substring(0, errormsg.length - 1) + "窗口没有查询到关联摄像机播放参数！";
				alert(errormsg);
			}
		},
		complete:function(XMLHttpRequest,textStatus){
			$("#data_loading").hide();
	    },
		error : function(data) {
			$("#data_loading").hide();
			alert("请求出现异常!");
		}
	});
	$("#data_loading").hide();
	$("#contab03").empty();//这里清空图片的内容
	
	closeAllPlaying();
	
	displayAutoImage($("#gaojingchuli"+indexGojing+"_pics").text());
	
	return false;
}

function displayAutoImage(pics){
	if(!pics || pics.trim().length == 0){
		//pics = "image/1.jpg,image/2.jpg,image/win-16.png,";
		return;
	}
	var picsarray = pics.split(',');
	for(var i=0;i<picsarray.length;i++){
		var pic = picsarray[i];
		if(!pic || pic.length==0){
			continue;
		}
		var location = (window.location+'').split('/'); 
		var basePath = location[0]+'//'+location[2]+'/'+location[3];
		$('#contab03').append("<div><img src='"+basePath + "/" +pic+"' style='width:90%;height:240px;margin:5px'/><img src='<%=basePath%>resources/images/delete1.png' style='width:5%;' onclick='deleteimg($(this).parent())'/></div>");
	}
	$("#iautopicture").val(pics);
}

function closeAllPlaying(){
	//关闭已经打开的窗口视频
	for(var i=0;i<4;i++){
		doipcstop(i);
	}
}

function ajaxReport(obj)
{
	if($("#shebeiid").val()==""||$("#gaojingbianhao").val()==""||$("#fangquid").val()=="")
	{
		alert("请先双击选择告警，然后再上报");
		return false;
	}
	else
	{
		var gaojingbianhaoVal = $("#gaojingbianhao").val();
		$("#gaojingchuliajaxform").ajaxSubmit({
		     type: "post",
		     url: $("#ctx").val()+"/linkagectl/reportSecurityAlarm",
		     dataType: "json",
			 resetForm: true,
		     beforeSubmit:function(e){
		    	 $("#data_loading").show();
		     },
		     success : function(data) {
				if(data.result==1)
				{
					alert("告警已经上报");
					$("#shebeiid").val("");
					$("#gaojingbianhao").val("");
					$("#fangquid").val("");
					$("#yanzhongchengduLv").val("");
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					var currentAnfangCount = $("#socket_anfanggaojing").text();
					$("#socket_anfanggaojing").text(parseInt(currentAnfangCount)-1);
					$("#contab03").empty();//这里清空图片的内容
					
					closeAllPlaying();
				}
				else if(data.result ==2)
				{
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					alert("该条数据已经处理，请不要重复操作，可以刷新页面获取最新列表!");
				}
				else
				{
					alert("告警上报失败");
				}
			},
			complete:function(e){
				$("#data_loading").hide();
		    },
			error : function(data) {
				alert("请求出现异常!");
				$("#data_loading").hide();
			}
		});
	}
}
function ajaxConfirm(obj)
{
	if($("#shebeiid").val()==""||$("#gaojingbianhao").val()==""||$("#fangquid").val()=="")
	{
		alert("请先双击选择告警，然后再确认");
		return false;
	}
	else
	{
		var gaojingbianhaoVal = $("#gaojingbianhao").val();
		$("#gaojingchuliajaxform").ajaxSubmit({
		     type: "post",
		     url: $("#ctx").val()+"/linkagectl/confirmSecurityAlarm",
		     dataType: "json",
			 resetForm: true,
		     beforeSubmit:function(e){
		    	 $("#data_loading").show();
		     },
		     success : function(data) {
				if(data.result==1)
				{
					alert("告警已经确认");
					$("#shebeiid").val("");
					$("#gaojingbianhao").val("");
					$("#fangquid").val("");
					$("#yanzhongchengduLv").val("");
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					var currentAnfangCount = $("#socket_anfanggaojing").text();
					$("#socket_anfanggaojing").text(parseInt(currentAnfangCount)-1);
					$("#contab03").empty();//这里清空图片的内容
					
					closeAllPlaying();
				}
				else if(data.result ==2)
				{
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					alert("该条数据已经处理，请不要重复操作，可以刷新页面获取最新列表!");
				}
				else
				{
					alert("告警确认失败");
				}
			},
			complete:function(e){
				$("#data_loading").hide();
		    },
			error : function(data) {
				alert("请求出现异常!");
				$("#data_loading").hide();
			}
		});
	}
}

function ajaxReportSimple(obj)
{
	if($("#gaojingbianhao").val()=="")
	{
		alert("请先双击选择告警，然后再上报");
		return false;
	}
	else
	{
		var gaojingbianhaoVal = $("#gaojingbianhao").val();
		var timenow = new Date().getTime();
		jQuery.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/linkagectl/reportSecurityAlarmSimple?alarmID="+gaojingbianhaoVal+"&d="+timenow,
			contentType: "application/json; charset=utf-8",
			beforeSend:function(XMLHttpRequest){
				$("#data_loading").show();
		    },
			success : function(data) {
				if(data.result==1)
				{
					alert("告警已经上报");
					$("#shebeiid").val("");
					$("#gaojingbianhao").val("");
					$("#fangquid").val("");
					$("#yanzhongchengduLv").val("");
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					$("#gaojingchuli_detail tbody td.content").html("");
					var currentAnfangCount = $("#socket_anfanggaojing").text();
					$("#socket_anfanggaojing").text(parseInt(currentAnfangCount)-1);
					$("#contab03").empty();//这里清空图片的内容
					
					closeAllPlaying();
				}
				else if(data.result ==2)
				{
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					alert("该条数据已经处理，请不要重复操作，可以刷新页面获取最新列表!");
				}
				else
				{
					alert("告警上报失败");
				}
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#data_loading").hide();
		    },
			error : function(data) {
				alert("请求出现异常!");
				$("#data_loading").hide();
			}
		});
	}
}
function ajaxConfirmSimple(obj)
{
	if($("#gaojingbianhao").val()=="")
	{
		alert("请先双击选择告警，然后再确认");
		return false;
	}
	else
	{
		var gaojingbianhaoVal = $("#gaojingbianhao").val();
		var timenow = new Date().getTime();
		jQuery.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/linkagectl/confirmSecurityAlarmSimple?alarmID="+gaojingbianhaoVal+"&d="+timenow,
			contentType: "application/json; charset=utf-8",
			beforeSend:function(XMLHttpRequest){
				$("#data_loading").show();
		    },
			success : function(data) {
				if(data.result==1)
				{
					alert("告警已经确认");
					$("#shebeiid").val("");
					$("#gaojingbianhao").val("");
					$("#fangquid").val("");
					$("#yanzhongchengduLv").val("");
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					$("#gaojingchuli_detail tbody td.content").html("");
					var currentAnfangCount = $("#socket_anfanggaojing").text();
					$("#socket_anfanggaojing").text(parseInt(currentAnfangCount)-1);
					$("#contab03").empty();//这里清空图片的内容
					
					closeAllPlaying();
				}
				else if(data.result ==2)
				{
					$("#gaojingchuli_tr_"+gaojingbianhaoVal).remove();
					alert("该条数据已经处理，请不要重复操作，可以刷新页面获取最新列表!");
				}
				else
				{
					alert("告警确认失败");
				}
			},
			complete:function(XMLHttpRequest,textStatus){
				$("#data_loading").hide();
		    },
			error : function(data) {
				$("#data_loading").hide();
				alert("请求出现异常!");
			}
		});
	}
}
$(function(){
	setWindowMode(4); /*设置视频窗口模式为四画面*/
	$("#gaojingchuliajaxform").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#shebeiid").val("");
	$("#gaojingbianhao").val("");
	$("#fangquid").val("");
	$("#yanzhongchengduLv").val("");
	$("#contab02").height($("#contab01").height());
	$("#contab03").height($("#contab01").height());
	$("#gaojingchuli_queren").click(function(){
		if($("#shebeiid").val()==""||$("#gaojingbianhao").val()==""||$("#fangquid").val()=="")
		{
			alert("请先双击选择告警，然后再确认");
			return false;
		}
		else
		{
			if($("#gaojingchuliajaxform").validationEngine('validate'))
			{
				ajaxConfirm(this);
			}
		}
	});
	$("#gaojingchuli_shangbao").click(function(){
		if($("#shebeiid").val()==""||$("#gaojingbianhao").val()==""||$("#fangquid").val()=="")
		{
			alert("请先双击选择告警，然后再上报");
			return false;
		}
		else
		{
			if($("#gaojingchuliajaxform").validationEngine('validate'))
			{
				if(confirm("确定上报告警信息吗?"))
				{
					ajaxReport(this);
				}
				return false;
			}
		}
	});
	$("#gaojingchuli_queren_2").click(function(){
		if($("#gaojingbianhao").val()=="")
		{
			alert("请先双击选择告警，然后再确认");
			return false;
		}
		else
		{
			ajaxConfirmSimple(this);
		}
	});
	$("#gaojingchuli_shangbao_2").click(function(){
		if($("#gaojingbianhao").val()=="")
		{
			alert("请先双击选择告警，然后再上报");
			return false;
		}
		else
		{
			if(confirm("确定上报告警信息吗?"))
			{
				ajaxReportSimple(this);
			}
			return false;
		}
	});
	$("#button_hanhua").mousedown(function(){
		var fangquID = $("#fangquid").val();
		if(fangquID==null||fangquID=="")
		{
			alert("请先双击选择告警");
			return false;
		}
		else
		{
			start();
		}
	});
	$("#button_hanhua").mouseup(function(){
		var audioFileName = getfile();
		stop();
		var fangquID = $("#fangquid").val();
		if(fangquID==null||fangquID=="")
		{
			alert("请先双击选择告警");
			return false;
		}
		else
		{
			audioFileName+= ".mp3";
			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/linkagectl/SpeakVoice?fileName="+audioFileName+"&zoneID="+fangquID+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
			    },
				success : function(data) {
					if(data.result==1)
					{
						alert("喊话成功");
					}
					else
					{
						alert("喊话失败");
					}
				},
				complete:function(XMLHttpRequest,textStatus){
					$("#data_loading").hide();
			    },
				error : function(data) {
					alert("请求出现异常!");
					$("#data_loading").hide();
				}
			});
		}
    });
	$("#button_zidongyuyan").click(function(){
		var fangquID = $("#fangquid").val();
		var yanzhongLv = $("#yanzhongchengduLv").val();
		if(fangquID==null||fangquID=="")
		{
			alert("请先双击选择告警");
			return false;
		}
		else
		{
			if(yanzhongLv==null||yanzhongLv=="")
			{
				yanzhongLv = 1;
			}
			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/linkagectl/PlayRecord?level="+yanzhongLv+"&ZoneID="+fangquID+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
			    },
				success : function(data) {
					if(data.result==1)
					{
						alert("自动语音成功");
					}
					else
					{
						alert("自动语音失败");
					}
				},
				complete:function(XMLHttpRequest,textStatus){
					$("#data_loading").hide();
			    },
				error : function(data) {
					$("#data_loading").hide();
					alert("请求出现异常!");
				}
			});
		}
	});
});