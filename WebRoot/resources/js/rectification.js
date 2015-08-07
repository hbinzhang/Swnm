$(function(){
//创建
$("#addrectification_btn").click(function(){
	if($("#addrectification_form").validationEngine('validate'))
	{	
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#addrectification_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/createRectification?d="+timenow,
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
//编辑
$("#editrectification_btn").click(function(){
	if($("#editrectification_form").validationEngine('validate'))
	{
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#editrectification_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/updateRectification?d="+timenow,
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
//删除
$(".deleterectification").click(function(){
	if(confirm("确定要删除该整改记录吗?")){
	var obj = $(this);
	var name = $(this).attr("name");
	var belongToOrgId = $(this).attr("belongToOrgId");
	var timenow = new Date().getTime();				
        jQuery.ajax({
			type : 'GET',
			cache:false,
			url : $("#ctx").val()+"/securityinfo/deleteRectification?name="+name+"&belongToOrgId="+belongToOrgId+"&d="+timenow,
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
});
function checkRectificationNameExistAjax(field, rules, i, options){
	var res = ''
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var jsonData ="rectification.name="+$('#name').val()+"&rectification.belongToOrgId="+$('#belongToOrgId').val();
	$.ajax({
		url:basePath + '/securityinfo/checkRectificationNameExist.action?'+jsonData,
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证整改记录是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}

