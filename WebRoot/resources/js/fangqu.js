function deleteIPC(obj)
{
	if(confirm("确定删除摄像头吗?"))
	{
		var url = $(obj).attr("durl");
		jQuery.ajax({
			type : 'GET',
			cache:false,
			url : url,
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result==1)
				{
					$(obj).parent().parent().remove();
					alert("删除成功");
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
	return false;
	
}

function ajaxImportFangqu(posturl){  
	if($("#zone").val()=="")
	{
		alert("请选择文件");
		return false;
	}
	var fileSuttfix=$("#zone").val().substr($("#zone").val().lastIndexOf(".")).toLowerCase();
	if(!(fileSuttfix==".xls"||fileSuttfix==".xlsx"))
	{
		alert("请选择Excel文件");
		$("#zone").val("");
		return false;
	}
	$("#data_loading").show();
    $.ajaxFileUpload({  
        url:posturl,  
        secureuri:false,                           
        fileElementId:'zone', 
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
        			var errorMsg="";
        			if(data.object['ZONEDATAERROR']!=null&&data.object['ZONEDATAERROR']!=""&&data.object['ZONEDATAERROR']!=undefined){
        				
            			$.each(data.object['ZONEDATAERROR'],function(i,n){
            				errorMsg=errorMsg+n;
    		            });
        			}
        			if(data.object['DEFENCEDATAERROR']!=null&&data.object['DEFENCEDATAERROR']!=""&&data.object['DEFENCEDATAERROR']!=undefined)
        			{
            			$.each(data.object['DEFENCEDATAERROR'],function(i,n){
            				errorMsg=errorMsg+n;
    		            });
        			}
        			if(data.object['INTEDATAERROR']!=null&&data.object['INTEDATAERROR']!=""&&data.object['INTEDATAERROR']!=undefined)
        			{
            			$.each(data.object['INTEDATAERROR'],function(i,n){
            				errorMsg=errorMsg+n;
    		            });
        			}
        			if(data.object['PULSEDATAERROR']!=null&&data.object['PULSEDATAERROR']!=""&&data.object['PULSEDATAERROR']!=undefined)
        			{
            			$.each(data.object['PULSEDATAERROR'],function(i,n){
            				errorMsg=errorMsg+n;
    		            });
        			}
        			if(data.object['POSITIONDATAERROR']!=null&&data.object['POSITIONDATAERROR']!=""&&data.object['POSITIONDATAERROR']!=undefined)
        			{
            			$.each(data.object['POSITIONDATAERROR'],function(i,n){
            				errorMsg=errorMsg+n;
    		            });
        			}
        			if(data.object['IPCDATAERROR']!=null&&data.object['IPCDATAERROR']!=""&&data.object['IPCDATAERROR']!=undefined)
        			{
            			$.each(data.object['IPCDATAERROR'],function(i,n){
            				errorMsg=errorMsg+n;
    		            });
        			}
        			if(data.object['SOUNDDATAERROR']!=null&&data.object['SOUNDDATAERROR']!=""&&data.object['SOUNDDATAERROR']!=undefined)
        			{
            			$.each(data.object['SOUNDDATAERROR'],function(i,n){
            				errorMsg=errorMsg+n;
    		            });
        			}
        			alert(errorMsg);
        			location.reload();
        		}
        		else
        		{
        			alert(data.object['FILE_FORMAT_ERROR']+"文件格式错误");
        		}
        	}
        	$("#zone").val("");
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
}
function checkStatus(status){
	if(status==2){
		alert("此防区跟管理处防区发生异常,请联系管理处排查。");
		return false;
	}else{
		return true;
	}
}
function checkMainIpcUnique(field,rules, i, options){
	var flag = false;
	if(field.val() == '1'){
		
		$('#shexiangtou_table tr:gt(0)').each(function(index,obj){
			
			if($.trim(obj.cells[2].innerHTML)=='主设备'){
				flag=true;
				return;
			}
		});
	}
  if(flag){
    // this allows to use i18 for the error msgs
    return options.allrules.validate2mainIpc.alertText;
  };
}

function checkZoneIdUnique(field,rules, i, options){
	var fangquid = field.val();
	var reg = new RegExp("^[0-9]{1,9}$");  
    if(!reg.test(fangquid)){  
       return;
    }
	var flag = false;
	var timenow = new Date().getTime();
	$.ajax({
		type : 'GET',
		cache:false,
		async:false,
		url : $("#ctx").val()+"/zone/isIdRepeat?zoneBean.zoneID="+field.val()+"&d="+timenow,
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			if(data.result!= 1)
			{
				flag=true;
			}
		},
		error : function(data) {
			alert("请求出现异常!");
		}
	});
  if(flag){
    // this allows to use i18 for the error msgs
    return options.allrules.validate2ZoneID.alertText;
  };
}

$(function(){
	
	$("#zoneaddbtn").click(function(){
		
		if(!($("#exitfanqguId").is(":hidden"))){
			return;
		}
		$("#fangqujibenxinxitianjia").submit();
		
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
	
	$("#fangqu_select_all").prop("checked", false);
	$("input[name='deleteids']").prop("checked", false);
	$("#fangqu_table").tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#fangqu_table").colorTable();
	$("#fangqu_select_all").on('click',function(){
		$("input[name='deleteids']").prop("checked", this.checked);
	});
	
	
	/*if($("#fangqu_search_filter option:selected").val()==1)
	{
		$("#queryfangquid").show();
		$("#queryfangquid").val("");
		$("#queryfangquname").hide();
		$("#weilanleixing").hide();
	}
	if($("#fangqu_search_filter option:selected").val()==2)
	{
		$("#queryfangquid").hide();
		$("#queryfangquname").show();
		$("#queryfangquname").val("");
		$("#weilanleixing").hide();
	}
	if($("#fangqu_search_filter option:selected").val()==3)
	{
		$("#queryfangquid").hide();
		$("#queryfangquname").hide();
		$("#queryfangquid").val("");
		$("#queryfangquname").val("");
		$("#weilanleixing").show();
	}*/
	$("#fangqu_search_filter").change(function ()
    {
        if($(this).val()==1)
        {
        	$("#queryfangquid").show();
        	$("#queryfangquname").hide();
        	$("#queryfangquid").val("");
        	$("#weilanleixing").hide();
        }
        else if($(this).val()==2)
        {
        	$("#queryfangquid").hide();
    		$("#queryfangquname").show();
    		$("#queryfangquname").val("");
    		$("#weilanleixing").hide();
        }
        else
        {
        	$("#queryfangquid").hide();
    		$("#queryfangquname").hide();
    		$("#weilanleixing").show();
        }
    });
	
	
	$("body").undelegate('.chefanglink', 'click').delegate(".chefanglink","click",function()
	{
		if(confirm("确定要撤防吗?"))
		{
			var zonid = $(this).attr("fangquid");
			var obj = $(this);
			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/zone/zoneCtrl?zoneBean.status=0&zoneBean.zoneID="+zonid+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
			    	 $("#data_loading").show();
			     },
				success : function(data) {
					if(data.result==1)
					{
						
						$(obj).removeClass("chefanglink");
						$(obj).addClass("bufanglink");
						$(obj).text("布防");
						$("#zone"+zonid+"_status").html("已撤防");
						return true;
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
	$("body").undelegate('.bufanglink', 'click').delegate(".bufanglink","click",function()
	{
		if(confirm("确定要布防吗?"))
		{
			var zonid = $(this).attr("fangquid");
			var obj = $(this);
			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/zone/zoneCtrl?zoneBean.status=1&zoneBean.zoneID="+zonid+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
			    	 $("#data_loading").show();
			     },
				success : function(data) {
					if(data.result==1)
					{
						$(obj).removeClass("bufanglink");
						$(obj).addClass("chefanglink");
						$(obj).text("撤防");
						$("#zone"+zonid+"_status").html("已布防");
						return true;
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
	//查看告警
	$(".view_fangqugaojing").click(function(){
		var timenow = new Date().getTime();
		jQuery.ajax({
			type : 'GET',
			cache:false,
			contentType : 'application/json',
			url : $("#ctx").val()+"/zone/queryAlarmInfo?zoneBean.zoneID="+$(this).attr("fangquid")+"&d="+timenow,
			success : function(data) {
				if(data.result==0)
				{
					$("#fangqu_gaojing_content_container .tableui tbody").empty();
					$('#fangqu_gaojing_content_container').append("页面加载失败");
				}
				else
				{
					$("#fangqu_gaojing_content_container .tableui tbody").empty();
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
		            	$("#fangqu_gaojing_content_container .tableui tbody").append(node);	
		            });
				}
				$("#view_fangqu_gaojing_container").dialogModal({
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
	
	$("#fangqu_weilanleixing").change(function ()
    {
        if($(this).val()==1)
        {
        	$("#maichong").show();
        	$("#yitihua").hide();
        	$("#dingweixing").hide();
        	$("#tancexing").hide();
        }
        else if($(this).val()==2)
        {
        	$("#yitihua").show();
        	$("#maichong").hide();
        	$("#dingweixing").hide();
        	$("#tancexing").hide();
        }
        else if($(this).val()==3)
        {
        	$("#dingweixing").show();
        	$("#maichong").hide();
        	$("#yitihua").hide();
        	$("#tancexing").hide();
        }
        else if($(this).val()==4)
        {
        	$("#tancexing").show();
        	$("#maichong").hide();
        	$("#yitihua").hide();
        	$("#dingweixing").hide();
        }
        var zID =$("#zoneIDofaddshebei").val();
        var leixing = jQuery("#fangqu_weilanleixing  option:selected").val();
		var timenow = new Date().getTime();
		jQuery.ajax({
			type : 'GET',
			cache:false,
			url : $("#ctx").val()+"/zone/findHostIDsByFenceType?zoneBean.fenceType="+leixing+"&zoneBean.zoneID="+zID+"&d="+timenow,
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result ==1)
				{
					if(data.object != null)
					{
						$("#hostidselect option").remove();
						for(var i=0;i<data.object.length;i++)
						{
							 $("#hostidselect").append("<option value='"+data.object[i]+"'>"+data.object[i]+"</option>");
						}
					}
				}
			},
			error : function(data) {
				alert("请求出现异常!");
			}
		});
    });
	
	//编辑页面摄像头添加
	$("#shexiangtouupdateadd").click(function(){
		if($("#addIPCForm").validationEngine('validate'))
		{
			if(confirm("确定添加摄像头吗?"))
			{
				 jQuery.ajax({
						type : 'POST',
						data:$("#addIPCForm").serialize(),
						dataType:"json",
						url : $("#ctx").val()+"/zone/addIpcInfo",
						//contentType: "application/json; charset=utf-8",
						success : function(data) {
							if(data.result==1)
							{
								var fangquid=$("#fangqushexiangtoubianjiid").val();
								var shebeiid = $("#yinpinshebei_shebeiid").val();
								var htm = "<tr><td>";
								htm += $("#yinpinshebei_shebeiid").val();
								htm += "</td><td>";
								htm += $("#yinpinshebei_syuzhiwei").val();
								htm += "</td><td>";
								if($("#yinpinshebei_zhufu").val()==1){
									htm +="主设备";
								}else{
									htm +="辅设备";
								}
								htm += "</td><td>";
								htm += $("#yinpinshebei_fujia").val();
								htm += "</td><td>";
								//htm += "<c:if test='${poteviofn:clContains(sessionScope.session.authorizatedOps,\"删除摄像头映射\") }'>";
								htm +="<a href='javascript:void(0);' durl='"+$("#ctx").val()+"/zone/delIpcMapInfo?ipcZoneMap.ipcId="+shebeiid+"&ipcZoneMap.zoneID="+fangquid+"' onclick='deleteIPC(this);'>删除</a>";
								//htm +="</c:if>";
								htm +="</td></tr>"
								$("#shexiangtou_table").find("tbody").append(htm);
								$("#shexiangtou_table").colorTable();
								alert("添加成功");
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
			return false;
		}
	});
	$(".editfanquinfo").click(function()
	{
		$("#fangqu_info_table").hide();
		$("#edit_fangqu_container").show();
	});
	//防区编辑基本信息---------------------------------------------------------
	$("#fangqueditbtn").click(function()
	{
		/*$("#fangqu_info_table").show();
		$("#edit_fangqu_container").hide();*/
		if($("#editfangqucontainer").validationEngine('validate'))
		{
			jQuery.ajax({
				type : 'POST',
				data:$("#editfangqucontainer").serialize(),
				dataType:"json",
				url : $("#ctx").val()+"/zone/doModZone",
				//contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result==1)
					{
						alert("编辑成功!")
						location.reload();
					}
					else
					{
						alert("编辑失败");
					}
				},
				error : function(data) {
					alert("请求出现异常!");
				}
			});
		}
	});
	
	$("#fangqudianziweilan_save_btn1").click(function()
	{
		zoneFenceMapEdit();
	});
	$("#fangqudianziweilan_save_btn2").click(function()
	{
		zoneFenceMapEdit();
	});
	$("#fangqudianziweilan_save_btn3").click(function()
	{
		zoneFenceMapEdit();
	});
	$("#fangqudianziweilan_save_btn4").click(function()
	{
		zoneFenceMapEdit();
	}); 
	//防区添加摄像头添加
	$("#shexiangtouadd").click(function()
	{
		if($("#addIPCForm").validationEngine('validate'))
		{
			if(confirm("确定添加摄像头吗?"))
			{
				 jQuery.ajax({
						type : 'POST',
						data:$("#addIPCForm").serialize(),
						dataType:"json",
						url : $("#ctx").val()+"/zone/addIpcInfo",
						//contentType: "application/json; charset=utf-8",
						success : function(data) {
							if(data.result==1)
							{
								var htm = "<tr><td>";
								htm += $("#yinpinshebei_shebeiid").val();
								htm += "</td><td>";
								htm += $("#yinpinshebei_syuzhiwei").val();
								htm += "</td><td>";
								if($("#yinpinshebei_zhufu").val()==1){
									
									htm +="主设备";
									
								}else{
									
									htm +="辅设备";
								}
								htm += "</td><td>";
								htm += $("#yinpinshebei_fujia").val();
								htm += "</td></tr>";
								$("#shexiangtou_table").find("tbody").append(htm);
								$("#shexiangtou_table").colorTable();
								alert("添加成功!");
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
			return false;
		}
	});
	function zoneFenceMapEdit()
	{
		//alert($("#dianziweilanshexiangtouedit").serialize());
		 jQuery.ajax({
				type : 'POST',
				data:$("#dianziweilanshexiangtouedit").serialize(),
				dataType:"json",
				url : $("#ctx").val()+"/zone/updateFenceMapInfo",
				//contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result==1)
					{
						
						alert("编辑成功!")
						location.reload();
					}
					else
					{
						alert(data.message);
						location.reload();
					}
				},
				error : function(data) {
					alert("请求出现异常!");
				}
			});
	}
	
	$("#cancel_fangqueditbtn").click(function()
	{
		/*$("#fangqu_info_table").show();
		$("#edit_fangqu_container").hide();*/
		 location.reload()
	});
	
	$("#fangqudianziweilan_edit_btn4").click(function()
			{
				 location.reload()
	 });
	$("#fangqudianziweilan_edit_btn3").click(function()
			{
				 location.reload()
	 });
	$("#fangqudianziweilan_edit_btn2").click(function()
			{
				 location.reload()
	 });
	$("#fangqudianziweilan_edit_btn1").click(function()
			{
				 location.reload()
	 });
	
	$(".editfangqudianziweilaninfo").click(function()
	{
		$("#fangqu_dianziweilan_info").hide();
		$("#fangqu_dianziweilan_edit_container").show();
	});
	$("#fangqudianziweilan_save_btn").click(function()
	{
		$("#fangqu_dianziweilan_info").show();
		$("#fangqu_dianziweilan_edit_container").hide();
	});
	$("#fangqudianziweilan_edit_btn").click(function()
	{
		$("#fangqu_dianziweilan_info").show();
		$("#fangqu_dianziweilan_edit_container").hide();
	});
	
	//删除防区
	$("#deletefangqu").click(function(){
		var arrChk=$("#deleteids:checked");
		if(arrChk.size()<=0)
		{
			alert("请选择要删除的防区");
			return false;
		}
		var deleteArray = new Array();
		$(arrChk).each(function(i)
		{
			if(!(deleteArray.indexOf($(this).attr("value"))>=0))
			{
				deleteArray.push($(this).attr("value"));
			}
		})
		var deleteParams = deleteArray.join(",");
		if(confirm("确定删除选择的防区吗?"))
		{
			var timenow = new Date().getTime();
	        jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/zone/delZone?zoneIDs="+deleteParams+"&d="+timenow,
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
	
	//防区添加电子围栏
	$("#adddianziweilan").click(function(){
		 jQuery.ajax({
				type : 'POST',
				data:$("#fangquform").serialize(),
				dataType:"json",
				url : $("#ctx").val()+"/zone/addFenceInfo",
				//contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result==1)
					{
						$("#neibufangquid").val("");
						$("#qishidian").val("");
						$("#jieshudian").val("");
						alert(data.message);
						$("#adddianziweilan").remove();
					}
					else
					{
						alert(data.message);
						location.href=$("#ctx").val()+"/zone/goAddZone";
					}
				},
				error : function(data) {
					alert("请求出现异常!");
				}
			});
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
		var postUrl = $("#ctx").val()+"/zone/batchImportZone";
		ajaxImportFangqu(postUrl);
	});
	//不能为空验证
	$("#fangqujibenxinxitianjia").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#fangquform").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#addIPCForm").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#editfangqucontainer").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	
	//判断防区ID是否存在 
	$("#exitfanqguId").hide();
	/*$("#fangquid").blur(function(){
			var fangquid = $("#fangquid").val();
			var reg = new RegExp("^[0-9]*$");  
		    if(!reg.test(fangquid)){  
		        alert("请输入数字!");
		    }
		    else
		    {
				var timenow = new Date().getTime();
		    	$.ajax({
					type : 'GET',
					cache:false,
					url : $("#ctx").val()+"/zone/isIdRepeat?zoneBean.zoneID="+fangquid+"&d="+timenow,
					contentType: "application/json; charset=utf-8",
					success : function(data) {
						if(data.result == 1)
						{
							$("#exitfanqguId").hide();
						}
						else
						{
							$("#exitfanqguId").show();
						}
					},
					error : function(data) {
						alert("请求出现异常!");
					}
				});
		    }
	});*/
	//----start----电子围栏防区映射添加数字验证-------------------------------
	
	$("#neibufangquid").blur(function(){
		var neibufangquid = $("#neibufangquid").val();
		var reg = new RegExp("^[0-9]*$");  
	    if(!reg.test(neibufangquid)){  
	        alert("请输入数字!");
	    }
	});
	$("#qishidian").blur(function(){
		var qishidian = $("#qishidian").val();
		var reg = new RegExp("^[0-9]*$");  
	    if(!reg.test(qishidian)){  
	        alert("请输入数字!");
	    }
	});
	$("#jieshudian").blur(function(){
		var jieshudian = $("#jieshudian").val();
		var reg = new RegExp("^[0-9]*$");  
	    if(!reg.test(jieshudian)){  
	        alert("请输入数字!");
	    }
	});
	//-----end---电子围栏防区映射添加数字验证-----------------------
	
});