$(function(){
//创建
$("#addrewardpunishment_btn").click(function(){

	if($("#addrewardpunishment_form").validationEngine('validate'))
	{	
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#addrewardpunishment_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/createRewardPunishment?d="+timenow,
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
$("#editrewardpunishment_btn").click(function(){
	if($("#editrewardpunishment_form").validationEngine('validate'))
	{
		var redirectUrl = $("#redirectUrl").val();
		var timenow = new Date().getTime();				
		$("#editrewardpunishment_form").ajaxSubmit({
		     type: "post",
			 url : $("#ctx").val()+"/securityinfo/updateRewardPunishment?d="+timenow,
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
$(".deleterewardpunishment").click(function(){
	if(confirm("确定要删除该奖惩记录吗?")){
	var obj = $(this);
	var name = $(this).attr("name");
	var belongToOrgId = $(this).attr("belongToOrgId");
	var timenow = new Date().getTime();				
        jQuery.ajax({
			type : 'GET',
			cache:false,
			url : $("#ctx").val()+"/securityinfo/deleteRewardPunishment?name="+name+"&belongToOrgId="+belongToOrgId+"&d="+timenow,
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
function checkRPNameExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	var jsonData ="rewardPunishment.name="+$('#name').val()+"&rewardPunishment.belongToOrgId="+$('#belongToOrgId').val();
	$.ajax({
		url:basePath + '/securityinfo/checkRPNameExist.action?'+jsonData,
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证奖惩记录是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}

