$(function(){
//创建考核指标
$("#addassessment_btn").click(function(){
	if($("#addassessment_form").validationEngine('validate'))
	{
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#addassessment_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/createAssessment?d="+timenow,
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
//编辑考核指标
$("#editassessment_btn").click(function(){
	if($("#editassessment_form").validationEngine('validate'))
	{
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#editassessment_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/updateAssessment?d="+timenow,
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
//评价考核指标
$("#evaluate_btn").click(function(){
	if($("#evaluate_form").validationEngine('validate'))
	{
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#evaluate_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/evaluateAssessment?d="+timenow,
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
//删除考核指标
$(".deleteassessment").click(function(){
	if(confirm("确定要删除该考核指标吗?")){
	var obj = $(this);
	var name = $(this).attr("name");
	var orgId = $(this).attr("orgId");
	var timenow = new Date().getTime();				
        jQuery.ajax({
			type : 'GET',
			cache:false,
			url : $("#ctx").val()+"/securityinfo/deleteAssessment?name="+name+"&orgId="+orgId+"&d="+timenow,
			contentType: "application/json; charset=utf-8",
			beforeSend:function(XMLHttpRequest){
		    	 $("#data_loading").show();
		     },
			success : function(data) {
				if(data.result==1)
				{
					alert(data.message);
					location.reload();
				}else
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
//通知考核指标
$(".noticeassessment").click(function(){
	if(confirm("确定要通知该考核指标吗?")){
	var obj = $(this);
	var name = $(this).attr("name");
	var orgId = $(this).attr("orgId");
	var timenow = new Date().getTime();				
        jQuery.ajax({
			type : 'GET',
			cache:false,
			url : $("#ctx").val()+"/securityinfo/noticeAssessment?name="+name+"&orgId="+orgId+"&d="+timenow,
			contentType: "application/json; charset=utf-8",
			beforeSend:function(XMLHttpRequest){
		    	 $("#data_loading").show();
		     },
			success : function(data) {
				if(data.result==1)
				{
					alert(data.message);
					location.reload();
				}else
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
//创建检查情况
$("#addcheckinfo_btn").click(function(){
	if($("#addcheckinfo_form").validationEngine('validate'))
	{
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#addcheckinfo_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/createCheckInfo?d="+timenow,
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
//删除考核指标
$(".deletecheckinfo").click(function(){
	if(confirm("确定要删除该检查情况吗?")){
	var obj = $(this);
	var indexName = $(this).attr("indexName");
	var orgId = $(this).attr("orgId");
	var time = $(this).attr("time");
	var checkInfo = $(this).attr("checkInfo");
	var timenow = new Date().getTime();				
        jQuery.ajax({
			type : 'GET',
			cache:false,
			url : $("#ctx").val()+"/securityinfo/deleteCheckInfo?indexName="+indexName+"&orgId="+orgId+"&time="+time+"&d="+timenow,
			contentType: "application/json; charset=utf-8",
			beforeSend:function(XMLHttpRequest){
		    	 $("#data_loading").show();
		     },
			success : function(data) {
				if(data.result==1)
				{
					alert(data.message);
					location.reload();
				}else
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
	$("#select_assessment_kaohejigou").change(function ()
		{
		    jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/securityinfo/queryOrgPersonByOrg?orgId="+$(this).val(),
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result==1)
					{
						var content = "";
						for(var i=0;i<data.object.length;i++)
						{	
							content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
						}
						$("#select_assessment_kaohejigouzerenren").empty();
			        	$("#select_assessment_kaohejigouzerenren").append(content);
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
	$("#select_assessment_kaohejigou").change(function ()
		{
		    jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/securityinfo/queryAssessNameByOrg?orgId="+$(this).val(),
				contentType: "application/json; charset=utf-8",
				success : function(data) {
					if(data.result==1)
					{
						var content = "";
						for(var i=0;i<data.object.length;i++)
						{	
							content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
						}
						$("#select_assessment_zhibiaomingcheng").empty();
			        	$("#select_assessment_zhibiaomingcheng").append(content);
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
	$(".input_tip").hide();
	$("#addassessment_form input").focus(function(){
	    $(this).next(".input_tip").show();
	
	}); 
	$("#addassessment_form input").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	$("#addassessment_form select").focus(function(){
	    $(this).next(".input_tip").show();
	
	}); 
	$("#addassessment_form select").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	
	$("#addassessment_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#editassessment_form input").focus(function(){
	    $(this).next(".input_tip").show();
	
	}); 
	$("#editassessment_form input").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	$("#editassessment_form select").focus(function(){
	    $(this).next(".input_tip").show();
	
	}); 
	$("#editassessment_form select").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	
	$("#editassessment_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
});
function checkAssessmentExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var jsonData ="assessment.name="+$('#name').val()+"&assessment.orgId="+$('#orgId').val();
	$.ajax({
		url:basePath + '/securityinfo/checkAssessmentExist.action?'+jsonData,
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证检查情况是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}
function checkCheckInfoExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var jsonData ="checkInfo.time="+$('#time').val()+"&checkInfo.orgId="+$('#orgId').val()+"&checkInfo.indexName="+$('#indexName').val();
	$.ajax({
		url:basePath + '/securityinfo/checkCheckInfoExist.action?'+jsonData,
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证检查情况是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}