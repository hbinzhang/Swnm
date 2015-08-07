function ajaxQuery()
{
	if($("#gaojingchaxun_from").validationEngine('validate'))
	{
	$(".searchloading").show();
	var timenow = new Date().getTime();
	var jsonStr = JSON.stringify($("#gaojingchaxun_from").serializeObject());
	jQuery.ajax({
		type : 'GET',
		url : $("#ctx").val()+"/alarmmgt/queryAlarm?jsonStr="+encodeURI(jsonStr,"UTF-8")+"&d="+timenow,
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success : function(data) {
			if(data.result==1)
			{
				var content = "";
				var hiddenNode = "";
				$.each(data.object.datas,function(i,n){
					if(n["type"]==1)
					{
						hiddenNode+="<span style='display:none;' id='anfang"+i+"chuliren'>"+n['userId']+"</span>";
						if(n['managerTime']==null || n['managerTime']=="" || n['managerTime']=="null")
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"chulishijian'>"+""+"</span>";
						}
						else
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"chulishijian'>"+n['managerTime']+"</span>";
						}
						hiddenNode+="<span style='display:none;' id='anfang"+i+"shijianyuanyin'>"+n['reason']+"</span>";
						hiddenNode+="<span style='display:none;' id='anfang"+i+"shijianguocheng'>"+n['description']+"</span>";
						hiddenNode+="<span style='display:none;' id='anfang"+i+"zerenren'>"+n['peopleId']+"</span>";
						if(n["isReal"]==0)
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"xushijing'>"+"虚警"+"</span>";
						}
						else
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"xushijing'>"+"实警"+"</span>";
						}
						if(n["report"] == 2)
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"shangbaoqingkuang'>"+"管理处"+"</span>";
						}
						else if(n["report"] == 1)
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"shangbaoqingkuang'>"+"分公司"+"</span>";
						}
						else
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"shangbaoqingkuang'>"+"总公司"+"</span>";
						}
						hiddenNode+="<span style='display:none;' id='anfang"+i+"shangbaoqingkuang'>"+n['report']+"</span>";
						if(n["checkMothod"] == 1)
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"fuheyiju'>"+"视频复核"+"</span>";
						}
						else
						{
							hiddenNode+="<span style='display:none;' id='anfang"+i+"fuheyiju'>"+"现场复核"+"</span>";
						}
						hiddenNode+="<span style='display:none;' id='anfang"+i+"fuheshouduan'>"+n['isReal']+"</span>";
						hiddenNode+="<span style='display:none;' id='anfang"+i+"fangqushijian'><a target='_blank' href='"+$("#ctx").val()+"/alarmmgt/queryAlarmVideoUrl?alarmId="+n['alarmId']+"'>点击查看</a></span>";
						//hiddenNode+="<span style='display:none;' id='anfang"+i+"zuoshijianshipin'><a target='_blank' href='"+$("#ctx").val()+"/alarmmgt/queryAlarmVideoUrl?alarmId="+n['alarmId']+"'>点击查看</a></span>";
						//hiddenNode+="<span style='display:none;' id='anfang"+i+"youshijianshipin'><a target='_blank' href='"+$("#ctx").val()+"/alarmmgt/queryAlarmVideoUrl?alarmId="+n['alarmId']+"'>点击查看</a></span>";
						hiddenNode+="<span style='display:none;' id='anfang"+i+"shijiantupian'><a target='_blank' href='"+$("#ctx").val()+"/alarmmgt/queryAlarmPictureUrl?alarmId="+n['alarmId']+"'>点击查看</a></span>";
						hiddenNode+="<span style='display:none;' id='anfang"+i+"chixushijian'>"+n['lastTime']+"</span>";
						hiddenNode+="<span style='display:none;' id='anfang"+i+"cishu'>"+n['alarmCount']+"</span>";
					}
					else
					{
						hiddenNode+="<span style='display:none;' id='shebei"+i+"chuliren'>"+n['peopleId']+"</span>";
						if(n['managerTime']==null || n['managerTime']=="" || n['managerTime']=="null")
						{
							hiddenNode+="<span style='display:none;' id='shebei"+i+"chulishijian'>"+""+"</span>";
						}
						else
						{
							hiddenNode+="<span style='display:none;' id='shebei"+i+"chulishijian'>"+n['managerTime']+"</span>";
						}						
						hiddenNode+="<span style='display:none;' id='shebei"+i+"kenengyuanyin'>"+n['cause']+"</span>";
						hiddenNode+="<span style='display:none;' id='shebei"+i+"kenenghouguo'>"+n['result']+"</span>";
						hiddenNode+="<span style='display:none;' id='shebei"+i+"jianyicaozuo'>"+n['operation']+"</span>";
						if(n['isAffect']==0)
						{
							hiddenNode+="<span style='display:none;' id='shebei"+i+"yingxiangyewu'>"+"不影响"+"</span>";
						}
						else
						{
							hiddenNode+="<span style='display:none;' id='shebei"+i+"yingxiangyewu'>"+"影响"+"</span>";
						}
					}
					var node = "";
	            	node+= "<tr>";
	            	if(n["type"]==1)
	            	{
	            		node+= "<td id='gaojing"+i+"gaojingbianhao'><a onclick='anfangDetail(this);' detailindex='"+i+"' class='detail anfang_gaojing_detail' href='javascript:void(0);'>"+n["alarmId"]+"</td>";
	            	}
	            	else
	            	{
	            		node+= "<td id='gaojing"+i+"gaojingbianhao'><a onclick='shebeiDetail(this);' detailindex='"+i+"' class='detail shebei_gaojing_detail' href='javascript:void(0);'>"+n["alarmId"]+"</td>";
	            	}
	            	node+= "<td id='gaojing"+i+"gaojingma'>"+n["alarmCode"]+"</td>";
	            	node+= "<td id='gaojing"+i+"gaojingmingcheng'>"+n["alarmName"]+"</td>";
	            	node+= "<td id='gaojing"+i+"gaojingshijian'>"+n["alarmTime"]+"</td>";
	            	/*if(n["type"]==-1)
	            	{
	            		if(n["checkLevel"]==1)
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"警告"+"</td>";
		            	}
		            	else if(n["checkLevel"]==2)
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"次要"+"</td>";
		            	}
		            	else if(n["checkLevel"]==3)
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"主要"+"</td>";
		            	}
		            	else
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"严重"+"</td>";
		            	}
	            	}
	            	else
	            	{*/
	            		if(n["alarmLevel"]==1)
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"警告"+"</td>";
		            	}
		            	else if(n["alarmLevel"]==2)
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"次要"+"</td>";
		            	}
		            	else if(n["alarmLevel"]==3)
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"主要"+"</td>";
		            	}
		            	else
		            	{
		            		node+= "<td id='gaojing"+i+"gaojingjibie'>"+"严重"+"</td>";
		            	}
	            	/*}*/
	            	if(n["deviceType"]==1)
	            	{
	            		node+= "<td id='gaojing"+i+"shebeileixing'>"+"高压脉冲主机"+"</td>";
	            	}
	            	else if(n["deviceType"]==2)
	            	{
	            		node+= "<td id='gaojing"+i+"shebeileixing'>"+"一体化探测主机"+"</td>";
	            	}
	            	else if(n["deviceType"]==3)
	            	{
	            		node+= "<td id='gaojing"+i+"shebeileixing'>"+"防区型振动光纤"+"</td>";
	            	}
	            	else if(n["deviceType"]==4)
	            	{
	            		node+= "<td id='gaojing"+i+"shebeileixing'>"+"定位型振动光纤"+"</td>";
	            	}
	            	else if(n["deviceType"]==5)
	            	{
	            		node+= "<td id='gaojing"+i+"shebeileixing'>"+"NVR"+"</td>";
	            	}
	            	else if(n["deviceType"]==6)
	            	{
	            		node+= "<td id='gaojing"+i+"shebeileixing'>"+"IPC"+"</td>";
	            	}
	            	else
	            	{
	            		node+= "<td id='gaojing"+i+"shebeileixing'>"+"智能视频服务器"+"</td>";
	            	}
	            	node+= "<td id='gaojing"+i+"shebeiid'>"+n["deviceId"]+"</td>";
	            	node+= "<td id='gaojing"+i+"fenggongsi'>"+n["branchName"]+"</td>";
	            	node+= "<td id='gaojing"+i+"guanlichu'>"+n["departmentName"]+"</td>";
	            	node+= "<td id='gaojing"+i+"fangqu'>"+n["zoneName"]+"</td>";
	            	if(n["alarmStatus"]==0)
	            	{
	            		node+= "<td id='gaojing"+i+"chulizhuangtai'>"+"未处理"+"</td>";
	            	}
	            	else
	            	{
	            		node+= "<td id='gaojing"+i+"chulizhuangtai'>"+"已处理"+"</td>";
	            	}
	            	node+="<td id='gaojing"+i+"fujiaxinxi'>"+n['info']+"</td>";
	            	node+= "</tr>";
	            	content = content + node;	
	            });
				$("#pageoffset").val(data.object.offset);
				$("#gaojingtablecontainer tbody").empty();
				$("#gaojingtablecontainer tbody").append(content);
				$("#detail_hidden").empty();
				$("#detail_hidden").append(hiddenNode);
				$("#gaojingtablecontainer").colorTable();
				$("#gaojingtablecontainer").tablesorter();
				$(".pagin").remove();
				if(data.object.pages>1)
				{
					var pageDiv = '<div class="pagin">';
					pageDiv+= '<div class="message left">共<a class="blue" href="javascript:void(0);">';
					pageDiv+= data.object.total;
					pageDiv+= '</a>条记录，共&nbsp;<a class="blue" href="javascript:void(0);">'+data.object.pages+'</a>页，当前显示第&nbsp;'+'<a class="blue" href="javascript:void(0);">';
					pageDiv+= data.object.offset;
					pageDiv+= '&nbsp;</a>页</div>';
					pageDiv+= '<ul class="paginList">';
					pageDiv+= '<li class="paginItem">';
					pageDiv+= '<a href="javascript:void(0);" class="pageclick" onclick="pageclick(this);" offset=1>'+'首页</a></li>';
					if(data.object.offset>1)
					{
						pageDiv+= '<li class="paginItem">';
						pageDiv+= '<a href="javascript:void(0);"  class="pageclick" onclick="pageclick(this);" offset='+(data.object.offset-1)+'>'+'上页</a></li>';
					}
					if(data.object.offset<data.object.pages)
					{
						pageDiv+= '<li class="paginItem">';
						pageDiv+= '<a href="javascript:void(0);"  class="pageclick" onclick="pageclick(this);" offset='+(data.object.offset+1)+'>'+'下页</a></li>';
					}
					pageDiv+= '<li class="paginItem2"><span>第<input type="text" id="jumppages" value="'+data.object.offset+'" name="jumppages">页</span></li>';
					pageDiv+= '<li class="paginItem"><a href="javascript:void(0);" onclick="jumpPages(this);" maxPages="'+data.object.pages+'">跳转</a></li>';
					pageDiv+= '<li class="paginItem">';
					pageDiv+= '<a href="javascript:void(0);" class="pageclick" onclick="pageclick(this);" offset='+data.object.pages+'>'+'尾页</a></li></ul>';
					pageDiv+= '</div>';
					$(".tableui_container").append(pageDiv);
				}
				else
				{
					var pageDiv = '<div class="pagin">';
					pageDiv+= '<div class="message left">共<a class="blue" href="javascript:void(0);">'+data.object.total;
					pageDiv+= '</a>条记录</div>';
					pageDiv+= '</div>';
					$(".tableui_container").append(pageDiv);
				}
				$(".searchloading").hide();
			}
			else
			{
				alert(data.message);
				$(".searchloading").hide();
			}
		},
		error : function(data) {
			alert("请求出现异常!");
			$(".searchloading").hide();
		}
	});
	}
}
function pageclick(obj)
{
	var ofs = $(obj).attr("offset");
	$("#pageoffset").val(ofs);
	ajaxQuery();
}
function jumpPages(obj)
{
	var maxPages = $(obj).attr("maxPages");
	var inputPages = $("#jumppages").val();
	if(parseInt(inputPages)>parseInt(maxPages))
	{
		alert("最大页数是 "+maxPages);
		$("#jumppages").val(maxPages);
		return false;
	}
	else
	{
		$("#pageoffset").val(inputPages);
		ajaxQuery();
	}
}
function anfangDetail(obj)
{
	var detailindex = $(obj).attr("detailindex");
	$(".anfanggaojing_detail tbody tr .content_gaojingbianhao").html($("#gaojing"+detailindex+"gaojingbianhao").text());
	$(".anfanggaojing_detail tbody tr .content_gaojingma").html($("#gaojing"+detailindex+"gaojingma").text());
	$(".anfanggaojing_detail tbody tr .content_gaojingmingcheng").html($("#gaojing"+detailindex+"gaojingmingcheng").text());
	$(".anfanggaojing_detail tbody tr .content_gaojingshijian").html($("#gaojing"+detailindex+"gaojingshijian").text());
	$(".anfanggaojing_detail tbody tr .content_gaojingjibie").html($("#gaojing"+detailindex+"gaojingjibie").text());
	$(".anfanggaojing_detail tbody tr .content_shebeileixing").html($("#gaojing"+detailindex+"shebeileixing").text());
	$(".anfanggaojing_detail tbody tr .content_shebeiid").html($("#gaojing"+detailindex+"shebeiid").text());
	$(".anfanggaojing_detail tbody tr .content_fengongsi").html($("#gaojing"+detailindex+"fenggongsi").text());
	$(".anfanggaojing_detail tbody tr .content_guanlichu").html($("#gaojing"+detailindex+"guanlichu").text());
	$(".anfanggaojing_detail tbody tr .content_fangqu").html($("#gaojing"+detailindex+"fangqu").text());
	$(".anfanggaojing_detail tbody tr .content_chulizhuangtai").html($("#gaojing"+detailindex+"chulizhuangtai").text());
	$(".anfanggaojing_detail tbody tr .content_chuliren").html($("#anfang"+detailindex+"chuliren").text());
	$(".anfanggaojing_detail tbody tr .content_chulishijian").html($("#anfang"+detailindex+"chulishijian").text());
	$(".anfanggaojing_detail tbody tr .content_shijianyuanyin").html($("#anfang"+detailindex+"shijianyuanyin").text());
	$(".anfanggaojing_detail tbody tr .content_shijianguocheng").html($("#anfang"+detailindex+"shijianguocheng").text());
	$(".anfanggaojing_detail tbody tr .content_zerenren").html($("#anfang"+detailindex+"zerenren").text());
	$(".anfanggaojing_detail tbody tr .content_xushijing").html($("#anfang"+detailindex+"xushijing").text());
	$(".anfanggaojing_detail tbody tr .content_shangbaoqingkuang").html($("#anfang"+detailindex+"shangbaoqingkuang").text());
	$(".anfanggaojing_detail tbody tr .content_fuheshouduan").html($("#anfang"+detailindex+"fuheshouduan").text());
	$(".anfanggaojing_detail tbody tr .content_fangqushijian").html($("#anfang"+detailindex+"fangqushijian").html());
	//$(".anfanggaojing_detail tbody tr .content_zuoshijianshipin").html($("#anfang"+detailindex+"zuoshijianshipin").html());
	//$(".anfanggaojing_detail tbody tr .content_youshijianshipin").html($("#anfang"+detailindex+"youshijianshipin").html());
	$(".anfanggaojing_detail tbody tr .content_shijiantupian").html($("#anfang"+detailindex+"shijiantupian").html());
	$(".anfanggaojing_detail tbody tr .content_chixushijian").html($("#anfang"+detailindex+"chixushijian").text());
	$(".anfanggaojing_detail tbody tr .content_cishu").html($("#anfang"+detailindex+"cishu").text());
	$(".anfanggaojing_detail tbody tr .content_fuheyiju").html($("#anfang"+detailindex+"fuheyiju").text());
	$(".anfanggaojing_detail tbody tr .content_fujiaxinxi").html($("#gaojing"+detailindex+"fujiaxinxi").text());
	$('#anfang_gaojing_info').dialogModal({
		onOkBut: function() {},
		onCancelBut: function() {},
		onLoad: function() {},
		onClose: function() {}
	});
}
function shebeiDetail(obj)
{
	var detailindex = $(obj).attr("detailindex");
	$(".shebeigaojing_detail tbody tr .content_gaojingbianhao").html($("#gaojing"+detailindex+"gaojingbianhao").text());
	$(".shebeigaojing_detail tbody tr .content_gaojingma").html($("#gaojing"+detailindex+"gaojingma").text());
	$(".shebeigaojing_detail tbody tr .content_gaojingmingcheng").html($("#gaojing"+detailindex+"gaojingmingcheng").text());
	$(".shebeigaojing_detail tbody tr .content_gaojingshijian").html($("#gaojing"+detailindex+"gaojingshijian").text());
	$(".shebeigaojing_detail tbody tr .content_gaojingjibie").html($("#gaojing"+detailindex+"gaojingjibie").text());
	$(".shebeigaojing_detail tbody tr .content_shebeileixing").html($("#gaojing"+detailindex+"shebeileixing").text());
	$(".shebeigaojing_detail tbody tr .content_shebeiid").html($("#gaojing"+detailindex+"shebeiid").text());
	$(".shebeigaojing_detail tbody tr .content_fengongsi").html($("#gaojing"+detailindex+"fenggongsi").text());
	$(".shebeigaojing_detail tbody tr .content_guanlichu").html($("#gaojing"+detailindex+"guanlichu").text());
	$(".shebeigaojing_detail tbody tr .content_fangqu").html($("#gaojing"+detailindex+"fangqu").text());
	$(".shebeigaojing_detail tbody tr .content_chulizhuangtai").html($("#gaojing"+detailindex+"chulizhuangtai").text());
	$(".shebeigaojing_detail tbody tr .content_chuliren").html($("#shebei"+detailindex+"chuliren").text());
	$(".shebeigaojing_detail tbody tr .content_chulishijian").html($("#shebei"+detailindex+"chulishijian").text());
	$(".shebeigaojing_detail tbody tr .content_kenengyuanyin").html($("#shebei"+detailindex+"kenengyuanyin").text());
	$(".shebeigaojing_detail tbody tr .content_kenenghouguo").html($("#shebei"+detailindex+"kenenghouguo").text());
	$(".shebeigaojing_detail tbody tr .content_jianyicaozuo").html($("#shebei"+detailindex+"jianyicaozuo").text());
	$(".shebeigaojing_detail tbody tr .content_yingxiangyewu").html($("#shebei"+detailindex+"yingxiangyewu").text());
	$(".shebeigaojing_detail tbody tr .content_fujiaxinxi").html($("#gaojing"+detailindex+"fujiaxinxi").text());
	$('#shebei_gaojing_info').dialogModal({
		onOkBut: function() {},
		onCancelBut: function() {},
		onLoad: function() {},
		onClose: function() {}
	});
}
$(function(){
	$("#gaojingchaxun_from").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#pageoffset").val("1");
	if(!$("input[name='type']").get(0).checked )
	{
		$("#youshijianshipin").hide();
		$("#check_youshijianshipin").attr("checked", false);
	}
	$("input[name='type']").change(function ()
    {
        if($(this).val()==1)
        {
        	$("#youshijianshipin").show();
        }
        else
        {
        	$("#youshijianshipin").hide();
        	$("#check_youshijianshipin").attr("checked", false);
        }
    });
	$("#fengongsi_select").change(function ()
    {
        if($(this).val()==0)
        {
        	$("#guanlichu_select").empty();
        	$("#guanlichu_select").append("<option value=''>全部</option>");
        	$("#fangqu_select").empty();
        	$("#fangqu_select").append("<option value=''>全部</option>");
        }
        else
        {
        	jQuery.ajax({
    			type : 'GET',
    			url : $("#ctx").val()+"/alarmmgt/queryDepartByBranch?branchId="+$(this).val(),
    			contentType: "application/json; charset=utf-8",
    			success : function(data) {
    				if(data.result==1)
    				{
    					var content = "";
    					for(var i=0;i<data.object.length;i++)
    					{	
    						content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
    					}
    					$("#guanlichu_select").empty();
    		        	$("#guanlichu_select").append("<option value=''>全部</option>");
    		        	$("#guanlichu_select").append(content);
    		        	
    				}
    				else
    				{
    					alert(data.message);
    				}
    			},
    			error : function(data) {
    				alert("请求出现异常!");
    			}
    		});
        }
    });
	$("#guanlichu_select").change(function ()
    {
        if($(this).val()==0)
        {
        	$("#fangqu_select").empty();
        	$("#fangqu_select").append("<option value=''>全部</option>");
        }
        else
        {
        	jQuery.ajax({
    			type : 'GET',
    			url : $("#ctx").val()+"/alarmmgt/queryZoneByDepart?departmentId="+$(this).val(),
    			contentType: "application/json; charset=utf-8",
    			success : function(data) {
    				if(data.result==1)
    				{
    					var content = "";
    					for(var i=0;i<data.object.length;i++)
    					{	
    						content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
    					}
    					$("#fangqu_select").empty();
    		        	$("#fangqu_select").append("<option value=''>全部</option>");
    		        	$("#fangqu_select").append(content);
    		        	
    				}
    				else
    				{
    					alert(data.message);
    				}
    			},
    			error : function(data) {
    				alert("请求出现异常!");
    			}
    		});
        }
    });
	$("#shebeileixing_select").change(function ()
    {
        jQuery.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/alarmmgt/queryAlarmNameByDevType?deviceTypeId="+$(this).val(),
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result==1)
				{
					var content = "";
					for(var i=0;i<data.object.length;i++)
					{	
						content+="<option value="+data.object[i]+">"+data.object[i]+"</option>";
					}
					$("#gaojingmingcheng_select").empty();
		        	$("#gaojingmingcheng_select").append("<option value=''>全部</option>");
		        	$("#gaojingmingcheng_select").append(content);
		        	
				}
				else
				{
					alert(data.message);
				}
			},
			error : function(data) {
				alert("请求出现异常!");
			}
		});
    });
	$("#gaojingchaxun_btn").click(function(){
		$("#pageoffset").val("1");
		ajaxQuery();
	});
	$("#export_gaojing_query").click(function(){
		var jsonStr = JSON.stringify($("#gaojingchaxun_from").serializeObject());
		$(this).attr("href",$("#ctx").val()+"/alarmmgt/exportAlarm?jsonStr="+jsonStr);
		return true;
	});
});