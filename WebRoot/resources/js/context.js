function openwin(userid,orgid) 
{
	 //var w = screen.availWidth, 
     //h = screen.availHeight; 
	 //OpenWindow=window.open($("#ctx").val()+"/gis.jsp?userID="+userid+"&orgID="+orgid, "newwin", "height="+screen.availHeight+", width="+screen.availWidth+",top=0,left="+screen.availWidth+",toolbar=no,scrollbars="+scroll+",menubar=no");
	
	var w = screen.availWidth-20;//wyj 
	var h = screen.availHeight-8;//wyj
	OpenWindow=window.open($("#ctx").val()+"/gis.jsp?userID="+userid+"&orgID="+orgid, "newwin", "height="+h+", width="+w+",top=0,left="+screen.availWidth+ ",toolbar=no,scrollbars=no,menubar=no,location=no,status=no,resizable=no,titlebar=no,depended=yes");//wyj

} 

function getAlarmCount()
{
	var timenow = new Date().getTime();
	jQuery.ajax({
		type : 'GET',
		url : $("#ctx").val()+"/alarmmgt/queryAlarmCount?d="+timenow,
		contentType: "application/json; charset=utf-8",
		success : function(data) {
			if(data.result==1)
			{
				$("#socket_anfanggaojing").text(data.object[0]);
				$("#socket_shebeigaojing").text(data.object[1]);
			}
			else
			{
			}
		},
		error : function(data) {
		}
	});
}
function pushGaojingDetail(obj){
	var alarmid = $(obj).attr("alarmid");
	$(".detail_table tbody tr .content_bianhao").html($("#alarm"+alarmid+"_bianhao").text());
	$(".detail_table tbody tr .content_gaojingma").html($("#alarm"+alarmid+"_gaojingma").text());
	$(".detail_table tbody tr .content_gaojingmingcheng").html($("#alarm"+alarmid+"_gaojingmingcheng").text());
	$(".detail_table tbody tr .content_gaojingshijian").html($("#alarm"+alarmid+"_gaojingshijian").text());
	$(".detail_table tbody tr .content_gaojingjibie").html($("#alarm"+alarmid+"_gaojingjibie").text());
	$(".detail_table tbody tr .content_gaojingleixing").html($("#alarm"+alarmid+"_gaojingleixing").text());
	$(".detail_table tbody tr .content_shebeileixing").html($("#alarm"+alarmid+"_shebeileixing").text());
	$(".detail_table tbody tr .content_shebeiid").html($("#alarm"+alarmid+"_shebeiid").text());
	$(".detail_table tbody tr .content_zhuangtai").html($("#alarm"+alarmid+"_zhuangtai").text());
	$(".detail_table tbody tr .content_fengongsi").html($("#alarm"+alarmid+"_fengongsi").text());
	$(".detail_table tbody tr .content_guanlichu").html($("#alarm"+alarmid+"_guanlichu").text());
	$(".detail_table tbody tr .content_fangqu").html($("#alarm"+alarmid+"_fangqu").text());
	$(".detail_table tbody tr .content_yuanyin").html($("#alarm"+alarmid+"_yuanyin").text());
	$(".detail_table tbody tr .content_houguo").html($("#alarm"+alarmid+"_houguo").text());
	$(".detail_table tbody tr .content_caozuo").html($("#alarm"+alarmid+"_caozuo").text());
	$(".detail_table tbody tr .content_yingxiang").html($("#alarm"+alarmid+"_yingxiang").text());
	$(".detail_table tbody tr .content_fujia").html($("#alarm"+alarmid+"_fujia").text());
	$('#gaojingjianshi_info').dialogModal({
		onOkBut: function() {},
		onCancelBut: function() {},
		onLoad: function() {},
		onClose: function() {}
	});
}
//查看账号详细信息
function viewUserDetail(obj)
{
	var zhanghaoid = $(obj).attr("zhanghaoid");
	$(".detail_table tbody tr .content_yonghuming").html($("#zhanghao"+zhanghaoid+"_yonghuming").text());
	$(".detail_table tbody tr .content_xingming").html($("#zhanghao"+zhanghaoid+"_xingming").text());
	$(".detail_table tbody tr .content_xingbie").html($("#zhanghao"+zhanghaoid+"_xingbie").text());
	$(".detail_table tbody tr .content_gonghao").html($("#zhanghao"+zhanghaoid+"_gonghao").text());
	$(".detail_table tbody tr .content_zhanghaoleixing").html($("#zhanghao"+zhanghaoid+"_zhanghaoleixing").text());
	$(".detail_table tbody tr .content_chushengriqi").html($("#zhanghao"+zhanghaoid+"_chushengriqi").text());
	$(".detail_table tbody tr .content_jigoumingcheng").html($("#zhanghao"+zhanghaoid+"_jigoumingcheng").text());
	$(".detail_table tbody tr .content_zhiwu").html($("#zhanghao"+zhanghaoid+"_zhiwu").text());
	$(".detail_table tbody tr .content_zhiwumiaoshu").html($("#zhanghao"+zhanghaoid+"_zhiwumiaoshu").text());
	$(".detail_table tbody tr .content_youxiang").html($("#zhanghao"+zhanghaoid+"_youxiang").text());
	$(".detail_table tbody tr .content_shouji").html($("#zhanghao"+zhanghaoid+"_shouji").text());
	$(".detail_table tbody tr .content_bangongdianhua").html($("#zhanghao"+zhanghaoid+"_bangongdianhua").text());
	$(".detail_table tbody tr .content_jiatingdianhua").html($("#zhanghao"+zhanghaoid+"_jiatingdianhua").text());
	$(".detail_table tbody tr .content_jiatingzhuzhi").html($("#zhanghao"+zhanghaoid+"_jiatingzhuzhi").text());
	$(".detail_table tbody tr .content_juese").html($("#zhanghao"+zhanghaoid+"_juese").text());
	$(".detail_table tbody tr .content_beizhuxinxi").html($("#zhanghao"+zhanghaoid+"_beizhuxinxi").text());
	$('#zhanghao_detail_container').dialogModal({
		onOkBut: function() {},
		onCancelBut: function() {},
		onLoad: function() {},
		onClose: function() {}
	});
}
$(function()
{
	getAlarmCount();
	
	//-------------------------------------用户修改密码-----------
	$("#change_user_password").click(function(){
		var contentdiv = "<div>";
		contentdiv +="<form action='' id='edit_password_form'>";
		contentdiv +='<div class="top10">';
		contentdiv +='<div class="" style="width: 96%;">';
		contentdiv +='<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">初始密码:</label>';
		contentdiv +='<input type="hidden" id="changePasResult" value="0"/>';
		contentdiv +='<input type="password" id="oldpasswd" name="oldPasswd" class="normaltext left validate[maxSize[32]]" style="width: 70%;"/>';
		contentdiv +='<div class="clear"></div>';
		contentdiv +='</div>';
		contentdiv +='<div class="top20" style="width: 96%;">';
		contentdiv +='<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">新密码:</label>';
		contentdiv +='<input type="password" id="newpasswd" name="password" class="normaltext left validate[maxSize[32]]" style="width: 70%;"/>';
		contentdiv +='<div class="clear"></div>';
		contentdiv +='</div>';
		contentdiv +='<div class="top20" style="width: 96%;">';
		contentdiv +='<label style="display: block;font-size:13px;width:20%;float: left;text-align: right;height:30px;line-height:30px;">确认新密码:</label>';
		contentdiv +='<input type="password" id="renewpasswd" name="rePassword" class="normaltext left validate[maxSize[32]]" style="width: 70%;"/>';
		contentdiv +='<div class="clear"></div>';
		contentdiv +='</div>';
		contentdiv +='</div>';
		$('#edit_password_container .dialogModal_content').empty();
		$('#edit_password_container .dialogModal_content').append(contentdiv);	
		$('#edit_password_container').dialogModal({
			onOkBut: function() {
				var oldPswNode = $("#edit_password_form #oldpasswd")[1];
				var newPswNode = $("#edit_password_form #newpasswd")[1];
				var rePswNode = $("#edit_password_form #renewpasswd")[1];
				var oldPass = $(oldPswNode).val();
				var newPass = $(newPswNode).val();
				var rePass = $(rePswNode).val();
				if(oldPass==""||newPass==""||rePass=="")
				{
					alert("请输入完整的信息");
					return false;
				}
				if(newPass.length>32)
				{
					alert("密码长度不能超过32位");
					return false;
				}
				if(newPass!=rePass)
				{
					alert("新密码和确认新密码不一致");
					return false;
				}
				var jsonData ="password="+encodeURI(newPass,"UTF-8")+"&oldPasswd="+encodeURI(oldPass,"UTF-8")+"&rePassword="+encodeURI(rePass,"UTF-8");
				jQuery.ajax({
					type : 'post',
					contentType : 'application/json',
					url : $("#ctx").val()+"/authmgt/updateAccountPassword?"+jsonData,
					success : function(data) {
						if(data.result==1)
						{
							alert("密码修改成功");
							$(".dialogModal").remove();
							$('body').removeClass('dialogModal' + 'Open');
							$('html.' + elemClass + 'Open').off('.' + 'dialogModal' + 'Event').removeClass('dialogModal' + 'Open');
							$(".dialogModal").find('.' + prevBut).off('click');
							$(".dialogModal").find('.' + nextBut).off('click');
						}
						else
						{
							alert(data.message);
						}
					},
					error : function(data) {
						alert(data + "请求出现异常!");
					}
				});
				return false;
			},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
	//-------------------------------------系统页面必须----------------------
	var windowHeight = $(window).height();
	$("#main_content_container").css("min-height",windowHeight-88-50);
	$("#lefttree").css("min-height",windowHeight-88-30);
	$(window).resize(function(){  
		$("#main_content_container").css("min-height",windowHeight-88-50);
	});
	$(".tableui_container .tableui").colorTable();
	$(".tableui_container .tableui").tablesorter();
	$(".imgtable").tablesorter();
	$(".tableui").resizableColumns({
		store: window.store
	});
	//---------------------删除提示框----------------------------------
	/*$(".delete").click(function(){
		if(confirm("确定要删除记录吗?"))
		{
			$(this).parent().parent().remove();
			return true;
		}
		return false;
	});*/
	$(".deleterole").click(function(){
		if(confirm("确定要删除角色吗?"))
		{
			var obj = $(this);
			var rolename = $(this).attr("rolename");			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/authmgt/deleteRole?commandId=103010005&name="+rolename+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
			    },
				success : function(data) {
					if(data.result==1)
					{
						alert(data.message);
						$(obj).parent().parent().remove();
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
		return false;
	});
	$("#roleform").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#organizationform").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	$("#accountform").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	
	$("#weilan_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	
	$("#zhanghao_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	
	$("#edit_password_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	
	$("#huihua_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});	
	
	$("#anquanrizhi_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});	
	$("#caozuorizhi_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});	
	//------------------------------------告警监视----------------------------------------
	//告警监视中的处理操作
	$(".gaojingchuli").click(function(){
		if(confirm("确定此告警已经得到处理吗?"))
		{
	        return true;
		}
		return false;
	});
	//查看告警监视详细信息
	$(".gaojingjianshi_detail").click(function(){
		var alarmid = $(this).attr("alarmid");
		$(".detail_table tbody tr .content_bianhao").html($("#alarm"+alarmid+"_bianhao").text());
		$(".detail_table tbody tr .content_gaojingma").html($("#alarm"+alarmid+"_gaojingma").text());
		$(".detail_table tbody tr .content_gaojingmingcheng").html($("#alarm"+alarmid+"_gaojingmingcheng").text());
		$(".detail_table tbody tr .content_gaojingshijian").html($("#alarm"+alarmid+"_gaojingshijian").text());
		$(".detail_table tbody tr .content_gaojingjibie").html($("#alarm"+alarmid+"_gaojingjibie").text());
		$(".detail_table tbody tr .content_gaojingleixing").html($("#alarm"+alarmid+"_gaojingleixing").text());
		$(".detail_table tbody tr .content_shebeileixing").html($("#alarm"+alarmid+"_shebeileixing").text());
		$(".detail_table tbody tr .content_shebeiid").html($("#alarm"+alarmid+"_shebeiid").text());
		$(".detail_table tbody tr .content_zhuangtai").html($("#alarm"+alarmid+"_zhuangtai").text());
		$(".detail_table tbody tr .content_fengongsi").html($("#alarm"+alarmid+"_fengongsi").text());
		$(".detail_table tbody tr .content_guanlichu").html($("#alarm"+alarmid+"_guanlichu").text());
		$(".detail_table tbody tr .content_fangqu").html($("#alarm"+alarmid+"_fangqu").text());
		$(".detail_table tbody tr .content_yuanyin").html($("#alarm"+alarmid+"_yuanyin").text());
		$(".detail_table tbody tr .content_houguo").html($("#alarm"+alarmid+"_houguo").text());
		$(".detail_table tbody tr .content_caozuo").html($("#alarm"+alarmid+"_caozuo").text());
		$(".detail_table tbody tr .content_yingxiang").html($("#alarm"+alarmid+"_yingxiang").text());
		$(".detail_table tbody tr .content_fujia").html($("#alarm"+alarmid+"_fujia").text());
    	$('#gaojingjianshi_info').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
	
	//告警知识库编辑字数限制
	$("#gaojingzhishiku_edit_form").validationEngine({
		validationEventTriggers:"blur",
		inlineValidation: true,
		success :  false,
		promptPosition: "topRight"
	});
	/*$(".alarmKnowledgecause").hide();
	$(".alarmKnowledgeresult").hide();
	$(".alarmKnowledgeoperation").hide();
	$(".alarmKnowledgeinfo").hide();
	
	$("#alarmKnowledgecause").blur(function(){
		var alarmKnowledgecause = $("#alarmKnowledgecause").val();
		$.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/alarmmgt/validateLength?str="+alarmKnowledgecause,
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result == 1)
				{
					$(".alarmKnowledgecause").show();
				}
				else
				{
					$(".alarmKnowledgecause").hide();
				}
			},
			error : function(data) {
				alert("请求出现异常!");
			}
		});
	});
	
	$("#alarmKnowledgeresult").blur(function(){
		var alarmKnowledgeresult = $("#alarmKnowledgeresult").val();
		$.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/alarmmgt/validateLength?str="+alarmKnowledgeresult,
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result ==1 )
				{
					$(".alarmKnowledgeresult").show();
				}
				else
				{
					$(".alarmKnowledgeresult").hide();
				}
			},
			error : function(data) {
				alert("请求出现异常!");
			}
		});
	});
	
	$("#alarmKnowledgeoperation").blur(function(){
		var alarmKnowledgeoperation = $("#alarmKnowledgeoperation").val();
		$.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/alarmmgt/validateLength?str="+alarmKnowledgeoperation,
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result ==1)
				{
					$(".alarmKnowledgeoperation").show();
				}
				else
				{
					$(".alarmKnowledgeoperation").hide();
				}
			},
			error : function(data) {
				alert("请求出现异常!");
			}
		});
	});
	
	$("#alarmKnowledgeinfo").blur(function(){
		var alarmKnowledgeinfo = $("#alarmKnowledgeinfo").val();
		$.ajax({
			type : 'GET',
			url : $("#ctx").val()+"/alarmmgt/validateLength?str="+alarmKnowledgeinfo,
			contentType: "application/json; charset=utf-8",
			success : function(data) {
				if(data.result ==1)
				{
					$(".alarmKnowledgeinfo").show();
				}
				else
				{
					$(".alarmKnowledgeinfo").hide();
				}
			},
			error : function(data) {
				alert("请求出现异常!");
			}
		});
	});*/

	//-------------------------------------账号管理-------------------------------------
	//账号管理中的表格分页和查询
	$("#search_users").click(function(){
		if($("#zhanghao_form").validationEngine('validate'))
		{
			var searchValue = $("#search_user_value").val()+"";
			$("#userlist_table_search tbody").empty();
			if(searchValue==null||searchValue=="")
			{
				$("#tableui_container").hide();
				$(".tableui_container").show();
				$("#pagin0").show();
			}
			else
			{
				$("#userlist_table tbody tr").each(function(trindex,tritem){
					var gonghao = $(tritem).attr("id");
					if(gonghao.indexOf(searchValue)>=0)
					{
						var temp = $(tritem).html();
						$("#userlist_table_search tbody").append("<tr>"+temp+"</tr>");
					}
					$("#tableui_container").show();
					$(".tableui_container").hide();
					$("#userlist_table_search").colorTable();
					$("#pagin0").hide();
	            });
			}
		}
	});
	//删除账号
	$(".deleteaccount").click(function(){
		if(confirm("确定要删除该帐号吗?"))
		{
			var obj = $(this);
			var gonghao = $(this).attr("gonghao");			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/authmgt/deleteAccount?id="+gonghao+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
				success : function(data) {
					if(data.result==1)
					{
						alert(data.message);
						$(obj).parent().parent().remove();
						TablePage("#userlist_table",15,"#pagin0",0,"pagin0");
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
		return false;
	});
	$(".changepasswdlink").click(function(){
		if(confirm("确定要重置该工号的密码吗?"))
		{
			var gonghao = $(this).attr("gonghao");			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/authmgt/resetAccountPassword?id="+gonghao+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
				success : function(data) {
					if(data.result==1)
					{
						alert(data.message);
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
		return false;
	});
	//-------------------------------------会化管理----------------------------------
	//会话等管理中按照工号和按照机构查询的控制
	$("#jigou_gonghao_search_control input[type='radio']").change(function(){
        if($(this).val()==1)
        {
        	$("#searchjigou_container").show();
        	$("#searchgonghao_container").hide();
        	$("#search_account").attr("disabled",true);
        	$("#searchjigou_container select").attr("disabled",false);
        }
        else
        {
        	$("#searchgonghao_container").show();
        	$("#searchjigou_container").hide();
        	$("#searchjigou_container select").attr("disabled",true);
        	$("#search_account").attr("disabled",false);
        }
    });
	$(".clearuserlink").click(function(){
		if(confirm("确定要清除此用户吗?"))
		{
			var obj = $(this);
			var contextid = $(this).attr("contextid");			var timenow = new Date().getTime();
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/authmgt/deleteSession?contextId="+contextid+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
				success : function(data) {
					if(data.result==1)
					{
						alert(data.message);
						$(obj).parent().parent().remove();
						TablePage("#session_table",15,"#pagin0",0,"pagin0");
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
		return false;
	});
	//查看角色详细信息
	$(".role_detail").click(function(){
		var roleindex = $(this).attr("roleindex");
		$(".detail_table tbody tr .content_rolename").html($("#role"+roleindex+"_rolename").text());
		$(".detail_table tbody tr .content_roledesc").html($("#role"+roleindex+"_roledescription").text());
		$(".detail_table tbody tr .content_ops").html($("#role"+roleindex+"_ops").text());
    	$('#role_detail_container').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
	//选中role下的权限
	$(".selectallpermissions").change(function(){
		if($(this).is(':checked'))
		{
			$(this).parent().parent().find("label input[type='checkbox']").prop("checked", true);
		}
		else
		{
			$(this).parent().parent().find("label input[type='checkbox']").prop("checked", false);
		}
	});
	
	//--------------------------------------日志管理----------------------------------------
	//查看安全日志的详细信息
	$(".anquan_detail").click(function(){
		var liushuihao = $(this).attr("liushuihao");
		$(".detail_table tbody tr .content_liushuihao").html($("#anquan"+liushuihao+"_liushuihao").text());
		$(".detail_table tbody tr .content_caozuoshijian").html($("#anquan"+liushuihao+"_caozuoshijian").text());
		$(".detail_table tbody tr .content_gonghao").html($("#anquan"+liushuihao+"_gonghao").text());
		$(".detail_table tbody tr .content_caozuobiaoshi").html($("#anquan"+liushuihao+"_caozuobiaoshi").text());
		$(".detail_table tbody tr .content_caozuojieguo").html($("#anquan"+liushuihao+"_caozuojieguo").text());
		$(".detail_table tbody tr .content_zhujimingcheng").html($("#anquan"+liushuihao+"_zhujimingcheng").text());
		$(".detail_table tbody tr .content_zhujidizhi").html($("#anquan"+liushuihao+"_zhujidizhi").text());
		$(".detail_table tbody tr .content_jigoubiaoshi").html($("#anquan"+liushuihao+"_jigoubiaoshi").text());
		$(".detail_table tbody tr .content_xiangxixinxi").html($("#anquan"+liushuihao+"_xiangxixinxi").text());
    	$('#anquan_detail_container').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
	$("#clearAnquanLog").click(function(){
		if(confirm("确定要清除查询结果吗?"))
		{
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/logmgt/deleteSecurityLog?"+$("#searchUri").val(),
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
				success : function(data) {
					if(data.result==1)
					{
						alert("删除成功");
						$("#anquanTableContainer tbody").empty();
						$("#anquanrizhi_page_container").empty();
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
	//查看操作日志的详细信息
	$(".caozuo_detail").click(function(){
		var liushuihao = $(this).attr("liushuihao");
		$(".detail_table tbody tr .content_liushuihao").html($("#caozuo"+liushuihao+"_liushuihao").text());
		$(".detail_table tbody tr .content_caozuoshijian").html($("#caozuo"+liushuihao+"_caozuoshijian").text());
		$(".detail_table tbody tr .content_gonghao").html($("#caozuo"+liushuihao+"_gonghao").text());
		$(".detail_table tbody tr .content_caozuobiaoshi").html($("#caozuo"+liushuihao+"_caozuobiaoshi").text());
		$(".detail_table tbody tr .content_caozuoduixiang").html($("#caozuo"+liushuihao+"_caozuoduixiang").text());
		$(".detail_table tbody tr .content_caozuojieguo").html($("#caozuo"+liushuihao+"_caozuojieguo").text());
		$(".detail_table tbody tr .content_zhujimingcheng").html($("#caozuo"+liushuihao+"_zhujimingcheng").text());
		$(".detail_table tbody tr .content_zhujidizhi").html($("#caozuo"+liushuihao+"_zhujidizhi").text());
		$(".detail_table tbody tr .content_jieshushijian").html($("#caozuo"+liushuihao+"_jieshushijian").text());
		$(".detail_table tbody tr .content_jigoumingcheng").html($("#caozuo"+liushuihao+"_jigoumingcheng").text());
		$(".detail_table tbody tr .content_caozuolaiyuan").html($("#caozuo"+liushuihao+"_caozuolaiyuan").text());
		$(".detail_table tbody tr .content_xiangxixinxi").html($("#caozuo"+liushuihao+"_xiangxixinxi").text());
    	$('#caozuo_detail_container').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});
	});
	$("#clearCaozuoLog").click(function(){
		if(confirm("确定要清除查询结果吗?"))
		{
			jQuery.ajax({
				type : 'GET',
				url : $("#ctx").val()+"/logmgt/deleteOperationLog?"+$("#searchUri").val(),
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
					$("#data_loading").show();
		        },
				success : function(data) {
					if(data.result==1)
					{
						alert("删除成功");
						$("#caozuoTableContainer tbody").empty();
						$("#caozuorizhi_page_container").empty();
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
	$("#gaojingchuli_select_all").prop("checked", false);
	$("input[name='chulialarmids']").prop("checked", false);
	$("#alarm_table").tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#gaojingchuli_select_all").on('click',function(){
		$("input[name='chulialarmids']").prop("checked", this.checked);
	});
	$("#gaojingpiliangchuli").click(function(){
		var arrChk=$("#chulialarmids:checked");
		if(arrChk.size()<=0)
		{
			alert("请选择要处理的告警信息");
			return false;
		}
		var chuliArray = new Array();
		$(arrChk).each(function(i)
		{
			if(!(chuliArray.indexOf($(this).attr("value"))>=0))
			{
				chuliArray.push($(this).attr("value"));
			}
		});
		var chuliParams = chuliArray.join(",");
		if(confirm("确定处理告警吗?")){
		var timenow = new Date().getTime();
	        jQuery.ajax({
				type : 'GET',
				cache:false,
				url : $("#ctx").val()+"/alarmmgt/handleDeviceAlarm?alarmStrs="+chuliParams+"&d="+timenow,
				contentType: "application/json; charset=utf-8",
				beforeSend:function(XMLHttpRequest){
			    	 $("#data_loading").show();
			     },
				success : function(data) {
					if(data.result==0)
					{
						alert(data.message);
						location.reload();
					}
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
}); 