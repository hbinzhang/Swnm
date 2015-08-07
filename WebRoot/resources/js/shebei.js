$(function(){

	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	
	$("#tipcs").tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#tipcs").colorTable();
	$('#ipc_select_all').on('click',function(d,e){
		$(this).prop('checked',this.checked);
		$('#tipcs .ipcselected').prop('checked',this.checked);
	});
	$('#tnvrs').tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#tnvrs").colorTable();
	$('#nvr_select_all').on('click',function(d,e){
		$(this).prop('checked',this.checked);
		$('#tnvrs .nvrselected').prop('checked',this.checked);
	});
	$('#tdecoders').tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#tdecoders").colorTable();
	$('#decoder_select_all').on('click',function(d,e){
		$(this).prop('checked',this.checked);
		$('#tdecoders .decoderselected').prop('checked',this.checked);
	});
	//peicaiyun add iva
	$('#tivas').tablesorter({
		headers: {
			0: {
				sorter: false
			}
		}
	});
	$("#tivas").colorTable();
	$('#iva_select_all').on('click',function(d,e){
		$(this).prop('checked',this.checked);
		$('#tivas .ivaselected').prop('checked',this.checked);
	});
	
	
	
	/*$(".delete").click(function(){
		if(confirm("确认删除此条记录吗?"))
		{
			var tr = $(this).parent().parent();
			var location = (window.location+'').split('/'); 
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			$.ajax({
				url:basePath + '/videomonitor/deleteIpc.action',
				data:'ipcidToDelete=' + tr.find('td:first-child').html(),
				type:'POST',
				success:function(d,e){
					tr.remove();
					window.location = basePath + '/videomonitor/loadIpc.action';
					alert('删除成功！');
				},
				error:function(d,e){
					alert(d.responseText);
				}
			});
			return true;
		}
		return false;
	});*/
	$('#ipcdelete').click(function(){
		var ipcids = '';
		$('#tipcs tr:gt(0)').each(function(e,d){
			var ischecked = $(this).find('.ipcselected').prop('checked');
			if(!ischecked){
				return true;
			}
			ipcids += $(this).find('td#tdipcid').html() + ',';
		});
		if(ipcids==''){
			alert('请勾选所要删除的设备！');
			return;
		}
		if(confirm('确认删除所勾选的设备吗？')){
			$.ajax({
				url:basePath + '/videomonitor/deleteIpc.action',
				dataType:'json',
				data:'ipcidToDelete=' + ipcids,
				type:'POST',
				success:function(d,e){
					window.location = basePath + '/videomonitor/loadIpc.action';
					alert(d==''?'删除成功！':d);
				},
				error:function(d,e){
					alert('删除失败！');
				}
			});
		}
	});
	/*$(".delete_nvr").click(function(){
		if(confirm("确认删除此条记录吗?"))
		{
			var tr = $(this).parent().parent();
			var location = (window.location+'').split('/'); 
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			$.ajax({
				url:basePath + '/videomonitor/deleteNvr.action',
				data:'nvridToDelete=' + tr.find('td:first-child').html(),
				type:'POST',
				success:function(d,e){
					tr.remove();
					window.location = basePath + '/videomonitor/loadNvr.action';
					alert('删除成功！');
				},
				error:function(d,e){
					alert(d.responseText);
				},
			});
			return true;
		}
		return false;
	});*/
	$("#nvrdelete").click(function(){
		var nvrids = '';
		$('#tnvrs tr:gt(0)').each(function(e,d){
			var ischecked = $(this).find('.nvrselected').prop('checked');
			if(!ischecked){
				return true;
			}
			nvrids += $(this).find('td#tdnvrid').html() + ',';
		});
		if(nvrids==''){
			alert('请勾选所要删除的设备！');
			return;
		}
		if(confirm("确认删除所勾选的设备吗？")){
			$.ajax({
				url:basePath + '/videomonitor/deleteNvr.action',
				data:'nvridToDelete=' + nvrids,
				type:'POST',
				success:function(d,e){
					window.location = basePath + '/videomonitor/loadNvr.action';
					alert('删除成功！');
				},
				error:function(d,e){
					alert('删除失败！');
				},
			});
			return true;
		}
		return false;
	});
	$("#yinpinshebei_addbtn").click(function(){
    	/*$('#yinpinshebei_add').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});*/
		var url = basePath + '/videomonitor/loadAddIpc.action'; 
		window.location = url;
	});
	$("#nvrshebei_addbtn").click(function(){
    	/*$('#yinpinshebei_add').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});*/
		var url = basePath + '/videomonitor/loadAddNvr.action'; 
		window.location = url;
	});
	$(".edit_yinpin").click(function(){
    	/*$('#yinpinshebei_edit').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});*/
		var tr = $(this).parent().parent();
		var ipcid = tr.find('td#tdipcid').html();
		var url = basePath + '/videomonitor/loadUpdateIpc.action?ipcToAddOrUpdate.ipcid='+ipcid; 
		window.location = url;
	});
	$(".edit_nvr").click(function(){
    	/*$('#yinpinshebei_edit').dialogModal({
			onOkBut: function() {},
			onCancelBut: function() {},
			onLoad: function() {},
			onClose: function() {}
		});*/
		var tr = $(this).parent().parent();
		var nvrid = tr.find('td#tdnvrid').html();
		var url = basePath + '/videomonitor/loadUpdateNvr.action?nvrToAddOrUpdate.nvrid='+nvrid; 
		window.location = url;
	});
	$('#isearch').click(function(){
		$('#searchform').submit();
	});
	$('#nsearch').click(function(){
		$('#nsearchform').submit();
	});
	
	$('#decoder_addbtn').click(function(){
		var url = basePath + '/videomonitor/loadAddDecoder.action'; 
		window.location = url;
	});
	$(".edit_decoder").click(function(){
		var tr = $(this).parent().parent();
		var decoderid = tr.find('td#tddecoderid').html();
		var url = basePath + '/videomonitor/loadUpdateDecoder.action?decoderToAddOrUpdate.decoderid='+decoderid; 
		window.location = url;
	});
	$('#dsearch').click(function(){
		$('#dsearchform').submit();
	});
	/*$('.delete_decoder').click(function(){
		if(confirm("确认删除此条记录吗?"))
		{
			var tr = $(this).parent().parent();
			var location = (window.location+'').split('/'); 
			var basePath = location[0]+'//'+location[2]+'/'+location[3];
			$.ajax({
				url:basePath + '/videomonitor/deleteDecoder.action',
				data:'decoderidToDelete=' + tr.find('td:first-child').html(),
				type:'POST',
				success:function(d,e){
					tr.remove();
					window.location = basePath + '/videomonitor/loadDecoder.action';
					alert('删除成功！');
				},
				error:function(d,e){
					alert(d.responseText);
				},
			});
			return true;
		}
		return false;
	});*/
	
	$('#decoderdelete').click(function(){
		var decoderids = '';
		$('#tdecoders tr:gt(0)').each(function(e,d){
			var ischecked = $(this).find('.decoderselected').prop('checked');
			if(!ischecked){
				return true;
			}
			decoderids += $(this).find('td#tddecoderid').html() + ',';
		});
		if(decoderids==''){
			alert('请勾选所要删除的设备！');
			return;
		}
		if(confirm("确认删除所勾选的设备吗？")){
			$.ajax({
				url:basePath + '/videomonitor/deleteDecoder.action',
				data:'decoderidToDelete=' + decoderids,
				type:'POST',
				success:function(d,e){
					window.location = basePath + '/videomonitor/loadDecoder.action';
					alert('删除成功！');
				},
				error:function(d,e){
					alert('删除失败！');
				},
			});
			return true;
		}
		return false;
	});
	
	//peicaiyun add iva
	$('#iva_addbtn').click(function(){
		var url = basePath + '/videomonitor/loadAddIva.action'; 
		window.location = url;
	});
	$(".edit_iva").click(function(){
		var tr = $(this).parent().parent();
		var ivaid = tr.find('td:eq(1)').html();
		var url = basePath + '/videomonitor/loadUpdateIva.action?ivaToAddOrUpdate.ivaID='+ivaid; 
		window.location = url;
	});
	
	$('#ivasearch').click(function(){
		$('#ivasearchform').submit();
	});
		
	$('#ivadelete').click(function(){
		var ivaids = '';
		$('#tivas tr:gt(0)').each(function(e,d){
			var ischecked = $(this).find('.ivaselected').prop('checked');
			if(!ischecked){
				return true;
			}
			ivaids += $(this).find('td:eq(1)').html() + ',';
		});
		if(ivaids==''){
			alert('请勾选所要删除的设备！');
			return;
		}
		if(confirm("确认删除所勾选的设备吗？")){
			$.ajax({
				url:basePath + '/videomonitor/deleteIva.action',
				data:'ivaidToDelete=' + ivaids,
				type:'POST',
				success:function(d,e){
					window.location = basePath + '/videomonitor/loadIva.action';
					alert('删除成功！');
				},
				error:function(d,e){
					alert('删除失败！');
				},
			});
			return true;
		}
		return false;
	});
	/*$.extend($.validationEngineLanguage.allRules,{ "checkIpcIdExist": {
		  "url": basePath + '/videomonitor/checkIdExist.action',
		  "extraData": 'ipcToAddOrUpdate.ipcid=' + $('#fipcid').val(),
		  "alertText": "* ID已经存在！",
		  "alertTextLoad": "* 验证中，请稍候..."}
		    });*/
});

/*function checkip(ipdom){
	var regx = "^(((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))$";
	if(!ipdom.val().match(regx)){
		ipdom.val('');
		alert('IP格式不正确！');
	}
}*/

function checkIpcIdExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	$.ajax({
		url:basePath + '/videomonitor/checkIpcIdExist.action',
		data:'ipcToAddOrUpdate.ipcid=' + $('#fipcid').val(),
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证ID是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}

function checkNvrIdExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	$.ajax({
		url:basePath + '/videomonitor/checkNvrIdExist.action',
		data:'nvrToAddOrUpdate.nvrid=' + $('#invrid').val(),
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证ID是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}

function checkDecoderIdExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	$.ajax({
		url:basePath + '/videomonitor/checkDecoderIdExist.action',
		data:'decoderToAddOrUpdate.decoderid=' + $('#fdecoderid').val(),
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证ID是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}
//peicaiyun add iva
function checkIvaIdExistAjax(field, rules, i, options){
	var res = '';
	var location = (window.location+'').split('/'); 
	var basePath = location[0]+'//'+location[2]+'/'+location[3];
	$.ajax({
		url:basePath + '/videomonitor/checkIvaIdExist.action',
		data:'ivaToAddOrUpdate.ivaID=' + $('#fivaid').val(),
		type:'POST',
		async:false,
		success:function(d,e){
			res = d;
		},
		error:function(d,e){
			alert('验证ID是否存在失败！');
		},
	});
	if(res && res != ""){
		return res;
	}
}


function saveipc(isadd,formid){
	var res = $("#"+formid).validationEngine('validate');
	if(res){
		$("#"+formid).ajaxSubmit({
			success:function(){
				var location = (window.location+'').split('/'); 
				var basePath = location[0]+'//'+location[2]+'/'+location[3];
				alert('保存成功！');
				window.location = basePath + "/videomonitor/loadIpc.action";
			},
			error:function(){
				alert('保存失败！');
			}
		});
	}
}

function savenvr(isadd,formid){
	var res = $("#"+formid).validationEngine('validate');
	if(res){
		$("#"+formid).ajaxSubmit({
			success:function(){
				var location = (window.location+'').split('/'); 
				var basePath = location[0]+'//'+location[2]+'/'+location[3];
				alert('保存成功！');
				window.location = basePath + "/videomonitor/loadNvr.action";
			},
			error:function(){
				alert('保存失败！');
			}
		});
	}
}

function savedecoder(isadd,formid){
	var res = $("#"+formid).validationEngine('validate');
	if(res){
		$("#"+formid).ajaxSubmit({
			success:function(){
				var location = (window.location+'').split('/'); 
				var basePath = location[0]+'//'+location[2]+'/'+location[3];
				alert('保存成功！');
				window.location = basePath + "/videomonitor/loadDecoder.action";
			},
			error:function(){
				alert('保存失败！');
			}
		});
	}
}
//peicaiyun add iva
function saveiva(isadd,formid){
	var res = $("#"+formid).validationEngine('validate');
	if(res){
		$("#"+formid).ajaxSubmit({
			success:function(){
				var location = (window.location+'').split('/'); 
				var basePath = location[0]+'//'+location[2]+'/'+location[3];
				alert('保存成功！');
				window.location = basePath + "/videomonitor/loadIva.action";
			},
			error:function(){
				alert('保存失败！');
			}
		});
	}
}
