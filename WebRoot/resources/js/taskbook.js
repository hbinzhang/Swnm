$(function(){
	// 任务书模块
	// 查看任务书详细信息
	$(".taskbook_detail").click(function(){
		var taskbookid = $(this).attr("taskbookid");
		$(".detail_table tbody tr .content_renwuming").html($("#taskbook"+taskbookid+"_renwuming").text());
		$(".detail_table tbody tr .content_mubiao").html($("#taskbook"+taskbookid+"_mubiao").text());
		$(".detail_table tbody tr .content_renwuneirong").html($("#taskbook"+taskbookid+"_renwuneirong").text());
		$(".detail_table tbody tr .content_renwuleixing").html($("#taskbook"+taskbookid+"_renwuleixing").text());
		$(".detail_table tbody tr .content_renwuzhuangtai").html($("#taskbook"+taskbookid+"_renwuzhuangtai").text());
		$(".detail_table tbody tr .content_zhidingshijian").html($("#taskbook"+taskbookid+"_zhidingshijian").text());
		$(".detail_table tbody tr .content_fabushijian").html($("#taskbook"+taskbookid+"_fabushijian").text());
		$(".detail_table tbody tr .content_qidongshijian").html($("#taskbook"+taskbookid+"_qidongshijian").text());
		$(".detail_table tbody tr .content_jieshushijian").html($("#taskbook"+taskbookid+"_jieshushijian").text());
		$(".detail_table tbody tr .content_zhidingjigou").html($("#taskbook"+taskbookid+"_zhidingjigou").text());
		$(".detail_table tbody tr .content_zhidingjigoufuzeren").html($("#taskbook"+taskbookid+"_zhidingjigoufuzeren").text());
		$(".detail_table tbody tr .content_zerenjigou").html($("#taskbook"+taskbookid+"_zerenjigou").text());
		$(".detail_table tbody tr .content_zerenjigoufuzeren").html($("#taskbook"+taskbookid+"_zerenjigoufuzeren").text());
		$(".detail_table tbody tr .content_beizhu").html($("#taskbook"+taskbookid+"_beizhu").text());

		$('#taskbook_detail_container').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
	//删除任务书
	$(".deletetaskbook").click(function(){
		if(confirm("确定要删除该任务吗?")){
		var obj = $(this);
		var name = $(this).attr("name");
		var planOrgId = $(this).attr("planOrgId");
		var timenow = new Date().getTime();				
	        jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/securityinfo/deleteTaskBook?name="+name+"&planOrgId="+planOrgId+"&d="+timenow,
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
	//发布任务书
	$(".publishtaskbook").click(function(){
		if(confirm("确定要发布该任务吗?")){
		var obj = $(this);
		var name = $(this).attr("name");
		var planOrgId = $(this).attr("planOrgId");
		var timenow = new Date().getTime();
	        jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/securityinfo/publishTaskBook?name="+name+"&planOrgId="+planOrgId+"&d="+timenow,
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
	//关闭任务书
	$(".closetaskbook").click(function(){
		if(confirm("确定要关闭该任务吗?")){
		var obj = $(this);
		var name = $(this).attr("name");
		var planOrgId = $(this).attr("planOrgId");
		var timenow = new Date().getTime();
	        jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/securityinfo/closeTaskBook?name="+name+"&planOrgId="+planOrgId+"&d="+timenow,
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
	//编辑任务书
	$("#edittaskbook_btn").click(function(){
		if($("#edittaskbook_form").validationEngine('validate'))
		{
			var redirectUrl = $("#redirectUrl").val();
			var timenow = new Date().getTime();				
			$("#edittaskbook_form").ajaxSubmit({
			     type: "post",
				 url : $("#ctx").val()+"/securityinfo/updateTaskBook?d="+timenow,
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
	//创建任务书
	$("#addtaskbook_btn").click(function(){
		if($("#addtaskbook_form").validationEngine('validate'))
		{
			var redirectUrl = $("#redirectUrl").val();
			var timenow = new Date().getTime();				
			$("#addtaskbook_form").ajaxSubmit({
			     type: "post",
				 url : $("#ctx").val()+"/securityinfo/createTaskBook?d="+timenow,
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
	$("#select_taskbook_dingzhijigou").change(function ()
	{
	    jQuery.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/securityinfo/queryInChargeOrgsByPorg?planOrgId="+$(this).val(),
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result==1)
				{
					var content = "";
					for(var i=0;i<data.object.length;i++)
					{	
						content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
					}
					$("#select_taskbook_zerenjigou").empty();
		        	$("#select_taskbook_zerenjigou").append(content);
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
	$("#select_taskbook_inchargeorg").change(function ()
			{
			    jQuery.ajax({
					type : 'GET',
					url : $("#ctx").val()+"/securityinfo/queryInChargeOrgPersonByOrg?inChargeOrgId="+$(this).val(),
					contentType: "application/json; charset=utf-8",
					success : function(data) {
						if(data.result==1)
						{
							var content = "";
							for(var i=0;i<data.object.length;i++)
							{	
								content+="<option value="+data.object[i].id+">"+data.object[i].name+"</option>";
							}
							$("#select_taskbook_zerenjigouren").empty();
				        	$("#select_taskbook_zerenjigouren").append(content);
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
	$("#addtaskbook_form input").focus(function(){
	    $(this).next(".input_tip").show();

	}); 
	$("#addtaskbook_form input").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	$("#addtaskbook_form select").focus(function(){
	    $(this).next(".input_tip").show();

	}); 
	$("#addtaskbook_form select").blur(function(){
	   $(this).next(".input_tip").hide();
	});

	$("#addtaskbook_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#edittaskbook_form input").focus(function(){
	    $(this).next(".input_tip").show();

	}); 
	$("#edittaskbook_form input").blur(function(){
	   $(this).next(".input_tip").hide();
	});
	$("#edittaskbook_form select").focus(function(){
	    $(this).next(".input_tip").show();

	}); 
	$("#edittaskbook_form select").blur(function(){
	   $(this).next(".input_tip").hide();
	});

	$("#edittaskbook_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
});
function checkTaskNameExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var timenow = new Date().getTime();
	$.ajax({
		url:basePath + '/securityinfo/checkTaskNameExist.action',
		data:'taskBook.name=' + $('#name').val(),
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证任命名称是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}