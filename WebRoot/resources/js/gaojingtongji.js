function ajaxQuery()
{
	$("#gaojingtongjiform").ajaxSubmit({
	     type: "post",
	     url: $("#ctx").val()+"/alarmmgt/statisticAlarm",
	     dataType: "json",
	     beforeSubmit:function(e){
	    	 $(".searchloading").show();
	     },
	     success : function(data) {
			if(data.result==1)
			{
				var trContent = "";
				var headNode = "<tr><th>开始时间</th>";
				headNode+= "<th>结束时间</th>";
				if($("#check_fengongsi").is(':checked'))
				{
					headNode+= "<th>分公司</th>";
				}
				if($("#check_guanlichu").is(':checked'))
				{
					headNode+= "<th>管理处</th>";
				}
				if($("#check_fangqu").is(':checked'))
				{
					headNode+= "<th>防区</th>";
				}
				if($("#check_shebeileixing").is(':checked'))
				{
					headNode+= "<th>设备类型</th>";
				}
				if($("#check_gaojingjibie").is(':checked'))
				{
					headNode+= "<th>告警级别</th>";
				}
				headNode+= "<th>告警个数</th></tr>";
				$.each(data.object.datas,function(i,n){
					var trNode = "";
					trNode+="<tr>";
					trNode+="<td>"+n['beginTime']+"</td>";
					trNode+="<td>"+n['endTime']+"</td>";
					if($("#check_fengongsi").is(':checked'))
					{
						trNode+="<td>"+n['branchName']+"</td>";
					}
					if($("#check_guanlichu").is(':checked'))
					{
						trNode+="<td>"+n['departmentName']+"</td>";
					}
					if($("#check_fangqu").is(':checked'))
					{
						trNode+="<td>"+n['zoneName']+"</td>";
					}
					if($("#check_shebeileixing").is(':checked'))
					{
	    				if(n['deviceType']==1)
	    					trNode+="<td>高压脉冲主机</td>";
	    				else if(n['deviceType']==2)
	    					trNode+="<td>一体化探测主机</td>";
	    				else if(n['deviceType']==3)
	    					trNode+="<td>防区型振动光纤</td>";
	    				else if(n['deviceType']==4)
	    					trNode+="<td>定位型振动光纤</td>";
	    				else if(n['deviceType']==5)
	    					trNode+="<td>NVR</td>";
	    				else if(n['deviceType']==6)
	    					trNode+="<td>IPC</td>";
	    				else
	    					trNode+="<td>智能视频服务器</td>";
					}
					if($("#check_gaojingjibie").is(':checked'))
					{
					/*	if(n['type']==1){
							if(n['checkLevel']==1)
		    					trNode+="<td>警告</td>";
		    				else if(n['checkLevel']==2)
		    					trNode+="<td>次要</td>";
		    				else if(n['checkLevel']==3)
		    					trNode+="<td>主要</td>";
		    				else
		    					trNode+="<td>严重</td>";
						}else{*/
							if(n['alarmLevel']==1)
		    					trNode+="<td>警告</td>";
		    				else if(n['alarmLevel']==2)
		    					trNode+="<td>次要</td>";
		    				else if(n['alarmLevel']==3)
		    					trNode+="<td>主要</td>";
		    				else
		    					trNode+="<td>严重</td>";
				//		}						
					}
					trNode+="<td>"+n['alarmNum']+"</td>";
					trNode+="</tr>";
					trContent = trContent + trNode;
	            });
				$("#pageoffset").val(data.object.offset);
				$("#gaojingtongjitablecontainer thead").empty();
				$("#gaojingtongjitablecontainer thead").append(headNode);
				$("#gaojingtongjitablecontainer tbody").empty();
				$("#gaojingtongjitablecontainer tbody").append(trContent);
				$("#gaojingtongjitablecontainer").colorTable();
				$("#gaojingtongjitablecontainer").tablesorter();
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
			}
			else
			{
				alert(data.message);
			}
		},
		complete:function(e){
	    	 $(".searchloading").hide();
	    },
		error : function(data) {
			alert("请求出现异常!");
			$(".searchloading").hide();
		}
	});
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
$(function(){
	$("#pageoffset").val("1");
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
	
	$("#gaojingtongjiform").validationEngine({
		validationEventTriggers:"",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#gaojingtongji_button").click(function(){
		if($("#gaojingtongjiform").validationEngine('validate'))
		{
			$("#pageoffset").val("1");
			ajaxQuery();
		}
	});
	$("#exportgaojingtongji").click(function(){
		if($("#gaojingtongjiform").validationEngine('validate'))
		{
			var arrChk=$(".staticGranularity_checkbox:checked");
			var tongjiliduArray = new Array();
			$(arrChk).each(function(i)
			{
				if(!(tongjiliduArray.indexOf($(this).attr("value"))>=0))
				{
					tongjiliduArray.push($(this).attr("value"));
				}
			});
			var staticGranularitys = "[";
			staticGranularitys+= tongjiliduArray.join(",");
			staticGranularitys+="],";
			var jsonStr = '{"type":';
			jsonStr+= '"'+$("#leixing_select").val()+'",';
			jsonStr+= '"branchId":'+'"'+$("#fengongsi_select").val()+'",';
			jsonStr+= '"departmentId":'+'"'+$("#guanlichu_select").val()+'",';
			jsonStr+= '"zoneId":'+'"'+$("#fangqu_select").val()+'",';
			jsonStr+= '"levelId":'+'"'+$("#selectgaojingjibie").val()+'",';
			jsonStr+= '"deviceTypeId":'+'"'+$("#selectshebeileixing").val()+'",';
			jsonStr+= '"beginTime":'+'"'+$("#fromdate").val()+'",';
			jsonStr+= '"endTime":'+'"'+$("#todate").val()+'",';
			jsonStr+= '"staticGranularity":'+staticGranularitys;
			jsonStr+= '"timeGranularity":'+'"'+$("#shijianlidu input[type=radio]:checked").val()+'"}';
			//var jsonStr = JSON.stringify($("#gaojingtongjiform").serializeObject());
			$(this).attr("href",$("#ctx").val()+"/alarmmgt/exportStatisticAlarm?jsonStr="+jsonStr);
			return true;
		}
	});
});