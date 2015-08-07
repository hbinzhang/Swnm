function ajaxImportFangqu(posturl){  
	if($("#yinpin").val()=="")
	{
		alert("请选择文件");
		return false;
	}
	var fileSuttfix=$("#yinpin").val().substr($("#yinpin").val().lastIndexOf(".")).toLowerCase();
	if(!(fileSuttfix==".xls"||fileSuttfix==".xlsx"))
	{
		alert("请选择Excel文件");
		$("#yinpin").val("");
		return false;
	}
	$("#data_loading").show();
    $.ajaxFileUpload({  
        url:posturl,  
        secureuri:false,                           
        fileElementId:'yinpin', 
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
        			var msg="";
        			if(data.object['ADAPTERDATAERROR']!=null&&data.object['ADAPTERDATAERROR']!=""&&data.object['ADAPTERDATAERROR']!=undefined){
        				msg=msg+"音频终端表："+"第"+data.object['ADAPTERDATAERROR']+"行出错。";
        			}
        			else if(data.object['IOCONTROLLERDATAERROR']!=null||data.object['IOCONTROLLERDATAERROR']!=""&&data.object['IOCONTROLLERDATAERROR']!=undefined)
        			{
        				msg=msg+"IO控制器表："+"第"+data.object['IOCONTROLLERDATAERROR']+"行出错。";
        			}
        			else if(data.object['SOUNDSERVERTABLE']!=null||data.object['SOUNDSERVERTABLE']!=""&&data.object['SOUNDSERVERTABLE']!=undefined)
        			{
        				msg=msg+"音频服务器表："+"第"+data.object['INTEDATAERROR']+"行出错。";
        			}
        			else if(data.object['AUDIOMANUFACTURERDATAERROR']!=null||data.object['AUDIOMANUFACTURERDATAERROR']!=""&&data.object['AUDIOMANUFACTURERDATAERROR']!=undefined)
        			{
        				msg=msg+"厂商信息表："+"第"+data.object['AUDIOMANUFACTURERDATAERROR']+"行出错。";
        			}
        			alert(msg);
        		}
        		else
        		{
        			alert(data.object['FILE_FORMAT_ERROR']+"文件格式错误");
        		}
        	}
        	$("#yinpin").val("");
        	$("#data_loading").hide();
        	/*var jsonString = $.parseJSON(data);
        	var success = $.ajaxCheck(jsonString);
        	if(success)
        	{
        		alert(jsonString.message);
        		location.reload();
        	}
        	else
        	{
        		alert(jsonString.message);
        	}
        	$("#attach").val("");*/
        },  
        error:function(data, status, e){ 
        	alert("上传过程中出现错误，请重试");
        	$("#data_loading").hide();
        }  
    });
    //$("#data_loading").hide();
}
$(function(){
	//音频编辑界面
	$("#editbtn").click(function(){
		if($("#yinpin_form").validationEngine('validate'))
		{
			var redirectUrl = $("#redirectUrl").val();
			$("#yinpin_form").ajaxSubmit({
			     type: "post",
				 url : $("#ctx").val()+"/sounddevUpdate",
			     dataType: "json",
			     beforeSubmit:function(e){
			    	 $("#data_loading").show();
			     },
			     success : function(data) {
			    	if(data.result ==2)
			    	{
			    		alert("设备ID已存在！");
			    		return false;
			    	}
					if(data.result==1)
					{
						window.location = $("#ctx").val()+redirectUrl;
					}
					else
					{
						if(data.message==""||data.message==null)
						{
							alert("编辑失败");
						}
						else
						{
							alert(data.message);
						}
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
	
	//音频添加界面
	$("#addbtn").click(function(){
		if($("#yinpinadd_form").validationEngine('validate'))
		{
			var redirectUrl = $("#redirecturl").val();
			$("#yinpinadd_form").ajaxSubmit({
			     type: "post",
				 url : $("#ctx").val()+"/sounddevAdd",
			     dataType: "json",
			     beforeSubmit:function(e){
			    	 $("#data_loading").show();
			     },
			     success : function(data) {
			    	if(data.result ==2)
			    	{
			    		alert("设备ID已存在！");
			    		return false;
			    	}
					if(data.result==1)
					{
						window.location = $("#ctx").val()+redirectUrl;
					}
					else
					{
						if(data.message==""||data.message==null)
						{
							alert("添加失败");
						}
						else
						{
							alert(data.message);
						}
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
	
	//联动查询设备ID
	$("#shebeiidtip").hide();
	$("#shebeiId").blur(function(){
		var shebeiId = $("#shebeiId").val();
		var timenow = new Date().getTime();
	    	$.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/SounddevQueryById?id="+shebeiId+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result == 0)
					{
						$("#shebeiidtip").hide();
					}
					else
					{
						$("#shebeiidtip").show();
					}
				},
				error : function(data) {
					alert("请求出现异常!");
				}
			});
	});
	
	$("#deletefangqu").click(function(){
		var arrChk=$("#deleteids:checked");
		if(arrChk.size()<=0)
		{
			alert("请选择要删除的音频设备");
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
		if(confirm("确定删除选择的音频设备吗?"))
		{
			var timenow = new Date().getTime();
	        jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/sounddevDelete?d="+timenow+"&toDeleteSoundDevId="+deleteParams,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
				success : function(data) {
					if(data.result==1)
					{
						location.reload(true);
					}
					else
					{
						if(data.message==""||data.message==null)
						{
							alert("处理失败");
						}
						else
						{
							alert(data.message);
						}
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
	
	
	
	//删除
	$("#yinpin_select_all").prop("checked", false);
	$("input[name='deleteids']").prop("checked", false);
	$("#yinpin_table").tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#yinpin_table").colorTable();
	$("#yinpin_select_all").on('click',function(){
		$("input[name='deleteids']").prop("checked", this.checked);
	});
	
	//上传文件
	$("#importfangqu").click(function(){
		$("#attach").val("");
		$("#uploadcontainer").show();
	});
	$("#calcelimport").click(function(){
		$("#uploadcontainer").hide();
	});
	$("#beginimport").click(function(){
		var postUrl = $("#ctx").val()+"/sounddevBatchImport";
		ajaxImportFangqu(postUrl);
	});
	
	function chuanlichu(){
		var content ="";
		$("#normal_guanlichu_select").each(function(){
			var glc = $("#normal_guanlichu_select").val();
			content += glc+",";
//			alert(content);
			/*guanlichu.push(glc);
			guanlichu.serializeArray();*/
			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/sounddevQuery?d="+timenow+"&queryCondition.mgt="+content,
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result==1)
					{
						alert("成功");
					}
					else
					{
						alert("处理失败");
					}
				},
				error : function(data) {
					alert("请求出现异常!");
				}
			});
		});
	}
	
	$("#yinpin_search_filter").change(function ()
		    {
		        if($(this).val()==1)
		        {
		        	$("#shebeiid").show();
		        	$("#ipdizhi").hide();
		        	$("#shebeiid").val("");
		        	$("#shebeileixing").hide();
		        }
		        else if($(this).val()==2)
		        {
		        	$("#shebeiid").hide();
		    		$("#ipdizhi").show();
		    		$("#ipdizhi").val("");
		    		$("#shebeileixing").hide();
		        }
		        else
		        {
		        	$("#shebeiid").hide();
		    		$("#ipdizhi").hide();
		    		$("#shebeileixing").show();
		        }
		    });
	
	//---------------联动查询管理处-------------------
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
		    		        	$("#normal_guanlichu_select").append("<option value=''>全部</option>");
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
	
	$("#yinpin_table").tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#yinpin_table").colorTable();
	$("#yinpin_select_all").on('click',function(){
		$("input[name='deleteids']").prop("checked", this.checked);
	});
	if($("#yinpin_search_filter option:selected").val()==3)
	{
		$("#yinpinleixing").show();
    	$("#yingpininput").hide();
    	$("#yingpininput").val("");
	}
	$("#yinpin_search_filter").change(function ()
    {
        if($(this).val()==3)
        {
        	$("#yinpinleixing").show();
        	$("#yingpininput").hide();
        	$("#yingpininput").val("");
        }
        else
        {
        	$("#yinpinleixing").hide();
        	$("#yingpininput").show();
        }
    });
	//添加音频设备时联动查询所属设备，设备管理处，关联摄像头id
	$("#yinpin_type").change(function ()
    {
		var timenow = new Date().getTime();
        if($(this).val()=="音频服务器")
        {
        	$("#yinpin_shebei_list").empty();
        	$('#yinpin_shebei_list').attr("disabled","disabled");
        	$("#yinpin_guanlichu_list").empty();
        	$('#yinpin_guanlichu_list').attr("disabled","disabled");
        	$("#yinpin_shexiangtou_id").empty();
        	$('#yinpin_shexiangtou_id').attr("disabled","disabled");
        }
        else if($(this).val()=="音频终端")
        {
        	$('#yinpin_shebei_list').removeAttr("disabled");
        	$('#yinpin_guanlichu_list').removeAttr("disabled");
        	$('#yinpin_shexiangtou_id').removeAttr("disabled");
			$("#yinpin_shebei_list").empty();
			$("#yinpin_guanlichu_list").empty();
			$("#yinpin_shexiangtou_id").empty();
        	jQuery.ajax({
    			type : 'GET',
    			url : $("#ctx").val()+"/sounddevaddadapterattachedquery?d="+timenow,
    			contentType: "application/json; charset=utf-8",
    			beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
    			success : function(data) {
    				if(data.result==1)
    				{
    					var content = "";
    					var content2 ="";
    					var content3="";
    					var shebei = data.object['soundServer'];
    					var mgt = data.object['mgts'];
    					var shexiangtou = data.object['ipcids'];
    					for(var i=0;i<shebei.length;i++)
    					{	
    						content+="<option value="+shebei[i].SERVERID+">"+shebei[i].SERVERNAME+"</option>";
    					}
    					for(var i=0;i<mgt.length;i++)
    					{	
    						content2+="<option value="+mgt[i].id+">"+mgt[i].name+"</option>";
    					}
    					for(var i=0;i<shexiangtou.length;i++)
    					{	
    						content3+="<option value="+shexiangtou[i]+">"+shexiangtou[i]+"</option>";
    					}
						$("#yinpin_shebei_list").append(content);
						$("#yinpin_guanlichu_list").append(content2);
						$("#yinpin_shexiangtou_id").append(content3);
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
        	/*$("#yinpin_shebei_list").empty();
        	$("#yinpin_shebei_list").append("<option value='0'>所属设备1(192.168.1.1)</option>");
        	$("#yinpin_shebei_list").append("<option value='0'>所属设备2(192.168.1.5)</option>");
        	$("#yinpin_shebei_list").append("<option value='0'>所属设备3(192.168.1.10)</option>");
        	$('#yinpin_guanlichu_list').removeAttr("disabled");
        	$("#yinpin_guanlichu_list").empty();
        	$("#yinpin_guanlichu_list").append("<option value='0'>管理处1</option>");
        	$("#yinpin_guanlichu_list").append("<option value='0'>管理处2</option>");
        	$("#yinpin_guanlichu_list").append("<option value='0'>管理处3</option>");
        	$('#yinpin_shexiangtou_id').removeAttr("disabled");*/
        }
        else if($(this).val()=="IO控制器")
        {
        	$('#yinpin_shebei_list').removeAttr("disabled");
        	$('#yinpin_shexiangtou_id').attr("disabled","disabled");
        	$('#yinpin_guanlichu_list').attr("disabled","disabled");
			$("#yinpin_shebei_list").empty();
			$("#yinpin_guanlichu_list").empty();
			$("#yinpin_shexiangtou_id").empty();
        	jQuery.ajax({
    			type : 'GET',
    			url : $("#ctx").val()+"/sounddevQueryAllAdapterNotAttachedController?d="+timenow,
    			contentType: "application/json; charset=utf-8",
    			beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
    			success : function(data) {
    				if(data.result==1)
    				{
    					var content = "";
    					var shebei = data.object['audioAdapter'];
    					for(var i=0;i<shebei.length;i++)
    					{	
    						content+="<option value="+shebei[i].audioId+">"+shebei[i].audioName+"</option>";
    					}
						$("#yinpin_shebei_list").append(content);
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
        	/*$("#yinpin_shebei_list").empty();
        	$("#yinpin_shebei_list").append("<option value='0'>所属设备1(192.168.1.1)</option>");
        	$("#yinpin_shebei_list").append("<option value='0'>所属设备2(192.168.1.5)</option>");
        	$("#yinpin_shebei_list").append("<option value='0'>所属设备3(192.168.1.10)</option>");
        	$("#yinpin_guanlichu_list").empty();
        	$('#yinpin_guanlichu_list').attr("disabled","disabled");
        	$("#yinpin_shexiangtou_id").val("");
        	$('#yinpin_shexiangtou_id').attr("disabled","disabled");*/
        }
    });
	//根据管理处关联查询摄像头
	$("#yinpin_guanlichu_list").change(function (){
		if($("#yinpin_type").val()=="音频终端")
		{
			var timenow = new Date().getTime();
			$("#yinpin_shexiangtou_id").empty();
			jQuery.ajax({
    			type : 'GET',
    			url : $("#ctx").val()+"/SoundDevQueryIPCByMgtId?mgtId="+$("#yinpin_guanlichu_list").val()+"&d="+timenow,
    			contentType: "application/json; charset=utf-8",
    			beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
    			success : function(data) {
    				if(data.result==1)
    				{
    					var content = "";
    					var shexiangtou = data.object;
    					for(var i=0;i<shexiangtou.length;i++)
    					{	
    						content+="<option value="+shexiangtou[i]+">"+shexiangtou[i]+"</option>";
    					}
        		        $("#yinpin_shexiangtou_id").append(content);
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
	
	$(".input_tip").hide();
	$("#yinpin_form input").focus(function(){
        $(this).next(".input_tip").show();

	}); 
	$("#yinpin_form input").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	$("#yinpin_form select").focus(function(){
        $(this).next(".input_tip").show();

	}); 
	$("#yinpin_form select").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	
	$("#yinpinadd_form input").focus(function(){
        $(this).next(".input_tip").show();

	}); 
	$("#yinpinadd_form input").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	$("#yinpinadd_form select").focus(function(){
        $(this).next(".input_tip").show();

	}); 
	$("#yinpinadd_form select").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	
	$("#yinpinadd_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#yinpin_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
});