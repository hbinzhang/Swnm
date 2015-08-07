function ajaxImportWeilan(posturl){  
	if($("#fence").val()=="")
	{
		alert("请选择文件");
		return false;
	}
	var fileSuttfix=$("#fence").val().substr($("#fence").val().lastIndexOf(".")).toLowerCase();
	if(!(fileSuttfix==".xls"||fileSuttfix==".xlsx"))
	{
		alert("请选择Excel文件");
		$("#fence").val("");
		return false;
	}
	$("#data_loading").show();
    $.ajaxFileUpload({  
        url:posturl,  
        secureuri:false,                           
        fileElementId:'fence', 
        dataType:'json', 
        success:function(data){ 
        	if(data.result==1)
        	{
        		alert("文件导入成功");
        		location.reload();
        	}
        	else
        	{
        		if(data.object['FILE_FORMAT_ERROR']==null||data.object['FILE_FORMAT_ERROR']=="")
        		{
        			var errorArrInfo ="";
        			$.each(data.object['FENCEDATAERROR'],function(i,n){
        				errorArrInfo=errorArrInfo+n;
		            });
        			
        			alert(errorArrInfo);
        		}
        		else
        		{
        			alert(data.object['FILE_FORMAT_ERROR']+"文件格式错误");
        		}
        	}
        	$("#fence").val("");
        	$("#data_loading").hide();
        },
        error:function(data, status, e){ 
        	alert("上传过程中出现错误，请重新上传");
        	$("#data_loading").hide();
        }  
    });
    //$("#data_loading").hide();
}
function checkStatus(status){
	if(status==4){
		alert("此围栏设备跟管理处设备发生异常,请联系管理处排查。");
		return false;
	}else{
		return true;
	}
	
}
$(function(){
	//添加围栏
	$("#weilanadd_form").validationEngine({
		validationEventTriggers:"",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#weilanaddbtn").click(function(){
		
		if(!($(".exithostId").is(":hidden"))){
			return;
		}
		
		if($("#weilanadd_form").validationEngine('validate'))
		{
			var redirectUrl = $("#redirecturl").val();
			$("#weilanadd_form").ajaxSubmit({
			     type: "post",
				 url : $("#ctx").val()+"/fence/addFence",
			     dataType: "json",
			     beforeSubmit:function(e){
			    	 $("#data_loading").show();
			     },
			     success : function(data) {
					if(data.result==1)
					{
						window.location = $("#ctx").val()+redirectUrl;
					}
					else
					{
						alert(data.message);
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
	//编辑围栏
	$("#weilanedit_form").validationEngine({
		validationEventTriggers:"",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#weilaneditbtn").click(function(){
		var redirectUrl = $("#redirectUrl").val();
		if($("#weilanedit_form").validationEngine('validate')){
			$("#weilanedit_form").ajaxSubmit({
			     type: "post",
				 url : $("#ctx").val()+"/fence/doModFence",
			     dataType: "json",
			     beforeSubmit:function(e){
			    	 $("#data_loading").show();
			     },
			     success : function(data) {
					if(data.result==1)
					{
						window.location = $("#ctx").val()+redirectUrl;
					}
					else
					{
						alert(data.message);
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
	
	$("#uploadcontainer").hide();
	$("#weilan_select_all").prop("checked", false);
	$("input[name='deleteids']").prop("checked", false);
	$("#weilan_table").tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	
	$("#weilan_table").colorTable();
	$("#weilan_select_all").on('click',function(){
		$("input[name='deleteids']").prop("checked", this.checked);
	});
	
	if($("#weilan_search_filter option:selected").val()==2)
	{
		$("#weilanleixing").show();
    	$("#weilaninput").hide();
    	$("#weilaninput").val("");
	}
	$("#weilan_search_filter").change(function ()
    {
        if($(this).val()==2)
        {
        	$("#weilanleixing").show();
        	$("#weilaninput").hide();
        	$("#weilaninput").val("");
        }
        else
        {
        	$("#weilanleixing").hide();
        	$("#weilaninput").show();
        }
    });
	$(".weilan_detail").click(function(){
		var weilanid = $(this).attr("weilanid");
		$(".detail_table tbody tr .content_zhujiid").html($("#weilan"+weilanid+"_zhujiid").text());
		$(".detail_table tbody tr .content_weilanleixing").html($("#weilan"+weilanid+"_weilanleixing").text());
		$(".detail_table tbody tr .content_weilanmingcheng").html($("#weilan"+weilanid+"_weilanmingcheng").text());
		$(".detail_table tbody tr .content_wangluodizhi").html($("#weilan"+weilanid+"_wangluodizhi").text());
		$(".detail_table tbody tr .content_duankouhao").html($("#weilan"+weilanid+"_duankouhao").text());
		$(".detail_table tbody tr .content_weilanzhuangtai").html($("#weilan"+weilanid+"_weilanzhuangtai").html());
		$(".detail_table tbody tr .content_shebeiid").html($("#weilan"+weilanid+"_shebeiid").text());
		$(".detail_table tbody tr .content_anzhuangshijian").html($("#weilan"+weilanid+"_anzhuangshijian").text());
		$(".detail_table tbody tr .content_ruanjianbanben").html($("#weilan"+weilanid+"_ruanjianbanben").text());
		$(".detail_table tbody tr .content_fengongsi").html($("#weilan"+weilanid+"_fengongsi").text());
		$(".detail_table tbody tr .content_guanlichu").html($("#weilan"+weilanid+"_guanlichu").text());
		$(".detail_table tbody tr .content_chanpinxinghao").html($("#weilan"+weilanid+"_chanpinxinghao").text());
		$(".detail_table tbody tr .content_jingdu").html($("#weilan"+weilanid+"_jingdu").text());
		$(".detail_table tbody tr .content_weidu").html($("#weilan"+weilanid+"_weidu").text());
		$(".detail_table tbody tr .content_shengchanchangshang").html($("#weilan"+weilanid+"_shengchanchangshang").text());
		$(".detail_table tbody tr .content_beizhu").html($("#weilan"+weilanid+"_beizhu").text());
    	$('#weilan_info').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
	$(".view_weilangaojing").click(function(){
		var timenow = new Date().getTime();
		jQuery.ajax({
			type : 'GET',
			cache:false,
			contentType : 'application/json',
			url : $("#ctx").val()+"/fence/findAlarmInfo?fenceBean.hostID="+$(this).attr("weilanid")+"&d="+timenow,
			success : function(data) {
				if(data.result==0)
				{
					$("#weilangaojing_content_container .tableui tbody").empty();
					$('#weilangaojing_content_container').append("页面加载失败");
				}
				else
				{
					$("#weilan_gaojing_content_container .tableui tbody").empty();
					$.each(data.object,function(i,n){
		            	var node = "<tr>";
		            	node+= "<td>"+n["alarmID"]+"</td>";
		            	node+= "<td>"+n["alarmCode"]+"</td>";
		            	node+= "<td>"+n["alarmName"]+"</td>";
		            	node+= "<td>"+n["alarmTime"]+"</td>";
		            	node+= "<td>"+n["alarmLevel"]+"</td>";
		            	node+= "<td>"+n["alarmType"]+"</td>";
		            	node+= "<td>"+n["deviceType"]+"</td>";
		            	node+= "<td>"+n["ip"]+"</td>";
		            	node+= "<td>"+n["branchName"]+"</td>";
		            	node+= "<td>"+n["mgtName"]+"</td>";
		            	node+= "<td>"+n["zoneName"]+"</td>";
		            	node+= "<td>"+n["alarmStatus"]+"</td>";
		            	node+= "</tr>";
		            	$("#weilan_gaojing_content_container .tableui tbody").append(node);	
		            });
				}
				$('#view_weilan_gaojing_container').dialogModal({
					onOkBut: function() {},
					onCancelBut: function() {},
					onLoad: function() {},
					onClose: function() {}
				});
			},
			error : function(data) {
				alert("请求出现异常");
			}
		});
	});
	
	$(".input_tip").hide();
	$("#weilan_form input").focus(function(){
        $(this).next(".input_tip").show();

	}); 
	$("#weilan_form input").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	$("#weilan_form select").focus(function(){
        $(this).next(".input_tip").show();

	}); 
	$("#weilan_form select").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	
	$("#deleteweilan").click(function(){
		var arrChk=$("#deleteids:checked");
		if(arrChk.size()<=0)
		{
			alert("请选择要删除的围栏");
			return false;
		}
		var deleteArray = new Array();
		$(arrChk).each(function(i)
		{
			if(!(deleteArray.indexOf($(this).attr("value"))>=0))
			{
				deleteArray.push($(this).attr("value"));
			}
		});
		var deleteParams = deleteArray.join(",");
		if(confirm("确定删除选择的围栏吗?"))
		{
			var timenow = new Date().getTime();
	        jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/fence/delFence?hostIDs="+deleteParams+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
			    	 $("#data_loading").show();
			     },
				success : function(data) {
					if(data.result==1)
					{
						alert(data.message);
						location.reload();
					}
					else
					{
						alert(data.message);
						location.reload();
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
		return false;
	});
	
	//判断围栏ID是否存在
	
	$(".exithostId").hide();
	$("#hostId").blur(function(){
			var host = $("#hostId").val();
			var timenow = new Date().getTime();
			$.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/fence/isIdRepeat?fenceBean.hostID="+host+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result == 1)
					{
						$(".exithostId").hide();
					}
					else
					{
						$(".exithostId").show();
					}
				},
				error : function(data) {
					alert("请求出现异常!");
				}
			});
	});
	//---------------联动查询管理------------------
	$("#normal_gongsi_select").change(function ()
		    {
		        if($(this).val()==0)
		        {
		        	$("#normal_guanlichu_select").empty();
		        	$("#normal_guanlichu_select").append("<option value=''>全部</option>");
		        }
		        else
		        {
					var timenow = new Date().getTime();
		        	jQuery.ajax({
		    			type : 'GET',
		    			url : $("#ctx").val()+"/alarmmgt/queryDepartByBranch?branchId="+$(this).val()+"&d="+timenow,
		    			contentType: "application/json; charset=utf-8",
		    			success : function(data) {
		    				if(data.result==1)
		    				{
		    					var content = "";
		    					for(var i=0;i<data.object.length;i++)
		    					{	
		    						content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
		    					}
		    					$("#normal_guanlichu_select").empty();
		    		        	//$("#normal_guanlichu_select").append("<option value=''>全部</option>");
		    		        	$("#normal_guanlichu_select").append(content);
		    		        	
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
	
	
	//上传文件
	$("#importweilan").click(function(){
		$("#attach").val("");
		$("#uploadcontainer").show();
	});
	$("#calcelimport").click(function(){
		$("#uploadcontainer").hide();
	});
	$("#beginimport").click(function(){
		var postUrl = $("#ctx").val()+"/fence/batchImportFence";
		ajaxImportWeilan(postUrl);
	});
});